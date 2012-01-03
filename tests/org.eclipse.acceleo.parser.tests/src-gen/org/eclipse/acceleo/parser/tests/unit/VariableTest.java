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
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link Variable} class.
 * 
 * @generated
 */
public class VariableTest extends AbstractCstTest {

	/**
	 * Tests the behavior of reference <code>initExpression</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testInitExpression() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getVariable_InitExpression();
		Variable variable = CstFactory.eINSTANCE.createVariable();
		variable.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression initExpressionValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(variable.eIsSet(feature));
		assertNull(variable.getInitExpression());

		variable.setInitExpression(initExpressionValue);
		assertTrue(notified);
		notified = false;
		assertSame(initExpressionValue, variable.getInitExpression());
		assertSame(variable.getInitExpression(), variable.eGet(feature));
		assertSame(variable.getInitExpression(), variable.eGet(feature, false));
		assertTrue(variable.eIsSet(feature));

		variable.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(variable.getInitExpression());
		assertSame(variable.getInitExpression(), variable.eGet(feature));
		assertSame(variable.getInitExpression(), variable.eGet(feature, false));
		assertFalse(variable.eIsSet(feature));

		variable.setInitExpression(initExpressionValue);
		assertTrue(notified);
		notified = false;
		assertSame(initExpressionValue, variable.getInitExpression());
		assertSame(variable.getInitExpression(), variable.eGet(feature));
		assertSame(variable.getInitExpression(), variable.eGet(feature, false));
		assertTrue(variable.eIsSet(feature));

		variable.eSet(feature, initExpressionValue);
		assertTrue(notified);
		notified = false;
		assertSame(initExpressionValue, variable.getInitExpression());
		assertSame(variable.getInitExpression(), variable.eGet(feature));
		assertSame(variable.getInitExpression(), variable.eGet(feature, false));
		assertTrue(variable.eIsSet(feature));

		variable.setInitExpression(null);
		assertTrue(notified);
		notified = false;
		assertNull(variable.getInitExpression());
		assertSame(feature.getDefaultValue(), variable.getInitExpression());
		assertSame(variable.getInitExpression(), variable.eGet(feature));
		assertSame(variable.getInitExpression(), variable.eGet(feature, false));
		assertFalse(variable.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		Variable variable = CstFactory.eINSTANCE.createVariable();
		variable.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(variable.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)variable.getStartPosition())
				.intValue());

		variable.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)variable.getStartPosition()).intValue());
		assertEquals(((Integer)variable.getStartPosition()).intValue(), ((Integer)variable.eGet(feature))
				.intValue());
		assertTrue(variable.eIsSet(feature));

		variable.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)variable.getStartPosition())
				.intValue());
		assertEquals(((Integer)variable.getStartPosition()).intValue(), ((Integer)variable.eGet(feature))
				.intValue());
		assertFalse(variable.eIsSet(feature));

		variable.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)variable.getStartPosition()).intValue());
		assertEquals(((Integer)variable.getStartPosition()).intValue(), ((Integer)variable.eGet(feature))
				.intValue());
		assertTrue(variable.eIsSet(feature));

		variable.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)variable.getStartPosition())
				.intValue());
		assertEquals(((Integer)variable.getStartPosition()).intValue(), ((Integer)variable.eGet(feature))
				.intValue());
		assertFalse(variable.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		Variable variable = CstFactory.eINSTANCE.createVariable();
		variable.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(variable.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)variable.getEndPosition())
				.intValue());

		variable.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)variable.getEndPosition()).intValue());
		assertEquals(((Integer)variable.getEndPosition()).intValue(), ((Integer)variable.eGet(feature))
				.intValue());
		assertTrue(variable.eIsSet(feature));

		variable.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)variable.getEndPosition())
				.intValue());
		assertEquals(((Integer)variable.getEndPosition()).intValue(), ((Integer)variable.eGet(feature))
				.intValue());
		assertFalse(variable.eIsSet(feature));

		variable.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)variable.getEndPosition()).intValue());
		assertEquals(((Integer)variable.getEndPosition()).intValue(), ((Integer)variable.eGet(feature))
				.intValue());
		assertTrue(variable.eIsSet(feature));

		variable.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)variable.getEndPosition())
				.intValue());
		assertEquals(((Integer)variable.getEndPosition()).intValue(), ((Integer)variable.eGet(feature))
				.intValue());
		assertFalse(variable.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>name</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testName() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getVariable_Name();
		Variable variable = CstFactory.eINSTANCE.createVariable();
		variable.eAdapters().add(new MockEAdapter());
		java.lang.String nameValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(variable.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), variable.getName());

		variable.setName(nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, variable.getName());
		assertEquals(variable.getName(), variable.eGet(feature));
		assertTrue(variable.eIsSet(feature));

		variable.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), variable.getName());
		assertEquals(variable.getName(), variable.eGet(feature));
		assertFalse(variable.eIsSet(feature));

		variable.eSet(feature, nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, variable.getName());
		assertEquals(variable.getName(), variable.eGet(feature));
		assertTrue(variable.eIsSet(feature));

		variable.setName(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), variable.getName());
		assertEquals(variable.getName(), variable.eGet(feature));
		assertFalse(variable.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>type</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testType() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getVariable_Type();
		Variable variable = CstFactory.eINSTANCE.createVariable();
		variable.eAdapters().add(new MockEAdapter());
		java.lang.String typeValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(variable.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), variable.getType());

		variable.setType(typeValue);
		assertTrue(notified);
		notified = false;
		assertEquals(typeValue, variable.getType());
		assertEquals(variable.getType(), variable.eGet(feature));
		assertTrue(variable.eIsSet(feature));

		variable.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), variable.getType());
		assertEquals(variable.getType(), variable.eGet(feature));
		assertFalse(variable.eIsSet(feature));

		variable.eSet(feature, typeValue);
		assertTrue(notified);
		notified = false;
		assertEquals(typeValue, variable.getType());
		assertEquals(variable.getType(), variable.eGet(feature));
		assertTrue(variable.eIsSet(feature));

		variable.setType(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), variable.getType());
		assertEquals(variable.getType(), variable.eGet(feature));
		assertFalse(variable.eIsSet(feature));
	}

}
