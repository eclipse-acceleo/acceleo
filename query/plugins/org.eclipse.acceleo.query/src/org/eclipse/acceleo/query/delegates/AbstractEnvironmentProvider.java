/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.delegates;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;

/**
 * Provide an {@link IQueryEnvironment}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractEnvironmentProvider {

	/**
	 * The cached {@link IQueryEnvironment}.
	 */
	private IQueryEnvironment environment;

	/**
	 * Gets the configured {@link IQueryEnvironment}.
	 * 
	 * @return the configured {@link IQueryEnvironment}
	 */
	protected IQueryEnvironment getEnvironment() {
		final IQueryEnvironment res;

		if (environment == null) {
			environment = Query.newEnvironmentWithDefaultServices(null);
			res = environment;

			for (Object ePkg : EPackageRegistryImpl.createGlobalRegistry().values()) {
				res.registerEPackage((EPackage)ePkg);
			}
		} else {
			res = environment;
		}

		return res;
	}

}
