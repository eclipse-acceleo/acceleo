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
package org.eclipse.acceleo.traceability.spec;

import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.impl.ModuleElementImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * This specific implementation of the {@link org.eclipse.acceleo.traceability.ModuleElement} will deal with
 * non generated bits.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ModuleElementSpec extends ModuleElementImpl {
	/**
	 * We know that the module element will never be modified during a generation, we can then cache the
	 * hashcode.
	 */
	private transient int hashCode;

	/**
	 * {@inheritDoc}
	 * 
	 * @see ModuleElementImpl#setModuleElement(EObject)
	 */
	@Override
	public void setModuleElement(EObject newModuleElement) {
		hashCode = 0;
		super.setModuleElement(newModuleElement);
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

		if (moduleElement == null) {
			hashCode = prime * hashCode;
		} else {
			hashCode = prime * hashCode + moduleElement.hashCode();
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
		if (other instanceof ModuleElement) {
			EObject otherModuleElement = ((ModuleElement)other).getModuleElement();
			return (moduleElement == null && otherModuleElement == null)
					|| (moduleElement != null && moduleElement.equals(otherModuleElement));
		}
		return false;
	}
}
