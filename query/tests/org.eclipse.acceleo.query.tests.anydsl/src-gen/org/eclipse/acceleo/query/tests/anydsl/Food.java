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
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Food</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Food#getColor <em>Color</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Food#getCaliber <em>Caliber</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Food#getRelatedFoods <em>Related Foods</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Food#getGroup <em>Group</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Food#getLabel <em>Label</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Food#getSource <em>Source</em>}</li>
 * <li>{@link org.eclipse.acceleo.query.tests.anydsl.Food#getProducers <em>Producers</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getFood()
 * @model
 * @generated
 */
public interface Food extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute list. The list contents are of type
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Color}. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Color}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Color</em>' attribute list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Color
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getFood_Color()
	 * @model
	 * @generated
	 */
	EList<Color> getColor();

	/**
	 * Returns the value of the '<em><b>Caliber</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Caliber}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Caliber</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Caliber</em>' attribute.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Caliber
	 * @see #setCaliber(Caliber)
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getFood_Caliber()
	 * @model
	 * @generated
	 */
	Caliber getCaliber();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.anydsl.Food#getCaliber <em>Caliber</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Caliber</em>' attribute.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Caliber
	 * @see #getCaliber()
	 * @generated
	 */
	void setCaliber(Caliber value);

	/**
	 * Returns the value of the '<em><b>Related Foods</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Foods</em>' reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Related Foods</em>' reference list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getFood_RelatedFoods()
	 * @model
	 * @generated
	 */
	EList<Food> getRelatedFoods();

	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute. The literals are from the enumeration
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Group}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Group</em>' attribute.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Group
	 * @see #setGroup(Group)
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getFood_Group()
	 * @model
	 * @generated
	 */
	Group getGroup();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.anydsl.Food#getGroup <em>Group</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Group</em>' attribute.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Group
	 * @see #getGroup()
	 * @generated
	 */
	void setGroup(Group value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getFood_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.anydsl.Food#getLabel <em>Label</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference. It is bidirectional and its opposite is '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Source#getFoods <em>Foods</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Source)
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getFood_Source()
	 * @see org.eclipse.acceleo.query.tests.anydsl.Source#getFoods
	 * @model opposite="foods"
	 * @generated
	 */
	Source getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.query.tests.anydsl.Food#getSource <em>Source</em>}'
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Source value);

	/**
	 * Returns the value of the '<em><b>Producers</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Producer}. It is bidirectional and its opposite is '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Producer#getFoods <em>Foods</em>}'. <!-- begin-user-doc
	 * -->
	 * <p>
	 * If the meaning of the '<em>Producers</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Producers</em>' reference list.
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getFood_Producers()
	 * @see org.eclipse.acceleo.query.tests.anydsl.Producer#getFoods
	 * @model opposite="foods"
	 * @generated
	 */
	EList<Producer> getProducers();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	boolean ripen(Color color);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	Color preferredColor();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	Food newFood();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	void setColor(Food food, Color newColor);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model newCaliberMany="true"
	 * @generated
	 */
	void setCaliber(Food food, EList<Caliber> newCaliber);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	boolean acceptedCaliber(Caliber caliber);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	void label(String text);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	String preferredLabel(String text);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	EObject identity(EObject eObject);

} // Food
