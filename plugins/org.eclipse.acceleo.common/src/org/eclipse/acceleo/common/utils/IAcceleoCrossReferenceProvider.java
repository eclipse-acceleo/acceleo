/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.utils;

import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

/**
 * Whenever the eInverse operation is called, Acceleo will try and adapt the EObject for which cross
 * references are needed into an implementation of this. If any adapter is found, we will use it instead of
 * our own CrossReferencer in order to determine cross references for an element.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @see AcceleoNonStandardLibrary#OPERATION_EOBJECT_EINVERSE
 * @since 3.2
 */
public interface IAcceleoCrossReferenceProvider {
	/**
	 * This will be called by Acceleo whenever eInverse() is called with no type filter. It is expected to
	 * return all inverse references towards <code>eObject</code>.
	 * 
	 * @param eObject
	 *            The EObject for which we need all inverse references.
	 * @return All inverse references towards the given eObject, at empty list if none. May not be
	 *         <code>null</code>.
	 */
	Set<EObject> getInverseReferences(EObject eObject);

	/**
	 * This will be called by Acceleo whenever eInverse() is called with a type filter. It is expected to
	 * return all inverse references towards <code>eObject</code> that are of type <code>filter</code>.
	 * 
	 * @param eObject
	 *            The EObject for which we need all inverse references.
	 * @param filter
	 *            The type filter for the resulting EObjects.
	 * @return All inverse references of the given type towards the given eObject, at empty list if none. May
	 *         not be <code>null</code>.
	 */
	Set<EObject> getInverseReferences(EObject eObject, EClassifier filter);
}
