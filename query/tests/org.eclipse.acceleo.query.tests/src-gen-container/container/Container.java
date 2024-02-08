/**
 */
package container;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link container.Container#getContained <em>Contained</em>}</li>
 * </ul>
 *
 * @see container.ContainerPackage#getContainer()
 * @model
 * @generated
 */
public interface Container extends EObject {
	/**
	 * Returns the value of the '<em><b>Contained</b></em>' containment reference list.
	 * The list contents are of type {@link container.Contained}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contained</em>' containment reference list.
	 * @see container.ContainerPackage#getContainer_Contained()
	 * @model containment="true"
	 * @generated
	 */
	EList<Contained> getContained();

} // Container
