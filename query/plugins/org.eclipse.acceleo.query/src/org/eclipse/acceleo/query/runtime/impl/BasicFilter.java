/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.regex.Pattern;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IProposalFilter;
import org.eclipse.acceleo.query.runtime.impl.completion.EClassifierCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EEnumLiteralCompletionProposal;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;

/**
 * {@link BasicFilter} filters on prefix and remaining.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class BasicFilter implements IProposalFilter {

	/** Pre-compiled pattern that'll allow us to match proposals with camel case. */
	private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("([a-z]+|[A-Z][a-z]*)");

	/** Pre-compiled pattern that'll allow us to change the prefix to match in a case insensitive way. */
	private static final Pattern CAMEL_CASE_PREFIX_PATTERN = Pattern.compile("^([a-z]+)");

	/**
	 * The {@link ICompletionResult}.
	 */
	private final ICompletionResult completionResult;

	/**
	 * Constructor.
	 *
	 * @param result
	 *            the {@link ICompletionResult}
	 */
	public BasicFilter(ICompletionResult result) {
		this.completionResult = result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IProposalFilter#keepProposal(org.eclipse.acceleo.query.runtime.ICompletionProposal)
	 */
	@Override
	public boolean keepProposal(ICompletionProposal proposal) {
		final boolean result;

		final String prefix = completionResult.getPrefix();
		if (prefix == null) {
			result = true;
		} else if (proposal instanceof EClassifierCompletionProposal) {
			final String[] segments = prefix.split("::?");
			final EClassifier eClassifier = ((EClassifierCompletionProposal)proposal).getObject();
			if (segments.length == 3) {
				result = false;
			} else if (segments.length == 2) {
				result = startsWithOrMatchCamelCase(eClassifier.getEPackage().getName(), segments[0])
						&& startsWithOrMatchCamelCase(eClassifier.getName(), segments[1]);
			} else if (segments.length == 1) {
				final boolean endsWithEmptySegment = prefix.endsWith(":");
				result = startsWithOrMatchCamelCase(eClassifier.getEPackage().getName(), segments[0])
						|| (!endsWithEmptySegment && startsWithOrMatchCamelCase(eClassifier.getName(),
								segments[0]));
			} else {
				result = true;
			}
		} else if (proposal instanceof EEnumLiteralCompletionProposal) {
			final String[] segments = prefix.split("::?");
			final EEnumLiteral eEnumLiteral = ((EEnumLiteralCompletionProposal)proposal).getObject();
			if (segments.length == 3) {
				result = startsWithOrMatchCamelCase(eEnumLiteral.getEEnum().getEPackage().getName(),
						segments[0]) && startsWithOrMatchCamelCase(eEnumLiteral.getEEnum().getName(),
								segments[1]) && startsWithOrMatchCamelCase(eEnumLiteral.getName(),
										segments[2]);
			} else if (segments.length == 2) {
				final boolean endsWithEmptySegment = prefix.endsWith(":");
				result = startsWithOrMatchCamelCase(eEnumLiteral.getEEnum().getEPackage().getName(),
						segments[0]) && (startsWithOrMatchCamelCase(eEnumLiteral.getEEnum().getName(),
								segments[1]) || (!endsWithEmptySegment && startsWithOrMatchCamelCase(
										eEnumLiteral.getName(), segments[1])));
			} else if (segments.length == 1) {
				final boolean endsWithEmptySegment = prefix.endsWith(":");
				result = startsWithOrMatchCamelCase(eEnumLiteral.getEEnum().getEPackage().getName(),
						segments[0]) || (!endsWithEmptySegment && startsWithOrMatchCamelCase(eEnumLiteral
								.getEEnum().getName(), segments[0])) || (!endsWithEmptySegment
										&& startsWithOrMatchCamelCase(eEnumLiteral.getName(), segments[0]));
			} else {
				result = true;
			}
		} else {
			result = startsWithOrMatchCamelCase(proposal.getProposal(), prefix);
		}

		return result;
	}

	/* visible for testing */
	/**
	 * Checks whether the given candidate starts with the given query, or if it matches said query as
	 * camelCase.
	 * <p>
	 * The String "thisIsAPotentialResult" must match the given queries :
	 * <ul>
	 * <li>th</li>
	 * <li>thIAPR</li>
	 * <li>tIA</li>
	 * <li>tIAPoR</li>
	 * <li>thisisa</li>
	 * <li>thisisA</li>
	 * </ul>
	 * However, it will not match :
	 * <ul>
	 * <li>tho</li>
	 * <li>tIaP</li>
	 * <li>tIApotential</li>
	 * <li>TIApotential</li>
	 * </ul>
	 * </p>
	 * 
	 * @param candidate
	 *            The candidate string.
	 * @param query
	 *            The query {@code candidate} must match.
	 * @return <code>true</code> if {@code candidate} matches {@code query}, <code>false</code> otherwise.
	 */
	public static boolean startsWithOrMatchCamelCase(String candidate, String query) {
		final boolean result;

		final String localCandidate;
		if (candidate != null && candidate.startsWith("_")) {
			localCandidate = candidate.substring(1);
		} else {
			localCandidate = candidate;
		}

		final String localQuery;
		if (candidate != null && query.startsWith("_")) {
			localQuery = query.substring(1);
		} else {
			localQuery = query;
		}

		if (startsWithIgnoreCase(localCandidate, localQuery)) {
			result = true;
		} else if (localCandidate != null) {
			// transform the query into a camelCase regex
			String regex = CAMEL_CASE_PATTERN.matcher(localQuery).replaceAll("$1[^A-Z]*") + ".*";
			// make the lowercase prefix case insensitive match
			regex = CAMEL_CASE_PREFIX_PATTERN.matcher(regex).replaceFirst("(?i)$1(?-i)");
			// allows any prefix
			regex = ".*?" + regex;
			result = localCandidate.matches(regex);
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * Checks if the given candidate String starts with the given prefix, ignoring case.
	 * 
	 * @param candidate
	 *            The candidate string.
	 * @param prefix
	 *            The expected prefix of {@code candidate}.
	 * @return <code>true</code> if the given {@code candidate} starts with the given {@code prefix}, ignoring
	 *         case.
	 */
	private static boolean startsWithIgnoreCase(String candidate, String prefix) {
		return candidate != null && candidate.regionMatches(true, 0, prefix, 0, prefix.length());
	}
}
