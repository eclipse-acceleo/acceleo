/*******************************************************************************
 * Copyright (c) 2009, 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.evaluation;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AcceleoEvaluationVisitorFileBlockTest.class, AcceleoEvaluationVisitorForBlockTest.class,
		AcceleoEvaluationVisitorIfBlockTest.class, AcceleoEvaluationVisitorLetBlockTest.class,
		AcceleoEvaluationVisitorProtectedAreaBlockTest.class,
		AcceleoEvaluationVisitorQueryInvocationTest.class,
		AcceleoEvaluationVisitorTemplateInvocationTest.class, LineReaderTest.class })
public class AllAcceleoEvaluationVisitorTests extends TestCase {
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
		return new JUnit4TestAdapter(AllAcceleoEvaluationVisitorTests.class);
	}
}
