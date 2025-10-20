/**
 */
package org.eclipse.acceleo.textualgame;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Has Items</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.HasItems#getItems <em>Items</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getHasItems()
 * @model
 * @generated
 */
public interface HasItems extends Condition {
	/**
	 * Returns the value of the '<em><b>Items</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.textualgame.Item}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Items</em>' reference list.
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getHasItems_Items()
	 * @model required="true"
	 * @generated
	 */
	EList<Item> getItems();

} // HasItems
