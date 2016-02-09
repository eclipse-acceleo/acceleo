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
package org.eclipse.acceleo.query.parser.tests;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.BasicFilter;
import org.eclipse.acceleo.query.runtime.impl.QueryCompletionEngine;
import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EOperationServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.JavaMethodServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableDeclarationCompletionProposal;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CompletionTest {

	private static final int TOTAL_NUMBER_OF_PROPOSAL = 131;

	QueryCompletionEngine engine;

	IQueryEnvironment queryEnvironment;

	/**
	 * Variable types.
	 */
	Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();

	@Before
	public void setup() {
		queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		engine = new QueryCompletionEngine(queryEnvironment);

		variableTypes.clear();
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("self", selfTypes);
		final Set<IType> stuffTypes = new LinkedHashSet<IType>();
		stuffTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("stuff", stuffTypes);
	}

	@Test
	public void typeLiteralOneColon() {
		final ICompletionResult completionResult = engine.getCompletion("ecore:", 6, variableTypes);

		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(6, completionResult.getReplacementLength());
	}

	@Test
	public void enumLiteralOneColon() {
		final ICompletionResult completionResult = engine.getCompletion("anydsl::Color:", 14, variableTypes);

		assertEquals(10, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(14, completionResult.getReplacementLength());
	}

	@Test
	public void nullTest() {
		final ICompletionResult completionResult = engine.getCompletion(null, 0, variableTypes);
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertEquals(null, completionResult.getPrefix());
		assertEquals(null, completionResult.getRemaining());
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeOffsetTest() {
		engine.getCompletion("aa", -1, variableTypes);
	}

	@Test(expected = IllegalArgumentException.class)
	public void outOffsetTest() {
		engine.getCompletion("aa", 10, variableTypes);
	}

	@Test
	public void emptyTest() {
		final ICompletionResult completionResult = engine.getCompletion("", 0, variableTypes);
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void navigationSegmentEmptyTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.", 5, variableTypes);

		assertEquals(73, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(5, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void navigationSegmentPrefixTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.na", 7, variableTypes);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("na", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(5, completionResult.getReplacementOffset());
		assertEquals(2, completionResult.getReplacementLength());
	}

	@Test
	public void navigationSegmentPrefixRemainingTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.nam", 7, variableTypes);

		assertEquals("na", completionResult.getPrefix());
		assertEquals("m", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(5, completionResult.getReplacementOffset());
		assertEquals(2, completionResult.getReplacementLength());
	}

	@Test
	public void navigationSegmentPrefixLongExpressionRemainingTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.nam.size()", 7, variableTypes);

		assertEquals("na", completionResult.getPrefix());
		assertEquals("m", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(5, completionResult.getReplacementOffset());
		assertEquals(2, completionResult.getReplacementLength());
	}

	@Test
	public void selfTest() {
		final ICompletionResult completionResult = engine.getCompletion("self ", 5, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(3, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(5, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void someIntTest() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		final Set<IType> someIntTypes = new LinkedHashSet<IType>();
		someIntTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEInt()));
		variableTypes.put("someInt", someIntTypes);
		final ICompletionResult completionResult = engine.getCompletion("someInt ", 8, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(10, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(8, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void alfMultTest() {
		final Set<IType> someIntTypes = new LinkedHashSet<IType>();
		someIntTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEInt()));
		variableTypes.put("someInt", someIntTypes);
		final ICompletionResult completionResult = engine.getCompletion("someInt * ", 10, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(132, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(10, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void notTest() {
		final ICompletionResult completionResult = engine.getCompletion("not ", 4, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(4, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void selfDotTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.", 5, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(73, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(5, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void selfArrowTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->", 6, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(6, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	/**
	 * Checks that the completion includes proposals for the implicit collect (i.e. "aSequence.name" which is
	 * the equivalent of "aSequence->collect(name)").
	 */
	@Test
	public void testImplicitCollectProposalsOnDot() {
		final ICompletionResult dotCompletionResult = engine.getCompletion("Sequence{self}.", 15,
				variableTypes);

		List<ICompletionProposal> dotCompletionProposals = dotCompletionResult.getProposals(new BasicFilter(
				dotCompletionResult));
		assertEquals("", dotCompletionResult.getPrefix());
		assertEquals("", dotCompletionResult.getRemaining());
		assertEquals(73, dotCompletionProposals.size());

		List<String> dotProposals = new ArrayList<String>();
		for (ICompletionProposal dotCompletionProposal : dotCompletionProposals) {
			dotProposals.add(dotCompletionProposal.getProposal());
		}

		assertTrue(dotProposals.contains("name"));
		assertTrue(dotProposals.contains("eContainer()"));
		assertTrue(dotProposals.contains("oclIsKindOf()"));
		assertTrue(!dotProposals.contains("size()"));

		assertEquals(15, dotCompletionResult.getReplacementOffset());
		assertEquals(0, dotCompletionResult.getReplacementLength());
	}

	@Test
	public void testImplicitCollectProposalsOnArrow() {
		final ICompletionResult arrowCompletionResult = engine.getCompletion("Sequence{self}->", 16,
				variableTypes);

		List<ICompletionProposal> arrowCompletionProposals = arrowCompletionResult
				.getProposals(new BasicFilter(arrowCompletionResult));
		assertEquals("", arrowCompletionResult.getPrefix());
		assertEquals("", arrowCompletionResult.getRemaining());
		assertEquals(44, arrowCompletionProposals.size());

		List<String> arrowProposals = new ArrayList<String>();
		for (ICompletionProposal arrowCompletionProposal : arrowCompletionProposals) {
			arrowProposals.add(arrowCompletionProposal.getProposal());
		}

		assertTrue(arrowProposals.contains("size()"));
		assertTrue(arrowProposals.contains("select()"));
		assertTrue(arrowProposals.contains("collect()"));
		assertTrue(!arrowProposals.contains("name"));

		assertEquals(16, arrowCompletionResult.getReplacementOffset());
		assertEquals(0, arrowCompletionResult.getReplacementLength());
	}

	@Test
	public void testImplicitCollectProposalsNoSeparator() {
		final ICompletionResult noSeparatorCompletionResult = engine.getCompletion("Sequence{self}", 14,
				variableTypes);

		List<ICompletionProposal> nothingCompletionProposals = noSeparatorCompletionResult
				.getProposals(new BasicFilter(noSeparatorCompletionResult));
		assertEquals("", noSeparatorCompletionResult.getPrefix());
		assertEquals("", noSeparatorCompletionResult.getRemaining());
		assertEquals(4, nothingCompletionProposals.size());

		List<String> nothingProposals = new ArrayList<String>();
		for (ICompletionProposal nothingCompletionProposal : nothingCompletionProposals) {
			nothingProposals.add(nothingCompletionProposal.getProposal());
		}

		assertTrue(nothingProposals.contains("+ "));
		assertTrue(nothingProposals.contains("- "));
		assertTrue(nothingProposals.contains("= "));
		assertTrue(nothingProposals.contains("<> "));

		assertEquals(14, noSeparatorCompletionResult.getReplacementOffset());
		assertEquals(0, noSeparatorCompletionResult.getReplacementLength());
	}

	@Test
	public void errorTypeLiteralOneSegmentTest() {
		final ICompletionResult completionResult = engine.getCompletion("ecore::", 7, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(7, completionResult.getReplacementLength());
	}

	@Test
	public void errorTypeLiteralTwoSegmentsTest() {
		final ICompletionResult completionResult = engine.getCompletion("ecore::EClass::", 15, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(15, completionResult.getReplacementLength());
	}

	@Test
	public void selfSelectTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->select(", 13, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		final ICompletionProposal proposal = completionResult.getProposals(new BasicFilter(completionResult))
				.get(0);
		assertEquals(true, proposal instanceof VariableDeclarationCompletionProposal);
		assertEquals("myEClass | ", proposal.getProposal());
		assertEquals("myEClass | ".length(), proposal.getCursorOffset());
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void selfSelectMissingVariableTypeTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->select( a : ", 17,
				variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(118, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(17, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void selfServiceManyArgumentsTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->service(self, ", 20,
				variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(20, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testLetCompletionFromNothing() {
		final ICompletionResult completionResult = engine.getCompletion("", 0, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		List<ICompletionProposal> proposals = completionResult
				.getProposals(new BasicFilter(completionResult));
		boolean found = false;
		for (ICompletionProposal proposal : proposals) {
			if ("let ".equals(proposal.getProposal())) {
				found = true;
				break;
			}
		}
		assertTrue(found);
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testLetCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("le", 2, variableTypes);

		assertEquals("le", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		ICompletionProposal proposal = completionResult.getProposals(new BasicFilter(completionResult))
				.get(0);
		assertEquals("let ", proposal.getProposal());
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(2, completionResult.getReplacementLength());
	}

	@Test
	public void testLetCompletionNoBinding() {
		final ICompletionResult completionResult = engine.getCompletion("let ", 4, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals(4, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testBindingCompletionNoEquals() {
		final ICompletionResult completionResult = engine.getCompletion("let a ", 6, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(2, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals(6, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testBindingCompletionWithErrorType() {
		final ICompletionResult completionResult = engine.getCompletion("let a : ecore::", 15, variableTypes);

		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(8, completionResult.getReplacementOffset());
		assertEquals(7, completionResult.getReplacementLength());
	}

	@Test
	public void testBindingCompletionWithType() {
		final ICompletionResult completionResult = engine.getCompletion("let a : ecore::EClass ", 22,
				variableTypes);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		ICompletionProposal proposal = completionResult.getProposals(new BasicFilter(completionResult))
				.get(0);
		assertEquals(" = ", proposal.getProposal());
		assertEquals(22, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testBindingCompletionNoType() {
		final ICompletionResult completionResult = engine.getCompletion("let a = ", 8, variableTypes);
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertEquals(8, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testLetBodyCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("let a=3 in ", 11, variableTypes);

		assertEquals(TOTAL_NUMBER_OF_PROPOSAL + 1, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertEquals(11, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testConditionalIfCompletionFromNothing() {
		final ICompletionResult completionResult = engine.getCompletion("", 0, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		List<ICompletionProposal> proposals = completionResult
				.getProposals(new BasicFilter(completionResult));
		boolean found = false;
		for (ICompletionProposal proposal : proposals) {
			if ("if ".equals(proposal.getProposal())) {
				found = true;
				break;
			}
		}
		assertTrue(found);
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testConditionalIfCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("i", 1, variableTypes);

		assertEquals("i", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		ICompletionProposal proposal = completionResult.getProposals(new BasicFilter(completionResult))
				.get(0);
		assertEquals("if ", proposal.getProposal());
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(1, completionResult.getReplacementLength());
	}

	@Test
	public void testConditionAfterIfCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if ", 3, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertEquals(3, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testConditionAfterPredicateCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if true ", 8, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("then ", completionResult.getProposals(new BasicFilter(completionResult)).get(0)
				.getProposal());
		assertEquals(8, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	/**
	 * Tests that after 'if <predicate> then ' the completion proposes the variables and the if.
	 */
	@Test
	public void testConditionalAfterThenCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if stuff then ", 13, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testConditionAfterTrueBranchCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if true then true ", 18,
				variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("else ", completionResult.getProposals(new BasicFilter(completionResult)).get(0)
				.getProposal());
		assertEquals(18, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testConditionWithinTrueBranchCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if true then tr", 15, variableTypes);

		assertEquals("tr", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("true", completionResult.getProposals(new BasicFilter(completionResult)).get(0)
				.getProposal());
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(2, completionResult.getReplacementLength());
	}

	/**
	 * Tests that after 'if <predicate> then <expr> else ' the completion proposes the variables and the if.
	 */
	@Test
	public void testConditionalAfterElseCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if stuff then self else ", 23,
				variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertEquals(23, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testConditionAfterFalseBranchCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if true then true else false ", 29,
				variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("endif ", completionResult.getProposals(new BasicFilter(completionResult)).get(0)
				.getProposal());
		assertEquals(29, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	/**
	 * Test for <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=470195">Bug 470195</a>.
	 */
	@Test
	public void test470195Completion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self.eClassifiers->select(p | self.)", 35, variableTypes);

		assertEquals(73, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(35, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	/**
	 * Test for <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=470195">Bug 470195</a>.
	 */
	@Test
	public void test470195ArrowCompletion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self.eClassifiers->select(p | self->)", 36, variableTypes);

		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(36, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	/**
	 * Test for <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=470195">Bug 470195</a>.
	 */
	@Test
	public void test470195DoubleCallCompletion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self.eClassifiers->select(w | self.eClassifiers->select(p | self.))", 65, variableTypes);

		assertEquals(73, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(65, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	/**
	 * Test for <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=470195">Bug 470195</a>.
	 */
	@Test
	public void test470195ArrowDoubleCallCompletion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self.eClassifiers->select(w | self.eClassifiers->select(p | self->))", 66, variableTypes);

		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(66, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test472179TypeLiteralCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("self.eContainer(ecore::EP)", 25,
				variableTypes);

		assertEquals(2, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("EP", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(16, completionResult.getReplacementOffset());
		assertEquals(9, completionResult.getReplacementLength());
	}

	@Test
	public void testTypeLiteralCompletionWithPrefix() {
		final ICompletionResult completionResult = engine.getCompletion("self.eContainer(ecore::EPack)", 28,
				variableTypes);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("EPack", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(16, completionResult.getReplacementOffset());
		assertEquals(12, completionResult.getReplacementLength());
	}

	@Test
	public void testTypeLiteralCompletionWithPrefixAndRemaining() {
		final ICompletionResult completionResult = engine.getCompletion("self.eContainer(ecore::EPack)", 26,
				variableTypes);

		assertEquals(2, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("EPa", completionResult.getPrefix());
		assertEquals("ck", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(16, completionResult.getReplacementOffset());
		// the "remaining" ck are not part of the replacement
		assertEquals(10, completionResult.getReplacementLength());
	}

	@Test
	public void test471583TypeLiteralCompletion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self->eClassifiers->filter(ecore::)", 34, variableTypes);

		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(27, completionResult.getReplacementOffset());
		assertEquals(7, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationCollectCompletion_Classifier() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->collect()", 14, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(14, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationCollectCompletion_Class() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new ClassType(queryEnvironment, EObject.class));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->collect()", 14, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(14, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationCollectCompletion_Nothing() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new NothingType("whatever"));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->collect()", 14, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(14, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationCollectCompletion_SequenceOfNothing() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SequenceType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->collect()", 14, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(14, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationSelectCompletion_Classifier() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(", 13, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationSelectCompletion_Class() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new ClassType(queryEnvironment, EObject.class));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(", 13, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationSelectCompletion_Nothing() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new NothingType("whatever"));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(", 13, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationSelectCompletion_SequenceOfNothing() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SequenceType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(", 13, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationRejectCompletion_Classifier() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->reject(", 13, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationRejectCompletion_Class() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new ClassType(queryEnvironment, EObject.class));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->reject(", 13, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationRejectCompletion_Nothing() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new NothingType("whatever"));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->reject(", 13, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test477213VariableDeclarationRejectCompletion_SequenceOfNothing() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SequenceType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->reject(", 13, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test478176MiddleOfEmptyStringLiteralCompletion() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SequenceType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self = ''", 8, types);

		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(8, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test478176MiddleOfStringLiteralCompletion() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SequenceType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self = 'test'", 10, types);

		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("te", completionResult.getPrefix());
		assertEquals("st", completionResult.getRemaining());

		// This would replace part of the string...
		// but makes no sense as there is no completion within string literals
		assertEquals(8, completionResult.getReplacementOffset());
		assertEquals(2, completionResult.getReplacementLength());
	}

	@Test
	public void test478384NoPipeDeclarationVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(e ", 15, types);

		assertEquals(2, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(15, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test478384NoPipeWithColonDeclarationVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(e :", 16, types);

		assertEquals(118, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertEquals(16, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test478384NoPipeWithTypeDeclarationVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(e : ecore::EClass ",
				31, types);

		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(31, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testOrderedSetMinus() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();

		final ICompletionResult completionResult = engine.getCompletion("OrderedSet{}-", 13, types);

		assertEquals(129, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(13, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test480853EmptyOrderedSetLiteralInExtension() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();

		final ICompletionResult completionResult = engine.getCompletion("OrderedSet{}->", 14, types);

		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(14, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void test480853EmptySequenceLiteralInExtension() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();

		final ICompletionResult completionResult = engine.getCompletion("Sequence{}->", 12, types);

		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(12, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testTypeLiteralInTypeSetLiteral() {
		final ICompletionResult completionResult = engine.getCompletion("{ecore::", 8, variableTypes);

		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(1, completionResult.getReplacementOffset());
		assertEquals(7, completionResult.getReplacementLength());
	}

	@Test
	public void testTypeSetLiteral() {
		final ICompletionResult completionResult = engine.getCompletion("{", 1, variableTypes);

		assertEquals(118, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(1, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testTypeLiteralInTypeSetLiteralInServiceCall() {
		final ICompletionResult completionResult = engine.getCompletion("self->filter({ecore::", 21,
				variableTypes);

		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(14, completionResult.getReplacementOffset());
		assertEquals(7, completionResult.getReplacementLength());
	}

	@Test
	public void testTypeSetLiteralInServiceCall() {
		final ICompletionResult completionResult = engine.getCompletion("self->filter({", 14, variableTypes);

		assertEquals(118, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(14, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testTypeSetLiteralDot() {
		final ICompletionResult completionResult = engine
				.getCompletion("{ecore::EClass}.", 16, variableTypes);

		assertEquals(46, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(16, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testTypeSetLiteralArrow() {
		final ICompletionResult completionResult = engine.getCompletion("{ecore::EClass}->", 17,
				variableTypes);

		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertEquals(17, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void errorStringLiteralWithEscapeSequence() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("'\\n", 3, types);

		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("n", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(2, completionResult.getReplacementOffset());
		assertEquals(1, completionResult.getReplacementLength());
	}

	@Test
	public void errorStringLiteralInSelectInCall() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(a | a.startsWith('",
				31, types);

		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(31, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void missingClosingParenthesisCallNoArguments_465037() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self.eAllContents(", 18, types);

		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
	}

	@Test
	public void missingClosingParenthesisIterationCallNoArguments_465037() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(a | true ", 22, types);

		assertEquals(12, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
	}

	@Test
	public void missingClosingParenthesisCall_465037() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self.eAllContents(self ", 23, types);

		assertEquals(2, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
	}

	@Test
	public void inSelectVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(a | ", 17, types);

		final List<ICompletionProposal> proposals = completionResult.getProposals(new BasicFilter(
				completionResult));
		boolean variableAFound = false;
		for (ICompletionProposal proposal : proposals) {
			if ("a".equals(proposal.getProposal())) {
				variableAFound = true;
				break;
			}
		}
		assertTrue(variableAFound);
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, proposals.size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
	}

	@Test
	public void inLetVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("let a = self in ", 16, types);

		final List<ICompletionProposal> proposals = completionResult.getProposals(new BasicFilter(
				completionResult));
		boolean variableAFound = false;
		for (ICompletionProposal proposal : proposals) {
			if ("a".equals(proposal.getProposal())) {
				variableAFound = true;
				break;
			}
		}
		assertTrue(variableAFound);
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, proposals.size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
	}

	@Test
	public void inLetVariableInSecondBinding() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("let a = self, b = ", 18, types);

		final List<ICompletionProposal> proposals = completionResult.getProposals(new BasicFilter(
				completionResult));
		boolean variableAFound = false;
		for (ICompletionProposal proposal : proposals) {
			if ("a".equals(proposal.getProposal())) {
				variableAFound = true;
				break;
			}
		}
		assertFalse(variableAFound);
		assertEquals(130, proposals.size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
	}

	@Test
	public void testCompletionOnNothingDot() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new NothingType("whatever"));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self.", 5, types);

		assertEquals(5, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testCompletionOnNothingArrow() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new NothingType("whatever"));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->", 6, types);

		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testCompletionOnSetOfNothingArrow() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SetType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->", 6, types);

		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testCompletionOnSequenceOfNothingArrow() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SequenceType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->", 6, types);

		assertEquals(44, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	public void assertNoVariableCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof VariableCompletionProposal);
		}
	}

	public static void assertNoFeatureCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof EFeatureCompletionProposal);
		}
	}

	public static void assertNoEOperationCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof EOperationServiceCompletionProposal);
		}
	}

	public static void assertNoVariableDeclarationCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof VariableDeclarationCompletionProposal);
		}
	}

	public static void assertNoServiceCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof JavaMethodServiceCompletionProposal);
		}
	}

	public static void assertOnlyVariableCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(true, prop instanceof VariableCompletionProposal);
		}
	}

	public static void assertApplyOn(ICompletionResult completionResult,
			IReadOnlyQueryEnvironment environment, Object... types) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			for (Object type : types) {
				if (prop instanceof EFeatureCompletionProposal) {
					if (type instanceof EClass) {
						assertEquals(type + " is not compatible with "
								+ ((EFeatureCompletionProposal)prop).getObject().getEContainingClass(), true,
								((EFeatureCompletionProposal)prop).getObject().getEContainingClass() == type
										|| ((EClass)type).getEAllSuperTypes().contains(
												((EFeatureCompletionProposal)prop).getObject()
														.getEContainingClass()));
					} else {
						fail("the receiver type must be an EClass for FeatureCompletionProposal");
					}
				} else if (prop instanceof JavaMethodServiceCompletionProposal) {
					final IType iType;
					if (type instanceof EClassifier) {
						iType = new EClassifierType(environment, (EClassifier)type);
					} else {
						iType = new ClassType(environment, (Class<?>)type);
					}
					assertTrue(((JavaMethodServiceCompletionProposal)prop).getObject().getParameterTypes(
							environment).get(0).isAssignableFrom(iType));
				}
			}
		}
	}

}
