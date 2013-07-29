/*******************************************************************************
 * Copyright (c) 2008, 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.generation.writers;

import java.io.IOException;

/**
 * This writer will intercept all the calls and do nothing.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.3
 */
public class AcceleoNullWriter extends AbstractAcceleoWriter {

	/**
	 * Suppresses super() behavior.
	 */
	public AcceleoNullWriter() {
		// Suppresses super() behavior.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#close()
	 */
	@Override
	public void close() throws IOException {
		// Suppresses super behavior.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#flush()
	 */
	@Override
	public void flush() throws IOException {
		// Suppresses super behavior.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#getTargetPath()
	 */
	@Override
	public String getTargetPath() {
		return ""; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#toString()
	 */
	@Override
	public String toString() {
		return ""; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#write(char[], int, int)
	 */
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		// Suppresses super behavior.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#write(int)
	 */
	@Override
	public void write(int c) throws IOException {
		// Suppresses super behavior.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.writers.AbstractAcceleoWriter#write(java.lang.String, int,
	 *      int)
	 */
	@Override
	public void write(String str, int off, int len) throws IOException {
		// Suppresses super behavior.
	}

}
