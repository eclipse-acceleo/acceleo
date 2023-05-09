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
import org.eclipse.acceleo.ErrorTemplate;
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
 * {@link org.eclipse.acceleo.ErrorTemplate} object. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ErrorTemplateItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ErrorTemplateItemProvider(AdapterFactory adapterFactory) {
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

			addDeprecatedPropertyDescriptor(object);
			addNamePropertyDescriptor(object);
			addMainPropertyDescriptor(object);
			addVisibilityPropertyDescriptor(object);
			addMissingVisibilityPropertyDescriptor(object);
			addMissingNamePropertyDescriptor(object);
			addMissingOpenParenthesisPropertyDescriptor(object);
			addMissingParametersPropertyDescriptor(object);
			addMissingCloseParenthesisPropertyDescriptor(object);
			addMissingGuardOpenParenthesisPropertyDescriptor(object);
			addMissingGuardCloseParenthesisPropertyDescriptor(object);
			addMissingPostCloseParenthesisPropertyDescriptor(object);
			addMissingEndHeaderPropertyDescriptor(object);
			addMissingEndPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Deprecated feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addDeprecatedPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_DocumentedElement_deprecated_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_DocumentedElement_deprecated_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_DocumentedElement_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.DOCUMENTED_ELEMENT__DEPRECATED, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
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
	 * This adds a property descriptor for the Main feature. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMainPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Template_main_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Template_main_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_Template_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.TEMPLATE__MAIN, true, false, false,
						ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Visibility feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addVisibilityPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Template_visibility_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Template_visibility_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_Template_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.TEMPLATE__VISIBILITY, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Visibility feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingVisibilityPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorTemplate_missingVisibility_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorTemplate_missingVisibility_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorTemplate_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_VISIBILITY, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
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
						getResourceLocator(), getString("_UI_ErrorTemplate_missingName_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorTemplate_missingName_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorTemplate_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_NAME, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
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
						getResourceLocator(), getString("_UI_ErrorTemplate_missingOpenParenthesis_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorTemplate_missingOpenParenthesis_feature", "_UI_ErrorTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Parameters feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingParametersPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorTemplate_missingParameters_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorTemplate_missingParameters_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorTemplate_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_PARAMETERS, true, false, false,
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
						getResourceLocator(), getString("_UI_ErrorTemplate_missingCloseParenthesis_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorTemplate_missingCloseParenthesis_feature", "_UI_ErrorTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Guard Open Parenthesis
	 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingGuardOpenParenthesisPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorTemplate_missingGuardOpenParenthesis_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorTemplate_missingGuardOpenParenthesis_feature", "_UI_ErrorTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Guard Close Parenthesis
	 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingGuardCloseParenthesisPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorTemplate_missingGuardCloseParenthesis_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorTemplate_missingGuardCloseParenthesis_feature", "_UI_ErrorTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Post Close Parenthesis
	 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingPostCloseParenthesisPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorTemplate_missingPostCloseParenthesis_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorTemplate_missingPostCloseParenthesis_feature", "_UI_ErrorTemplate_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS, true, false, false,
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
						getResourceLocator(), getString("_UI_ErrorTemplate_missingEndHeader_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorTemplate_missingEndHeader_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorTemplate_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_END_HEADER, true, false, false,
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
						getResourceLocator(), getString("_UI_ErrorTemplate_missingEnd_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorTemplate_missingEnd_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorTemplate_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_TEMPLATE__MISSING_END, true, false, false,
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
			childrenFeatures.add(AcceleoPackage.Literals.DOCUMENTED_ELEMENT__DOCUMENTATION);
			childrenFeatures.add(AcceleoPackage.Literals.TEMPLATE__PARAMETERS);
			childrenFeatures.add(AcceleoPackage.Literals.TEMPLATE__GUARD);
			childrenFeatures.add(AcceleoPackage.Literals.TEMPLATE__POST);
			childrenFeatures.add(AcceleoPackage.Literals.TEMPLATE__BODY);
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
	 * This returns ErrorTemplate.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ErrorTemplate")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((ErrorTemplate) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_ErrorTemplate_type") : //$NON-NLS-1$
				getString("_UI_ErrorTemplate_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ErrorTemplate.class)) {
		case AcceleoPackage.ERROR_TEMPLATE__DEPRECATED:
		case AcceleoPackage.ERROR_TEMPLATE__NAME:
		case AcceleoPackage.ERROR_TEMPLATE__MAIN:
		case AcceleoPackage.ERROR_TEMPLATE__VISIBILITY:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_VISIBILITY:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_NAME:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_OPEN_PARENTHESIS:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_PARAMETERS:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_CLOSE_PARENTHESIS:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_OPEN_PARENTHESIS:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_GUARD_CLOSE_PARENTHESIS:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_POST_CLOSE_PARENTHESIS:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_END_HEADER:
		case AcceleoPackage.ERROR_TEMPLATE__MISSING_END:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case AcceleoPackage.ERROR_TEMPLATE__DOCUMENTATION:
		case AcceleoPackage.ERROR_TEMPLATE__PARAMETERS:
		case AcceleoPackage.ERROR_TEMPLATE__GUARD:
		case AcceleoPackage.ERROR_TEMPLATE__POST:
		case AcceleoPackage.ERROR_TEMPLATE__BODY:
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

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.DOCUMENTED_ELEMENT__DOCUMENTATION,
				AcceleoFactory.eINSTANCE.createModuleDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.DOCUMENTED_ELEMENT__DOCUMENTATION,
				AcceleoFactory.eINSTANCE.createErrorModuleDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.DOCUMENTED_ELEMENT__DOCUMENTATION,
				AcceleoFactory.eINSTANCE.createModuleElementDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.DOCUMENTED_ELEMENT__DOCUMENTATION,
				AcceleoFactory.eINSTANCE.createErrorModuleElementDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TEMPLATE__PARAMETERS,
				AcceleoFactory.eINSTANCE.createVariable()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TEMPLATE__PARAMETERS,
				AcceleoFactory.eINSTANCE.createErrorVariable()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TEMPLATE__PARAMETERS,
				AcceleoFactory.eINSTANCE.createBinding()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TEMPLATE__PARAMETERS,
				AcceleoFactory.eINSTANCE.createErrorBinding()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TEMPLATE__GUARD,
				AcceleoFactory.eINSTANCE.createExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TEMPLATE__GUARD,
				AcceleoFactory.eINSTANCE.createErrorExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TEMPLATE__POST,
				AcceleoFactory.eINSTANCE.createExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.TEMPLATE__POST,
				AcceleoFactory.eINSTANCE.createErrorExpression()));

		newChildDescriptors.add(
				createChildParameter(AcceleoPackage.Literals.TEMPLATE__BODY, AcceleoFactory.eINSTANCE.createBlock()));
	}

	/**
	 * This returns the label text for
	 * {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
		Object childFeature = feature;
		Object childObject = child;

		boolean qualify = childFeature == AcceleoPackage.Literals.TEMPLATE__GUARD
				|| childFeature == AcceleoPackage.Literals.TEMPLATE__POST;

		if (qualify) {
			return getString("_UI_CreateChild_text2", //$NON-NLS-1$
					new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
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
