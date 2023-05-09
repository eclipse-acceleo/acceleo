/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
	/** Hides the default constructor. */
	private QueryEvaluation() {
		// Shouldn't be instantiated.
	}

	/**
	 * Create a new {@link IQueryEvaluationEngine} suitable for evaluating
	 * {@link IQueryBuilderEngine#build(String) parsed} expressions into the given {@link IQuery Environment}.
	 * 
	 * @param environment
	 *            the {@link IQueryEnvironment} to use during evaluation.
	 * @return a new {@link IQueryEvaluationEngine} suitable for evaluating
	 *         {@link IQueryBuilderEngine#build(String) parsed} expressions into the given
	 *         {@link IQueryEnvironment}.
	 */
	public static IQueryEvaluationEngine newEngine(IQueryEnvironment environment) {
		return new QueryEvaluationEngine(environment);
	}

}
