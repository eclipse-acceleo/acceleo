/**
 */
package org.eclipse.acceleo.textualgame;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Illustrated Element</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.IllustratedElement#getImage <em>Image</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getIllustratedElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IllustratedElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Image</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the value of the '<em>Image</em>' attribute.
	 * @see #setImage(String)
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getIllustratedElement_Image()
	 * @model
	 * @generated
	 */
	String getImage();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.textualgame.IllustratedElement#getImage
	 * <em>Image</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Image</em>' attribute.
	 * @see #getImage()
	 * @generated
	 */
	void setImage(String value);

} // IllustratedElement
