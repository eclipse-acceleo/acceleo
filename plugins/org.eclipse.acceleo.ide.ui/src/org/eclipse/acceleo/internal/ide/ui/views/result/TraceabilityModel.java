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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Model to text traceability information.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class TraceabilityModel extends TraceabilityContainer {

	/**
	 * The current EObject synchronized with the text.
	 */
	private EObject eObject;

	/** The Label we are to display for this element. */
	private String label;

	/**
	 * The regions of the text synchronized with the EObject.
	 */
	private List<TraceabilityRegion> regions;

	/**
	 * Constructor.
	 * 
	 * @param eObject
	 *            the current EObject synchronized with the text
	 * @param label
	 *            the Label we are to display for this element.
	 */
	public TraceabilityModel(EObject eObject, String label) {
		this.eObject = eObject;
		this.regions = new ArrayList<TraceabilityRegion>();
		this.label = label;
	}

	/**
	 * Gets the current EObject synchronized with the text.
	 * 
	 * @return the current EObject synchronized with the text
	 */
	public EObject getEObject() {
		return eObject;
	}

	/**
	 * Gets the regions of the text synchronized with the EObject.
	 * 
	 * @return the regions of the text synchronized with the EObject
	 */
	public List<TraceabilityRegion> getRegions() {
		return regions;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.views.result.AbstractTraceabilityElement#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (eObject != null) {
			String result;
			EStructuralFeature eFeature = eObject.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
			if (eFeature != null) {
				Object value = eObject.eGet(eFeature);
				result = "<" + eObject.eClass().getName() + "> " + String.valueOf(value); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				result = "<" + eObject.eClass().getName() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			return result;
		}
		return ""; //$NON-NLS-1$
	}

}
