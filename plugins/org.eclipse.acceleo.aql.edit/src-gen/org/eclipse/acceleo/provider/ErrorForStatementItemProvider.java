/**
 * Copyright (c) 2008, 2021 Obeo.
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
import org.eclipse.acceleo.ErrorForStatement;
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
 * {@link org.eclipse.acceleo.ErrorForStatement} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ErrorForStatementItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ErrorForStatementItemProvider(AdapterFactory adapterFactory) {
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

			addMissingOpenParenthesisPropertyDescriptor(object);
			addMissingBindingPropertyDescriptor(object);
			addMissingCloseParenthesisPropertyDescriptor(object);
			addMissingSeparatorCloseParenthesisPropertyDescriptor(object);
			addMissingEndHeaderPropertyDescriptor(object);
			addMissingEndPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Missing Open Parenthesis feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingOpenParenthesisPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorForStatement_missingOpenParenthesis_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorForStatement_missingOpenParenthesis_feature", "_UI_ErrorForStatement_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_FOR_STATEMENT__MISSING_OPEN_PARENTHESIS, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Binding feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingBindingPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorForStatement_missingBinding_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorForStatement_missingBinding_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorForStatement_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_FOR_STATEMENT__MISSING_BINDING, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Close Parenthesis feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingCloseParenthesisPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorForStatement_missingCloseParenthesis_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorForStatement_missingCloseParenthesis_feature", "_UI_ErrorForStatement_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_FOR_STATEMENT__MISSING_CLOSE_PARENTHESIS, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Separator Close Parenthesis
	 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingSeparatorCloseParenthesisPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ErrorForStatement_missingSeparatorCloseParenthesis_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
						"_UI_ErrorForStatement_missingSeparatorCloseParenthesis_feature", "_UI_ErrorForStatement_type"), //$NON-NLS-1$ //$NON-NLS-2$
				AcceleoPackage.Literals.ERROR_FOR_STATEMENT__MISSING_SEPARATOR_CLOSE_PARENTHESIS, true, false, false,
				ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing End Header feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingEndHeaderPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorForStatement_missingEndHeader_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorForStatement_missingEndHeader_feature", "_UI_ErrorForStatement_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_FOR_STATEMENT__MISSING_END_HEADER, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing End feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingEndPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorForStatement_missingEnd_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorForStatement_missingEnd_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorForStatement_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_FOR_STATEMENT__MISSING_END, true, false, false,
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
			childrenFeatures.add(AcceleoPackage.Literals.FOR_STATEMENT__BINDING);
			childrenFeatures.add(AcceleoPackage.Literals.FOR_STATEMENT__SEPARATOR);
			childrenFeatures.add(AcceleoPackage.Literals.FOR_STATEMENT__BODY);
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
	 * This returns ErrorForStatement.gif. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ErrorForStatement")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		ErrorForStatement errorForStatement = (ErrorForStatement) object;
		return getString("_UI_ErrorForStatement_type") + " " + errorForStatement.getMissingOpenParenthesis(); //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ErrorForStatement.class)) {
		case AcceleoPackage.ERROR_FOR_STATEMENT__MISSING_OPEN_PARENTHESIS:
		case AcceleoPackage.ERROR_FOR_STATEMENT__MISSING_BINDING:
		case AcceleoPackage.ERROR_FOR_STATEMENT__MISSING_CLOSE_PARENTHESIS:
		case AcceleoPackage.ERROR_FOR_STATEMENT__MISSING_SEPARATOR_CLOSE_PARENTHESIS:
		case AcceleoPackage.ERROR_FOR_STATEMENT__MISSING_END_HEADER:
		case AcceleoPackage.ERROR_FOR_STATEMENT__MISSING_END:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case AcceleoPackage.ERROR_FOR_STATEMENT__BINDING:
		case AcceleoPackage.ERROR_FOR_STATEMENT__SEPARATOR:
		case AcceleoPackage.ERROR_FOR_STATEMENT__BODY:
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

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FOR_STATEMENT__BINDING,
				AcceleoFactory.eINSTANCE.createBinding()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FOR_STATEMENT__BINDING,
				AcceleoFactory.eINSTANCE.createErrorBinding()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FOR_STATEMENT__SEPARATOR,
				AcceleoFactory.eINSTANCE.createExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FOR_STATEMENT__SEPARATOR,
				AcceleoFactory.eINSTANCE.createErrorExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FOR_STATEMENT__BODY,
				AcceleoFactory.eINSTANCE.createBlock()));
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
