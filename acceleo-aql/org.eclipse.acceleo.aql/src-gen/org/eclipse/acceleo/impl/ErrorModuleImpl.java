/**
 * Copyright (c) 2008, 2016 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.impl;

import java.util.Collection;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.DocumentedElement;
import org.eclipse.acceleo.ErrorModule;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.NamedElement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Error Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#isDeprecated <em>Deprecated</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getMetamodels <em>Metamodels</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getExtends <em>Extends</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getModuleElements <em>Module Elements</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getStartHeaderPosition <em>Start Header Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getEndHeaderPosition <em>End Header Position</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#isMissingOpenParenthesis <em>Missing Open Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#isMissingEPackage <em>Missing EPackage</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#isMissingCloseParenthesis <em>Missing Close Parenthesis</em>}</li>
 *   <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#isMissingEndHeader <em>Missing End Header</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ErrorModuleImpl extends MinimalEObjectImpl.Container implements ErrorModule {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected Documentation documentation;

	/**
	 * The default value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPRECATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected boolean deprecated = DEPRECATED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMetamodels() <em>Metamodels</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodels()
	 * @generated
	 * @ordered
	 */
	protected EList<Metamodel> metamodels;

	/**
	 * The cached value of the '{@link #getExtends() <em>Extends</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtends()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleReference> extends_;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<Import> imports;

	/**
	 * The cached value of the '{@link #getModuleElements() <em>Module Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModuleElements()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleElement> moduleElements;

	/**
	 * The default value of the '{@link #getStartHeaderPosition() <em>Start Header Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_HEADER_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartHeaderPosition() <em>Start Header Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected int startHeaderPosition = START_HEADER_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndHeaderPosition() <em>End Header Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int END_HEADER_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndHeaderPosition() <em>End Header Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected int endHeaderPosition = END_HEADER_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingOpenParenthesis() <em>Missing Open Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_OPEN_PARENTHESIS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingOpenParenthesis() <em>Missing Open Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected boolean missingOpenParenthesis = MISSING_OPEN_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingEPackage() <em>Missing EPackage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEPackage()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_EPACKAGE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingEPackage() <em>Missing EPackage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEPackage()
	 * @generated
	 * @ordered
	 */
	protected boolean missingEPackage = MISSING_EPACKAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingCloseParenthesis() <em>Missing Close Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_CLOSE_PARENTHESIS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingCloseParenthesis() <em>Missing Close Parenthesis</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected boolean missingCloseParenthesis = MISSING_CLOSE_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #isMissingEndHeader() <em>Missing End Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MISSING_END_HEADER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMissingEndHeader() <em>Missing End Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected boolean missingEndHeader = MISSING_END_HEADER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ErrorModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_MODULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MODULE__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Documentation getDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDocumentation(Documentation newDocumentation, NotificationChain msgs) {
		Documentation oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__DOCUMENTATION, oldDocumentation, newDocumentation);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentation(Documentation newDocumentation) {
		if (newDocumentation != documentation) {
			NotificationChain msgs = null;
			if (documentation != null)
				msgs = ((InternalEObject)documentation).eInverseRemove(this,
						AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
			if (newDocumentation != null)
				msgs = ((InternalEObject)newDocumentation).eInverseAdd(this,
						AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
			msgs = basicSetDocumentation(newDocumentation, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MODULE__DOCUMENTATION,
					newDocumentation, newDocumentation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeprecated() {
		return deprecated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeprecated(boolean newDeprecated) {
		boolean oldDeprecated = deprecated;
		deprecated = newDeprecated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MODULE__DEPRECATED,
					oldDeprecated, deprecated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Metamodel> getMetamodels() {
		if (metamodels == null) {
			metamodels = new EObjectResolvingEList<Metamodel>(Metamodel.class, this,
					AcceleoPackage.ERROR_MODULE__METAMODELS);
		}
		return metamodels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModuleReference> getExtends() {
		if (extends_ == null) {
			extends_ = new EObjectContainmentEList<ModuleReference>(ModuleReference.class, this,
					AcceleoPackage.ERROR_MODULE__EXTENDS);
		}
		return extends_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Import> getImports() {
		if (imports == null) {
			imports = new EObjectContainmentEList<Import>(Import.class, this,
					AcceleoPackage.ERROR_MODULE__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModuleElement> getModuleElements() {
		if (moduleElements == null) {
			moduleElements = new EObjectContainmentEList<ModuleElement>(ModuleElement.class, this,
					AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS);
		}
		return moduleElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStartHeaderPosition() {
		return startHeaderPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartHeaderPosition(int newStartHeaderPosition) {
		int oldStartHeaderPosition = startHeaderPosition;
		startHeaderPosition = newStartHeaderPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__START_HEADER_POSITION, oldStartHeaderPosition,
					startHeaderPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getEndHeaderPosition() {
		return endHeaderPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndHeaderPosition(int newEndHeaderPosition) {
		int oldEndHeaderPosition = endHeaderPosition;
		endHeaderPosition = newEndHeaderPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__END_HEADER_POSITION, oldEndHeaderPosition, endHeaderPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingOpenParenthesis() {
		return missingOpenParenthesis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingOpenParenthesis(boolean newMissingOpenParenthesis) {
		boolean oldMissingOpenParenthesis = missingOpenParenthesis;
		missingOpenParenthesis = newMissingOpenParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__MISSING_OPEN_PARENTHESIS, oldMissingOpenParenthesis,
					missingOpenParenthesis));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingEPackage() {
		return missingEPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingEPackage(boolean newMissingEPackage) {
		boolean oldMissingEPackage = missingEPackage;
		missingEPackage = newMissingEPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__MISSING_EPACKAGE, oldMissingEPackage, missingEPackage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingCloseParenthesis() {
		return missingCloseParenthesis;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingCloseParenthesis(boolean newMissingCloseParenthesis) {
		boolean oldMissingCloseParenthesis = missingCloseParenthesis;
		missingCloseParenthesis = newMissingCloseParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__MISSING_CLOSE_PARENTHESIS, oldMissingCloseParenthesis,
					missingCloseParenthesis));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMissingEndHeader() {
		return missingEndHeader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMissingEndHeader(boolean newMissingEndHeader) {
		boolean oldMissingEndHeader = missingEndHeader;
		missingEndHeader = newMissingEndHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__MISSING_END_HEADER, oldMissingEndHeader, missingEndHeader));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
				if (documentation != null)
					msgs = ((InternalEObject)documentation).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
							- AcceleoPackage.ERROR_MODULE__DOCUMENTATION, null, msgs);
				return basicSetDocumentation((Documentation)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case AcceleoPackage.ERROR_MODULE__EXTENDS:
				return ((InternalEList<?>)getExtends()).basicRemove(otherEnd, msgs);
			case AcceleoPackage.ERROR_MODULE__IMPORTS:
				return ((InternalEList<?>)getImports()).basicRemove(otherEnd, msgs);
			case AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS:
				return ((InternalEList<?>)getModuleElements()).basicRemove(otherEnd, msgs);
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
			case AcceleoPackage.ERROR_MODULE__NAME:
				return getName();
			case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
				return getDocumentation();
			case AcceleoPackage.ERROR_MODULE__DEPRECATED:
				return isDeprecated();
			case AcceleoPackage.ERROR_MODULE__METAMODELS:
				return getMetamodels();
			case AcceleoPackage.ERROR_MODULE__EXTENDS:
				return getExtends();
			case AcceleoPackage.ERROR_MODULE__IMPORTS:
				return getImports();
			case AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS:
				return getModuleElements();
			case AcceleoPackage.ERROR_MODULE__START_HEADER_POSITION:
				return getStartHeaderPosition();
			case AcceleoPackage.ERROR_MODULE__END_HEADER_POSITION:
				return getEndHeaderPosition();
			case AcceleoPackage.ERROR_MODULE__MISSING_OPEN_PARENTHESIS:
				return isMissingOpenParenthesis();
			case AcceleoPackage.ERROR_MODULE__MISSING_EPACKAGE:
				return isMissingEPackage();
			case AcceleoPackage.ERROR_MODULE__MISSING_CLOSE_PARENTHESIS:
				return isMissingCloseParenthesis();
			case AcceleoPackage.ERROR_MODULE__MISSING_END_HEADER:
				return isMissingEndHeader();
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
			case AcceleoPackage.ERROR_MODULE__NAME:
				setName((String)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
				setDocumentation((Documentation)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__DEPRECATED:
				setDeprecated((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__METAMODELS:
				getMetamodels().clear();
				getMetamodels().addAll((Collection<? extends Metamodel>)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__EXTENDS:
				getExtends().clear();
				getExtends().addAll((Collection<? extends ModuleReference>)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__IMPORTS:
				getImports().clear();
				getImports().addAll((Collection<? extends Import>)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS:
				getModuleElements().clear();
				getModuleElements().addAll((Collection<? extends ModuleElement>)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__START_HEADER_POSITION:
				setStartHeaderPosition((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__END_HEADER_POSITION:
				setEndHeaderPosition((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_EPACKAGE:
				setMissingEPackage((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis((Boolean)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_END_HEADER:
				setMissingEndHeader((Boolean)newValue);
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
			case AcceleoPackage.ERROR_MODULE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
				setDocumentation((Documentation)null);
				return;
			case AcceleoPackage.ERROR_MODULE__DEPRECATED:
				setDeprecated(DEPRECATED_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE__METAMODELS:
				getMetamodels().clear();
				return;
			case AcceleoPackage.ERROR_MODULE__EXTENDS:
				getExtends().clear();
				return;
			case AcceleoPackage.ERROR_MODULE__IMPORTS:
				getImports().clear();
				return;
			case AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS:
				getModuleElements().clear();
				return;
			case AcceleoPackage.ERROR_MODULE__START_HEADER_POSITION:
				setStartHeaderPosition(START_HEADER_POSITION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE__END_HEADER_POSITION:
				setEndHeaderPosition(END_HEADER_POSITION_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis(MISSING_OPEN_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_EPACKAGE:
				setMissingEPackage(MISSING_EPACKAGE_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis(MISSING_CLOSE_PARENTHESIS_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_END_HEADER:
				setMissingEndHeader(MISSING_END_HEADER_EDEFAULT);
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
			case AcceleoPackage.ERROR_MODULE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
				return documentation != null;
			case AcceleoPackage.ERROR_MODULE__DEPRECATED:
				return deprecated != DEPRECATED_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE__METAMODELS:
				return metamodels != null && !metamodels.isEmpty();
			case AcceleoPackage.ERROR_MODULE__EXTENDS:
				return extends_ != null && !extends_.isEmpty();
			case AcceleoPackage.ERROR_MODULE__IMPORTS:
				return imports != null && !imports.isEmpty();
			case AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS:
				return moduleElements != null && !moduleElements.isEmpty();
			case AcceleoPackage.ERROR_MODULE__START_HEADER_POSITION:
				return startHeaderPosition != START_HEADER_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE__END_HEADER_POSITION:
				return endHeaderPosition != END_HEADER_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE__MISSING_OPEN_PARENTHESIS:
				return missingOpenParenthesis != MISSING_OPEN_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE__MISSING_EPACKAGE:
				return missingEPackage != MISSING_EPACKAGE_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE__MISSING_CLOSE_PARENTHESIS:
				return missingCloseParenthesis != MISSING_CLOSE_PARENTHESIS_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE__MISSING_END_HEADER:
				return missingEndHeader != MISSING_END_HEADER_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE__NAME:
					return AcceleoPackage.NAMED_ELEMENT__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == DocumentedElement.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION;
				case AcceleoPackage.ERROR_MODULE__DEPRECATED:
					return AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == Module.class) {
			switch (derivedFeatureID) {
				case AcceleoPackage.ERROR_MODULE__METAMODELS:
					return AcceleoPackage.MODULE__METAMODELS;
				case AcceleoPackage.ERROR_MODULE__EXTENDS:
					return AcceleoPackage.MODULE__EXTENDS;
				case AcceleoPackage.ERROR_MODULE__IMPORTS:
					return AcceleoPackage.MODULE__IMPORTS;
				case AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS:
					return AcceleoPackage.MODULE__MODULE_ELEMENTS;
				case AcceleoPackage.ERROR_MODULE__START_HEADER_POSITION:
					return AcceleoPackage.MODULE__START_HEADER_POSITION;
				case AcceleoPackage.ERROR_MODULE__END_HEADER_POSITION:
					return AcceleoPackage.MODULE__END_HEADER_POSITION;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == NamedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.NAMED_ELEMENT__NAME:
					return AcceleoPackage.ERROR_MODULE__NAME;
				default:
					return -1;
			}
		}
		if (baseClass == DocumentedElement.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.DOCUMENTED_ELEMENT__DOCUMENTATION:
					return AcceleoPackage.ERROR_MODULE__DOCUMENTATION;
				case AcceleoPackage.DOCUMENTED_ELEMENT__DEPRECATED:
					return AcceleoPackage.ERROR_MODULE__DEPRECATED;
				default:
					return -1;
			}
		}
		if (baseClass == Module.class) {
			switch (baseFeatureID) {
				case AcceleoPackage.MODULE__METAMODELS:
					return AcceleoPackage.ERROR_MODULE__METAMODELS;
				case AcceleoPackage.MODULE__EXTENDS:
					return AcceleoPackage.ERROR_MODULE__EXTENDS;
				case AcceleoPackage.MODULE__IMPORTS:
					return AcceleoPackage.ERROR_MODULE__IMPORTS;
				case AcceleoPackage.MODULE__MODULE_ELEMENTS:
					return AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS;
				case AcceleoPackage.MODULE__START_HEADER_POSITION:
					return AcceleoPackage.ERROR_MODULE__START_HEADER_POSITION;
				case AcceleoPackage.MODULE__END_HEADER_POSITION:
					return AcceleoPackage.ERROR_MODULE__END_HEADER_POSITION;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", deprecated: "); //$NON-NLS-1$
		result.append(deprecated);
		result.append(", startHeaderPosition: "); //$NON-NLS-1$
		result.append(startHeaderPosition);
		result.append(", endHeaderPosition: "); //$NON-NLS-1$
		result.append(endHeaderPosition);
		result.append(", missingOpenParenthesis: "); //$NON-NLS-1$
		result.append(missingOpenParenthesis);
		result.append(", missingEPackage: "); //$NON-NLS-1$
		result.append(missingEPackage);
		result.append(", missingCloseParenthesis: "); //$NON-NLS-1$
		result.append(missingCloseParenthesis);
		result.append(", missingEndHeader: "); //$NON-NLS-1$
		result.append(missingEndHeader);
		result.append(')');
		return result.toString();
	}

} //ErrorModuleImpl
