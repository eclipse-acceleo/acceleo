/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace.workspace;

import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class QueryWorkspaceQualifiedNameResolver implements IQueryWorkspaceQualifiedNameResolver {

	/**
	 * The {@link IQualifiedNameResolver} for the project
	 */
	private final IQualifiedNameResolver resolver;

	/**
	 * Constructor.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver} to delegate local project resolution to
	 * @param project
	 *            the project for this resolver
	 */
	public QueryWorkspaceQualifiedNameResolver(IQualifiedNameResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public IQualifiedNameResolver getLocalProjectResolver() {
		return resolver;
	}

	/**
	 * Gets the {@link Set} of project {@link IQueryWorkspaceQualifiedNameResolver} dependencies.
	 * 
	 * @return the {@link Set} of project {@link IQueryWorkspaceQualifiedNameResolver} dependencies
	 */
	protected abstract Set<IQueryWorkspaceQualifiedNameResolver> getDependencies();

	@Override
	public IQueryWorkspaceQualifiedNameResolver getDeclarationResolver(String qualifiedName) {
		IQueryWorkspaceQualifiedNameResolver res = null;

		if (resolver.getResolvedQualifiedNames().contains(qualifiedName)) {
			res = this;
		} else {
			for (IQueryWorkspaceQualifiedNameResolver resolver : getDependencies()) {
				if (resolver.getLocalProjectResolver().getResolvedQualifiedNames().contains(qualifiedName)) {
					res = resolver;
					break;
				}
			}
		}

		return res;
	}

	@Override
	public String getQualifiedName(URI uri) {
		return delegateToFirstResolver(r -> r.getQualifiedName(uri));
	}

	@Override
	public URI getURI(String qualifiedName) {
		return delegateToFirstResolver(r -> r.getURI(qualifiedName));
	}

	@Override
	public URI getSourceURI(String qualifiedName) {
		return delegateToFirstResolver(r -> r.getSourceURI(qualifiedName));
	}

	@Override
	public ISourceLocation getSourceLocation(IService<?> service) {
		return delegateToFirstResolver(r -> r.getSourceLocation(service));
	}

	@Override
	public ISourceLocation getSourceLocation(String qualifiedName) {
		return delegateToFirstResolver(r -> r.getSourceLocation(qualifiedName));
	}

	@Override
	public void clear(Set<String> qualifiedNames) {
		resolver.clear(qualifiedNames);
	}

	@Override
	public Object resolve(String qualifiedName) {
		return delegateToFirstResolver(r -> r.resolve(qualifiedName));
	}

	@Override
	public void register(String qualifiedName, Object object) {
		resolver.register(qualifiedName, object);
	}

	@Override
	public String getQualifiedName(Object object) {
		return delegateToFirstResolver(r -> r.getQualifiedName(object));
	}

	@Override
	public URI getURI(Object object) {
		return delegateToFirstResolver(r -> r.getURI(object));
	}

	@Override
	public Set<IService<?>> getServices(IQualifiedNameLookupEngine lookupEngine, Object object,
			String contextQualifiedName) {
		return resolver.getServices(lookupEngine, object, contextQualifiedName);
	}

	@Override
	public String getContextQualifiedName(IService<?> service) {
		return resolver.getContextQualifiedName(service);
	}

	@Override
	public void cleanContextQualifiedName(String qualifiedName) {
		resolver.cleanContextQualifiedName(qualifiedName);
	}

	@Override
	public String getExtend(String qualifiedName) {
		return resolver.getExtend(qualifiedName);
	}

	@Override
	public List<String> getImports(String qualifiedName) {
		return resolver.getImports(qualifiedName);
	}

	@Override
	public Set<String> getDependOn(String qualifiedName) {
		return resolver.getDependOn(qualifiedName);
	}

	@Override
	public Set<String> getAvailableQualifiedNames() {
		return delegateToFirstResolver(r -> r.getAvailableQualifiedNames());
	}

	@Override
	public Set<String> getResolvedQualifiedNames() {
		return resolver.getResolvedQualifiedNames();
	}

	@Override
	public InputStream getInputStream(String resourceName) {
		return delegateToFirstResolver(r -> r.getInputStream(resourceName));
	}

	@Override
	public Class<?> getClass(String qualifiedName) {
		return delegateToFirstResolver(r -> r.getClass(qualifiedName));
	}

	@Override
	public void addLoader(ILoader loader) {
		resolver.addLoader(loader);
	}

	@Override
	public void removeLoader(ILoader loader) {
		resolver.removeLoader(loader);
	}

	@Override
	public void clearLoaders() {
		resolver.clearLoaders();
	}

	@Override
	public URI getBinaryURI(URI sourceURI) {
		return delegateToFirstResolver(r -> r.getBinaryURI(sourceURI));
	}

	/**
	 * Applies the given function to the local {@link IQualifiedNameResolver} and all resolvers for
	 * {@link #getDependencies()} until one return a non <code>null</code> value for the function.
	 * 
	 * @param <R>
	 *            the result kind
	 * @param function
	 *            the {@link BiFunction} to apply to the {@link IQualifiedNameResolver}
	 * @return the first non <code>null</code> result for the function
	 */
	private <A, R> R delegateToFirstResolver(Function<IQualifiedNameResolver, R> function) {
		R res;

		res = function.apply(resolver);
		if (res == null) {
			for (IQueryWorkspaceQualifiedNameResolver resolver : getDependencies()) {
				res = function.apply(resolver.getLocalProjectResolver());
				if (res != null) {
					break;
				}
			}
		}

		return res;
	}

}
