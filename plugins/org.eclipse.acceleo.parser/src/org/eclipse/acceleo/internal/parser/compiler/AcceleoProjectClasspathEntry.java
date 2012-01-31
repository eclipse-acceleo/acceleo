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
package org.eclipse.acceleo.internal.parser.compiler;

import java.io.File;

/**
 * This class represent a classpath entry for an Acceleo project. This class defines an input folder and an
 * output folder for a given project.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoProjectClasspathEntry {

	/**
	 * The input directory.
	 */
	private File inputDirectory;

	/**
	 * The output directory.
	 */
	private File outputDirectory;

	/**
	 * Creates a new classpath entry.
	 * 
	 * @param inputDirectory
	 *            The input directory.
	 * @param outputDirectory
	 *            The output directory.
	 */
	public AcceleoProjectClasspathEntry(File inputDirectory, File outputDirectory) {
		this.inputDirectory = inputDirectory;
		this.outputDirectory = outputDirectory;
	}

	/**
	 * Returns the input directory.
	 * 
	 * @return The input directory.
	 */
	public File getInputDirectory() {
		return inputDirectory;
	}

	/**
	 * Returns the output directory.
	 * 
	 * @return The output directory.
	 */
	public File getOutputDirectory() {
		return outputDirectory;
	}
}
