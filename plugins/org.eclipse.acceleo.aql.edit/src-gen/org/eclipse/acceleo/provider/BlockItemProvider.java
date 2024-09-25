/**
 * Copyright (c) 2008, 2024 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.AcceleoFactory;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Block;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
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
 * This is the item provider adapter for a {@link org.eclipse.acceleo.Block}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class BlockItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BlockItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addInlinedPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Inlined feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addInlinedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Block_inlined_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Block_inlined_feature", "_UI_Block_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						AcceleoPackage.Literals.BLOCK__INLINED, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an
	 * appropriate feature for an {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(AcceleoPackage.Literals.BLOCK__STATEMENTS);
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
		// Check the type of the specified child object and return the proper feature to
		// use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns Block.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Block")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		Block block = (Block) object;
		return getString("_UI_Block_type") + " " + block.isInlined(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update
	 * any cached children and by creating a viewer notification, which it passes to
	 * {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Block.class)) {
		case AcceleoPackage.BLOCK__INLINED:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case AcceleoPackage.BLOCK__STATEMENTS:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing
	 * the children that can be created under this object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createComment()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createBlockComment()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorComment()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorBlockComment()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createModuleDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorModuleDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createModuleElementDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorModuleElementDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createParameterDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createLeafStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createExpressionStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorExpressionStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createProtectedArea()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorProtectedArea()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createForStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorForStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createIfStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorIfStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createLetStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorLetStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createFileStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorFileStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createTextStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createNewLineStatement()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.BLOCK__STATEMENTS,
				AcceleoFactory.eINSTANCE.createErrorMargin()));
	}

	/**
	 * Return the resource locator for this item provider's resources. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return AcceleoEditPlugin.INSTANCE;
	}

}
