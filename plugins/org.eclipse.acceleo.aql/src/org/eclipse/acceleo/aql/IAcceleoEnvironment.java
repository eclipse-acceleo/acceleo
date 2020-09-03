/*******************************************************************************
 * Copyright (c) 2017, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql;

import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
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
	 * Gets the {@link IQualifiedNameQueryEnvironment}.
	 * 
	 * @return the {@link IQualifiedNameQueryEnvironment}
	 */
	IQualifiedNameQueryEnvironment getQueryEnvironment();

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
	 * @throws IOException
	 *             if the writed can't be opened
	 */
	void openWriter(URI uri, OpenModeKind openMode, Charset charset, String lineDelimiter) throws IOException;

	/**
	 * Closes the last {@link #openWriter(String, OpenModeKind, String, String) opened} writer.
	 * 
	 * @throws IOException
	 *             if the writer can't be closed
	 */
	void closeWriter() throws IOException;

	/**
	 * Writes the given {@link String} to the last {@link #openWriter(String, OpenModeKind, String, String)
	 * opened} writer.
	 * 
	 * @param text
	 *            the text to write
	 * @throws IOException
	 *             if the writer can't be written
	 */
	void write(String text) throws IOException;

	/**
	 * Gets the {@link GenerationResult}.
	 * 
	 * @return the {@link GenerationResult}
	 */
	GenerationResult getGenerationResult();

}
