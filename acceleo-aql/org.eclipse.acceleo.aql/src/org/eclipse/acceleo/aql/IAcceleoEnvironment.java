/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql;

import java.nio.charset.Charset;
import java.util.Collection;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.emf.common.util.URI;

/**
 * Acceleo environment.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IAcceleoEnvironment {

	/**
	 * Gets the destination {@link URI}.
	 * 
	 * @return the destination {@link URI}
	 */
	URI getDestination();

	/**
	 * Registers the given module against this environment.
	 * 
	 * @param qualifiedName
	 *            The qualified name of this module.
	 * @param module
	 *            The module to register.
	 */
	void registerModule(String qualifiedName, Module module);

	/**
	 * Gets the {@link Module} qualified name.
	 * 
	 * @param module
	 *            the {@link Module}
	 * @return the {@link Module} qualified name if the {@link Module} is
	 *         {@link IAcceleoEnvironment#registerModule(String, Module) registered}, <code>null</code>
	 *         otherwise
	 */
	String getModuleQualifiedName(Module module);

	/**
	 * Gets the extend for the given module qualified name.
	 * 
	 * @param qualifiedName
	 *            the module qualified name
	 * @return the extend for the given module qualified name if nay, <code>null</code> otherwise
	 */
	String getExtend(String qualifiedName);

	/**
	 * Gets the {@link Collection} of imports for the given module qualified name.
	 * 
	 * @param qualifiedName
	 *            the module qualified name
	 * @return the {@link Collection} of imports for the given module qualified name
	 */
	Collection<String> getImports(String qualifiedName);

	/**
	 * Tells if the given {@link org.eclipse.acceleo.Module} qualified name exists.
	 * 
	 * @param qualifiedName
	 *            the {@link org.eclipse.acceleo.Module} qualified name
	 * @return <code>true</code> if the given {@link org.eclipse.acceleo.Module} qualified name exists,
	 *         <code>false</code> otherwise
	 */
	boolean hasModule(String qualifiedName);

	/**
	 * Gets the {@link IQueryEnvironment}.
	 * 
	 * @return the {@link IQueryEnvironment}
	 */
	IQueryEnvironment getQueryEnvironment();

	/**
	 * Pushes the given imported module qualified name.
	 * 
	 * @param importModuleQualifiedName
	 *            the imported module qualified name
	 * @param moduleElement
	 *            the {@link ModuleElement} been called
	 */
	void pushImport(String importModuleQualifiedName, ModuleElement moduleElement);

	/**
	 * Pushes the given module qualified name.
	 * 
	 * @param moduleElement
	 *            the {@link ModuleElement} been called
	 */
	void push(ModuleElement moduleElement);

	/**
	 * Removes a module qualified name from the latest call stack, popping that stack out as well if it's
	 * empty afterwards.
	 * 
	 * @param moduleElement
	 *            The {@link ModuleElement} we're exiting out of.
	 */
	void popStack(ModuleElement moduleElement);

	/**
	 * Opens a writer for the given file uri.
	 * 
	 * @param uri
	 *            The {@link URI} for which we need a writer.
	 * @param openMode
	 *            The mode in which to open the file.
	 * @param charset
	 *            The {@link Charset} for the target file.
	 * @param lineDelimiter
	 *            Line delimiter that should be used for that file.
	 */
	public void openWriter(URI uri, OpenModeKind openMode, Charset charset, String lineDelimiter);

	/**
	 * Closes the last {@link #openWriter(String, OpenModeKind, String, String) opened} writer.
	 */
	public void closeWriter();

	/**
	 * Writes the given {@link String} to the last {@link #openWriter(String, OpenModeKind, String, String)
	 * opened} writer.
	 * 
	 * @param text
	 *            the text to write
	 */
	public void write(String text);

	/**
	 * Gets the {@link GenerationResult}.
	 * 
	 * @return the {@link GenerationResult}
	 */
	GenerationResult getGenerationResult();

}
