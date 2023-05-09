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

import java.util.Comparator;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.JavaMethodServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;

/**
 * A default {@link Comparator} for {@link ICompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ProposalComparator implements Comparator<ICompletionProposal> {

	/**
	 * {@inheritDoc}
	 *
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(ICompletionProposal proposal1, ICompletionProposal proposal2) {
		final int result;

		if (proposal1 instanceof VariableCompletionProposal) {
			if (proposal2 instanceof VariableCompletionProposal) {
				result = proposal1.getProposal().compareTo(proposal2.getProposal());
			} else {
				result = 1;
			}
		} else if (proposal1 instanceof JavaMethodServiceCompletionProposal) {
			if (proposal2 instanceof JavaMethodServiceCompletionProposal) {
				result = proposal1.getProposal().compareTo(proposal2.getProposal());
			} else {
				result = -1;
			}
		} else {
			result = proposal1.getProposal().compareTo(proposal2.getProposal());
		}
		return result;
	}

}
