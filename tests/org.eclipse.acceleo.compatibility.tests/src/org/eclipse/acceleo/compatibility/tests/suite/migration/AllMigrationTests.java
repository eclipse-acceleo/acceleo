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
package org.eclipse.acceleo.compatibility.tests.suite.migration;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.textui.TestRunner;

import org.eclipse.acceleo.compatibility.tests.unit.migration.AdaptTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.ArgsTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.FileScriptParameterTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.FilterTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.ImportScriptTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.IsScriptTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.NavigationTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.PostTrimTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.SelectTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.ServiceTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.SizeTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.UserCodeTest;
import org.eclipse.acceleo.compatibility.tests.unit.migration.getPropertyTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This will call all tests for the migration from Acceleo 2 to Acceleo 3.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Suite.class)
@SuiteClasses({FileScriptParameterTest.class, IsScriptTest.class, PostTrimTest.class, FilterTest.class,
		getPropertyTest.class, ArgsTest.class, SizeTest.class, ServiceTest.class, NavigationTest.class,
		AdaptTest.class, SelectTest.class, UserCodeTest.class, ImportScriptTest.class })
public class AllMigrationTests {
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
		return new JUnit4TestAdapter(AllMigrationTests.class);
	}
}
