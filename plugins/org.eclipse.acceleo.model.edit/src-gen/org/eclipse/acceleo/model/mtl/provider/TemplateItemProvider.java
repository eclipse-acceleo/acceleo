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
import org.eclipse.acceleo.model.mtl.Template;
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
 * This is the item provider adapter for a {@link org.eclipse.acceleo.model.mtl.Template} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class TemplateItemProvider extends BlockItemProvider implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public TemplateItemProvider(AdapterFactory adapterFactory) {
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

			addVisibilityPropertyDescriptor(object);
			addOverridesPropertyDescriptor(object);
			addMainPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Visibility feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addVisibilityPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(
						((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
						getResourceLocator(),
						getString("_UI_ModuleElement_visibility_feature"), //$NON-NLS-1$
						getString(
								"_UI_PropertyDescriptor_description", "_UI_ModuleElement_visibility_feature", "_UI_ModuleElement_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						MtlPackage.Literals.MODULE_ELEMENT__VISIBILITY, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Overrides feature. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addOverridesPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory)
						.getRootAdapterFactory(),
						getResourceLocator(),
						getString("_UI_Template_overrides_feature"), //$NON-NLS-1$
						getString(
								"_UI_PropertyDescriptor_description", "_UI_Template_overrides_feature", "_UI_Template_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						MtlPackage.Literals.TEMPLATE__OVERRIDES, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Main feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unused")
	protected void addMainPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(
						((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
						getResourceLocator(),
						getString("_UI_Template_main_feature"), //$NON-NLS-1$
						getString(
								"_UI_PropertyDescriptor_description", "_UI_Template_main_feature", "_UI_Template_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						MtlPackage.Literals.TEMPLATE__MAIN, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
			childrenFeatures.add(MtlPackage.Literals.TEMPLATE__PARAMETER);
			childrenFeatures.add(MtlPackage.Literals.TEMPLATE__GUARD);
			childrenFeatures.add(MtlPackage.Literals.TEMPLATE__POST);
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
	 * This returns Template.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Template")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((Template)object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Template_type") : //$NON-NLS-1$
				getString("_UI_Template_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(Template.class)) {
			case MtlPackage.TEMPLATE__VISIBILITY:
			case MtlPackage.TEMPLATE__MAIN:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false,
						true));
				return;
			case MtlPackage.TEMPLATE__PARAMETER:
			case MtlPackage.TEMPLATE__GUARD:
			case MtlPackage.TEMPLATE__POST:
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

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__PARAMETER,
				EcoreFactory.eINSTANCE.createVariable()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				MtlFactory.eINSTANCE.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__GUARD,
				EcoreFactory.eINSTANCE.createVariableExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createTemplateExpression()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createTemplate()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createTemplateInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createQueryInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createProtectedAreaBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createForBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createIfBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createLetBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createFileBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createTraceBlock()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createMacro()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST, MtlFactory.eINSTANCE
				.createMacroInvocation()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createAssociationClassCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createBooleanLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createCollectionLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createEnumLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createIfExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createIntegerLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createUnlimitedNaturalLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createInvalidLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createIterateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createIteratorExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createLetExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createMessageExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createNullLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createOperationCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createPropertyCallExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createRealLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createStateExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createStringLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createTupleLiteralExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createTypeExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
				EcoreFactory.eINSTANCE.createUnspecifiedValueExp()));

		newChildDescriptors.add(createChildParameter(MtlPackage.Literals.TEMPLATE__POST,
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
				|| childFeature == MtlPackage.Literals.TEMPLATE__GUARD
				|| childFeature == MtlPackage.Literals.TEMPLATE__POST;

		if (qualify) {
			return getString(
					"_UI_CreateChild_text2", //$NON-NLS-1$
					new Object[] {getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
