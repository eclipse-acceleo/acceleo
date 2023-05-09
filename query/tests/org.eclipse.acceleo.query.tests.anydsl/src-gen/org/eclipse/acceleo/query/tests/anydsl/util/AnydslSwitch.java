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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage
 * @generated
 */
public class AnydslSwitch<T> extends Switch<T> {
	/**
	 * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static AnydslPackage modelPackage;

	/**
	 * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AnydslSwitch() {
		if (modelPackage == null) {
			modelPackage = AnydslPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields
	 * that result. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case AnydslPackage.WORLD: {
				World world = (World)theEObject;
				T result = caseWorld(world);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.MULTI_NAMED_ELEMENT: {
				MultiNamedElement multiNamedElement = (MultiNamedElement)theEObject;
				T result = caseMultiNamedElement(multiNamedElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.NAMED_ELEMENT: {
				NamedElement namedElement = (NamedElement)theEObject;
				T result = caseNamedElement(namedElement);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.PRODUCER: {
				Producer producer = (Producer)theEObject;
				T result = caseProducer(producer);
				if (result == null)
					result = caseNamedElement(producer);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.ADRESS: {
				Adress adress = (Adress)theEObject;
				T result = caseAdress(adress);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.COMPANY: {
				Company company = (Company)theEObject;
				T result = caseCompany(company);
				if (result == null)
					result = caseNamedElement(company);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.PRODUCTION_COMPANY: {
				ProductionCompany productionCompany = (ProductionCompany)theEObject;
				T result = caseProductionCompany(productionCompany);
				if (result == null)
					result = caseCompany(productionCompany);
				if (result == null)
					result = caseNamedElement(productionCompany);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.RESTAURANT: {
				Restaurant restaurant = (Restaurant)theEObject;
				T result = caseRestaurant(restaurant);
				if (result == null)
					result = caseCompany(restaurant);
				if (result == null)
					result = caseNamedElement(restaurant);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.CHEF: {
				Chef chef = (Chef)theEObject;
				T result = caseChef(chef);
				if (result == null)
					result = caseNamedElement(chef);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.RECIPE: {
				Recipe recipe = (Recipe)theEObject;
				T result = caseRecipe(recipe);
				if (result == null)
					result = caseNamedElement(recipe);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.FOOD: {
				Food food = (Food)theEObject;
				T result = caseFood(food);
				if (result == null)
					result = caseNamedElement(food);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.SOURCE: {
				Source source = (Source)theEObject;
				T result = caseSource(source);
				if (result == null)
					result = caseMultiNamedElement(source);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.PLANT: {
				Plant plant = (Plant)theEObject;
				T result = casePlant(plant);
				if (result == null)
					result = caseSource(plant);
				if (result == null)
					result = caseMultiNamedElement(plant);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.ANIMAL: {
				Animal animal = (Animal)theEObject;
				T result = caseAnimal(animal);
				if (result == null)
					result = caseSource(animal);
				if (result == null)
					result = caseMultiNamedElement(animal);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			case AnydslPackage.ESTRING_TO_RECIPE_MAP: {
				@SuppressWarnings("unchecked")
				Map.Entry<String, Recipe> eStringToRecipeMap = (Map.Entry<String, Recipe>)theEObject;
				T result = caseEStringToRecipeMap(eStringToRecipeMap);
				if (result == null)
					result = defaultCase(theEObject);
				return result;
			}
			default:
				return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>World</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>World</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWorld(World object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Multi Named Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Multi Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMultiNamedElement(MultiNamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Named Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNamedElement(NamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Producer</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Producer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProducer(Producer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adress</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adress</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdress(Adress object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Company</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Company</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompany(Company object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Production Company</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Production Company</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProductionCompany(ProductionCompany object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Restaurant</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Restaurant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRestaurant(Restaurant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Chef</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Chef</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChef(Chef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Recipe</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Recipe</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRecipe(Recipe object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Food</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Food</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFood(Food object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Source</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSource(Source object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Plant</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Plant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlant(Plant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Animal</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Animal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnimal(Animal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EString To Recipe Map</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EString To Recipe Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEStringToRecipeMap(Map.Entry<String, Recipe> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch, but this
	 * is the last case anyway. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // AnydslSwitch
