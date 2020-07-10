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
package org.eclipse.acceleo.aql.profiler;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Profile Resource</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.aql.profiler.ProfileResource#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileResource()
 * @model
 * @generated
 * @since 4.0
 */
public interface ProfileResource extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.aql.profiler.ProfileEntry}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getProfileResource_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProfileEntry> getEntries();

} // ProfileResource
