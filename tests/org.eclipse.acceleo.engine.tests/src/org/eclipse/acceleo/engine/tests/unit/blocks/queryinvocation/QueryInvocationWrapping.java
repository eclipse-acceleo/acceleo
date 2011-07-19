package org.eclipse.acceleo.engine.tests.unit.blocks.queryinvocation;

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;

/**
 * Tests the behavior of query invocations.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class QueryInvocationWrapping extends AbstractAcceleoTest {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/QueryInvocation/query_invocation_wrapping.mtl"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "QueryInvocation"; //$NON-NLS-1$
	}

	/**
	 * Tests that the template invocations can be used as parameters of other template invocations.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testQueryInvocationWrapping() throws IOException {
		generationRoot = new File(getGenerationRootPath("QueryInvocationWrapping")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("QueryInvocationWrapping")); //$NON-NLS-1$

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
			assertEquals("Attributes  end" + "Attributes  end", content); //$NON-NLS-1$ //$NON-NLS-2$ 
		}
	}
}
