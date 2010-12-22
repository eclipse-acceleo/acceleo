/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.services;

public class StringService {
	public String convertToPackageString(String s) {
		StringBuffer result = new StringBuffer(String.valueOf(s.charAt(0)));
		for (int i = 1; i < s.length(); i++) {
			final char next = s.charAt(i);
			if (Character.isUpperCase(next)) {
				result.append('_');
			}
			result.append(Character.toUpperCase(next));
		}
		return result.toString();
	}
}
