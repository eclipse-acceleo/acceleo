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
import org.eclipse.acceleo.parser.cst.LetBlock;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link LetBlock} class.
 * 
 * @generated
 */
public class LetBlockTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Body();
		LetBlock letBlock = CstFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.TemplateExpression bodyValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createTemplateExpression();
		List<org.eclipse.acceleo.parser.cst.TemplateExpression> listBody = new ArrayList<org.eclipse.acceleo.parser.cst.TemplateExpression>(
				1);
		listBody.add(bodyValue);

		assertFalse(letBlock.eIsSet(feature));
		assertTrue(letBlock.getBody().isEmpty());

		letBlock.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getBody().contains(bodyValue));
		assertSame(letBlock.getBody(), letBlock.eGet(feature));
		assertSame(letBlock.getBody(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getBody().isEmpty());
		assertSame(letBlock.getBody(), letBlock.eGet(feature));
		assertSame(letBlock.getBody(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getBody().contains(bodyValue));
		assertSame(letBlock.getBody(), letBlock.eGet(feature));
		assertSame(letBlock.getBody(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>elseLet</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testElseLet() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getLetBlock_ElseLet();
		LetBlock letBlock = CstFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.LetBlock elseLetValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createLetBlock();
		List<org.eclipse.acceleo.parser.cst.LetBlock> listElseLet = new ArrayList<org.eclipse.acceleo.parser.cst.LetBlock>(
				1);
		listElseLet.add(elseLetValue);

		assertFalse(letBlock.eIsSet(feature));
		assertTrue(letBlock.getElseLet().isEmpty());

		letBlock.getElseLet().add(elseLetValue);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getElseLet().contains(elseLetValue));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getElseLet().isEmpty());
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.eSet(feature, listElseLet);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getElseLet().contains(elseLetValue));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Init();
		LetBlock letBlock = CstFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.InitSection initValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createInitSection();

		assertFalse(letBlock.eIsSet(feature));
		assertNull(letBlock.getInit());

		letBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getInit());
		assertSame(feature.getDefaultValue(), letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>else</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testElse() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getLetBlock_Else();
		LetBlock letBlock = CstFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.Block elseValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createBlock();

		assertFalse(letBlock.eIsSet(feature));
		assertNull(letBlock.getElse());

		letBlock.setElse(elseValue);
		assertTrue(notified);
		notified = false;
		assertSame(elseValue, letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.setElse(elseValue);
		assertTrue(notified);
		notified = false;
		assertSame(elseValue, letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eSet(feature, elseValue);
		assertTrue(notified);
		notified = false;
		assertSame(elseValue, letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setElse(null);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getElse());
		assertSame(feature.getDefaultValue(), letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>letVariable</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testLetVariable() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getLetBlock_LetVariable();
		LetBlock letBlock = CstFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.Variable letVariableValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createVariable();

		assertFalse(letBlock.eIsSet(feature));
		assertNull(letBlock.getLetVariable());

		letBlock.setLetVariable(letVariableValue);
		assertTrue(notified);
		notified = false;
		assertSame(letVariableValue, letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.setLetVariable(letVariableValue);
		assertTrue(notified);
		notified = false;
		assertSame(letVariableValue, letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eSet(feature, letVariableValue);
		assertTrue(notified);
		notified = false;
		assertSame(letVariableValue, letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setLetVariable(null);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getLetVariable());
		assertSame(feature.getDefaultValue(), letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		LetBlock letBlock = CstFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(letBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getStartPosition())
				.intValue());

		letBlock.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)letBlock.getStartPosition()).intValue());
		assertEquals(((Integer)letBlock.getStartPosition()).intValue(), ((Integer)letBlock.eGet(feature))
				.intValue());
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getStartPosition())
				.intValue());
		assertEquals(((Integer)letBlock.getStartPosition()).intValue(), ((Integer)letBlock.eGet(feature))
				.intValue());
		assertFalse(letBlock.eIsSet(feature));

		letBlock.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)letBlock.getStartPosition()).intValue());
		assertEquals(((Integer)letBlock.getStartPosition()).intValue(), ((Integer)letBlock.eGet(feature))
				.intValue());
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getStartPosition())
				.intValue());
		assertEquals(((Integer)letBlock.getStartPosition()).intValue(), ((Integer)letBlock.eGet(feature))
				.intValue());
		assertFalse(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		LetBlock letBlock = CstFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(letBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getEndPosition())
				.intValue());

		letBlock.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)letBlock.getEndPosition()).intValue());
		assertEquals(((Integer)letBlock.getEndPosition()).intValue(), ((Integer)letBlock.eGet(feature))
				.intValue());
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getEndPosition())
				.intValue());
		assertEquals(((Integer)letBlock.getEndPosition()).intValue(), ((Integer)letBlock.eGet(feature))
				.intValue());
		assertFalse(letBlock.eIsSet(feature));

		letBlock.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)letBlock.getEndPosition()).intValue());
		assertEquals(((Integer)letBlock.getEndPosition()).intValue(), ((Integer)letBlock.eGet(feature))
				.intValue());
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getEndPosition())
				.intValue());
		assertEquals(((Integer)letBlock.getEndPosition()).intValue(), ((Integer)letBlock.eGet(feature))
				.intValue());
		assertFalse(letBlock.eIsSet(feature));
	}

}
