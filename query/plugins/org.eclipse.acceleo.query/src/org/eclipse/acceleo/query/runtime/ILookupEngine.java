/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import java.util.Set;

import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Provides {@link IService} in the {@link IReadOnlyQueryEnvironment}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface ILookupEngine {

	/**
	 * Message used when a service cannot be instantiated.
	 */
	String INSTANTIATION_PROBLEM_MSG = "Couldn't instantiate class ";

	/**
	 * Returns the service that has the specified name and that best matches the specified argument types if
	 * any is found. Services are ordered according to their parameter types. A type <em>T1</em> is lower than
	 * a type <em>T2</em> if <em>T1</em>is a sub-class of <em>T2</em>. A method <em>M1</em> is lower than a
	 * method <em>M2</em> if all the types of the former are lower than the type of the later at the same
	 * index.
	 * <table border="1">
	 * <caption> Global service lookup priority </caption>
	 * <tr>
	 * <th>{@link IService#getPriority() priority}\
	 * {@link IService#isEqualParameterTypes(IReadOnlyQueryEnvironment, IService) parameter types}</th>
	 * <th>&lt;</th>
	 * <th>=</th>
	 * <th>&gt;</th>
	 * </tr>
	 * <tr>
	 * <th>&lt;</th>
	 * <td>&lt;</td>
	 * <td>&lt;</td>
	 * <td>&lt;</td>
	 * </tr>
	 * <tr>
	 * <th>=</th>
	 * <td>&lt;</td>
	 * <td>=</td>
	 * <td>&gt;</td>
	 * </tr>
	 * <tr>
	 * <th>&gt;</th>
	 * <td>&gt;</td>
	 * <td>&gt;</td>
	 * <td>&gt;</td>
	 * </tr>
	 * </table>
	 * 
	 * @param name
	 *            the name of the service to retrieve.
	 * @param argumentTypes
	 *            {@link IType} of the arguments to best match.
	 * @return the best service's match of the registered services if any.
	 * @since 5.0
	 */
	IService<?> lookup(String name, IType[] argumentTypes);

	/**
	 * Gets the {@link Set} of known {@link IService} with a compatible receiver {@link IType types}.
	 * 
	 * @param receiverTypes
	 *            the receiver {@link IType types}
	 * @return the {@link Set} of known {@link IService} with a compatible receiver {@link IType types}
	 */
	Set<IService<?>> getServices(Set<IType> receiverTypes);

	/**
	 * Gets the {@link Set} of registered {@link IService}.
	 * 
	 * @return the {@link Set} of registered {@link IService}
	 * @since 5.0
	 */
	Set<IService<?>> getRegisteredServices();

	/**
	 * Tells if the given {@link IService} is registered.
	 * 
	 * @param service
	 *            the {@link IService} to check
	 * @return <code>true</code> if the given {@link IService} is registered, <code>false</code> otherwise
	 * @since 5.0
	 */
	boolean isRegisteredService(IService<?> service);

}
