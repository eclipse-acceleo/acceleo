/*******************************************************************************
 * Copyright (c) 2020, 2025 Obeo.
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
	 * The class extension.
	 */
	public static final String CLASS = "class";

	/**
	 * The java extension.
	 */
	public static final String JAVA = "java";

	/**
	 * Tells if the {@link IService} will be used in a workspace.
	 */
	private boolean forWorkspace;

	/**
	 * Constructor.
	 * 
	 * @param qualifierSeparator
	 *            the qualifier separator
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 */
	public JavaLoader(String qualifierSeparator, boolean forWorkspace) {
		super(qualifierSeparator, CLASS, JAVA);
		this.forWorkspace = forWorkspace;
	}

	@Override
	public Class<?> load(IQualifiedNameResolver resolver, String qualifiedName) {
		return resolver.getClass(qualifiedName);
	}

	@Override
	public boolean canHandle(Object object) {
		return object instanceof Class<?>;
	}

	@Override
	public Set<IService<?>> getServices(IQualifiedNameLookupEngine lookupEngine, Object object,
			String contextQualifiedName) {
		return ServiceUtils.getServices(lookupEngine.getQueryEnvironment(), (Class<?>)object, forWorkspace);
	}

	@Override
	public List<String> getImports(Object object) {
		return Collections.emptyList();
	}

	@Override
	public List<String> getNsURIImports(Object object) {
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
