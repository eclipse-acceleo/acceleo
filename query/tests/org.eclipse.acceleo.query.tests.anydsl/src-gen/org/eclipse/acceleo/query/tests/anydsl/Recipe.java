/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.anydsl;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Recipe</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Recipe#getIngredients <em>Ingredients</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getRecipe()
 * @model
 * @generated
 */
public interface Recipe extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Ingredients</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ingredients</em>' reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ingredients</em>' reference list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getRecipe_Ingredients()
	 * @model
	 * @generated
	 */
	EList<Food> getIngredients();

} // Recipe
