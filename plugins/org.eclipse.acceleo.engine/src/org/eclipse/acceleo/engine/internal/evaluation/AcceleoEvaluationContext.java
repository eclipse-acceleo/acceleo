/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.evaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.EObject;

/**
 * This will hold all necessary variables for the evaluation of an Acceleo module.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoEvaluationContext {
	/** This will hold the system specific line separator ("\n" for unix, "\r\n" for dos, "\r" for mac, ...). */
	protected static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	/** Default size to be used for new buffers. */
	private static final int DEFAULT_BUFFER_SIZE = 1024;

	/** This is the tag we will look for to determine if a file has to be passed through JMerge. */
	private static final String JMERGE_TAG = "@generated"; //$NON-NLS-1$

	/** Holds the generation preview in the form of mappings filePath => fileContent. */
	protected final Map<String, Writer> generationPreview = new HashMap<String, Writer>();

	/** References the file which is to be used as the root for all generated files. */
	private final File generationRoot;

	/** The state of his boolean will be changed while reading files prior to generation. */
	private boolean hasJMergeTag;

	/**
	 * This will hold the list of all listeners registered for notification on text generation from this
	 * engine.
	 */
	private final List<IAcceleoTextGenerationListener> listeners = new ArrayList<IAcceleoTextGenerationListener>(
			3);

	/** This will be initialized with this generation's progress monitor. */
	private final Monitor progressMonitor;

	/** The current generation strategy. */
	private final IAcceleoGenerationStrategy strategy;

	/** This will keep a reference to all user code blocks of a given File. */
	private final Map<Writer, Map<String, String>> userCodeBlocks = new HashMap<Writer, Map<String, String>>();

	/** This will hold the buffer stack. */
	private final LinkedList<Writer> writers = new LinkedList<Writer>();

	/**
	 * Instantiates an evaluation context given the root of the to-be-generated files.
	 * 
	 * @param root
	 *            Root of all files that will be generated.
	 * @param listeners
	 *            The list of all listeners that are to be notified for text generation from this context.
	 * @param generationStrategy
	 *            The generation strategy that's to be used by this context.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation.
	 */
	public AcceleoEvaluationContext(File root, List<IAcceleoTextGenerationListener> listeners,
			IAcceleoGenerationStrategy generationStrategy, Monitor monitor) {
		generationRoot = root;
		strategy = generationStrategy;
		this.listeners.addAll(listeners);
		if (monitor != null) {
			progressMonitor = monitor;
		} else {
			progressMonitor = new BasicMonitor();
		}
	}

	/**
	 * Appends the given string to the last buffer of the context stack. This will notify all text generation
	 * listeners along the way.
	 * 
	 * @param string
	 *            String that is to be appended to the current buffer.
	 * @param sourceBlock
	 *            The block for which this text has been generated.
	 * @param source
	 *            The Object for which was generated this text.
	 * @param fireEvent
	 *            Tells us whether we should fire generation events.
	 * @throws AcceleoEvaluationException
	 *             Thrown if we cannot append to the current buffer.
	 */
	public void append(String string, Block sourceBlock, EObject source, boolean fireEvent)
			throws AcceleoEvaluationException {
		try {
			final Writer currentWriter = writers.getLast();
			currentWriter.append(string);
			if (fireEvent) {
				fireTextGenerated(new AcceleoTextGenerationEvent(string, sourceBlock, source));
			}
		} catch (final IOException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationContext.AppendError"), e); //$NON-NLS-1$
		}
	}

	/**
	 * Allows clients to await for the lost file creation to end.
	 * 
	 * @throws InterruptedException
	 *             This will be thrown if the lost files creation is interrupted somehow.
	 */
	public void awaitCompletion() throws InterruptedException {
		strategy.awaitCompletion();
	}

	/**
	 * Closes the last writer of the stack and returns its result if it was a StringWriter. The empty String
	 * will be returned for FileWriters.
	 * 
	 * @return Result held by the last writer of the stack.
	 * @throws AcceleoEvaluationException
	 *             This will be thrown if the last writer of the stack cannot be flushed and closed.
	 */
	public String closeContext() throws AcceleoEvaluationException {
		final Writer last = writers.removeLast();
		final String result;
		try {
			if (last instanceof AbstractAcceleoWriter) {
				final String filePath = ((AbstractAcceleoWriter)last).getTargetPath();
				final Map<String, String> lostCode = userCodeBlocks.get(last);
				if (lostCode.size() > 0) {
					Map<String, StringWriter> lostFiles = strategy.createLostFile(filePath, lostCode);
					if (lostFiles != null) {
						for (Map.Entry<String, StringWriter> lostFile : lostFiles.entrySet()) {
							generationPreview.put(lostFile.getKey(), lostFile.getValue());
						}
					}
				}
				strategy.flushWriter(filePath, last);
				result = ""; //$NON-NLS-1$
			} else if (last instanceof OutputStreamWriter) {
				last.close();
				result = ""; //$NON-NLS-1$
			} else {
				// others are plain StringWriters. Close has no effect on those.
				// Note that we'll never be here for file blocks : these always are AcceleoWriterDecorators
				result = last.toString();
			}
			return result;
		} catch (final IOException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationContext.WriteError"), e); //$NON-NLS-1$
		}
	}

	/**
	 * This will be used to dispose of all created buffers and caches.
	 * 
	 * @throws AcceleoEvaluationException
	 *             Thrown if the disposal of the old writers fails.
	 */
	public void dispose() throws AcceleoEvaluationException {
		AcceleoEvaluationException exception = null;
		try {
			try {
				awaitCompletion();
			} catch (InterruptedException e) {
				exception = new AcceleoEvaluationException(AcceleoEngineMessages
						.getString("AcceleoEvaluationContext.CleanUpError"), e); //$NON-NLS-1$
			}
			try {
				for (final Writer writer : writers) {
					writer.close();
				}
			} catch (final IOException e) {
				exception = new AcceleoEvaluationException(AcceleoEngineMessages
						.getString("AcceleoEvaluationContext.CleanUpError"), e); //$NON-NLS-1$
			}
		} finally {
			generationPreview.clear();
			listeners.clear();
			userCodeBlocks.clear();
			writers.clear();
		}
		if (exception != null) {
			throw exception;
		}
	}

	/**
	 * Returns the file that would be created for the given filePath according to the current generation root.
	 * 
	 * @param filePath
	 *            path of the file that will be generated.
	 * @return The File that would be created for the given filePath.
	 */
	public File getFileFor(String filePath) {
		final File generatedFile;
		if (filePath.startsWith("file:")) { //$NON-NLS-1$
			generatedFile = new File(filePath);
		} else {
			generatedFile = new File(generationRoot, filePath);
		}
		return generatedFile;
	}

	/**
	 * Returns the preview of the generation handled by this context.
	 * 
	 * @return The generation preview.
	 */
	public Map<String, String> getGenerationPreview() {
		return new HashMap<String, String>(strategy.preparePreview(generationPreview));
	}

	/**
	 * This will return the current progress monitor.
	 * 
	 * @return The current progress monitor.
	 */
	public Monitor getProgressMonitor() {
		return progressMonitor;
	}

	/**
	 * This will return the content of the protected area associated with the given marker in the current
	 * context.
	 * 
	 * @param marker
	 *            Marker of the sought protected area content.
	 * @return Content of the protected area associated with the given marker. <code>null</code> if no content
	 *         can be found.
	 */
	public String getProtectedAreaContent(String marker) {
		// Seeks out the last opened file writer
		Writer writer = null;
		for (int i = writers.size() - 1; i >= 0; i--) {
			writer = writers.get(i);
			if (writer instanceof AbstractAcceleoWriter) {
				break;
			}
			writer = null;
		}

		final Map<String, String> areas = userCodeBlocks.get(writer);
		if (areas != null) {
			return areas.remove(marker);
		}
		return null;
	}

	/**
	 * This will be called by the generation engine once all evaluations are finished for this generation. It
	 * will be used to call for the current genertion strategy's global handlers.
	 */
	public void hookGenerationEnd() {
		final Map<String, Map<String, String>> lostCode = new HashMap<String, Map<String, String>>();
		for (Map.Entry<Writer, Map<String, String>> entry : userCodeBlocks.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				final String filePath = ((AbstractAcceleoWriter)entry.getKey()).getTargetPath();
				lostCode.put(filePath, entry.getValue());
			}
		}
		if (!lostCode.isEmpty()) {
			strategy.createLostFiles(lostCode);
		}
		try {
			strategy.flushWriters(generationPreview);
		} catch (IOException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationContext.WriteError"), e); //$NON-NLS-1$
		}
	}

	/**
	 * Creates a new writer and appends it to the end of the stack.
	 * 
	 * @throws AcceleoEvaluationException
	 *             Thrown if the precedent buffer cannot be flushed.
	 */
	public void openNested() throws AcceleoEvaluationException {
		try {
			if (writers.size() > 0) {
				writers.getLast().flush();
			}
		} catch (final IOException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationContext.FlushError"), e); //$NON-NLS-1$
		}
		writers.add(new StringWriter(DEFAULT_BUFFER_SIZE));
	}

	/**
	 * Create a new writer for the file located at the given path under <tt>generationRoot</tt> and appends it
	 * to the end of the stack.
	 * <p>
	 * &quot;file&quot; schemes are handled as absolute paths and will ignore the <tt>generationRoot</tt>.
	 * </p>
	 * 
	 * @param generatedFile
	 *            File that is to be created.
	 * @param fileBlock
	 *            The file block which asked for this context. Only used for generation events.
	 * @param source
	 *            The source EObject for this file block. Only used for generation events.
	 * @param appendMode
	 *            If <code>false</code>, the file will be replaced by a new one.
	 * @param charset
	 *            Charset of the target file.
	 * @throws AcceleoEvaluationException
	 *             Thrown if the file cannot be created.
	 */
	public void openNested(File generatedFile, Block fileBlock, EObject source, boolean appendMode,
			String charset) throws AcceleoEvaluationException {
		fireFilePathComputed(new AcceleoTextGenerationEvent(generatedFile.getPath(), fileBlock, source));
		try {
			if (writers.size() > 0) {
				writers.getLast().flush();
			}
			final Map<String, String> savedCodeBlocks = new HashMap<String, String>();
			if (generatedFile.exists()) {
				savedCodeBlocks.putAll(saveProtectedAreas(generatedFile));
			}
			// If the current preview contains overlapping blocks, give them priority
			if (generationPreview.containsKey(generatedFile.getPath())) {
				savedCodeBlocks.putAll(saveProtectedAreas(generationPreview.get(generatedFile.getPath())
						.toString()));
			}
			// We checked for JMerge tags when saving protected areas. we'll use this information here.
			final AbstractAcceleoWriter writer;
			if (charset != null) {
				writer = strategy.createWriterFor(generatedFile, (AbstractAcceleoWriter)generationPreview
						.get(generatedFile.getPath()), appendMode, hasJMergeTag, charset);
			} else {
				writer = strategy.createWriterFor(generatedFile, (AbstractAcceleoWriter)generationPreview
						.get(generatedFile.getPath()), appendMode, hasJMergeTag);
			}
			generationPreview.put(generatedFile.getPath(), writer);
			// reset the jmerge state for the following file blocks
			hasJMergeTag = false;
			userCodeBlocks.put(writer, savedCodeBlocks);
			writers.add(writer);
		} catch (final IOException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationContext.FileCreationError", generatedFile.getPath()), e); //$NON-NLS-1$
		}
	}

	/**
	 * Create a new writer directed at the given {@link OutputStream}. This is mainly used for fileBlocks with
	 * "stdout" URI.
	 * 
	 * @param stream
	 *            Stream to which writing will be directed.
	 */
	public void openNested(OutputStream stream) {
		try {
			if (writers.size() > 0) {
				writers.getLast().flush();
			}
		} catch (final IOException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationContext.FlushError"), e); //$NON-NLS-1$
		}
		writers.add(new OutputStreamWriter(new AcceleoFilterOutputStream(stream)));
	}

	/**
	 * Create a new writer for the file located at the given path under <tt>generationRoot</tt> and appends it
	 * to the end of the stack.
	 * <p>
	 * &quot;file&quot; schemes are handled as absolute paths and will ignore the <tt>generationRoot</tt>.
	 * </p>
	 * 
	 * @param filePath
	 *            Path of the file around which we need a FileWriter. The file will be created under the
	 *            generationRoot if needed.
	 * @param fileBlock
	 *            The file block which asked for this context. Only used for generation events.
	 * @param source
	 *            The source EObject for this file block. Only used for generation events.
	 * @param appendMode
	 *            If <code>false</code>, the file will be replaced by a new one.
	 * @throws AcceleoEvaluationException
	 *             Thrown if the file cannot be created.
	 */
	public void openNested(String filePath, Block fileBlock, EObject source, boolean appendMode)
			throws AcceleoEvaluationException {
		openNested(getFileFor(filePath), fileBlock, source, appendMode, null);
	}

	/**
	 * Notifies all listeners that a file is going to be created.
	 * 
	 * @param event
	 *            The generation event that is to be sent to registered listeners.
	 */
	private void fireFilePathComputed(AcceleoTextGenerationEvent event) {
		for (IAcceleoTextGenerationListener listener : listeners) {
			listener.filePathComputed(event);
		}
	}

	/**
	 * Notifies all listeners that text has been generated.
	 * 
	 * @param event
	 *            The generation event that is to be sent to registered listeners.
	 */
	private void fireTextGenerated(AcceleoTextGenerationEvent event) {
		for (IAcceleoTextGenerationListener listener : listeners) {
			listener.textGenerated(event);
		}
	}

	/**
	 * This will return the list of protected areas the given file contains.
	 * 
	 * @param reader
	 *            Reader which content is to be searched through for protected areas.
	 * @return The list of saved protected areas.
	 * @throws IOException
	 *             Thrown if we cannot read through the provided reader.
	 */
	private Map<String, String> internalSaveProtectedAreas(BufferedReader reader) throws IOException {
		final Map<String, String> protectedAreas = new HashMap<String, String>();
		final String usercodeStart = AcceleoEngineMessages.getString("usercode.start"); //$NON-NLS-1$
		final String usercodeEnd = AcceleoEngineMessages.getString("usercode.end"); //$NON-NLS-1$
		String line = reader.readLine();
		while (line != null) {
			if (!hasJMergeTag && line.contains(JMERGE_TAG)) {
				hasJMergeTag = true;
			}
			if (line.contains(usercodeStart)) {
				final String marker = line.substring(line.indexOf(usercodeStart) + usercodeStart.length())
						.trim();
				final StringBuffer areaContent = new StringBuffer(DEFAULT_BUFFER_SIZE);
				// Append a line separator before the protected area if need be
				if (line.indexOf(usercodeStart) - LINE_SEPARATOR.length() > 0) {
					final String previous = line.substring(line.indexOf(usercodeStart)
							- LINE_SEPARATOR.length(), line.indexOf(usercodeStart));
					if (LINE_SEPARATOR.equals(previous)) {
						areaContent.append(LINE_SEPARATOR);
					}
				}
				// Everything preceding the start of user code doesn't need to be saved
				areaContent.append(line.substring(line.indexOf(usercodeStart)));
				line = reader.readLine();
				while (line != null) {
					areaContent.append(LINE_SEPARATOR);
					if (!hasJMergeTag && line.contains(JMERGE_TAG)) {
						hasJMergeTag = true;
					}
					// Everything following the end of use code marker doesn't need to be saved
					if (line.contains(usercodeEnd)) {
						final int endOffset = line.indexOf(usercodeEnd) + usercodeEnd.length();
						areaContent.append(line.substring(0, endOffset));
						// Append a line separator after the protected area if need be
						if (endOffset + LINE_SEPARATOR.length() <= line.length()
								&& LINE_SEPARATOR.equals(line.substring(endOffset, endOffset
										+ LINE_SEPARATOR.length()))) {
							areaContent.append(LINE_SEPARATOR);
						}
						break;
					}
					areaContent.append(line);
					line = reader.readLine();
				}
				protectedAreas.put(marker, areaContent.toString());
			}
			line = reader.readLine();
		}
		return protectedAreas;
	}

	/**
	 * This will return the list of protected areas the given file contains. <b>Note</b> that we will use this
	 * occasion to look for {@value #JMERGE_TAG} throughout the file.
	 * 
	 * @param file
	 *            File which protected areas are to be saved.
	 * @return The list of saved protected areas.
	 * @throws IOException
	 *             Thrown if we cannot read through <tt>file</tt>.
	 */
	private Map<String, String> saveProtectedAreas(File file) throws IOException {
		Map<String, String> protectedAreas = new HashMap<String, String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			protectedAreas = internalSaveProtectedAreas(reader);
		} catch (final FileNotFoundException e) {
			// cannot be thrown here, we were called after testing that the file indeed existed.
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return protectedAreas;
	}

	/**
	 * This will return the list of protected areas the given String contains. <b>Note</b> that we will use
	 * this occasion to look for {@value #JMERGE_TAG} throughout the file.
	 * 
	 * @param buffer
	 *            String (file content) which protected areas are to be saved.
	 * @return The list of saved protected areas.
	 */
	private Map<String, String> saveProtectedAreas(String buffer) {
		Map<String, String> protectedAreas = new HashMap<String, String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new StringReader(buffer));
			protectedAreas = internalSaveProtectedAreas(reader);
		} catch (IOException e) {
			// Cannot happen here
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// This should never happen with a String Reader
				}
			}
		}
		return protectedAreas;
	}

	/**
	 * This implementation of a FilterOutputStream will avoid closing the standard output if it is the
	 * underlying stream.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private final class AcceleoFilterOutputStream extends FilterOutputStream {
		/**
		 * Constructs an output stream redirecting all calls to the given {@link OutputStream}.
		 * 
		 * @param out
		 *            The decorated output stream.
		 */
		public AcceleoFilterOutputStream(OutputStream out) {
			super(out);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.io.FilterOutputStream#close()
		 */
		@Override
		public void close() throws IOException {
			try {
				flush();
			} catch (IOException e) {
				// Ignored exception
			}
			if (out != System.out) {
				out.close();
			}
		}
	}
}
