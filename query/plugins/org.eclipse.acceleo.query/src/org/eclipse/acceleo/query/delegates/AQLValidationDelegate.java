/*******************************************************************************
 * Copyright (c) 2016, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.delegates;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.acceleo.query.runtime.QueryParsing;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EValidator.ValidationDelegate;

/**
 * A validation delegate supporting AQL.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLValidationDelegate extends AbstractEnvironmentProvider implements ValidationDelegate {

	/**
	 * The self variable name.
	 */
	private static final String SELF = "self";

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator.ValidationDelegate#validate(org.eclipse.emf.ecore.EClass,
	 *      org.eclipse.emf.ecore.EObject, java.util.Map, org.eclipse.emf.ecore.EOperation, java.lang.String)
	 */
	@Override
	public boolean validate(EClass eClass, EObject eObject, Map<Object, Object> context, EOperation invariant,
			String expression) {
		return Boolean.TRUE.equals(evaluate(eObject, expression));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator.ValidationDelegate#validate(org.eclipse.emf.ecore.EClass,
	 *      org.eclipse.emf.ecore.EObject, java.util.Map, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean validate(EClass eClass, EObject eObject, Map<Object, Object> context, String constraint,
			String expression) {
		return Boolean.TRUE.equals(evaluate(eObject, expression));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.ecore.EValidator.ValidationDelegate#validate(org.eclipse.emf.ecore.EDataType,
	 *      java.lang.Object, java.util.Map, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean validate(EDataType eDataType, Object value, Map<Object, Object> context, String constraint,
			String expression) {
		return Boolean.TRUE.equals(evaluate(value, expression));
	}

	/**
	 * Evaluates the given AQL expression on the given self {@link Object}.
	 * 
	 * @param self
	 *            the self {@link Object}
	 * @param expression
	 *            the AQL expression
	 * @return the evaluated value
	 */
	private Object evaluate(Object self, String expression) {
		final IQueryEnvironment environment = getEnvironment();

		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(SELF, self);

		final IQueryBuilderEngine builderEngine = QueryParsing.newBuilder();
		final AstResult astResult = builderEngine.build(expression);

		if (astResult.getDiagnostic().getSeverity() == Diagnostic.OK) {
			final IQueryEvaluationEngine evaluationEngine = QueryEvaluation.newEngine(environment);
			final EvaluationResult evaluationResult = evaluationEngine.eval(astResult, variables);

			if (evaluationResult.getDiagnostic().getSeverity() != Diagnostic.OK) {
				final StringBuilder messages = new StringBuilder();
				for (Diagnostic child : evaluationResult.getDiagnostic().getChildren()) {
					messages.append("\n" + child.getMessage());
				}
				throw new IllegalArgumentException("Unable to evaluate \"" + expression + "\"" + messages
						.toString());
			}
			return evaluationResult.getResult();
		} else {
			final StringBuilder messages = new StringBuilder();
			for (Diagnostic child : astResult.getDiagnostic().getChildren()) {
				messages.append("\n" + child.getMessage());
			}
			throw new IllegalArgumentException("Unable to parse \"" + expression + "\"" + messages
					.toString());
		}

	}
}
