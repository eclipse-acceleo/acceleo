/*******************************************************************************
 * ¤¤COPYRIGHT¤¤
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.blocks.templateinvocation;

import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.junit.Test;

public class RecursiveTemplateInvocation extends AbstractAcceleoTest {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/TemplateInvocation/recursive_template_invocation.mtl"; //$NON-NLS-1$
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
	public void testRecursiveTemplateInvocation() throws IOException {
		this.init("RecursiveTemplateInvocation"); //$NON-NLS-1$
		this.generate("recursive_source_is_argument", defaultStrategy); //$NON-NLS-1$
		this.generate("recursive_source_is_not_argument", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}
}
