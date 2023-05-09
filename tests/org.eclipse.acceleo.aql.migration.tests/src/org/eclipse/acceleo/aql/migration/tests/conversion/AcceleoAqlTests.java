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
package org.eclipse.acceleo.aql.migration.tests.conversion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.aql.migration.tests.utils.AbstractMigrationTestSuite;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests the conversion of forked A4 tests.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public class AcceleoAqlTests extends AbstractMigrationTestSuite {

	/**
	 * Constructor.
	 * 
	 * @param testFolder
	 *            the test folder
	 * @throws IOException
	 *             if the tested template can't be read
	 */
	public AcceleoAqlTests(String testFolder) throws IOException {
		super(testFolder);
	}

	/**
	 * Gets test folders from resources/feature.
	 * 
	 * @return test folders from resources/feature
	 */
	@Parameters(name = "{0}")
	public static Collection<Object[]> retrieveTestFolders() {
		List<Object[]> res = new ArrayList<>();

		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/evaluation/ifStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/evaluation/fileStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/evaluation/forStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/evaluation/letStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/evaluation/expressionStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/module"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/expressionStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/fileStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/protectedArea"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/query"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/textStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/ifStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/letStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/forStatement"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/template"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/moduleDocumentation"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/moduleElementDocumentation"));
		res.addAll(retrieveTestFolders("resources/acceleoAqlTests/language/comment", new String[] {
				// NOT-MIGRATED non-module level comments
				"endOfBlock", "middleOfBlock", "beginingOfBlock",
				// NOT-MIGRATED before module comments
				"beforeModuleDocumentation", "afterModuleDocumentation", }));
		// NOT-MIGRATED non-module level comments
		// res.addAll(retrieveTestFolders("resources/acceleoAqlTests/evaluation/comment"));
		// TODO encoding issues
		// res.addAll(retrieveTestFolders("resources/acceleoAqlTests/evaluation/textStatement"));

		return res;
	}
}
