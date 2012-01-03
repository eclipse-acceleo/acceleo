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

import java.util.Collection;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Generated File</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedFileImpl#getGeneratedRegions <em>Generated Regions</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedFileImpl#getSourceElements <em>Source Elements</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedFileImpl#getNameRegions <em>Name Regions</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedFileImpl#getFileBlock <em>File Block</em>}</li>
 *   <li>{@link org.eclipse.acceleo.traceability.impl.GeneratedFileImpl#getLength <em>Length</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GeneratedFileImpl extends ResourceImpl implements GeneratedFile {
	/**
	 * The cached value of the '{@link #getGeneratedRegions() <em>Generated Regions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getGeneratedRegions()
	 * @generated
	 * @ordered
	 */
	protected EList<GeneratedText> generatedRegions;

	/**
	 * The cached value of the '{@link #getSourceElements() <em>Source Elements</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSourceElements()
	 * @generated
	 * @ordered
	 */
	protected EList<InputElement> sourceElements;

	/**
	 * The cached value of the '{@link #getNameRegions() <em>Name Regions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNameRegions()
	 * @generated
	 * @ordered
	 */
	protected EList<GeneratedText> nameRegions;

	/**
	 * The cached value of the '{@link #getFileBlock() <em>File Block</em>}' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getFileBlock()
	 * @generated
	 * @ordered
	 */
	protected ModuleElement fileBlock;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected GeneratedFileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TraceabilityPackage.Literals.GENERATED_FILE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GeneratedText> getGeneratedRegions() {
		if (generatedRegions == null) {
			generatedRegions = new EObjectContainmentWithInverseEList<GeneratedText>(GeneratedText.class, this, TraceabilityPackage.GENERATED_FILE__GENERATED_REGIONS, TraceabilityPackage.GENERATED_TEXT__OUTPUT_FILE);
		}
		return generatedRegions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InputElement> getSourceElements() {
		if (sourceElements == null) {
			sourceElements = new EObjectResolvingEList<InputElement>(InputElement.class, this, TraceabilityPackage.GENERATED_FILE__SOURCE_ELEMENTS);
		}
		return sourceElements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GeneratedText> getNameRegions() {
		if (nameRegions == null) {
			nameRegions = new EObjectContainmentEList<GeneratedText>(GeneratedText.class, this, TraceabilityPackage.GENERATED_FILE__NAME_REGIONS);
		}
		return nameRegions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleElement getFileBlock() {
		if (fileBlock != null && fileBlock.eIsProxy()) {
			InternalEObject oldFileBlock = (InternalEObject)fileBlock;
			fileBlock = (ModuleElement)eResolveProxy(oldFileBlock);
			if (fileBlock != oldFileBlock) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TraceabilityPackage.GENERATED_FILE__FILE_BLOCK, oldFileBlock, fileBlock));
			}
		}
		return fileBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ModuleElement basicGetFileBlock() {
		return fileBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileBlock(ModuleElement newFileBlock) {
		ModuleElement oldFileBlock = fileBlock;
		fileBlock = newFileBlock;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.GENERATED_FILE__FILE_BLOCK, oldFileBlock, fileBlock));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setLength(int newLength) {
		int oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TraceabilityPackage.GENERATED_FILE__LENGTH, oldLength, length));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TraceabilityPackage.GENERATED_FILE__GENERATED_REGIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getGeneratedRegions()).basicAdd(otherEnd, msgs);
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
			case TraceabilityPackage.GENERATED_FILE__GENERATED_REGIONS:
				return ((InternalEList<?>)getGeneratedRegions()).basicRemove(otherEnd, msgs);
			case TraceabilityPackage.GENERATED_FILE__NAME_REGIONS:
				return ((InternalEList<?>)getNameRegions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TraceabilityPackage.GENERATED_FILE__GENERATED_REGIONS:
				return getGeneratedRegions();
			case TraceabilityPackage.GENERATED_FILE__SOURCE_ELEMENTS:
				return getSourceElements();
			case TraceabilityPackage.GENERATED_FILE__NAME_REGIONS:
				return getNameRegions();
			case TraceabilityPackage.GENERATED_FILE__FILE_BLOCK:
				if (resolve) return getFileBlock();
				return basicGetFileBlock();
			case TraceabilityPackage.GENERATED_FILE__LENGTH:
				return getLength();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case TraceabilityPackage.GENERATED_FILE__GENERATED_REGIONS:
				getGeneratedRegions().clear();
				getGeneratedRegions().addAll((Collection<? extends GeneratedText>)newValue);
				return;
			case TraceabilityPackage.GENERATED_FILE__SOURCE_ELEMENTS:
				getSourceElements().clear();
				getSourceElements().addAll((Collection<? extends InputElement>)newValue);
				return;
			case TraceabilityPackage.GENERATED_FILE__NAME_REGIONS:
				getNameRegions().clear();
				getNameRegions().addAll((Collection<? extends GeneratedText>)newValue);
				return;
			case TraceabilityPackage.GENERATED_FILE__FILE_BLOCK:
				setFileBlock((ModuleElement)newValue);
				return;
			case TraceabilityPackage.GENERATED_FILE__LENGTH:
				setLength((Integer)newValue);
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
			case TraceabilityPackage.GENERATED_FILE__GENERATED_REGIONS:
				getGeneratedRegions().clear();
				return;
			case TraceabilityPackage.GENERATED_FILE__SOURCE_ELEMENTS:
				getSourceElements().clear();
				return;
			case TraceabilityPackage.GENERATED_FILE__NAME_REGIONS:
				getNameRegions().clear();
				return;
			case TraceabilityPackage.GENERATED_FILE__FILE_BLOCK:
				setFileBlock((ModuleElement)null);
				return;
			case TraceabilityPackage.GENERATED_FILE__LENGTH:
				setLength(LENGTH_EDEFAULT);
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
			case TraceabilityPackage.GENERATED_FILE__GENERATED_REGIONS:
				return generatedRegions != null && !generatedRegions.isEmpty();
			case TraceabilityPackage.GENERATED_FILE__SOURCE_ELEMENTS:
				return sourceElements != null && !sourceElements.isEmpty();
			case TraceabilityPackage.GENERATED_FILE__NAME_REGIONS:
				return nameRegions != null && !nameRegions.isEmpty();
			case TraceabilityPackage.GENERATED_FILE__FILE_BLOCK:
				return fileBlock != null;
			case TraceabilityPackage.GENERATED_FILE__LENGTH:
				return length != LENGTH_EDEFAULT;
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
		result.append(" (length: "); //$NON-NLS-1$
		result.append(length);
		result.append(')');
		return result.toString();
	}

} // GeneratedFileImpl
