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
package org.eclipse.acceleo.parser.tests.suite;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.acceleo.parser.tests.unit.BlockTest;
import org.eclipse.acceleo.parser.tests.unit.CommentTest;
import org.eclipse.acceleo.parser.tests.unit.CstAdapterFactoryTest;
import org.eclipse.acceleo.parser.tests.unit.CstFactoryTest;
import org.eclipse.acceleo.parser.tests.unit.CstSwitchTest;
import org.eclipse.acceleo.parser.tests.unit.FileBlockTest;
import org.eclipse.acceleo.parser.tests.unit.ForBlockTest;
import org.eclipse.acceleo.parser.tests.unit.IfBlockTest;
import org.eclipse.acceleo.parser.tests.unit.InitSectionTest;
import org.eclipse.acceleo.parser.tests.unit.LetBlockTest;
import org.eclipse.acceleo.parser.tests.unit.MacroTest;
import org.eclipse.acceleo.parser.tests.unit.ModelExpressionTest;
import org.eclipse.acceleo.parser.tests.unit.ModuleExtendsValueTest;
import org.eclipse.acceleo.parser.tests.unit.ModuleImportsValueTest;
import org.eclipse.acceleo.parser.tests.unit.ModuleTest;
import org.eclipse.acceleo.parser.tests.unit.OpenModeKindTest;
import org.eclipse.acceleo.parser.tests.unit.ProtectedAreaBlockTest;
import org.eclipse.acceleo.parser.tests.unit.QueryTest;
import org.eclipse.acceleo.parser.tests.unit.TemplateOverridesValueTest;
import org.eclipse.acceleo.parser.tests.unit.TemplateTest;
import org.eclipse.acceleo.parser.tests.unit.TextExpressionTest;
import org.eclipse.acceleo.parser.tests.unit.TraceBlockTest;
import org.eclipse.acceleo.parser.tests.unit.TypedModelTest;
import org.eclipse.acceleo.parser.tests.unit.VariableTest;
import org.eclipse.acceleo.parser.tests.unit.VisibilityKindTest;

/**
 * This test suite allows clients to launch all tests generated for package cst at once.
 * 
 * @generated
 */
public class CstTestSuite extends TestCase {
	/**
	 * Standalone launcher for package cst's tests.
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * This will return a suite populated with all generated tests for package cst.
	 * 
	 * @generated
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite("Cst test suite"); //$NON-NLS-1$

		suite.addTestSuite(ModuleTest.class);
		suite.addTestSuite(ModuleExtendsValueTest.class);
		suite.addTestSuite(ModuleImportsValueTest.class);
		suite.addTestSuite(TypedModelTest.class);
		suite.addTestSuite(CommentTest.class);
		suite.addTestSuite(TemplateTest.class);
		suite.addTestSuite(TemplateOverridesValueTest.class);
		suite.addTestSuite(VariableTest.class);
		suite.addTestSuite(ModelExpressionTest.class);
		suite.addTestSuite(TextExpressionTest.class);
		suite.addTestSuite(BlockTest.class);
		suite.addTestSuite(InitSectionTest.class);
		suite.addTestSuite(ProtectedAreaBlockTest.class);
		suite.addTestSuite(ForBlockTest.class);
		suite.addTestSuite(IfBlockTest.class);
		suite.addTestSuite(LetBlockTest.class);
		suite.addTestSuite(FileBlockTest.class);
		suite.addTestSuite(TraceBlockTest.class);
		suite.addTestSuite(MacroTest.class);
		suite.addTestSuite(QueryTest.class);
		suite.addTestSuite(VisibilityKindTest.class);
		suite.addTestSuite(OpenModeKindTest.class);
		suite.addTestSuite(CstAdapterFactoryTest.class);
		suite.addTestSuite(CstFactoryTest.class);
		suite.addTestSuite(CstSwitchTest.class);

		return suite;
	}
}
