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

import java.net.URI;

/**
 * A workspace invalidates resolvers according to outside changes.
 * 
 * @param <P>
 *            the kind of project
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IQueryWorkspace<P> {

	/**
	 * Gets the {@link IQueryWorkspaceQualifiedNameResolver} for the given project.
	 * 
	 * @param project
	 *            the project
	 * @return the {@link IQueryWorkspaceQualifiedNameResolver} for the given project if any,
	 *         <code>null</code> otherwise
	 */
	IQueryWorkspaceQualifiedNameResolver getResolver(P project);

	/**
	 * Adds a new project.
	 * 
	 * @param project
	 *            the project to add
	 */
	void addProject(P project);

	/**
	 * Removes a project.
	 * 
	 * @param project
	 *            the project to remove
	 */
	void removeProject(P project);

	/**
	 * Adds the given resource {@link URI} to the given project.
	 * 
	 * @param project
	 *            the project
	 * @param resource
	 *            the resource {@link URI} t add
	 * @return the qualified name for the given {@link URI} if any, <code>null</code> otherwise
	 */
	String addResource(P project, URI resource);

	/**
	 * Removes the given resource {@link URI} from the given project.
	 * 
	 * @param project
	 *            the project
	 * @param resource
	 *            the resource {@link URI} to remove
	 * @return the qualified name for the given {@link URI} if any, <code>null</code> otherwise
	 */
	String removeResource(P project, URI resource);

	/**
	 * Moves a resource.
	 * 
	 * @param sourceProject
	 *            the source project
	 * @param sourceResource
	 *            the source resource {@link URI}
	 * @param targetProject
	 *            the target project
	 * @param targetResource
	 *            the target resource {@link URI}
	 * @return the qualified name for the given target {@link URI} if any, <code>null</code> otherwise
	 */
	String moveResource(P sourceProject, URI sourceResource, P targetProject, URI targetResource);

	/**
	 * Changes the given resource {@link URI} from the given project.
	 * 
	 * @param project
	 *            the project
	 * @param resource
	 *            the resource {@link URI} to change
	 * @return the qualified name for the given {@link URI} if any, <code>null</code> otherwise
	 */
	String changeResource(P project, URI resource);

}
