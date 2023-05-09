/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services;

import java.util.Comparator;
import java.util.Map;

/**
 * Evaluates a lambda then uses the result as comparables.
 */
final class LambdaComparator<T> implements Comparator<T> {
	/**
	 * Pre-populated map linking the objects from the collection we're sorting with the values calculated by
	 * this lambda.
	 */
	private final Map<T, Object> preComputedValues;

	/**
	 * Constructs a comparator given its lambda.
	 * 
	 * @param preComputedValues
	 *            Pre-populated map linking the objects from the collection we're sorting with the values
	 *            calculated by this lambda
	 */
	LambdaComparator(Map<T, Object> preComputedValues) {
		this.preComputedValues = preComputedValues;
	}

	@Override
	public int compare(T o1, T o2) {
		final int result;

		Object o1Result = preComputedValues.get(o1);
		Object o2Result = preComputedValues.get(o2);
		try {
			if (o1Result instanceof Comparable<?>) {
				@SuppressWarnings("unchecked")
				Comparable<Object> c1 = (Comparable<Object>)o1Result;
				result = c1.compareTo(o2Result);
			} else if (o2Result instanceof Comparable<?>) {
				@SuppressWarnings("unchecked")
				Comparable<Object> c2 = (Comparable<Object>)o2Result;
				result = -c2.compareTo(o1Result);
			} else {
				result = 0;
			}
			// CHECKSTYLE:OFF
		} catch (Exception e) {
			// CHECKSTYLE:ON
			throw new IllegalArgumentException("Cannot compare " + o1 + " with " + o2, e);
		}

		return result;
	}
}
