/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.qmodel.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.query.tests.qmodel.QmodelFactory;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

/**
 * This is the item provider adapter for a
 * {@link org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class QueryEvaluationResultExpectationItemProvider extends
		ExpectationItemProvider {
	/**
	 * This constructs an instance from a factory and a notifier. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public QueryEvaluationResultExpectationItemProvider(
			AdapterFactory adapterFactory) {
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

		}
		return itemPropertyDescriptors;
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to
	 * deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand},
	 * {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in
	 * {@link #createCommand}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(
			Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures
					.add(QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT);
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
		// Check the type of the specified child object and return the proper
		// feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns QueryEvaluationResultExpectation.gif. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(
				object,
				getResourceLocator().getImage(
						"full/obj16/QueryEvaluationResultExpectation"));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected boolean shouldComposeCreationImage() {
		return true;
	}

	/**
	 * This returns the label text for the adapted class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		return getString("_UI_QueryEvaluationResultExpectation_type");
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to
	 * update any cached children and by creating a viewer notification, which
	 * it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification
				.getFeatureID(QueryEvaluationResultExpectation.class)) {
		case QmodelPackage.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT:
			fireNotifyChanged(new ViewerNotification(notification,
					notification.getNotifier(), true, false));
			return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s
	 * describing the children that can be created under this object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(
			Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createListResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createSetResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createErrorResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createSerializableResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createEnumeratorResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createBooleanResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createStringResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createEmptyResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createIntegerResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createInvalidResult()));

		newChildDescriptors
				.add(createChildParameter(
						QmodelPackage.Literals.QUERY_EVALUATION_RESULT_EXPECTATION__EXPECTED_RESULT,
						QmodelFactory.eINSTANCE.createEObjectResult()));
	}

}
