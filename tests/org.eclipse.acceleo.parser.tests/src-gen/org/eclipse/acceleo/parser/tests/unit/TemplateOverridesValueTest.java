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
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link TemplateOverridesValue} class.
 * 
 * @generated
 */
public class TemplateOverridesValueTest extends AbstractCstTest {

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		TemplateOverridesValue templateOverridesValue = CstFactory.eINSTANCE.createTemplateOverridesValue();
		templateOverridesValue.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(templateOverridesValue.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateOverridesValue
				.getStartPosition()).intValue());

		templateOverridesValue.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)templateOverridesValue.getStartPosition()).intValue());
		assertEquals(((Integer)templateOverridesValue.getStartPosition()).intValue(),
				((Integer)templateOverridesValue.eGet(feature)).intValue());
		assertTrue(templateOverridesValue.eIsSet(feature));

		templateOverridesValue.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateOverridesValue
				.getStartPosition()).intValue());
		assertEquals(((Integer)templateOverridesValue.getStartPosition()).intValue(),
				((Integer)templateOverridesValue.eGet(feature)).intValue());
		assertFalse(templateOverridesValue.eIsSet(feature));

		templateOverridesValue.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)templateOverridesValue.getStartPosition()).intValue());
		assertEquals(((Integer)templateOverridesValue.getStartPosition()).intValue(),
				((Integer)templateOverridesValue.eGet(feature)).intValue());
		assertTrue(templateOverridesValue.eIsSet(feature));

		templateOverridesValue.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateOverridesValue
				.getStartPosition()).intValue());
		assertEquals(((Integer)templateOverridesValue.getStartPosition()).intValue(),
				((Integer)templateOverridesValue.eGet(feature)).intValue());
		assertFalse(templateOverridesValue.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		TemplateOverridesValue templateOverridesValue = CstFactory.eINSTANCE.createTemplateOverridesValue();
		templateOverridesValue.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(templateOverridesValue.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateOverridesValue
				.getEndPosition()).intValue());

		templateOverridesValue.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)templateOverridesValue.getEndPosition()).intValue());
		assertEquals(((Integer)templateOverridesValue.getEndPosition()).intValue(),
				((Integer)templateOverridesValue.eGet(feature)).intValue());
		assertTrue(templateOverridesValue.eIsSet(feature));

		templateOverridesValue.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateOverridesValue
				.getEndPosition()).intValue());
		assertEquals(((Integer)templateOverridesValue.getEndPosition()).intValue(),
				((Integer)templateOverridesValue.eGet(feature)).intValue());
		assertFalse(templateOverridesValue.eIsSet(feature));

		templateOverridesValue.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)templateOverridesValue.getEndPosition()).intValue());
		assertEquals(((Integer)templateOverridesValue.getEndPosition()).intValue(),
				((Integer)templateOverridesValue.eGet(feature)).intValue());
		assertTrue(templateOverridesValue.eIsSet(feature));

		templateOverridesValue.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateOverridesValue
				.getEndPosition()).intValue());
		assertEquals(((Integer)templateOverridesValue.getEndPosition()).intValue(),
				((Integer)templateOverridesValue.eGet(feature)).intValue());
		assertFalse(templateOverridesValue.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>name</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testName() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getTemplateOverridesValue_Name();
		TemplateOverridesValue templateOverridesValue = CstFactory.eINSTANCE.createTemplateOverridesValue();
		templateOverridesValue.eAdapters().add(new MockEAdapter());
		java.lang.String nameValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(templateOverridesValue.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), templateOverridesValue.getName());

		templateOverridesValue.setName(nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, templateOverridesValue.getName());
		assertEquals(templateOverridesValue.getName(), templateOverridesValue.eGet(feature));
		assertTrue(templateOverridesValue.eIsSet(feature));

		templateOverridesValue.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), templateOverridesValue.getName());
		assertEquals(templateOverridesValue.getName(), templateOverridesValue.eGet(feature));
		assertFalse(templateOverridesValue.eIsSet(feature));

		templateOverridesValue.eSet(feature, nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, templateOverridesValue.getName());
		assertEquals(templateOverridesValue.getName(), templateOverridesValue.eGet(feature));
		assertTrue(templateOverridesValue.eIsSet(feature));

		templateOverridesValue.setName(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), templateOverridesValue.getName());
		assertEquals(templateOverridesValue.getName(), templateOverridesValue.eGet(feature));
		assertFalse(templateOverridesValue.eIsSet(feature));
	}

}
