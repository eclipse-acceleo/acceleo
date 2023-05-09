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
package org.eclipse.acceleo.query.runtime.test;

import java.util.Collection;

public class TestServiceDefinition {

	public Integer add(Integer a, Integer b) {
		return a + b;
	}

	public String toString(Object obj) {
		return obj.toString();
	}

	public Integer special(Integer i) {
		if (i == 3) {
			return null;
		} else {
			return i;
		}
	}

	public Object serviceReturnsNull(Object obj) {
		return null;
	}

	public Object serviceThrowsException(Object obj) {
		throw new NullPointerException("This is the purpose of this service.");
	}

	public int size(Collection<?> collection) {
		return collection.size();
	}
}
