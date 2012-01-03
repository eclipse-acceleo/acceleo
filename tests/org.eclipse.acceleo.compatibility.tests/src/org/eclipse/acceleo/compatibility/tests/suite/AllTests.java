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
package org.eclipse.acceleo.compatibility.tests.suite;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.acceleo.compatibility.tests.suite.parser.ParserTests;

/**
 * Allows us to launch all of the Parser tests at once.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AllTests extends TestCase {
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
		final TestSuite suite = new TestSuite("Acceleo Compatibility Test Suite");

		// Parser
		suite.addTest(ParserTests.suite());
		// Migration
		// For some reason, these fail on the build machine
		// suite.addTest(MigrationTests.suite());

		return suite;
	}
}
