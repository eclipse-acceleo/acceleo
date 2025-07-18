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
package org.eclipse.acceleo.query.runtime.impl.namespace.workspace;

import org.eclipse.acceleo.query.runtime.impl.namespace.AbstractLoaderWrapper;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspace;
import org.eclipse.emf.ecore.EPackage;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class WorkspaceJavaLoaderWrapper extends AbstractLoaderWrapper {

	/**
	 * The {@link IQueryWorkspace}.
	 */
	private final IQueryWorkspace<?> workspace;

	/**
	 * Constructor.
	 * 
	 * @param loader
	 *            the wrapped {@link JavaLoader}
	 * @param workspace
	 *            the {@link QueryWorkspace}
	 */
	public WorkspaceJavaLoaderWrapper(ILoader loader, IQueryWorkspace<?> workspace) {
		super(loader);
		this.workspace = workspace;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object load(IQualifiedNameResolver resolver, String qualifiedName) {
		final Object res = super.load(resolver, qualifiedName);

		if (res instanceof Class<?> && ((Class<?>)res).isInterface() && EPackage.class.isAssignableFrom(
				(Class<?>)res)) {
			workspace.getEPackageRegistry().register((Class<? extends EPackage>)res, qualifiedName);
		}

		return res;
	}

}
