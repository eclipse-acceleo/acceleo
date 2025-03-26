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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.eclipse.emf.common.util.URI;

/**
 * The default File writer used by Acceleo.
 * <p>
 * Data will be written to the disk without any verification about the workspace or VCS. Do not use with
 * pessimistic locking VCS or if JMerge is needed.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoFileWriter extends AbstractAcceleoWriter {

	/** The target {@link File}. */
	private final File target;

	/** Tells if the {@link FileOutputStream} should be opened in append mode. */
	private final boolean append;

	/**
	 * Creates a writer for the given target {@link URI}.
	 * 
	 * @param target
	 *            the target {@link File}.
	 * @param uriConverter
	 *            URI Converter to use for this writer's target.
	 * @param charset
	 *            The charset for our written content.
	 * @param append
	 *            <code>true</code> if the {@link FileOutputStream} should be opened in append mode,
	 *            <code>false</code> otherwise
	 */
	public AcceleoFileWriter(File target, Charset charset, boolean append) {
		this(target, charset, append, null);
	}

	/**
	 * Creates a writer for the given target {@link URI}.
	 * 
	 * @param target
	 *            the target {@link File}.
	 * @param uriConverter
	 *            URI Converter to use for this writer's target.
	 * @param charset
	 *            The charset for our written content.
	 * @param append
	 *            <code>true</code> if the {@link FileOutputStream} should be opened in append mode,
	 *            <code>false</code> otherwise
	 * @param preview
	 *            the preview {@link Map} or <code>null</code> for no preview
	 */
	public AcceleoFileWriter(File target, Charset charset, boolean append, Map<URI, String> preview) {
		super(URI.createFileURI(target.getAbsolutePath()), charset, preview);
		this.target = target;
		this.append = append;
	}

	@Override
	protected OutputStream createOutputStream() throws IOException {
		return new FileOutputStream(target, append);
	}

}
