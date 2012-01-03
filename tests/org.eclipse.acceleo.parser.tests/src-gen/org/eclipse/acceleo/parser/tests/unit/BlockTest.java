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

import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link Block} class.
 * 
 * @generated
 */
public class BlockTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Body();
		Block block = CstFactory.eINSTANCE.createBlock();
		block.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.TemplateExpression bodyValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createTemplateExpression();
		List<org.eclipse.acceleo.parser.cst.TemplateExpression> listBody = new ArrayList<org.eclipse.acceleo.parser.cst.TemplateExpression>(
				1);
		listBody.add(bodyValue);

		assertFalse(block.eIsSet(feature));
		assertTrue(block.getBody().isEmpty());

		block.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(block.getBody().contains(bodyValue));
		assertSame(block.getBody(), block.eGet(feature));
		assertSame(block.getBody(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));

		block.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(block.getBody().isEmpty());
		assertSame(block.getBody(), block.eGet(feature));
		assertSame(block.getBody(), block.eGet(feature, false));
		assertFalse(block.eIsSet(feature));

		block.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(block.getBody().contains(bodyValue));
		assertSame(block.getBody(), block.eGet(feature));
		assertSame(block.getBody(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Init();
		Block block = CstFactory.eINSTANCE.createBlock();
		block.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.InitSection initValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createInitSection();

		assertFalse(block.eIsSet(feature));
		assertNull(block.getInit());

		block.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));

		block.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertFalse(block.eIsSet(feature));

		block.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));

		block.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));

		block.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(block.getInit());
		assertSame(feature.getDefaultValue(), block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertFalse(block.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		Block block = CstFactory.eINSTANCE.createBlock();
		block.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(block.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getStartPosition())
				.intValue());

		block.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)block.getStartPosition()).intValue());
		assertEquals(((Integer)block.getStartPosition()).intValue(), ((Integer)block.eGet(feature))
				.intValue());
		assertTrue(block.eIsSet(feature));

		block.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getStartPosition())
				.intValue());
		assertEquals(((Integer)block.getStartPosition()).intValue(), ((Integer)block.eGet(feature))
				.intValue());
		assertFalse(block.eIsSet(feature));

		block.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)block.getStartPosition()).intValue());
		assertEquals(((Integer)block.getStartPosition()).intValue(), ((Integer)block.eGet(feature))
				.intValue());
		assertTrue(block.eIsSet(feature));

		block.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getStartPosition())
				.intValue());
		assertEquals(((Integer)block.getStartPosition()).intValue(), ((Integer)block.eGet(feature))
				.intValue());
		assertFalse(block.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		Block block = CstFactory.eINSTANCE.createBlock();
		block.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(block.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getEndPosition())
				.intValue());

		block.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)block.getEndPosition()).intValue());
		assertEquals(((Integer)block.getEndPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertTrue(block.eIsSet(feature));

		block.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getEndPosition())
				.intValue());
		assertEquals(((Integer)block.getEndPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertFalse(block.eIsSet(feature));

		block.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)block.getEndPosition()).intValue());
		assertEquals(((Integer)block.getEndPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertTrue(block.eIsSet(feature));

		block.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getEndPosition())
				.intValue());
		assertEquals(((Integer)block.getEndPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertFalse(block.eIsSet(feature));
	}

}
