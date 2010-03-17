/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
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
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.acceleo.parser.tests.AcceleoParserTests;
import org.eclipse.acceleo.parser.tests.cst.CSTParserBlockTests;
import org.eclipse.acceleo.parser.tests.cst.CSTParserTests;
import org.eclipse.acceleo.parser.tests.cst.utils.FileContentTests;
import org.eclipse.acceleo.parser.tests.cst.utils.SequenceTests;

/**
 * This suite will launch all the tests defined for the Acceleo parser.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
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
		final TestSuite suite = new TestSuite("Acceleo parser test suite");
		suite.addTestSuite(AcceleoParserTests.class);
		suite.addTestSuite(CSTParserBlockTests.class);
		suite.addTestSuite(CSTParserTests.class);
		suite.addTestSuite(FileContentTests.class);
		suite.addTestSuite(SequenceTests.class);

		// generated tests
		suite.addTest(CstTestSuite.suite());

		return suite;
	}

}
