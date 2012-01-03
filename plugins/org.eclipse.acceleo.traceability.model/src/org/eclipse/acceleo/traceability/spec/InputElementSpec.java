/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.spec;

import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.impl.InputElementImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This specific implementation of the {@link org.eclipse.acceleo.traceability.InputElement} will deal with
 * non generated bits.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class InputElementSpec extends InputElementImpl {
	/**
	 * We know that neither the model element nor its feature will ever be modified during a generation, we
	 * can then cache the hashcode.
	 */
	private transient int hashCode;

	/**
	 * {@inheritDoc}
	 * 
	 * @see InputElementImpl#setFeature(EStructuralFeature)
	 */
	@Override
	public void setFeature(EStructuralFeature newFeature) {
		hashCode = 0;
		super.setFeature(newFeature);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see InputElementImpl#setModel(EObject)
	 */
	@Override
	public void setModelElement(EObject newModelElement) {
		hashCode = 0;
		super.setModelElement(newModelElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (hashCode != 0) {
			return hashCode;
		}

		final int prime = 31;
		hashCode = prime;

		if (modelElement == null) {
			hashCode = prime * hashCode;
		} else {
			hashCode = prime * hashCode + modelElement.hashCode();
		}

		if (feature == null) {
			hashCode = prime * hashCode;
		} else {
			hashCode = prime * hashCode + feature.hashCode();
		}

		return hashCode;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof InputElement) {
			EObject otherModelElement = ((InputElement)other).getModelElement();
			boolean equal = true;
			if (modelElement == null) {
				equal = otherModelElement == null;
			} else {
				equal = modelElement.equals(otherModelElement);
			}
			if (equal && feature == null) {
				equal = ((InputElement)other).getFeature() == null;
			} else if (equal) {
				equal = feature.equals(((InputElement)other).getFeature());
			}
			return equal;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.impl.BasicEObjectImpl#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder buffer = new StringBuilder();
		final EStructuralFeature nameFeature = getModelElement().eClass().getEStructuralFeature("name"); //$NON-NLS-1$
		if (nameFeature != null) {
			buffer.append(getModelElement().eGet(nameFeature));
		} else {
			buffer.append(getModelElement().toString());
		}
		if (getFeature() != null) {
			buffer.append(", feature='"); //$NON-NLS-1$
			buffer.append(getFeature().getName());
			buffer.append("'"); //$NON-NLS-1$
		}
		if (getOperation() != null) {
			buffer.append(", operation='"); //$NON-NLS-1$
			buffer.append(getOperation().getName());
			buffer.append("'"); //$NON-NLS-1$
		}
		return buffer.toString();
	}
}
