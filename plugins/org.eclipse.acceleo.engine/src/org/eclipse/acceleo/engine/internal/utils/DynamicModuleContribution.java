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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A dynamic module contribution.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class DynamicModuleContribution {
	/**
	 * The generator IDs.
	 */
	private List<String> generatorIDs = new ArrayList<String>();

	/**
	 * The module files.
	 */
	private List<URL> files = new ArrayList<URL>();

	/**
	 * The constructor.
	 * 
	 * @param generatorIDs
	 *            The generator ID.
	 * @param files
	 *            The files of the dynamic modules.
	 */
	public DynamicModuleContribution(List<String> generatorIDs, List<URL> files) {
		this.generatorIDs = generatorIDs;
		this.files = files;
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
	 * Returns the files of the dynamic modules.
	 * 
	 * @return The files of the dynamic modules.
	 */
	public List<URL> getFiles() {
		return files;
	}
}
