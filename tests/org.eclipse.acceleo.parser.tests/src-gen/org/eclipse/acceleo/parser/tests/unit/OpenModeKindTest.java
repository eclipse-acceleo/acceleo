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
package org.eclipse.acceleo.parser.tests.unit;

import junit.framework.TestCase;

import org.eclipse.acceleo.parser.cst.OpenModeKind;

/**
 * Tests the behavior of the {@link OpenModeKind} enumeration.
 * 
 * @generated
 */
@SuppressWarnings("nls")
public class OpenModeKindTest extends TestCase {
	/**
	 * Tests the behavior of the {@link OpenModeKind#get(int)} method.
	 * 
	 * @generated
	 */
	public void testGetInt() {
		int highestValue = -1;
		for (OpenModeKind value : OpenModeKind.VALUES) {
			assertSame(OpenModeKind.get(value.getValue()), value);
			if (value.getValue() > highestValue) {
				highestValue = value.getValue();
			}
		}
		assertNull(OpenModeKind.get(++highestValue));
	}

	/**
	 * Tests the behavior of the {@link OpenModeKind#get(java.lang.String)} method.
	 * 
	 * @generated
	 */
	public void testGetString() {
		for (OpenModeKind value : OpenModeKind.VALUES) {
			assertSame(OpenModeKind.get(value.getLiteral()), value);
		}
		assertNull(OpenModeKind.get("ThisIsNotAValueOfTheTestedEnum"));
	}

	/**
	 * Tests the behavior of the {@link OpenModeKind#getByName(java.lang.String)} method.
	 * 
	 * @generated
	 */
	public void testGetByName() {
		for (OpenModeKind value : OpenModeKind.VALUES) {
			assertSame(OpenModeKind.getByName(value.getName()), value);
		}
		assertNull(OpenModeKind.getByName("ThisIsNotTheNameOfAValueFromTheTestedEnum"));
	}
}
