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
 * Request sent to change a breakpoint.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ChangeBreakPointRequest extends AbstractBreakpointRequest {

	/**
	 * The attribute to change.
	 */
	private final String attribute;

	/**
	 * The new value of the attribute.
	 */
	private final String value;

	/**
	 * Constructor.
	 * 
	 * @param uri
	 *            the {@link URI} pointing the {@link rg.eclipse.emf.ecore.EObject instruction}
	 * @param attribute
	 *            the attribute to change
	 * @param value
	 *            the new value of the attribute
	 */
	public ChangeBreakPointRequest(URI uri, String attribute, String value) {
		super(uri);
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * Gets the attribute to change.
	 * 
	 * @return the attribute to change
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * Gets the new value of the attribute.
	 * 
	 * @return the new value of the attribute
	 */
	public String getValue() {
		return value;
	}

}
