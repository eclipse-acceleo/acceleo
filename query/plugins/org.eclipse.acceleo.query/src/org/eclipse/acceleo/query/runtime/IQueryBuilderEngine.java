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

import org.eclipse.acceleo.query.parser.AstResult;

/**
 * / Evaluation Engine is used to evaluate acceleo expressions. The evaluation engine allows to register
 * packages of services that can be called.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public interface IQueryBuilderEngine extends IQueryEngine {

	/**
	 * Builds the specified expression.
	 * 
	 * @param expression
	 *            the expression that must be evaluated
	 * @return the resulting {@link AstResult}.
	 * @throws AcceleoQueryEvaluationException
	 *             if expression's syntax is invalid.
	 */
	AstResult build(String expression) throws AcceleoQueryEvaluationException;

}
