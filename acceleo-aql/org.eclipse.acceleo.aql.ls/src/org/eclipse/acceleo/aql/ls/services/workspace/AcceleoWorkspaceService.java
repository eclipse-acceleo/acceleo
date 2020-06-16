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
package org.eclipse.acceleo.aql.ls.services.workspace;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.WorkspaceService;

public class AcceleoWorkspaceService implements WorkspaceService, LanguageClientAware {

	/**
	 * The owning {@link AcceleoLanguageServer} of this service.
	 */
	private final AcceleoLanguageServer owner;

	/**
	 * The current client.
	 */
	private LanguageClient languageClient;

	/**
	 * Creates a new {@link AcceleoWorkspaceService}.
	 * 
	 * @param acceleoLanguageServer
	 *            the (non-{@code null}) owning {@link AcceleoLanguageServer}.
	 */
	public AcceleoWorkspaceService(AcceleoLanguageServer acceleoLanguageServer) {
		this.owner = Objects.requireNonNull(acceleoLanguageServer);
	}

	@Override
	public void connect(LanguageClient newLanguageClient) {
		this.languageClient = newLanguageClient;
	}

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		// TODO Auto-generated method stub
		System.out.println(params);
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		// TODO Auto-generated method stub
		System.out.println(params);
	}

	@Override
	public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
		// TODO Auto-generated method stub
		WorkspaceService.super.didChangeWorkspaceFolders(params);
	}

	@Override
	public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
		// TODO Auto-generated method stub
		return WorkspaceService.super.executeCommand(params);
	}

	@Override
	public CompletableFuture<List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params) {
		// TODO Auto-generated method stub
		return WorkspaceService.super.symbol(params);
	}

}
