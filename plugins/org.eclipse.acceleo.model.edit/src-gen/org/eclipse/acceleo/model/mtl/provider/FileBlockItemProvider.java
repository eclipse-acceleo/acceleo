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

import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.ocl.ecore.EcoreFactory;

/**
 * This is the item provider adapter for a {@link org.eclipse.acceleo.model.mtl.FileBlock} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class FileBlockItemProvider extends BlockItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public FileBlockItemProvider(AdapterFactory adapterFactory) {
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

			addOpenModePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Open Mode feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addOpenModePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(
						((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
						getResourceLocator(),
						getString("_UI_FileBlock_openMode_feature"), //$NON-NLS-1$
						getString(
								"_UI_PropertyDescriptor_description", "_UI_FileBlock_openMode_feature", "_UI_FileBlock_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						MtlPackage.Literals.FILE_BLOCK__OPEN_MODE, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
			childrenFeatures.add(MtlPackage.Literals.FILE_BLOCK__FILE_URL);
			childrenFeatures.add(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID);
			childrenFeatures.add(MtlPackage.Literals.FILE_BLOCK__CHARSET);
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
	 * This returns FileBlock.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/FileBlock")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((FileBlock)object).getName();
		return label == null || label.length() == 0 ? getString("_UI_FileBlock_type") : //$NON-NLS-1$
				getString("_UI_FileBlock_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(FileBlock.class)) {
			case MtlPackage.FILE_BLOCK__OPEN_MODE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false,
						true));
				return;
			case MtlPackage.FILE_BLOCK__FILE_URL:
			case MtlPackage.FILE_BLOCK__UNIQ_ID:
			case MtlPackage.FILE_BLOCK__CHARSET:
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

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__FILE_URL,
				EcoreFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__UNIQ_ID,
				EcoreFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.FILE_BLOCK__CHARSET,
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
				|| childFeature == MtlPackage.Literals.FILE_BLOCK__FILE_URL
				|| childFeature == MtlPackage.Literals.FILE_BLOCK__UNIQ_ID
				|| childFeature == MtlPackage.Literals.FILE_BLOCK__CHARSET;

		if (qualify) {
			return getString(
					"_UI_CreateChild_text2", //$NON-NLS-1$
					new Object[] {getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
