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
package org.eclipse.acceleo.traceability;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Input Element</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.InputElement#getModelElement <em>Model Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.InputElement#getFeature <em>Feature</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.InputElement#getOperation <em>Operation</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getInputElement()
 * @model
 * @generated
 */
public interface InputElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Element</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Element</em>' reference.
	 * @see #setModelElement(EObject)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getInputElement_ModelElement()
	 * @model
	 * @generated
	 */
	EObject getModelElement();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.InputElement#getModelElement <em>Model Element</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Element</em>' reference.
	 * @see #getModelElement()
	 * @generated
	 */
	void setModelElement(EObject value);

	/**
	 * Returns the value of the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' reference.
	 * @see #setFeature(EStructuralFeature)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getInputElement_Feature()
	 * @model
	 * @generated
	 */
	EStructuralFeature getFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.InputElement#getFeature <em>Feature</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(EStructuralFeature value);

	/**
	 * Returns the value of the '<em><b>Operation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation</em>' reference.
	 * @see #setOperation(EOperation)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getInputElement_Operation()
	 * @model
	 * @generated
	 */
	EOperation getOperation();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.InputElement#getOperation <em>Operation</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation</em>' reference.
	 * @see #getOperation()
	 * @generated
	 */
	void setOperation(EOperation value);

} // InputElement
