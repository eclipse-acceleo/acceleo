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

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

/**
 * An IQueryEnvironment contains all informations needed to execute query services.
 * 
 * @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 */
public interface IQueryEnvironment extends IReadOnlyQueryEnvironment {

	/**
	 * Registers the given {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService} to register.
	 * @return the {@link ServiceRegistrationResult}
	 * @since 5.0
	 */
	ServiceRegistrationResult registerService(IService<?> service);

	/**
	 * Removes the given {@link IService} from {@link IQueryEnvironment#registerService(IService) registered}
	 * {@link IService}.
	 * 
	 * @param service
	 *            the {@link IService} to remove
	 * @since 5.0
	 */
	void removeService(IService<?> service);

	/**
	 * Tells if the given {@link IService} is {@link IQueryEnvironment#registerService(IService) registered}.
	 * 
	 * @param service
	 *            the {@link IService} to check
	 * @return <code>true</code> if the given {@link IService} is
	 *         {@link IQueryEnvironment#registerService(IService) registered}, <code>false</code> otherwise
	 * @since 5.0
	 */
	boolean isRegisteredService(IService<?> service);

	/**
	 * Registers a new {@link EPackage} that can be referred during evaluation and validation.
	 * 
	 * @param ePackage
	 *            the package to be registered.
	 */
	void registerEPackage(EPackage ePackage);

	/**
	 * Remove the given {@link EPackage}.
	 * 
	 * @param ePackage
	 *            the {@link EPackage} to remove
	 * @since 6.0
	 */
	void removeEPackage(EPackage ePackage);

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
