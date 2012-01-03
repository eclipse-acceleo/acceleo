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
import org.eclipse.acceleo.parser.cst.Macro;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link Macro} class.
 * 
 * @generated
 */
public class MacroTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Body();
		Macro macro = CstFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.TemplateExpression bodyValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createTemplateExpression();
		List<org.eclipse.acceleo.parser.cst.TemplateExpression> listBody = new ArrayList<org.eclipse.acceleo.parser.cst.TemplateExpression>(
				1);
		listBody.add(bodyValue);

		assertFalse(macro.eIsSet(feature));
		assertTrue(macro.getBody().isEmpty());

		macro.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getBody().contains(bodyValue));
		assertSame(macro.getBody(), macro.eGet(feature));
		assertSame(macro.getBody(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getBody().isEmpty());
		assertSame(macro.getBody(), macro.eGet(feature));
		assertSame(macro.getBody(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getBody().contains(bodyValue));
		assertSame(macro.getBody(), macro.eGet(feature));
		assertSame(macro.getBody(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>parameter</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testParameter() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getMacro_Parameter();
		Macro macro = CstFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.Variable parameterValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createVariable();
		List<org.eclipse.acceleo.parser.cst.Variable> listParameter = new ArrayList<org.eclipse.acceleo.parser.cst.Variable>(
				1);
		listParameter.add(parameterValue);

		assertFalse(macro.eIsSet(feature));
		assertTrue(macro.getParameter().isEmpty());

		macro.getParameter().add(parameterValue);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getParameter().contains(parameterValue));
		assertSame(macro.getParameter(), macro.eGet(feature));
		assertSame(macro.getParameter(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getParameter().isEmpty());
		assertSame(macro.getParameter(), macro.eGet(feature));
		assertSame(macro.getParameter(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, listParameter);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getParameter().contains(parameterValue));
		assertSame(macro.getParameter(), macro.eGet(feature));
		assertSame(macro.getParameter(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getBlock_Init();
		Macro macro = CstFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.InitSection initValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createInitSection();

		assertFalse(macro.eIsSet(feature));
		assertNull(macro.getInit());

		macro.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));

		macro.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(macro.getInit());
		assertSame(feature.getDefaultValue(), macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		Macro macro = CstFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(macro.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getStartPosition())
				.intValue());

		macro.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)macro.getStartPosition()).intValue());
		assertEquals(((Integer)macro.getStartPosition()).intValue(), ((Integer)macro.eGet(feature))
				.intValue());
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getStartPosition())
				.intValue());
		assertEquals(((Integer)macro.getStartPosition()).intValue(), ((Integer)macro.eGet(feature))
				.intValue());
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)macro.getStartPosition()).intValue());
		assertEquals(((Integer)macro.getStartPosition()).intValue(), ((Integer)macro.eGet(feature))
				.intValue());
		assertTrue(macro.eIsSet(feature));

		macro.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getStartPosition())
				.intValue());
		assertEquals(((Integer)macro.getStartPosition()).intValue(), ((Integer)macro.eGet(feature))
				.intValue());
		assertFalse(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		Macro macro = CstFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(macro.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getEndPosition())
				.intValue());

		macro.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)macro.getEndPosition()).intValue());
		assertEquals(((Integer)macro.getEndPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getEndPosition())
				.intValue());
		assertEquals(((Integer)macro.getEndPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)macro.getEndPosition()).intValue());
		assertEquals(((Integer)macro.getEndPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertTrue(macro.eIsSet(feature));

		macro.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getEndPosition())
				.intValue());
		assertEquals(((Integer)macro.getEndPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertFalse(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>name</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testName() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModuleElement_Name();
		Macro macro = CstFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		java.lang.String nameValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(macro.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), macro.getName());

		macro.setName(nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, macro.getName());
		assertEquals(macro.getName(), macro.eGet(feature));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), macro.getName());
		assertEquals(macro.getName(), macro.eGet(feature));
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, nameValue);
		assertTrue(notified);
		notified = false;
		assertEquals(nameValue, macro.getName());
		assertEquals(macro.getName(), macro.eGet(feature));
		assertTrue(macro.eIsSet(feature));

		macro.setName(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), macro.getName());
		assertEquals(macro.getName(), macro.eGet(feature));
		assertFalse(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>visibility</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testVisibility() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModuleElement_Visibility();
		Macro macro = CstFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.VisibilityKind visibilityValue = (org.eclipse.acceleo.parser.cst.VisibilityKind)feature
				.getDefaultValue();
		for (org.eclipse.acceleo.parser.cst.VisibilityKind aVisibilityKind : org.eclipse.acceleo.parser.cst.VisibilityKind.VALUES) {
			if (visibilityValue.getValue() != aVisibilityKind.getValue()) {
				visibilityValue = aVisibilityKind;
				break;
			}
		}

		assertFalse(macro.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), macro.getVisibility());

		macro.setVisibility(visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, macro.getVisibility());
		assertEquals(macro.getVisibility(), macro.eGet(feature));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), macro.getVisibility());
		assertEquals(macro.getVisibility(), macro.eGet(feature));
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, macro.getVisibility());
		assertEquals(macro.getVisibility(), macro.eGet(feature));
		assertTrue(macro.eIsSet(feature));

		macro.setVisibility(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), macro.getVisibility());
		assertEquals(macro.getVisibility(), macro.eGet(feature));
		assertFalse(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>type</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testType() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getMacro_Type();
		Macro macro = CstFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		java.lang.String typeValue = (java.lang.String)getValueDistinctFromDefault(feature);

		assertFalse(macro.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), macro.getType());

		macro.setType(typeValue);
		assertTrue(notified);
		notified = false;
		assertEquals(typeValue, macro.getType());
		assertEquals(macro.getType(), macro.eGet(feature));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), macro.getType());
		assertEquals(macro.getType(), macro.eGet(feature));
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, typeValue);
		assertTrue(notified);
		notified = false;
		assertEquals(typeValue, macro.getType());
		assertEquals(macro.getType(), macro.eGet(feature));
		assertTrue(macro.eIsSet(feature));

		macro.setType(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), macro.getType());
		assertEquals(macro.getType(), macro.eGet(feature));
		assertFalse(macro.eIsSet(feature));
	}

}
