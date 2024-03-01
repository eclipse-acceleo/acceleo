/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * The preview {@link IWriterFactory}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class PreviewWriterFactory implements IWriterFactory {

	/**
	 * The preview {@link IAcceleoWriter} that populate the {@link PreviewWriter#preview preview map}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class PreviewWriter implements IAcceleoWriter {

		/**
		 * The target {@link URI}.
		 */
		private final URI uri;

		/**
		 * The {@link Charset}.
		 */
		private final Charset charset;

		/**
		 * The {@link StringBuilder} that will contains the generated contents.
		 */
		private final StringBuilder builder = new StringBuilder();

		/**
		 * The preview {@link Map} preview {@link Map} form target {@link URI} to {@link String} contents to
		 * populate.
		 */
		private final Map<URI, String> preview;

		/**
		 * Constructor.
		 * 
		 * @param targetURI
		 *            the target {@link URI}
		 * @param charset
		 *            the {@link Charset}
		 * @param preview
		 *            the preview {@link Map}
		 */
		public PreviewWriter(URI targetURI, Charset charset, Map<URI, String> preview) {
			this.uri = targetURI;
			this.charset = charset;
			this.preview = preview;
		}

		@Override
		public void close() throws IOException {
			preview.put(getTargetURI(), builder.toString());
		}

		@Override
		public URI getTargetURI() {
			return uri;
		}

		@Override
		public Charset getCharset() {
			return charset;
		}

		@Override
		public void append(String content) {
			builder.append(content);
		}

	}

	/**
	 * The preview {@link Map} form target {@link URI} to {@link String} contents.
	 */
	private final Map<URI, String> preview = new LinkedHashMap<>();

	@Override
	public IAcceleoWriter createWriter(OpenModeKind openModeKind, URI uri, URIConverter uriConverter,
			Charset charset, String lineDelimiter) {
		return new PreviewWriter(uri, charset, preview);
	}

	/**
	 * Gets the preview {@link Map} form target {@link URI} to {@link String} contents.
	 * 
	 * @return the preview {@link Map} form target {@link URI} to {@link String} contents
	 */
	public Map<URI, String> getPreview() {
		return preview;
	}

}
