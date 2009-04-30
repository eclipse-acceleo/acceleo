/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.suite;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.acceleo.engine.tests.unit.blocks.forBlock.ForBlockTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.ifBlock.IfBlockTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.protectedareablock.ProtectedAreaBlockTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.template.TemplateTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.templateinvocation.DualTemplateInvocationTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.templateinvocation.QueryPropertyInvocationTest;
import org.eclipse.acceleo.engine.tests.unit.engine.AcceleoGenericEngineTest;
import org.eclipse.acceleo.engine.tests.unit.environment.AcceleoNonStandardLibraryTest;
import org.eclipse.acceleo.engine.tests.unit.environment.AcceleoStandardLibraryParsedTest;
import org.eclipse.acceleo.engine.tests.unit.environment.AcceleoStandardLibraryTest;
import org.eclipse.acceleo.engine.tests.unit.evaluation.AcceleoEvaluationVisitorTestSuite;
import org.eclipse.acceleo.engine.tests.unit.listeners.AcceleoListenersTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.namesake.NamesakeGuardResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.namesake.ParameterTypeNarrowingResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.namesake.SimpleNamesakeResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.override.OverrideGuardResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.override.OverrideParameterTypeNarrowingResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.override.SimpleOverridesResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.utils.AcceleoEnginePluginTest;
import org.eclipse.acceleo.engine.tests.unit.utils.MessagesTest;
import org.eclipse.acceleo.engine.tests.unit.variables.SelfTest;

/**
 * This suite will launch all the tests defined for the Acceleo engine.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AllTests extends TestCase {
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
		final TestSuite suite = new TestSuite("Acceleo engine test suite"); //$NON-NLS-1$

		// Blocks
		suite.addTestSuite(ForBlockTest.class);
		suite.addTestSuite(IfBlockTest.class);
		suite.addTestSuite(ProtectedAreaBlockTest.class);
		suite.addTestSuite(TemplateTest.class);
		suite.addTestSuite(DualTemplateInvocationTest.class);
		suite.addTestSuite(QueryPropertyInvocationTest.class);

		// Engine
		suite.addTestSuite(AcceleoGenericEngineTest.class);

		// Evaluation visitor
		suite.addTest(AcceleoEvaluationVisitorTestSuite.suite());

		// Listeners
		suite.addTestSuite(AcceleoListenersTest.class);

		// Namesakes
		suite.addTestSuite(SimpleNamesakeResolutionTest.class);
		suite.addTestSuite(ParameterTypeNarrowingResolutionTest.class);
		suite.addTestSuite(NamesakeGuardResolutionTest.class);

		// overrides
		suite.addTestSuite(SimpleOverridesResolutionTest.class);
		suite.addTestSuite(OverrideParameterTypeNarrowingResolutionTest.class);
		suite.addTestSuite(OverrideGuardResolutionTest.class);

		// variables
		suite.addTestSuite(SelfTest.class);

		// utilities
		suite.addTestSuite(MessagesTest.class);
		suite.addTestSuite(AcceleoEnginePluginTest.class);

		// operation libraries
		suite.addTestSuite(AcceleoStandardLibraryTest.class);
		suite.addTestSuite(AcceleoStandardLibraryParsedTest.class);
		suite.addTestSuite(AcceleoNonStandardLibraryTest.class);

		return suite;
	}
}
