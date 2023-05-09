/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import java.util.Comparator;

import org.eclipse.acceleo.query.runtime.impl.BasicFilter;
import org.eclipse.acceleo.query.runtime.impl.ProposalComparator;
import org.eclipse.acceleo.query.runtime.impl.QueryCompletionEngine;

/**
 * Static utility methods pertaining to completion and code assist for Acceleo queries.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public final class QueryCompletion {
	/** Hides the default constructor. */
	private QueryCompletion() {
		// Shouldn't be instantiated.
	}

	/**
	 * Create a new {@link IQueryCompletionEngine} for the given {@link IQueryEnvironment}.
	 * 
	 * @param environment
	 *            the {@link IQueryEnvironment} to use.
	 * @return a new {@link IQueryCompletionEngine} for the given {@link IQueryEnvironment}.
	 */
	public static IQueryCompletionEngine newEngine(IQueryEnvironment environment) {
		return new QueryCompletionEngine(environment);
	}

	/**
	 * Create a new {@link IProposalFilter} which uses the prefix and remaining strings to filter proposal.
	 * 
	 * @param result
	 *            a completion result.
	 * @return a new {@link IProposalFilter} which uses the prefix and remaining strings to filter proposal.
	 */
	public static IProposalFilter createBasicFilter(ICompletionResult result) {
		return new BasicFilter(result);
	}

	/**
	 * Create a new {@link Comparator<ICompletionProposal>} suitable for ordering completion proposals.
	 * 
	 * @return a new {@link Comparator<ICompletionProposal>} suitable for ordering completion proposals.
	 */
	public static Comparator<ICompletionProposal> createProposalComparator() {
		return new ProposalComparator();
	}

}
