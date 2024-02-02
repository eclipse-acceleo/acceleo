/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.common.util.Diagnostic;

/**
 * Represents the result of a query evaluation, along with potential errors or warnings if any.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class EvaluationResult {
	/** The actual result of this evaluation. */
	private final Object result;

	/** Diagnostic of the evaluation. */
	private final Diagnostic diagnostic;

	/**
	 * The <code>null</code> {@link IType} if any.
	 */
	private final IType nullType;

	/**
	 * Creates an evaluation result given the actual Object result of said evaluation, and the diagnostic to
	 * associate to it.
	 * 
	 * @param result
	 *            The actual result of this evaluation.
	 * @param nullType
	 *            the <code>null</code> {@link IType}
	 * @param diagnostic
	 *            Diagnostic of the evaluation.
	 */
	public EvaluationResult(Object result, IType nullType, Diagnostic diagnostic) {
		this.result = result;
		this.nullType = nullType;
		this.diagnostic = diagnostic;
	}

	/**
	 * Returns the actual evaluation result.
	 * 
	 * @return the actual evaluation result.
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * Gets the <code>null</code> {@link IType}.
	 * 
	 * @return the <code>null</code> {@link IType} if any, <code>null</code> otherwise
	 */
	public IType getNullType() {
		return nullType;
	}

	/**
	 * Returns the status of this evaluation.
	 * 
	 * @return the status of this evaluation.
	 */
	public Diagnostic getDiagnostic() {
		return diagnostic;
	}
}
