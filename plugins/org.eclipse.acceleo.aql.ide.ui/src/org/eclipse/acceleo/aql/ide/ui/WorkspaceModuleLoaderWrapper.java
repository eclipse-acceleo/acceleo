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
package org.eclipse.acceleo.aql.ide.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.runtime.impl.namespace.AbstractLoaderWrapper;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspace;

public class WorkspaceModuleLoaderWrapper extends AbstractLoaderWrapper {

	/**
	 * The {@link IQueryWorkspace}.
	 */
	private final IQueryWorkspace<?> workspace;

	/**
	 * Constructor.
	 * 
	 * @param loader
	 *            the wrapped {@link ModuleLoader}
	 * @param workspace
	 */
	public WorkspaceModuleLoaderWrapper(ModuleLoader loader, IQueryWorkspace<?> workspace) {
		super(loader);
		this.workspace = workspace;
	}

	@Override
	public Object load(IQualifiedNameResolver resolver, String qualifiedName) {
		final Object res = super.load(resolver, qualifiedName);

		if (res instanceof Module) {
			final Set<String> nsURIs = new HashSet<>();
			for (Metamodel metamodel : ((Module)res).getMetamodels()) {
				final String nsURI = metamodel.getReferencedPackage();
				if (nsURI != null) {
					nsURIs.add(nsURI);
				}
			}
			workspace.getEPackageRegistry().setDependencies(qualifiedName, nsURIs);
		}

		return res;
	}

}
