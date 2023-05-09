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
import org.eclipse.acceleo.ErrorFileStatement;
import org.eclipse.acceleo.OpenModeKind;
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
 * {@link org.eclipse.acceleo.ErrorFileStatement} object. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ErrorFileStatementItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ErrorFileStatementItemProvider(AdapterFactory adapterFactory) {
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

			addModePropertyDescriptor(object);
			addMissingOpenParenthesisPropertyDescriptor(object);
			addMissingCommaPropertyDescriptor(object);
			addMissingOpenModePropertyDescriptor(object);
			addMissingCloseParenthesisPropertyDescriptor(object);
			addMissingEndHeaderPropertyDescriptor(object);
			addMissingEndPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Mode feature. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addModePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_FileStatement_mode_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_FileStatement_mode_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_FileStatement_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.FILE_STATEMENT__MODE, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
						getResourceLocator(), getString("_UI_ErrorFileStatement_missingOpenParenthesis_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorFileStatement_missingOpenParenthesis_feature", "_UI_ErrorFileStatement_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Comma feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingCommaPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorFileStatement_missingComma_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorFileStatement_missingComma_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorFileStatement_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_FILE_STATEMENT__MISSING_COMMA, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Open Mode feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingOpenModePropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_ErrorFileStatement_missingOpenMode_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorFileStatement_missingOpenMode_feature", "_UI_ErrorFileStatement_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_FILE_STATEMENT__MISSING_OPEN_MODE, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Missing Close Parenthesis feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMissingCloseParenthesisPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add(createItemPropertyDescriptor(
				((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(), getResourceLocator(),
				getString("_UI_ErrorFileStatement_missingCloseParenthesis_feature"), //$NON-NLS-1$
				getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
						"_UI_ErrorFileStatement_missingCloseParenthesis_feature", "_UI_ErrorFileStatement_type"), //$NON-NLS-1$ //$NON-NLS-2$
				AcceleoPackage.Literals.ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS, true, false, false,
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
						getResourceLocator(), getString("_UI_ErrorFileStatement_missingEndHeader_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", //$NON-NLS-1$
								"_UI_ErrorFileStatement_missingEndHeader_feature", "_UI_ErrorFileStatement_type"), //$NON-NLS-1$ //$NON-NLS-2$
						AcceleoPackage.Literals.ERROR_FILE_STATEMENT__MISSING_END_HEADER, true, false, false,
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
						getResourceLocator(), getString("_UI_ErrorFileStatement_missingEnd_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_ErrorFileStatement_missingEnd_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_ErrorFileStatement_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.ERROR_FILE_STATEMENT__MISSING_END, true, false, false,
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
			childrenFeatures.add(AcceleoPackage.Literals.FILE_STATEMENT__URL);
			childrenFeatures.add(AcceleoPackage.Literals.FILE_STATEMENT__CHARSET);
			childrenFeatures.add(AcceleoPackage.Literals.FILE_STATEMENT__BODY);
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
	 * This returns ErrorFileStatement.gif. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/ErrorFileStatement")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		OpenModeKind labelValue = ((ErrorFileStatement) object).getMode();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ? getString("_UI_ErrorFileStatement_type") : //$NON-NLS-1$
				getString("_UI_ErrorFileStatement_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(ErrorFileStatement.class)) {
		case AcceleoPackage.ERROR_FILE_STATEMENT__MODE:
		case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_PARENTHESIS:
		case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_COMMA:
		case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_OPEN_MODE:
		case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_CLOSE_PARENTHESIS:
		case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END_HEADER:
		case AcceleoPackage.ERROR_FILE_STATEMENT__MISSING_END:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case AcceleoPackage.ERROR_FILE_STATEMENT__URL:
		case AcceleoPackage.ERROR_FILE_STATEMENT__CHARSET:
		case AcceleoPackage.ERROR_FILE_STATEMENT__BODY:
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

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FILE_STATEMENT__URL,
				AcceleoFactory.eINSTANCE.createExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FILE_STATEMENT__URL,
				AcceleoFactory.eINSTANCE.createErrorExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FILE_STATEMENT__CHARSET,
				AcceleoFactory.eINSTANCE.createExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FILE_STATEMENT__CHARSET,
				AcceleoFactory.eINSTANCE.createErrorExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.FILE_STATEMENT__BODY,
				AcceleoFactory.eINSTANCE.createBlock()));
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

		boolean qualify = childFeature == AcceleoPackage.Literals.FILE_STATEMENT__URL
				|| childFeature == AcceleoPackage.Literals.FILE_STATEMENT__CHARSET;

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
