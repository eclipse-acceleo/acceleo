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
package org.eclipse.acceleo.compatibility.model.mt;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Resource Set</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.ResourceSet#getResources <em>Resources</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.MtPackage#getResourceSet()
 * @model
 * @generated
 */
public interface ResourceSet extends EObject {
	/**
	 * Returns the value of the '<em><b>Resources</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.acceleo.compatibility.model.mt.Resource}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Resources</em>' containment reference list.
	 * @see org.eclipse.acceleo.compatibility.model.mt.MtPackage#getResourceSet_Resources()
	 * @model containment="true"
	 * @generated
	 */
	EList<Resource> getResources();

} // ResourceSet
