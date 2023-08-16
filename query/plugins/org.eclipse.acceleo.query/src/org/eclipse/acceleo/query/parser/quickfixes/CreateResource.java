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
 * Create a resource.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CreateResource implements IAstResourceChange {

	/**
	 * The {@link URI} of the resource to create.
	 */
	private final URI uri;

	/**
	 * Constructor.
	 * 
	 * @param uri
	 *            the {@link URI} of the resource to create
	 */
	public CreateResource(URI uri) {
		this.uri = uri;
	}

	/**
	 * Gets the {@link URI} of the resource to create.
	 * 
	 * @return the {@link URI} of the resource to create
	 */
	public URI getUri() {
		return uri;
	};

	@Override
	public String toString() {
		return "Create " + getUri();
	}

}
