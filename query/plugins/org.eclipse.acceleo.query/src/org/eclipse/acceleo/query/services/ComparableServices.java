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

	public <T> Boolean lower(Comparable<T> a, T b) {
		return Boolean.valueOf(a.compareTo(b) < 0);
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

	public <T> Boolean lowerEqual(Comparable<T> a, T b) {
		return Boolean.valueOf(a.compareTo(b) <= 0);
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

	public <T> Boolean greater(Comparable<T> a, T b) {
		return Boolean.valueOf(a.compareTo(b) > 0);
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

	public <T> Boolean greaterEqual(Comparable<T> a, T b) {
		return Boolean.valueOf(a.compareTo(b) >= 0);
	}

	/**
	 * Compares two comparable instances. Returns <code>true</code> if a == b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @param <T>
	 *            the type been compared
	 * @return <code>true</code> if a == b.
	 */

	public <T> Boolean equals(Comparable<T> a, T b) {
		return Boolean.valueOf(a.compareTo(b) == 0);
	}

	/**
	 * Compares two comparable instances. Returns <code>true</code> if a != b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @param <T>
	 *            the type been compared
	 * @return <code>true</code> if a != b.
	 */
	public <T> Boolean differs(Comparable<T> a, T b) {
		return Boolean.valueOf(a.compareTo(b) != 0);
	}

}
