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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter;
import org.eclipse.acceleo.engine.generation.writers.AcceleoStringWriter;

/**
 * This generation strategy is meant to be used for preview purposes only : no files will be written to disk.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public class PreviewStrategy extends AbstractGenerationStrategy {
	/** Default size to be used for new buffers. */
	private static final int DEFAULT_BUFFER_SIZE = 1024;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#createLostFile(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public Map<String, StringWriter> createLostFile(String originalPath, Map<String, String> lostCode) {
		StringBuilder lostContent = new StringBuilder();
		for (final String lostAreaContent : lostCode.values()) {
			lostContent.append(lostAreaContent);
			lostContent.append(LINE_SEPARATOR);
		}
		final File lostFile = new File(originalPath.concat(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
		StringWriter writer = new StringWriter(DEFAULT_BUFFER_SIZE);
		if (lostFile.exists() && lostFile.canRead()) {
			BufferedReader lostFileReader = null;
			try {
				lostFileReader = new BufferedReader(new FileReader(lostFile));
				String line = lostFileReader.readLine();
				while (line != null) {
					writer.append(line);
					line = lostFileReader.readLine();
				}
			} catch (IOException e) {
				// The enclosing if should prevent this, log anyhow.
				final String errorMessage = AcceleoEngineMessages.getString(
						"AcceleoEvaluationContext.LostContent", originalPath, lostContent); //$NON-NLS-1$
				AcceleoEnginePlugin.log(errorMessage, false);
			} finally {
				if (lostFileReader != null) {
					try {
						lostFileReader.close();
					} catch (IOException e) {
						AcceleoEnginePlugin.log(e, true);
					}
				}
			}
		}
		writer.append(LINE_SEPARATOR).append(Calendar.getInstance().getTime().toString()).append(
				LINE_SEPARATOR);
		writer.append("================================================================================"); //$NON-NLS-1$
		writer.append(LINE_SEPARATOR);
		writer.append(lostContent);
		writer.flush();
		Map<String, StringWriter> result = new HashMap<String, StringWriter>(1);
		result.put(lostFile.getPath(), writer);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#createWriterFor(java.io.File,
	 *      java.io.Writer, boolean, boolean)
	 */
	@Override
	public AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous,
			boolean appendMode, boolean hasJMergeTags) throws IOException {
		return createWriterFor(file, previous, appendMode, hasJMergeTags, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#createWriterFor(java.io.File,
	 *      org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter, boolean, boolean,
	 *      java.lang.String)
	 */
	@Override
	public AbstractAcceleoWriter createWriterFor(File file, AbstractAcceleoWriter previous,
			boolean appendMode, boolean hasJMergeTags, String charset) throws IOException {
		final AbstractAcceleoWriter writer;

		if (file.isDirectory()) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationContext.FileNameIsDirectory", file)); //$NON-NLS-1$
		}

		if (appendMode && previous != null) {
			writer = previous;
			writer.append(LINE_SEPARATOR);
		} else if (!appendMode && hasJMergeTags) {
			// previous cannot be null if hasJMergeTags is true
			writer = previous;
			assert writer != null;
			/*
			 * We know the writer is an AcceleoStringWriter, reinitializing it allows it to know it should
			 * merge its content through JMerge when it's next closed.
			 */
			writer.reinit();
		} else {
			if (charset != null) {
				if (Charset.isSupported(charset)) {
					writer = new AcceleoStringWriter(file, appendMode, hasJMergeTags, charset);
				} else {
					final String message = AcceleoEngineMessages.getString(
							"AcceleoGenerationStrategy.UnsupportedCharset", charset); //$NON-NLS-1$
					AcceleoEnginePlugin.log(message, false);
					writer = new AcceleoStringWriter(file, appendMode, hasJMergeTags);
				}
			} else {
				writer = new AcceleoStringWriter(file, appendMode, hasJMergeTags);
			}
		}
		return writer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#preparePreview(java.util.Map)
	 */
	@Override
	public Map<String, String> preparePreview(Map<String, Writer> currentWriters) {
		Map<String, String> preview = super.preparePreview(currentWriters);

		// We know all writers are AcceleoStringWriter, simply flush and return them
		for (Map.Entry<String, Writer> entry : currentWriters.entrySet()) {
			try {
				entry.getValue().flush();
			} catch (IOException e) {
				AcceleoEnginePlugin.log(e, true);
			}
			preview.put(entry.getKey(), entry.getValue().toString());
		}

		return preview;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#willReturnPreview()
	 */
	@Override
	public boolean willReturnPreview() {
		return true;
	}
}
