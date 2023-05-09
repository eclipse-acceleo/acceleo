/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls;

import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspace;

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
public interface AcceleoLanguageServerContext {

	/**
	 * Creates an {@link AcceleoWorkspace}.
	 * 
	 * @return the newly-created {@link AcceleoWorkspace}.
	 */
	AcceleoWorkspace createWorkspace();

	/**
	 * Called when the owner {@link AcceleoLanguageServer} shutdowns.
	 * 
	 * @param workspaceToDelete
	 *            the (non-{@code null}) {@link AcceleoWorkspace} to delete.
	 */
	void deleteWorkspace(AcceleoWorkspace workspaceToDelete);

}
