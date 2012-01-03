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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.InitSection;

/**
 * Tests the behavior of the {@link InitSection} class.
 * 
 * @generated
 */
public class InitSectionTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>variable</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testVariable() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getInitSection_Variable();
		InitSection initSection = CstFactory.eINSTANCE.createInitSection();
		initSection.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.Variable variableValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createVariable();
		List<org.eclipse.acceleo.parser.cst.Variable> listVariable = new ArrayList<org.eclipse.acceleo.parser.cst.Variable>(
				1);
		listVariable.add(variableValue);

		assertFalse(initSection.eIsSet(feature));
		assertTrue(initSection.getVariable().isEmpty());

		initSection.getVariable().add(variableValue);
		assertTrue(notified);
		notified = false;
		assertTrue(initSection.getVariable().contains(variableValue));
		assertSame(initSection.getVariable(), initSection.eGet(feature));
		assertSame(initSection.getVariable(), initSection.eGet(feature, false));
		assertTrue(initSection.eIsSet(feature));

		initSection.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(initSection.getVariable().isEmpty());
		assertSame(initSection.getVariable(), initSection.eGet(feature));
		assertSame(initSection.getVariable(), initSection.eGet(feature, false));
		assertFalse(initSection.eIsSet(feature));

		initSection.eSet(feature, listVariable);
		assertTrue(notified);
		notified = false;
		assertTrue(initSection.getVariable().contains(variableValue));
		assertSame(initSection.getVariable(), initSection.eGet(feature));
		assertSame(initSection.getVariable(), initSection.eGet(feature, false));
		assertTrue(initSection.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		InitSection initSection = CstFactory.eINSTANCE.createInitSection();
		initSection.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(initSection.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection
				.getStartPosition()).intValue());

		initSection.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)initSection.getStartPosition()).intValue());
		assertEquals(((Integer)initSection.getStartPosition()).intValue(), ((Integer)initSection
				.eGet(feature)).intValue());
		assertTrue(initSection.eIsSet(feature));

		initSection.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection
				.getStartPosition()).intValue());
		assertEquals(((Integer)initSection.getStartPosition()).intValue(), ((Integer)initSection
				.eGet(feature)).intValue());
		assertFalse(initSection.eIsSet(feature));

		initSection.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)initSection.getStartPosition()).intValue());
		assertEquals(((Integer)initSection.getStartPosition()).intValue(), ((Integer)initSection
				.eGet(feature)).intValue());
		assertTrue(initSection.eIsSet(feature));

		initSection.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection
				.getStartPosition()).intValue());
		assertEquals(((Integer)initSection.getStartPosition()).intValue(), ((Integer)initSection
				.eGet(feature)).intValue());
		assertFalse(initSection.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		InitSection initSection = CstFactory.eINSTANCE.createInitSection();
		initSection.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(initSection.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection.getEndPosition())
				.intValue());

		initSection.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)initSection.getEndPosition()).intValue());
		assertEquals(((Integer)initSection.getEndPosition()).intValue(), ((Integer)initSection.eGet(feature))
				.intValue());
		assertTrue(initSection.eIsSet(feature));

		initSection.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection.getEndPosition())
				.intValue());
		assertEquals(((Integer)initSection.getEndPosition()).intValue(), ((Integer)initSection.eGet(feature))
				.intValue());
		assertFalse(initSection.eIsSet(feature));

		initSection.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)initSection.getEndPosition()).intValue());
		assertEquals(((Integer)initSection.getEndPosition()).intValue(), ((Integer)initSection.eGet(feature))
				.intValue());
		assertTrue(initSection.eIsSet(feature));

		initSection.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection.getEndPosition())
				.intValue());
		assertEquals(((Integer)initSection.getEndPosition()).intValue(), ((Integer)initSection.eGet(feature))
				.intValue());
		assertFalse(initSection.eIsSet(feature));
	}

}
