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

import org.eclipse.acceleo.query.runtime.impl.completion.VariableDeclarationCompletionProposal;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link VariableDeclarationCompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class VariableDeclarationCompletionProposalTests {

	@Test
	public void getCursorOffset() {
		final VariableDeclarationCompletionProposal proposal = new VariableDeclarationCompletionProposal(
				new ClassType(null, Object.class));

		assertEquals(11, proposal.getCursorOffset());
	}

	@Test
	public void getProposal() {
		final VariableDeclarationCompletionProposal proposal = new VariableDeclarationCompletionProposal(
				new ClassType(null, Object.class));

		assertEquals("myObject | ", proposal.getProposal());
	}

	@Test
	public void getDescription() {
		final VariableDeclarationCompletionProposal proposal = new VariableDeclarationCompletionProposal(
				new ClassType(null, Object.class));

		assertEquals("myObject | ", proposal.getDescription());
	}

	@Test
	public void getObject() {
		final VariableDeclarationCompletionProposal proposal = new VariableDeclarationCompletionProposal(
				new ClassType(null, Object.class));

		assertEquals(new ClassType(null, Object.class), proposal.getObject());
	}

	@Test
	public void testToString() {
		final VariableDeclarationCompletionProposal proposal = new VariableDeclarationCompletionProposal(
				new ClassType(null, Object.class));

		assertEquals("myObject | ", proposal.toString());
	}

}
