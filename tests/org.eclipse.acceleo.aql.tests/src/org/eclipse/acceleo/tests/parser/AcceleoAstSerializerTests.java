/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.tests.utils.AbstractLanguageTestSuite;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests {@link AcceleoAstSerializer}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Parameterized.class)
public class AcceleoAstSerializerTests {

	/**
	 * The root folder.
	 */
	private static final String ROOT = "resources";

	/**
	 * The {@link AcceleoAstResult}.
	 */
	private final AcceleoAstResult ast;

	/**
	 * The source of the module.
	 */
	private final String source;

	/**
	 * The module path.
	 */
	private String modulePath;

	/**
	 * Constructor.
	 * 
	 * @param modulePath
	 *            the test name
	 * @throws IOException
	 *             if the module can't be read
	 * @throws FileNotFoundException
	 *             if the module file can't be found
	 */
	public AcceleoAstSerializerTests(String modulePath) throws FileNotFoundException, IOException {
		this.modulePath = modulePath;
		EcorePackage.eINSTANCE.getName(); // initialize Ecore package
		AcceleoParser parser = new AcceleoParser();
		try (InputStream stream = new FileInputStream(ROOT + File.separator + modulePath)) {
			source = AcceleoUtil.getContent(stream, AbstractLanguageTestSuite.UTF_8);
			ast = parser.parse(source, "org::eclipse::acceleo::tests::test");
		}
	}

	/**
	 * Tests {@link AcceleoAstSerializer#serialize(org.eclipse.acceleo.ASTNode)}.
	 * 
	 * @throws IOException
	 *             if the expected serialized module file can't be read
	 * @throws FileNotFoundException
	 *             if the expected serialized module file can't be found
	 */
	@Test
	public void serialize() throws FileNotFoundException, IOException {
		final AcceleoAstSerializer serializer = new AcceleoAstSerializer();
		final String actualSerializedModule = serializer.serialize(ast.getModule());

		final File expectedSerializedFile = new File(ROOT + File.separator + "serialization" + File.separator
				+ modulePath + "-expected.txt");

		createActualFileIfNeeded(actualSerializedModule, expectedSerializedFile);
		try (FileInputStream stream = new FileInputStream(expectedSerializedFile)) {
			final String expectedSerializedModule = AcceleoUtil.getContent(stream,
					AbstractLanguageTestSuite.UTF_8);
			assertEquals(expectedSerializedModule, actualSerializedModule);
			stream.close();
		}
	}

	private void createActualFileIfNeeded(final String actualSerializedModule,
			final File expectedSerializedFile) throws IOException, UnsupportedEncodingException,
			FileNotFoundException {
		if (!expectedSerializedFile.exists()) {
			final File actualSerializedFile = new File(ROOT + File.separator + "serialization"
					+ File.separator + modulePath + "-actual.txt");
			if (!actualSerializedFile.exists()) {
				final File parentFile = actualSerializedFile.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				actualSerializedFile.createNewFile();
			}
			AbstractLanguageTestSuite.setContent(new FileOutputStream(actualSerializedFile),
					AbstractLanguageTestSuite.UTF_8, actualSerializedModule);
			fail("file doesn't exist.");
		}
	}

	/**
	 * Gets the {@link List} of module path to parse.
	 * 
	 * @return the {@link List} of module path to parse
	 * @throws IOException
	 *             if the module can't be read
	 * @throws FileNotFoundException
	 *             if the module can't be found
	 */
	@Parameters(name = "{0}")
	public static Collection<Object[]> retrieveTests() {
		final List<Object[]> res = new ArrayList<Object[]>();

		final List<String> modules = new ArrayList<String>();
		final File root = new File(ROOT);
		modules.addAll(findModuleFiles(root));

		final Path currentPath = new File(ROOT).toPath().toAbsolutePath();
		for (String module : modules) {
			final Path relativePath = currentPath.relativize(new File(module).toPath().toAbsolutePath());
			res.add(new Object[] {relativePath.toString() });
		}

		return res;
	}

	private static List<String> findModuleFiles(File root) {
		final List<String> res = new ArrayList<String>();

		for (File child : root.listFiles()) {
			if (child.isFile()) {
				if (child.getName().endsWith("." + AcceleoParser.MODULE_FILE_EXTENSION)) {
					res.add(child.getAbsolutePath());
				}
			} else {
				res.addAll(findModuleFiles(child));
			}
		}

		return res;
	}

}
