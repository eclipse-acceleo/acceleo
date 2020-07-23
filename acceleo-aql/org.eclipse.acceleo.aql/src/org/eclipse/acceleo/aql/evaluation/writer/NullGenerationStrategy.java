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
package org.eclipse.acceleo.aql.evaluation.writer;

import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.acceleo.OpenModeKind;
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
	public String getProtectedAreaContent(URI uri, String protectedAreaID) {
		// Do nothing
		return null;
	}

	@Override
	public IAcceleoWriter createWriterFor(URI uri, OpenModeKind openMode, Charset charset,
			String lineDelimiter) throws IOException {
		return new NullWriter(uri);
	}

	@Override
	public void terminate() {
		// Do nothing
	}
}
