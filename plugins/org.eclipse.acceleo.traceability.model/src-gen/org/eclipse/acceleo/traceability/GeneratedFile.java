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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Generated File</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedFile#getGeneratedRegions <em>Generated Regions</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedFile#getSourceElements <em>Source Elements</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedFile#getNameRegions <em>Name Regions</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedFile#getFileBlock <em>File Block</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.GeneratedFile#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedFile()
 * @model
 * @generated
 */
public interface GeneratedFile extends Resource {
	/**
	 * Returns the value of the '<em><b>Generated Regions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.traceability.GeneratedText}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.acceleo.traceability.GeneratedText#getOutputFile <em>Output File</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generated Regions</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generated Regions</em>' containment reference list.
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedFile_GeneratedRegions()
	 * @see org.eclipse.acceleo.traceability.GeneratedText#getOutputFile
	 * @model opposite="outputFile" containment="true"
	 * @generated
	 */
	EList<GeneratedText> getGeneratedRegions();

	/**
	 * Returns the value of the '<em><b>Source Elements</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.traceability.InputElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Elements</em>' reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Elements</em>' reference list.
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedFile_SourceElements()
	 * @model
	 * @generated
	 */
	EList<InputElement> getSourceElements();

	/**
	 * Returns the value of the '<em><b>Name Regions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.traceability.GeneratedText}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name Regions</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name Regions</em>' containment reference list.
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedFile_NameRegions()
	 * @model containment="true"
	 * @generated
	 */
	EList<GeneratedText> getNameRegions();

	/**
	 * Returns the value of the '<em><b>File Block</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Block</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Block</em>' reference.
	 * @see #setFileBlock(ModuleElement)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedFile_FileBlock()
	 * @model
	 * @generated
	 */
	ModuleElement getFileBlock();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.GeneratedFile#getFileBlock <em>File Block</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>File Block</em>' reference.
	 * @see #getFileBlock()
	 * @generated
	 */
	void setFileBlock(ModuleElement value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getGeneratedFile_Length()
	 * @model
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.traceability.GeneratedFile#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

} // GeneratedFile
