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

import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link VariableCompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class VariableCompletionProposalTests {

	@Test
	public void getCursorOffset() {
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar");

		assertEquals(5, proposal.getCursorOffset());
	}

	@Test
	public void getProposal() {
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar");

		assertEquals("myVar", proposal.getProposal());
	}

	@Test
	public void getDescription() {
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar");

		assertEquals("Variable myVar", proposal.getDescription());
	}

	@Test
	public void getObject() {
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar");

		assertEquals("myVar", proposal.getObject());
	}

	@Test
	public void testToString() {
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar");

		assertEquals("myVar", proposal.toString());
	}

}
