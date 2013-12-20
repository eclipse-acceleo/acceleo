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

import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.junit.Test;

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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "IfBlock"; //$NON-NLS-1$
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testIfStatementCompleteIf() throws IOException {
		this.init("CompleteIf"); //$NON-NLS-1$
		this.generate("testingCompleteIf", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testIfStatementElse() throws IOException {
		this.init("Else"); //$NON-NLS-1$
		this.generate("testingElse", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testIfStatementElseif() throws IOException {
		this.init("Elseif"); //$NON-NLS-1$
		this.generate("testingElseif", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testIfStatementIf() throws IOException {
		this.init("If"); //$NON-NLS-1$
		this.generate("testingIf", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Test.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testIfStatementNestedIf() throws IOException {
		this.init("NestedIf"); //$NON-NLS-1$
		this.generate("testingNestedIf", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}
}
