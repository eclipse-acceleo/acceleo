/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.compatibility.tests.unit.migration;

import java.io.File;
import java.io.IOException;

/**
 * Class to test File script parameter.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class getPropertyTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.compatibility.gen.tests.unit.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getTestName() {
		return "getProperty";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.compatibility.gen.tests.unit.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "/data/" + getTestName() + "/mtlGenerated";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.compatibility.gen.tests.unit.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getMtlExpectedPath() {
		return "/data/" + getTestName() + "/mtlExpected";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.compatibility.gen.tests.unit.tests.unit.AbstractAcceleoTest#getEmtlModelPath()
	 */
	@Override
	public String getEmtlModelPath() {
		return "/data/" + getTestName() + "/emt/chain.emt";
	}

	/**
	 * Test file script translate.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void testGetProperty() throws IOException {

		generationRoot = new File(getMtlTargetRootPath());
		// emtlModelRoot = new File(getEmtlModelPath());
		mtlExpectedRoot = new File(getMtlExpectedRootPath());

		cleanMtlGenerationRoot();
		try {
			generateEmt();
			compareDirectories(mtlExpectedRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}
}
