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
import org.eclipse.acceleo.aql.location.AcceleoLocator;
import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerPositionUtils;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoProject;
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
	 * FIXME: move somewhere else.
	 */
	private static final String VALIDATION_NAMESPACE = "_reserved_::to::validate";

	/**
	 * The {@link URI} that uniquely identifies this document.
	 */
	private final URI uri;

	/**
	 * The {@link AcceleoProject} that contains this text document.
	 */
	private AcceleoProject ownerProject;

	/**
	 * The {@link String} contents of the text document.
	 */
	private String contents;

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
	 * @param textDocumentUri
	 *            the (non-{@code null}) {@link URI} of this text document.
	 * @param textDocumentContents
	 *            the (non-{@code null}) initial contents of this text document.
	 */
	public AcceleoTextDocument(URI textDocumentUri, String textDocumentContents) {
		Objects.requireNonNull(textDocumentUri);
		Objects.requireNonNull(textDocumentContents);

		this.uri = textDocumentUri;

		this.setContents(textDocumentContents);
	}

	/**
	 * Behavior triggered when the contents or environment of this document have changed. We update the cached
	 * values of the parsing and validation results.
	 */
	private void documentChanged() {
		// Retrieve the parsing result first, as other services depend on it.
		this.parseContents();

		// And validation second, as some other services depend on it.
		this.validateAndPublishResults();
	}

	/**
	 * This is called when this document is saved. Notifies external parties which may rely on this document.
	 */
	public void documentSaved() {
		// TODO: we probably only want to send this notification out when the "publicly-accessible" parts of
		// the Module have changed, like a public/protected Template/Query signature.
		if (this.getProject() != null) {
			// This text document belongs to an AcceleoProject which holds the AcceleoEnvironment in which
			// this module is registered.
			this.getProject().documentSaved(this);
		}
	}

	/**
	 * Sets the owner {@link AcceleoProject} of this document.
	 * 
	 * @param acceleoProject
	 *            the new (maybe-{@code null}) owner {@link AcceleoProject}.
	 */
	public void setProject(AcceleoProject acceleoProject) {
		this.ownerProject = acceleoProject;
		// When the project changes, the environment changes.
		this.environmentChanged();
	}

	/**
	 * This is called to notify this {@link AcceleoTextDocument} that its contextual
	 * {@link IAcceleoEnvironment} has changed. As a result, it needs to be re-parsed and re-validated.
	 */
	public void environmentChanged() {
		this.documentChanged();
	}

	/**
	 * Provides the owner {@link AcceleoProject} of this document.
	 * 
	 * @return the (maybe-{@code null}) owner {@link AcceleoProject}.
	 */
	public AcceleoProject getProject() {
		return this.ownerProject;
	}

	/**
	 * Provides the file name without its extension.
	 * 
	 * @return the file name of this text document.
	 */
	public String getFileNameWithoutExtension() {
		return this.uri.toString().substring(this.uri.toString().lastIndexOf('/'), this.uri.toString()
				.lastIndexOf('.'));
	}

	/**
	 * Parses the contents of this document and updates the cached AST.
	 */
	private void parseContents() {
		AcceleoAstResult parsingResult = null;
		if (this.getAcceleoEnvironment() != null) {
			parsingResult = doParsing(this.getModuleQualifiedName(), this.contents, this
					.getAcceleoEnvironment());
		}
		this.acceleoAstResult = parsingResult;
	}

	/**
	 * Performs the parsing.
	 * 
	 * @param moduleQualifiedName
	 *            the (non-{@code null}) qualified name of the document we are parsing.
	 * @param documentContents
	 *            the (non-{@code null}) contents of the document we are parsing.
	 * @param acceleoEnvironment
	 *            the (non-{@code null}) {@link IAcceleoEnvironment} of the document we are parsing.
	 * @return the resulting {@link AcceleoAstResult}.
	 */
	private static AcceleoAstResult doParsing(String moduleQualifiedName, String documentContents,
			IAcceleoEnvironment acceleoEnvironment) {
		Objects.requireNonNull(moduleQualifiedName);
		Objects.requireNonNull(documentContents);
		Objects.requireNonNull(acceleoEnvironment);

		AcceleoParser acceleoParser = new AcceleoParser(acceleoEnvironment.getQueryEnvironment());
		return acceleoParser.parse(documentContents, moduleQualifiedName);
	}

	/**
	 * Validates the contents of this document, and caches its results.
	 */
	public void validateContents() {
		IAcceleoValidationResult validationResults = null;
		if (this.acceleoAstResult != null && this.getAcceleoEnvironment() != null) {
			validationResults = doValidation(this.acceleoAstResult, this.getAcceleoEnvironment());
		}
		this.acceleoValidationResult = validationResults;
	}

	/**
	 * Validates the contents of this document using the {@link AcceleoValidator}. As a side effect, it
	 * registers the resulting
	 * 
	 * @param acceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult} to validate.
	 * @param acceleoEnvironment
	 *            the (non-{@code null}) {@link IAcceleoEnvironment} of the document being validated.
	 * @return the {@link IAcceleoValidationResult}.
	 */
	private static IAcceleoValidationResult doValidation(AcceleoAstResult acceleoAstResult,
			IAcceleoEnvironment acceleoEnvironment) {
		Objects.requireNonNull(acceleoAstResult);
		Objects.requireNonNull(acceleoEnvironment);

		return validate(acceleoEnvironment, new AcceleoValidator(acceleoEnvironment), acceleoAstResult);
	}

	/**
	 * Provides the owning {@link AcceleoLanguageServer}.
	 * 
	 * @return the (maybe-{@code null}) owning {@link AcceleoLanguageServer}.
	 */
	public AcceleoLanguageServer getLanguageServer() {
		if (this.getProject() != null) {
			return this.getProject().getLanguageServer();
		} else {
			return null;
		}
	}

	/**
	 * Provides the {@link AcceleoTextDocumentService} of the owning {@link AcceleoLanguageServer}.
	 * 
	 * @return the (maybe-{@code null}) {@link AcceleoTextDocumentService}.
	 */
	public AcceleoTextDocumentService getTextDocumentService() {
		AcceleoLanguageServer languageServer = this.getLanguageServer();
		if (languageServer != null) {
			return languageServer.getTextDocumentService();
		} else {
			return null;
		}
	}

	/**
	 * Publishes the cached validation results.
	 */
	public void publishValidationResults() {
		AcceleoTextDocumentService service = this.getTextDocumentService();
		if (service != null) {
			service.publishValidationResults(this);
		}
	}

	/**
	 * Validates this document and publishes the validation results.
	 */
	public void validateAndPublishResults() {
		this.validateContents();
		this.publishValidationResults();
	}

	/**
	 * Provides the qualified name of the Acceleo {@link Module} represented by this document, as computed by
	 * the {@link AcceleoEnvironment}'s {@link IQualifiedNameResolver}.
	 * 
	 * @return the qualified name of the {@link Module}. {@code null} if this document has no
	 *         {@link IAcceleoEnvironment}.
	 */
	public String getModuleQualifiedName() {
		if (this.getAcceleoEnvironment() == null || this.getAcceleoEnvironment()
				.getModuleResolver() == null) {
			return null;
		} else {
			return this.getAcceleoEnvironment().getModuleResolver().getQualifierName(this.getUrl());
		}
	}

	/**
	 * Provides the {@link URL} version of this document's {@link URI}.
	 * 
	 * @return the corresponding {@link URI}.
	 */
	public URL getUrl() {
		try {
			return this.getUri().toURL();
		} catch (MalformedURLException urlException) {
			throw new RuntimeException("Could not convert into a URL the URI of document " + this.getUri()
					.toString(), urlException);
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
		return new AcceleoLocator(this.getAcceleoEnvironment()).getDefinitionLocations(this.acceleoAstResult,
				position);
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
		return new AcceleoLocator(this.getAcceleoEnvironment()).getDeclarationLocations(this.acceleoAstResult,
				position);
	}

	/**
	 * Provides the URI of this {@link AcceleoTextDocument}.
	 * 
	 * @return the {@link URI} of this {@link AcceleoTextDocument}.
	 */
	public URI getUri() {
		return this.uri;
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
		String newTextDocumentContents = this.contents;
		for (TextDocumentContentChangeEvent textDocumentContentChangeEvent : textDocumentContentchangeEvents) {
			newTextDocumentContents = apply(textDocumentContentChangeEvent, newTextDocumentContents);
		}

		this.setContents(newTextDocumentContents);

		return this;
	}

	/**
	 * Updates the known contents of the text document, which triggers the parsing of the new contents.
	 * 
	 * @param newTextDocumentContents
	 *            the new (non-{@code null}) contents of the text document.
	 */
	public void setContents(String newTextDocumentContents) {
		this.contents = newTextDocumentContents;
		this.documentChanged();
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
	 * @return the (maybe-{@code null}) {@link IAcceleoEnvironment} associated to this
	 *         {@link AcceleoTextDocument}.
	 */
	public IAcceleoEnvironment getAcceleoEnvironment() {
		if (this.ownerProject != null) {
			return this.ownerProject.getAcceleoEnvironment();
		} else {
			return null;
		}
	}

	/**
	 * Provides the current {@link String text contents} of this text document.
	 * 
	 * @return the (non-{@code null}) {@link String} contents of this {@link AcceleoTextDocument}.
	 */
	public String getContents() {
		return this.contents;
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
		String moduleQualifiedNameForValidation = VALIDATION_NAMESPACE + AcceleoParser.QUALIFIER_SEPARATOR
				+ acceleoAstResult.getModule().getName();
		acceleoEnvironment.registerModule(moduleQualifiedNameForValidation, acceleoAstResult.getModule());

		IAcceleoValidationResult validationResults = acceleoValidator.validate(acceleoAstResult,
				moduleQualifiedNameForValidation);
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
