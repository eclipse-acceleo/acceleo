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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Generated Text</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedText#getSourceElement <em>Source Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedText#getModuleElement <em>Module Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedText#getOutputFile <em>Output File</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedText#getStartOffset <em>Start Offset</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedText#getEndOffset <em>End Offset</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedText()
 * @model superTypes="org.eclipse.acceleo.traceability.IComparable<org.eclipse.acceleo.traceability.GeneratedText>"
 * @generated
 */
public interface GeneratedText extends EObject, Comparable<GeneratedText> {
	/**
	 * Returns the value of the '<em><b>Source Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Element</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Element</em>' reference.
	 * @see #setSourceElement(InputElement)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedText_SourceElement()
	 * @model
	 * @generated
	 */
	InputElement getSourceElement();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.GeneratedText#getSourceElement <em>Source Element</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Element</em>' reference.
	 * @see #getSourceElement()
	 * @generated
	 */
	void setSourceElement(InputElement value);

	/**
	 * Returns the value of the '<em><b>Module Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Element</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Element</em>' reference.
	 * @see #setModuleElement(ModuleElement)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedText_ModuleElement()
	 * @model
	 * @generated
	 */
	ModuleElement getModuleElement();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.GeneratedText#getModuleElement <em>Module Element</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Element</em>' reference.
	 * @see #getModuleElement()
	 * @generated
	 */
	void setModuleElement(ModuleElement value);

	/**
	 * Returns the value of the '<em><b>Output File</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.acceleo.traceability.GeneratedFile#getGeneratedRegions <em>Generated Regions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output File</em>' container reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output File</em>' container reference.
	 * @see #setOutputFile(GeneratedFile)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedText_OutputFile()
	 * @see org.eclipse.acceleo.traceability.GeneratedFile#getGeneratedRegions
	 * @model opposite="generatedRegions" transient="false"
	 * @generated
	 */
	GeneratedFile getOutputFile();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.GeneratedText#getOutputFile <em>Output File</em>}' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output File</em>' container reference.
	 * @see #getOutputFile()
	 * @generated
	 */
	void setOutputFile(GeneratedFile value);

	/**
	 * Returns the value of the '<em><b>Start Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Offset</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Offset</em>' attribute.
	 * @see #setStartOffset(int)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedText_StartOffset()
	 * @model
	 * @generated
	 */
	int getStartOffset();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.GeneratedText#getStartOffset <em>Start Offset</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Offset</em>' attribute.
	 * @see #getStartOffset()
	 * @generated
	 */
	void setStartOffset(int value);

	/**
	 * Returns the value of the '<em><b>End Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Offset</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Offset</em>' attribute.
	 * @see #setEndOffset(int)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedText_EndOffset()
	 * @model
	 * @generated
	 */
	int getEndOffset();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.GeneratedText#getEndOffset <em>End Offset</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Offset</em>' attribute.
	 * @see #getEndOffset()
	 * @generated
	 */
	void setEndOffset(int value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	int compareTo(GeneratedText other);

} // GeneratedText
