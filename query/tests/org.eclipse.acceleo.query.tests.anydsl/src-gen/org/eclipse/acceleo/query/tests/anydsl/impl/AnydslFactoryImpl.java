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
import org.eclipse.acceleo.query.tests.anydsl.Continent;
import org.eclipse.acceleo.query.tests.anydsl.Country;
import org.eclipse.acceleo.query.tests.anydsl.Food;
import org.eclipse.acceleo.query.tests.anydsl.Group;
import org.eclipse.acceleo.query.tests.anydsl.Kind;
import org.eclipse.acceleo.query.tests.anydsl.Part;
import org.eclipse.acceleo.query.tests.anydsl.Plant;
import org.eclipse.acceleo.query.tests.anydsl.Producer;
import org.eclipse.acceleo.query.tests.anydsl.ProductionCompany;
import org.eclipse.acceleo.query.tests.anydsl.Recipe;
import org.eclipse.acceleo.query.tests.anydsl.Restaurant;
import org.eclipse.acceleo.query.tests.anydsl.World;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class AnydslFactoryImpl extends EFactoryImpl implements AnydslFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static AnydslFactory init() {
		try {
			AnydslFactory theAnydslFactory = (AnydslFactory)EPackage.Registry.INSTANCE
					.getEFactory(AnydslPackage.eNS_URI);
			if (theAnydslFactory != null) {
				return theAnydslFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AnydslFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AnydslFactoryImpl() {
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
			case AnydslPackage.WORLD:
				return createWorld();
			case AnydslPackage.PRODUCER:
				return createProducer();
			case AnydslPackage.ADRESS:
				return createAdress();
			case AnydslPackage.PRODUCTION_COMPANY:
				return createProductionCompany();
			case AnydslPackage.RESTAURANT:
				return createRestaurant();
			case AnydslPackage.CHEF:
				return createChef();
			case AnydslPackage.RECIPE:
				return createRecipe();
			case AnydslPackage.FOOD:
				return createFood();
			case AnydslPackage.PLANT:
				return createPlant();
			case AnydslPackage.ANIMAL:
				return createAnimal();
			case AnydslPackage.ESTRING_TO_RECIPE_MAP:
				return (EObject)createEStringToRecipeMap();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case AnydslPackage.COLOR:
				return createColorFromString(eDataType, initialValue);
			case AnydslPackage.CALIBER:
				return createCaliberFromString(eDataType, initialValue);
			case AnydslPackage.GROUP:
				return createGroupFromString(eDataType, initialValue);
			case AnydslPackage.CONTINENT:
				return createContinentFromString(eDataType, initialValue);
			case AnydslPackage.KIND:
				return createKindFromString(eDataType, initialValue);
			case AnydslPackage.PART:
				return createPartFromString(eDataType, initialValue);
			case AnydslPackage.COUNTRY_DATA:
				return createCountryDataFromString(eDataType, initialValue);
			case AnydslPackage.SINGLE_STRING:
				return createSingleStringFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case AnydslPackage.COLOR:
				return convertColorToString(eDataType, instanceValue);
			case AnydslPackage.CALIBER:
				return convertCaliberToString(eDataType, instanceValue);
			case AnydslPackage.GROUP:
				return convertGroupToString(eDataType, instanceValue);
			case AnydslPackage.CONTINENT:
				return convertContinentToString(eDataType, instanceValue);
			case AnydslPackage.KIND:
				return convertKindToString(eDataType, instanceValue);
			case AnydslPackage.PART:
				return convertPartToString(eDataType, instanceValue);
			case AnydslPackage.COUNTRY_DATA:
				return convertCountryDataToString(eDataType, instanceValue);
			case AnydslPackage.SINGLE_STRING:
				return convertSingleStringToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public World createWorld() {
		WorldImpl world = new WorldImpl();
		return world;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Producer createProducer() {
		ProducerImpl producer = new ProducerImpl();
		return producer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Adress createAdress() {
		AdressImpl adress = new AdressImpl();
		return adress;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ProductionCompany createProductionCompany() {
		ProductionCompanyImpl productionCompany = new ProductionCompanyImpl();
		return productionCompany;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Restaurant createRestaurant() {
		RestaurantImpl restaurant = new RestaurantImpl();
		return restaurant;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Chef createChef() {
		ChefImpl chef = new ChefImpl();
		return chef;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Recipe createRecipe() {
		RecipeImpl recipe = new RecipeImpl();
		return recipe;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Food createFood() {
		FoodImpl food = new FoodImpl();
		return food;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Plant createPlant() {
		PlantImpl plant = new PlantImpl();
		return plant;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Animal createAnimal() {
		AnimalImpl animal = new AnimalImpl();
		return animal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, Recipe> createEStringToRecipeMap() {
		EStringToRecipeMapImpl eStringToRecipeMap = new EStringToRecipeMapImpl();
		return eStringToRecipeMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Color createColorFromString(EDataType eDataType, String initialValue) {
		Color result = Color.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertColorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Caliber createCaliberFromString(EDataType eDataType, String initialValue) {
		Caliber result = Caliber.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertCaliberToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Group createGroupFromString(EDataType eDataType, String initialValue) {
		Group result = Group.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertGroupToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Continent createContinentFromString(EDataType eDataType, String initialValue) {
		Continent result = Continent.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertContinentToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Kind createKindFromString(EDataType eDataType, String initialValue) {
		Kind result = Kind.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Part createPartFromString(EDataType eDataType, String initialValue) {
		Part result = Part.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertPartToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Country createCountryDataFromString(EDataType eDataType, String initialValue) {
		// Serializes the country string value
		String[] split = initialValue.split("-");
		if (split.length == 2) {
			String countryName = split[0].trim();
			Continent continent = Continent.get(split[1].trim());
			if (countryName != null && continent != null) {
				return new Country(countryName, continent);
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String convertCountryDataToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue instanceof Country) {
			Country country = (Country)instanceValue;
			String name = country.getName();
			Continent continent = country.getContinent();
			if (name != null && continent != null) {
				return name + "-" + continent.toString();
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Object createSingleStringFromString(EDataType eDataType, String initialValue) {
		// Serializes the Name string value
		return initialValue;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String convertSingleStringToString(EDataType eDataType, Object instanceValue) {
		return instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AnydslPackage getAnydslPackage() {
		return (AnydslPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AnydslPackage getPackage() {
		return AnydslPackage.eINSTANCE;
	}

} // AnydslFactoryImpl
