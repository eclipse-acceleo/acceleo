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
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * The default {@link URI} writer used by Acceleo.
 * <p>
 * Data will be written to the disk without any verification about the workspace or VCS. Do not use with
 * pessimistic locking VCS or if JMerge is needed.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoFileWriter implements IAcceleoWriter {

	/**
	 * The {@link StringBuilder} initial size.
	 */
	private static final int INITIAL_SIZE = 1024;

	/** The charset for our written content. */
	protected final Charset charset;

	/** {@link URIConverter} to use for this writer's target. */
	protected final URIConverter uriConverter;

	/** {@link URI} of the target. */
	private final URI targetURI;

	private final StringBuilder builder = new StringBuilder(INITIAL_SIZE);

	/**
	 * Creates a writer for the given target {@link URI}.
	 * 
	 * @param targetURI
	 *            URI of the target {@link URI}.
	 * @param uriConverter
	 *            URI Converter to use for this writer's target.
	 * @param charset
	 *            The charset for our written content.
	 */
	public AcceleoFileWriter(URI targetURI, URIConverter uriConverter, Charset charset) {
		this.targetURI = targetURI;
		this.uriConverter = uriConverter;
		this.charset = charset;
	}

	@Override
	public void append(String content) {
		builder.append(content);
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
	public void close() throws IOException {
		try (final OutputStream output = uriConverter.createOutputStream(targetURI)) {
			output.write(builder.toString().getBytes(charset));
		}
	}

	/**
	 * Gets the {@link StringBuilder}.
	 * 
	 * @return the {@link StringBuilder}
	 */
	protected StringBuilder getBuilder() {
		return builder;
	}

}
