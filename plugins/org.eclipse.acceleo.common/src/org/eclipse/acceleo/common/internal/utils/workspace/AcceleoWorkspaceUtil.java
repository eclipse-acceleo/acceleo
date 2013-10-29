/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.workspace;

import com.google.common.collect.Sets;

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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.internal.registry.ExtensionRegistry;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.spi.IDynamicExtensionRegistry;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
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

	/**
	 * Time-out used when we are waiting for an OSGI operation done through package admin.
	 */
	private static final int OSGI_TIMEOUT = 3000;

	/** Key of the error message that is to be used when we cannot uninstall workspace bundles. */
	private static final String UNINSTALLATION_FAILURE_KEY = "WorkspaceUtil.UninstallationFailure"; //$NON-NLS-1$

	/** We'll use this to add 'empty' contributions for the bundles we dynamically install. */
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

	/** This will allow us to react to project additions/removals in the running workspace. */
	private final IResourceChangeListener workspaceListener = new WorkspaceResourcesListener();

	/** This will allow us to only load once the "duplicate project" warnings. */
	private final Set<String> logOnceProjectLoad = Sets.newHashSet();

	/**
	 * This class is a singleton. Access instance through {@link #INSTANCE}.
	 */
	private AcceleoWorkspaceUtil() {
		// hides constructor
	}

	/**
	 * This will search through the workspace for a plugin defined with the given symbolic name and return it
	 * if any.
	 * 
	 * @param bundleName
	 *            Symbolic name of the plugin we're searching a workspace project for.
	 * @return The workspace project of the given symbolic name, <code>null</code> if none could be found.
	 */
	public static IProject getProject(String bundleName) {
		for (IPluginModelBase model : PluginRegistry.getWorkspaceModels()) {
			if (model.getBundleDescription().getSymbolicName().equals(bundleName)) {
				return model.getUnderlyingResource().getProject();
			}
		}
		return null;
	}

	/**
	 * This will try and find a resource of the given name using the bundle from which was originally loaded
	 * the given class so as to try and detect if it is jarred. If <code>clazz</code> hasn't been loaded from
	 * a bundle class loader, we'll resort to the default class loader mechanism. This will only return
	 * <code>null</code> in the case where the resource at <code>resourcePath</code> cannot be located at all.
	 * 
	 * @param clazz
	 *            Class which class loader will be used to try and locate the resource.
	 * @param resourcePath
	 *            Path of the resource we seek, relative to the class.
	 * @return The URL of the resource as we could locate it.
	 * @throws IOException
	 *             This will be thrown if we fail to convert bundle-scheme URIs into file-scheme URIs.
	 */
	public static URL getResourceURL(Class<?> clazz, String resourcePath) throws IOException {
		BundleContext context = AcceleoCommonPlugin.getDefault().getContext();
		ServiceReference packageAdminReference = context.getServiceReference(PackageAdmin.class.getName());
		PackageAdmin packageAdmin = null;
		if (packageAdminReference != null) {
			packageAdmin = (PackageAdmin)context.getService(packageAdminReference);
		}

		URL resourceURL = null;
		if (packageAdmin != null) {
			Bundle bundle = packageAdmin.getBundle(clazz);
			if (bundle != null) {
				final String pathSeparator = "/"; //$NON-NLS-1$
				// We found the appropriate bundle. We'll now try and determine whether the emtl is jarred
				resourceURL = getBundleResourceURL(bundle, pathSeparator, resourcePath);
			}
		}
		/*
		 * We couldn't locate either the bundle which loaded the class or the resource. Resort to the class
		 * loader and return null if it cannot locate the resource either.
		 */
		if (resourceURL == null) {
			resourceURL = clazz.getResource(resourcePath);
		}

		if (packageAdminReference != null) {
			context.ungetService(packageAdminReference);
		}
		return resourceURL;
	}

	/**
	 * Returns the URL of the resource at the given path in the given bundle.
	 * 
	 * @param bundle
	 *            The bundle in which we will look for the resource
	 * @param pathSeparator
	 *            The path separator
	 * @param resourcePath
	 *            The path of the resource in the bundle
	 * @return the URL of the resource at the given path in the given bundle
	 * @throws IOException
	 *             This will be thrown if we fail to convert bundle-scheme URIs into file-scheme URIs.
	 */
	private static URL getBundleResourceURL(Bundle bundle, String pathSeparator, String resourcePath)
			throws IOException {
		URL resourceURL = null;
		Enumeration<?> emtlFiles = bundle.findEntries(pathSeparator, resourcePath, true);
		if (emtlFiles != null && emtlFiles.hasMoreElements()) {
			resourceURL = (URL)emtlFiles.nextElement();
		}

		// We haven't found the URL of the resource, let's check if we have an absolute URL instead of the
		// name of the resource. Fix for [336109] and [325351].
		if (resourceURL == null) {
			Enumeration<?> resources = bundle.getResources(resourcePath);
			if (resources != null && resources.hasMoreElements()) {
				resourceURL = (URL)resources.nextElement();
			}
		}

		// This can only be a bundle-scheme URL if we found the URL. Convert it to file or jar scheme
		if (resourceURL != null) {
			resourceURL = FileLocator.resolve(resourceURL);
		}
		return resourceURL;
	}

	/**
	 * Return the bundle of the given class.
	 * 
	 * @param clazz
	 *            The class
	 * @return The bundle of the given class
	 * @since 3.1
	 */
	public static Bundle getBundle(Class<?> clazz) {
		Bundle bundle = null;
		BundleContext context = AcceleoCommonPlugin.getDefault().getContext();
		ServiceReference packageAdminReference = context.getServiceReference(PackageAdmin.class.getName());
		PackageAdmin packageAdmin = null;
		if (packageAdminReference != null) {
			packageAdmin = (PackageAdmin)context.getService(packageAdminReference);
		}

		if (packageAdmin != null) {
			bundle = packageAdmin.getBundle(clazz);
		}
		if (packageAdminReference != null) {
			context.ungetService(packageAdminReference);
		}
		return bundle;
	}

	/**
	 * Returns the bundles with the given name.
	 * 
	 * @param bundleName
	 *            The bundle name.
	 * @return The bundles with the given name.
	 */
	public static Bundle[] getBundles(String bundleName) {
		Bundle[] bundle = null;
		BundleContext context = AcceleoCommonPlugin.getDefault().getContext();
		ServiceReference packageAdminReference = context.getServiceReference(PackageAdmin.class.getName());
		PackageAdmin packageAdmin = null;
		if (packageAdminReference != null) {
			packageAdmin = (PackageAdmin)context.getService(packageAdminReference);
		}

		if (packageAdmin != null) {
			bundle = packageAdmin.getBundles(bundleName, null);
		}
		if (packageAdminReference != null) {
			context.ungetService(packageAdminReference);
		}
		return bundle;
	}

	/**
	 * This will try and resolve the given {@link java.io.File} within the workspace and return it if found.
	 * 
	 * @param file
	 *            The file we wish to find in the workspace.
	 * @return The resolved IFile if any, <code>null</code> otherwise.
	 */
	public static IFile getWorkspaceFile(File file) {
		return ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(file.getAbsolutePath()));
	}

	/**
	 * This will try and resolve the given path against a workspace file. The path can either be relative to
	 * the workpace, an absolute path towards a workspace file or represent an uri of platform scheme.
	 * 
	 * @param path
	 *            The path we seek a file for. Cannot be <code>null</code>.
	 * @return The resolved file.
	 * @throws IOException
	 *             This will be throw if we cannot resolve a "platform://plugin" URI to an existing file.
	 */
	public static File getWorkspaceFile(String path) throws IOException {
		final String platformResourcePrefix = "platform:/resource/"; //$NON-NLS-1$
		final String platformPluginPrefix = "platform:/plugin/"; //$NON-NLS-1$

		final File soughtFile;
		if (path.startsWith(platformResourcePrefix)) {
			final IPath relativePath = new Path(path.substring(platformResourcePrefix.length()));
			final IFile soughtIFile = ResourcesPlugin.getWorkspace().getRoot().getFile(relativePath);
			soughtFile = soughtIFile.getLocation().toFile();
		} else if (path.startsWith(platformPluginPrefix)) {
			final int bundleNameEnd = path.indexOf('/', platformPluginPrefix.length() + 1);
			final String bundleName = path.substring(platformPluginPrefix.length(), bundleNameEnd);
			Bundle bundle = Platform.getBundle(bundleName);
			if (bundle != null) {
				final URL bundleFileURL = bundle.getEntry(path.substring(bundleNameEnd));
				final URL fileURL = FileLocator.toFileURL(bundleFileURL);
				soughtFile = new File(fileURL.getFile());
			} else {
				/*
				 * Being here means that the bundle id is different than the bundle name. We could try and
				 * find the corresponding bundle in the PluginRegistry.getActiveModels(false) list, but is it
				 * worth the trouble? Most cases should be handled by the previous code and going through such
				 * a loop would be extremely CPU intensive.
				 */
				soughtFile = null;
			}
		} else {
			final IPath workspaceLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			IPath fullPath = new Path(path);
			if (workspaceLocation.isPrefixOf(fullPath)) {
				fullPath = fullPath.removeFirstSegments(workspaceLocation.segmentCount());
			}
			final IFile soughtIFile = ResourcesPlugin.getWorkspace().getRoot().getFile(fullPath);
			soughtFile = soughtIFile.getLocation().toFile();
		}
		return soughtFile;
	}

	/**
	 * This can be used to convert a file-scheme URI to a "platform:/plugin" scheme URI if it can be resolved
	 * in the installed plugins.
	 * 
	 * @param filePath
	 *            File scheme URI that is to be converted.
	 * @return The converted URI if the file could be resolved in the installed plugins, <code>null</code>
	 *         otherwise.
	 */
	public static String resolveAsPlatformPlugin(String filePath) {
		BundleURLConverter converter = new BundleURLConverter(filePath);
		return converter.resolveAsPlatformPlugin();
	}

	/**
	 * This can be used to convert a file-protocol URI to a native protocol (jar, file, http...) URI if it can
	 * be resolved in the installed plugins.
	 * 
	 * @param filePath
	 *            File protocol URI that is to be converted.
	 * @return The converted URI if the file could be resolved in the installed plugins, <code>null</code>
	 *         otherwise.
	 */
	public static String resolveInBundles(String filePath) {
		BundleURLConverter converter = new BundleURLConverter(filePath);
		return converter.resolveAsNativeProtocolURL();
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
	private static Set<String> getOutputFolders(IProject project) {
		final Set<String> classpathEntries = new CompactLinkedHashSet<String>();
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
					if (workspaceInstance.getBundle().equals(model.getBundleDescription().getSymbolicName())) {
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

		// The class hasn't been instantiated yet ; search for the class without instantiating it
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
					// Swallow this ; we'll log the issue later on if we cannot find the class at all
				}
			}
		}

		if (clazz == null) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassLookupFailure", //$NON-NLS-1$
					qualifiedName), false);
		}

		return clazz;
	}

	/**
	 * This will install or refresh the given workspace contribution if needed, then search through it for a
	 * class corresponding to <code>qualifiedName</code>.
	 * 
	 * @param project
	 *            The project that is to be dynamically installed when Acceleo searches for workspace
	 *            contributions.
	 * @param qualifiedName
	 *            The qualified name of the class we seek to load.
	 * @return An instance of the class <code>qualifiedName</code> if it could be found in the workspace
	 *         bundles, <code>null</code> otherwise.
	 */
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

	/**
	 * This will try and load a ResourceBundle for the given qualified name.
	 * 
	 * @param qualifiedName
	 *            Name if the resource bundle we need to load.
	 * @return The loaded resource bundle.
	 */
	public synchronized ResourceBundle getResourceBundle(String qualifiedName) {
		MissingResourceException originalException = null;

		// shortcut evaluation in case this properties file can be found in the current classloader.
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(qualifiedName);
			return bundle;
		} catch (MissingResourceException e) {
			originalException = e;
		}

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
					// Swallow this, we'll throw the original MissingResourceException
				} finally {
					try {
						if (stream != null) {
							stream.close();
						}
					} catch (IOException e) {
						// Swallow this, we'll throw the original MissingResourceException
					}
				}
			}
		}
		throw originalException;
	}

	/**
	 * Retrieves the singleton instance of the given service class after refreshing it if needed.
	 * 
	 * @param serviceClass
	 *            The service class we need an instance of.
	 * @return The singleton instance of the given service class if any.
	 */
	public synchronized Object getServiceInstance(Class<?> serviceClass) {
		String qualifiedName = serviceClass.getName();
		for (Map.Entry<String, WorkspaceClassInstance> workspaceClass : workspaceLoadedClasses.entrySet()) {
			if (workspaceClass.getKey().equals(qualifiedName)) {
				WorkspaceClassInstance workspaceInstance = workspaceClass.getValue();
				if (workspaceInstance.isStale()) {
					for (Map.Entry<IPluginModelBase, Bundle> entry : workspaceInstalledBundles.entrySet()) {
						final IPluginModelBase model = entry.getKey();
						if (workspaceInstance.getBundle().equals(
								model.getBundleDescription().getSymbolicName())) {
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
	 * This can be used to check whether the given class is located in a dynamically installed bundle.
	 * 
	 * @param clazz
	 *            The class of which we need to determine the originating bundle.
	 * @return <code>true</code> if the given class has been loaded from a dynamic bundle, <code>false</code>
	 *         otherwise.
	 */
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
				final String candidateLocationReference = REFERENCE_URI_PREFIX
						+ URLDecoder.decode(url.toExternalForm(), System.getProperty("file.encoding")); //$NON-NLS-1$

				Bundle bundle = getBundle(candidateLocationReference);

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
			 * Closing and deleting projects trigger the same actions : we must remove the model listener and
			 * uninstall the bundle.
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
					//$FALL-THROUGH$
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
						final WorkspaceClassInstance workspaceInstance = workspaceLoadedClasses
								.get(changedClass);
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
					// workspace has been cleaned. Unload every service until next they're built
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
