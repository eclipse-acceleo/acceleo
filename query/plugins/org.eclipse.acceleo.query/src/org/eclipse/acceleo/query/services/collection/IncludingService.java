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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;

/**
 * Including {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class IncludingService extends AbstractCollectionService {

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
	public IncludingService(Method serviceMethod, Object serviceInstance, boolean forWorkspace) {
		super(serviceMethod, serviceInstance, forWorkspace);
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();
		result.add(createReturnCollectionWithType(queryEnvironment, ((ICollectionType)argTypes.get(0))
				.getCollectionType()));
		result.add(createReturnCollectionWithType(queryEnvironment, argTypes.get(1)));
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
			result.add(createReturnCollectionWithType(queryEnvironment, services.nothing(
					"Nothing left after %s:" + builder.toString(), getName())));
		}

		return result;
	}
}
