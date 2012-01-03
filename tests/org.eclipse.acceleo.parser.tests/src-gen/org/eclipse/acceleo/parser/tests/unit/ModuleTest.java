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
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Tests the behavior of the {@link Module} class.
 * 
 * @generated
 */
public class ModuleTest extends AbstractCstTest {
	/**
	 * Tests the behavior of reference <code>input</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testInput() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getModule_Input();
		Module module = CstFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.TypedModel inputValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createTypedModel();
		List<org.eclipse.acceleo.parser.cst.TypedModel> listInput = new ArrayList<org.eclipse.acceleo.parser.cst.TypedModel>(
				1);
		listInput.add(inputValue);

		assertFalse(module.eIsSet(feature));
		assertTrue(module.getInput().isEmpty());

		module.getInput().add(inputValue);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getInput().contains(inputValue));
		assertSame(module.getInput(), module.eGet(feature));
		assertSame(module.getInput(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getInput().isEmpty());
		assertSame(module.getInput(), module.eGet(feature));
		assertSame(module.getInput(), module.eGet(feature, false));
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, listInput);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getInput().contains(inputValue));
		assertSame(module.getInput(), module.eGet(feature));
		assertSame(module.getInput(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>ownedModuleElement</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testOwnedModuleElement() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getModule_OwnedModuleElement();
		Module module = CstFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModuleElement ownedModuleElementValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createComment();
		List<org.eclipse.acceleo.parser.cst.ModuleElement> listOwnedModuleElement = new ArrayList<org.eclipse.acceleo.parser.cst.ModuleElement>(
				1);
		listOwnedModuleElement.add(ownedModuleElementValue);

		assertFalse(module.eIsSet(feature));
		assertTrue(module.getOwnedModuleElement().isEmpty());

		module.getOwnedModuleElement().add(ownedModuleElementValue);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getOwnedModuleElement().contains(ownedModuleElementValue));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getOwnedModuleElement().isEmpty());
		assertSame(module.getOwnedModuleElement(), module.eGet(feature));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature, false));
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, listOwnedModuleElement);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getOwnedModuleElement().contains(ownedModuleElementValue));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>extends</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testExtends() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getModule_Extends();
		Module module = CstFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModuleExtendsValue extendsValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModuleExtendsValue();
		List<org.eclipse.acceleo.parser.cst.ModuleExtendsValue> listExtends = new ArrayList<org.eclipse.acceleo.parser.cst.ModuleExtendsValue>(
				1);
		listExtends.add(extendsValue);

		assertFalse(module.eIsSet(feature));
		assertTrue(module.getExtends().isEmpty());

		module.getExtends().add(extendsValue);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getExtends().contains(extendsValue));
		assertSame(module.getExtends(), module.eGet(feature));
		assertSame(module.getExtends(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getExtends().isEmpty());
		assertSame(module.getExtends(), module.eGet(feature));
		assertSame(module.getExtends(), module.eGet(feature, false));
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, listExtends);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getExtends().contains(extendsValue));
		assertSame(module.getExtends(), module.eGet(feature));
		assertSame(module.getExtends(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>imports</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testImports() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE.getModule_Imports();
		Module module = CstFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.parser.cst.ModuleImportsValue importsValue = org.eclipse.acceleo.parser.cst.CstFactory.eINSTANCE
				.createModuleImportsValue();
		List<org.eclipse.acceleo.parser.cst.ModuleImportsValue> listImports = new ArrayList<org.eclipse.acceleo.parser.cst.ModuleImportsValue>(
				1);
		listImports.add(importsValue);

		assertFalse(module.eIsSet(feature));
		assertTrue(module.getImports().isEmpty());

		module.getImports().add(importsValue);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getImports().contains(importsValue));
		assertSame(module.getImports(), module.eGet(feature));
		assertSame(module.getImports(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getImports().isEmpty());
		assertSame(module.getImports(), module.eGet(feature));
		assertSame(module.getImports(), module.eGet(feature, false));
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, listImports);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getImports().contains(importsValue));
		assertSame(module.getImports(), module.eGet(feature));
		assertSame(module.getImports(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_StartPosition();
		Module module = CstFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(module.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)module.getStartPosition())
				.intValue());

		module.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)module.getStartPosition()).intValue());
		assertEquals(((Integer)module.getStartPosition()).intValue(), ((Integer)module.eGet(feature))
				.intValue());
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)module.getStartPosition())
				.intValue());
		assertEquals(((Integer)module.getStartPosition()).intValue(), ((Integer)module.eGet(feature))
				.intValue());
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)module.getStartPosition()).intValue());
		assertEquals(((Integer)module.getStartPosition()).intValue(), ((Integer)module.eGet(feature))
				.intValue());
		assertTrue(module.eIsSet(feature));

		module.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)module.getStartPosition())
				.intValue());
		assertEquals(((Integer)module.getStartPosition()).intValue(), ((Integer)module.eGet(feature))
				.intValue());
		assertFalse(module.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.acceleo.parser.cst.CstPackage.eINSTANCE
				.getCSTNode_EndPosition();
		Module module = CstFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(module.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)module.getEndPosition())
				.intValue());

		module.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)module.getEndPosition()).intValue());
		assertEquals(((Integer)module.getEndPosition()).intValue(), ((Integer)module.eGet(feature))
				.intValue());
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)module.getEndPosition())
				.intValue());
		assertEquals(((Integer)module.getEndPosition()).intValue(), ((Integer)module.eGet(feature))
				.intValue());
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)module.getEndPosition()).intValue());
		assertEquals(((Integer)module.getEndPosition()).intValue(), ((Integer)module.eGet(feature))
				.intValue());
		assertTrue(module.eIsSet(feature));

		module.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)module.getEndPosition())
				.intValue());
		assertEquals(((Integer)module.getEndPosition()).intValue(), ((Integer)module.eGet(feature))
				.intValue());
		assertFalse(module.eIsSet(feature));
	}

}
