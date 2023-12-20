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
package org.eclipse.acceleo.aql.ls;

import java.net.URI;

import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoProject;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspace;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;

/**
 * The context of the {@link AcceleoLanguageServer} provided by the runtime that hosts the server. It is used
 * for the integration of the Acceleo editor features into the host IDE.<br/>
 * <br/>
 * FIXME: note that the services brought by this API should instead be implemented as extensions to the LSP
 * protocol. Currently, an Acceleo LS assumes its creator also is able to provide information about the
 * client, essentially binding the server with a particular client.
 * 
 * @author Florent Latombe
 */
public interface IAcceleoLanguageServerContext {

	/**
	 * Gets the {@link AcceleoWorkspace}.
	 * 
	 * @return the {@link AcceleoWorkspace}.
	 */
	AcceleoWorkspace getWorkspace();

	/**
	 * Gets the contents of the given resource {@link URI}.
	 * 
	 * @param resource
	 *            the resource {@link URI}
	 * @return the contents of the given resource {@link URI}
	 */
	String getResourceContents(URI resource);

	/**
	 * Gets the {@link AcceleoProject} containing the given resource {@link URI}.
	 * 
	 * @param resource
	 *            the resource {@link URI}
	 * @return the {@link AcceleoProject} containing the given resource {@link URI} if any, <code>null</code>
	 *         otherwise
	 */
	AcceleoProject getProject(AcceleoWorkspace workspace, URI resource);

	/**
	 * Creates the {@link IQueryWorkspaceQualifiedNameResolver} for the given {@link AcceleoProject}.
	 * 
	 * @param project
	 *            the {@link AcceleoProject}
	 * @return the created {@link IQueryWorkspaceQualifiedNameResolver} for the given {@link AcceleoProject}
	 */
	IQueryWorkspaceQualifiedNameResolver createResolver(AcceleoProject project);

}
