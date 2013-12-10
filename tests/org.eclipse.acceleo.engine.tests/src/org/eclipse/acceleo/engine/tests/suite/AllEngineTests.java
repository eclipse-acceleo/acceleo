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
package org.eclipse.acceleo.engine.tests.suite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.textui.TestRunner;

import org.eclipse.acceleo.engine.tests.unit.blocks.forBlock.ForBlockTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.ifBlock.IfBlockTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.protectedareablock.ProtectedAreaBlockTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.queryinvocation.QueryInvocationWrapping;
import org.eclipse.acceleo.engine.tests.unit.blocks.template.TemplateTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.templateinvocation.DualTemplateInvocationTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.templateinvocation.QueryPropertyInvocationTest;
import org.eclipse.acceleo.engine.tests.unit.blocks.templateinvocation.RecursiveTemplateInvocation;
import org.eclipse.acceleo.engine.tests.unit.encoding.GenerationEncodingTest;
import org.eclipse.acceleo.engine.tests.unit.environment.AcceleoNonStandardLibraryParsedTest;
import org.eclipse.acceleo.engine.tests.unit.environment.AcceleoNonStandardLibraryTest;
import org.eclipse.acceleo.engine.tests.unit.environment.AcceleoStandardLibraryParsedTest;
import org.eclipse.acceleo.engine.tests.unit.environment.AcceleoStandardLibraryTest;
import org.eclipse.acceleo.engine.tests.unit.evaluation.AllAcceleoEvaluationVisitorTests;
import org.eclipse.acceleo.engine.tests.unit.event.AcceleoListenersTest;
import org.eclipse.acceleo.engine.tests.unit.extensibility.dynamicoverride.AcceleoDynamicOverridesTest;
import org.eclipse.acceleo.engine.tests.unit.generation.AcceleoGenericEngineTest;
import org.eclipse.acceleo.engine.tests.unit.generation.AcceleoProgressMonitorTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.namesake.NamesakeGuardResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.namesake.ParameterTypeNarrowingResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.namesake.SimpleNamesakeResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.override.OverrideGuardResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.override.OverrideParameterTypeNarrowingResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.resolution.override.SimpleOverridesResolutionTest;
import org.eclipse.acceleo.engine.tests.unit.service.facade.AcceleoServiceTest;
import org.eclipse.acceleo.engine.tests.unit.service.java.AcceleoJavaServicesTest;
import org.eclipse.acceleo.engine.tests.unit.utils.AcceleoEnginePluginTest;
import org.eclipse.acceleo.engine.tests.unit.utils.MessagesTest;
import org.eclipse.acceleo.engine.tests.unit.variables.SelfTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This suite will launch all the tests defined for the Acceleo engine.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@RunWith(Suite.class)
@SuiteClasses({ForBlockTest.class, IfBlockTest.class, ProtectedAreaBlockTest.class, TemplateTest.class,
		DualTemplateInvocationTest.class, QueryPropertyInvocationTest.class,
		RecursiveTemplateInvocation.class, QueryInvocationWrapping.class, GenerationEncodingTest.class,
		AcceleoGenericEngineTest.class, AcceleoProgressMonitorTest.class,
		AllAcceleoEvaluationVisitorTests.class, AcceleoServiceTest.class, AcceleoJavaServicesTest.class,
		AcceleoListenersTest.class, SimpleNamesakeResolutionTest.class,
		ParameterTypeNarrowingResolutionTest.class, NamesakeGuardResolutionTest.class,
		SimpleOverridesResolutionTest.class, OverrideParameterTypeNarrowingResolutionTest.class,
		OverrideGuardResolutionTest.class, SelfTest.class, MessagesTest.class, AcceleoEnginePluginTest.class,
		AcceleoStandardLibraryTest.class, AcceleoStandardLibraryParsedTest.class,
		AcceleoNonStandardLibraryTest.class, AcceleoNonStandardLibraryParsedTest.class,
		AcceleoDynamicOverridesTest.class })
public class AllEngineTests {
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
		return new JUnit4TestAdapter(AllEngineTests.class);
	}
}
