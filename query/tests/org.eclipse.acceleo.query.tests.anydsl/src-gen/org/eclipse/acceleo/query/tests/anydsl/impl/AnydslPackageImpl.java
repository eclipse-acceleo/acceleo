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
package org.eclipse.acceleo.query.tests.anydsl.impl;

import java.util.Map;

import org.eclipse.acceleo.query.tests.anydsl.Adress;
import org.eclipse.acceleo.query.tests.anydsl.Animal;
import org.eclipse.acceleo.query.tests.anydsl.AnydslFactory;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Caliber;
import org.eclipse.acceleo.query.tests.anydsl.Chef;
import org.eclipse.acceleo.query.tests.anydsl.Color;
import org.eclipse.acceleo.query.tests.anydsl.Company;
import org.eclipse.acceleo.query.tests.anydsl.Continent;
import org.eclipse.acceleo.query.tests.anydsl.Country;
import org.eclipse.acceleo.query.tests.anydsl.Food;
import org.eclipse.acceleo.query.tests.anydsl.Group;
import org.eclipse.acceleo.query.tests.anydsl.Kind;
import org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement;
import org.eclipse.acceleo.query.tests.anydsl.NamedElement;
import org.eclipse.acceleo.query.tests.anydsl.Part;
import org.eclipse.acceleo.query.tests.anydsl.Plant;
import org.eclipse.acceleo.query.tests.anydsl.Producer;
import org.eclipse.acceleo.query.tests.anydsl.ProductionCompany;
import org.eclipse.acceleo.query.tests.anydsl.Recipe;
import org.eclipse.acceleo.query.tests.anydsl.Restaurant;
import org.eclipse.acceleo.query.tests.anydsl.Source;
import org.eclipse.acceleo.query.tests.anydsl.World;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class AnydslPackageImpl extends EPackageImpl implements AnydslPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass worldEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass multiNamedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass namedElementEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass producerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass adressEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass companyEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass productionCompanyEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass restaurantEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass chefEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass recipeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass foodEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass sourceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass plantEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass animalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass eStringToRecipeMapEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum colorEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum caliberEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum groupEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum continentEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum kindEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum partEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType countryDataEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType singleStringEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AnydslPackageImpl() {
		super(eNS_URI, AnydslFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it
	 * depends.
	 * <p>
	 * This method is used to initialize {@link AnydslPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AnydslPackage init() {
		if (isInited)
			return (AnydslPackage)EPackage.Registry.INSTANCE.getEPackage(AnydslPackage.eNS_URI);

		// Obtain or create and register package
		AnydslPackageImpl theAnydslPackage = (AnydslPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AnydslPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI)
				: new AnydslPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theAnydslPackage.createPackageContents();

		// Initialize created meta-data
		theAnydslPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAnydslPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AnydslPackage.eNS_URI, theAnydslPackage);
		return theAnydslPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getWorld() {
		return worldEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getWorld_Companies() {
		return (EReference)worldEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getWorld_Foods() {
		return (EReference)worldEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getWorld_Sources() {
		return (EReference)worldEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getMultiNamedElement() {
		return multiNamedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getMultiNamedElement_Name() {
		return (EAttribute)multiNamedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getNamedElement() {
		return namedElementEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNamedElement_Name() {
		return (EAttribute)namedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getProducer() {
		return producerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getProducer_Adress() {
		return (EReference)producerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getProducer_Company() {
		return (EReference)producerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getProducer_Foods() {
		return (EReference)producerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAdress() {
		return adressEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAdress_ZipCode() {
		return (EAttribute)adressEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAdress_City() {
		return (EAttribute)adressEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAdress_Country() {
		return (EAttribute)adressEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCompany() {
		return companyEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCompany_Adress() {
		return (EReference)companyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCompany_World() {
		return (EReference)companyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getProductionCompany() {
		return productionCompanyEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getProductionCompany_Producers() {
		return (EReference)productionCompanyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRestaurant() {
		return restaurantEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRestaurant_Chefs() {
		return (EReference)restaurantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRestaurant_Menu() {
		return (EReference)restaurantEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getChef() {
		return chefEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChef_Adress() {
		return (EReference)chefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChef_Recipes() {
		return (EReference)chefEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRecipe() {
		return recipeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRecipe_Ingredients() {
		return (EReference)recipeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getFood() {
		return foodEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getFood_Color() {
		return (EAttribute)foodEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getFood_Caliber() {
		return (EAttribute)foodEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFood_RelatedFoods() {
		return (EReference)foodEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getFood_Group() {
		return (EAttribute)foodEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getFood_Label() {
		return (EAttribute)foodEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFood_Source() {
		return (EReference)foodEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getFood_Producers() {
		return (EReference)foodEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EOperation getFood__Ripen__Color() {
		return foodEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EOperation getFood__PreferredColor() {
		return foodEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EOperation getFood__NewFood() {
		return foodEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EOperation getFood__SetColor__Food_Color() {
		return foodEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EOperation getFood__SetCaliber__Food_EList() {
		return foodEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EOperation getFood__AcceptedCaliber__Caliber() {
		return foodEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EOperation getFood__Label__String() {
		return foodEClass.getEOperations().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EOperation getFood__PreferredLabel__String() {
		return foodEClass.getEOperations().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EOperation getFood__Identity__EObject() {
		return foodEClass.getEOperations().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSource() {
		return sourceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getSource_Foods() {
		return (EReference)sourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSource_Origin() {
		return (EAttribute)sourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPlant() {
		return plantEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getPlant_Kind() {
		return (EAttribute)plantEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAnimal() {
		return animalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAnimal_Part() {
		return (EAttribute)animalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getEStringToRecipeMap() {
		return eStringToRecipeMapEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getEStringToRecipeMap_Key() {
		return (EAttribute)eStringToRecipeMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getEStringToRecipeMap_Value() {
		return (EReference)eStringToRecipeMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getColor() {
		return colorEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getCaliber() {
		return caliberEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getGroup() {
		return groupEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getContinent() {
		return continentEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getKind() {
		return kindEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getPart() {
		return partEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getCountryData() {
		return countryDataEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getSingleString() {
		return singleStringEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AnydslFactory getAnydslFactory() {
		return (AnydslFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		worldEClass = createEClass(WORLD);
		createEReference(worldEClass, WORLD__COMPANIES);
		createEReference(worldEClass, WORLD__FOODS);
		createEReference(worldEClass, WORLD__SOURCES);

		multiNamedElementEClass = createEClass(MULTI_NAMED_ELEMENT);
		createEAttribute(multiNamedElementEClass, MULTI_NAMED_ELEMENT__NAME);

		namedElementEClass = createEClass(NAMED_ELEMENT);
		createEAttribute(namedElementEClass, NAMED_ELEMENT__NAME);

		producerEClass = createEClass(PRODUCER);
		createEReference(producerEClass, PRODUCER__ADRESS);
		createEReference(producerEClass, PRODUCER__COMPANY);
		createEReference(producerEClass, PRODUCER__FOODS);

		adressEClass = createEClass(ADRESS);
		createEAttribute(adressEClass, ADRESS__ZIP_CODE);
		createEAttribute(adressEClass, ADRESS__CITY);
		createEAttribute(adressEClass, ADRESS__COUNTRY);

		companyEClass = createEClass(COMPANY);
		createEReference(companyEClass, COMPANY__ADRESS);
		createEReference(companyEClass, COMPANY__WORLD);

		productionCompanyEClass = createEClass(PRODUCTION_COMPANY);
		createEReference(productionCompanyEClass, PRODUCTION_COMPANY__PRODUCERS);

		restaurantEClass = createEClass(RESTAURANT);
		createEReference(restaurantEClass, RESTAURANT__CHEFS);
		createEReference(restaurantEClass, RESTAURANT__MENU);

		chefEClass = createEClass(CHEF);
		createEReference(chefEClass, CHEF__ADRESS);
		createEReference(chefEClass, CHEF__RECIPES);

		recipeEClass = createEClass(RECIPE);
		createEReference(recipeEClass, RECIPE__INGREDIENTS);

		foodEClass = createEClass(FOOD);
		createEAttribute(foodEClass, FOOD__COLOR);
		createEAttribute(foodEClass, FOOD__CALIBER);
		createEReference(foodEClass, FOOD__RELATED_FOODS);
		createEAttribute(foodEClass, FOOD__GROUP);
		createEAttribute(foodEClass, FOOD__LABEL);
		createEReference(foodEClass, FOOD__SOURCE);
		createEReference(foodEClass, FOOD__PRODUCERS);
		createEOperation(foodEClass, FOOD___RIPEN__COLOR);
		createEOperation(foodEClass, FOOD___PREFERRED_COLOR);
		createEOperation(foodEClass, FOOD___NEW_FOOD);
		createEOperation(foodEClass, FOOD___SET_COLOR__FOOD_COLOR);
		createEOperation(foodEClass, FOOD___SET_CALIBER__FOOD_ELIST);
		createEOperation(foodEClass, FOOD___ACCEPTED_CALIBER__CALIBER);
		createEOperation(foodEClass, FOOD___LABEL__STRING);
		createEOperation(foodEClass, FOOD___PREFERRED_LABEL__STRING);
		createEOperation(foodEClass, FOOD___IDENTITY__EOBJECT);

		sourceEClass = createEClass(SOURCE);
		createEReference(sourceEClass, SOURCE__FOODS);
		createEAttribute(sourceEClass, SOURCE__ORIGIN);

		plantEClass = createEClass(PLANT);
		createEAttribute(plantEClass, PLANT__KIND);

		animalEClass = createEClass(ANIMAL);
		createEAttribute(animalEClass, ANIMAL__PART);

		eStringToRecipeMapEClass = createEClass(ESTRING_TO_RECIPE_MAP);
		createEAttribute(eStringToRecipeMapEClass, ESTRING_TO_RECIPE_MAP__KEY);
		createEReference(eStringToRecipeMapEClass, ESTRING_TO_RECIPE_MAP__VALUE);

		// Create enums
		colorEEnum = createEEnum(COLOR);
		caliberEEnum = createEEnum(CALIBER);
		groupEEnum = createEEnum(GROUP);
		continentEEnum = createEEnum(CONTINENT);
		kindEEnum = createEEnum(KIND);
		partEEnum = createEEnum(PART);

		// Create data types
		countryDataEDataType = createEDataType(COUNTRY_DATA);
		singleStringEDataType = createEDataType(SINGLE_STRING);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is guarded to have no affect
	 * on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		producerEClass.getESuperTypes().add(this.getNamedElement());
		companyEClass.getESuperTypes().add(this.getNamedElement());
		productionCompanyEClass.getESuperTypes().add(this.getCompany());
		restaurantEClass.getESuperTypes().add(this.getCompany());
		chefEClass.getESuperTypes().add(this.getNamedElement());
		recipeEClass.getESuperTypes().add(this.getNamedElement());
		foodEClass.getESuperTypes().add(this.getNamedElement());
		sourceEClass.getESuperTypes().add(this.getMultiNamedElement());
		plantEClass.getESuperTypes().add(this.getSource());
		animalEClass.getESuperTypes().add(this.getSource());

		// Initialize classes, features, and operations; add parameters
		initEClass(worldEClass, World.class, "World", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getWorld_Companies(), this.getCompany(), null, "companies", null, 0, -1, World.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWorld_Foods(), this.getFood(), null, "foods", null, 0, -1, World.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getWorld_Sources(), this.getSource(), null, "sources", null, 0, -1, World.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(multiNamedElementEClass, MultiNamedElement.class, "MultiNamedElement", IS_ABSTRACT,
				IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMultiNamedElement_Name(), this.getSingleString(), "name", null, 0, -1,
				MultiNamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(namedElementEClass, NamedElement.class, "NamedElement", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNamedElement_Name(), this.getSingleString(), "name", null, 0, 1,
				NamedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(producerEClass, Producer.class, "Producer", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProducer_Adress(), this.getAdress(), null, "adress", null, 0, 1, Producer.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProducer_Company(), this.getCompany(), null, "company", null, 0, 1, Producer.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProducer_Foods(), this.getFood(), this.getFood_Producers(), "foods", null, 0, -1,
				Producer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(adressEClass, Adress.class, "Adress", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAdress_ZipCode(), ecorePackage.getEString(), "zipCode", null, 0, 1, Adress.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getAdress_City(), ecorePackage.getEString(), "city", null, 0, 1, Adress.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getAdress_Country(), this.getCountryData(), "country", null, 0, 1, Adress.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(companyEClass, Company.class, "Company", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompany_Adress(), this.getAdress(), null, "adress", null, 0, 1, Company.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompany_World(), this.getWorld(), null, "world", null, 0, 1, Company.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(productionCompanyEClass, ProductionCompany.class, "ProductionCompany", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProductionCompany_Producers(), this.getProducer(), null, "producers", null, 0, -1,
				ProductionCompany.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(restaurantEClass, Restaurant.class, "Restaurant", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRestaurant_Chefs(), this.getChef(), null, "chefs", null, 0, -1, Restaurant.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRestaurant_Menu(), this.getEStringToRecipeMap(), null, "menu", null, 0, -1,
				Restaurant.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(chefEClass, Chef.class, "Chef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChef_Adress(), this.getAdress(), null, "adress", null, 0, 1, Chef.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChef_Recipes(), this.getRecipe(), null, "recipes", null, 0, -1, Chef.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(recipeEClass, Recipe.class, "Recipe", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRecipe_Ingredients(), this.getFood(), null, "ingredients", null, 0, -1,
				Recipe.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(foodEClass, Food.class, "Food", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFood_Color(), this.getColor(), "color", null, 0, -1, Food.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFood_Caliber(), this.getCaliber(), "caliber", null, 0, 1, Food.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getFood_RelatedFoods(), this.getFood(), null, "relatedFoods", null, 0, -1, Food.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFood_Group(), this.getGroup(), "group", null, 0, 1, Food.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFood_Label(), ecorePackage.getEString(), "label", null, 0, 1, Food.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getFood_Source(), this.getSource(), this.getSource_Foods(), "source", null, 0, 1,
				Food.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFood_Producers(), this.getProducer(), this.getProducer_Foods(), "producers", null,
				0, -1, Food.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getFood__Ripen__Color(), ecorePackage.getEBoolean(), "ripen", 0, 1,
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getColor(), "color", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getFood__PreferredColor(), this.getColor(), "preferredColor", 0, 1, IS_UNIQUE,
				IS_ORDERED);

		initEOperation(getFood__NewFood(), this.getFood(), "newFood", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFood__SetColor__Food_Color(), null, "setColor", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFood(), "food", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getColor(), "newColor", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFood__SetCaliber__Food_EList(), null, "setCaliber", 0, 1, IS_UNIQUE,
				IS_ORDERED);
		addEParameter(op, this.getFood(), "food", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCaliber(), "newCaliber", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFood__AcceptedCaliber__Caliber(), ecorePackage.getEBoolean(),
				"acceptedCaliber", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getCaliber(), "caliber", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFood__Label__String(), null, "label", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "text", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFood__PreferredLabel__String(), ecorePackage.getEString(), "preferredLabel",
				0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "text", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getFood__Identity__EObject(), ecorePackage.getEObject(), "identity", 0, 1,
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEObject(), "eObject", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(sourceEClass, Source.class, "Source", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSource_Foods(), this.getFood(), this.getFood_Source(), "foods", null, 0, -1,
				Source.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSource_Origin(), this.getCountryData(), "origin", null, 0, -1, Source.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(plantEClass, Plant.class, "Plant", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPlant_Kind(), this.getKind(), "kind", null, 0, 1, Plant.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(animalEClass, Animal.class, "Animal", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAnimal_Part(), this.getPart(), "part", null, 0, -1, Animal.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eStringToRecipeMapEClass, Map.Entry.class, "EStringToRecipeMap", !IS_ABSTRACT,
				!IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEStringToRecipeMap_Key(), ecorePackage.getEString(), "key", null, 0, 1,
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEStringToRecipeMap_Value(), this.getRecipe(), null, "value", null, 0, 1,
				Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(colorEEnum, Color.class, "Color");
		addEEnumLiteral(colorEEnum, Color.BLACK);
		addEEnumLiteral(colorEEnum, Color.RED);
		addEEnumLiteral(colorEEnum, Color.GREEN);
		addEEnumLiteral(colorEEnum, Color.YELLOW);
		addEEnumLiteral(colorEEnum, Color.ORANGE);
		addEEnumLiteral(colorEEnum, Color.BROWN);
		addEEnumLiteral(colorEEnum, Color.PINK);
		addEEnumLiteral(colorEEnum, Color.PAL_PINK);
		addEEnumLiteral(colorEEnum, Color.VERY_YELLOW);
		addEEnumLiteral(colorEEnum, Color.WHITE);

		initEEnum(caliberEEnum, Caliber.class, "Caliber");
		addEEnumLiteral(caliberEEnum, Caliber.S);
		addEEnumLiteral(caliberEEnum, Caliber.M);
		addEEnumLiteral(caliberEEnum, Caliber.L);
		addEEnumLiteral(caliberEEnum, Caliber.XL);

		initEEnum(groupEEnum, Group.class, "Group");
		addEEnumLiteral(groupEEnum, Group.WATER);
		addEEnumLiteral(groupEEnum, Group.DAIRY);
		addEEnumLiteral(groupEEnum, Group.FRUIT);
		addEEnumLiteral(groupEEnum, Group.GRAIN);
		addEEnumLiteral(groupEEnum, Group.PROTEIN);
		addEEnumLiteral(groupEEnum, Group.SWEET);
		addEEnumLiteral(groupEEnum, Group.VEGETABLE);
		addEEnumLiteral(groupEEnum, Group.ALCOHOL);

		initEEnum(continentEEnum, Continent.class, "Continent");
		addEEnumLiteral(continentEEnum, Continent.EUROPE);
		addEEnumLiteral(continentEEnum, Continent.ASIA);
		addEEnumLiteral(continentEEnum, Continent.AFRICA);
		addEEnumLiteral(continentEEnum, Continent.AMERICA);
		addEEnumLiteral(continentEEnum, Continent.AUSTRALIA);
		addEEnumLiteral(continentEEnum, Continent.ANTARCTICA);

		initEEnum(kindEEnum, Kind.class, "Kind");
		addEEnumLiteral(kindEEnum, Kind.OTHER);
		addEEnumLiteral(kindEEnum, Kind.SEED);
		addEEnumLiteral(kindEEnum, Kind.OILSEED);
		addEEnumLiteral(kindEEnum, Kind.TREE);
		addEEnumLiteral(kindEEnum, Kind.ROOT);
		addEEnumLiteral(kindEEnum, Kind.BULB);
		addEEnumLiteral(kindEEnum, Kind.LEAF);
		addEEnumLiteral(kindEEnum, Kind.STEM);
		addEEnumLiteral(kindEEnum, Kind.FLOWER);
		addEEnumLiteral(kindEEnum, Kind.INFLORESCENCE);
		addEEnumLiteral(kindEEnum, Kind.SPICE);

		initEEnum(partEEnum, Part.class, "Part");
		addEEnumLiteral(partEEnum, Part.OTHER);
		addEEnumLiteral(partEEnum, Part.MUSCLE);
		addEEnumLiteral(partEEnum, Part.ORGAN);

		// Initialize data types
		initEDataType(countryDataEDataType, Country.class, "CountryData", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(singleStringEDataType, String.class, "SingleString", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.obeo.fr/dsl/dnc/archetype
		createArchetypeAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.obeo.fr/dsl/dnc/archetype</b>. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void createArchetypeAnnotations() {
		String source = "http://www.obeo.fr/dsl/dnc/archetype";
		addAnnotation(worldEClass, source, new String[] {"archetype", "MomentInterval" });
	}

} // AnydslPackageImpl
