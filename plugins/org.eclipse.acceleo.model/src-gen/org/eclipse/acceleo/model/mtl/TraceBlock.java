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
package org.eclipse.acceleo.model.mtl;

import org.eclipse.ocl.ecore.OCLExpression;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Trace Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.TraceBlock#getModelElement <em>Model Element</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTraceBlock()
 * @model
 * @generated
 */
public interface TraceBlock extends Block {
	/**
	 * Returns the value of the '<em><b>Model Element</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Element</em>' containment reference isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Model Element</em>' containment reference.
	 * @see #setModelElement(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTraceBlock_ModelElement()
	 * @model containment="true" required="true"
	 * @generated
	 */
	OCLExpression getModelElement();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.TraceBlock#getModelElement
	 * <em>Model Element</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Model Element</em>' containment reference.
	 * @see #getModelElement()
	 * @generated
	 */
	void setModelElement(OCLExpression value);

} // TraceBlock
