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

import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.ForBlock;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link ForBlock} class.
 * 
 * @generated
 */
public class ForBlockTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Body();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.TemplateExpression bodyValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createTemplateExpression();
		List<org.eclipse.acceleo.parser.cst.TemplateExpression> listBody = new ArrayList<org.eclipse.acceleo.parser.cst.TemplateExpression>(
				1);
		listBody.add(bodyValue);

		assertFalse(forBlock.eIsSet(feature));
		assertTrue(forBlock.getBody().isEmpty());

		forBlock.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(forBlock.getBody().contains(bodyValue));
		assertSame(forBlock.getBody(), forBlock.eGet(feature));
		assertSame(forBlock.getBody(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(forBlock.getBody().isEmpty());
		assertSame(forBlock.getBody(), forBlock.eGet(feature));
		assertSame(forBlock.getBody(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));

		forBlock.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(forBlock.getBody().contains(bodyValue));
		assertSame(forBlock.getBody(), forBlock.eGet(feature));
		assertSame(forBlock.getBody(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Init();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.InitSection initValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createInitSection();

		assertFalse(forBlock.eIsSet(feature));
		assertNull(forBlock.getInit());

		forBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, forBlock.getInit());
		assertSame(forBlock.getInit(), forBlock.eGet(feature));
		assertSame(forBlock.getInit(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getInit());
		assertSame(forBlock.getInit(), forBlock.eGet(feature));
		assertSame(forBlock.getInit(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));

		forBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, forBlock.getInit());
		assertSame(forBlock.getInit(), forBlock.eGet(feature));
		assertSame(forBlock.getInit(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, forBlock.getInit());
		assertSame(forBlock.getInit(), forBlock.eGet(feature));
		assertSame(forBlock.getInit(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getInit());
		assertSame(feature.getDefaultValue(), forBlock.getInit());
		assertSame(forBlock.getInit(), forBlock.eGet(feature));
		assertSame(forBlock.getInit(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>loopVariable</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testLoopVariable() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getForBlock_LoopVariable();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.Variable loopVariableValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createVariable();

		assertFalse(forBlock.eIsSet(feature));
		assertNull(forBlock.getLoopVariable());

		forBlock.setLoopVariable(loopVariableValue);
		assertTrue(notified);
		notified = false;
		assertSame(loopVariableValue, forBlock.getLoopVariable());
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature));
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getLoopVariable());
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature));
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));

		forBlock.setLoopVariable(loopVariableValue);
		assertTrue(notified);
		notified = false;
		assertSame(loopVariableValue, forBlock.getLoopVariable());
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature));
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eSet(feature, loopVariableValue);
		assertTrue(notified);
		notified = false;
		assertSame(loopVariableValue, forBlock.getLoopVariable());
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature));
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.setLoopVariable(null);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getLoopVariable());
		assertSame(feature.getDefaultValue(), forBlock.getLoopVariable());
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature));
		assertSame(forBlock.getLoopVariable(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>iterSet</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testIterSet() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getForBlock_IterSet();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression iterSetValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(forBlock.eIsSet(feature));
		assertNull(forBlock.getIterSet());

		forBlock.setIterSet(iterSetValue);
		assertTrue(notified);
		notified = false;
		assertSame(iterSetValue, forBlock.getIterSet());
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature));
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getIterSet());
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature));
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));

		forBlock.setIterSet(iterSetValue);
		assertTrue(notified);
		notified = false;
		assertSame(iterSetValue, forBlock.getIterSet());
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature));
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eSet(feature, iterSetValue);
		assertTrue(notified);
		notified = false;
		assertSame(iterSetValue, forBlock.getIterSet());
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature));
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.setIterSet(null);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getIterSet());
		assertSame(feature.getDefaultValue(), forBlock.getIterSet());
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature));
		assertSame(forBlock.getIterSet(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>before</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBefore() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getForBlock_Before();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression beforeValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(forBlock.eIsSet(feature));
		assertNull(forBlock.getBefore());

		forBlock.setBefore(beforeValue);
		assertTrue(notified);
		notified = false;
		assertSame(beforeValue, forBlock.getBefore());
		assertSame(forBlock.getBefore(), forBlock.eGet(feature));
		assertSame(forBlock.getBefore(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getBefore());
		assertSame(forBlock.getBefore(), forBlock.eGet(feature));
		assertSame(forBlock.getBefore(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));

		forBlock.setBefore(beforeValue);
		assertTrue(notified);
		notified = false;
		assertSame(beforeValue, forBlock.getBefore());
		assertSame(forBlock.getBefore(), forBlock.eGet(feature));
		assertSame(forBlock.getBefore(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eSet(feature, beforeValue);
		assertTrue(notified);
		notified = false;
		assertSame(beforeValue, forBlock.getBefore());
		assertSame(forBlock.getBefore(), forBlock.eGet(feature));
		assertSame(forBlock.getBefore(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.setBefore(null);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getBefore());
		assertSame(feature.getDefaultValue(), forBlock.getBefore());
		assertSame(forBlock.getBefore(), forBlock.eGet(feature));
		assertSame(forBlock.getBefore(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>each</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEach() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getForBlock_Each();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression eachValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(forBlock.eIsSet(feature));
		assertNull(forBlock.getEach());

		forBlock.setEach(eachValue);
		assertTrue(notified);
		notified = false;
		assertSame(eachValue, forBlock.getEach());
		assertSame(forBlock.getEach(), forBlock.eGet(feature));
		assertSame(forBlock.getEach(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getEach());
		assertSame(forBlock.getEach(), forBlock.eGet(feature));
		assertSame(forBlock.getEach(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));

		forBlock.setEach(eachValue);
		assertTrue(notified);
		notified = false;
		assertSame(eachValue, forBlock.getEach());
		assertSame(forBlock.getEach(), forBlock.eGet(feature));
		assertSame(forBlock.getEach(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eSet(feature, eachValue);
		assertTrue(notified);
		notified = false;
		assertSame(eachValue, forBlock.getEach());
		assertSame(forBlock.getEach(), forBlock.eGet(feature));
		assertSame(forBlock.getEach(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.setEach(null);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getEach());
		assertSame(feature.getDefaultValue(), forBlock.getEach());
		assertSame(forBlock.getEach(), forBlock.eGet(feature));
		assertSame(forBlock.getEach(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>after</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testAfter() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getForBlock_After();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression afterValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(forBlock.eIsSet(feature));
		assertNull(forBlock.getAfter());

		forBlock.setAfter(afterValue);
		assertTrue(notified);
		notified = false;
		assertSame(afterValue, forBlock.getAfter());
		assertSame(forBlock.getAfter(), forBlock.eGet(feature));
		assertSame(forBlock.getAfter(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getAfter());
		assertSame(forBlock.getAfter(), forBlock.eGet(feature));
		assertSame(forBlock.getAfter(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));

		forBlock.setAfter(afterValue);
		assertTrue(notified);
		notified = false;
		assertSame(afterValue, forBlock.getAfter());
		assertSame(forBlock.getAfter(), forBlock.eGet(feature));
		assertSame(forBlock.getAfter(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eSet(feature, afterValue);
		assertTrue(notified);
		notified = false;
		assertSame(afterValue, forBlock.getAfter());
		assertSame(forBlock.getAfter(), forBlock.eGet(feature));
		assertSame(forBlock.getAfter(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.setAfter(null);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getAfter());
		assertSame(feature.getDefaultValue(), forBlock.getAfter());
		assertSame(forBlock.getAfter(), forBlock.eGet(feature));
		assertSame(forBlock.getAfter(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>guard</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testGuard() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getForBlock_Guard();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression guardValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(forBlock.eIsSet(feature));
		assertNull(forBlock.getGuard());

		forBlock.setGuard(guardValue);
		assertTrue(notified);
		notified = false;
		assertSame(guardValue, forBlock.getGuard());
		assertSame(forBlock.getGuard(), forBlock.eGet(feature));
		assertSame(forBlock.getGuard(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getGuard());
		assertSame(forBlock.getGuard(), forBlock.eGet(feature));
		assertSame(forBlock.getGuard(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));

		forBlock.setGuard(guardValue);
		assertTrue(notified);
		notified = false;
		assertSame(guardValue, forBlock.getGuard());
		assertSame(forBlock.getGuard(), forBlock.eGet(feature));
		assertSame(forBlock.getGuard(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eSet(feature, guardValue);
		assertTrue(notified);
		notified = false;
		assertSame(guardValue, forBlock.getGuard());
		assertSame(forBlock.getGuard(), forBlock.eGet(feature));
		assertSame(forBlock.getGuard(), forBlock.eGet(feature, false));
		assertTrue(forBlock.eIsSet(feature));

		forBlock.setGuard(null);
		assertTrue(notified);
		notified = false;
		assertNull(forBlock.getGuard());
		assertSame(feature.getDefaultValue(), forBlock.getGuard());
		assertSame(forBlock.getGuard(), forBlock.eGet(feature));
		assertSame(forBlock.getGuard(), forBlock.eGet(feature, false));
		assertFalse(forBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(forBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)forBlock.getStartPosition())
				.intValue());

		forBlock.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)forBlock.getStartPosition()).intValue());
		assertEquals(((Integer)forBlock.getStartPosition()).intValue(), ((Integer)forBlock.eGet(feature))
				.intValue());
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)forBlock.getStartPosition())
				.intValue());
		assertEquals(((Integer)forBlock.getStartPosition()).intValue(), ((Integer)forBlock.eGet(feature))
				.intValue());
		assertFalse(forBlock.eIsSet(feature));

		forBlock.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)forBlock.getStartPosition()).intValue());
		assertEquals(((Integer)forBlock.getStartPosition()).intValue(), ((Integer)forBlock.eGet(feature))
				.intValue());
		assertTrue(forBlock.eIsSet(feature));

		forBlock.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)forBlock.getStartPosition())
				.intValue());
		assertEquals(((Integer)forBlock.getStartPosition()).intValue(), ((Integer)forBlock.eGet(feature))
				.intValue());
		assertFalse(forBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		ForBlock forBlock = CstFactory.eINSTANCE.createForBlock();
		forBlock.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(forBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)forBlock.getEndPosition())
				.intValue());

		forBlock.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)forBlock.getEndPosition()).intValue());
		assertEquals(((Integer)forBlock.getEndPosition()).intValue(), ((Integer)forBlock.eGet(feature))
				.intValue());
		assertTrue(forBlock.eIsSet(feature));

		forBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)forBlock.getEndPosition())
				.intValue());
		assertEquals(((Integer)forBlock.getEndPosition()).intValue(), ((Integer)forBlock.eGet(feature))
				.intValue());
		assertFalse(forBlock.eIsSet(feature));

		forBlock.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)forBlock.getEndPosition()).intValue());
		assertEquals(((Integer)forBlock.getEndPosition()).intValue(), ((Integer)forBlock.eGet(feature))
				.intValue());
		assertTrue(forBlock.eIsSet(feature));

		forBlock.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)forBlock.getEndPosition())
				.intValue());
		assertEquals(((Integer)forBlock.getEndPosition()).intValue(), ((Integer)forBlock.eGet(feature))
				.intValue());
		assertFalse(forBlock.eIsSet(feature));
	}

}
