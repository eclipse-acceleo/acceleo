/**
 * <copyright>
 * </copyright>
 *
 * $Id: ModuleFile.java,v 1.2 2010/04/26 15:24:12 lgoubet Exp $
 */
package org.eclipse.acceleo.traceability;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module File</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.traceability.ModuleFile#getModuleElements <em>Module Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getModuleFile()
 * @model
 * @generated
 */
public interface ModuleFile extends Resource {
	/**
	 * Returns the value of the '<em><b>Module Elements</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.traceability.ModuleElement}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Elements</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Module Elements</em>' containment reference list.
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getModuleFile_ModuleElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModuleElement> getModuleElements();

} // ModuleFile
