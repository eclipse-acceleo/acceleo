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
package org.eclipse.acceleo.query.runtime.impl.namespace.workspace;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspace;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;

/**
 * A workspace invalidates resolvers according to outside changes.
 * 
 * @param <P>
 *            the kind of project
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class QueryWorkspace<P> implements IQueryWorkspace<P> {

	/**
	 * The mapping from a project to its resolver.
	 */
	private final Map<P, IQueryWorkspaceQualifiedNameResolver> projectToResolver = new LinkedHashMap<>();

	/**
	 * The mapping from a resolver to its project.
	 */
	private final Map<IQueryWorkspaceQualifiedNameResolver, P> resolverToProject = new LinkedHashMap<>();

	@Override
	public IQueryWorkspaceQualifiedNameResolver getResolver(P project) {
		return projectToResolver.get(project);
	}

	@Override
	public void addProject(P project) {
		final IQueryWorkspaceQualifiedNameResolver resolver = createResolver(project);
		projectToResolver.put(project, resolver);
		resolverToProject.put(resolver, project);
		for (IQueryWorkspaceQualifiedNameResolver dependencyResolver : resolver.getResolversDependOn()) {
			P dependency = resolverToProject.get(dependencyResolver);
			// at this point we don't know what is in the added project
			// so we revalidate all that have been resolved in the dependency project
			final Set<String> resolvedQualifiedNames = dependencyResolver.getResolvedQualifiedNames();
			final Set<String> dependsOn = invalidate(dependency, dependencyResolver, resolvedQualifiedNames);
			validate(dependency, dependencyResolver, dependsOn);
		}
	}

	@Override
	public void removeProject(P project) {
		final IQueryWorkspaceQualifiedNameResolver resolver = projectToResolver.remove(project);
		resolverToProject.remove(resolver);
		for (IQueryWorkspaceQualifiedNameResolver dependencyResolver : resolver.getResolversDependOn()) {
			final P dependency = resolverToProject.get(dependencyResolver);
			clearQualifiedNames(dependency, dependencyResolver, resolver.getResolvedQualifiedNames());
		}
	}

	@Override
	public String addResource(P project, URI resource) {
		final IQueryWorkspaceQualifiedNameResolver resolver = getResolver(project);
		final String qualifiedName = resolver.getQualifiedName(resource);
		// this resource can be resolved
		if (qualifiedName != null) {
			// clean previous resolved values to resolve the new one
			resolver.clear(Collections.singleton(qualifiedName));
			validate(project, resolver, qualifiedName);
			Set<String> qualifiedNames = Collections.singleton(qualifiedName);
			final Set<P> projectsToUpdate = new LinkedHashSet<P>();
			projectsToUpdate.add(project);
			for (IQueryWorkspaceQualifiedNameResolver dependencyResolver : resolver.getResolversDependOn()) {
				projectsToUpdate.add(resolverToProject.get(dependencyResolver));
			}
			for (P projectToUpdate : projectsToUpdate) {
				final Set<String> dependsOn = invalidate(projectToUpdate, getResolver(projectToUpdate),
						qualifiedNames);
				validate(projectToUpdate, getResolver(projectToUpdate), dependsOn);
			}
		}

		return qualifiedName;
	}

	@Override
	public String removeResource(P project, URI resource) {
		final IQueryWorkspaceQualifiedNameResolver resolver = getResolver(project);
		final String qualifiedName = resolver.getQualifiedName(resource);
		// this resource has been resolved before
		if (qualifiedName != null && resolver.getResolvedQualifiedNames().contains(qualifiedName)) {
			final Object resolved = resolver.resolve(qualifiedName);
			final Set<String> qualifiedNames = new LinkedHashSet<>();
			qualifiedNames.add(qualifiedName);
			if (needNewResolverOnChange(resolved)) {
				qualifiedNames.addAll(resolver.getResolvedQualifiedNames());
				replaceResolver(project, resolver);
			}
			propagateChanges(project, qualifiedNames);
		}

		return qualifiedName;
	}

	/**
	 * Propagates the changed qualified name for the given project. It validates the qualified names in the
	 * project itself including depending qualified names and then to depending projects
	 * 
	 * @param project
	 *            the project
	 * @param qualifiedNames
	 *            the {@link Set} of qualified names
	 */
	public void propagateChanges(P project, final Set<String> qualifiedNames) {
		final Set<P> projectsToUpdate = new LinkedHashSet<P>();
		projectsToUpdate.add(project);
		for (IQueryWorkspaceQualifiedNameResolver dependencyResolver : getResolver(project)
				.getResolversDependOn()) {
			projectsToUpdate.add(resolverToProject.get(dependencyResolver));
		}
		for (P projectToUpdate : projectsToUpdate) {
			clearQualifiedNames(projectToUpdate, getResolver(projectToUpdate), qualifiedNames);
		}
	}

	public void registerAndPropagateChanges(P project, String qualifiedName, Object newResolved) {
		final Set<P> projectsToUpdate = new LinkedHashSet<P>();
		projectsToUpdate.add(project);
		for (IQueryWorkspaceQualifiedNameResolver dependencyResolver : getResolver(project)
				.getResolversDependOn()) {
			projectsToUpdate.add(resolverToProject.get(dependencyResolver));
		}
		final Set<String> qualifiedNames = Collections.singleton(qualifiedName);
		for (P projectToUpdate : projectsToUpdate) {
			final IQueryWorkspaceQualifiedNameResolver resolverToUpdate = getResolver(projectToUpdate);
			clearQualifiedNames(projectToUpdate, resolverToUpdate, qualifiedNames);
			final Set<String> dependsOn = invalidate(project, resolverToUpdate, qualifiedNames);
			resolverToUpdate.clear(qualifiedNames);
			if (projectToUpdate == project) {
				resolverToUpdate.register(qualifiedName, newResolved);
			}
			for (String dependent : dependsOn) {
				resolverToUpdate.resolve(dependent);
			}
			validate(project, resolverToUpdate, dependsOn);
		}
	}

	@Override
	public String moveResource(P sourceProject, URI sourceResource, P targetProject, URI targetResource) {
		// first remove without validating
		final IQueryWorkspaceQualifiedNameResolver sourceResolver = getResolver(sourceProject);
		final String sourceQualifiedName = sourceResolver.getQualifiedName(sourceResource);
		// the source resource has been resolved before
		final Map<P, Set<String>> dependsOn = new LinkedHashMap<>();
		if (sourceQualifiedName != null && sourceResolver.getResolvedQualifiedNames().contains(
				sourceQualifiedName)) {
			final Object resolved = sourceResolver.resolve(sourceQualifiedName);
			final Set<String> qualifiedNames = new LinkedHashSet<>();
			qualifiedNames.add(sourceQualifiedName);
			if (needNewResolverOnChange(resolved)) {
				qualifiedNames.addAll(sourceResolver.getResolvedQualifiedNames());
				replaceResolver(sourceProject, sourceResolver);
			}
			final Set<P> projectsToUpdate = new LinkedHashSet<P>();
			projectsToUpdate.add(sourceProject);
			for (IQueryWorkspaceQualifiedNameResolver dependencyResolver : getResolver(sourceProject)
					.getResolversDependOn()) {
				projectsToUpdate.add(resolverToProject.get(dependencyResolver));
			}
			for (P projectToUpdate : projectsToUpdate) {
				dependsOn.put(projectToUpdate, invalidate(projectToUpdate, getResolver(projectToUpdate),
						qualifiedNames));
				sourceResolver.clear(qualifiedNames);
			}
		}
		// then add without validating
		final IQueryWorkspaceQualifiedNameResolver targetResolver = getResolver(targetProject);
		final String targetQualifiedName = targetResolver.getQualifiedName(targetResource);
		// the target resource can be resolved
		if (targetQualifiedName != null) {
			// clean previous resolved values to resolve the new one
			targetResolver.clear(Collections.singleton(targetQualifiedName));
			validate(targetProject, targetResolver, targetQualifiedName);
			final Set<P> projectsToUpdate = new LinkedHashSet<P>();
			projectsToUpdate.add(targetProject);
			for (IQueryWorkspaceQualifiedNameResolver dependencyResolver : getResolver(targetProject)
					.getResolversDependOn()) {
				projectsToUpdate.add(resolverToProject.get(dependencyResolver));
			}
			Set<String> qualifiedNames = Collections.singleton(targetQualifiedName);
			for (P projectToUpdate : projectsToUpdate) {
				final Set<String> dependsOnForProject = dependsOn.computeIfAbsent(targetProject,
						p -> new LinkedHashSet<>());
				dependsOnForProject.addAll(invalidate(projectToUpdate, getResolver(projectToUpdate),
						qualifiedNames));
			}
		}
		// then validate everything
		for (Entry<P, Set<String>> entry : dependsOn.entrySet()) {
			validate(entry.getKey(), getResolver(entry.getKey()), entry.getValue());
		}

		return targetQualifiedName;
	}

	@Override
	public String changeResource(P project, URI resource) {
		final IQueryWorkspaceQualifiedNameResolver resolver = getResolver(project);
		final String qualifiedName = resolver.getQualifiedName(resource);
		// this resource has been resolved before
		if (qualifiedName != null && resolver.getResolvedQualifiedNames().contains(qualifiedName)) {
			final Object resolved = resolver.resolve(qualifiedName);
			final Set<String> qualifiedNames = new LinkedHashSet<>();
			qualifiedNames.add(qualifiedName);
			if (needNewResolverOnChange(resolved)) {
				qualifiedNames.addAll(resolver.getResolvedQualifiedNames());
				replaceResolver(project, resolver);
			}
			updateResourceContents(project, getResolver(project), resource);
			validate(project, resolver, qualifiedName);
			propagateChanges(project, qualifiedNames);
		}

		return qualifiedName;
	}

	/**
	 * Updates the contents for the given resource {@link URI}.
	 * 
	 * @param project
	 *            the project
	 * @param resolver
	 *            the project resolver
	 * @param resource
	 *            the resource {@link URI}
	 */
	protected abstract void updateResourceContents(P project, IQualifiedNameResolver resolver, URI resource);

	/**
	 * Replaces the given {@link IQualifiedNameResolver} for the given project.
	 * 
	 * @param project
	 *            the project
	 * @param resolver
	 *            the current {@link IQualifiedNameResolver}
	 */
	protected void replaceResolver(P project, IQualifiedNameResolver resolver) {
		final IQueryWorkspaceQualifiedNameResolver newResolver = createResolver(project);
		for (String qualifiedName : resolver.getResolvedQualifiedNames()) {
			final Object resolved = resolver.resolve(qualifiedName);
			if (needNewResolverOnChange(resolved)) {
				newResolver.resolve(qualifiedName);
			} else {
				newResolver.register(qualifiedName, resolved);
			}
		}
		projectToResolver.put(project, newResolver);
		resolverToProject.put(newResolver, project);
	}

	/**
	 * Clears the {@link Set} of qualified names for the given project and its {@link IQualifiedNameResolver}.
	 * 
	 * @param project
	 *            the project
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param qualifiedNames
	 *            the {@link Set} of qualified names
	 */
	protected void clearQualifiedNames(P project, IQualifiedNameResolver resolver,
			Set<String> qualifiedNames) {
		final Set<String> dependsOn = invalidate(project, resolver, qualifiedNames);
		resolver.clear(qualifiedNames);
		for (String qualifiedName : qualifiedNames) {
			resolver.resolve(qualifiedName);
		}
		validate(project, resolver, dependsOn);
	}

	/**
	 * Invalidates the given {@link Set} of qualified names for the given project and its
	 * {@link IQualifiedNameResolver}
	 * 
	 * @param project
	 *            the project
	 * @param resolver
	 *            the {@link IQualifiedNameResolver} for this project
	 * @param qualifiedNames
	 *            the {@link Set} of qualified names to invalidate
	 * @return the {@link Set} of dependencies of invalidated qualified names in the given project
	 */
	protected Set<String> invalidate(P project, IQualifiedNameResolver resolver, Set<String> qualifiedNames) {
		final Set<String> res = new LinkedHashSet<>();

		for (String qualifiedName : qualifiedNames) {
			res.addAll(resolver.getDependOn(qualifiedName));
			final IQualifiedNameLookupEngine lookupEngine = getLookupEngine(project, resolver, qualifiedName);
			if (lookupEngine != null) {
				lookupEngine.clearContext(qualifiedName);
			}
		}
		for (String qualifiedName : res) {
			final IQualifiedNameLookupEngine lookupEngine = getLookupEngine(project, resolver, qualifiedName);
			if (lookupEngine != null) {
				lookupEngine.clearContext(qualifiedName);
			}
		}

		return res;
	}

	/**
	 * Validates the given qualified names for the given project and {@link IQualifiedNameResolver}.
	 * 
	 * @param project
	 *            the project
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param qualifiedNames
	 *            the {@link Set} of qualified names
	 */
	protected void validate(P project, IQualifiedNameResolver resolver, final Set<String> qualifiedNames) {
		for (String qualifiedName : qualifiedNames) {
			validate(project, resolver, qualifiedName);
		}
	}

	/**
	 * Tells if the given resolved object need to reinitialize a new {@link IQualifiedNameResolver} if
	 * changed.
	 * 
	 * @param resolved
	 *            the resolved {@link Object}
	 * @return <code>true</code> if the given resolved object need to reinitialize a new
	 *         {@link IQualifiedNameResolver} if changed, <code>false</code> otherwise
	 */
	protected boolean needNewResolverOnChange(Object resolved) {
		return resolved instanceof Class<?>;
	}

	/**
	 * Validates the given qualified name for the given project and its {@link IQualifiedNameResolver}.
	 * 
	 * @param project
	 *            the project
	 * @param resolver
	 *            the {@link IQualifiedNameResolver} for the passed project
	 * @param qualifiedName
	 *            the qualified name to validate
	 */
	protected abstract void validate(P project, IQualifiedNameResolver resolver, String qualifiedName);

	/**
	 * Gets the lookup engine for the given qualified name in the given project.
	 * 
	 * @param project
	 *            the project
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param qualifiedName
	 *            the qualified name
	 * @return the lookup engine for the given qualified name in the given project if any, <code>null</code>
	 *         otherwise
	 */
	protected abstract IQualifiedNameLookupEngine getLookupEngine(P project, IQualifiedNameResolver resolver,
			String qualifiedName);

	/**
	 * Creates the {@link IQueryWorkspaceQualifiedNameResolver} for the given project.
	 * 
	 * @param project
	 *            the project
	 * @return the created {@link IQueryWorkspaceQualifiedNameResolver} for the given project
	 */
	protected abstract IQueryWorkspaceQualifiedNameResolver createResolver(P project);

}
