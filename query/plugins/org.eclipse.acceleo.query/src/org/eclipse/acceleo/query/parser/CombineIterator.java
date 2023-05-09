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
package org.eclipse.acceleo.query.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Combines objects. for instance: [[0,1],[0,1,2]]
 * <ul>
 * <li>[0,0]</li>
 * <li>[0,1]</li>
 * <li>[0,2]</li>
 * <li>[1,0]</li>
 * <li>[1,1]</li>
 * <li>[1,2]</li>
 * </ul>
 * 
 * @param <T>
 *            the kind of object to combine
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CombineIterator<T> implements Iterator<List<T>> {

	/**
	 * {@link Object} to combine.
	 */
	private final Object[][] objects;

	/**
	 * The number of combinations.
	 */
	private final int nbCominations;

	/**
	 * The index of the current combination.
	 */
	private int currentCombination;

	/**
	 * Constructor.
	 * 
	 * @param objects
	 *            the {@link List} of {@link Set} of objects to combine.
	 */
	public CombineIterator(List<Set<T>> objects) {
		this.objects = new Object[objects.size()][];
		int cominations;
		if (objects.size() != 0) {
			cominations = 1;
		} else {
			cominations = 0;
		}
		for (int i = 0; i < this.objects.length; ++i) {
			this.objects[i] = objects.get(i).toArray();
			cominations *= this.objects[i].length;
		}
		nbCominations = cominations;
		currentCombination = 0;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return currentCombination < nbCominations;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Iterator#next()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> next() {
		final List<T> res = new ArrayList<T>(objects.length);
		int base = nbCominations;
		int remains = currentCombination;
		for (int i = 0; i < objects.length; ++i) {
			final Object[] array = objects[i];
			base /= array.length;
			res.add((T)objects[i][remains / base]);
			remains = remains % base;
		}
		++currentCombination;

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
