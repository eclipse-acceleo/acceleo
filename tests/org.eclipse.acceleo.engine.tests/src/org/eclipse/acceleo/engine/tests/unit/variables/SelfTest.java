/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.variables;

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;

/**
 * This test allows us to check the behavior of the Acceleo engine when dealing with the OCL "self" variable.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class SelfTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/Variables/template_self.mtl"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "Variables"; //$NON-NLS-1$
	}

	/**
	 * Tests the usage of the self variable in a query.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testSelfVariableInQuery() throws IOException {
		generationRoot = new File(getGenerationRootPath("SelfQuery")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("SelfQuery")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_self_query", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the name of the package to have been generated
			assertTrue(content.contains("target")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the usage of the self variable in a template.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testSelfVariableInTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("SelfTemplate")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("SelfTemplate")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_self_template", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the name of the package to have been generated
			assertTrue(content.contains("target")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the implicit usage of the self variable in a query.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testImplicitSelfVariableInQuery() throws IOException {
		generationRoot = new File(getGenerationRootPath("ImplicitSelfQuery")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ImplicitSelfQuery")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_implicit_self_query", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the name of the package to have been generated
			assertTrue(content.contains("target")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the implicit usage of the self variable in a template.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testImplicitSelfVariableInTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("ImplicitSelfTemplate")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ImplicitSelfTemplate")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_implicit_self_template", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the name of the package to have been generated
			assertTrue(content.contains("target")); //$NON-NLS-1$
		}
	}
}
