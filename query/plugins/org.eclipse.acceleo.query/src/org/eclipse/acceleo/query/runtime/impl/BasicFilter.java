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

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IProposalFilter;

/**
 * {@link BasicFilter} filters on prefix and remaining.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class BasicFilter implements IProposalFilter {

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
		final String name = proposal.getProposal();
		String prefix = completionResult.getPrefix();
		if (prefix == null) {
			return true;
		}
	
		boolean result = false;
		if (name.substring(0, prefix.length()).equalsIgnoreCase(prefix)) {
			String remaining = completionResult.getRemaining();
			if (remaining == null) {
			remaining = "";
			}
			if (remaining != null && remaining.length() != 0) {
			result = name.lastIndexOf(remaining) >= prefix.length();
			} else {
			result = true;
			}
		}

		return result;
	}

}
