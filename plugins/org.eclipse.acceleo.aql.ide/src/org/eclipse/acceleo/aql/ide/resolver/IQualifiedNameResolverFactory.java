/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.resolver;

import org.eclipse.acceleo.aql.resolver.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.core.resources.IProject;

public interface IQualifiedNameResolverFactory {

	/**
	 * Creates an {@link IQualifiedNameResolver} for the given {@link IProject}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param project
	 *            the {@link IProject}
	 * @return the created {@link IQualifiedNameResolver}
	 */
	IQualifiedNameResolver createResolver(IReadOnlyQueryEnvironment queryEnvironment, IProject project);

}
