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
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate;
import org.eclipse.emf.ecore.InternalEObject;

/**
 * An invocation delegate supporting AQL.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLInvocationDelegate implements InvocationDelegate {

	/**
	 * The {@link IQueryEvaluationEngine}.
	 */
	private final IQueryEvaluationEngine engine;

	/**
	 * The {@link AstResult}.
	 */
	private final AstResult astResult;

	/**
	 * The {@link List} of {@link org.eclipse.emf.ecore.EParameter#getName() eParameter name}.
	 */
	private final List<String> parameterNames;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQueryEnvironment}
	 * @param astResult
	 *            the {@link AstResult}
	 * @param parameterNames
	 *            the {@link List} of {@link org.eclipse.emf.ecore.EParameter#getName() eParameter name}
	 */
	public AQLInvocationDelegate(IQueryEnvironment queryEnvironment, AstResult astResult,
			List<String> parameterNames) {
		engine = QueryEvaluation.newEngine(queryEnvironment);
		this.astResult = astResult;
		this.parameterNames = parameterNames;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EOperation.Internal.InvocationDelegate#dynamicInvoke(org.eclipse.emf.ecore.InternalEObject,
	 *      org.eclipse.emf.common.util.EList)
	 */
	@Override
	public Object dynamicInvoke(InternalEObject target, EList<?> arguments) throws InvocationTargetException {
		if (arguments.size() != parameterNames.size()) {
			throw new IllegalArgumentException("number of arguments mismatch " + arguments.size()
					+ " instead of " + parameterNames.size());
		}

		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", target);
		for (int index = 0; index < parameterNames.size(); index++) {
			variables.put(parameterNames.get(index), arguments.get(index));
		}

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
