/*******************************************************************************
 * Copyright (c) 2017, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.runtime.impl.QueryCompletionEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;

/**
 * Acceleo service for content assist / auto-completion.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoCompletor {

	/**
	 * The name space for completion.
	 */
	private static final String TO_COMPLETION_NAMESPACE = "_reserved_::to::completion";

	/**
	 * The new line {@link String}.
	 */
	private String newLine;

	/**
	 * The {@link EPackage.Registry} used for completion.
	 */
	private final Registry ePackageRegistry;

	/**
	 * Constructor.
	 * 
	 * @param newLine
	 *            the new line {@link String}
	 */
	public AcceleoCompletor(String newLine) {
		this(newLine, EPackage.Registry.INSTANCE);
	}

	/**
	 * Constructor.
	 * 
	 * @param newLine
	 *            the new line {@link String}
	 * @param ePackageRegistry
	 *            the {@link EPackage.Registry}
	 */
	public AcceleoCompletor(String newLine, Registry ePackageRegistry) {
		this.newLine = newLine;
		this.ePackageRegistry = ePackageRegistry;
	}

	/**
	 * Provides the {@link List} of {@link AcceleoCompletionProposal completion proposals} available for the
	 * given Acceleo source at the given position in the given environment.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param moduleFileName
	 *            the (non-{@code null}) name of the file containing the module (without extension).
	 * @param source
	 *            the (non-{@code null}) Acceleo text source contents.
	 * @param position
	 *            the caret position in {@code source}.
	 * @return the {@link List} of {@link AcceleoCompletionProposal} for the given source at the given
	 *         position
	 */
	public List<AcceleoCompletionProposal> getProposals(IQualifiedNameQueryEnvironment queryEnvironment,
			String moduleFileName, String source, int position) {
		String moduleQualifiedNameForCompletion = TO_COMPLETION_NAMESPACE + AcceleoParser.QUALIFIER_SEPARATOR
				+ moduleFileName;

		// First, parse the source contents up to the position.
		final AcceleoParser acceleoParser = new AcceleoParser();
		final String remaining = QueryCompletionEngine.getRemaining(source, position);
		final String partialAcceleoSource = source.substring(0, position + remaining.length());
		final AcceleoAstResult partialAcceleoAstResult = acceleoParser.parse(partialAcceleoSource.substring(0,
				position), null, moduleQualifiedNameForCompletion);

		// Second, validate the AST - this is required further on for the AQL completion.
		final AcceleoAstResult acceleoAstResult = acceleoParser.parse(source, null,
				moduleQualifiedNameForCompletion);
		queryEnvironment.getLookupEngine().getResolver().register(moduleQualifiedNameForCompletion,
				acceleoAstResult.getModule());
		final List<AcceleoCompletionProposal> proposals;
		try {
			final AcceleoValidator acceleoValidator = new AcceleoValidator(queryEnvironment,
					ePackageRegistry);
			IAcceleoValidationResult acceleoValidationResult = acceleoValidator.validate(
					partialAcceleoAstResult, moduleQualifiedNameForCompletion);

			// Find which element of the AST we are completing.
			final AcceleoASTNode acceleoElementToComplete = getElementToComplete(partialAcceleoAstResult);
			queryEnvironment.getLookupEngine().pushImportsContext(moduleQualifiedNameForCompletion,
					moduleQualifiedNameForCompletion);
			try {
				proposals = this.getProposals(queryEnvironment, moduleFileName, partialAcceleoSource,
						position, acceleoValidationResult, acceleoElementToComplete);
			} finally {
				queryEnvironment.getLookupEngine().popContext(moduleQualifiedNameForCompletion);
			}
		} finally {
			queryEnvironment.getLookupEngine().getResolver().clear(Collections.singleton(
					moduleQualifiedNameForCompletion));
			queryEnvironment.getLookupEngine().clearContext(moduleQualifiedNameForCompletion);
		}

		return proposals;
	}

	/**
	 * Provides the {@link List} of {@link AcceleoCompletionProposal completion proposals} for the given
	 * {@link EObject} of an Acceleo AST in the given environment.
	 * 
	 * @param queryEnvironment
	 *            the (non-{@code null}) contextual {@link IQualifiedNameQueryEnvironment}.
	 * @param computedModuleName
	 *            the module computed name
	 * @param sourceFragment
	 *            the module source fragment
	 * @param position
	 *            the caret position in {@code source}.
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) contextual {@link IAcceleoValidationResult}.
	 * @param acceleoElementToComplete
	 *            the {@link AcceleoASTNode} to complete.
	 * @return the {@link List} of {@link AcceleoCompletionProposal}.
	 */
	protected List<AcceleoCompletionProposal> getProposals(IQualifiedNameQueryEnvironment queryEnvironment,
			String computedModuleName, String sourceFragment, int position,
			IAcceleoValidationResult acceleoValidationResult, AcceleoASTNode acceleoElementToComplete) {
		final List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		AcceleoAstCompletor acceleoSyntaxCompletor = new AcceleoAstCompletor(queryEnvironment,
				acceleoValidationResult, newLine, ePackageRegistry);

		completionProposals.addAll(acceleoSyntaxCompletor.getCompletion(computedModuleName, sourceFragment,
				position, acceleoElementToComplete));

		return completionProposals;
	}

	/**
	 * Provides the {@link AcceleoASTNode} element to complete.
	 * 
	 * @param acceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult} to complete.
	 * @return the {@link AcceleoASTNode} element to complete.
	 */
	private AcceleoASTNode getElementToComplete(AcceleoAstResult acceleoAstResult) {
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
