/**
 * Copyright (c) 2008, 2023 Obeo.
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

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.ErrorVariable;
import org.eclipse.acceleo.query.ast.AstFactory;
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
 * This is the item provider adapter for a
 * {@link org.eclipse.acceleo.ErrorVariable} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ErrorVariableItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ErrorVariableItemProvider(AdapterFactory adapterFactory) {
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

			addTypePropertyDescriptor(object);
			addNamePropertyDescriptor(object);
			addMissingNamePropertyDescriptor(object);
			addMissingColonPropertyDescriptor(object);
			addMissingTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Type feature. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_TypedElement_type_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_TypedElement_type_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_TypedElement_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.TYPED_ELEMENT__TYPE, true, false, true,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Name feature. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_NamedElement_name_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_NamedElement_name_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_NamedElement_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.NAMED_ELEMENT__NAME, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Name feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingNamePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorVariable_missingName_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorVariable_missingName_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorVariable_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_VARIABLE__MISSING_NAME, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Colon feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingColonPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorVariable_missingColon_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorVariable_missingColon_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorVariable_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_VARIABLE__MISSING_COLON, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Type feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorVariable_missingType_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorVariable_missingType_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorVariable_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_VARIABLE__MISSING_TYPE, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
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
			childrenFeatures.add(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL);
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
	 * This returns ErrorVariable.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ErrorVariable")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ErrorVariable) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_ErrorVariable_type") : //$NON-NLS-1$
				getString("_UI_ErrorVariable_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ErrorVariable.class)) {
		case AcceleoPackage.ERROR_VARIABLE__NAME:
		case AcceleoPackage.ERROR_VARIABLE__MISSING_NAME:
		case AcceleoPackage.ERROR_VARIABLE__MISSING_COLON:
		case AcceleoPackage.ERROR_VARIABLE__MISSING_TYPE:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case AcceleoPackage.ERROR_VARIABLE__TYPE_AQL:
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

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createVarRef()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createCall()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createIntegerLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createRealLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createStringLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createBooleanLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createEnumLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createEClassifierTypeLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createClassTypeLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createTypeSetLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createCollectionTypeLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createLambda()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createNullLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createSetInExtensionLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createSequenceInExtensionLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createErrorExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createErrorTypeLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createErrorEClassifierTypeLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createErrorEnumLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createErrorCall()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createErrorVariableDeclaration()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createErrorStringLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createErrorConditional()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createErrorBinding()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createLet()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createConditional()));

		newChildDescriptors.add(
				createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL, AstFactory.eINSTANCE.createOr()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createAnd()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TYPED_ELEMENT__TYPE_AQL,
				AstFactory.eINSTANCE.createImplies()));
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
