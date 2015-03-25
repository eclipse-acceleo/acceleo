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

/**
 * Interface that can be implemented by service {@link Class} to provides its own {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IServiceProvider {

	/**
	 * Gets services offered by this class.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @return services offered by this class
	 * @throws InvalidAcceleoPackageException
	 *             if the specified class doesn't follow the acceleo package rules.
	 */
	List<IService> getServices(IReadOnlyQueryEnvironment queryEnvironment)
			throws InvalidAcceleoPackageException;

}
