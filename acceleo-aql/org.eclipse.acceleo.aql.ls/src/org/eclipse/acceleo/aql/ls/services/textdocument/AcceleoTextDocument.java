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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.completion.AcceleoCompletor;
import org.eclipse.acceleo.aql.location.AcceleoLocator;
import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerPositionUtils;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.resolver.IQualifiedNameResolver;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;

/**
 * Represents an Acceleo Text Document as known by the Language Server. It is maintained consistent with the
 * contents of the client's editor thanks to the LSP-compliant notifications sent to the Language Server by
 * the client. It is used to provide the language services back to the client.
 * 
 * @author Florent Latombe
 */
public class AcceleoTextDocument {

	/**
	 * The owner {@link AcceleoTextDocumentService}.
	 */
	private final AcceleoTextDocumentService ownerService;

	/**
	 * The {@link URI} that uniquely identifies this document.
	 */
	private final URI textDocumentUri;

	/**
	 * The {@link IAcceleoEnvironment} used to analyze the text document.
	 */
	private final IAcceleoEnvironment acceleoEnvironment;

	// Language services
	/**
	 * The {@link AcceleoParser} used to parse the text document.
	 */
	private final AcceleoParser acceleoParser;

	/**
	 * The {@link AcceleoValidator} used to validate the text document's contents.
	 */
	private final AcceleoValidator acceleoValidator;

	/**
	 * The {@link AcceleoCompletor} used for content assist.
	 */
	private final AcceleoCompletor acceleoCompletor;

	/**
	 * The {@link AcceleoLocator} used to locate definitions, declarations, references, etc. in the text
	 * document's contents.
	 */
	private final AcceleoLocator acceleoLocator;

	/**
	 * The {@link String} contents of the text document.
	 */
	private String textDocumentContents;

	// Cached values that depend on the contents.
	/**
	 * The cached {@link AcceleoAstResult} resulting form parsing the contents of the text document.
	 */
	private AcceleoAstResult acceleoAstResult;

	/**
	 * The cached {@link IAcceleoValidationResult}, updated upon any changes in the contents of this document.
	 */
	private IAcceleoValidationResult acceleoValidationResult;

	/**
	 * Creates a new {@link AcceleoTextDocument} corresponding to the given URI and with the given initial
	 * contents.
	 * 
	 * @param ownerService
	 *            the (non-{@code null}) owner {@link AcceleoTextDocumentService}.
	 * @param textDocumentUri
	 *            the (non-{@code null}) {@link URI} of this text document.
	 * @param textDocumentContents
	 *            the (non-{@code null}) initial contents of this text document.
	 * @param acceleoEnvironment
	 *            the (non-{@code null}) {@link IAcceleoEnvironment} of this Acceleo document.
	 */
	public AcceleoTextDocument(AcceleoTextDocumentService ownerService, URI textDocumentUri,
			String textDocumentContents, IAcceleoEnvironment acceleoEnvironment) {
		Objects.requireNonNull(ownerService);
		Objects.requireNonNull(textDocumentUri);
		Objects.requireNonNull(textDocumentContents);
		Objects.requireNonNull(acceleoEnvironment);

		this.ownerService = ownerService;
		this.textDocumentUri = textDocumentUri;
		this.acceleoEnvironment = acceleoEnvironment;

		// Language services that depend on the Acceleo AST.
		this.acceleoParser = new AcceleoParser(this.getAcceleoEnvironment().getQueryEnvironment());
		this.acceleoValidator = new AcceleoValidator(this.getAcceleoEnvironment());
		this.acceleoCompletor = new AcceleoCompletor();
		this.acceleoLocator = new AcceleoLocator(this.getAcceleoEnvironment());

		this.setDocumentContents(textDocumentContents);
	}

	/**
	 * Behavior triggered when the contents of this document have changed. We update the cached values of the
	 * parsing and validation results, and also notify external interested parties.
	 */
	private void contentsChanged() {
		// Retrieve the parsing result first, as other services depend on it.
		this.acceleoAstResult = this.acceleoParser.parse(this.textDocumentContents, this
				.getModuleQualifiedName());

		// And validation second, as some other services depend on it.
		this.acceleoValidationResult = validate(this.getAcceleoEnvironment(), this.acceleoValidator,
				this.acceleoAstResult);

		// TODO: we probably only want to send this notification out when the "publicly-accessible" parts of
		// the Module have changed.
		this.ownerService.contentsChanged(this);
	}

	/**
	 * Provides the qualified name of the Acceleo {@link Module} represented by this document, as computed by
	 * the {@link AcceleoEnvironment}'s {@link IQualifiedNameResolver}.
	 * 
	 * @return the qualified name of the {@link Module}.
	 */
	public String getModuleQualifiedName() {
		return this.getAcceleoEnvironment().getModuleResolver().getQualifierName(this.getUrl());
	}

	/**
	 * Provides the {@link URL} version of this document's {@link URI}.
	 * 
	 * @return the corresponding {@link URI}.
	 */
	public URL getUrl() {
		try {
			return this.getDocumentUri().toURL();
		} catch (MalformedURLException urlException) {
			throw new RuntimeException("Could not convert into a URL the URI of document " + this
					.getDocumentUri().toString(), urlException);
		}
	}

	/**
	 * Provides the links from the given position to the location(s) defining the element at the given
	 * position.
	 * 
	 * @param position
	 *            the (positive) position in the source contents.
	 * @return the {@link List} of {@link AbstractLocationLink} corresponding to the definition location(s) of
	 *         the Acceleo element found at the given position in the source contents.
	 */
	public List<AbstractLocationLink<?, ?>> getDefinitionLocations(int position) {
		return this.acceleoLocator.getDefinitionLocations(this.acceleoAstResult, position);
	}

	/**
	 * Provides the links from the given position to the location(s) declaring the element at the given
	 * position.
	 * 
	 * @param position
	 *            the (positive) position in the source contents.
	 * @return the {@link List} of {@link AbstractLocationLink} corresponding to the declaration location(s)
	 *         of the Acceleo element found at the given position in the source contents.
	 */
	public List<AbstractLocationLink<?, ?>> getDeclarationLocations(int position) {
		return this.acceleoLocator.getDeclarationLocations(this.acceleoAstResult, position);
	}

	/**
	 * Provides the URI of this {@link AcceleoTextDocument}.
	 * 
	 * @return the {@link URI} of this {@link AcceleoTextDocument}.
	 */
	public URI getDocumentUri() {
		return this.textDocumentUri;
	}

	/**
	 * Applies {@link TextDocumentContentChangeEvent TextDocumentContentChangeEvents} to the contents of this
	 * text document.
	 * 
	 * @param textDocumentContentchangeEvents
	 *            the {@link Iterable} of {@link TextDocumentContentChangeEvent} to apply, in the same order.
	 * @return this {@link AcceleoTextDocument}, with the new contents resulting from applying the changes.
	 */
	public AcceleoTextDocument applyChanges(
			Iterable<TextDocumentContentChangeEvent> textDocumentContentchangeEvents) {
		String newTextDocumentContents = this.textDocumentContents;
		for (TextDocumentContentChangeEvent textDocumentContentChangeEvent : textDocumentContentchangeEvents) {
			newTextDocumentContents = apply(textDocumentContentChangeEvent, newTextDocumentContents);
		}

		this.setDocumentContents(newTextDocumentContents);

		return this;
	}

	/**
	 * Updates the known contents of the text document, which triggers the parsing of the new contents.
	 * 
	 * @param newTextDocumentContents
	 *            the new (non-{@code null}) contents of the text document.
	 */
	private void setDocumentContents(String newTextDocumentContents) {
		this.textDocumentContents = newTextDocumentContents;
		this.contentsChanged();
	}

	/**
	 * Provides the Abstract Syntax Tree (AST) corresponding to the contents of this text document.
	 * 
	 * @return the (non-{@code null}) {@link AcceleoAstResult} of this document.
	 */
	public AcceleoAstResult getAcceleoAstResult() {
		return acceleoAstResult;
	}

	/**
	 * Provides the results of the Acceleo validation of the contents of this text document.
	 * 
	 * @return the (non-{@code null}) {@link IAcceleoValidationResult} of this document.
	 */
	public IAcceleoValidationResult getAcceleoValidationResults() {
		return this.acceleoValidationResult;
	}

	/**
	 * Provides the {@link IAcceleoEnvironment} associated to this text document.
	 * 
	 * @return the (non-{@code null}) {@link IAcceleoEnvironment} associated to this
	 *         {@link AcceleoTextDocument}.
	 */
	public IAcceleoEnvironment getAcceleoEnvironment() {
		return this.acceleoEnvironment;
	}

	/**
	 * Provides the current {@link String text contents} of this text document.
	 * 
	 * @return the (non-{@code null}) {@link String} contents of this {@link AcceleoTextDocument}.
	 */
	public String getContents() {
		return this.textDocumentContents;
	}

	/**
	 * Performs the Acceleo validation.
	 * 
	 * @param acceleoEnvironment
	 *            the (non-{@code null}) {@link IAcceleoEnvironment}.
	 * @param acceleoValidator
	 *            the (non-{@code null}) {@link AcceleoValidator}.
	 * @param acceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link IAcceleoValidationResult}.
	 */
	private static IAcceleoValidationResult validate(IAcceleoEnvironment acceleoEnvironment,
			AcceleoValidator acceleoValidator, AcceleoAstResult acceleoAstResult) {
		// FIXME: Isn't there a more "proper" way to trigger validation ?
		acceleoEnvironment.registerModule("to::validate", acceleoAstResult.getModule());

		IAcceleoValidationResult validationResults = acceleoValidator.validate(acceleoAstResult,
				"to::validate");
		return validationResults;
	}

	/**
	 * Applies a {@link TextDocumentContentChangeEvent} to the {@link String} representing the text document
	 * contents.
	 * 
	 * @param textDocumentContentChangeEvent
	 *            the (non-{@code null}) {@link TextDocumentContentChangeEvent} to apply.
	 * @param inText
	 *            the (non-{@code null}) current {@link String text document contents}.
	 * @return the new {@link String text document contents}.
	 */
	private static String apply(TextDocumentContentChangeEvent textDocumentContentChangeEvent,
			String inText) {
		String newTextExcerpt = textDocumentContentChangeEvent.getText();
		Range changeRange = textDocumentContentChangeEvent.getRange();
		// We can safely ignore the range length, which gets deprecated in the next version of LSP.
		// cf. https://github.com/Microsoft/language-server-protocol/issues/9

		if (changeRange == null) {
			// The whole text was replaced.
			return newTextExcerpt;
		} else {
			return AcceleoLanguageServerPositionUtils.replace(inText, changeRange.getStart(), changeRange
					.getEnd(), newTextExcerpt);
		}
	}
}
