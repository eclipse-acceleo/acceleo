/*******************************************************************************
 * Copyright (c) 2009, 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.ide.authoring;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.internal.utils.workspace.ClassLoadingCompanion;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.internal.registry.ExtensionRegistry;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.spi.IDynamicExtensionRegistry;
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
public final class AcceleoWorkspaceUtil implements ClassLoadingCompanion {

	/** Singleton instance of this utility. */
	public static final AcceleoWorkspaceUtil INSTANCE = new AcceleoWorkspaceUtil();

	/**
	 * Time-out used when we are waiting for an OSGI operation done through package admin.
	 */
	private static final int OSGI_TIMEOUT = 3000;

	/**
	 * Key of the error message that is to be used when we cannot uninstall workspace bundles.
	 */
	private static final String UNINSTALLATION_FAILURE_KEY = "WorkspaceUtil.UninstallationFailure"; //$NON-NLS-1$

	/**
	 * We'll use this to add 'empty' contributions for the bundles we dynamically install.
	 */
	private static final String EMPTY_PLUGIN_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><plugin/>"; //$NON-NLS-1$

	/** Prefix we'll use for the dynamic bundles' MANIFEST. */
	private static final String REFERENCE_URI_PREFIX = "reference:"; //$NON-NLS-1$

	/**
	 * This will keep references on the workspace's IPluginModelBase which have been changed since the last
	 * refresh. This is the trigger of refreshing workspace contributions : if empty, no plugins will ever be
	 * installed.
	 */
	final Set<IPluginModelBase> changedContributions = new CompactLinkedHashSet<IPluginModelBase>();

	/** Keeps track of all manually loaded workspace bundles. */
	final Map<IPluginModelBase, Bundle> workspaceInstalledBundles = new HashMap<IPluginModelBase, Bundle>();

	/**
	 * This will keep track of the classes that have been loaded from workspace bundles. Keys will be the
	 * class' qualified name while values will be the containing bundles' symbolic names.
	 */
	final Map<String, WorkspaceClassInstance> workspaceLoadedClasses = new HashMap<String, WorkspaceClassInstance>();

	/**
	 * This will allow us to react to project additions/removals in the running workspace.
	 */
	private final IResourceChangeListener workspaceListener = new WorkspaceResourcesListener();

	/** This will allow us to only load once the "duplicate project" warnings. */
	private final Set<String> logOnceProjectLoad = new LinkedHashSet<String>();

	/**
	 * This class is a singleton. Access instance through {@link #INSTANCE}.
	 */
	private AcceleoWorkspaceUtil() {
		// hides constructor
	}

	@Override
	public IProject getProject(final String bundleName) {
		for (IPluginModelBase model : PluginRegistry.getWorkspaceModels()) {
			if (model.getBundleDescription().getSymbolicName().equals(bundleName)) {
				return model.getUnderlyingResource().getProject();
			}
		}
		return null;
	}

	/**
	 * This will iterate over the "Export-Package" manifest header of the given bundle and search a bundle
	 * corresponding to the given qualified class name.
	 * <p>
	 * For example, if the qualified name we're given is "org.eclipse.acceleo.sample.Test", we'll search for a
	 * bundle exporting the package "org.eclipse.acceleo.sample".
	 * </p>
	 * 
	 * @param model
	 *            The bundle model that is to be checked.
	 * @param qualifiedName
	 *            Qualified name of the class we search the exported package of.
	 * @return <code>true</code> iff <code>model</code> has an entry for a package corresponding to
	 *         <code>qualifiedName</code>.
	 */
	private static boolean hasCorrespondingExportPackage(IPluginModelBase model, String qualifiedName) {
		String packageName = ""; //$NON-NLS-1$
		final int end = qualifiedName.lastIndexOf('.');
		if (end != -1) {
			packageName = qualifiedName.substring(0, end);
		}

		for (ExportPackageDescription exported : model.getBundleDescription().getExportPackages()) {
			if (packageName.startsWith(exported.getName())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Adds a given project to the set of projects that are to be dynamically installed.
	 * 
	 * @param project
	 *            The project that is to be dynamically installed when Acceleo searches for workspace
	 *            contributions.
	 */
	public synchronized void addWorkspaceContribution(IProject project) {
		final IPluginModelBase model = PluginRegistry.findModel(project);
		if (model != null && !workspaceInstalledBundles.containsKey(model)) {
			changedContributions.add(model);
		}
	}

	@Override
	public synchronized void dispose() {
		changedContributions.clear();
		workspaceLoadedClasses.clear();
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(workspaceListener);

		for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
			final Bundle bundle = entry.getValue();

			try {
				uninstallBundle(bundle);
			} catch (BundleException e) {
				AcceleoCommonPlugin.log(new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID,
						AcceleoCommonMessages.getString(UNINSTALLATION_FAILURE_KEY, bundle.getSymbolicName()),
						e));
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
	 * @param honorOSGiVisibility
	 *            If <code>true</code>, this will only search through exported packages for the class
	 *            <code>qualifiedName</code>. Otherwise we'll search through all bundles by simply trying to
	 *            load the class and catching the {@link ClassNotFoundException} if it isn't loadable.
	 * @return The class <code>qualifiedName</code> if it could be found in the workspace bundles,
	 *         <code>null</code> otherwise.
	 */
	public synchronized Class<?> getClass(String qualifiedName, boolean honorOSGiVisibility) {
		if (changedContributions.size() > 0) {
			refreshContributions();
		}

		// Has an instance of this class already been loaded?
		Class<?> clazz = null;
		final WorkspaceClassInstance workspaceInstance = workspaceLoadedClasses.get(qualifiedName);
		if (workspaceInstance != null) {
			if (workspaceInstance.isStale()) {
				for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
					final IPluginModelBase model = entry.getKey();
					if (workspaceInstance.getBundle().equals(model.getBundleDescription()
							.getSymbolicName())) {
						clazz = internalLoadClass(entry.getValue(), qualifiedName);
						workspaceInstance.setStale(false);
						workspaceInstance.setClass(clazz);
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

		// The class hasn't been instantiated yet ; search for the class without
		// instantiating it
		Iterator<Map.Entry<IPluginModelBase, Bundle>> iterator = workspaceInstalledBundles.entrySet()
				.iterator();
		while (clazz == null && iterator.hasNext()) {
			Map.Entry<IPluginModelBase, Bundle> entry = iterator.next();
			/*
			 * If we're asked to honor OSGi package visibility, we'll first check the "Export-Package" header
			 * of this bundle's MANIFEST.
			 */
			if (!honorOSGiVisibility || hasCorrespondingExportPackage(entry.getKey(), qualifiedName)) {
				try {
					clazz = entry.getValue().loadClass(qualifiedName);
				} catch (ClassNotFoundException e) {
					// Swallow this ; we'll log the issue later on if we cannot
					// find the class at all
				}
			}
		}

		if (clazz == null) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassLookupFailure", //$NON-NLS-1$
					qualifiedName), false);
		}

		return clazz;
	}

	@Override
	public synchronized Class<?> getClass(IProject project, String qualifiedName) {
		Class<?> instance = null;
		addWorkspaceContribution(project);
		refreshContributions();
		final IPluginModelBase model = PluginRegistry.findModel(project);
		final Bundle installedBundle = workspaceInstalledBundles.get(model);
		if (installedBundle != null) {
			instance = internalLoadClass(installedBundle, qualifiedName);
		}
		return instance;
	}

	@Override
	public synchronized ResourceBundle getResourceBundle(String qualifiedName) {
		if (changedContributions.size() > 0) {
			refreshContributions();
		}

		/*
		 * We'll iterate over the bundles that have been installed from the workspace, search for one that
		 * exports a package of the name we're looking for, then try and load the properties file from this
		 * bundle's class loader (bundle.getResource()). If the resource couldn't be found in that bundle, we
		 * loop over to the next.
		 */
		for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
			final Bundle bundle = entry.getValue();
			URL propertiesResource = bundle.getResource(qualifiedName.replace('.', '/') + ".properties"); //$NON-NLS-1$
			if (propertiesResource != null) {
				InputStream stream = null;
				try {
					stream = propertiesResource.openStream();
					// make sure this stream is buffered
					stream = new BufferedInputStream(stream);
					return new PropertyResourceBundle(stream);
				} catch (IOException e) {
					// Swallow this, we'll throw the original
					// MissingResourceException
				} finally {
					try {
						if (stream != null) {
							stream.close();
						}
					} catch (IOException e) {
						// Swallow this, we'll throw the original
						// MissingResourceException
					}
				}
			}
		}
		return null;
	}

	@Override
	public synchronized Object getServiceInstance(Class<?> serviceClass) {
		String qualifiedName = serviceClass.getName();
		for (Map.Entry<String, WorkspaceClassInstance> workspaceClass : workspaceLoadedClasses.entrySet()) {
			if (workspaceClass.getKey().equals(qualifiedName)) {
				WorkspaceClassInstance workspaceInstance = workspaceClass.getValue();
				if (workspaceInstance.isStale()) {
					for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
						final IPluginModelBase model = entry.getKey();
						if (workspaceInstance.getBundle().equals(model.getBundleDescription()
								.getSymbolicName())) {
							Class<?> clazz = internalLoadClass(entry.getValue(), qualifiedName);
							workspaceInstance.setStale(false);
							workspaceInstance.setClass(clazz);
							break;
						}
					}
				}
				return workspaceInstance.getInstance();
			}
		}
		return null;
	}

	@Override
	public void initialize() {
		final int interestingEvents = IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE
				| IResourceChangeEvent.POST_CHANGE | IResourceChangeEvent.POST_BUILD;
		ResourcesPlugin.getWorkspace().addResourceChangeListener(workspaceListener, interestingEvents);
	}

	@Override
	public boolean isInDynamicBundle(Class<?> clazz) {
		BundleContext context = AcceleoCommonPlugin.getDefault().getContext();
		ServiceReference packageAdminReference = context.getServiceReference(PackageAdmin.class.getName());
		PackageAdmin packageAdmin = null;
		if (packageAdminReference != null) {
			packageAdmin = (PackageAdmin)context.getService(packageAdminReference);
		}

		if (packageAdmin != null) {
			Bundle bundle = packageAdmin.getBundle(clazz);
			if (workspaceInstalledBundles.values().contains(bundle)) {
				return true;
			}
		}

		if (packageAdminReference != null) {
			context.ungetService(packageAdminReference);
		}

		return false;
	}

	@Override
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
	 * This will be used internally to reset the workspace utility to its initialized state. Not intended to
	 * be called by clients.
	 */
	public synchronized void reset() {
		changedContributions.clear();
		workspaceLoadedClasses.clear();

		for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
			final Bundle bundle = entry.getValue();

			try {
				if (bundle.getState() != Bundle.UNINSTALLED) {
					uninstallBundle(bundle);
				}
			} catch (BundleException e) {
				AcceleoCommonPlugin.log(new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID,
						AcceleoCommonMessages.getString(UNINSTALLATION_FAILURE_KEY, bundle.getSymbolicName()),
						e));
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
	@SuppressWarnings("restriction")
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

			/*
			 * Hack-ish : Make sure the contributions from this bundle won't be parsed. When installing a
			 * bundle, the EclipseBundleListener will _always_ parse the plugin.xml and notify the extension
			 * registry (and its listeners) of the new contributions. Problem is : some bundles listen to new
			 * contributions, but ignore the event of contribution removals (when we uninstall the bundle). We
			 * would thus end with "invalid registry object" exceptions thrown ... with no way to prevent or
			 * fix them whatsoever. Equinox does not provide us with an API to disable the contributions from
			 * the bundle we're installing, temporarily disable the extension listeners... or any other way to
			 * workaround this registry issue. We'll then make use of the fact that the EclipseBundleListener
			 * does not add contributions from a contributor which has already been added: we'll add the
			 * contributor beforehand with an empty plugin.xml, disabling all potential contributions this
			 * bundle would have made otherwise.
			 */

			if (bundles != null && Platform.getExtensionRegistry() instanceof IDynamicExtensionRegistry) {
				IExtensionRegistry registry = Platform.getExtensionRegistry();
				for (Bundle bundle : bundles) {
					IContributor contributor = ContributorFactoryOSGi.createContributor(bundle);
					if (!((IDynamicExtensionRegistry)registry).hasContributor(contributor)) {
						registry.addContribution(new ByteArrayInputStream(EMPTY_PLUGIN_XML.getBytes()),
								contributor, false, null, null, ((ExtensionRegistry)registry)
										.getTemporaryUserToken());
					}
				}
			}

			context.addFrameworkListener(listener);
			packageAdmin.refreshPackages(bundles);
			synchronized(flag) {
				while (!flag[0]) {
					try {
						flag.wait(OSGI_TIMEOUT);
					} catch (InterruptedException e) {
						// discard
						break;
					}
				}
			}
			context.removeFrameworkListener(listener);
			if (packageAdminReference != null) {
				context.ungetService(packageAdminReference);
			}
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
	 * This will check the indirect dependencies of <code>model</code> and install the necessary workspace
	 * plugins if we need to import some of their packages.
	 * 
	 * @param model
	 *            The model of which we wish the dependencies checked.
	 */
	private void checkImportPackagesDependencies(IPluginModelBase model) {
		final BundleDescription desc = model.getBundleDescription();
		if (desc == null) {
			return;
		}
		for (ImportPackageSpecification importPackage : desc.getImportPackages()) {
			for (IPluginModelBase workspaceModel : PluginRegistry.getWorkspaceModels()) {
				if (workspaceModel != null && workspaceModel.getBundleDescription() != null) {
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
	}

	/**
	 * This will check through the dependencies of <code>model</code> and install the necessary workspace
	 * plugins if they are required.
	 * 
	 * @param model
	 *            The model of which we wish the dependencies checked.
	 */
	private void checkRequireBundleDependencies(IPluginModelBase model) {
		final BundleDescription desc = model.getBundleDescription();
		if (desc == null) {
			return;
		}
		for (BundleSpecification requiredBundle : desc.getRequiredBundles()) {
			for (IPluginModelBase workspaceModel : PluginRegistry.getWorkspaceModels()) {
				if (requiredBundle.isSatisfiedBy(workspaceModel.getBundleDescription())) {
					installBundle(workspaceModel);
					break;
				}
			}
		}
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
			final IProject project = candidateManifest.getProject();

			URL url = null;
			try {
				url = project.getLocationURI().toURL();
			} catch (MalformedURLException e) {
				// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=354360
				try {
					URI uri = project.getLocationURI();
					IFileStore store = EFS.getStore(uri);
					File file = store.toLocalFile(0, null);
					if (file != null) {
						url = file.toURI().toURL();
					}
				} catch (CoreException ex) {
					// Logging both exceptions just to be sure
					AcceleoCommonPlugin.log(e, false);
					AcceleoCommonPlugin.log(ex, false);
				}
			}

			if (url != null) {
				final String candidateLocationReference = REFERENCE_URI_PREFIX + URLDecoder.decode(url
						.toExternalForm(), System.getProperty("file.encoding")); //$NON-NLS-1$

				Bundle bundle = org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil
						.getBundle(candidateLocationReference);

				/*
				 * Install the bundle if needed. Note that we'll check bundle dependencies in two phases as
				 * even if there cannot be cyclic dependencies through the "require-bundle" header, there
				 * could be through the "import package" header.
				 */
				if (bundle == null) {
					checkRequireBundleDependencies(model);
					bundle = installBundle(candidateLocationReference);
					setBundleClasspath(project, bundle);
					workspaceInstalledBundles.put(model, bundle);
					checkImportPackagesDependencies(model);
				}
				refreshPackages(new Bundle[] {bundle, });
			}
		} catch (BundleException e) {
			String bundleName = model.getBundleDescription().getName();
			if (!logOnceProjectLoad.contains(bundleName)) {
				logOnceProjectLoad.add(bundleName);
				AcceleoCommonPlugin.log(new Status(IStatus.WARNING, AcceleoCommonPlugin.PLUGIN_ID,
						AcceleoCommonMessages.getString("WorkspaceUtil.InstallationFailure", //$NON-NLS-1$
								bundleName, e.getMessage()), e));
			}
		} catch (MalformedURLException e) {
			AcceleoCommonPlugin.log(e, false);
		} catch (UnsupportedEncodingException e) {
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
					"WorkspaceUtil.IllegalBundleState", target, Integer.valueOf(state))); //$NON-NLS-1$
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
	private Class<?> internalLoadClass(Bundle bundle, String qualifiedName) {
		try {
			WorkspaceClassInstance workspaceInstance = workspaceLoadedClasses.get(qualifiedName);
			final Class<?> clazz;
			if (workspaceInstance == null) {
				clazz = bundle.loadClass(qualifiedName);
				workspaceLoadedClasses.put(qualifiedName, new WorkspaceClassInstance(clazz, bundle
						.getSymbolicName()));
			} else if (workspaceInstance.isStale()) {
				clazz = bundle.loadClass(qualifiedName);
				workspaceInstance.setStale(false);
				workspaceInstance.setClass(clazz);
			} else {
				clazz = workspaceInstance.getClassInstance();
			}

			return clazz;
		} catch (ClassNotFoundException e) {
			e.fillInStackTrace();
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassLookupFailure", //$NON-NLS-1$
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
	@SuppressWarnings("restriction")
	/*
	 * This methods sports a number of restricted class/method usage, yet there is no workaround and the
	 * equinox team has no plan to open it. We're suppressing the warning until plans change. Details in bug
	 * 271761.
	 */
	/*
	 * FIXME This now fails with Luna... and there is no available workaround. Commenting everything out,
	 * though this means we no longer support workspace launch of generators.
	 */
	private void setBundleClasspath(IProject plugin, Bundle bundle) {
		// final Set<String> classpathEntries = getOutputFolders(plugin);
		// if (classpathEntries.size() > 0) {
		// final org.eclipse.osgi.baseadaptor.BaseData bundleData =
		// (org.eclipse.osgi.baseadaptor.BaseData)((org.eclipse.osgi.framework.internal.core.AbstractBundle)bundle)
		// .getBundleData();
		// final StringBuilder classpath = new StringBuilder();
		// classpath.append(bundleData.getClassPathString()).append(',');
		// final Iterator<String> entryIterator = classpathEntries.iterator();
		// while (entryIterator.hasNext()) {
		// classpath.append(entryIterator.next());
		// if (entryIterator.hasNext()) {
		// classpath.append(',');
		// }
		// }
		// bundleData.setClassPathString(classpath.toString());
		// }
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
							changedContributions.remove(model);
							if (bundle != null) {
								try {
									uninstallBundle(bundle);
								} catch (BundleException e) {
									AcceleoCommonPlugin.log(new Status(IStatus.ERROR,
											AcceleoCommonPlugin.PLUGIN_ID, AcceleoCommonMessages.getString(
													UNINSTALLATION_FAILURE_KEY, bundle.getSymbolicName()),
											e));
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
		 *            <code>event.getType() == IResourceChangeEvent.POST_BUILD</code> .
		 */
		private void processBuildEvent(IResourceChangeEvent event) {
			final IResourceDelta delta = event.getDelta();
			switch (event.getBuildKind()) {
				case IncrementalProjectBuilder.AUTO_BUILD:
					// Nothing built in such cases
					if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
						break;
					}
					// fall through to the incremental build handling otherwise
				case IncrementalProjectBuilder.INCREMENTAL_BUILD:
					final AcceleoDeltaVisitor visitor = new AcceleoDeltaVisitor();
					try {
						delta.accept(visitor);
					} catch (CoreException e) {
						AcceleoCommonPlugin.log(e, false);
					}
					for (IProject changed : visitor.getChangedProjects()) {
						IPluginModelBase model = PluginRegistry.findModel(changed);
						if (model != null && workspaceInstalledBundles.keySet().contains(model)) {
							changedContributions.add(model);
						}
					}
					for (String changedClass : visitor.getChangedClasses()) {
						final WorkspaceClassInstance workspaceInstance = workspaceLoadedClasses.get(
								changedClass);
						if (workspaceInstance != null) {
							workspaceInstance.setStale(true);
						}
					}
					break;
				case IncrementalProjectBuilder.FULL_BUILD:
					for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
						IPluginModelBase model = entry.getKey();
						if (model != null) {
							changedContributions.add(model);
						}
					}
					for (WorkspaceClassInstance workspaceInstance : workspaceLoadedClasses.values()) {
						workspaceInstance.setStale(true);
					}
					break;
				case IncrementalProjectBuilder.CLEAN_BUILD:
					// workspace has been cleaned. Unload every service until next
					// they're built
					final Iterator<Map.Entry<IPluginModelBase, Bundle>> workspaceBundleIterator = workspaceInstalledBundles
							.entrySet().iterator();
					while (workspaceBundleIterator.hasNext()) {
						Map.Entry<IPluginModelBase, Bundle> entry = workspaceBundleIterator.next();
						final Bundle bundle = entry.getValue();

						try {
							uninstallBundle(bundle);
						} catch (BundleException e) {
							AcceleoCommonPlugin.log(new Status(IStatus.ERROR, AcceleoCommonPlugin.PLUGIN_ID,
									AcceleoCommonMessages.getString(UNINSTALLATION_FAILURE_KEY, bundle
											.getSymbolicName()), e));
						}

						workspaceBundleIterator.remove();
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
