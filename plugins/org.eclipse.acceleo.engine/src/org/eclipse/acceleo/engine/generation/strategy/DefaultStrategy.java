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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter;
import org.eclipse.acceleo.engine.generation.writers.AcceleoFileWriter;

/**
 * This generation strategy can be used to generate files on-the-fly. It will create
 * {@link java.io.BufferedWriter}s so that files are written to disk whenever needed. This is the least
 * memory-expansive strategy; however it is not aware of VCSs as it does not check for write access before
 * writing the files. This is not to be used with a project under clearcase for example.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public class DefaultStrategy extends AbstractGenerationStrategy {
	/** This will be populated with the list of tasks currently executing fot the creation of lost files. */
	protected final List<Future<Object>> lostCreationTasks = new ArrayList<Future<Object>>();

	/** This pool will be used for the lost file creators. */
	protected final ExecutorService lostCreatorsPool = Executors.newFixedThreadPool(Runtime.getRuntime()
			.availableProcessors());

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#awaitCompletion()
	 */
	@Override
	public void awaitCompletion() throws InterruptedException {
		for (Future<Object> task : new ArrayList<Future<Object>>(lostCreationTasks)) {
			while (!task.isDone() && !task.isCancelled()) {
				try {
					task.get();
				} catch (ExecutionException e) {
					// LostFileWriters cannot throw exceptions
					AcceleoEnginePlugin.log(e, true);
				}
			}
			lostCreationTasks.remove(task);
		}
		lostCreatorsPool.shutdown();
		super.awaitCompletion();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#createLostFile(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public Map<String, StringWriter> createLostFile(String originalPath, Map<String, String> lostCode) {
		final Callable<Object> fileCreator = new LostFileCreator(originalPath, lostCode);
		lostCreationTasks.add(lostCreatorsPool.submit(fileCreator));
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy#createWriterFor(java.io.File,
	 *      org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter, boolean, boolean)
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
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
						"AcceleoEvaluationContext.FolderCreationError", file.getParentFile())); //$NON-NLS-1$
			}
		}

		if (file.isDirectory()) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationContext.FileNameIsDirectory", file)); //$NON-NLS-1$
		}

		boolean fileExisted = file.exists();

		if (!hasJMergeTags || appendMode) {
			if (charset != null) {
				if (Charset.isSupported(charset)) {
					writer = new AcceleoFileWriter(file, appendMode, charset);
				} else {
					final String message = AcceleoEngineMessages.getString(
							"AcceleoGenerationStrategy.UnsupportedCharset", charset); //$NON-NLS-1$
					AcceleoEnginePlugin.log(message, false);
					writer = new AcceleoFileWriter(file, appendMode);
				}
			} else {
				writer = new AcceleoFileWriter(file, appendMode);
			}
			if (appendMode && fileExisted) {
				writer.append(LINE_SEPARATOR);
			}
		} else {
			if (charset != null) {
				if (Charset.isSupported(charset)) {
					writer = new AcceleoFileWriter(file.getPath(), charset);
				} else {
					final String message = AcceleoEngineMessages.getString(
							"AcceleoGenerationStrategy.UnsupportedCharset", charset); //$NON-NLS-1$
					AcceleoEnginePlugin.log(message, false);
					writer = new AcceleoFileWriter(file.getPath());
				}
			} else {
				writer = new AcceleoFileWriter(file.getPath());
			}
		}
		return writer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#dispose()
	 */
	@Override
	public void dispose() {
		try {
			awaitCompletion();
		} catch (InterruptedException e) {
			// discard : we're disposing of everything
		}
		lostCreationTasks.clear();
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.strategy.AbstractGenerationStrategy#flushWriter(java.lang.String,
	 *      java.io.Writer)
	 */
	@Override
	public void flushWriter(String filePath, Writer writer) throws IOException {
		writer.close();
	}

	/**
	 * This will be used to create lost files.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 * @since 3.0
	 */
	protected final class LostFileCreator implements Callable<Object> {
		/** Lost protected areas. */
		private final Map<String, String> lostAreas;

		/** Path to the file which protected areas have been lost. */
		private final String originalPath;

		/**
		 * Instantiate a writer given the path to the original file (will be suffixed by &quot;.lost&quot;)
		 * and a map containing the lost protected areas.
		 * 
		 * @param originalPath
		 *            Path to the file in which protected areas have been lost.
		 * @param lostAreas
		 *            Map containing the lost protected areas of this file.
		 */
		LostFileCreator(String originalPath, Map<String, String> lostAreas) {
			this.originalPath = originalPath;
			this.lostAreas = lostAreas;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public Object call() {
			StringBuilder lostContent = new StringBuilder();
			for (final String lostAreaContent : lostAreas.values()) {
				lostContent.append(lostAreaContent);
				lostContent.append(LINE_SEPARATOR);
			}
			Writer writer = null;
			try {
				final File lostFile = new File(originalPath
						.concat(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
				writer = new BufferedWriter(new FileWriter(lostFile, true));
				writer.append(LINE_SEPARATOR).append(Calendar.getInstance().getTime().toString()).append(
						LINE_SEPARATOR);
				writer.append("================================================================================"); //$NON-NLS-1$
				writer.append(LINE_SEPARATOR);
				writer.append(lostContent);
			} catch (final IOException e) {
				final String errorMessage = AcceleoEngineMessages.getString(
						"AcceleoEvaluationContext.LostContent", originalPath, lostContent); //$NON-NLS-1$
				AcceleoEnginePlugin.log(errorMessage, false);
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						AcceleoEnginePlugin.log(e, false);
					}
				}
			}
			// This has no explicit result. Only used to await termination
			return null;
		}
	}
}
