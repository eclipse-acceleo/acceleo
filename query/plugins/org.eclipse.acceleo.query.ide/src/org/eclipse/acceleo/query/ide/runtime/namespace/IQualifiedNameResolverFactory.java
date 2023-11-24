/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.runtime.namespace;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.core.resources.IProject;

public interface IQualifiedNameResolverFactory {

	/**
	 * Creates an {@link IQualifiedNameResolver} for the given {@link IProject}.
	 * 
	 * @param classLoader
	 *            the {@link ClassLoader}
	 * @param project
	 *            the {@link IProject}
	 * @param qualifierSeparator
	 *            the qualifier name separator
	 * @param forWorkspace
	 *            <code>true</code> for workspace use, local project resolution only
	 * @return the created {@link IQualifiedNameResolver}
	 */
	IQualifiedNameResolver createResolver(ClassLoader classLoader, IProject project,
			String qualifierSeparator, boolean forWorkspace);

	/**
	 * Creates a Java {@link ILoader} with the given qualifier name separator.
	 * 
	 * @param qualifierSeparator
	 *            the qualifier name separator
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 * @return the created Java {@link ILoader}
	 */
	ILoader createJavaLoader(String qualifierSeparator, boolean forWorkspace);

}
