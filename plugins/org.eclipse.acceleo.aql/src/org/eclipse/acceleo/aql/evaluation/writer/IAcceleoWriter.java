/*******************************************************************************
 * Copyright (c) 2017, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation.writer;

import java.io.Closeable;
import java.nio.charset.Charset;

import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.emf.common.util.URI;

/**
 * Base class for the writers that need to be returned by the {@link IAcceleoGenerationStrategy generation
 * strategies}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public interface IAcceleoWriter extends Closeable {
	/**
	 * Returns the URI for which this writer was created.
	 * 
	 * @return The URI for which this writer was created.
	 */
	URI getTargetURI();

	/**
	 * Gets the {@link Charset} of this writer.
	 * 
	 * @return the {@link Charset} of this writer
	 */
	Charset getCharset();

	/**
	 * Gets the line delimiter.
	 * 
	 * @return the line delimiter
	 */
	String getLineDelimiter();

	/**
	 * Appends the given content.
	 * 
	 * @param content
	 *            the content
	 */
	void append(String content);
}
