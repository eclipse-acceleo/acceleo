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

import org.eclipse.acceleo.query.runtime.impl.completion.EEnumLiteralCompletionProposal;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link EEnumLiteralCompletionProposal}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EEnumLiteralCompletionProposalTests {

	@Test
	public void getCursorOffset() {
		final EEnumLiteralCompletionProposal proposal = new EEnumLiteralCompletionProposal(
				AnydslPackage.eINSTANCE.getColor().getEEnumLiteral("black"));

		assertEquals(20, proposal.getCursorOffset());
	}

	@Test
	public void getProposal() {
		final EEnumLiteralCompletionProposal proposal = new EEnumLiteralCompletionProposal(
				AnydslPackage.eINSTANCE.getColor().getEEnumLiteral("black"));

		assertEquals("anydsl::Color::black", proposal.getProposal());
	}

	@Test
	public void getDescription() {
		final EEnumLiteralCompletionProposal proposal = new EEnumLiteralCompletionProposal(
				AnydslPackage.eINSTANCE.getColor().getEEnumLiteral("black"));

		assertEquals("EEnumLiteral named black in Color(http://www.eclipse.org/acceleo/anydsl)", proposal
				.getDescription());
	}

	@Test
	public void getObject() {
		final EEnumLiteralCompletionProposal proposal = new EEnumLiteralCompletionProposal(
				AnydslPackage.eINSTANCE.getColor().getEEnumLiteral("black"));

		assertEquals(AnydslPackage.eINSTANCE.getColor().getEEnumLiteral("black"), proposal.getObject());
	}

	@Test
	public void testToString() {
		final EEnumLiteralCompletionProposal proposal = new EEnumLiteralCompletionProposal(
				AnydslPackage.eINSTANCE.getColor().getEEnumLiteral("black"));

		assertEquals("anydsl::Color::black", proposal.toString());
	}

}
