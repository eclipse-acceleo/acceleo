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
package org.eclipse.acceleo.query.runtime;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Interface that a service usable in query evaluation engine must implement.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public interface IService {

	/**
	 * Gets the name of the service. This name is used to identify the service.
	 * 
	 * @return the name of the service
	 * @since 4.1.0
	 */
	String getName();

	/**
	 * Gets a human readable signature of the service.
	 * 
	 * @return a human readable signature of the service
	 */
	String getShortSignature();

	/**
	 * Gets the identifying signature of the service in the underlying technology.
	 * 
	 * @return the identifying signature of the service in the underlying technology
	 */
	String getLongSignature();

	/**
	 * Gets the {@link List} of parameter {@link IType}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @return the {@link List} of parameter {@link IType}
	 * @since 4.1.0
	 */
	List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment);

	/**
	 * Gets the number of parameters including the receiver.
	 * 
	 * @return the number of parameters including the receiver
	 * @since 4.1.0
	 */
	int getNumberOfParameters();

	/**
	 * Invokes the service with given arguments.
	 * 
	 * @param arguments
	 *            arguments
	 * @return the result of the invocation
	 * @throws AcceleoQueryEvaluationException
	 *             when the invocation goes wrong
	 * @since 4.1.0
	 */
	Object invoke(Object... arguments) throws AcceleoQueryEvaluationException;

	/**
	 * Gets the priority of this service. The highest priority is used when {@link IService#getName() name}
	 * and {@link IService#getParameterTypes() parameter types} are matching for more than one
	 * {@link IService} . In the case of same priority the last added {@link IService} will be used.
	 * 
	 * @return the priority
	 * @since 4.1.0
	 */
	int getPriority();

	/**
	 * Gets the {@link IType} of elements returned by the service.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @param services
	 *            the {@link ValidationServices}
	 * @param validationResult
	 *            the {@link IValidationResult} being constructed
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param argTypes
	 *            arguments {@link IType}
	 * @return the {@link IType} of elements returned by the service
	 * @since 4.0.0
	 */
	Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes);

	/**
	 * Validates all couple of arguments {@link IType} and the {@link IType} of elements returned by the
	 * service.
	 * 
	 * @param services
	 *            the {@link ValidationServices}
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param allTypes
	 *            all couple of arguments {@link IType} and the {@link IType} of elements returned by the
	 *            service
	 * @return validated {@link IType}
	 */
	Set<IType> validateAllType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
			Map<List<IType>, Set<IType>> allTypes);

}
