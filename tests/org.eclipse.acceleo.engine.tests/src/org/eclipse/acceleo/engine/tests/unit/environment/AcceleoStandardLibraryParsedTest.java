/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.junit.Before;
import org.junit.Test;

/**
 * This will test the behavior of the Acceleo standard library's operations when called from a parsed
 * generator file.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoStandardLibraryParsedTest extends AbstractAcceleoTest {
	/** This will be generated from setup. */
	private Map<String, String> generatedPreview;

	/** This is the output we expect from each generated file. */
	private final static String OUTPUT = "\t\tconstant output" + System.getProperty("line.separator");

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Before
	@Override
	public void setUp() {
		super.setUp();
		this.init("StdLib");
		generatedPreview = generate("test_stdlib", previewStrategy); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/Library/stdlib.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "StandardLibrary";
	}

	/**
	 * Tests the behavior of the standard "first(int)" operation on Strings. Expects the result to be the same
	 * as source.substring(0, int).
	 */
	@Test
	public void testStringFirst() {
		final String fileName = "test_string_first";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard "index(String)" operation on Strings. Expects the result to be the
	 * same as source.indexOf(String).
	 */
	@Test
	public void testStringIndex() {
		final String fileName = "test_string_index";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard "isAlpha()" operation on Strings. Expects the result to be
	 * <code>true</code> if and only if {@link Character#isLetter(char)} returns <code>true</code> for each
	 * and every character of the source value.
	 */
	@Test
	public void testStringIsAlpha() {
		final String fileName = "test_string_isAlpha";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard "isAlphanum()" operation on Strings. Expects the result to be
	 * <code>true</code> if and only if {@link Character#isLetterOrDigit(char)} returns <code>true</code> for
	 * each and every character of the source value.
	 */
	@Test
	public void testStringIsAlphanum() {
		final String fileName = "test_string_isAlphanum";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard "last(int)" operation on Strings. Expects the result to be the same
	 * as source.substring(source.length() - int, source.length()).
	 */
	@Test
	public void testStringLast() {
		final String fileName = "test_string_last";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard "strcmp(String)" operation on Strings. Expects the result to be the
	 * same as source.compareTo(String).
	 */
	@Test
	public void testStringStrcmp() {
		final String fileName = "test_string_strcmp";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard "strstr(String)" operation on Strings. Expects the result to be the
	 * same as source.contains(String).
	 */
	@Test
	public void testStringStrstr() {
		final String fileName = "test_string_strstr";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard "strtok(String, int)" operation on Strings.
	 */
	@Test
	public void testStringStrtok() {
		final String fileName = "test_string_strtok";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard String.substitute(String, String) operation.
	 * <p>
	 * We expect the call to replace the first occurence of the substring by the replacement, not considering
	 * both parameters as regular expressions.
	 * </p>
	 */
	@Test
	public void testStringSubstitute() {
		final String fileName = "test_string_substitute";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard String.toLowerFirst() operation.
	 */
	@Test
	public void testStringToLowerFirst() {
		final String fileName = "test_string_toLowerFirst";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}

	/**
	 * Tests the behavior of the standard String.toUpperFirst() operation.
	 */
	@Test
	public void testStringToUpperFirst() {
		final String fileName = "test_string_toUpperFirst";
		boolean fileFound = false;
		for (Map.Entry<String, String> filePreview : generatedPreview.entrySet()) {
			if (filePreview.getKey().endsWith(fileName)) {
				assertEquals(OUTPUT.trim(), filePreview.getValue().toString().trim());
				fileFound = true;
			}
		}
		if (!fileFound) {
			fail("expected file hasn't been generated.");
		}
	}
}
