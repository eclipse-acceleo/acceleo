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

import java.util.Objects;

import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.writer.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerStringUtils;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.emf.common.util.URI;
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
	 * The URI of the text document.
	 */
	private final String textDocumentUri;

	/**
	 * The {@link IAcceleoEnvironment} used to analyze the text document.
	 */
	private final IAcceleoEnvironment acceleoEnvironment;

	// /**
	// * The {@link AcceleoParser} used to parse the text document.
	// */
	// private final AcceleoParser acceleoParser;

	/**
	 * The {@link AcceleoValidator} used to validate the text document's contents.
	 */
	private final AcceleoValidator acceleoValidator;

	/**
	 * The {@link AcceleoAstResult} resulting form parsing the contents of the text document.
	 */
	private AcceleoAstResult parsedAcceleoAstResult;

	/**
	 * The {@link String} contents of the text document.
	 */
	private String textDocumentContents;

	/**
	 * Creates a new {@link AcceleoTextDocument} corresponding to the given URI and with the given initial
	 * contents.
	 * 
	 * @param textDocumentUri
	 *            the (non-{@code null}) URI of the text document.
	 * @param textDocumentContents
	 *            the (non-{@code null}) initial contents of the text document.
	 */
	public AcceleoTextDocument(String textDocumentUri, String textDocumentContents) {
		Objects.requireNonNull(textDocumentUri);
		Objects.requireNonNull(textDocumentContents);

		this.textDocumentUri = textDocumentUri;
		this.acceleoEnvironment = new AcceleoEnvironment(new DefaultGenerationStrategy(), URI.createURI(""));
		this.acceleoValidator = new AcceleoValidator(this.getAcceleoEnvironment());

		this.setContents(textDocumentContents);
	}

	private AcceleoParser getParser() {
		return new AcceleoParser(this.getAcceleoEnvironment().getQueryEnvironment());
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

		this.setContents(newTextDocumentContents);

		return this;
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
			return AcceleoLanguageServerStringUtils.replace(inText, changeRange.getStart(), changeRange
					.getEnd(), newTextExcerpt);
		}
	}

	/**
	 * Updates the known contents of the text document, which triggers the parsing of the new contents.
	 * 
	 * @param newTextDocumentContents
	 *            the new (non-{@code null}) contents of the text document.
	 */
	private void setContents(String newTextDocumentContents) {
		this.textDocumentContents = newTextDocumentContents;
		this.parsedAcceleoAstResult = this.getParser().parse(this.textDocumentContents);
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
	 * Provides the Abstract Syntax Tree (AST) corresponding to the contents of this text document.
	 * 
	 * @return the (non-{@code null}) {@link AcceleoAstResult} of this document.
	 */
	public AcceleoAstResult getParsedAcceleoAstResult() {
		return parsedAcceleoAstResult;
	}

	/**
	 * Provides the results of the Acceleo validation of the contents of this text document.
	 * 
	 * @return the (non-{@code null}) {@link IAcceleoValidationResult} of this document.
	 */
	public IAcceleoValidationResult getValidationResults() {
		// FIXME: Isn't there a more "proper" way to trigger validation ?
		this.getAcceleoEnvironment().registerModule("to::validate", this.getParsedAcceleoAstResult()
				.getModule());

		// FIXME: also we probably want to do the validation "once" and cache its results.
		// Once := upon a change on the document coming from the client, or in the workspace and that has an
		// impact on our document.
		IAcceleoValidationResult validationResults = this.acceleoValidator.validate(this
				.getParsedAcceleoAstResult(), "to::validate");
		return validationResults;
	}

}
