/**
 */
package org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.ConstraintResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.EvaluationResultPackage;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLResult;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.Severity;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>OCL Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLElementImpl#getElement <em>Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.impl.OCLElementImpl#getChildren <em>Children</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OCLElementImpl extends MinimalEObjectImpl.Container implements OCLElement {
	/**
	 * The cached value of the '{@link #getElement() <em>Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElement()
	 * @generated
	 * @ordered
	 */
	protected EObject element;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<OCLElement> children;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OCLElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EvaluationResultPackage.Literals.OCL_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getElement() {
		if (element != null && element.eIsProxy()) {
			InternalEObject oldElement = (InternalEObject)element;
			element = eResolveProxy(oldElement);
			if (element != oldElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							EvaluationResultPackage.OCL_ELEMENT__ELEMENT, oldElement, element));
			}
		}
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetElement() {
		return element;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElement(EObject newElement) {
		EObject oldElement = element;
		element = newElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EvaluationResultPackage.OCL_ELEMENT__ELEMENT, oldElement, element));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OCLElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<OCLElement>(OCLElement.class, this,
					EvaluationResultPackage.OCL_ELEMENT__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Severity getWorstSeverity() {
		Severity severity = Severity.OK;
		final Iterator<EObject> allContent = eAllContents();
		while (allContent.hasNext() && severity != Severity.ERROR) {
			final EObject next = allContent.next();
			if (next instanceof ConstraintResult) {
				final Severity nextSeverity = ((ConstraintResult)next).getSeverity();
				if (nextSeverity.getValue() > severity.getValue()) {
					severity = nextSeverity;
				}
			} else if (next instanceof OCLResult) {
				final OCLResult result = (OCLResult)next;
				if (result.getInterpreterResult().getStatus() != null
						&& !result.getInterpreterResult().getStatus().isOK()) {
					severity = Severity.ERROR;
				}
			}
		}
		return severity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EvaluationResultPackage.OCL_ELEMENT__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EvaluationResultPackage.OCL_ELEMENT__ELEMENT:
				if (resolve)
					return getElement();
				return basicGetElement();
			case EvaluationResultPackage.OCL_ELEMENT__CHILDREN:
				return getChildren();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EvaluationResultPackage.OCL_ELEMENT__ELEMENT:
				setElement((EObject)newValue);
				return;
			case EvaluationResultPackage.OCL_ELEMENT__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends OCLElement>)newValue);
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
			case EvaluationResultPackage.OCL_ELEMENT__ELEMENT:
				setElement((EObject)null);
				return;
			case EvaluationResultPackage.OCL_ELEMENT__CHILDREN:
				getChildren().clear();
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
			case EvaluationResultPackage.OCL_ELEMENT__ELEMENT:
				return element != null;
			case EvaluationResultPackage.OCL_ELEMENT__CHILDREN:
				return children != null && !children.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //OCLElementImpl
