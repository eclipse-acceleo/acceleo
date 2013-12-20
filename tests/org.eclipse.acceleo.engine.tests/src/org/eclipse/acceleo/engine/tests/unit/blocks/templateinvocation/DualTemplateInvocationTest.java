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
package org.eclipse.acceleo.engine.tests.unit.blocks.templateinvocation;

import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.junit.Test;

/**
 * Tests the behavior of template invocations.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class DualTemplateInvocationTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/TemplateInvocation/dual_template_call.mtl"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "TemplateInvocation"; //$NON-NLS-1$
	}

	/**
	 * Tests that the template invocations can be used as parameters of other template invocations.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testDualTemplateInvocation() throws IOException {
		this.init("DualTemplateInvocation"); //$NON-NLS-1$
		this.generate("test", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}
}
