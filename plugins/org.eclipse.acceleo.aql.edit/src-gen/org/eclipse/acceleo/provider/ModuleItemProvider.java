/**
 * Copyright (c) 2008, 2024 Obeo.
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
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a {@link org.eclipse.acceleo.Module}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ModuleItemProvider extends NamedElementItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModuleItemProvider(AdapterFactory adapterFactory) {
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
			addMetamodelsPropertyDescriptor(object);
			addExtendsPropertyDescriptor(object);
			addImportsPropertyDescriptor(object);
			addStartHeaderPositionPropertyDescriptor(object);
			addEndHeaderPositionPropertyDescriptor(object);
			addAstPropertyDescriptor(object);
			addEncodingPropertyDescriptor(object);
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
	 * This adds a property descriptor for the Metamodels feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addMetamodelsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Module_metamodels_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Module_metamodels_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_Module_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.MODULE__METAMODELS, true, false, true, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Extends feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addExtendsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Module_extends_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Module_extends_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_Module_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.MODULE__EXTENDS, true, false, false, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Imports feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addImportsPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Module_imports_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Module_imports_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_Module_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.MODULE__IMPORTS, true, false, false, null, null, null));
	}

	/**
	 * This adds a property descriptor for the Start Header Position feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addStartHeaderPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Module_startHeaderPosition_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Module_startHeaderPosition_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_Module_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.MODULE__START_HEADER_POSITION, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the End Header Position feature. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addEndHeaderPositionPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Module_endHeaderPosition_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Module_endHeaderPosition_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_Module_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.MODULE__END_HEADER_POSITION, true, false, false,
						ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Ast feature. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addAstPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Module_ast_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Module_ast_feature", "_UI_Module_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						AcceleoPackage.Literals.MODULE__AST, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This adds a property descriptor for the Encoding feature. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void addEncodingPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Module_encoding_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Module_encoding_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_Module_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.MODULE__ENCODING, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
			childrenFeatures.add(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS);
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
	 * This returns Module.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Module")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((org.eclipse.acceleo.Module) object).getName();
		return label == null || label.length() == 0 ? getString("_UI_Module_type") : //$NON-NLS-1$
				getString("_UI_Module_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

		switch (notification.getFeatureID(org.eclipse.acceleo.Module.class)) {
		case AcceleoPackage.MODULE__DEPRECATED:
		case AcceleoPackage.MODULE__EXTENDS:
		case AcceleoPackage.MODULE__IMPORTS:
		case AcceleoPackage.MODULE__START_HEADER_POSITION:
		case AcceleoPackage.MODULE__END_HEADER_POSITION:
		case AcceleoPackage.MODULE__AST:
		case AcceleoPackage.MODULE__ENCODING:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case AcceleoPackage.MODULE__DOCUMENTATION:
		case AcceleoPackage.MODULE__MODULE_ELEMENTS:
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

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createComment()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createBlockComment()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createErrorComment()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createErrorBlockComment()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createModuleDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createErrorModuleDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createModuleElementDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createErrorModuleElementDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createParameterDocumentation()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createTemplate()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createErrorTemplate()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createQuery()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS,
				AcceleoFactory.eINSTANCE.createErrorQuery()));
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

		boolean qualify = childFeature == AcceleoPackage.Literals.DOCUMENTED_ELEMENT__DOCUMENTATION
				|| childFeature == AcceleoPackage.Literals.MODULE__MODULE_ELEMENTS;

		if (qualify) {
			return getString("_UI_CreateChild_text2", //$NON-NLS-1$
					new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
		}
		return super.getCreateChildText(owner, feature, child, selection);
	}

}
