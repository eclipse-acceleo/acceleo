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
package org.eclipse.acceleo.model.mtl.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.provider.EPackageItemProvider;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.ocl.ecore.EcoreFactory;

/**
 * This is the item provider adapter for a {@link org.eclipse.acceleo.model.mtl.Module} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ModuleItemProvider extends EPackageItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public ModuleItemProvider(AdapterFactory adapterFactory) {
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

			addExtendsPropertyDescriptor(object);
			addImportsPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Extends feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addExtendsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
						.getRootAdapterFactory(),
						getResourceLocator(),
						getString("_UI_Module_extends_feature"), //$NON-NLS-1$
						getString(
								"_UI_PropertyDescriptor_description", "_UI_Module_extends_feature", "_UI_Module_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						MtlPackage.Literals.MODULE__EXTENDS, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Imports feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addImportsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
						.getRootAdapterFactory(),
						getResourceLocator(),
						getString("_UI_Module_imports_feature"), //$NON-NLS-1$
						getString(
								"_UI_PropertyDescriptor_description", "_UI_Module_imports_feature", "_UI_Module_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						MtlPackage.Literals.MODULE__IMPORTS, true, false, true, null, null, null));
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
			childrenFeatures.add(MtlPackage.Literals.MODULE__INPUT);
			childrenFeatures.add(MtlPackage.Literals.MODULE__OWNED_MODULE_ELEMENT);
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
	 * This returns Module.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Module")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Module)object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Module_type") : //$NON-NLS-1$
				getString("_UI_Module_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(Module.class)) {
			case MtlPackage.MODULE__INPUT:
			case MtlPackage.MODULE__OWNED_MODULE_ELEMENT:
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

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createAnyType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createCollectionType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createBagType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createElementType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createInvalidType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createMessageType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createOrderedSetType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createPrimitiveType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createSequenceType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createSetType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createTupleType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createTypeType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ECLASSIFIERS,
				EcoreFactory.eINSTANCE.createVoidType()));

		newChildDescriptors.add(createChildParameter(EcorePackage.Literals.EPACKAGE__ESUBPACKAGES,
				MtlFactory.eINSTANCE.createModule()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.MODULE__INPUT, MtlFactory.eINSTANCE
				.createTypedModel()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.MODULE__OWNED_MODULE_ELEMENT,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.MODULE__OWNED_MODULE_ELEMENT,
				MtlFactory.eINSTANCE.createQuery()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.MODULE__OWNED_MODULE_ELEMENT,
				MtlFactory.eINSTANCE.createMacro()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return MtlEditPlugin.INSTANCE;
	}

}
