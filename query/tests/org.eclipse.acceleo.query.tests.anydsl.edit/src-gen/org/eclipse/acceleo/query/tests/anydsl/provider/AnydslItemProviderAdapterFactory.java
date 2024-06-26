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
package org.eclipse.acceleo.query.tests.anydsl.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.acceleo.query.tests.anydsl.util.AnydslAdapterFactory;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers. The adapters
 * generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged
 * fireNotifyChanged}. The adapters also support Eclipse property sheets. Note that most of the adapters are
 * shared among multiple instances. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class AnydslItemProviderAdapterFactory extends AnydslAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Collection<Object> supportedTypes = new ArrayList<Object>();

	/**
	 * This constructs an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AnydslItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.acceleo.query.tests.anydsl.World}
	 * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected WorldItemProvider worldItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.World}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createWorldAdapter() {
		if (worldItemProvider == null) {
			worldItemProvider = new WorldItemProvider(this);
		}

		return worldItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Producer} instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProducerItemProvider producerItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.Producer}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createProducerAdapter() {
		if (producerItemProvider == null) {
			producerItemProvider = new ProducerItemProvider(this);
		}

		return producerItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.acceleo.query.tests.anydsl.Adress}
	 * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected AdressItemProvider adressItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.Adress}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createAdressAdapter() {
		if (adressItemProvider == null) {
			adressItemProvider = new AdressItemProvider(this);
		}

		return adressItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.acceleo.query.tests.anydsl.ProductionCompany} instances. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ProductionCompanyItemProvider productionCompanyItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.ProductionCompany}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createProductionCompanyAdapter() {
		if (productionCompanyItemProvider == null) {
			productionCompanyItemProvider = new ProductionCompanyItemProvider(this);
		}

		return productionCompanyItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all
	 * {@link org.eclipse.acceleo.query.tests.anydsl.Restaurant} instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected RestaurantItemProvider restaurantItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.Restaurant}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createRestaurantAdapter() {
		if (restaurantItemProvider == null) {
			restaurantItemProvider = new RestaurantItemProvider(this);
		}

		return restaurantItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.acceleo.query.tests.anydsl.Chef}
	 * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ChefItemProvider chefItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.Chef}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createChefAdapter() {
		if (chefItemProvider == null) {
			chefItemProvider = new ChefItemProvider(this);
		}

		return chefItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.acceleo.query.tests.anydsl.Recipe}
	 * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected RecipeItemProvider recipeItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.Recipe}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createRecipeAdapter() {
		if (recipeItemProvider == null) {
			recipeItemProvider = new RecipeItemProvider(this);
		}

		return recipeItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.acceleo.query.tests.anydsl.Food}
	 * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected FoodItemProvider foodItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.Food}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createFoodAdapter() {
		if (foodItemProvider == null) {
			foodItemProvider = new FoodItemProvider(this);
		}

		return foodItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.acceleo.query.tests.anydsl.Plant}
	 * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PlantItemProvider plantItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.Plant}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createPlantAdapter() {
		if (plantItemProvider == null) {
			plantItemProvider = new PlantItemProvider(this);
		}

		return plantItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.acceleo.query.tests.anydsl.Animal}
	 * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected AnimalItemProvider animalItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.Animal}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createAnimalAdapter() {
		if (animalItemProvider == null) {
			animalItemProvider = new AnimalItemProvider(this);
		}

		return animalItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link java.util.Map.Entry} instances. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected EStringToRecipeMapItemProvider eStringToRecipeMapItemProvider;

	/**
	 * This creates an adapter for a {@link java.util.Map.Entry}. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter createEStringToRecipeMapAdapter() {
		if (eStringToRecipeMapItemProvider == null) {
			eStringToRecipeMapItemProvider = new EStringToRecipeMapItemProvider(this);
		}

		return eStringToRecipeMapItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public void dispose() {
		if (worldItemProvider != null)
			worldItemProvider.dispose();
		if (producerItemProvider != null)
			producerItemProvider.dispose();
		if (adressItemProvider != null)
			adressItemProvider.dispose();
		if (productionCompanyItemProvider != null)
			productionCompanyItemProvider.dispose();
		if (restaurantItemProvider != null)
			restaurantItemProvider.dispose();
		if (chefItemProvider != null)
			chefItemProvider.dispose();
		if (recipeItemProvider != null)
			recipeItemProvider.dispose();
		if (foodItemProvider != null)
			foodItemProvider.dispose();
		if (plantItemProvider != null)
			plantItemProvider.dispose();
		if (animalItemProvider != null)
			animalItemProvider.dispose();
		if (eStringToRecipeMapItemProvider != null)
			eStringToRecipeMapItemProvider.dispose();
	}

}
