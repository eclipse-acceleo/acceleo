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
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link TextExpression} class.
 * 
 * @generated
 */
public class TextExpressionTest extends AbstractCstTest {

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		TextExpression textExpression = CstFactory.eINSTANCE.createTextExpression();
		textExpression.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(textExpression.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)textExpression
				.getStartPosition()).intValue());

		textExpression.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)textExpression.getStartPosition()).intValue());
		assertEquals(((Integer)textExpression.getStartPosition()).intValue(), ((Integer)textExpression
				.eGet(feature)).intValue());
		assertTrue(textExpression.eIsSet(feature));

		textExpression.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)textExpression
				.getStartPosition()).intValue());
		assertEquals(((Integer)textExpression.getStartPosition()).intValue(), ((Integer)textExpression
				.eGet(feature)).intValue());
		assertFalse(textExpression.eIsSet(feature));

		textExpression.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)textExpression.getStartPosition()).intValue());
		assertEquals(((Integer)textExpression.getStartPosition()).intValue(), ((Integer)textExpression
				.eGet(feature)).intValue());
		assertTrue(textExpression.eIsSet(feature));

		textExpression.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)textExpression
				.getStartPosition()).intValue());
		assertEquals(((Integer)textExpression.getStartPosition()).intValue(), ((Integer)textExpression
				.eGet(feature)).intValue());
		assertFalse(textExpression.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		TextExpression textExpression = CstFactory.eINSTANCE.createTextExpression();
		textExpression.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(textExpression.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)textExpression
				.getEndPosition()).intValue());

		textExpression.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)textExpression.getEndPosition()).intValue());
		assertEquals(((Integer)textExpression.getEndPosition()).intValue(), ((Integer)textExpression
				.eGet(feature)).intValue());
		assertTrue(textExpression.eIsSet(feature));

		textExpression.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)textExpression
				.getEndPosition()).intValue());
		assertEquals(((Integer)textExpression.getEndPosition()).intValue(), ((Integer)textExpression
				.eGet(feature)).intValue());
		assertFalse(textExpression.eIsSet(feature));

		textExpression.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)textExpression.getEndPosition()).intValue());
		assertEquals(((Integer)textExpression.getEndPosition()).intValue(), ((Integer)textExpression
				.eGet(feature)).intValue());
		assertTrue(textExpression.eIsSet(feature));

		textExpression.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)textExpression
				.getEndPosition()).intValue());
		assertEquals(((Integer)textExpression.getEndPosition()).intValue(), ((Integer)textExpression
				.eGet(feature)).intValue());
		assertFalse(textExpression.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>value</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testValue() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getTextExpression_Value();
		TextExpression textExpression = CstFactory.eINSTANCE.createTextExpression();
		textExpression.eAdapters().add(new MockEAdapter());
		java.lang.String valueValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(textExpression.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), textExpression.getValue());

		textExpression.setValue(valueValue);
		assertTrue(notified);
		notified = false;
		assertEquals(valueValue, textExpression.getValue());
		assertEquals(textExpression.getValue(), textExpression.eGet(feature));
		assertTrue(textExpression.eIsSet(feature));

		textExpression.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), textExpression.getValue());
		assertEquals(textExpression.getValue(), textExpression.eGet(feature));
		assertFalse(textExpression.eIsSet(feature));

		textExpression.eSet(feature, valueValue);
		assertTrue(notified);
		notified = false;
		assertEquals(valueValue, textExpression.getValue());
		assertEquals(textExpression.getValue(), textExpression.eGet(feature));
		assertTrue(textExpression.eIsSet(feature));

		textExpression.setValue(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), textExpression.getValue());
		assertEquals(textExpression.getValue(), textExpression.eGet(feature));
		assertFalse(textExpression.eIsSet(feature));
	}

}
