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
package org.eclipse.acceleo.query.ide.runtime.impl.namespace.workspace;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.ide.runtime.namespace.workspace.IWorkspaceResolverProvider;
import org.eclipse.acceleo.query.runtime.impl.namespace.workspace.QueryWorkspaceQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * {@link IQualifiedNameResolver} for the Eclipse workspace and its {@link IProject}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EclipseWorkspaceQualifiedNameResolver extends QueryWorkspaceQualifiedNameResolver {

	/**
	 * The {@link IProject}.
	 */
	private final IProject project;

	/**
	 * the {@link IWorkspaceResolverProvider}.
	 */
	private final IWorkspaceResolverProvider resolverProvider;

	/**
	 * Constructor.
	 * 
	 * @param project
	 *            the {@link IProject} for this resolver
	 * @param resolver
	 *            the {@link IQualifiedNameResolver} to delegate local project resolution to
	 * @param resolverProvider
	 *            the {@link IWorkspaceResolverProvider} to retrieve other {@link IProject} resolver.
	 */
	public EclipseWorkspaceQualifiedNameResolver(IProject project, IQualifiedNameResolver resolver,
			IWorkspaceResolverProvider resolverProvider) {
		super(resolver);
		this.project = project;
		this.resolverProvider = resolverProvider;
	}

	@Override
	protected Set<IQueryWorkspaceQualifiedNameResolver> getDependencies() {
		final Set<IQueryWorkspaceQualifiedNameResolver> res = new LinkedHashSet<>();

		if (project.isAccessible()) {
			try {
				for (IProject project : project.getReferencedProjects()) {
					if (project.exists() && project.isOpen()) {
						final IQueryWorkspaceQualifiedNameResolver resolver = resolverProvider.getResolver(
								project);
						if (resolver != null) {
							res.add(resolver);
						}
					}
				}
			} catch (CoreException e) {
				QueryPlugin.INSTANCE.log(new Status(IStatus.ERROR, QueryPlugin.PLUGIN_ID,
						"can't resolve project dependencies for " + project.getName(), e));
			}
		} else {
			for (IProject workspaceProject : project.getWorkspace().getRoot().getProjects()) {
				if (workspaceProject.isAccessible()) {
					final IQueryWorkspaceQualifiedNameResolver resolver = resolverProvider.getResolver(
							workspaceProject);
					if (resolver != null) {
						res.add(resolver);
					}
				}
			}
		}

		return res;
	}

	@Override
	public Set<IQueryWorkspaceQualifiedNameResolver> getResolversDependOn() {
		final Set<IQueryWorkspaceQualifiedNameResolver> res = new LinkedHashSet<>();

		for (IProject project : project.getReferencingProjects()) {
			if (project.exists()) {
				final IQueryWorkspaceQualifiedNameResolver resolver = resolverProvider.getResolver(project);
				if (resolver != null) {
					res.add(resolver);
				}
			}
		}

		return res;
	}

}
