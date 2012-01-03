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

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;

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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "TemplateInvocation"; //$NON-NLS-1$
	}

	/**
	 * Tests that the template invocations can be used as parameters of other template invocations.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testDualTemplateInvocation() throws IOException {
		generationRoot = new File(getGenerationRootPath("DualTemplateInvocation")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("DualTemplateInvocation")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect two protected areas to have been created
			assertTrue(content.contains("AbstractClasS")); //$NON-NLS-1$
		}
	}
}
