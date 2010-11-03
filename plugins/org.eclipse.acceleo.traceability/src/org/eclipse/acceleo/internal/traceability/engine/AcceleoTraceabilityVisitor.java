/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
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
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.common.utils.AcceleoStandardLibrary;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationCancelledException;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
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
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.expressions.AssociationClassCallExp;
import org.eclipse.ocl.expressions.EnumLiteralExp;
import org.eclipse.ocl.expressions.ExpressionsPackage;
import org.eclipse.ocl.expressions.IfExp;
import org.eclipse.ocl.expressions.IterateExp;
import org.eclipse.ocl.expressions.IteratorExp;
import org.eclipse.ocl.expressions.LetExp;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.PropertyCallExp;
import org.eclipse.ocl.expressions.StateExp;
import org.eclipse.ocl.expressions.StringLiteralExp;
import org.eclipse.ocl.expressions.Variable;
import org.eclipse.ocl.expressions.VariableExp;
import org.eclipse.ocl.types.PrimitiveType;
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

	/** Traceability needs to kno what expression is being processed at all times. */
	private OCLExpression<C> currentExpression;

	/** This will hold the stack of generated files. */
	private LinkedList<GeneratedFile> currentFiles = new LinkedList<GeneratedFile>();

	/** All traceability information for this session will be saved in this instance. */
	private final TraceabilityModel evaluationTrace;

	/** Keeps track of the variable currently being initialized. */
	private Variable<C, PM> initializingVariable;

	/** This will be used to keep pointers towards the latest template invocation traces. */
	private LinkedList<ExpressionTrace<C>> invocationTraces;

	/** This will be used to keep pointers towards the input elements created for the current trace. */
	private Map<EObject, Set<InputElement>> cachedInputElements = new HashMap<EObject, Set<InputElement>>(
			INITIAL_CACHE_SIZE);

	/** This will be used to keep pointers towards the module elements created for the current trace. */
	private Map<EObject, ModuleElement> cachedModuleElements = new HashMap<EObject, ModuleElement>(
			INITIAL_CACHE_SIZE);

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
	 * as soon as we exit this area. It will be used to shortcut all input recorded inside of such an area.
	 */
	private InputElement protectedAreaSource;

	/** This will be used internally to prevent trace recording for set expressions. */
	private boolean record = true;

	/** This will hold the stack of all created traceability contexts. */
	private final LinkedList<ExpressionTrace<C>> recordedTraces = new LinkedList<ExpressionTrace<C>>();

	/** This will be updated each time we enter a for/template/query/... with the scope variable. */
	private LinkedList<EObject> scopeEObjects = new LinkedList<EObject>();

	/**
	 * Records all variable traces for this session. Note that only primitive type variables will be recorded.
	 */
	private final Map<Variable<C, PM>, VariableTrace<C, PM>> variableTraces = new HashMap<Variable<C, PM>, VariableTrace<C, PM>>();

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
		if (fireEvent && string.length() > 0) {
			if (currentFiles != null && recordedTraces != null && currentFiles.size() > 0
					&& recordedTraces.size() > 0) {
				GeneratedFile generatedFile = currentFiles.getLast();
				final ExpressionTrace<C> trace;
				boolean disposeTrace = !(sourceBlock instanceof IfBlock)
						|| !(sourceBlock instanceof ForBlock);
				if (sourceBlock instanceof IfBlock || sourceBlock instanceof ForBlock) {
					trace = recordedTraces.getLast();
				} else {
					trace = recordedTraces.removeLast();
				}
				if (protectedAreaSource != null) {
					alterProtectedAreaTrace(string, sourceBlock, trace);
				}
				final int fileLength = generatedFile.getLength();
				int addedLength = 0;

				// We no longer need to refer to the same trace instance, copy its current state.
				if (invocationTraces != null && invocationTraces.contains(trace)) {
					invocationTraces.remove(trace);
					invocationTraces.add(new ExpressionTrace<C>(trace));
				}

				for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
					Iterator<GeneratedText> textIterator = entry.getValue().iterator();
					while (textIterator.hasNext()) {
						GeneratedText text = textIterator.next();
						int startingOffset = addedLength;
						addedLength += text.getEndOffset() - text.getStartOffset();
						text.setStartOffset(fileLength + startingOffset);
						text.setEndOffset(fileLength + addedLength);
						generatedFile.getGeneratedRegions().add(text);
						textIterator.remove();
					}
				}
				generatedFile.setLength(fileLength + addedLength);
				if (disposeTrace) {
					trace.dispose();
				}
			}
		}
		super.append(string, sourceBlock, source, fireEvent);
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

		GeneratedFile file = getGeneratedFile(generatedFile, appendMode);
		file.setCharset(charset);
		file.setFileBlock(getModuleElement(fileBlock));
		currentFiles.add(file);

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
		if ("".equals(indentation)) { //$NON-NLS-1$
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

		return operationVisitor.visitReplaceOperation(source, regex, replacement, indentationTrace, true,
				true);
	}

	/**
	 * Returns the stack of generated files.
	 * 
	 * @return The stack of generated files.
	 */
	public LinkedList<GeneratedFile> getCurrentFiles() {
		return currentFiles;
	}

	/**
	 * Returns the last invocation's recorded traces.
	 * 
	 * @return The last invocation's recorded traces.
	 */
	public LinkedList<ExpressionTrace<C>> getInvocationTraces() {
		return invocationTraces;
	}

	/**
	 * Returns the last recorded expression trace.
	 * 
	 * @return The last recorded expression trace.
	 */
	public ExpressionTrace<C> getLastExpressionTrace() {
		return recordedTraces.getLast();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoFileBlock(org.eclipse.acceleo.model.mtl.FileBlock)
	 */
	@Override
	public void visitAcceleoFileBlock(FileBlock fileBlock) {
		super.visitAcceleoFileBlock(fileBlock);

		currentFiles.removeLast();
		if (recordedTraces.size() > 0 && recordedTraces.getLast().getReferredExpression() == fileBlock
				&& recordedTraces.getLast().getTraces().size() == 0) {
			recordedTraces.removeLast();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitorDecorator#visitAcceleoForBlock(org.eclipse.acceleo.model.mtl.ForBlock)
	 */
	@Override
	public void visitAcceleoForBlock(ForBlock forBlock) {
		if (forBlock.getLoopVariable() != null) {
			scopeEObjects.add(forBlock.getLoopVariable());
		}
		// FIXME what to do if the iteration set is of a primitive type? ([for (eClass.eSupertypes.name)/])

		super.visitAcceleoForBlock(forBlock);

		if (forBlock.getLoopVariable() != null) {
			scopeEObjects.removeLast();
		}
		if (recordedTraces.size() > 0 && recordedTraces.getLast().getReferredExpression() == forBlock
				&& recordedTraces.getLast().getTraces().size() == 0) {
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

		if (recordedTraces.size() > 0 && recordedTraces.getLast().getReferredExpression() == ifBlock
				&& recordedTraces.getLast().getTraces().size() == 0) {
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

		super.visitAcceleoProtectedArea(protectedArea);

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
		scopeEObjects.add(invocation.getDefinition().getParameter().get(0));

		final Object result = super.visitAcceleoQueryInvocation(invocation);

		scopeEObjects.removeLast();

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
		LinkedList<ExpressionTrace<C>> oldTraces = invocationTraces;
		invocationTraces = new LinkedList<ExpressionTrace<C>>();

		final Object result = super.visitAcceleoTemplateInvocation(invocation);

		for (ExpressionTrace<C> trace : invocationTraces) {
			if (oldTraces != null) {
				oldTraces.add(trace);
			} else {
				trace.dispose();
			}
		}
		invocationTraces = oldTraces;
		if (invocation.getArgument().size() > 0) {
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
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitEnumLiteralExp(org.eclipse.ocl.expressions.EnumLiteralExp)
	 */
	@Override
	public Object visitEnumLiteralExp(EnumLiteralExp<C, EL> literalExp) {
		final Object result = super.visitEnumLiteralExp(literalExp);

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

		// Very first call of a template comes from IAcceleoEngine#doEvaluate()
		if (scopeEObjects.size() == 0 && expression instanceof Template) {
			for (org.eclipse.ocl.ecore.Variable var : ((Template)expression).getParameter()) {
				Object value = getEvaluationEnvironment().getValueOf(var.getName());
				if (value instanceof EObject) {
					scopeEObjects.add((EObject)value);
					break;
				}
			}
		}

		boolean oldRecordingValue = record;
		if (expression.eContainingFeature() == MtlPackage.eINSTANCE.getIfBlock_IfExpr()
				|| expression.eContainingFeature() == ExpressionsPackage.eINSTANCE.getIfExp_Condition()) {
			record = false;
		}
		if (shouldRecordTrace((EReference)expression.eContainingFeature())
				&& !(expression.eContainer() instanceof ProtectedAreaBlock)) {
			ExpressionTrace<C> trace = new ExpressionTrace<C>(expression);
			recordedTraces.add(trace);
			if (invocationTraces != null) {
				invocationTraces.add(trace);
			}
		}
		Object result = null;
		try {
			result = getDelegate().visitExpression(expression);
		} catch (AcceleoEvaluationCancelledException e) {
			cancel();
			throw e;
		} finally {
			record = oldRecordingValue;
		}

		if (isPropertyCallSource(expression)) {
			propertyCallSource = (EObject)result;
		} else if (isOperationCallSource(expression)) {
			operationCallSource = result;
		}
		if (expression.eContainingFeature() == MtlPackage.eINSTANCE.getProtectedAreaBlock_Marker()) {
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitIteratorExp(org.eclipse.ocl.expressions.IteratorExp)
	 */
	@Override
	public Object visitIteratorExp(IteratorExp<C, PM> callExp) {
		scopeEObjects.add(callExp.getIterator().get(0));

		final Object result = super.visitIteratorExp(callExp);

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

		final Object result;
		if (isTraceabilityImpactingOperation(callExp)) {
			result = internalVisitOperationCallExp(callExp);
		} else {
			result = super.visitOperationCallExp(callExp);
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

		final Object result = getDelegate().visitPropertyCallExp(callExp);

		propertyCallSourceExpression = oldPropertyCallSourceExpression;

		if (propertyCallSource != null && result != null) {
			InputElement propertyCallInput = getInputElement(propertyCallSource, (EStructuralFeature)callExp
					.getReferredProperty());
			propertyCallSource = null;

			if (protectedAreaSource != null) {
				propertyCallInput = protectedAreaSource;
			}
			GeneratedText text = createGeneratedTextFor(callExp);
			if (operationArgumentTrace != null) {
				operationArgumentTrace.addTrace(propertyCallInput, text, result);
			} else if (initializingVariable != null && !(result instanceof EObject)) {
				variableTraces.get(initializingVariable).addTrace(propertyCallInput, text, result);
			} else if (recordedTraces.size() > 0 && shouldRecordTrace(callExp)) {
				recordedTraces.getLast().addTrace(propertyCallInput, text, result);
			}
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

		InputElement input = getInputElement(retrieveScopeEObjectValue());
		if (protectedAreaSource != null) {
			input = protectedAreaSource;
		}
		GeneratedText text = createGeneratedTextFor(literalExp);
		if (operationArgumentTrace != null) {
			operationArgumentTrace.addTrace(input, text, result);
		} else if (initializingVariable != null) {
			variableTraces.get(initializingVariable).addTrace(input, text, result);
		} else if (recordedTraces.size() > 0 && ((String)result).length() > 0
				&& shouldRecordTrace(literalExp)) {
			recordedTraces.getLast().addTrace(input, text, result);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitVariable(org.eclipse.ocl.expressions.Variable)
	 */
	@Override
	public Object visitVariable(Variable<C, PM> variable) {
		final boolean isPrimitive = variable.getType() instanceof PrimitiveType<?>;
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

		initializingVariable = oldVar;
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

		boolean recordOperationArgument = operationArgumentTrace != null;
		boolean recordVariableInitialization = initializingVariable != null
				&& variableTraces.get(variableExp.getReferredVariable()) != null
				&& shouldRecordTrace(variableExp);
		boolean recordTrace = initializingVariable == null && recordedTraces.size() > 0
				&& result instanceof String && ((String)result).length() > 0;

		if (recordVariableInitialization || recordTrace || recordOperationArgument) {
			VariableTrace<C, PM> referredVarTrace = variableTraces.get(variableExp.getReferredVariable());
			if (referredVarTrace != null) {
				for (Map.Entry<InputElement, Set<GeneratedText>> entry : referredVarTrace.getTraces()
						.entrySet()) {
					InputElement input = entry.getKey();
					if (protectedAreaSource != null) {
						input = protectedAreaSource;
					}
					for (GeneratedText text : entry.getValue()) {
						if (recordOperationArgument) {
							operationArgumentTrace.addTrace(input, text, result);
						} else if (recordTrace) {
							recordedTraces.getLast().addTrace(input, text, result);
						} else if (recordVariableInitialization) {
							variableTraces.get(initializingVariable).addTrace(input, text, result);
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
				GeneratedText text = createGeneratedTextFor(variableExp);
				if (recordOperationArgument) {
					operationArgumentTrace.addTrace(input, text, result);
				} else if (recordTrace) {
					recordedTraces.getLast().addTrace(input, text, result);
				} else if (recordVariableInitialization) {
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
	 * This will be used to create the evaluation trace for a given protected area's content and alter the
	 * trace for its marker; indeed traces aren't recorded anywhere else than for the marker evaluation as the
	 * remainder are mainly hard-coded strings.
	 * 
	 * @param content
	 *            Full content of the protected area, starting/ending strings and marker included.
	 * @param protectedArea
	 *            The {@link ProtectedAreaBlock} from which we're generating <em>content</em>.
	 * @param trace
	 *            The execution trace recorded for this area.
	 */
	private void alterProtectedAreaTrace(String content, Block protectedArea, ExpressionTrace<C> trace) {
		final String areaStart = AcceleoEngineMessages.getString("usercode.start") + ' '; //$NON-NLS-1$
		final String areaEnd = AcceleoEngineMessages.getString("usercode.end"); //$NON-NLS-1$
		final int markerIndex = content.indexOf(areaStart) + areaStart.length();
		final int areaEndIndex = content.indexOf(areaEnd);
		final boolean isExistingArea = trace.getTraces().size() == 0;

		int markerEndIndex = content.indexOf("\r\n", markerIndex); //$NON-NLS-1$
		if (markerEndIndex == -1) {
			markerEndIndex = content.indexOf('\n', markerIndex);
		}
		if (markerEndIndex == -1) {
			markerEndIndex = content.indexOf('\r', markerIndex);
		}

		int contentStart = -1;
		int endOffset = -1;

		final List<GeneratedText> contentTraces = new ArrayList<GeneratedText>();
		if (isExistingArea) {
			final GeneratedText markerRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
			markerRegion.setStartOffset(markerIndex);
			markerRegion.setStartOffset(markerEndIndex);
			markerRegion.setModuleElement(getModuleElement(protectedArea));
			trace.addTrace(protectedAreaSource, markerRegion, content);
		} else {
			// Alter indices So that they start and end after the "Start of user code"
			for (Map.Entry<InputElement, Set<GeneratedText>> entry : trace.getTraces().entrySet()) {
				for (GeneratedText text : entry.getValue()) {
					text.setStartOffset(text.getStartOffset() + markerIndex);
					text.setEndOffset(text.getEndOffset() + markerIndex);
					if (text.getEndOffset() > markerEndIndex
							&& (contentStart == -1 || text.getStartOffset() < contentStart)) {
						contentStart = text.getStartOffset();
						contentTraces.add(text);
					}
					if (endOffset == -1 || text.getEndOffset() > endOffset) {
						endOffset = text.getEndOffset();
					}
				}
			}
		}

		/*
		 * If we have traces for the content, ensure whether their offsets are within the [markerEndIndex,
		 * areaEndIndex] range.
		 */
		if (contentStart > markerEndIndex) {
			int gap = contentStart - markerEndIndex;
			endOffset -= gap;
			for (GeneratedText text : contentTraces) {
				text.setStartOffset(text.getStartOffset() - gap);
				text.setEndOffset(text.getEndOffset() - gap);
			}
		}

		// Create a region containing all text preceding the marker
		final GeneratedText startRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
		startRegion.setEndOffset(markerIndex);
		startRegion.setModuleElement(getModuleElement(protectedArea));
		startRegion.setSourceElement(protectedAreaSource);

		// Then a region containing the "end of user code"
		final GeneratedText endRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
		endRegion.setStartOffset(areaEndIndex);
		endRegion.setEndOffset(content.length());
		endRegion.setModuleElement(getModuleElement(protectedArea));
		endRegion.setSourceElement(protectedAreaSource);

		// If we don't have the traces for the content of the area, we'll create them
		GeneratedText contentRegion = null;
		if (endOffset != areaEndIndex) {
			contentRegion = TraceabilityFactory.eINSTANCE.createGeneratedText();
			contentRegion.setStartOffset(endOffset);
			contentRegion.setEndOffset(areaEndIndex);
			contentRegion.setModuleElement(getModuleElement(protectedArea));
			contentRegion.setSourceElement(protectedAreaSource);
		}

		// We need to reorder the whole thing
		LinkedHashSet<GeneratedText> set = (LinkedHashSet<GeneratedText>)trace.getTraces().get(
				protectedAreaSource);
		LinkedHashSet<GeneratedText> copy = new LinkedHashSet<GeneratedText>(set);
		set.clear();

		set.add(startRegion);
		set.addAll(copy);
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
	 * @return {@link GeneratedFile} contained in the {@link #evaluationTrace} model.
	 */
	private GeneratedFile getGeneratedFile(File generatedFile, boolean appendMode) {
		GeneratedFile soughtFile = evaluationTrace.getGeneratedFile(generatedFile.getPath());
		if (soughtFile == null) {
			soughtFile = TraceabilityFactory.eINSTANCE.createGeneratedFile();
			soughtFile.setPath(generatedFile.getPath());
			soughtFile.setName(stripFileNameFrom(generatedFile.getPath()));
			if (appendMode && generatedFile.exists() && generatedFile.canRead()) {
				int length = 0;
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(generatedFile));
					String line = reader.readLine();
					while (line != null) {
						// add 1 for the carriage return
						length += line.length() + 1;
						line = reader.readLine();
					}
				} catch (IOException e) {
					// traceability may not be good on this one
					AcceleoEnginePlugin.log(e, false);
				} finally {
					try {
						if (reader != null) {
							reader.close();
						}
					} catch (IOException e) {
						AcceleoEnginePlugin.log(e, false);
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
	 * model element and neither structural feature nor operation linked. Note that it will be created if
	 * needed.
	 * 
	 * @param modelElement
	 *            Model element we seek the corresponding InputElement of.
	 * @return {@link InputElement} contained in the {@link #evaluationTrace} model.
	 */
	private InputElement getInputElement(EObject modelElement) {
		ModelFile soughtModel = getModelFile(modelElement);

		Set<InputElement> candidateInputs = cachedInputElements.get(modelElement);
		if (candidateInputs == null) {
			candidateInputs = new HashSet<InputElement>();
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
	 * This will return an InputElement contained within {@link #evaluationTrace} corresponding to the given
	 * model element and operation. Note that it will be created if needed.
	 * 
	 * @param modelElement
	 *            Model element we seek the corresponding InputElement of.
	 * @param operation
	 *            EOperation of this input element. Can be <code>null</code>.
	 * @return {@link InputElement} contained in the {@link #evaluationTrace} model.
	 */
	private InputElement getInputElement(EObject modelElement, EOperation operation) {
		ModelFile soughtModel = getModelFile(modelElement);

		Set<InputElement> candidateInputs = cachedInputElements.get(modelElement);
		if (candidateInputs == null) {
			candidateInputs = new HashSet<InputElement>();
			cachedInputElements.put(modelElement, candidateInputs);
		}
		for (InputElement input : candidateInputs) {
			if (input.getOperation() == operation) {
				return input;
			}
		}

		// If we're here, such an InputElement does not already exist
		InputElement soughtElement = TraceabilityFactory.eINSTANCE.createInputElement();
		soughtElement.setModelElement(modelElement);
		soughtElement.setOperation(operation);
		soughtModel.getInputElements().add(soughtElement);
		candidateInputs.add(soughtElement);
		return soughtElement;
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
			candidateInputs = new HashSet<InputElement>();
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
		final URI modelURI = modelElement.eResource().getURI();
		final String name = modelURI.lastSegment();
		String path = modelURI.toString();

		if (path.startsWith("http://") && EMFPlugin.IS_ECLIPSE_RUNNING) { //$NON-NLS-1$
			EPackage pack = EPackage.Registry.INSTANCE.getEPackage(path);
			try {
				URL ecoreURL = AcceleoWorkspaceUtil.getResourceURL(pack.getClass(), "*.ecore"); //$NON-NLS-1$
				if (ecoreURL != null) {
					path = ecoreURL.toString();
				}
			} catch (IOException e) {
				AcceleoEnginePlugin.log(e, false);
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
		ModuleFile soughtModule = getModuleFile(moduleElement);

		ModuleElement element = cachedModuleElements.get(moduleElement);
		if (element != null) {
			return element;
		}

		// If we're here, such a ModuleElement does not already exist
		ModuleElement soughtElement = TraceabilityFactory.eINSTANCE.createModuleElement();
		soughtElement.setModuleElement(moduleElement);
		soughtModule.getModuleElements().add(soughtElement);
		cachedModuleElements.put(moduleElement, soughtElement);
		return soughtElement;
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
				AcceleoEnginePlugin.log(e, false);
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

		/*
		 * All four "substitute" type operations will be handled the same, yet they require that argument
		 * traces be recorded. We'll handle them apart from the rest.
		 */
		if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_SUBSTITUTE)
				|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL)
				|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE)
				|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL)) {
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

			Object sourceObject = super.visitExpression(callExp.getSource());
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
			operationArgumentTrace = oldArgTrace;

			return result;
		}
		/*
		 * FIXME when all cases are handled, externalize source/args evaluation before the if - else if, and
		 * check for invalid values.
		 */
		if (callExp.getOperationCode() == PredefinedType.SUBSTRING) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			Object startIndex = super.visitExpression(callExp.getArgument().get(0));
			Object endIndex = super.visitExpression(callExp.getArgument().get(1));
			result = operationVisitor.visitSubstringOperation((String)sourceObject, ((Integer)startIndex)
					.intValue() - 1, ((Integer)endIndex).intValue());
		} else if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_TRIM)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			result = operationVisitor.visitTrimOperation((String)sourceObject);
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_FIRST)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			Object endIndex = super.visitExpression(callExp.getArgument().get(0));
			result = operationVisitor.visitFirstOperation((String)sourceObject, ((Integer)endIndex)
					.intValue());
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_LAST)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			Object charCount = super.visitExpression(callExp.getArgument().get(0));
			result = operationVisitor.visitLastOperation((String)sourceObject, ((Integer)charCount)
					.intValue());
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_INDEX)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			Object substring = super.visitExpression(callExp.getArgument().get(0));
			result = operationVisitor.visitIndexOperation((String)sourceObject, (String)substring);
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_ISALPHA)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			result = operationVisitor.visitIsAlphaOperation((String)sourceObject);
		} else if (operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_ISALPHANUM)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			result = operationVisitor.visitIsAlphanumOperation((String)sourceObject);
		} else if (operationName.equals(AcceleoNonStandardLibrary.OPERATION_COLLECTION_SEP)) {
			Object sourceObject = super.visitExpression(callExp.getSource());
			ExpressionTrace<C> oldArgTrace = operationArgumentTrace;
			operationArgumentTrace = new ExpressionTrace<C>(callExp.getArgument().get(0));
			Object separator = super.visitExpression(callExp.getArgument().get(0));
			result = operationVisitor.visitSepOperation((Collection<Object>)sourceObject, (String)separator);
			operationArgumentTrace = oldArgTrace;
		} else {
			result = super.visitOperationCallExp(callExp);
		}

		return result;
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
		final int operationCode = operationCall.getOperationCode();

		EClassifier operationEType = ((EOperation)operationCall.getReferredOperation()).getEType();
		if (operationEType == getEnvironment().getOCLStandardLibrary().getString()
				|| operationEType.getName().equals(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME)) {
			// first, switch on the predefined OCL operations
			if (operationCode > 0) {
				isImpacting = operationCode == PredefinedType.SUBSTRING;
			} else {
				final String operationName = ((EOperation)operationCall.getReferredOperation()).getName();
				isImpacting = isImpacting
						|| operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_FIRST);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_INDEX);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_ISALPHA);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_ISALPHANUM);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_LAST);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_STRCMP);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_STRSTR);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_STRTOK);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoStandardLibrary.OPERATION_STRING_SUBSTITUTE);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_CONTAINS);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_ENDSWITH);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_STARTSWITH);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_TOKENIZE);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_STRING_TRIM);
				isImpacting = isImpacting
						|| operationName.equals(AcceleoNonStandardLibrary.OPERATION_COLLECTION_SEP);
			}
		}

		return isImpacting;
	}

	/**
	 * Retrieve the scope value of the currently evaluated expression.
	 * 
	 * @return The scope value of the currently evaluated expression.
	 */
	private EObject retrieveScopeEObjectValue() {
		return retrieveScopeEObjectValue(scopeEObjects.size() - 1);
	}

	/**
	 * Returne the scope value of the n-th scope of this evaluation. 0 being the EObject passed as argument of
	 * the generation, <em>{@link #scopeEObjects}.size() - 1</em> the scope of the current expression.
	 * 
	 * @param index
	 *            Index of the scope EObject value we wish to fetch.
	 * @return The scope value of the n-th scope of this evaluation.
	 */
	@SuppressWarnings("unchecked")
	private EObject retrieveScopeEObjectValue(int index) {
		EObject scopeValue = null;
		for (int i = index; i >= 0; i--) {
			EObject scope = scopeEObjects.get(i);
			if (scope instanceof Variable<?, ?>) {
				final Object value = getEvaluationEnvironment()
						.getValueOf(((Variable<C, PM>)scope).getName());
				if (value instanceof EObject) {
					scopeValue = (EObject)value;
					break;
				}
			} else if (scope instanceof VariableExp<?, ?>) {
				final Object value = getEvaluationEnvironment().getValueOf(
						(((VariableExp<C, PM>)scope).getReferredVariable()).getName());
				if (value instanceof EObject) {
					scopeValue = (EObject)value;
					break;
				}
			} else {
				scopeValue = scope;
				if (scope instanceof OCLExpression<?>) {
					Object self = getEvaluationEnvironment().getValueOf(IAcceleoConstants.SELF);
					if (self instanceof EObject) {
						scopeValue = (EObject)self;
					}
				}
				break;
			}
		}
		return scopeValue;
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
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_Each();
		generate = generate || reference == MtlPackage.eINSTANCE.getFileBlock_FileUrl();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_Before();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_After();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_Before();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_After();
		generate = generate || reference == MtlPackage.eINSTANCE.getProtectedAreaBlock_Marker();
		return generate;
	}

	/**
	 * For a given expression, we'll check whether execution traces mst be recorded. This involves checking
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
		if (expression.eContainingFeature() == MtlPackage.eINSTANCE.getForBlock_IterSet() || !record) {
			return false;
		}
		if (isPropertyCallSource(expression)) {
			result = false;
		} else if (isOperationCallSource(expression)) {
			OperationCallExp<C, O> call = (OperationCallExp<C, O>)expression.eContainer();
			EOperation op = (EOperation)call.getReferredOperation();
			/*
			 * if (isTraceabilityImpactingOperation(call)) { result = false; } else
			 */
			if (op.getEType() != getEnvironment().getOCLStandardLibrary().getString()) {
				result = false;
			}
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
				filePath.substring(slashIndex + 1);
			}
		}
		return filePath;
	}
}
