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
import java.security.Provider.Service;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * A {@link Service} returning the raw collection type of the first argument.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class FirstArgumentRawCollectionType extends AbstractCollectionService {

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
	public FirstArgumentRawCollectionType(Method serviceMethod, Object serviceInstance,
			boolean forWorkspace) {
		super(serviceMethod, serviceInstance, forWorkspace);
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final IType receiverType = argTypes.get(0);
		if (receiverType instanceof ICollectionType) {
			result.add(((ICollectionType)receiverType).getCollectionType());
		} else {
			result.add(new ClassType(queryEnvironment, Object.class));
		}

		return result;
	}
}
