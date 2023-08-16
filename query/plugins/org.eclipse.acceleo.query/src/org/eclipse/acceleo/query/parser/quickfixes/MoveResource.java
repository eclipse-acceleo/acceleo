/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser.quickfixes;

import java.net.URI;

/**
 * Move a resource.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class MoveResource implements IAstResourceChange {

	/**
	 * The source resource {@link URI}.
	 */
	private final URI source;

	/**
	 * The target resource {@link URI}.
	 */
	private final URI target;

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            the source {@link URI}
	 * @param target
	 *            the target {@link URI}
	 */
	public MoveResource(URI source, URI target) {
		this.source = source;
		this.target = target;
	}

	/**
	 * Gets the source URI.
	 * 
	 * @return the source {@link URI}
	 */
	public URI getSource() {
		return source;
	}

	/**
	 * Gets the target {@link URI}
	 * 
	 * @return the target {@link URI}
	 */
	public URI getTarget() {
		return target;
	}

	@Override
	public String toString() {
		return "Move " + getSource() + " " + getTarget();
	}

}
