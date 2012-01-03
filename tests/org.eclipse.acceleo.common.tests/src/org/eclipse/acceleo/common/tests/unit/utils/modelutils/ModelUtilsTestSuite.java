/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.tests.unit.utils.modelutils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.acceleo.common.utils.ModelUtils;

/**
 * Launches all the JUnit tests for the {@link ModelUtils} class.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class ModelUtilsTestSuite extends TestCase {
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
		final TestSuite suite = new TestSuite("Tests for the ModelUtils behavior");
		suite.addTestSuite(AttachResourceTest.class);
		suite.addTestSuite(GetModelsFromTest.class);
		suite.addTestSuite(LoadFromFileTest.class);
		suite.addTestSuite(LoadFromInputStreamTest.class);
		suite.addTestSuite(LoadFromStringTest.class);
		suite.addTestSuite(SaveSerializeTest.class);
		return suite;
	}
}
