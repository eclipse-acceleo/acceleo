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

import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.junit.Test;

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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "Variables"; //$NON-NLS-1$
	}

	/**
	 * Tests the usage of the self variable in a query.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testSelfVariableInQuery() throws IOException {
		this.init("SelfQuery"); //$NON-NLS-1$
		this.generate("test_self_query", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the usage of the self variable in a template.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testSelfVariableInTemplate() throws IOException {
		this.init("SelfTemplate"); //$NON-NLS-1$
		this.generate("test_self_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the implicit usage of the self variable in a query.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testImplicitSelfVariableInQuery() throws IOException {
		this.init("ImplicitSelfQuery"); //$NON-NLS-1$
		this.generate("test_implicit_self_query", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the implicit usage of the self variable in a template.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testImplicitSelfVariableInTemplate() throws IOException {
		this.init("ImplicitSelfTemplate"); //$NON-NLS-1$
		this.generate("test_implicit_self_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}
}
