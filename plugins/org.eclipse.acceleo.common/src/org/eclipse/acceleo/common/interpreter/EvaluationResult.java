/*****************************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *****************************************************************************************/
package org.eclipse.acceleo.common.interpreter;

import org.eclipse.core.runtime.IStatus;

/**
 * This will be used as the result of our evaluation tasks. It will hold a reference to the actual evaluation
 * result (Module, ModuleElement, OCLExpression ...) along with the status of the compilation.
 * <p>
 * {@link #status} can be either a simple IStatus (only a single error/warning/info has been encountered) or a
 * MultiStatus with all encountered problems as children.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
 */
public class EvaluationResult {
	/**
	 * This will hold the actual result of the evaluation. Note that this can legally be <code>null</code> if
	 * the evaluation either a) did result in a <code>null</code> result or b) the evaluation could not be run
	 * because of critical issues in either compilation or evaluation.
	 */
	private Object evaluationResult;

	/**
	 * This can hold any info, warning or error that has been encountered by the evaluation. This can legally
	 * be <code>null</code> if no problem was encountered.
	 */
	private IStatus status;

	/**
	 * Creates an evaluation result given the evaluation issues. This assumes that critical errors were
	 * encountered during the evaluation or compilation, and that the evaluation itself could not be run.
	 * 
	 * @param status
	 *            The status to report to the user. Can be a {@link org.eclipse.core.runtime.MultiStatus}.
	 */
	public EvaluationResult(IStatus status) {
		this.status = status;
	}

	/**
	 * Creates an evaluation result given the actual result. This assumes that no issues were encountered
	 * during the evaluation.
	 * 
	 * @param evaluationResult
	 *            Result of the evaluation.
	 */
	public EvaluationResult(Object evaluationResult) {
		this.evaluationResult = evaluationResult;
	}

	/**
	 * Creates an evaluation result given the actual result and the status of the evaluation.
	 * 
	 * @param evaluationResult
	 *            Result of the evaluation.
	 * @param status
	 *            The status to report to the user. Can be a {@link org.eclipse.core.runtime.MultiStatus}.
	 */
	public EvaluationResult(Object evaluationResult, IStatus status) {
		this.evaluationResult = evaluationResult;
		this.status = status;
	}

	/**
	 * Returns the evaluation result.
	 * 
	 * @return The evaluation result.
	 */
	public Object getEvaluationResult() {
		return evaluationResult;
	}

	/**
	 * Returns the status of this evaluation.
	 * 
	 * @return The status of this evaluation.
	 */
	public IStatus getStatus() {
		return status;
	}
}
