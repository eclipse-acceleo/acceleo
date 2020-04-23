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
package org.eclipse.acceleo.aql.ls.services.textdocument;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.completion.AcceleoCompletor;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerPositionUtils;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerServicesUtils;
import org.eclipse.acceleo.aql.ls.services.exceptions.LanguageServerProtocolException;
import org.eclipse.acceleo.aql.outline.AcceleoOutliner;
import org.eclipse.acceleo.aql.outline.AcceleoSymbol;
import org.eclipse.acceleo.aql.parser.AcceleoAstUtils;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
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
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
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
	 * {@link Map} of the loaded documents, uniquely identified by their {@link URI}.
	 */
	private final Map<URI, AcceleoTextDocument> loadedDocumentsIndex = new HashMap<>();

	/**
	 * The {@link AcceleoLocationLinkResolver} helps dealing with {@link AbstractLocationLink} provided by the
	 * Acceleo API.
	 */
	private final AcceleoLocationLinkResolver acceleoLocationLinkResolver = new AcceleoLocationLinkResolver(
			this);

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
	}
	////

	// Mandatory TextDocumentService API.
	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		URI openedDocumentUri = toUri(params.getTextDocument().getUri());
		String openedDocumentText = params.getTextDocument().getText();
		AcceleoTextDocument openedAcceleoTextDocument = getOrLoadTextDocument(openedDocumentUri,
				openedDocumentText);
		this.openedDocumentsIndex.put(openedDocumentUri, openedAcceleoTextDocument);
		this.validate(openedAcceleoTextDocument);
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		String changedDocumentUri = params.getTextDocument().getUri();
		this.checkDocumentIsOpened(changedDocumentUri);

		List<TextDocumentContentChangeEvent> textDocumentContentchangeEvents = params.getContentChanges();
		AcceleoTextDocument changedAcceleoTextDocument = this.loadedDocumentsIndex.get(toUri(
				changedDocumentUri));
		changedAcceleoTextDocument.applyChanges(textDocumentContentchangeEvents);

		// Upon every change, re-validate the module and send the results to the client.
		validate(changedAcceleoTextDocument);
	}

	/**
	 * Validates the given {@link AcceleoTextDocument} and publishes the results to the client if there is
	 * one.
	 * 
	 * @param acceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument} to validate.
	 * @return the {@link List} of {@link Diagnostic} representing the validation results.
	 */
	private List<Diagnostic> validate(AcceleoTextDocument acceleoTextDocument) {
		IAcceleoValidationResult validationResults = acceleoTextDocument.getAcceleoValidationResults();
		List<Diagnostic> diagnostics = AcceleoLanguageServerServicesUtils.transform(validationResults,
				acceleoTextDocument.getContents());
		if (this.languageClient != null) {
			this.languageClient.publishDiagnostics(new PublishDiagnosticsParams(acceleoTextDocument
					.getDocumentUri().toString(), diagnostics));
		}
		return diagnostics;
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		String closedDocumentUri = params.getTextDocument().getUri();
		checkDocumentIsOpened(closedDocumentUri);
		this.openedDocumentsIndex.remove(toUri(closedDocumentUri));
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		// TODO: Upon saving a document, we probably want to make sure it does not make other documents
		// invalid?
	}
	////

	/**
	 * Creates a normalized {@link URI} from a {@link String}.
	 * 
	 * @param stringUri
	 *            the (non-{@code null}) {@link String} to transform.
	 * @return the corresponding normalized {@link URI}.
	 */
	private static URI toUri(String stringUri) {
		try {
			return new URI(stringUri).normalize();
		} catch (URISyntaxException uriException) {
			throw new RuntimeException("Unexpected URI syntax: " + stringUri, uriException);
		}
	}

	/**
	 * Loads an Acceleo text document if it was not already loaded. Otherwise, the already-loaded document is
	 * provided.
	 * 
	 * @param acceleoTextDocumentUri
	 *            the (non-{@code null}) {@link URI} of the document to load.
	 * @param acceleoTextDocumentContents
	 *            the (non-{@code null}) {@link String text contents} of the document to load.
	 * @return the corresponding {@link AcceleoTextDocument}.
	 */
	public AcceleoTextDocument getOrLoadTextDocument(URI acceleoTextDocumentUri,
			String acceleoTextDocumentContents) {
		if (this.loadedDocumentsIndex.containsKey(acceleoTextDocumentUri)) {
			return this.loadedDocumentsIndex.get(acceleoTextDocumentUri);
		} else {
			return this.loadTextDocument(acceleoTextDocumentUri, acceleoTextDocumentContents);
		}
	}

	/**
	 * Loads an Acceleo text document.
	 * 
	 * @param acceleoTextDocumentUri
	 *            the (non-{@code null}) {@link URI} of the document to load.
	 * @param acceleoTextDocumentContents
	 *            the (non-{@code null}) {@link String text contents} of the document to load.
	 * @return the corresponding {@link AcceleoTextDocument}.
	 */
	private AcceleoTextDocument loadTextDocument(URI acceleoTextDocumentUri,
			String acceleoTextDocumentContents) {
		AcceleoTextDocument acceleoTextDocument = new AcceleoTextDocument(this, acceleoTextDocumentUri,
				acceleoTextDocumentContents, this.server.createAcceleoEnvironmentFor(acceleoTextDocumentUri));
		this.loadedDocumentsIndex.put(acceleoTextDocumentUri, acceleoTextDocument);
		return acceleoTextDocument;
	}

	/**
	 * Unloads an Acceleo text document.
	 * 
	 * @param acceleoTextDocumentUri
	 *            the (non-{@code null}) {@link URI} of the document to unload.
	 */
	private void unloadTextDocument(URI acceleoTextDocumentUri) {
		this.loadedDocumentsIndex.remove(acceleoTextDocumentUri);
	}

	/**
	 * This method is called by an {@link AcceloTextDocument} to notify us that its contents have changed. We
	 * need to update the corresponding cached {@link Module} values in the {@link AcceleoEnvironments} of all
	 * our text documents.
	 * 
	 * @param changedAcceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument} whose contents have changed.
	 */
	public void contentsChanged(AcceleoTextDocument changedAcceleoTextDocument) {
		for (AcceleoTextDocument acceleoTextDocument : this.loadedDocumentsIndex.values()) {
			IAcceleoEnvironment acceleoEnvironment = acceleoTextDocument.getAcceleoEnvironment();
			if (acceleoEnvironment.getModule(changedAcceleoTextDocument.getUrl()) != null) {
				// The current document's environment has a cached version of the module that just changed, so
				// we want to update the cached value.
				acceleoEnvironment.registerModule(acceleoEnvironment.getModuleResolver().getQualifierName(
						changedAcceleoTextDocument.getUrl()), changedAcceleoTextDocument.getAcceleoAstResult()
								.getModule());
			}
		}
	}

	/**
	 * Provides the {@link AcceleoTextDocument} that defines the given {@link Module}.
	 * 
	 * @param definedModule
	 *            the (non-{@code null}) {@link Module}.
	 * @return the {@link AcceleoTextDocument} that defines {@code definedModule}, or {@code null} if it could
	 *         not be determined.
	 */
	public AcceleoTextDocument findTextDocumentDefining(Module definedModule) {
		AcceleoTextDocument definingTextDocument = null;

		// First look in the already loaded documents.
		for (AcceleoTextDocument candidate : this.loadedDocumentsIndex.values()) {
			if (documentDefinesModule(candidate, definedModule)) {
				definingTextDocument = candidate;
				break;
			}
		}

		if (definingTextDocument == null) {
			// Otherwise, search in the workspace.
			CompletableFuture<List<WorkspaceFolder>> futureWorkspaceFolders = this.languageClient
					.workspaceFolders();
			try {
				List<WorkspaceFolder> workspaceFolders = futureWorkspaceFolders.get();
				for (WorkspaceFolder workspaceFolder : workspaceFolders) {
					List<AcceleoTextDocument> acceleoTextDocuments = this.server.loadAllAcceleoDocumentsIn(
							workspaceFolder.getUri());
					for (AcceleoTextDocument candidateAcceleoDocument : acceleoTextDocuments) {
						if (documentDefinesModule(candidateAcceleoDocument, definedModule)) {
							definingTextDocument = candidateAcceleoDocument;
						}
					}
				}
			} catch (InterruptedException | ExecutionException exception) {
				throw new RuntimeException(exception);
			}

		}
		return definingTextDocument;
	}

	/**
	 * Provides whether the given {@link AcceleoTextDocument} defines the given {@link Module} or not.
	 * 
	 * @param acceleoTextDocument
	 *            the (non-{@code null} {@link AcceleoTextDocument}.
	 * @param definedModule
	 *            the (non-{@code null}) Acceleo {@link Module}.
	 * @return {@code true} if {@code acceleoTextDocument} defines {@code definedModule}, {@code false}
	 *         otherwise.
	 */
	private static boolean documentDefinesModule(AcceleoTextDocument acceleoTextDocument,
			Module definedModule) {
		return AcceleoAstUtils.isEqualStructurally(acceleoTextDocument.getAcceleoAstResult().getModule(),
				definedModule);
	}

	// Implementation of the various capabilities declared by the {@link AcceleoLanguageServer}.
	/**
	 * Checks that this service knows of an open document with the given URI. Otherwise, a
	 * {@link LanguageServerProtocolException} is thrown because we are not supposed to receive requests on
	 * open documents before the document is opened.
	 * 
	 * @param documentStringUri
	 *            the candidate {@link String document URI}.
	 */
	protected void checkDocumentIsOpened(String documentStringUri) {
		URI documentUri = toUri(documentStringUri);
		if (!this.openedDocumentsIndex.containsKey(documentUri)) {
			throw new LanguageServerProtocolException("Received a notification for document \"" + documentUri
					+ "\" but it has not previously been opened. This should never happen.");
		}
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
			CompletionParams params) {
		final String textDocumentUri = params.getTextDocument().getUri();
		checkDocumentIsOpened(textDocumentUri);
		AcceleoTextDocument acceleoTextDocument = this.openedDocumentsIndex.get(toUri(textDocumentUri));
		Position position = params.getPosition();
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
			final AcceleoCompletor acceleoCompletor = new AcceleoCompletor();
			IAcceleoEnvironment acceleoEnvironment = acceleoTextDocument.getAcceleoEnvironment();
			String source = acceleoTextDocument.getContents();
			int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position, source);
			List<AcceleoCompletionProposal> completionProposals = acceleoCompletor.getProposals(
					acceleoEnvironment, source, atIndex);

			canceler.checkCanceled();
			List<CompletionItem> completionItems = AcceleoLanguageServerServicesUtils.transform(
					completionProposals);

			canceler.checkCanceled();
			return Either.forLeft(completionItems);
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
			TextDocumentPositionParams params) {
		final String textDocumentUri = params.getTextDocument().getUri();
		checkDocumentIsOpened(textDocumentUri);
		AcceleoTextDocument acceleoTextDocument = this.openedDocumentsIndex.get(toUri(textDocumentUri));
		Position position = params.getPosition();
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

			int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position,
					acceleoTextDocument.getContents());
			List<AbstractLocationLink<?, ?>> declarationLocations = acceleoTextDocument
					.getDeclarationLocations(atIndex);

			canceler.checkCanceled();
			List<LocationLink> locationLinks = declarationLocations.stream().map(
					acceleoLocationLinkResolver::transform).collect(Collectors.toList());

			canceler.checkCanceled();
			return Either.forRight(locationLinks);
		});
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			TextDocumentPositionParams params) {
		final String textDocumentUri = params.getTextDocument().getUri();
		checkDocumentIsOpened(textDocumentUri);
		AcceleoTextDocument acceleoTextDocument = this.openedDocumentsIndex.get(toUri(textDocumentUri));
		Position position = params.getPosition();
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

			int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position,
					acceleoTextDocument.getContents());
			List<AbstractLocationLink<?, ?>> definitionLocations = acceleoTextDocument.getDefinitionLocations(
					atIndex);

			canceler.checkCanceled();
			List<LocationLink> locationLinks = definitionLocations.stream().map(
					acceleoLocationLinkResolver::transform).collect(Collectors.toList());

			canceler.checkCanceled();
			return Either.forRight(locationLinks);
		});
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {
		final String textDocumentUri = params.getTextDocument().getUri();
		checkDocumentIsOpened(textDocumentUri);
		AcceleoTextDocument acceleoTextDocument = this.openedDocumentsIndex.get(toUri(textDocumentUri));
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

			// Acceleo provides an API to access all defined symbols
			final AcceleoOutliner acceleoOutliner = new AcceleoOutliner();
			List<AcceleoSymbol> acceleoSymbols = acceleoOutliner.getAllDeclaredSymbols(acceleoTextDocument
					.getAcceleoValidationResults());

			canceler.checkCanceled();
			List<Either<SymbolInformation, DocumentSymbol>> documentSymbols = acceleoSymbols.stream().map(
					acceleoSymbol -> AcceleoLanguageServerServicesUtils.transform(acceleoSymbol,
							acceleoTextDocument.getContents())).map(
									Either::<SymbolInformation, DocumentSymbol> forRight).collect(Collectors
											.toList());

			canceler.checkCanceled();
			return documentSymbols;
		});
	}
	////

}
