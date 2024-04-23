/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.model;

import org.eclipse.emf.common.util.URI;

/**
 * Breakpoint {@link IDSLModelRequest request}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractBreakpointRequest implements IDSLModelRequest {

	/**
	 * The {@link URI} pointing the {@link rg.eclipse.emf.ecore.EObject instruction}.
	 */
	private final URI uri;

	/**
	 * Constructor.
	 * 
	 * @param uri
	 *            the {@link URI} pointing the {@link rg.eclipse.emf.ecore.EObject instruction}
	 */
	public AbstractBreakpointRequest(URI uri) {
		this.uri = uri;
	}

	/**
	 * Gets the {@link URI} pointing the {@link rg.eclipse.emf.ecore.EObject instruction}.
	 * 
	 * @return the {@link URI} pointing the {@link rg.eclipse.emf.ecore.EObject instruction}
	 */
	public URI getURI() {
		return uri;
	}

}
