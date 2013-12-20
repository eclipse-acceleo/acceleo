/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.encoding;

import java.io.IOException;

import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This will be used to test the behavior of Acceleo when trying to generate text in distinct encodings.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
@Ignore
public class GenerationEncodingTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/Encoding/generation_encoding.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "Encoding";
	}

	/**
	 * Checks that we could properly generate a file with UTF-8 encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	@Test
	public void testEvaluateUTF8Template() throws IOException {
		this.init("UTF8"); //$NON-NLS-1$
		this.generate("generate_UTF_8", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Checks that we could properly generate a file with ISO-8859-1 encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	@Test
	public void testEvaluateLatin1Template() throws IOException {
		this.init("Latin1"); //$NON-NLS-1$
		this.generate("generate_ISO_8859_1", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Checks that we could properly generate a file with ISO-8859-5 encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	@Test
	public void testEvaluateRussianTemplate() throws IOException {
		this.init("Russian"); //$NON-NLS-1$
		this.generate("generate_ISO_8859_5", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Checks that we could properly generate a file with ISO-8859-7 encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	@Test
	public void testEvaluateGreekTemplate() throws IOException {
		this.init("Greek"); //$NON-NLS-1$
		this.generate("generate_ISO_8859_7", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Checks that we cannot generate a file with an inexistant encoding.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	@Test
	public void testEvaluateWrongEncodingTemplate() throws IOException {
		this.init("WrongEncoding"); //$NON-NLS-1$
		this.generate("generate_wrong_encoding", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}
}
