/*******************************************************************************
 * Copyright (c) 2013, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.ocl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.examples.domain.evaluation.DomainModelManager;
import org.eclipse.ocl.examples.domain.values.CollectionValue;
import org.eclipse.ocl.examples.domain.values.OrderedSetValue;
import org.eclipse.ocl.examples.domain.values.SetValue;
import org.eclipse.ocl.examples.domain.values.Value;
import org.eclipse.ocl.examples.domain.values.impl.InvalidValueException;
import org.eclipse.ocl.examples.domain.values.util.ValuesUtil;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.LetExp;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.examples.pivot.evaluation.EvaluationVisitorImpl;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironment;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironmentFactory;

public abstract class AbstractOCLEvaluator {
	/**
	 * Return the metamodel manager this evaluator should use.
	 * 
	 * @return The metamodel manager this evaluator should use.
	 */
	protected abstract MetaModelManager getMetaModelManager();

	/**
	 * Evaluates the given ExpressionInOCL with the given evaluationTarget as context.
	 * 
	 * @param expression
	 *            The OCL expression to evaluate.
	 * @param evaluationTarget
	 *            Context of this evaluation.
	 * @return Result of this evaluation.
	 */
	public EvaluationResult evaluateExpression(ExpressionInOCL expression, EObject evaluationTarget) {
		final EvaluationVisitor visitor = createEvaluationVisitor(expression, evaluationTarget);

		try {
			final Object value = visitor.visitExpressionInOCL(expression);

			final EvaluationResult result;
			if (value instanceof Value) {
				result = new EvaluationResult(unwrap((Value)value));
			} else {
				result = new EvaluationResult(value);
			}
			return result;
		} catch (InvalidValueException e) {
			final String message;
			if (e.getCause() != null) {
				message = e.getCause().getMessage();
			} else {
				message = e.getMessage();
			}
			return new EvaluationResult(new Status(IStatus.ERROR, OCLInterpreterActivator.PLUGIN_ID, message,
					e));
		}
	}

	/**
	 * Evaluates the given OCL expression on the given context EObject.
	 * <p>
	 * If this expression is a part of a larger OCLExpression, we'll evaluate the "let" located before it so
	 * as to determine all variables in the context before trying to evaluate.
	 * </p>
	 * 
	 * @param expression
	 *            The expression to evaluate.
	 * @param evaluationTarget
	 *            Context of the evaluation.
	 * @return Result of the evaluation.
	 */
	public EvaluationResult evaluateExpression(OCLExpression expression, EObject evaluationTarget) {
		EvaluationResult result = internalEvaluateExpression(expression, evaluationTarget);
		if (result.getEvaluationResult() instanceof Value) {
			result = new EvaluationResult(unwrap((Value)result.getEvaluationResult()));
		}
		return result;
	}

	/**
	 * Evaluates the given OCL expression on the given context EObject, without unwrapping its 'Value' result.
	 * <p>
	 * If this expression is a part of a larger OCLExpression, we'll evaluate the "let" located before it so
	 * as to determine all variables in the context before trying to evaluate.
	 * </p>
	 * 
	 * @param expression
	 *            The expression to evaluate.
	 * @param evaluationTarget
	 *            Context of the evaluation.
	 * @return Result of the evaluation.
	 */
	protected EvaluationResult internalEvaluateExpression(OCLExpression expression, EObject evaluationTarget) {
		EObject current = expression;
		final List<LetExp> letInContext = new ArrayList<LetExp>();
		while (current != null && !(current instanceof ExpressionInOCL)) {
			if (current instanceof LetExp) {
				letInContext.add((LetExp)current);
			}
			current = current.eContainer();
		}

		final ExpressionInOCL rootExpression;
		if (current instanceof ExpressionInOCL) {
			rootExpression = (ExpressionInOCL)current;
		} else {
			return new EvaluationResult(new Status(IStatus.ERROR, OCLInterpreterActivator.PLUGIN_ID,
					"Compilation error in expression."));
		}

		final EvaluationVisitor visitor = createEvaluationVisitor(rootExpression, evaluationTarget);

		EvaluationResult result = null;
		try {
			final ListIterator<LetExp> letIterator = letInContext.listIterator(letInContext.size());
			while (letIterator.hasPrevious()) {
				LetExp let = letIterator.previous();
				Object variableValue = let.getVariable().accept(visitor);
				visitor.getEvaluationEnvironment().add(let.getVariable(), variableValue);
			}
			final Object value = expression.accept(visitor);
			result = new EvaluationResult(value);
		} catch (InvalidValueException e) {
			final String message;
			if (e.getCause() != null) {
				message = e.getCause().getMessage();
			} else {
				message = e.getMessage();
			}
			result = new EvaluationResult(new Status(IStatus.ERROR, OCLInterpreterActivator.PLUGIN_ID,
					message, e));
		}
		return result;
	}

	/**
	 * Create an evaluation visitor for the given expression, positioning the context to match
	 * {@code evaluationTarget}.
	 * 
	 * @param expression
	 *            The expression to evaluate.
	 * @param evaluationTarget
	 *            Context of the evaluation.
	 * @return An evaluation visitor for this expression.
	 */
	private EvaluationVisitor createEvaluationVisitor(ExpressionInOCL expression, EObject evaluationTarget) {
		PivotEnvironmentFactory environmentFactory = new PivotEnvironmentFactory(null, getMetaModelManager());
		PivotEnvironment environment = environmentFactory.createEnvironment();
		EvaluationEnvironment evaluationEnvironment = environmentFactory.createEvaluationEnvironment();
		Object contextValue = getMetaModelManager().getIdResolver().boxedValueOf(evaluationTarget);
		evaluationEnvironment.add(expression.getContextVariable(), contextValue);
		DomainModelManager modelManager = evaluationEnvironment.createModelManager(evaluationTarget);

		return new EvaluationVisitorImpl(environment, evaluationEnvironment, modelManager);
	}

	/**
	 * This unwrap the evaluated result.
	 * 
	 * @param value
	 *            The evaluated result.
	 * @return The unwrapped result.
	 */
	@SuppressWarnings("unchecked")
	protected Object unwrap(Value value) {
		Object realObject = value.asObject();
		CollectionValue collectionValue = ValuesUtil.isCollectionValue(value);
		if (collectionValue != null) {
			if (collectionValue instanceof OrderedSetValue) {
				realObject = new LinkedHashSet<Object>();
			} else if (collectionValue instanceof SetValue) {
				realObject = new HashSet<Object>();
			} else {
				realObject = new ArrayList<Object>();
			}
			for (Object child : collectionValue.iterable()) {
				((Collection<Object>)realObject).add(child);
			}
		}
		return realObject;
	}
}
