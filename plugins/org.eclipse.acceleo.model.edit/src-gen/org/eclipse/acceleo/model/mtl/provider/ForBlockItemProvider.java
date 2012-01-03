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

import org.eclipse.acceleo.model.mtl.ForBlock;
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
 * This is the item provider adapter for a {@link org.eclipse.acceleo.model.mtl.ForBlock} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ForBlockItemProvider extends BlockItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public ForBlockItemProvider(AdapterFactory adapterFactory) {
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
			childrenFeatures.add(MtlPackage.Literals.FOR_BLOCK__LOOP_VARIABLE);
			childrenFeatures.add(MtlPackage.Literals.FOR_BLOCK__ITER_SET);
			childrenFeatures.add(MtlPackage.Literals.FOR_BLOCK__BEFORE);
			childrenFeatures.add(MtlPackage.Literals.FOR_BLOCK__EACH);
			childrenFeatures.add(MtlPackage.Literals.FOR_BLOCK__AFTER);
			childrenFeatures.add(MtlPackage.Literals.FOR_BLOCK__GUARD);
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
	 * This returns ForBlock.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ForBlock")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ForBlock)object).getName();
		return label == null || label.length() == 0 ? getString("_UI_ForBlock_type") : //$NON-NLS-1$
				getString("_UI_ForBlock_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ForBlock.class)) {
			case MtlPackage.FOR_BLOCK__LOOP_VARIABLE:
			case MtlPackage.FOR_BLOCK__ITER_SET:
			case MtlPackage.FOR_BLOCK__BEFORE:
			case MtlPackage.FOR_BLOCK__EACH:
			case MtlPackage.FOR_BLOCK__AFTER:
			case MtlPackage.FOR_BLOCK__GUARD:
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

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__LOOP_VARIABLE,
				EcoreFactory.eINSTANCE.createVariable()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__ITER_SET,
				EcoreFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__BEFORE,
				EcoreFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__EACH,
				EcoreFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__AFTER,
				EcoreFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FOR_BLOCK__GUARD,
				EcoreFactory.eINSTANCE.createVariableExp()));
	}

	/**
	 * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify = childFeature == MtlPackage.Literals.BLOCK__BODY
				|| childFeature == MtlPackage.Literals.FOR_BLOCK__ITER_SET
				|| childFeature == MtlPackage.Literals.FOR_BLOCK__BEFORE
				|| childFeature == MtlPackage.Literals.FOR_BLOCK__EACH
				|| childFeature == MtlPackage.Literals.FOR_BLOCK__AFTER
				|| childFeature == MtlPackage.Literals.FOR_BLOCK__GUARD;

		if (qualify) {
			return getString(
					"_UI_CreateChild_text2", //$NON-NLS-1$
					new Object[] {getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
