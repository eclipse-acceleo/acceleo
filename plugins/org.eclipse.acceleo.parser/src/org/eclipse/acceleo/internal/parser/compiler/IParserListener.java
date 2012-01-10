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

import org.eclipse.emf.common.util.URI;

/**
 * The common interface for the listeners of the compilation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public interface IParserListener {

	/**
	 * Called when the file is build.
	 * 
	 * @param file
	 *            The file to build.
	 */
	void startBuild(File file);

	/**
	 * Called when the given file is loaded as a dependency.
	 * 
	 * @param file
	 *            The file to load.
	 */
	void loadDependency(File file);

	/**
	 * Called when the resource at the given URI is loaded as a dependency.
	 * 
	 * @param uri
	 *            The URI of the resource to load.
	 */
	void loadDependency(URI uri);

	/**
	 * Called when the file built is saved.
	 * 
	 * @param file
	 *            The file built.
	 */
	void fileSaved(File file);

	/**
	 * Called when the compilation of the file is completed.
	 * 
	 * @param file
	 *            The file built.
	 */
	void endBuild(File file);
}
