/**
 * Copyright (c) 2008, 2024 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.NamedElement;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
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
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Error Module</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getDocumentation <em>Documentation</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#isDeprecated <em>Deprecated</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getMetamodels <em>Metamodels</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getImports <em>Imports</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getModuleElements <em>Module Elements</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getStartHeaderPosition <em>Start Header
 * Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getEndHeaderPosition <em>End Header Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getAst <em>Ast</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getEncoding <em>Encoding</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getMissingOpenParenthesis <em>Missing Open
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getMissingEPackage <em>Missing EPackage</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getMissingCloseParenthesis <em>Missing Close
 * Parenthesis</em>}</li>
 * <li>{@link org.eclipse.acceleo.impl.ErrorModuleImpl#getMissingEndHeader <em>Missing End Header</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ErrorModuleImpl extends MinimalEObjectImpl.Container implements ErrorModule {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected Documentation documentation;

	/**
	 * The default value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPRECATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeprecated() <em>Deprecated</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #isDeprecated()
	 * @generated
	 * @ordered
	 */
	protected boolean deprecated = DEPRECATED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMetamodels() <em>Metamodels</em>}' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMetamodels()
	 * @generated
	 * @ordered
	 */
	protected EList<Metamodel> metamodels;

	/**
	 * The cached value of the '{@link #getExtends() <em>Extends</em>}' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getExtends()
	 * @generated
	 * @ordered
	 */
	protected ModuleReference extends_;

	/**
	 * The cached value of the '{@link #getImports() <em>Imports</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getImports()
	 * @generated
	 * @ordered
	 */
	protected EList<Import> imports;

	/**
	 * The cached value of the '{@link #getModuleElements() <em>Module Elements</em>}' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getModuleElements()
	 * @generated
	 * @ordered
	 */
	protected EList<ModuleElement> moduleElements;

	/**
	 * The default value of the '{@link #getStartHeaderPosition() <em>Start Header Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_HEADER_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartHeaderPosition() <em>Start Header Position</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getStartHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected int startHeaderPosition = START_HEADER_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndHeaderPosition() <em>End Header Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int END_HEADER_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndHeaderPosition() <em>End Header Position</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getEndHeaderPosition()
	 * @generated
	 * @ordered
	 */
	protected int endHeaderPosition = END_HEADER_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getAst() <em>Ast</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAst()
	 * @generated
	 * @ordered
	 */
	protected static final AcceleoAstResult AST_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAst() <em>Ast</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getAst()
	 * @generated
	 * @ordered
	 */
	protected AcceleoAstResult ast = AST_EDEFAULT;

	/**
	 * The default value of the '{@link #getEncoding() <em>Encoding</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getEncoding()
	 * @generated
	 * @ordered
	 */
	protected static final String ENCODING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEncoding() <em>Encoding</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getEncoding()
	 * @generated
	 * @ordered
	 */
	protected String encoding = ENCODING_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingOpenParenthesis() <em>Missing Open Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_OPEN_PARENTHESIS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingOpenParenthesis() <em>Missing Open Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingOpenParenthesis()
	 * @generated
	 * @ordered
	 */
	protected int missingOpenParenthesis = MISSING_OPEN_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingEPackage() <em>Missing EPackage</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEPackage()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_EPACKAGE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingEPackage() <em>Missing EPackage</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEPackage()
	 * @generated
	 * @ordered
	 */
	protected int missingEPackage = MISSING_EPACKAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingCloseParenthesis() <em>Missing Close Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_CLOSE_PARENTHESIS_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingCloseParenthesis() <em>Missing Close Parenthesis</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingCloseParenthesis()
	 * @generated
	 * @ordered
	 */
	protected int missingCloseParenthesis = MISSING_CLOSE_PARENTHESIS_EDEFAULT;

	/**
	 * The default value of the '{@link #getMissingEndHeader() <em>Missing End Header</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected static final int MISSING_END_HEADER_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getMissingEndHeader() <em>Missing End Header</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getMissingEndHeader()
	 * @generated
	 * @ordered
	 */
	protected int missingEndHeader = MISSING_END_HEADER_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ErrorModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AcceleoPackage.Literals.ERROR_MODULE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MODULE__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Documentation getDocumentation() {
		if (documentation != null && documentation.eIsProxy()) {
			InternalEObject oldDocumentation = (InternalEObject)documentation;
			documentation = (Documentation)eResolveProxy(oldDocumentation);
			if (documentation != oldDocumentation) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							AcceleoPackage.ERROR_MODULE__DOCUMENTATION, oldDocumentation, documentation));
			}
		}
		return documentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Documentation basicGetDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isDeprecated() {
		return deprecated;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDeprecated(boolean newDeprecated) {
		boolean oldDeprecated = deprecated;
		deprecated = newDeprecated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MODULE__DEPRECATED,
					oldDeprecated, deprecated));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Metamodel> getMetamodels() {
		if (metamodels == null) {
			metamodels = new EObjectResolvingEList<Metamodel>(Metamodel.class, this,
					AcceleoPackage.ERROR_MODULE__METAMODELS);
		}
		return metamodels;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ModuleReference getExtends() {
		return extends_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetExtends(ModuleReference newExtends, NotificationChain msgs) {
		ModuleReference oldExtends = extends_;
		extends_ = newExtends;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__EXTENDS, oldExtends, newExtends);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setExtends(ModuleReference newExtends) {
		if (newExtends != extends_) {
			NotificationChain msgs = null;
			if (extends_ != null)
				msgs = ((InternalEObject)extends_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_MODULE__EXTENDS, null, msgs);
			if (newExtends != null)
				msgs = ((InternalEObject)newExtends).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- AcceleoPackage.ERROR_MODULE__EXTENDS, null, msgs);
			msgs = basicSetExtends(newExtends, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MODULE__EXTENDS,
					newExtends, newExtends));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Import> getImports() {
		if (imports == null) {
			imports = new EObjectContainmentEList<Import>(Import.class, this,
					AcceleoPackage.ERROR_MODULE__IMPORTS);
		}
		return imports;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<ModuleElement> getModuleElements() {
		if (moduleElements == null) {
			moduleElements = new EObjectContainmentEList<ModuleElement>(ModuleElement.class, this,
					AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS);
		}
		return moduleElements;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getStartHeaderPosition() {
		return startHeaderPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setStartHeaderPosition(int newStartHeaderPosition) {
		int oldStartHeaderPosition = startHeaderPosition;
		startHeaderPosition = newStartHeaderPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__START_HEADER_POSITION, oldStartHeaderPosition,
					startHeaderPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getEndHeaderPosition() {
		return endHeaderPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setEndHeaderPosition(int newEndHeaderPosition) {
		int oldEndHeaderPosition = endHeaderPosition;
		endHeaderPosition = newEndHeaderPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__END_HEADER_POSITION, oldEndHeaderPosition,
					endHeaderPosition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AcceleoAstResult getAst() {
		return ast;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAst(AcceleoAstResult newAst) {
		AcceleoAstResult oldAst = ast;
		ast = newAst;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MODULE__AST, oldAst,
					ast));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getEncoding() {
		return encoding;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setEncoding(String newEncoding) {
		String oldEncoding = encoding;
		encoding = newEncoding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AcceleoPackage.ERROR_MODULE__ENCODING,
					oldEncoding, encoding));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingOpenParenthesis() {
		return missingOpenParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingOpenParenthesis(int newMissingOpenParenthesis) {
		int oldMissingOpenParenthesis = missingOpenParenthesis;
		missingOpenParenthesis = newMissingOpenParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__MISSING_OPEN_PARENTHESIS, oldMissingOpenParenthesis,
					missingOpenParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingEPackage() {
		return missingEPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEPackage(int newMissingEPackage) {
		int oldMissingEPackage = missingEPackage;
		missingEPackage = newMissingEPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__MISSING_EPACKAGE, oldMissingEPackage, missingEPackage));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingCloseParenthesis() {
		return missingCloseParenthesis;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingCloseParenthesis(int newMissingCloseParenthesis) {
		int oldMissingCloseParenthesis = missingCloseParenthesis;
		missingCloseParenthesis = newMissingCloseParenthesis;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__MISSING_CLOSE_PARENTHESIS, oldMissingCloseParenthesis,
					missingCloseParenthesis));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getMissingEndHeader() {
		return missingEndHeader;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setMissingEndHeader(int newMissingEndHeader) {
		int oldMissingEndHeader = missingEndHeader;
		missingEndHeader = newMissingEndHeader;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					AcceleoPackage.ERROR_MODULE__MISSING_END_HEADER, oldMissingEndHeader, missingEndHeader));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
				if (documentation != null)
					msgs = ((InternalEObject)documentation).eInverseRemove(this,
							AcceleoPackage.DOCUMENTATION__DOCUMENTED_ELEMENT, Documentation.class, msgs);
				return basicSetDocumentation((Documentation)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
				return basicSetDocumentation(null, msgs);
			case AcceleoPackage.ERROR_MODULE__EXTENDS:
				return basicSetExtends(null, msgs);
			case AcceleoPackage.ERROR_MODULE__IMPORTS:
				return ((InternalEList<?>)getImports()).basicRemove(otherEnd, msgs);
			case AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS:
				return ((InternalEList<?>)getModuleElements()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AcceleoPackage.ERROR_MODULE__NAME:
				return getName();
			case AcceleoPackage.ERROR_MODULE__DOCUMENTATION:
				if (resolve)
					return getDocumentation();
				return basicGetDocumentation();
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
			case AcceleoPackage.ERROR_MODULE__AST:
				return getAst();
			case AcceleoPackage.ERROR_MODULE__ENCODING:
				return getEncoding();
			case AcceleoPackage.ERROR_MODULE__MISSING_OPEN_PARENTHESIS:
				return getMissingOpenParenthesis();
			case AcceleoPackage.ERROR_MODULE__MISSING_EPACKAGE:
				return getMissingEPackage();
			case AcceleoPackage.ERROR_MODULE__MISSING_CLOSE_PARENTHESIS:
				return getMissingCloseParenthesis();
			case AcceleoPackage.ERROR_MODULE__MISSING_END_HEADER:
				return getMissingEndHeader();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
				setExtends((ModuleReference)newValue);
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
			case AcceleoPackage.ERROR_MODULE__AST:
				setAst((AcceleoAstResult)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__ENCODING:
				setEncoding((String)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_OPEN_PARENTHESIS:
				setMissingOpenParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_EPACKAGE:
				setMissingEPackage((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_CLOSE_PARENTHESIS:
				setMissingCloseParenthesis((Integer)newValue);
				return;
			case AcceleoPackage.ERROR_MODULE__MISSING_END_HEADER:
				setMissingEndHeader((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
				setExtends((ModuleReference)null);
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
			case AcceleoPackage.ERROR_MODULE__AST:
				setAst(AST_EDEFAULT);
				return;
			case AcceleoPackage.ERROR_MODULE__ENCODING:
				setEncoding(ENCODING_EDEFAULT);
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
				return extends_ != null;
			case AcceleoPackage.ERROR_MODULE__IMPORTS:
				return imports != null && !imports.isEmpty();
			case AcceleoPackage.ERROR_MODULE__MODULE_ELEMENTS:
				return moduleElements != null && !moduleElements.isEmpty();
			case AcceleoPackage.ERROR_MODULE__START_HEADER_POSITION:
				return startHeaderPosition != START_HEADER_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE__END_HEADER_POSITION:
				return endHeaderPosition != END_HEADER_POSITION_EDEFAULT;
			case AcceleoPackage.ERROR_MODULE__AST:
				return AST_EDEFAULT == null ? ast != null : !AST_EDEFAULT.equals(ast);
			case AcceleoPackage.ERROR_MODULE__ENCODING:
				return ENCODING_EDEFAULT == null ? encoding != null : !ENCODING_EDEFAULT.equals(encoding);
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
		if (baseClass == org.eclipse.acceleo.Module.class) {
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
				case AcceleoPackage.ERROR_MODULE__AST:
					return AcceleoPackage.MODULE__AST;
				case AcceleoPackage.ERROR_MODULE__ENCODING:
					return AcceleoPackage.MODULE__ENCODING;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
		if (baseClass == org.eclipse.acceleo.Module.class) {
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
				case AcceleoPackage.MODULE__AST:
					return AcceleoPackage.ERROR_MODULE__AST;
				case AcceleoPackage.MODULE__ENCODING:
					return AcceleoPackage.ERROR_MODULE__ENCODING;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", deprecated: "); //$NON-NLS-1$
		result.append(deprecated);
		result.append(", startHeaderPosition: "); //$NON-NLS-1$
		result.append(startHeaderPosition);
		result.append(", endHeaderPosition: "); //$NON-NLS-1$
		result.append(endHeaderPosition);
		result.append(", ast: "); //$NON-NLS-1$
		result.append(ast);
		result.append(", encoding: "); //$NON-NLS-1$
		result.append(encoding);
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

} // ErrorModuleImpl
