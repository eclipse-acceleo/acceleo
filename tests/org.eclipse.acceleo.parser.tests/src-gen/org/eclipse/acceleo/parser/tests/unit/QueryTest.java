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
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link Query} class.
 * 
 * @generated
 */
public class QueryTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>parameter</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testParameter() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getQuery_Parameter();
		Query query = CstFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.Variable parameterValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createVariable();
		List<org.eclipse.acceleo.parser.cst.Variable> listParameter = new ArrayList<org.eclipse.acceleo.parser.cst.Variable>(
				1);
		listParameter.add(parameterValue);

		assertFalse(query.eIsSet(feature));
		assertTrue(query.getParameter().isEmpty());

		query.getParameter().add(parameterValue);
		assertTrue(notified);
		notified = false;
		assertTrue(query.getParameter().contains(parameterValue));
		assertSame(query.getParameter(), query.eGet(feature));
		assertSame(query.getParameter(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(query.getParameter().isEmpty());
		assertSame(query.getParameter(), query.eGet(feature));
		assertSame(query.getParameter(), query.eGet(feature, false));
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, listParameter);
		assertTrue(notified);
		notified = false;
		assertTrue(query.getParameter().contains(parameterValue));
		assertSame(query.getParameter(), query.eGet(feature));
		assertSame(query.getParameter(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>expression</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testExpression() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getQuery_Expression();
		Query query = CstFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression expressionValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(query.eIsSet(feature));
		assertNull(query.getExpression());

		query.setExpression(expressionValue);
		assertTrue(notified);
		notified = false;
		assertSame(expressionValue, query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertFalse(query.eIsSet(feature));

		query.setExpression(expressionValue);
		assertTrue(notified);
		notified = false;
		assertSame(expressionValue, query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.eSet(feature, expressionValue);
		assertTrue(notified);
		notified = false;
		assertSame(expressionValue, query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.setExpression(null);
		assertTrue(notified);
		notified = false;
		assertNull(query.getExpression());
		assertSame(feature.getDefaultValue(), query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertFalse(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		Query query = CstFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(query.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getStartPosition())
				.intValue());

		query.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)query.getStartPosition()).intValue());
		assertEquals(((Integer)query.getStartPosition()).intValue(), ((Integer)query.eGet(feature))
				.intValue());
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getStartPosition())
				.intValue());
		assertEquals(((Integer)query.getStartPosition()).intValue(), ((Integer)query.eGet(feature))
				.intValue());
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)query.getStartPosition()).intValue());
		assertEquals(((Integer)query.getStartPosition()).intValue(), ((Integer)query.eGet(feature))
				.intValue());
		assertTrue(query.eIsSet(feature));

		query.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getStartPosition())
				.intValue());
		assertEquals(((Integer)query.getStartPosition()).intValue(), ((Integer)query.eGet(feature))
				.intValue());
		assertFalse(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		Query query = CstFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(query.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getEndPosition())
				.intValue());

		query.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)query.getEndPosition()).intValue());
		assertEquals(((Integer)query.getEndPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getEndPosition())
				.intValue());
		assertEquals(((Integer)query.getEndPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)query.getEndPosition()).intValue());
		assertEquals(((Integer)query.getEndPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertTrue(query.eIsSet(feature));

		query.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getEndPosition())
				.intValue());
		assertEquals(((Integer)query.getEndPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertFalse(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>name</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testName() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModuleElement_Name();
		Query query = CstFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		java.lang.String nameValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(query.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), query.getName());

		query.setName(nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, query.getName());
		assertEquals(query.getName(), query.eGet(feature));
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), query.getName());
		assertEquals(query.getName(), query.eGet(feature));
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, query.getName());
		assertEquals(query.getName(), query.eGet(feature));
		assertTrue(query.eIsSet(feature));

		query.setName(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), query.getName());
		assertEquals(query.getName(), query.eGet(feature));
		assertFalse(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>visibility</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testVisibility() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModuleElement_Visibility();
		Query query = CstFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.VisibilityKind visibilityValue = (org.eclipse.acceleo.parser.cst.VisibilityKind)feature
				.getDefaultValue();
		for (org.eclipse.acceleo.parser.cst.VisibilityKind aVisibilityKind : org.eclipse.acceleo.parser.cst.VisibilityKind.VALUES) {
			if (visibilityValue.getValue() != aVisibilityKind.getValue()) {
				visibilityValue = aVisibilityKind;
				break;
			}
		}

		assertFalse(query.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), query.getVisibility());

		query.setVisibility(visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, query.getVisibility());
		assertEquals(query.getVisibility(), query.eGet(feature));
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), query.getVisibility());
		assertEquals(query.getVisibility(), query.eGet(feature));
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, query.getVisibility());
		assertEquals(query.getVisibility(), query.eGet(feature));
		assertTrue(query.eIsSet(feature));

		query.setVisibility(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), query.getVisibility());
		assertEquals(query.getVisibility(), query.eGet(feature));
		assertFalse(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>type</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testType() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getQuery_Type();
		Query query = CstFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		java.lang.String typeValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(query.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), query.getType());

		query.setType(typeValue);
		assertTrue(notified);
		notified = false;
		assertEquals(typeValue, query.getType());
		assertEquals(query.getType(), query.eGet(feature));
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), query.getType());
		assertEquals(query.getType(), query.eGet(feature));
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, typeValue);
		assertTrue(notified);
		notified = false;
		assertEquals(typeValue, query.getType());
		assertEquals(query.getType(), query.eGet(feature));
		assertTrue(query.eIsSet(feature));

		query.setType(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), query.getType());
		assertEquals(query.getType(), query.eGet(feature));
		assertFalse(query.eIsSet(feature));
	}

}
