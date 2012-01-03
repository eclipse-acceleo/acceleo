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
package org.eclipse.acceleo.engine.generation.strategy;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter;

/**
 * Subclasses of this can be used to determine how the files should be written to disk.
 * <p>
 * Override {@link #flushWriters(Map)} if you need to handle all of the generated files at once (for example,
 * you need to prompt the user to accept the overriding of a file and want to display a single popup for all
 * files instead of one popup for each file). Override {@link #flushWriter(String, Writer)} otherwise.
 * </p>
 * <p>
 * Take note that the engine will <b>not</b> do any closing of the writers for you. That is the strategy's
 * implementation job. Also, even if a writer has been closed by your implementation of
 * {@link #flushWriter(String, Writer)}, it will still be present in the map handed over to
 * {@link #flushWriters(Map)} later on.
 * </p>
 * <p>
 * Clients may override {@link AbstractGenerationStrategy} or one of the default implementations instead of
 * implementing the whole interface.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public interface IAcceleoGenerationStrategy {
	/** This will hold the system specific line separator ("\n" for unix, "\r\n" for dos, "\r" for mac, ...). */
	String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	/**
	 * This will be called internally by the engine if the generation is cancelled or when it ends. If this
	 * strategy is multi-threaded, this can be used to force the engine to wait for all threads' completion.
	 * 
	 * @throws InterruptedException
	 *             This can be thrown if this thread has been stopped somehow. It will be caught by Acceleo
	 *             and logged before it finishes cleaning up.
	 */
	void awaitCompletion() throws InterruptedException;

	/**
	 * This will be called slightly before {@link #flushWriter(String, Writer)} if code has been lost from the
	 * file that is to be saved. <b>Take note</b> that this will <u>not</u> be called if no code has been lost
	 * from the original file.
	 * 
	 * @param originalPath
	 *            Path of the file from which user code has been lost by this generation.
	 * @param lostCode
	 *            This will contain the lost code blocks from this writer in the form area_key=area_content.
	 * @return The writer opened to create the lost file along with the filePath chosen for this file. This
	 *         only serves for preview purposes and can be ignored if you do not want this lost file to appear
	 *         in the preview returned by the engine.
	 */
	Map<String, StringWriter> createLostFile(String originalPath, Map<String, String> lostCode);

	/**
	 * This will be called slightly before {@link #flushWriters(Map)} if user code has been lost from any of
	 * the generated files.<b>Take note</b> that this will <u>not</u> be called if no code has been lost from
	 * the original files.
	 * <p>
	 * As a side note, remember that even if you handle lost code with {@link #createLostFile(String, Map)}
	 * before, their content will still be present in <code>lostCode</code>. This method is only meant to be
	 * an handler for <u>all</u> lost code at once and should probably not be implemented if
	 * {@link #createLostFile(String, Map)} is.
	 * </p>
	 * 
	 * @param lostCode
	 *            This will contain the lost code blocks from all writers in the form
	 *            original_file_path=(area_key=area_content).
	 * @return The writers opened to create lost files. This only serves for preview purposes and can be
	 *         ignored if you do not want these files to appear in the preview returned by the engine.
	 */
	Map<String, StringWriter> createLostFiles(Map<String, Map<String, String>> lostCode);

	/**
	 * This will be called internally by the engine whenever a [file/] block is encountered so that the
	 * strategy can decide which writer is to be created for this file's generation.
	 * <p>
	 * For example, whatever the states of <code>appendMode</code> and <code>hasJMergeTags</code>, the writer
	 * for the default {@link PreviewStrategy} will always be a StringWriter.
	 * </p>
	 * 
	 * @param file
	 *            File that is to be generated or overriden.
	 * @param previous
	 *            If a writer has already been generated for the same <code>file</code>, it will be given
	 *            here. Will be <code>null</code> otherwise.
	 * @param appendMode
	 *            This will be <code>true</code> if we need a Writer to be opened in appendMode,
	 *            <code>false</code> otherwise. <b>Note</b> that this might be <code>true</code> even if the
	 *            target file doesn't exist yet.
	 * @param hasJMergeTags
	 *            This will be <code>true</code> if the target file contains &quot;@generated&quot; tags. This
	 *            will never be <code>true</code> if the target file doesn't exist yet.
	 * @return The created writer.
	 * @throws IOException
	 *             Can be thrown if the file is invalid according to this strategy. Will never be thrown for
	 *             the {@link PreviewStrategy} for example.
	 */
	AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous, boolean appendMode,
			boolean hasJMergeTags) throws IOException;

	/**
	 * This will be called internally by the engine whenever a [file/] block is encountered so that the
	 * strategy can decide which writer is to be created for this file's generation.
	 * <p>
	 * For example, whatever the states of <code>appendMode</code> and <code>hasJMergeTags</code>, the writer
	 * for the default {@link PreviewStrategy} will always be a StringWriter.
	 * </p>
	 * 
	 * @param file
	 *            File that is to be generated or overriden.
	 * @param previous
	 *            If a writer has already been generated for the same <code>file</code>, it will be given
	 *            here. Will be <code>null</code> otherwise.
	 * @param appendMode
	 *            This will be <code>true</code> if we need a Writer to be opened in appendMode,
	 *            <code>false</code> otherwise. <b>Note</b> that this might be <code>true</code> even if the
	 *            target file doesn't exist yet.
	 * @param hasJMergeTags
	 *            This will be <code>true</code> if the target file contains &quot;@generated&quot; tags. This
	 *            will never be <code>true</code> if the target file doesn't exist yet.
	 * @param charset
	 *            Charset of the stream that's to be saved.
	 * @return The created writer.
	 * @throws IOException
	 *             Can be thrown if the file is invalid according to this strategy. Will never be thrown for
	 *             the {@link PreviewStrategy} for example.
	 */
	AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous, boolean appendMode,
			boolean hasJMergeTags, String charset) throws IOException;

	/**
	 * This will be called internally by the engine when the evaluation ends.
	 */
	void dispose();

	/**
	 * This will be called by the engine when it finishes evaluating a given [file/] block. Use this instead
	 * of {@link #flushWriters(Map)} if you don't need a "all at once" handling of the generated files.
	 * 
	 * @param filePath
	 *            Path of the file this writer had been created for.
	 * @param writer
	 *            Writer that is to be flushed.
	 * @throws IOException
	 *             Can be throw if the flushing fails anyhow.
	 */
	void flushWriter(String filePath, Writer writer) throws IOException;

	/**
	 * This will be called by the engine at the end of a generation to give clients a chance at handling all
	 * files together. For example, this might be useful for strategies that need to validate accesses to
	 * files to validate only once for all files.
	 * 
	 * @param currentWriters
	 *            This will hold the whole set of writers mapped to their corresponding file path. Take note
	 *            that if the writer is a FileWriter or a BufferedWriter, it may already have been written to
	 *            disk.
	 * @throws IOException
	 *             Can be throw if the flushing fails anyhow.
	 */
	void flushWriters(Map<String, Writer> currentWriters) throws IOException;

	/**
	 * This will be called to leave the strategy a chance at removing closed or useless writers from the
	 * preview.
	 * 
	 * @param currentWriters
	 *            Writers that have been used throughout the generation.
	 * @return The actual preview that should be returned by the engine.
	 */
	Map<String, String> preparePreview(Map<String, Writer> currentWriters);

	/**
	 * This will be called by the engine to determine if a preview of the result should be returned at the end
	 * of a generation.
	 * 
	 * @return <code>true</code> if the preview should be returned, <code>false</code> otherwise.
	 */
	boolean willReturnPreview();
}
