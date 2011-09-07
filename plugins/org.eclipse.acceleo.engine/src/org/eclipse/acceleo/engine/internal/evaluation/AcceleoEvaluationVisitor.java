/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
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
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.AcceleoEvaluationCancelledException;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.AcceleoRuntimeException;
import org.eclipse.acceleo.engine.internal.debug.ASTFragment;
import org.eclipse.acceleo.engine.internal.debug.IDebugAST;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironment;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEvaluationEnvironment;
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
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
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
	/** The marker of the lies generated inside of the protected area. */
	private static final String PROTECTED_AREA_MARKER = "ACCELEO_PROTECTED_AREA_MARKER_FIT_INDENTATION"; //$NON-NLS-1$

	/** Keeps track of the System's line separator. */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

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

	/** This flag will be set to <code>true</code> whenever we start evaluation of init section's variables. */
	private boolean evaluatingInitSection;

	/**
	 * This will be changed to <code>true</code> when generation event should fired and reset to
	 * <code>false</code> whenever they are to be blocked.
	 */
	private boolean fireGenerationEvent;

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
		// assumes I have no decorator if not set explicitely
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
		context.append(string, sourceBlock, source, fireEvent);
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
		context.openNested(generatedFile, fileBlock, source, appendMode, charset);
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
		// Do not alter the very first line (^)
		// FIXME SBE Change the indentation mechanism
		String regex = "\r\n|\r|\n"; //$NON-NLS-1$
		String replacement = "$0" + indentation; //$NON-NLS-1$

		Matcher sourceMatcher = Pattern.compile(regex).matcher(source);
		StringBuffer result = new StringBuffer();
		boolean hasMatch = sourceMatcher.find();
		while (hasMatch) {
			sourceMatcher.appendReplacement(result, replacement);
			hasMatch = sourceMatcher.find();
		}
		sourceMatcher.appendTail(result);
		return result.toString();
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
			final AcceleoEvaluationException exception = context.createAcceleoException(fileBlock,
					"AcceleoEvaluationVisitor.UndefinedFileURL", getEvaluationEnvironment() //$NON-NLS-1$
							.getValueOf(SELF_VARIABLE_NAME));
			throw exception;
		} else if (fileURLResult instanceof Collection<?>) {
			final AcceleoEvaluationException exception = context.createAcceleoException(fileBlock,
					"AcceleoEvaluationVisitor.CollectionFileURL", getEvaluationEnvironment() //$NON-NLS-1$
							.getValueOf(SELF_VARIABLE_NAME));
			throw exception;
		} else if (!(fileURLResult instanceof String)) {
			final AcceleoEvaluationException exception = context.createAcceleoException(fileBlock,
					"AcceleoEvaluationVisitor.NotStringFileURL", getEvaluationEnvironment() //$NON-NLS-1$
							.getValueOf(SELF_VARIABLE_NAME));
			throw exception;
		} else if ("".equals(fileURLResult)) { //$NON-NLS-1$
			final AcceleoEvaluationException exception = context.createAcceleoException(fileBlock,
					"AcceleoEvaluationVisitor.EmptyFileName", getEvaluationEnvironment() //$NON-NLS-1$
							.getValueOf(SELF_VARIABLE_NAME));
			throw exception;
		}
		final String filePath = String.valueOf(fileURLResult).trim();

		String fileCharset = null;
		if (fileBlock.getCharset() != null) {
			final Object fileCharsetResult = visitExpression((OCLExpression<C>)fileBlock.getCharset());
			if (isUndefined(fileCharsetResult)) {
				final AcceleoEvaluationException exception = context.createAcceleoException(fileBlock,
						"AcceleoEvaluationVisitor.UndefinedFileCharset", getEvaluationEnvironment() //$NON-NLS-1$
								.getValueOf(SELF_VARIABLE_NAME));
				AcceleoEnginePlugin.log(exception, false);
			}
			fileCharset = String.valueOf(fileCharsetResult);
		}

		final boolean appendMode = fileBlock.getOpenMode().getValue() == OpenModeKind.APPEND_VALUE;

		final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		final EObject source;
		if (currentSelf instanceof EObject) {
			source = (EObject)currentSelf;
		} else {
			source = lastEObjectSelfValue;
		}

		context.getProgressMonitor().subTask(
				AcceleoEngineMessages.getString("AcceleoEvaluationVisitor.Generatingfile", filePath)); //$NON-NLS-1$
		if ("stdout".equals(filePath)) { //$NON-NLS-1$
			context.openNested(System.out);
		} else {
			delegateCreateFileWriter(filePath, fileBlock, source, appendMode, fileCharset);
		}
		// TODO handle file ID
		for (final org.eclipse.ocl.ecore.OCLExpression nested : fileBlock.getBody()) {
			fireGenerationEvent = true;
			getVisitor().visitExpression((OCLExpression<C>)nested);
			fireGenerationEvent = fireEvents;
		}
		context.closeContext(fileBlock, source);
		context.getProgressMonitor().worked(1);
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
			throw context.createAcceleoException(forBlock,
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
				if (loopVariable != null && loopVariable.getType() != null
						&& !loopVariable.getType().isInstance(o)) {
					if (!iterationCCE) {
						int line = getLineOf(forBlock);
						String actualType = "null"; //$NON-NLS-1$
						if (o != null) {
							actualType = o.getClass().getName();
						}
						final String expectedType = loopVariable.getType().getName();
						final String message = AcceleoEngineMessages.getString(
								"AcceleoEvaluationVisitor.IterationClassCast", Integer.valueOf(line), //$NON-NLS-1$
								((Module)EcoreUtil.getRootContainer(forBlock)).getName(),
								forBlock.toString(), actualType, expectedType);
						final AcceleoEvaluationException exception = new AcceleoEvaluationException(message);
						exception.setStackTrace(context.createAcceleoStackTrace());
						AcceleoEnginePlugin.log(exception, false);
						iterationCCE = true;
					}
					continue;
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
					final AcceleoEvaluationException exception = context.createAcceleoException(forBlock,
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
			final AcceleoEvaluationException exception = context.createAcceleoException(ifBlock,
					"AcceleoEvaluationVisitor.UndefinedCondition", currentSelf); //$NON-NLS-1$
			throw exception;
		}

		if (conditionValue != null && !(conditionValue instanceof Boolean)) {
			throw context.createAcceleoException(ifBlock, condition,
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
					final Object elseValue = getVisitor().visitExpression(
							(OCLExpression<C>)elseif.getIfExpr());
					fireGenerationEvent = fireEvents;
					if (isInvalid(elseValue)) {
						final AcceleoEvaluationException exception = context.createAcceleoException(elseif,
								"AcceleoEvaluationVisitor.UndefinedElseCondition", currentSelf); //$NON-NLS-1$
						throw exception;
					}

					if (elseValue != null && !(elseValue instanceof Boolean)) {
						throw context.createAcceleoException(ifBlock, (OCLExpression<C>)elseif.getIfExpr(),
								"AcceleoEvaluationVisitor.NotBooleanCondition", currentSelf); //$NON-NLS-1$
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
			final AcceleoEvaluationException exception = context.createAcceleoException(letBlock,
					"AcceleoEvaluationVisitor.UndefinedLetValue", currentSelf); //$NON-NLS-1$
			throw exception;
		}

		// This will hold the name of the variable which value we replaced
		String varName = null;
		try {
			if (var.getType().isInstance(value)
					|| var.getType() == getEnvironment().getOCLStandardLibrary().getOclAny()) {
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
							final AcceleoEvaluationException exception = context.createAcceleoException(
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
		// FIXME SBE add marker
		boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		final Object markerValue = getVisitor().visitExpression((OCLExpression<C>)protectedArea.getMarker());
		fireGenerationEvent = fireEvents;
		final Object source = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		if (isUndefined(markerValue)) {
			final AcceleoEvaluationException exception = context.createAcceleoException(protectedArea,
					"AcceleoEvaluationVisitor.UndefinedAreaMarker", source); //$NON-NLS-1$
			throw exception;
		}

		final String marker = toString(markerValue).trim();
		final String areaContent = context.getProtectedAreaContent(marker);
		if (source instanceof EObject) {
			lastEObjectSelfValue = (EObject)source;
		}
		if (areaContent != null) {
			if (isInFileBlock(protectedArea)) {
				delegateAppend(areaContent, protectedArea, lastEObjectSelfValue, fireGenerationEvent);
			} else {
				// the content of the protected area has its own indentation. Do not touch it.
				delegateAppend(areaContent.replaceAll("(\r\n|\n|\r)", PROTECTED_AREA_MARKER), protectedArea, //$NON-NLS-1$
						lastEObjectSelfValue, fireGenerationEvent);
			}
		} else {
			context.openNested();
			fireGenerationEvent = false;
			visitAcceleoBlock(protectedArea);
			fireGenerationEvent = fireEvents;
			String blockContent = context.closeContext();

			// We'll make it so that we only have a single appending in the context
			StringBuilder buffer = new StringBuilder();
			buffer.append(AcceleoEngineMessages.getString("usercode.start")); //$NON-NLS-1$
			buffer.append(' ');
			buffer.append(marker);
			buffer.append(blockContent);
			buffer.append(AcceleoEngineMessages.getString("usercode.end")); //$NON-NLS-1$

			delegateAppend(buffer.toString(), protectedArea, lastEObjectSelfValue, fireGenerationEvent);
		}
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
	 * Handles the evaluation of an Acceleo {@link QueryInvocation}.
	 * 
	 * @param invocation
	 *            The Acceleo query invocation that is to be evaluated.
	 * @return result of the invocation.
	 */
	@SuppressWarnings("unchecked")
	public Object visitAcceleoQueryInvocation(QueryInvocation invocation) {
		final Query query = invocation.getDefinition();
		String implicitContextVariableName = null;

		final List<Object> arguments = new ArrayList<Object>();
		boolean fireEvents = fireGenerationEvent;
		fireGenerationEvent = false;
		for (int i = 0; i < query.getParameter().size(); i++) {
			final Variable var = query.getParameter().get(i);
			var.setInitExpression(new ParameterInitExpression((OCLExpression<C>)invocation.getArgument().get(
					i)));
			getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)var);
			final Object argValue = getEvaluationEnvironment().getValueOf(var.getName());
			if (isInvalid(argValue)) {
				final OCLExpression<C> failingExpression = (OCLExpression<C>)invocation.getArgument().get(i);
				final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
				final AcceleoEvaluationException exception = context.createAcceleoException(invocation,
						failingExpression, "AcceleoEvaluationVisitor.UndefinedArgument", currentSelf); //$NON-NLS-1$
				// Evaluation of this query failed. Remove all previously created variables
				for (int j = 0; j <= i; j++) {
					getEvaluationEnvironment().remove(query.getParameter().get(j).getName());
				}
				throw exception;
			}
			arguments.add(argValue);
			var.setInitExpression(null);
		}
		fireGenerationEvent = fireEvents;

		// If the query has already been run with these arguments, return the cached result
		Object cachedResult = delegateGetCachedResult(query, arguments);
		if (QueryCache.isCachedResult(cachedResult)) {
			// We no longer need the variables at their current value.
			for (Variable var : query.getParameter()) {
				getEvaluationEnvironment().remove(var.getName());
			}
			if (QueryCache.isInvalid(cachedResult) && AcceleoPreferences.isDebugMessagesEnabled()) {
				final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
				final AcceleoEvaluationException exception = context.createAcceleoException(query,
						(OCLExpression<C>)query.getExpression(), "AcceleoEvaluationVisitor.InvalidQuery", //$NON-NLS-1$
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
			result = visitExpression((OCLExpression<C>)query.getExpression());
		} finally {
			// restores parameters as they were prior to the call
			for (Variable var : query.getParameter()) {
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
		delegateCacheResult(query, arguments, result);
		if (isInvalid(result) && AcceleoPreferences.isDebugMessagesEnabled()) {
			final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
			final AcceleoEvaluationException exception = context.createAcceleoException(query,
					(OCLExpression<C>)query.getExpression(), "AcceleoEvaluationVisitor.InvalidQuery", //$NON-NLS-1$
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
		context.openNested();
		/*
		 * Variables have been positionned by either the AcceleoEngine (first template) or this visitor
		 * (template invocation).
		 */
		for (final org.eclipse.ocl.ecore.OCLExpression nested : template.getBody()) {
			getVisitor().visitExpression((OCLExpression<C>)nested);
		}
		String result = context.closeContext();
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
			final Object contextValue = getEvaluationEnvironment().getValueOf(
					actualTemplate.getParameter().get(0).getName());
			// [255379] sets new value of "self" to match the very first arg of the invocation
			getEvaluationEnvironment().add(SELF_VARIABLE_NAME, contextValue);
			implicitContextVariableName = addContextVariableFor(contextValue);
		}

		context.openNested();
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
				getEvaluationEnvironment().remove(TEMPORARY_INVOCATION_ARG_PREFIX + i);
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
		String invocationResult = context.closeContext();
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

		context.addToStack(expression);

		if (context.getProgressMonitor().isCanceled()) {
			cancel(astFragment, debugInput, result);
		}

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
				EObject generatedBlock = expression;
				while (!(generatedBlock instanceof Block)) {
					generatedBlock = generatedBlock.eContainer();
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
					final boolean fireEvent = fireGenerationEvent
							&& !(expression instanceof TemplateInvocation)
							&& !(expression instanceof Template);
					delegateAppend(toString(result), (Block)generatedBlock, lastEObjectSelfValue, fireEvent);
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
			AcceleoRuntimeException acceleoException = context.createAcceleoRuntimeException(e);
			try {
				context.dispose();
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
			context.removeFromStack();
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
		// We need something a little more intelligent to avoid overriding all "+" operators
		if (callExp.getOperationCode() == PredefinedType.PLUS) {
			final C stringType = getEnvironment().getOCLStandardLibrary().getString();
			if (callExp.getSource().getType() == stringType
					|| callExp.getArgument().get(0).getType() == stringType) {
				((EObject)callExp.getReferredOperation()).eAdapters().add(new AcceleoOverrideAdapter());
			}
		}
		/*
		 * OCL used "/" as operation name for the division ... Which means divisions will never be
		 * serializable : its URI is invalid. We'll then try and "trick" OCL for these.
		 */
		if (((EObject)callExp.getReferredOperation()).eIsProxy()) {
			URI uri = ((InternalEObject)callExp.getReferredOperation()).eProxyURI();
			if (uri.fragment() != null && uri.fragment().endsWith("%2F")) { //$NON-NLS-1$
				callExp.setOperationCode(PredefinedType.DIVIDE);
			}
		}
		lastSourceExpression = callExp.getSource();
		return getDelegate().visitOperationCallExp(callExp);
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
	 * @param debugInput
	 *            Current debug input
	 * @param result
	 *            Result of the evaluation from which we detected the operation canceling.
	 */
	private void cancel(ASTFragment astFragment, EObject debugInput, Object result) {
		// #276667 "debug" can be null
		if (debug != null) {
			debug.stepDebugOutput(astFragment, debugInput, result);
			debug.endDebug(astFragment);
			debug = null;
		}
		context.dispose();
		throw new AcceleoEvaluationCancelledException(AcceleoEngineMessages
				.getString("AcceleoEvaluationVisitor.CancelException")); //$NON-NLS-1$
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
			actualString = string.replaceAll(PROTECTED_AREA_MARKER, LINE_SEPARATOR);
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
			while (parent != null
					&& !(parent instanceof FileBlock && sourceBlock.eContainingFeature() == MtlPackage.eINSTANCE
							.getBlock_Body())) {
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
			getAcceleoVisitor().createFileWriter(context.getFileFor(filePath), fileBlock, source, appendMode,
					charset);
		} else {
			createFileWriter(context.getFileFor(filePath), fileBlock, source, appendMode, charset);
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
		String currentIndent = context.getCurrentLineIndentation();
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
		for (final Template candidate : new ArrayList<Template>(candidates)) {
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
					exception = context.createAcceleoException(candidate, (OCLExpression<C>)candidate
							.getGuard(), UNDEFINED_GUARD_MESSAGE_KEY, currentSelf);
				}
				candidates.remove(candidate);
				continue;
			}

			// FIXME could be other than Boolean
			if (guardValue == null || !((Boolean)guardValue).booleanValue()) {
				candidates.remove(candidate);
			}
		}
		if (candidates.size() == 0 && exception != null) {
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
			final Template containingTemplate = (Template)invocation.eContainer();
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
			}
			fireGenerationEvent = fireEvents;
		} else {
			final List<Object> argValues = new ArrayList<Object>();
			// Determine values of the arguments
			boolean fireEvents = fireGenerationEvent;
			fireGenerationEvent = false;
			for (int i = 0; i < invocation.getArgument().size(); i++) {
				final Variable tempVar = EcoreFactory.eINSTANCE.createVariable();
				tempVar.setName(TEMPORARY_INVOCATION_ARG_PREFIX + i);
				tempVar.setInitExpression(new ParameterInitExpression((OCLExpression<C>)invocation
						.getArgument().get(i)));
				tempVar.setType((EClassifier)((OCLExpression<C>)invocation.getArgument().get(i)).getType());
				temporaryArgVars.add(tempVar);
				// Evaluate the value of this new variable
				getVisitor().visitVariable((org.eclipse.ocl.expressions.Variable<C, PM>)tempVar);
				final Object argValue = getEvaluationEnvironment().getValueOf(tempVar.getName());
				if (isInvalid(argValue)) {
					final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
					final AcceleoEvaluationException exception = context.createAcceleoException(invocation,
							(OCLExpression<C>)invocation.getArgument().get(i),
							"AcceleoEvaluationVisitor.UndefinedArgument", currentSelf); //$NON-NLS-1$
					// Evaluation of this template failed. Remove all previously created variables
					for (int j = 0; j <= i; j++) {
						getEvaluationEnvironment().remove(invocation.getArgument().get(j).getName());
					}
					throw exception;
				}
				argValues.add(getEvaluationEnvironment().getValueOf(tempVar.getName()));
			}
			fireGenerationEvent = fireEvents;
			// retrieve all applicable candidates of the call
			final List<Template> applicableCandidates = ((AcceleoEvaluationEnvironment)getEvaluationEnvironment())
					.getAllCandidates((Module)EcoreUtil.getRootContainer(invocation), template, argValues);
			evaluateGuards(applicableCandidates, temporaryArgVars);
			// We now know the actual template that's to be called ; create its variable scope
			((AcceleoEvaluationEnvironment)getEvaluationEnvironment()).createVariableScope();

			if (applicableCandidates.size() > 0) {
				actualTemplate = ((AcceleoEvaluationEnvironment)getEvaluationEnvironment())
						.getMostSpecificTemplate(applicableCandidates, argValues);

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
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationVisitor.UnresolvedCompilationError")); //$NON-NLS-1$
		}
		AcceleoEvaluationVisitorDecorator<PK, C, O, P, EL, PM, S, COA, SSA, CT, CLS, E> delegate = getAcceleoVisitor();
		if (expression instanceof Template) {
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
			result = context.getDefaultText();
			if (result == null) {
				result = ""; //$NON-NLS-1$
			}
		} else if (expression instanceof ForBlock) {
			if (delegate != null) {
				delegate.visitAcceleoForBlock((ForBlock)expression);
			} else {
				visitAcceleoForBlock((ForBlock)expression);
			}
			result = context.getDefaultText();
			if (result == null) {
				result = ""; //$NON-NLS-1$
			}
		} else if (expression instanceof FileBlock) {
			if (delegate != null) {
				delegate.visitAcceleoFileBlock((FileBlock)expression);
			} else {
				visitAcceleoFileBlock((FileBlock)expression);
			}
			result = context.getDefaultText();
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
			result = context.getDefaultText();
			if (result == null) {
				result = ""; //$NON-NLS-1$
			}
		} else if (expression instanceof ProtectedAreaBlock) {
			if (delegate != null) {
				delegate.visitAcceleoProtectedArea((ProtectedAreaBlock)expression);
			} else {
				visitAcceleoProtectedArea((ProtectedAreaBlock)expression);
			}
			result = context.getDefaultText();
			if (result == null) {
				result = ""; //$NON-NLS-1$
			}
		} else if (!(expression instanceof TemplateExpression)) {
			result = super.visitExpression(expression);
		} else {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationVisitor.UnresolvedCompilationError")); //$NON-NLS-1$
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
		public ParameterInitExpression(OCLExpression<C> expession) {
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
