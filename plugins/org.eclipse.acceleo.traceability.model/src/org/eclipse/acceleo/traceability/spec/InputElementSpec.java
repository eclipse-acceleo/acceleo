/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
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
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This specific implementation of the {@link org.eclipse.acceleo.traceability.InputElement} will deal with
 * non generated bits.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class InputElementSpec extends InputElementImpl {
	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof InputElement) {
			if (getModelElement() == ((InputElement)obj).getModelElement()) {
				return getFeature() == ((InputElement)obj).getFeature();
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (getModelElement() == null && getFeature() == null) {
			return super.hashCode();
		}

		int hashcode = 0;
		if (getModelElement() != null) {
			hashcode = getModelElement().hashCode();
		}
		if (getFeature() != null) {
			hashcode += getFeature().hashCode();
		}
		return hashcode;
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
