/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.generation.writers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.internal.evaluation.JMergeUtil;
import org.eclipse.emf.common.EMFPlugin;

/**
 * Wraps a buffered FileWriter for generation with Acceleo. Note that this can also be used to create a Writer
 * that will previously merge the content of an existing file with the new content through JMerge.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public final class AcceleoFileWriter extends AbstractAcceleoWriter {
	/** If using JMerge, this will be used as the charset for the file created with the merged content. */
	private String selectedCharset;

	/**
	 * If this is set to <code>true</code>, closing this buffer will first attempt to merge the previous file
	 * content with the to-be-generated content.
	 */
	private boolean shouldMerge;

	/** Keeps a reference to the target file's absolute path. */
	private final String targetPath;

	/**
	 * Constructs a buffered file writer around the given file. The file will be created with the default
	 * System encoding.
	 * 
	 * @param target
	 *            File in which this writer will append text.
	 * @param appendMode
	 *            Tells us wether the former content of the file should be deleted.
	 * @throws IOException
	 *             Thrown if the target file doesn't exist and cannot be created.
	 */
	public AcceleoFileWriter(File target, boolean appendMode) throws IOException {
		delegate = new BufferedWriter(new FileWriter(target, appendMode));
		targetPath = target.getAbsolutePath();
		shouldMerge = false;
	}

	/**
	 * Constructs a buffered file writer around the given file and tells which encoding should be used to
	 * generate the file.
	 * 
	 * @param target
	 *            File in which this writer will append text.
	 * @param appendMode
	 *            Tells us wether the former content of the file should be deleted.
	 * @param charset
	 *            Encoding that should be used to create the target file.
	 * @throws IOException
	 *             Thrown if the target file doesn't exist and cannot be created.
	 */
	public AcceleoFileWriter(File target, boolean appendMode, String charset) throws IOException {
		final OutputStream fileOutputStream = new FileOutputStream(target, appendMode);
		final OutputStreamWriter fileWriter = new OutputStreamWriter(fileOutputStream, charset);
		delegate = new BufferedWriter(fileWriter);
		targetPath = target.getAbsolutePath();
		shouldMerge = false;
	}

	/**
	 * Constructs a writer that will use JMerge to merge the content of the file existing at path
	 * <em>filePath</em> with its new content. Note that the file will be written with the default System
	 * encoding if using this.
	 * 
	 * @param filePath
	 *            Path of the file this writer will contain the content of.
	 */
	public AcceleoFileWriter(String filePath) {
		delegate = new StringWriter(DEFAULT_BUFFER_SIZE);
		targetPath = filePath;
		shouldMerge = true;
	}

	/**
	 * Constructs a writer that will use JMerge to merge the content of the file existing at path
	 * <em>filePath</em> with its new content.
	 * 
	 * @param filePath
	 *            Path of the file this writer will contain the content of.
	 * @param charset
	 *            Encoding that's to be used to create the file with the merged content.
	 */
	public AcceleoFileWriter(String filePath, String charset) {
		this(filePath);
		selectedCharset = charset;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#close()
	 */
	@Override
	public void close() throws IOException {
		if (!shouldMerge || !EMFPlugin.IS_ECLIPSE_RUNNING) {
			delegate.close();
		} else {
			// The decorated writer is a StringWriter. Closing has no effect on it
			flush();
			try {
				Class.forName("org.eclipse.emf.codegen.merge.java.JMerger"); //$NON-NLS-1$
				final String mergedContent = JMergeUtil.mergeFileContent(new File(targetPath), toString(),
						selectedCharset);
				// Write the new file on disk
				Writer writer = null;
				try {
					if (selectedCharset == null) {
						writer = new BufferedWriter(new FileWriter(new File(targetPath)));
					} else {
						final OutputStream fileOutputStream = new FileOutputStream(new File(targetPath));
						final OutputStreamWriter fileWriter = new OutputStreamWriter(fileOutputStream,
								selectedCharset);
						writer = new BufferedWriter(fileWriter);
					}
					writer.append(mergedContent);
				} finally {
					if (writer != null) {
						writer.close();
					}
				}
			} catch (ClassNotFoundException e) {
				/*
				 * shouldn't happen. This would mean we are in eclipse yet org.eclipse.emf.codegen cannot be
				 * found as a dependency of the generator plugin. This shouldn't happen since it is a
				 * reexported dependency of the engine.
				 */
				AcceleoEnginePlugin.log(e, true);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#getTargetPath()
	 */
	@Override
	public String getTargetPath() {
		return targetPath;
	}
}
