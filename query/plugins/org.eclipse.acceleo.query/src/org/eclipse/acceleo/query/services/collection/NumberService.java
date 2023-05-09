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

/**
 * Sum, min, max {@link IService}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class NumberService extends AbstractCollectionService {

	/**
	 * Constructor.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 */
	public NumberService(Method serviceMethod, Object serviceInstance) {
		super(serviceMethod, serviceInstance);
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();
		if (!argTypes.isEmpty() && argTypes.get(0) instanceof ICollectionType) {
			IType argType = ((ICollectionType)argTypes.get(0)).getCollectionType();

			IType intType = new ClassType(queryEnvironment, Integer.class);
			IType longType = new ClassType(queryEnvironment, Long.class);
			IType numberType = new ClassType(queryEnvironment, Number.class);

			if (intType.isAssignableFrom(argType) || longType.isAssignableFrom(argType)) {
				result.add(longType);
			} else if (numberType.isAssignableFrom(argType)) {
				// any number that is not an int or long is widened to Double
				result.add(new ClassType(queryEnvironment, Double.class));
			} else {
				result.add(services.nothing(String.format(ONLY_NUMERIC_ERROR, getName())));
			}
		}
		return result;
	}

	@Override
	public Set<IType> validateAllType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
			Map<List<IType>, Set<IType>> allTypes) {
		IType currentResult = null;

		final IType longType = new ClassType(queryEnvironment, Long.class);
		final IType doubleType = new ClassType(queryEnvironment, Double.class);
		for (Map.Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {

			IType returnType = entry.getValue().iterator().next();
			if (currentResult == null) {
				currentResult = returnType;
			} else if (currentResult.equals(doubleType) || returnType.equals(doubleType)) {
				currentResult = new ClassType(queryEnvironment, Double.class);
			} else if (returnType.equals(longType)) {
				currentResult = new ClassType(queryEnvironment, Long.class);
			} else {
				currentResult = services.nothing(String.format(ONLY_NUMERIC_ERROR, getName()));
				break;
			}
		}

		Set<IType> result = new LinkedHashSet<IType>();
		result.add(currentResult);
		return result;
	}
}
