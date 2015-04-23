/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * Represents the result of a query evaluation, along with potential errors or warnings if any.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class EvaluationResult {
	private final Object result;

	private final Diagnostic diagnostic;

	public EvaluationResult(Object result, Diagnostic diagnostic) {
		this.result = result;
		this.diagnostic = diagnostic;
	}

	/**
	 * @return the actual evaluation result.
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @return the status of this evaluation.
	 */
	public Diagnostic getDiagnostic() {
		return diagnostic;
	}
}
