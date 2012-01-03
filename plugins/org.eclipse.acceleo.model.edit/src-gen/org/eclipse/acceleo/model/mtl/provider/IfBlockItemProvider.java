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

import org.eclipse.acceleo.model.mtl.IfBlock;
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
 * This is the item provider adapter for a {@link org.eclipse.acceleo.model.mtl.IfBlock} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class IfBlockItemProvider extends BlockItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public IfBlockItemProvider(AdapterFactory adapterFactory) {
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
			childrenFeatures.add(MtlPackage.Literals.IF_BLOCK__IF_EXPR);
			childrenFeatures.add(MtlPackage.Literals.IF_BLOCK__ELSE);
			childrenFeatures.add(MtlPackage.Literals.IF_BLOCK__ELSE_IF);
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
	 * This returns IfBlock.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/IfBlock")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((IfBlock)object).getName();
		return label == null || label.length() == 0 ? getString("_UI_IfBlock_type") : //$NON-NLS-1$
				getString("_UI_IfBlock_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(IfBlock.class)) {
			case MtlPackage.IF_BLOCK__IF_EXPR:
			case MtlPackage.IF_BLOCK__ELSE:
			case MtlPackage.IF_BLOCK__ELSE_IF:
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

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__IF_EXPR,
				EcoreFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE, MtlFactory.eINSTANCE
				.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE, MtlFactory.eINSTANCE
				.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE, MtlFactory.eINSTANCE
				.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE, MtlFactory.eINSTANCE
				.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE, MtlFactory.eINSTANCE
				.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE, MtlFactory.eINSTANCE
				.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE, MtlFactory.eINSTANCE
				.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE, MtlFactory.eINSTANCE
				.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE, MtlFactory.eINSTANCE
				.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.IF_BLOCK__ELSE_IF,
				MtlFactory.eINSTANCE.createIfBlock()));
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
				|| childFeature == MtlPackage.Literals.IF_BLOCK__IF_EXPR
				|| childFeature == MtlPackage.Literals.IF_BLOCK__ELSE
				|| childFeature == MtlPackage.Literals.IF_BLOCK__ELSE_IF;

		if (qualify) {
			return getString(
					"_UI_CreateChild_text2", //$NON-NLS-1$
					new Object[] {getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
