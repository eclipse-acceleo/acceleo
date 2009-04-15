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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
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
		AcceleoWorkspaceUtil.INSTANCE.addWorkspaceContribution(project);
		AcceleoWorkspaceUtil.INSTANCE.refreshContributions();
		final Object instance = AcceleoWorkspaceUtil.INSTANCE.getClassInstance(qualifiedName);
		if (instance != null) {
			REGISTERED_SERVICES.add(qualifiedName);
		}
		return instance;
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
			// FIXME log
		} catch (IllegalAccessException e) {
			// FIXME log
		} catch (InstantiationException e) {
			// FIXME log
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
		final IProject project = AcceleoWorkspaceUtil.INSTANCE.getProject(bundleName);
		if (project != null) {
			instance = registerService(project, qualifiedName);
		} else {
			instance = registerService(Platform.getBundle(bundleName), qualifiedName);
		}
		return instance;
	}

	/**
	 * Returns all registered service classes.
	 * 
	 * @return All registered service classes.
	 */
	public static Set<Object> getRegisteredServices() {
		return AcceleoWorkspaceUtil.INSTANCE.refreshInstances(REGISTERED_SERVICES, false);
	}
}
