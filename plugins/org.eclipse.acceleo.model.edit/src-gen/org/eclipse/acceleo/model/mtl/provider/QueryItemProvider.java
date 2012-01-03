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

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.Query;
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
 * This is the item provider adapter for a {@link org.eclipse.acceleo.model.mtl.Query} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class QueryItemProvider extends ModuleElementItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public QueryItemProvider(AdapterFactory adapterFactory) {
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
			childrenFeatures.add(MtlPackage.Literals.QUERY__PARAMETER);
			childrenFeatures.add(MtlPackage.Literals.QUERY__EXPRESSION);
			childrenFeatures.add(MtlPackage.Literals.QUERY__TYPE);
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
	 * This returns Query.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Query")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Query)object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Query_type") : //$NON-NLS-1$
				getString("_UI_Query_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(Query.class)) {
			case MtlPackage.QUERY__PARAMETER:
			case MtlPackage.QUERY__EXPRESSION:
			case MtlPackage.QUERY__TYPE:
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

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__PARAMETER,
				EcoreFactory.eINSTANCE.createVariable()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__EXPRESSION,
				EcoreFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE,
				org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEClass()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE,
				org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEDataType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE,
				org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEEnum()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createAnyType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createCollectionType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createBagType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createElementType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createInvalidType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createMessageType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createOrderedSetType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createPrimitiveType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createSequenceType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createSetType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createTupleType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createTypeType()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.QUERY__TYPE, EcoreFactory.eINSTANCE
				.createVoidType()));
	}

}
