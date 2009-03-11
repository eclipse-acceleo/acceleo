/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.pde.core.IModelChangedEvent;
import org.eclipse.pde.core.IModelChangedListener;
import org.eclipse.pde.core.plugin.IExtensions;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

/**
 * Eclipse-specific utilities to handle workspace contributions of Java services, ATL libraries, ... Extension
 * points that need be aware of workspace-defined contributions should be added to the range searched for by
 * this utility. This can be done in {@link #isDynamicExtensionPoint(String)}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoWorkspaceUtil {
	/**
	 * This will keep references on the workspace's IPluginModelBase which have been changed since the last
	 * refresh.
	 */
	static final Set<IPluginModelBase> CHANGED_CONTRIBUTIONS = new HashSet<IPluginModelBase>();

	/** Keeps track of all listeners we've registered against plugin models. */
	static final Map<IPluginModelBase, IModelChangedListener> INSTALLED_MODEL_LISTENERS = new HashMap<IPluginModelBase, IModelChangedListener>();

	/** Keeps track of all manually loaded workspace bundles. */
	static final Map<IPluginModelBase, Bundle> WORKSPACE_INSTALLED_BUNDLES = new HashMap<IPluginModelBase, Bundle>();

	/** This will allow us to react to project additions/removals in the running workspace. */
	private static final IResourceChangeListener WORKSPACE_LISTENER = new WorkspaceResourcesListener();

	/**
	 * Utility classes don't need a default constructor.
	 */
	private AcceleoWorkspaceUtil() {
		// hides constructor
	}

	/**
	 * This can be used to uninstall all manually loaded bundles from the registry and remove all listeners.
	 * It will be called on plugin stopping and is not intended to be called by clients.
	 */
	public static void dispose() {
		CHANGED_CONTRIBUTIONS.clear();
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(WORKSPACE_LISTENER);

		for (Map.Entry<IPluginModelBase, Bundle> entry : WORKSPACE_INSTALLED_BUNDLES.entrySet()) {
			final IPluginModelBase model = entry.getKey();
			final Bundle bundle = entry.getValue();

			final IModelChangedListener listener = INSTALLED_MODEL_LISTENERS.remove(model);
			model.removeModelChangedListener(listener);

			try {
				uninstallBundle(bundle);
			} catch (BundleException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(
						"WorkspaceUtil.UninstallationFailure", bundle.getSymbolicName(), e //$NON-NLS-1$
								.getMessage()), true);
				AcceleoCommonPlugin.log(e, true);
			}
		}
		WORKSPACE_INSTALLED_BUNDLES.clear();

		for (Map.Entry<IPluginModelBase, IModelChangedListener> entry : INSTALLED_MODEL_LISTENERS.entrySet()) {
			final IPluginModelBase model = entry.getKey();
			final IModelChangedListener listener = entry.getValue();

			model.removeModelChangedListener(listener);
		}
		INSTALLED_MODEL_LISTENERS.clear();
	}

	/**
	 * Adds model listeners to all workspace-defined bundles. This will be called at plugin starting and is
	 * not intended to be called by clients.
	 */
	public static void initialize() {
		/*
		 * FIXME currently not reacting to project creations. Should : find the plugin model
		 * (PluginRegistry.findModel()), install the model listener and install the bundle if needed.
		 */
		final int interestingEvents = IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(WORKSPACE_LISTENER, interestingEvents);

		final IPluginModelBase[] workspaceModels = PluginRegistry.getWorkspaceModels();
		for (IPluginModelBase workspaceModel : workspaceModels) {
			final IModelChangedListener listener = new WorkspaceModelListener();
			workspaceModel.addModelChangedListener(listener);
			INSTALLED_MODEL_LISTENERS.put(workspaceModel, listener);
		}

		checkForInitialContributions();
	}

	/**
	 * This will refresh the list of contributions to the registry by either installing the given plugins in
	 * the current running Eclipse or refresh their packages. Keep this synchronized as it will be called by
	 * each of the utilities and these calls can come from multiple threads.
	 */
	public static synchronized void refreshContributions() {
		if (CHANGED_CONTRIBUTIONS.size() == 0) {
			return;
		}

		BundleContext context = AcceleoCommonPlugin.getDefault().getContext();
		ServiceReference jobManagerReference = context.getServiceReference(IJobManager.class.getName());
		IJobManager jobManager = null;
		if (jobManagerReference != null) {
			jobManager = (IJobManager)context.getService(jobManagerReference);
		}
		try {
			final boolean[] flag = new boolean[] {false,};
			for (IPluginModelBase candidate : CHANGED_CONTRIBUTIONS) {
				try {
					final IResource candidateManifest = candidate.getUnderlyingResource();
					final String candidateLocationReference = "reference:" //$NON-NLS-1$
							+ candidateManifest.getProject().getLocationURI().toURL().toExternalForm();

					// Prepare the job listener so that we can wait for the end of all registry updates
					Bundle bundle = getBundle(candidateLocationReference);
					final int expectedRegistryEvents;
					if (bundle == null) {
						expectedRegistryEvents = 1;
					} else {
						// one event for the removal of old contributions, 1 for the addition
						expectedRegistryEvents = 2;
					}
					final IJobChangeListener listener = new JobChangeAdapter() {
						private int expectedEvents = expectedRegistryEvents;

						@Override
						public void done(IJobChangeEvent event) {
							final IStatus result = event.getResult();
							if ("org.eclipse.equinox.registry".equals(result.getPlugin())) { //$NON-NLS-1$
								if (--expectedEvents == 0) {
									synchronized(flag) {
										flag[0] = true;
										flag.notifyAll();
									}
								}
							}
						}
					};
					if (jobManager != null) {
						jobManager.addJobChangeListener(listener);
					}

					// do the actual installation or refreshing of bundles
					if (bundle == null) {
						bundle = installBundle(candidateLocationReference);
						WORKSPACE_INSTALLED_BUNDLES.put(candidate, bundle);
					} else {
						refreshPackages(new Bundle[] {bundle,});
					}

					// wait for all registry updates to end and remove listener
					if (jobManager != null) {
						synchronized(flag) {
							while (!flag[0]) {
								try {
									flag.wait();
								} catch (InterruptedException e) {
									// discard
								}
							}
						}
						jobManager.removeJobChangeListener(listener);
					}
				} catch (BundleException e) {
					AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(
							"WorkspaceUtil.installationFailure", candidate //$NON-NLS-1$
									.getBundleDescription().getName(), e.getMessage()), false);
					AcceleoCommonPlugin.log(e, false);
				} catch (MalformedURLException e) {
					AcceleoCommonPlugin.log(e, false);
				}
			}
			CHANGED_CONTRIBUTIONS.clear();
		} finally {
			context.ungetService(jobManagerReference);
		}
	}

	/**
	 * This will return <code>true</code> if the given ID corresponds to one of Acceleo's dynamic extension
	 * points.
	 * 
	 * @param extensionPointId
	 *            ID of the considered extension point.
	 * @return <code>true</code> if the given ID corresponds to one of Acceleo's dynamic extension points,
	 *         <code>false</code> otherwise.
	 */
	static boolean isDynamicExtensionPoint(String extensionPointId) {
		boolean isDynamic = AcceleoCommonPlugin.LIBRARIES_EXTENSION_POINT.equals(extensionPointId);
		isDynamic = isDynamic || AcceleoCommonPlugin.SERVICES_EXTENSION_POINT.equals(extensionPointId);
		return isDynamic;
	}

	/**
	 * Uninstalls the given bundle from the context.
	 * 
	 * @param target
	 *            The bundle that is to be uninstalled.
	 * @throws BundleException
	 *             Thrown if a lifecycle issue arises.
	 */
	static void uninstallBundle(Bundle target) throws BundleException {
		target.uninstall();
		refreshPackages(null);
	}

	/**
	 * There could already be contributions to our dynamic extension point at plugin start time. Check for
	 * them.
	 */
	private static void checkForInitialContributions() {
		for (IPluginModelBase model : PluginRegistry.getWorkspaceModels()) {
			final IExtensions extensions = model.getExtensions();
			final IPluginExtension[] pluginExtensions = extensions.getExtensions();
			for (IPluginExtension ext : pluginExtensions) {
				if (isDynamicExtensionPoint(ext.getPoint())) {
					CHANGED_CONTRIBUTIONS.add(model);
					// no need to carry on on this inner loop
					break;
				}
			}
		}
	}

	/**
	 * Returns the bundle corresponding to the given location if any.
	 * 
	 * @param pluginLocation
	 *            The location of the bundle we seek.
	 * @return The bundle corresponding to the given location if any, <code>null</code> otherwise.
	 */
	private static Bundle getBundle(String pluginLocation) {
		Bundle[] bundles = AcceleoCommonPlugin.getDefault().getContext().getBundles();
		for (int i = 0; i < bundles.length; i++) {
			if (pluginLocation.equals(bundles[i].getLocation())) {
				return bundles[i];
			}
		}
		return null;
	}

	/**
	 * Installs the bundle corresponding to the given location. This will fail if the location doesn't point
	 * to a valid bundle.
	 * 
	 * @param pluginLocation
	 *            Location of the bundle to be installed.
	 * @return The installed bundle.
	 * @throws BundleException
	 *             Thrown if the Bundle isn't valid.
	 * @throws IllegalStateException
	 *             Thrown if the bundle couldn't be installed properly.
	 */
	private static Bundle installBundle(String pluginLocation) throws BundleException, IllegalStateException {
		Bundle target = AcceleoCommonPlugin.getDefault().getContext().installBundle(pluginLocation);
		int state = target.getState();
		if (state != Bundle.INSTALLED) {
			throw new IllegalStateException(AcceleoCommonMessages.getString(
					"WorkspaceUtil.IllegalBundleState", target, state)); //$NON-NLS-1$
		}
		refreshPackages(new Bundle[] {target});
		return target;
	}

	/**
	 * Refreshes all exported packages of the given bundles. This must be called after installing the bundle.
	 * 
	 * @param bundles
	 *            Bundles which exported packages are to be refreshed.
	 */
	private static synchronized void refreshPackages(Bundle[] bundles) {
		BundleContext context = AcceleoCommonPlugin.getDefault().getContext();
		ServiceReference packageAdminReference = context.getServiceReference(PackageAdmin.class.getName());
		PackageAdmin packageAdmin = null;
		if (packageAdminReference != null) {
			packageAdmin = (PackageAdmin)context.getService(packageAdminReference);
		}

		if (packageAdmin != null) {
			final boolean[] flag = new boolean[] {false,};
			FrameworkListener listener = new FrameworkListener() {
				public void frameworkEvent(FrameworkEvent event) {
					if (event.getType() == FrameworkEvent.PACKAGES_REFRESHED) {
						synchronized(flag) {
							flag[0] = true;
							flag.notifyAll();
						}
					}
				}
			};

			context.addFrameworkListener(listener);
			packageAdmin.refreshPackages(bundles);
			synchronized(flag) {
				while (!flag[0]) {
					try {
						flag.wait();
					} catch (InterruptedException e) {
						// discard
					}
				}
			}
			context.removeFrameworkListener(listener);
			context.ungetService(packageAdminReference);
		}
	}

	/**
	 * This will allow us to listen to changes within the workspace-defined bundles and react to changes to
	 * our extension points.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	static class WorkspaceModelListener implements IModelChangedListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.pde.core.IModelChangedListener#modelChanged(org.eclipse.pde.core.IModelChangedEvent)
		 */
		public void modelChanged(IModelChangedEvent event) {
			for (Object changed : event.getChangedObjects()) {
				if (changed instanceof IExtensions) {
					final IPluginExtension[] pluginExtensions = ((IExtensions)changed).getExtensions();
					for (IPluginExtension ext : pluginExtensions) {
						if (isDynamicExtensionPoint(ext.getPoint())) {
							CHANGED_CONTRIBUTIONS.add(((IExtensions)changed).getPluginModel());
							// no need to carry on on this inner loop
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Allows us to react to changes in the workspace and install/uninstall listeners as needed.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	static class WorkspaceResourcesListener implements IResourceChangeListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
		 */
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getResource() instanceof IProject) {
				switch (event.getType()) {
					case IResourceChangeEvent.PRE_CLOSE:
					case IResourceChangeEvent.PRE_DELETE:
						final IProject project = (IProject)event.getResource();
						final IPluginModelBase model = PluginRegistry.findModel(project);
						if (model != null) {
							final IModelChangedListener listener = INSTALLED_MODEL_LISTENERS.remove(model);
							model.removeModelChangedListener(listener);

							final Bundle bundle = WORKSPACE_INSTALLED_BUNDLES.remove(model);
							if (bundle != null) {
								try {
									uninstallBundle(bundle);
								} catch (BundleException e) {
									AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(
											"WorkspaceUtil.UninstallationFailure", bundle.getSymbolicName(), //$NON-NLS-1$
											e.getMessage()), true);
									AcceleoCommonPlugin.log(e, true);
								}
							}
						}
						break;
					default:
						// no default action
				}
			}
		}
	}
}
