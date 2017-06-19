/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation.writer;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;

/**
 * A writer that simply discards written bytes.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class NullWriter implements IAcceleoWriter {

	/**
	 * The target {@link URI}.
	 */
	private final URI targetURI;

	/**
	 * Constructor.
	 * 
	 * @param targetURI
	 *            the target {@link URI}
	 */
	public NullWriter(URI targetURI) {
		this.targetURI = targetURI;
	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public Appendable append(CharSequence csq) throws IOException {
		return null;
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws IOException {
		return null;
	}

	@Override
	public Appendable append(char c) throws IOException {
		return null;
	}

	@Override
	public URI getTargetURI() {
		return targetURI;
	}
}
