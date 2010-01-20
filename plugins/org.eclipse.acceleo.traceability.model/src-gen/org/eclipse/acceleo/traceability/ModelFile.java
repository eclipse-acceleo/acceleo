/**
 * <copyright>
 * </copyright>
 *
 * $Id: ModelFile.java,v 1.1 2010/01/20 14:30:08 lgoubet Exp $
 */
package org.eclipse.acceleo.traceability;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model File</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.ModelFile#getInputElements <em>Input Elements</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getModelFile()
 * @model
 * @generated
 */
public interface ModelFile extends Resource {
	/**
	 * Returns the value of the '<em><b>Input Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.traceability.InputElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input Elements</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input Elements</em>' containment reference list.
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getModelFile_InputElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<InputElement> getInputElements();

} // ModelFile
