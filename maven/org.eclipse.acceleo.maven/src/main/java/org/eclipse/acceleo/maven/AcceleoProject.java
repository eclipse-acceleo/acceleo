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
package org.eclipse.acceleo.maven;

import java.io.File;
import java.util.List;

/**
 * Utility class to map the Acceleo project declared in the pom.xml
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoProject {

	/**
	 * The project root.
	 */
	private File root;

	/**
	 * The classpath entries.
	 */
	private List<Entry> entries;

	/**
	 * The jars.
	 */
	private List<String> jars;

	/**
	 * The dependencies.
	 */
	private List<AcceleoProject> dependencies;

	/**
	 * Returns the classpath entries.
	 * 
	 * @return The classpath entries.
	 */
	public List<Entry> getEntries() {
		return entries;
	}

	/**
	 * Returns the Acceleo project dependencies.
	 * 
	 * @return The Acceleo project dependencies.
	 */
	public List<AcceleoProject> getDependencies() {
		return dependencies;
	}

	/**
	 * Returns the jar dependencies.
	 * 
	 * @return The jar dependencies.
	 */
	public List<String> getJars() {
		return jars;
	}

	/**
	 * Returns the project root.
	 * 
	 * @return The project root.
	 */
	public File getRoot() {
		return root;
	}
}
