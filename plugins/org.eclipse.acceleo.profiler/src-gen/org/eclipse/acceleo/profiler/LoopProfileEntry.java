/**
 * <copyright>
 * </copyright>
 *
 * $Id: LoopProfileEntry.java,v 1.2 2010/05/04 07:38:00 lgoubet Exp $
 */
package org.eclipse.acceleo.profiler;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Loop Profile Entry</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.profiler.LoopProfileEntry#getLoopElements <em>Loop Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.profiler.ProfilerPackage#getLoopProfileEntry()
 * @model
 * @generated
 * @since 3.0
 */
public interface LoopProfileEntry extends ProfileEntry {
	/**
	 * Returns the value of the '<em><b>Loop Elements</b></em>' containment reference list. The list contents
	 * are of type {@link org.eclipse.acceleo.profiler.ProfileEntry}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Loop Elements</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Loop Elements</em>' containment reference list.
	 * @see org.eclipse.acceleo.profiler.ProfilerPackage#getLoopProfileEntry_LoopElements()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProfileEntry> getLoopElements();

} // LoopProfileEntry
