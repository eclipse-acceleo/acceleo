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

import com.google.common.collect.Maps;

import java.util.Map;

import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.parser.AstEvaluator;

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
	private AstEvaluator evaluator;

	/**
	 * The ast used to represent the lambda.
	 */
	private Lambda lambdaLiteral;

	/**
	 * Creates a new {@link LambdaValue} instance.
	 * 
	 * @param literal
	 *            the lambda literal this is a value of.
	 * @param envEvaluator
	 *            the evaluator capturing the environment.
	 */
	public LambdaValue(Lambda literal, AstEvaluator envEvaluator) {
		this.evaluator = envEvaluator;
		this.lambdaLiteral = literal;
	}

	/**
	 * Returns the value obtained by evaluating the lambda on the specified arguments.
	 * 
	 * @param args
	 *            the call arguments.
	 * @return the result of calling the lambda on the specified argument.
	 */
	public Object eval(Object[] args) {
		Map<String, Object> variables = Maps.newHashMap();
		int argc = args.length;
		for (int i = 0; i < argc; i++) {
			variables.put(lambdaLiteral.getParameters().get(i).getName(), args[i]);
		}
		return evaluator.eval(variables, lambdaLiteral.getExpression());
	}

}
