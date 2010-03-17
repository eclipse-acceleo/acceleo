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
package org.eclipse.acceleo.internal.ide.ui.views.result;

/**
 * A data element to store one model to text traceability information.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class TraceabilityElement {

	/**
	 * The parent.
	 */
	private TraceabilityElement parent;

	/**
	 * Gets the parent model to text traceability information.
	 * 
	 * @return the parent model to text traceability information, it can be null
	 */
	public TraceabilityElement getParent() {
		return parent;
	}

	/**
	 * Updates the parent model to text traceability information.
	 * 
	 * @param parent
	 *            is the new parent
	 */
	public void setParent(TraceabilityElement parent) {
		this.parent = parent;
	}

}
