/*******************************************************************************
 *  Copyright (c) 2016, 2024 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.tests.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.migration.IModuleResolver;
import org.eclipse.acceleo.aql.migration.ModuleMigrator;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
import org.eclipse.acceleo.tests.utils.AbstractLanguageTestSuite;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Run a folder with templates as a test suite JUnit.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
@RunWith(Parameterized.class)
public abstract class AbstractMigrationTestSuite {

	/**
	 * UTF-8 content.
	 */
	public static final String UTF_8 = "UTF-8";

	/**
	 * The A3 to A4 migrator.
	 */
	private static final ModuleMigrator MODULE_MIGRATOR = new ModuleMigrator(new IModuleResolver() {

		public String getQualifiedName(org.eclipse.acceleo.model.mtl.Module module,
				org.eclipse.acceleo.model.mtl.Module refModule) {
			String moduleUri = EcoreUtil.getURI(refModule).toString();
			String qualifiedName = moduleUri.substring(moduleUri.indexOf("misc/"), moduleUri.lastIndexOf("#"))
					.replaceAll("/", "::").replaceAll(".emtl", "");
			return qualifiedName;
		}
	}, null);

	/**
	 * The test folder path.
	 */
	protected final File testFolder;

	/**
	 * The {@link Module} after conversion.
	 */
	private final Module module;

	/**
	 * The converted module content.
	 */
	private String moduleContent;

	/**
	 * Constructor.
	 * 
	 * @param testFolderPath
	 *            the test folder path
	 * @throws IOException
	 *             if the tested template can't be read
	 */
	public AbstractMigrationTestSuite(String testFolderPath) throws IOException {
		testFolder = new File(testFolderPath);
		final String newLine = "\n";
		module = MODULE_MIGRATOR.migrate(getFile(".emtl"), getFile("-origin.mtl"), newLine);
		try {
			moduleContent = new AcceleoAstSerializer(newLine).serialize(module);
		} catch (Exception e) {
			System.err.println("Serialization issue:");
			e.printStackTrace();
		}
	}

	@Test
	public void migrate() throws FileNotFoundException, IOException {
		final File actualMTLFile = getFile(".mtl");
		if (!actualMTLFile.exists()) {
			actualMTLFile.createNewFile();
		}
		AbstractLanguageTestSuite.setContent(new FileOutputStream(actualMTLFile), UTF_8, moduleContent);
		final File expectedMTLFile = getFile(testFolder, "-expected.mtl");

		// //[BUILD TEST purpose] erases/inits the expected file
		// if (!expectedMTLFile.exists()) {
		// expectedMTLFile.createNewFile();
		// }
		// AbstractLanguageTestSuite.setContent(new FileOutputStream(expectedMTLFile), UTF_8, moduleContent);

		if (!expectedMTLFile.exists()) {
			fail("Expected migrated MTL file doesn't exist.");
		}
		try (FileInputStream stream = new FileInputStream(expectedMTLFile)) {
			final String expectedMtl = AcceleoUtil.getContent(stream, UTF_8);
			stream.close();
			assertEquals(expectedMtl, moduleContent);
		}
	}

	/**
	 * Gets the file from the test folder.
	 * 
	 * @param extension
	 *            the file extension
	 * @return the file from the test folder path
	 */
	private File getFile(String extension) {
		return getFile(testFolder, extension);
	}

	/**
	 * Gets the file from the test folder.
	 * 
	 * @param test
	 *            folder the test folder
	 * @param extension
	 *            the file extension
	 * @return the file from the test folder path
	 */
	private static File getFile(File folder, String extension) {
		return new File(folder + File.separator + folder.getName() + extension);
	}

	/**
	 * Gets the {@link Collection} of test folders from the given folder path.
	 * 
	 * @param folderPath
	 *            the folder path
	 * @return the {@link Collection} of test folders from the given folder path
	 */
	protected static Collection<Object[]> retrieveTestFolders(String folderPath,
			final String... excludedTests) {
		Collection<Object[]> parameters = new ArrayList<Object[]>();
		File folder = new File(folderPath);
		final File[] children = folder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				final boolean res;

				if (pathname.isDirectory() && pathname.canRead() && !Arrays.asList(excludedTests).contains(
						pathname.getName())) {
					res = getFile(pathname, ".emtl").exists();
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
