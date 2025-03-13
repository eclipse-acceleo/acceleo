/*******************************************************************************
 * Copyright (c) 2015, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IProposalFilter;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Result of a
 * {@link org.eclipse.acceleo.query.runtime.IQueryCompletionEngine#getCompletion(String, int, java.util.Map)
 * completion}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CompletionResult implements ICompletionResult {

	/**
	 * The {@link List} of {@link ICompletionProposal}.
	 */
	private final List<ICompletionProposal> proposals;

	/**
	 * The prefix of identifier part before the cursor, can be <code>null</code>.
	 */
	private String prefix;

	/**
	 * The remaining of identifier part after the cursor, can be <code>null</code>.
	 */
	private String remaining;

	/** The offset at which this completion proposal should start replacing existing text. */
	private int replacementOffset;

	/** Number of characters to replace from the existing text starting at{@link #replacementOffset}. */
	private int replacementLength;

	/**
	 * The {@link IValidationResult}.
	 */
	private IValidationResult validationResult;

	/**
	 * Constructor.
	 * 
	 * @param proposals
	 *            the {@link List} of {@link ICompletionProposal}
	 * @deprecated see {@link #CompletionResult(List, IValidationResult)}
	 */
	public CompletionResult(List<ICompletionProposal> proposals) {
		this(proposals, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param proposals
	 *            the {@link List} of {@link ICompletionProposal}
	 * @param validationResult
	 *            THE {@link IValidationResult}
	 * @since 8.0.4
	 */
	public CompletionResult(List<ICompletionProposal> proposals, IValidationResult validationResult) {
		this.proposals = proposals;
		this.validationResult = validationResult;
	}

	@Override
	public List<ICompletionProposal> getProposals(IProposalFilter filter) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		for (ICompletionProposal proposal : proposals) {
			if (filter.keepProposal(proposal)) {
				result.add(proposal);
			}
		}

		return result;
	}

	@Override
	public void sort(Comparator<ICompletionProposal> comparator) {
		Collections.sort(proposals, comparator);
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String getRemaining() {
		return remaining;
	}

	@Override
	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}

	@Override
	public int getReplacementLength() {
		return replacementLength;
	}

	@Override
	public int getReplacementOffset() {
		return replacementOffset;
	}

	@Override
	public void setReplacementLength(int replacementLength) {
		this.replacementLength = replacementLength;
	}

	@Override
	public void setReplacementOffset(int offset) {
		this.replacementOffset = offset;
	}

	@Override
	public IValidationResult getIValidationResult() {
		return validationResult;
	}

	@Override
	public Set<IType> getPossibleReceiverTypes() {
		final Set<IType> result = new LinkedHashSet<>();

		final Error error = getIValidationResult().getErrorToComplete();
		if (error instanceof ErrorCall) {
			result.addAll(validationResult.getPossibleTypes(((ErrorCall)error).getArguments().get(0)));
		}

		return result;
	}

}
