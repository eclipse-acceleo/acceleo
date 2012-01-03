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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.internal.evaluation.JMergeUtil;
import org.eclipse.emf.common.EMFPlugin;

/**
 * This will wrap String writers for generation through Acceleo. It will be written to disk only at
 * {@link #close()} time so that the strategy can ask the workspace for confirmation beforehand.
 * <p>
 * <b>Note</b> That this <u>cannot</u> be used in standalone mode.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public class AcceleoWorkspaceFileWriter extends AbstractAcceleoWriter {
	/** Keeps a reference to the target file's absolute path. */
	private final String targetPath;

	/** If using JMerge, this will be used as the charset for the file created with the merged content. */
	private String selectedCharset;

	/**
	 * If this is set to <code>true</code>, closing this buffer will first attempt to merge the previous file
	 * content with the to-be-generated content.
	 */
	private boolean shouldMerge;

	/**
	 * Constructs a writer that will use JMerge to merge the content of the file existing at path
	 * <em>filePath</em> with its new content. Note that the file will be written with the default System
	 * encoding if using this.
	 * 
	 * @param target
	 *            File in which this writer will append text.
	 * @param appendMode
	 *            Tells us whether the former content of the file should be deleted.
	 * @param merge
	 *            Tells us whether we should merge the new generated content with the existing file content.
	 * @throws IOException
	 *             Thrown if the target cannot be read.
	 */
	public AcceleoWorkspaceFileWriter(File target, boolean appendMode, boolean merge) throws IOException {
		this(target, appendMode, merge, null);
	}

	/**
	 * Constructs a writer that will use JMerge to merge the content of the file existing at path
	 * <em>filePath</em> with its new content.
	 * 
	 * @param target
	 *            File in which this writer will append text.
	 * @param appendMode
	 *            Tells us whether the former content of the file should be deleted.
	 * @param merge
	 *            Tells us whether we should merge the new generated content with the existing file content.
	 * @param charset
	 *            Encoding that's to be used to create the file with the merged content.
	 * @throws IOException
	 *             Thrown if the target cannot be read.
	 */
	public AcceleoWorkspaceFileWriter(File target, boolean appendMode, boolean merge, String charset)
			throws IOException {
		delegate = new StringWriter(DEFAULT_BUFFER_SIZE);
		if (appendMode && target.exists() && target.canRead()) {
			delegate.append(readOldContent(target, charset));
			delegate.append(LINE_SEPARATOR);
		}
		targetPath = target.getPath();
		shouldMerge = !appendMode && merge;
		selectedCharset = charset;
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

	/**
	 * Reads the content of the given file given its encoding.
	 * 
	 * @param target
	 *            File that is to be read.
	 * @param charset
	 *            Charset of the file. Can be <code>null</code>.
	 * @return The old content of the file.
	 * @throws IOException
	 *             Will be thrown if the file cannot be read.
	 */
	private String readOldContent(File target, String charset) throws IOException {
		BufferedReader reader = null;
		StringBuilder oldContent = new StringBuilder();
		try {
			if (charset == null) {
				reader = new BufferedReader(new FileReader(target));
			} else {
				final InputStream fileInputStream = new FileInputStream(target);
				final InputStreamReader fileReader = new InputStreamReader(fileInputStream, charset);
				reader = new BufferedReader(fileReader);
			}
			char[] buf = new char[DEFAULT_BUFFER_SIZE];
			int length = reader.read(buf);
			while (length > 0) {
				oldContent.append(buf, 0, length);
				length = reader.read(buf);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return oldContent.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#close()
	 */
	@Override
	public void close() throws IOException {
		// Note that the delegated writer is a StringWriter. Closing has no effect
		flush();
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
			if (!shouldMerge || !EMFPlugin.IS_ECLIPSE_RUNNING) {
				writer.append(toString());
			} else {
				try {
					Class.forName("org.eclipse.emf.codegen.merge.java.JMerger"); //$NON-NLS-1$
					final String mergedContent = JMergeUtil.mergeFileContent(new File(targetPath),
							toString(), selectedCharset);
					writer.append(mergedContent);
				} catch (ClassNotFoundException e) {
					/*
					 * shouldn't happen. This would mean we are in eclipse yet org.eclipse.emf.codegen cannot
					 * be found as a dependency of the generator plugin. This shouldn't happen since it is a
					 * reexported dependency of the engine.
					 */
					AcceleoEnginePlugin.log(e, true);
				}
			}
		} finally {
			// Write the new file on disk
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * This can be used to check wether the old file content has been changed as regards to the new merged
	 * content.
	 * 
	 * @return <code>true</code> if the content has changed somehow, <code>false</code> otherwise.
	 * @throws IOException
	 *             Will be thrown if the original file cannot be read.
	 */
	public boolean hasChanged() throws IOException {
		boolean hasChanged = false;
		final File target = new File(targetPath);
		if (shouldMerge && EMFPlugin.IS_ECLIPSE_RUNNING) {
			try {
				if (target.exists() && target.canRead()) {
					Class.forName("org.eclipse.emf.codegen.merge.java.JMerger"); //$NON-NLS-1$
					final String mergedContent = JMergeUtil.mergeFileContent(target, toString(),
							selectedCharset);
					final String oldContent = readOldContent(target, selectedCharset);
					if (!mergedContent.equals(oldContent)) {
						hasChanged = true;
					}
				} else {
					hasChanged = true;
				}
			} catch (ClassNotFoundException e) {
				/*
				 * shouldn't happen. This would mean we are in eclipse yet org.eclipse.emf.codegen cannot be
				 * found as a dependency of the generator plugin. This shouldn't happen since it is a
				 * reexported dependency of the engine.
				 */
				AcceleoEnginePlugin.log(e, true);
			}
		} else {
			if (target.exists() && target.canRead()) {
				final String oldContent = readOldContent(target, selectedCharset);
				final String newContent = toString();
				if (!newContent.equals(oldContent)) {
					hasChanged = true;
				}
			} else {
				hasChanged = true;
			}
		}
		return hasChanged;
	}
}
