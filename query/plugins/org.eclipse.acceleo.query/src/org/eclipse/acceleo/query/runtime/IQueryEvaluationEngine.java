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

import java.util.Map;

import org.eclipse.acceleo.query.parser.AstResult;

/**
 * / Evaluation Engine is used to evaluate acceleo expressions. The evaluation engine allows to register
 * packages of services that can be called.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public interface IQueryEvaluationEngine extends IQueryEngine {

	/**
	 * Evaluates the specified expression in the specified environment. The expression must comply to Acceleo
	 * Query Language's syntax. The provided environment must at least contain a definition for a variable
	 * named 'self'.
	 * 
	 * @param expression
	 *            the expression that must be evaluated
	 * @param variables
	 *            an environment that maps variable's name to their defined values.
	 * @return the result of evaluating the specified expression in the specified environment.
	 * @throws AcceleoQueryEvaluationException
	 *             if the variable self isn't defined or if expression's syntax is invalid.
	 */
	EvaluationResult eval(AstResult expression, Map<String, Object> variables)
			throws AcceleoQueryEvaluationException;

}
