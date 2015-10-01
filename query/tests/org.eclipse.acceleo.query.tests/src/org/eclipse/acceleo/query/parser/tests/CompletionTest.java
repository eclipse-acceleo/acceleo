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
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.BasicFilter;
import org.eclipse.acceleo.query.runtime.impl.QueryCompletionEngine;
import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EOperationCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.ServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableDeclarationCompletionProposal;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
	public void setup() throws InvalidAcceleoPackageException {
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
	public void nullTest() {
		final ICompletionResult completionResult = engine.getCompletion(null, 0, variableTypes);
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
		assertEquals(null, completionResult.getPrefix());
		assertEquals(null, completionResult.getRemaining());
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeOffsetTest() {
		final ICompletionResult completionResult = engine.getCompletion("aa", -1, variableTypes);
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void outOffsetTest() {
		final ICompletionResult completionResult = engine.getCompletion("aa", 10, variableTypes);
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
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
	}

	@Test
	public void navigationSegmentEmptyTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.", 5, variableTypes);

		assertEquals(65, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
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
	}

	@Test
	public void someIntTest() {
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
	}

	@Test
	public void selfDotTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.", 5, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(65, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void selfArrowTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->", 6, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testCollectionImplicitCollect() {
		final ICompletionResult dotCompletionResult = engine.getCompletion("Sequence{self}.", 15,
				variableTypes);
		final ICompletionResult nothingCompletionResult = engine.getCompletion("Sequence{self}", 14,
				variableTypes);
		final ICompletionResult arrowCompletionResult = engine.getCompletion("Sequence{self}->", 16,
				variableTypes);

		List<ICompletionProposal> dotCompletionProposals = dotCompletionResult.getProposals(new BasicFilter(
				dotCompletionResult));
		assertEquals("", dotCompletionResult.getPrefix());
		assertEquals("", dotCompletionResult.getRemaining());
		assertEquals(52, dotCompletionProposals.size());

		List<String> dotProposals = new ArrayList<String>();
		for (ICompletionProposal dotCompletionProposal : dotCompletionProposals) {
			dotProposals.add(dotCompletionProposal.getProposal());
		}

		assertTrue(dotProposals.contains("name"));
		assertTrue(dotProposals.contains("eContainer()"));
		assertTrue(dotProposals.contains("oclIsKindOf()"));
		assertTrue(!dotProposals.contains("size()"));

		List<ICompletionProposal> nothingCompletionProposals = nothingCompletionResult
				.getProposals(new BasicFilter(nothingCompletionResult));
		assertEquals("", nothingCompletionResult.getPrefix());
		assertEquals("", nothingCompletionResult.getRemaining());
		assertEquals(4, nothingCompletionProposals.size());

		List<String> nothingProposals = new ArrayList<String>();
		for (ICompletionProposal nothingCompletionProposal : nothingCompletionProposals) {
			nothingProposals.add(nothingCompletionProposal.getProposal());
		}

		assertTrue(nothingProposals.contains("+ "));
		assertTrue(nothingProposals.contains("- "));
		assertTrue(nothingProposals.contains("= "));
		assertTrue(nothingProposals.contains("<> "));

		List<ICompletionProposal> arrowCompletionProposals = arrowCompletionResult
				.getProposals(new BasicFilter(arrowCompletionResult));
		assertEquals("", arrowCompletionResult.getPrefix());
		assertEquals("", arrowCompletionResult.getRemaining());
		assertEquals(53, arrowCompletionProposals.size());

		List<String> arrowProposals = new ArrayList<String>();
		for (ICompletionProposal arrowCompletionProposal : arrowCompletionProposals) {
			arrowProposals.add(arrowCompletionProposal.getProposal());
		}

		assertTrue(arrowProposals.contains("size()"));
		assertTrue(arrowProposals.contains("select()"));
		assertTrue(arrowProposals.contains("collect()"));
		assertTrue(!arrowProposals.contains("name"));

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
	}

	@Test
	public void errorTypeLiteralTwoSegmentsTest() {
		final ICompletionResult completionResult = engine.getCompletion("ecore::EClass::", 15, variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableDeclarationCompletionProposal(completionResult);
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
	}

	@Test
	public void selfSelectMissingVariableTypeTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->select( a : ", 17,
				variableTypes);

		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(117, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertNoVariableCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
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
	}

	@Test
	public void testBindingCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("let a=", 6, variableTypes);
		assertEquals(131, completionResult.getProposals(new BasicFilter(completionResult)).size());
	}

	@Test
	public void testLetBodyCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("let a=3 in ", 11, variableTypes);
		assertEquals(131, completionResult.getProposals(new BasicFilter(completionResult)).size());
	}

	public void testIfCompletionFromNothing() {
		final ICompletionResult completionResult = engine.getCompletion("", 0, variableTypes);
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		List<ICompletionProposal> proposals = completionResult
				.getProposals(new BasicFilter(completionResult));
		boolean found = false;
		for (ICompletionProposal proposal : proposals) {
			if ("if".equals(proposal.getProposal())) {
				found = true;
				break;
			}
		}
		assertTrue(found);
	}

	@Test
	public void testIfCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("i", 1, variableTypes);
		assertEquals("i", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		ICompletionProposal proposal = completionResult.getProposals(new BasicFilter(completionResult))
				.get(0);
		assertEquals("if ", proposal.getProposal());
	}

	@Test
	public void testAfterIfCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if ", 3, variableTypes);
		assertEquals("", completionResult.getRemaining());
		assertEquals(TOTAL_NUMBER_OF_PROPOSAL, completionResult.getProposals(
				new BasicFilter(completionResult)).size());
	}

	/**
	 * Tests that after 'if <predicate> then ' the completion proposes the variables and the if.
	 */
	@Test
	public void testAfterThenCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if stuff then ", 13, variableTypes);
		assertEquals(131, completionResult.getProposals(new BasicFilter(completionResult)).size());
	}

	/**
	 * Tests that after 'if <predicate> then <expr> else ' the completion proposes the variables and the if.
	 */
	@Test
	public void testAfterElseCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if stuff then self else ", 23,
				variableTypes);
		assertEquals(131, completionResult.getProposals(new BasicFilter(completionResult)).size());
	}

	/**
	 * Test for <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=470195">Bug 470195</a>.
	 */
	@Test
	public void test470195Completion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self.eClassifiers->select(p | self.)", 35, variableTypes);

		assertEquals(65, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	/**
	 * Test for <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=470195">Bug 470195</a>.
	 */
	@Test
	public void test470195ArrowCompletion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self.eClassifiers->select(p | self->)", 36, variableTypes);

		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	/**
	 * Test for <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=470195">Bug 470195</a>.
	 */
	@Test
	public void test470195DoubleCallCompletion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self.eClassifiers->select(w | self.eClassifiers->select(p | self.))", 65, variableTypes);

		assertEquals(65, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	/**
	 * Test for <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=470195">Bug 470195</a>.
	 */
	@Test
	public void test470195ArrowDoubleCallCompletion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self.eClassifiers->select(w | self.eClassifiers->select(p | self->))", 66, variableTypes);

		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void test472179TypeLiteralComletion() {
		final ICompletionResult completionResult = engine.getCompletion("self.eContainer(ecore::EP)", 25,
				variableTypes);

		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("EP", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void test471583TypeLiteralComletion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self->eClassifiers->filter(ecore::)", 34, variableTypes);

		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
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
	}

	@Test
	public void test477213VariableDeclarationCollectCompletion_Nothing() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new NothingType("whatever"));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->collect()", 14, types);

		// FIXME 477534 : should be the same count as for the SequenceOfNothing
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
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
	}

	@Test
	public void test477213VariableDeclarationSelectCompletion_Nothing() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new NothingType("whatever"));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(", 13, types);

		// FIXME 477534 : should be the same count as for the SequenceOfNothing
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
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
	}

	@Test
	public void test477213VariableDeclarationRejectCompletion_Nothing() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new NothingType("whatever"));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->reject(", 13, types);

		// FIXME 477534 : should be the same count as for the SequenceOfNothing
		assertEquals(0, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
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
	}

	public void test478384NoPipeDeclarationVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(e ", 15, types);

		assertEquals(2, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
	}

	public void test478384NoPipeWithColonDeclarationVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(e :", 16, types);

		assertEquals(117, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoEOperationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
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
	}

	public void testTypeLiteralInTypeSetLiteral() {
		final ICompletionResult completionResult = engine.getCompletion("{ecore::", 8, variableTypes);

		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeSetLiteral() {
		final ICompletionResult completionResult = engine.getCompletion("{", 1, variableTypes);

		assertEquals(117, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
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
	}

	@Test
	public void testTypeSetLiteralInServiceCall() {
		final ICompletionResult completionResult = engine.getCompletion("self->filter({", 14, variableTypes);

		assertEquals(117, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeSetLiteralDot() {
		final ICompletionResult completionResult = engine
				.getCompletion("{ecore::EClass}.", 16, variableTypes);

		assertEquals(36, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeSetLiteralArrow() {
		final ICompletionResult completionResult = engine.getCompletion("{ecore::EClass}->", 17,
				variableTypes);

		assertEquals(53, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
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
			assertEquals(false, prop instanceof EOperationCompletionProposal);
		}
	}

	public static void assertNoVariableDeclarationCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof VariableDeclarationCompletionProposal);
		}
	}

	public static void assertNoServiceCompletionProposal(ICompletionResult completionResult) {
		for (ICompletionProposal prop : completionResult.getProposals(new BasicFilter(completionResult))) {
			assertEquals(false, prop instanceof ServiceCompletionProposal);
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
				} else if (prop instanceof ServiceCompletionProposal) {
					final Class<?> cls;
					if (type instanceof EClass) {
						cls = environment.getEPackageProvider().getClass((EClass)type);
					} else {
						cls = (Class<?>)type;
					}
					assertTrue(((ServiceCompletionProposal)prop).getObject().getServiceMethod()
							.getParameterTypes()[0].isAssignableFrom(cls));
				}
			}
		}
	}

}
