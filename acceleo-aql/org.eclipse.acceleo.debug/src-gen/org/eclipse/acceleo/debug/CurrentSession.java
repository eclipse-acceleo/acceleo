/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Current Session</b></em>'. <!--
 * end-user-doc --> <!-- begin-model-doc --> A {@link DebugTarget} container. <!-- end-model-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.debug.CurrentSession#getDebugTargets <em>Debug Targets</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage#getCurrentSession()
 * @model
 * @generated
 */
public interface CurrentSession extends EObject {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * Returns the value of the '<em><b>Debug Targets</b></em>' containment reference list. The list contents
	 * are of type {@link org.eclipse.acceleo.debug.DebugTarget}. <!-- begin-user-doc --> <!-- end-user-doc
	 * --> <!-- begin-model-doc --> {@link DebugTarget Debug targets}. <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Debug Targets</em>' containment reference list.
	 * @see org.eclipse.acceleo.debug.DebugPackage#getCurrentSession_DebugTargets()
	 * @model containment="true"
	 * @generated
	 */
	EList<DebugTarget> getDebugTargets();

} // CurrentSession
