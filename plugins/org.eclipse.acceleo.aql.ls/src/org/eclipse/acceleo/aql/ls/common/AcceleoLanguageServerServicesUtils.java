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
package org.eclipse.acceleo.aql.ls.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplateCompletionProposal;
import org.eclipse.acceleo.aql.outline.AcceleoSymbol;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.SymbolKind;

/**
 * Utility methods to make the Acceleo and LSP4J APIs work together.
 * 
 * @author Florent Latombe
 */
public final class AcceleoLanguageServerServicesUtils {

	private AcceleoLanguageServerServicesUtils() {
	}

	/**
	 * Creates a normalized {@link URI} from a {@link String}.
	 * 
	 * @param stringUri
	 *            the (non-{@code null}) {@link String} to transform.
	 * @return the corresponding normalized {@link URI}.
	 */
	public static URI toUri(String stringUri) {
		try {
			return new URI(stringUri).normalize();
		} catch (URISyntaxException uriException) {
			throw new RuntimeException("Unexpected URI syntax: " + stringUri, uriException);
		}
	}

	/**
	 * Transforms a {@link List} of {@link AcceleoCompletionProposal} from Acceleo into a corresponding
	 * {@link List} of {@link CompletionItem} for LSP4J.
	 * 
	 * @param acceleoCompletionProposals
	 *            the (non-{@code null}) {@link List} of {@link AcceleoCompletionProposal}, with a
	 *            non-{@code null} proposed text, to transform.
	 * @return the {@link List} of {@link CompletionItem} corresponding to {@code acceleoCompletionProposals}.
	 */
	public static List<CompletionItem> transform(List<AcceleoCompletionProposal> acceleoCompletionProposals) {
		Objects.requireNonNull(acceleoCompletionProposals);

		return acceleoCompletionProposals.stream().map(acceleoCompletionProposal -> {
			String text = acceleoCompletionProposal.getText();

			if (text != null) {
				CompletionItem completionItem = new CompletionItem();
				String proposalDescription = acceleoCompletionProposal.getDescription();
				if (proposalDescription != null) {
					completionItem.setDocumentation(proposalDescription);
				} else {
					// If there is no description then the documentation is simply the text.
					completionItem.setDocumentation(text);
				}
				completionItem.setInsertText(text);
				completionItem.setLabel(acceleoCompletionProposal.getLabel());
				completionItem.setKind(getKind(acceleoCompletionProposal));

				// Keep the same List order.
				String sortText = String.join("", Collections.nCopies(acceleoCompletionProposals.indexOf(
						acceleoCompletionProposal), "a"));
				completionItem.setSortText(sortText);
				return completionItem;
			} else {
				throw new IllegalArgumentException("The text proposed by an " + acceleoCompletionProposal
						.getClass().getCanonicalName() + " should not be null.");
			}
		}).collect(Collectors.toList());
	}

	/**
	 * Provides the {@link CompletionItemKind} corresponding to an {@link AcceleoCompletionProposal}.
	 * 
	 * @param acceleoCompletionProposal
	 *            the (non-{@code null}) {@link AcceleoCompletionProposal}.
	 * @return the {@link CompletionItemKind} corresponding to {@code acceleoCompletionProposal}.
	 */
	private static CompletionItemKind getKind(AcceleoCompletionProposal acceleoCompletionProposal) {
		CompletionItemKind completionItemKind;
		if (acceleoCompletionProposal instanceof AcceleoCodeTemplateCompletionProposal) {
			completionItemKind = CompletionItemKind.Snippet;
		} else {
			EClass acceleoType = acceleoCompletionProposal.getAcceleoType();
			if (acceleoType == null) {
				completionItemKind = CompletionItemKind.Text;
			} else {
				completionItemKind = new AcceleoAstNodeToCompletionItemKind().doSwitchOnType(
						acceleoCompletionProposal.getAcceleoType());
			}
		}
		return completionItemKind;
	}

	/**
	 * Transforms an {@link AcceleoSymbol} from Acceleo into a corresponding {@link DocumentSymbol} for LSP4J.
	 * 
	 * @param acceleoSymbol
	 *            the (non-{@code null}) {@link AcceleoSymbol} to transform.
	 * @param acceleoSourceContents
	 *            the (non-{@code null}) {@link String} of the Acceleo source contents, so we can translate
	 *            characters indices (from Acceleo) to {@link Position Positions} (from LSP4J).
	 * @return the {@link DocumentSymbol} corresponding to {@code acceleoSymbol}.
	 */
	public static DocumentSymbol transform(AcceleoSymbol acceleoSymbol, String acceleoSourceContents) {
		Objects.requireNonNull(acceleoSymbol);

		final int startPosition = acceleoSymbol.getAcceleoValidationResult().getAcceleoAstResult()
				.getStartPosition(acceleoSymbol.getSemanticElement());
		final int endPosition = acceleoSymbol.getAcceleoValidationResult().getAcceleoAstResult()
				.getEndPosition(acceleoSymbol.getSemanticElement());
		Range range = AcceleoLanguageServerPositionUtils.getCorrespondingRange(startPosition, endPosition,
				acceleoSourceContents);
		String symbolName = acceleoSymbol.getName();

		// FIXME: workaround to lsp4e not adding separator between symbol and details.
		if (acceleoSymbol.getDetails() != null) {
			symbolName += " : ";
		}

		// FIXME: for now we don't make a difference between range and selectionRange.
		DocumentSymbol documentSymbol = new DocumentSymbol(symbolName, getKind(acceleoSymbol), range, range,
				acceleoSymbol.getDetails(), acceleoSymbol.getChildren().stream().map(
						childAcceleoSymbol -> transform(childAcceleoSymbol, acceleoSourceContents)).collect(
								Collectors.toList()));

		return documentSymbol;
	}

	/**
	 * Maps an {@link AcceleoSymbol} to a corresponding {@link SymbolKind}.
	 * 
	 * @param acceleoSymbol
	 *            the (non-{@code null}) {@link AcceleoSymbol}.
	 * @return the corresponding {@link SymbolKind}.
	 */
	private static SymbolKind getKind(AcceleoSymbol acceleoSymbol) {
		return new AcceleoAstNodeToSymbolKind(acceleoSymbol.getAcceleoValidationResult()).doSwitch(
				acceleoSymbol.getSemanticElement());
	}

	/**
	 * Transforms an {@link IAcceleoValidationResult} from Acceleo into a corresponding {@link List} of
	 * {@link Diagnostic Diagnostics} for LSP4J.
	 * 
	 * @param acceleoValidationResults
	 *            the (maybe-{@code null}) {@link IAcceleoValidationResult} to transform.
	 * @param acceleoSourceContents
	 *            the (non-{@code null}) {@link String} of the Acceleo source contents, so we can translate
	 *            characters indices (from Acceleo) to {@link Position Positions} (from LSP4J).
	 * @return the {@link List} of {@link Diagnostic Diagnostics} corresponding to
	 *         {@code acceleoValidationResults}.
	 */
	public static List<Diagnostic> transform(IAcceleoValidationResult acceleoValidationResults,
			String acceleoSourceContents) {
		List<Diagnostic> diagnostics = new ArrayList<>();
		if (acceleoValidationResults != null) {
			diagnostics.addAll(acceleoValidationResults.getValidationMessages().stream().map(
					acceleoValidationMessage -> AcceleoLanguageServerServicesUtils.transform(
							acceleoValidationMessage, acceleoSourceContents)).collect(Collectors.toList()));
		} else {
			diagnostics.add(getNullValidationDiagnosticFor(acceleoSourceContents));
		}
		return diagnostics;
	}

	/**
	 * Transforms an {@link IValidationMessage} form Acceleo into a corresponding {@link Diagnostic} for
	 * LSP4J.
	 * 
	 * @param acceleoValidationMessage
	 *            the (non-{@code null}) {@link IValidationMessage} to transform.
	 * @param acceleoSourceContents
	 *            the (non-{@code null}) {@link String} of the Acceleo source contents, so we can translate
	 *            characters indices (from Acceleo) to {@link Position Positions} (from LSP4J).
	 * @return the {@link Diagnostic} corresponding to {@code acceleoValidationMessage}.
	 */
	private static Diagnostic transform(IValidationMessage acceleoValidationMessage,
			String acceleoSourceContents) {
		int startPosition = acceleoValidationMessage.getStartPosition();
		int endPosition = acceleoValidationMessage.getEndPosition();

		Range range = AcceleoLanguageServerPositionUtils.getCorrespondingRange(startPosition, endPosition,
				acceleoSourceContents);
		String message = acceleoValidationMessage.getMessage();
		DiagnosticSeverity severity = transform(acceleoValidationMessage.getLevel());
		String source = "Acceleo Validation";
		return new Diagnostic(range, message, severity, source);
	}

	/**
	 * Creates a {@link Diagnostic} for a {@code null} {@link IAcceleoValidationResult}.
	 * 
	 * @param documentContents
	 *            the (non-{@code null}) {@link String contents} of the document whose validation results were
	 *            {@code null}.
	 * @return a (non-{@code null}) {@link Diagnostic} that displays to the user that the validation could not
	 *         be performed.
	 */
	private static Diagnostic getNullValidationDiagnosticFor(String documentContents) {
		int startPosition = 0;
		int endPosition = documentContents.length() - 1;

		Range range = AcceleoLanguageServerPositionUtils.getCorrespondingRange(startPosition, endPosition,
				documentContents);
		String message = "The Acceleo validation could not be performed on this document.";
		DiagnosticSeverity severity = DiagnosticSeverity.Warning;
		String source = "Acceleo Validation";
		return new Diagnostic(range, message, severity, source);
	}

	/**
	 * Transforms a {@link ValidationMessageLevel} from Acceleo into the corresponding
	 * {@link DiagnosticSeverity} for LSP4J.
	 * 
	 * @param acceleoValidationMessageLevel
	 *            the (non-{@code null}) {@link ValidationMessageLevel}.
	 * @return the corresponding {@link DiagnosticSeverity}.
	 */
	private static DiagnosticSeverity transform(ValidationMessageLevel acceleoValidationMessageLevel) {
		DiagnosticSeverity diagnosticSeverity = null;
		switch (acceleoValidationMessageLevel) {
			case INFO:
				diagnosticSeverity = DiagnosticSeverity.Information;
				break;
			case WARNING:
				diagnosticSeverity = DiagnosticSeverity.Warning;
				break;
			case ERROR:
				diagnosticSeverity = DiagnosticSeverity.Error;
				break;
			default:
				throw new IllegalArgumentException("Unsupported " + ValidationMessageLevel.class
						.getCanonicalName() + " literal: " + acceleoValidationMessageLevel.toString());
		}
		return diagnosticSeverity;
	}

}
