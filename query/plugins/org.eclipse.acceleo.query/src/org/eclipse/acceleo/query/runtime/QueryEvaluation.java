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

import org.eclipse.acceleo.query.runtime.impl.QueryEvaluationEngine;

/**
 * Static utility methods pertaining to the evaluation of Acceleo queries.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public final class QueryEvaluation {

	private QueryEvaluation() {

	}

	/**
	 * Create a new {@link IQueryEvaluationEngine} suitable for evaluating parsed expressions.
	 * 
	 * @param environment
	 *            the environment to use during evaluation.
	 * @return a new {@link IQueryEvaluationEngine} suitable for evaluating parsed expressions.
	 */
	public static IQueryEvaluationEngine newEngine(IQueryEnvironment environment) {
		return new QueryEvaluationEngine(environment);
	}

}
