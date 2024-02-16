/*******************************************************************************
 * Copyright (c) 2008, 2024 Obeo.
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
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.internal.AcceleoMessages;
import org.eclipse.emf.common.util.URI;

/**
 * This will determine how Acceleo will write files to disk.
 * <p>
 * Take note that the engine will <b>not</b> do any closing of the writers for you. That is the strategy's
 * implementation job.
 * </p>
 * <p>
 * Clients may override one of the default implementations instead of implementing the whole interface.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public interface IAcceleoGenerationStrategy {

	/** The start of user code String. Marks the start of a protected area. */
	String USER_CODE_START = AcceleoMessages.getString("usercode.start"); //$NON-NLS-1$

	/** The end of user code String. Marks the end of a protected area. */
	String USER_CODE_END = AcceleoMessages.getString("usercode.end"); //$NON-NLS-1$

	/**
	 * This will be called by the engine when it finishes evaluating a given [file/] block.
	 * 
	 * @param writer
	 *            Writer that is to be flushed.
	 * @throws IOException
	 *             Can be throw if the flushing fails anyhow.
	 */
	void closeWriter(IAcceleoWriter writer) throws IOException;

	/**
	 * This will be called by the engine when it encounters a protected area. This should consume (return and
	 * remove) the content of the specified protected area if any, <code>null</code> if none. An empty String
	 * will be considered to be the protected area's contents.
	 * 
	 * @param uri
	 *            URI from which user code has been lost by this generation.
	 * @param protectedAreaID
	 *            ID of the protected area which content we're seeking.
	 * @return Content of that protected area if any, <code>null</code> if none.
	 */
	String consumeProtectedAreaContent(URI uri, String protectedAreaID);

	/**
	 * This will be called by the engine when it ends the generation of the given {@link URI}. This should
	 * consume (return and remove) all the remaining content. An empty String will be considered to be the
	 * protected area's contents.
	 * 
	 * @param uri
	 *            the ended generation URI.
	 * @return a mapping from remaining protected area ID to its content
	 */
	Map<String, List<String>> consumeAllProtectedAreas(URI uri);

	/**
	 * Creates an {@link IAcceleoWriter} for the log content for the given destination {@link URI}.
	 * 
	 * @param uri
	 *            the log {@link URI}
	 * @param charset
	 *            the {@link Charset} of the stream that's to be saved
	 * @param lineDelimiter
	 *            the line delimiter that was demanded by the user for this writer.
	 * @throws IOException
	 *             if the writer can't be created
	 * @return the created writer. It can't be <code>null</code> use {@link NullWriter} instead.
	 */
	IAcceleoWriter createWriterForLog(URI uri, Charset charset, String lineDelimiter) throws IOException;

	/**
	 * Creates an {@link IAcceleoWriter} for the lost content for the given generated {@link URI} with the
	 * given protected area ID.
	 * 
	 * @param uri
	 *            the generated {@link URI}
	 * @param protectedAreaID
	 *            the lost protected area ID.
	 * @param charset
	 *            the {@link Charset} of the stream that's to be saved
	 * @param lineDelimiter
	 *            the line delimiter that was demanded by the user for this writer.
	 * @throws IOException
	 *             if the writer can't be created
	 * @return the created writer. It can't be <code>null</code> use {@link NullWriter} instead.
	 */
	IAcceleoWriter createWriterForLostContent(URI uri, String protectedAreaID, Charset charset,
			String lineDelimiter) throws IOException;

	/**
	 * This will be called internally by the engine whenever a [file/] block is encountered so that the
	 * strategy can decide which writer is to be created for that file's generation.
	 * <p>
	 * The {@code openMode} will be one of:
	 * <table>
	 * <tr>
	 * <td>{@link OpenModeKind#APPEND}</td>
	 * <td>The generated data should be appended at the end of this file (File should be created if it doesn't
	 * exist).</td>
	 * </tr>
	 * <tr>
	 * <td>{@link OpenModeKind#OVERWRITE}</td>
	 * <td>The file will be overwritten if it exists, and all data will be lost (save for the zones marked as
	 * [protected/] blocks). It will be created if it didn't previously exist.</td>
	 * </tr>
	 * <tr>
	 * <td>{@link OpenModeKind#CREATE}</td>
	 * <td>The file will be created if it doesn't exist, and left untouched if it does.</td>
	 * </tr>
	 * </table>
	 * </p>
	 * 
	 * @param uri
	 *            URI of the file that is to be generated or overriden.
	 * @param openMode
	 *            The kind of generation specified by this file block.
	 * @param charset
	 *            The {@link Charset} of the stream that's to be saved.
	 * @param lineDelimiter
	 *            the line delimiter that was demanded by the user for this writer.
	 * @return The created writer. It can't be <code>null</code> use {@link NullWriter} instead.
	 * @throws IOException
	 *             if the writer can't be created
	 */
	IAcceleoWriter createWriterFor(URI uri, OpenModeKind openMode, Charset charset, String lineDelimiter)
			throws IOException;

	/**
	 * This will be called by the engine when the evaluation starts. The strategy can use this chance to run
	 * initialization tasks or start long-running background jobs.
	 * 
	 * @param destination
	 *            the destination {@link URI}
	 */
	public void start(URI destination);

	/**
	 * This will be called by the engine when the evaluation ends or is cancelled. The strategy can use this
	 * chance to run cleanup tasks or terminate long-running background jobs.
	 */
	void terminate();
}
