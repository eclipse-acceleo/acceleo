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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

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
		SERVICES.clear();
	}

	/**
	 * Returns all registered service classes.
	 * 
	 * @return All registered service classes.
	 */
	public static Set<Object> getRegisteredServices() {
		AcceleoWorkspaceUtil.refreshContributions();
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
}
