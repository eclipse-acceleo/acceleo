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
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

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
public class AcceleoURIWriter extends AbstractAcceleoWriter {

	/** {@link URIConverter} to use for this writer's target. */
	protected final URIConverter uriConverter;

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
	public AcceleoURIWriter(URI targetURI, URIConverter uriConverter, Charset charset) {
		this(targetURI, uriConverter, charset, null);
	}

	/**
	 * Creates a writer for the given target {@link URI}.
	 * 
	 * @param targetURI
	 *            URI of the target {@link URI}.
	 * @param uriConverter
	 *            URI Converter to use for this writer's target.
	 * @param charset
	 *            The charset for our written content.
	 * @param preview
	 *            the preview {@link Map} or <code>null</code> for no preview
	 */
	public AcceleoURIWriter(URI targetURI, URIConverter uriConverter, Charset charset,
			Map<URI, String> preview) {
		super(targetURI, charset, preview);
		this.uriConverter = uriConverter;
	}

	@Override
	protected OutputStream createOutputStream() throws IOException {
		return uriConverter.createOutputStream(getTargetURI());
	}

}
