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
package org.eclipse.acceleo.aql.migration.tests.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.acceleo.tests.utils.AbstractEvaluationTestSuite;

/**
 * This class aggregates unsorted tests.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public class AbstractEvaluationTests extends AbstractEvaluationTestSuite {

	/**
	 * Constructor.
	 * 
	 * @param testFolder
	 *            the test folder
	 * @throws IOException
	 *             if the tested template can't be read
	 */
	public AbstractEvaluationTests(String testFolder) throws IOException {
		super(testFolder);
	}

	protected static Collection<Object[]> retrieveTestFoldersWithExclude(String folderPath,
			final String... excludedTests) {
		Collection<Object[]> parameters = new ArrayList<Object[]>();

		File folder = new File(folderPath);
		final File[] children = folder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				final boolean res;

				if (pathname.isDirectory() && pathname.canRead() && !Arrays.asList(excludedTests).contains(
						pathname.getName())) {
					res = new File(pathname + File.separator + pathname.getName() + ".mtl").exists();
				} else {
					res = false;
				}

				return res;
			}

		});
		Arrays.sort(children);
		for (File child : children) {
			parameters.add(new Object[] {child.getPath() });
		}

		return parameters;
	}
}
