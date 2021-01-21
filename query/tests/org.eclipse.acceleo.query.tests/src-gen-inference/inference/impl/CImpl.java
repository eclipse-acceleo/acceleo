/**
 */
package inference.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import inference.C;
import inference.InferencePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>C</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link inference.impl.CImpl#getCAttr <em>CAttr</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CImpl extends BImpl implements C {
	/**
	 * The default value of the '{@link #getCAttr() <em>CAttr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCAttr()
	 * @generated
	 * @ordered
	 */
	protected static final Boolean CATTR_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getCAttr() <em>CAttr</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCAttr()
	 * @generated
	 * @ordered
	 */
	protected Boolean cAttr = CATTR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InferencePackage.Literals.C;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Boolean getCAttr() {
		return cAttr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCAttr(Boolean newCAttr) {
		Boolean oldCAttr = cAttr;
		cAttr = newCAttr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InferencePackage.C__CATTR, oldCAttr, cAttr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InferencePackage.C__CATTR:
				return getCAttr();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InferencePackage.C__CATTR:
				setCAttr((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case InferencePackage.C__CATTR:
				setCAttr(CATTR_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case InferencePackage.C__CATTR:
				return CATTR_EDEFAULT == null ? cAttr != null : !CATTR_EDEFAULT.equals(cAttr);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (cAttr: ");
		result.append(cAttr);
		result.append(')');
		return result.toString();
	}

} //CImpl
