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

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.emf.ecore.EObject;

/**
 * Template to text traceability information.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class TraceabilityTemplate extends TraceabilityModel {

	/**
	 * Constructor.
	 * 
	 * @param eTemplateElement
	 *            the current module synchronized with the text
	 * @param label
	 *            The label that is to be displayed for this element.
	 */
	public TraceabilityTemplate(Module eTemplateElement, String label) {
		super(eTemplateElement, label);
	}

	/**
	 * Constructor.
	 * 
	 * @param eTemplateElement
	 *            the current template element synchronized with the text
	 * @param label
	 *            The label that is to be displayed for this element.
	 */
	public TraceabilityTemplate(TemplateExpression eTemplateElement, String label) {
		super(eTemplateElement, label);
	}

	/**
	 * Gets the current template element synchronized with the text.
	 * 
	 * @return the current template element synchronized with the text
	 */
	public EObject getTemplateElement() {
		return getEObject();
	}

}
