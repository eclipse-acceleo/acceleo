/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.compiler;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * The Acceleo Compiler ANT Task.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 * @since 3.1
 */
public class AcceleoCompiler extends Task {

	/**
	 * The compiler helper.
	 */
	private final AcceleoCompilerHelper helper = new AcceleoCompilerHelper();

	/**
	 * Sets the source folder to compile.
	 * 
	 * @param theSourceFolder
	 *            are the source folder to compile
	 */
	public void setSourceFolder(String theSourceFolder) {
		helper.setSourceFolder(theSourceFolder);
	}

	/**
	 * Sets the output folder.
	 * 
	 * @param theOutputFolder
	 *            The output folder.
	 */
	public void setOutputFolder(String theOutputFolder) {
		helper.setOutputFolder(theOutputFolder);
	}

	/**
	 * Sets the dependencies to load before to compile. They are separated by ';'.
	 * 
	 * @param allDependencies
	 *            are the dependencies identifiers
	 */
	public void setDependencies(String allDependencies) {
		helper.setDependencies(allDependencies);
	}

	/**
	 * Sets the binary resource attribute.
	 * 
	 * @param binaryResource
	 *            Indicates if we should use a binary resource.
	 */
	public void setBinaryResource(boolean binaryResource) {
		helper.setBinaryResource(binaryResource);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.tools.ant.Task#execute()
	 */
	@Override
	public void execute() throws BuildException {
		// CHECKSTYLE:OFF
		try {
			helper.execute();
		} catch (Throwable e) {
			log(e.getMessage(), Project.MSG_ERR);
			throw new BuildException(e.getMessage(), getLocation());
		}
		// CHECKSTYLE:ON
	}
}
