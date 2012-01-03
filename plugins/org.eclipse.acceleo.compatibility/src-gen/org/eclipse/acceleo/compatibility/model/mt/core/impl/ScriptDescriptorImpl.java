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
package org.eclipse.acceleo.compatibility.model.mt.core.impl;

import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.acceleo.compatibility.model.mt.core.FilePath;
import org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Script Descriptor</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptDescriptorImpl#getName <em>Name</em>}
 * </li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptDescriptorImpl#getType <em>Type</em>}
 * </li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptDescriptorImpl#getDescription <em>
 * Description</em>}</li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptDescriptorImpl#getFile <em>File</em>}
 * </li>
 * <li>{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptDescriptorImpl#getPost <em>Post</em>}
 * </li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ScriptDescriptorImpl extends ASTNodeImpl implements ScriptDescriptor {
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
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFile() <em>File</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected FilePath file;

	/**
	 * The cached value of the '{@link #getPost() <em>Post</em>}' containment reference. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #getPost()
	 * @generated
	 * @ordered
	 */
	protected Expression post;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ScriptDescriptorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CorePackage.Literals.SCRIPT_DESCRIPTOR;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.SCRIPT_DESCRIPTOR__NAME,
					oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.SCRIPT_DESCRIPTOR__TYPE,
					oldType, type));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.SCRIPT_DESCRIPTOR__DESCRIPTION,
					oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FilePath getFile() {
		return file;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetFile(FilePath newFile, NotificationChain msgs) {
		FilePath oldFile = file;
		file = newFile;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CorePackage.SCRIPT_DESCRIPTOR__FILE, oldFile, newFile);
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
	public void setFile(FilePath newFile) {
		if (newFile != file) {
			NotificationChain msgs = null;
			if (file != null)
				msgs = ((InternalEObject)file).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CorePackage.SCRIPT_DESCRIPTOR__FILE, null, msgs);
			if (newFile != null)
				msgs = ((InternalEObject)newFile).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CorePackage.SCRIPT_DESCRIPTOR__FILE, null, msgs);
			msgs = basicSetFile(newFile, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.SCRIPT_DESCRIPTOR__FILE,
					newFile, newFile));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getPost() {
		return post;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetPost(Expression newPost, NotificationChain msgs) {
		Expression oldPost = post;
		post = newPost;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CorePackage.SCRIPT_DESCRIPTOR__POST, oldPost, newPost);
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
	public void setPost(Expression newPost) {
		if (newPost != post) {
			NotificationChain msgs = null;
			if (post != null)
				msgs = ((InternalEObject)post).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
						- CorePackage.SCRIPT_DESCRIPTOR__POST, null, msgs);
			if (newPost != null)
				msgs = ((InternalEObject)newPost).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- CorePackage.SCRIPT_DESCRIPTOR__POST, null, msgs);
			msgs = basicSetPost(newPost, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CorePackage.SCRIPT_DESCRIPTOR__POST,
					newPost, newPost));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CorePackage.SCRIPT_DESCRIPTOR__FILE:
				return basicSetFile(null, msgs);
			case CorePackage.SCRIPT_DESCRIPTOR__POST:
				return basicSetPost(null, msgs);
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
			case CorePackage.SCRIPT_DESCRIPTOR__NAME:
				return getName();
			case CorePackage.SCRIPT_DESCRIPTOR__TYPE:
				return getType();
			case CorePackage.SCRIPT_DESCRIPTOR__DESCRIPTION:
				return getDescription();
			case CorePackage.SCRIPT_DESCRIPTOR__FILE:
				return getFile();
			case CorePackage.SCRIPT_DESCRIPTOR__POST:
				return getPost();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CorePackage.SCRIPT_DESCRIPTOR__NAME:
				setName((String)newValue);
				return;
			case CorePackage.SCRIPT_DESCRIPTOR__TYPE:
				setType((String)newValue);
				return;
			case CorePackage.SCRIPT_DESCRIPTOR__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case CorePackage.SCRIPT_DESCRIPTOR__FILE:
				setFile((FilePath)newValue);
				return;
			case CorePackage.SCRIPT_DESCRIPTOR__POST:
				setPost((Expression)newValue);
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
			case CorePackage.SCRIPT_DESCRIPTOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CorePackage.SCRIPT_DESCRIPTOR__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case CorePackage.SCRIPT_DESCRIPTOR__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case CorePackage.SCRIPT_DESCRIPTOR__FILE:
				setFile((FilePath)null);
				return;
			case CorePackage.SCRIPT_DESCRIPTOR__POST:
				setPost((Expression)null);
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
			case CorePackage.SCRIPT_DESCRIPTOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CorePackage.SCRIPT_DESCRIPTOR__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case CorePackage.SCRIPT_DESCRIPTOR__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT
						.equals(description);
			case CorePackage.SCRIPT_DESCRIPTOR__FILE:
				return file != null;
			case CorePackage.SCRIPT_DESCRIPTOR__POST:
				return post != null;
		}
		return super.eIsSet(featureID);
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", type: "); //$NON-NLS-1$
		result.append(type);
		result.append(", description: "); //$NON-NLS-1$
		result.append(description);
		result.append(')');
		return result.toString();
	}

} // ScriptDescriptorImpl
