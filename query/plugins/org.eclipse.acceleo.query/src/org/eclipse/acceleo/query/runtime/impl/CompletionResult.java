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
package org.eclipse.acceleo.query.runtime.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IProposalFilter;

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
	 * Constructor.
	 * 
	 * @param proposals
	 *            the {@link List} of {@link ICompletionProposal}
	 */
	public CompletionResult(List<ICompletionProposal> proposals) {
		this.proposals = proposals;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#getProposals(org.eclipse.acceleo.query.runtime.IProposalFilter)
	 */
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#sort(java.util.Comparator)
	 */
	@Override
	public void sort(Comparator<ICompletionProposal> comparator) {
		Collections.sort(proposals, comparator);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#getPrefix()
	 */
	@Override
	public String getPrefix() {
		return prefix;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#setPrefix(java.lang.String)
	 */
	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#getRemaining()
	 */
	@Override
	public String getRemaining() {
		return remaining;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#setRemaining(java.lang.String)
	 */
	@Override
	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#getReplacementLength()
	 */
	@Override
	public int getReplacementLength() {
		return replacementLength;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#getReplacementOffset()
	 */
	@Override
	public int getReplacementOffset() {
		return replacementOffset;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#setReplacementLength(int)
	 */
	@Override
	public void setReplacementLength(int replacementLength) {
		this.replacementLength = replacementLength;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionResult#setReplacementOffset(int)
	 */
	@Override
	public void setReplacementOffset(int offset) {
		this.replacementOffset = offset;
	}
}
