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

import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.core.resources.IProject;

/**
 * Provides {@link IQueryWorkspaceQualifiedNameResolver} for a given {@link IProject}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IWorkspaceResolverProvider {

	/**
	 * Gets the {@link IQueryWorkspaceQualifiedNameResolver} for the given {@link IProject}.
	 * 
	 * @param project
	 *            the {@link IProject}
	 * @return the {@link IQueryWorkspaceQualifiedNameResolver} for the given {@link IProject} if any,
	 *         <code>null</code> otherwise
	 */
	IQueryWorkspaceQualifiedNameResolver getResolver(IProject project);

}
