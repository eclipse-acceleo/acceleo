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
package org.eclipse.acceleo.compatibility.model.mt.statements.impl;

import org.eclipse.acceleo.compatibility.model.mt.statements.Comment;
import org.eclipse.acceleo.compatibility.model.mt.statements.Feature;
import org.eclipse.acceleo.compatibility.model.mt.statements.For;
import org.eclipse.acceleo.compatibility.model.mt.statements.If;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsFactory;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage;
import org.eclipse.acceleo.compatibility.model.mt.statements.Text;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class StatementsFactoryImpl extends EFactoryImpl implements StatementsFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static StatementsFactory init() {
		try {
			StatementsFactory theStatementsFactory = (StatementsFactory)EPackage.Registry.INSTANCE
					.getEFactory("http://www.eclipse.org/acceleo/mt/2.6.0/statements"); //$NON-NLS-1$
			if (theStatementsFactory != null) {
				return theStatementsFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new StatementsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StatementsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case StatementsPackage.COMMENT:
				return createComment();
			case StatementsPackage.IF:
				return createIf();
			case StatementsPackage.FOR:
				return createFor();
			case StatementsPackage.FEATURE:
				return createFeature();
			case StatementsPackage.TEXT:
				return createText();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() //$NON-NLS-1$
						+ "' is not a valid classifier"); //$NON-NLS-1$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Comment createComment() {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public If createIf() {
		IfImpl if_ = new IfImpl();
		return if_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public For createFor() {
		ForImpl for_ = new ForImpl();
		return for_;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Feature createFeature() {
		FeatureImpl feature = new FeatureImpl();
		return feature;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Text createText() {
		TextImpl text = new TextImpl();
		return text;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public StatementsPackage getStatementsPackage() {
		return (StatementsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static StatementsPackage getPackage() {
		return StatementsPackage.eINSTANCE;
	}

} // StatementsFactoryImpl
