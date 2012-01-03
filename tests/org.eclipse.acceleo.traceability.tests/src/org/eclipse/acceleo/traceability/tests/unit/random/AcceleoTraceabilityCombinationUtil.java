/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.tests.unit.random;

import java.util.ArrayList;
import java.util.List;

public final class AcceleoTraceabilityCombinationUtil {

	/**
	 * The constructor.
	 */
	private AcceleoTraceabilityCombinationUtil() {
		// prevent instantiation
	}

	public static <T> List<List<T>> combinate(List<List<T>> uncombinedList) {
		List<List<T>> list = new ArrayList<List<T>>();
		int index[] = new int[uncombinedList.size()];
		int combinations = combinations(uncombinedList) - 1;
		// Initialize index
		for (int i = 0; i < index.length; i++) {
			index[i] = 0;
		}
		// First combination is always valid
		List<T> combination = new ArrayList<T>();
		for (int m = 0; m < index.length; m++) {
			combination.add(uncombinedList.get(m).get(index[m]));
		}
		list.add(combination);
		for (int k = 0; k < combinations; k++) {
			combination = new ArrayList<T>();
			boolean found = false;
			// We Use reverse order
			for (int l = index.length - 1; l >= 0 && found == false; l--) {
				int currentListSize = uncombinedList.get(l).size();
				if (index[l] < currentListSize - 1) {
					index[l] = index[l] + 1;
					found = true;
				} else {
					// Overflow
					index[l] = 0;
				}
			}
			for (int m = 0; m < index.length; m++) {
				combination.add(uncombinedList.get(m).get(index[m]));
			}
			list.add(combination);
		}
		return list;
	}

	private static <T> int combinations(List<List<T>> list) {
		int count = 1;
		for (List<T> current : list) {
			count = count * current.size();
		}
		return count;
	}
}
