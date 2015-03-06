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
package org.eclipse.acceleo.query.services;

/**
 * Services on {@link Number}.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class NumberServices {

	/**
	 * Can't divide by zero message.
	 */
	private static final String CAN_T_DIVIDE_BY_ZERO = "Can't divide by zero.";

	/**
	 * Performs the negation of the specified argument.
	 * 
	 * @param value
	 *            the argument to be negated.
	 * @return the negation of the argument.
	 */
	public Integer unaryMin(Integer value) {
		return Integer.valueOf(-value.intValue());
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

	/**
	 * Compares two integers : returns <code>true</code> if a<b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a<b
	 */
	public Boolean lessThan(Integer a, Integer b) {
		return Boolean.valueOf(a.doubleValue() < b.doubleValue());
	}

	/**
	 * Compares two integers : returns <code>true</code> if a<=b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a<=b
	 */
	public Boolean lessThanEqual(Integer a, Integer b) {
		return Boolean.valueOf(a.intValue() <= b.intValue());
	}

	/**
	 * Compares two integers : returns <code>true</code> if a>b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a>b
	 */

	public Boolean greaterThan(Integer a, Integer b) {
		return Boolean.valueOf(a.intValue() > b.intValue());
	}

	/**
	 * Compares two integers : returns <code>true</code> if a>=b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a>=b
	 */

	public Boolean greaterThanEqual(Integer a, Integer b) {
		return Boolean.valueOf(a.intValue() >= b.intValue());
	}

	/**
	 * Compares two integers : returns <code>true</code> if a==b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a==b
	 */

	public Boolean equals(Integer a, Integer b) {
		final Boolean result;

		if (a == null) {
			result = Boolean.valueOf(b == null);
		} else {
			result = Boolean.valueOf(a.equals(b));
		}

		return result;
	}

	/**
	 * Compares two integers : returns <code>true</code> if a!=b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a!=b
	 */

	public Boolean differs(Integer a, Integer b) {
		return Boolean.valueOf(!equals(a, b));
	}

	/**
	 * Compares two doubles : returns <code>true</code> if a<b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a<b
	 */

	public Boolean lessThan(Double a, Double b) {
		return Boolean.valueOf(a.doubleValue() < b.doubleValue());
	}

	/**
	 * Compares two doubles : returns <code>true</code> if a<b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a<b
	 */

	public Boolean lessThanEqual(Double a, Double b) {
		return Boolean.valueOf(a.doubleValue() <= b.doubleValue());
	}

	/**
	 * Compares two doubles : returns <code>true</code> if a>b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a>b
	 */

	public Boolean greaterThan(Double a, Double b) {
		return Boolean.valueOf(a.doubleValue() > b.doubleValue());
	}

	/**
	 * Compares two doubles : returns <code>true</code> if a>=b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a>=b
	 */

	public Boolean greaterThanEqual(Double a, Double b) {
		return Boolean.valueOf(a.doubleValue() >= b.doubleValue());
	}

	/**
	 * Compares two doubles : returns <code>true</code> if a==b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a==b
	 */

	public Boolean equals(Double a, Double b) {
		final Boolean result;

		if (a == null) {
			result = Boolean.valueOf(b == null);
		} else {
			result = Boolean.valueOf(a.equals(b));
		}

		return result;
	}

	/**
	 * Compares two doubles : returns <code>true</code> if a!=b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a!=b
	 */

	public Boolean differs(Double a, Double b) {
		return Boolean.valueOf(!equals(a, b));
	}

	/**
	 * negates a double value..
	 * 
	 * @param value
	 *            the operand.
	 * @return the negation of the value.
	 */
	public Double unaryMin(Double value) {
		return Double.valueOf(-value.doubleValue());
	}

	/**
	 * Returns the absolute value of self, self if it is already a positive number.
	 * 
	 * @param self
	 *            the current value
	 * @return the absolute value of self, self if it is already a positive number
	 */
	public Double abs(Double self) {
		return Double.valueOf(Math.abs(self.doubleValue()));
	}

	/**
	 * Returns the absolute value of self, self if it is already a positive number.
	 * 
	 * @param self
	 *            the current value
	 * @return the absolute value of self, self if it is already a positive number
	 */
	public Integer abs(Integer self) {
		return Integer.valueOf(Math.abs(self.intValue()));
	}

	/**
	 * Returns the integer part of self.
	 * 
	 * @param self
	 *            the current value
	 * @return the integer part of self
	 */
	public Integer floor(Double self) {
		return Integer.valueOf((int)Math.floor(self.doubleValue()));
	}

	/**
	 * Returns self.
	 * 
	 * @param self
	 *            the current value
	 * @return self
	 */
	public Integer floor(Integer self) {
		return self;
	}

	/**
	 * Returns the greatest number between self and r.
	 * 
	 * @param self
	 *            the current value
	 * @param r
	 *            the other value
	 * @return the greatest number between self and r.
	 */
	public Integer max(Integer self, Integer r) {
		return Integer.valueOf(Math.max(self.intValue(), r.intValue()));
	}

	/**
	 * Returns the greatest number between self and r.
	 * 
	 * @param self
	 *            the current value
	 * @param r
	 *            the other value
	 * @return the greatest number between self and r.
	 */
	public Double max(Double self, Double r) {
		return Double.valueOf(Math.max(self.doubleValue(), r.doubleValue()));
	}

	/**
	 * Returns the lowest number between self and r.
	 * 
	 * @param self
	 *            the current value
	 * @param r
	 *            the other value
	 * @return the greatest number between self and r.
	 */
	public Integer min(Integer self, Integer r) {
		return Integer.valueOf(Math.min(self.intValue(), r.intValue()));
	}

	/**
	 * Returns the lowest number between self and r.
	 * 
	 * @param self
	 *            the current value
	 * @param r
	 *            the other value
	 * @return the greatest number between self and r.
	 */
	public Double min(Double self, Double r) {
		return Double.valueOf(Math.min(self.doubleValue(), r.doubleValue()));
	}

	/**
	 * Returns the nearest integer to self.
	 * 
	 * @param self
	 *            the current value
	 * @return the nearest integer to self
	 */
	public Integer round(Double self) {
		return Integer.valueOf((int)Math.round(self.doubleValue()));
	}

	/**
	 * Returns self.
	 * 
	 * @param self
	 *            the current value
	 * @return self
	 */
	public Integer round(Integer self) {
		return self;
	}

	/**
	 * Returns the integer quotient of the division of self by i.
	 * 
	 * @param self
	 *            the current value
	 * @param i
	 *            the divider
	 * @return the integer quotient of the division of self by i
	 */
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

	/**
	 * Returns the integer quotient of the division of self by i.
	 * 
	 * @param self
	 *            the current value
	 * @param i
	 *            the divider
	 * @return the integer quotient of the division of self by i
	 */
	public Integer div(Integer self, Integer i) {
		// see comment in #div(Double, Double) for this test
		if (i.equals(Integer.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Integer.valueOf((int)(self.intValue() / i.intValue()));
	}

	/**
	 * Returns the integer remainder of the division of self by i.
	 * 
	 * @param self
	 *            the current value
	 * @param i
	 *            the divider
	 * @return the integer remainder of the division of self by i
	 */
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

	/**
	 * Returns the integer remainder of the division of self by i.
	 * 
	 * @param self
	 *            the current value
	 * @param i
	 *            the divider
	 * @return the integer remainder of the division of self by i
	 */
	public Integer mod(Integer self, Integer i) {
		// see comment in #mod(Double, Double) for this test
		if (i.equals(Integer.valueOf(0))) {
			throw new IllegalArgumentException(CAN_T_DIVIDE_BY_ZERO);
		}
		return Integer.valueOf((int)(self.intValue() % i.intValue()));
	}

}
