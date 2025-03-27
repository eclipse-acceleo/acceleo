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
package org.eclipse.acceleo.aql.evaluation.strategy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.evaluation.writer.NullWriter;
import org.eclipse.emf.common.util.URI;

/**
 * A generation strategy that does nothing.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class NullGenerationStrategy implements IAcceleoGenerationStrategy {
	@Override
	public void closeWriter(IAcceleoWriter writer) throws IOException {
		// Do nothing
	}

	@Override
	public String consumeProtectedAreaContent(URI uri, String protectedAreaID) {
		// Do nothing
		return null;
	}

	@Override
	public Map<String, List<String>> consumeAllProtectedAreas(URI uri) {
		return Collections.emptyMap();
	}

	@Override
	public IAcceleoWriter createWriterForLog(URI uri, Charset charset, String lineDelimiter)
			throws IOException {
		return new NullWriter(uri, charset, lineDelimiter);
	}

	@Override
	public IAcceleoWriter createWriterForLostContent(URI uri, String protectedAreaID, Charset charset,
			String lineDelimiter) throws IOException {
		return new NullWriter(uri, charset, lineDelimiter);
	}

	@Override
	public IAcceleoWriter createWriterFor(URI uri, OpenModeKind openMode, Charset charset,
			String lineDelimiter) throws IOException {
		return new NullWriter(uri, charset, lineDelimiter);
	}

	@Override
	public void start(URI destination) {
		// Do nothing
	}

	@Override
	public void terminate() {
		// Do nothing
	}
}
