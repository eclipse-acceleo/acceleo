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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile.main;

import org.eclipse.acceleo.model.mtl.Module;

/**
 * A module project handler for the 'FileBlocks' selection dialog. The goal of this class is to handle a
 * workspace project or a bundle project.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class FileBlocksProjectHandler {

	/**
	 * The name.
	 */
	private String name;

	/**
	 * The EMTL modules of the project.
	 */
	private Module[] modules;

	/**
	 * Indicates if the current project is resolved in the classpath of the file of the active editor.
	 */
	private boolean resolved;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            is the name
	 * @param modules
	 *            the EMTL modules of the project
	 * @param resolved
	 *            indicates if the current project is resolved in the classpath of the file of the active
	 *            editor
	 */
	public FileBlocksProjectHandler(String name, Module[] modules, boolean resolved) {
		this.name = name;
		this.modules = modules;
		this.resolved = resolved;
	}

	/**
	 * Gets the name of the workspace project or the symbolic name of the bundle.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the EMTL modules of the project.
	 * 
	 * @return the EMTL modules of the project
	 */
	public Module[] getModules() {
		return modules;
	}

	/**
	 * Indicates if the current project is resolved in the classpath of the file of the active editor.
	 * 
	 * @return true if the current project is resolved
	 */
	public boolean isResolved() {
		return resolved;
	}
}
