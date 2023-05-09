/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.delegates;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.util.QueryDelegate;

/**
 * A query delegate supporting AQL.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLQueryDelegate implements QueryDelegate {

	/**
	 * The {@link IQueryEvaluationEngine}.
	 */
	private final IQueryEvaluationEngine engine;

	/**
	 * The {@link AstResult}.
	 */
	private final AstResult astResult;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQueryEnvironment}
	 * @param astResult
	 *            the {@link AstResult}
	 */
	public AQLQueryDelegate(IQueryEnvironment queryEnvironment, AstResult astResult) {
		engine = QueryEvaluation.newEngine(queryEnvironment);
		this.astResult = astResult;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.util.QueryDelegate#prepare()
	 */
	@Override
	public void prepare() throws InvocationTargetException {
		// nothing to do here
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.util.QueryDelegate#execute(java.lang.Object, java.util.Map)
	 */
	@Override
	public Object execute(Object target, Map<String, ?> arguments) throws InvocationTargetException {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", target);
		variables.putAll(arguments);

		final EvaluationResult evaluationResult;
		try {
			evaluationResult = engine.eval(astResult, variables);
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new InvocationTargetException(e);
		}
		if (evaluationResult.getDiagnostic().getSeverity() == Diagnostic.ERROR) {
			throw new InvocationTargetException(new AcceleoQueryEvaluationException(evaluationResult
					.getDiagnostic().getMessage()));
		}

		return evaluationResult.getResult();
	}

}
