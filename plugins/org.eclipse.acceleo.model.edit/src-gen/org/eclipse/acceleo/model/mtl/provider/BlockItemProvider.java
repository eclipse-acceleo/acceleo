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

import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.ocl.ecore.EcoreFactory;

/**
 * This is the item provider adapter for a {@link org.eclipse.acceleo.model.mtl.Block} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class BlockItemProvider extends TemplateExpressionItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public BlockItemProvider(AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
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
			childrenFeatures.add(MtlPackage.Literals.BLOCK__INIT);
			childrenFeatures.add(MtlPackage.Literals.BLOCK__BODY);
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
	 * This returns Block.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Block")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Block)object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Block_type") : //$NON-NLS-1$
				getString("_UI_Block_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(Block.class)) {
			case MtlPackage.BLOCK__INIT:
			case MtlPackage.BLOCK__BODY:
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

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__INIT, MtlFactory.eINSTANCE
				.createInitSection()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, MtlFactory.eINSTANCE
				.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.BLOCK__BODY, EcoreFactory.eINSTANCE
				.createVariableExp()));
	}

}
