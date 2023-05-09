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
 * Listener for {@link IQueryEnvironment}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 4.1
 */
public interface IQueryEnvironmentListener {

	/**
	 * Notifies a call to {@link IQueryEnvironment#registerService(IService)}.
	 * 
	 * @param result
	 *            the {@link ServiceRegistrationResult} to be returned by
	 *            {@link IQueryEnvironment#registerService(IService)}
	 * @param service
	 *            the registered {@link IService}
	 * @since 5.0
	 */
	void serviceRegistered(ServiceRegistrationResult result, IService<?> service);

	/**
	 * Notifies a call to {@link IQueryEnvironment#removeService(IService)}.
	 * 
	 * @param service
	 *            the removed {@link IService}
	 * @since 5.0
	 */
	void serviceRemoved(IService<?> service);

	/**
	 * Notifies a call to {@link IQueryEnvironment#registerEPackage(EPackage)}.
	 * 
	 * @param ePackage
	 *            the registered {@link EPackage}
	 */
	void ePackageRegistered(EPackage ePackage);

	/**
	 * Notifies a call to {@link IQueryEnvironment#removeEPackage(EPackage)}.
	 * 
	 * @param ePackage
	 *            the removed {@link EPackage}
	 */
	void ePackageRemoved(EPackage ePackage);

	/**
	 * Notifies a call to {@link IQueryEnvironment#registerCustomClassMapping(EClassifier, Class)}.
	 * 
	 * @param eClassifier
	 *            the {@link EClassifier}
	 * @param cls
	 *            the {@link Class}
	 */
	void customClassMappingRegistered(EClassifier eClassifier, Class<?> cls);

}
