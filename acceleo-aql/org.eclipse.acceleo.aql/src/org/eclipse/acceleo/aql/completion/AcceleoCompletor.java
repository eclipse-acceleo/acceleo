/*******************************************************************************
 * Copyright (c) 2017, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.emf.ecore.EObject;

/**
 * Acceleo service for content assist / auto-completion.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoCompletor {

	/**
	 * The name space for completion.
	 */
	private static final String TO_COMPLETION_NAMESPACE = "to::completion";

	/**
	 * Provides the {@link List} of {@link AcceleoCompletionProposal completion proposals} available for the
	 * given Acceleo source at the given position in the given environment.
	 * 
	 * @param acceleoEnvironment
	 *            the (non-{@code null}) contextual {@link IAcceleoEnvironment}.
	 * @param source
	 *            the (non-{@code null}) Acceleo text source contents.
	 * @param position
	 *            the caret position in {@code source}.
	 * @return the {@link List} of {@link AcceleoCompletionProposal} for the given source at the given
	 *         position
	 */
	public List<AcceleoCompletionProposal> getProposals(IAcceleoEnvironment acceleoEnvironment, String source,
			int position) {
		// First, parse the source contents up to the position.
		final AcceleoParser acceleoParser = new AcceleoParser(acceleoEnvironment.getQueryEnvironment());
		final String partialAcceleoSource = source.substring(0, position);
		final AcceleoAstResult partialAcceleoAstResult = acceleoParser.parse(partialAcceleoSource,
				TO_COMPLETION_NAMESPACE);

		// Second, validate the AST - this is required further on for the AQL completion.
		acceleoEnvironment.registerModule(TO_COMPLETION_NAMESPACE, partialAcceleoAstResult.getModule());
		final AcceleoValidator acceleoValidator = new AcceleoValidator(acceleoEnvironment);
		IAcceleoValidationResult acceleoValidationResult = acceleoValidator.validate(partialAcceleoAstResult,
				TO_COMPLETION_NAMESPACE);

		// Find which element of the AST we are completing.
		EObject acceleoElementToComplete = getElementToComplete(partialAcceleoAstResult);

		return this.getProposals(acceleoEnvironment, acceleoValidationResult, acceleoElementToComplete);
	}

	/**
	 * Provides the {@link List} of {@link AcceleoCompletionProposal completion proposals} for the given
	 * {@link EObject} of an Acceleo AST in the given environment.
	 * 
	 * @param acceleoEnvironment
	 *            the (non-{@code null}) contextual {@link IAcceleoEnvironment}.
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) contextual {@link IAcceleoValidationResult}.
	 * @param acceleoElementToComplete
	 *            the {@link EObject Acceleo AST element} to complete.
	 * @return the {@link List} of {@link AcceleoCompletionProposal}.
	 */
	protected List<AcceleoCompletionProposal> getProposals(IAcceleoEnvironment acceleoEnvironment,
			IAcceleoValidationResult acceleoValidationResult, EObject acceleoElementToComplete) {
		final List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		AcceleoAstCompletor acceleoSyntaxCompletor = new AcceleoAstCompletor(acceleoEnvironment,
				acceleoValidationResult);

		completionProposals.addAll(acceleoSyntaxCompletor.doSwitch(acceleoElementToComplete));

		return completionProposals;
	}

	/**
	 * Provides the Acceleo AST element to complete.
	 * 
	 * @param acceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult} to complete.
	 * @return the Acceleo AST element to complete.
	 */
	private EObject getElementToComplete(AcceleoAstResult acceleoAstResult) {
		final Error errorToComplete = getErrorToComplete(acceleoAstResult);
		if (errorToComplete == null) {
			final Module moduleToComplete = acceleoAstResult.getModule();
			return moduleToComplete;
		} else {
			return errorToComplete;
		}
	}

	/**
	 * Gets the {@link Error} to use for completion starting point. It's the first error whose
	 * {@link org.eclipse.acceleo.ASTNode#getEndPosition() end} is at the end of the {@link Module}.
	 * 
	 * @param acceleoAstResultToComplete
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @return the {@link Error} to use as the completion starting point. {@code null} if there is
	 *         {@link AcceleoAstResult#getErrors() no errors} in {@code acceleoAstResultToComplete}.
	 */
	private Error getErrorToComplete(AcceleoAstResult acceleoAstResultToComplete) {
		Objects.requireNonNull(acceleoAstResultToComplete);

		List<Error> errors = acceleoAstResultToComplete.getErrors();
		if (errors.isEmpty()) {
			return null;
		} else {
			Error currentError = errors.get(0);
			int currentErrorEndPosition = acceleoAstResultToComplete.getEndPosition(currentError);

			for (int i = 1; i < errors.size(); i++) {
				final Error candidateError = errors.get(i);
				int candidateErrorEnd = acceleoAstResultToComplete.getEndPosition(candidateError);
				if (candidateErrorEnd > currentErrorEndPosition) {
					currentError = candidateError;
					currentErrorEndPosition = candidateErrorEnd;
				}
			}

			return currentError;
		}
	}

}
