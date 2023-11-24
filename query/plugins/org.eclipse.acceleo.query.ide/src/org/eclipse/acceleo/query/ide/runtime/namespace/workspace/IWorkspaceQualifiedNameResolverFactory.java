/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.runtime.namespace.workspace;

import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.core.resources.IProject;

public interface IWorkspaceQualifiedNameResolverFactory {

	/**
	 * Creates an {@link IQueryWorkspaceQualifiedNameResolver} for the given {@link IProject}.
	 * 
	 * @param project
	 *            the {@link IProject}
	 * @param resolver
	 *            the {@link IQualifiedNameResolver} for the {@link IProject}
	 * @param resolverProvider
	 *            the {@link IWorkspaceResolverProvider} to retrieve other {@link IProject} resolver.
	 * @return the created {@link IQueryWorkspaceQualifiedNameResolver}
	 */
	IQueryWorkspaceQualifiedNameResolver createResolver(IProject project, IQualifiedNameResolver resolver,
			IWorkspaceResolverProvider resolverProvider);

}
