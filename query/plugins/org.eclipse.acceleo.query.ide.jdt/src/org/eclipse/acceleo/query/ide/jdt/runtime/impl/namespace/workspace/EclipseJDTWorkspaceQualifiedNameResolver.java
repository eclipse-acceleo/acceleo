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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.query.ide.jdt.Activator;
import org.eclipse.acceleo.query.ide.runtime.namespace.workspace.IWorkspaceResolverProvider;
import org.eclipse.acceleo.query.runtime.impl.namespace.workspace.QueryWorkspaceQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * {@link IQualifiedNameResolver} for the Eclipse workspace and its {@link IProject}, use the JDT to get
 * project dependencies.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EclipseJDTWorkspaceQualifiedNameResolver extends QueryWorkspaceQualifiedNameResolver {

	/**
	 * The {@link IJavaProject}.
	 */
	private final IJavaProject javaProject;

	/**
	 * The {@link IWorkspaceResolverProvider}.
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
	public EclipseJDTWorkspaceQualifiedNameResolver(IProject project, IQualifiedNameResolver resolver,
			IWorkspaceResolverProvider resolverProvider) {
		super(resolver);
		this.javaProject = JavaCore.create(project);
		this.resolverProvider = resolverProvider;
	}

	@Override
	protected Set<IQueryWorkspaceQualifiedNameResolver> getDependencies() {
		final Set<IQueryWorkspaceQualifiedNameResolver> res = new LinkedHashSet<>();

		try {
			for (IClasspathEntry entry : javaProject.getResolvedClasspath(true)) {
				if (entry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
					final IProject dependencyProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
							entry.getPath().lastSegment());
					final IQueryWorkspaceQualifiedNameResolver resolver = resolverProvider.getResolver(
							dependencyProject);
					if (resolver != null) {
						res.add(resolver);
					}
				}
			}
		} catch (JavaModelException e) {
			Activator.INSTANCE.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					"couldn't resolve project dependencies", e));
		}

		return res;
	}

	@Override
	public Set<IQueryWorkspaceQualifiedNameResolver> getResolversDependOn() {
		final Set<IQueryWorkspaceQualifiedNameResolver> res = new LinkedHashSet<>();

		for (IProject project : javaProject.getProject().getReferencingProjects()) {
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
