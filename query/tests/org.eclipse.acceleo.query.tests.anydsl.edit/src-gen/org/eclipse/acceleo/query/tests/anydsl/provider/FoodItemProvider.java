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

import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Food;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.eclipse.acceleo.query.tests.anydsl.Food} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class FoodItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public FoodItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addNamePropertyDescriptor(object);
			addColorPropertyDescriptor(object);
			addCaliberPropertyDescriptor(object);
			addRelatedFoodsPropertyDescriptor(object);
			addGroupPropertyDescriptor(object);
			addLabelPropertyDescriptor(object);
			addSourcePropertyDescriptor(object);
			addProducersPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Name feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(), getString("_UI_NamedElement_name_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_NamedElement_name_feature",
						"_UI_NamedElement_type"), AnydslPackage.Literals.NAMED_ELEMENT__NAME, true, false,
				false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Color feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addColorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(), getString("_UI_Food_color_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Food_color_feature", "_UI_Food_type"),
				AnydslPackage.Literals.FOOD__COLOR, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Caliber feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addCaliberPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(), getString("_UI_Food_caliber_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Food_caliber_feature", "_UI_Food_type"),
				AnydslPackage.Literals.FOOD__CALIBER, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Related Foods feature. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addRelatedFoodsPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(), getString("_UI_Food_relatedFoods_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Food_relatedFoods_feature",
						"_UI_Food_type"), AnydslPackage.Literals.FOOD__RELATED_FOODS, true, false, true,
				null, null, null));
	}

	/**
	 * This adds a property descriptor for the Group feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addGroupPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(), getString("_UI_Food_group_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Food_group_feature", "_UI_Food_type"),
				AnydslPackage.Literals.FOOD__GROUP, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Label feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addLabelPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(), getString("_UI_Food_label_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Food_label_feature", "_UI_Food_type"),
				AnydslPackage.Literals.FOOD__LABEL, true, false, false,
				ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Source feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addSourcePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(), getString("_UI_Food_source_feature"),
				getString("_UI_PropertyDescriptor_description", "_UI_Food_source_feature", "_UI_Food_type"),
				AnydslPackage.Literals.FOOD__SOURCE, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Producers feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	protected void addProducersPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
						.getRootAdapterFactory(), getResourceLocator(),
						getString("_UI_Food_producers_feature"), getString(
								"_UI_PropertyDescriptor_description", "_UI_Food_producers_feature",
								"_UI_Food_type"), AnydslPackage.Literals.FOOD__PRODUCERS, true, false, true,
						null, null, null));
	}

	/**
	 * This returns Food.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Food"));
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Food)object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Food_type") : getString("_UI_Food_type")
				+ " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached children and
	 * by creating a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Food.class)) {
			case AnydslPackage.FOOD__NAME:
			case AnydslPackage.FOOD__COLOR:
			case AnydslPackage.FOOD__CALIBER:
			case AnydslPackage.FOOD__GROUP:
			case AnydslPackage.FOOD__LABEL:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false,
						true));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be
	 * created under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);
	}

	/**
	 * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return AnydslEditPlugin.INSTANCE;
	}

}
