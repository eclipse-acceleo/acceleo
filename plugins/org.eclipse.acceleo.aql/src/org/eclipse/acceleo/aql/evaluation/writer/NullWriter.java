/*******************************************************************************
 * Copyright (c) 2017, 2023 Obeo.
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

import org.eclipse.emf.common.util.URI;

/**
 * A writer that simply discards written bytes.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class NullWriter implements IAcceleoWriter {

	/**
	 * The target {@link URI}.
	 */
	private final URI targetURI;

	private final Charset charset;

	/**
	 * Constructor.
	 * 
	 * @param targetURI
	 *            the target {@link URI}
	 * @param charset
	 *            The {@link Charset} for our written content.
	 */
	public NullWriter(URI targetURI, Charset charset) {
		this.targetURI = targetURI;
		this.charset = charset;
	}

	@Override
	public void close() throws IOException {
		// nothing to do here
	}

	@Override
	public void append(String content) {
		// nothing to do here
	}

	@Override
	public URI getTargetURI() {
		return targetURI;
	}

	@Override
	public Charset getCharset() {
		return charset;
	}
}
