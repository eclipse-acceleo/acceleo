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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslFactory
 * @model kind="package"
 * @generated
 */
public interface AnydslPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "anydsl";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/anydsl";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "anydsl";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	AnydslPackage eINSTANCE = org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.WorldImpl
	 * <em>World</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.WorldImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getWorld()
	 * @generated
	 */
	int WORLD = 0;

	/**
	 * The feature id for the '<em><b>Companies</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORLD__COMPANIES = 0;

	/**
	 * The feature id for the '<em><b>Foods</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORLD__FOODS = 1;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORLD__SOURCES = 2;

	/**
	 * The number of structural features of the '<em>World</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORLD_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>World</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WORLD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement
	 * <em>Multi Named Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getMultiNamedElement()
	 * @generated
	 */
	int MULTI_NAMED_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Multi Named Element</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Multi Named Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MULTI_NAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.NamedElement
	 * <em>Named Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.NamedElement
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Named Element</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProducerImpl
	 * <em>Producer</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.ProducerImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getProducer()
	 * @generated
	 */
	int PRODUCER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCER__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Adress</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCER__ADRESS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Company</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCER__COMPANY = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Foods</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCER__FOODS = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Producer</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCER_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Producer</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCER_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.AdressImpl
	 * <em>Adress</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AdressImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getAdress()
	 * @generated
	 */
	int ADRESS = 4;

	/**
	 * The feature id for the '<em><b>Zip Code</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADRESS__ZIP_CODE = 0;

	/**
	 * The feature id for the '<em><b>City</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADRESS__CITY = 1;

	/**
	 * The feature id for the '<em><b>Country</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADRESS__COUNTRY = 2;

	/**
	 * The number of structural features of the '<em>Adress</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADRESS_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Adress</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADRESS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.Company <em>Company</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.Company
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getCompany()
	 * @generated
	 */
	int COMPANY = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPANY__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Adress</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPANY__ADRESS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>World</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPANY__WORLD = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Company</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPANY_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Company</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPANY_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProductionCompanyImpl
	 * <em>Production Company</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.ProductionCompanyImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getProductionCompany()
	 * @generated
	 */
	int PRODUCTION_COMPANY = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCTION_COMPANY__NAME = COMPANY__NAME;

	/**
	 * The feature id for the '<em><b>Adress</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCTION_COMPANY__ADRESS = COMPANY__ADRESS;

	/**
	 * The feature id for the '<em><b>World</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCTION_COMPANY__WORLD = COMPANY__WORLD;

	/**
	 * The feature id for the '<em><b>Producers</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCTION_COMPANY__PRODUCERS = COMPANY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Production Company</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCTION_COMPANY_FEATURE_COUNT = COMPANY_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Production Company</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRODUCTION_COMPANY_OPERATION_COUNT = COMPANY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.RestaurantImpl
	 * <em>Restaurant</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.RestaurantImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getRestaurant()
	 * @generated
	 */
	int RESTAURANT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESTAURANT__NAME = COMPANY__NAME;

	/**
	 * The feature id for the '<em><b>Adress</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESTAURANT__ADRESS = COMPANY__ADRESS;

	/**
	 * The feature id for the '<em><b>World</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESTAURANT__WORLD = COMPANY__WORLD;

	/**
	 * The feature id for the '<em><b>Chefs</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESTAURANT__CHEFS = COMPANY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Menu</b></em>' map. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESTAURANT__MENU = COMPANY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Restaurant</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESTAURANT_FEATURE_COUNT = COMPANY_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Restaurant</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESTAURANT_OPERATION_COUNT = COMPANY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.ChefImpl <em>Chef</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.ChefImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getChef()
	 * @generated
	 */
	int CHEF = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CHEF__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Adress</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CHEF__ADRESS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Recipes</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CHEF__RECIPES = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Chef</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CHEF_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Chef</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CHEF_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.RecipeImpl
	 * <em>Recipe</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.RecipeImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getRecipe()
	 * @generated
	 */
	int RECIPE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RECIPE__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Ingredients</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RECIPE__INGREDIENTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Recipe</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RECIPE_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Recipe</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RECIPE_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl <em>Food</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getFood()
	 * @generated
	 */
	int FOOD = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Color</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD__COLOR = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Caliber</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD__CALIBER = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Related Foods</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD__RELATED_FOODS = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD__GROUP = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD__LABEL = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD__SOURCE = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Producers</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD__PRODUCERS = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Food</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The operation id for the '<em>Ripen</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD___RIPEN__COLOR = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Preferred Color</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD___PREFERRED_COLOR = NAMED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The operation id for the '<em>New Food</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD___NEW_FOOD = NAMED_ELEMENT_OPERATION_COUNT + 2;

	/**
	 * The operation id for the '<em>Set Color</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD___SET_COLOR__FOOD_COLOR = NAMED_ELEMENT_OPERATION_COUNT + 3;

	/**
	 * The operation id for the '<em>Set Caliber</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD___SET_CALIBER__FOOD_ELIST = NAMED_ELEMENT_OPERATION_COUNT + 4;

	/**
	 * The operation id for the '<em>Accepted Caliber</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD___ACCEPTED_CALIBER__CALIBER = NAMED_ELEMENT_OPERATION_COUNT + 5;

	/**
	 * The operation id for the '<em>Label</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD___LABEL__STRING = NAMED_ELEMENT_OPERATION_COUNT + 6;

	/**
	 * The operation id for the '<em>Preferred Label</em>' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD___PREFERRED_LABEL__STRING = NAMED_ELEMENT_OPERATION_COUNT + 7;

	/**
	 * The operation id for the '<em>Identity</em>' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD___IDENTITY__EOBJECT = NAMED_ELEMENT_OPERATION_COUNT + 8;

	/**
	 * The number of operations of the '<em>Food</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOOD_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.Source <em>Source</em>}'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.Source
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getSource()
	 * @generated
	 */
	int SOURCE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SOURCE__NAME = MULTI_NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Foods</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SOURCE__FOODS = MULTI_NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SOURCE__ORIGIN = MULTI_NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Source</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SOURCE_FEATURE_COUNT = MULTI_NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Source</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SOURCE_OPERATION_COUNT = MULTI_NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.PlantImpl
	 * <em>Plant</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.PlantImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getPlant()
	 * @generated
	 */
	int PLANT = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANT__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Foods</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANT__FOODS = SOURCE__FOODS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANT__ORIGIN = SOURCE__ORIGIN;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANT__KIND = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Plant</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANT_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Plant</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLANT_OPERATION_COUNT = SOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.AnimalImpl
	 * <em>Animal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnimalImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getAnimal()
	 * @generated
	 */
	int ANIMAL = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANIMAL__NAME = SOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Foods</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANIMAL__FOODS = SOURCE__FOODS;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANIMAL__ORIGIN = SOURCE__ORIGIN;

	/**
	 * The feature id for the '<em><b>Part</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANIMAL__PART = SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Animal</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANIMAL_FEATURE_COUNT = SOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Animal</em>' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANIMAL_OPERATION_COUNT = SOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.EStringToRecipeMapImpl
	 * <em>EString To Recipe Map</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.EStringToRecipeMapImpl
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getEStringToRecipeMap()
	 * @generated
	 */
	int ESTRING_TO_RECIPE_MAP = 14;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_RECIPE_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_RECIPE_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EString To Recipe Map</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_RECIPE_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>EString To Recipe Map</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_RECIPE_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.Color <em>Color</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.Color
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getColor()
	 * @generated
	 */
	int COLOR = 15;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.Caliber <em>Caliber</em>}'
	 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.Caliber
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getCaliber()
	 * @generated
	 */
	int CALIBER = 16;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.Group <em>Group</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.Group
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 17;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.Continent <em>Continent</em>}
	 * ' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.Continent
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getContinent()
	 * @generated
	 */
	int CONTINENT = 18;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.Kind <em>Kind</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.Kind
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getKind()
	 * @generated
	 */
	int KIND = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.query.tests.anydsl.Part <em>Part</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.Part
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getPart()
	 * @generated
	 */
	int PART = 20;

	/**
	 * The meta object id for the '<em>Country Data</em>' data type. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.acceleo.query.tests.anydsl.Country
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getCountryData()
	 * @generated
	 */
	int COUNTRY_DATA = 21;

	/**
	 * The meta object id for the '<em>Single String</em>' data type. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see java.lang.String
	 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getSingleString()
	 * @generated
	 */
	int SINGLE_STRING = 22;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.World <em>World</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>World</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.World
	 * @generated
	 */
	EClass getWorld();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.World#getCompanies <em>Companies</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Companies</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.World#getCompanies()
	 * @see #getWorld()
	 * @generated
	 */
	EReference getWorld_Companies();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.World#getFoods <em>Foods</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Foods</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.World#getFoods()
	 * @see #getWorld()
	 * @generated
	 */
	EReference getWorld_Foods();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.World#getSources <em>Sources</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Sources</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.World#getSources()
	 * @see #getWorld()
	 * @generated
	 */
	EReference getWorld_Sources();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement
	 * <em>Multi Named Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Multi Named Element</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement
	 * @generated
	 */
	EClass getMultiNamedElement();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement#getName <em>Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Name</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement#getName()
	 * @see #getMultiNamedElement()
	 * @generated
	 */
	EAttribute getMultiNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.NamedElement
	 * <em>Named Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.NamedElement#getName <em>Name</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Producer
	 * <em>Producer</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Producer</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Producer
	 * @generated
	 */
	EClass getProducer();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Producer#getAdress <em>Adress</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Adress</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Producer#getAdress()
	 * @see #getProducer()
	 * @generated
	 */
	EReference getProducer_Adress();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Producer#getCompany <em>Company</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Company</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Producer#getCompany()
	 * @see #getProducer()
	 * @generated
	 */
	EReference getProducer_Company();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Producer#getFoods <em>Foods</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Foods</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Producer#getFoods()
	 * @see #getProducer()
	 * @generated
	 */
	EReference getProducer_Foods();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Adress
	 * <em>Adress</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Adress</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Adress
	 * @generated
	 */
	EClass getAdress();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Adress#getZipCode <em>Zip Code</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Zip Code</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Adress#getZipCode()
	 * @see #getAdress()
	 * @generated
	 */
	EAttribute getAdress_ZipCode();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Adress#getCity <em>City</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>City</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Adress#getCity()
	 * @see #getAdress()
	 * @generated
	 */
	EAttribute getAdress_City();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Adress#getCountry <em>Country</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Country</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Adress#getCountry()
	 * @see #getAdress()
	 * @generated
	 */
	EAttribute getAdress_Country();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Company
	 * <em>Company</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Company</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Company
	 * @generated
	 */
	EClass getCompany();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Company#getAdress <em>Adress</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Adress</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Company#getAdress()
	 * @see #getCompany()
	 * @generated
	 */
	EReference getCompany_Adress();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Company#getWorld <em>World</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>World</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Company#getWorld()
	 * @see #getCompany()
	 * @generated
	 */
	EReference getCompany_World();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.ProductionCompany
	 * <em>Production Company</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Production Company</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.ProductionCompany
	 * @generated
	 */
	EClass getProductionCompany();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.ProductionCompany#getProducers <em>Producers</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Producers</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.ProductionCompany#getProducers()
	 * @see #getProductionCompany()
	 * @generated
	 */
	EReference getProductionCompany_Producers();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Restaurant
	 * <em>Restaurant</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Restaurant</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Restaurant
	 * @generated
	 */
	EClass getRestaurant();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Restaurant#getChefs <em>Chefs</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Chefs</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Restaurant#getChefs()
	 * @see #getRestaurant()
	 * @generated
	 */
	EReference getRestaurant_Chefs();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.acceleo.query.tests.anydsl.Restaurant#getMenu
	 * <em>Menu</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the map '<em>Menu</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Restaurant#getMenu()
	 * @see #getRestaurant()
	 * @generated
	 */
	EReference getRestaurant_Menu();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Chef <em>Chef</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Chef</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Chef
	 * @generated
	 */
	EClass getChef();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Chef#getAdress <em>Adress</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Adress</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Chef#getAdress()
	 * @see #getChef()
	 * @generated
	 */
	EReference getChef_Adress();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Chef#getRecipes <em>Recipes</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Recipes</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Chef#getRecipes()
	 * @see #getChef()
	 * @generated
	 */
	EReference getChef_Recipes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Recipe
	 * <em>Recipe</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Recipe</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Recipe
	 * @generated
	 */
	EClass getRecipe();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Recipe#getIngredients <em>Ingredients</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Ingredients</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Recipe#getIngredients()
	 * @see #getRecipe()
	 * @generated
	 */
	EReference getRecipe_Ingredients();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Food <em>Food</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Food</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food
	 * @generated
	 */
	EClass getFood();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#getColor <em>Color</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Color</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#getColor()
	 * @see #getFood()
	 * @generated
	 */
	EAttribute getFood_Color();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#getCaliber <em>Caliber</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Caliber</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#getCaliber()
	 * @see #getFood()
	 * @generated
	 */
	EAttribute getFood_Caliber();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#getRelatedFoods <em>Related Foods</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Related Foods</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#getRelatedFoods()
	 * @see #getFood()
	 * @generated
	 */
	EReference getFood_RelatedFoods();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.tests.anydsl.Food#getGroup
	 * <em>Group</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Group</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#getGroup()
	 * @see #getFood()
	 * @generated
	 */
	EAttribute getFood_Group();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.tests.anydsl.Food#getLabel
	 * <em>Label</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#getLabel()
	 * @see #getFood()
	 * @generated
	 */
	EAttribute getFood_Label();

	/**
	 * Returns the meta object for the reference '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#getSource <em>Source</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#getSource()
	 * @see #getFood()
	 * @generated
	 */
	EReference getFood_Source();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#getProducers <em>Producers</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Producers</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#getProducers()
	 * @see #getFood()
	 * @generated
	 */
	EReference getFood_Producers();

	/**
	 * Returns the meta object for the '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#ripen(org.eclipse.acceleo.query.tests.anydsl.Color)
	 * <em>Ripen</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Ripen</em>' operation.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#ripen(org.eclipse.acceleo.query.tests.anydsl.Color)
	 * @generated
	 */
	EOperation getFood__Ripen__Color();

	/**
	 * Returns the meta object for the '{@link org.eclipse.acceleo.query.tests.anydsl.Food#preferredColor()
	 * <em>Preferred Color</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Preferred Color</em>' operation.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#preferredColor()
	 * @generated
	 */
	EOperation getFood__PreferredColor();

	/**
	 * Returns the meta object for the '{@link org.eclipse.acceleo.query.tests.anydsl.Food#newFood()
	 * <em>New Food</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>New Food</em>' operation.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#newFood()
	 * @generated
	 */
	EOperation getFood__NewFood();

	/**
	 * Returns the meta object for the '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#setColor(org.eclipse.acceleo.query.tests.anydsl.Food, org.eclipse.acceleo.query.tests.anydsl.Color)
	 * <em>Set Color</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Set Color</em>' operation.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#setColor(org.eclipse.acceleo.query.tests.anydsl.Food,
	 *      org.eclipse.acceleo.query.tests.anydsl.Color)
	 * @generated
	 */
	EOperation getFood__SetColor__Food_Color();

	/**
	 * Returns the meta object for the '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#setCaliber(org.eclipse.acceleo.query.tests.anydsl.Food, org.eclipse.emf.common.util.EList)
	 * <em>Set Caliber</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Set Caliber</em>' operation.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#setCaliber(org.eclipse.acceleo.query.tests.anydsl.Food,
	 *      org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	EOperation getFood__SetCaliber__Food_EList();

	/**
	 * Returns the meta object for the '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#acceptedCaliber(org.eclipse.acceleo.query.tests.anydsl.Caliber)
	 * <em>Accepted Caliber</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Accepted Caliber</em>' operation.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#acceptedCaliber(org.eclipse.acceleo.query.tests.anydsl.Caliber)
	 * @generated
	 */
	EOperation getFood__AcceptedCaliber__Caliber();

	/**
	 * Returns the meta object for the '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#label(java.lang.String) <em>Label</em>}' operation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Label</em>' operation.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#label(java.lang.String)
	 * @generated
	 */
	EOperation getFood__Label__String();

	/**
	 * Returns the meta object for the '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#preferredLabel(java.lang.String)
	 * <em>Preferred Label</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Preferred Label</em>' operation.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#preferredLabel(java.lang.String)
	 * @generated
	 */
	EOperation getFood__PreferredLabel__String();

	/**
	 * Returns the meta object for the '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Food#identity(org.eclipse.emf.ecore.EObject)
	 * <em>Identity</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the '<em>Identity</em>' operation.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food#identity(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	EOperation getFood__Identity__EObject();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Source
	 * <em>Source</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Source</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Source
	 * @generated
	 */
	EClass getSource();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Source#getFoods <em>Foods</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Foods</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Source#getFoods()
	 * @see #getSource()
	 * @generated
	 */
	EReference getSource_Foods();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Source#getOrigin <em>Origin</em>}'. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Origin</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Source#getOrigin()
	 * @see #getSource()
	 * @generated
	 */
	EAttribute getSource_Origin();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Plant <em>Plant</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Plant</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Plant
	 * @generated
	 */
	EClass getPlant();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.acceleo.query.tests.anydsl.Plant#getKind
	 * <em>Kind</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Plant#getKind()
	 * @see #getPlant()
	 * @generated
	 */
	EAttribute getPlant_Kind();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.query.tests.anydsl.Animal
	 * <em>Animal</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Animal</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Animal
	 * @generated
	 */
	EClass getAnimal();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Animal#getPart <em>Part</em>}'. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Part</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Animal#getPart()
	 * @see #getAnimal()
	 * @generated
	 */
	EAttribute getAnimal_Part();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EString To Recipe Map</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>EString To Recipe Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.acceleo.query.tests.anydsl.Recipe"
	 * @generated
	 */
	EClass getEStringToRecipeMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToRecipeMap()
	 * @generated
	 */
	EAttribute getEStringToRecipeMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToRecipeMap()
	 * @generated
	 */
	EReference getEStringToRecipeMap_Value();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.query.tests.anydsl.Color <em>Color</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Color</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Color
	 * @generated
	 */
	EEnum getColor();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.query.tests.anydsl.Caliber
	 * <em>Caliber</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Caliber</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Caliber
	 * @generated
	 */
	EEnum getCaliber();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.query.tests.anydsl.Group <em>Group</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Group</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Group
	 * @generated
	 */
	EEnum getGroup();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.query.tests.anydsl.Continent
	 * <em>Continent</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Continent</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Continent
	 * @generated
	 */
	EEnum getContinent();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.query.tests.anydsl.Kind <em>Kind</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Kind</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Kind
	 * @generated
	 */
	EEnum getKind();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.acceleo.query.tests.anydsl.Part <em>Part</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Part</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Part
	 * @generated
	 */
	EEnum getPart();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.acceleo.query.tests.anydsl.Country
	 * <em>Country Data</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Country Data</em>'.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Country
	 * @model instanceClass="org.eclipse.acceleo.query.tests.anydsl.Country"
	 * @generated
	 */
	EDataType getCountryData();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Single String</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>Single String</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 * @generated
	 */
	EDataType getSingleString();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AnydslFactory getAnydslFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.WorldImpl
		 * <em>World</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.WorldImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getWorld()
		 * @generated
		 */
		EClass WORLD = eINSTANCE.getWorld();

		/**
		 * The meta object literal for the '<em><b>Companies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WORLD__COMPANIES = eINSTANCE.getWorld_Companies();

		/**
		 * The meta object literal for the '<em><b>Foods</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WORLD__FOODS = eINSTANCE.getWorld_Foods();

		/**
		 * The meta object literal for the '<em><b>Sources</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference WORLD__SOURCES = eINSTANCE.getWorld_Sources();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement
		 * <em>Multi Named Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getMultiNamedElement()
		 * @generated
		 */
		EClass MULTI_NAMED_ELEMENT = eINSTANCE.getMultiNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute list feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MULTI_NAMED_ELEMENT__NAME = eINSTANCE.getMultiNamedElement_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.NamedElement
		 * <em>Named Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.NamedElement
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.ProducerImpl
		 * <em>Producer</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.ProducerImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getProducer()
		 * @generated
		 */
		EClass PRODUCER = eINSTANCE.getProducer();

		/**
		 * The meta object literal for the '<em><b>Adress</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PRODUCER__ADRESS = eINSTANCE.getProducer_Adress();

		/**
		 * The meta object literal for the '<em><b>Company</b></em>' reference feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PRODUCER__COMPANY = eINSTANCE.getProducer_Company();

		/**
		 * The meta object literal for the '<em><b>Foods</b></em>' reference list feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PRODUCER__FOODS = eINSTANCE.getProducer_Foods();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.AdressImpl
		 * <em>Adress</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AdressImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getAdress()
		 * @generated
		 */
		EClass ADRESS = eINSTANCE.getAdress();

		/**
		 * The meta object literal for the '<em><b>Zip Code</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ADRESS__ZIP_CODE = eINSTANCE.getAdress_ZipCode();

		/**
		 * The meta object literal for the '<em><b>City</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ADRESS__CITY = eINSTANCE.getAdress_City();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ADRESS__COUNTRY = eINSTANCE.getAdress_Country();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.Company
		 * <em>Company</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.Company
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getCompany()
		 * @generated
		 */
		EClass COMPANY = eINSTANCE.getCompany();

		/**
		 * The meta object literal for the '<em><b>Adress</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPANY__ADRESS = eINSTANCE.getCompany_Adress();

		/**
		 * The meta object literal for the '<em><b>World</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference COMPANY__WORLD = eINSTANCE.getCompany_World();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.anydsl.impl.ProductionCompanyImpl
		 * <em>Production Company</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.ProductionCompanyImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getProductionCompany()
		 * @generated
		 */
		EClass PRODUCTION_COMPANY = eINSTANCE.getProductionCompany();

		/**
		 * The meta object literal for the '<em><b>Producers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PRODUCTION_COMPANY__PRODUCERS = eINSTANCE.getProductionCompany_Producers();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.RestaurantImpl
		 * <em>Restaurant</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.RestaurantImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getRestaurant()
		 * @generated
		 */
		EClass RESTAURANT = eINSTANCE.getRestaurant();

		/**
		 * The meta object literal for the '<em><b>Chefs</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RESTAURANT__CHEFS = eINSTANCE.getRestaurant_Chefs();

		/**
		 * The meta object literal for the '<em><b>Menu</b></em>' map feature. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RESTAURANT__MENU = eINSTANCE.getRestaurant_Menu();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.ChefImpl
		 * <em>Chef</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.ChefImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getChef()
		 * @generated
		 */
		EClass CHEF = eINSTANCE.getChef();

		/**
		 * The meta object literal for the '<em><b>Adress</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CHEF__ADRESS = eINSTANCE.getChef_Adress();

		/**
		 * The meta object literal for the '<em><b>Recipes</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference CHEF__RECIPES = eINSTANCE.getChef_Recipes();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.RecipeImpl
		 * <em>Recipe</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.RecipeImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getRecipe()
		 * @generated
		 */
		EClass RECIPE = eINSTANCE.getRecipe();

		/**
		 * The meta object literal for the '<em><b>Ingredients</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference RECIPE__INGREDIENTS = eINSTANCE.getRecipe_Ingredients();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl
		 * <em>Food</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.FoodImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getFood()
		 * @generated
		 */
		EClass FOOD = eINSTANCE.getFood();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' attribute list feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FOOD__COLOR = eINSTANCE.getFood_Color();

		/**
		 * The meta object literal for the '<em><b>Caliber</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FOOD__CALIBER = eINSTANCE.getFood_Caliber();

		/**
		 * The meta object literal for the '<em><b>Related Foods</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOOD__RELATED_FOODS = eINSTANCE.getFood_RelatedFoods();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FOOD__GROUP = eINSTANCE.getFood_Group();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FOOD__LABEL = eINSTANCE.getFood_Label();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOOD__SOURCE = eINSTANCE.getFood_Source();

		/**
		 * The meta object literal for the '<em><b>Producers</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOOD__PRODUCERS = eINSTANCE.getFood_Producers();

		/**
		 * The meta object literal for the '<em><b>Ripen</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation FOOD___RIPEN__COLOR = eINSTANCE.getFood__Ripen__Color();

		/**
		 * The meta object literal for the '<em><b>Preferred Color</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation FOOD___PREFERRED_COLOR = eINSTANCE.getFood__PreferredColor();

		/**
		 * The meta object literal for the '<em><b>New Food</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation FOOD___NEW_FOOD = eINSTANCE.getFood__NewFood();

		/**
		 * The meta object literal for the '<em><b>Set Color</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation FOOD___SET_COLOR__FOOD_COLOR = eINSTANCE.getFood__SetColor__Food_Color();

		/**
		 * The meta object literal for the '<em><b>Set Caliber</b></em>' operation. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation FOOD___SET_CALIBER__FOOD_ELIST = eINSTANCE.getFood__SetCaliber__Food_EList();

		/**
		 * The meta object literal for the '<em><b>Accepted Caliber</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation FOOD___ACCEPTED_CALIBER__CALIBER = eINSTANCE.getFood__AcceptedCaliber__Caliber();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation FOOD___LABEL__STRING = eINSTANCE.getFood__Label__String();

		/**
		 * The meta object literal for the '<em><b>Preferred Label</b></em>' operation. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation FOOD___PREFERRED_LABEL__STRING = eINSTANCE.getFood__PreferredLabel__String();

		/**
		 * The meta object literal for the '<em><b>Identity</b></em>' operation. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @generated
		 */
		EOperation FOOD___IDENTITY__EOBJECT = eINSTANCE.getFood__Identity__EObject();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.Source
		 * <em>Source</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.Source
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getSource()
		 * @generated
		 */
		EClass SOURCE = eINSTANCE.getSource();

		/**
		 * The meta object literal for the '<em><b>Foods</b></em>' reference list feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SOURCE__FOODS = eINSTANCE.getSource_Foods();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' attribute list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SOURCE__ORIGIN = eINSTANCE.getSource_Origin();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.PlantImpl
		 * <em>Plant</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.PlantImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getPlant()
		 * @generated
		 */
		EClass PLANT = eINSTANCE.getPlant();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLANT__KIND = eINSTANCE.getPlant_Kind();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.impl.AnimalImpl
		 * <em>Animal</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnimalImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getAnimal()
		 * @generated
		 */
		EClass ANIMAL = eINSTANCE.getAnimal();

		/**
		 * The meta object literal for the '<em><b>Part</b></em>' attribute list feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ANIMAL__PART = eINSTANCE.getAnimal_Part();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.query.tests.anydsl.impl.EStringToRecipeMapImpl
		 * <em>EString To Recipe Map</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.EStringToRecipeMapImpl
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getEStringToRecipeMap()
		 * @generated
		 */
		EClass ESTRING_TO_RECIPE_MAP = eINSTANCE.getEStringToRecipeMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ESTRING_TO_RECIPE_MAP__KEY = eINSTANCE.getEStringToRecipeMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ESTRING_TO_RECIPE_MAP__VALUE = eINSTANCE.getEStringToRecipeMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.Color
		 * <em>Color</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.Color
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getColor()
		 * @generated
		 */
		EEnum COLOR = eINSTANCE.getColor();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.Caliber
		 * <em>Caliber</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.Caliber
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getCaliber()
		 * @generated
		 */
		EEnum CALIBER = eINSTANCE.getCaliber();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.Group
		 * <em>Group</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.Group
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getGroup()
		 * @generated
		 */
		EEnum GROUP = eINSTANCE.getGroup();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.Continent
		 * <em>Continent</em>}' enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.Continent
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getContinent()
		 * @generated
		 */
		EEnum CONTINENT = eINSTANCE.getContinent();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.Kind <em>Kind</em>}'
		 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.Kind
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getKind()
		 * @generated
		 */
		EEnum KIND = eINSTANCE.getKind();

		/**
		 * The meta object literal for the '{@link org.eclipse.acceleo.query.tests.anydsl.Part <em>Part</em>}'
		 * enum. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.Part
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getPart()
		 * @generated
		 */
		EEnum PART = eINSTANCE.getPart();

		/**
		 * The meta object literal for the '<em>Country Data</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.query.tests.anydsl.Country
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getCountryData()
		 * @generated
		 */
		EDataType COUNTRY_DATA = eINSTANCE.getCountryData();

		/**
		 * The meta object literal for the '<em>Single String</em>' data type. <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * 
		 * @see java.lang.String
		 * @see org.eclipse.acceleo.query.tests.anydsl.impl.AnydslPackageImpl#getSingleString()
		 * @generated
		 */
		EDataType SINGLE_STRING = eINSTANCE.getSingleString();

	}

} // AnydslPackage
