/**
 */
package org.eclipse.acceleo.textualgame.util;

import org.eclipse.acceleo.textualgame.Action;
import org.eclipse.acceleo.textualgame.AddItemsToInventory;
import org.eclipse.acceleo.textualgame.Condition;
import org.eclipse.acceleo.textualgame.ConditionalElement;
import org.eclipse.acceleo.textualgame.Game;
import org.eclipse.acceleo.textualgame.GotoRoom;
import org.eclipse.acceleo.textualgame.HasItems;
import org.eclipse.acceleo.textualgame.IllustratedElement;
import org.eclipse.acceleo.textualgame.InRoom;
import org.eclipse.acceleo.textualgame.InRoomState;
import org.eclipse.acceleo.textualgame.Item;
import org.eclipse.acceleo.textualgame.NamedElement;
import org.eclipse.acceleo.textualgame.Restart;
import org.eclipse.acceleo.textualgame.Room;
import org.eclipse.acceleo.textualgame.RoomState;
import org.eclipse.acceleo.textualgame.TextualgamePackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.acceleo.textualgame.TextualgamePackage
 * @generated
 */
public class TextualgameSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static TextualgamePackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public TextualgameSwitch() {
		if (modelPackage == null) {
			modelPackage = TextualgamePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param ePackage
	 *            the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields
	 * that result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TextualgamePackage.GAME: {
				Game game = (Game)theEObject;
				T result = caseGame(game);
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.ROOM: {
				Room room = (Room)theEObject;
				T result = caseRoom(room);
				if (result == null) {
					result = caseNamedElement(room);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.ROOM_STATE: {
				RoomState roomState = (RoomState)theEObject;
				T result = caseRoomState(roomState);
				if (result == null) {
					result = caseNamedElement(roomState);
				}
				if (result == null) {
					result = caseIllustratedElement(roomState);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.NAMED_ELEMENT: {
				NamedElement namedElement = (NamedElement)theEObject;
				T result = caseNamedElement(namedElement);
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.ACTION: {
				Action action = (Action)theEObject;
				T result = caseAction(action);
				if (result == null) {
					result = caseNamedElement(action);
				}
				if (result == null) {
					result = caseConditionalElement(action);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.CONDITIONAL_ELEMENT: {
				ConditionalElement conditionalElement = (ConditionalElement)theEObject;
				T result = caseConditionalElement(conditionalElement);
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.ILLUSTRATED_ELEMENT: {
				IllustratedElement illustratedElement = (IllustratedElement)theEObject;
				T result = caseIllustratedElement(illustratedElement);
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.ITEM: {
				Item item = (Item)theEObject;
				T result = caseItem(item);
				if (result == null) {
					result = caseNamedElement(item);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.GOTO_ROOM: {
				GotoRoom gotoRoom = (GotoRoom)theEObject;
				T result = caseGotoRoom(gotoRoom);
				if (result == null) {
					result = caseAction(gotoRoom);
				}
				if (result == null) {
					result = caseNamedElement(gotoRoom);
				}
				if (result == null) {
					result = caseConditionalElement(gotoRoom);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.ADD_ITEMS_TO_INVENTORY: {
				AddItemsToInventory addItemsToInventory = (AddItemsToInventory)theEObject;
				T result = caseAddItemsToInventory(addItemsToInventory);
				if (result == null) {
					result = caseAction(addItemsToInventory);
				}
				if (result == null) {
					result = caseNamedElement(addItemsToInventory);
				}
				if (result == null) {
					result = caseConditionalElement(addItemsToInventory);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.RESTART: {
				Restart restart = (Restart)theEObject;
				T result = caseRestart(restart);
				if (result == null) {
					result = caseAction(restart);
				}
				if (result == null) {
					result = caseNamedElement(restart);
				}
				if (result == null) {
					result = caseConditionalElement(restart);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.CONDITION: {
				Condition condition = (Condition)theEObject;
				T result = caseCondition(condition);
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.IN_ROOM: {
				InRoom inRoom = (InRoom)theEObject;
				T result = caseInRoom(inRoom);
				if (result == null) {
					result = caseCondition(inRoom);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.HAS_ITEMS: {
				HasItems hasItems = (HasItems)theEObject;
				T result = caseHasItems(hasItems);
				if (result == null) {
					result = caseCondition(hasItems);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			case TextualgamePackage.IN_ROOM_STATE: {
				InRoomState inRoomState = (InRoomState)theEObject;
				T result = caseInRoomState(inRoomState);
				if (result == null) {
					result = caseCondition(inRoomState);
				}
				if (result == null) {
					result = defaultCase(theEObject);
				}
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Game</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Game</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGame(Game object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Room</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Room</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoom(Room object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Room State</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Room State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoomState(RoomState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAction(Action object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Conditional Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Conditional Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConditionalElement(ConditionalElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Illustrated Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Illustrated Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIllustratedElement(IllustratedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Item</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseItem(Item object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Goto Room</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Goto Room</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGotoRoom(GotoRoom object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Add Items To Inventory</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Add Items To Inventory</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAddItemsToInventory(AddItemsToInventory object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Restart</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Restart</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRestart(Restart object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Condition</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCondition(Condition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>In Room</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>In Room</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInRoom(InRoom object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Has Items</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Has Items</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHasItems(HasItems object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>In Room State</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>In Room State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInRoomState(InRoomState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch, but this
	 * is the last case anyway. <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // TextualgameSwitch
