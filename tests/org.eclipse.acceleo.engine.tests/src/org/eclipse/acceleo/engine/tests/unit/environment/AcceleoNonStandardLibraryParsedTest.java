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

import java.io.File;
import java.util.Map;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;

/**
 * This will test the behavior of the Acceleo non standard library's operations when called from a parsed
 * generator file.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoNonStandardLibraryParsedTest extends AbstractAcceleoTest {
	/** This is the output we expect from each generated file. */
	private final static String OUTPUT = "\t\tconstant output" + System.getProperty("line.separator");

	/** This will be generated from setup. */
	private Map<String, String> generatedPreview;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/Library/nonstdlib.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "NonStandardLibrary";
	}

	/**
	 * Tests the behavior of the non standard "ancestors()" operation on OclAny.
	 */
	public void testOclAnyAncestors() {
		assertFileContainsOutput("test_oclany_ancestors");
	}

	/**
	 * Tests the behavior of the non standard "eAllContents()" operation on OclAny.
	 */
	public void testOclAnyEAllContents() {
		assertFileContainsOutput("test_oclany_eAllContents");
	}

	/**
	 * Tests the behavior of the non standard "eInverse()" operation on OclAny.
	 */
	public void testOclAnyEInverse() {
		assertFileContainsOutput("test_oclany_eInverse");
	}

	/**
	 * Tests the behavior of the non standard "invoke()" operation on OclAny.
	 */
	public void testOclAnyInvoke() {
		assertFileContainsOutput("test_oclany_invoke");
	}

	/**
	 * Tests the behavior of the non standard "siblings()" operation on OclAny.
	 */
	public void testOclAnySiblings() {
		assertFileContainsOutput("test_oclany_siblings");
	}

	/**
	 * Tests the behavior of the non standard "toString()" operation on OclAny.
	 */
	public void testOclAnyToString() {
		assertFileContainsOutput("test_oclany_toString");
	}

	/**
	 * Tests the behavior of the non standard "ancestors(OclAny)" operation on OclAny.
	 */
	public void testOclAnyTypedAncestors() {
		assertFileContainsOutput("test_oclany_typed_eAllContents");
	}

	/**
	 * Tests the behavior of the non standard "eAllContents(OclAny)" operation on OclAny.
	 */
	public void testOclAnyTypedEAllContents() {
		assertFileContainsOutput("test_oclany_typed_eAllContents");
	}

	/**
	 * Tests the behavior of the non standard "eInverse(OclAny)" operation on OclAny.
	 */
	public void testOclAnyTypedEInverse() {
		assertFileContainsOutput("test_oclany_typed_eInverse");
	}

	/**
	 * Tests the behavior of the non standard "siblings(OclAny)" operation on OclAny.
	 */
	public void testOclAnyTypedSiblings() {
		assertFileContainsOutput("test_oclany_typed_siblings");
	}

	/**
	 * Tests the behavior of the non standard "contains(String)" operation on Strings.
	 */
	public void testStringContains() {
		assertFileContainsOutput("test_string_contains");
	}

	/**
	 * Tests the behavior of the non standard "endsWith(String)" operation on Strings.
	 */
	public void testStringEndsWith() {
		assertFileContainsOutput("test_string_endsWith");
	}

	/**
	 * Tests the behavior of the non standard "replace(String, String)" operation on Strings.
	 */
	public void testStringReplace() {
		assertFileContainsOutput("test_string_replace");
	}

	/**
	 * Tests the behavior of the non standard "replaceAll(String, String)" operation on Strings.
	 */
	public void testStringReplaceAll() {
		assertFileContainsOutput("test_string_replaceAll");
	}

	/**
	 * Tests the behavior of the non standard "startsWith(String)" operation on Strings.
	 */
	public void testStringStartsWith() {
		assertFileContainsOutput("test_string_startsWith");
	}

	/**
	 * Tests the behavior of the non standard "substituteAll(String, String)" operation on Strings.
	 */
	public void testStringSubstituteAll() {
		assertFileContainsOutput("test_string_substituteAll");
	}

	/**
	 * Tests the behavior of the non standard "tokenize(String)" operation on Strings.
	 */
	public void testStringTokenize() {
		assertFileContainsOutput("test_string_tokenize");
	}

	/**
	 * Tests the behavior of the non standard "trim()" operation on Strings.
	 */
	public void testStringTrim() {
		assertFileContainsOutput("test_string_trim");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		generationRoot = new File(getGenerationRootPath("NonStdLib")); //$NON-NLS-1$
		generatedPreview = generate("test_nonstdlib", previewStrategy); //$NON-NLS-1$
	}

	/**
	 * This is the only assertion needed for each of the preview files.
	 * 
	 * @param fileName
	 *            Name of the file on which to run the assertion.
	 */
	private void assertFileContainsOutput(String fileName) {
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
