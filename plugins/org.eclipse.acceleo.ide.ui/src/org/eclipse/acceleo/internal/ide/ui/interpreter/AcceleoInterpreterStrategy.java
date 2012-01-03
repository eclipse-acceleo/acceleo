/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.interpreter;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter;

/**
 * This particular implementation of a generation strategy will allow us to quickly retrieve the preview of a
 * generation. This is only intended to be used in the context of the interpreter view.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoInterpreterStrategy extends PreviewStrategy {
	/** Writers that have been created through this strategy. */
	Map<String, Writer> writers = new HashMap<String, Writer>();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy#createWriterFor(java.io.File,
	 *      org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter, boolean, boolean,
	 *      java.lang.String)
	 */
	@Override
	public AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous,
			boolean appendMode, boolean hasJMergeTags, String charset) throws IOException {
		AbstractAcceleoWriter writer = super.createWriterFor(file, previous, appendMode, hasJMergeTags,
				charset);
		writers.put(file.getPath(), writer);
		return writer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy#preparePreview(java.util.Map)
	 */
	@Override
	public Map<String, String> preparePreview(Map<String, Writer> currentWriters) {
		if (currentWriters == null) {
			return super.preparePreview(writers);
		}
		return super.preparePreview(currentWriters);
	}
}
