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
package org.eclipse.acceleo.query.tests.anydsl.util;

import java.util.Map;

import org.eclipse.acceleo.query.tests.anydsl.Adress;
import org.eclipse.acceleo.query.tests.anydsl.Animal;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Chef;
import org.eclipse.acceleo.query.tests.anydsl.Company;
import org.eclipse.acceleo.query.tests.anydsl.Food;
import org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement;
import org.eclipse.acceleo.query.tests.anydsl.NamedElement;
import org.eclipse.acceleo.query.tests.anydsl.Plant;
import org.eclipse.acceleo.query.tests.anydsl.Producer;
import org.eclipse.acceleo.query.tests.anydsl.ProductionCompany;
import org.eclipse.acceleo.query.tests.anydsl.Recipe;
import org.eclipse.acceleo.query.tests.anydsl.Restaurant;
import org.eclipse.acceleo.query.tests.anydsl.Source;
import org.eclipse.acceleo.query.tests.anydsl.World;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage
 * @generated
 */
public class AnydslAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static AnydslPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AnydslAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = AnydslPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model. <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected AnydslSwitch<Adapter> modelSwitch = new AnydslSwitch<Adapter>() {
		@Override
		public Adapter caseWorld(World object) {
			return createWorldAdapter();
		}

		@Override
		public Adapter caseMultiNamedElement(MultiNamedElement object) {
			return createMultiNamedElementAdapter();
		}

		@Override
		public Adapter caseNamedElement(NamedElement object) {
			return createNamedElementAdapter();
		}

		@Override
		public Adapter caseProducer(Producer object) {
			return createProducerAdapter();
		}

		@Override
		public Adapter caseAdress(Adress object) {
			return createAdressAdapter();
		}

		@Override
		public Adapter caseCompany(Company object) {
			return createCompanyAdapter();
		}

		@Override
		public Adapter caseProductionCompany(ProductionCompany object) {
			return createProductionCompanyAdapter();
		}

		@Override
		public Adapter caseRestaurant(Restaurant object) {
			return createRestaurantAdapter();
		}

		@Override
		public Adapter caseChef(Chef object) {
			return createChefAdapter();
		}

		@Override
		public Adapter caseRecipe(Recipe object) {
			return createRecipeAdapter();
		}

		@Override
		public Adapter caseFood(Food object) {
			return createFoodAdapter();
		}

		@Override
		public Adapter caseSource(Source object) {
			return createSourceAdapter();
		}

		@Override
		public Adapter casePlant(Plant object) {
			return createPlantAdapter();
		}

		@Override
		public Adapter caseAnimal(Animal object) {
			return createAnimalAdapter();
		}

		@Override
		public Adapter caseEStringToRecipeMap(Map.Entry<String, Recipe> object) {
			return createEStringToRecipeMapAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.World
	 * <em>World</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.World
	 * @generated
	 */
	public Adapter createWorldAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement <em>Multi Named Element</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.MultiNamedElement
	 * @generated
	 */
	public Adapter createMultiNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.NamedElement <em>Named Element</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.NamedElement
	 * @generated
	 */
	public Adapter createNamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Producer
	 * <em>Producer</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Producer
	 * @generated
	 */
	public Adapter createProducerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Adress
	 * <em>Adress</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Adress
	 * @generated
	 */
	public Adapter createAdressAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Company
	 * <em>Company</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Company
	 * @generated
	 */
	public Adapter createCompanyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.query.tests.anydsl.ProductionCompany <em>Production Company</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.ProductionCompany
	 * @generated
	 */
	public Adapter createProductionCompanyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Restaurant
	 * <em>Restaurant</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Restaurant
	 * @generated
	 */
	public Adapter createRestaurantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Chef
	 * <em>Chef</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Chef
	 * @generated
	 */
	public Adapter createChefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Recipe
	 * <em>Recipe</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Recipe
	 * @generated
	 */
	public Adapter createRecipeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Food
	 * <em>Food</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Food
	 * @generated
	 */
	public Adapter createFoodAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Source
	 * <em>Source</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Source
	 * @generated
	 */
	public Adapter createSourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Plant
	 * <em>Plant</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Plant
	 * @generated
	 */
	public Adapter createPlantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.query.tests.anydsl.Animal
	 * <em>Animal</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.query.tests.anydsl.Animal
	 * @generated
	 */
	public Adapter createAnimalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry
	 * <em>EString To Recipe Map</em>}'. <!-- begin-user-doc --> This default implementation returns null so
	 * that we can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases
	 * anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createEStringToRecipeMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns
	 * null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // AnydslAdapterFactory
