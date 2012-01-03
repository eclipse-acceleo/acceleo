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
import org.eclipse.acceleo.parser.cst.IfBlock;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link IfBlock} class.
 * 
 * @generated
 */
public class IfBlockTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Body();
		IfBlock ifBlock = CstFactory.eINSTANCE.createIfBlock();
		ifBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.TemplateExpression bodyValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createTemplateExpression();
		List<org.eclipse.acceleo.parser.cst.TemplateExpression> listBody = new ArrayList<org.eclipse.acceleo.parser.cst.TemplateExpression>(
				1);
		listBody.add(bodyValue);

		assertFalse(ifBlock.eIsSet(feature));
		assertTrue(ifBlock.getBody().isEmpty());

		ifBlock.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(ifBlock.getBody().contains(bodyValue));
		assertSame(ifBlock.getBody(), ifBlock.eGet(feature));
		assertSame(ifBlock.getBody(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(ifBlock.getBody().isEmpty());
		assertSame(ifBlock.getBody(), ifBlock.eGet(feature));
		assertSame(ifBlock.getBody(), ifBlock.eGet(feature, false));
		assertFalse(ifBlock.eIsSet(feature));

		ifBlock.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(ifBlock.getBody().contains(bodyValue));
		assertSame(ifBlock.getBody(), ifBlock.eGet(feature));
		assertSame(ifBlock.getBody(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>elseIf</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testElseIf() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getIfBlock_ElseIf();
		IfBlock ifBlock = CstFactory.eINSTANCE.createIfBlock();
		ifBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.IfBlock elseIfValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createIfBlock();
		List<org.eclipse.acceleo.parser.cst.IfBlock> listElseIf = new ArrayList<org.eclipse.acceleo.parser.cst.IfBlock>(
				1);
		listElseIf.add(elseIfValue);

		assertFalse(ifBlock.eIsSet(feature));
		assertTrue(ifBlock.getElseIf().isEmpty());

		ifBlock.getElseIf().add(elseIfValue);
		assertTrue(notified);
		notified = false;
		assertTrue(ifBlock.getElseIf().contains(elseIfValue));
		assertSame(ifBlock.getElseIf(), ifBlock.eGet(feature));
		assertSame(ifBlock.getElseIf(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(ifBlock.getElseIf().isEmpty());
		assertSame(ifBlock.getElseIf(), ifBlock.eGet(feature));
		assertSame(ifBlock.getElseIf(), ifBlock.eGet(feature, false));
		assertFalse(ifBlock.eIsSet(feature));

		ifBlock.eSet(feature, listElseIf);
		assertTrue(notified);
		notified = false;
		assertTrue(ifBlock.getElseIf().contains(elseIfValue));
		assertSame(ifBlock.getElseIf(), ifBlock.eGet(feature));
		assertSame(ifBlock.getElseIf(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Init();
		IfBlock ifBlock = CstFactory.eINSTANCE.createIfBlock();
		ifBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.InitSection initValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createInitSection();

		assertFalse(ifBlock.eIsSet(feature));
		assertNull(ifBlock.getInit());

		ifBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, ifBlock.getInit());
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature));
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(ifBlock.getInit());
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature));
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature, false));
		assertFalse(ifBlock.eIsSet(feature));

		ifBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, ifBlock.getInit());
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature));
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, ifBlock.getInit());
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature));
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(ifBlock.getInit());
		assertSame(feature.getDefaultValue(), ifBlock.getInit());
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature));
		assertSame(ifBlock.getInit(), ifBlock.eGet(feature, false));
		assertFalse(ifBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>ifExpr</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testIfExpr() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getIfBlock_IfExpr();
		IfBlock ifBlock = CstFactory.eINSTANCE.createIfBlock();
		ifBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression ifExprValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(ifBlock.eIsSet(feature));
		assertNull(ifBlock.getIfExpr());

		ifBlock.setIfExpr(ifExprValue);
		assertTrue(notified);
		notified = false;
		assertSame(ifExprValue, ifBlock.getIfExpr());
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature));
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(ifBlock.getIfExpr());
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature));
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature, false));
		assertFalse(ifBlock.eIsSet(feature));

		ifBlock.setIfExpr(ifExprValue);
		assertTrue(notified);
		notified = false;
		assertSame(ifExprValue, ifBlock.getIfExpr());
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature));
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eSet(feature, ifExprValue);
		assertTrue(notified);
		notified = false;
		assertSame(ifExprValue, ifBlock.getIfExpr());
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature));
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.setIfExpr(null);
		assertTrue(notified);
		notified = false;
		assertNull(ifBlock.getIfExpr());
		assertSame(feature.getDefaultValue(), ifBlock.getIfExpr());
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature));
		assertSame(ifBlock.getIfExpr(), ifBlock.eGet(feature, false));
		assertFalse(ifBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>else</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testElse() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getIfBlock_Else();
		IfBlock ifBlock = CstFactory.eINSTANCE.createIfBlock();
		ifBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.Block elseValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createBlock();

		assertFalse(ifBlock.eIsSet(feature));
		assertNull(ifBlock.getElse());

		ifBlock.setElse(elseValue);
		assertTrue(notified);
		notified = false;
		assertSame(elseValue, ifBlock.getElse());
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature));
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(ifBlock.getElse());
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature));
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature, false));
		assertFalse(ifBlock.eIsSet(feature));

		ifBlock.setElse(elseValue);
		assertTrue(notified);
		notified = false;
		assertSame(elseValue, ifBlock.getElse());
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature));
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eSet(feature, elseValue);
		assertTrue(notified);
		notified = false;
		assertSame(elseValue, ifBlock.getElse());
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature));
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature, false));
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.setElse(null);
		assertTrue(notified);
		notified = false;
		assertNull(ifBlock.getElse());
		assertSame(feature.getDefaultValue(), ifBlock.getElse());
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature));
		assertSame(ifBlock.getElse(), ifBlock.eGet(feature, false));
		assertFalse(ifBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		IfBlock ifBlock = CstFactory.eINSTANCE.createIfBlock();
		ifBlock.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(ifBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)ifBlock.getStartPosition())
				.intValue());

		ifBlock.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)ifBlock.getStartPosition()).intValue());
		assertEquals(((Integer)ifBlock.getStartPosition()).intValue(), ((Integer)ifBlock.eGet(feature))
				.intValue());
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)ifBlock.getStartPosition())
				.intValue());
		assertEquals(((Integer)ifBlock.getStartPosition()).intValue(), ((Integer)ifBlock.eGet(feature))
				.intValue());
		assertFalse(ifBlock.eIsSet(feature));

		ifBlock.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)ifBlock.getStartPosition()).intValue());
		assertEquals(((Integer)ifBlock.getStartPosition()).intValue(), ((Integer)ifBlock.eGet(feature))
				.intValue());
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)ifBlock.getStartPosition())
				.intValue());
		assertEquals(((Integer)ifBlock.getStartPosition()).intValue(), ((Integer)ifBlock.eGet(feature))
				.intValue());
		assertFalse(ifBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		IfBlock ifBlock = CstFactory.eINSTANCE.createIfBlock();
		ifBlock.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(ifBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)ifBlock.getEndPosition())
				.intValue());

		ifBlock.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)ifBlock.getEndPosition()).intValue());
		assertEquals(((Integer)ifBlock.getEndPosition()).intValue(), ((Integer)ifBlock.eGet(feature))
				.intValue());
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)ifBlock.getEndPosition())
				.intValue());
		assertEquals(((Integer)ifBlock.getEndPosition()).intValue(), ((Integer)ifBlock.eGet(feature))
				.intValue());
		assertFalse(ifBlock.eIsSet(feature));

		ifBlock.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)ifBlock.getEndPosition()).intValue());
		assertEquals(((Integer)ifBlock.getEndPosition()).intValue(), ((Integer)ifBlock.eGet(feature))
				.intValue());
		assertTrue(ifBlock.eIsSet(feature));

		ifBlock.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)ifBlock.getEndPosition())
				.intValue());
		assertEquals(((Integer)ifBlock.getEndPosition()).intValue(), ((Integer)ifBlock.eGet(feature))
				.intValue());
		assertFalse(ifBlock.eIsSet(feature));
	}

}
