/*******************************************************************************
 * Copyright (c) 2009, 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.traceability.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.common.utils.AcceleoStandardLibrary;
import org.eclipse.acceleo.common.utils.CircularArrayDeque;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.common.utils.Deque;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEvaluationCancelledException;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator;
import org.eclipse.acceleo.engine.internal.evaluation.QueryCache;
import org.eclipse.acceleo.internal.traceability.AcceleoTraceabilityMessages;
import org.eclipse.acceleo.internal.traceability.AcceleoTraceabilityPlugin;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModelFile;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.ModuleFile;
import org.eclipse.acceleo.traceability.TraceabilityFactory;
import org.eclipse.acceleo.traceability.TraceabilityModel;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.expressions.AssociationClassCallExp;
import org.eclipse.ocl.expressions.BooleanLiteralExp;
import org.eclipse.ocl.expressions.CollectionItem;
import org.eclipse.ocl.expressions.CollectionLiteralExp;
import org.eclipse.ocl.expressions.EnumLiteralExp;
import org.eclipse.ocl.expressions.ExpressionsPackage;
import org.eclipse.ocl.expressions.IfExp;
import org.eclipse.ocl.expressions.IntegerLiteralExp;
import org.eclipse.ocl.expressions.IterateExp;
import org.eclipse.ocl.expressions.IteratorExp;
import org.eclipse.ocl.expressions.LetExp;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.PropertyCallExp;
import org.eclipse.ocl.expressions.RealLiteralExp;
import org.eclipse.ocl.expressions.StateExp;
import org.eclipse.ocl.expressions.StringLiteralExp;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.ocl.expressions.VariableExp;
import org.eclipse.ocl.util.OCLStandardLibraryUtil;
import org.eclipse.ocl.utilities.PredefinedType;

/**
 * This implementation of an evaluation visitor will record traceability information as the evaluation
 * processes. All actual evaluations and generations will be delegated to the standard Acceleo evaluation
 * visitor.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <PK>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <C>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <O>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <P>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <EL>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <PM>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <S>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <COA>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <SSA>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <CT>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <CLS>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 * @param <E>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 */
public class AcceleoTraceabilityVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> extends AcceleoEvaluationVisitorDecorator<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> {
	/** Initial size of our "cache" maps. */
	private static final int INITIAL_CACHE_SIZE = 128;

	/** Initial size of our recorded traces collection. */
	private static final int RECORDED_TRACE_SIZE = 32;

	/**
	 * As we add and remove the "scope" object for templates at a different times, we need to remember whether
	 * the template actually had a scope or not.
	 */
	private boolean addedTemplateScope;

	/** This will be used to keep pointers towards the input elements created for the current trace. */
	private Map<EObject, Set<InputElement>> cachedInputElements = new HashMap<EObject, Set<InputElement>>(
			INITIAL_CACHE_SIZE);

	/** This will be used to keep pointers towards the module elements created for the current trace. */
	private Map<EObject, ModuleElement> cachedModuleElements = new HashMap<EObject, ModuleElement>(
			INITIAL_CACHE_SIZE);

	/** Traceability needs to know what expression is being processed at all times. */
	private OCLExpression<C> currentExpression;

	/** This will hold the stack of generated files. */
	private Deque<GeneratedFile> currentFiles = new CircularArrayDeque<GeneratedFile>();

	/** Caches the result of searching for the plugin URL of the metamodels. */
	private final Map<String, String> ecoreURLCache = new HashMap<String, String>();

	/** This will be set to <code>true</code> whenever we begin the evaluation of an iterator's set. */
	private boolean evaluatingIterationSet;

	/**
	 * This boolean will allow us to ignore Template Invocation traces when they're nested in Operation Calls.
	 */
	private boolean evaluatingOperationCall;

	/**
	 * This will be set to <code>true</code> whenever we begin the evaluation of a template post-invocation.
	 */
	private boolean evaluatingPostCall;

	/** All traceability information for this session will be saved in this instance. */
	private final TraceabilityModel evaluationTrace;

	/** Keeps track of the variable currently being initialized. */
	private Variable<C, PM> initializingVariable;

	/** This will be used to keep pointers towards the latest template invocation traces. */
	private Deque<ExpressionTrace<C>> invocationTraces;

	/** This will allow us to determine when we finish the evaluation of an iteration. */
	private OCLExpression<C> iterationBody;

	/**
	 * This allows us to infer the current iteration trace offsets.
	 */
	private Deque<Integer> iterationCount = new CircularArrayDeque<Integer>();

	/** This will be used to record the trace information for the current iteration's source. */
	private Deque<IterationTrace<C, PM>> iterationTraces = new CircularArrayDeque<IterationTrace<C, PM>>();

	/**
	 * This will allow us to restore generated files' offsets in the case where traceability information is
	 * wrong in any way.
	 */
	private int lastInvocationTracesLength;

	/**
	 * We'll use this to record accurate trace information for the library's traceability-impacting
	 * operations.
	 */
	private ExpressionTrace<C> operationArgumentTrace;

	/**
	 * Along with {@link #operationCallSourceExpression}, this allows us to retrieve the source value of an
	 * operation call.
	 */
	private Object operationCallSource;

	/** This will allow us to retrieve the proper source value of a given operation call. */
	private OCLExpression<C> operationCallSourceExpression;

	/** Our delegate operation visitor. */
	@SuppressWarnings("unchecked")
	private AcceleoTraceabilityOperationVisitor<C, PM> operationVisitor = new AcceleoTraceabilityOperationVisitor<C, PM>(
			(AcceleoTraceabilityVisitor<EPackage, C, EOperation, EStructuralFeature, EEnumLiteral, PM, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject>)this);

	/**
	 * Along with {@link #propertyCallSourceExpression}, this allows us to retrieve the source value of a
	 * property call.
	 */
	private EObject propertyCallSource;

	/** This we'll allow us to retrieve the proper source value of a given property call. */
	private OCLExpression<C> propertyCallSourceExpression;

	/**
	 * This will be set as soon as we enter the evaluation of a protected area, and reset to <code>null</code>
	 * as soon as we exit this area. it will be used as the source for the two hard-coded strings "start of
	 * user code" and "end of user code".
	 */
	private ModuleElement protectedAreaModuleElement;

	/**
	 * This will be set as soon as we enter the evaluation of a protected area, and reset to <code>null</code>
	 * as soon as we exit this area. It will be used to shortcut all input recorded inside of such an area.
	 */
	private InputElement protectedAreaSource;

	/** Query results are cached, thus we need to cache their traces too. */
	private QueryTraceCache<C> queryTraceCache = new QueryTraceCache<C>();

	/** This will be used internally to prevent trace recording for set expressions. */
	private boolean record = true;

	/** This will hold the stack of all created traceability contexts. */
	private Deque<ExpressionTrace<C>> recordedTraces = new CircularArrayDeque<ExpressionTrace<C>>(
			RECORDED_TRACE_SIZE);

	/** This will be updated each time we enter a for/template/query/... with the scope variable. */
	private Deque<EObject> scopeEObjects = new CircularArrayDeque<EObject>();

	/**
	 * Records all variable traces for this session. Note that only primitive type variables will be recorded.
	 */
	private final Map<Variable<C, PM>, VariableTrace<C, PM>> variableTraces = new HashMap<Variable<C, PM>, VariableTrace<C, PM>>();

	/**
	 * The evaluation visitor.
	 */
	private AcceleoEvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> evaluationVisitor;

	/**
	 * Default constructor.
	 * 
	 * @param decoratedVisitor
	 *            The evaluation visitor this instance will decorate.
	 * @param trace
	 *            Model in which evaluation traces are to be recorded.
	 */
	public AcceleoTraceabilityVisitor(
			AcceleoEvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> decoratedVisitor,
			TraceabilityModel trace) {
		super(decoratedVisitor);
		this.evaluationVisitor = decoratedVisitor;
		evaluationTrace = trace;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#append(java.lang.String,
	 *      org.eclipse.acceleo.model.mtl.Block, org.eclipse.emf.ecore.EObject, boolean)
	 */
	@Override
	public void append(String string, Block sourceBlock, EObject source, boolean fireEvent) {
		// Check whether we should consider these traces
		boolean considerTrace = true;
		// If we don't need an event or if the generated String is empty, no need to carry on
		considerTrace = considerTrace && string.length() > 0;
		// No need to go any further either if anything of our trace information is empty
		considerTrace = considerTrace && currentFiles != null && !currentFiles.isEmpty();
		considerTrace = considerTrace && recordedTraces != null && !recordedTraces.isEmpty();
		// Lastly, we need to ignore those events corresponding to TemplateInvocation nested in OperationCalls
		// We will look for the root source block to ensure the same condition for nested if/let/for.
		Block rootSourceBlock = sourceBlock;
		if (!(rootSourceBlock instanceof Template) && !(rootSourceBlock instanceof FileBlock)) {
			while (rootSourceBlock.eContainer() instanceof Block && !(rootSourceBlock
					.eContainer() instanceof FileBlock)) {
				rootSourceBlock = (Block)rootSourceBlock.eContainer();
			}
		}

		// If we have a template call in an evaluation call we don't consider it
		// If this template call contains a template, we will consider it
		boolean isTemplateInOperationCall = (rootSourceBlock instanceof Template
				|| rootSourceBlock instanceof FileBlock) && evaluatingOperationCall;
		considerTrace = considerTrace && (rootSourceBlock instanceof FileBlock || !isTemplateInOperationCall);
		if (considerTrace && fireEvent) {
			GeneratedFile generatedFile = currentFiles.getLast();
			ExpressionTrace<C> trace;
			boolean disposeTrace = !(sourceBlock instanceof IfBlock) && !(sourceBlock instanceof ForBlock);
			if (disposeTrace) {
				trace = recordedTraces.removeLast();
			} else {
				trace = recordedTraces.getLast();
			}
			if (trace.getReferredExpression() instanceof ProtectedAreaBlock && trace.getTraces().isEmpty()) {
				createProtectedAreaTrace(string, sourceBlock, trace);
			}
			final int fileLength = generatedFile.getLength();
			int addedLength = 0;

			// We no longer need to refer to the same trace instance, copy its current state.
			if (invocationTraces != null) {
				invocationTraces.remove(trace);
				invocationTraces.add(new ExpressionTrace<C>(trace));
			}

			for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
				Iterator<GeneratedText> textIterator = entry.getValue().iterator();
				while (textIterator.hasNext()) {
					GeneratedText text = textIterator.next();
					textIterator.remove();
					addedLength += text.getEndOffset() - text.getStartOffset();
					text.setStartOffset(fileLength + text.getStartOffset());
					text.setEndOffset(fileLength + text.getEndOffset());
					generatedFile.getGeneratedRegions().add(text);
				}
			}

			int stringLength = string.length();
			if (addedLength != stringLength && lastInvocationTracesLength != stringLength
					&& !(sourceBlock instanceof ProtectedAreaBlock)) {
				/*
				 * We might have had an error with traceability information on this expression. Force the
				 * length of the file to grow the length of the String. Should we log anything? This
				 * information would be interesting if a user encounters such a failure.
				 */
				// generatedFile.setLength(fileLength + stringLength);
				addedLength = stringLength;
			} else {
				generatedFile.setLength(fileLength + addedLength);
			}
			if (invocationTraces != null) {
				lastInvocationTracesLength += addedLength;
			} else {
				lastInvocationTracesLength = 0;
			}
			if (disposeTrace) {
				trace.dispose();
			} else {
				trace.setOffset(0);
			}
		} else if (considerTrace && invocationTraces != null && !isProtectedAreaContent(sourceBlock)
				&& invocationTraces.contains(recordedTraces.getLast())) {
			/*
			 * We are here to handle the cases of nested template invocations. If we have template1 that calls
			 * template2 that calls template3... and there are multiple post(trim()) along the way, we might
			 * end up trying to trim [[0,4], [0,7], [0,2]] with each of these three traces being results of a
			 * different template. We would not be able to determine which is the first of the three, and how
			 * they are related. We need to "advance" the region to reflect the actual positions, so that we
			 * will end up with regions [[0,4], [4,11], [11,13]].
			 */
			final int fileLength = currentFiles.getLast().getLength();
			final ExpressionTrace<C> trace = recordedTraces.removeLast();

			invocationTraces.remove(trace);
			invocationTraces.add(new ExpressionTrace<C>(trace));

			for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
				Iterator<GeneratedText> textIterator = entry.getValue().iterator();
				while (textIterator.hasNext()) {
					GeneratedText text = textIterator.next();
					text.setStartOffset(fileLength + text.getStartOffset());
					text.setEndOffset(fileLength + text.getEndOffset());
				}
			}

			trace.dispose();
		}
		super.append(string, sourceBlock, source, fireEvent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see AcceleoEvaluationVisitorDecorator#cacheResult(Query, List, Object)
	 */
	@Override
	public void cacheResult(Query query, List<Object> arguments, Object result) {
		queryTraceCache.cacheTrace(query, arguments, new ExpressionTrace<C>(recordedTraces.getLast()));

		super.cacheResult(query, arguments, result);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#createFileWriter(java.io.File,
	 *      org.eclipse.acceleo.model.mtl.Block, org.eclipse.emf.ecore.EObject, boolean, java.lang.String)
	 */
	@Override
	public void createFileWriter(File generatedFile, Block fileBlock, EObject source, boolean appendMode,
			String charset) throws AcceleoEvaluationException {
		boolean fileExisted = generatedFile.exists();

		GeneratedFile file = getGeneratedFile(generatedFile, appendMode, charset);
		file.setCharset(charset);
		file.setFileBlock(getModuleElement(fileBlock));
		currentFiles.add(file);

		// Remove the traceability information of the path of the file
		ExpressionTrace<C> traceContext = recordedTraces.removeLast();
		for (Map.Entry<InputElement, Set<GeneratedText>> entry : traceContext.getTraces().entrySet()) {
			file.getSourceElements().add(entry.getKey());
			file.getNameRegions().addAll(entry.getValue());
		}
		traceContext.dispose();

		if (appendMode && fileExisted) {
			file.setLength(file.getLength() + 1);
		}

		super.createFileWriter(generatedFile, fileBlock, source, appendMode, charset);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#fitIndentationTo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public String fitIndentationTo(String source, String indentation) {
		boolean shouldBeIndented = source.trim().contains("\r\n") || source.trim().contains("\r") //$NON-NLS-1$ //$NON-NLS-2$
				|| source.trim().contains("\n"); //$NON-NLS-1$
		if ("".equals(indentation) || !shouldBeIndented) { //$NON-NLS-1$
			return source;
		}
		String regex = "\r\n|\r|\n"; //$NON-NLS-1$
		String replacement = "$0" + indentation; //$NON-NLS-1$

		EObject scopeEObject = retrieveScopeEObjectValue();

		InputElement input = getInputElement(scopeEObject);
		if (protectedAreaSource != null) {
			input = protectedAreaSource;
		}
		GeneratedText text = createGeneratedTextFor(currentExpression);
		text.setEndOffset(indentation.length());

		ExpressionTrace<C> indentationTrace = new ExpressionTrace<C>(currentExpression);
		indentationTrace.addTrace(input, text, indentation);

		String result = operationVisitor.visitReplaceOperation(source, regex, replacement, indentationTrace,
				true, true);
		indentationTrace.dispose();

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see AcceleoEvaluationVisitorDecorator#getCachedResult(Query, List)
	 */
	@Override
	public Object getCachedResult(Query query, List<Object> arguments) {
		ExpressionTrace<C> cachedTraces = queryTraceCache.getCachedTrace(query, arguments);
		if (cachedTraces != null) {
			// The query was already in cache, replace all of its invocation traces by the cached ones
			recordedTraces.removeLast();
			recordedTraces.add(new ExpressionTrace<C>(cachedTraces));
			return super.getCachedResult(query, arguments);
		}

		// We don't have traces for this query, force its reevaluation (bypass cache once)
		return QueryCache.NO_CACHED_RESULT;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoFileBlock(org.eclipse.acceleo.model.mtl.FileBlock)
	 */
	@Override
	public void visitAcceleoFileBlock(FileBlock fileBlock) {
		Deque<ExpressionTrace<C>> oldInvocationTraces = invocationTraces;
		invocationTraces = new CircularArrayDeque<ExpressionTrace<C>>();

		super.visitAcceleoFileBlock(fileBlock);

		invocationTraces = oldInvocationTraces;
		GeneratedFile file = currentFiles.removeLast();
		List<GeneratedText> regions = new ArrayList<GeneratedText>(file.getGeneratedRegions());
		Collections.sort(regions);
		int offset = 0;
		boolean hasProblems = false;
		for (GeneratedText region : regions) {
			if (region.getStartOffset() != offset) {
				hasProblems = true;
			}
			offset += region.getEndOffset() - region.getStartOffset();
			if (hasProblems) {
				break;
			}
		}

		if (!recordedTraces.isEmpty() && recordedTraces.getLast().getReferredExpression() == fileBlock
				&& recordedTraces.getLast().getTraces().isEmpty()) {
			recordedTraces.removeLast().dispose();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoForBlock(org.eclipse.acceleo.model.mtl.ForBlock)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void visitAcceleoForBlock(ForBlock forBlock) {
		if (forBlock.getLoopVariable() != null) {
			scopeEObjects.add(forBlock.getLoopVariable());
		}

		iterationTraces.add(new IterationTrace<C, PM>((Variable<C, PM>)forBlock.getLoopVariable(),
				(OCLExpression<C>)forBlock.getIterSet()));
		OCLExpression<C> oldIterationBody = iterationBody;
		if (!forBlock.getBody().isEmpty()) {
			iterationBody = (OCLExpression<C>)forBlock.getBody().get(forBlock.getBody().size() - 1);
		}
		iterationCount.add(Integer.valueOf(0));

		try {
			super.visitAcceleoForBlock(forBlock);
		} finally {
			iterationTraces.removeLast().dispose();
			iterationBody = oldIterationBody;
			iterationCount.removeLast();
		}

		if (forBlock.getLoopVariable() != null) {
			scopeEObjects.removeLast();
		}
		if (!recordedTraces.isEmpty() && recordedTraces.getLast().getReferredExpression() == forBlock
				&& recordedTraces.getLast().getTraces().isEmpty()) {
			recordedTraces.removeLast();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoIfBlock(org.eclipse.acceleo.model.mtl.IfBlock)
	 */
	@Override
	public void visitAcceleoIfBlock(IfBlock ifBlock) {
		super.visitAcceleoIfBlock(ifBlock);

		if (!recordedTraces.isEmpty() && recordedTraces.getLast().getReferredExpression() == ifBlock
				&& recordedTraces.getLast().getTraces().isEmpty()) {
			recordedTraces.removeLast();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoProtectedArea(org.eclipse.acceleo.model.mtl.ProtectedAreaBlock)
	 */
	@Override
	public void visitAcceleoProtectedArea(ProtectedAreaBlock protectedArea) {
		protectedAreaSource = getInputElement(retrieveScopeEObjectValue());
		protectedAreaModuleElement = getModuleElement(protectedArea);

		super.visitAcceleoProtectedArea(protectedArea);

		protectedAreaModuleElement = null;
		protectedAreaSource = null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoQueryInvocation(org.eclipse.acceleo.model.mtl.QueryInvocation)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object visitAcceleoQueryInvocation(QueryInvocation invocation) {
		if (invocation.getDefinition().getParameter().size() > 0) {
			scopeEObjects.add(invocation.getDefinition().getParameter().get(0));
		}

		// If this invocation isn't cached yet, we'll need to record all of its traces
		OCLExpression<C> expression = (OCLExpression<C>)invocation.getDefinition().getExpression();
		if (!isInitializingVariable()) {
			recordedTraces.add(new ExpressionTrace<C>(expression));
		}

		final Object result = super.visitAcceleoQueryInvocation(invocation);

		/*
		 * Query traces are cached, but the cache contains the traces of the very first invocation of this
		 * query. We need to change the cached instances of GeneratedText to the current GeneratedFile.
		 */
		if (!isInitializingVariable()) {
			final AbstractTrace queryTrace = recordedTraces.removeLast();
			if (!iterationTraces.isEmpty() && EcoreUtil.isAncestor(iterationTraces.getLast()
					.getReferredExpression(), invocation)) {
				iterationTraces.getLast().addTraceCopy(queryTrace);
			} else {
				ExpressionTrace<C> currentTrace = recordedTraces.getLast();
				currentTrace.addTraceCopy(queryTrace);
			}
			queryTrace.dispose();
		}

		if (invocation.getDefinition().getParameter().size() > 0) {
			scopeEObjects.removeLast();
		}

		if (isPropertyCallSource((OCLExpression<C>)invocation)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource((OCLExpression<C>)invocation)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoTemplate(org.eclipse.acceleo.model.mtl.Template)
	 */
	@Override
	public String visitAcceleoTemplate(Template template) {
		if (template.getParameter().size() > 0) {
			scopeEObjects.add(template.getParameter().get(0));
			addedTemplateScope = true;
		}
		return super.visitAcceleoTemplate(template);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoTemplateInvocation(org.eclipse.acceleo.model.mtl.TemplateInvocation)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object visitAcceleoTemplateInvocation(TemplateInvocation invocation) {
		Deque<ExpressionTrace<C>> oldTraces = invocationTraces;
		boolean oldTemplateHadScope = addedTemplateScope;
		addedTemplateScope = false;
		invocationTraces = new CircularArrayDeque<ExpressionTrace<C>>();

		Object result = null;
		final boolean oldRecordState = switchRecordState((OCLExpression<C>)invocation);
		try {
			result = super.visitAcceleoTemplateInvocation(invocation);
		} finally {
			record = oldRecordState;

			if (oldTraces != null && invocationTraces != null) {
				for (ExpressionTrace<C> trace : invocationTraces) {
					if (!oldTraces.contains(trace)) {
						oldTraces.add(trace);
					}
				}
			}
			invocationTraces = oldTraces;
			if (addedTemplateScope) {
				scopeEObjects.removeLast();
			}
			addedTemplateScope = oldTemplateHadScope;
		}

		if (isPropertyCallSource((OCLExpression<C>)invocation)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource((OCLExpression<C>)invocation)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitAssociationClassCallExp(org.eclipse.ocl.expressions.AssociationClassCallExp)
	 */
	@Override
	public Object visitAssociationClassCallExp(AssociationClassCallExp<C, P> callExp) {
		final Object result = super.visitAssociationClassCallExp(callExp);

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitBooleanLiteralExp(org.eclipse.ocl.expressions.BooleanLiteralExp)
	 */
	@Override
	public Object visitBooleanLiteralExp(BooleanLiteralExp<C> literalExp) {
		final Object result = super.visitBooleanLiteralExp(literalExp);

		recordLiteral(literalExp, result);

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitCollectionLiteralExp(org.eclipse.ocl.expressions.CollectionLiteralExp)
	 */
	@Override
	public Object visitCollectionLiteralExp(CollectionLiteralExp<C> literalExp) {
		final Object result = super.visitCollectionLiteralExp(literalExp);

		if (isPropertyCallSource(literalExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(literalExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitEnumLiteralExp(org.eclipse.ocl.expressions.EnumLiteralExp)
	 */
	@Override
	public Object visitEnumLiteralExp(EnumLiteralExp<C, EL> literalExp) {
		final Object result = super.visitEnumLiteralExp(literalExp);

		recordLiteral(literalExp, result);

		if (isPropertyCallSource(literalExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(literalExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitExpression(org.eclipse.ocl.expressions.OCLExpression)
	 */
	@Override
	public Object visitExpression(OCLExpression<C> expression) {
		OCLExpression<C> oldExpression = currentExpression;
		currentExpression = expression;

		// We are evaluating a main template's guard
		boolean isEvaluatingMainTemplateGuard = scopeEObjects.isEmpty() && expression != null && expression
				.eContainer() instanceof Template && expression.eContainingFeature() == MtlPackage.eINSTANCE
						.getTemplate_Guard();
		// FIXME keeps compiler happy, but means we might get NPEs here
		assert expression != null;
		if (isEvaluatingMainTemplateGuard) {
			for (org.eclipse.ocl.ecore.Variable var : ((Template)expression.eContainer()).getParameter()) {
				Object value = getEvaluationEnvironment().getValueOf(var.getName());
				if (value instanceof EObject) {
					scopeEObjects.add((EObject)value);
					break;
				}
			}
		}

		// Very first call of a template comes from IAcceleoEngine#doEvaluate()
		if (scopeEObjects.isEmpty() && expression instanceof Template) {
			for (org.eclipse.ocl.ecore.Variable var : ((Template)expression).getParameter()) {
				Object value = getEvaluationEnvironment().getValueOf(var.getName());
				if (value instanceof EObject) {
					scopeEObjects.add((EObject)value);
					break;
				}
			}
		}

		Deque<OCLExpression<C>> expressionStack = this.evaluationVisitor.getContext().getExpressionStack();
		if ((expression instanceof TemplateInvocation
				|| expression instanceof org.eclipse.ocl.ecore.StringLiteralExp) && expressionStack
						.getLast() instanceof FileBlock) {
			ExpressionTrace<C> trace = new ExpressionTrace<C>(expression);
			recordedTraces.add(trace);
		}

		if (expression.eContainer() instanceof FileBlock && expression.eContainingFeature().equals(
				MtlPackage.eINSTANCE.getBlock_Body()) && ((FileBlock)expression.eContainer()).getBody()
						.indexOf(expression) == 0) {
			// If we have the first expression of a block, let's create a trace
			ExpressionTrace<C> trace = new ExpressionTrace<C>(expression);
			recordedTraces.add(trace);
		}

		boolean oldRecordingValue = switchRecordState(expression);
		boolean oldIterSet = evaluatingIterationSet;
		evaluatingIterationSet = expression.eContainingFeature() == MtlPackage.eINSTANCE
				.getForBlock_IterSet();
		boolean oldEvaluatingPostCall = evaluatingPostCall;
		evaluatingPostCall = evaluatingPostCall || expression.eContainingFeature() == MtlPackage.eINSTANCE
				.getTemplate_Post();
		final EReference containingFeature = (EReference)expression.eContainingFeature();
		boolean isOperationArgumentTrace = evaluatingOperationCall && shouldRecordOperationTrace(expression);
		if (isOperationArgumentTrace || !evaluatingOperationCall && shouldRecordTrace(containingFeature)) {
			ExpressionTrace<C> trace = new ExpressionTrace<C>(expression);
			recordedTraces.add(trace);
			if (invocationTraces != null) {
				invocationTraces.add(trace);
			}
		} else if (shouldRecordTrace(containingFeature) && invocationTraces != null && recordedTraces
				.size() > 0) {
			final ExpressionTrace<C> trace = recordedTraces.getLast();
			if (!invocationTraces.contains(trace)) {
				invocationTraces.add(trace);
			}
		}
		Object result = null;
		try {
			// Intercept OCL iterator bodies
			if (iterationBody == expression && expression.eContainingFeature() != MtlPackage.eINSTANCE
					.getBlock_Body()) {
				result = visitIteratorBody(expression);
			} else {
				result = getDelegate().visitExpression(expression);
			}
		} catch (AcceleoEvaluationCancelledException e) {
			cancel();
			throw e;
		} finally {
			record = oldRecordingValue;
			evaluatingIterationSet = oldIterSet;
			evaluatingPostCall = oldEvaluatingPostCall;
			// move back the argument trace into its corresponding operation's
			if (isOperationArgumentTrace && recordedTraces.size() > 1) {
				ExpressionTrace<C> argTrace = recordedTraces.removeLast();
				recordedTraces.getLast().addTraceCopy(argTrace);
				if (invocationTraces != null && (!(expression instanceof OperationCallExp<?, ?>)
						|| invocationTraces.size() > 1)) {
					invocationTraces.removeLast();
					argTrace.dispose();
				}
			}
			// Advance Acceleo iterator (for loops) iteration count
			if (iterationBody == expression && expression.eContainingFeature() == MtlPackage.eINSTANCE
					.getBlock_Body()) {
				final Integer current = iterationCount.removeLast();
				iterationCount.add(Integer.valueOf(current.intValue() + 1));
			}
			// protected area markers will be evaluated twice. The first traces must be ignored
			if (expression.eContainingFeature() == MtlPackage.eINSTANCE.getProtectedAreaBlock_Marker()
					&& recordedTraces.size() >= 2) {
				if (recordedTraces.get(recordedTraces.size() - 2)
						.getReferredExpression() instanceof ProtectedAreaBlock) {
					final ExpressionTrace<C> firstMarkerTrace = recordedTraces.removeLast();
					if (invocationTraces != null) {
						invocationTraces.remove(firstMarkerTrace);
					}
					firstMarkerTrace.dispose();
				}
			}
		}

		if (isPropertyCallSource(expression)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(expression)) {
			operationCallSource = result;
		}
		if (expression.eContainingFeature() == MtlPackage.eINSTANCE.getProtectedAreaBlock_Marker()
				&& recordedTraces.getLast().getReferredExpression() == expression) {
			ExpressionTrace<C> trace = recordedTraces.getLast();
			int gap = -1;
			for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
				for (GeneratedText text : entry.getValue()) {
					if (gap == -1 || text.getStartOffset() < gap) {
						gap = text.getStartOffset();
					}
				}
			}
			operationVisitor.visitTrimOperation((String)result, gap);
		}
		currentExpression = oldExpression;
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitIfExp(org.eclipse.ocl.expressions.IfExp)
	 */
	@Override
	public Object visitIfExp(IfExp<C> ifExp) {
		final Object result = super.visitIfExp(ifExp);

		if (isPropertyCallSource(ifExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(ifExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitIntegerLiteralExp(org.eclipse.ocl.expressions.IntegerLiteralExp)
	 */
	@Override
	public Object visitIntegerLiteralExp(IntegerLiteralExp<C> literalExp) {
		final Object result = super.visitIntegerLiteralExp(literalExp);

		recordLiteral(literalExp, result);

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitIterateExp(org.eclipse.ocl.expressions.IterateExp)
	 */
	@Override
	public Object visitIterateExp(IterateExp<C, PM> callExp) {
		scopeEObjects.add(callExp.getIterator().get(0));

		final Object result = super.visitIterateExp(callExp);

		scopeEObjects.removeLast();

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * We'll delegate all calls to an iterator body's evaluation to this method in order to intercept and
	 * modify the trace information as needed.
	 * 
	 * @param iteratorBody
	 *            Body of the iterator which is to be evaluated.
	 * @return The result of this iterator body, after we've taken care of modifying the trace.
	 */
	public Object visitIteratorBody(OCLExpression<C> iteratorBody) {
		int opCode = OCLStandardLibraryUtil.getOperationCode(((IteratorExp<?, ?>)iteratorBody.eContainer())
				.getName());
		if (opCode == PredefinedType.COLLECT || opCode == PredefinedType.COLLECT_NESTED) {
			/*
			 * Add another trace for these so that we can avoid modifying them as a whole if the iteration
			 * body has an iterator expression
			 */
			recordedTraces.add(new ExpressionTrace<C>(iteratorBody));
		}

		Object result = null;
		try {
			result = getDelegate().visitExpression(iteratorBody);
		} finally {
			switch (opCode) {
				case PredefinedType.COLLECT:
				case PredefinedType.COLLECT_NESTED:
					// Merge the iteration body trace with the remainder of the iteration traces
					if (recordedTraces.size() > 1) {
						ExpressionTrace<C> last = recordedTraces.removeLast();
						recordedTraces.getLast().addTraceCopy(last);
						last.dispose();
					}
					break;
				case PredefinedType.SELECT:
					if (result instanceof Boolean && ((Boolean)result).booleanValue() && !iterationTraces
							.isEmpty() && iterationTraces.getLast().getTracesForIteration() != null) {
						ExpressionTrace<C> trace = recordedTraces.getLast();
						for (Map.Entry<InputElement, Set<GeneratedText>> entry : iterationTraces.getLast()
								.getTracesForIteration().entrySet()) {
							trace.mergeTrace(entry.getKey(), entry.getValue());
						}
					}
					break;
				case PredefinedType.REJECT:
					if (result instanceof Boolean && !((Boolean)result).booleanValue() && !iterationTraces
							.isEmpty() && iterationTraces.getLast().getTracesForIteration() != null) {
						ExpressionTrace<C> trace = recordedTraces.getLast();
						for (Map.Entry<InputElement, Set<GeneratedText>> entry : iterationTraces.getLast()
								.getTracesForIteration().entrySet()) {
							trace.mergeTrace(entry.getKey(), entry.getValue());
						}
					}
					break;
				case PredefinedType.ANY:
					if (result instanceof Boolean && ((Boolean)result).booleanValue() && !iterationTraces
							.isEmpty() && iterationTraces.getLast().getTracesForIteration() != null) {
						ExpressionTrace<C> trace = recordedTraces.getLast();
						for (Map.Entry<InputElement, Set<GeneratedText>> entry : iterationTraces.getLast()
								.getTracesForIteration().entrySet()) {
							trace.mergeTrace(entry.getKey(), entry.getValue());
						}
					}
					break;
				// FIXME handle the other iterators
				default:
					break;
			}
			final Integer current = iterationCount.removeLast();
			iterationCount.add(Integer.valueOf(current.intValue() + 1));
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitIteratorExp(org.eclipse.ocl.expressions.IteratorExp)
	 */
	@Override
	public Object visitIteratorExp(IteratorExp<C, PM> callExp) {
		boolean oldOperationEvaluationState = evaluatingOperationCall;
		evaluatingOperationCall = true;
		scopeEObjects.add(callExp.getIterator().get(0));
		iterationTraces.add(new IterationTrace<C, PM>(callExp.getIterator().get(0), callExp.getSource()));
		OCLExpression<C> oldIterationBody = iterationBody;
		iterationBody = callExp.getBody();
		iterationCount.add(Integer.valueOf(0));

		// Iterator expressions should have their own traces in case they are themselves an iterator source
		recordedTraces.add(new ExpressionTrace<C>(callExp));

		Object result = null;
		try {
			result = super.visitIteratorExp(callExp);
			// CHECKSTYLE:OFF
		} catch (Throwable t) {
			// CHECKSTYLE:ON

			// Yeah, I know it's dirty
			throw new AcceleoEvaluationException(AcceleoTraceabilityMessages.getString(
					"AcceleoTraceabilityVisitor.NullEvaluation", callExp), t); //$NON-NLS-1$
		} finally {
			evaluatingOperationCall = oldOperationEvaluationState;
			if (recordedTraces.size() > 0) {
				ExpressionTrace<C> traces = recordedTraces.removeLast();
				IterationTrace<C, PM> iterTrace = iterationTraces.removeLast();
				if (!iterationTraces.isEmpty() && (iterationTraces.getLast()
						.getReferredExpression() == callExp || iterationTraces.getLast()
								.getReferredExpression() == callExp.eContainer())) {
					iterationTraces.getLast().addTraceCopy(traces);
				} else if (recordedTraces.size() > 0) {
					recordedTraces.getLast().addTraceCopy(traces);
				}
				traces.dispose();
				iterTrace.dispose();
				iterationBody = oldIterationBody;
				iterationCount.removeLast();
			}
		}

		scopeEObjects.removeLast();

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitLetExp(org.eclipse.ocl.expressions.LetExp)
	 */
	@Override
	public Object visitLetExp(LetExp<C, PM> letExp) {
		scopeEObjects.add(letExp.getVariable());

		final Object result = super.visitLetExp(letExp);

		scopeEObjects.removeLast();

		if (isPropertyCallSource(letExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(letExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitOperationCallExp(org.eclipse.ocl.expressions.OperationCallExp)
	 */
	@Override
	public Object visitOperationCallExp(OperationCallExp<C, O> callExp) {
		OCLExpression<C> oldOperationCallSourceExpression = operationCallSourceExpression;
		operationCallSourceExpression = callExp.getSource();

		boolean oldOperationEvaluationState = evaluatingOperationCall;
		boolean oldRecordingValue = switchRecordState(callExp);
		evaluatingOperationCall = true;

		final Object result;

		boolean isGuard = callExp.eContainingFeature().getFeatureID() == MtlPackage.TEMPLATE__GUARD;
		isGuard = isGuard && callExp.eContainer() instanceof Template;
		try {
			if (record && !isGuard && isTraceabilityImpactingOperation(callExp)) {
				// Boolean and Integer returning operations evaluation won't be intercepted
				// But we'll alter their trace information
				if (isBooleanReturningOperation(callExp)) {
					result = super.visitOperationCallExp(callExp);
					// for non-primitive sources, we'll need to create our own trace
					ModuleElement moduleElement = getModuleElement(callExp);
					Object source = operationCallSource;
					if (source instanceof Collection<?> || source == null) {
						source = retrieveScopeEObjectValue();
					}
					operationVisitor.changeTraceabilityIndicesBooleanReturn(((Boolean)result).booleanValue(),
							source, moduleElement);
				} else if (isNumberReturningOperation(callExp)) {
					result = super.visitOperationCallExp(callExp);
					// for non-primitive sources, we'll need to create our own trace
					ModuleElement moduleElement = getModuleElement(callExp);
					Object source = operationCallSource;
					if (source instanceof Collection<?> || source == null) {
						source = retrieveScopeEObjectValue();
					}
					operationVisitor.changeTraceabilityIndicesNumberReturn((Number)result, source,
							moduleElement);
				} else {
					result = internalVisitOperationCallExp(callExp);
				}
			} else {
				// getProperty is special as it "creates" new traces, and the argument traces should be
				// ignored.
				boolean isGetProperty = AcceleoNonStandardLibrary.OPERATION_OCLANY_GETPROPERTY.equals(
						((EOperation)callExp.getReferredOperation()).getName());
				final boolean oldRecord = record;
				if (isGetProperty) {
					record = false;
				}
				result = super.visitOperationCallExp(callExp);
				if (isGetProperty) {
					record = oldRecord;
				}
				if (record && !isGuard && isGetProperty) {
					recordLiteral(callExp, result);
				}
			}
		} finally {
			record = oldRecordingValue;
			evaluatingOperationCall = oldOperationEvaluationState;
		}

		operationCallSource = null;
		operationCallSourceExpression = oldOperationCallSourceExpression;

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitPropertyCallExp(org.eclipse.ocl.expressions.PropertyCallExp)
	 */
	@Override
	public Object visitPropertyCallExp(PropertyCallExp<C, P> callExp) {
		OCLExpression<C> oldPropertyCallSourceExpression = propertyCallSourceExpression;
		propertyCallSourceExpression = callExp.getSource();

		Object result = null;
		boolean oldRecordingValue = switchRecordState(callExp);
		try {
			result = getDelegate().visitPropertyCallExp(callExp);
		} finally {
			if (propertyCallSource != null && result != null && TraceabilityVisitorUtil.isPrimitive(result)) {
				InputElement propertyCallInput = getInputElement(propertyCallSource,
						(EStructuralFeature)callExp.getReferredProperty());
				propertyCallSource = null;

				if (protectedAreaSource != null) {
					propertyCallInput = protectedAreaSource;
				}
				if (operationArgumentTrace != null) {
					GeneratedText text = createGeneratedTextFor(callExp);
					operationArgumentTrace.addTrace(propertyCallInput, text, result);
				} else if (isInitializingVariable()) {
					GeneratedText text = createGeneratedTextFor(callExp);
					variableTraces.get(initializingVariable).addTrace(propertyCallInput, text, result);
				} else if (record && !recordedTraces.isEmpty() && shouldRecordTrace(callExp)) {
					GeneratedText text = createGeneratedTextFor(callExp);
					recordedTraces.getLast().addTrace(propertyCallInput, text, result);
				} else if (!iterationTraces.isEmpty() && shouldRecordTrace(callExp)) {
					GeneratedText text = createGeneratedTextFor(callExp);
					iterationTraces.getLast().addTrace(propertyCallInput, text, result);
				}
			}

			propertyCallSourceExpression = oldPropertyCallSourceExpression;
			record = oldRecordingValue;
		}

		if (isPropertyCallSource(callExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(callExp)) {
			operationCallSource = result;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitRealLiteralExp(org.eclipse.ocl.expressions.RealLiteralExp)
	 */
	@Override
	public Object visitRealLiteralExp(RealLiteralExp<C> literalExp) {
		final Object result = super.visitRealLiteralExp(literalExp);

		recordLiteral(literalExp, result);

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitStateExp(org.eclipse.ocl.expressions.StateExp)
	 */
	@Override
	public Object visitStateExp(StateExp<C, S> stateExp) {
		final Object result = super.visitStateExp(stateExp);

		if (isPropertyCallSource(stateExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(stateExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitStringLiteralExp(org.eclipse.ocl.expressions.StringLiteralExp)
	 */
	@Override
	public Object visitStringLiteralExp(StringLiteralExp<C> literalExp) {
		final Object result = super.visitStringLiteralExp(literalExp);

		recordLiteral(literalExp, result);

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitVariable(org.eclipse.ocl.expressions.Variable)
	 */
	@Override
	public Object visitVariable(Variable<C, PM> variable) {
		final boolean isPrimitive = TraceabilityVisitorUtil.isPrimitive(variable.getType())
				|| TraceabilityVisitorUtil.isPrimitiveCollection((EClassifier)variable.getType());
		Variable<C, PM> oldVar = null;
		if (isPrimitive) {
			variableTraces.put(variable, new VariableTrace<C, PM>(variable));
			oldVar = initializingVariable;
			initializingVariable = variable;
		}

		final Object result = getDelegate().visitVariable(variable);

		if (scopeEObjects.getLast() == variable) {
			if (result instanceof EObject) {
				scopeEObjects.removeLast();
				scopeEObjects.add((EObject)result);
			} else {
				/*
				 * we won't remove the "variable" scope object; it'll be removed when we exit the current
				 * scope.
				 */
			}
		}

		if (isPrimitive) {
			initializingVariable = oldVar;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitVariableExp(org.eclipse.ocl.expressions.VariableExp)
	 */
	@Override
	public Object visitVariableExp(VariableExp<C, PM> variableExp) {
		final Object result = super.visitVariableExp(variableExp);

		boolean recordOperationArgument = operationArgumentTrace != null && result instanceof String;
		boolean recordVariableInitialization = isInitializingVariable() && shouldRecordTrace(variableExp)
				&& (variableTraces.get(variableExp.getReferredVariable()) != null || initializingVariable
						.getInitExpression() == variableExp);
		boolean recordTrace = !isInitializingVariable() && !recordedTraces.isEmpty()
				&& TraceabilityVisitorUtil.isPrimitive(result) && result.toString().length() > 0;

		// Whether we'll record them or not, advance the iterator traces if needed
		VariableTrace<C, PM> referredVarTrace = variableTraces.get(variableExp.getReferredVariable());

		final ListIterator<IterationTrace<C, PM>> iterator = iterationTraces.listIterator(iterationTraces
				.size());
		final boolean isSelf = "self".equals(variableExp.getReferredVariable().getName()); //$NON-NLS-1$
		boolean tracesFound = false;
		while (!tracesFound && iterator.hasPrevious()) {
			final IterationTrace<C, PM> trace = iterator.previous();
			if (isSelf || variableExp.getReferredVariable() == trace.getVariable()) {
				tracesFound = true;

				if (trace.getLastIteration() != iterationCount.getLast().intValue() && result != null) {
					trace.advanceIteration(result.toString());
				}

				referredVarTrace = new VariableTrace<C, PM>(variableExp.getReferredVariable());
				if (trace.getTracesForIteration() != null) {
					for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTracesForIteration()
							.entrySet()) {
						referredVarTrace.getTraces().put(entry.getKey(), entry.getValue());
					}
				}
			}
		}

		if (recordVariableInitialization || (record && recordTrace) || recordOperationArgument) {
			if (referredVarTrace != null) {
				for (Map.Entry<InputElement, Set<GeneratedText>> entry : referredVarTrace.getTraces()
						.entrySet()) {
					InputElement input = entry.getKey();
					if (protectedAreaSource != null) {
						input = protectedAreaSource;
					}
					for (GeneratedText text : entry.getValue()) {
						// 3.4 compatibility : EcoreUtil.copy() wasn't generic. The cast is necessary
						@SuppressWarnings("cast")
						GeneratedText copy = (GeneratedText)EcoreUtil.copy(text);
						int regionLength = copy.getEndOffset() - copy.getStartOffset();
						if (recordOperationArgument) {
							operationArgumentTrace.addTrace(input, copy, regionLength);
						} else if (recordTrace) {
							recordedTraces.getLast().addTrace(input, copy, regionLength);
						} else if (recordVariableInitialization) {
							variableTraces.get(initializingVariable).addTrace(input, copy, regionLength);
						}
					}
				}
			} else {
				final InputElement input;
				if (protectedAreaSource == null) {
					input = getInputElement(retrieveScopeEObjectValue(0));
				} else {
					input = protectedAreaSource;
				}
				if (recordOperationArgument) {
					GeneratedText text = createGeneratedTextFor(variableExp);
					operationArgumentTrace.addTrace(input, text, result);
				} else if (recordTrace && !isEvaluatingPostCall()) {
					GeneratedText text = createGeneratedTextFor(variableExp);
					recordedTraces.getLast().addTrace(input, text, result);
				} else if (recordVariableInitialization) {
					GeneratedText text = createGeneratedTextFor(variableExp);
					variableTraces.get(initializingVariable).addTrace(input, text, result);
				}
			}
		}

		if (isPropertyCallSource(variableExp)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(variableExp)) {
			operationCallSource = result;
		}
		return result;
	}

	/**
	 * Returns the stack of generated files.
	 * 
	 * @return The stack of generated files.
	 */
	Deque<GeneratedFile> getCurrentFiles() {
		return currentFiles;
	}

	public Deque<ExpressionTrace<C>> getRecordedTraces() {
		return recordedTraces;
	}

	/**
	 * Returns the traces of the current variable initialization.
	 * 
	 * @return The traces of the current variable initialization.
	 */
	AbstractTrace getInitializingVariableTrace() {
		return variableTraces.get(initializingVariable);
	}

	/**
	 * This will return an InputElement contained within {@link #evaluationTrace} corresponding to the given
	 * model element and neither structural feature nor operation linked. Note that it will be created if
	 * needed.
	 * 
	 * @param modelElement
	 *            Model element we seek the corresponding InputElement of.
	 * @return {@link InputElement} contained in the {@link #evaluationTrace} model.
	 */
	InputElement getInputElement(EObject modelElement) {
		ModelFile soughtModel = getModelFile(modelElement);

		Set<InputElement> candidateInputs = cachedInputElements.get(modelElement);
		if (candidateInputs == null) {
			candidateInputs = new CompactHashSet<InputElement>();
			cachedInputElements.put(modelElement, candidateInputs);
		}
		for (InputElement input : candidateInputs) {
			if (input.getFeature() == null && input.getOperation() == null) {
				return input;
			}
		}

		// If we're here, such an InputElement does not already exist
		InputElement soughtElement = TraceabilityFactory.eINSTANCE.createInputElement();
		soughtElement.setModelElement(modelElement);
		soughtModel.getInputElements().add(soughtElement);
		candidateInputs.add(soughtElement);
		return soughtElement;
	}

	/**
	 * Returns the last invocation's recorded traces.
	 * 
	 * @return The last invocation's recorded traces.
	 */
	Deque<ExpressionTrace<C>> getInvocationTraces() {
		return invocationTraces;
	}

	/**
	 * Returns the last recorded expression trace.
	 * 
	 * @return The last recorded expression trace.
	 */
	AbstractTrace getLastExpressionTrace() {
		return recordedTraces.getLast();
	}

	/**
	 * Returns the value of {@link #evaluatingPostCall}. Package visibility as this is only meant for the
	 * {@link AcceleoTraceabilityOperationVisitor} to know which traces to consider.
	 * 
	 * @return The value of {@link #evaluatingPostCall}.
	 */
	boolean isEvaluatingPostCall() {
		return evaluatingPostCall;
	}

	/**
	 * Returns the value of {@link #initializingVariable}. Package visibility as this is only meant for the
	 * {@link AcceleoTraceabilityOperationVisitor} to know which traces to consider.
	 * 
	 * @return The value of {@link #initializingVariable}.
	 */
	boolean isInitializingVariable() {
		return initializingVariable != null;
	}

	/**
	 * Retrieve the scope value of the currently evaluated expression.
	 * 
	 * @return The scope value of the currently evaluated expression.
	 */
	EObject retrieveScopeEObjectValue() {
		return retrieveScopeEObjectValue(scopeEObjects.size() - 1);
	}

	/**
	 * This will be used to create the evaluation trace for a given protected area's content.
	 * 
	 * @param content
	 *            Full content of the protected area, starting/ending strings and marker included.
	 * @param protectedArea
	 *            The {@link ProtectedAreaBlock} from which we're generating <em>content</em>.
	 * @param trace
	 *            The execution trace for this area.
	 */
	private void createProtectedAreaTrace(String content, Block protectedArea, ExpressionTrace<C> trace) {
		final String actualContent = AcceleoEvaluationVisitor.removeProtectedMarkers(content);

		final String areaStart = AcceleoEngineMessages.getString("usercode.start") + ' '; //$NON-NLS-1$
		final String areaEnd = AcceleoEngineMessages.getString("usercode.end"); //$NON-NLS-1$
		final int markerIndex = actualContent.indexOf(areaStart) + areaStart.length();
		final int areaEndIndex = actualContent.indexOf(areaEnd);

		int markerEndIndex = actualContent.indexOf("\r\n", markerIndex); //$NON-NLS-1$
		if (markerEndIndex == -1) {
			markerEndIndex = actualContent.indexOf('\n', markerIndex);
		}
		if (markerEndIndex == -1) {
			markerEndIndex = actualContent.indexOf('\r', markerIndex);
		}

		int endOffset = -1;

		final GeneratedText markerRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
		markerRegion.setStartOffset(markerIndex);
		markerRegion.setEndOffset(markerEndIndex);
		markerRegion.setModuleElement(getModuleElement(protectedArea));
		markerRegion.setSourceElement(protectedAreaSource);

		// Create a region containing all text preceding the marker
		final GeneratedText startRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
		startRegion.setEndOffset(markerIndex);
		startRegion.setModuleElement(getModuleElement(protectedArea));
		startRegion.setSourceElement(protectedAreaSource);

		// Create traces for the content of the area
		GeneratedText contentRegion = null;
		if (endOffset != areaEndIndex) {
			contentRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
			contentRegion.setStartOffset(markerEndIndex);
			contentRegion.setEndOffset(areaEndIndex);
			contentRegion.setModuleElement(getModuleElement(protectedArea));
			contentRegion.setSourceElement(protectedAreaSource);
		}

		// Then a region containing the "end of user code"
		final GeneratedText endRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
		endRegion.setStartOffset(areaEndIndex);
		endRegion.setEndOffset(actualContent.length());
		endRegion.setModuleElement(getModuleElement(protectedArea));
		endRegion.setSourceElement(protectedAreaSource);

		// Create the region set
		Set<GeneratedText> set = new CompactLinkedHashSet<GeneratedText>();
		trace.getTraces().put(protectedAreaSource, set);

		set.add(startRegion);
		set.add(markerRegion);
		if (contentRegion != null) {
			set.add(contentRegion);
		}
		set.add(endRegion);
	}

	/**
	 * This will be called when the evaluation has been cancelled somehow.
	 */
	private void cancel() {
		currentExpression = null;
		if (currentFiles != null) {
			currentFiles.clear();
			currentFiles = null;
		}
		initializingVariable = null;
		if (invocationTraces != null) {
			for (ExpressionTrace<C> trace : invocationTraces) {
				trace.dispose();
			}
			invocationTraces.clear();
			invocationTraces = null;
		}
		if (operationArgumentTrace != null) {
			operationArgumentTrace.dispose();
			operationArgumentTrace = null;
		}
		operationCallSource = null;
		operationCallSourceExpression = null;
		propertyCallSource = null;
		propertyCallSourceExpression = null;
		protectedAreaSource = null;
		record = true;
		for (ExpressionTrace<C> trace : recordedTraces) {
			trace.dispose();
		}
		recordedTraces.clear();
		if (scopeEObjects != null) {
			scopeEObjects.clear();
		}
		for (VariableTrace<C, PM> trace : variableTraces.values()) {
			trace.dispose();
		}
		variableTraces.clear();
		for (IterationTrace<C, PM> trace : iterationTraces) {
			trace.dispose();
		}
		iterationTraces.clear();
		iterationCount.clear();
	}

	/**
	 * Creates both the {@link ModuleElement} instance and a {@link GeneratedText} for the given
	 * <em>moduleElement</em>.
	 * 
	 * @param moduleElement
	 *            Element from the Acceleo module for which we need a GeneratedText created.
	 * @return A new {@link GeneratedText} for the given <em>moduleElement</em>.
	 */
	private GeneratedText createGeneratedTextFor(EObject moduleElement) {
		ModuleElement modElement = getModuleElement(moduleElement);
		GeneratedText text = TraceabilityFactory.eINSTANCE.createGeneratedText();
		text.setModuleElement(modElement);
		return text;
	}

	/**
	 * Returns an existing GeneratedFile in the trace model, creating it if needed.
	 * 
	 * @param generatedFile
	 *            File that is to be created.
	 * @param appendMode
	 *            If <code>true</code>, we need to retrieve the current file length if it exists.
	 * @param charset
	 *            Charset of the file.
	 * @return {@link GeneratedFile} contained in the {@link #evaluationTrace} model.
	 */
	private GeneratedFile getGeneratedFile(File generatedFile, boolean appendMode, String charset) {
		GeneratedFile soughtFile = evaluationTrace.getGeneratedFile(generatedFile.getPath());
		if (soughtFile == null) {
			soughtFile = TraceabilityFactory.eINSTANCE.createGeneratedFile();
			soughtFile.setPath(generatedFile.getPath());
			soughtFile.setName(stripFileNameFrom(generatedFile.getPath()));
			if (appendMode && generatedFile.exists() && generatedFile.canRead()) {
				int length = 0;
				BufferedReader reader = null;
				try {
					if (charset == null) {
						reader = new BufferedReader(new FileReader(generatedFile));
					} else {
						reader = new BufferedReader(new InputStreamReader(new FileInputStream(generatedFile),
								charset));
					}
					String line = reader.readLine();
					while (line != null) {
						// add 1 for the carriage return
						length += line.length() + 1;
						line = reader.readLine();
					}
				} catch (IOException e) {
					// traceability may not be good on this one
					AcceleoTraceabilityPlugin.log(e, false);
				} finally {
					try {
						if (reader != null) {
							reader.close();
						}
					} catch (IOException e) {
						AcceleoTraceabilityPlugin.log(e, false);
					}
				}
				soughtFile.setLength(length);
			}
			evaluationTrace.getGeneratedFiles().add(soughtFile);
		}
		return soughtFile;
	}

	/**
	 * This will return an InputElement contained within {@link #evaluationTrace} corresponding to the given
	 * model element and structural feature. Note that it will be created if needed.
	 * 
	 * @param modelElement
	 *            Model element we seek the corresponding InputElement of.
	 * @param feature
	 *            Structural feature of this input element. Can be <code>null</code>.
	 * @return {@link InputElement} contained in the {@link #evaluationTrace} model.
	 */
	private InputElement getInputElement(EObject modelElement, EStructuralFeature feature) {
		ModelFile soughtModel = getModelFile(modelElement);

		Set<InputElement> candidateInputs = cachedInputElements.get(modelElement);
		if (candidateInputs == null) {
			candidateInputs = new CompactHashSet<InputElement>();
			cachedInputElements.put(modelElement, candidateInputs);
		}
		for (InputElement input : candidateInputs) {
			if (input.getFeature() == feature) {
				return input;
			}
		}

		// If we're here, such an InputElement does not already exist
		InputElement soughtElement = TraceabilityFactory.eINSTANCE.createInputElement();
		soughtElement.setModelElement(modelElement);
		soughtElement.setFeature(feature);
		soughtModel.getInputElements().add(soughtElement);
		candidateInputs.add(soughtElement);
		return soughtElement;
	}

	/**
	 * Returns an existing ModelFile for the given model element, creating it if needed.
	 * 
	 * @param modelElement
	 *            Model element we need the {@link ModelFile} of.
	 * @return {@link ModelFile} contained in the {@link #evaluationTrace} model.
	 */
	private ModelFile getModelFile(EObject modelElement) {
		final URI modelURI;
		if (modelElement.eResource() != null) {
			modelURI = modelElement.eResource().getURI();
		} else if (modelElement.eIsProxy()) {
			modelURI = ((InternalEObject)modelElement).eProxyURI().trimFragment();
		} else {
			throw new IllegalArgumentException(AcceleoTraceabilityMessages.getString(
					"AcceleoTraceabilityVisitor.MissingResource")); //$NON-NLS-1$
		}
		final String name = modelURI.lastSegment();
		String path = modelURI.toString();

		if (path.startsWith("http://") && EMFPlugin.IS_ECLIPSE_RUNNING) { //$NON-NLS-1$
			if (ecoreURLCache.containsKey(path)) {
				path = ecoreURLCache.get(path);
			} else {
				String nsURI = path;
				EPackage pack = EPackage.Registry.INSTANCE.getEPackage(nsURI);
				try {
					URL ecoreURL = AcceleoWorkspaceUtil.getResourceURL(pack.getClass(), "*.ecore"); //$NON-NLS-1$
					if (ecoreURL != null) {
						path = ecoreURL.toString();
					}
				} catch (IOException e) {
					AcceleoTraceabilityPlugin.log(e, false);
				}
				ecoreURLCache.put(nsURI, path);
			}
		}

		ModelFile soughtModel = evaluationTrace.getInputModel(path);
		if (soughtModel == null) {
			soughtModel = TraceabilityFactory.eINSTANCE.createModelFile();
			soughtModel.setPath(path);
			soughtModel.setName(name);
			evaluationTrace.getModelFiles().add(soughtModel);
		}
		return soughtModel;
	}

	/**
	 * This will return a {@link ModuleElement} contained within {@link #evaluationTrace} corresponding to the
	 * given module element. Note that it will be created if needed.
	 * 
	 * @param moduleElement
	 *            The module element we need a {@link ModuleElement} instance for.
	 * @return {@link ModuleElement} contained in the {@link #evaluationTrace} model.
	 */
	private ModuleElement getModuleElement(EObject moduleElement) {
		if (moduleElement instanceof StringLiteralExp<?> && moduleElement.eContainer().eContainer() == null) {
			return protectedAreaModuleElement;
		}

		ModuleFile soughtModule = getModuleFile(moduleElement);

		ModuleElement element = cachedModuleElements.get(moduleElement);
		if (element == null) {
			// If we're here, such a ModuleElement does not already exist
			element = TraceabilityFactory.eINSTANCE.createModuleElement();
			element.setModuleElement(moduleElement);
			soughtModule.getModuleElements().add(element);
			cachedModuleElements.put(moduleElement, element);
		}
		return element;
	}

	/**
	 * Returns an existing ModuleFile for the given module element, creating it if needed.
	 * 
	 * @param moduleElement
	 *            Module element we need the {@link ModuleFile} of.
	 * @return {@link ModuleFile} contained in the {@link #evaluationTrace} model.
	 */
	private ModuleFile getModuleFile(EObject moduleElement) {
		final URI moduleURI = moduleElement.eResource().getURI();
		String path = moduleURI.toString();

		if (path.startsWith("http://") && EMFPlugin.IS_ECLIPSE_RUNNING) { //$NON-NLS-1$
			EPackage pack = EPackage.Registry.INSTANCE.getEPackage(path);
			try {
				URL emtlURL = AcceleoWorkspaceUtil.getResourceURL(pack.getClass(), stripPathFrom(path));
				if (emtlURL != null) {
					path = emtlURL.toString();
				}
			} catch (IOException e) {
				AcceleoTraceabilityPlugin.log(e, false);
			}
		}

		ModuleFile soughtModule = evaluationTrace.getGenerationModule(path);
		if (soughtModule == null) {
			soughtModule = TraceabilityFactory.eINSTANCE.createModuleFile();
			soughtModule.setPath(path);
			soughtModule.setName(stripFileNameFrom(path));
			evaluationTrace.getModules().add(soughtModule);
		}
		return soughtModule;
	}

	/**
	 * Returns the list of all operations that will impact traceability information.
	 * 
	 * @return The list of all operations that will impact traceability information.
	 */
	private List<String> getTraceabilityImpactingCollectionOperationNames() {
		List<String> operationNames = new ArrayList<String>();

		// Operations returning Integer or Boolean values are handled otherwise

		// non standard library
		operationNames.add(AcceleoNonStandardLibrary.OPERATION_COLLECTION_REVERSE);
		operationNames.add(AcceleoNonStandardLibrary.OPERATION_COLLECTION_SEP);

		// OCL
		operationNames.add(PredefinedType.FIRST_NAME);
		operationNames.add(PredefinedType.LAST_NAME);

		return operationNames;
	}

	/**
	 * Returns the list of all operations that will impact traceability information.
	 * 
	 * @return The list of all operations that will impact traceability information.
	 */
	private List<String> getTraceabilityImpactingStringOperationNames() {
		List<String> operationNames = new ArrayList<String>();

		// Operations returning Integer or Boolean values are handled otherwise

		// standard library
		operationNames.add(AcceleoStandardLibrary.OPERATION_STRING_FIRST);
		operationNames.add(AcceleoStandardLibrary.OPERATION_STRING_LAST);
		operationNames.add(AcceleoStandardLibrary.OPERATION_STRING_STRTOK);
		operationNames.add(AcceleoStandardLibrary.OPERATION_STRING_SUBSTITUTE);

		// non standard library
		operationNames.add(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE);
		operationNames.add(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL);
		operationNames.add(AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL);
		operationNames.add(AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTRING);
		operationNames.add(AcceleoNonStandardLibrary.OPERATION_STRING_TOKENIZE);
		operationNames.add(AcceleoNonStandardLibrary.OPERATION_STRING_TRIM);

		// OCL
		operationNames.add(PredefinedType.SUBSTRING_NAME);

		return operationNames;
	}

	/**
	 * Handles the call to a non-standard MTL operation that impacts traceability.
	 * 
	 * @param callExp
	 *            The operation call to visit.
	 * @param source
	 *            Source of the call.
	 * @return Result of the call.
	 */
	@SuppressWarnings("unchecked")
	private Object internalVisitNonStandardOperation(OperationCallExp<C, O> callExp, Object source) {
		final String operationName = ((EOperation)callExp.getReferredOperation()).getName();
		final Object result;

		final List<Object> arguments = new ArrayList<Object>(callExp.getArgument().size());
		for (OCLExpression<C> expression : callExp.getArgument()) {
			// FIXME handle invalid and null
			boolean oldRecordingValue = record;
			record = false;
			arguments.add(super.visitExpression(expression));
			record = oldRecordingValue;
		}

		if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_TRIM)) {
			result = operationVisitor.visitTrimOperation((String)source);
		} else if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTRING)) {
			int startIndex = Integer.valueOf(((Integer)arguments.get(0)).intValue() - 1).intValue();
			result = operationVisitor.visitSubstringOperation((String)source, startIndex);
		} else if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_COLLECTION_REVERSE)) {
			result = operationVisitor.visitReverseOperation((Collection<Object>)source);
		} else if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_TOKENIZE)) {
			String delims = (String)arguments.get(0);
			result = operationVisitor.visitTokenizeOperation((String)source, delims);
		} else if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_OCLANY_TOSTRING)) {
			// toString on non-primitives will need to create its own trace.
			ModuleElement moduleElement = getModuleElement(callExp);
			result = operationVisitor.visitTostringOperation(source, moduleElement);
		} else {
			// Note that we'll never be here : isTraceabilityImpactingOperation limits us to known operations
			throw new UnsupportedOperationException();
		}

		return result;
	}

	/**
	 * Handles the call to a standard OCL operation that impacts traceability.
	 * 
	 * @param callExp
	 *            The operation call to visit.
	 * @param source
	 *            Source of the call.
	 * @return Result of the call.
	 */
	private Object internalVisitOCLOperation(OperationCallExp<C, O> callExp, Object source) {
		final Object result;

		final List<Object> arguments = new ArrayList<Object>(callExp.getArgument().size());
		for (OCLExpression<C> expression : callExp.getArgument()) {
			// FIXME handle invalid and null
			boolean oldRecordingValue = record;
			record = false;
			arguments.add(super.visitExpression(expression));
			record = oldRecordingValue;
		}

		switch (callExp.getOperationCode()) {
			case PredefinedType.SUBSTRING:
				int startIndex = ((Integer)arguments.get(0)).intValue() - 1;
				int endIndex = ((Integer)arguments.get(1)).intValue();
				result = operationVisitor.visitSubstringOperation((String)source, startIndex, endIndex);
				break;
			case PredefinedType.FIRST:
				result = operationVisitor.visitFirstOperation((Collection<?>)source);
				break;
			case PredefinedType.LAST:
				result = operationVisitor.visitLastOperation((Collection<?>)source);
				break;
			default:
				// Note that we'll never be here : isTraceabilityImpactingOperation limits us to known
				// operations
				if (callExp.eResource() != null) {
					URI uri = callExp.eResource().getURI();
					int startPosition = callExp.getStartPosition();
					int endPosition = callExp.getEndPosition();
					throw new UnsupportedOperationException(AcceleoTraceabilityMessages.getString(
							"AcceleoTraceabilityVisitor.OperationNotFound", callExp.toString(), source, uri, //$NON-NLS-1$
							Integer.valueOf(startPosition), Integer.valueOf(endPosition)));
				}

				throw new UnsupportedOperationException(callExp.toString());
		}

		return result;
	}

	/**
	 * We'll intercept each and every calls to traceability-impacting operations as returned by
	 * {@link #isTraceabilityImpactingOperation(OperationCallExp)} and reroute them here. We'll then
	 * specialize the evaluation of that given operation call so as to record evaluation traces.
	 * 
	 * @param callExp
	 *            Operation that is to be evaluated.
	 * @return result of the invocation.
	 */
	@SuppressWarnings("unchecked")
	private Object internalVisitOperationCallExp(OperationCallExp<C, O> callExp) {
		final String operationName = ((EOperation)callExp.getReferredOperation()).getName();
		final Object result;

		Object sourceObject = visitExpression(callExp.getSource());

		/*
		 * All four "substitute" type operations will be handled the same, yet they require that argument
		 * traces be recorded. We'll handle them apart from the rest.
		 */
		if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_SUBSTITUTE) || operationName.equals(
				AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL) || operationName.equals(
						AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE) || operationName.equals(
								AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL)) {
			boolean substituteAll = false;
			boolean substituteRegexes = false;
			if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL)) {
				substituteAll = true;
			} else if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE)) {
				substituteRegexes = true;
			} else if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL)) {
				substituteAll = true;
				substituteRegexes = true;
			}

			boolean oldRecordingValue = record;
			record = false;
			Object substring = super.visitExpression(callExp.getArgument().get(0));
			record = oldRecordingValue;
			ExpressionTrace<C> oldArgTrace = operationArgumentTrace;
			operationArgumentTrace = new ExpressionTrace<C>(callExp.getArgument().get(1));
			Object substitution = super.visitExpression(callExp.getArgument().get(1));

			if (!substituteRegexes) {
				// substitute replaces Strings, not regexes.
				// Surrounding the regex with \Q [...] \E allows just that
				substring = "\\Q" + substring + "\\E"; //$NON-NLS-1$ //$NON-NLS-2$
				// We also need to escape backslashes and dollar signs in the replacement (scary!)
				substitution = ((String)substitution).replaceAll("\\\\", "\\\\\\\\").replaceAll("\\$", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						"\\\\\\$"); //$NON-NLS-1$
			}

			result = operationVisitor.visitReplaceOperation((String)sourceObject, (String)substring,
					(String)substitution, operationArgumentTrace, substituteAll, false);
			operationArgumentTrace.dispose();
			operationArgumentTrace = oldArgTrace;

			return result;
		}

		EOperation operation = (EOperation)callExp.getReferredOperation();

		// Collection::sep also requires that its separator traces be recorded
		if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_COLLECTION_SEP)) {
			if (sourceObject instanceof Collection) {
				if (callExp.getArgument().size() == 1) {
					ExpressionTrace<C> oldArgTrace = operationArgumentTrace;
					operationArgumentTrace = new ExpressionTrace<C>(callExp.getArgument().get(0));
					Object separator = super.visitExpression(callExp.getArgument().get(0));
					result = operationVisitor.visitSepOperation((Collection<Object>)sourceObject,
							(String)separator, operationArgumentTrace);
					operationArgumentTrace.dispose();
					operationArgumentTrace = oldArgTrace;
				} else {
					// collection->sep(start, separator, end)
					result = visitSepThreeOperation(callExp, sourceObject);
				}
			} else {
				result = null;
			}
		} else if (operation.getEAnnotation("MTL") != null) { //$NON-NLS-1$
			result = internalVisitStandardOperation(callExp, sourceObject);
		} else if (operation.getEAnnotation("MTL non-standard") != null) { //$NON-NLS-1$
			result = internalVisitNonStandardOperation(callExp, sourceObject);
		} else {
			// Only OCL operations remain
			result = internalVisitOCLOperation(callExp, sourceObject);
		}

		return result;
	}

	/**
	 * Runs the operation sep(start, separator, end) on the given source object. Traceability information will
	 * not be computed here.
	 * 
	 * @param callExp
	 *            The operation
	 * @param sourceObject
	 *            The source object
	 * @return The result of the operation.
	 */
	private Object visitSepThreeOperation(OperationCallExp<C, O> callExp, Object sourceObject) {
		final Object result;
		Object start = super.visitExpression(callExp.getArgument().get(0));
		Object separator = super.visitExpression(callExp.getArgument().get(1));
		Object end = super.visitExpression(callExp.getArgument().get(2));

		Collection<?> source = (Collection<?>)sourceObject;
		final Collection<Object> temp = new ArrayList<Object>();
		temp.add(start);

		final List<String> stringSource = new ArrayList<String>();
		final Iterator<?> sourceIterator = source.iterator();
		while (sourceIterator.hasNext()) {
			Object nextSource = sourceIterator.next();
			temp.add(nextSource);
			stringSource.add(nextSource.toString());
			if (sourceIterator.hasNext()) {
				temp.add(separator);
			}
		}

		temp.add(end);
		result = temp;
		return result;
	}

	/**
	 * Handles the call to a standard MTL operation that impacts traceability.
	 * 
	 * @param callExp
	 *            The operation call to visit.
	 * @param source
	 *            Source of the call.
	 * @return Result of the call.
	 */
	private Object internalVisitStandardOperation(OperationCallExp<C, O> callExp, Object source) {
		final String operationName = ((EOperation)callExp.getReferredOperation()).getName();
		final Object result;

		final List<Object> arguments = new ArrayList<Object>(callExp.getArgument().size());
		for (OCLExpression<C> expression : callExp.getArgument()) {
			// FIXME handle invalid and null
			boolean oldRecordingValue = record;
			record = false;
			arguments.add(super.visitExpression(expression));
			record = oldRecordingValue;
		}

		if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_FIRST)) {
			int charCount = ((Integer)arguments.get(0)).intValue();
			result = operationVisitor.visitFirstOperation((String)source, charCount);
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_LAST)) {
			int charCount = ((Integer)arguments.get(0)).intValue();
			result = operationVisitor.visitLastOperation((String)source, charCount);
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_STRTOK)) {
			String delimiters = (String)arguments.get(0);
			Integer flag = (Integer)arguments.get(1);
			result = operationVisitor.visitStrtokOperation((String)source, delimiters, flag);
		} else {
			// Note that we'll never be here : isTraceabilityImpactingOperation limits us to known operations
			throw new UnsupportedOperationException();
		}

		return result;
	}

	/**
	 * This will return <code>true</code> if the given operation call returns a boolean.
	 * 
	 * @param operationCall
	 *            The operation call which return type is to be checked.
	 * @return <code>true</code> if the given operation call returns a boolean.
	 */
	private boolean isBooleanReturningOperation(OperationCallExp<C, O> operationCall) {
		final EClassifier operationReturnEType = (EClassifier)operationCall.getType();
		final EClassifier booleanClassifier = (EClassifier)getEnvironment().getOCLStandardLibrary()
				.getBoolean();

		return booleanClassifier == operationReturnEType;
	}

	/**
	 * Returns <code>true</code> if the given expression is the source of an iteration.
	 * 
	 * @param expression
	 *            Expression to compare.
	 * @return <code>true</code> if <code>expression</code> is the source of an iteration call,
	 *         <code>false</code> otherwise.
	 */
	private boolean isIteratorCallSource(OCLExpression<?> expression) {
		boolean isSource = false;
		EObject eContainer = expression.eContainer();
		if (eContainer instanceof IteratorExp) {
			IteratorExp<?, ?> iteratorExp = (IteratorExp<?, ?>)eContainer;
			OCLExpression<?> source = iteratorExp.getSource();
			isSource = (source == expression) || isIteratorCallSource(iteratorExp);
		} else if (eContainer instanceof CollectionItem) {
			CollectionItem<?> collectionItem = (CollectionItem<?>)eContainer;
			eContainer = collectionItem.eContainer();
			if (eContainer instanceof CollectionLiteralExp<?>) {
				CollectionLiteralExp<?> collectionLiteralExp = (CollectionLiteralExp<?>)eContainer;
				isSource = isIteratorCallSource(collectionLiteralExp);
			}
		} else if (eContainer instanceof ForBlock) {
			isSource = EcoreUtil.isAncestor(iterationTraces.getLast().getReferredExpression(), expression);
		}
		return isSource;
	}

	/**
	 * This will return <code>true</code> if the given operation call returns a Number.
	 * 
	 * @param operationCall
	 *            The operation call which return type is to be checked.
	 * @return <code>true</code> if the given operation call returns a Number.
	 */
	private boolean isNumberReturningOperation(OperationCallExp<C, O> operationCall) {
		final EClassifier operationReturnEType = (EClassifier)operationCall.getType();
		final EClassifier integerClassifier = (EClassifier)getEnvironment().getOCLStandardLibrary()
				.getInteger();
		final EClassifier realClassifier = (EClassifier)getEnvironment().getOCLStandardLibrary().getReal();

		return integerClassifier == operationReturnEType || realClassifier == operationReturnEType;
	}

	/**
	 * Returns <code>true</code> if the given expression is the source of the operation call we seek.
	 * 
	 * @param expression
	 *            Expression to compare.
	 * @return <code>true</code> if <code>expression</code> is the source of the current operation call,
	 *         <code>false</code> otherwise.
	 */
	private boolean isOperationCallSource(OCLExpression<C> expression) {
		return expression == operationCallSourceExpression;
	}

	/**
	 * Returns <code>true</code> if the given expression is located directly under a protected area block.
	 * 
	 * @param expression
	 *            The expression to consider.
	 * @return <code>true</code> if <code>expression</code> is located in the content tree of a
	 *         {@link ProtectedAreaBlock}, <code>false</code> otherwise.
	 */
	private boolean isProtectedAreaContent(EObject expression) {
		boolean isProtectedAreaContent = expression instanceof ProtectedAreaBlock;
		EObject container = expression.eContainer();
		while (container != null && !isProtectedAreaContent) {
			isProtectedAreaContent = container instanceof ProtectedAreaBlock;
			container = container.eContainer();
		}
		return isProtectedAreaContent;
	}

	/**
	 * Returns <code>true</code> if the given expression is the source of the property call we seek.
	 * 
	 * @param expression
	 *            Expression to compare.
	 * @return <code>true</code> if <code>expression</code> is the source of the current property call,
	 *         <code>false</code> otherwise.
	 */
	private boolean isPropertyCallSource(OCLExpression<C> expression) {
		return expression == propertyCallSourceExpression;
	}

	/**
	 * This will return <code>true</code> if the given operation call refers to a String operation which
	 * invocation will result in an alteration of the traceability information for the source.
	 * 
	 * @param operationCall
	 *            Operation call that is to be considered.
	 * @return <code>true</code> if calling this operation would alter the traceability information.
	 */
	private boolean isTraceabilityImpactingOperation(OperationCallExp<C, O> operationCall) {
		boolean isImpacting = false;

		final EClassifier operationReceiverEType = (EClassifier)operationCall.getSource().getType();
		final EClassifier stringType = (EClassifier)getEnvironment().getOCLStandardLibrary().getString();
		final EClassifier collectionType = (EClassifier)getEnvironment().getOCLStandardLibrary()
				.getCollection();

		// Any operation that returns a Primitive impacts the traceability
		if (isBooleanReturningOperation(operationCall) || isNumberReturningOperation(operationCall)) {
			isImpacting = true;
		} else {
			final String operationName = ((EOperation)operationCall.getReferredOperation()).getName();
			if (operationReceiverEType == stringType || AcceleoStandardLibrary.PRIMITIVE_STRING_NAME.equals(
					operationReceiverEType.getName())) {
				isImpacting = getTraceabilityImpactingStringOperationNames().contains(operationName);
			} else if (collectionType.eClass().isInstance(operationReceiverEType)) {
				isImpacting = getTraceabilityImpactingCollectionOperationNames().contains(operationName);
			} else if (AcceleoNonStandardLibrary.OPERATION_OCLANY_TOSTRING.equals(operationName)
					&& !TraceabilityVisitorUtil.isPrimitive(operationReceiverEType)
					&& !(operationReceiverEType instanceof EEnum)) {
				// toString() applied on non-primitive will need to create its own traceability information.
				isImpacting = true;
			}
		}

		return isImpacting;
	}

	/**
	 * Records information for the evaluation of literal expressions. This will be used both for
	 * {@link org.eclipse.ocl.expressions.LiteralExp} and calls to "getProperty".
	 * 
	 * @param expression
	 *            The expression literal we've evaluated.
	 * @param result
	 *            The result of the evaluation.
	 */
	private void recordLiteral(OCLExpression<C> expression, Object result) {
		InputElement input = getInputElement(retrieveScopeEObjectValue());
		if (protectedAreaSource != null) {
			input = protectedAreaSource;
		}

		// We do not create traceability information for the charset of the file block (ex: UTF-8)
		boolean isFileBlockCharset = expression.eContainingFeature() == MtlPackage.eINSTANCE
				.getFileBlock_Charset();

		if (record && !isFileBlockCharset) {
			if (operationArgumentTrace != null) {
				GeneratedText text = createGeneratedTextFor(expression);
				operationArgumentTrace.addTrace(input, text, result);
			} else if (isInitializingVariable()) {
				GeneratedText text = createGeneratedTextFor(expression);
				variableTraces.get(initializingVariable).addTrace(input, text, result);
			} else if (!recordedTraces.isEmpty() && result.toString().length() > 0 && shouldRecordTrace(
					expression)) {
				GeneratedText text = createGeneratedTextFor(expression);
				recordedTraces.getLast().addTrace(input, text, result);
			} else if (isOperationCallSource(expression)) {
				GeneratedText text = createGeneratedTextFor(expression);
				recordedTraces.getLast().addTrace(input, text, result);
			} else if (!iterationTraces.isEmpty()) {
				GeneratedText text = createGeneratedTextFor(expression);
				iterationTraces.getLast().addTrace(input, text, result);
			}
		} else if (!record && operationArgumentTrace != null) {
			GeneratedText text = createGeneratedTextFor(expression);
			operationArgumentTrace.addTrace(input, text, result);
		}
	}

	/**
	 * Returns the scope value of the n-th scope of this evaluation. 0 being the EObject passed as argument of
	 * the generation, <em>{@link #scopeEObjects}.size() - 1</em> the scope of the current expression.
	 * 
	 * @param index
	 *            Index of the scope EObject value we wish to fetch.
	 * @return The scope value of the n-th scope of this evaluation.
	 */
	@SuppressWarnings("unchecked")
	private EObject retrieveScopeEObjectValue(int index) {
		EObject scopeValue = null;
		for (int i = index; i >= 0 && scopeValue == null; i--) {
			EObject scope = scopeEObjects.get(i);
			if (scope instanceof Variable<?, ?>) {
				final Object value = getEvaluationEnvironment().getValueOf(((Variable<C, PM>)scope)
						.getName());
				if (value instanceof EObject) {
					scopeValue = (EObject)value;
				}
			} else if (scope instanceof VariableExp<?, ?>) {
				final Object value = getEvaluationEnvironment().getValueOf((((VariableExp<C, PM>)scope)
						.getReferredVariable()).getName());
				if (value instanceof EObject) {
					scopeValue = (EObject)value;
				}
			} else {
				scopeValue = scope;
				if (scope instanceof OCLExpression<?>) {
					Object self = getEvaluationEnvironment().getValueOf(IAcceleoConstants.SELF);
					if (self instanceof EObject) {
						scopeValue = (EObject)self;
					}
				}
			}
		}
		return scopeValue;
	}

	/**
	 * This check is specifically designed in order for us to separately record the traces of traceability
	 * impacting operations.
	 * <p>
	 * For example, for the call <code>'string' + 'anotherString'.last(6)</code>, the traces for the "last"
	 * operation call <b>must</b> be recorded separately (otherwise, the call itself would alter the
	 * traceability information of the whole "+" operation).
	 * </p>
	 * 
	 * @param expression
	 *            The expression we are to check.
	 * @return <code>true</code> if this specific expression matches the above, <code>false</code> otherwise.
	 */
	@SuppressWarnings("unchecked")
	private boolean shouldRecordOperationTrace(OCLExpression<C> expression) {
		boolean recordTrace = false;
		if (expression instanceof OperationCallExp<?, ?>) {
			final OperationCallExp<C, O> call = (OperationCallExp<C, O>)expression;
			recordTrace = call.eContainingFeature() != MtlPackage.eINSTANCE.getTemplate_Post()
					&& isTraceabilityImpactingOperation(call);
		} else if (expression instanceof Template && ((Template)expression)
				.getPost() instanceof OperationCallExp<?, ?>) {
			final OperationCallExp<C, O> call = (OperationCallExp<C, O>)((Template)expression).getPost();
			recordTrace = isTraceabilityImpactingOperation(call);
		}
		return recordTrace;
	}

	/**
	 * This will check if the given reference should cause trace recording. More specifically, it will return
	 * <code>false</code> for any reference that isn't one of the following :
	 * <table>
	 * <tr>
	 * <td>Metaclass</td>
	 * <td>Reference</td>
	 * </tr>
	 * <tr>
	 * <td>Block</td>
	 * <td>body</td>
	 * </tr>
	 * <tr>
	 * <td>FileBlock</td>
	 * <td>fileURL</td>
	 * </tr>
	 * <tr>
	 * <td>ForBlock</td>
	 * <td>before</td>
	 * </tr>
	 * <tr>
	 * <td>ForBlock</td>
	 * <td>each</td>
	 * </tr>
	 * <tr>
	 * <td>ForBlock</td>
	 * <td>after</td>
	 * </tr>
	 * <tr>
	 * <td>TemplateInvocation</td>
	 * <td>before</td>
	 * </tr>
	 * <tr>
	 * <td>TemplateInvocation</td>
	 * <td>each</td>
	 * </tr>
	 * <tr>
	 * <td>TemplateInvocation</td>
	 * <td>after</td>
	 * </tr>
	 * </table>
	 * 
	 * @param reference
	 *            The reference for which we need to know if text is to be generated.
	 * @return <code>True</code> if we need to generate text for the given reference, <code>false</code>
	 *         otherwise.
	 */
	private boolean shouldRecordTrace(EReference reference) {
		// Note : sort this by order of frequency to allow shot-circuit evaluation
		boolean generate = reference == MtlPackage.eINSTANCE.getBlock_Body();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_Each();
		generate = generate || reference == MtlPackage.eINSTANCE.getFileBlock_FileUrl();
		generate = generate || reference == MtlPackage.eINSTANCE.getProtectedAreaBlock_Marker();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_Before();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_After();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_Each();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_Before();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_After();
		return generate;
	}

	/**
	 * For a given expression, we'll check whether execution traces must be recorded. This involves checking
	 * whether the expression is the source of a property or operation call, then looking at which operation
	 * will be called on the result if needed.
	 * 
	 * @param expression
	 *            Expression we're currently evaluating.
	 * @return <code>true</code> if traces are to be recorded, <code>false</code> otherwise.
	 */
	@SuppressWarnings("unchecked")
	private boolean shouldRecordTrace(OCLExpression<C> expression) {
		boolean result = true;
		if (evaluatingIterationSet || !record) {
			return false;
		}
		if (isPropertyCallSource(expression)) {
			result = false;
		} else if (isOperationCallSource(expression)) {
			OperationCallExp<C, O> call = (OperationCallExp<C, O>)expression.eContainer();
			EOperation op = (EOperation)call.getReferredOperation();
			if (isTraceabilityImpactingOperation(call) && TraceabilityVisitorUtil.isPrimitive(
					(EClassifier)call.getType())) {
				result = true;
			} else if (op.getEType() != getEnvironment().getOCLStandardLibrary().getString()) {
				result = false;
			}
		} else if (isIteratorCallSource(expression)) {
			result = !(recordedTraces.getLast().getReferredExpression() instanceof IteratorExp<?, ?>
					&& EcoreUtil.isAncestor(recordedTraces.getLast().getReferredExpression(), expression));
		} else if (expression.eContainingFeature() == ExpressionsPackage.eINSTANCE
				.getOperationCallExp_Argument() && isTraceabilityImpactingOperation(
						(OperationCallExp<C, O>)expression.eContainer())) {
			result = false;
		}
		return result;
	}

	/**
	 * This will return only the "name" section of a given path. This excludes any parent directory and file
	 * extension info.
	 * <p>
	 * For example, this will return <code>"MyClass"</code> from path <code>c:\dev\java\MyClass.java</code>.
	 * </p>
	 * 
	 * @param filePath
	 *            Path that is to be stripped.
	 * @return The extracted file name.
	 */
	private String stripFileNameFrom(String filePath) {
		String fileName = filePath;
		if (fileName.indexOf(File.separator) != -1) {
			fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
		}
		if (fileName.indexOf('.') > 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		}
		return fileName;
	}

	/**
	 * This will return the "path" section of a given uri, ignoring the protocol as well as the project name
	 * if said protocol is "platform".
	 * 
	 * @param fileURI
	 *            The URI from which to retrieve a path.
	 * @return The path section of the given URI.
	 */
	private String stripPathFrom(String fileURI) {
		URI fileEMFURI = URI.createURI(fileURI);
		String filePath = fileEMFURI.path();
		if (fileEMFURI.isPlatform()) {
			int slashIndex = filePath.indexOf('/');
			if (slashIndex > 0) {
				slashIndex = filePath.indexOf('/', slashIndex + 1);
			}
			if (slashIndex > 0) {
				filePath = filePath.substring(slashIndex + 1);
			}
		}
		return filePath;
	}

	/**
	 * Switches the "record" boolean to false if the given expression shouldn't record any trace information.
	 * The old value of the record boolean will be returned.
	 * 
	 * @param expression
	 *            Expression for which we need to check whether traces should be recorded.
	 * @return The old value of the record boolean.
	 */
	@SuppressWarnings("unchecked")
	private boolean switchRecordState(OCLExpression<C> expression) {
		// Do not switch on the recording state if it was previously switched off (avoids recording
		// information for nested expressions)
		if (!record) {
			return record;
		}
		boolean oldRecordingValue = record;
		EStructuralFeature containingFeature = expression.eContainingFeature();
		EObject container = expression.eContainer();

		// If this is an "if" condition
		record = containingFeature != MtlPackage.eINSTANCE.getIfBlock_IfExpr();
		record = record && containingFeature != ExpressionsPackage.eINSTANCE.getIfExp_Condition();

		// If this is a guard expression
		record = record && containingFeature != MtlPackage.eINSTANCE.getForBlock_Guard();
		record = record && containingFeature != MtlPackage.eINSTANCE.getTemplate_Guard();

		// If this is a let initialization
		record = record && (containingFeature != ExpressionsPackage.eINSTANCE.getVariable_InitExpression()
				|| container.eContainingFeature() != MtlPackage.eINSTANCE.getLetBlock_LetVariable());

		// Similarly, bodies of all iterators except for collect shouldn't record any trace
		if (record && container instanceof IteratorExp<?, ?>) {
			int opCode = OCLStandardLibraryUtil.getOperationCode(((IteratorExp<?, ?>)container).getName());
			if (opCode != PredefinedType.COLLECT && opCode != PredefinedType.COLLECT_NESTED) {
				record = containingFeature != ExpressionsPackage.eINSTANCE.getLoopExp_Body();
			}
		}

		// We won't record any trace information either for the traceability impacting operations' arguments
		if (record && containingFeature == ExpressionsPackage.eINSTANCE.getOperationCallExp_Argument()) {
			record = !isTraceabilityImpactingOperation((OperationCallExp<C, O>)container);
		}

		return oldRecordingValue;
	}
}
