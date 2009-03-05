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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
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
 * Eclipse-specific utilities for Acceleo services. It will be initialized with all services that could be
 * parsed from the extension point if Eclipse is running and won't be used when outside of Eclipse.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoServicesEclipseUtil {
	/**
	 * Keeps track of all contributions to the services extension point. <b>Note</b> that this will be
	 * frequently changed as the registry's listener updates the list of available extensions.
	 */
	private static final Set<Object> SERVICES = new LinkedHashSet<Object>();

	/** Keeps track of all manually loaded workspace bundles. */
	private static final Set<Bundle> WORKSPACE_INSTALLED_BUNDLES = new HashSet<Bundle>();

	/**
	 * Utility classes don't need a default constructor.
	 */
	private AcceleoServicesEclipseUtil() {
		// hides constructor
	}

	/**
	 * Adds a given service to the list of available ones.
	 * 
	 * @param service
	 *            The actual service instance.
	 */
	public static void addService(Object service) {
		SERVICES.add(service);
	}

	/**
	 * Clears all registered extensions out of the eclipse registry.
	 */
	public static void clearRegistry() {
		// All manually loaded workspace contributions have to be unloaded here
		for (Bundle bundle : WORKSPACE_INSTALLED_BUNDLES) {
			try {
				uninstallBundle(bundle);
			} catch (BundleException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString(
						"AcceleoServicesEclipseUtil.UninstallationFailure", bundle.getSymbolicName(), e //$NON-NLS-1$
								.getMessage()), true);
				AcceleoCommonPlugin.log(e, true);
			}
		}
		WORKSPACE_INSTALLED_BUNDLES.clear();
		SERVICES.clear();
	}

	/**
	 * Returns all registered service classes.
	 * 
	 * @return All registered service classes.
	 */
	public static Set<Object> getRegisteredServices() {
		loadWorkspaceContributions();
		return new LinkedHashSet<Object>(SERVICES);
	}

	/**
	 * Removes a given services from the list of available ones.
	 * 
	 * @param service
	 *            The qualified class name of the service that is to be removed.
	 */
	public static void removeService(String service) {
		for (Object candidate : new ArrayList<Object>(SERVICES)) {
			if (service.equals(candidate.getClass().getName())) {
				SERVICES.remove(candidate);
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
					"AcceleoServicesEclipseUtil.IllegalBundleState", target, state)); //$NON-NLS-1$
		}
		refreshPackages(new Bundle[] {target});
		return target;
	}

	/**
	 * This will search through workspace-defined plugins to check if any of them contributes a Java Service,
	 * then load them through OSGi.
	 */
	private static void loadWorkspaceContributions() {
		final IPluginModelBase[] workspaceModels = PluginRegistry.getWorkspaceModels();
		final List<IPluginModelBase> javaServiceProvider = new ArrayList<IPluginModelBase>(
				workspaceModels.length);
		for (IPluginModelBase workspaceModel : workspaceModels) {
			final IExtensions extensions = workspaceModel.getExtensions();
			final IPluginExtension[] pluginExtensions = extensions.getExtensions();
			for (IPluginExtension ext : pluginExtensions) {
				if (AcceleoCommonPlugin.SERVICES_EXTENSION_POINT.equals(ext.getPoint())) {
					javaServiceProvider.add(workspaceModel);
				}
			}
		}

		BundleContext context = AcceleoCommonPlugin.getDefault().getContext();
		ServiceReference jobManagerReference = context.getServiceReference(IJobManager.class.getName());
		IJobManager jobManager = null;
		if (jobManagerReference != null) {
			jobManager = (IJobManager)context.getService(jobManagerReference);
		}
		try {
			final boolean[] flag = new boolean[] {false,};
			for (IPluginModelBase candidate : javaServiceProvider) {
				try {
					final IResource candidateManifest = candidate.getUnderlyingResource();
					final String candidateLocationReference = "reference:" //$NON-NLS-1$
							+ candidateManifest.getProject().getLocationURI().toURL().toExternalForm();

					// Prepare the job listener so that we can wait for the end of all registry updates
					Bundle bundle = getBundle(candidateLocationReference);
					final int registryEventExpected;
					if (bundle == null) {
						registryEventExpected = 1;
					} else {
						// one event for the removal of old contributions, 1 for the addition
						registryEventExpected = 2;
					}
					final IJobChangeListener listener = new JobChangeAdapter() {
						private int expectedEvents = registryEventExpected;

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
						WORKSPACE_INSTALLED_BUNDLES.add(bundle);
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
					AcceleoCommonPlugin
							.log(
									AcceleoCommonMessages
											.getString(
													"AcceleoServicesEclipseUtil.installationFailure", candidate.getBundleDescription() //$NON-NLS-1$
															.getName(), e.getMessage()), false);
					AcceleoCommonPlugin.log(e, false);
				} catch (MalformedURLException e) {
					AcceleoCommonPlugin.log(e, false);
				}
			}
		} finally {
			context.ungetService(jobManagerReference);
		}
	}

	/**
	 * Refreshes all exported packages of the given bundles. This must be called after installing the bundle.
	 * 
	 * @param bundles
	 *            Bundles which exported packages are to be refreshed.
	 */
	private static void refreshPackages(Bundle[] bundles) {
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
	 * Uninstalls the given bundle from the context.
	 * 
	 * @param target
	 *            The bundle that is to be uninstalled.
	 * @throws BundleException
	 *             Thrown if a lifecycle issue arises.
	 */
	private static void uninstallBundle(Bundle target) throws BundleException {
		target.uninstall();
		refreshPackages(null);
	}
}
