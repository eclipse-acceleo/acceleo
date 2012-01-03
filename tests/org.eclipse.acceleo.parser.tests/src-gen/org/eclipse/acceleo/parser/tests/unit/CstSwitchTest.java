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
import org.eclipse.acceleo.parser.cst.util.CstSwitch;

/*
 * TODO This is but a skeleton for the tests of CstSwitch.
 * Set as "generated NOT" and override each test if you overrode the default generated
 * behavior.
 */
/**
 * Tests the behavior of the {@link CstSwitch generated switch} for package cst.
 * 
 * @generated
 */
public class CstSwitchTest extends TestCase {
	/**
	 * Ensures that the generated switch knows {@link Module}.
	 * 
	 * @generated
	 */
	public void testCaseModule() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseModule(CstFactory.eINSTANCE.createModule()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createModule()));
	}

	/**
	 * Ensures that the generated switch knows {@link ModuleExtendsValue}.
	 * 
	 * @generated
	 */
	public void testCaseModuleExtendsValue() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseModuleExtendsValue(CstFactory.eINSTANCE.createModuleExtendsValue()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createModuleExtendsValue()));
	}

	/**
	 * Ensures that the generated switch knows {@link ModuleImportsValue}.
	 * 
	 * @generated
	 */
	public void testCaseModuleImportsValue() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseModuleImportsValue(CstFactory.eINSTANCE.createModuleImportsValue()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createModuleImportsValue()));
	}

	/**
	 * Ensures that the generated switch knows {@link TypedModel}.
	 * 
	 * @generated
	 */
	public void testCaseTypedModel() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseTypedModel(CstFactory.eINSTANCE.createTypedModel()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createTypedModel()));
	}

	/**
	 * Ensures that the generated switch knows {@link ModuleElement}.
	 * 
	 * @generated
	 */
	public void testCaseModuleElement() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseModuleElement(new org.eclipse.acceleo.parser.cst.impl.ModuleElementImpl() {
			// Empty implementation
		}));
		assertNull(cstswitch.doSwitch(new org.eclipse.acceleo.parser.cst.impl.ModuleElementImpl() {
			// Empty implementation
		}));
	}

	/**
	 * Ensures that the generated switch knows {@link Comment}.
	 * 
	 * @generated
	 */
	public void testCaseComment() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseComment(CstFactory.eINSTANCE.createComment()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createComment()));
	}

	/**
	 * Ensures that the generated switch knows {@link Template}.
	 * 
	 * @generated
	 */
	public void testCaseTemplate() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseTemplate(CstFactory.eINSTANCE.createTemplate()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createTemplate()));
	}

	/**
	 * Ensures that the generated switch knows {@link TemplateOverridesValue}.
	 * 
	 * @generated
	 */
	public void testCaseTemplateOverridesValue() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseTemplateOverridesValue(CstFactory.eINSTANCE.createTemplateOverridesValue()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createTemplateOverridesValue()));
	}

	/**
	 * Ensures that the generated switch knows {@link Variable}.
	 * 
	 * @generated
	 */
	public void testCaseVariable() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseVariable(CstFactory.eINSTANCE.createVariable()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createVariable()));
	}

	/**
	 * Ensures that the generated switch knows {@link TemplateExpression}.
	 * 
	 * @generated
	 */
	public void testCaseTemplateExpression() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseTemplateExpression(CstFactory.eINSTANCE.createTemplateExpression()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createTemplateExpression()));
	}

	/**
	 * Ensures that the generated switch knows {@link ModelExpression}.
	 * 
	 * @generated
	 */
	public void testCaseModelExpression() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseModelExpression(CstFactory.eINSTANCE.createModelExpression()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createModelExpression()));
	}

	/**
	 * Ensures that the generated switch knows {@link TextExpression}.
	 * 
	 * @generated
	 */
	public void testCaseTextExpression() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseTextExpression(CstFactory.eINSTANCE.createTextExpression()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createTextExpression()));
	}

	/**
	 * Ensures that the generated switch knows {@link Block}.
	 * 
	 * @generated
	 */
	public void testCaseBlock() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseBlock(CstFactory.eINSTANCE.createBlock()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link InitSection}.
	 * 
	 * @generated
	 */
	public void testCaseInitSection() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseInitSection(CstFactory.eINSTANCE.createInitSection()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createInitSection()));
	}

	/**
	 * Ensures that the generated switch knows {@link ProtectedAreaBlock}.
	 * 
	 * @generated
	 */
	public void testCaseProtectedAreaBlock() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseProtectedAreaBlock(CstFactory.eINSTANCE.createProtectedAreaBlock()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createProtectedAreaBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link ForBlock}.
	 * 
	 * @generated
	 */
	public void testCaseForBlock() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseForBlock(CstFactory.eINSTANCE.createForBlock()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createForBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link IfBlock}.
	 * 
	 * @generated
	 */
	public void testCaseIfBlock() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseIfBlock(CstFactory.eINSTANCE.createIfBlock()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createIfBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link LetBlock}.
	 * 
	 * @generated
	 */
	public void testCaseLetBlock() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseLetBlock(CstFactory.eINSTANCE.createLetBlock()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createLetBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link FileBlock}.
	 * 
	 * @generated
	 */
	public void testCaseFileBlock() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseFileBlock(CstFactory.eINSTANCE.createFileBlock()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createFileBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link TraceBlock}.
	 * 
	 * @generated
	 */
	public void testCaseTraceBlock() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseTraceBlock(CstFactory.eINSTANCE.createTraceBlock()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createTraceBlock()));
	}

	/**
	 * Ensures that the generated switch knows {@link Macro}.
	 * 
	 * @generated
	 */
	public void testCaseMacro() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseMacro(CstFactory.eINSTANCE.createMacro()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createMacro()));
	}

	/**
	 * Ensures that the generated switch knows {@link Query}.
	 * 
	 * @generated
	 */
	public void testCaseQuery() {
		CstSwitch<?> cstswitch = new CstSwitch<Object>();
		assertNull(cstswitch.caseQuery(CstFactory.eINSTANCE.createQuery()));
		assertNull(cstswitch.doSwitch(CstFactory.eINSTANCE.createQuery()));
	}
}
