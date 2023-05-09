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
package org.eclipse.acceleo.aql.ide.ui;

import java.util.Objects;

import org.eclipse.acceleo.aql.ls.AcceleoLanguageServerContext;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspace;
import org.eclipse.core.resources.IWorkspace;

/**
 * Eclipse-specific implementation of {@link AcceleoLanguageServerContext}.
 * 
 * @author Florent Latombe
 */
public class EclipseAcceleoLanguageServerContext implements AcceleoLanguageServerContext {

	/**
	 * The client {@link IWorkspace}.
	 */
	private final IWorkspace clientWorkspace;

	/**
	 * The {@link EclipseWorkspace2AcceleoWorkspace} that allows us to bridge the {@link IWorkspace} and
	 * {@link AcceleoWorkspace} APIs.
	 */
	private final EclipseWorkspace2AcceleoWorkspace workspaceSynchronizer = new EclipseWorkspace2AcceleoWorkspace();

	/**
	 * The constructor.
	 * 
	 * @param clientWorkspace
	 *            the (non-{@code null}) contextual {@link IWorkspace}.
	 */
	public EclipseAcceleoLanguageServerContext(IWorkspace clientWorkspace) {
		this.clientWorkspace = Objects.requireNonNull(clientWorkspace);
	}

	@Override
	public AcceleoWorkspace createWorkspace() {
		return this.workspaceSynchronizer.createAcceleoWorkspace(this.clientWorkspace);
	}

	@Override
	public void deleteWorkspace(AcceleoWorkspace acceleoWorkspaceToDelete) {
		this.workspaceSynchronizer.deleteAcceleoWorkspace(acceleoWorkspaceToDelete);
	}

}
