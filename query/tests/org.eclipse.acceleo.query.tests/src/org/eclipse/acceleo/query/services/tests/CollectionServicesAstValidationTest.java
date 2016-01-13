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
package org.eclipse.acceleo.query.services.tests;

import com.google.common.collect.ImmutableSet;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryValidationEngine;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.QueryValidationEngine;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollectionServicesAstValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		getQueryEnvironment().registerServicePackage(CollectionServices.class);
	}

	private IValidationResult validate(String expression, Map<String, Set<IType>> vars) {
		final Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();
		if (vars != null) {
			variableTypes.putAll(vars);
		}

		final IQueryValidationEngine builder = new QueryValidationEngine(getQueryEnvironment());
		return builder.validate(expression, variableTypes);
	}

	private IValidationResult validate(String expression) {
		return validate(expression, null);
	}

	@Test
	public void testAddImplicitSetNothingString() {
		VariableBuilder variables = new VariableBuilder().addVar("self", nothingType("Empty"));
		final IValidationResult validationResult = validate("self->add('hello')", variables.build());

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Empty", validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddImplicitSetStringNothing() {
		VariableBuilder variables = new VariableBuilder().addVar("self", nothingType("Empty"));
		final IValidationResult validationResult = validate("'hello'->add(self)", variables.build());

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Empty", validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddImplicitSet() {
		final IValidationResult validationResult = validate("'hello'->add(Sequence{1,2,3})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(Integer.class))),
				types);
	}

	@Test
	public void testAddSetNothingString() {
		VariableBuilder variables = new VariableBuilder().addVar("self", setType(nothingType("Empty")));
		final IValidationResult validationResult = validate("self->add('hello')", variables.build());

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Empty", validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddSetStringNothing() {
		VariableBuilder variables = new VariableBuilder().addVar("self", nothingType("Empty"));
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->add(self)", variables
				.build());

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Empty", validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->add(OrderedSet{1,2,3})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(Integer.class))),
				types);
	}

	@Test
	public void testAddListNothingString() {
		VariableBuilder variables = new VariableBuilder().addVar("self", sequenceType(nothingType("Empty")));
		final IValidationResult validationResult = validate("self->add('hello')", variables.build());

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Empty", validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddListStringNothing() {
		VariableBuilder variables = new VariableBuilder().addVar("self", nothingType("Empty"));
		final IValidationResult validationResult = validate("Sequence{'hello'}->add(self)", variables.build());

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Empty", validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->add(Sequence{1,2,3})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testAnyNoBooleanLambda() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->any(i | 1)");

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("expression in an any must return a boolean", validationResult.getMessages().get(0)
				.getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		assertTrue(types.iterator().next() instanceof NothingType);
		assertEquals("expression in an any must return a boolean", ((NothingType)types.iterator().next())
				.getMessage());
	}

	@Test
	public void testAnyNoBooleanLambda_noLast() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->any(i | 1)->size()");

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("expression in an any must return a boolean", validationResult.getMessages().get(0)
				.getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAnySet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->any(i | i = 'hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testAnyList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->any(i | i = 'hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testAsOrderedSetSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->asOrderedSet()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testAsOrderedSetList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->asOrderedSet()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testAsSequenceSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->asSequence()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testAsSequenceList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->asSequence()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testAsSetSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->asSet()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testAsSetList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->asSet()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testAt() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->at(1)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testSortedBySet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->sortedBy(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSortedBySetNoComparableLambda() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->sortedBy(i | i->asSequence())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSortedByList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->sortedBy(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSortedByListNoComparableLambda() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->sortedBy(i | i->asSequence())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testCollectBySet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->collect(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class))), types);
	}

	@Test
	public void testCollectByList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->collect(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testConcat() {
		final IValidationResult validationResult = validate("'hello'.concat('world')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testCountImplicitSet() {
		final IValidationResult validationResult = validate("'hello'->count(5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
	}

	@Test
	public void testCountSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->count(5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
	}

	@Test
	public void testCountList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->count(5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
	}

	@Test
	public void testExcludesSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->excludes(5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->excludes(5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->includes(5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->includes(5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludingSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->including(5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(Integer.class))),
				types);
	}

	@Test
	public void testIncludingList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->including(5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testReverseSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->reverse()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testReverseList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->reverse()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testIndexOf() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->indexOf(10)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
	}

	@Test
	public void testInsertAt() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->insertAt(1, 2.0)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)),
				sequenceType(classType(Double.class))), types);
	}

	// FIXME move this elsewhere
	@Test
	public void testLambdaInSelect() {
		final IValidationResult validationResult = validate("2.0->select(i | i.oclIsKindOf(String))->isEmpty()");

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Always false:\nNothing inferred when i (java.lang.Double) is kind of java.lang.String",
				validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	// FIXME move this elsewhere
	@Test
	public void testLambdaInSelect_complex() {
		final IValidationResult validationResult = validate("'hello'->select(i | i.oclIsKindOf(Integer) or i.oclIsKindOf(Real))->isEmpty()");

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(
				"Always false:\nNothing inferred when i (java.lang.String) is kind of java.lang.Integer",
				validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());
		assertEquals("Always false:\nNothing inferred when i (java.lang.String) is kind of java.lang.Double",
				validationResult.getMessages().get(1).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(1).getLevel());
		assertEquals(2, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	// FIXME move this elsewhere
	@Test
	public void testNotEquals() {
		final IValidationResult validationResult = validate("'hello' != null");

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Always false:\nNothing inferred when i (java.lang.Double) is kind of java.lang.String",
				validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	// FIXME move this elsewhere
	@Test
	public void testCollectionAddString() {
		final IValidationResult validationResult = validate("'hello'->asSet()->add(' ')->add('world')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	// FIXME move this elsewhere
	@Test
	public void testCollectionStringAddInteger() {
		final IValidationResult validationResult = validate("OrderedSet{'a','b'}->add(OrderedSet{1})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testIntersectionSetSetNothingLeft() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		getQueryEnvironment().registerEPackage(ePkg);
		VariableBuilder variables = new VariableBuilder().addVar("eCls1", eClassifierType(eCls1)).addVar(
				"eCls2", eClassifierType(eCls2));
		final IValidationResult validationResult = validate("eCls1->intersection(eCls2->asSet())", variables
				.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals("Nothing left after intersection:\n "
				+ "Nothing left after intersection of Set(EClassifier=eCls1) and Set(EClassifier=eCls2)",
				((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionSetSetNothingLeft_atCall() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		getQueryEnvironment().registerEPackage(ePkg);
		VariableBuilder variables = new VariableBuilder().addVar("eCls1", eClassifierType(eCls1)).addVar(
				"eCls2", eClassifierType(eCls2));
		final IValidationResult validationResult = validate(
				"eCls1->intersection(eCls2->asSet())->asSequence()->at(0)", variables.build());

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Nothing left after intersection:\n"
				+ " Nothing left after intersection of Set(EClassifier=eCls1) and Set(EClassifier=eCls2)",
				validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals("Nothing left after intersection:\n "
				+ "Nothing left after intersection of Set(EClassifier=eCls1) and Set(EClassifier=eCls2)",
				((NothingType)type).getMessage());
	}

	@Test
	public void testIntersectionSetSetNothingLeft_addCall() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		getQueryEnvironment().registerEPackage(ePkg);
		VariableBuilder variables = new VariableBuilder().addVar("eCls1", eClassifierType(eCls1)).addVar(
				"eCls2", eClassifierType(eCls2));
		final IValidationResult validationResult = validate(
				"eCls1->intersection(eCls2->asSet())->add(eCls1)", variables.build());

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Nothing left after intersection:\n"
				+ " Nothing left after intersection of Set(EClassifier=eCls1) and Set(EClassifier=eCls2)",
				validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals("Nothing left after intersection:\n "
				+ "Nothing left after intersection of Set(EClassifier=eCls1) and Set(EClassifier=eCls2)",
				((NothingType)type).getMessage());
	}

	public void testIntersectionSetSetTopSubType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);
		final EClass eCls3 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls3.setName("eCls3");
		eCls3.getESuperTypes().add(eCls1);
		eCls3.getESuperTypes().add(eCls2);
		ePkg.getEClassifiers().add(eCls3);
		final EClass eCls4 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls4.setName("eCls4");
		eCls4.getESuperTypes().add(eCls1);
		eCls4.getESuperTypes().add(eCls2);
		eCls4.getESuperTypes().add(eCls3);
		ePkg.getEClassifiers().add(eCls4);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {setType(eClassifierType(eCls1)),
					setType(eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(eCls3)) };

			assertValidation(expectedReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	public void testIntersectionSetSetSameType() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)),
				setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionSetSetEClassEClassifier() {
		final IType[] parameterTypes = new IType[] {setType(classType(EClass.class)),
				setType(classType(EClassifier.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionSetSetEClassifierEClass() {
		final IType[] parameterTypes = new IType[] {setType(classType(EClassifier.class)),
				setType(classType(EClass.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionListListNothingLeft() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(eCls1)),
					sequenceType(eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("Nothing left after intersection of Sequence(EClassifier=eCls1) and Sequence(EClassifier=eCls2)")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing left after intersection:\n Nothing left after intersection of Sequence(EClassifier=eCls1) and Sequence(EClassifier=eCls2)")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	public void testIntersectionListListTopSubType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);
		final EClass eCls3 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls3.setName("eCls3");
		eCls3.getESuperTypes().add(eCls1);
		eCls3.getESuperTypes().add(eCls2);
		ePkg.getEClassifiers().add(eCls3);
		final EClass eCls4 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls4.setName("eCls4");
		eCls4.getESuperTypes().add(eCls1);
		eCls4.getESuperTypes().add(eCls2);
		eCls4.getESuperTypes().add(eCls3);
		ePkg.getEClassifiers().add(eCls4);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(eCls1)),
					sequenceType(eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(eCls3)) };

			assertValidation(expectedReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	public void testIntersectionListListSameType() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionListListEClassEClassifier() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(EClass.class)),
				sequenceType(classType(EClassifier.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionListListEClassifierEClass() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(EClassifier.class)),
				sequenceType(classType(EClass.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionListSetNothingLeft() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(eCls1)),
					setType(eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("Nothing left after intersection of Sequence(EClassifier=eCls1) and Set(EClassifier=eCls2)")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing left after intersection:\n Nothing left after intersection of Sequence(EClassifier=eCls1) and Set(EClassifier=eCls2)")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	public void testIntersectionListSetTopSubType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);
		final EClass eCls3 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls3.setName("eCls3");
		eCls3.getESuperTypes().add(eCls1);
		eCls3.getESuperTypes().add(eCls2);
		ePkg.getEClassifiers().add(eCls3);
		final EClass eCls4 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls4.setName("eCls4");
		eCls4.getESuperTypes().add(eCls1);
		eCls4.getESuperTypes().add(eCls2);
		eCls4.getESuperTypes().add(eCls3);
		ePkg.getEClassifiers().add(eCls4);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(eCls1)),
					setType(eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(eCls3)) };

			assertValidation(expectedReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	public void testIntersectionListSetSameType() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionListSetEClassEClassifier() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(EClass.class)),
				setType(classType(EClassifier.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionListSetEClassifierEClass() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(EClassifier.class)),
				setType(classType(EClass.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionSetListNothingLeft() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {setType(eClassifierType(eCls1)),
					sequenceType(eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {setType(nothingType("Nothing left after intersection of Set(EClassifier=eCls1) and Sequence(EClassifier=eCls2)")) };
			final IType[] expectedAllReturnTypes = new IType[] {setType(nothingType("Nothing left after intersection:\n Nothing left after intersection of Set(EClassifier=eCls1) and Sequence(EClassifier=eCls2)")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	public void testIntersectionSetListTopSubType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);
		final EClass eCls3 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls3.setName("eCls3");
		eCls3.getESuperTypes().add(eCls1);
		eCls3.getESuperTypes().add(eCls2);
		ePkg.getEClassifiers().add(eCls3);
		final EClass eCls4 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls4.setName("eCls4");
		eCls4.getESuperTypes().add(eCls1);
		eCls4.getESuperTypes().add(eCls2);
		eCls4.getESuperTypes().add(eCls3);
		ePkg.getEClassifiers().add(eCls4);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {setType(eClassifierType(eCls1)),
					sequenceType(eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(eCls3)) };

			assertValidation(expectedReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	public void testIntersectionSetListSameType() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)),
				sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionSetListEClassEClassifier() {
		final IType[] parameterTypes = new IType[] {setType(classType(EClass.class)),
				sequenceType(classType(EClassifier.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIntersectionSetListEClassifierEClass() {
		final IType[] parameterTypes = new IType[] {setType(classType(EClassifier.class)),
				sequenceType(classType(EClass.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	public void testIsEmptyList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isEmpty", parameterTypes);
	}

	public void testIsEmptySet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isEmpty", parameterTypes);
	}

	public void testIsUniqueSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)),
				lambdaType("i", classType(String.class), classType(Object.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isUnique", parameterTypes);
	}

	public void testIsUniqueList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				lambdaType("i", classType(String.class), classType(Object.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isUnique", parameterTypes);
	}

	public void testLast() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "last", parameterTypes);
	}

	public void testNotEmptyList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "notEmpty", parameterTypes);
	}

	public void testNotEmptySet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "notEmpty", parameterTypes);
	}

	public void testOneNoBooleanLambda() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				lambdaType("i", classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType("expression in one must return a boolean") };

		assertValidation(expectedReturnTypes, "one", parameterTypes);
	}

	public void testOneSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					setType(classType(String.class)),
					lambdaType("i", classType(String.class), eClassifierType(EcorePackage.eINSTANCE
							.getEBooleanObject())) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEBooleanObject()) };

			assertValidation(expectedReturnTypes, "one", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	public void testOneList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					sequenceType(classType(String.class)),
					lambdaType("i", classType(String.class), eClassifierType(EcorePackage.eINSTANCE
							.getEBooleanObject())) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEBooleanObject()) };

			assertValidation(expectedReturnTypes, "one", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	public void testPrepend() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "prepend", parameterTypes);
	}

	public void testRejectNoBooleanLambda() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				lambdaType("i", classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType("expression in a reject must return a boolean") };

		assertValidation(expectedReturnTypes, "reject", parameterTypes);
	}

	public void testRejectSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					setType(classType(String.class)),
					lambdaType("i", classType(String.class), eClassifierType(EcorePackage.eINSTANCE
							.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

			assertValidation(expectedReturnTypes, "reject", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	public void testRejectList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					sequenceType(classType(String.class)),
					lambdaType("i", classType(String.class), eClassifierType(EcorePackage.eINSTANCE
							.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

			assertValidation(expectedReturnTypes, "reject", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	public void testSelectNoBooleanLambda() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				lambdaType("i", classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType("expression in a select must return a boolean") };

		assertValidation(expectedReturnTypes, "select", parameterTypes);
	}

	public void testSelectSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					setType(classType(String.class)),
					lambdaType("i", classType(String.class), eClassifierType(EcorePackage.eINSTANCE
							.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

			assertValidation(expectedReturnTypes, "select", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	public void testSelectList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					sequenceType(classType(String.class)),
					lambdaType("i", classType(String.class), eClassifierType(EcorePackage.eINSTANCE
							.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

			assertValidation(expectedReturnTypes, "select", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	public void testSep2List() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "sep", parameterTypes);
	}

	public void testSep2Set() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)),
				classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "sep", parameterTypes);
	}

	public void testSep4List() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				classType(Integer.class), classType(String.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class)), sequenceType(classType(Double.class)) };

		assertValidation(expectedReturnTypes, "sep", parameterTypes);
	}

	public void testSep4Set() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)),
				classType(Integer.class), classType(String.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class)), sequenceType(classType(Double.class)) };

		assertValidation(expectedReturnTypes, "sep", parameterTypes);
	}

	public void testSizeList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "size", parameterTypes);
	}

	public void testSizeSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "size", parameterTypes);
	}

	public void testSubList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "sub", parameterTypes);
	}

	public void testSubSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)),
				setType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "sub", parameterTypes);
	}

	public void testSubOrderedSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)),
				classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "subOrderedSet", parameterTypes);
	}

	public void testSubSequence() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "subSequence", parameterTypes);
	}

	public void testSumList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "sum", parameterTypes);
	}

	public void testSumSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "sum", parameterTypes);
	}

	public void testUnionList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "union", parameterTypes);
	}

	public void testUnionSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)),
				setType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)),
				setType(classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "union", parameterTypes);
	}

	public void testFilterList() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {
				sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	public void testFilterListUnregistered() {
		final IType[] parameterTypes = new IType[] {
				sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(new NothingType(
				"Type EClassifier=EClass is not registered in the environment.")) };
		final IType[] expectedReturnAllTypes = new IType[] {sequenceType(new NothingType(
				"Nothing will be left after calling filter:\nType EClassifier=EClass is not registered in the environment.")) };

		assertValidation(expectedReturnTypes, expectedReturnAllTypes, "filter", parameterTypes);
	}

	public void testFilterSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
		final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	public void testFilterSetUnregistered() {
		final IType[] parameterTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
		final IType[] expectedReturnTypes = new IType[] {setType(new NothingType(
				"Type EClassifier=EClass is not registered in the environment.")) };
		final IType[] expectedReturnAllTypes = new IType[] {setType(new NothingType(
				"Nothing will be left after calling filter:\nType EClassifier=EClass is not registered in the environment.")) };

		assertValidation(expectedReturnTypes, expectedReturnAllTypes, "filter", parameterTypes);
	}

	public void testFilterListEClassifierSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {
				sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
				eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
						.getEDataType()) };
		final IType[] expectedReturnTypes = new IType[] {
				sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEDataType())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	public void testFilterListEClassifierSetUnregistered() {
		final IType[] parameterTypes = new IType[] {
				sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
				eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
						.getEDataType()) };
		final IType[] expectedReturnTypes = new IType[] {
				sequenceType(new NothingType("Type EClassifier=EClass is not registered in the environment.")),
				sequenceType(new NothingType(
						"Type EClassifier=EDataType is not registered in the environment.")) };
		final IType[] expectedReturnAllTypes = new IType[] {sequenceType(new NothingType(
				"Nothing will be left after calling filter:\n"
						+ "Type EClassifier=EClass is not registered in the environment.\n"
						+ "Type EClassifier=EDataType is not registered in the environment.")) };

		assertValidation(expectedReturnTypes, expectedReturnAllTypes, "filter", parameterTypes);
	}

	public void testFilterSetEClassifierSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
				eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
						.getEDataType()) };
		final IType[] expectedReturnTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				setType(eClassifierType(EcorePackage.eINSTANCE.getEDataType())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	public void testFilterSetEClassifierSetUnregistered() {
		final IType[] parameterTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
				eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
						.getEDataType()) };
		final IType[] expectedReturnTypes = new IType[] {
				setType(new NothingType("Type EClassifier=EClass is not registered in the environment.")),
				setType(new NothingType("Type EClassifier=EDataType is not registered in the environment.")) };
		final IType[] expectedReturnAllTypes = new IType[] {setType(new NothingType(
				"Nothing will be left after calling filter:\n"
						+ "Type EClassifier=EClass is not registered in the environment.\n"
						+ "Type EClassifier=EDataType is not registered in the environment.")) };

		assertValidation(expectedReturnTypes, expectedReturnAllTypes, "filter", parameterTypes);
	}

	public void testFilterListEInt() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {sequenceType(classType(Integer.class)),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEInt()) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEInt())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	public void testFilterListEIntUnregistered() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(Integer.class)),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEInt()) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(new NothingType(
				"Type EClassifier=EInt is not registered in the environment.")) };
		final IType[] expectedReturnAllTypes = new IType[] {sequenceType(new NothingType(
				"Nothing will be left after calling filter:\n"
						+ "Type EClassifier=EInt is not registered in the environment.")) };

		assertValidation(expectedReturnTypes, expectedReturnAllTypes, "filter", parameterTypes);
	}

	public void testFilterSetEInt() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(classType(Integer.class)),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEInt()) };
		final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEInt())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	public void testFilterSetEIntUnregistered() {
		final IType[] parameterTypes = new IType[] {setType(classType(Integer.class)),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEInt()) };
		final IType[] expectedReturnTypes = new IType[] {setType(new NothingType(
				"Type EClassifier=EInt is not registered in the environment.")) };
		final IType[] expectedReturnAllTypes = new IType[] {setType(new NothingType(
				"Nothing will be left after calling filter:\n"
						+ "Type EClassifier=EInt is not registered in the environment.")) };

		assertValidation(expectedReturnTypes, expectedReturnAllTypes, "filter", parameterTypes);
	}

	public void testFilterSetIncompatibleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
		final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEPackage())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	public void testFilterSetIncompatibleTypesUnregistered() {
		final IType[] parameterTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
		final IType[] expectedReturnTypes = new IType[] {setType(new NothingType(
				"Type EClassifier=EPackage is not registered in the environment.")) };
		final IType[] expectedReturnAllTypes = new IType[] {setType(new NothingType(
				"Nothing will be left after calling filter:\n"
						+ "Type EClassifier=EPackage is not registered in the environment.")) };

		assertValidation(expectedReturnTypes, expectedReturnAllTypes, "filter", parameterTypes);
	}

	public void testFilterSetCompatibleAndIncompatibleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
						.getEPackage()) };
		final IType[] expectedReturnTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	public void testFilterSetCompatibleAndIncompatibleTypesUnregistered() {
		final IType[] parameterTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
						.getEPackage()) };
		final IType[] expectedReturnTypes = new IType[] {
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	private static class VariableBuilder {
		private Map<String, Set<IType>> variables;

		public VariableBuilder() {
			variables = new LinkedHashMap<String, Set<IType>>();
		}

		public VariableBuilder addVar(String name, IType... types) {
			variables.put(name, ImmutableSet.copyOf(types));
			return this;
		}

		public Map<String, Set<IType>> build() {
			return variables;
		}
	}
}
