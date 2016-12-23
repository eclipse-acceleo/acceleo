/*******************************************************************************
 *  Copyright (c) 2016 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Run a folder with templates as a test suite JUnit.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Parameterized.class)
public abstract class AbstractTemplatesTestSuite {

	/**
	 * The {@link ModuleAstSerializer}.
	 */
	private final ModuleAstSerializer moduleAstSerializer = new ModuleAstSerializer();

	/**
	 * The test folder path.
	 */
	private final String testFolderPath;

	/**
	 * The {@link Module}.
	 */
	private final Module module;

	/**
	 * The {@link Resource}.
	 */
	private final Resource model;

	/**
	 * The {@link IQueryEnvironment}.
	 */
	private final IQueryEnvironment queryEnvironment;

	/**
	 * Constructor.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @throws IOException
	 *             if the tested template can't be read
	 */
	public AbstractTemplatesTestSuite(String testFolder) throws IOException {
		this.testFolderPath = testFolder;
		final File genconfFile = getModelFile(new File(testFolderPath));
		final ResourceSet rs = getResourceSet();
		// model = getModel(genconfFile, rs);
		model = null;
		queryEnvironment = Query.newEnvironment();
		final File moduleFile = getModuleFile(new File(testFolderPath));
		final AcceleoParser parser = new AcceleoParser(queryEnvironment);

		try (FileInputStream stream = new FileInputStream(moduleFile)) {
			module = parser.parse(getContent(stream, "UTF-8"));
		}
	}

	/**
	 * Gets the {@link Generation}.
	 * 
	 * @param genconfFile
	 *            the {@link Generation} file
	 * @param rs
	 *            the {@link ResourceSet}
	 * @return the {@link Generation}
	 */
	protected Resource getModel(File genconfFile, ResourceSet rs) {
		return rs.getResource(URI.createFileURI(genconfFile.getAbsolutePath()), true);
	}

	/**
	 * Gets the test folder path.
	 * 
	 * @return the test folder path
	 */
	protected String getTestFolderPath() {
		return testFolderPath;
	}

	/**
	 * Gets the {@link ResourceSet}.
	 * 
	 * @return the {@link ResourceSet}
	 */
	protected ResourceSet getResourceSet() {
		ResourceSetImpl res = new ResourceSetImpl();

		res.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());

		return res;
	}

	/**
	 * Tests the parsing by comparing a textual representation of the AST.
	 * 
	 * @throws IOException
	 *             if the expected AST file can't be read
	 * @throws FileNotFoundException
	 *             if the expected AST file can't be found
	 */
	@Test
	public void parsing() throws FileNotFoundException, IOException {
		final File expectedASTFile = getExpectedASTFile(new File(testFolderPath));
		if (!expectedASTFile.exists()) {
			expectedASTFile.createNewFile();
		}
		try (FileInputStream stream = new FileInputStream(expectedASTFile)) {
			final String expectedAst = getContent(stream, "UTF-8");
			String actualAst = moduleAstSerializer.serialize(module);
			assertEquals(expectedAst, actualAst);
			stream.close();
		}
	}

	/**
	 * Tests the validation by comparing the validated template.
	 */
	public void validation() {
		// TODO
		fail("not implemented yet.");
	}

	/**
	 * Tests the generation by comparing the result of the generation.
	 */
	public void generation() {
		// TODO
		fail("not implemented yet.");
	}

	/**
	 * Gets the template file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the template file from the test folder path
	 */
	protected final File getModuleFile(File testFolder) {
		return getTemplateFileInternal(testFolder);
	}

	/**
	 * Gets the expected AST file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the template file from the test folder path
	 */
	protected File getExpectedASTFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-ast.txt");
	}

	/**
	 * Gets the expected validated template file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected template file from the test folder path
	 */
	protected File getExpectedValidatedFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-validation.txt");
	}

	/**
	 * Gets the actual validated template file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual template file from the test folder path
	 */
	protected File getActualValidatedFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-actual-validation.txt");
	}

	/**
	 * Gets the expected generated file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the expected template file from the test folder path
	 */
	protected File getExpectedGeneratedFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-expected-generation.txt");
	}

	/**
	 * Gets the actual generated file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the actual template file from the test folder path
	 */
	protected File getActualGeneratedFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + "-actual-generation.txt");
	}

	/**
	 * Gets the model file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the model file from the test folder path
	 */
	protected File getModelFile(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + ".xmi");
	}

	/**
	 * Gets the template file from the test folder path.
	 * 
	 * @param testFolder
	 *            the test folder path
	 * @return the template file from the test folder path
	 */
	private static File getTemplateFileInternal(File testFolder) {
		return new File(testFolder + File.separator + testFolder.getName() + ".mtl");
	}

	/**
	 * Gets the {@link Collection} of test folders from the given folder path.
	 * 
	 * @param folderPath
	 *            the folder path
	 * @return the {@link Collection} of test folders from the given folder path
	 */
	public static Collection<Object[]> retrieveTestFolders(String folderPath) {
		Collection<Object[]> parameters = new ArrayList<Object[]>();

		File folder = new File(folderPath);
		final File[] children = folder.listFiles(new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				final boolean res;

				if (pathname.isDirectory() && pathname.canRead()) {
					res = getTemplateFileInternal(pathname).exists();
				} else {
					res = false;
				}

				return res;
			}

		});
		Arrays.sort(children);
		for (File child : children) {
			parameters.add(new Object[] {child.getAbsolutePath() });
		}

		return parameters;
	}

	/**
	 * Gets the content of the given {@link InputStream}.
	 * 
	 * @param stream
	 *            the {@link InputStream}
	 * @param charsetName
	 *            The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
	 * @return a {@link CharSequence} of the content of the given {@link InputStream}
	 * @throws IOException
	 *             if the {@link InputStream} can't be read
	 */
	public static String getContent(InputStream stream, String charsetName) throws IOException {
		final int len = 8192;
		StringBuilder res = new StringBuilder(len);
		if (len != 0) {
			try (InputStreamReader input = new InputStreamReader(new BufferedInputStream(stream), charsetName)) {
				char[] buffer = new char[len];
				int length = input.read(buffer);
				while (length != -1) {
					res.append(buffer, 0, length);
					length = input.read(buffer);
				}
				input.close();
			}
		}
		return res.toString();
	}
}
