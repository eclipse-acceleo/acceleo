/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.runtime.impl.completion;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;
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
		Set<IType> types = new LinkedHashSet<>();
		types.add(new ClassType(null, String.class));
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar", types);

		assertEquals(5, proposal.getCursorOffset());
	}

	@Test
	public void getProposal() {
		Set<IType> types = new LinkedHashSet<>();
		types.add(new ClassType(null, String.class));
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar", types);

		assertEquals("myVar", proposal.getProposal());
	}

	@Test
	public void getDescription() {
		Set<IType> types = new LinkedHashSet<>();
		types.add(new ClassType(null, String.class));
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar", types);

		assertEquals("Variable myVar = String", proposal.getDescription());
	}

	@Test
	public void getObject() {
		Set<IType> types = new LinkedHashSet<>();
		types.add(new ClassType(null, String.class));
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar", types);

		assertEquals("myVar", proposal.getObject());
	}

	@Test
	public void testToString() {
		Set<IType> types = new LinkedHashSet<>();
		types.add(new ClassType(null, String.class));
		final VariableCompletionProposal proposal = new VariableCompletionProposal("myVar", types);

		assertEquals("myVar", proposal.toString());
	}

}
