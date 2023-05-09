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
 * A proposal for the completion.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface ICompletionProposal {

	/**
	 * Gets the proposed {@link String}.
	 * 
	 * @return the proposed {@link String}
	 */
	String getProposal();

	/**
	 * Gets the cursor offset in the {@link ICompletionProposal#getProposal() proposed message}.
	 * 
	 * @return the cursor offset in the {@link ICompletionProposal#getProposal() proposed message}
	 */
	int getCursorOffset();

	/**
	 * Gets the {@link Object} associated to the proposal.
	 * 
	 * @return the {@link Object} associated to the proposal
	 */
	Object getObject();

	/**
	 * Return a description that describes this proposal.
	 * 
	 * @return a description that describes this proposal.
	 */
	String getDescription();

}
