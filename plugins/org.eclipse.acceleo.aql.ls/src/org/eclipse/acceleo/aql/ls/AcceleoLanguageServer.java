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
package org.eclipse.acceleo.aql.ls;

import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocumentService;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspace;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspaceService;
import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.RenameOptions;
import org.eclipse.lsp4j.SaveOptions;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextDocumentSyncOptions;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.LanguageServer;

/**
 * Acceleo <a href="https://microsoft.github.io/language-server-protocol/">LSP</a> server.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoLanguageServer implements LanguageServer, LanguageClientAware {

	/**
	 * The text-document-related service.
	 */
	private final AcceleoTextDocumentService textDocumentService;

	/**
	 * The workspace-related service.
	 */
	private final AcceleoWorkspaceService workspaceService;

	/**
	 * The root {@link IAcceleoLanguageServerContext} of this server.
	 */
	private final IAcceleoLanguageServerContext acceleoLanguageServerContext;

	/**
	 * The {@link Instant} at which this server was created.
	 */
	private final Instant creationTimestamp;

	/**
	 * The current language client.
	 */
	private LanguageClient languageClient;

	/**
	 * Creates a new {@link AcceleoLanguageServer}.
	 * 
	 * @param acceleoLanguageServerContext
	 *            the (non-{@code null}) root {@link IAcceleoLanguageServerContext} for this server.
	 */
	public AcceleoLanguageServer(IAcceleoLanguageServerContext acceleoLanguageServerContext) {
		this.creationTimestamp = Instant.now();
		this.acceleoLanguageServerContext = Objects.requireNonNull(acceleoLanguageServerContext);

		this.workspaceService = new AcceleoWorkspaceService(this);
		this.textDocumentService = new AcceleoTextDocumentService(this);
	}

	/**
	 * Provides a {@link String} label to identify this language server.
	 * 
	 * @return the label for this language server.
	 */
	public String getLabel() {
		return this.getClass().getSimpleName() + "@" + this.hashCode() + "_" + this.creationTimestamp;
	}

	/**
	 * Creates the {@link AcceleoWorkspace}.
	 * 
	 * @return the newly-created {@link AcceleoWorkspace}.
	 */
	public AcceleoWorkspace createWorkspace() {
		AcceleoWorkspace workspace = this.acceleoLanguageServerContext.createWorkspace();
		workspace.setOwner(this);
		return workspace;
	}

	/**
	 * Publishes all {@link AcceleoTextDocument} validation result in the workspace of this server.
	 */
	private void publishAll() {
		if (this.getWorkspace() != null) {
			this.getWorkspace().getAllTextDocuments().forEach(textDocument -> textDocumentService
					.publishValidationResults(textDocument));
		}
	}

	@Override
	public void connect(LanguageClient newLanguageClient) {
		this.languageClient = newLanguageClient;
		this.textDocumentService.connect(newLanguageClient);
		this.workspaceService.connect(newLanguageClient);

		// The client just changed, re-validate all documents so they publish the validation results to the
		// client.
		this.publishAll();

		newLanguageClient.logMessage(new MessageParams(MessageType.Info,
				"Connected to the Acceleo Language Server, relying on Acceleo Workspace " + this
						.getWorkspace().getName()));
	}

	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		final ServerCapabilities capabilities = new ServerCapabilities();

		capabilities.setTextDocumentSync(createTextDocumentSyncOptions());

		final CompletionOptions completionProvider = new CompletionOptions();
		completionProvider.setResolveProvider(true);
		capabilities.setCompletionProvider(completionProvider);

		// Note that LSP4E seems to ignore the declaration provider, cf. {@link
		// OpenDeclarationHyperlinkDetector}, so we also need to enable the definition provider.
		// capabilities.setDeclarationProvider(true);
		capabilities.setDefinitionProvider(true);

		// FIXME: Maybe at some point we will need to set hierarchicalDocumentSymbolSupport to true?
		capabilities.setDocumentSymbolProvider(true);

		capabilities.setReferencesProvider(true);

		capabilities.setRenameProvider(new RenameOptions(true));

		capabilities.setCodeActionProvider(true);

		final InitializeResult res = new InitializeResult(capabilities);
		return CompletableFuture.completedFuture(res);
	}

	/**
	 * Creates the {@link TextDocumentSyncOptions} capability for this server.
	 * 
	 * @return the {@link TextDocumentSyncOptions} for this server.
	 */
	private TextDocumentSyncOptions createTextDocumentSyncOptions() {
		TextDocumentSyncOptions textDocumentSyncOptions = new TextDocumentSyncOptions();
		textDocumentSyncOptions.setChange(TextDocumentSyncKind.Full);
		textDocumentSyncOptions.setOpenClose(true);
		textDocumentSyncOptions.setSave(new SaveOptions(true));
		textDocumentSyncOptions.setWillSave(true);
		textDocumentSyncOptions.setWillSaveWaitUntil(true);

		return textDocumentSyncOptions;
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		this.acceleoLanguageServerContext.deleteWorkspace(this.getWorkspace());
		if (this.languageClient != null) {
			this.languageClient.logMessage(new MessageParams(MessageType.Log,
					"Acceleo Language Server is shutting down"));
		}
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public void exit() {
		if (this.languageClient != null) {
			// FIXME removed log on editor closing, fails in DEV mode
			// this.languageClient.logMessage(new MessageParams(MessageType.Log,
			// "Acceleo Language Server is exiting"));
		}
	}

	@Override
	public AcceleoTextDocumentService getTextDocumentService() {
		return this.textDocumentService;
	}

	@Override
	public AcceleoWorkspaceService getWorkspaceService() {
		return this.workspaceService;
	}

	/**
	 * Provides the {@link AcceleoWorkspace}.
	 * 
	 * @return the current (non-{@code null}) {@link AcceleoWorkspace}.
	 */
	public AcceleoWorkspace getWorkspace() {
		return this.getWorkspaceService().getWorkspace();
	}

}
