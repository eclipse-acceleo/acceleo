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
import java.util.List;

/**
 * Result of a {@link IQueryCompletionEngine#getCompletion(String, int, java.util.Map) completion}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface ICompletionResult {

	/**
	 * Gets the {@link ICompletionProposal} according to the given {@link IProposalFilter}.
	 * 
	 * @param filter
	 *            the {@link IProposalFilter}
	 * @return the {@link ICompletionProposal} according to the given {@link IProposalFilter}
	 */
	List<ICompletionProposal> getProposals(IProposalFilter filter);

	/**
	 * Sorts the {@link ICompletionProposal} using the given {@link Comparator}.
	 * 
	 * @param comparator
	 *            the {@link Comparator} to use for {@link ICompletionProposal} sorting
	 */
	void sort(Comparator<ICompletionProposal> comparator);

	/**
	 * Gets the prefix of identifier part before the cursor.
	 * 
	 * @return the prefix of identifier part before the cursor, can be <code>null</code>
	 */
	String getPrefix();

	/**
	 * Sets the prefix of identifier part before the cursor.
	 * 
	 * @param prefix
	 *            the prefix of identifier part before the cursor
	 */
	void setPrefix(String prefix);

	/**
	 * Gets the remaining of identifier part after the cursor.
	 * 
	 * @return the remaining of identifier part after the cursor, can be <code>null</code>
	 */
	String getRemaining();

	/**
	 * Sets the remaining of identifier part after the cursor.
	 * 
	 * @param remaining
	 *            the remaining of identifier part after the cursor
	 */
	void setRemaining(String remaining);

	/**
	 * Returns the offset at which this completion proposal should start replacing existing text.
	 * 
	 * @return The offset at which this completion proposal should start replacing existing text.
	 * @since 4.1
	 */
	int getReplacementOffset();

	/**
	 * Sets the offset at which this completion proposal should start replacing existing text.
	 * 
	 * @param offset
	 *            The offset at which this completion proposal should start replacing existing text.
	 * @since 4.1
	 */
	void setReplacementOffset(int offset);

	/**
	 * If this proposal is going to replace text when accepted, this will return the number of characters to
	 * replace.
	 * 
	 * @return Number of characters to replace from the existing text starting at
	 *         {@link #getReplacementOffset()}.
	 * @since 4.1
	 */
	int getReplacementLength();

	/**
	 * Sets the number of characters to replace with this proposal.
	 * 
	 * @param replacementLength
	 *            The number of characters to replace with this proposal.
	 * @since 4.1
	 */
	void setReplacementLength(int replacementLength);
}
