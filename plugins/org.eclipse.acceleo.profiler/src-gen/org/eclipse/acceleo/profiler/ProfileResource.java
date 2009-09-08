/**
 * <copyright>
 * </copyright>
 *
 * $Id: ProfileResource.java,v 1.1 2009/09/08 16:10:04 ylussaud Exp $
 */
package org.eclipse.acceleo.profiler;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Profile Resource</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.profiler.ProfileResource#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.profiler.ProfilerPackage#getProfileResource()
 * @model
 * @generated
 */
public interface ProfileResource extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.profiler.ProfileEntry}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see org.eclipse.acceleo.profiler.ProfilerPackage#getProfileResource_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProfileEntry> getEntries();

} // ProfileResource
