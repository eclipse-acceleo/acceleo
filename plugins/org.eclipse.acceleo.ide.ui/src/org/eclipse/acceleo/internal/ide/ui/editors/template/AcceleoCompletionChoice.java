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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.ocl.helper.Choice;
import org.eclipse.ocl.helper.ChoiceKind;

/**
 * Description of an Acceleo syntax completion choice. An Acceleo choice is based on an OCL choice. The choice
 * also can provide the actual Acceleo model element that it represents.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCompletionChoice implements Choice {

	/**
	 * The OCL choice.
	 */
	private Choice oclChoice;

	/**
	 * The Acceleo element.
	 */
	private ModuleElement acceleoElement;

	/**
	 * Constructor.
	 * 
	 * @param oclChoice
	 *            the OCL choice
	 * @param acceleoElement
	 *            the Acceleo element
	 */
	AcceleoCompletionChoice(Choice oclChoice, ModuleElement acceleoElement) {
		this.oclChoice = oclChoice;
		this.acceleoElement = acceleoElement;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.helper.Choice#getName()
	 */
	public String getName() {
		return oclChoice.getName();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.helper.Choice#getDescription()
	 */
	public String getDescription() {
		return oclChoice.getDescription();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.helper.Choice#getKind()
	 */
	public ChoiceKind getKind() {
		return oclChoice.getKind();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.helper.Choice#getElement()
	 */
	public Object getElement() {
		return oclChoice.getElement();
	}

	/**
	 * Gets the Acceleo element.
	 * 
	 * @return the Acceleo element
	 */
	public ModuleElement getAcceleoElement() {
		return acceleoElement;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return oclChoice.hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AcceleoCompletionChoice) {
			return oclChoice.equals(((AcceleoCompletionChoice)obj).oclChoice);
		}
		return oclChoice.equals(obj);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return oclChoice.toString();
	}
}
