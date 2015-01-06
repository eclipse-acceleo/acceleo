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
	 * Comares two comparable instances. Returns <code>true</code> if a < b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a < b.
	 */

	public Boolean lower(Comparable a, Comparable b) {
		return a.compareTo(b) < 0;
	}

	/**
	 * Comares two comparable instances. Returns <code>true</code> if a <= b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a <= b.
	 */

	public Boolean lowerEqual(Comparable a, Comparable b) {
		return a.compareTo(b) <= 0;
	}

	/**
	 * Comares two comparable instances. Returns <code>true</code> if a > b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a > b.
	 */

	public Boolean greater(Comparable a, Comparable b) {
		return a.compareTo(b) > 0;
	}

	/**
	 * Comares two comparable instances. Returns <code>true</code> if a >= b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a >= b.
	 */

	public Boolean greaterEqual(Comparable a, Comparable b) {
		return a.compareTo(b) >= 0;
	}

	/**
	 * Comares two comparable instances. Returns <code>true</code> if a == b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a == b.
	 */

	public Boolean equals(Comparable a, Comparable b) {
		return a.compareTo(b) == 0;
	}

	/**
	 * Comares two comparable instances. Returns <code>true</code> if a != b.
	 * 
	 * @param a
	 *            the first operand
	 * @param b
	 *            the second operand
	 * @return <code>true</code> if a != b.
	 */
	public Boolean differs(Comparable a, Comparable b) {
		return a.compareTo(b) != 0;
	}

}
