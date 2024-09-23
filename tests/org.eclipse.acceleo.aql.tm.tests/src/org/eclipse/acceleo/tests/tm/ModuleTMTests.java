/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.tm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.tests.tm.support.TMEditor;
import org.eclipse.acceleo.tests.tm.support.TestUtils;
import org.eclipse.acceleo.tests.tm.support.command.ICommand;
import org.eclipse.acceleo.tests.utils.AbstractLanguageTestSuite;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.registry.TMEclipseRegistryPlugin;
import org.eclipse.tm4e.ui.TMUIPlugin;
import org.eclipse.tm4e.ui.themes.ITokenProvider;
import org.eclipse.tm4e.ui.themes.ThemeIdConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.osgi.framework.Bundle;

/**
 * Tests Acceleo {@link Module} TextMate syntactic coloration.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Parameterized.class)
public class ModuleTMTests {

	final static Bundle MODULE_BUNDLE = Platform.getBundle("org.eclipse.acceleo.aql.tests");

	private static boolean viaBundle;

	/**
	 * The Acceleo {@link IGrammar}.
	 */
	private final IGrammar grammar = TMEclipseRegistryPlugin.getGrammarRegistryManager()
			.getGrammarForFileExtension(AcceleoParser.MODULE_FILE_EXTENSION);;

	/**
	 * The {@link ITokenProvider}.
	 */
	private final ITokenProvider theme = TMUIPlugin.getThemeManager().getThemeById(
			ThemeIdConstants.SolarizedLight);

	private TMEditor editor;

	/**
	 * The source of the module.
	 */
	private final String source;

	/**
	 * The module path.
	 */
	private String modulePath;

	public ModuleTMTests(String modulePath) throws FileNotFoundException, IOException {
		this.modulePath = modulePath;
		final String encoding;
		EcorePackage.eINSTANCE.getName(); // initialize Ecore package
		GenModelPackage.eINSTANCE.getName(); // initialize GenModel package
		AcceleoParser parser = new AcceleoParser();
		if (viaBundle) {
			try (InputStream stream = MODULE_BUNDLE.getResource(modulePath).openStream()) {
				encoding = parser.parseEncoding(stream);
			}
			try (InputStream stream = MODULE_BUNDLE.getResource(modulePath).openStream()) {
				source = AcceleoUtil.getContent(stream, encoding);
			}
		} else {
			try (InputStream stream = new FileInputStream(modulePath)) {
				encoding = parser.parseEncoding(stream);
			}
			try (InputStream stream = new FileInputStream(modulePath)) {
				source = AcceleoUtil.getContent(stream, encoding);
			}
		}
	}

	@Before
	public void setup() throws Exception {
		// TODO ignore these tests on GitHub Actions Windows runner because they are extremely slow (4-5min)
		// for an unknown reason
		org.junit.Assume.assumeFalse(TestUtils.isGitHubActions() && System.getProperty("os.name")
				.toLowerCase().contains("windows"));

		TestUtils.assertNoTM4EThreadsRunning();
	}

	@After
	public void tearDown() throws InterruptedException {
		if (editor != null) {
			editor.dispose();
		}
		TestUtils.assertNoTM4EThreadsRunning();
	}

	@Test
	public void colorize() throws IOException {
		editor = new TMEditor(grammar, theme, source);

		final List<ICommand> commands = editor.execute();
		assertEquals(1, commands.size());

		final ICommand command = commands.get(0);
		final String actualStyleRanges = command.getStyleRanges();

		final File expectedStyleRangesFile = new File("." + modulePath + "-expected.txt");
		createActualFileIfNeeded(actualStyleRanges, expectedStyleRangesFile);
		try (FileInputStream stream = new FileInputStream(expectedStyleRangesFile)) {
			final String expectedStyleRanges = AcceleoUtil.getContent(stream, StandardCharsets.UTF_8.name());
			assertEquals(expectedStyleRanges, actualStyleRanges);
		}
	}

	private void createActualFileIfNeeded(final String actualSerializedModule,
			final File expectedSerializedFile) throws IOException, UnsupportedEncodingException,
			FileNotFoundException {
		if (!expectedSerializedFile.exists()) {
			final File actualSerializedFile = new File("." + modulePath + "-actual.txt");
			if (!actualSerializedFile.exists()) {
				final File parentFile = actualSerializedFile.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				actualSerializedFile.createNewFile();
			}
			AbstractLanguageTestSuite.setContent(new FileOutputStream(actualSerializedFile),
					StandardCharsets.UTF_8.name(), actualSerializedModule);
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

		final Enumeration<URL> entries = MODULE_BUNDLE.findEntries("resources", "*.mtl", true);
		if (entries != null) {
			final Iterator<URL> it = entries.asIterator();
			while (it.hasNext()) {
				res.add(new String[] {it.next().getPath() });
			}
			viaBundle = true;
		} else {
			final File root = new File(".." + File.separator + "org.eclipse.acceleo.aql.tests"
					+ File.separator + "resources");
			for (String file : findModuleFiles(root)) {
				res.add(new String[] {file });
			}
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
