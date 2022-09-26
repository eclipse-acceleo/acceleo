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
package org.eclipse.acceleo.engine.internal.evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.common.utils.AcceleoASTNodeAdapter;
import org.eclipse.acceleo.common.utils.Deque;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationCancelledException;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.AcceleoRuntimeException;
import org.eclipse.acceleo.engine.internal.debug.ASTFragment;
import org.eclipse.acceleo.engine.internal.debug.IDebugAST;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironment;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEvaluationEnvironment;
import org.eclipse.acceleo.engine.internal.environment.AcceleoLibraryOperationVisitor;
import org.eclipse.acceleo.engine.internal.utils.AcceleoOverrideAdapter;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.profiler.Profiler;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.EvaluationVisitor;
import org.eclipse.ocl.EvaluationVisitorDecorator;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.ocl.ecore.impl.OCLExpressionImpl;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.PropertyCallExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ocl.utilities.PredefinedType;

/**
 * This visitor will handle the evaluation of Acceleo nodes.
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
public class AcceleoEvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> extends EvaluationVisitorDecorator<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> {
	/** The marker of the lines generated inside of the protected area. */
	public static final String PROTECTED_AREA_MARKER = "ACCELEO_PROTECTED_AREA_MARKER_FIT_INDENTATION"; //$NON-NLS-1$

	/** This will be set by launch configs to debug AST evaluations. */
	private static IDebugAST debug;

	/** We'll use this to store the value of the iteration count. */
	private static final String ITERATION_COUNT_VARIABLE_NAME = "i"; //$NON-NLS-1$

	/** To profile an AST evaluation. */
	private static Profiler profile;

	/** Externalized name of the "self" OCL variable to avoid too many distinct uses. */
	private static final String SELF_VARIABLE_NAME = "self"; //$NON-NLS-1$

	/** Holds the prefix we'll use for the temporary context variables created to hold context values. */
	private static final String TEMPORARY_CONTEXT_VAR_PREFIX = "context$"; //$NON-NLS-1$

	/** Holds the prefix we'll use for the temporary variables created to hold argument values. */
	private static final String TEMPORARY_INVOCATION_ARG_PREFIX = "temporaryInvocationVariable$"; //$NON-NLS-1$

	/** Key of the "undefined guard" error message in acceleoenginemessages.properties. */
	private static final String UNDEFINED_GUARD_MESSAGE_KEY = "AcceleoEvaluationVisitor.UndefinedGuard"; //$NON-NLS-1$

	/** Generation context of this visitor. */
	private final AcceleoEvaluationContext<C> context;

	/**
	 * This will be used to construct a "stack" of contexts so as to be able to know the order of their
	 * declaration.
	 */
	private int currentContextIndex;

	/**
	 * This flag will be set to <code>true</code> whenever we start evaluation of init section's variables.
	 */
	private boolean evaluatingInitSection;

	/**
	 * This will be changed to <code>true</code> when generation event should fired and reset to
	 * <code>false</code> whenever they are to be blocked.
	 */
	private boolean fireGenerationEvent;

	/**
	 * This will shut down the generation of the text for protected area marker.
	 */
	private boolean fireProtectedAreaGenerationEvent;

	/** Retrieve invalid once and for all. */
	private final Object invalid = getAcceleoEnvironment().getOCLStandardLibraryReflection().getInvalid();

	/** This will allow us to remember the last EObject value of the self variable. */
	private EObject lastEObjectSelfValue;

	/**
	 * This will be set before each call to a PropertyCallExpression or OperationCallExpression and will allow
	 * us to determine the source of such or such call.
	 */
	private OCLExpression<C> lastSourceExpression;

	/** Keeps track of the result of the last source expression. */
	private Object lastSourceExpressionResult;

	/**
	 * A query returns the same result each time it is called with the same arguments. This map will allow us
	 * to keep the result in cache for faster subsequent calls.
	 */
	private QueryCache queryCache = new QueryCache(getAcceleoEnvironment().getOCLStandardLibraryReflection()
			.getInvalid());

	/** My decorating visitor. */
	private EvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> visitor;

	/**
	 * Default constructor.
	 * 
	 * @param decoratedVisitor
	 *            The evaluation visitor this instance will decorate.
	 * @param context
	 *            The evaluation context this visitor has to take into account.
	 */
	public AcceleoEvaluationVisitor(
			EvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> decoratedVisitor,
			AcceleoEvaluationContext<C> context) {
		super(decoratedVisitor);
		this.context = context;
		// assumes I have no decorator if not set explicitly
		visitor = this;
	}

	/**
	 * To debug an AST evaluation.
	 * 
	 * @param acceleoDebug
	 *            is the new debugger to consider
	 */
	public static void setDebug(IDebugAST acceleoDebug) {
		debug = acceleoDebug;
	}

	/**
	 * Returns the current debugger.
	 * 
	 * @return The current debugger.
	 */
	public static IDebugAST getDebug() {
		return debug;
	}

	/**
	 * To profile an AST evaluation.
	 * 
	 * @param acceleoProfile
	 *            is the new debugger to consider
	 * @since 3.0
	 */
	public static void setProfile(Profiler acceleoProfile) {
		profile = acceleoProfile;
	}

	/**
	 * Returns the profiler.
	 * 
	 * @return The profiler.
	 * @since 3.0
	 */
	public static Profiler getProfiler() {
		return profile;
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
	 */
	public void append(String string, Block sourceBlock, EObject source, boolean fireEvent) {
		getContext().append(string, sourceBlock, source, fireEvent);
	}

	/**
	 * Caches the result of the given invocation.
	 * 
	 * @param query
	 *            The query for which we need to cache results.
	 * @param arguments
	 *            Arguments of the invocation.
	 * @param result
	 *            The result that is to be cached.
	 */
	public void cacheResult(Query query, List<Object> arguments, Object result) {
		queryCache.cacheResult(query, arguments, result);
	}

	/**
	 * Demands creation of a writer for the given file path from the generation strategy.
	 * 
	 * @param generatedFile
	 *            File that is to be created.
	 * @param fileBlock
	 *            The file block which asked for this writer. Only used for generation events.
	 * @param source
	 *            The source EObject for this file block. Only used for generation events.
	 * @param appendMode
	 *            If <code>false</code>, the file will be replaced by a new one.
	 * @param charset
	 *            Charset of the target file.
	 * @throws AcceleoEvaluationException
	 *             Thrown if the file cannot be created.
	 */
	public void createFileWriter(File generatedFile, Block fileBlock, EObject source, boolean appendMode,
			String charset) throws AcceleoEvaluationException {
		if (generatedFile.getName().endsWith(File.separator) || generatedFile.isDirectory()) {
			return;
		}
		getContext().openNested(generatedFile, fileBlock, source, appendMode, charset);
	}

	/**
	 * This will alter all lines of the given <em>text</em> so that they all fit with the indentation of the
	 * last line of the current context.
	 * 
	 * @param source
	 *            Text which indentation is to be altered.
	 * @param indentation
	 *            Indentation that is to be given to all of <em>source</em> lines.
	 * @return The input <em>text</em> after its indentation has been modified to fit the context.
	 */
	public String fitIndentationTo(String source, String indentation) {
		final String result;

		if (!"".equals(indentation)) { //$NON-NLS-1$
			// Do not alter the very first line (^)
			String regex = "\r\n|\r|\n"; //$NON-NLS-1$
			String replacement = "$0" + indentation; //$NON-NLS-1$

			Matcher sourceMatcher = Pattern.compile(regex).matcher(source);
			StringBuffer buffer = new StringBuffer(source.length());
			boolean hasMatch = sourceMatcher.find();
			while (hasMatch) {
				sourceMatcher.appendReplacement(buffer, replacement);
				hasMatch = sourceMatcher.find();
			}
			sourceMatcher.appendTail(buffer);
			result = buffer.toString();
		} else {
			result = source;
		}
		return result;
	}

	/**
	 * Return the cached result for the given invocation.
	 * 
	 * @param query
	 *            The query for which we need cached results.
	 * @param arguments
	 *            Arguments of the invocation.
	 * @return The cached result if any.
	 */
	public Object getCachedResult(Query query, List<Object> arguments) {
		return queryCache.getResult(query, arguments);
	}

	/**
	 * Returns the current evaluation context.
	 * 
	 * @return The current evaluation context.
	 */
	public AcceleoEvaluationContext<C> getContext() {
		return context;
	}

	/**
	 * Handles the evaluation of an Acceleo {@link Block}.
	 * 
	 * @param block
	 *            The Acceleo block that is to be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoBlock(Block block) {
		for (final org.eclipse.ocl.ecore.OCLExpression nested : block.getBody()) {
			getVisitor().visitExpression((OCLExpression<C>)nested);
		}
	}

	/**
	 * Handles the evaluation of an Acceleo {@link FileBlock}.
	 * 
	 * @param fileBlock
	 *            The file block that need be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoFileBlock(FileBlock fileBlock) {
		// evaluate sub expressions
		boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		final Object fileURLResult = getVisitor().visitExpression((OCLExpression<C>)fileBlock.getFileUrl());
		fireGenerationEvent = fireEvents;

		if (isUndefined(fileURLResult)) {
			final AcceleoEvaluationException exception = getContext().createAcceleoException(fileBlock,
					"AcceleoEvaluationVisitor.UndefinedFileURL", getEvaluationEnvironment() //$NON-NLS-1$
							.getValueOf(SELF_VARIABLE_NAME));
			throw exception;
		} else if (fileURLResult instanceof Collection<?>) {
			final AcceleoEvaluationException exception = getContext().createAcceleoException(fileBlock,
					"AcceleoEvaluationVisitor.CollectionFileURL", getEvaluationEnvironment() //$NON-NLS-1$
							.getValueOf(SELF_VARIABLE_NAME));
			throw exception;
		} else if (!(fileURLResult instanceof String)) {
			final AcceleoEvaluationException exception = getContext().createAcceleoException(fileBlock,
					"AcceleoEvaluationVisitor.NotStringFileURL", getEvaluationEnvironment() //$NON-NLS-1$
							.getValueOf(SELF_VARIABLE_NAME));
			throw exception;
		} else if ("".equals(fileURLResult)) { //$NON-NLS-1$
			final AcceleoEvaluationException exception = getContext().createAcceleoException(fileBlock,
					"AcceleoEvaluationVisitor.EmptyFileName", getEvaluationEnvironment() //$NON-NLS-1$
							.getValueOf(SELF_VARIABLE_NAME));
			throw exception;
		}
		final String filePath = String.valueOf(fileURLResult).trim();

		String fileCharset = null;
		if (fileBlock.getCharset() != null) {
			final Object fileCharsetResult = visitExpression((OCLExpression<C>)fileBlock.getCharset());
			if (isUndefined(fileCharsetResult)) {
				final AcceleoEvaluationException exception = getContext().createAcceleoException(fileBlock,
						"AcceleoEvaluationVisitor.UndefinedFileCharset", getEvaluationEnvironment() //$NON-NLS-1$
								.getValueOf(SELF_VARIABLE_NAME));
				AcceleoEnginePlugin.log(exception, false);
			}
			fileCharset = String.valueOf(fileCharsetResult);
		}

		final boolean appendMode = fileBlock.getOpenMode().getValue() == OpenModeKind.APPEND_VALUE;
		if (!appendMode) {
			getContext().generateFile(filePath);
		}

		final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		final EObject source;
		if (currentSelf instanceof EObject) {
			source = (EObject)currentSelf;
		} else {
			source = lastEObjectSelfValue;
		}

		getContext().getProgressMonitor().subTask(AcceleoEngineMessages.getString(
				"AcceleoEvaluationVisitor.Generatingfile", filePath)); //$NON-NLS-1$
		if ("stdout".equals(filePath)) { //$NON-NLS-1$
			getContext().openNested(System.out);
		} else {
			delegateCreateFileWriter(filePath, fileBlock, source, appendMode, fileCharset);
		}
		// TODO handle file ID
		for (final org.eclipse.ocl.ecore.OCLExpression nested : fileBlock.getBody()) {
			fireGenerationEvent = true;
			getVisitor().visitExpression((OCLExpression<C>)nested);
			fireGenerationEvent = fireEvents;
		}
		getContext().closeContext(fileBlock, source);
		getContext().getProgressMonitor().worked(1);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link ForBlock}.
	 * 
	 * @param forBlock
	 *            The Acceleo block that is to be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoForBlock(ForBlock forBlock) {
		boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		final Object iteration = getVisitor().visitExpression((OCLExpression<C>)forBlock.getIterSet());
		fireGenerationEvent = fireEvents;
		final Variable loopVariable = forBlock.getLoopVariable();
		final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		if (isUndefined(iteration)) {
			throw getContext().createAcceleoException(forBlock,
					"AcceleoEvaluationVisitor.InvalidForIteration", currentSelf); //$NON-NLS-1$
		}
		// There is a possibility for the for to have a single element in its iteration
		final Collection<Object> actualIteration;
		if (iteration instanceof Collection<?>) {
			actualIteration = (Collection<Object>)iteration;
		} else {
			actualIteration = new ArrayList<Object>();
			((List<Object>)actualIteration).add(iteration);
		}
		if (actualIteration.size() > 0 && forBlock.getBefore() != null) {
			getVisitor().visitExpression((OCLExpression<C>)forBlock.getBefore());
		}
		final Iterator<Object> contentIterator = actualIteration.iterator();
		// This will be used to only record and log a single CCE if many arise with this loop
		boolean iterationCCE = false;
		// This will be used to generate separators only if the iterator had a previous element
		boolean hasPrevious = false;
		String implicitContextVariableName = null;
		// The iteration count will start at 1 to match with OCL
		int count = 0;
		try {
			while (contentIterator.hasNext()) {
				count++;
				if (count == 1) {
					getEvaluationEnvironment().add(ITERATION_COUNT_VARIABLE_NAME, Integer.valueOf(count));
				} else {
					getEvaluationEnvironment().replace(ITERATION_COUNT_VARIABLE_NAME, Integer.valueOf(count));
				}
				final Object o = contentIterator.next();
				// null typed loop variables will be the same as "Object" typed
				// We could have no loop variables. In such cases, "self" will do
				// TODO implicit loop variables (self) are non standard. provide a way to deactivate
				if (loopVariable != null && loopVariable.getType() != null) {
					// class cast check on each iteration value
					// [436843] "OclAny" is a wild card such as "Object" would be in java.
					if (!loopVariable.getType().isInstance(o) && loopVariable
							.getType() != getAcceleoEnvironment().getOCLStandardLibrary().getOclAny()) {
						if (!iterationCCE) {
							logIterationError(forBlock, loopVariable, o);
							iterationCCE = true;
						}
						continue;
					}
				}
				if (loopVariable != null) {
					// Do not remove previous values of the loop variable if this is the first iteration.
					if (count == 1) {
						getEvaluationEnvironment().add(loopVariable.getName(), o);
					} else {
						getEvaluationEnvironment().replace(loopVariable.getName(), o);
					}
				}
				if (implicitContextVariableName == null) {
					implicitContextVariableName = addContextVariableFor(o);
				} else {
					getEvaluationEnvironment().replace(implicitContextVariableName, o);
				}
				// [255379] sets new value of "self" to change context
				getEvaluationEnvironment().add(SELF_VARIABLE_NAME, o);
				final Object guardValue;
				if (forBlock.getGuard() == null) {
					guardValue = Boolean.TRUE;
				} else {
					fireGenerationEvent = false;
					guardValue = getVisitor().visitExpression((OCLExpression<C>)forBlock.getGuard());
					fireGenerationEvent = fireEvents;
				}
				if (isInvalid(guardValue)) {
					final AcceleoEvaluationException exception = getContext().createAcceleoException(forBlock,
							(OCLExpression<C>)forBlock.getGuard(), UNDEFINED_GUARD_MESSAGE_KEY, o);
					throw exception;
				}
				if (guardValue != null && ((Boolean)guardValue).booleanValue()) {
					if (forBlock.getEach() != null && hasPrevious) {
						getVisitor().visitExpression((OCLExpression<C>)forBlock.getEach());
						/*
						 * no need to reset the state of the "previous" boolean as all following do have a
						 * previous item
						 */
					}
					for (final org.eclipse.ocl.ecore.OCLExpression nested : forBlock.getBody()) {
						getVisitor().visitExpression((OCLExpression<C>)nested);
					}
					hasPrevious = true;
				}
				// [255379] restore context
				getEvaluationEnvironment().remove(SELF_VARIABLE_NAME);
			}
		} finally {
			if (count > 0) {
				if (loopVariable != null) {
					getEvaluationEnvironment().remove(loopVariable.getName());
				}
				getEvaluationEnvironment().remove(implicitContextVariableName);
				currentContextIndex--;
				getEvaluationEnvironment().remove(ITERATION_COUNT_VARIABLE_NAME);
			}
		}
		if (actualIteration.size() > 0 && forBlock.getAfter() != null) {
			getVisitor().visitExpression((OCLExpression<C>)forBlock.getAfter());
		}
	}

	/**
	 * Logs erroneous typing errors as they occur during for loop evaluations. This will only be called once
	 * per for block even if multiple values have the wrong type in a single iteration.
	 * 
	 * @param forBlock
	 *            The for loop on which a class cast occurred.
	 * @param loopVariable
	 *            The iteration variable of this for block.
	 * @param iterationValue
	 *            Current value of the iteration.
	 */
	private void logIterationError(ForBlock forBlock, Variable loopVariable, Object iterationValue) {
		int line = getLineOf(forBlock);
		String actualType = "null"; //$NON-NLS-1$
		if (iterationValue != null) {
			actualType = iterationValue.getClass().getName();
		}
		final String expectedType = loopVariable.getType().getName();
		final String message = AcceleoEngineMessages.getString("AcceleoEvaluationVisitor.IterationClassCast", //$NON-NLS-1$
				Integer.valueOf(line), ((Module)EcoreUtil.getRootContainer(forBlock)).getName(), forBlock
						.toString(), actualType, expectedType);
		final AcceleoEvaluationException exception = new AcceleoEvaluationException(message);
		exception.setStackTrace(getContext().createAcceleoStackTrace());
		AcceleoEnginePlugin.log(exception, false);

	}

	/**
	 * Handles the evaluation of an Acceleo {@link IfBlock}.
	 * 
	 * @param ifBlock
	 *            The Acceleo block that is to be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoIfBlock(IfBlock ifBlock) {
		final OCLExpression<C> condition = (OCLExpression<C>)ifBlock.getIfExpr();
		final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);

		boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		final Object conditionValue = getVisitor().visitExpression(condition);
		fireGenerationEvent = fireEvents;
		if (isInvalid(conditionValue)) {
			final AcceleoEvaluationException exception = getContext().createAcceleoException(ifBlock,
					"AcceleoEvaluationVisitor.UndefinedCondition", currentSelf); //$NON-NLS-1$
			throw exception;
		}

		if (conditionValue != null && !(conditionValue instanceof Boolean)) {
			throw getContext().createAcceleoException(ifBlock, condition,
					"AcceleoEvaluationVisitor.NotBooleanCondition", currentSelf); //$NON-NLS-1$
		}

		if (conditionValue != null && ((Boolean)conditionValue).booleanValue()) {
			for (final org.eclipse.ocl.ecore.OCLExpression nested : ifBlock.getBody()) {
				getVisitor().visitExpression((OCLExpression<C>)nested);
			}
		} else {
			if (ifBlock.getElseIf().size() > 0) {
				// If one of the else ifs has its condition evaluated to true, this will hold it
				IfBlock temp = null;
				for (final IfBlock elseif : ifBlock.getElseIf()) {
					fireGenerationEvent = false;
					final Object elseValue = getVisitor().visitExpression((OCLExpression<C>)elseif
							.getIfExpr());
					fireGenerationEvent = fireEvents;
					if (isInvalid(elseValue)) {
						final AcceleoEvaluationException exception = getContext().createAcceleoException(
								elseif, "AcceleoEvaluationVisitor.UndefinedElseCondition", currentSelf); //$NON-NLS-1$
						throw exception;
					}

					if (elseValue != null && !(elseValue instanceof Boolean)) {
						throw getContext().createAcceleoException(ifBlock, (OCLExpression<C>)elseif
								.getIfExpr(), "AcceleoEvaluationVisitor.NotBooleanCondition", currentSelf); //$NON-NLS-1$
					}

					if (elseValue != null && ((Boolean)elseValue).booleanValue()) {
						temp = elseif;
						break;
					}
				}
				if (temp != null) {
					for (final org.eclipse.ocl.ecore.OCLExpression nested : temp.getBody()) {
						getVisitor().visitExpression((OCLExpression<C>)nested);
					}
				} else if (ifBlock.getElse() != null) {
					visitAcceleoBlock(ifBlock.getElse());
				}
			} else if (ifBlock.getElse() != null) {
				visitAcceleoBlock(ifBlock.getElse());
			}
		}
	}

	/**
	 * Handles the evaluation of an Acceleo {@link InitSection}. This will simply add all the declared
	 * variables to the evaluation environment. Evaluation of all blocks should use this in order to save the
	 * variables state <b>before</b> evaluation and be able to restore it afterwards through
	 * {@link #restoreVariables()} .
	 * 
	 * @param init
	 *            The init section containing the variables to process.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoInitSection(InitSection init) {
		final boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		evaluatingInitSection = true;
		for (final Variable var : init.getVariable()) {
			getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)var);
		}
		evaluatingInitSection = false;
		fireGenerationEvent = fireEvents;
	}

	/**
	 * Handles the evaluation of an Acceleo {@link LetBlock}.
	 * 
	 * @param letBlock
	 *            The Acceleo let block that is to be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoLetBlock(LetBlock letBlock) {
		final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		Variable var = letBlock.getLetVariable();
		boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		Object value = getVisitor().visitExpression((OCLExpression<C>)var.getInitExpression());
		fireGenerationEvent = fireEvents;
		if (isInvalid(value)) {
			final AcceleoEvaluationException exception = getContext().createAcceleoException(letBlock,
					"AcceleoEvaluationVisitor.UndefinedLetValue", currentSelf); //$NON-NLS-1$
			throw exception;
		}

		// This will hold the name of the variable which value we replaced
		String varName = null;
		try {
			if (var.getType().isInstance(value) || var.getType() == getEnvironment().getOCLStandardLibrary()
					.getOclAny()) {
				varName = var.getName();
				getEvaluationEnvironment().add(varName, value);
				for (final org.eclipse.ocl.ecore.OCLExpression nested : letBlock.getBody()) {
					getVisitor().visitExpression((OCLExpression<C>)nested);
				}
			} else {
				if (letBlock.getElseLet().size() > 0) {
					// If one of the else lets has its "instanceof" condition evaluated to true, this will
					// hold it
					LetBlock temp = null;
					for (final LetBlock elseLet : letBlock.getElseLet()) {
						var = elseLet.getLetVariable();
						fireGenerationEvent = false;
						value = visitExpression((OCLExpression<C>)var.getInitExpression());
						fireGenerationEvent = fireEvents;
						if (isInvalid(value)) {
							final AcceleoEvaluationException exception = getContext().createAcceleoException(
									elseLet, "AcceleoEvaluationVisitor.UndefinedElseLetValue", currentSelf); //$NON-NLS-1$
							throw exception;
						}
						if (var.getType().isInstance(value)) {
							varName = var.getName();
							getEvaluationEnvironment().add(var.getName(), value);
							temp = elseLet;
							break;
						}
					}
					if (temp != null) {
						for (final org.eclipse.ocl.ecore.OCLExpression nested : temp.getBody()) {
							getVisitor().visitExpression((OCLExpression<C>)nested);
						}
					} else if (letBlock.getElse() != null) {
						visitAcceleoBlock(letBlock.getElse());
					}
				} else if (letBlock.getElse() != null) {
					visitAcceleoBlock(letBlock.getElse());
				}
			}
		} finally {
			if (varName != null) {
				getEvaluationEnvironment().remove(varName);
			}
		}
	}

	/**
	 * Handles the evaluation of an Acceleo {@link ProtectedAreaBlock}.
	 * 
	 * @param protectedArea
	 *            The Acceleo protected area that is to be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoProtectedArea(ProtectedAreaBlock protectedArea) {
		boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		fireProtectedAreaGenerationEvent = false;
		final Object markerValue = getVisitor().visitExpression((OCLExpression<C>)protectedArea.getMarker());
		fireProtectedAreaGenerationEvent = true;
		fireGenerationEvent = fireEvents;
		final Object source = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		if (isUndefined(markerValue)) {
			final AcceleoEvaluationException exception = getContext().createAcceleoException(protectedArea,
					"AcceleoEvaluationVisitor.UndefinedAreaMarker", source); //$NON-NLS-1$
			throw exception;
		}

		final String marker = toString(markerValue).trim();
		final String areaContent = getContext().getProtectedAreaContent(marker);
		if (source instanceof EObject) {
			lastEObjectSelfValue = (EObject)source;
		}
		if (areaContent != null) {
			if (isInFileBlock(protectedArea)) {
				delegateAppend(areaContent, protectedArea, lastEObjectSelfValue, fireGenerationEvent);
			} else {
				// the content of the protected area has its own indentation. Do not touch it.
				// Replace with different markers according to the current line separator
				String actualContent = areaContent.replaceAll("\r\n", PROTECTED_AREA_MARKER + "{rn}"); //$NON-NLS-1$ //$NON-NLS-2$
				actualContent = actualContent.replaceAll("\r", PROTECTED_AREA_MARKER + "{r}"); //$NON-NLS-1$ //$NON-NLS-2$
				actualContent = actualContent.replaceAll("\n", PROTECTED_AREA_MARKER + "{n}"); //$NON-NLS-1$ //$NON-NLS-2$
				delegateAppend(actualContent, protectedArea, lastEObjectSelfValue, fireGenerationEvent);
			}
		} else {
			// Build the OCLExpressions we'll visit for the protected area surroundings
			final StringLiteralExp userCodeStart = EcoreFactory.eINSTANCE.createStringLiteralExp();
			userCodeStart.setStringSymbol(AcceleoEngineMessages.getString("usercode.start") + ' '); //$NON-NLS-1$

			final StringLiteralExp userCodeEnd = EcoreFactory.eINSTANCE.createStringLiteralExp();
			userCodeEnd.setStringSymbol(AcceleoEngineMessages.getString("usercode.end")); //$NON-NLS-1$

			// add the two to a "dummy" protected area so that their containing feature is known
			ProtectedAreaBlock dummy = MtlFactory.eINSTANCE.createProtectedAreaBlock();
			dummy.getBody().add(userCodeStart);
			dummy.getBody().add(userCodeEnd);

			getVisitor().visitExpression((OCLExpression<C>)userCodeStart);
			getVisitor().visitExpression((OCLExpression<C>)protectedArea.getMarker());
			visitAcceleoBlock(protectedArea);
			getVisitor().visitExpression((OCLExpression<C>)userCodeEnd);
		}
	}

	/**
	 * This will be used to remove all protected area indentation markers and replace them with the line
	 * terminator that was previously in their place.
	 * 
	 * @param string
	 *            The content from which we are to remove all markers.
	 * @return The protected content without any indentation marker.
	 */
	public static String removeProtectedMarkers(String string) {
		final Matcher matcher = Pattern.compile(PROTECTED_AREA_MARKER + "\\{([^}])([^}])?\\}") //$NON-NLS-1$
				.matcher(string);
		if (matcher.find()) {
			final StringBuffer buffer = new StringBuffer();
			do {
				final String replacement;
				if (matcher.group(2) != null) {
					replacement = "\r\n"; //$NON-NLS-1$
				} else if ("n".equals(matcher.group(1))) { //$NON-NLS-1$
					replacement = "\n"; //$NON-NLS-1$
				} else {
					replacement = "\r"; //$NON-NLS-1$
				}
				matcher.appendReplacement(buffer, replacement);
			} while (matcher.find());
			matcher.appendTail(buffer);
			return buffer.toString();
		}
		return string;
	}

	/**
	 * Handles the evaluation of an Acceleo {@link QueryInvocation}.
	 * 
	 * @param invocation
	 *            The Acceleo query invocation that is to be evaluated.
	 * @return result of the invocation.
	 */
	@SuppressWarnings("unchecked")
	public Object visitAcceleoQueryInvocation(QueryInvocation invocation) {
		if (getContext().getProgressMonitor().isCanceled()) {
			cancel(new ASTFragment(invocation));
		}
		final Query query = invocation.getDefinition();
		String implicitContextVariableName = null;

		final List<Object> arguments = prepareInvocation(invocation);

		final Query dynamicQuery = prepareInvocation(invocation, arguments);

		// If the query has already been run with these arguments, return the cached result
		Object cachedResult = delegateGetCachedResult(dynamicQuery, arguments);
		if (QueryCache.isCachedResult(cachedResult)) {
			// We no longer need the variables at their current value.
			for (Variable var : dynamicQuery.getParameter()) {
				getEvaluationEnvironment().remove(var.getName());
			}
			if (QueryCache.isInvalid(cachedResult) && EMFPlugin.IS_ECLIPSE_RUNNING && AcceleoPreferences
					.isDebugMessagesEnabled()) {
				final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
				final AcceleoEvaluationException exception = getContext().createAcceleoException(dynamicQuery,
						(OCLExpression<C>)dynamicQuery.getExpression(),
						"AcceleoEvaluationVisitor.InvalidQuery", //$NON-NLS-1$
						currentSelf);
				throw exception;
			}
			if (QueryCache.isNull(cachedResult)) {
				cachedResult = null;
			}
			return cachedResult;
		}

		// [255379] change context
		if (arguments.size() > 0) {
			getEvaluationEnvironment().add(SELF_VARIABLE_NAME, arguments.get(0));
			implicitContextVariableName = addContextVariableFor(arguments.get(0));
		}

		Object result = null;
		try {
			result = visitExpression((OCLExpression<C>)dynamicQuery.getExpression());
		} finally {
			// restores parameters as they were prior to the call
			for (Variable var : dynamicQuery.getParameter()) {
				getEvaluationEnvironment().remove(var.getName());
			}
			// [255379] restore context if need be
			if (arguments.size() > 0) {
				getEvaluationEnvironment().remove(SELF_VARIABLE_NAME);
				getEvaluationEnvironment().remove(implicitContextVariableName);
				currentContextIndex--;
			}
		}

		// Store result of the query invocation
		delegateCacheResult(dynamicQuery, arguments, result);
		if (isInvalid(result) && AcceleoPreferences.isDebugMessagesEnabled()) {
			final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
			final AcceleoEvaluationException exception = getContext().createAcceleoException(dynamicQuery,
					(OCLExpression<C>)dynamicQuery.getExpression(), "AcceleoEvaluationVisitor.InvalidQuery", //$NON-NLS-1$
					currentSelf);
			throw exception;
		}
		return result;
	}

	/**
	 * Handles the evaluation of an Acceleo {@link Template}.
	 * 
	 * @param template
	 *            The Acceleo Template that is to be evaluated.
	 * @return Result of the template evaluation.
	 */
	@SuppressWarnings("unchecked")
	public String visitAcceleoTemplate(Template template) {
		getContext().openNested();
		/*
		 * Variables have been positioned by either the AcceleoEngine (first template) or this visitor
		 * (template invocation).
		 */
		List<org.eclipse.ocl.ecore.OCLExpression> nestedExpressions = template.getBody();
		for (int i = 0; i < nestedExpressions.size(); i++) {
			getVisitor().visitExpression((OCLExpression<C>)nestedExpressions.get(i));
		}
		String result = getContext().closeContext();
		if (template.getPost() != null) {
			getEvaluationEnvironment().add(SELF_VARIABLE_NAME, result);
			final Object postResult = getVisitor().visitExpression((OCLExpression<C>)template.getPost());
			getEvaluationEnvironment().remove(SELF_VARIABLE_NAME);
			if (isInvalid(postResult)) {
				int line = getLineOf(template);
				final String moduleName = ((Module)EcoreUtil.getRootContainer(template)).getName();
				final String message = AcceleoEngineMessages.getString(
						"AcceleoEvaluationVisitor.UndefinedPost", template.getPost(), Integer.valueOf(line), //$NON-NLS-1$
						moduleName, template, result);
				final AcceleoEvaluationException exception = new AcceleoEvaluationException(message);
				throw exception;
			}
			result = toString(postResult);
		}
		return result;
	}

	/**
	 * Handles the evaluation of an Acceleo {@link TemplateInvocation}.
	 * 
	 * @param invocation
	 *            The Acceleo template invocation that is to be evaluated.
	 * @return result of the invocation.
	 */
	@SuppressWarnings("unchecked")
	public Object visitAcceleoTemplateInvocation(TemplateInvocation invocation) {
		String implicitContextVariableName = null;

		// FIXME handle multiple invocations and "each"
		final Template actualTemplate = prepareInvocation(invocation);
		if (actualTemplate.getParameter().size() > 0) {
			final Object contextValue = getEvaluationEnvironment().getValueOf(actualTemplate.getParameter()
					.get(0).getName());
			// [255379] sets new value of "self" to match the very first arg of the invocation
			getEvaluationEnvironment().add(SELF_VARIABLE_NAME, contextValue);
			implicitContextVariableName = addContextVariableFor(contextValue);
		}

		getContext().openNested();
		if (invocation.getBefore() != null) {
			getVisitor().visitExpression((OCLExpression<C>)invocation.getBefore());
		}
		final Object source = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		if (source instanceof EObject) {
			lastEObjectSelfValue = (EObject)source;
		}
		try {
			final Object result = getVisitor().visitExpression((OCLExpression<C>)actualTemplate);
			delegateAppend(toString(result), actualTemplate, lastEObjectSelfValue, false);
		} finally {
			// restore parameters as they were prior to the call
			for (int i = 0; i < actualTemplate.getParameter().size(); i++) {
				final Variable param = actualTemplate.getParameter().get(i);
				param.setInitExpression(null);
				getEvaluationEnvironment().remove(param.getName());
			}
			// [255379] restore self if need be
			if (actualTemplate.getParameter().size() > 0) {
				getEvaluationEnvironment().remove(SELF_VARIABLE_NAME);
				getEvaluationEnvironment().remove(implicitContextVariableName);
				currentContextIndex--;
			}
		}
		if (invocation.getAfter() != null) {
			getVisitor().visitExpression((OCLExpression<C>)invocation.getAfter());
		}
		// Close the invoked template's variable scope now
		((AcceleoEvaluationEnvironment)getEvaluationEnvironment()).removeVariableScope();
		String invocationResult = getContext().closeContext();
		if (evaluatingInitSection) {
			return invocationResult;
		}

		return delegateFitIndentation(invocationResult);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationVisitor#visitExpression(org.eclipse.ocl.expressions.OCLExpression)
	 */
	@Override
	public Object visitExpression(OCLExpression<C> expression) {
		Object result = null;
		EObject debugInput = null;
		ASTFragment astFragment = null;

		getContext().addToStack(expression);

		if (debug != null && !(expression instanceof StringLiteralExp)) {
			debugInput = lastEObjectSelfValue;
			astFragment = new ASTFragment(expression);
			if (debugInput != null && debugInput.eClass().getEStructuralFeature("name") != null) { //$NON-NLS-1$
				Object name = debugInput.eGet(debugInput.eClass().getEStructuralFeature("name")); //$NON-NLS-1$
				if (name instanceof String) {
					astFragment.setEObjectNameFilter((String)name);
				}
			}
			debug.startDebug(astFragment);
			Map<String, Object> vars;
			if (getEvaluationEnvironment() instanceof AcceleoEvaluationEnvironment) {
				vars = ((AcceleoEvaluationEnvironment)getEvaluationEnvironment()).getCurrentVariables();
			} else {
				vars = new HashMap<String, Object>();
			}
			debug.stepDebugInput(astFragment, vars);
		}
		if (profile != null && profileExpression(expression)) {
			profile.start(expression);
			profile.loop(lastEObjectSelfValue);
		}
		// This try / catch block allows us to handle the disposal of all context information.
		final boolean hasInit = expression instanceof Block && ((Block)expression).getInit() != null;
		try {
			// All evaluations pass through here. We'll handle blocks' init sections here.
			if (hasInit) {
				visitAcceleoInitSection(((Block)expression).getInit());
			}
			// Actual delegation to the visitor's methods.
			final boolean fireEvents = fireGenerationEvent;
			if (expression == lastSourceExpression) {
				fireGenerationEvent = false;
			}
			result = switchExpression(expression);
			fireGenerationEvent = fireEvents;

			if (expression == lastSourceExpression) {
				lastSourceExpressionResult = result;
			}
			if (shouldGenerateText((EReference)expression.eContainingFeature())) {
				Object source = null;
				// TODO get last structural feature
				Block generatedBlock = getContext().getLastVisitedBlock();
				if (generatedBlock == null) {
					EObject temp = expression;
					while (!(temp instanceof Block) && temp.eContainer() != null) {
						temp = temp.eContainer();
					}
					if (temp instanceof Block) {
						generatedBlock = (Block)temp;
					}
				}
				if (lastSourceExpressionResult == null) {
					source = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
				} else {
					source = lastSourceExpressionResult;
					lastSourceExpressionResult = null;
				}
				if (source instanceof EObject) {
					lastEObjectSelfValue = (EObject)source;
				}
				if (result != null) {
					boolean fireEvent = fireEvent(expression);
					delegateAppend(toString(result), generatedBlock, lastEObjectSelfValue, fireEvent);
				}
			}
		} catch (final AcceleoEvaluationCancelledException e) {
			/*
			 * We already took care of all disposal, yet we need this catch to avoid falling back to the
			 * generic catch (AcceleoEvaluationException).
			 */
			throw e;
		} catch (final AcceleoEvaluationException e) {
			// We'll Try and carry on evaluating the expressions
			AcceleoEnginePlugin.log(e, false);
		} catch (final AcceleoRuntimeException e) {
			// We simply need to propagate this, this block is here to avoid falling back to the catch
			// RuntimeException
			throw e;
			// CHECKSTYLE:OFF
			/*
			 * deactivated checkstyle as we need to properly dispose the context when an exception is thrown
			 * yet we cannot use finally (this visitor is called recursively).
			 */
		} catch (final RuntimeException e) {
			// CHECKSTYLE:ON
			AcceleoRuntimeException acceleoException = getContext().createAcceleoRuntimeException(e);
			try {
				getContext().dispose();
			} catch (final AcceleoEvaluationException ee) {
				// We're already in an exception handling phase. Propagate the former exception.
			}
			throw acceleoException;
		} finally {
			if (debug != null && !(expression instanceof StringLiteralExp)) {
				debug.stepDebugOutput(astFragment, debugInput, result);
				debug.endDebug(astFragment);
			}
			if (profile != null && profileExpression(expression)) {
				profile.stop();
			}
			// If we were evaluating a block and it had an init section, restore variables as they were now.
			if (hasInit) {
				restoreInitVariables(((Block)expression).getInit());
			}
			getContext().removeFromStack();
		}

		return result;
	}

	/**
	 * Fire (or not) a generation event for the given expression evaluated.
	 * 
	 * @param expression
	 *            The expression evaluated
	 * @return <code>true</code> if a generation has been fired, <code>false</code> otherwise.
	 */
	private boolean fireEvent(OCLExpression<C> expression) {
		boolean fireEvent = fireGenerationEvent && !(expression instanceof Template);
		boolean preventTemplateInvocation = true;

		if (expression.eContainingFeature().equals(MtlPackage.eINSTANCE.getBlock_Body())) {
			EObject eContainer = expression.eContainer();
			while (eContainer != null && !(eContainer instanceof FileBlock) && MtlPackage.eINSTANCE
					.getBlock_Body().equals(eContainer.eContainingFeature())) {
				eContainer = eContainer.eContainer();
			}

			if (eContainer instanceof FileBlock) {
				// If this file block has been called by another template, fire an event
				Deque<OCLExpression<C>> expressionStack = this.context.getExpressionStack();
				for (int i = expressionStack.size() - 1; i >= 0; i--) {
					OCLExpression<C> oclExpression = expressionStack.get(i);
					if (oclExpression instanceof TemplateInvocation && expressionStack.indexOf(
							eContainer) > expressionStack.indexOf(oclExpression)) {
						preventTemplateInvocation = false;
						break;
					}
				}
			}
		}

		if (preventTemplateInvocation) {
			fireEvent = fireEvent && !(expression instanceof TemplateInvocation);
		}
		return fireEvent;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitOperationCallExp(org.eclipse.ocl.expressions.OperationCallExp)
	 */
	@Override
	public Object visitOperationCallExp(OperationCallExp<C, O> callExp) {
		// We need something a little more intelligent to avoid overriding all "+" operators
		if (callExp.getOperationCode() == PredefinedType.PLUS) {
			final C stringType = getEnvironment().getOCLStandardLibrary().getString();
			if (callExp.getSource().getType() == stringType || callExp.getArgument().get(0)
					.getType() == stringType) {
				((EObject)callExp.getReferredOperation()).eAdapters().add(new AcceleoOverrideAdapter());
			}
		} else if (((EObject)callExp.getReferredOperation()).eIsProxy()) {
			/*
			 * OCL used "/" as operation name for the division ... Which means divisions will never be
			 * serializable : its URI is invalid. We'll then try and "trick" OCL for these.
			 */
			URI uri = ((InternalEObject)callExp.getReferredOperation()).eProxyURI();
			if (uri.fragment() != null && uri.fragment().endsWith("%2F")) { //$NON-NLS-1$
				callExp.setOperationCode(PredefinedType.DIVIDE);
			}
		}

		// We know these are handled by us, no need to carry on with OCL.
		final EOperation operation = (EOperation)callExp.getReferredOperation();
		Object result;
		lastSourceExpression = callExp.getSource();
		boolean isStandardOperation = false;
		boolean isNonStandardOperation = false;
		final List<EAnnotation> annotations = operation.getEAnnotations();
		for (int i = 0; i < annotations.size(); i++) {
			EAnnotation annotation = annotations.get(i);
			if ("MTL non-standard".equals(annotation.getSource())) { //$NON-NLS-1$
				isNonStandardOperation = true;
				break;
			} else if ("MTL".equals(annotation.getSource())) { //$NON-NLS-1$
				isStandardOperation = true;
				break;
			}
		}
		if (isStandardOperation || isNonStandardOperation) {
			final Object source = getDelegate().visitExpression(callExp.getSource());
			final Object[] args = new Object[callExp.getArgument().size()];
			for (int i = 0; i < callExp.getArgument().size(); i++) {
				args[i] = getVisitor().visitExpression(callExp.getArgument().get(i));
			}
			try {
				if (isStandardOperation) {
					result = AcceleoLibraryOperationVisitor.callStandardOperation(
							(AcceleoEvaluationEnvironment)getEvaluationEnvironment(), operation, source,
							args);
				} else {
					result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
							(AcceleoEvaluationEnvironment)getEvaluationEnvironment(), operation, source,
							args);
				}
				// CHECKSTYLE:OFF
			} catch (Exception e) {
				// CHECKSTYLE:ON
				AcceleoEnginePlugin.log(e, true);

				// Set the result to invalid
				result = this.invalid;
			}
		} else {
			return getDelegate().visitOperationCallExp(callExp);
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
		lastSourceExpression = callExp.getSource();
		return getDelegate().visitPropertyCallExp(callExp);
	}

	/**
	 * Sets the visitor on which I perform nested visitor calls.
	 * 
	 * @param decoratingVisitor
	 *            The visitor decorating me.
	 */
	void setVisitor(EvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> decoratingVisitor) {
		visitor = decoratingVisitor;
	}

	/**
	 * If my delegate (as returned by {@link #getVisitor()} is an {@link AcceleoEvaluationVisitorDecorator},
	 * this will return it once cast.
	 * 
	 * @return The decorating visitor if it is an {@link AcceleoEvaluationVisitorDecorator}, <code>null</code>
	 *         otherwise.
	 */
	protected final AcceleoEvaluationVisitorDecorator<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> getAcceleoVisitor() {
		if (getVisitor() instanceof AcceleoEvaluationVisitorDecorator<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) {
			return (AcceleoEvaluationVisitorDecorator<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E>)getVisitor();
		}
		return null;
	}

	/**
	 * Returns the decorating visitor.
	 * 
	 * @return The decorating visitor.
	 */
	protected final EvaluationVisitor<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> getVisitor() {
		return visitor;
	}

	/**
	 * Adds a context variable for the given Object.
	 * 
	 * @param contextValue
	 *            Value of the variable that is to be created.
	 * @return Name of the newly created variable.
	 */
	private String addContextVariableFor(Object contextValue) {
		String variableName = TEMPORARY_CONTEXT_VAR_PREFIX + currentContextIndex++;
		while (getEvaluationEnvironment().getValueOf(variableName) != null) {
			variableName = TEMPORARY_CONTEXT_VAR_PREFIX + currentContextIndex++;
		}
		getEvaluationEnvironment().add(variableName, contextValue);
		return variableName;
	}

	/**
	 * Cancels the current evaluation. <b>Note</b> that the normal execution of this method will throw a
	 * runtime exception.
	 * 
	 * @param astFragment
	 *            Current debug AST fragment.
	 */
	private void cancel(ASTFragment astFragment) {
		// #276667 "debug" can be null
		if (debug != null) {
			debug.endDebug(astFragment);
			debug = null;
		}
		getContext().dispose();
		throw new AcceleoEvaluationCancelledException(AcceleoEngineMessages.getString(
				"AcceleoEvaluationVisitor.CancelException")); //$NON-NLS-1$
	}

	/**
	 * If I have an {@link AcceleoEvaluationVisitorDecorator Acceleo-specific decorator}, I'll delegate all
	 * appending to it.
	 * 
	 * @param string
	 *            String that is to be appended to the current buffer.
	 * @param sourceBlock
	 *            The block for which this text has been generated.
	 * @param source
	 *            The Object for which was generated this text.
	 * @param fireEvent
	 *            Tells us whether we should fire generation events.
	 */
	private void delegateAppend(String string, Block sourceBlock, EObject source, boolean fireEvent) {
		// Make sure that the protected marker never makes its way down to the generated file
		String actualString = string;
		if (shouldRemoveProtectedMarker(sourceBlock)) {
			actualString = removeProtectedMarkers(string);
		}

		if (getVisitor() instanceof AcceleoEvaluationVisitorDecorator<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) {
			getAcceleoVisitor().append(actualString, sourceBlock, source, fireEvent);
		} else {
			append(actualString, sourceBlock, source, fireEvent);
		}
	}

	/**
	 * Indicates if the source block qualifies for the removal of the protected area marker.
	 * 
	 * @param sourceBlock
	 *            The source block.
	 * @return <code>true</code> if the protected area marker should be removed, <code>false</code> otherwise.
	 */
	private boolean shouldRemoveProtectedMarker(Block sourceBlock) {
		boolean result = false;

		// If file block -> ok
		if (sourceBlock instanceof FileBlock) {
			result = true;
		} else {
			// If inside a file block -> ok (not after a template/query call).
			EObject parent = sourceBlock.eContainer();
			while (parent != null && !(parent instanceof FileBlock)) {
				parent = parent.eContainer();
			}
			if (parent instanceof FileBlock) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * If I have an {@link AcceleoEvaluationVisitorDecorator Acceleo-specific decorator}, I'll delegate the
	 * query result caching to it.
	 * 
	 * @param query
	 *            The query for which we need to cache results.
	 * @param arguments
	 *            Arguments of the invocation.
	 * @param result
	 *            The result that is to be cached.
	 */
	private void delegateCacheResult(Query query, List<Object> arguments, Object result) {
		if (getVisitor() instanceof AcceleoEvaluationVisitorDecorator<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) {
			getAcceleoVisitor().cacheResult(query, arguments, result);
		} else {
			cacheResult(query, arguments, result);
		}
	}

	/**
	 * If I have an {@link AcceleoEvaluationVisitorDecorator Acceleo-specific decorator}, I'll delegate all
	 * file creation to it.
	 * 
	 * @param filePath
	 *            Path of the file around which we need a FileWriter. The file can be created under the
	 *            generationRoot if needed.
	 * @param fileBlock
	 *            The file block which asked for this writer. Only used for generation events.
	 * @param source
	 *            The source EObject for this file block. Only used for generation events.
	 * @param appendMode
	 *            If <code>false</code>, the file will be replaced by a new one.
	 * @param charset
	 *            Charset of the target file.
	 * @throws AcceleoEvaluationException
	 *             Thrown if the file cannot be created.
	 */
	private void delegateCreateFileWriter(String filePath, Block fileBlock, EObject source,
			boolean appendMode, String charset) throws AcceleoEvaluationException {
		if (getVisitor() instanceof AcceleoEvaluationVisitorDecorator<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) {
			getAcceleoVisitor().createFileWriter(getContext().getFileFor(filePath), fileBlock, source,
					appendMode, charset);
		} else {
			createFileWriter(getContext().getFileFor(filePath), fileBlock, source, appendMode, charset);
		}
	}

	/**
	 * If I have an {@link AcceleoEvaluationVisitorDecorator Acceleo-specific decorator}, I'll delegate all
	 * indentation management to it.
	 * 
	 * @param source
	 *            Text which indentation is to be altered.
	 * @return The source text after having changed all of its lines' indentation.
	 */
	private String delegateFitIndentation(String source) {
		String currentIndent = getContext().getCurrentLineIndentation();
		if (getVisitor() instanceof AcceleoEvaluationVisitorDecorator<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) {
			return getAcceleoVisitor().fitIndentationTo(source, currentIndent);
		}
		return fitIndentationTo(source, currentIndent);
	}

	/**
	 * If I have an {@link AcceleoEvaluationVisitorDecorator Acceleo-specific decorator}, I'll delegate the
	 * query result caching to it.
	 * 
	 * @param query
	 *            The query for which we need cached results.
	 * @param arguments
	 *            Arguments of the invocation.
	 * @return The cached result if any.
	 */
	private Object delegateGetCachedResult(Query query, List<Object> arguments) {
		if (getVisitor() instanceof AcceleoEvaluationVisitorDecorator<?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?>) {
			return getAcceleoVisitor().getCachedResult(query, arguments);
		}
		return getCachedResult(query, arguments);
	}

	/**
	 * This will evaluate guards of all <code>candidates</code> and filter out those whose guard is evaluated
	 * to <code>false</code>. Template with no specified guards aren't removed from the list.
	 * <p>
	 * <b>Note</b> that the parameter list will be modified by this call. Order will be preserved.
	 * </p>
	 * 
	 * @param candidates
	 *            List that is to be filtered.
	 * @param arguments
	 *            Arguments of all templates. Those need to be set for guard evaluation.
	 */
	@SuppressWarnings("unchecked")
	private void evaluateGuards(List<Template> candidates, List<Variable> arguments) {
		final boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		AcceleoEvaluationException exception = null;
		/*
		 * NOTE : we depend on the ordering offered by List types. Do not change Collection implementation to
		 * a non-ordered one.
		 */
		final Iterator<Template> candidateIterator = candidates.iterator();
		int current = 0;
		int size = candidates.size();
		while (current < size) {
			current++;
			final Template candidate = candidateIterator.next();
			if (candidate.getGuard() == null) {
				// no need to go any further
				continue;
			}
			// Set parameter values
			for (int i = 0; i < candidate.getParameter().size(); i++) {
				final Variable param = candidate.getParameter().get(i);
				VariableExp init = EcoreFactory.eINSTANCE.createVariableExp();
				init.setReferredVariable(arguments.get(i));
				param.setInitExpression(init);
				getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)param);
				// [255379] sets new value of "self" to match the very first arg of the invocation
				if (i == 0) {
					Object newContext = getEvaluationEnvironment().getValueOf(param.getName());
					getEvaluationEnvironment().add(SELF_VARIABLE_NAME, newContext);
				}
			}
			final Object guardValue = getVisitor().visitExpression((OCLExpression<C>)candidate.getGuard());

			// restore parameters as they were prior to the call
			for (int i = 0; i < candidate.getParameter().size(); i++) {
				final Variable param = candidate.getParameter().get(i);
				((VariableExp)param.getInitExpression()).setReferredVariable(null);
				param.setInitExpression(null);
				getEvaluationEnvironment().remove(param.getName());
			}
			// [255379] restore self if need be
			if (candidate.getParameter().size() > 0) {
				getEvaluationEnvironment().remove(SELF_VARIABLE_NAME);
			}

			if (isInvalid(guardValue)) {
				if (exception == null) {
					final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
					exception = getContext().createAcceleoException(candidate, (OCLExpression<C>)candidate
							.getGuard(), UNDEFINED_GUARD_MESSAGE_KEY, currentSelf);
				}
				candidateIterator.remove();
			} else if (guardValue == null || !((Boolean)guardValue).booleanValue()) {
				// FIXME could be other than Boolean
				candidateIterator.remove();
			}
		}
		if (candidates.isEmpty() && exception != null) {
			throw exception;
		}
		fireGenerationEvent = fireEvents;
	}

	/**
	 * Returns the current environment, cast as an AcceleoEnvironment as we know it is one.
	 * 
	 * @return The current environment as an AcceleoEnvironment.
	 */
	private AcceleoEnvironment getAcceleoEnvironment() {
		return (AcceleoEnvironment)getEnvironment();
	}

	/**
	 * This will search through the list of adapters of the given <em>node</em> for the adapter in charge of
	 * keeping track of this node's line number, then return it.
	 * 
	 * @param node
	 *            The node of which we seek the line.
	 * @return Line where the given <em>node</em> is located in the modulefile.
	 */
	private int getLineOf(ASTNode node) {
		Adapter adapter = EcoreUtil.getAdapter(node.eAdapters(), AcceleoASTNodeAdapter.class);
		int line = 0;
		if (adapter != null) {
			line = ((AcceleoASTNodeAdapter)adapter).getLine();
		}
		return line;
	}

	/**
	 * This will be used in order to check whether the given protected area is directly contained within a
	 * file block : such areas will not need to see their indentation restored.
	 * 
	 * @param protectedArea
	 *            The area which containing hierarchy is to be checked.
	 * @return <code>true</code> if we encounter a {@link FileBlock} before a {@link Template} in this area's
	 *         containing hierarchy.
	 */
	private boolean isInFileBlock(ProtectedAreaBlock protectedArea) {
		EObject container = protectedArea.eContainer();
		boolean isInFileBlock = false;
		while (container != null && !isInFileBlock && !(container instanceof Template)) {
			isInFileBlock = container instanceof FileBlock;
			container = container.eContainer();
		}
		return isInFileBlock;
	}

	/**
	 * Returns <code>true</code> if the value is equal to the OCL standard library's OCLInvalid object.
	 * 
	 * @param value
	 *            Value we need to test.
	 * @return <code>true</code> if the value is equal to the OCL standard library's OCLInvalid object,
	 *         <code>false</code> otherwise.
	 */
	private boolean isInvalid(Object value) {
		return value == invalid;
	}

	/**
	 * Returns <code>true</code> if the value is either <code>null</code> or equal to the OCL standard
	 * library's OCLInvalid object.
	 * 
	 * @param value
	 *            Value we need to test.
	 * @return <code>true</code> if the value is either <code>null</code> or equal to the OCL standard
	 *         library's OCLInvalid object, <code>false</code> otherwise.
	 */
	private boolean isUndefined(Object value) {
		return value == null || value == invalid;
	}

	/**
	 * This will be in charge of retrieving the actual template that is to be called for the evaluation of the
	 * given <code>invocation</code> and evaluate its arguments to set their values in the environment.
	 * 
	 * @param invocation
	 *            The template invocation which evaluation is to be prepared.
	 * @return The actual template referenced by this invocation.
	 */
	@SuppressWarnings("unchecked")
	private Template prepareInvocation(TemplateInvocation invocation) {
		final Template template = invocation.getDefinition();
		final List<Variable> temporaryArgVars = new ArrayList<Variable>(invocation.getArgument().size());
		final Template actualTemplate;

		if (invocation.isSuper()) {
			EObject container = invocation.eContainer();
			while (!(container instanceof Template)) {
				container = container.eContainer();
			}
			Template containingTemplate = (Template)container;
			// Was the containing template called through another template invocation?
			// If Yes, then this latter is the actual *super* we seek
			// If No, then our super is the first template overriden
			if (containingTemplate.eContainer() instanceof TemplateInvocation) {
				actualTemplate = ((TemplateInvocation)containingTemplate.eContainer()).getDefinition();
			} else {
				actualTemplate = template.getOverrides().get(0);
			}
			boolean fireEvents = fireGenerationEvent;
			fireGenerationEvent = false;

			for (int i = 0; i < actualTemplate.getParameter().size(); i++) {
				/*
				 * We'll create a temporary var beforehand for each parameter to avoid scoping issues with the
				 * variables context (temporary vars are global). This shouldn't be too much overhead in terms
				 * of raw performance.
				 */
				final Variable tempVar = EcoreFactory.eINSTANCE.createVariable();
				tempVar.setName(TEMPORARY_INVOCATION_ARG_PREFIX + i);
				final VariableExp init = EcoreFactory.eINSTANCE.createVariableExp();
				init.setReferredVariable(containingTemplate.getParameter().get(i));
				tempVar.setInitExpression(init);
				temporaryArgVars.add(tempVar);
				// Evaluate the value of this new variable
				getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)tempVar);
			}

			// The actual template that will be called is known ; create its variable scope
			((AcceleoEvaluationEnvironment)getEvaluationEnvironment()).createVariableScope();

			// Determine argument values and context
			for (int i = 0; i < actualTemplate.getParameter().size(); i++) {
				Variable var = actualTemplate.getParameter().get(i);
				final VariableExp init = EcoreFactory.eINSTANCE.createVariableExp();
				final Variable temporaryVar = temporaryArgVars.get(i);
				init.setReferredVariable(temporaryVar);
				var.setInitExpression(init);
				// Evaluate the value of this new variable
				getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)var);
				// Unset every temporary reference we've set to prevent cross referencers from keeping these
				var.setInitExpression(null);
				init.setReferredVariable(null);
				temporaryVar.setType(null);
				temporaryVar.setInitExpression(null);

				// We're done with this temporary variable
				getEvaluationEnvironment().remove(temporaryVar.getName());
			}
			fireGenerationEvent = fireEvents;
		} else {
			final int argumentCount = invocation.getArgument().size();
			final Object[] argValues = new Object[argumentCount];
			// Determine values of the arguments
			boolean fireEvents = fireGenerationEvent;
			fireGenerationEvent = false;
			for (int i = 0; i < argumentCount; i++) {
				final OCLExpression<C> argument = (OCLExpression<C>)invocation.getArgument().get(i);
				final Variable tempVar = EcoreFactory.eINSTANCE.createVariable();
				tempVar.setName(TEMPORARY_INVOCATION_ARG_PREFIX + i);
				tempVar.setInitExpression(new ParameterInitExpression(argument));
				tempVar.setType((EClassifier)argument.getType());
				temporaryArgVars.add(tempVar);
				// Evaluate the value of this new variable
				getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)tempVar);
				final Object argValue = getEvaluationEnvironment().getValueOf(tempVar.getName());
				if (isInvalid(argValue)) {
					final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
					final AcceleoEvaluationException exception = getContext().createAcceleoException(
							invocation, argument, "AcceleoEvaluationVisitor.UndefinedArgument", currentSelf); //$NON-NLS-1$
					// Evaluation of this template failed. Remove all previously created variables
					for (int j = 0; j <= i; j++) {
						getEvaluationEnvironment().remove(invocation.getArgument().get(j).getName());
					}
					throw exception;
				}
				argValues[i] = getEvaluationEnvironment().getValueOf(tempVar.getName());
			}
			fireGenerationEvent = fireEvents;
			// retrieve all applicable candidates of the call
			final List<Template> applicableCandidates = ((AcceleoEvaluationEnvironment)getEvaluationEnvironment())
					.getAllCandidates((Module)EcoreUtil.getRootContainer(invocation), template, argValues);
			evaluateGuards(applicableCandidates, temporaryArgVars);
			// We now know the actual template that's to be called ; create its variable scope
			((AcceleoEvaluationEnvironment)getEvaluationEnvironment()).createVariableScope();

			if (applicableCandidates.iterator().hasNext()) {
				actualTemplate = ((AcceleoEvaluationEnvironment)getEvaluationEnvironment()).getMostSpecific(
						applicableCandidates, argValues);

				// Determine argument values and context
				for (int i = 0; i < actualTemplate.getParameter().size(); i++) {
					Variable var = actualTemplate.getParameter().get(i);
					final VariableExp init = EcoreFactory.eINSTANCE.createVariableExp();
					final Variable temporaryVar = temporaryArgVars.get(i);
					init.setReferredVariable(temporaryVar);
					var.setInitExpression(init);
					// Evaluate the value of this new variable
					getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)var);
					// Unset every temporary reference we've set to prevent cross referencers from keeping
					// these
					var.setInitExpression(null);
					init.setReferredVariable(null);
					temporaryVar.setType(null);
					temporaryVar.setInitExpression(null);

					// We're done with this temporary variable
					getEvaluationEnvironment().remove(temporaryVar.getName());
				}
			} else {
				// No template remains after guard evaluation. Create an empty template so no
				// text will be generated from this call.
				actualTemplate = MtlFactory.eINSTANCE.createTemplate();
			}
		}

		return actualTemplate;
	}

	/**
	 * This will be in charge of retrieving the actual template that is to be called for the evaluation of the
	 * given <code>invocation</code> and evaluate its arguments to set their values in the environment.
	 * 
	 * @param invocation
	 *            The template invocation which evaluation is to be prepared.
	 * @param arguments
	 * @return The actual template referenced by this invocation.
	 */
	@SuppressWarnings("unchecked")
	private Query prepareInvocation(QueryInvocation invocation, List<Object> arguments) {
		final Query query = invocation.getDefinition();
		Object[] argValues = arguments.toArray();
		// retrieve all applicable candidates of the call
		final List<Query> applicableCandidates = ((AcceleoEvaluationEnvironment)getEvaluationEnvironment())
				.getAllCandidates((Module)EcoreUtil.getRootContainer(invocation), query, argValues);

		Query actualQuery;
		if (applicableCandidates.iterator().hasNext()) {
			actualQuery = ((AcceleoEvaluationEnvironment)getEvaluationEnvironment()).getMostSpecific(
					applicableCandidates, argValues);
		} else

		{
			// No template remains after guard evaluation. Create an empty template so no
			// text will be generated from this call.
			actualQuery = MtlFactory.eINSTANCE.createQuery();
		}

		return actualQuery;
	}

	/**
	 * Evaluate all parameters of the query before changing the actual values of the associated variables. See
	 * bug 344424 for an example of what would fail (strangely) if we didn't do that.
	 * 
	 * @param invocation
	 *            The invocation to prepare
	 * @return The list of argument values.
	 */
	@SuppressWarnings("unchecked")
	private List<Object> prepareInvocation(QueryInvocation invocation) {
		final List<Object> arguments = new ArrayList<Object>();
		final List<Variable> temporaryArgVars = new ArrayList<Variable>(invocation.getArgument().size());
		final Query query = invocation.getDefinition();

		boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		for (int i = 0; i < query.getParameter().size(); i++) {
			/*
			 * We'll create a temporary var beforehand for each parameter to avoid scoping issues with the
			 * variables context (temporary vars are global). This shouldn't be too much overhead in terms of
			 * raw performance.
			 */
			final Variable tempVar = EcoreFactory.eINSTANCE.createVariable();
			tempVar.setName(TEMPORARY_INVOCATION_ARG_PREFIX + i);
			tempVar.setInitExpression(new ParameterInitExpression((OCLExpression<C>)invocation.getArgument()
					.get(i)));
			temporaryArgVars.add(tempVar);
			// Evaluate the value of this new variable
			getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)tempVar);

			final Object argValue = getEvaluationEnvironment().getValueOf(tempVar.getName());
			if (isInvalid(argValue)) {
				final OCLExpression<C> failingExpression = (OCLExpression<C>)invocation.getArgument().get(i);
				final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
				final AcceleoEvaluationException exception = getContext().createAcceleoException(invocation,
						failingExpression, "AcceleoEvaluationVisitor.UndefinedArgument", currentSelf); //$NON-NLS-1$
				// Evaluation of this query failed. Remove all previously created variables
				for (int j = 0; j <= i; j++) {
					getEvaluationEnvironment().remove(temporaryArgVars.get(j).getName());
				}
				throw exception;
			}
		}

		// Now that they've all been computed, we can set the final variable values
		for (int i = 0; i < query.getParameter().size(); i++) {
			Variable var = query.getParameter().get(i);
			final VariableExp init = EcoreFactory.eINSTANCE.createVariableExp();
			final Variable temporaryVar = temporaryArgVars.get(i);
			init.setReferredVariable(temporaryVar);
			var.setInitExpression(init);
			// Evaluate the value of this new variable
			getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)var);
			// Unset every temporary reference we've set to prevent cross referencers from keeping these
			var.setInitExpression(null);
			init.setReferredVariable(null);
			temporaryVar.setType(null);
			temporaryVar.setInitExpression(null);

			final Object argValue = getEvaluationEnvironment().getValueOf(var.getName());
			arguments.add(argValue);

			// We're done with this temporary variable
			getEvaluationEnvironment().remove(temporaryVar.getName());
		}
		fireGenerationEvent = fireEvents;

		return arguments;
	}

	/**
	 * Tell if we should profile this expression.
	 * 
	 * @param expression
	 *            the expression to check
	 * @return true if we should profile
	 */
	private boolean profileExpression(OCLExpression<C> expression) {
		return !(expression instanceof StringLiteralExp);
	}

	/**
	 * Removes all variables values initialized by an init section from the environment.
	 * 
	 * @param init
	 *            Init section from which variables were initialized.
	 */
	private void restoreInitVariables(InitSection init) {
		for (Variable var : init.getVariable()) {
			getEvaluationEnvironment().remove(var.getName());
		}
	}

	/**
	 * This will check if the given reference should cause text generation. More specifically, it will return
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
	private boolean shouldGenerateText(EReference reference) {
		// Note : sort this by order of frequency to allow shot-circuit evaluation
		boolean generate = reference == MtlPackage.eINSTANCE.getBlock_Body();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_Each();
		generate = generate || (fireProtectedAreaGenerationEvent && reference == MtlPackage.eINSTANCE
				.getProtectedAreaBlock_Marker());
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_Each();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_Before();
		generate = generate || reference == MtlPackage.eINSTANCE.getForBlock_After();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_Before();
		generate = generate || reference == MtlPackage.eINSTANCE.getTemplateInvocation_After();
		return generate;
	}

	/**
	 * Switch to the visiting method according to the expression type.
	 * 
	 * @param expression
	 *            the expression to visite
	 * @return the result of the visit evaluation
	 */
	private Object switchExpression(OCLExpression<C> expression) {
		Object result;
		if (expression == null) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationVisitor.UnresolvedCompilationErrorNull")); //$NON-NLS-1$
		}
		AcceleoEvaluationVisitorDecorator<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> delegate = getAcceleoVisitor();
		if (expression instanceof Template) {
			if (getContext().getProgressMonitor().isCanceled()) {
				cancel(new ASTFragment(expression));
			}
			if (delegate != null) {
				result = delegate.visitAcceleoTemplate((Template)expression);
			} else {
				result = visitAcceleoTemplate((Template)expression);
			}
		} else if (expression instanceof IfBlock) {
			if (delegate != null) {
				delegate.visitAcceleoIfBlock((IfBlock)expression);
			} else {
				visitAcceleoIfBlock((IfBlock)expression);
			}
			result = getContext().getDefaultText();
			if (result == null) {
				result = ""; //$NON-NLS-1$
			}
		} else if (expression instanceof ForBlock) {
			if (delegate != null) {
				delegate.visitAcceleoForBlock((ForBlock)expression);
			} else {
				visitAcceleoForBlock((ForBlock)expression);
			}
			result = getContext().getDefaultText();
			if (result == null) {
				result = ""; //$NON-NLS-1$
			}
		} else if (expression instanceof FileBlock) {
			if (getContext().getProgressMonitor().isCanceled()) {
				cancel(new ASTFragment(expression));
			}
			if (delegate != null) {
				delegate.visitAcceleoFileBlock((FileBlock)expression);
			} else {
				visitAcceleoFileBlock((FileBlock)expression);
			}
			result = getContext().getDefaultText();
			if (result == null) {
				result = ""; //$NON-NLS-1$
			}
		} else if (expression instanceof TemplateInvocation) {
			if (delegate != null) {
				result = delegate.visitAcceleoTemplateInvocation((TemplateInvocation)expression);
			} else {
				result = visitAcceleoTemplateInvocation((TemplateInvocation)expression);
			}
		} else if (expression instanceof QueryInvocation) {
			if (delegate != null) {
				result = delegate.visitAcceleoQueryInvocation((QueryInvocation)expression);
			} else {
				result = visitAcceleoQueryInvocation((QueryInvocation)expression);
			}
		} else if (expression instanceof LetBlock) {
			if (delegate != null) {
				delegate.visitAcceleoLetBlock((LetBlock)expression);
			} else {
				visitAcceleoLetBlock((LetBlock)expression);
			}
			result = getContext().getDefaultText();
			if (result == null) {
				result = ""; //$NON-NLS-1$
			}
		} else if (expression instanceof ProtectedAreaBlock) {
			if (delegate != null) {
				delegate.visitAcceleoProtectedArea((ProtectedAreaBlock)expression);
			} else {
				visitAcceleoProtectedArea((ProtectedAreaBlock)expression);
			}
			result = getContext().getDefaultText();
			if (result == null) {
				result = ""; //$NON-NLS-1$
			}
		} else if (!(expression instanceof TemplateExpression)) {
			result = super.visitExpression(expression);
		} else {
			// The expression itself fails, so it's toString will likely end in NPE
			// (OCLExpression.toString() will do so). Try and go up to the container.
			String failedOnExpression = null;
			try {
				failedOnExpression = expression.eContainer().toString();
				// CHECKSTYLE:OFF We're trying to be resilient to any errors on this toString since we're
				// going to throw an exception anyway
			} catch (Exception e) {
				// CHECKSTYLE:ON
				// Try and find a Template containing our expression
				EObject container = expression.eContainer();
				while (!(container instanceof Template) && container != null) {
					container = container.eContainer();
				}
				if (container != null) {
					failedOnExpression = container.toString();
				}
			}
			if (failedOnExpression == null) {
				throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
						"AcceleoEvaluationVisitor.UnresolvedCompilationErrorNoExpression", expression //$NON-NLS-1$
								.eResource().getURI().lastSegment()));
			}
			throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
					"AcceleoEvaluationVisitor.UnresolvedCompilationError", expression.eContainer(), //$NON-NLS-1$
					expression.eResource().getURI().lastSegment()));
		}
		return result;
	}

	/**
	 * Collections need special handling when generated from Acceleo.
	 * 
	 * @param object
	 *            The object we wish the String representation of.
	 * @return String representation of the given Object. For Collections, this will be the concatenation of
	 *         all contained Objects' toString.
	 */
	private String toString(Object object) {
		final StringBuffer buffer = new StringBuffer();
		if (object instanceof Collection<?>) {
			final Iterator<?> childrenIterator = ((Collection<?>)object).iterator();
			while (childrenIterator.hasNext()) {
				buffer.append(toString(childrenIterator.next()));
			}
		} else if (object != null) {
			buffer.append(object.toString());
		}
		return buffer.toString();
	}

	/**
	 * These will be used as dummies for our temporary variables so as not to change the container of
	 * TemplateInvocation and QueryInvocation parameter expressions.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class ParameterInitExpression extends OCLExpressionImpl {
		/** Expression this will delegate evaluation to. */
		private final OCLExpression<C> referredExpression;

		/**
		 * Instantiates an instance given the expression to delegate evaluation to.
		 * 
		 * @param expession
		 *            Expression to which evaluation will be delegated.
		 */
		ParameterInitExpression(OCLExpression<C> expession) {
			referredExpression = expession;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ocl.ecore.impl.OCLExpressionImpl#accept(U)
		 */
		@Override
		public <T extends Object, U extends org.eclipse.ocl.utilities.Visitor<T, ?, ?, ?, ?, ?, ?, ?, ?, ?>> T accept(
				U v) {
			return referredExpression.accept(v);
		}
	}
}
