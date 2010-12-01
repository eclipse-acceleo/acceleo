/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.tests.suite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.textui.TestRunner;

import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceIntParameterTest;
import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceNoParametersTest;
import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceStringParameterTest;
import org.eclipse.acceleo.traceability.tests.unit.random.string.StringSourceStringStringParameterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({StringSourceNoParametersTest.class, StringSourceStringParameterTest.class,
		StringSourceIntParameterTest.class, StringSourceStringStringParameterTest.class })
public class RandomAllTests {
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
	 * Creates the {@link junit.framework.TestSuite TestSuite} for all the tests.
	 * 
	 * @return The test suite containing all the tests.
	 */
	public static Test suite() {
		return new JUnit4TestAdapter(AllTests.class);
	}
}
