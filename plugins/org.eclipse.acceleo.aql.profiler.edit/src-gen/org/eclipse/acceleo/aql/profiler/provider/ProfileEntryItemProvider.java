/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.provider;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfilerFactory;
import org.eclipse.acceleo.aql.profiler.ProfilerPackage;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
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
 * This is the item provider adapter for a {@link org.eclipse.acceleo.aql.profiler.ProfileEntry} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProfileEntryItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public ProfileEntryItemProvider(AdapterFactory adapterFactory) {
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

			addDurationPropertyDescriptor(object);
			addCountPropertyDescriptor(object);
			addPercentagePropertyDescriptor(object);
			addCreateTimePropertyDescriptor(object);
			addMonitoredPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Duration feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addDurationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ProfileEntry_duration_feature"), getString( //$NON-NLS-1$
						"_UI_PropertyDescriptor_description", "_UI_ProfileEntry_duration_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_ProfileEntry_type"), ProfilerPackage.Literals.PROFILE_ENTRY__DURATION, true, //$NON-NLS-1$
				false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Count feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addCountPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(), getString("_UI_ProfileEntry_count_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", "_UI_ProfileEntry_count_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_ProfileEntry_type"), ProfilerPackage.Literals.PROFILE_ENTRY__COUNT, true, false, //$NON-NLS-1$
				false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Percentage feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addPercentagePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ProfileEntry_percentage_feature"), getString( //$NON-NLS-1$
						"_UI_PropertyDescriptor_description", "_UI_ProfileEntry_percentage_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_ProfileEntry_type"), ProfilerPackage.Literals.PROFILE_ENTRY__PERCENTAGE, true, //$NON-NLS-1$
				false, false, ItemPropertyDescriptor.REAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Create Time feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addCreateTimePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ProfileEntry_createTime_feature"), getString( //$NON-NLS-1$
						"_UI_PropertyDescriptor_description", "_UI_ProfileEntry_createTime_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_ProfileEntry_type"), ProfilerPackage.Literals.PROFILE_ENTRY__CREATE_TIME, true, //$NON-NLS-1$
				false, false, ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Monitored feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addMonitoredPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
				.getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ProfileEntry_monitored_feature"), getString( //$NON-NLS-1$
						"_UI_PropertyDescriptor_description", "_UI_ProfileEntry_monitored_feature", //$NON-NLS-1$ //$NON-NLS-2$
						"_UI_ProfileEntry_type"), ProfilerPackage.Literals.PROFILE_ENTRY__MONITORED, true, //$NON-NLS-1$
				false, true, null, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for
	 * an {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand}
	 * or {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(ProfilerPackage.Literals.PROFILE_ENTRY__CALLEES);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns ProfileEntry.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		EObject monitored = ((ProfileEntry)object).getMonitored();

		return ProfilerEditPlugin.LABEL_PROVIDER.getImage(monitored);
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		final ProfileEntry profileEntry = (ProfileEntry)object;
		final EObject monitored = profileEntry.getMonitored();
		final NumberFormat format = new DecimalFormat();
		format.setMaximumIntegerDigits(3);
		format.setMaximumFractionDigits(2);

		return format.format(profileEntry.getPercentage()) + "% / " + profileEntry.getDuration() + "ms / " //$NON-NLS-1$ //$NON-NLS-2$
				+ profileEntry.getCount() + " times [" + ProfilerEditPlugin.LABEL_PROVIDER.getText(monitored) //$NON-NLS-1$
				+ "]"; //$NON-NLS-1$
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

		switch (notification.getFeatureID(ProfileEntry.class)) {
			case ProfilerPackage.PROFILE_ENTRY__DURATION:
			case ProfilerPackage.PROFILE_ENTRY__COUNT:
			case ProfilerPackage.PROFILE_ENTRY__PERCENTAGE:
			case ProfilerPackage.PROFILE_ENTRY__CREATE_TIME:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false,
						true));
				return;
			case ProfilerPackage.PROFILE_ENTRY__CALLEES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true,
						false));
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

		newChildDescriptors.add(createChildParameter(ProfilerPackage.Literals.PROFILE_ENTRY__CALLEES,
				ProfilerFactory.eINSTANCE.createProfileEntry()));

		newChildDescriptors.add(createChildParameter(ProfilerPackage.Literals.PROFILE_ENTRY__CALLEES,
				ProfilerFactory.eINSTANCE.createLoopProfileEntry()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return ProfilerEditPlugin.INSTANCE;
	}

}
