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
package org.eclipse.acceleo.engine.generation.strategy;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter;

/**
 * This may be extended instead of {@link IAcceleoGenerationStrategy} to ease the implementation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public abstract class AbstractGenerationStrategy implements IAcceleoGenerationStrategy {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#awaitCompletion()
	 */
	public void awaitCompletion() throws InterruptedException {
		// Empty implementation
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#createLostFile(java.lang.String,
	 *      java.util.Map)
	 */
	public Map<String, StringWriter> createLostFile(String originalPath, Map<String, String> lostCode) {
		// Empty implementation
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#createLostFiles(java.util.Map)
	 */
	public Map<String, StringWriter> createLostFiles(Map<String, Map<String, String>> lostCode) {
		// Empty implementation
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#createWriterFor(java.io.File,
	 *      org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter, boolean, boolean)
	 */
	public abstract AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous,
			boolean appendMode, boolean hasJMergeTags) throws IOException;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#createWriterFor(java.io.File,
	 *      org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter, boolean, boolean,
	 *      java.lang.String)
	 */
	public abstract AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous,
			boolean appendMode, boolean hasJMergeTags, String charset) throws IOException;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#dispose()
	 */
	public void dispose() {
		// Empty implementation
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#flushWriter(java.lang.String,
	 *      java.io.Writer)
	 */
	public void flushWriter(String filePath, Writer writer) throws IOException {
		writer.flush();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#flushWriters(java.util.Map)
	 */
	public void flushWriters(Map<String, Writer> currentWriters) throws IOException {
		// Empty implementation
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#preparePreview(java.util.Map)
	 */
	public Map<String, String> preparePreview(Map<String, Writer> currentWriters) {
		return new HashMap<String, String>();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#willReturnPreview()
	 */
	public boolean willReturnPreview() {
		return false;
	}
}
