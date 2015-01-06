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

import java.util.logging.Logger;

import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.emf.ecore.EPackage;

/**
 * An IQueryEnvironment contains all informations needed to execute query services.
 * 
 * @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 */
public interface IQueryEnvironment {

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
	 * @throws InvalidAcceleoPackageException
	 *             if the specified class has no default constructor.
	 */
	void registerServicePackage(Class<?> services) throws InvalidAcceleoPackageException;

	/**
	 * Registers a new {@link EPackage} that can be referred during evaluation and validation.
	 * 
	 * @param ePackage
	 *            the package to be registered.
	 */
	void registerEPackage(EPackage ePackage);

	/**
	 * Remove a registered package given it's nsPrefix.
	 * 
	 * @param nsPrefix
	 *            the nsPrefix of the package to be removed.
	 */
	void removeEPackage(String nsPrefix);

	/**
	 * Return the engine providing query services.
	 * 
	 * @return the engine providing query services.
	 */
	BasicLookupEngine getLookupEngine();

	/**
	 * Return the EPackageProvider providing access to registered ecore packages.
	 * 
	 * @return the EPackageProvider providing access to registered ecore packages.
	 */
	EPackageProvider getEPackageProvider();

	/**
	 * Returns the logger used to log messages in this evaluation environment.
	 * 
	 * @return the logger used to log messages in this evaluation environment.
	 */
	Logger getLogger();

}
