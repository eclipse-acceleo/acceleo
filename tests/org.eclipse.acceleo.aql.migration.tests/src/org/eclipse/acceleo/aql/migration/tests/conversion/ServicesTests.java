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
import java.util.Collection;

import org.eclipse.acceleo.aql.migration.tests.utils.AbstractMigrationTestSuite;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests the conversion of services-related tests.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public class ServicesTests extends AbstractMigrationTestSuite {

	/**
	 * Constructor.
	 * 
	 * @param testFolder
	 *            the test folder
	 * @throws IOException
	 *             if the tested template can't be read
	 */
	public ServicesTests(String testFolder) throws IOException {
		super(testFolder);
	}

	/**
	 * Gets test folders from resources/feature.
	 * 
	 * @return test folders from resources/feature
	 */
	@Parameters(name = "{0}")
	public static Collection<Object[]> retrieveTestFolders() {
		return retrieveTestFolders("resources/services");
	}

}
