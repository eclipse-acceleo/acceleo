/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.runtime.namespace;

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
	 * @return the created {@link IQualifiedNameResolver}
	 */
	IQualifiedNameResolver createResolver(ClassLoader classLoader, IProject project,
			String qualifierSeparator);

}
