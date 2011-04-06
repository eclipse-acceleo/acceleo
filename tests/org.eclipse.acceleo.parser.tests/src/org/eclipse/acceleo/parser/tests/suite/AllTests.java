/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests.suite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.textui.TestRunner;

import org.eclipse.acceleo.parser.tests.AcceleoParserMessagesTests;
import org.eclipse.acceleo.parser.tests.AcceleoParserTests;
import org.eclipse.acceleo.parser.tests.ast.ASTContextForTests;
import org.eclipse.acceleo.parser.tests.ast.ASTContextLetTests;
import org.eclipse.acceleo.parser.tests.ast.ASTDocumentationTests;
import org.eclipse.acceleo.parser.tests.ast.ASTModelExpressionsTests;
import org.eclipse.acceleo.parser.tests.ast.ASTOperationTests;
import org.eclipse.acceleo.parser.tests.ast.ASTParserMacroTests;
import org.eclipse.acceleo.parser.tests.ast.ASTParserModuleTests;
import org.eclipse.acceleo.parser.tests.ast.ASTParserQueryTests;
import org.eclipse.acceleo.parser.tests.ast.ASTParserTemplateTests;
import org.eclipse.acceleo.parser.tests.ast.ASTScopeForTests;
import org.eclipse.acceleo.parser.tests.ast.ASTScopeLetTests;
import org.eclipse.acceleo.parser.tests.ast.ASTScopeQueryTests;
import org.eclipse.acceleo.parser.tests.ast.ASTScopeTemplateTests;
import org.eclipse.acceleo.parser.tests.cst.CSTParserBlockTests;
import org.eclipse.acceleo.parser.tests.cst.CSTParserModuleTests;
import org.eclipse.acceleo.parser.tests.cst.CSTParserQueryTests;
import org.eclipse.acceleo.parser.tests.cst.CSTParserTemplateTests;
import org.eclipse.acceleo.parser.tests.cst.CSTParserTests;
import org.eclipse.acceleo.parser.tests.cst.utils.FileContentTests;
import org.eclipse.acceleo.parser.tests.cst.utils.SequenceTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This suite will launch all the tests defined for the Acceleo parser.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
@RunWith(Suite.class)
@SuiteClasses({AcceleoParserTests.class, CSTParserBlockTests.class, CSTParserModuleTests.class,
		CSTParserQueryTests.class, CSTParserTemplateTests.class, CSTParserTests.class,
		FileContentTests.class, SequenceTests.class, ASTParserModuleTests.class, ASTOperationTests.class,
		ASTParserQueryTests.class, ASTParserTemplateTests.class, ASTParserMacroTests.class,
		ASTModelExpressionsTests.class, ASTDocumentationTests.class, AcceleoParserMessagesTests.class,
		ASTScopeForTests.class, ASTScopeLetTests.class, ASTScopeQueryTests.class,
		ASTScopeTemplateTests.class, ASTContextForTests.class, ASTContextLetTests.class, })
public class AllTests {

	/**
	 * Launches the test with the given arguments.
	 * 
	 * @param args
	 *            Arguments of the testCase.
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * Creates the {@link junit.framework.TestSuite TestSuite} for all the test.
	 * 
	 * @return The test suite containing all the tests
	 */
	public static Test suite() {
		return new JUnit4TestAdapter(AllTests.class);
	}

}
