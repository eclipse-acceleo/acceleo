/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services;

import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * EAllContents {@link org.eclipse.acceleo.query.runtime.IService IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
class EAllContentsService extends FilterService {

	/**
	 * Creates a new service instance given a method and an instance.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 */
	EAllContentsService(Method serviceMethod, Object serviceInstance) {
		super(serviceMethod, serviceInstance);
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final IType receiverType = argTypes.get(0);
		final Set<EClass> receiverEClasses = new LinkedHashSet<EClass>();
		if (receiverType.getType() instanceof EClass) {
			receiverEClasses.add((EClass)receiverType.getType());
		} else if (receiverType.getType() instanceof Class) {
			final Set<EClassifier> eClassifiers = queryEnvironment.getEPackageProvider().getEClassifiers(
					(Class<?>)receiverType.getType());
			if (eClassifiers != null) {
				for (EClassifier eCls : eClassifiers) {
					if (eCls instanceof EClass) {
						receiverEClasses.add((EClass)eCls);
					}
				}
			}
		} else {
			throw new IllegalStateException("don't know what to do with " + receiverType.getType());
		}

		if (receiverEClasses.isEmpty()) {
			result.add(new SequenceType(queryEnvironment, services.nothing(
					"Only EClass can contain other EClasses not %s", argTypes.get(0))));
		} else {
			for (EClass eCls : receiverEClasses) {
				if (eCls == EcorePackage.eINSTANCE.getEObject()) {
					if (argTypes.size() == 1) {
						result.add(new SequenceType(queryEnvironment, argTypes.get(0)));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierLiteralType) {
						result.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
								((EClassifierLiteralType)argTypes.get(1)).getType())));
					} else if (argTypes.size() == 2 && argTypes.get(1) instanceof EClassifierSetLiteralType) {
						for (EClassifier eClsFilter : ((EClassifierSetLiteralType)argTypes.get(1))
								.getEClassifiers()) {
							result.add(new SequenceType(queryEnvironment, new EClassifierType(
									queryEnvironment, eClsFilter)));
						}
					} else if (argTypes.size() == 2) {
						result.addAll(super.getType(call, services, validationResult, queryEnvironment,
								argTypes));
					}
				} else {
					result.addAll(getTypeForSpecificType(services, queryEnvironment, argTypes, eCls));
				}
			}
		}

		return result;
	}

	/**
	 * Gets the {@link IType} of elements returned by the service when the receiver type is not the
	 * {@link org.eclipse.emf.ecore.EObject EObject} {@link EClass}.
	 * 
	 * @param services
	 *            the {@link ValidationServices}
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param argTypes
	 *            arguments {@link IType}
	 * @param receiverEClass
	 *            the receiver type can't be {@link org.eclipse.emf.ecore.EObject EObject} {@link EClass}
	 * @return the {@link IType} of elements returned by the service when the receiver type is not the
	 *         {@link org.eclipse.emf.ecore.EObject EObject} {@link EClass}
	 */
	private Set<IType> getTypeForSpecificType(ValidationServices services,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes, final EClass receiverEClass) {
		final Set<IType> result = new LinkedHashSet<IType>();

		if (argTypes.size() == 1) {
			final Set<IType> containedTypes = new LinkedHashSet<IType>();
			for (EClass contained : queryEnvironment.getEPackageProvider().getAllContainedEClasses(
					receiverEClass)) {
				containedTypes.add(new SequenceType(queryEnvironment, new EClassifierType(queryEnvironment,
						contained)));
			}
			result.addAll(containedTypes);
			if (result.isEmpty()) {
				result.add(new SequenceType(queryEnvironment, services.nothing(
						"%s doesn't contain any other EClass", argTypes.get(0))));
			}
		} else if (argTypes.size() == 2) {
			final Set<IType> filterTypes = Sets.newLinkedHashSet();
			if (argTypes.get(1) instanceof EClassifierSetLiteralType) {
				for (EClassifier eClassifier : ((EClassifierSetLiteralType)argTypes.get(1)).getEClassifiers()) {
					filterTypes.add(new EClassifierType(queryEnvironment, eClassifier));
				}
			} else if (argTypes.get(1) instanceof EClassifierLiteralType) {
				filterTypes.add(argTypes.get(1));
			} else {
				final Collection<EClassifier> eObjectEClasses = queryEnvironment.getEPackageProvider()
						.getTypes("ecore", "EObject");
				for (EClassifier eObjectEClass : eObjectEClasses) {
					filterTypes.add(new EClassifierType(queryEnvironment, eObjectEClass));
				}
			}
			for (IType filterType : filterTypes) {
				for (EClass containedEClass : queryEnvironment.getEPackageProvider().getAllContainedEClasses(
						receiverEClass)) {
					final IType lowerType = services.lower(new EClassifierType(queryEnvironment,
							containedEClass), filterType);
					if (lowerType != null) {
						result.add(new SequenceType(queryEnvironment, lowerType));
					}
				}
			}
			if (result.isEmpty()) {
				result.add(new SequenceType(queryEnvironment, services.nothing(
						"%s can't contain %s direclty or indirectly", argTypes.get(0), argTypes.get(1))));
			}
		}

		return result;
	}
}
