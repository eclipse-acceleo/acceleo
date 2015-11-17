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
 * Listener for {@link IQueryEnvironment}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 4.1
 */
public interface IQueryEnvironmentListener {

	/**
	 * Notifies a call to {@link IQueryEnvironment#registerServicePackage(Class)}.
	 * 
	 * @param result
	 *            the {@link ServiceRegistrationResult} to be returned by
	 *            {@link IQueryEnvironment#registerServicePackage(Class)}
	 * @param services
	 *            the registered {@link Class} services
	 */
	void servicePackageRegistered(ServiceRegistrationResult result, Class<?> services);

	/**
	 * Notifies a call to {@link IQueryEnvironment#removeServicePackage(Class)}.
	 * 
	 * @param services
	 *            the removed {@link Class} services
	 */
	void servicePackageRemoved(Class<?> services);

	/**
	 * Notifies a call to {@link IQueryEnvironment#registerEPackage(EPackage)}.
	 * 
	 * @param ePackage
	 *            the registered {@link EPackage}
	 */
	void ePackageRegistered(EPackage ePackage);

	/**
	 * Notifies a call to {@link IQueryEnvironment#removeEPackage(String)}.
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
