/**
 */
package org.eclipse.acceleo.textualgame;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Room</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.textualgame.Room#getStates <em>States</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.Room#getDefaultState <em>Default State</em>}</li>
 * <li>{@link org.eclipse.acceleo.textualgame.Room#getActions <em>Actions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getRoom()
 * @model
 * @generated
 */
public interface Room extends NamedElement {
	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.textualgame.RoomState}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getRoom_States()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<RoomState> getStates();

	/**
	 * Returns the value of the '<em><b>Default State</b></em>' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Default State</em>' reference.
	 * @see #setDefaultState(RoomState)
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getRoom_DefaultState()
	 * @model required="true"
	 * @generated
	 */
	RoomState getDefaultState();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.textualgame.Room#getDefaultState <em>Default
	 * State</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Default State</em>' reference.
	 * @see #getDefaultState()
	 * @generated
	 */
	void setDefaultState(RoomState value);

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.textualgame.Action}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see org.eclipse.acceleo.textualgame.TextualgamePackage#getRoom_Actions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Action> getActions();

} // Room
