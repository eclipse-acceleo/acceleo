/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
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
 * Utility class to represent a contribution from the dynamic module extension point.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDynamicModulesDescriptor {

	/**
	 * The generator IDs.
	 */
	private List<String> generatorIDs = new ArrayList<String>();

	/**
	 * The module paths.
	 */
	private List<String> paths = new ArrayList<String>();

	/**
	 * The constructor.
	 * 
	 * @param generatorIDs
	 *            The generator ID.
	 * @param paths
	 *            The paths of the dynamic modules.
	 */
	public AcceleoDynamicModulesDescriptor(List<String> generatorIDs, List<String> paths) {
		this.generatorIDs = generatorIDs;
		this.paths = paths;
	}

	/**
	 * Returns the generator IDs.
	 * 
	 * @return the generator IDs.
	 */
	public List<String> getGeneratorIDs() {
		return generatorIDs;
	}

	/**
	 * Returns the paths of the dynamic modules.
	 * 
	 * @return The paths of the dynamic modules.
	 */
	public List<String> getPaths() {
		return paths;
	}
}
