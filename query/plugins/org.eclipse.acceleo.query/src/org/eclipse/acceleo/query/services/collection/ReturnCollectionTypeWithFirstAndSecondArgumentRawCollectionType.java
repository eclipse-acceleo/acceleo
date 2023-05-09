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
import java.security.Provider.Service;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;

/**
 * A {@link Service} returning the same collection type as the method with the raw collection type of first
 * and second arguments.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ReturnCollectionTypeWithFirstAndSecondArgumentRawCollectionType extends AbstractCollectionService {

	/**
	 * Constructor.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 */
	public ReturnCollectionTypeWithFirstAndSecondArgumentRawCollectionType(Method serviceMethod,
			Object serviceInstance) {
		super(serviceMethod, serviceInstance);
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final IType arg1Type;
		if (argTypes.get(0) instanceof ICollectionType) {
			arg1Type = ((ICollectionType)argTypes.get(0)).getCollectionType();
		} else if (argTypes.get(0) instanceof NothingType) {
			arg1Type = argTypes.get(0);
		} else {
			arg1Type = services.nothing(
					"%s can only be called on collections, but %s was used as its receiver.", getName(),
					argTypes.get(0));
		}
		final IType arg2Type;
		if (argTypes.get(1) instanceof ICollectionType) {
			arg2Type = ((ICollectionType)argTypes.get(1)).getCollectionType();
		} else if (argTypes.get(1) instanceof NothingType) {
			arg2Type = argTypes.get(1);
		} else {
			arg2Type = services.nothing(
					"%s can only be called on collections, but %s was used as its argument.", getName(),
					argTypes.get(1));
		}

		result.add(createReturnCollectionWithType(queryEnvironment, arg1Type));
		result.add(createReturnCollectionWithType(queryEnvironment, arg2Type));

		return result;
	}

	@Override
	public Set<IType> validateAllType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
			Map<List<IType>, Set<IType>> allTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();
		final StringBuilder builder = new StringBuilder();

		for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			for (IType type : entry.getValue()) {
				if (((ICollectionType)type).getCollectionType() instanceof NothingType) {
					builder.append(MESSAGE_SEPARATOR);
					builder.append(((NothingType)((ICollectionType)type).getCollectionType()).getMessage());
				} else {
					result.add(type);
				}
			}
		}

		if (result.isEmpty()) {
			IType nothing = services.nothing("Nothing left after %s:" + builder.toString(), getName());
			result.add(createReturnCollectionWithType(queryEnvironment, nothing));
		}

		return result;
	}

}
