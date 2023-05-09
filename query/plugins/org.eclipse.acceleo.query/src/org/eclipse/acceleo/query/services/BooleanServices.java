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
package org.eclipse.acceleo.query.services;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;

//@formatter:off
@ServiceProvider(
	value = "Services available for Booleans"
)
//@formatter:on
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class BooleanServices {

	// @formatter:off
	@Documentation(
		value = "Logical or.",
		params = {
			@Param(name = "op1", value = "The first operand"),
			@Param(name = "op2", value = "The second operand")
		},
		result = "The logical or of the two operands",
		examples = {
			@Example(expression = "true or false", result = "true"),
			@Example(expression = "false or true", result = "true"),
			@Example(expression = "true or true", result = "true"),
			@Example(expression = "false or false", result = "false")
		}
	)
	// @formatter:on
	public Boolean or(Boolean op1, Boolean op2) {
		return Boolean.valueOf(op1.booleanValue() || op2.booleanValue());
	}

	// @formatter:off
	@Documentation(
		value = "Logical and.",
		params = {
			@Param(name = "op1", value = "The first operand"),
			@Param(name = "op2", value = "The second operand")
		},
		result = "The logical and of the two operands",
		examples = {
			@Example(expression = "true and false", result = "false"),
			@Example(expression = "false and true", result = "false"),
			@Example(expression = "true and true", result = "true"),
			@Example(expression = "false and false", result = "false")
		}
	)
	// @formatter:on
	public Boolean and(Boolean op1, Boolean op2) {
		return Boolean.valueOf(op1.booleanValue() && op2.booleanValue());
	}

	// @formatter:off
	@Documentation(
		value = "Logical negation.",
		params = {
			@Param(name = "op1", value = "The operand")
		},
		result = "The logical negation of the operand",
		examples = {
			@Example(expression = "not true", result = "false"),
			@Example(expression = "not false", result = "true")
		}
	)
	// @formatter:on
	public Boolean not(Boolean op1) {
		return Boolean.valueOf(!op1.booleanValue());
	}

	/**
	 * Logical implies.
	 * 
	 * @param op1
	 *            the first operand
	 * @param op2
	 *            the second operand
	 * @return the logical implies of the two operands
	 */
	// @formatter:off
	@Documentation(
		value = "Logical implies.",
		params = {
			@Param(name = "op1", value = "The first operand"),
			@Param(name = "op2", value = "The second operand"),
		},
		result = "The logical implies of the operands",
		examples = {
			@Example(expression = "true implies true", result = "true"),
			@Example(expression = "true implies false", result = "false"),
			@Example(expression = "false implies true", result = "true"),
			@Example(expression = "false implies false", result = "true"),
		}
	)
	// @formatter:on
	public Boolean implies(Boolean op1, Boolean op2) {
		final Boolean result;

		if (!op1.booleanValue()) {
			result = Boolean.TRUE;
		} else {
			result = op2;
		}

		return result;
	}

	// @formatter:off
	@Documentation(
		value = "Logical xor.",
		params = {
			@Param(name = "op1", value = "The first operand"),
			@Param(name = "op2", value = "The second operand"),
		},
		result = "The logical xor of the operands",
		examples = {
			@Example(expression = "true xor true", result = "false"),
			@Example(expression = "true xor false", result = "true"),
			@Example(expression = "false xor true", result = "true"),
			@Example(expression = "false xor false", result = "false"),
		}
	)
	// @formatter:on
	public Boolean xor(Boolean op1, Boolean op2) {
		return Boolean.valueOf(op1.booleanValue() ^ op2.booleanValue());
	}

}
