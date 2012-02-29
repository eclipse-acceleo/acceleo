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
package org.eclipse.acceleo.compatibility.tests.suite.migration;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.acceleo.compatibility.tests.unit.migration.AdaptTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.ArgsTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.FileScriptParameterTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.FilterTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.IsScriptTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.NavigationTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.PostTrimTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.ServiceTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.SizeTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.getPropertyTest;

/**
 * This will call all tests for the migration from Acceleo 2 to Acceleo 3.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class MigrationTests extends TestCase {
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
		final TestSuite suite = new TestSuite("Acceleo Migration Test Suite");

		suite.addTestSuite(FileScriptParameterTest.class);
		suite.addTestSuite(IsScriptTest.class);
		suite.addTestSuite(PostTrimTest.class);
		suite.addTestSuite(FilterTest.class);
		suite.addTestSuite(getPropertyTest.class);
		suite.addTestSuite(ArgsTest.class);
		suite.addTestSuite(SizeTest.class);
		suite.addTestSuite(ServiceTest.class);
		suite.addTestSuite(NavigationTest.class);
		suite.addTestSuite(AdaptTest.class);

		// Those unit tests do not work now.
		// suite.addTestSuite(SelectTest.class);
		// suite.addTestSuite(UserCodeTest.class);
		// suite.addTestSuite(ImportScriptTest.class);

		return suite;
	}
}
