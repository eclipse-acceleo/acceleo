/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

/**
 * Eclipse-specific utilities for Acceleo services. It will be initialized with all services that could be
 * parsed from the extension point if Eclipse is running and won't be used when outside of Eclipse.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoServicesEclipseUtil {
	/** Services are cached within {@link AcceleoWorkspaceUtil}. This will only store their qualified names. */
	private static final Set<String> REGISTERED_SERVICES = new LinkedHashSet<String>();

	/**
	 * Utility classes don't need a default constructor.
	 */
	private AcceleoServicesEclipseUtil() {
		// hides constructor
	}

	/**
	 * This will clear the registry off all services that have been registered for evaluation.
	 */
	public static void clearRegistry() {
		REGISTERED_SERVICES.clear();
	}

	/**
	 * Returns all registered service classes.
	 * 
	 * @return All registered service classes.
	 */
	public static Set<Object> getRegisteredServices() {
		return AcceleoWorkspaceUtil.INSTANCE.refreshInstances(REGISTERED_SERVICES, false);
	}

	/**
	 * Returns the registered service going by <code>qualifiedName</code>. <b>Note</b> that the service needs
	 * to be registered before it can be retrieved through this if it is not in the workspace.
	 * 
	 * @param qualifiedName
	 *            Qualified name of the service we seek an instance of.
	 * @return The registered service going by name <code>qualifiedName</code>.
	 */
	public static Object getService(String qualifiedName) {
		return AcceleoWorkspaceUtil.INSTANCE.refreshInstance(qualifiedName, false);
	}

	/**
	 * This will return an instance of class named <code>qualifiedName</code> loaded from the given bundle.
	 * This is fully equivalent to calling <code>bundle.loadClass(qualifiedName).newInstance()</code>.
	 * <p>
	 * As a result of this call, the service will be added to the list of registered services, allowing it to
	 * be retrieved through {@link #getRegisteredServices()} afterwards.
	 * </p>
	 * 
	 * @param bundle
	 *            The {@link Bundle} containing the acceleo file which tries to make use of a service name
	 *            <code>qualifiedName</code>.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	public static Object registerService(Bundle bundle, String qualifiedName) {
		Object instance = null;
		try {
			final Class<?> clazz = bundle.loadClass(qualifiedName);
			instance = clazz.newInstance();
			if (instance != null) {
				REGISTERED_SERVICES.add(qualifiedName);
			}
		} catch (ClassNotFoundException e) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassLookupFailure", //$NON-NLS-1$
					qualifiedName, bundle.getSymbolicName()), e, false);
		} catch (IllegalAccessException e) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassConstructorFailure", //$NON-NLS-1$
					qualifiedName, bundle.getSymbolicName()), e, false);
		} catch (InstantiationException e) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassInstantiationFailure", //$NON-NLS-1$
					qualifiedName, bundle.getSymbolicName()), e, false);
		}
		return instance;
	}

	/**
	 * This will refresh workspace contributions to Acceleo and load the given service if it is located in a
	 * workspace bundle.
	 * <p>
	 * As a result of this call, the service will be added to the list of registered services, allowing it to
	 * be retrieved through {@link #getRegisteredServices()} afterwards.
	 * </p>
	 * 
	 * @param project
	 *            The {@link IProject} containing the acceleo file which tries to make use of a service name
	 *            <code>qualifiedName</code>.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	public static Object registerService(IProject project, String qualifiedName) {
		final Object instance = AcceleoWorkspaceUtil.INSTANCE.getClassInstance(project, qualifiedName);
		if (instance != null) {
			REGISTERED_SERVICES.add(qualifiedName);
		}
		return instance;
	}

	/**
	 * This will return an instance of class named <code>qualifiedName</code> loaded from the given bundle.
	 * This will first attempt to search through the workspace projects if one of them corresponds to this
	 * symbolic name, and will be fully equivalent to calling
	 * <code>Platform.getBundle(bundleName).loadClass(qualifiedName).newinstance()</code> otherwise.
	 * <p>
	 * As a result of this call, the service will be added to the list of registered services, allowing it to
	 * be retrieved through {@link #getRegisteredServices()} afterwards.
	 * </p>
	 * 
	 * @param bundleName
	 *            The symbolic name of the bundle {@link Bundle} containing the acceleo file which tries to
	 *            make use of a service name <code>qualifiedName</code>.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	public static Object registerService(String bundleName, String qualifiedName) {
		Object instance = null;
		final IProject project = AcceleoWorkspaceUtil.getProject(bundleName);
		if (project != null) {
			instance = registerService(project, qualifiedName);
		} else {
			instance = registerService(Platform.getBundle(bundleName), qualifiedName);
		}
		return instance;
	}

	/**
	 * This will return an instance of class named <code>qualifiedName</code> loaded from the bundle
	 * containing the file described by <code>uri</code>.
	 * <p>
	 * As a result of this call, the service will be added to the list of registered services, allowing it to
	 * be retrieved through {@link #getRegisteredServices()} afterwards.
	 * </p>
	 * 
	 * @param uri
	 *            URI of the module currently being evaluated. This will be used as a source to find the
	 *            required service by looking through its dependencies.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	public static Object registerService(URI uri, String qualifiedName) {
		Object instance = null;
		if (uri.isPlatform()) {
			final String bundleName = uri.segment(1);
			final Bundle bundle = Platform.getBundle(bundleName);
			if (bundle != null) {
				instance = registerService(bundle, qualifiedName);
			}
		} else {
			final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
			final URI workspaceRootURI = URI.createURI(workspaceRoot.getLocationURI().toString());
			final URI workspaceRelative = uri.deresolve(workspaceRootURI);
			if (!workspaceRelative.equals(uri)) {
				final String projectName = workspaceRelative.segment(1);
				final IProject project = workspaceRoot.getProject(projectName);
				if (project != null && project.exists()) {
					instance = registerService(project, qualifiedName);
				}
			}
			if (instance == null) {
				instance = workspaceSuffixWorkaround(uri, qualifiedName);
			}
		}
		if (instance != null) {
			REGISTERED_SERVICES.add(qualifiedName);
		}
		return instance;
	}

	/**
	 * This is a workaround for workspace with same suffix.
	 * 
	 * @param uri
	 *            URI of the module currently being evaluated. This will be used as a source to find the
	 *            required service by looking through its dependencies.
	 * @param qualifiedName
	 *            Qualified name of the service we are looking for.
	 * @return An instance of the loaded service. Loaded services are stored as singleton instances.
	 */
	private static Object workspaceSuffixWorkaround(URI uri, String qualifiedName) {
		Object res = null;
		URI platformURI = URI.createURI(AcceleoWorkspaceUtil.resolveAsPlatformPluginResource(uri.toString()));
		if (platformURI != null) {
			String bundleName = platformURI.segment(1);
			Bundle bundle = Platform.getBundle(bundleName);
			if (bundle != null) {
				res = registerService(bundle, qualifiedName);
			}
		}
		return res;
	}
}
