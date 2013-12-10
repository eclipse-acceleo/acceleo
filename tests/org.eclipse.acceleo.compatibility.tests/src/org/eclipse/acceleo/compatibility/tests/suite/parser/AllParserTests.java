/*******************************************************************************
 * Copyright (c) 2008, 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.compatibility.tests.suite.parser;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.expressions.CallParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.expressions.CallSetParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.expressions.ExpressionParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.expressions.LiteralParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.expressions.NotParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.expressions.OperatorParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.expressions.ParenthesisParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.statements.CommentParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.statements.FeatureParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.statements.ForParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.statements.IfParserTest;
import org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.statements.TextParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Class for the Template parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Suite.class)
@SuiteClasses({CallParserTest.class, CallSetParserTest.class, ExpressionParserTest.class,
		LiteralParserTest.class, NotParserTest.class, OperatorParserTest.class, ParenthesisParserTest.class,
		CommentParserTest.class, FeatureParserTest.class, ForParserTest.class, IfParserTest.class,
		TextParserTest.class })
public class AllParserTests extends TestCase {
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
		return new JUnit4TestAdapter(AllParserTests.class);
	}
}
