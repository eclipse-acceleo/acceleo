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

/**
 * Services on {@link Number}.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
@SuppressWarnings({"checkstyle:javadocmethod", "checkstyle:javadoctype" })
public class NumberServices {

	/**
	 * Can't divide by zero message.
	 */
	private static final String CAN_T_DIVIDE_BY_ZERO = "Can't divide by zero.";

	// @formatter:off
	@Documentation(
		value = "Performs the negation of the specified argument.",
		params = {
			@Param(name = "self", value = "The argument to be negated.")
		},
		result = "The negation of the argument.",
		examples = {
			@Example(expression = "1.unaryMin()", result = "-1"),
			@Example(expression = "-1.unaryMin()", result = "1")
		},
		comment = "You can use \"-expression\" for the same result."
	)
	// @formatter:on
	public Integer unaryMin(Integer self) {
		return Integer.valueOf(-self.intValue());
	}

	/**
	 * Performs the addition of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the addition of the arguments.
	 */
	public Integer add(Integer a, Integer b) {
		return Integer.valueOf(a.intValue() + b.intValue());
	}

	/**
	 * Performs the substraction of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the substraction of the arguments.
	 */
	public Integer sub(Integer a, Integer b) {
		return Integer.valueOf(a.intValue() - b.intValue());
	}

	/**
	 * Performs the multiplication of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the multiplication of the arguments.
	 */

	public Integer mult(Integer a, Integer b) {
		return Integer.valueOf(a.intValue() * b.intValue());
	}

	/**
	 * Performs the division of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the division of the arguments.
	 */

	public Integer divOp(Integer a, Integer b) {
		return Integer.valueOf(a.intValue() / b.intValue());
	}

	/**
	 * Performs the addition of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the addition of the arguments.
	 */

	public Double add(Double a, Double b) {
		return Double.valueOf(a.doubleValue() + b.doubleValue());
	}

	/**
	 * Performs the substraction of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the substraction of the arguments.
	 */

	public Double sub(Double a, Double b) {
		return Double.valueOf(a.doubleValue() - b.doubleValue());
	}

	/**
	 * Performs the multiplication of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the multiplication of the arguments.
	 */

	public Double mult(Double a, Double b) {
		return Double.valueOf(a.doubleValue() * b.doubleValue());
	}

	/**
	 * Performs the division of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the division of the arguments.
	 */
	public Double divOp(Double a, Double b) {
		return Double.valueOf(a.doubleValue() / b.doubleValue());
	}

	// @formatter:off
	@Documentation(
		value = "Performs the negation of the specified argument.",
		params = {
			@Param(name = "self", value = "The argument to be negated.")
		},
		result = "The negation of the argument.",
		examples = {
			@Example(expression = "3.14.unaryMin()", result = "-3.14"),
			@Example(expression = "-3.14.unaryMin()", result = "3.14")
		},
		comment = "You can use \"-expression\" for the same result."
	)
	// @formatter:on
	public Double unaryMin(Double value) {
		return Double.valueOf(-value.doubleValue());
	}

	// @formatter:off
	@Documentation(
		value = "Returns the absolute value of self, self if it is already a positive number.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "The absolute value of self, self if it is already a positive number",
		examples = {
			@Example(expression = "-3.14.abs()", result = "3.14"),
			@Example(expression = "3.14.abs()", result = "3.14")
		}
	)
	// @formatter:on
	public Double abs(Double self) {
		return Double.valueOf(Math.abs(self.doubleValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the absolute value of self, self if it is already a positive number.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "The absolute value of self, self if it is already a positive number",
		examples = {
			@Example(expression = "-3.abs()", result = "3"),
			@Example(expression = "3.abs()", result = "3")
		}
	)
	// @formatter:on
	public Integer abs(Integer self) {
		return Integer.valueOf(Math.abs(self.intValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the integer part of self.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "The integer part of self.",
		examples = {
			@Example(expression = "3.14.floor()", result = "3"),
			@Example(expression = "3.66.floor()", result = "3")
		}
	)
	// @formatter:on
	public Integer floor(Double self) {
		return Integer.valueOf((int)Math.floor(self.doubleValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns self.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "Self.",
		examples = {
			@Example(expression = "3.floor()", result = "3")
		}
	)
	// @formatter:on
	public Integer floor(Integer self) {
		return self;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the greatest number between self and r.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "r", value = "The other value.")
		},
		result = "The greatest number between self and r.",
		examples = {
			@Example(expression = "3.max(6)", result = "6"),
			@Example(expression = "6.max(3)", result = "6")
		}
	)
	// @formatter:on
	public Integer max(Integer self, Integer r) {
		return Integer.valueOf(Math.max(self.intValue(), r.intValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the greatest number between self and r.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "r", value = "The other value.")
		},
		result = "The greatest number between self and r.",
		examples = {
			@Example(expression = "3.14.max(6.7)", result = "6.7"),
			@Example(expression = "6.7.max(3.14)", result = "6.7")
		}
	)
	// @formatter:on
	public Double max(Double self, Double r) {
		return Double.valueOf(Math.max(self.doubleValue(), r.doubleValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the lowest number between self and r.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "r", value = "The other value.")
		},
		result = "The lowest number between self and r.",
		examples = {
			@Example(expression = "3.min(6)", result = "3"),
			@Example(expression = "6.min(3)", result = "3")
		}
	)
	// @formatter:on
	public Integer min(Integer self, Integer r) {
		return Integer.valueOf(Math.min(self.intValue(), r.intValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the lowest number between self and r.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "r", value = "The other value.")
		},
		result = "The lowest number between self and r.",
		examples = {
			@Example(expression = "3.14.min(6.7)", result = "3.14"),
			@Example(expression = "6.7.min(3.14)", result = "3.14")
		}
	)
	// @formatter:on
	public Double min(Double self, Double r) {
		return Double.valueOf(Math.min(self.doubleValue(), r.doubleValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the nearest integer to self.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "The nearest integer to self.",
		examples = {
			@Example(expression = "3.14.round()", result = "3"),
			@Example(expression = "3.66.round()", result = "4")
		}
	)
	// @formatter:on
	public Integer round(Double self) {
		return Integer.valueOf((int)Math.round(self.doubleValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns self.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "Self.",
		examples = {
			@Example(expression = "3.round()", result = "3")
		}
	)
	// @formatter:on
	public Integer round(Integer self) {
		return self;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the integer quotient of the division of self by i.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "i", value = "The divider.")
		},
		result = "The integer quotient of the division of self by i.",
		examples = {
			@Example(expression = "6.9.div(3.1)", result = "2")
		}
	)
	// @formatter:on
	public Integer div(Double self, Double i) {
		/*
		 * 0d/0d doesn't fail in ArithmeticsException but rather returns Double#POSITIVE_INFINITY. We want the
		 * same failure for both operations, hence the explicit test.
		 */
		if (i.equals(Double.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Integer.valueOf((int)(self.doubleValue() / i.doubleValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the integer quotient of the division of self by i.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "i", value = "The divider.")
		},
		result = "The integer quotient of the division of self by i.",
		examples = {
			@Example(expression = "7.div(3)", result = "2")
		}
	)
	// @formatter:on
	public Integer div(Integer self, Integer i) {
		// see comment in #div(Double, Double) for this test
		if (i.equals(Integer.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Integer.valueOf((int)(self.intValue() / i.intValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the integer remainder of the division of self by i.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "i", value = "The divider.")
		},
		result = "The integer remainder of the division of self by i.",
		examples = {
			@Example(expression = "7.5.div(3.1)", result = "1")
		}
	)
	// @formatter:on
	public Integer mod(Double self, Double i) {
		/*
		 * As with division, mod operation will not fail in exception when using zero as divisor, but rather
		 * return Double#NaN. We want this operation to fail as does its version with Integer, hence the
		 * explicit test.
		 */
		if (i.equals(Double.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Integer.valueOf((int)Math.ceil(self.doubleValue() % i.doubleValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the integer remainder of the division of self by i.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "i", value = "The divider.")
		},
		result = "The integer remainder of the division of self by i.",
		examples = {
			@Example(expression = "7.div(3)", result = "1")
		}
	)
	// @formatter:on
	public Integer mod(Integer self, Integer i) {
		// see comment in #mod(Double, Double) for this test
		if (i.equals(Integer.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Integer.valueOf((int)(self.intValue() % i.intValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Performs the negation of the specified argument.",
		params = {
			@Param(name = "self", value = "The argument to be negated.")
		},
		result = "The negation of the argument.",
		examples = {
			@Example(expression = "1.unaryMin()", result = "-1"),
			@Example(expression = "-1.unaryMin()", result = "1")
		},
		comment = "You can use \"-expression\" for the same result."
	)
	// @formatter:on
	public Long unaryMin(Long self) {
		return Long.valueOf(-self.longValue());
	}

	/**
	 * Performs the addition of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the addition of the arguments.
	 */
	public Long add(Long a, Long b) {
		return Long.valueOf(a.longValue() + b.longValue());
	}

	/**
	 * Performs the substraction of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the substraction of the arguments.
	 */
	public Long sub(Long a, Long b) {
		return Long.valueOf(a.longValue() - b.longValue());
	}

	/**
	 * Performs the multiplication of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the multiplication of the arguments.
	 */

	public Long mult(Long a, Long b) {
		return Long.valueOf(a.longValue() * b.longValue());
	}

	/**
	 * Performs the division of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the division of the arguments.
	 */

	public Long divOp(Long a, Long b) {
		return Long.valueOf(a.longValue() / b.longValue());
	}

	/**
	 * Performs the addition of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the addition of the arguments.
	 */

	public Float add(Float a, Float b) {
		return Float.valueOf(a.floatValue() + b.floatValue());
	}

	/**
	 * Performs the substraction of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the substraction of the arguments.
	 */

	public Float sub(Float a, Float b) {
		return Float.valueOf(a.floatValue() - b.floatValue());
	}

	/**
	 * Performs the multiplication of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the multiplication of the arguments.
	 */

	public Float mult(Float a, Float b) {
		return Float.valueOf(a.floatValue() * b.floatValue());
	}

	/**
	 * Performs the division of the specified arguments.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return the division of the arguments.
	 */
	public Float divOp(Float a, Float b) {
		return Float.valueOf(a.floatValue() / b.floatValue());
	}

	// @formatter:off
	@Documentation(
		value = "Performs the negation of the specified argument.",
		params = {
			@Param(name = "self", value = "The argument to be negated.")
		},
		result = "The negation of the argument.",
		examples = {
			@Example(expression = "3.14.unaryMin()", result = "-3.14"),
			@Example(expression = "-3.14.unaryMin()", result = "3.14")
		},
		comment = "You can use \"-expression\" for the same result."
	)
	// @formatter:on
	public Float unaryMin(Float value) {
		return Float.valueOf(-value.floatValue());
	}

	// @formatter:off
	@Documentation(
		value = "Returns the absolute value of self, self if it is already a positive number.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "The absolute value of self, self if it is already a positive number",
		examples = {
			@Example(expression = "-3.14.abs()", result = "3.14"),
			@Example(expression = "3.14.abs()", result = "3.14")
		}
	)
	// @formatter:on
	public Float abs(Float self) {
		return Float.valueOf(Math.abs(self.floatValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the absolute value of self, self if it is already a positive number.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "The absolute value of self, self if it is already a positive number",
		examples = {
			@Example(expression = "-3.abs()", result = "3"),
			@Example(expression = "3.abs()", result = "3")
		}
	)
	// @formatter:on
	public Long abs(Long self) {
		return Long.valueOf(Math.abs(self.longValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the integer part of self.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "The integer part of self.",
		examples = {
			@Example(expression = "3.14.floor()", result = "3"),
			@Example(expression = "3.66.floor()", result = "3")
		}
	)
	// @formatter:on
	public Integer floor(Float self) {
		return Integer.valueOf((int)Math.floor(self.floatValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns self.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "Self.",
		examples = {
			@Example(expression = "3.floor()", result = "3")
		}
	)
	// @formatter:on
	public Long floor(Long self) {
		return self;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the greatest number between self and r.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "r", value = "The other value.")
		},
		result = "The greatest number between self and r.",
		examples = {
			@Example(expression = "3.max(6)", result = "6"),
			@Example(expression = "6.max(3)", result = "6")
		}
	)
	// @formatter:on
	public Long max(Long self, Long r) {
		return Long.valueOf(Math.max(self.longValue(), r.longValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the greatest number between self and r.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "r", value = "The other value.")
		},
		result = "The greatest number between self and r.",
		examples = {
			@Example(expression = "3.14.max(6.7)", result = "6.7"),
			@Example(expression = "6.7.max(3.14)", result = "6.7")
		}
	)
	// @formatter:on
	public Float max(Float self, Float r) {
		return Float.valueOf(Math.max(self.floatValue(), r.floatValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the lowest number between self and r.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "r", value = "The other value.")
		},
		result = "The lowest number between self and r.",
		examples = {
			@Example(expression = "3.min(6)", result = "3"),
			@Example(expression = "6.min(3)", result = "3")
		}
	)
	// @formatter:on
	public Long min(Long self, Long r) {
		return Long.valueOf(Math.min(self.longValue(), r.longValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the lowest number between self and r.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "r", value = "The other value.")
		},
		result = "The lowest number between self and r.",
		examples = {
			@Example(expression = "3.14.min(6.7)", result = "3.14"),
			@Example(expression = "6.7.min(3.14)", result = "3.14")
		}
	)
	// @formatter:on
	public Float min(Float self, Float r) {
		return Float.valueOf(Math.min(self.floatValue(), r.floatValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the nearest integer to self.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "The nearest integer to self.",
		examples = {
			@Example(expression = "3.14.round()", result = "3"),
			@Example(expression = "3.66.round()", result = "4")
		}
	)
	// @formatter:on
	public Integer round(Float self) {
		return Integer.valueOf((int)Math.round(self.floatValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns self.",
		params = {
			@Param(name = "self", value = "The current value.")
		},
		result = "Self.",
		examples = {
			@Example(expression = "3.round()", result = "3")
		}
	)
	// @formatter:on
	public Long round(Long self) {
		return self;
	}

	// @formatter:off
	@Documentation(
		value = "Returns the long quotient of the division of self by i.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "i", value = "The divider.")
		},
		result = "The integer quotient of the division of self by i.",
		examples = {
			@Example(expression = "6.9.div(3.1)", result = "2")
		}
	)
	// @formatter:on
	public Integer div(Float self, Float i) {
		/*
		 * 0d/0d doesn't fail in ArithmeticsException but rather returns Float#POSITIVE_INFINITY. We want the
		 * same failure for both operations, hence the explicit test.
		 */
		if (i.equals(Float.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Integer.valueOf((int)(self.floatValue() / i.floatValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the long quotient of the division of self by i.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "i", value = "The divider.")
		},
		result = "The long quotient of the division of self by i.",
		examples = {
			@Example(expression = "7.div(3)", result = "2")
		}
	)
	// @formatter:on
	public Long div(Long self, Long i) {
		// see comment in #div(Float, Float) for this test
		if (i.equals(Long.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Long.valueOf((long)(self.longValue() / i.longValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the integer remainder of the division of self by i.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "i", value = "The divider.")
		},
		result = "The integer remainder of the division of self by i.",
		examples = {
			@Example(expression = "7.5.div(3.1)", result = "1")
		}
	)
	// @formatter:on
	public Integer mod(Float self, Float i) {
		/*
		 * As with division, mod operation will not fail in exception when using zero as divisor, but rather
		 * return Float#NaN. We want this operation to fail as does its version with Long, hence the explicit
		 * test.
		 */
		if (i.equals(Float.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Integer.valueOf((int)Math.ceil(self.floatValue() % i.floatValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the long remainder of the division of self by i.",
		params = {
				@Param(name = "self", value = "The current value."),
				@Param(name = "i", value = "The divider.")
		},
		result = "The long remainder of the division of self by i.",
		examples = {
			@Example(expression = "7.div(3)", result = "1")
		}
	)
	// @formatter:on
	public Long mod(Long self, Long i) {
		// see comment in #mod(Float, Float) for this test
		if (i.equals(Long.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Long.valueOf((long)(self.longValue() % i.longValue()));
	}

	// @formatter:off
	@Documentation(
		value = "Returns the double value of the given double.",
		params = {
				@Param(name = "self", value = "The current value."),
		},
		result = "The double value.",
		examples = {
			@Example(expression = "1.0.toDouble()", result = "1.0")
		}
	)
	// @formatter:on
	public Double toDouble(Double self) {
		return Double.valueOf(self);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the double value of the given integer.",
		params = {
				@Param(name = "self", value = "The current value."),
		},
		result = "The double value.",
		examples = {
			@Example(expression = "1.toDouble()", result = "1.0")
		}
	)
	// @formatter:on
	public Double toDouble(Integer self) {
		return Double.valueOf(self.intValue());
	}

	// @formatter:off
	@Documentation(
		value = "Returns the double value of the given float.",
		params = {
				@Param(name = "self", value = "The current value."),
		},
		result = "The double value.",
		examples = {
			@Example(expression = "1.0.toDouble()", result = "1.0")
		}
	)
	// @formatter:on
	public Double toDouble(Float self) {
		return Double.valueOf(self.floatValue());
	}

	// @formatter:off
	@Documentation(
		value = "Returns the long value of the given long.",
		params = {
				@Param(name = "self", value = "The current value."),
		},
		result = "The long value.",
		examples = {
			@Example(expression = "1.toLong()", result = "1")
		}
	)
	// @formatter:on
	public Long toLong(Long self) {
		return Long.valueOf(self);
	}

	// @formatter:off
	@Documentation(
		value = "Returns the long value of the given integer.",
		params = {
				@Param(name = "self", value = "The current value."),
		},
		result = "The long value.",
		examples = {
			@Example(expression = "1.toLong()", result = "1")
		}
	)
	// @formatter:on
	public Long toLong(Integer self) {
		return Long.valueOf(self.intValue());
	}

}
