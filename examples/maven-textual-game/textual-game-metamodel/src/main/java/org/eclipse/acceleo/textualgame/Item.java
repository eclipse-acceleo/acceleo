/**
 */
package org.eclipse.acceleo.textualgame;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Item</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.Item#getActions <em>Actions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getItem()
 * @model
 * @generated
 */
public interface Item extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.textualgame.Action}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getItem_Actions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Action> getActions();

} // Item
