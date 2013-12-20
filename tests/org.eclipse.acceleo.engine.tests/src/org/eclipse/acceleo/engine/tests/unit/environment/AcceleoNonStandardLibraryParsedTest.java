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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "NonStandardLibrary";
	}

	/**
	 * Tests the behavior of the non standard "ancestors()" operation on OclAny.
	 */
	@Test
	public void testOclAnyAncestors() {
		assertFileContainsOutput("test_oclany_ancestors");
	}

	/**
	 * Tests the behavior of the non standard "eAllContents()" operation on OclAny.
	 */
	@Test
	public void testOclAnyEAllContents() {
		assertFileContainsOutput("test_oclany_eAllContents");
	}

	/**
	 * Tests the behavior of the non standard "eInverse()" operation on OclAny.
	 */
	@Test
	public void testOclAnyEInverse() {
		assertFileContainsOutput("test_oclany_eInverse");
	}

	/**
	 * Tests the behavior of the non standard "invoke()" operation on OclAny.
	 */
	@Test
	public void testOclAnyInvoke() {
		assertFileContainsOutput("test_oclany_invoke");
	}

	/**
	 * Tests the behavior of the non standard "siblings()" operation on OclAny.
	 */
	@Test
	public void testOclAnySiblings() {
		assertFileContainsOutput("test_oclany_siblings");
	}

	/**
	 * Tests the behavior of the non standard "toString()" operation on OclAny.
	 */
	@Test
	public void testOclAnyToString() {
		assertFileContainsOutput("test_oclany_toString");
	}

	/**
	 * Tests the behavior of the non standard "ancestors(OclAny)" operation on OclAny.
	 */
	@Test
	public void testOclAnyTypedAncestors() {
		assertFileContainsOutput("test_oclany_typed_eAllContents");
	}

	/**
	 * Tests the behavior of the non standard "eAllContents(OclAny)" operation on OclAny.
	 */
	@Test
	public void testOclAnyTypedEAllContents() {
		assertFileContainsOutput("test_oclany_typed_eAllContents");
	}

	/**
	 * Tests the behavior of the non standard "eInverse(OclAny)" operation on OclAny.
	 */
	@Test
	public void testOclAnyTypedEInverse() {
		assertFileContainsOutput("test_oclany_typed_eInverse");
	}

	/**
	 * Tests the behavior of the non standard "siblings(OclAny)" operation on OclAny.
	 */
	@Test
	public void testOclAnyTypedSiblings() {
		assertFileContainsOutput("test_oclany_typed_siblings");
	}

	/**
	 * Tests the behavior of the non standard "lineSeparator(OclAny)" operation on OclAny.
	 */
	@Test
	public void testOclAnyLineSeparator() {
		assertFileContainsOutput("test_oclany_line_separator");
	}

	/**
	 * Tests the behavior of the non standard "contains(String)" operation on Strings.
	 */
	@Test
	public void testStringContains() {
		assertFileContainsOutput("test_string_contains");
	}

	/**
	 * Tests the behavior of the non standard "endsWith(String)" operation on Strings.
	 */
	@Test
	public void testStringEndsWith() {
		assertFileContainsOutput("test_string_endsWith");
	}

	/**
	 * Tests the behavior of the non standard "replace(String, String)" operation on Strings.
	 */
	@Test
	public void testStringReplace() {
		assertFileContainsOutput("test_string_replace");
	}

	/**
	 * Tests the behavior of the non standard "replaceAll(String, String)" operation on Strings.
	 */
	@Test
	public void testStringReplaceAll() {
		assertFileContainsOutput("test_string_replaceAll");
	}

	/**
	 * Tests the behavior of the non standard "startsWith(String)" operation on Strings.
	 */
	@Test
	public void testStringStartsWith() {
		assertFileContainsOutput("test_string_startsWith");
	}

	/**
	 * Tests the behavior of the non standard "substituteAll(String, String)" operation on Strings.
	 */
	@Test
	public void testStringSubstituteAll() {
		assertFileContainsOutput("test_string_substituteAll");
	}

	/**
	 * Tests the behavior of the non standard "tokenize(String)" operation on Strings.
	 */
	@Test
	public void testStringTokenize() {
		assertFileContainsOutput("test_string_tokenize");
	}

	/**
	 * Tests the behavior of the non standard "trim()" operation on Strings.
	 */
	@Test
	public void testStringTrim() {
		assertFileContainsOutput("test_string_trim");
	}

	/**
	 * Tests the behavior of the non standard "index(String, Integer)" operation on Strings.
	 */
	@Test
	public void testStringIndex() {
		assertFileContainsOutput("test_string_index");
	}

	/**
	 * Tests the behavior of the non standard "lastIndex(String, Integer)" operation on Strings.
	 */
	@Test
	public void testStringLastIndex() {
		assertFileContainsOutput("test_string_last_index");
	}

	/**
	 * Tests the behavior of the non standard "tokenize()" operation on Strings.
	 */
	@Test
	public void testStringTokenize_2() {
		assertFileContainsOutput("test_string_tokenize_2");
	}

	/**
	 * Tests the behavior of the non standard "tokenizeLine()" operation on Strings.
	 */
	@Test
	public void testStringTokenizeLine() {
		assertFileContainsOutput("test_string_tokenize_line");
	}

	/**
	 * Tests the behavior of the non standard "sep(String, String, String)" operation on Collections.
	 */
	@Test
	public void testCollectionSep() {
		assertFileContainsOutput("test_collection_sep_3");
	}

	/**
	 * Tests the behavior of the non standard "addAll(Collection)" operation on Collections.
	 */
	@Test
	public void testCollectionAddAll() {
		assertFileContainsOutput("test_collection_add_all_sequence_1");
		assertFileContainsOutput("test_collection_add_all_sequence_2");
		assertFileContainsOutput("test_collection_add_all_sequence_3");
		assertFileContainsOutput("test_collection_add_all_sequence_4");

		assertFileContainsOutput("test_collection_add_all_ordered_set_1");
		assertFileContainsOutput("test_collection_add_all_ordered_set_2");
		assertFileContainsOutput("test_collection_add_all_ordered_set_3");
		assertFileContainsOutput("test_collection_add_all_ordered_set_4");

		assertFileContainsOutput("test_collection_add_all_set_1");
		assertFileContainsOutput("test_collection_add_all_set_2");
		assertFileContainsOutput("test_collection_add_all_set_3");
		assertFileContainsOutput("test_collection_add_all_set_4");

		assertFileContainsOutput("test_collection_add_all_bag_1");
		assertFileContainsOutput("test_collection_add_all_bag_2");
		assertFileContainsOutput("test_collection_add_all_bag_3");
		assertFileContainsOutput("test_collection_add_all_bag_4");
	}

	/**
	 * Tests the behavior of the non standard "removeAll(Collection)" operation on Collections.
	 */
	public void testCollectionRemoveAll() {
		assertFileContainsOutput("test_collection_remove_all_sequence_1");
		assertFileContainsOutput("test_collection_remove_all_sequence_2");
		assertFileContainsOutput("test_collection_remove_all_sequence_3");
		assertFileContainsOutput("test_collection_remove_all_sequence_4");

		assertFileContainsOutput("test_collection_remove_all_ordered_set_1");
		assertFileContainsOutput("test_collection_remove_all_ordered_set_2");
		assertFileContainsOutput("test_collection_remove_all_ordered_set_3");
		assertFileContainsOutput("test_collection_remove_all_ordered_set_4");

		assertFileContainsOutput("test_collection_remove_all_set_1");
		assertFileContainsOutput("test_collection_remove_all_set_2");
		assertFileContainsOutput("test_collection_remove_all_set_3");
		assertFileContainsOutput("test_collection_remove_all_set_4");

		assertFileContainsOutput("test_collection_remove_all_bag_1");
		assertFileContainsOutput("test_collection_remove_all_bag_2");
		assertFileContainsOutput("test_collection_remove_all_bag_3");
		assertFileContainsOutput("test_collection_remove_all_bag_4");
	}

	/**
	 * Tests the behavior of the non standard "drop(Integer)" operation on Sequence and OrderedSet.
	 */
	@Test
	public void testCollectionDrop() {
		assertFileContainsOutput("test_collection_drop");
	}

	/**
	 * Tests the behavior of the non standard "dropRight(Integer)" operation on Sequence and OrderedSet.
	 */
	@Test
	public void testCollectionDropRight() {
		assertFileContainsOutput("test_collection_drop_right");
	}

	/**
	 * Tests the behavior of the non standard "startsWith(Collection)" operation on Sequence and OrderedSet.
	 */
	@Test
	public void testCollectionStartsWith() {
		assertFileContainsOutput("test_collection_starts_with");
	}

	/**
	 * Tests the behavior of the non standard "endsWith(Collection)" operation on Sequence and OrderedSet.
	 */
	@Test
	public void testCollectionEndsWith() {
		assertFileContainsOutput("test_collection_ends_with");
	}

	/**
	 * Tests the behavior of the non standard "indexOfSlice(Collection)" operation on Sequence and OrderedSet.
	 */
	@Test
	public void testCollectionIndexOfSlice() {
		assertFileContainsOutput("test_collection_index_of_slice");
	}

	/**
	 * Tests the behavior of the non standard "lastIndexOfSlice(Collection)" operation on Sequence and
	 * OrderedSet.
	 */
	@Test
	public void testCollectionLastIndexOfSlice() {
		assertFileContainsOutput("test_collection_last_index_of_slice");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Before
	@Override
	public void setUp() {
		super.setUp();
		this.init("NonStdLib");
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
