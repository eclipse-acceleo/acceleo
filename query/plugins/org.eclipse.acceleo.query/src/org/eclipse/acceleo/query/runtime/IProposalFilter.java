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

/**
 * Filter for {@link ICompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IProposalFilter {

	/**
	 * Tells if we should keep the given {@link ICompletionProposal}.
	 * 
	 * @param proposal
	 *            the {@link ICompletionProposal} to check
	 * @return <code>true</code> if we should keep the given {@link ICompletionProposal}, <code>false</code>
	 *         otherwise
	 */
	boolean keepProposal(ICompletionProposal proposal);

}
