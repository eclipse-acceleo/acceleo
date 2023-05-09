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
package org.eclipse.acceleo.query.runtime.impl.completion;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;

/**
 * A text proposal for symbols and constant text.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TextCompletionProposal implements ICompletionProposal {

	/**
	 * The proposed {@link String}.
	 */
	private final String proposal;

	/**
	 * The cursor offset in the {@link ICompletionProposal#getProposal() proposed message}.
	 */
	private final int offset;

	/**
	 * Constructor.
	 * 
	 * @param proposal
	 *            the proposed {@link String}
	 * @param offsetFromtheEnd
	 *            the offset of the cursor from the end of the proposed {@link String}
	 */
	public TextCompletionProposal(String proposal, int offsetFromtheEnd) {
		this.proposal = proposal;
		this.offset = proposal.length() - offsetFromtheEnd;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getProposal()
	 */
	@Override
	public String getProposal() {
		return proposal;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getCursorOffset()
	 */
	@Override
	public int getCursorOffset() {
		return offset;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getObject()
	 */
	@Override
	public String getObject() {
		return proposal;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getProposal();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.ICompletionProposal#getDescription()
	 */
	@Override
	public String getDescription() {
		return getProposal();
	}

}
