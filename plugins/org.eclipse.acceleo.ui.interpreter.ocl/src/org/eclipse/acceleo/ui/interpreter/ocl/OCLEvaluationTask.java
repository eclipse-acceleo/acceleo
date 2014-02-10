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

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;

import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.examples.pivot.ExpressionInOCL;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;

/**
 * This class aims at providing the necessary API to evaluate an OCL query that was compiled from a simple
 * String.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class OCLEvaluationTask extends AbstractOCLEvaluator implements Callable<EvaluationResult> {
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
	public OCLEvaluationTask(EvaluationContext context, MetaModelManager metaModelManager) {
		this.context = context;
		this.metaModelManager = metaModelManager;
	}

	@Override
	protected MetaModelManager getMetaModelManager() {
		return metaModelManager;
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
			shortcut = new EvaluationResult(new Status(IStatus.ERROR, OCLInterpreterActivator.PLUGIN_ID,
					"Compilation error in expression."));
		} else if (compilationResult.getStatus() != null
				&& compilationResult.getStatus().getSeverity() != IStatus.OK) {
			shortcut = new EvaluationResult(null);
		}
		if (shortcut != null) {
			return shortcut;
		}

		checkCancelled();

		EvaluationResult result = new EvaluationResult(new Status(IStatus.ERROR,
				OCLInterpreterActivator.PLUGIN_ID, "Unknown error evaluating expression."));
		EObject evaluationTarget = context.getTargetEObjects().get(0);
		assert compilationResult != null;
		if (compilationResult.getCompiledExpression() instanceof ExpressionInOCL) {
			ExpressionInOCL expression = (ExpressionInOCL)compilationResult.getCompiledExpression();
			result = evaluateExpression(expression, evaluationTarget);
		} else if (compilationResult.getCompiledExpression() instanceof OCLExpression) {
			OCLExpression compiledExpression = (OCLExpression)compilationResult.getCompiledExpression();
			result = evaluateExpression(compiledExpression, evaluationTarget);
		}

		return result;
	}

	/**
	 * Checks if the compiled expression is an OCL expression or not.
	 * 
	 * @param compilationResult
	 *            The compilation result.
	 * @return true if the result is valid. false otherwise.
	 */
	private boolean checkExpression(CompilationResult compilationResult) {
		Object expression = compilationResult.getCompiledExpression();
		return expression instanceof ExpressionInOCL || expression instanceof OCLExpression;
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
