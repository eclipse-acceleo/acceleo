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

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

/**
 * An IQueryEnvironment contains all informations needed to execute query services.
 * 
 * @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 */
public interface IQueryEnvironment extends IReadOnlyQueryEnvironment {

	/**
	 * Register a set of services. The specified {@link Class} must have a default constructor with no
	 * parameters. Any public, non void, instance method in the specified class will be registered as a
	 * service. Services can be called directly from Acceleo Query Language expressions. if several services
	 * with the same name and signature are registered, the latest registered service among all the identical
	 * services is used. This allows to override services in the language. Overriding services can be done
	 * either by introducing a new class with a service that exists in another registered class or by
	 * registering a subclass of an registered service class. In that later case, the sub-class will replace
	 * the existing service class.
	 * 
	 * @param services
	 *            the service package to be registered.
	 * @return the {@link ServiceRegistrationResult}
	 * @throws InvalidAcceleoPackageException
	 *             if the specified class has no default constructor.
	 */
	ServiceRegistrationResult registerServicePackage(Class<?> services) throws InvalidAcceleoPackageException;

	/**
	 * Tells if the given {@link Class} is already registered.
	 * 
	 * @param services
	 *            the service package to check
	 * @return <code>true</code> if the given {@link Class} is already registered, <code>false</code>
	 *         otherwise
	 */
	boolean isRegisteredServicePackage(Class<?> services);

	/**
	 * Removes the given {@link Class} from registered the service package.
	 * 
	 * @param services
	 *            the service package to be removed
	 */
	void removeServicePackage(Class<?> services);

	/**
	 * Registers a new {@link EPackage} that can be referred during evaluation and validation.
	 * 
	 * @param ePackage
	 *            the package to be registered.
	 */
	void registerEPackage(EPackage ePackage);

	/**
	 * Remove a registered package given it's name.
	 * 
	 * @param name
	 *            the name of the package to be removed.
	 */
	void removeEPackage(String name);

	/**
	 * Registers a custom mapping from an {@link EClassifier} to its {@link Class}.
	 * 
	 * @param eClassifier
	 *            the {@link EClassifier}
	 * @param cls
	 *            the {@link Class}
	 */
	void registerCustomClassMapping(EClassifier eClassifier, Class<?> cls);

	/**
	 * Adds the given {@link IQueryEnvironmentListener}.
	 * 
	 * @param listener
	 *            the {@link IQueryEnvironmentListener} to add, can't be <code>null</code>
	 * @since 4.1
	 */
	void addQueryEnvironmentListener(IQueryEnvironmentListener listener);

	/**
	 * Removes the given {@link IQueryEnvironmentListener}.
	 * 
	 * @param listener
	 *            the {@link IQueryEnvironmentListener} to remove
	 * @since 4.1
	 */
	void removeQueryEnvironmentListener(IQueryEnvironmentListener listener);

}
