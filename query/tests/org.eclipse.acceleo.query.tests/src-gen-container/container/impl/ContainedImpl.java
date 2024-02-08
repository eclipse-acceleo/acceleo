/**
 */
package container.impl;

import container.Contained;
import container.ContainerPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contained</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ContainedImpl extends MinimalEObjectImpl.Container implements Contained {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContainedImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContainerPackage.Literals.CONTAINED;
	}

} //ContainedImpl
