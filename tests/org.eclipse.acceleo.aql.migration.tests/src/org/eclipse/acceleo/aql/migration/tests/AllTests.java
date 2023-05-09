/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class aggregates tests for the org.eclipse.acceleo.aql.migration.tests plug-in.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
@RunWith(Suite.class)
@SuiteClasses(value = {

		// Tests forked from A4 tests
		org.eclipse.acceleo.aql.migration.tests.conversion.AcceleoAqlTests.class,

		// Basic language tests, no gen
		org.eclipse.acceleo.aql.migration.tests.conversion.LanguageTests.class,

		// Basic evaluation tests
		org.eclipse.acceleo.aql.migration.tests.conversion.EvaluationTests.class,
		org.eclipse.acceleo.aql.migration.tests.generation.EvaluationTests.class,

		// Services tests
		org.eclipse.acceleo.aql.migration.tests.conversion.ServicesTests.class,
		org.eclipse.acceleo.aql.migration.tests.generation.ServicesTests.class,

})
public class AllTests {
}
