/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import java.util.List;

/**
 * Interface that can be implemented to provides its own {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IServiceProvider {

	/**
	 * Gets services offered by this class.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 * @return services offered by this class
	 * @since 8.0.2
	 */
	List<IService<?>> getServices(IReadOnlyQueryEnvironment queryEnvironment, boolean forWorkspace);

}
