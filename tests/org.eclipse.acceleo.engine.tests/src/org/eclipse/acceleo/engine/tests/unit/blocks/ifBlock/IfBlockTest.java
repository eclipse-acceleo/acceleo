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
package org.eclipse.acceleo.engine.tests.unit.blocks.ifBlock;

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;

/**
 * Class to test If block.
 * 
 * @author <a href="mailto:freddy.allilaire@obeo.fr">Freddy Allilaire</a>
 */
public class IfBlockTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/IfBlock/template_if.mtl"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "IfBlock"; //$NON-NLS-1$
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testIfStatementCompleteIf() throws IOException {
		generationRoot = new File(getGenerationRootPath("CompleteIf")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("CompleteIf")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingCompleteIf", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testIfStatementElse() throws IOException {
		generationRoot = new File(getGenerationRootPath("Else")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("Else")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingElse", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testIfStatementElseif() throws IOException {
		generationRoot = new File(getGenerationRootPath("Elseif")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("Elseif")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingElseif", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testIfStatementIf() throws IOException {
		generationRoot = new File(getGenerationRootPath("If")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("If")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingIf", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testIfStatementNestedIf() throws IOException {
		generationRoot = new File(getGenerationRootPath("NestedIf")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NestedIf")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("testingNestedIf", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}
}
