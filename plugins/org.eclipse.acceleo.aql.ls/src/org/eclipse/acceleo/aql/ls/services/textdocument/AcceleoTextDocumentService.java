/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.acceleo.Module;
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
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WillSaveTextDocumentParams;
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
		this.openedDocumentsIndex.clear();
	}
	////

	// Mandatory TextDocumentService API.
	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		URI openedDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument().getUri());
		AcceleoTextDocument openedAcceleoTextDocument = this.server.getWorkspace().getTextDocument(
				openedDocumentUri);
		if (openedAcceleoTextDocument == null) {
			throw new IllegalStateException("Could not find the Acceleo Text Document at URI "
					+ openedDocumentUri);
		} else {
			this.openedDocumentsIndex.put(openedDocumentUri, openedAcceleoTextDocument);
			openedAcceleoTextDocument.setOpened(true);
		}
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		URI changedDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument().getUri());
		this.checkDocumentIsOpened(changedDocumentUri);

		List<TextDocumentContentChangeEvent> textDocumentContentchangeEvents = params.getContentChanges();
		AcceleoTextDocument changedAcceleoTextDocument = this.server.getWorkspace().getTextDocument(
				changedDocumentUri);
		changedAcceleoTextDocument.applyChanges(textDocumentContentchangeEvents);
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		URI closedDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument().getUri());
		checkDocumentIsOpened(closedDocumentUri);
		AcceleoTextDocument openedAcceleoTextDocument = this.server.getWorkspace().getTextDocument(
				closedDocumentUri);
		if (openedAcceleoTextDocument == null) {
			throw new IllegalStateException("Could not find the Acceleo Text Document at URI "
					+ closedDocumentUri);
		} else {
			openedAcceleoTextDocument.setOpened(false);
		}
		this.openedDocumentsIndex.remove(closedDocumentUri);
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		URI savedDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument().getUri());
		AcceleoTextDocument savedTextDocument = this.server.getWorkspace().getTextDocument(savedDocumentUri);
		savedTextDocument.documentSaved();
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
			IAcceleoValidationResult validationResults = acceleoTextDocument.getAcceleoValidationResults();
			List<Diagnostic> diagnosticsToPublish = AcceleoLanguageServerServicesUtils.transform(
					validationResults, acceleoTextDocument.getContents());
			this.languageClient.publishDiagnostics(new PublishDiagnosticsParams(acceleoTextDocument.getUri()
					.toString(), diagnosticsToPublish));
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

		List<AcceleoTextDocument> allTextDocuments = this.server.getWorkspace().getAllTextDocuments();
		for (AcceleoTextDocument candidate : allTextDocuments) {
			if (documentDefinesModule(candidate, definedModule)) {
				definingTextDocument = candidate;
				break;
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
		// We could also simply compare the "unique Module ID" that is placed in the EMF Resource that
		// "contains" the Module.
		return AcceleoAstUtils.isEqualStructurally(acceleoTextDocument.getAcceleoAstResult().getModule(),
				definedModule);
	}

	// Implementation of the various capabilities declared by the {@link AcceleoLanguageServer}.
	/**
	 * Checks that this service knows of an open document with the given URI. Otherwise, a
	 * {@link LanguageServerProtocolException} is thrown because we are not supposed to receive requests on
	 * open documents before the document is opened.
	 * 
	 * @param documentUri
	 *            the {@link URI} of the document.
	 */
	protected void checkDocumentIsOpened(URI documentUri) {
		if (!this.openedDocumentsIndex.containsKey(documentUri)) {
			throw new LanguageServerProtocolException("Received a notification for document \"" + documentUri
					+ "\" but it has not previously been opened. This should never happen.");
		}
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
			CompletionParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		checkDocumentIsOpened(textDocumentUri);
		AcceleoTextDocument acceleoTextDocument = this.openedDocumentsIndex.get(textDocumentUri);
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
			String source = acceleoTextDocument.getContents();
			int atIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(position, source);
			List<AcceleoCompletionProposal> completionProposals = acceleoCompletor.getProposals(
					acceleoTextDocument.getQueryEnvironment(), acceleoTextDocument
							.getFileNameWithoutExtension(), source, atIndex);

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
			DeclarationParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		checkDocumentIsOpened(textDocumentUri);
		AcceleoTextDocument acceleoTextDocument = this.openedDocumentsIndex.get(textDocumentUri);
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
			DefinitionParams params) {
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		checkDocumentIsOpened(textDocumentUri);
		AcceleoTextDocument acceleoTextDocument = this.openedDocumentsIndex.get(textDocumentUri);
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
		final URI textDocumentUri = AcceleoLanguageServerServicesUtils.toUri(params.getTextDocument()
				.getUri());
		checkDocumentIsOpened(textDocumentUri);
		AcceleoTextDocument acceleoTextDocument = this.openedDocumentsIndex.get(textDocumentUri);
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

			List<Either<SymbolInformation, DocumentSymbol>> documentSymbols = new ArrayList<>();
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
		checkDocumentIsOpened(textDocumentUri);
		AcceleoTextDocument acceleoTextDocument = this.openedDocumentsIndex.get(textDocumentUri);
		Position position = params.getPosition();
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
			final List<Location> locations = acceleoTextDocument.getReferencesLocations(atIndex);

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

}
