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
 * Services on {@link Comparable} instances.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class ComparableServices {

	/**
	 * {@link Comparable#compareTo(Object) Compares} <code>a</code> to <code>b</code> and takes care of
	 * <code>null</code>.
	 * 
	 * @param a
	 *            first {@link Comparable} can be <code>null</code>
	 * @param b
	 *            second {@link Comparable} can be <code>null</code>
	 * @return {@link Comparable#compareTo(Object) Compares} <code>a</code> to <code>b</code> and takes care
	 *         of <code>null</code>
	 * @param <T>
	 *            the kind of {@link Comparable}
	 */
	private <T extends Comparable<? super T>> int safeCompare(T a, T b) {
		final int result;

		if (a == null) {
			if (b == null) {
				result = 0;
			} else {
				result = -b.compareTo(a);
			}
		} else {
			result = a.compareTo(b);
		}

		return result;
	}

	/**
	 * Compares two comparable instances. Returns <code>true</code> if a < b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @param <T>
	 *            the type been compared
	 * @return <code>true</code> if a < b.
	 */

	public <T extends Comparable<? super T>> Boolean lessThan(T a, T b) {
		return Boolean.valueOf(safeCompare(a, b) < 0);
	}

	/**
	 * Compares two comparable instances. Returns <code>true</code> if a <= b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @param <T>
	 *            the type been compared
	 * @return <code>true</code> if a <= b.
	 */

	public <T extends Comparable<? super T>> Boolean lessThanEqual(T a, T b) {
		return Boolean.valueOf(safeCompare(a, b) <= 0);
	}

	/**
	 * Compares two comparable instances. Returns <code>true</code> if a > b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @param <T>
	 *            the type been compared
	 * @return <code>true</code> if a > b.
	 */

	public <T extends Comparable<? super T>> Boolean greaterThan(T a, T b) {
		return Boolean.valueOf(safeCompare(a, b) > 0);
	}

	/**
	 * Compares two comparable instances. Returns <code>true</code> if a >= b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @param <T>
	 *            the type been compared
	 * @return <code>true</code> if a >= b.
	 */

	public <T extends Comparable<? super T>> Boolean greaterThanEqual(T a, T b) {
		return Boolean.valueOf(safeCompare(a, b) >= 0);
	}

}
