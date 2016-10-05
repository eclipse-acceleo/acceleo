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

import java.util.HashSet;
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

		assertCompletion(completionResult, 53, "", "", 0, 6, "ecore::EClass", "ecore::EPackage");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void enumLiteralOneColon() {
		final ICompletionResult completionResult = engine.getCompletion("anydsl::Color:", 14, variableTypes);

		assertCompletion(completionResult, 10, "", "", 0, 14, "anydsl::Color::black", "anydsl::Color::red");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void enumLiteral() {
		final ICompletionResult completionResult = engine.getCompletion("anydsl::Color::", 15, variableTypes);

		assertCompletion(completionResult, 10, "", "", 0, 15, "anydsl::Color::black", "anydsl::Color::yellow");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void enumLiteralWithinCollectionLiteralOneColon() {
		final ICompletionResult completionResult = engine.getCompletion("OrderedSet{anydsl::Color:", 25,
				variableTypes);

		assertCompletion(completionResult, 10, "", "", 11, 14, "anydsl::Color::white",
				"anydsl::Color::palPink");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void enumLiteralWithinCollectionLiteral() {
		final ICompletionResult completionResult = engine.getCompletion("OrderedSet{anydsl::Color::", 26,
				variableTypes);

		assertCompletion(completionResult, 10, "", "", 11, 15, "anydsl::Color::white", "anydsl::Color::red");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void nullTest() {
		final ICompletionResult completionResult = engine.getCompletion(null, 0, variableTypes);

		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, null, null, 0, 0, "self",
				"ecore::EPackage", "not ");
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
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

		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 0, 0, "anydsl::Group::Fruit",
				"true");
		assertNoServiceCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void navigationSegmentEmptyTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.", 5, variableTypes);

		assertCompletion(completionResult, 73, "", "", 5, 0, "eAllContents()", "eClass()");
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void navigationSegmentPrefixTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.na", 7, variableTypes);

		assertCompletion(completionResult, 1, "na", "", 5, 2, "name");
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void navigationSegmentPrefixRemainingTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.nam", 7, variableTypes);

		assertCompletion(completionResult, 1, "na", "m", 5, 2, "name");
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void navigationSegmentPrefixLongExpressionRemainingTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.nam.size()", 7, variableTypes);

		assertCompletion(completionResult, 1, "na", "m", 5, 2, "name");
		assertNoVariableCompletionProposal(completionResult);
		assertApplyOn(completionResult, queryEnvironment, EcorePackage.eINSTANCE.getEClass());
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void selfTest() {
		final ICompletionResult completionResult = engine.getCompletion("self ", 5, variableTypes);

		assertCompletion(completionResult, 3, "", "", 5, 0, "= ", "+ ", "<> ");
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void someIntTest() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		final Set<IType> someIntTypes = new LinkedHashSet<IType>();
		someIntTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEInt()));
		variableTypes.put("someInt", someIntTypes);
		final ICompletionResult completionResult = engine.getCompletion("someInt ", 8, variableTypes);

		assertCompletion(completionResult, 10, "", "", 8, 0, "= ", "+ ", "- ");
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

		// plus "someInt"
		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL + 1, "", "", 10, 0, "someInt",
				"ecore::EChar", "if ");
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void notTest() {
		final ICompletionResult completionResult = engine.getCompletion("not ", 4, variableTypes);

		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 4, 0, "true", "let ", "self",
				"ecore::EPackage");
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void selfDotTest() {
		final ICompletionResult completionResult = engine.getCompletion("self.", 5, variableTypes);

		assertCompletion(completionResult, 73, "", "", 5, 0, "eClass()", "name");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(5, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void selfArrowTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->", 6, variableTypes);

		assertCompletion(completionResult, 44, "", "", 6, 0, "size()", "including()");
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	/**
	 * Checks that the completion includes proposals for the implicit collect (i.e. "aSequence.name" which is
	 * the equivalent of "aSequence->collect(name)").
	 */
	@Test
	public void testImplicitCollectProposalsOnDot() {
		final ICompletionResult dotCompletionResult = engine.getCompletion("Sequence{self}.", 15,
				variableTypes);

		assertCompletion(dotCompletionResult, 73, "", "", 15, 0, "name", "eContainer()", "oclIsKindOf()");
	}

	@Test
	public void testImplicitCollectProposalsOnArrow() {
		final ICompletionResult arrowCompletionResult = engine.getCompletion("Sequence{self}->", 16,
				variableTypes);

		assertCompletion(arrowCompletionResult, 44, "", "", 16, 0, "size()", "select()", "collect()");
	}

	@Test
	public void testImplicitCollectProposalsNoSeparator() {
		final ICompletionResult noSeparatorCompletionResult = engine.getCompletion("Sequence{self}", 14,
				variableTypes);

		assertCompletion(noSeparatorCompletionResult, 4, "", "", 14, 0, "+ ", "- ", "= ", "<> ");
	}

	@Test
	public void errorTypeLiteralOneSegmentTest() {
		final ICompletionResult completionResult = engine.getCompletion("ecore::", 7, variableTypes);

		assertCompletion(completionResult, 53, "", "", 0, 7, "ecore::EObject", "ecore::EInt");
		assertNoVariableCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void errorTypeLiteralTwoSegmentsTest() {
		final ICompletionResult completionResult = engine.getCompletion("ecore::EClass::", 15, variableTypes);

		assertCompletion(completionResult, 0, "", "", 0, 15);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void selfSelectTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->select(", 13, variableTypes);

		assertCompletion(completionResult, 1, "", "", 13, 0, "myEClass | ");
		final ICompletionProposal proposal = completionResult.getProposals(new BasicFilter(completionResult))
				.get(0);
		assertEquals(true, proposal instanceof VariableDeclarationCompletionProposal);
		assertEquals("myEClass | ".length(), proposal.getCursorOffset());
	}

	@Test
	public void selfSelectMissingVariableTypeTest() {
		final ICompletionResult completionResult = engine.getCompletion("self->select( a : ", 17,
				variableTypes);

		assertCompletion(completionResult, 118, "", "", 17, 0, "ecore::EClass", "ecore::EString");
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

		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 20, 0, "self", "ecore::EBoolean");
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testLetCompletionFromNothing() {
		final ICompletionResult completionResult = engine.getCompletion("", 0, variableTypes);

		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 0, 0, "let ");
	}

	@Test
	public void testLetCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("le", 2, variableTypes);

		assertCompletion(completionResult, 1, "le", "", 0, 2, "let ");
	}

	@Test
	public void testLetCompletionNoBinding() {
		final ICompletionResult completionResult = engine.getCompletion("let ", 4, variableTypes);

		assertCompletion(completionResult, 0, "", "", 4, 0);
	}

	@Test
	public void testBindingCompletionNoEquals() {
		final ICompletionResult completionResult = engine.getCompletion("let a", 5, variableTypes);

		assertCompletion(completionResult, 0, "a", "", 4, 1);
	}

	@Test
	public void testBindingCompletionNoEqualsWithSpace() {
		final ICompletionResult completionResult = engine.getCompletion("let a ", 6, variableTypes);

		assertCompletion(completionResult, 2, "", "", 6, 0, ": ", "= ");
	}

	@Test
	public void testBindingCompletionWithErrorType() {
		final ICompletionResult completionResult = engine.getCompletion("let a : ecore::", 15, variableTypes);

		assertCompletion(completionResult, 53, "", "", 8, 7, "ecore::EPackage", "ecore::EString");
		assertNoServiceCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testBindingCompletionWithTypeNoSpace() {
		final ICompletionResult completionResult = engine.getCompletion("let a : ecore::EClass", 21,
				variableTypes);

		assertCompletion(completionResult, 2, "EClass", "", 8, 13, "ecore::EClass", "ecore::EClassifier");
	}

	@Test
	public void testBindingCompletionWithTypeAndSpace() {
		final ICompletionResult completionResult = engine.getCompletion("let a : ecore::EClass ", 22,
				variableTypes);

		assertCompletion(completionResult, 1, "", "", 22, 0, "= ");
	}

	@Test
	public void testBindingCompletionNoType() {
		final ICompletionResult completionResult = engine.getCompletion("let a = ", 8, variableTypes);
		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 8, 0, "self", "true");
	}

	@Test
	public void testLetBodyCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("let a=3 in ", 11, variableTypes);

		// "a" is a new possible variable
		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL + 1, "", "", 11, 0, "a", "self",
				"ecore::EInt", "not ", "if ");
	}

	@Test
	public void testConditionalIfCompletionFromNothing() {
		final ICompletionResult completionResult = engine.getCompletion("", 0, variableTypes);

		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 0, 0, "if ");
	}

	@Test
	public void testConditionalIfCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("i", 1, variableTypes);

		assertCompletion(completionResult, 1, "i", "", 0, 1, "if ");
	}

	@Test
	public void testConditionAfterIfCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if ", 3, variableTypes);

		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 3, 0, "self", "if ", "let ");
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
	}

	@Test
	public void testConditionAfterPredicateCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if true ", 8, variableTypes);

		assertCompletion(completionResult, 12, "", "", 8, 0, "then ");
	}

	@Test
	public void thenVar() {
		final Set<IType> thenVarTypes = new LinkedHashSet<IType>();
		thenVarTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("thenVar", thenVarTypes);
		final ICompletionResult completionResult = engine.getCompletion("then", 4, variableTypes);
		assertEquals("then", completionResult.getPrefix());
		assertEquals("", completionResult.getRemaining());
		assertEquals(1, completionResult.getProposals(new BasicFilter(completionResult)).size());
		assertEquals("thenVar", completionResult.getProposals(new BasicFilter(completionResult)).get(0)
				.getProposal());
		assertEquals(0, completionResult.getReplacementOffset());
		assertEquals(4, completionResult.getReplacementLength());
	}

	/**
	 * Tests that after 'if <predicate> then ' the completion proposes the variables and the if.
	 */
	@Test
	public void testConditionalAfterThenCompletion() {
		final Set<IType> thenVarTypes = new LinkedHashSet<IType>();
		thenVarTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("thenVar", thenVarTypes);
		final ICompletionResult completionResult = engine.getCompletion("if stuff then ", 13, variableTypes);

		assertCompletion(completionResult, 1, "then", "", 9, 4, "then ");
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
	}

	@Test
	public void testConditionAfterTrueBranchCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if true then true ", 18,
				variableTypes);

		assertCompletion(completionResult, 12, "", "", 18, 0, "else ");
	}

	@Test
	public void testConditionWithinTrueBranchCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if true then tr", 15, variableTypes);

		assertCompletion(completionResult, 1, "tr", "", 13, 2, "true");
	}

	/**
	 * Tests that after 'if <predicate> then <expr> else ' the completion proposes the variables and the if.
	 */
	@Test
	public void testConditionalAfterElseCompletion() {
		final Set<IType> elseVarTypes = new LinkedHashSet<IType>();
		elseVarTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("elseVar", elseVarTypes);
		final ICompletionResult completionResult = engine.getCompletion("if stuff then self else ", 23,
				variableTypes);

		assertCompletion(completionResult, 1, "else", "", 19, 4, "else ");
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoServiceCompletionProposal(completionResult);
	}

	@Test
	public void testConditionAfterFalseBranchCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("if true then true else false ", 29,
				variableTypes);

		assertCompletion(completionResult, 12, "", "", 29, 0, "endif ");
	}

	/**
	 * Test for <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=470195">Bug 470195</a>.
	 */
	@Test
	public void test470195Completion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self.eClassifiers->select(p | self.)", 35, variableTypes);

		assertCompletion(completionResult, 73, "", "", 35, 0, "name", "eAllContents()");
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

		assertCompletion(completionResult, 44, "", "", 36, 0, "size()", "first()");
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

		assertCompletion(completionResult, 73, "", "", 65, 0, "name");
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

		assertCompletion(completionResult, 44, "", "", 66, 0, "size()", "first()");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void test472179TypeLiteralCompletion() {
		final ICompletionResult completionResult = engine.getCompletion("self.eContainer(ecore::EP)", 25,
				variableTypes);

		assertCompletion(completionResult, 2, "EP", "", 16, 9, "ecore::EPackage", "ecore::EParameter");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeLiteralCompletionWithPrefix() {
		final ICompletionResult completionResult = engine.getCompletion("self.eContainer(ecore::EPack)", 28,
				variableTypes);

		assertCompletion(completionResult, 1, "EPack", "", 16, 12, "ecore::EPackage");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeLiteralCompletionWithPrefixAndRemaining() {
		final ICompletionResult completionResult = engine.getCompletion("self.eContainer(ecore::EPack)", 26,
				variableTypes);

		// the "remaining" ck are not part of the replacement
		assertCompletion(completionResult, 2, "EPa", "ck", 16, 10, "ecore::EPackage", "ecore::EParameter");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void test471583TypeLiteralCompletion() {
		final ICompletionResult completionResult = engine.getCompletion(
				"self->eClassifiers->filter(ecore::)", 34, variableTypes);

		assertCompletion(completionResult, 53, "", "", 27, 7, "ecore::EPackage", "ecore::EClass");
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

		assertCompletion(completionResult, 1, "", "", 14, 0, "myEObject | ");
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

		assertCompletion(completionResult, 1, "", "", 14, 0, "myEObject | ");
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

		assertCompletion(completionResult, 1, "", "", 14, 0, "myNothing | ");
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

		assertCompletion(completionResult, 1, "", "", 14, 0, "myNothing | ");
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

		assertCompletion(completionResult, 1, "", "", 13, 0, "myEObject | ");
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

		assertCompletion(completionResult, 1, "", "", 13, 0, "myEObject | ");
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

		assertCompletion(completionResult, 1, "", "", 13, 0, "myNothing | ");
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

		assertCompletion(completionResult, 1, "", "", 13, 0, "myNothing | ");
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

		assertCompletion(completionResult, 1, "", "", 13, 0, "myEObject | ");
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

		assertCompletion(completionResult, 1, "", "", 13, 0, "myEObject | ");
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

		assertCompletion(completionResult, 1, "", "", 13, 0, "myNothing | ");
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

		assertCompletion(completionResult, 1, "", "", 13, 0, "myNothing | ");
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

		assertCompletion(completionResult, 0, "", "", 8, 0);
	}

	@Test
	public void test478176MiddleOfStringLiteralCompletion() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SequenceType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self = 'test'", 10, types);

		// This would replace part of the string...
		// but makes no sense as there is no completion within string literals
		assertCompletion(completionResult, 0, "te", "st", 8, 2);
	}

	@Test
	public void test478384NoPipeDeclarationVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(e ", 15, types);

		assertCompletion(completionResult, 2, "", "", 15, 0, "| ", ": ");
	}

	@Test
	public void test478384NoPipeWithColonDeclarationVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(e :", 16, types);

		assertCompletion(completionResult, 118, "", "", 16, 0, "ecore::EPackage", "ecore::EClass");
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

		assertCompletion(completionResult, 1, "", "", 31, 0, "| ");
	}

	@Test
	public void testOrderedSetMinus() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();

		final ICompletionResult completionResult = engine.getCompletion("OrderedSet{}-", 13, types);

		assertCompletion(completionResult, 129, "", "", 13, 0, "OrderedSet{}", "ecore::EPackage");
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void test480853EmptyOrderedSetLiteralInExtension() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();

		final ICompletionResult completionResult = engine.getCompletion("OrderedSet{}->", 14, types);

		assertCompletion(completionResult, 44, "", "", 14, 0, "size()", "first()");
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void test480853EmptySequenceLiteralInExtension() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();

		final ICompletionResult completionResult = engine.getCompletion("Sequence{}->", 12, types);

		assertCompletion(completionResult, 44, "", "", 12, 0, "size()", "first()");
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeLiteralInTypeSetLiteral() {
		final ICompletionResult completionResult = engine.getCompletion("{ecore::", 8, variableTypes);

		assertCompletion(completionResult, 53, "", "", 1, 7, "ecore::EClass", "ecore::EInt");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeSetLiteral() {
		final ICompletionResult completionResult = engine.getCompletion("{", 1, variableTypes);

		assertCompletion(completionResult, 118, "", "", 1, 0, "ecore::EPackage");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeLiteralInTypeSetLiteralInServiceCall() {
		final ICompletionResult completionResult = engine.getCompletion("self->filter({ecore::", 21,
				variableTypes);

		assertCompletion(completionResult, 53, "", "", 14, 7, "ecore::EClass", "ecore::EInt");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeSetLiteralInServiceCall() {
		final ICompletionResult completionResult = engine.getCompletion("self->filter({", 14, variableTypes);

		assertCompletion(completionResult, 118, "", "", 14, 0, "ecore::EPackage");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertEquals(14, completionResult.getReplacementOffset());
		assertEquals(0, completionResult.getReplacementLength());
	}

	@Test
	public void testTypeSetLiteralDot() {
		final ICompletionResult completionResult = engine
				.getCompletion("{ecore::EClass}.", 16, variableTypes);

		assertCompletion(completionResult, 46, "", "", 16, 0, "name", "eClass()");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void testTypeSetLiteralArrow() {
		final ICompletionResult completionResult = engine.getCompletion("{ecore::EClass}->", 17,
				variableTypes);

		assertCompletion(completionResult, 44, "", "", 17, 0, "size()", "first()");
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

		assertCompletion(completionResult, 0, "n", "", 2, 1);
	}

	@Test
	public void errorStringLiteralInSelectInCall() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(a | a.startsWith('",
				31, types);

		assertCompletion(completionResult, 0, "", "", 31, 0);
	}

	@Test
	public void missingClosingParenthesisCallNoArguments_465037() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self.eAllContents(", 18, types);

		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 18, 0, "self", "ecore::EClass");
	}

	@Test
	public void missingClosingParenthesisIterationCallNoArguments_465037() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self->select(a | true ", 22, types);

		assertCompletion(completionResult, 12, "", "", 22, 0, "and ", "or ", "implies ");
		assertNoVariableCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
	}

	@Test
	public void missingClosingParenthesisCall_465037() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self.eAllContents(self ", 23, types);

		assertCompletion(completionResult, 2, "", "", 23, 0, ", ", ")");
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
		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 17, 0, "self", "ecore::EClass");
	}

	@Test
	public void inLetVariable() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject()));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("let a = self in ", 16, types);

		assertCompletion(completionResult, TOTAL_NUMBER_OF_PROPOSAL, "", "", 16, 0, "a", "self");
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
		assertCompletion(completionResult, 130, "", "", 18, 0, "self");
	}

	@Test
	public void testCompletionOnNothingDot() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new NothingType("whatever"));
		types.put("self", selfType);

		final ICompletionResult completionResult = engine.getCompletion("self.", 5, types);

		assertCompletion(completionResult, 5, "", "", 5, 0, "toString()", "oclIsKindOf()");
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

		assertCompletion(completionResult, 44, "", "", 6, 0, "size()", "first()");
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

		assertCompletion(completionResult, 44, "", "", 6, 0, "size()", "first()");
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

		assertCompletion(completionResult, 44, "", "", 6, 0, "size()", "first()");
		assertNoVariableCompletionProposal(completionResult);
		assertNoFeatureCompletionProposal(completionResult);
		assertNoEOperationCompletionProposal(completionResult);
		assertNoVariableDeclarationCompletionProposal(completionResult);
	}

	@Test
	public void negativeIndex() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SequenceType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		boolean exception = false;
		try {
			engine.getCompletion("self", -1, types);
		} catch (IllegalArgumentException e) {
			assertEquals("offset (-1) must be in the range of the given expression: \"self\"", e.getMessage());
			exception = true;
		}
		assertTrue(exception);
	}

	@Test
	public void tooHighIndex() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();
		selfType.add(new SequenceType(queryEnvironment, new NothingType("whatever")));
		types.put("self", selfType);

		boolean exception = false;
		try {
			engine.getCompletion("self", 5, types);
		} catch (IllegalArgumentException e) {
			assertEquals("offset (5) must be in the range of the given expression: \"self\"", e.getMessage());
			exception = true;
		}
		assertTrue(exception);
	}

	public void suffixFilter_479632() {
		final ICompletionResult completionResult = engine.getCompletion("self.eAllContents()", 5,
				variableTypes);

		assertCompletion(completionResult, 73, "", "eAllContents", 5, 0, "eContainer()", "eAttributes");
	}

	public static void assertCompletion(ICompletionResult completionResult, int size, String prefix,
			String suffix, int replacementOffset, int replacementLength, String... proposalStrings) {
		final List<ICompletionProposal> proposals = completionResult.getProposals(new BasicFilter(
				completionResult));
		if (proposalStrings.length != 0) {
			final Set<String> actualProposalStrings = new HashSet<String>(proposals.size());
			for (ICompletionProposal proposal : proposals) {
				actualProposalStrings.add(proposal.getProposal());
			}
			for (String proposalString : proposalStrings) {
				assertTrue(proposalString + " is missing", actualProposalStrings.contains(proposalString));
			}
		}

		assertEquals(size, proposals.size());
		assertEquals(prefix, completionResult.getPrefix());
		assertEquals(suffix, completionResult.getRemaining());
		assertEquals(replacementOffset, completionResult.getReplacementOffset());
		assertEquals(replacementLength, completionResult.getReplacementLength());
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
