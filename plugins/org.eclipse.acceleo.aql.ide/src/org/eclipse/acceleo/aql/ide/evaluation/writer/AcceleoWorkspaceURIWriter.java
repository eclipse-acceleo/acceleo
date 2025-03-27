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
package org.eclipse.acceleo.aql.ide.evaluation.writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

import org.eclipse.acceleo.aql.evaluation.writer.AcceleoURIWriter;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * This {@link IAcceleoWriter} will call JMerge on its content before writing to the target {@link URI}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoWorkspaceURIWriter extends AcceleoURIWriter {

	/** Size to use for our buffers. */
	private static final int BUFFER_SIZE = 8192;

	/** Used to call URIConverter methods with no options. */
	private static final Map<String, Object> EMPTY_OPTION_MAP = Collections.emptyMap();

	/**
	 * Creates a writer for the given target {@link URI}.
	 * 
	 * @param targetURI
	 *            URI of the target {@link URI}.
	 * @param uriConverter
	 *            URI Converter to use for this writer's target.
	 * @param charset
	 *            The charset for our written content.
	 * @param lineDelimiter
	 *            the line delimiter
	 * @param preview
	 *            the preview {@link Map} or <code>null</code> for no preview
	 */
	public AcceleoWorkspaceURIWriter(URI targetURI, URIConverter uriConverter, Charset charset,
			String lineDelimiter, Map<URI, String> preview) {
		super(targetURI, uriConverter, charset, lineDelimiter, preview);
	}

	@Override
	protected String getContent() throws IOException {
		final String res;

		if (uriConverter.exists(getTargetURI(), EMPTY_OPTION_MAP)) {
			res = mergeURIContent(getTargetURI(), getBuilder(), getCharset());
			;
		} else {
			res = super.getContent();
		}

		return res;
	}

	private String mergeURIContent(URI targetURI, StringBuilder stringBuilder, Charset contentCharset)
			throws IOException {
		final String jmergeFile = URI.createPlatformPluginURI(
				"org.eclipse.emf.codegen.ecore/templates/emf-merge.xml", false).toString(); //$NON-NLS-1$
		final JControlModel model = new JControlModel();
		model.initialize(new ASTFacadeHelper(), jmergeFile);
		if (model.canMerge()) {
			try (final InputStream existingContent = uriConverter.createInputStream(targetURI)) {
				final String source = stringBuilder.toString();

				try {
					final JMerger jMerger = new JMerger(model);
					jMerger.setSourceCompilationUnit(jMerger.createCompilationUnitForContents(source));
					jMerger.setTargetCompilationUnit(jMerger.createCompilationUnitForInputStream(
							existingContent, contentCharset.toString()));
					jMerger.merge();
					return jMerger.getTargetCompilationUnit().getContents();
				} catch (WrappedException e) {
					// The Java file contains errors. We'll copy the old file as a ".lost"
					final URI lostURI = targetURI.appendFileExtension("lost");
					try {
						createLostURI(targetURI, lostURI);
					} catch (IOException ee) {
						AcceleoPlugin.getPlugin().log(new Status(IStatus.WARNING, AcceleoPlugin.PLUGIN_ID,
								"Cannot createlost file " + lostURI, e));
					}
					AcceleoPlugin.getPlugin().log(new Status(IStatus.WARNING, AcceleoPlugin.PLUGIN_ID,
							"Cannot use JMerge on " + getTargetURI().toString(), e));
				}
			}
		} else {
			AcceleoPlugin.getPlugin().log(new Status(IStatus.WARNING, AcceleoPlugin.PLUGIN_ID,
					"Cannot find JMerge configuration " + jmergeFile));
		}
		return null;
	}

	/**
	 * This will copy lost content from the given target {@link URI} to the given lost {@link URI}.
	 * 
	 * @param targetURI
	 *            the target {@link URI}
	 * @param lostURI
	 *            {@link URI} of the lost file.
	 * @throws IOException
	 *             Thrown if we couldn't read the source file or create its ".lost" sibling.
	 */
	private void createLostURI(URI targetURI, URI lostURI) throws IOException {
		try (InputStream source = uriConverter.createInputStream(targetURI);
				OutputStream destination = uriConverter.createOutputStream(lostURI)) {

			// Print a time stamp of the current copy
			StringBuilder timestamp = new StringBuilder();
			timestamp.append(getLineDelimiter()).append(Calendar.getInstance().getTime().toString()).append(
					getLineDelimiter());
			timestamp.append(
					"================================================================================"); //$NON-NLS-1$
			timestamp.append(getLineDelimiter());

			destination.write(timestamp.toString().getBytes(getCharset()));

			copy(source, destination);
		}
	}

	/**
	 * Copy/pasted from Files#copy(InputStream, OutputStream) since that one is private for some reason.
	 * 
	 * @param source
	 *            Stream to copy bytes from.
	 * @param sink
	 *            Stream to copy bytes to.
	 * @throws IOException
	 *             if we can't read from {@code source} or write into {@code sink}.
	 */
	private static void copy(InputStream source, OutputStream sink) throws IOException {
		byte[] buf = new byte[BUFFER_SIZE];
		int n;
		while ((n = source.read(buf)) > 0) {
			sink.write(buf, 0, n);
		}
	}
}
