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
}
