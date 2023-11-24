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
package org.eclipse.acceleo.query.ide.jdt.runtime.impl.namespace.workspace;

import org.eclipse.acceleo.query.ide.runtime.namespace.workspace.IWorkspaceQualifiedNameResolverFactory;
import org.eclipse.acceleo.query.ide.runtime.namespace.workspace.IWorkspaceResolverProvider;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.core.resources.IProject;

/**
 * The factory for {@link EclipseJDTWorkspaceQualifiedNameResolver}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EclipseJDTWorkspaceQualifiedNameResolverFactory implements IWorkspaceQualifiedNameResolverFactory {

	@Override
	public IQueryWorkspaceQualifiedNameResolver createResolver(IProject project,
			IQualifiedNameResolver resolver, IWorkspaceResolverProvider resolverProvider) {
		return new EclipseJDTWorkspaceQualifiedNameResolver(project, resolver, resolverProvider);
	}

}
