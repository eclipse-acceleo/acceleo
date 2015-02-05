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
	 * Performs the negation of the specified argument.
	 * 
	 * @param value
	 *            the argument to be negated.
	 * @return the negation of the argument.
	 */
	public Integer unaryMin(Integer value) {
		return -value;
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
		return a + b;
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
		return a - b;
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
		return a * b;
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

	public Integer div(Integer a, Integer b) {
		return a / b;
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
		return a + b;
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
		return a - b;
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
		return a * b;
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

	public Double div(Double a, Double b) {
		return a / b;
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
		return a < b;
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
		return a <= b;
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
		return a > b;
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
		return a >= b;
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
		final boolean result;

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
		return !equals(a, b);
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
		return a < b;
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
		return a <= b;
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
		return a > b;
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
		return a >= b;
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
		return !equals(a, b);
	}

	/**
	 * negates a double value..
	 * 
	 * @param value
	 *            the operand.
	 * @return the negation of the value.
	 */
	public Double unaryMin(Double value) {
		return -value;
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

}
