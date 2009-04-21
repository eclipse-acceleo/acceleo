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
package org.eclipse.acceleo.common.internal.utils.workspace;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.osgi.baseadaptor.BaseData;
import org.eclipse.osgi.framework.internal.core.AbstractBundle;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.osgi.service.resolver.ExportPackageDescription;
import org.eclipse.osgi.service.resolver.ImportPackageSpecification;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

/* TODO add model listeners to the workspace installed plugins to check for updates on required bundles
 * or imported packages.
 */
/**
 * Eclipse-specific utilities to handle workspace contributions of Java services, ATL libraries, ...
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoWorkspaceUtil {
	/** Singleton instance of this utility. */
	public static final AcceleoWorkspaceUtil INSTANCE = new AcceleoWorkspaceUtil();

	/** Key of the error message that is to be used when we cannot uninstall workspace bundles. */
	private static final String UNINSTALLATION_FAILURE_KEY = "WorkspaceUtil.UninstallationFailure"; //$NON-NLS-1$

	/**
	 * This will keep references on the workspace's IPluginModelBase which have been changed since the last
	 * refresh. This is the trigger of refreshing workspace contributions : if empty, no plugins will ever be
	 * installed.
	 */
	final Set<IPluginModelBase> changedContributions = new LinkedHashSet<IPluginModelBase>();

	/** Keeps track of all manually loaded workspace bundles. */
	final Map<IPluginModelBase, Bundle> workspaceInstalledBundles = new HashMap<IPluginModelBase, Bundle>();

	/**
	 * This will keep track of the classes that have been loaded from workspace bundles. Keys will be the
	 * class' qualified name while values will be the containing bundles' symbolic names.
	 */
	final Map<String, WorkspaceClassInstance> workspaceLoadedClasses = new HashMap<String, WorkspaceClassInstance>();

	/** This will allow us to react to project additions/removals in the running workspace. */
	private final IResourceChangeListener workspaceListener = new WorkspaceResourcesListener();

	/**
	 * This class is a singleton. Access instance through {@link #INSTANCE}.
	 */
	private AcceleoWorkspaceUtil() {
		// hides constructor
	}

	/**
	 * Adds a given to the set of projects that are to be dynamically installed.
	 * 
	 * @param project
	 *            The project that is to be dynamically installed when Acceleo searches for workspace
	 *            contributions.
	 */
	public synchronized void addWorkspaceContribution(IProject project) {
		final IPluginModelBase model = PluginRegistry.findModel(project);
		if (!workspaceInstalledBundles.containsKey(model)) {
			changedContributions.add(model);
		}
	}

	/**
	 * This can be used to uninstall all manually loaded bundles from the registry and remove all listeners.
	 * It will be called on plugin stopping and is not intended to be called by clients.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public synchronized void dispose() {
		changedContributions.clear();
		workspaceLoadedClasses.clear();
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(workspaceListener);

		for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
			final Bundle bundle = entry.getValue();

			try {
				uninstallBundle(bundle);
			} catch (BundleException e) {
				AcceleoCommonPlugin
						.log(new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID, AcceleoCommonMessages
								.getString(UNINSTALLATION_FAILURE_KEY, bundle.getSymbolicName()), e));
			}
		}
		workspaceInstalledBundles.clear();
	}

	/**
	 * This will refresh the workspace contributions if needed, then search through the workspace loaded
	 * bundles for a class corresponding to <code>qualifiedName</code>.
	 * 
	 * @param qualifiedName
	 *            The qualified name of the class we seek to load.
	 * @return The class <code>qualifiedName</code> if it could be found in the workspace bundles,
	 *         <code>null</code> otherwise.
	 */
	public synchronized Class<?> getClass(String qualifiedName) {
		if (changedContributions.size() > 0) {
			refreshContributions();
		}
		Class<?> clazz = null;
		final WorkspaceClassInstance workspaceInstance = workspaceLoadedClasses.get(qualifiedName);
		if (workspaceInstance != null) {
			if (workspaceInstance.isStale()) {
				for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
					final IPluginModelBase model = entry.getKey();
					if (workspaceInstance.getBundle().equals(model.getBundleDescription().getSymbolicName())) {
						final Object instance = internalLoadClass(entry.getValue(), qualifiedName);
						workspaceInstance.setStale(false);
						workspaceInstance.setInstance(instance);
						clazz = instance.getClass();
						break;
					}
				}
			} else {
				clazz = workspaceInstance.getInstance().getClass();
			}
		}
		if (clazz != null) {
			return clazz;
		}

		for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
			final IPluginModelBase model = entry.getKey();

			String packageName = ""; //$NON-NLS-1$
			final int end = qualifiedName.lastIndexOf('.');
			if (end != -1) {
				packageName = qualifiedName.substring(0, end);
			}

			boolean packageFound = false;
			for (ExportPackageDescription exported : model.getBundleDescription().getExportPackages()) {
				if (packageName.startsWith(exported.getName())) {
					packageFound = true;
					break;
				}
			}
			if (!packageFound) {
				continue;
			}

			final Bundle bundle = entry.getValue();
			try {
				clazz = bundle.loadClass(qualifiedName);
			} catch (ClassNotFoundException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassLookupFailure", //$NON-NLS-1$
						qualifiedName, bundle.getSymbolicName()), e, false);
			}
			if (clazz != null) {
				break;
			}
		}

		return clazz;
	}

	/**
	 * This will refresh the workspace contributions if needed, then search through the workspace loaded
	 * bundles for a class corresponding to <code>qualifiedName</code>.
	 * 
	 * @param qualifiedName
	 *            The qualified name of the class we seek to load.
	 * @return An instance of the class <code>qualifiedName</code> if it could be found in the workspace
	 *         bundles, <code>null</code> otherwise.
	 */
	public synchronized Object getClassInstance(String qualifiedName) {
		if (changedContributions.size() > 0) {
			refreshContributions();
		}
		Object instance = null;
		final WorkspaceClassInstance workspaceInstance = workspaceLoadedClasses.get(qualifiedName);
		if (workspaceInstance != null) {
			if (workspaceInstance.isStale()) {
				for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
					final IPluginModelBase model = entry.getKey();
					if (workspaceInstance.getBundle().equals(model.getBundleDescription().getSymbolicName())) {
						instance = internalLoadClass(entry.getValue(), qualifiedName);
						workspaceInstance.setStale(false);
						workspaceInstance.setInstance(instance);
						break;
					}
				}
			} else {
				instance = workspaceInstance.getInstance();
			}
		}
		if (instance != null) {
			return instance;
		}

		for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
			final IPluginModelBase model = entry.getKey();

			String packageName = ""; //$NON-NLS-1$
			final int end = qualifiedName.lastIndexOf('.');
			if (end != -1) {
				packageName = qualifiedName.substring(0, end);
			}

			boolean packageFound = false;
			for (ExportPackageDescription exported : model.getBundleDescription().getExportPackages()) {
				if (packageName.startsWith(exported.getName())) {
					packageFound = true;
					break;
				}
			}
			if (!packageFound) {
				continue;
			}

			final Bundle bundle = entry.getValue();
			instance = internalLoadClass(bundle, qualifiedName);
			if (instance != null) {
				break;
			}
		}

		return instance;
	}

	/**
	 * This will search through the workspace for a plugin defined with the given symbolic name and return it
	 * if any.
	 * 
	 * @param bundleName
	 *            Symbolic name of the plugin we're searching a workspace project for.
	 * @return The workspace project of the given symbolic name, <code>null</code> if none could be found.
	 */
	public IProject getProject(String bundleName) {
		for (IPluginModelBase model : PluginRegistry.getWorkspaceModels()) {
			if (model.getBundleDescription().getSymbolicName().equals(bundleName)) {
				return model.getUnderlyingResource().getProject();
			}
		}
		return null;
	}

	/**
	 * This will return the set of all classes that have been loaded from the workspace and set in cache.
	 * <b>Note</b> that this will refresh the workspace contributions and attempt to refresh all stale class
	 * instances if any. Also take note that as a result of this refreshing, the order in which the instances
	 * are returned is not guaranteed to be the same for each call.
	 * 
	 * @return The set of all classes that have been loaded from the workspace and set in cache.
	 */
	public synchronized Set<Object> getWorkspaceInstances() {
		if (changedContributions.size() > 0) {
			refreshContributions();
		}
		final Set<Object> workspaceInstances = new LinkedHashSet<Object>();

		for (String qualifiedName : workspaceLoadedClasses.keySet()) {
			workspaceInstances.add(getClassInstance(qualifiedName));
		}

		return workspaceInstances;
	}

	/**
	 * Adds model listeners to all workspace-defined bundles. This will be called at plugin starting and is
	 * not intended to be called by clients.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public void initialize() {
		final int interestingEvents = IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE
				| IResourceChangeEvent.POST_CHANGE | IResourceChangeEvent.POST_BUILD;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(workspaceListener, interestingEvents);
	}

	/**
	 * This will refresh the list of contributions to the registry by either installing the given plugins in
	 * the current running Eclipse or refresh their packages. Keep this synchronized as it will be called by
	 * each of the utilities and these calls can come from multiple threads.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public synchronized void refreshContributions() {
		if (changedContributions.size() == 0) {
			return;
		}

		for (IPluginModelBase candidate : changedContributions) {
			installBundle(candidate);
		}
		changedContributions.clear();
	}

	/**
	 * This will seek through the workspace loaded instances for a class corresponding to the given qualified
	 * name and return its singleton instance if it has already been loaded. Qualified names that do not
	 * correspond to loaded workspace classes will only be loaded if <code>loadNew</code> is <code>true</code>
	 * .
	 * <p>
	 * Take note that any stale instance will be instantiated anew as a result of this call. Workspace
	 * contributions will also be refreshed prior to any attempt at seeking cached instances.
	 * </p>
	 * 
	 * @param qualifiedName
	 *            Qualified name of the instance we seek to retrieve.
	 * @param loadNew
	 *            If <code>true</code>, qualified names corresponding to classes that haven't been loaded yet
	 *            will be resolved in the workspace and an instance will be returned. Otherwise, they will
	 *            simply be ignored.
	 * @return The refreshed instance, <code>null</code> if it couldn't be found or loaded.
	 */
	public synchronized Object refreshInstance(String qualifiedName, boolean loadNew) {
		if (changedContributions.size() > 0) {
			refreshContributions();
		}

		if (loadNew || workspaceLoadedClasses.containsKey(qualifiedName)) {
			return getClassInstance(qualifiedName);
		}

		return null;
	}

	/**
	 * This will seek through the workspace loaded instances for classes corresponding to the given qualified
	 * names and return their singleton instances if they have already been loaded. Qualified names that do
	 * not correspond to loaded workspace classes will only be loaded if <code>loadNew</code> is
	 * <code>true</code>.
	 * <p>
	 * Take note that any stale instance will be instantiated anew as a result of this call. Workspace
	 * contributions will also be refreshed prior to any attempt at seeking cached instances.
	 * </p>
	 * <p>
	 * The order of the returned set of instances will be consistent with the order in which
	 * <code>qualifiedNames</code> are supplied.
	 * </p>
	 * 
	 * @param qualifiedNames
	 *            Qualified names of the instances we seek to retrieve.
	 * @param loadNew
	 *            If <code>true</code>, qualified names corresponding to classes that haven't been loaded yet
	 *            will be resolved in the workspace and an instance will be returned. Otherwise, they will
	 *            simply be ignored.
	 * @return The set of refreshed instances. Order will be consistent with <code>qualifiedNames</code>.
	 */
	public synchronized Set<Object> refreshInstances(Set<String> qualifiedNames, boolean loadNew) {
		if (changedContributions.size() > 0) {
			refreshContributions();
		}
		final Set<Object> workspaceInstances = new LinkedHashSet<Object>();

		for (String qualifiedName : qualifiedNames) {
			if (loadNew || workspaceLoadedClasses.containsKey(qualifiedName)) {
				workspaceInstances.add(getClassInstance(qualifiedName));
			}
		}

		return workspaceInstances;
	}

	/**
	 * This will be used internally to reset the workspace utility to its initialized state. Not intended to
	 * be called by clients.
	 */
	public synchronized void reset() {
		changedContributions.clear();
		workspaceLoadedClasses.clear();

		for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
			final Bundle bundle = entry.getValue();

			try {
				uninstallBundle(bundle);
			} catch (BundleException e) {
				AcceleoCommonPlugin
						.log(new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID, AcceleoCommonMessages
								.getString(UNINSTALLATION_FAILURE_KEY, bundle.getSymbolicName()), e));
			}
		}
		workspaceInstalledBundles.clear();
	}

	/**
	 * Refreshes all exported packages of the given bundles. This must be called after installing the bundle.
	 * 
	 * @param bundles
	 *            Bundles which exported packages are to be refreshed.
	 */
	void refreshPackages(Bundle[] bundles) {
		BundleContext context = AcceleoCommonPlugin.getDefault().getContext();
		ServiceReference packageAdminReference = context.getServiceReference(PackageAdmin.class.getName());
		PackageAdmin packageAdmin = null;
		if (packageAdminReference != null) {
			packageAdmin = (PackageAdmin)context.getService(packageAdminReference);
		}

		if (packageAdmin != null) {
			final boolean[] flag = new boolean[] {false, };
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
						break;
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
	void uninstallBundle(Bundle target) throws BundleException {
		target.uninstall();
		refreshPackages(null);
	}

	/**
	 * This will check through the dependencies of <code>model</code> and install the necessary workspace
	 * plugins if they are either required or imported.
	 * 
	 * @param model
	 *            The model we wish the dependencies checked of.
	 */
	private void checkDependencies(IPluginModelBase model) {
		final BundleDescription desc = model.getBundleDescription();
		for (BundleSpecification requiredBundle : desc.getRequiredBundles()) {
			for (IPluginModelBase workspaceModel : PluginRegistry.getWorkspaceModels()) {
				if (requiredBundle.isSatisfiedBy(workspaceModel.getBundleDescription())) {
					installBundle(workspaceModel);
					break;
				}
			}
		}
		for (ImportPackageSpecification importPackage : desc.getImportPackages()) {
			for (IPluginModelBase workspaceModel : PluginRegistry.getWorkspaceModels()) {
				for (ExportPackageDescription export : workspaceModel.getBundleDescription()
						.getExportPackages()) {
					if (importPackage.isSatisfiedBy(export)) {
						installBundle(workspaceModel);
						break;
					}
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
	private Bundle getBundle(String pluginLocation) {
		Bundle[] bundles = AcceleoCommonPlugin.getDefault().getContext().getBundles();
		for (int i = 0; i < bundles.length; i++) {
			if (pluginLocation.equals(bundles[i].getLocation())) {
				return bundles[i];
			}
		}
		return null;
	}

	/**
	 * This will return the set of output folders name for the given (java) project.
	 * <p>
	 * For example, if a project has a source folder "src" with its output folder set as "bin" and a source
	 * folder "src-gen" with its output folder set as "bin-gen", this will return a LinkedHashSet containing
	 * both "bin" and "bin-gen".
	 * </p>
	 * 
	 * @param project
	 *            The project we seek the output folders of.
	 * @return The set of output folders name for the given (java) project.
	 */
	private Set<String> getOutputFolders(IProject project) {
		final Set<String> classpathEntries = new LinkedHashSet<String>();
		final IJavaProject javaProject = JavaCore.create(project);
		try {
			for (IClasspathEntry entry : javaProject.getResolvedClasspath(true)) {
				if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					final IPath output = entry.getOutputLocation();
					if (output != null) {
						classpathEntries.add(output.removeFirstSegments(1).toString());
					}
				}
			}
			/*
			 * Add the default output location to the classpath anyway since source folders are not required
			 * to have their own
			 */
			final IPath output = javaProject.getOutputLocation();
			classpathEntries.add(output.removeFirstSegments(1).toString());
		} catch (JavaModelException e) {
			AcceleoCommonPlugin.log(e, false);
		}
		return classpathEntries;
	}

	/**
	 * Installs the bundle corresponding to the model.
	 * 
	 * @param model
	 *            Model of the bundle to be installed.
	 */
	private void installBundle(IPluginModelBase model) {
		try {
			final IResource candidateManifest = model.getUnderlyingResource();
			final String candidateLocationReference = "reference:" //$NON-NLS-1$
					+ candidateManifest.getProject().getLocationURI().toURL().toExternalForm();

			Bundle bundle = getBundle(candidateLocationReference);

			// Install the bundle if needed
			if (bundle == null) {
				checkDependencies(model);
				bundle = installBundle(candidateLocationReference);
				final IProject project = model.getUnderlyingResource().getProject();
				setBundleClasspath(project, bundle);
				workspaceInstalledBundles.put(model, bundle);
			}
			refreshPackages(new Bundle[] {bundle, });
		} catch (BundleException e) {
			AcceleoCommonPlugin.log(new Status(IStatus.WARNING, AcceleoCommonPlugin.PLUGIN_ID,
					AcceleoCommonMessages.getString("WorkspaceUtil.InstallationFailure", model //$NON-NLS-1$
							.getBundleDescription().getName()), e));
		} catch (MalformedURLException e) {
			AcceleoCommonPlugin.log(e, false);
		}
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
	private Bundle installBundle(String pluginLocation) throws BundleException, IllegalStateException {
		Bundle target = AcceleoCommonPlugin.getDefault().getContext().installBundle(pluginLocation);
		int state = target.getState();
		if (state != Bundle.INSTALLED) {
			throw new IllegalStateException(AcceleoCommonMessages.getString(
					"WorkspaceUtil.IllegalBundleState", target, state)); //$NON-NLS-1$
		}
		return target;
	}

	/**
	 * Loads the class <code>qualifiedName</code> from the specified <code>bundle</code> if possible.
	 * 
	 * @param bundle
	 *            The bundle from which to load the sought class.
	 * @param qualifiedName
	 *            Qualified name of the class that is to be loaded.
	 * @return An instance of the class if it could be loaded, <code>null</code> otherwise.
	 */
	private Object internalLoadClass(Bundle bundle, String qualifiedName) {
		try {
			final Class<?> clazz = bundle.loadClass(qualifiedName);
			final Object instance = clazz.newInstance();
			workspaceLoadedClasses.put(qualifiedName, new WorkspaceClassInstance(instance, bundle
					.getSymbolicName()));
			return instance;
		} catch (ClassNotFoundException e) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassLookupFailure", //$NON-NLS-1$
					qualifiedName, bundle.getSymbolicName()), e, false);
		} catch (InstantiationException e) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassInstantiationFailure", //$NON-NLS-1$
					qualifiedName, bundle.getSymbolicName()), e, false);
		} catch (IllegalAccessException e) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassConstructorFailure", //$NON-NLS-1$
					qualifiedName, bundle.getSymbolicName()), e, false);
		}
		return null;
	}

	/**
	 * This will set the equinox classpath of <code>bundle</code> to reflect the eclipse classpath of
	 * <code>plugin</code>.
	 * 
	 * @param plugin
	 *            The eclipse plugin which classpath is to be set for its corresponding equinox bundle.
	 * @param bundle
	 *            The equinox bundle which classpath is to reflect an eclipse development plugin.
	 */
	private void setBundleClasspath(IProject plugin, Bundle bundle) {
		final Set<String> classpathEntries = getOutputFolders(plugin);
		final BaseData bundleData = (BaseData)((AbstractBundle)bundle).getBundleData();
		if (classpathEntries.size() == 1) {
			bundleData.setClassPathString(classpathEntries.iterator().next());
		} else {
			final StringBuilder classpath = new StringBuilder();
			final Iterator<String> entryIterator = classpathEntries.iterator();
			while (entryIterator.hasNext()) {
				classpath.append(entryIterator.next());
				if (entryIterator.hasNext()) {
					classpath.append(',');
				}
			}
			bundleData.setClassPathString(classpath.toString());
		}
	}

	/**
	 * Allows us to react to changes in the workspace and install/uninstall listeners as needed.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	class WorkspaceResourcesListener implements IResourceChangeListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
		 */
		public void resourceChanged(IResourceChangeEvent event) {
			switch (event.getType()) {
				/*
				 * Closing and deleting projects trigger the same actions : we must remove the model listener
				 * and uninstall the bundle.
				 */
				case IResourceChangeEvent.PRE_CLOSE:
				case IResourceChangeEvent.PRE_DELETE:
					if (event.getResource() instanceof IProject) {
						final IProject project = (IProject)event.getResource();
						final IPluginModelBase model = PluginRegistry.findModel(project);
						if (model != null) {
							final Bundle bundle = workspaceInstalledBundles.remove(model);
							if (bundle != null) {
								try {
									uninstallBundle(bundle);
								} catch (BundleException e) {
									AcceleoCommonPlugin
											.log(new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID,
													AcceleoCommonMessages.getString(
															UNINSTALLATION_FAILURE_KEY, bundle
																	.getSymbolicName()), e));
								}
							}
						}
					}
					break;
				case IResourceChangeEvent.POST_BUILD:
					processBuildEvent(event);
					break;
				case IResourceChangeEvent.POST_CHANGE:
				default:
					// no default action
			}
		}

		/**
		 * This will process IResourceChangeEvent.POST_BUILD events so that we can react to builds of our
		 * workspace loaded services.
		 * 
		 * @param event
		 *            The event that is to be processed. Assumes that
		 *            <code>event.getType() == IResourceChangeEvent.POST_BUILD</code>.
		 */
		private void processBuildEvent(IResourceChangeEvent event) {
			final IResourceDelta delta = event.getDelta();
			switch (event.getBuildKind()) {
				case IncrementalProjectBuilder.AUTO_BUILD:
					// Nothing built in such cases
					if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
						break;
					}
					// Fall through to the incremental build handling otherwise
				case IncrementalProjectBuilder.INCREMENTAL_BUILD:
					final AcceleoDeltaVisitor visitor = new AcceleoDeltaVisitor();
					try {
						delta.accept(visitor);
					} catch (CoreException e) {
						AcceleoCommonPlugin.log(e, false);
					}
					for (IProject changed : visitor.getChangedProjects()) {
						changedContributions.add(PluginRegistry.findModel(changed));
					}
					for (String changedClass : visitor.getChangedClasses()) {
						final WorkspaceClassInstance workspaceInstance = workspaceLoadedClasses
								.get(changedClass);
						if (workspaceInstance != null) {
							workspaceInstance.setStale(true);
						}
					}
					break;
				case IncrementalProjectBuilder.FULL_BUILD:
					for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
						changedContributions.add(entry.getKey());
					}
					for (WorkspaceClassInstance workspaceInstance : workspaceLoadedClasses.values()) {
						workspaceInstance.setStale(true);
					}
					break;
				case IncrementalProjectBuilder.CLEAN_BUILD:
					// workspace has been cleaned. Unload every service until next they're built
					for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
						final Bundle bundle = entry.getValue();

						try {
							uninstallBundle(bundle);
						} catch (BundleException e) {
							AcceleoCommonPlugin.log(new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID,
									AcceleoCommonMessages.getString(UNINSTALLATION_FAILURE_KEY, bundle
											.getSymbolicName()), e));
						}
					}
					for (WorkspaceClassInstance workspaceInstance : workspaceLoadedClasses.values()) {
						workspaceInstance.setStale(true);
					}
					break;
				default:
					// no default action
			}
		}
	}
}
