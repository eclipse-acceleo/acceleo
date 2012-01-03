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
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link ModelExpression} class.
 * 
 * @generated
 */
public class ModelExpressionTest extends AbstractCstTest {

	/**
	 * Tests the behavior of reference <code>before</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBefore() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModelExpression_Before();
		ModelExpression modelExpression = CstFactory.eINSTANCE.createModelExpression();
		modelExpression.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression beforeValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(modelExpression.eIsSet(feature));
		assertNull(modelExpression.getBefore());

		modelExpression.setBefore(beforeValue);
		assertTrue(notified);
		notified = false;
		assertSame(beforeValue, modelExpression.getBefore());
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature));
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature, false));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(modelExpression.getBefore());
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature));
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature, false));
		assertFalse(modelExpression.eIsSet(feature));

		modelExpression.setBefore(beforeValue);
		assertTrue(notified);
		notified = false;
		assertSame(beforeValue, modelExpression.getBefore());
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature));
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature, false));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.eSet(feature, beforeValue);
		assertTrue(notified);
		notified = false;
		assertSame(beforeValue, modelExpression.getBefore());
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature));
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature, false));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.setBefore(null);
		assertTrue(notified);
		notified = false;
		assertNull(modelExpression.getBefore());
		assertSame(feature.getDefaultValue(), modelExpression.getBefore());
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature));
		assertSame(modelExpression.getBefore(), modelExpression.eGet(feature, false));
		assertFalse(modelExpression.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>each</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEach() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModelExpression_Each();
		ModelExpression modelExpression = CstFactory.eINSTANCE.createModelExpression();
		modelExpression.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression eachValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(modelExpression.eIsSet(feature));
		assertNull(modelExpression.getEach());

		modelExpression.setEach(eachValue);
		assertTrue(notified);
		notified = false;
		assertSame(eachValue, modelExpression.getEach());
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature));
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature, false));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(modelExpression.getEach());
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature));
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature, false));
		assertFalse(modelExpression.eIsSet(feature));

		modelExpression.setEach(eachValue);
		assertTrue(notified);
		notified = false;
		assertSame(eachValue, modelExpression.getEach());
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature));
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature, false));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.eSet(feature, eachValue);
		assertTrue(notified);
		notified = false;
		assertSame(eachValue, modelExpression.getEach());
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature));
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature, false));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.setEach(null);
		assertTrue(notified);
		notified = false;
		assertNull(modelExpression.getEach());
		assertSame(feature.getDefaultValue(), modelExpression.getEach());
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature));
		assertSame(modelExpression.getEach(), modelExpression.eGet(feature, false));
		assertFalse(modelExpression.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>after</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testAfter() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModelExpression_After();
		ModelExpression modelExpression = CstFactory.eINSTANCE.createModelExpression();
		modelExpression.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression afterValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(modelExpression.eIsSet(feature));
		assertNull(modelExpression.getAfter());

		modelExpression.setAfter(afterValue);
		assertTrue(notified);
		notified = false;
		assertSame(afterValue, modelExpression.getAfter());
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature));
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature, false));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(modelExpression.getAfter());
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature));
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature, false));
		assertFalse(modelExpression.eIsSet(feature));

		modelExpression.setAfter(afterValue);
		assertTrue(notified);
		notified = false;
		assertSame(afterValue, modelExpression.getAfter());
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature));
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature, false));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.eSet(feature, afterValue);
		assertTrue(notified);
		notified = false;
		assertSame(afterValue, modelExpression.getAfter());
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature));
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature, false));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.setAfter(null);
		assertTrue(notified);
		notified = false;
		assertNull(modelExpression.getAfter());
		assertSame(feature.getDefaultValue(), modelExpression.getAfter());
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature));
		assertSame(modelExpression.getAfter(), modelExpression.eGet(feature, false));
		assertFalse(modelExpression.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		ModelExpression modelExpression = CstFactory.eINSTANCE.createModelExpression();
		modelExpression.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(modelExpression.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)modelExpression
				.getStartPosition()).intValue());

		modelExpression.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)modelExpression.getStartPosition()).intValue());
		assertEquals(((Integer)modelExpression.getStartPosition()).intValue(), ((Integer)modelExpression
				.eGet(feature)).intValue());
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)modelExpression
				.getStartPosition()).intValue());
		assertEquals(((Integer)modelExpression.getStartPosition()).intValue(), ((Integer)modelExpression
				.eGet(feature)).intValue());
		assertFalse(modelExpression.eIsSet(feature));

		modelExpression.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)modelExpression.getStartPosition()).intValue());
		assertEquals(((Integer)modelExpression.getStartPosition()).intValue(), ((Integer)modelExpression
				.eGet(feature)).intValue());
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)modelExpression
				.getStartPosition()).intValue());
		assertEquals(((Integer)modelExpression.getStartPosition()).intValue(), ((Integer)modelExpression
				.eGet(feature)).intValue());
		assertFalse(modelExpression.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		ModelExpression modelExpression = CstFactory.eINSTANCE.createModelExpression();
		modelExpression.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(modelExpression.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)modelExpression
				.getEndPosition()).intValue());

		modelExpression.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)modelExpression.getEndPosition()).intValue());
		assertEquals(((Integer)modelExpression.getEndPosition()).intValue(), ((Integer)modelExpression
				.eGet(feature)).intValue());
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)modelExpression
				.getEndPosition()).intValue());
		assertEquals(((Integer)modelExpression.getEndPosition()).intValue(), ((Integer)modelExpression
				.eGet(feature)).intValue());
		assertFalse(modelExpression.eIsSet(feature));

		modelExpression.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)modelExpression.getEndPosition()).intValue());
		assertEquals(((Integer)modelExpression.getEndPosition()).intValue(), ((Integer)modelExpression
				.eGet(feature)).intValue());
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)modelExpression
				.getEndPosition()).intValue());
		assertEquals(((Integer)modelExpression.getEndPosition()).intValue(), ((Integer)modelExpression
				.eGet(feature)).intValue());
		assertFalse(modelExpression.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>body</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModelExpression_Body();
		ModelExpression modelExpression = CstFactory.eINSTANCE.createModelExpression();
		modelExpression.eAdapters().add(new MockEAdapter());
		java.lang.String bodyValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(modelExpression.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), modelExpression.getBody());

		modelExpression.setBody(bodyValue);
		assertTrue(notified);
		notified = false;
		assertEquals(bodyValue, modelExpression.getBody());
		assertEquals(modelExpression.getBody(), modelExpression.eGet(feature));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), modelExpression.getBody());
		assertEquals(modelExpression.getBody(), modelExpression.eGet(feature));
		assertFalse(modelExpression.eIsSet(feature));

		modelExpression.eSet(feature, bodyValue);
		assertTrue(notified);
		notified = false;
		assertEquals(bodyValue, modelExpression.getBody());
		assertEquals(modelExpression.getBody(), modelExpression.eGet(feature));
		assertTrue(modelExpression.eIsSet(feature));

		modelExpression.setBody(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), modelExpression.getBody());
		assertEquals(modelExpression.getBody(), modelExpression.eGet(feature));
		assertFalse(modelExpression.eIsSet(feature));
	}

}
