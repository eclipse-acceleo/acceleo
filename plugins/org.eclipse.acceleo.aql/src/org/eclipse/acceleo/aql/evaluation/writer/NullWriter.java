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

	/**
	 * The {@link Charset}.
	 */
	private final Charset charset;

	/**
	 * The line separator.
	 */
	private String lineSeparator;

	/**
	 * Constructor.
	 * 
	 * @param targetURI
	 *            the target {@link URI}
	 * @param lineSeparator
	 *            the line separator
	 * @param charset
	 *            The {@link Charset} for our written content.
	 */
	public NullWriter(URI targetURI, Charset charset, String lineSeparator) {
		this.targetURI = targetURI;
		this.charset = charset;
		this.lineSeparator = lineSeparator;
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

	@Override
	public String getLineDelimiter() {
		return lineSeparator;
	}

}
