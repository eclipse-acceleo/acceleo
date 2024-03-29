/*******************************************************************************
 * Copyright (c) 2020, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.services.workspace;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerServicesUtils;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.aql.ls.services.workspace.command.DocumentRangeParams;
import org.eclipse.acceleo.aql.ls.services.workspace.command.ExtractTemplateCommand;
import org.eclipse.acceleo.aql.ls.services.workspace.command.WrapInForCommand;
import org.eclipse.acceleo.aql.ls.services.workspace.command.WrapInIfCommand;
import org.eclipse.acceleo.aql.ls.services.workspace.command.WrapInLetCommand;
import org.eclipse.acceleo.aql.ls.services.workspace.command.WrapInProtectedCommand;
import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.WorkspaceSymbol;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.WorkspaceService;

public class AcceleoWorkspaceService implements WorkspaceService, LanguageClientAware {

	/**
	 * The owning {@link AcceleoLanguageServer} of this service.
	 */
	private final AcceleoLanguageServer server;

	/**
	 * The current client.
	 */
	private LanguageClient languageClient;

	/**
	 * The current {@link AcceleoWorkspace}.
	 */
	private final AcceleoWorkspace acceleoWorkspace;

	/**
	 * Creates a new {@link AcceleoWorkspaceService}.
	 * 
	 * @param acceleoLanguageServer
	 *            the (non-{@code null}) owning {@link AcceleoLanguageServer}.
	 */
	public AcceleoWorkspaceService(AcceleoLanguageServer acceleoLanguageServer) {
		this.server = Objects.requireNonNull(acceleoLanguageServer);
		this.acceleoWorkspace = this.server.connectWorkspace();
	}

	@Override
	public void connect(LanguageClient newLanguageClient) {
		this.languageClient = newLanguageClient;
	}

	/**
	 * Disconnects from the {@link LanguageClient}.
	 */
	public void disconnect() {
		this.languageClient = null;
	}

	/**
	 * Provides the current {@link AcceleoWorkspace}.
	 * 
	 * @return the (non-{@code null}) current {@link AcceleoWorkspace}.
	 */
	public AcceleoWorkspace getWorkspace() {
		return this.acceleoWorkspace;
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
		final DocumentRangeParams documentRangeParams = new Gson().fromJson((JsonObject)params.getArguments()
				.get(0), DocumentRangeParams.class);
		final Range range = documentRangeParams.getRange();
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(documentRangeParams
				.getTextDocument().getUri());
		final AcceleoTextDocument document = getWorkspace().getDocument(textDocumentUri);

		final CompletableFuture<Object> workspaceEdit;
		final String label;
		switch (params.getCommand()) {
			case AcceleoLanguageServer.EXTRACT_TEMPLATE_COMMAND:
				workspaceEdit = CompletableFutures.computeAsync(canceler -> {
					canceler.checkCanceled();
					return new ExtractTemplateCommand().exec(document, range);
				});
				label = "Extract Template";
				break;

			case AcceleoLanguageServer.WRAP_IN_FOR_COMMAND:
				workspaceEdit = CompletableFutures.computeAsync(canceler -> {
					canceler.checkCanceled();
					return new WrapInForCommand().exec(document, range);
				});
				label = "Wrap in For";
				break;

			case AcceleoLanguageServer.WRAP_IN_IF_COMMAND:
				workspaceEdit = CompletableFutures.computeAsync(canceler -> {
					canceler.checkCanceled();
					return new WrapInIfCommand().exec(document, range);
				});
				label = "Wrap in If";
				break;

			case AcceleoLanguageServer.WRAP_IN_LET_COMMAND:
				workspaceEdit = CompletableFutures.computeAsync(canceler -> {
					canceler.checkCanceled();
					return new WrapInLetCommand().exec(document, range);
				});
				label = "Wrap in Let";
				break;

			case AcceleoLanguageServer.WRAP_IN_PROTECTED_COMMAND:
				workspaceEdit = CompletableFutures.computeAsync(canceler -> {
					canceler.checkCanceled();
					return new WrapInProtectedCommand().exec(document, range);
				});
				label = "Wrap in Protected";
				break;

			default:
				WorkspaceService.super.executeCommand(params);
				workspaceEdit = null;
				label = null;
				break;
		}

		if (workspaceEdit != null) {
			workspaceEdit.thenAccept(we -> {
				try {
					languageClient.applyEdit(new ApplyWorkspaceEditParams((WorkspaceEdit)we, label)).get(1,
							TimeUnit.SECONDS);
				} catch (InterruptedException | ExecutionException | TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}

		return workspaceEdit;
	}

	@Override
	public CompletableFuture<Either<List<? extends SymbolInformation>, List<? extends WorkspaceSymbol>>> symbol(
			WorkspaceSymbolParams params) {
		// TODO Auto-generated method stub
		return WorkspaceService.super.symbol(params);
	}

}
