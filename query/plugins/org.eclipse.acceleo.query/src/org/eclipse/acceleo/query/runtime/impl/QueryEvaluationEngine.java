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
package org.eclipse.acceleo.query.runtime.impl;

import java.util.Map;

import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;

/**
 * {@link QueryEvaluationEngine} is the default query evaluation engine.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class QueryEvaluationEngine implements IQueryEvaluationEngine {

	/**
	 * When <code>true</code> the evaluation services log the error and warning messages.
	 */
	private boolean log = true;

	/**
	 * The environment containing all necessary information and used to execute query services.
	 */
	private IQueryEnvironment queryEnvironment;

	/**
	 * Constructor. It takes an IQueryEnvironment as parameter.
	 * 
	 * @param queryEnvironment
	 *            The environment containing all necessary information and used to execute query services.
	 */
	public QueryEvaluationEngine(IQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
	}

	@Override
	public Object eval(AstResult expression, Map<String, Object> environment)
			throws AcceleoQueryEvaluationException {
		Object result = null;
		if (expression != null && expression.getAst() != null) {
			Expression ast = expression.getAst();
			AstEvaluator evaluator = new AstEvaluator(queryEnvironment, log);
			result = evaluator.eval(environment, ast);
			if (result instanceof Nothing) {
				result = null;
			}
		}
		return result;
	}

	@Override
	public void setDoLog(boolean doLog) {
		this.log = doLog;
	}

}
