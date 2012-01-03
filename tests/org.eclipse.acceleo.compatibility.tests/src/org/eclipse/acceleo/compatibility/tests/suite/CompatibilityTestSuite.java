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

import org.eclipse.acceleo.compatibility.tests.suite.migration.MigrationTests;

/**
 * Allows us to launch all of the Parser tests at once. This suite will launch each and every possible test,
 * including those we need to disable on the eclipse build server.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CompatibilityTestSuite extends TestCase {
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
		// use the same test suite as what's launched on the build server, but add disabled tests to it.
		final TestSuite suite = (TestSuite)AllTests.suite();

		// Migration
		suite.addTest(MigrationTests.suite());

		return suite;
	}
}
