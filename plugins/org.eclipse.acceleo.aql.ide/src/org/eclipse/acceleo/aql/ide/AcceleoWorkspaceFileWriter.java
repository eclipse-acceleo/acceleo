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
package org.eclipse.acceleo.aql.ide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.LinkedHashMap;

import org.eclipse.acceleo.aql.evaluation.writer.AcceleoFileWriter;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.merge.java.facade.ast.ASTFacadeHelper;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * This file writer will call JMerge on its content before writing the final file.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoWorkspaceFileWriter extends AcceleoFileWriter {
	/** Size to use for our buffers. */
	private static final int BUFFER_SIZE = 8192;

	public AcceleoWorkspaceFileWriter(URI fileURI, URIConverter uriConverter, Charset charset)
			throws IOException {
		super(fileURI, uriConverter, charset);
	}

	@Override
	public void close() throws IOException {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING || !"java".equals(getTargetURI().fileExtension())
				|| !uriConverter.exists(getTargetURI(), new LinkedHashMap<>())) {
			super.close();
		} else {
			try {
				Class.forName("org.eclipse.emf.codegen.merge.java.JMerger"); //$NON-NLS-1$
				// close the temporary file so that we can read its full content
				buffer.close();

				final String mergedContent = mergeFileContent(getTargetURI(), temporaryFilePath, charset);
				OutputStream output = uriConverter.createOutputStream(getTargetURI());
				if (mergedContent != null) {
					OutputStreamWriter writer = new OutputStreamWriter(output, charset);
					writer.append(mergedContent);
					writer.close();
				} else {
					Files.copy(temporaryFilePath, output);
				}
			} catch (ClassNotFoundException e) {
				// JMerge is not in our classpath
				// FIXME log?
				super.close();
			}
		}
	}

	private String mergeFileContent(URI targetURI, Path tempFile, Charset contentCharset) throws IOException {
		String jmergeFile = URI.createPlatformPluginURI(
				"org.eclipse.emf.codegen.ecore/templates/emf-merge.xml", false).toString(); //$NON-NLS-1$
		JControlModel model = new JControlModel();
		model.initialize(new ASTFacadeHelper(), jmergeFile);
		InputStream existingContent = uriConverter.createInputStream(targetURI);
		if (model.canMerge()) {
			InputStream source = Files.newInputStream(tempFile);

			try {
				JMerger jMerger = new JMerger(model);
				jMerger.setSourceCompilationUnit(jMerger.createCompilationUnitForInputStream(source,
						contentCharset.toString()));
				jMerger.setTargetCompilationUnit(jMerger.createCompilationUnitForInputStream(existingContent,
						contentCharset.toString()));
				jMerger.merge();
				return jMerger.getTargetCompilationUnit().getContents();
			} catch (WrappedException e) {
				// The Java file contains errors. We'll copy the old file as a ".lost"
				try {
					createLostFile(targetURI);
				} catch (IOException ee) {
					// FIXME log Couldn't create the lost file.
				}
				Activator.log("Cannot use JMerge on " + getTargetURI().toString(), false);
			}
		} else {
			// FIXME log, couldn't find emf-merge.xml
		}
		return null;
	}

	/**
	 * This will copy the given file as a ".lost" file in the same folder with the same name.
	 * 
	 * @param fileURI
	 *            URI of the file we are to copy as a lost file.
	 * @throws IOException
	 *             Thrown if we couldn't read the source file or create its ".lost" sibling.
	 */
	private void createLostFile(URI fileURI) throws IOException {
		InputStream source = uriConverter.createInputStream(fileURI);
		OutputStream destination = uriConverter.createOutputStream(fileURI.appendFileExtension("lost"));

		// Print a time stamp of the current copy
		// FIXME use delimiter specified by file block
		StringBuilder timestamp = new StringBuilder();
		timestamp.append('\n').append(Calendar.getInstance().getTime().toString()).append('\n');
		timestamp.append("================================================================================"); //$NON-NLS-1$
		timestamp.append('\n');

		destination.write(timestamp.toString().getBytes());

		copy(source, destination);
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
