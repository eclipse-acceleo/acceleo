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
package org.eclipse.acceleo.ui.interpreter.completeocl.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.examples.pivot.Constraint;
import org.eclipse.ocl.examples.pivot.Element;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Operation;
import org.eclipse.ocl.examples.pivot.Package;
import org.eclipse.ocl.examples.pivot.Root;
import org.eclipse.ocl.examples.pivot.Type;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;

/**
 * This class aims at providing the necessary API to evaluate an OCL query that was compiled from a simple
 * String.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CompleteOCLEvaluationTask implements Callable<EvaluationResult> {
	/** Current interpreter context. */
	private final EvaluationContext context;

	/** Current metaModel Manager. */
	private final MetaModelManager metaModelManager;

	/**
	 * Instantiates the evaluation task for the given evaluation context.
	 * 
	 * @param context
	 *            The Current interpreter context.
	 * @param metaModelManager
	 *            The Current MetaModel Manager.
	 */
	public CompleteOCLEvaluationTask(EvaluationContext context, MetaModelManager metaModelManager) {
		this.context = context;
		this.metaModelManager = metaModelManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public EvaluationResult call() throws Exception {
		checkCancelled();

		CompilationResult compilationResult = context.getCompilationResult();
		EvaluationResult shortcut = null;
		if (compilationResult == null || !checkExpression(compilationResult)) {
			shortcut = new EvaluationResult(new Status(IStatus.ERROR,
					CompleteOCLInterpreterActivator.PLUGIN_ID, "Compilation error in expression."));
		} else if (compilationResult.getStatus() != null
				&& compilationResult.getStatus().getSeverity() != IStatus.OK) {
			shortcut = new EvaluationResult(compilationResult.getStatus());
		}
		if (shortcut != null) {
			return shortcut;
		}
		assert compilationResult != null;

		checkCancelled();

		if (context.getTargetNotifiers().isEmpty()) {
			return new EvaluationResult(new Status(IStatus.INFO, CompleteOCLInterpreterActivator.PLUGIN_ID,
					"No context selected."));
		}

		final CompleteOCLEvaluator evaluator = new CompleteOCLEvaluator(metaModelManager);
		final Notifier evaluationTarget = context.getTargetNotifiers().get(0);
		final EvaluationResult result;
		if (compilationResult.getCompiledExpression() instanceof ExpressionInOCL) {
			final ExpressionInOCL expressionInOCL = (ExpressionInOCL)compilationResult
					.getCompiledExpression();
			if (evaluationTarget instanceof EObject) {
				result = evaluator.evaluateExpression(expressionInOCL, (EObject)evaluationTarget);
			} else {
				result = new EvaluationResult(new Status(IStatus.INFO,
						CompleteOCLInterpreterActivator.PLUGIN_ID, "Cannot evaluate " + expressionInOCL
								+ " on the selected Notifier."));
			}
		} else if (compilationResult.getCompiledExpression() instanceof OCLExpression) {
			if (evaluationTarget instanceof EObject) {
				result = evaluator.evaluateExpression(
						(OCLExpression)compilationResult.getCompiledExpression(), (EObject)evaluationTarget);
			} else {
				result = new EvaluationResult(new Status(IStatus.INFO,
						CompleteOCLInterpreterActivator.PLUGIN_ID, "Cannot evaluate "
								+ compilationResult.getCompiledExpression() + " on the selected Notifier."));
			}
		} else if (compilationResult.getCompiledExpression() instanceof Element) {
			result = evaluator.evaluateCompleteOCLElement((Element)compilationResult.getCompiledExpression(),
					evaluationTarget);
		} else {
			result = new EvaluationResult(new Status(IStatus.ERROR,
					CompleteOCLInterpreterActivator.PLUGIN_ID, "Unknown error evaluating expression."));
		}
		return result;
	}

	/**
	 * Checks whether the given compilation result contains a valid Pivot expression.
	 * 
	 * @param compilationResult
	 *            The compilation result.
	 * @return true if the result is valid. false otherwise.
	 */
	private boolean checkExpression(CompilationResult compilationResult) {
		Object expression = compilationResult.getCompiledExpression();

		boolean knownExpression = false;
		if (expression instanceof Root || expression instanceof Package || expression instanceof Type
				|| expression instanceof Constraint || expression instanceof Operation) {
			knownExpression = true;
		} else {
			knownExpression = expression instanceof OCLExpression || expression instanceof ExpressionInOCL;
		}
		return knownExpression;
	}

	/**
	 * Throws a new {@link CancellationException} if the current thread has been cancelled.
	 */
	private void checkCancelled() {
		if (Thread.currentThread().isInterrupted()) {
			throw new CancellationException();
		}
	}
}
