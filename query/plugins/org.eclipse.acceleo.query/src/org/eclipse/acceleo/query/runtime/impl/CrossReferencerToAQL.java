/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.Collection;

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;

/**
 * An Wrapper for a {@link CrossReferencer}.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public class CrossReferencerToAQL implements CrossReferenceProvider {
	/**
	 * Wrapped cross referencer.
	 */
	private CrossReferencer xRef;

	/**
	 * Create a {@link CrossReferenceProvider} by wrapping an EMF {@link CrossReferencer}.
	 * 
	 * @param xRef
	 *            the cross referencer to wrap.
	 */
	public CrossReferencerToAQL(CrossReferencer xRef) {
		this.xRef = xRef;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.CrossReferenceProvider#getInverseReferences(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public Collection<Setting> getInverseReferences(EObject self) {
		return xRef.get(self);
	}

}
