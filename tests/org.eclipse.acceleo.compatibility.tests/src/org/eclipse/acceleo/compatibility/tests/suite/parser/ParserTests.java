/*
 * Copyright (c) 2005, 2012 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 */
package org.eclipse.acceleo.compatibility.tests.suite.parser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
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

/**
 * Test Class for the Template parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class ParserTests extends TestCase {
	/**
	 * Launches the test with the given arguments.
	 * 
	 * @param args
	 *            Arguments of the testCase.
	 */
	public static void main(final String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * Creates the {@link junit.framework.TestSuite TestSuite} for all the tests.
	 * 
	 * @return The testsuite containing all the tests
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite("Acceleo Parser Test Suite");

		// Expression parsing
		suite.addTestSuite(CallParserTest.class);
		suite.addTestSuite(CallSetParserTest.class);
		suite.addTestSuite(ExpressionParserTest.class);
		suite.addTestSuite(LiteralParserTest.class);
		suite.addTestSuite(NotParserTest.class);
		suite.addTestSuite(OperatorParserTest.class);
		suite.addTestSuite(ParenthesisParserTest.class);

		// Statement parsing
		suite.addTestSuite(CommentParserTest.class);
		suite.addTestSuite(FeatureParserTest.class);
		suite.addTestSuite(ForParserTest.class);
		suite.addTestSuite(IfParserTest.class);
		suite.addTestSuite(TextParserTest.class);

		return suite;
	}
}
