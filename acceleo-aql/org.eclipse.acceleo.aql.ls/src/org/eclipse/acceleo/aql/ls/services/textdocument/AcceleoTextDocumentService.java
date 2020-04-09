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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.completion.AcceleoCompletor;
import org.eclipse.acceleo.aql.location.AcceleoLocationLink;
import org.eclipse.acceleo.aql.location.AcceleoLocator;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerServicesUtils;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerStringUtils;
import org.eclipse.acceleo.aql.ls.services.exceptions.LanguageServerProtocolException;
import org.eclipse.acceleo.aql.outline.AcceleoOutliner;
import org.eclipse.acceleo.aql.outline.AcceleoSymbol;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
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
	 * {@link Map} of the opened documents, uniquely identified by their URI.
	 */
	private final Map<String, AcceleoTextDocument> openedDocumentsIndex = new HashMap<>();

	/**
	 * The current client.
	 */
	private LanguageClient languageClient;

	/**
	 * Creates a new {@link AcceleoTextDocumentService}.
	 */
	public AcceleoTextDocumentService() {
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
		String openedDocumentUri = params.getTextDocument().getUri();
		String openedDocumentText = params.getTextDocument().getText();
		AcceleoTextDocument openedAcceleoTextDocument = new AcceleoTextDocument(openedDocumentUri,
				openedDocumentText);
		this.openedDocumentsIndex.put(openedDocumentUri, openedAcceleoTextDocument);
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		String changedDocumentUri = params.getTextDocument().getUri();
		this.checkDocumentUriIsOpened(changedDocumentUri);

		List<TextDocumentContentChangeEvent> textDocumentContentchangeEvents = params.getContentChanges();
		AcceleoTextDocument changedAcceleoTextDocument = this.openedDocumentsIndex.get(changedDocumentUri);
		changedAcceleoTextDocument.applyChanges(textDocumentContentchangeEvents);

		// Upon every change, re-validate the module and send the results to the client.
		if (this.languageClient != null) {
			IAcceleoValidationResult validationResults = changedAcceleoTextDocument.getValidationResults();
			List<Diagnostic> diagnostics = AcceleoLanguageServerServicesUtils.transform(validationResults,
					changedAcceleoTextDocument.getContents());
			this.languageClient.publishDiagnostics(new PublishDiagnosticsParams(changedDocumentUri,
					diagnostics));
		}
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		String closedDocumentUri = params.getTextDocument().getUri();
		checkDocumentUriIsOpened(closedDocumentUri);
		this.openedDocumentsIndex.remove(closedDocumentUri);
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		// TODO: Upon saving a document, we probably want to make sure it does not make other documents
		// invalid?
	}
	////

	/**
	 * Checks that this service knows of an open document with the given URI. Otherwise, a
	 * {@link LanguageServerProtocolException} is thrown because we are not supposed to receive requests on
	 * open documents before the document is opened.
	 * 
	 * @param documentUri
	 *            the candidate {@link String document URI}.
	 */
	private void checkDocumentUriIsOpened(String documentUri) {
		if (!this.openedDocumentsIndex.containsKey(documentUri)) {
			throw new LanguageServerProtocolException("Received a notification for document \"" + documentUri
					+ "\" but it has not previously been opened. This should never happen.");
		}
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
			CompletionParams params) {
		final String textDocumentUri = params.getTextDocument().getUri();
		checkDocumentUriIsOpened(textDocumentUri);
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
			IAcceleoEnvironment acceleoEnvironment = acceleoTextDocument.getAcceleoEnvironment();
			String source = acceleoTextDocument.getContents();
			int atIndex = AcceleoLanguageServerStringUtils.getCorrespondingCharacterIndex(position, source);
			List<ICompletionProposal> completionProposals = acceleoCompletor.getProposals(acceleoEnvironment,
					source, atIndex);

			canceler.checkCanceled();
			List<CompletionItem> completionItems = completionProposals.stream().map(
					AcceleoLanguageServerServicesUtils::transform).collect(Collectors.toList());

			canceler.checkCanceled();
			return Either.forLeft(completionItems);
		});
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> declaration(
			TextDocumentPositionParams params) {
		final String textDocumentUri = params.getTextDocument().getUri();
		checkDocumentUriIsOpened(textDocumentUri);
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
	private static CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> declaration(
			AcceleoTextDocument acceleoTextDocument, Position position) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			// Acceleo provides an API to access declaration locations.
			final AcceleoLocator acceleoLocator = new AcceleoLocator();
			IAcceleoEnvironment acceleoEnvironment = acceleoTextDocument.getAcceleoEnvironment();
			String source = acceleoTextDocument.getContents();
			int atIndex = AcceleoLanguageServerStringUtils.getCorrespondingCharacterIndex(position, source);
			List<AcceleoLocationLink> declarationLocations = acceleoLocator.getDeclarationLocations(
					acceleoEnvironment, source, atIndex);

			canceler.checkCanceled();
			List<LocationLink> locationLinks = declarationLocations.stream().map(
					AcceleoLanguageServerServicesUtils::transform).collect(Collectors.toList());

			canceler.checkCanceled();
			return Either.forRight(locationLinks);
		});
	}

	@Override
	public CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			TextDocumentPositionParams params) {
		final String textDocumentUri = params.getTextDocument().getUri();
		checkDocumentUriIsOpened(textDocumentUri);
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
	private static CompletableFuture<Either<List<? extends Location>, List<? extends LocationLink>>> definition(
			AcceleoTextDocument acceleoTextDocument, Position position) {
		return CompletableFutures.computeAsync(canceler -> {
			canceler.checkCanceled();

			// Acceleo provides an API to access definition locations.
			final AcceleoLocator acceleoLocator = new AcceleoLocator();
			IAcceleoEnvironment acceleoEnvironment = acceleoTextDocument.getAcceleoEnvironment();
			String source = acceleoTextDocument.getContents();
			int atIndex = AcceleoLanguageServerStringUtils.getCorrespondingCharacterIndex(position, source);
			List<AcceleoLocationLink> definitionLocations = acceleoLocator.getDefinitionLocations(
					acceleoEnvironment, source, atIndex);

			canceler.checkCanceled();
			List<LocationLink> locationLinks = definitionLocations.stream().map(
					AcceleoLanguageServerServicesUtils::transform).collect(Collectors.toList());

			canceler.checkCanceled();
			return Either.forRight(locationLinks);
		});
	}

	@Override
	public CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> documentSymbol(
			DocumentSymbolParams params) {
		final String textDocumentUri = params.getTextDocument().getUri();
		checkDocumentUriIsOpened(textDocumentUri);
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

			// Acceleo provides an API to access all defined symbols
			final AcceleoOutliner acceleoOutliner = new AcceleoOutliner();
			List<AcceleoSymbol> acceleoSymbols = acceleoOutliner.getAllDeclaredSymbols(acceleoTextDocument
					.getValidationResults());

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

}
