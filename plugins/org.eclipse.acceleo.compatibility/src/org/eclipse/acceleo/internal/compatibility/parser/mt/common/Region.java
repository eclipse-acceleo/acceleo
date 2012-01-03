/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.compatibility.parser.mt.common;

/**
 * Complex index in a string.
 * <p>
 * Attributes are public to simplify their uses.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class Region {

	/**
	 * Index not found.
	 */
	public static final Region NOT_FOUND = new Region(-1, -1);

	/**
	 * The beginning index of a substring.
	 */
	private int b;

	/**
	 * The ending index of a substring.
	 */
	private int e;

	/**
	 * Constructor.
	 * 
	 * @param b
	 *            is the begin index of a substring
	 * @param e
	 *            is the end index of a substring
	 */
	public Region(int b, int e) {
		this.b = b;
		this.e = e;
	}

	/**
	 * Return the beginning index.
	 * 
	 * @return the beginning index.
	 */
	public int b() {
		return b;
	}

	/**
	 * Return the ending index.
	 * 
	 * @return the ending index.
	 */
	public int e() {
		return e;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Region) {
			final Region other = (Region)arg0;
			return b == other.b && e == other.e;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return b;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return '[' + Integer.toString(b) + ',' + Integer.toString(e) + ']';
	}

}
