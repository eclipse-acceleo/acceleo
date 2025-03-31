/*******************************************************************************
 * Copyright (c) 2020, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.services.textdocument;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.acceleo.aql.completion.AcceleoCompletor;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerPositionUtils;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerServicesUtils;
import org.eclipse.acceleo.aql.ls.services.exceptions.LanguageServerProtocolException;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoProject;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspace;
import org.eclipse.acceleo.aql.outline.AcceleoOutliner;
import org.eclipse.acceleo.aql.outline.AcceleoSymbol;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DeclarationParams;
import org.eclipse.lsp4j.DefinitionParams;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PrepareRenameDefaultBehavior;
import org.eclipse.lsp4j.PrepareRenameParams;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WillSaveTextDocumentParams;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Either3;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageClientAware;
import org.eclipse.lsp4j.services.TextDocumentService;

/**
 * The {@link TextDocumentService} implementation for Acceleo.
 * 
 * @author Florent Latombe
 */
public class AcceleoTextDocumentService implements TextDocumentService, LanguageClientAware {

	/**
	 * {@link Map} of the opened documents, uniquely identified by their {@link URI}.
	 */
	private final Map<URI, AcceleoTextDocument> openedDocumentsIndex = new HashMap<>();

	/**
	 * The owner {@link AcceleoLanguageServer} of this service.
	 */
	private final AcceleoLanguageServer server;

	/**
	 * The current client.
	 */
	private LanguageClient languageClient;

	/**
	 * Creates a new {@link AcceleoTextDocumentService}.
	 * 
	 * @param acceleoLanguageServer
	 *            the (non-{@code null}) owning {@link AcceleoLanguageServer}.
	 */
	public AcceleoTextDocumentService(AcceleoLanguageServer acceleoLanguageServer) {
		this.server = Objects.requireNonNull(acceleoLanguageServer);
	}

	// LanguageClientAware API.
	@Override
	public void connect(LanguageClient newLanguageClient) {
		this.languageClient = newLanguageClient;
		this.openedDocumentsIndex.clear();
	}
	////

	/**
	 * Disconnects from the {@link LanguageClient}.
	 */
	public void disconnect() {
		this.languageClient = null;
	}

	// Mandatory TextDocumentService API.
	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		final AcceleoWorkspace workspace = server.getWorkspace();
		synchronized(workspace) {
			final URI openedDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
					.getUri());
			AcceleoTextDocument openedAcceleoTextDocument = this.server.getWorkspace().getDocument(
					openedDocumentUri);
			// the workspace might not be synchronized yet, try to add the document
			if (openedAcceleoTextDocument == null) {
				final AcceleoProject project = workspace.getProject(openedDocumentUri);
				final IQueryWorkspaceQualifiedNameResolver resolver = workspace.getResolver(project);
				final URI binaryURI = resolver.getBinaryURI(openedDocumentUri);
				if (binaryURI != null) {
					workspace.addResource(project, binaryURI);
					openedAcceleoTextDocument = workspace.getDocument(openedDocumentUri);
					if (openedAcceleoTextDocument != null) {
						openedAcceleoTextDocument.validateAndPublishResults();
					}
				}
			}
			if (openedAcceleoTextDocument == null) {
				throw new IllegalStateException("Could not find the Acceleo Text Document at URI "
						+ openedDocumentUri);
			} else {
				open(openedDocumentUri, openedAcceleoTextDocument);
				openedAcceleoTextDocument.open(params.getTextDocument().getText());
			}
		}
	}

	public void open(URI openedDocumentUri, AcceleoTextDocument acceleoTextDocument) {
		this.openedDocumentsIndex.put(openedDocumentUri, acceleoTextDocument);
	}

	public void close(URI closedDocumentUri) {
		this.openedDocumentsIndex.remove(closedDocumentUri);
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		final URI changedDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument changedAcceleoTextDocument = getAcceleoTextDocument(changedDocumentUri,
				true, true);

		final List<TextDocumentContentChangeEvent> textDocumentContentchangeEvents = params
				.getContentChanges();
		changedAcceleoTextDocument.applyChanges(textDocumentContentchangeEvents);
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		final URI closedDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument closedAcceleoTextDocument = getAcceleoTextDocument(closedDocumentUri, false,
				true);
		if (closedAcceleoTextDocument == null) {
			throw new IllegalStateException("Could not find the Acceleo Text Document at URI "
					+ closedDocumentUri);
		} else {
			closedAcceleoTextDocument.close();
			close(closedDocumentUri);
		}
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		// the Eclipse workspace will notified the change
	}
	////

	/**
	 * Retrieves the latest {@link IAcceleoValidationResult} of the given {@link AcceleoTextDocument} and
	 * publishes them to the client if there is one.
	 *
	 * @param acceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument}.
	 */
	public void publishValidationResults(AcceleoTextDocument acceleoTextDocument) {
		if (this.languageClient != null) {
			final IAcceleoValidationResult validationResults = acceleoTextDocument
					.getAcceleoValidationResults();
			final List<Diagnostic> diagnosticsToPublish = AcceleoLanguageServerServicesUtils.transform(
					validationResults, acceleoTextDocument.getContents());
			final URI sourceURI = acceleoTextDocument.getProject().getResolver().getSourceURI(
					acceleoTextDocument.getModuleQualifiedName());
			if (sourceURI != null) {
				this.languageClient.publishDiagnostics(new PublishDiagnosticsParams(sourceURI.toASCIIString(),
						diagnosticsToPublish));
			}
		}
	}

	// Implementation of the various capabilities declared by the {@link AcceleoLanguageServer}.

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
			CompletionParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument acceleoTextDocument = getAcceleoTextDocument(textDocumentUri, false, true);
		final Position position = params.getPosition();
		return completion(acceleoTextDocument, position);
	}

	/**
	 * Provides the completion for a {@link Position} in a {@link AcceleoTextDocument}.
	 * 
	 * @param acceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument}.
	 * @param position
	 *            the (non-{@code null}) {@link Position}.
	 * @return the asynchronous computation of the completion proposals provided by an
	 *         {@link AcceleoCompletor}.
	 */
	private static CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
			AcceleoTextDocument acceleoTextDocument, Position position) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			// Acceleo provides an API to access completion proposals.
			final AcceleoCompletor acceleoCompletor = new AcceleoCompletor(System.lineSeparator());
			final String source = acceleoTextDocument.getContents();
			final int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position,
					source);
			final List<AcceleoCompletionProposal> completionProposals = acceleoCompletor.getProposals(
					acceleoTextDocument.getQueryEnvironment(), acceleoTextDocument
							.getFileNameWithoutExtension(), source, atIndex);

			canceler.checkCanceled();
			final CompletionList res = new CompletionList();
			final List<CompletionItem> completionItems = AcceleoLanguageServerServicesUtils.transform(
					completionProposals);
			res.getItems().addAll(completionItems);
			res.setIsIncomplete(true);

			canceler.checkCanceled();
			return Either.forRight(res);
		});
	}

	@Override
	public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();
			// For now, the completion already provides fully-resolved items.
			return unresolved;
		});
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> declaration(
			DeclarationParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument acceleoTextDocument = getAcceleoTextDocument(textDocumentUri, false, true);
		final Position position = params.getPosition();
		return declaration(acceleoTextDocument, position);
	}

	/**
	 * Provides the "go to declaration" results for a {@link Position} in a {@link AcceleoTextDocument}.
	 * 
	 * @param acceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument}.
	 * @param position
	 *            the (non-{@code null}) {@link Position}.
	 * @return the asynchronous computation of the "go to declaration" proposals.
	 */
	private CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> declaration(
			AcceleoTextDocument acceleoTextDocument, Position position) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			final int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position,
					acceleoTextDocument.getContents());

			canceler.checkCanceled();
			return Either.forRight(acceleoTextDocument.getDeclarationLocations(atIndex, true));
		});
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			DefinitionParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument acceleoTextDocument = getAcceleoTextDocument(textDocumentUri, false, true);
		final Position position = params.getPosition();
		return definition(acceleoTextDocument, position);
	}

	/**
	 * Provides the "go to definition" results for a {@link Position} in a {@link AcceleoTextDocument}.
	 * 
	 * @param acceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument}.
	 * @param position
	 *            the (non-{@code null}) {@link Position}.
	 * @return the asynchronous computation of the "go to definition" proposals.
	 */
	private CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			AcceleoTextDocument acceleoTextDocument, Position position) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			final int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position,
					acceleoTextDocument.getContents());

			canceler.checkCanceled();
			return Either.forRight(acceleoTextDocument.getDefinitionLocations(atIndex));
		});
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument acceleoTextDocument = getAcceleoTextDocument(textDocumentUri, false, true);
		return documentSymbol(acceleoTextDocument);
	}

	/**
	 * Provides all the symbols (templates, queries, etc.) defined in a {@link AcceleoTextDocument}.
	 * 
	 * @param acceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument}.
	 * @return the asynchronous computation of all the symbols defined in the document.
	 */
	private static CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			AcceleoTextDocument acceleoTextDocument) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			final List<Either<SymbolInformation, DocumentSymbol>> documentSymbols;
			if (acceleoTextDocument.getAcceleoValidationResults() != null) {
				// Acceleo provides an API to access all defined symbols
				final AcceleoOutliner acceleoOutliner = new AcceleoOutliner();
				List<AcceleoSymbol> acceleoSymbols = acceleoOutliner.getAllDeclaredSymbols(acceleoTextDocument
						.getAcceleoValidationResults());

				canceler.checkCanceled();
				documentSymbols = acceleoSymbols.stream().map(
						acceleoSymbol -> AcceleoLanguageServerServicesUtils.transform(acceleoSymbol,
								acceleoTextDocument.getContents())).map(
										Either::<SymbolInformation, DocumentSymbol> forRight).collect(
												Collectors.toList());
			} else {
				documentSymbols = Collections.emptyList();
			}

			canceler.checkCanceled();
			return documentSymbols;
		});
	}
	////

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument acceleoTextDocument = getAcceleoTextDocument(textDocumentUri, false, true);
		final Position position = params.getPosition();
		return references(acceleoTextDocument, position);
	}

	/**
	 * Provides the "go to definition" results for a {@link Position} in a {@link AcceleoTextDocument}.
	 * 
	 * @param acceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument}.
	 * @param position
	 *            the (non-{@code null}) {@link Position}.
	 * @return the asynchronous computation of the "go to definition" proposals.
	 */
	private CompletableFuture<List<? extends Location>> references(AcceleoTextDocument acceleoTextDocument,
			Position position) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position,
					acceleoTextDocument.getContents());
			final List<Location> locations = acceleoTextDocument.getReferencesLocations(atIndex, true);

			canceler.checkCanceled();
			return locations;
		});
	}

	@Override
	public CompletableFuture<List<TextEdit>> willSaveWaitUntil(WillSaveTextDocumentParams params) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();
			// TODO we can return a list of text edit here for organize import or formating on save.
			return Collections.emptyList();
		});
	}

	@Override
	public CompletableFuture<Either3<Range, PrepareRenameResult, PrepareRenameDefaultBehavior>> prepareRename(
			PrepareRenameParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument acceleoTextDocument = getAcceleoTextDocument(textDocumentUri, false, true);
		final Position position = params.getPosition();
		return prepareRename(acceleoTextDocument, position);
	}

	/**
	 * Provides the rename results for a {@link Position} in a {@link AcceleoTextDocument}.
	 * 
	 * @param acceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument}.
	 * @param position
	 *            the (non-{@code null}) {@link Position}.
	 * @return the asynchronous computation of the "go to definition" proposals.
	 */
	private CompletableFuture<Either3<Range, PrepareRenameResult, PrepareRenameDefaultBehavior>> prepareRename(
			AcceleoTextDocument acceleoTextDocument, Position position) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			final int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position,
					acceleoTextDocument.getContents());
			final PrepareRenameResult prepareRenameResult = acceleoTextDocument.getPrepareRenameResult(
					atIndex);

			canceler.checkCanceled();
			return Either3.forSecond(prepareRenameResult);
		});
	}

	@Override
	public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument acceleoTextDocument = getAcceleoTextDocument(textDocumentUri, false, true);
		final Position position = params.getPosition();
		return rename(acceleoTextDocument, position, params.getNewName());
	}

	/**
	 * Gets the {@link WorkspaceEdit} corresponding to the rename operation.
	 * 
	 * @param acceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument}.
	 * @param position
	 *            the (non-{@code null}) {@link Position}.
	 * @param newName
	 *            the (non-{@code null}) new name.
	 * @return
	 */
	private CompletableFuture<WorkspaceEdit> rename(AcceleoTextDocument acceleoTextDocument,
			Position position, String newName) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			final int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position,
					acceleoTextDocument.getContents());
			final Map<String, List<TextEdit>> changes = acceleoTextDocument.getRenames(atIndex, newName);

			canceler.checkCanceled();
			return new WorkspaceEdit(changes);
		});
	}

	@Override
	public CompletableFuture<List<Either<Command, CodeAction>>> codeAction(CodeActionParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		final AcceleoTextDocument acceleoTextDocument = getAcceleoTextDocument(textDocumentUri, false, false);
		final Range position = params.getRange();
		final CodeActionContext context = params.getContext();
		return codeAction(acceleoTextDocument, position, context);
	}

	/**
	 * Gets the {@link AcceleoTextDocument} for the given {@link URI}.
	 * 
	 * @param textDocumentUri
	 *            the text document {@link URI}
	 * @param fromWorkspace
	 *            <code>true</code> gets the document from the workspace, <code>false</code> take the document
	 *            from opened documents
	 * @param checkOpened
	 *            <code>true</code> to check if the document is currently opened, <code>false</code> otherwise
	 * @return the {@link AcceleoTextDocument} for the given {@link URI}
	 */
	protected AcceleoTextDocument getAcceleoTextDocument(final URI textDocumentUri, boolean fromWorkspace,
			boolean checkOpened) {
		final AcceleoTextDocument res;

		final AcceleoTextDocument openedDocument = this.openedDocumentsIndex.get(textDocumentUri);
		if (checkOpened && openedDocument == null) {
			throw new LanguageServerProtocolException("Received a notification for document \""
					+ textDocumentUri
					+ "\" but it has not previously been opened. This should never happen.");
		}

		if (fromWorkspace || openedDocument == null) {
			res = server.getWorkspace().getDocument(textDocumentUri);
		} else {
			res = openedDocument;
		}

		return res;
	}

	private CompletableFuture<List<Either<Command, CodeAction>>> codeAction(
			AcceleoTextDocument acceleoTextDocument, Range range, CodeActionContext context) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			final int atStartIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(range
					.getStart(), acceleoTextDocument.getContents());
			final int atEndIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(range
					.getEnd(), acceleoTextDocument.getContents());
			final List<Either<Command, CodeAction>> codeActions = acceleoTextDocument.getCodeActions(
					atStartIndex, atEndIndex, context);

			canceler.checkCanceled();
			return codeActions;
		});
	}

}
