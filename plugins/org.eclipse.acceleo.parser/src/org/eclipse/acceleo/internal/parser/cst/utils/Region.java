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
package org.eclipse.acceleo.internal.parser.cst.utils;

/**
 * A region with a beginning index and an ending index.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class Region {

	/**
	 * To specify that the region doesn't exist.
	 */
	public static final Region NOT_FOUND = new Region(-1, -1, null);

	/**
	 * To specify that the region isn't initialized.
	 */
	private static final Region NOT_INITIALIZED = new Region(-2, -2, null);

	/**
	 * The beginning index of a substring.
	 */
	private int b;

	/**
	 * The ending index of a substring.
	 */
	private int e;

	/**
	 * The sequence used to match the region.
	 */
	private Sequence sequence;

	/**
	 * Constructor.
	 * 
	 * @param b
	 *            is the beginning index of a substring
	 * @param e
	 *            is the ending index of a substring
	 * @param sequence
	 *            is the sequence used to match the region
	 */
	public Region(int b, int e, Sequence sequence) {
		this.b = b;
		this.e = e;
		this.sequence = sequence;
	}

	/**
	 * Gets the beginning index.
	 * 
	 * @return the beginning index.
	 */
	public int b() {
		return b;
	}

	/**
	 * Gets the ending index.
	 * 
	 * @return the ending index.
	 */
	public int e() {
		return e;
	}

	/**
	 * Gets the sequence used to match the region.
	 * 
	 * @return the sequence used to match the region
	 */
	public Sequence getSequence() {
		return sequence;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Region) {
			Region other = (Region)arg0;
			return (b == other.b) && (e == other.e) && (sequence == other.sequence);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + e;
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return '[' + Integer.toString(b) + ',' + Integer.toString(e) + ']';
	}

	/**
	 * Initializes a table of region.
	 * 
	 * @param length
	 *            is the length of the table to create
	 * @return the table, each element is a Region(-2, -2, null)
	 */
	public static Region[] createPositions(int length) {
		Region[] positions = new Region[length];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = NOT_INITIALIZED;
		}
		return positions;
	}

}
