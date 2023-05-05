/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EOperation;

/**
 * Interface that a service usable in query evaluation engine must implement.
 * 
 * @param <O>
 *            the origin type of the service
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public interface IService<O> {

	/**
	 * Visibility of an {@link IService}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	enum Visibility {
		/**
		 * Private.
		 */
		PRIVATE,
		/**
		 * Protected.
		 */
		PROTECTED,
		/**
		 * Public.
		 */
		PUBLIC
	}

	/**
	 * Gets the name of the service. This name is used to identify the service.
	 * 
	 * @return the name of the service
	 * @since 4.1
	 */
	String getName();

	/**
	 * Gets a human readable signature of the service.
	 * 
	 * @return a human readable signature of the service
	 * @since 4.1
	 */
	String getShortSignature();

	/**
	 * Gets the identifying signature of the service in the underlying technology.
	 * 
	 * @return the identifying signature of the service in the underlying technology
	 * @since 4.1
	 */
	String getLongSignature();

	/**
	 * Gets the {@link List} of parameter {@link IType}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @return the {@link List} of parameter {@link IType}
	 * @since 4.1
	 */
	List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment);

	/**
	 * Gets the number of parameters including the receiver.
	 * 
	 * @return the number of parameters including the receiver
	 * @since 4.1
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
	 * @since 4.1
	 */
	Object invoke(Object... arguments) throws AcceleoQueryEvaluationException;

	/**
	 * Gets the priority of this service. The highest priority is used when {@link IService#getName() name}
	 * and {@link IService#getParameterTypes() parameter types} are matching for more than one
	 * {@link IService} . In the case of same priority the last
	 * {@link IQueryEnvironment#registerServicePackage(Class) added} {@link IService} will be used.
	 * 
	 * @return the priority
	 * @since 4.1
	 */
	int getPriority();

	/**
	 * Gets the dynamic {@link IType} of elements returned by the service {@link Call}.
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
	 * @since 4.0
	 */
	Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes);

	/**
	 * Gets the static {@link IType} for the service.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @return the static {@link IType} for the service
	 */
	Set<IType> getType(IReadOnlyQueryEnvironment queryEnvironment);

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

	/**
	 * Predicates that is <code>true</code> if and only if all this service's parameter types are assignable
	 * to the given service's parameter types at the same index.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param service
	 *            ISe {@link IService} to compare
	 * @return <code>true</code> if this <= service in terms of parameter types, <code>false</code> otherwise
	 * @since 5.0
	 */
	boolean isLowerOrEqualParameterTypes(IReadOnlyQueryEnvironment queryEnvironment, IService<?> service);

	/**
	 * Predicates that is <code>true</code> if and only if all given service's parameter types are the same as
	 * the this service's parameter types at the same index.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param service
	 *            the {@link IService} to compare
	 * @return <code>true</code> if this == service in terms of parameter types, <code>false</code> otherwise
	 * @since 5.0
	 */
	boolean isEqualParameterTypes(IReadOnlyQueryEnvironment queryEnvironment, IService<?> service);

	/**
	 * Predicates that is <code>true</code> when the specified argument types match the specified service's
	 * parameter types. An argument's type matches a parameter's type if the latter is assignable from the
	 * former.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param argumentTypes
	 *            the argument {@link IType} to match against the
	 *            {@link IService#getParameterTypes(IReadOnlyQueryEnvironment) service parameters type}
	 * @return <code>true</code> when the specified service matches the specified set of types
	 * @since 5.0
	 */
	boolean matches(IReadOnlyQueryEnvironment queryEnvironment, IType[] argumentTypes);

	/**
	 * Gets the {@link List} of {@link ICompletionProposal}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param receiverTypes
	 *            the possible receiver {@link IType}
	 * @return the {@link List} of {@link ICompletionProposal}
	 * @since 5.0
	 */
	List<ICompletionProposal> getProposals(IReadOnlyQueryEnvironment queryEnvironment,
			Set<IType> receiverTypes);

	/**
	 * Provides the semantic element that is at the origin of this service, like a Java {@link Method}, an EMF
	 * {@link EOperation}, ...
	 * 
	 * @return the {@link O origin} of this {@link IService}.
	 */
	O getOrigin();

	/**
	 * Gets the {@link Visibility}.
	 * 
	 * @return the {@link Visibility}
	 */
	Visibility getVisibility();

}
