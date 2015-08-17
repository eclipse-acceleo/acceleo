/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.runtime.impl.completion;

import static org.junit.Assert.assertEquals;

import org.eclipse.acceleo.query.runtime.impl.completion.EOperationCompletionProposal;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

/**
 * Tests of the EOperation completion proposal.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">St&eacute;phane B&eacute;gaudeau</a>
 */
public class EOperationCompletionProposalTests {
	@Test
	public void testCreateEClassEOperationCompletionProposal() {
		EOperation createEClassEOperation = EcorePackage.eINSTANCE.getEFactory__Create__EClass();
		EOperationCompletionProposal eOperationCompletionProposal = new EOperationCompletionProposal(
				createEClassEOperation);

		assertEquals("create(eClass: ecore::EClass): ecore::EObject", eOperationCompletionProposal.toString());
	}
}
