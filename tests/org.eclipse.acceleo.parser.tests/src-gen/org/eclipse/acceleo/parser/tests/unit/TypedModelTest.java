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
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link TypedModel} class.
 * 
 * @generated
 */
public class TypedModelTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>takesTypesFrom</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testTakesTypesFrom() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getTypedModel_TakesTypesFrom();
		TypedModel typedModel = CstFactory.eINSTANCE.createTypedModel();
		typedModel.eAdapters().add(new MockEAdapter());
		org.eclipse.emf.ecore.EPackage takesTypesFromValue = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE
				.createEPackage();
		List<org.eclipse.emf.ecore.EPackage> listTakesTypesFrom = new ArrayList<org.eclipse.emf.ecore.EPackage>(
				1);
		listTakesTypesFrom.add(takesTypesFromValue);

		assertFalse(typedModel.eIsSet(feature));
		assertTrue(typedModel.getTakesTypesFrom().isEmpty());

		typedModel.getTakesTypesFrom().add(takesTypesFromValue);
		assertTrue(notified);
		notified = false;
		assertTrue(typedModel.getTakesTypesFrom().contains(takesTypesFromValue));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature, false));
		assertTrue(typedModel.eIsSet(feature));

		typedModel.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(typedModel.getTakesTypesFrom().isEmpty());
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature, false));
		assertFalse(typedModel.eIsSet(feature));

		typedModel.eSet(feature, listTakesTypesFrom);
		assertTrue(notified);
		notified = false;
		assertTrue(typedModel.getTakesTypesFrom().contains(takesTypesFromValue));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature, false));
		assertTrue(typedModel.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		TypedModel typedModel = CstFactory.eINSTANCE.createTypedModel();
		typedModel.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(typedModel.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(),
				((Integer)typedModel.getStartPosition()).intValue());

		typedModel.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)typedModel.getStartPosition()).intValue());
		assertEquals(((Integer)typedModel.getStartPosition()).intValue(), ((Integer)typedModel.eGet(feature))
				.intValue());
		assertTrue(typedModel.eIsSet(feature));

		typedModel.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(),
				((Integer)typedModel.getStartPosition()).intValue());
		assertEquals(((Integer)typedModel.getStartPosition()).intValue(), ((Integer)typedModel.eGet(feature))
				.intValue());
		assertFalse(typedModel.eIsSet(feature));

		typedModel.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)typedModel.getStartPosition()).intValue());
		assertEquals(((Integer)typedModel.getStartPosition()).intValue(), ((Integer)typedModel.eGet(feature))
				.intValue());
		assertTrue(typedModel.eIsSet(feature));

		typedModel.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(),
				((Integer)typedModel.getStartPosition()).intValue());
		assertEquals(((Integer)typedModel.getStartPosition()).intValue(), ((Integer)typedModel.eGet(feature))
				.intValue());
		assertFalse(typedModel.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		TypedModel typedModel = CstFactory.eINSTANCE.createTypedModel();
		typedModel.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(typedModel.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)typedModel.getEndPosition())
				.intValue());

		typedModel.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)typedModel.getEndPosition()).intValue());
		assertEquals(((Integer)typedModel.getEndPosition()).intValue(), ((Integer)typedModel.eGet(feature))
				.intValue());
		assertTrue(typedModel.eIsSet(feature));

		typedModel.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)typedModel.getEndPosition())
				.intValue());
		assertEquals(((Integer)typedModel.getEndPosition()).intValue(), ((Integer)typedModel.eGet(feature))
				.intValue());
		assertFalse(typedModel.eIsSet(feature));

		typedModel.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)typedModel.getEndPosition()).intValue());
		assertEquals(((Integer)typedModel.getEndPosition()).intValue(), ((Integer)typedModel.eGet(feature))
				.intValue());
		assertTrue(typedModel.eIsSet(feature));

		typedModel.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)typedModel.getEndPosition())
				.intValue());
		assertEquals(((Integer)typedModel.getEndPosition()).intValue(), ((Integer)typedModel.eGet(feature))
				.intValue());
		assertFalse(typedModel.eIsSet(feature));
	}

}
