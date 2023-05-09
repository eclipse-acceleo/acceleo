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

import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests of the EFeature completion proposal.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">St&eacute;phane B&eacute;gaudeau</a>
 */
public class EFeatureCompletionProposalTests {
	@Test
	public void testNameCompletionProposal() {
		EAttribute nameEAttribute = EcorePackage.eINSTANCE.getENamedElement_Name();
		EFeatureCompletionProposal eFeatureCompletionProposal = new EFeatureCompletionProposal(nameEAttribute);

		assertEquals("name: ecore::EString [0..1]", eFeatureCompletionProposal.toString());
	}

	@Test
	public void testEClassifierCompletionProposal() {
		EReference eClassifiersEReference = EcorePackage.eINSTANCE.getEPackage_EClassifiers();
		EFeatureCompletionProposal eFeatureCompletionProposal = new EFeatureCompletionProposal(
				eClassifiersEReference);

		assertEquals("eClassifiers: ecore::EClassifier [0..*]", eFeatureCompletionProposal.toString());
	}

	@Test
	public void getCursorOffset() {
		EReference eClassifiersEReference = EcorePackage.eINSTANCE.getEPackage_EClassifiers();
		EFeatureCompletionProposal eFeatureCompletionProposal = new EFeatureCompletionProposal(
				eClassifiersEReference);

		assertEquals(12, eFeatureCompletionProposal.getCursorOffset());
	}

	@Test
	public void getDescription() {
		EReference eClassifiersEReference = EcorePackage.eINSTANCE.getEPackage_EClassifiers();
		EFeatureCompletionProposal eFeatureCompletionProposal = new EFeatureCompletionProposal(
				eClassifiersEReference);

		assertEquals("EReference named eClassifiers in EPackage(http://www.eclipse.org/emf/2002/Ecore)",
				eFeatureCompletionProposal.getDescription());
	}

}
