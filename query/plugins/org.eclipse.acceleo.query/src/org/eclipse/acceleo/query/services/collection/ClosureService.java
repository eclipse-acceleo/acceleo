/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.LambdaType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SetType;

/**
 * Closure {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ClosureService extends AbstractCollectionService {
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
	public ClosureService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
		super(serviceMethod, serviceInstance, forWorkspace);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.JavaMethodService#getType(org.eclipse.acceleo.query.ast.Call,
	 *      org.eclipse.acceleo.query.runtime.impl.ValidationServices,
	 *      org.eclipse.acceleo.query.runtime.IValidationResult,
	 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.List)
	 */
	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		if (argTypes.get(1) instanceof LambdaType) {
			final IType lambdaExpressionType = ((LambdaType)argTypes.get(1)).getLambdaExpressionType();

			// FIXME need to make the closure on type as well... it's not possible for the moment because we
			// need variable types...
			final IType receiverType = argTypes.get(0);
			if (receiverType instanceof NothingType) {
				result.add(createReturnCollectionWithType(queryEnvironment, receiverType));
			} else if (receiverType instanceof ICollectionType && ((ICollectionType)receiverType)
					.getCollectionType() instanceof NothingType) {
				result.add(receiverType);
			} else if (lambdaExpressionType instanceof ICollectionType) {
				result.add(new SetType(queryEnvironment, ((ICollectionType)lambdaExpressionType)
						.getCollectionType()));
			} else {
				result.add(new SetType(queryEnvironment, lambdaExpressionType));
			}
		} else {
			result.add(services.nothing("The %s service takes a lambda as parameter: v | v...", call
					.getServiceName()));
		}

		return result;
	}
}
