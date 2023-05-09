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

import org.eclipse.acceleo.query.runtime.impl.completion.EClassifierCompletionProposal;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link EClassifierCompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EClassifierCompletionProposalTests {

	@Test
	public void getCursorOffset() {
		final EClassifierCompletionProposal proposal = new EClassifierCompletionProposal(
				AnydslPackage.eINSTANCE.getColor());

		assertEquals(13, proposal.getCursorOffset());
	}

	@Test
	public void getProposal() {
		final EClassifierCompletionProposal proposal = new EClassifierCompletionProposal(
				AnydslPackage.eINSTANCE.getColor());

		assertEquals("anydsl::Color", proposal.getProposal());
	}

	@Test
	public void getDescription() {
		final EClassifierCompletionProposal proposal = new EClassifierCompletionProposal(
				AnydslPackage.eINSTANCE.getColor());

		assertEquals("EEnum named Color in anydsl(http://www.eclipse.org/acceleo/anydsl)", proposal
				.getDescription());
	}

	@Test
	public void getObject() {
		final EClassifierCompletionProposal proposal = new EClassifierCompletionProposal(
				AnydslPackage.eINSTANCE.getColor());

		assertEquals(AnydslPackage.eINSTANCE.getColor(), proposal.getObject());
	}

	@Test
	public void testToString() {
		final EClassifierCompletionProposal proposal = new EClassifierCompletionProposal(
				AnydslPackage.eINSTANCE.getColor());

		assertEquals("anydsl::Color", proposal.toString());
	}

}
