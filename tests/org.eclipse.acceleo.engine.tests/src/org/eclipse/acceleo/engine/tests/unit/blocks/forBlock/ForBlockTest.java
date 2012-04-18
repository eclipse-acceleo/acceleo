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
package org.eclipse.acceleo.engine.tests.unit.blocks.forBlock;

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;

/**
 * Class to test For block.
 * 
 * @author <a href="mailto:freddy.allilaire@obeo.fr">Freddy Allilaire</a>
 */
public class ForBlockTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/ForBlock/template_for.mtl"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "ForBlock"; //$NON-NLS-1$
	}

	/**
	 * Test For statement with After keyword.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testForStatementAfterKeyword() throws IOException {
		generationRoot = new File(getGenerationRootPath("AfterKeyword")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("AfterKeyword")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingAfter", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test For statement with basic example.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testForStatementBasicExample() throws IOException {
		generationRoot = new File(getGenerationRootPath("BasicExample")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("BasicExample")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingSimple", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test For statement with Before keyword.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testForStatementBeforeKeyword() throws IOException {
		generationRoot = new File(getGenerationRootPath("BeforeKeyword")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("BeforeKeyword")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingBefore", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test For statement with Guard keyword.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testForStatementGuardKeyword() throws IOException {
		generationRoot = new File(getGenerationRootPath("GuardKeyword")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("GuardKeyword")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingGuard", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test For statement with Separator keyword.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testForStatementSeparatorKeyword() throws IOException {
		generationRoot = new File(getGenerationRootPath("SeparatorKeyword")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("SeparatorKeyword")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingSeparator", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test For statement with the "i" variable.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testForStatementIVariable() throws IOException {
		generationRoot = new File(getGenerationRootPath("IVariable")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("IVariable")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingI", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test For statement with the "i" variable.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testForStatementIVariableSeparator() throws IOException {
		generationRoot = new File(getGenerationRootPath("IVariableSeparator")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("IVariableSeparator")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingISeparator", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}
}
