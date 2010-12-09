/*******************************************************************************
 * ¤¤COPYRIGHT¤¤
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.blocks.templateinvocation;

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;

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
	public void testRecursiveTemplateInvocation() throws IOException {
		generationRoot = new File(getGenerationRootPath("RecursiveTemplateInvocation")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("RecursiveTemplateInvocation")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("recursive_source_is_argument", defaultStrategy); //$NON-NLS-1$
		generate("recursive_source_is_not_argument", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect two protected areas to have been created
			assertTrue(content.contains("startcontinuestartstop")); //$NON-NLS-1$
		}
	}
}
