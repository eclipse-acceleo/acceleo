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

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocumentService;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspaceService;
import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.MessageType;
import org.eclipse.lsp4j.SaveOptions;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextDocumentSyncOptions;
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

	/**
	 * The text-document-related service.
	 */
	private final AcceleoTextDocumentService textDocumentService = new AcceleoTextDocumentService(this);

	/**
	 * The workspace-related service.
	 */
	private final AcceleoWorkspaceService workspaceService = new AcceleoWorkspaceService(this);

	/**
	 * The root {@link AcceleoLanguageServerContext} of this server.
	 */
	private final AcceleoLanguageServerContext acceleoLanguageServerContext;

	/**
	 * The current language client.
	 */
	private LanguageClient languageClient;

	/**
	 * Creates a new {@link AcceleoLanguageServer}.
	 * 
	 * @param acceleoLanguageServerContext
	 *            the (non-{@code null}) root {@link AcceleoLanguageServerContext} for this server.
	 */
	public AcceleoLanguageServer(AcceleoLanguageServerContext acceleoLanguageServerContext) {
		this.acceleoLanguageServerContext = Objects.requireNonNull(acceleoLanguageServerContext);
	}

	@Override
	public void connect(LanguageClient newLanguageClient) {
		this.languageClient = newLanguageClient;
		this.textDocumentService.connect(newLanguageClient);
		this.workspaceService.connect(newLanguageClient);

		newLanguageClient.logMessage(new MessageParams(MessageType.Info,
				"Connected to the Acceleo Language Server"));
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
		if (this.languageClient != null) {
			this.languageClient.logMessage(new MessageParams(MessageType.Log,
					"Acceleo Language Server is shutting down"));
		}
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public void exit() {
		if (this.languageClient != null) {
			this.languageClient.logMessage(new MessageParams(MessageType.Log,
					"Acceleo Language Server is exiting"));
		}
	}

	@Override
	public TextDocumentService getTextDocumentService() {
		return textDocumentService;
	}

	@Override
	public WorkspaceService getWorkspaceService() {
		return workspaceService;
	}

	/**
	 * Creates the {@link IAcceleoEnvironment} for an Acceleo document.
	 * 
	 * @param acceleoDocumentUri
	 *            the {@link URI} corresponding to an Acceleo document handled by this server.
	 * @return the {@link IAcceleoEnvironment} for the given Acceleo document.
	 */
	public IAcceleoEnvironment createAcceleoEnvironmentFor(URI acceleoDocumentUri) {
		return this.acceleoLanguageServerContext.createAcceleoEnvironmentFor(acceleoDocumentUri);
	}

	/**
	 * Recursively finds all Acceleo documents in the given folder, and loads them.
	 * 
	 * @param folderUri
	 *            the (non-{@code null}) {@link String URI} of a folder from the client workspace.
	 * @return the {@link List} of all loaded {@link AcceleoTextDocument}.
	 */
	public List<AcceleoTextDocument> loadAllAcceleoDocumentsIn(String folderUri) {
		Map<URI, String> acceleoDocumentsMap = this.acceleoLanguageServerContext.getAllAcceleoDocumentsIn(
				folderUri);
		List<AcceleoTextDocument> acceleoTextDocuments = acceleoDocumentsMap.entrySet().stream().map(
				entry -> this.textDocumentService.getOrLoadTextDocument(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
		return acceleoTextDocuments;
	}

}
