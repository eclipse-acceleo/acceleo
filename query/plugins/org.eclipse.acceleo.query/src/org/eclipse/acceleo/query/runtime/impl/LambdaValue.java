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
package org.eclipse.acceleo.query.runtime.impl;

import java.util.Map;

import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;

/**
 * Values of type Lambda must be represented by a Java Object other than the Ast.
 * 
 * @author Romain Guider
 */
public class LambdaValue {

	/**
	 * The evaluator used to get a value out of the lambda. The evaluator captures the environment in which
	 * the lambda has been created.
	 */
	private final AstEvaluator evaluator;

	/**
	 * The ast used to represent the lambda.
	 */
	private final Lambda lambdaLiteral;

	/**
	 * Environment variables value.
	 */
	private final Map<String, Object> variables;

	/**
	 * The {@link Diagnostic} to {@link LambdaValue#logException(Exception) log} evaluation {@link Exception}.
	 */
	private final Diagnostic diagnostic;

	/**
	 * Creates a new {@link LambdaValue} instance.
	 * 
	 * @param literal
	 *            the lambda literal this is a value of
	 * @param variables
	 *            environment variable
	 * @param envEvaluator
	 *            the evaluator capturing the environment
	 * @param diagnostic
	 *            the {@link Diagnostic} to {@link LambdaValue#logException(Exception) log} evaluation
	 *            {@link Exception}
	 */
	public LambdaValue(Lambda literal, Map<String, Object> variables, AstEvaluator envEvaluator,
			Diagnostic diagnostic) {
		this.evaluator = envEvaluator;
		this.lambdaLiteral = literal;
		this.variables = variables;
		this.diagnostic = diagnostic;
	}

	/**
	 * Returns the value obtained by evaluating the lambda on the specified arguments.
	 * 
	 * @param args
	 *            the call arguments
	 * @return the result of calling the lambda on the specified argument
	 */
	public Object eval(Object[] args) {
		int argc = lambdaLiteral.getParameters().size();
		for (int i = 0; i < argc; i++) {
			variables.put(lambdaLiteral.getParameters().get(i).getName(), args[i]);
		}
		final EvaluationResult evalResult = evaluator.eval(variables, lambdaLiteral.getExpression());
		return evalResult.getResult();
	}

	/**
	 * Logs the given {@link Exception}.
	 * 
	 * @param severity
	 *            {@link Diagnostic#INFO}, {@link Diagnostic#WARNING}, or {@link Diagnostic#ERROR}
	 * @param e
	 *            the {@link Exception} to log
	 */
	public void logException(int severity, Exception e) {
		if (diagnostic instanceof DiagnosticChain) {
			Diagnostic child = new BasicDiagnostic(severity, AstBuilderListener.PLUGIN_ID, 0, e.getMessage(),
					new Object[] {lambdaLiteral });
			((DiagnosticChain)diagnostic).add(child);
		}
	}

}
