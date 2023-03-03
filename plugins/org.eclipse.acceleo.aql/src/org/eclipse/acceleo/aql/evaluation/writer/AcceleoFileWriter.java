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
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * The default file writer used by Acceleo.
 * <p>
 * It will write generated text as it comes to a temporary file, then, on close, transfer the content to the
 * final destination.
 * </p>
 * <p>
 * Data will be written to the disk without any verification about the workspace or VCS. Do not use with
 * pessimistic locking VCS or if JMerge is needed.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoFileWriter implements IAcceleoWriter {
	/** Path towards the temporary file we're using as a buffer. */
	protected final Path temporaryFilePath;

	/** Writer opened for the temporary file we're using as a buffer for the "final" content. */
	protected final Writer buffer;

	/** The charset for our written content. */
	protected final Charset charset;

	/** URI Converter to use for this writer's target. */
	protected final URIConverter uriConverter;

	/** URI of the target file. */
	private final URI fileURI;

	/**
	 * Creates a writer for the given file.
	 * 
	 * @param fileURI
	 *            URI of the target file.
	 * @param uriConverter
	 *            URI Converter to use for this writer's target.
	 * @param charset
	 *            The charset for our written content.
	 * @throws IOException
	 *             thrown if we cannot open a writer to a temporary file as a buffer.
	 */
	public AcceleoFileWriter(URI fileURI, URIConverter uriConverter, Charset charset) throws IOException {
		this.fileURI = fileURI;
		this.uriConverter = uriConverter;
		this.charset = charset;

		// FIXME use memory instead of temp file and copy
		// With the current implementation of AcceleoEvaluator it doesn't make sens to buffer anyway
		temporaryFilePath = Files.createTempFile("acceleo_", "_generated");
		// Opens a file for writing, truncating existing if any.
		buffer = Files.newBufferedWriter(temporaryFilePath, charset);
	}

	@Override
	public URI getTargetURI() {
		return fileURI;
	}

	@Override
	public Charset getCharset() {
		return charset;
	}

	@Override
	public void close() throws IOException {
		buffer.close();

		OutputStream output = uriConverter.createOutputStream(fileURI);
		Files.copy(temporaryFilePath, output);
		output.close();
	}

	@Override
	public Appendable append(CharSequence csq) throws IOException {
		return buffer.append(csq);
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws IOException {
		return buffer.append(csq, start, end);
	}

	@Override
	public Appendable append(char c) throws IOException {
		return buffer.append(c);
	}
}
