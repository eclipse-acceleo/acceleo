/*******************************************************************************
 * Copyright (c) 2017, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.LinkedHashMap;

import org.eclipse.acceleo.aql.evaluation.writer.AcceleoURIWriter;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.common.EMFPlugin;
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

	public AcceleoWorkspaceURIWriter(URI targetURI, URIConverter uriConverter, Charset charset)
			throws IOException {
		super(targetURI, uriConverter, charset);
	}

	@Override
	public void close() throws IOException {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING || !"java".equals(getTargetURI().fileExtension()) || !uriConverter
				.exists(getTargetURI(), new LinkedHashMap<>())) {
			super.close();
		} else {
			try {
				Class.forName("org.eclipse.emf.codegen.merge.java.JMerger"); //$NON-NLS-1$

				final String mergedContent = mergeURIContent(getTargetURI(), getBuilder(), getCharset());
				OutputStream output = uriConverter.createOutputStream(getTargetURI());
				if (mergedContent != null) {
					OutputStreamWriter writer = new OutputStreamWriter(output, getCharset());
					writer.append(mergedContent);
					writer.close();
				} else {
					super.close();
				}
			} catch (ClassNotFoundException e) {
				AcceleoPlugin.getPlugin().log(new Status(IStatus.WARNING, AcceleoPlugin.PLUGIN_ID,
						"Cannot find org.eclipse.emf.codegen.merge.java.JMerger in the classpath.", e));
				super.close();
			}
		}
	}

	private String mergeURIContent(URI targetURI, StringBuilder stringBuilder, Charset contentCharset)
			throws IOException {
		String jmergeFile = URI.createPlatformPluginURI(
				"org.eclipse.emf.codegen.ecore/templates/emf-merge.xml", false).toString(); //$NON-NLS-1$
		JControlModel model = new JControlModel();
		model.initialize(new ASTFacadeHelper(), jmergeFile);
		if (model.canMerge()) {
			try (final InputStream existingContent = uriConverter.createInputStream(targetURI)) {
				final String source = stringBuilder.toString();

				try {
					JMerger jMerger = new JMerger(model);
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
			// FIXME use delimiter specified by file block
			StringBuilder timestamp = new StringBuilder();
			timestamp.append('\n').append(Calendar.getInstance().getTime().toString()).append('\n');
			timestamp.append(
					"================================================================================"); //$NON-NLS-1$
			timestamp.append('\n');

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
