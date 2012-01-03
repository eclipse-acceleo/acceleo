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
 * This will contain all Acceleo engines that have been parsed for the extension point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoEngineRegistry {
	/** List of engine creators created from the extension point contributions. */
	private static final List<AcceleoEngineCreatorDescriptor> CREATORS = new ArrayList<AcceleoEngineCreatorDescriptor>();

	/**
	 * Utility classes don't need a default constructor.
	 */
	private AcceleoEngineRegistry() {
		// hides constructor
	}

	/**
	 * Adds an engine creator the registry.
	 * 
	 * @param creator
	 *            The engine creator that is to be added to the registry.
	 */
	public static void addCreator(AcceleoEngineCreatorDescriptor creator) {
		CREATORS.add(creator);
	}

	/**
	 * Removes all creators from the registry. This will be called at plugin stopping.
	 */
	public static void clearRegistry() {
		CREATORS.clear();
	}

	/**
	 * Returns a copy of the registered creators' list.
	 * 
	 * @return A copy of the registered creators' list.
	 */
	public static List<AcceleoEngineCreatorDescriptor> getRegisteredCreators() {
		return new ArrayList<AcceleoEngineCreatorDescriptor>(CREATORS);
	}

	/**
	 * Removes an engine creator from the registry.
	 * 
	 * @param engineClassName
	 *            Qualified class name for which the engine creator is to be removed from the registry.
	 */
	public static void removeCreator(String engineClassName) {
		for (AcceleoEngineCreatorDescriptor creator : getRegisteredCreators()) {
			if (creator.getClassName().equals(engineClassName)) {
				CREATORS.remove(creator);
			}
		}
	}
}
