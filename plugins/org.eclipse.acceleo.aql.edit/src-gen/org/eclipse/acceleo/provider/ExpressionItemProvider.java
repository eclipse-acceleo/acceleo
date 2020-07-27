/**
 * Copyright (c) 2008, 2020 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.query.ast.AstFactory;
import org.eclipse.acceleo.query.parser.AstResult;
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
 * This is the item provider adapter for a {@link org.eclipse.acceleo.Expression} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ExpressionItemProvider extends ItemProviderAdapter implements IEditingDomainItemProvider,
		IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addAstPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

	/**
	 * This adds a property descriptor for the Ast feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addAstPropertyDescriptor(Object object) {
		itemPropertyDescriptors
				.add(createItemPropertyDescriptor(((ComposeableAdapterFactory) adapterFactory).getRootAdapterFactory(),
						getResourceLocator(), getString("_UI_Expression_ast_feature"), //$NON-NLS-1$
						getString("_UI_PropertyDescriptor_description", "_UI_Expression_ast_feature", //$NON-NLS-1$//$NON-NLS-2$
								"_UI_Expression_type"), //$NON-NLS-1$
						AcceleoPackage.Literals.EXPRESSION__AST, true, false, false,
						ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(AcceleoPackage.Literals.EXPRESSION__AQL);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns Expression.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/Expression")); //$NON-NLS-1$
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		AstResult labelValue = ((Expression) object).getAst();
		String label = labelValue == null ? null : labelValue.toString();
		return label == null || label.length() == 0 ? getString("_UI_Expression_type") : //$NON-NLS-1$
				getString("_UI_Expression_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(Expression.class)) {
		case AcceleoPackage.EXPRESSION__AST:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
			return;
		case AcceleoPackage.EXPRESSION__AQL:
			fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add(
				createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL, AstFactory.eINSTANCE.createVarRef()));

		newChildDescriptors
				.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL, AstFactory.eINSTANCE.createCall()));

		newChildDescriptors.add(
				createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL, AstFactory.eINSTANCE.createLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createIntegerLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createRealLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createStringLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createBooleanLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createEnumLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createTypeLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createTypeSetLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createCollectionTypeLiteral()));

		newChildDescriptors.add(
				createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL, AstFactory.eINSTANCE.createLambda()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createNullLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createSetInExtensionLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createSequenceInExtensionLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createErrorExpression()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createErrorTypeLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createErrorEClassifierTypeLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createErrorEnumLiteral()));

		newChildDescriptors.add(
				createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL, AstFactory.eINSTANCE.createErrorCall()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createErrorVariableDeclaration()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createErrorStringLiteral()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createErrorConditional()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createErrorBinding()));

		newChildDescriptors
				.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL, AstFactory.eINSTANCE.createLet()));

		newChildDescriptors.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL,
				AstFactory.eINSTANCE.createConditional()));

		newChildDescriptors
				.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL, AstFactory.eINSTANCE.createOr()));

		newChildDescriptors
				.add(createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL, AstFactory.eINSTANCE.createAnd()));

		newChildDescriptors.add(
				createChildParameter(AcceleoPackage.Literals.EXPRESSION__AQL, AstFactory.eINSTANCE.createImplies()));
	}

	/**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		return AcceleoEditPlugin.INSTANCE;
	}

}
