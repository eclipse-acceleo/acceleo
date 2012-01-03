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
package org.eclipse.acceleo.engine.internal.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Eclipse-specific utilities for Acceleo traceability listener.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public final class AcceleoTraceabilityRegistryListenerUils {

	/** The list of the listeners created from the extension point contributions. */
	private static final List<AcceleoListenerDescriptor> LISTENERS = new ArrayList<AcceleoListenerDescriptor>();

	/**
	 * The constructor.
	 */
	private AcceleoTraceabilityRegistryListenerUils() {
		// prevent instantiation
	}

	/**
	 * Removes all the listeners from the registry. This will be called at plugin stopping.
	 */
	public static void clearRegistry() {
		LISTENERS.clear();
	}

	/**
	 * Adds a traceability listener to the registry.
	 * 
	 * @param acceleoListenerDescriptor
	 *            The traceability listener.
	 */
	public static void addListener(AcceleoListenerDescriptor acceleoListenerDescriptor) {
		LISTENERS.add(acceleoListenerDescriptor);
	}

	/**
	 * Removes the listeners with the given class name from the registry.
	 * 
	 * @param className
	 *            The name of the listener.
	 */
	public static void removeListener(String className) {
		for (AcceleoListenerDescriptor listener : LISTENERS) {
			if (listener.getName().equals(className)) {
				LISTENERS.remove(listener);
			}
		}
	}

	/**
	 * Returns a copy of the registry listeners' list.
	 * 
	 * @return A copy of the registry listeners' list.
	 */
	public static List<AcceleoListenerDescriptor> getListenerDescriptors() {
		return new ArrayList<AcceleoListenerDescriptor>(LISTENERS);
	}

}
