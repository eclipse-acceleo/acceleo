/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

/**
 * {@link BasicFilter} filters on prefix and remaining.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class BasicFilter implements IProposalFilter {
	/** Pre-compiled pattern that'll allow us to match proposals with camel case. */
	private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("([a-z]+|[A-Z][a-z]*)");

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
		String prefix = completionResult.getPrefix();
		if (prefix == null) {
			return true;
		}

		String candidateName = proposal.getProposal();
		if (proposal instanceof EClassifierCompletionProposal) {
			candidateName = ((EClassifierCompletionProposal)proposal).getObject().getName();
		}

		return startsWithOrMatchCamelCase(candidateName, prefix);
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
		boolean result = false;
		if (startsWithIgnoreCase(candidate, query)) {
			result = true;
		} else if (candidate != null) {
			// transform the query into a camelCase regex
			String regex = CAMEL_CASE_PATTERN.matcher(query).replaceAll("$1[^A-Z]*") + ".*";
			result = candidate.matches(regex);
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
