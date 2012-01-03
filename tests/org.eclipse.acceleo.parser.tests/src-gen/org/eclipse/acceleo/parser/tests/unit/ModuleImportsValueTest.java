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

import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link ModuleImportsValue} class.
 * 
 * @generated
 */
public class ModuleImportsValueTest extends AbstractCstTest {

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		ModuleImportsValue moduleImportsValue = CstFactory.eINSTANCE.createModuleImportsValue();
		moduleImportsValue.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(moduleImportsValue.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)moduleImportsValue
				.getStartPosition()).intValue());

		moduleImportsValue.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)moduleImportsValue.getStartPosition()).intValue());
		assertEquals(((Integer)moduleImportsValue.getStartPosition()).intValue(),
				((Integer)moduleImportsValue.eGet(feature)).intValue());
		assertTrue(moduleImportsValue.eIsSet(feature));

		moduleImportsValue.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)moduleImportsValue
				.getStartPosition()).intValue());
		assertEquals(((Integer)moduleImportsValue.getStartPosition()).intValue(),
				((Integer)moduleImportsValue.eGet(feature)).intValue());
		assertFalse(moduleImportsValue.eIsSet(feature));

		moduleImportsValue.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)moduleImportsValue.getStartPosition()).intValue());
		assertEquals(((Integer)moduleImportsValue.getStartPosition()).intValue(),
				((Integer)moduleImportsValue.eGet(feature)).intValue());
		assertTrue(moduleImportsValue.eIsSet(feature));

		moduleImportsValue.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)moduleImportsValue
				.getStartPosition()).intValue());
		assertEquals(((Integer)moduleImportsValue.getStartPosition()).intValue(),
				((Integer)moduleImportsValue.eGet(feature)).intValue());
		assertFalse(moduleImportsValue.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		ModuleImportsValue moduleImportsValue = CstFactory.eINSTANCE.createModuleImportsValue();
		moduleImportsValue.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(moduleImportsValue.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)moduleImportsValue
				.getEndPosition()).intValue());

		moduleImportsValue.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)moduleImportsValue.getEndPosition()).intValue());
		assertEquals(((Integer)moduleImportsValue.getEndPosition()).intValue(), ((Integer)moduleImportsValue
				.eGet(feature)).intValue());
		assertTrue(moduleImportsValue.eIsSet(feature));

		moduleImportsValue.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)moduleImportsValue
				.getEndPosition()).intValue());
		assertEquals(((Integer)moduleImportsValue.getEndPosition()).intValue(), ((Integer)moduleImportsValue
				.eGet(feature)).intValue());
		assertFalse(moduleImportsValue.eIsSet(feature));

		moduleImportsValue.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)moduleImportsValue.getEndPosition()).intValue());
		assertEquals(((Integer)moduleImportsValue.getEndPosition()).intValue(), ((Integer)moduleImportsValue
				.eGet(feature)).intValue());
		assertTrue(moduleImportsValue.eIsSet(feature));

		moduleImportsValue.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)moduleImportsValue
				.getEndPosition()).intValue());
		assertEquals(((Integer)moduleImportsValue.getEndPosition()).intValue(), ((Integer)moduleImportsValue
				.eGet(feature)).intValue());
		assertFalse(moduleImportsValue.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>name</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testName() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModuleImportsValue_Name();
		ModuleImportsValue moduleImportsValue = CstFactory.eINSTANCE.createModuleImportsValue();
		moduleImportsValue.eAdapters().add(new MockEAdapter());
		java.lang.String nameValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(moduleImportsValue.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), moduleImportsValue.getName());

		moduleImportsValue.setName(nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, moduleImportsValue.getName());
		assertEquals(moduleImportsValue.getName(), moduleImportsValue.eGet(feature));
		assertTrue(moduleImportsValue.eIsSet(feature));

		moduleImportsValue.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), moduleImportsValue.getName());
		assertEquals(moduleImportsValue.getName(), moduleImportsValue.eGet(feature));
		assertFalse(moduleImportsValue.eIsSet(feature));

		moduleImportsValue.eSet(feature, nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, moduleImportsValue.getName());
		assertEquals(moduleImportsValue.getName(), moduleImportsValue.eGet(feature));
		assertTrue(moduleImportsValue.eIsSet(feature));

		moduleImportsValue.setName(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), moduleImportsValue.getName());
		assertEquals(moduleImportsValue.getName(), moduleImportsValue.eGet(feature));
		assertFalse(moduleImportsValue.eIsSet(feature));
	}

}
