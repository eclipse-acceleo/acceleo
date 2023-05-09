/**
 *  Copyright (c) 2015, 2021 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast.impl;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Implies;
import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Implies</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ImpliesImpl extends CallImpl implements Implies {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ImpliesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AstPackage.Literals.IMPLIES;
	}

} // ImpliesImpl
