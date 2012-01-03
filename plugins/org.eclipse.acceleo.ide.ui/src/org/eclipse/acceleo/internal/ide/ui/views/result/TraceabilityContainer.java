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
package org.eclipse.acceleo.internal.ide.ui.views.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Data container to store the model to text traceability information.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class TraceabilityContainer extends AbstractTraceabilityElement {

	/**
	 * Children model to text traceability information.
	 */
	private List<TraceabilityModel> children;

	/**
	 * Constructor.
	 */
	public TraceabilityContainer() {
		this.children = new ArrayList<TraceabilityModel>();
	}

	/**
	 * Gets the children model to text traceability information.
	 * 
	 * @return the children model to text traceability information
	 */
	public List<TraceabilityModel> getChildren() {
		return children;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.views.result.AbstractTraceabilityElement#getLabel()
	 */
	@Override
	public String getLabel() {
		// Should be overriden
		return ""; //$NON-NLS-1$
	}
}
