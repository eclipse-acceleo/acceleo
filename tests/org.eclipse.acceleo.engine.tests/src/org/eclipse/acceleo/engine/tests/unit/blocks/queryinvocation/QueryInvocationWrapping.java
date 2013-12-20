package org.eclipse.acceleo.engine.tests.unit.blocks.queryinvocation;

import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.junit.Test;

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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "QueryInvocation"; //$NON-NLS-1$
	}

	/**
	 * Tests that the template invocations can be used as parameters of other template invocations.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testQueryInvocationWrapping() throws IOException {
		this.init("QueryInvocationWrapping"); //$NON-NLS-1$
		this.generate("test", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}
}
