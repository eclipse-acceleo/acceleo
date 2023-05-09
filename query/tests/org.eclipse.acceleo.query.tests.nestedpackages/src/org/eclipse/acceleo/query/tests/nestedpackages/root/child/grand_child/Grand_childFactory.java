/**
 *  Copyright (c) 2015 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.Grand_childPackage
 * @generated
 */
public interface Grand_childFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	Grand_childFactory eINSTANCE = org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.impl.Grand_childFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Grand Child EClass</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Grand Child EClass</em>'.
	 * @generated
	 */
	GrandChildEClass createGrandChildEClass();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	Grand_childPackage getGrand_childPackage();

} //Grand_childFactory
