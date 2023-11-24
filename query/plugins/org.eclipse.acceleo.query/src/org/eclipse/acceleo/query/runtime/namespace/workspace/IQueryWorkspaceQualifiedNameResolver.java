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
package org.eclipse.acceleo.query.runtime.namespace.workspace;

import java.util.Set;

import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;

/**
 * {@link IQualifiedNameResolver} for workspace use.
 * 
 * @param <P>
 *            the kind of project
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IQueryWorkspaceQualifiedNameResolver extends IQualifiedNameResolver {

	/**
	 * Gets the project local resolver.
	 * 
	 * @return the project local resolver
	 */
	IQualifiedNameResolver getLocalProjectResolver();

	/**
	 * Gets the {@link Set} of {@link IQueryWorkspaceQualifiedNameResolver} depending on this resolver.
	 * 
	 * @return the {@link Set} of {@link IQueryWorkspaceQualifiedNameResolver} depending on this resolver
	 */
	Set<IQueryWorkspaceQualifiedNameResolver> getResolversDependOn();

	/**
	 * Gets the {@link IQueryWorkspaceQualifiedNameResolver} that resolver the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link IQueryWorkspaceQualifiedNameResolver} that resolver the given qualified name if any,
	 *         <code>null</code> otherwise
	 */
	IQueryWorkspaceQualifiedNameResolver getDeclarationResolver(String qualifiedName);

}
