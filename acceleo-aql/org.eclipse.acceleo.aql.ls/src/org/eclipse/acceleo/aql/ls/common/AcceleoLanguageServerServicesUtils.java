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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.acceleo.aql.location.AcceleoLocationLink;
import org.eclipse.acceleo.aql.outline.AcceleoSymbol;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DocumentSymbol;
import org.eclipse.lsp4j.LocationLink;
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
	 * Transforms an {@link ICompletionProposal} from Acceleo into a corresponding {@link CompletionItem} for
	 * LSP4J.
	 * 
	 * @param acceleoCompletionProposal
	 *            the (non-{@code null}) {@link ICompletionProposal}, with a non-{@code null} proposed text,
	 *            to transform.
	 * @return the {@link CompletionItem} corresponding to {@code acceleoCompletionProposal}.
	 */
	public static CompletionItem transform(ICompletionProposal acceleoCompletionProposal) {
		Objects.requireNonNull(acceleoCompletionProposal);

		String text = acceleoCompletionProposal.getProposal();

		if (text != null) {
			CompletionItem completionItem = new CompletionItem();
			completionItem.setDocumentation(acceleoCompletionProposal.getDescription());
			completionItem.setData(acceleoCompletionProposal.getObject());
			completionItem.setKind(CompletionItemKind.Text);
			completionItem.setInsertText(text);
			// FIXME: Acceleo should provide us with more information about the proposal.
			completionItem.setLabel(text);
			return completionItem;
		} else {
			throw new IllegalArgumentException("The text proposed by an " + acceleoCompletionProposal
					.getClass().getCanonicalName() + " should not be null.");
		}
	}

	/**
	 * Transforms an {@link AcceleoLocationLink} from Acceleo into a corresponding {@link LocationLink} for
	 * LSP4J.
	 * 
	 * @param acceleoLocationLink
	 *            the (non-{@code null}) {@link AcceleoLocationLink} to transform.
	 * @return the {@link LocationLink} corresponding to {@code acceleoLocationLink}.
	 */
	public static LocationLink transform(AcceleoLocationLink acceleoLocationLink) {
		Objects.requireNonNull(acceleoLocationLink);

		// TODO: implement transformation once we know what the AcceleoLocationLink API looks like.
		throw new UnsupportedOperationException("TODO: implement");
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

		Range range = AcceleoLanguageServerStringUtils.getCorrespondingRange(acceleoSymbol
				.getSemanticElement().getStartPosition(), acceleoSymbol.getSemanticElement().getEndPosition(),
				acceleoSourceContents);
		String symbolName = acceleoSymbol.getName();

		// FIXME: workaround to lsp4e not supporting the display of a symbol's details.
		if (acceleoSymbol.getDetails() != null) {
			symbolName += " : " + acceleoSymbol.getDetails();
		}

		// FIXME: is there any way to distinguish range and selectionRange with the Acceleo parser ?
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
	 *            the (non-{@code null}) {@link IAcceleoValidationResult} to transform.
	 * @param acceleoSourceContents
	 *            the (non-{@code null}) {@link String} of the Acceleo source contents, so we can translate
	 *            characters indices (from Acceleo) to {@link Position Positions} (from LSP4J).
	 * @return the {@link List} of {@link Diagnostic Diagnostics} corresponding to
	 *         {@code acceleoValidationResults}.
	 */
	public static List<Diagnostic> transform(IAcceleoValidationResult acceleoValidationResults,
			String acceleoSourceContents) {
		Objects.requireNonNull(acceleoValidationResults);

		List<Diagnostic> diagnostics = acceleoValidationResults.getValidationMessages().stream().map(
				acceleoValidationMessage -> AcceleoLanguageServerServicesUtils.transform(
						acceleoValidationMessage, acceleoSourceContents)).collect(Collectors.toList());

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

		Range range = AcceleoLanguageServerStringUtils.getCorrespondingRange(startPosition, endPosition,
				acceleoSourceContents);
		String message = acceleoValidationMessage.getMessage();
		DiagnosticSeverity severity = transform(acceleoValidationMessage.getLevel());
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
