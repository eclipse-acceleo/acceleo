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
package org.eclipse.acceleo.query.tests.runtime.impl.completion;

import org.eclipse.acceleo.query.runtime.impl.completion.TextCompletionProposal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link TextCompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TextCompletionProposalTests {

	@Test
	public void getProposal() {
		final TextCompletionProposal proposal = new TextCompletionProposal("proposal", 3);

		assertEquals("proposal", proposal.getProposal());
	}

	@Test
	public void getCursorOffset() {
		final TextCompletionProposal proposal = new TextCompletionProposal("proposal", 3);

		assertEquals(5, proposal.getCursorOffset());
	}

	@Test
	public void getDescription() {
		final TextCompletionProposal proposal = new TextCompletionProposal("proposal", 3);

		assertEquals("proposal", proposal.getDescription());
	}

	@Test
	public void getObject() {
		final TextCompletionProposal proposal = new TextCompletionProposal("proposal", 3);

		assertEquals("proposal", proposal.getObject());
	}

}
