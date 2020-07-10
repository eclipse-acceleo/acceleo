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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Loop Profile Entry</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.aql.profiler.LoopProfileEntry#getLoopElements <em>Loop Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getLoopProfileEntry()
 * @model
 * @generated
 * @since 4.0
 */
public interface LoopProfileEntry extends ProfileEntry {
	/**
	 * Returns the value of the '<em><b>Loop Elements</b></em>' containment reference list. The list contents
	 * are of type {@link org.eclipse.acceleo.aql.profiler.ProfileEntry}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Loop Elements</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Loop Elements</em>' containment reference list.
	 * @see org.eclipse.acceleo.aql.profiler.ProfilerPackage#getLoopProfileEntry_LoopElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProfileEntry> getLoopElements();

} // LoopProfileEntry
