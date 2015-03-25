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

import java.lang.reflect.Method;
import java.util.Set;

/**
 * Provides {@link IService} in the {@link IReadOnlyQueryEnvironment}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface ILookupEngine {

	/**
	 * Message used when a service cannot be instanciated.
	 */
	String INSTANTIATION_PROBLEM_MSG = "Couldn't instantiate class ";

	/**
	 * Returns the {@link CrossReferencer} that this engine uses.
	 * 
	 * @return The {@link CrossReferencer} that this engine uses.
	 */
	CrossReferenceProvider getCrossReferencer();

	/**
	 * Returns the service that has the specified name and that best matches the specified argument types if
	 * any is found. Services are ordered according to their parameter types. A type <em>T1</em> is lower than
	 * a type <em>T2</em> if <em>T1</em>is a sub-class of <em>T2</em>. A method <em>M1</em> is lower than a
	 * method <em>M2</em> if all the types of the former are lower than the type of the later at the same
	 * index.
	 * 
	 * @param name
	 *            the name of the service to retrieve.
	 * @param argumentTypes
	 *            the types of the arguments to best match.
	 * @return the best service's match of the registered services if any.
	 */
	IService lookup(String name, Class<?>[] argumentTypes);

	/**
	 * Tells if the given method is the one that indicates we have to set the cross referencer to the service
	 * instance.
	 * 
	 * @param method
	 *            The method we want to know if it the one that indicates we have to set the cross referencer
	 *            to the service instance.
	 * @return true if the given method is the one that indicates we have to set the cross referencer to the
	 *         service instance. False otherwise.
	 */
	boolean isCrossReferencerMethod(Method method);

	/**
	 * Gets the {@link Set} of known {@link IService} with a compatible receiver type.
	 * 
	 * @param receiverTypes
	 *            the receiver types
	 * @return the {@link Set} of known {@link IService} with a compatible receiver type
	 */
	Set<IService> getServices(Set<Class<?>> receiverTypes);

	/**
	 * Tells if a given {@link Method} is considered as a {@link IService#getServiceMethod() service} to
	 * provide when querying.
	 * 
	 * @param method
	 *            the method we want to know if it must be considered as a service to provide when querying.
	 * @return <code>true</code> if a given {@link Method} is considered as a
	 *         {@link IService#getServiceMethod() service} to provide when querying, <code>false</code>
	 *         otherwise
	 */
	boolean isServiceMethod(Method method);

}
