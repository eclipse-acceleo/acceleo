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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.internal.evaluation.JMergeUtil;
import org.eclipse.emf.common.EMFPlugin;

/**
 * This will wrap a String writer for generation with Acceleo. Note that this can also be used to create a
 * Writer that will previously merge the content of an writer with the new content through JMerge.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public class AcceleoStringWriter extends AbstractAcceleoWriter {
	/**
	 * If the user has JMerge tags in the previously generated content, this will be initialized with the old
	 * contents of this writer.
	 */
	private String oldContent;

	/**
	 * If this is set to <code>true</code>, closing this buffer will first attempt to merge the previous
	 * content with the to-be-generated content.
	 */
	private boolean shouldMerge;

	/** Keeps a reference to the target file's absolute path. */
	private final String targetPath;

	/**
	 * Constructs a String Writer reflecting the contents of the file at the given path.
	 * 
	 * @param target
	 *            File which contents will be reflected by this buffer.
	 * @param appendMode
	 *            Tells us whether we should read the former content of the file.
	 * @param merge
	 *            Tells us whether we should merge the new generated content with the existing file content.
	 */
	public AcceleoStringWriter(File target, boolean appendMode, boolean merge) {
		this(target, appendMode, merge, null);
	}

	/**
	 * Constructs a String Writer reflecting the contents of the file at the given path.
	 * 
	 * @param target
	 *            File which contents will be reflected by this buffer.
	 * @param appendMode
	 *            Tells us whether we should read the former content of the file.
	 * @param merge
	 *            Tells us whether we should merge the new generated content with the existing file content.
	 * @param fileCharset
	 *            Charset of the file which content should be reflected by this buffer.
	 */
	public AcceleoStringWriter(File target, boolean appendMode, boolean merge, String fileCharset) {
		delegate = new StringWriter(DEFAULT_BUFFER_SIZE);
		targetPath = target.getPath();
		try {
			if (appendMode && target.exists() && target.canRead()) {
				delegate.append(readOldContent(target, fileCharset));
				delegate.append(LINE_SEPARATOR);
			}
			if (!appendMode && merge) {
				oldContent = readOldContent(target, fileCharset);
				shouldMerge = true;
			}
		} catch (IOException e) {
			// preview mode. Discard this.
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#close()
	 */
	@Override
	public void close() throws IOException {
		// Note that our delegate is a StringWriter. Closing has no effect.
		flush();
		if (shouldMerge && EMFPlugin.IS_ECLIPSE_RUNNING) {
			try {
				Class.forName("org.eclipse.emf.codegen.merge.java.JMerger"); //$NON-NLS-1$
				final String mergedContent = JMergeUtil.mergeContent(new File(targetPath), toString(),
						oldContent);
				delegate = new StringWriter(DEFAULT_BUFFER_SIZE);
				append(mergedContent);
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#reinit()
	 */
	@Override
	public void reinit() {
		oldContent = toString();
		shouldMerge = true;
		delegate = new StringWriter(DEFAULT_BUFFER_SIZE);
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
		StringBuilder fileContent = new StringBuilder();
		try {
			if (charset == null) {
				reader = new BufferedReader(new FileReader(target));
			} else {
				final InputStream fileInputStream = new FileInputStream(target);
				final InputStreamReader fileReader = new InputStreamReader(fileInputStream, charset);
				reader = new BufferedReader(fileReader);
			}
			String line = reader.readLine();
			while (line != null) {
				fileContent.append(line);
				line = reader.readLine();
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return fileContent.toString();
	}
}
