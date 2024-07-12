/*******************************************************************************
 * Copyright (c) 2017, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.tests.conversion;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.acceleo.aql.migration.tests.utils.AbstractMigrationTestSuite;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests the conversion of tests related to the migration documentation that should also be tested for
 * evaluation.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public class EvaluationTests extends AbstractMigrationTestSuite {

	public static final String[] EXCLUDED_TESTS = new String[] {
			// Excluded tests:
			"templateVariableInitialization", // TODO (declare let inside of the template)
			"protectedAreaBlock", // TODO serialization issues
			"elseletBlock", // UNSUPPORTED in A4
			"range", // UNSUPPORTED in A4
	};

	/**
	 * Constructor.
	 * 
	 * @param testFolder
	 *            the test folder
	 * @throws IOException
	 *             if the tested template can't be read
	 */
	public EvaluationTests(String testFolder) throws IOException {
		super(testFolder);
	}

	/**
	 * Gets test folders from resources/feature.
	 * 
	 * @return test folders from resources/feature
	 */
	@Parameters(name = "{0}")
	public static Collection<Object[]> retrieveTestFolders() {
		//@formatter:off
		return retrieveTestFolders("resources/evaluation", EXCLUDED_TESTS);
		//@formatter:on
	}

}
