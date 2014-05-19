/*******************************************************************************
 * Copyright (c) 2008, 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.evaluation;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.common.utils.AcceleoASTNodeAdapter;
import org.eclipse.acceleo.common.utils.CircularArrayDeque;
import org.eclipse.acceleo.common.utils.Deque;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.AcceleoRuntimeException;
import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter;
import org.eclipse.acceleo.engine.generation.writers.AcceleoFileWriter;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * This will hold all necessary variables for the evaluation of an Acceleo module.
 * 
 * @param <C>
 *            This should be EClassifier for ecore, Class for UML.
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationContext<C> {
	/** Default size to be used for new buffers. */
	private static final int DEFAULT_BUFFER_SIZE = 1024;

	/** This is the tag we will look for to determine if a file has to be passed through JMerge. */
	private static final String JMERGE_TAG = "@generated"; //$NON-NLS-1$

	/** DOS line separators. */
	private static final String DOS_LINE_SEPARATOR = "\r\n"; //$NON-NLS-1$

	/** Unix line separators. */
	private static final String UNIX_LINE_SEPARATOR = "\n"; //$NON-NLS-1$

	/** Mac line separators. */
	private static final String MAC_LINE_SEPARATOR = "\r"; //$NON-NLS-1$

	/** Holds the generation preview in the form of mappings filePath => fileContent. */
	protected final Map<String, Writer> generationPreview = new HashMap<String, Writer>();

	/**
	 * This will hold the list of all listeners registered for notification on text generation from this
	 * engine.
	 * 
	 * @since 1.0
	 */
	protected final List<IAcceleoTextGenerationListener> listeners = new ArrayList<IAcceleoTextGenerationListener>(
			3);

	/**
	 * This will be set to true if one of the registered generation listener is interested in generation end
	 * notifications.
	 * 
	 * @since 3.0
	 */
	protected final boolean notifyOnGenerationEnd;

	/** This will maintain the stack trace of expression evaluations. */
	private Deque<OCLExpression<C>> expressionStack = new CircularArrayDeque<OCLExpression<C>>();

	/** References the file which is to be used as the root for all generated files. */
	private final File generationRoot;

	/** The state of his boolean will be changed while reading files prior to generation. */
	private boolean hasJMergeTag;

	/** This will be initialized with this generation's progress monitor. */
	private final Monitor progressMonitor;

	/** The current generation strategy. */
	private final IAcceleoGenerationStrategy strategy;

	/** This will keep a reference to all user code blocks of a given File. */
	private final Map<Writer, Map<String, String>> userCodeBlocks = new HashMap<Writer, Map<String, String>>();

	/** This will hold the buffer stack. */
	private final Deque<Writer> writers = new CircularArrayDeque<Writer>();

	/**
	 * If we try and generate something out of any context (for example, an "if" block outside of any Template
	 * or File), we'll use this "default" writer in order not to lose the generated text.
	 */
	private StringWriter defaultWriter;

	/**
	 * This will allow us to determine whether a given generation tried to generate one or more file(s) more
	 * than once.
	 */
	private Map<String, Integer> generateFiles = new HashMap<String, Integer>();

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

		boolean temp = false;
		for (IAcceleoTextGenerationListener listener : listeners) {
			if (listener.listensToGenerationEnd()) {
				temp = true;
				break;
			}
		}
		notifyOnGenerationEnd = temp;
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
			if (!writers.isEmpty()) {
				final Writer currentWriter = writers.getLast();
				currentWriter.append(string);
				if (fireEvent && string.length() > 0) {
					fireTextGenerated(new AcceleoTextGenerationEvent(string, sourceBlock, source));
				}
			} else {
				final String message = AcceleoEngineMessages
						.getString("AcceleoEvaluationVisitor.PossibleEmptyFileName"); //$NON-NLS-1$
				if (EMFPlugin.IS_ECLIPSE_RUNNING && AcceleoPreferences.isDebugMessagesEnabled()) {
					AcceleoEnginePlugin.log(message, false);
				}
				if (defaultWriter == null) {
					defaultWriter = new StringWriter(DEFAULT_BUFFER_SIZE);
				}
				defaultWriter.append(string);
			}
		} catch (final IOException e) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationContext.AppendError"), e); //$NON-NLS-1$
		}
	}

	/**
	 * Adds the given expression at the end of the expression stack.
	 * 
	 * @param expression
	 *            Expression that is to be appended to the expression stack trace.
	 */
	public void addToStack(OCLExpression<C> expression) {
		expressionStack.add(expression);
	}

	/**
	 * Allows clients to await for the lost file creation to end.
	 * 
	 * @throws InterruptedException
	 *             This will be thrown if the lost files creation is interrupted somehow.
	 * @deprecated does nothing call {@link IAcceleoGenerationStrategy#awaitCompletion()} directly.
	 */
	@Deprecated
	public void awaitCompletion() throws InterruptedException {
		// strategy.awaitCompletion();
	}

	/**
	 * This will create and return an evaluation exception with a custom stack trace filled in for the given
	 * block. The <em>messageKey</em> should map to an actual message in
	 * <em>org/eclipse/acceleo/engine/acceleoenginemessages.properties</em>.
	 * 
	 * @param node
	 *            Node from which the failure originated.
	 * @param messageKey
	 *            This should map to the message that is to be retrieved for this exception.
	 * @param currentSelf
	 *            The last recorded value of the <em>self</em> variable.
	 * @return An evaluation exception for the given block.
	 */
	public AcceleoEvaluationException createAcceleoException(ASTNode node, String messageKey,
			Object currentSelf) {
		return createAcceleoException(node, null, messageKey, currentSelf);
	}

	/**
	 * This will create and return an evaluation exception with a custom stack trace filled in for the given
	 * block. The <em>messageKey</em> should map to an actual message in
	 * <em>org/eclipse/acceleo/engine/acceleoenginemessages.properties</em>.
	 * 
	 * @param node
	 *            Node from which the failure originated.
	 * @param expression
	 *            if the actual failure was caused by a subexpression of <em>block</em>, pass it here.
	 * @param messageKey
	 *            This should map to the message that is to be retrieved for this exception.
	 * @param currentSelf
	 *            The last recorded value of the <em>self</em> variable.
	 * @return An evaluation exception for the given block.
	 */
	public AcceleoEvaluationException createAcceleoException(ASTNode node, OCLExpression<C> expression,
			String messageKey, Object currentSelf) {
		Adapter adapter = EcoreUtil.getAdapter(node.eAdapters(), AcceleoASTNodeAdapter.class);
		int line = 0;
		if (adapter instanceof AcceleoASTNodeAdapter) {
			line = ((AcceleoASTNodeAdapter)adapter).getLine();
		}
		String moduleName = ((Module)EcoreUtil.getRootContainer(node)).getName();
		String message = AcceleoEngineMessages.getString(messageKey, Integer.valueOf(line), moduleName, node
				.toString(), currentSelf, expression);

		AcceleoFileWriter acceleoFileWriter = this.getAcceleoFileWriterFromContext();
		if (acceleoFileWriter != null) {
			message += " " + AcceleoEngineMessages.getString("AcceleoEvaluationContext.FileException", //$NON-NLS-1$ //$NON-NLS-2$
					acceleoFileWriter.getTargetPath());
		}

		final AcceleoEvaluationException exception = new AcceleoEvaluationException(message);
		exception.setStackTrace(createAcceleoStackTrace());
		return exception;
	}

	/**
	 * Returns the first Acceleo writer found in the context or <code>null</code> otherwise.
	 * 
	 * @return The first Acceleo writer found in the context or <code>null</code> otherwise.
	 */
	private AcceleoFileWriter getAcceleoFileWriterFromContext() {
		for (int i = writers.size() - 1; i >= 0; i--) {
			Writer writer = writers.get(i);
			if (writer instanceof AcceleoFileWriter) {
				return (AcceleoFileWriter)writer;
			}
		}
		return null;
	}

	/**
	 * Wraps the given throwable inside a custom Acceleo Exception.
	 * 
	 * @param cause
	 *            Actual cause of the failure.
	 * @return The created exception. Could be <code>null</code> if this context has already been disposed.
	 */
	public AcceleoRuntimeException createAcceleoRuntimeException(Throwable cause) {
		AcceleoRuntimeException exception = new AcceleoRuntimeException(cause);
		if (expressionStack.size() > 0) {
			StackTraceElement[] traceElements = createAcceleoStackTrace();
			if (traceElements.length > 0) {
				exception.setStackTrace(traceElements);
			}
		}
		return exception;
	}

	/**
	 * This will create a stack trace according to the current evaluation stack as recorded in
	 * {@link #expressionStack}.
	 * 
	 * @return Stack trace that can be used with {@link Exception#setStackTrace(StackTraceElement[])}.
	 */
	public StackTraceElement[] createAcceleoStackTrace() {
		StackTraceElement[] stackTrace = new StackTraceElement[expressionStack.size()];
		for (int i = expressionStack.size() - 1; i >= 0; i--) {
			OCLExpression<C> expression = expressionStack.get(i);

			EObject rootContainer = EcoreUtil.getRootContainer(expression);
			if (rootContainer instanceof Module) {
				Module containingModule = (Module)rootContainer;
				String moduleFile;
				if (containingModule.eResource() != null && containingModule.eResource().getURI() != null) {
					moduleFile = containingModule.eResource().getURI().trimFileExtension().lastSegment()
							+ '.' + IAcceleoConstants.MTL_FILE_EXTENSION;
				} else {
					moduleFile = containingModule.getName() + '.' + IAcceleoConstants.MTL_FILE_EXTENSION;
				}
				EObject containingModuleElement = expression;
				while (!(containingModuleElement instanceof ModuleElement)) {
					containingModuleElement = containingModuleElement.eContainer();
				}
				Adapter adapter = EcoreUtil.getAdapter(expression.eAdapters(), AcceleoASTNodeAdapter.class);
				int line = 0;
				if (adapter instanceof AcceleoASTNodeAdapter) {
					line = ((AcceleoASTNodeAdapter)adapter).getLine();
				}
				stackTrace[expressionStack.size() - i - 1] = new StackTraceElement(
						containingModule.getName(), containingModuleElement.toString(), moduleFile, line);
			} else if (rootContainer instanceof ProtectedAreaBlock) {
				// Let's not handle this now...
				stackTrace = new StackTraceElement[0];
			}
		}
		return stackTrace;
	}

	/**
	 * Closes the last writer of the stack and returns its result if it was a StringWriter. This is a
	 * convenience methode to close contexts that were opened for other than file blocks.
	 * 
	 * @return Result held by the last writer of the stack.
	 * @throws AcceleoEvaluationException
	 *             This will be thrown if the last writer of the stack cannot be flushed and closed.
	 */
	public String closeContext() throws AcceleoEvaluationException {
		return closeContext(null, null);
	}

	/**
	 * Closes the last writer of the stack and returns its result if it was a StringWriter. The empty String
	 * will be returned for FileWriters.
	 * 
	 * @param sourceBlock
	 *            The source block that first created this context. Only used when closing a file context.
	 * @param source
	 *            The source EObject for this block. Only used when closing a file context.
	 * @return Result held by the last writer of the stack.
	 * @throws AcceleoEvaluationException
	 *             This will be thrown if the last writer of the stack cannot be flushed and closed.
	 */
	public String closeContext(Block sourceBlock, EObject source) throws AcceleoEvaluationException {
		if (writers.isEmpty()) {
			final String message = AcceleoEngineMessages
					.getString("AcceleoEvaluationVisitor.PossibleEmptyFileName"); //$NON-NLS-1$
			if (EMFPlugin.IS_ECLIPSE_RUNNING && AcceleoPreferences.isDebugMessagesEnabled()) {
				AcceleoEnginePlugin.log(message, false);
			}
			return ""; //$NON-NLS-1$
		}

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
				fireFileGenerated(filePath, sourceBlock, source);
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
			expressionStack.clear();
		}
		if (exception != null) {
			throw exception;
		}
	}

	/**
	 * Notifies the context that a file at the given <em>filePath</em> will be generated.
	 * 
	 * @param filePath
	 *            Path to the file.
	 */
	public void generateFile(String filePath) {
		Integer timesGenerated = generateFiles.get(filePath);
		if (timesGenerated == null) {
			timesGenerated = Integer.valueOf(1);
		} else {
			timesGenerated = Integer.valueOf(timesGenerated.intValue() + 1);
		}
		generateFiles.put(filePath, timesGenerated);
	}

	/**
	 * This will return the indentation of the very last line of the very last file writer in context.
	 * 
	 * @return indentation of the very last line in context.
	 */
	public String getLastFileIndentation() {
		Writer writer = null;
		for (int i = writers.size() - 1; i >= 0 && !(writer instanceof AbstractAcceleoWriter); i--) {
			writer = writers.get(i);
		}
		if (writer != null) {
			return ((AbstractAcceleoWriter)writer).getCurrentLineIndentation();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Walks up the expression stack and returns the last visited Block.
	 * 
	 * @return The last visited Block.
	 */
	public Block getLastVisitedBlock() {
		if (expressionStack.isEmpty()) {
			return null;
		}
		final ListIterator<OCLExpression<C>> expressionIterator = expressionStack
				.listIterator(expressionStack.size());
		OCLExpression<C> previous;
		do {
			previous = expressionIterator.previous();
		} while (!(previous instanceof Block) && expressionIterator.hasPrevious());

		Block lastBlock = null;
		if (previous instanceof Block) {
			lastBlock = (Block)previous;
		}
		return lastBlock;
	}

	public Deque<OCLExpression<C>> getExpressionStack() {
		return expressionStack;
	}

	/**
	 * This will return the indentation of the very last line of the very last opened writer in context.
	 * 
	 * @return indentation of the very last line in context.
	 */
	public String getCurrentLineIndentation() {
		StringBuffer currentIndentation = new StringBuffer();
		if (!writers.isEmpty()) {
			Writer writer = writers.getLast();
			if (writer instanceof AbstractAcceleoWriter) {
				return ((AbstractAcceleoWriter)writer).getCurrentLineIndentation();
			}
			// Only String writers remain
			String content = writer.toString();
			int newLineIndex = -1;
			if (content.contains(DOS_LINE_SEPARATOR)) {
				newLineIndex = content.lastIndexOf(DOS_LINE_SEPARATOR) + DOS_LINE_SEPARATOR.length();
			} else if (content.contains(UNIX_LINE_SEPARATOR)) {
				newLineIndex = content.lastIndexOf(UNIX_LINE_SEPARATOR) + UNIX_LINE_SEPARATOR.length();
			} else if (content.contains(MAC_LINE_SEPARATOR)) {
				newLineIndex = content.lastIndexOf(MAC_LINE_SEPARATOR) + MAC_LINE_SEPARATOR.length();
			}

			if (newLineIndex == -1) {
				newLineIndex = 0;
			}
			for (int i = newLineIndex; i < content.length(); i++) {
				if (Character.isWhitespace(content.charAt(i))) {
					currentIndentation.append(content.charAt(i));
				} else {
					break;
				}
			}
		}
		return currentIndentation.toString();
	}

	/**
	 * Returns the text that has been appended to the default writer, if any.
	 * 
	 * @return The text that has been appended to the default writer, <code>null</code> if none.
	 */
	public String getDefaultText() {
		if (defaultWriter != null) {
			defaultWriter.flush();
			String text = defaultWriter.toString();
			defaultWriter = null;
			return text;
		}
		return null;
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
		for (int i = writers.size() - 1; i >= 0 && !(writer instanceof AbstractAcceleoWriter); i--) {
			writer = writers.get(i);
		}

		final Map<String, String> areas = userCodeBlocks.get(writer);
		if (areas != null) {
			return areas.remove(marker);
		}
		return null;
	}

	/**
	 * This will be called by the generation engine once all evaluations are finished for this generation. It
	 * will be used to call for the current generation strategy's global handlers.
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

		Map<String, Integer> filteredFiles = Maps.filterEntries(generateFiles,
				new Predicate<Map.Entry<String, Integer>>() {
					public boolean apply(Map.Entry<String, Integer> input) {
						return input.getValue().intValue() > 1;
					}
				});

		if (!filteredFiles.isEmpty()) {
			final StringBuilder message = new StringBuilder(AcceleoEngineMessages
					.getString("AcceleoEvaluationContext.OverrodeFiles")); //$NON-NLS-1$
			message.append('\n').append('\n');
			for (Map.Entry<String, Integer> file : filteredFiles.entrySet()) {
				message.append(file.getKey() + " : " + file.getValue().toString() + " times" + '\n'); //$NON-NLS-1$ //$NON-NLS-2$
			}
			AcceleoEnginePlugin.log(message.toString(), false);
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
			if (!writers.isEmpty()) {
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
			if (!writers.isEmpty()) {
				writers.getLast().flush();
			}
			final Map<String, String> savedCodeBlocks = new HashMap<String, String>();
			if (generatedFile.exists()) {
				savedCodeBlocks.putAll(saveProtectedAreas(generatedFile, charset));
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
			if (!writers.isEmpty()) {
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
	 * Removes the last added expression from the expression stack trace.
	 */
	public void removeFromStack() {
		if (!expressionStack.isEmpty()) {
			expressionStack.removeLast();
		}
	}

	/**
	 * Notifies all listeners that a file has just been generated.
	 * 
	 * @param filePath
	 *            Path of the generated file.
	 * @param fileBlock
	 *            File block which generation just ended.
	 * @param source
	 *            The Object for which was generated this file.
	 */
	protected void fireFileGenerated(String filePath, Block fileBlock, EObject source) {
		AcceleoTextGenerationEvent event = new AcceleoTextGenerationEvent(filePath, fileBlock, source);
		for (IAcceleoTextGenerationListener listener : listeners) {
			listener.fileGenerated(event);
		}
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
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).textGenerated(event);
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
	private Map<String, String> internalSaveProtectedAreas(LineReader reader) throws IOException {
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
				final int start = line.indexOf(usercodeStart);
				// Everything preceding the start of user code doesn't need to be saved
				areaContent.append(line.substring(start));

				/*
				 * TODO If there is no "end of user code", or if the protected content is too large, this will
				 * fail in OutOfMemoryErrors. Could we use a temp File (java.nio?) instead of a StringBuffer?
				 */
				String lastEOF = reader.getLastEOLSequence();
				areaContent.append(lastEOF);

				line = reader.readLine();
				lastEOF = reader.getLastEOLSequence();
				while (line != null) {
					if (!hasJMergeTag && line.contains(JMERGE_TAG)) {
						hasJMergeTag = true;
					}
					// Everything following the end of user code marker doesn't need to be saved
					if (line.contains(usercodeEnd)) {
						final int endOffset = line.indexOf(usercodeEnd) + usercodeEnd.length();
						areaContent.append(line.substring(0, endOffset));
						break;
					}
					areaContent.append(line);
					areaContent.append(lastEOF);

					line = reader.readLine();
					lastEOF = reader.getLastEOLSequence();
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
	 * @param charset
	 *            Charset of the file.
	 * @return The list of saved protected areas.
	 * @throws IOException
	 *             Thrown if we cannot read through <tt>file</tt>.
	 */
	private Map<String, String> saveProtectedAreas(File file, String charset) throws IOException {
		Map<String, String> protectedAreas = new HashMap<String, String>();
		LineReader reader = null;
		try {
			if (charset == null) {
				reader = new LineReader(new FileReader(file));
			} else {
				reader = new LineReader(new InputStreamReader(new FileInputStream(file), charset));
			}
			protectedAreas = internalSaveProtectedAreas(reader);
		} catch (final FileNotFoundException e) {
			// cannot be thrown here, we were called after testing that the file indeed existed.
			AcceleoEnginePlugin.log(e, true);
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
		LineReader reader = null;
		try {
			reader = new LineReader(new StringReader(buffer));
			protectedAreas = internalSaveProtectedAreas(reader);
		} catch (IOException e) {
			// Cannot happen here
			AcceleoEnginePlugin.log(e, true);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// This should never happen with a String Reader
					AcceleoEnginePlugin.log(e, true);
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

	/**
	 * This implementation of a Reader will allow us to read lines while still giving us access to the eol
	 * sequence.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	public final class LineReader extends BufferedReader {
		/** Size of our read buffer. */
		private static final int BUFFER_SIZE = 8192;

		/** Character buffer in which we'll read. */
		private char[] buffer = new char[BUFFER_SIZE];

		/** Our underlying stream. */
		private Reader input;

		/** Number of chars in the local buffer. */
		private int nChars;

		/** Next character to read from the local buffer. */
		private int nextChar;

		/** Last EOL sequence encountered by {@link #readLine(boolean)}. */
		private String lastEOL = DOS_LINE_SEPARATOR;

		/**
		 * Constructs our buffered reader given its underlying reader.
		 * 
		 * @param in
		 *            The reader from which to retrieve input.
		 */
		public LineReader(Reader in) {
			super(in);
			this.input = in;
			nChars = 0;
		}

		/**
		 * Reads a line of text. A line is considered to be terminated by either a line feed ('\n'), a
		 * carriage return ('\r') or a carriage return followed immediately by a line feed ("\r\n"). The line
		 * termination sequence will be omitted.
		 * 
		 * @return A string containing the content of the line, or <code>null</code> if the end of the stream
		 *         has been reached.
		 * @throws IOException
		 *             Thrown if the stream is closed or an I/O operation fails.
		 */
		@Override
		public String readLine() throws IOException {
			StringBuilder lineBuffer = null;
			int startChar;

			String line = null;
			synchronized(lock) {
				if (input == null) {
					throw new IOException(AcceleoEngineMessages
							.getString("AcceleoEvaluationContext.ClosedStream")); //$NON-NLS-1$
				}

				while (line == null) {
					int bufferGap = 0;
					if (nextChar >= nChars) {
						bufferGap = nextChar - nChars;
						fillBuffer();
					}
					if (nextChar >= nChars) {
						// Reached the end of the stream
						if (lineBuffer != null && lineBuffer.length() > 0) {
							line = lineBuffer.toString();
						}
						break;
					}

					nextChar = nextChar + bufferGap;

					boolean eol = false;
					char c = 0;
					int i;

					for (i = nextChar; i < nChars; i++) {
						c = buffer[i];
						if (c == '\n' || c == '\r') {
							eol = true;
							break;
						}
					}

					startChar = nextChar;
					nextChar = i;

					if (eol) {
						if (lineBuffer == null) {
							line = new String(buffer, startChar, i - startChar);
						} else {
							lineBuffer.append(buffer, startChar, i - startChar);
							line = lineBuffer.toString();
						}
						if (c == '\n') {
							lastEOL = "\n"; //$NON-NLS-1$
							nextChar++;
						} else if (c == '\r') {
							final int max = 8191;
							if (nextChar != max && buffer.length >= nextChar && buffer[nextChar + 1] == '\n') {
								lastEOL = DOS_LINE_SEPARATOR;
								nextChar += 2;
							} else if (nextChar != max) {
								lastEOL = "\r"; //$NON-NLS-1$
								nextChar++;
							} else if (nextChar == max && DOS_LINE_SEPARATOR.equals(lastEOL)) {
								nextChar += 2;
							} else if (nextChar == max) {
								nextChar++;
							}
						}
					}

					if (lineBuffer == null) {
						lineBuffer = new StringBuilder();
					}
					lineBuffer.append(buffer, startChar, i - startChar);
				}
			}
			return line;
		}

		/**
		 * Returns the last EOL sequence encountered by {@link #readLine()}.
		 * 
		 * @return The last EOL sequence encountered by {@link #readLine()}. May be <code>null</code>.
		 */
		public String getLastEOLSequence() {
			return lastEOL;
		}

		/**
		 * Fills the input buffer.
		 * 
		 * @throws IOException
		 *             Thrown if the stream is closed or an I/O operation fails.
		 */
		private void fillBuffer() throws IOException {
			int n;
			do {
				n = input.read(buffer, 0, buffer.length);
			} while (n == 0);

			if (n > 0) {
				nChars = n;
				nextChar = 0;
			}
		}
	}
}
