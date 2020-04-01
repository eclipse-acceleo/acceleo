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
package org.eclipse.acceleo.aql.ls;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

/**
 * Acceleo <a href="https://microsoft.github.io/language-server-protocol/">LSP</a> server.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoLanguageServer implements LanguageServer, LanguageClientAware {

	private final AcceleoTextDocumentService textDocumentService = new AcceleoTextDocumentService();

	private final AcceleoWorkspaceService workspaceService = new AcceleoWorkspaceService();

	/**
	 * The {@link LanguageClient}.
	 */
	private LanguageClient client;

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		final ServerCapabilities capabilities = new ServerCapabilities();

		final CompletionOptions completionProvider = new CompletionOptions();
		completionProvider.setResolveProvider(true);
		capabilities.setCompletionProvider(completionProvider);

		final InitializeResult res = new InitializeResult(capabilities);
		return CompletableFuture.completedFuture(res);
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	@Override
	public TextDocumentService getTextDocumentService() {
		return textDocumentService;
	}

	@Override
	public WorkspaceService getWorkspaceService() {
		return workspaceService;
	}

	@Override
	public void connect(LanguageClient client) {
		this.client = client;
		textDocumentService.connect(client);
		workspaceService.connect(client);
	}

}
