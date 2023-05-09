/**
 *  Copyright (c) 2015 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.query.tests.nestedpackages.root;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entity Holder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.query.tests.nestedpackages.root.EntityHolder#getEntityInterfaces <em>Entity Interfaces</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.RootPackage#getEntityHolder()
 * @model
 * @generated
 */
public interface EntityHolder extends EObject {
	/**
	 * Returns the value of the '<em><b>Entity Interfaces</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.query.tests.nestedpackages.root.EntityInterface}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entity Interfaces</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entity Interfaces</em>' containment reference list.
	 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.RootPackage#getEntityHolder_EntityInterfaces()
	 * @model containment="true"
	 * @generated
	 */
	EList<EntityInterface> getEntityInterfaces();

} // EntityHolder
