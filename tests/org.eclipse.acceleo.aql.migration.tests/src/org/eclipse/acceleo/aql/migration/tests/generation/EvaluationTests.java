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
package org.eclipse.acceleo.aql.migration.tests.generation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import org.eclipse.acceleo.aql.migration.tests.utils.AbstractEvaluationTests;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests the evaluation of forked A3 tests.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public class EvaluationTests extends AbstractEvaluationTests {

	public static final String[] EXCLUDED_TESTS = new String[] {};

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
		return retrieveTestFoldersWithExclude("resources/evaluation", Stream.concat(Arrays.stream(
				org.eclipse.acceleo.aql.migration.tests.conversion.EvaluationTests.EXCLUDED_TESTS), Arrays
						.stream(EXCLUDED_TESTS)).toArray(String[]::new));
	}

	@Override
	public void parsing() throws FileNotFoundException, IOException {
		// we deactivate the parsing checking as this is not the purpose of those tests
	}
}
