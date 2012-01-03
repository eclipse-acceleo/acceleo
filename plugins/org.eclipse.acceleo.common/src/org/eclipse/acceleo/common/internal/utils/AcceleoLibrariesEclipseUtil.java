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
package org.eclipse.acceleo.common.internal.utils;

import java.util.ArrayList;
import java.util.Set;

import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.library.connector.ILibrary;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;

/**
 * Eclipse-specific utilities for Acceleo services. It will be initialized with all libraries that could be
 * parsed from the extension point if Eclipse is running and won't be used when outside of Eclipse.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoLibrariesEclipseUtil {

	/** Keeps track of all connectors that are contributed to the libraries extension point. */
	private static final Set<ILibrary> LIBRARIES = new CompactLinkedHashSet<ILibrary>();

	/**
	 * Utility classes don't need a default constructor.
	 */
	private AcceleoLibrariesEclipseUtil() {
		// hides constructor
	}

	/**
	 * Add a library to this registry.
	 * 
	 * @param library
	 *            the library to add
	 */
	public static void addLibrary(ILibrary library) {
		LIBRARIES.add(library);
	}

	/**
	 * Clears all registered extensions out of the eclipse registry.
	 */
	public static void clearRegistry() {
		LIBRARIES.clear();
	}

	/**
	 * Returns all registered library instances.
	 * 
	 * @return All registered library instances.
	 */
	public static Set<ILibrary> getRegisteredLibraries() {
		AcceleoWorkspaceUtil.INSTANCE.refreshContributions();
		return new CompactLinkedHashSet<ILibrary>(LIBRARIES);
	}

	/**
	 * Removes a given libraries from the list of available ones.
	 * 
	 * @param library
	 *            The filename of the library that is to be removed.
	 */
	public static void removeLibrary(String library) {
		for (ILibrary candidate : new ArrayList<ILibrary>(LIBRARIES)) {
			if (candidate.getURI().toString().endsWith(library)) {
				LIBRARIES.remove(candidate);
			}
		}
	}
}
