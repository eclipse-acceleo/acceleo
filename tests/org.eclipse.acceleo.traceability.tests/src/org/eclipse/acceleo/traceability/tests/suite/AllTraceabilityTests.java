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
package org.eclipse.acceleo.traceability.tests.suite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.textui.TestRunner;

import org.eclipse.acceleo.traceability.tests.unit.block.AcceleoTraceabilityBlockTests;
import org.eclipse.acceleo.traceability.tests.unit.library.AcceleoTraceabilityLibraryOclTests;
import org.eclipse.acceleo.traceability.tests.unit.library.AcceleoTraceabilityLibraryStringTests;
import org.eclipse.acceleo.traceability.tests.unit.model.AcceleoTraceabilityModelTests;
import org.eclipse.acceleo.traceability.tests.unit.query.AcceleoTraceabilityQueryTests;
import org.eclipse.acceleo.traceability.tests.unit.template.AcceleoTraceabilityTemplateTests;
import org.eclipse.acceleo.traceability.tests.unit.text.AcceleoTraceabilityTextTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This suite will launch all the tests defined for the Acceleo traceability.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
@RunWith(Suite.class)
@SuiteClasses({AcceleoTraceabilityTextTests.class, AcceleoTraceabilityTemplateTests.class,
		AcceleoTraceabilityQueryTests.class, AcceleoTraceabilityModelTests.class,
		AcceleoTraceabilityBlockTests.class, AcceleoTraceabilityLibraryStringTests.class,
		AcceleoTraceabilityLibraryOclTests.class })
public class AllTraceabilityTests {
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
		return new JUnit4TestAdapter(AllTraceabilityTests.class);
	}
}
