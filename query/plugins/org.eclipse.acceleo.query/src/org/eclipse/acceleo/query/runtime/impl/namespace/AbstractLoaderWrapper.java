/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AbstractLoaderWrapper implements ILoader {

	/**
	 * The wrapped {@link ILoader}.
	 */
	private final ILoader loader;

	public AbstractLoaderWrapper(ILoader loader) {
		this.loader = loader;
	}

	@Override
	public String resourceName(String qualifiedName) {
		return loader.resourceName(qualifiedName);
	}

	@Override
	public String sourceResourceName(String qualifiedName) {
		return loader.sourceResourceName(qualifiedName);
	}

	@Override
	public String qualifiedName(String resourceName) {
		return loader.qualifiedName(resourceName);
	}

	@Override
	public Object load(IQualifiedNameResolver resolver, String qualifiedName) {
		return loader.load(resolver, qualifiedName);
	}

	@Override
	public boolean canHandle(Object object) {
		return loader.canHandle(object);
	}

	@Override
	public Set<IService<?>> getServices(IQualifiedNameLookupEngine lookupEngine, Object object,
			String contextQualifiedName) {
		return loader.getServices(lookupEngine, object, contextQualifiedName);
	}

	@Override
	public List<String> getImports(Object object) {
		return loader.getImports(object);
	}

	@Override
	public List<String> getNsURIImports(Object object) {
		return loader.getNsURIImports(object);
	}

	@Override
	public String getExtends(Object object) {
		return loader.getExtends(object);
	}

	@Override
	public ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, IService<?> service) {
		return loader.getSourceLocation(resolver, service);
	}

	@Override
	public ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, String qualifiedName) {
		return loader.getSourceLocation(resolver, qualifiedName);
	}

}
