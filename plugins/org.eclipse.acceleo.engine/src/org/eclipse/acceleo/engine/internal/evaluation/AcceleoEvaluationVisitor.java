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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.internal.debug.ASTFragment;
import org.eclipse.acceleo.engine.internal.debug.IDebugAST;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEvaluationEnvironment;
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
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.EvaluationVisitor;
import org.eclipse.ocl.EvaluationVisitorDecorator;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.PropertyCallExp;

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
	/** Externalized name of the "self" OCL variable to avoid too many distinct uses. */
	private static final String SELF_VARIABLE_NAME = "self"; //$NON-NLS-1$

	/** Key of the "undefined guard" error message in acceleoenginemessages.properties. */
	private static final String UNDEFINED_GUARD_MESSAGE_KEY = "AcceleoEvaluationVisitor.UndefinedGuard"; //$NON-NLS-1$

	/**
	 * To debug an AST evaluation. TODO JMU : Put this debugger instance in the evaluation context
	 */
	private static IDebugAST debug;

	/**
	 * A query returns the same result each time it is called with the same arguments. This map will allow us
	 * to keep the result in cache for faster subsequent calls.
	 */
	private final Map<Query, Map<List<Object>, Object>> queryResults = new HashMap<Query, Map<List<Object>, Object>>();

	/** Generation context of this visitor. */
	private final AcceleoEvaluationContext context;

	/** This will allow us to remember the last EObject value of the self variable. */
	private EObject lastEObjectSelfValue;

	/**
	 * This will be set before each call to a PropertyCallExpression or OperationCallExpression and will allow
	 * us to determine the source of such or such call.
	 */
	private OCLExpression<C> lastSourceExpression;

	/** Keeps track of the result of the last source expression. */
	private Object lastSourceExpressionResult;

	/** Retrieve OCL_Invalid once and for all. */
	private final Object oclInvalid = getEnvironment().getOCLStandardLibrary().getOclInvalid();

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
			AcceleoEvaluationContext context) {
		super(decoratedVisitor);
		this.context = context;
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEvaluationVisitor#visitExpression(org.eclipse.ocl.expressions.OCLExpression)
	 */
	@Override
	public Object visitExpression(OCLExpression<C> expression) {
		Object result = null;
		EObject debugInput = null;
		ASTFragment astFragment = null;
		if (debug != null && !(expression instanceof StringLiteralExp)) {
			// TODO JMU see new object "lastSourceExpressionResult". this could be interesting.
			debugInput = lastEObjectSelfValue;
			astFragment = new ASTFragment(expression);
			if (debugInput != null && debugInput.eClass().getEStructuralFeature("name") != null) { //$NON-NLS-1$
				Object name = debugInput.eGet(debugInput.eClass().getEStructuralFeature("name")); //$NON-NLS-1$
				if (name instanceof String) {
					astFragment.setEObjectNameFilter((String)name);
				}
			}
			debug.startDebug(astFragment);
			debug.stepDebugInput(astFragment, debugInput);
		}
		// This try / catch block allows us to handle the disposal of all context information.
		try {
			// All evaluations pass through here. We'll handle blocks' init sections here.
			final boolean hasInit = expression instanceof Block && ((Block)expression).getInit() != null;
			if (hasInit) {
				handleAcceleoInitSection(((Block)expression).getInit());
			}
			// Actual delegation to the visitor's methods.
			if (expression instanceof Template) {
				result = visitAcceleoTemplate((Template)expression);
			} else if (expression instanceof IfBlock) {
				visitAcceleoIfBlock((IfBlock)expression);
				// This has no explicit result
				result = ""; //$NON-NLS-1$
			} else if (expression instanceof ForBlock) {
				visitAcceleoForBlock((ForBlock)expression);
				// This has no explicit result
				result = ""; //$NON-NLS-1$
			} else if (expression instanceof FileBlock) {
				visitAcceleoFileBlock((FileBlock)expression);
				// This has no explicit result
				result = ""; //$NON-NLS-1$
			} else if (expression instanceof TemplateInvocation) {
				result = visitAcceleoTemplateInvocation((TemplateInvocation)expression);
			} else if (expression instanceof QueryInvocation) {
				result = visitAcceleoQueryInvocation((QueryInvocation)expression);
			} else if (expression instanceof LetBlock) {
				visitAcceleoLetBlock((LetBlock)expression);
				// This has no explicit result
				result = ""; //$NON-NLS-1$
			} else if (expression instanceof ProtectedAreaBlock) {
				visitAcceleoProtectedArea((ProtectedAreaBlock)expression);
				// This has no explicit result
				result = ""; //$NON-NLS-1$
			} else {
				result = super.visitExpression(expression);
			}

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
				context.append(String.valueOf(result), (Block)generatedBlock, lastEObjectSelfValue);
			}

			// If we were evaluating a block and it had an init section, restore variables as they were now.
			if (hasInit) {
				restoreVariables();
			}
		} catch (final AcceleoEvaluationException e) {
			throw e;
			// CHECKSTYLE:OFF
			/*
			 * deactivated checkstyle as we need to properly dispose the context when an exception is throw
			 * yet we cannot use finally (this visitor is called recursively).
			 */
		} catch (final RuntimeException e) {
			// CHECKSTYLE:ON
			try {
				context.dispose();
			} catch (final AcceleoEvaluationException ee) {
				// We're already in an exception handling phase. Propagate the former exception.
			}
			throw e;
		} finally {
			if (debug != null && !(expression instanceof StringLiteralExp)) {
				debug.stepDebugOutput(astFragment, debugInput, result);
				debug.endDebug(astFragment);
			}
		}
		// FIXME check if OCL_Invalid results are logged by OCL
		// FIXME handle exceptions (should probably add runtime error markers)

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
		return super.visitPropertyCallExp(callExp);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.EvaluationVisitorDecorator#visitOperationCallExp(org.eclipse.ocl.expressions.OperationCallExp)
	 */
	@Override
	public Object visitOperationCallExp(OperationCallExp<C, O> callExp) {
		lastSourceExpression = callExp.getSource();
		return super.visitOperationCallExp(callExp);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link Block}.
	 * 
	 * @param block
	 *            The Acceleo block that is to be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoBlock(Block block) {
		for (final OCLExpression nested : block.getBody()) {
			visitExpression(nested);
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
		final Object fileURLResult = visitExpression((OCLExpression)fileBlock.getFileUrl());
		if (isUndefined(fileURLResult)) {
			final AcceleoEvaluationException exception = new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationVisitor.UndefinedFileURL", fileBlock.getStartPosition(), //$NON-NLS-1$
							((Module)EcoreUtil.getRootContainer(fileBlock)).getName(), fileBlock.toString(),
							getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME)));
			exception.fillInStackTrace();
			throw exception;
		} else if (fileURLResult instanceof Collection) {
			final AcceleoEvaluationException exception = new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationVisitor.CollectionFileURL", fileBlock.getStartPosition(), //$NON-NLS-1$
							((Module)EcoreUtil.getRootContainer(fileBlock)).getName(), fileBlock.toString(),
							getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME)));
			exception.fillInStackTrace();
			throw exception;
		}
		final String filePath = String.valueOf(fileURLResult);
		final boolean appendMode = fileBlock.getOpenMode().getValue() == OpenModeKind.APPEND_VALUE;
		if ("stdout".equals(filePath)) { //$NON-NLS-1$
			context.openNested(System.out);
		} else {
			final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
			final EObject source;
			if (currentSelf instanceof EObject) {
				source = (EObject)currentSelf;
			} else {
				source = lastEObjectSelfValue;
			}
			context.openNested(filePath, fileBlock, source, appendMode);
		}
		// TODO handle file ID
		for (final OCLExpression nested : fileBlock.getBody()) {
			visitExpression(nested);
		}
		context.closeContext();
	}

	/**
	 * Handles the evaluation of an Acceleo {@link ForBlock}.
	 * 
	 * @param forBlock
	 *            The Acceleo block that is to be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoForBlock(ForBlock forBlock) {
		final Object iteration = visitExpression((OCLExpression)forBlock.getIterSet());
		final Variable loopVariable = forBlock.getLoopVariable();
		final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);

		if (isUndefined(iteration)) {
			final AcceleoEvaluationException exception = new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationVisitor.NullForIteration", forBlock.getStartPosition(), //$NON-NLS-1$
							((Module)EcoreUtil.getRootContainer(forBlock)).getName(), forBlock.toString(),
							currentSelf));
			exception.fillInStackTrace();
			throw exception;
		}

		// There is a possibility for the for to have a single element in its iteration
		if (iteration instanceof Collection) {
			if (((Collection)iteration).size() > 0 && forBlock.getBefore() != null) {
				visitExpression((OCLExpression)forBlock.getBefore());
			}
			final Iterator<Object> contentIterator = ((Collection)iteration).iterator();
			while (contentIterator.hasNext()) {
				final Object o = contentIterator.next();
				getEvaluationEnvironment().replace(loopVariable.getName(), o);
				// [255379] sets new value of "self" to change context
				getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, o);

				final Object guardValue;
				if (forBlock.getGuard() == null) {
					guardValue = Boolean.TRUE;
				} else {
					guardValue = visitExpression((OCLExpression)forBlock.getGuard());
				}
				if (isUndefined(guardValue)) {
					final AcceleoEvaluationException exception = new AcceleoEvaluationException(
							AcceleoEngineMessages.getString(UNDEFINED_GUARD_MESSAGE_KEY, forBlock
									.getStartPosition(), ((Module)EcoreUtil.getRootContainer(forBlock))
									.getName(), forBlock, o, forBlock.getGuard()));
					exception.fillInStackTrace();
					throw exception;
				}

				if (((Boolean)guardValue).booleanValue()) {
					for (final OCLExpression nested : forBlock.getBody()) {
						visitExpression(nested);
					}
					if (forBlock.getEach() != null && contentIterator.hasNext()) {
						visitExpression((OCLExpression)forBlock.getEach());
					}
				}
			}
			if (((Collection)iteration).size() > 0 && forBlock.getAfter() != null) {
				visitExpression((OCLExpression)forBlock.getAfter());
			}
		} else {
			if (forBlock.getBefore() != null) {
				visitExpression((OCLExpression)forBlock.getBefore());
			}
			getEvaluationEnvironment().replace(loopVariable.getName(), iteration);
			// [255379] sets new value of "self" to change context
			getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, iteration);

			final Object guardValue;
			if (forBlock.getGuard() == null) {
				guardValue = Boolean.TRUE;
			} else {
				guardValue = visitExpression((OCLExpression)forBlock.getGuard());
			}
			if (isUndefined(guardValue)) {
				final AcceleoEvaluationException exception = new AcceleoEvaluationException(
						AcceleoEngineMessages.getString(UNDEFINED_GUARD_MESSAGE_KEY, forBlock
								.getStartPosition(),
								((Module)EcoreUtil.getRootContainer(forBlock)).getName(), forBlock,
								iteration, forBlock.getGuard()));
				exception.fillInStackTrace();
				throw exception;
			}

			if (((Boolean)guardValue).booleanValue()) {
				for (final OCLExpression nested : forBlock.getBody()) {
					visitExpression(nested);
				}
			}
			if (forBlock.getAfter() != null) {
				visitExpression((OCLExpression)forBlock.getAfter());
			}
		}

		// [255379] restore context
		getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, currentSelf);
	}

	/**
	 * Handles the evaluation of an Acceleo {@link IfBlock}.
	 * 
	 * @param ifBlock
	 *            The Acceleo block that is to be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoIfBlock(IfBlock ifBlock) {
		final OCLExpression condition = ifBlock.getIfExpr();
		final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);

		final Object conditionValue = visitExpression(condition);
		if (isUndefined(conditionValue)) {
			final AcceleoEvaluationException exception = new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationVisitor.UndefinedCondition", ifBlock.getStartPosition(), //$NON-NLS-1$
							((Module)EcoreUtil.getRootContainer(ifBlock)).getName(), ifBlock, currentSelf));
			exception.fillInStackTrace();
			throw exception;
		}

		if (((Boolean)conditionValue).booleanValue()) {
			for (final OCLExpression nested : ifBlock.getBody()) {
				visitExpression(nested);
			}
		} else {
			if (ifBlock.getElseIf().size() > 0) {
				// If one of the else ifs has its condition evaluated to true, this will hold it
				IfBlock temp = null;
				for (final IfBlock elseif : ifBlock.getElseIf()) {
					final Object elseValue = visitExpression((OCLExpression)elseif.getIfExpr());
					if (isUndefined(elseValue)) {
						final AcceleoEvaluationException exception = new AcceleoEvaluationException(
								AcceleoEngineMessages.getString(
										"AcceleoEvaluationVisitor.UndefinedElseCondition", elseif //$NON-NLS-1$
												.getStartPosition(), ((Module)EcoreUtil
												.getRootContainer(elseif)).getName(), elseif, currentSelf));
						exception.fillInStackTrace();
						throw exception;
					}
					if (((Boolean)elseValue).booleanValue()) {
						temp = elseif;
						break;
					}
				}
				if (temp != null) {
					for (final OCLExpression nested : temp.getBody()) {
						visitExpression(nested);
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
	 * Handles the evaluation of an Acceleo {@link LetBlock}.
	 * 
	 * @param letBlock
	 *            The Acceleo let block that is to be evaluated.
	 */
	@SuppressWarnings("unchecked")
	public void visitAcceleoLetBlock(LetBlock letBlock) {
		final Object currentSelf = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		Variable var = letBlock.getLetVariable();
		Object value = visitExpression((OCLExpression)var.getInitExpression());
		if (isUndefined(value)) {
			final AcceleoEvaluationException exception = new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationVisitor.UndefinedLetValue", letBlock.getStartPosition(), //$NON-NLS-1$
							((Module)EcoreUtil.getRootContainer(letBlock)).getName(), letBlock, currentSelf));
			exception.fillInStackTrace();
			throw exception;
		}

		if (var.getType().isInstance(value)) {
			getEvaluationEnvironment().replace(var.getName(), value);
			for (final OCLExpression nested : letBlock.getBody()) {
				visitExpression(nested);
			}
		} else {
			if (letBlock.getElseLet().size() > 0) {
				// If one of the else lets has its "instanceof" condition evaluated to true, this will hold it
				LetBlock temp = null;
				for (final LetBlock elseLet : letBlock.getElseLet()) {
					var = elseLet.getLetVariable();
					value = visitExpression((OCLExpression)var.getInitExpression());
					if (isUndefined(value)) {
						final AcceleoEvaluationException exception = new AcceleoEvaluationException(
								AcceleoEngineMessages.getString(
										"AcceleoEvaluationVisitor.UndefinedElseLetValue", elseLet //$NON-NLS-1$
												.getStartPosition(), ((Module)EcoreUtil
												.getRootContainer(elseLet)).getName(), elseLet, currentSelf));
						exception.fillInStackTrace();
						throw exception;
					}
					if (var.getType().isInstance(value)) {
						getEvaluationEnvironment().replace(var.getName(), value);
						temp = elseLet;
						break;
					}
				}
				if (temp != null) {
					for (final OCLExpression nested : temp.getBody()) {
						visitExpression(nested);
					}
				} else if (letBlock.getElse() != null) {
					visitAcceleoBlock(letBlock.getElse());
				}
			} else if (letBlock.getElse() != null) {
				visitAcceleoBlock(letBlock.getElse());
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
		final Object markerValue = visitExpression((OCLExpression)protectedArea.getMarker());
		final Object source = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		if (isUndefined(markerValue)) {
			final AcceleoEvaluationException exception = new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEvaluationVisitor.UndefinedAreaMarker", protectedArea //$NON-NLS-1$
							.getStartPosition(), ((Module)EcoreUtil.getRootContainer(protectedArea))
							.getName(), protectedArea, source));
			exception.fillInStackTrace();
			throw exception;
		}

		final String marker = markerValue.toString().trim();
		final String areaContent = context.getProtectedAreaContent(marker);
		if (source instanceof EObject) {
			lastEObjectSelfValue = (EObject)source;
		}
		if (areaContent != null) {
			context.append(areaContent, protectedArea, lastEObjectSelfValue);
		} else {
			context.append(
					AcceleoEngineMessages.getString("usercode.start"), protectedArea, lastEObjectSelfValue); //$NON-NLS-1$
			context.append(' ' + marker, protectedArea, lastEObjectSelfValue);
			visitAcceleoBlock(protectedArea);
			context.append(
					AcceleoEngineMessages.getString("usercode.end"), protectedArea, lastEObjectSelfValue); //$NON-NLS-1$
		}
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

		final List<Object> arguments = new ArrayList<Object>();
		for (int i = 0; i < query.getParameter().size(); i++) {
			final Object argValue = visitExpression((OCLExpression)invocation.getArgument().get(i));
			if (isUndefined(argValue)) {
				final AcceleoEvaluationException exception = new AcceleoEvaluationException(
						AcceleoEngineMessages.getString(
								"AcceleoEvaluationVisitor.UndefinedArgument", //$NON-NLS-1$
								invocation.getStartPosition(), ((Module)EcoreUtil
										.getRootContainer(invocation)).getName(), invocation,
								getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME), invocation
										.getArgument().get(i)));
				exception.fillInStackTrace();
				throw exception;
			}
			arguments.add(argValue);
		}
		// If the query has already been run with these arguments, return the cached result
		if (queryResults.containsKey(query)) {
			final Map<List<Object>, Object> results = queryResults.get(query);
			final Object result = results.get(arguments);
			if (result != null) {
				return result;
			}
		}

		// sets or replaces parameters for the invocation
		// [255379] size + 1 to keep old value of "self"
		final Object[] oldArgs = new Object[invocation.getArgument().size() + 1];
		for (int i = 0; i < query.getParameter().size(); i++) {
			final Variable param = query.getParameter().get(i);
			oldArgs[i] = getEvaluationEnvironment().getValueOf(param.getName());
			getEvaluationEnvironment().replace(param.getName(), arguments.get(i));
			// [255379] sets new value of "self" to match the very first arg of the query
			if (i == 0) {
				oldArgs[oldArgs.length - 1] = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
				getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, arguments.get(i));
			}
		}

		final Object result = visitExpression((OCLExpression)query.getExpression());

		// restores parameters as they were prior to the call
		for (int i = 0; i < query.getParameter().size(); i++) {
			final Variable param = query.getParameter().get(i);
			getEvaluationEnvironment().replace(param.getName(), oldArgs[i]);
		}
		// [255379] restore self if need be
		if (query.getParameter().size() > 0) {
			getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, oldArgs[oldArgs.length - 1]);
		}
		// Store result of the query invocation
		if (queryResults.containsKey(query)) {
			final Map<List<Object>, Object> results = queryResults.get(query);
			results.put(arguments, result);
		} else {
			final Map<List<Object>, Object> results = new HashMap<List<Object>, Object>(2);
			results.put(arguments, result);
			queryResults.put(query, results);
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
		for (final OCLExpression nested : template.getBody()) {
			visitExpression(nested);
		}
		return context.closeContext();
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
		// FIXME refactor this into multiple methods
		context.openNested();
		final Template template = invocation.getDefinition();
		final List<Object> newArguments = new ArrayList<Object>();
		final Object[] oldArgs;
		// FIXME handle multiple invocations and "each"
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
			// determine new values of argument variables and save the old
			// [255379] size + 1 to keep old value of "self"
			oldArgs = new Object[containingTemplate.getParameter().size() + 1];
			for (int i = 0; i < actualTemplate.getParameter().size(); i++) {
				final Variable param = actualTemplate.getParameter().get(i);
				oldArgs[i] = getEvaluationEnvironment().getValueOf(param.getName());
				final Object newArg = getEvaluationEnvironment().getValueOf(
						containingTemplate.getParameter().get(i).getName());
				getEvaluationEnvironment().replace(param.getName(), newArg);
				newArguments.add(newArg);
				// [255379] sets new value of "self" to match the very first arg of the invocation
				if (i == 0) {
					oldArgs[oldArgs.length - 1] = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
					getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, newArg);
				}
			}
		} else {
			// [255379] size + 1 to keep old value of "self"
			oldArgs = new Object[invocation.getArgument().size() + 1];
			// Determine values of the arguments
			for (OCLExpression expression : invocation.getArgument()) {
				final Object argValue = visitExpression(expression);
				if (isUndefined(argValue)) {
					final AcceleoEvaluationException exception = new AcceleoEvaluationException(
							AcceleoEngineMessages.getString(
									"AcceleoEvaluationVisitor.UndefinedArgument", //$NON-NLS-1$
									invocation.getStartPosition(), ((Module)EcoreUtil
											.getRootContainer(invocation)).getName(), invocation,
									getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME), expression));
					exception.fillInStackTrace();
					throw exception;
				}
				newArguments.add(argValue);
			}
			// retrieve all applicable candidates of the call
			final List<Template> applicableCandidates = ((AcceleoEvaluationEnvironment)getEvaluationEnvironment())
					.getAllCandidates((Module)EcoreUtil.getRootContainer(invocation), template, newArguments);
			evaluateGuards(applicableCandidates, newArguments);
			// We now know the actual template that's to be called
			if (applicableCandidates.size() > 0) {
				actualTemplate = ((AcceleoEvaluationEnvironment)getEvaluationEnvironment())
						.getMostSpecificTemplate(applicableCandidates, newArguments);
				// Set parameter values while retaining old ones
				for (int i = 0; i < actualTemplate.getParameter().size(); i++) {
					final Variable param = actualTemplate.getParameter().get(i);
					oldArgs[i] = getEvaluationEnvironment().getValueOf(param.getName());
					getEvaluationEnvironment().replace(param.getName(), newArguments.get(i));
					// [255379] sets new value of "self" to match the very first arg of the invocation
					if (i == 0) {
						oldArgs[oldArgs.length - 1] = getEvaluationEnvironment().getValueOf(
								SELF_VARIABLE_NAME);
						getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, newArguments.get(i));
					}
				}
			} else {
				// No template remains after guard evaluation. Create an empty template so no
				// text will be generated from this call.
				actualTemplate = MtlFactory.eINSTANCE.createTemplate();
			}
		}
		if (invocation.getBefore() != null) {
			visitExpression((OCLExpression)invocation.getBefore());
		}
		final Object source = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
		if (source instanceof EObject) {
			lastEObjectSelfValue = (EObject)source;
		}
		context.append(visitExpression((OCLExpression)actualTemplate).toString(), actualTemplate,
				lastEObjectSelfValue);
		if (invocation.getAfter() != null) {
			visitExpression((OCLExpression)invocation.getAfter());
		}

		// restore parameters as they were prior to the call
		for (int i = 0; i < actualTemplate.getParameter().size(); i++) {
			final Variable param = actualTemplate.getParameter().get(i);
			getEvaluationEnvironment().replace(param.getName(), oldArgs[i]);
		}
		// [255379] restore self if need be
		if (actualTemplate.getParameter().size() > 0) {
			getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, oldArgs[oldArgs.length - 1]);
		}
		return context.closeContext();
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
	private void evaluateGuards(List<Template> candidates, List<Object> arguments) {
		AcceleoEvaluationException exception = null;
		/*
		 * NOTE : we depend on the ordering offered by List types. Do not change Collection implementation to
		 * a non-ordered one.
		 */
		for (final Template candidate : new ArrayList<Template>(candidates)) {
			// Set parameter values while retaining old ones
			// [255379] size + 1 to retain "self" value
			final Object[] oldArgs = new Object[candidate.getParameter().size() + 1];
			for (int i = 0; i < candidate.getParameter().size(); i++) {
				final Variable param = candidate.getParameter().get(i);
				oldArgs[i] = getEvaluationEnvironment().getValueOf(param.getName());
				getEvaluationEnvironment().replace(param.getName(), arguments.get(i));
				// [255379] sets new value of "self" to match the very first arg of the invocation
				if (i == 0) {
					oldArgs[oldArgs.length - 1] = getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME);
					getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, arguments.get(i));
				}
			}
			final Object guardValue;
			if (candidate.getGuard() == null) {
				guardValue = Boolean.TRUE;
			} else {
				guardValue = visitExpression((OCLExpression)candidate.getGuard());
			}
			if (isUndefined(guardValue)) {
				exception = new AcceleoEvaluationException(AcceleoEngineMessages.getString(
						UNDEFINED_GUARD_MESSAGE_KEY, candidate.getStartPosition(), ((Module)EcoreUtil
								.getRootContainer(candidate)).getName(), candidate,
						getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME), candidate.getGuard()));
				exception.fillInStackTrace();
				candidates.remove(candidate);
				continue;
			}

			if (!((Boolean)guardValue).booleanValue()) {
				candidates.remove(candidate);
			}

			// restore parameters as they were prior to the call
			for (int i = 0; i < candidate.getParameter().size(); i++) {
				final Variable param = candidate.getParameter().get(i);
				getEvaluationEnvironment().replace(param.getName(), oldArgs[i]);
			}
			// [255379] restore self if need be
			if (candidate.getParameter().size() > 0) {
				getEvaluationEnvironment().replace(SELF_VARIABLE_NAME, oldArgs[oldArgs.length - 1]);
			}
		}
		if (candidates.size() == 0 && exception != null) {
			throw exception;
		}
	}

	/**
	 * Handles the evaluation of an Acceleo {@link InitSection}. This will simply add all the declared
	 * variables to the evaluation environment. Evaluation of all blocks should use this in order to save the
	 * variables state <u>before</u> evaluation and be able to restore it afterwards through
	 * {@link #restoreVariables()} .
	 * 
	 * @param init
	 *            The init section containing the variables to process.
	 */
	@SuppressWarnings("unchecked")
	private void handleAcceleoInitSection(InitSection init) {
		final Map<String, Object> oldVariables = new HashMap<String, Object>(init.getVariable().size());
		for (final Variable var : init.getVariable()) {
			final String varName = var.getName();
			final Object oldValue = getEvaluationEnvironment().getValueOf(varName);
			oldVariables.put(varName, oldValue);
			final Object newValue = visitExpression((OCLExpression)var.getInitExpression());
			if (isUndefined(newValue)) {
				final AcceleoEvaluationException exception = new AcceleoEvaluationException(
						AcceleoEngineMessages.getString("AcceleoEvaluationVisitor.UndefinedVariable", var //$NON-NLS-1$
								.getStartPosition(), ((Module)EcoreUtil.getRootContainer(init)).getName(),
								init.eContainer(), getEvaluationEnvironment().getValueOf(SELF_VARIABLE_NAME),
								var));
				exception.fillInStackTrace();
				throw exception;
			}
			getEvaluationEnvironment().replace(varName, newValue);
		}
		context.saveVariableValues(oldVariables);
	}

	/**
	 * This will restore all variables in the environment according to the values saved within the evaluation
	 * context. This is called internally for all blocks that had an init section and shouldn't be called from
	 * anywhere else.
	 */
	private void restoreVariables() {
		final Map<String, Object> variables = context.getLastVariablesValues();
		for (final Entry<String, Object> entry : variables.entrySet()) {
			getEvaluationEnvironment().replace(entry.getKey(), entry.getValue());
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
	 * Returns <code>true</code> if the value is either <code>null</code> or equal to the OCL standard
	 * library's OCLInvalid object.
	 * 
	 * @param value
	 *            Value we need to test.
	 * @return <code>true</code> if the value is either <code>null</code> or equal to the OCL standard
	 *         library's OCLInvalid object, <code>false</code> otherwise.
	 */
	private boolean isUndefined(Object value) {
		return value == null || value == oclInvalid;
	}
}
