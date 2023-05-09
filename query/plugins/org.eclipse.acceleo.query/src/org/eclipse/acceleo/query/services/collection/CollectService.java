/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.LambdaType;
import org.eclipse.acceleo.query.validation.type.NothingType;

/**
 * Collect {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CollectService extends AbstractCollectionService {
	/**
	 * Constructor.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 */
	public CollectService(Method serviceMethod, Object serviceInstance) {
		super(serviceMethod, serviceInstance);
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final IType receiverType = argTypes.get(0);
		if (receiverType instanceof NothingType) {
			result.add(createReturnCollectionWithType(queryEnvironment, receiverType));
		} else if (receiverType instanceof ICollectionType && ((ICollectionType)receiverType)
				.getCollectionType() instanceof NothingType) {
			result.add(receiverType);
		} else {
			final LambdaType lambdaType = (LambdaType)argTypes.get(1);
			// flatten if needed
			result.add(createReturnCollectionWithType(queryEnvironment, flatten(lambdaType
					.getLambdaExpressionType())));
		}
		return result;
	}

	private IType flatten(IType type) {
		if (type instanceof ICollectionType) {
			return flatten(((ICollectionType)type).getCollectionType());
		}
		return type;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices,
	 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.Map)
	 */
	@Override
	public Set<IType> validateAllType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
			Map<List<IType>, Set<IType>> allTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();
		for (Map.Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			for (IType type : entry.getValue()) {
				final IType collectionType = ((ICollectionType)type).getCollectionType();
				if (collectionType instanceof ClassType && ((ClassType)collectionType).getType() == null) {
					// This is the null literal, which we don't want in our result
					// and will be stripped at runtime.
				} else {
					result.add(type);
				}
			}
		}
		return result;
	}
}
