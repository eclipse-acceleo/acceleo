/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.impl;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.acceleo.traceability.minimal.MinimalEObjectImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Generated Text</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedTextImpl#getSourceElement <em>Source Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedTextImpl#getModuleElement <em>Module Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedTextImpl#getOutputFile <em>Output File</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedTextImpl#getStartOffset <em>Start Offset</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedTextImpl#getEndOffset <em>End Offset</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GeneratedTextImpl extends MinimalEObjectImpl.Container implements GeneratedText {
	/**
	 * The cached value of the '{@link #getSourceElement() <em>Source Element</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSourceElement()
	 * @generated
	 * @ordered
	 */
	protected InputElement sourceElement;

	/**
	 * The cached value of the '{@link #getModuleElement() <em>Module Element</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModuleElement()
	 * @generated
	 * @ordered
	 */
	protected ModuleElement moduleElement;

	/**
	 * The default value of the '{@link #getStartOffset() <em>Start Offset</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartOffset()
	 * @generated
	 * @ordered
	 */
	protected static final int START_OFFSET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartOffset() <em>Start Offset</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartOffset()
	 * @generated
	 * @ordered
	 */
	protected int startOffset = START_OFFSET_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndOffset() <em>End Offset</em>}' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getEndOffset()
	 * @generated
	 * @ordered
	 */
	protected static final int END_OFFSET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndOffset() <em>End Offset</em>}' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getEndOffset()
	 * @generated
	 * @ordered
	 */
	protected int endOffset = END_OFFSET_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected GeneratedTextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraceabilityPackage.Literals.GENERATED_TEXT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InputElement getSourceElement() {
		if (sourceElement != null && sourceElement.eIsProxy()) {
			InternalEObject oldSourceElement = (InternalEObject)sourceElement;
			sourceElement = (InputElement)eResolveProxy(oldSourceElement);
			if (sourceElement != oldSourceElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TraceabilityPackage.GENERATED_TEXT__SOURCE_ELEMENT, oldSourceElement, sourceElement));
			}
		}
		return sourceElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InputElement basicGetSourceElement() {
		return sourceElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceElement(InputElement newSourceElement) {
		InputElement oldSourceElement = sourceElement;
		sourceElement = newSourceElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.GENERATED_TEXT__SOURCE_ELEMENT, oldSourceElement, sourceElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleElement getModuleElement() {
		if (moduleElement != null && moduleElement.eIsProxy()) {
			InternalEObject oldModuleElement = (InternalEObject)moduleElement;
			moduleElement = (ModuleElement)eResolveProxy(oldModuleElement);
			if (moduleElement != oldModuleElement) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TraceabilityPackage.GENERATED_TEXT__MODULE_ELEMENT, oldModuleElement, moduleElement));
			}
		}
		return moduleElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleElement basicGetModuleElement() {
		return moduleElement;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setModuleElement(ModuleElement newModuleElement) {
		ModuleElement oldModuleElement = moduleElement;
		moduleElement = newModuleElement;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.GENERATED_TEXT__MODULE_ELEMENT, oldModuleElement, moduleElement));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public GeneratedFile getOutputFile() {
		if (eContainerFeatureID() != TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE) return null;
		return (GeneratedFile)eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutputFile(GeneratedFile newOutputFile, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOutputFile, TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputFile(GeneratedFile newOutputFile) {
		if (newOutputFile != eInternalContainer() || (eContainerFeatureID() != TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE && newOutputFile != null)) {
			if (EcoreUtil.isAncestor(this, newOutputFile))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOutputFile != null)
				msgs = ((InternalEObject)newOutputFile).eInverseAdd(this, TraceabilityPackage.GENERATED_FILE__GENERATED_REGIONS, GeneratedFile.class, msgs);
			msgs = basicSetOutputFile(newOutputFile, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE, newOutputFile, newOutputFile));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getStartOffset() {
		return startOffset;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartOffset(int newStartOffset) {
		int oldStartOffset = startOffset;
		startOffset = newStartOffset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.GENERATED_TEXT__START_OFFSET, oldStartOffset, startOffset));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getEndOffset() {
		return endOffset;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndOffset(int newEndOffset) {
		int oldEndOffset = endOffset;
		endOffset = newEndOffset;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.GENERATED_TEXT__END_OFFSET, oldEndOffset, endOffset));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int compareTo(GeneratedText other) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOutputFile((GeneratedFile)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE:
				return basicSetOutputFile(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE:
				return eInternalContainer().eInverseRemove(this, TraceabilityPackage.GENERATED_FILE__GENERATED_REGIONS, GeneratedFile.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TraceabilityPackage.GENERATED_TEXT__SOURCE_ELEMENT:
				if (resolve) return getSourceElement();
				return basicGetSourceElement();
			case TraceabilityPackage.GENERATED_TEXT__MODULE_ELEMENT:
				if (resolve) return getModuleElement();
				return basicGetModuleElement();
			case TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE:
				return getOutputFile();
			case TraceabilityPackage.GENERATED_TEXT__START_OFFSET:
				return getStartOffset();
			case TraceabilityPackage.GENERATED_TEXT__END_OFFSET:
				return getEndOffset();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TraceabilityPackage.GENERATED_TEXT__SOURCE_ELEMENT:
				setSourceElement((InputElement)newValue);
				return;
			case TraceabilityPackage.GENERATED_TEXT__MODULE_ELEMENT:
				setModuleElement((ModuleElement)newValue);
				return;
			case TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE:
				setOutputFile((GeneratedFile)newValue);
				return;
			case TraceabilityPackage.GENERATED_TEXT__START_OFFSET:
				setStartOffset((Integer)newValue);
				return;
			case TraceabilityPackage.GENERATED_TEXT__END_OFFSET:
				setEndOffset((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case TraceabilityPackage.GENERATED_TEXT__SOURCE_ELEMENT:
				setSourceElement((InputElement)null);
				return;
			case TraceabilityPackage.GENERATED_TEXT__MODULE_ELEMENT:
				setModuleElement((ModuleElement)null);
				return;
			case TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE:
				setOutputFile((GeneratedFile)null);
				return;
			case TraceabilityPackage.GENERATED_TEXT__START_OFFSET:
				setStartOffset(START_OFFSET_EDEFAULT);
				return;
			case TraceabilityPackage.GENERATED_TEXT__END_OFFSET:
				setEndOffset(END_OFFSET_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case TraceabilityPackage.GENERATED_TEXT__SOURCE_ELEMENT:
				return sourceElement != null;
			case TraceabilityPackage.GENERATED_TEXT__MODULE_ELEMENT:
				return moduleElement != null;
			case TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE:
				return getOutputFile() != null;
			case TraceabilityPackage.GENERATED_TEXT__START_OFFSET:
				return startOffset != START_OFFSET_EDEFAULT;
			case TraceabilityPackage.GENERATED_TEXT__END_OFFSET:
				return endOffset != END_OFFSET_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (startOffset: "); //$NON-NLS-1$
		result.append(startOffset);
		result.append(", endOffset: "); //$NON-NLS-1$
		result.append(endOffset);
		result.append(')');
		return result.toString();
	}

} // GeneratedTextImpl
