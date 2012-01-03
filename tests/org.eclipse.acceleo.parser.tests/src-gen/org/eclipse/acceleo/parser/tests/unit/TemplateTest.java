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
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link Template} class.
 * 
 * @generated
 */
public class TemplateTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Body();
		Template template = CstFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.TemplateExpression bodyValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createTemplateExpression();
		List<org.eclipse.acceleo.parser.cst.TemplateExpression> listBody = new ArrayList<org.eclipse.acceleo.parser.cst.TemplateExpression>(
				1);
		listBody.add(bodyValue);

		assertFalse(template.eIsSet(feature));
		assertTrue(template.getBody().isEmpty());

		template.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getBody().contains(bodyValue));
		assertSame(template.getBody(), template.eGet(feature));
		assertSame(template.getBody(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getBody().isEmpty());
		assertSame(template.getBody(), template.eGet(feature));
		assertSame(template.getBody(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getBody().contains(bodyValue));
		assertSame(template.getBody(), template.eGet(feature));
		assertSame(template.getBody(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>overrides</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testOverrides() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getTemplate_Overrides();
		Template template = CstFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.TemplateOverridesValue overridesValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createTemplateOverridesValue();
		List<org.eclipse.acceleo.parser.cst.TemplateOverridesValue> listOverrides = new ArrayList<org.eclipse.acceleo.parser.cst.TemplateOverridesValue>(
				1);
		listOverrides.add(overridesValue);

		assertFalse(template.eIsSet(feature));
		assertTrue(template.getOverrides().isEmpty());

		template.getOverrides().add(overridesValue);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getOverrides().contains(overridesValue));
		assertSame(template.getOverrides(), template.eGet(feature));
		assertSame(template.getOverrides(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getOverrides().isEmpty());
		assertSame(template.getOverrides(), template.eGet(feature));
		assertSame(template.getOverrides(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, listOverrides);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getOverrides().contains(overridesValue));
		assertSame(template.getOverrides(), template.eGet(feature));
		assertSame(template.getOverrides(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>parameter</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testParameter() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getTemplate_Parameter();
		Template template = CstFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.Variable parameterValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createVariable();
		List<org.eclipse.acceleo.parser.cst.Variable> listParameter = new ArrayList<org.eclipse.acceleo.parser.cst.Variable>(
				1);
		listParameter.add(parameterValue);

		assertFalse(template.eIsSet(feature));
		assertTrue(template.getParameter().isEmpty());

		template.getParameter().add(parameterValue);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getParameter().contains(parameterValue));
		assertSame(template.getParameter(), template.eGet(feature));
		assertSame(template.getParameter(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getParameter().isEmpty());
		assertSame(template.getParameter(), template.eGet(feature));
		assertSame(template.getParameter(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, listParameter);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getParameter().contains(parameterValue));
		assertSame(template.getParameter(), template.eGet(feature));
		assertSame(template.getParameter(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Init();
		Template template = CstFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.InitSection initValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createInitSection();

		assertFalse(template.eIsSet(feature));
		assertNull(template.getInit());

		template.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(template.getInit());
		assertSame(feature.getDefaultValue(), template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>guard</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testGuard() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getTemplate_Guard();
		Template template = CstFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModelExpression guardValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModelExpression();

		assertFalse(template.eIsSet(feature));
		assertNull(template.getGuard());

		template.setGuard(guardValue);
		assertTrue(notified);
		notified = false;
		assertSame(guardValue, template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.setGuard(guardValue);
		assertTrue(notified);
		notified = false;
		assertSame(guardValue, template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eSet(feature, guardValue);
		assertTrue(notified);
		notified = false;
		assertSame(guardValue, template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.setGuard(null);
		assertTrue(notified);
		notified = false;
		assertNull(template.getGuard());
		assertSame(feature.getDefaultValue(), template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		Template template = CstFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(template.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getStartPosition())
				.intValue());

		template.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)template.getStartPosition()).intValue());
		assertEquals(((Integer)template.getStartPosition()).intValue(), ((Integer)template.eGet(feature))
				.intValue());
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getStartPosition())
				.intValue());
		assertEquals(((Integer)template.getStartPosition()).intValue(), ((Integer)template.eGet(feature))
				.intValue());
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)template.getStartPosition()).intValue());
		assertEquals(((Integer)template.getStartPosition()).intValue(), ((Integer)template.eGet(feature))
				.intValue());
		assertTrue(template.eIsSet(feature));

		template.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getStartPosition())
				.intValue());
		assertEquals(((Integer)template.getStartPosition()).intValue(), ((Integer)template.eGet(feature))
				.intValue());
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		Template template = CstFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(template.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getEndPosition())
				.intValue());

		template.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)template.getEndPosition()).intValue());
		assertEquals(((Integer)template.getEndPosition()).intValue(), ((Integer)template.eGet(feature))
				.intValue());
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getEndPosition())
				.intValue());
		assertEquals(((Integer)template.getEndPosition()).intValue(), ((Integer)template.eGet(feature))
				.intValue());
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)template.getEndPosition()).intValue());
		assertEquals(((Integer)template.getEndPosition()).intValue(), ((Integer)template.eGet(feature))
				.intValue());
		assertTrue(template.eIsSet(feature));

		template.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getEndPosition())
				.intValue());
		assertEquals(((Integer)template.getEndPosition()).intValue(), ((Integer)template.eGet(feature))
				.intValue());
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>name</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testName() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModuleElement_Name();
		Template template = CstFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		java.lang.String nameValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(template.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), template.getName());

		template.setName(nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, template.getName());
		assertEquals(template.getName(), template.eGet(feature));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), template.getName());
		assertEquals(template.getName(), template.eGet(feature));
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, template.getName());
		assertEquals(template.getName(), template.eGet(feature));
		assertTrue(template.eIsSet(feature));

		template.setName(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), template.getName());
		assertEquals(template.getName(), template.eGet(feature));
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>visibility</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testVisibility() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModuleElement_Visibility();
		Template template = CstFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.VisibilityKind visibilityValue = (org.eclipse.acceleo.parser.cst.VisibilityKind)feature
				.getDefaultValue();
		for (org.eclipse.acceleo.parser.cst.VisibilityKind aVisibilityKind : org.eclipse.acceleo.parser.cst.VisibilityKind.VALUES) {
			if (visibilityValue.getValue() != aVisibilityKind.getValue()) {
				visibilityValue = aVisibilityKind;
				break;
			}
		}

		assertFalse(template.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), template.getVisibility());

		template.setVisibility(visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, template.getVisibility());
		assertEquals(template.getVisibility(), template.eGet(feature));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), template.getVisibility());
		assertEquals(template.getVisibility(), template.eGet(feature));
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, template.getVisibility());
		assertEquals(template.getVisibility(), template.eGet(feature));
		assertTrue(template.eIsSet(feature));

		template.setVisibility(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), template.getVisibility());
		assertEquals(template.getVisibility(), template.eGet(feature));
		assertFalse(template.eIsSet(feature));
	}

}
