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
package org.eclipse.acceleo.compatibility.model.mt.statements;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract
 * class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsPackage
 * @generated
 */
public interface StatementsFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	StatementsFactory eINSTANCE = org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsFactoryImpl
			.init();

	/**
	 * Returns a new object of class '<em>Comment</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
	Comment createComment();

	/**
	 * Returns a new object of class '<em>If</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>If</em>'.
	 * @generated
	 */
	If createIf();

	/**
	 * Returns a new object of class '<em>For</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>For</em>'.
	 * @generated
	 */
	For createFor();

	/**
	 * Returns a new object of class '<em>Feature</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Feature</em>'.
	 * @generated
	 */
	Feature createFeature();

	/**
	 * Returns a new object of class '<em>Text</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Text</em>'.
	 * @generated
	 */
	Text createText();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	StatementsPackage getStatementsPackage();

} // StatementsFactory
