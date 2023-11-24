/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.collection;

import java.lang.reflect.Method;
import java.security.Provider.Service;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.services.FilterService;
import org.eclipse.acceleo.query.validation.type.ClassLiteralType;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.emf.ecore.EClassifier;

/**
 * A {@link Service} returning the
 * {@link org.eclipse.acceleo.query.validation.type.EClassifierLiteralType#getType() classifier literal type}
 * of the second argument in the {@link org.eclipse.acceleo.query.validation.type.ICollectionType
 * ICollectionType} of the first argument.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SecondArgumentTypeInFirstArgumentCollectionType extends FilterService {

	/**
	 * Constructor.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 */
	public SecondArgumentTypeInFirstArgumentCollectionType(Method serviceMethod, Object serviceInstance,
			boolean forWorkspace) {
		super(serviceMethod, serviceInstance, forWorkspace);
	}

	@Override
	public java.util.Set<IType> getType(Call call, ValidationServices services,
			IValidationResult validationResult, IReadOnlyQueryEnvironment queryEnvironment,
			java.util.List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final Set<IType> rawTypes = new LinkedHashSet<IType>();

		final IType receiverType = argTypes.get(0);
		if (receiverType instanceof NothingType) {
			result.add(createReturnCollectionWithType(queryEnvironment, receiverType));
		} else if (receiverType instanceof ICollectionType && ((ICollectionType)receiverType)
				.getCollectionType() instanceof NothingType) {
			result.add(receiverType);
		} else if (argTypes.get(1) instanceof ClassLiteralType) {
			rawTypes.add(new ClassType(queryEnvironment, ((ClassLiteralType)argTypes.get(1)).getType()));
		} else if (argTypes.get(1) instanceof ClassType && ((ClassType)argTypes.get(1)).getType() == null) {
			result.add(services.nothing("EClassifier on %s cannot be null.", getName()));
		} else if (argTypes.get(1) instanceof EClassifierType) {
			rawTypes.add(new EClassifierType(queryEnvironment, ((EClassifierType)argTypes.get(1)).getType()));
		} else if (argTypes.get(1) instanceof EClassifierSetLiteralType) {
			for (EClassifier eCls : ((EClassifierSetLiteralType)argTypes.get(1)).getEClassifiers()) {
				rawTypes.add(new EClassifierType(queryEnvironment, eCls));
			}
		}
		for (IType rawType : rawTypes) {
			result.add(createReturnCollectionWithType(queryEnvironment, rawType));
		}

		return result;

	}
}
