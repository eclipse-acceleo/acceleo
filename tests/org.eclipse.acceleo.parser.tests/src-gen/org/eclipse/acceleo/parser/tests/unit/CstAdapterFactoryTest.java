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

import junit.framework.TestCase;

import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.FileBlock;
import org.eclipse.acceleo.parser.cst.ForBlock;
import org.eclipse.acceleo.parser.cst.IfBlock;
import org.eclipse.acceleo.parser.cst.InitSection;
import org.eclipse.acceleo.parser.cst.LetBlock;
import org.eclipse.acceleo.parser.cst.Macro;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.ProtectedAreaBlock;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateExpression;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.TraceBlock;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.acceleo.parser.cst.util.CstAdapterFactory;

/*
 * TODO This is but a skeleton for the tests of CstAdapterFactory.
 * Set as "generated NOT" and override each test if you overrode the default generated
 * behavior.
 */
/**
 * Tests the behavior of the {@link CstAdapterFactory generated adapter factory} for package cst.
 * 
 * @generated
 */
public class CstAdapterFactoryTest extends TestCase {
	/**
	 * Ensures that creating adapters for {@link CSTNode} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateCSTNodeAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createCSTNodeAdapter());
	}

	/**
	 * Ensures that creating adapters for {@link Module} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateModuleAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createModuleAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createModule()));
	}

	/**
	 * Ensures that creating adapters for {@link ModuleExtendsValue} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateModuleExtendsValueAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createModuleExtendsValueAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createModuleExtendsValue()));
	}

	/**
	 * Ensures that creating adapters for {@link ModuleImportsValue} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateModuleImportsValueAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createModuleImportsValueAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createModuleImportsValue()));
	}

	/**
	 * Ensures that creating adapters for {@link TypedModel} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTypedModelAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createTypedModelAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createTypedModel()));
	}

	/**
	 * Ensures that creating adapters for {@link ModuleElement} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateModuleElementAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createModuleElementAdapter());
		assertNull(adapterFactory.createAdapter(new org.eclipse.acceleo.parser.cst.impl.ModuleElementImpl() {
			// Empty implementation
		}));
	}

	/**
	 * Ensures that creating adapters for {@link Comment} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateCommentAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createCommentAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createComment()));
	}

	/**
	 * Ensures that creating adapters for {@link Template} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTemplateAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createTemplateAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createTemplate()));
	}

	/**
	 * Ensures that creating adapters for {@link TemplateOverridesValue} can be done through the
	 * AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTemplateOverridesValueAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createTemplateOverridesValueAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createTemplateOverridesValue()));
	}

	/**
	 * Ensures that creating adapters for {@link Variable} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateVariableAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createVariableAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createVariable()));
	}

	/**
	 * Ensures that creating adapters for {@link TemplateExpression} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTemplateExpressionAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createTemplateExpressionAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createTemplateExpression()));
	}

	/**
	 * Ensures that creating adapters for {@link ModelExpression} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateModelExpressionAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createModelExpressionAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createModelExpression()));
	}

	/**
	 * Ensures that creating adapters for {@link TextExpression} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTextExpressionAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createTextExpressionAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createTextExpression()));
	}

	/**
	 * Ensures that creating adapters for {@link Block} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateBlockAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createBlockAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link InitSection} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateInitSectionAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createInitSectionAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createInitSection()));
	}

	/**
	 * Ensures that creating adapters for {@link ProtectedAreaBlock} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateProtectedAreaBlockAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createProtectedAreaBlockAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createProtectedAreaBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link ForBlock} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateForBlockAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createForBlockAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createForBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link IfBlock} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateIfBlockAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createIfBlockAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createIfBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link LetBlock} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateLetBlockAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createLetBlockAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createLetBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link FileBlock} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateFileBlockAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createFileBlockAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createFileBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link TraceBlock} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateTraceBlockAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createTraceBlockAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createTraceBlock()));
	}

	/**
	 * Ensures that creating adapters for {@link Macro} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateMacroAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createMacroAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createMacro()));
	}

	/**
	 * Ensures that creating adapters for {@link Query} can be done through the AdapterFactory.
	 * 
	 * @generated
	 */
	public void testCreateQueryAdapter() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertNull(adapterFactory.createQueryAdapter());
		assertNull(adapterFactory.createAdapter(CstFactory.eINSTANCE.createQuery()));
	}

	/**
	 * Ensures that the AdapterFactory knows all classes of package cst.
	 * 
	 * @generated
	 */
	public void testIsFactoryForType() {
		CstAdapterFactory adapterFactory = new CstAdapterFactory();
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createModule()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createModuleExtendsValue()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createModuleImportsValue()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createTypedModel()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createComment()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createTemplate()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createTemplateOverridesValue()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createVariable()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createTemplateExpression()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createModelExpression()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createTextExpression()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createBlock()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createInitSection()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createProtectedAreaBlock()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createForBlock()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createIfBlock()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createLetBlock()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createFileBlock()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createTraceBlock()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createMacro()));
		assertTrue(adapterFactory.isFactoryForType(CstFactory.eINSTANCE.createQuery()));
		assertTrue(adapterFactory.isFactoryForType(CstPackage.eINSTANCE));
		org.eclipse.emf.ecore.EClass eClass = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEClass();
		assertFalse(adapterFactory.isFactoryForType(eClass));
		assertFalse(adapterFactory.isFactoryForType(new Object()));
	}
}
