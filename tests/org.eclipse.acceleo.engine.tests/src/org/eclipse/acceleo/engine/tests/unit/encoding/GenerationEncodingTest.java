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
package org.eclipse.acceleo.engine.tests.unit.encoding;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;

/**
 * This will be used to test the behavior of Acceleo when trying to generate text in distinct encodings.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class GenerationEncodingTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/Encoding/generation_encoding.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "Encoding";
	}

	/**
	 * Checks that we could properly generate a file with UTF-8 encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateUTF8Template() throws IOException {
		generationRoot = new File(getGenerationRootPath("UTF8")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("UTF8")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("generate_UTF_8", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot, "UTF-8");
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Checks that we could properly generate a file with ISO-8859-1 encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateLatin1Template() throws IOException {
		generationRoot = new File(getGenerationRootPath("Latin1")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("Latin1")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("generate_ISO_8859_1", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot, "ISO-8859-1");
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Checks that we could properly generate a file with ISO-8859-5 encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateRussianTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("Russian")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("Russian")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("generate_ISO_8859_5", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot, "ISO-8859-5");
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Checks that we could properly generate a file with ISO-8859-7 encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateGreekTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("Greek")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("Greek")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("generate_ISO_8859_7", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot, "ISO-8859-7");
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Checks that we cannot generate a file with an inexistant encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateWrongEncodingTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("WrongEncoding")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("generate_wrong_encoding", defaultStrategy); //$NON-NLS-1$
		File[] children = getFiles(generationRoot);
		for (File child : children) {
			// Reads the file with the default System charset (should have been encoded as such)
			final String content = getAbsoluteFileContent(child.getAbsolutePath());
			// If the file hasn't been saved in the default encoding, this will fail
			final String defaultCharset = Charset.defaultCharset().displayName();
			assertTrue(content.contains(new String("\u0414".getBytes(defaultCharset), defaultCharset)));
		}
	}
}
