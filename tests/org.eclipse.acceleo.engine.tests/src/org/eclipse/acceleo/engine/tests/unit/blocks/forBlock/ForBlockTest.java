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

import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.junit.Test;

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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "ForBlock"; //$NON-NLS-1$
	}

	/**
	 * Test For statement with After keyword.
	 */
	@Test
	public void testForStamentAfterKeyword() {
		this.init("AfterKeyword"); //$NON-NLS-1$
		this.generate("testingAfter", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test For statement with basic example.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testForStatementBasicExample() throws IOException {
		this.init("BasicExample"); //$NON-NLS-1$
		this.generate("testingSimple", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test For statement with Before keyword.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testForStatementBeforeKeyword() throws IOException {
		this.init("BeforeKeyword"); //$NON-NLS-1$
		this.generate("testingBefore", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test For statement with Guard keyword.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testForStatementGuardKeyword() throws IOException {
		this.init("GuardKeyword"); //$NON-NLS-1$
		this.generate("testingGuard", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test For statement with Separator keyword.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testForStatementSeparatorKeyword() throws IOException {
		this.init("SeparatorKeyword"); //$NON-NLS-1$
		this.generate("testingSeparator", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test For statement with the "i" variable.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testForStatementIVariable() throws IOException {
		this.init("IVariable"); //$NON-NLS-1$
		this.generate("testingI", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test For statement with the "i" variable.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testForStatementIVariableSeparator() throws IOException {
		this.init("IVariableSeparator"); //$NON-NLS-1$
		this.generate("testingISeparator", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

}
