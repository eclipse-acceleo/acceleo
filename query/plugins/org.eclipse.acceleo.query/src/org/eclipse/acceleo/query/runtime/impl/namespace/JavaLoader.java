/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;

/**
 * Load Java {@link Class}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class JavaLoader extends AbstractLoader {

	/**
	 * Constructor.
	 * 
	 * @param qualifierSeparator
	 *            the qualifier separator
	 */
	public JavaLoader(String qualifierSeparator) {
		super(qualifierSeparator, "class", "java");
	}

	@Override
	public Object load(IQualifiedNameResolver resolver, String qualifiedName) {
		return resolver.getClass(qualifiedName);
	}

	@Override
	public boolean canHandle(Object object) {
		return object instanceof Class<?>;
	}

	@Override
	public Set<IService<?>> getServices(IQualifiedNameLookupEngine lookupEngine, Object object,
			String contextQualifiedName) {
		return ServiceUtils.getServices(lookupEngine.getQueryEnvironment(), (Class<?>)object);
	}

	@Override
	public List<String> getImports(Object object) {
		return Collections.emptyList();
	}

	@Override
	public String getExtends(Object object) {
		return null;
	}

	@Override
	public ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, IService<?> service) {
		return null;
	}

	@Override
	public ISourceLocation getSourceLocation(IQualifiedNameResolver resolver, String qualifiedName) {
		return null;
	}

}
