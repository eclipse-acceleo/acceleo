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
package org.eclipse.acceleo.compatibility.tests.unit.migration;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Class to test File script parameter.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class UserCodeTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.compatibility.gen.tests.unit.tests.unit.AbstractAcceleoTest#getTestName()
	 */
	@Override
	public String getTestName() {
		return "userCode";
	}

	/**
	 * Test file script translate.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Ignore
	@Test
	public void testUserCode() throws IOException {
		// launch test
		genericTest();
	}
}
