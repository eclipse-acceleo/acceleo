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
package org.eclipse.acceleo.common.internal.utils.workspace;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.ecore.EPackage;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
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
	 * This will refresh the list of contributions to the registry by either installing the given plugins in
	 * the current running Eclipse or refresh their packages. Keep this synchronized as it will be called by
	 * each of the utilities and these calls can come from multiple threads.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public void refreshContributions() {
		for (ClassLoadingCompanion companion : ClassLoadingCompanionsRegistry.INSTANCE
				.getAllRegisteredCompanions()) {
			companion.refreshContributions();
		}
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
	 * This will return the singleton instance of the given class that serves as invocation source by using
	 * the registered {@link ClassLoadingCompanion}.
	 * 
	 * @param serviceClass
	 *            The class we need the service singleton of.
	 * @return The singleton instance of the given service class.
	 * @since 3.6
	 */
	public Object getServiceInstance(Class<?> serviceClass) {
		Iterator<ClassLoadingCompanion> it = ClassLoadingCompanionsRegistry.INSTANCE
				.getAllRegisteredCompanions().iterator();
		Object found = null;
		while (it.hasNext() && found == null) {
			found = it.next().getServiceInstance(serviceClass);
		}
		return found;
	}

	/**
	 * This will refresh workspace contributions to Acceleo and load the given service if it is located in a
	 * workspace bundle.
	 * 
	 * @param project
	 *            The {@link IProject} containing the acceleo file which tries to make use of a service name
	 *            <code>qualifiedName</code>.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	public Class<?> getClass(IProject project, String qualifiedName) {
		Iterator<ClassLoadingCompanion> it = ClassLoadingCompanionsRegistry.INSTANCE
				.getAllRegisteredCompanions().iterator();
		Class<?> found = null;
		while (it.hasNext() && found == null) {
			found = it.next().getClass(project, qualifiedName);
		}
		return found;
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
		Iterator<ClassLoadingCompanion> it = ClassLoadingCompanionsRegistry.INSTANCE
				.getAllRegisteredCompanions().iterator();
		IProject found = null;
		while (it.hasNext() && found == null) {
			found = it.next().getProject(bundleName);
		}
		return found;
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
	 * Adds model listeners to all workspace-defined bundles. This will be called at plugin starting and is
	 * not intended to be called by clients.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public void initialize() {
		for (ClassLoadingCompanion companion : ClassLoadingCompanionsRegistry.INSTANCE
				.getAllRegisteredCompanions()) {
			companion.initialize();
		}
	}

	/**
	 * This can be used to uninstall all manually loaded bundles from the registry and remove all listeners.
	 * It will be called on plugin stopping and is not intended to be called by clients.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public void dispose() {
		for (ClassLoadingCompanion companion : ClassLoadingCompanionsRegistry.INSTANCE
				.getAllRegisteredCompanions()) {
			companion.dispose();
		}
	}

	/**
	 * This can be used to check whether the given class is located in a dynamically installed bundle.
	 * 
	 * @param clazz
	 *            The class of which we need to determine the originating bundle.
	 * @return <code>true</code> if the given class has been loaded from a dynamic bundle, <code>false</code>
	 *         otherwise.
	 */
	public boolean isInDynamicBundle(Class<? extends EPackage> clazz) {
		Iterator<ClassLoadingCompanion> it = ClassLoadingCompanionsRegistry.INSTANCE
				.getAllRegisteredCompanions().iterator();
		boolean isDynamic = false;
		while (it.hasNext() && !isDynamic) {
			isDynamic = it.next().isInDynamicBundle(clazz);
		}
		return isDynamic;
	}

	/**
	 * This will try and load a ResourceBundle for the given qualified name.
	 * 
	 * @param qualifiedName
	 *            Name if the resource bundle we need to load.
	 * @return The loaded resource bundle.
	 */
	public ResourceBundle getResourceBundle(String qualifiedName) {
		MissingResourceException originalException = null;

		// shortcut evaluation in case this properties file can be found in the current classloader.
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(qualifiedName);
			return bundle;
		} catch (MissingResourceException e) {
			originalException = e;
		}
		Iterator<ClassLoadingCompanion> it = ClassLoadingCompanionsRegistry.INSTANCE
				.getAllRegisteredCompanions().iterator();
		while (it.hasNext()) {
			ResourceBundle found = it.next().getResourceBundle(qualifiedName);
			if (found != null) {
				return found;
			}
		}
		throw originalException;
	}

	/**
	 * Resolve a filePath in the bundles and return the corresponding native protocol URL.
	 * 
	 * @param filePath
	 *            a file path.
	 * @return the absolute path if resolved, null otherwise.
	 */
	public static String resolveInBundles(String filePath) {
		BundleURLConverter converter = new BundleURLConverter(filePath);
		return converter.resolveAsNativeProtocolURL();
	}

	/**
	 * Return the bundle of the given class.
	 * 
	 * @param clazz
	 *            The class
	 * @return The bundle of the given class
	 * @since 3.1
	 */
	public static Bundle getBundle(Class clazz) {
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
	 * Returns the bundle corresponding to the given location if any.
	 * 
	 * @param pluginLocation
	 *            The location of the bundle we seek.
	 * @return The bundle corresponding to the given location if any, <code>null</code> otherwise.
	 */
	public static Bundle getBundle(String pluginLocation) {
		Bundle[] bundles = AcceleoCommonPlugin.getDefault().getContext().getBundles();
		for (int i = 0; i < bundles.length; i++) {
			if (pluginLocation.equals(bundles[i].getLocation())) {
				return bundles[i];
			}
		}
		return null;
	}

}
