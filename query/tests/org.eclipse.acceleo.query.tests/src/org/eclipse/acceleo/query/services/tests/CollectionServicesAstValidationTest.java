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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryValidationEngine;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.QueryValidationEngine;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
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
	public void testConcatEmptyListEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->concat(Sequence{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty Sequence defined in extension\n"
				+ " Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatIntListRealList() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->concat(Sequence{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(Double.class))), types);
	}

	@Test
	public void testConcatEmptyListEmptySet() {
		final IValidationResult validationResult = validate("Sequence{}->concat(OrderedSet{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty Sequence defined in extension\n"
				+ " Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatIntListRealSet() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->concat(OrderedSet{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(Double.class))), types);
	}

	@Test
	public void testConcatListNull() {
		final IValidationResult validationResult = validate("Sequence{}->concat(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty Sequence defined in extension\n"
				+ " concat can only be called on collections, but null was used as its argument.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatListNullAsList() {
		final IValidationResult validationResult = validate("Sequence{}->concat(null->asSequence())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty Sequence defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatListNullAsSet() {
		final IValidationResult validationResult = validate("Sequence{}->concat(null->asSet())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty Sequence defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatNullList() {
		final IValidationResult validationResult = validate("null->concat(Sequence{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n The Collection was empty due to a null value being wrapped as a Collection.\n"
				+ " Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatNullSet() {
		final IValidationResult validationResult = validate("null->concat(OrderedSet{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n The Collection was empty due to a null value being wrapped as a Collection.\n"
				+ " Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatEmptySetEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->concat(OrderedSet{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty OrderedSet defined in extension\n"
				+ " Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatIntSetRealSet() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3}->concat(OrderedSet{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class)), setType(classType(Double.class))),
				types);
	}

	@Test
	public void testConcatEmptySetEmptyList() {
		final IValidationResult validationResult = validate("OrderedSet{}->concat(Sequence{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty OrderedSet defined in extension\n"
				+ " Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatIntSetRealList() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3}->concat(Sequence{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class)), setType(classType(Double.class))),
				types);
	}

	@Test
	public void testConcatSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{}->concat(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty OrderedSet defined in extension\n"
				+ " concat can only be called on collections, but null was used as its argument.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatSetNullAsList() {
		final IValidationResult validationResult = validate("OrderedSet{}->concat(null->asSequence())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty OrderedSet defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testConcatSetNullAsSet() {
		final IValidationResult validationResult = validate("OrderedSet{}->concat(null->asSet())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after concat:\n Empty OrderedSet defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testUnionEmptyListEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->union(Sequence{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after union:\n Empty Sequence defined in extension\n"
				+ " Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testUnionIntListRealList() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->union(Sequence{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(Double.class))), types);
	}

	@Test
	public void testUnionListNull() {
		final IValidationResult validationResult = validate("Sequence{}->union(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after union:\n Empty Sequence defined in extension\n"
				+ " union can only be called on collections, but null was used as its argument.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testUnionListNullAsList() {
		final IValidationResult validationResult = validate("Sequence{}->union(null->asSequence())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after union:\n Empty Sequence defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testUnionNullSet() {
		final IValidationResult validationResult = validate("null->union(OrderedSet{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after union:\n The Collection was empty due to a null value being wrapped as a Collection.\n"
				+ " Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testUnionEmptySetEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->union(OrderedSet{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after union:\n Empty OrderedSet defined in extension\n"
				+ " Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testUnionIntSetRealSet() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3}->union(OrderedSet{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class)), setType(classType(Double.class))),
				types);
	}

	@Test
	public void testUnionSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{}->union(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after union:\n Empty OrderedSet defined in extension\n"
				+ " union can only be called on collections, but null was used as its argument.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testUnionSetNullAsSet() {
		final IValidationResult validationResult = validate("OrderedSet{}->union(null->asSet())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing left after union:\n Empty OrderedSet defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testAddImplicitSetNothingStringAsSequence() {
		VariableBuilder variables = new VariableBuilder().addVar("self", nothingType("Empty"));
		final IValidationResult validationResult = validate("self->add('hello'->asSequence())", variables
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
	public void testAddImplicitSetNothingStringAsSet() {
		VariableBuilder variables = new VariableBuilder().addVar("self", nothingType("Empty"));
		final IValidationResult validationResult = validate("self->add('hello'->asSet())", variables.build());

		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals("Empty", validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());
		assertEquals(1, validationResult.getMessages().size());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddImplicitSetStringSequenceOfNothing() {
		VariableBuilder variables = new VariableBuilder().addVar("self", sequenceType(nothingType("Empty")));
		final IValidationResult validationResult = validate("'hello'->add(self)", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testAddImplicitSetStringSetOfNothing() {
		VariableBuilder variables = new VariableBuilder().addVar("self", setType(nothingType("Empty")));
		final IValidationResult validationResult = validate("'hello'->add(self)", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
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

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
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

		// TODO don't we want to report the Nothing's message?
		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
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
	public void testSubEmptyListEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->sub(Sequence{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubIntListRealList() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->sub(Sequence{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testSubEmptyListEmptySet() {
		final IValidationResult validationResult = validate("Sequence{}->sub(OrderedSet{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubIntListRealSet() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->sub(OrderedSet{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testSubListNull() {
		final IValidationResult validationResult = validate("Sequence{}->sub(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubListNullAsList() {
		final IValidationResult validationResult = validate("Sequence{}->sub(null->asSequence())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubListNullAsSet() {
		final IValidationResult validationResult = validate("Sequence{}->sub(null->asSet())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubNullList() {
		final IValidationResult validationResult = validate("null->sub(Sequence{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubNullSet() {
		final IValidationResult validationResult = validate("null->sub(OrderedSet{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubEmptySetEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->sub(OrderedSet{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubIntSetRealSet() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3}->sub(OrderedSet{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class))), types);
	}

	@Test
	public void testSubEmptySetEmptyList() {
		final IValidationResult validationResult = validate("OrderedSet{}->sub(Sequence{})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubIntSetRealList() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3}->sub(Sequence{2.0})");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class))), types);
	}

	@Test
	public void testSubSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{}->sub(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubSetNullAsList() {
		final IValidationResult validationResult = validate("OrderedSet{}->sub(null->asSequence())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubSetNullAsSet() {
		final IValidationResult validationResult = validate("OrderedSet{}->sub(null->asSet())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIncludingList() {
		final IValidationResult validationResult = validate("Sequence{1}->including(2)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testIncludingListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{1}->including('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class))), types);
	}

	@Test
	public void testIncludingListNull() {
		final IValidationResult validationResult = validate("Sequence{1}->including(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)), sequenceType(classType(null))),
				types);
	}

	@Test
	public void testIncludingOnNull() {
		final IValidationResult validationResult = validate("null->including(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(null))), types);
	}

	@Test
	public void testIncludingSet() {
		final IValidationResult validationResult = validate("OrderedSet{1}->including(2)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class))), types);
	}

	@Test
	public void testIncludingSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{1}->including('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class)), setType(classType(String.class))),
				types);
	}

	@Test
	public void testIncludingSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{1}->including(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class)), setType(classType(null))), types);
	}

	@Test
	public void testExcludingList() {
		final IValidationResult validationResult = validate("Sequence{1}->excluding(2)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testExcludingListNull() {
		final IValidationResult validationResult = validate("Sequence{1}->excluding(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testExcludingOnNull() {
		final IValidationResult validationResult = validate("null->excluding(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testExcludingSet() {
		final IValidationResult validationResult = validate("OrderedSet{1}->excluding(2)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class))), types);
	}

	@Test
	public void testExcludingSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{1}->excluding(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class))), types);
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
	public void testReverseOnNull() {
		final IValidationResult validationResult = validate("null->reverse()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
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
	public void testIsEmptyList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->isEmpty()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIsEmptyOnNull() {
		final IValidationResult validationResult = validate("null->isEmpty()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIsEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->isEmpty()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testNotEmptyList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->notEmpty()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testNotEmptyOnNull() {
		final IValidationResult validationResult = validate("null->notEmpty()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testNotEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->notEmpty()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testFirstList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->first()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testFirstEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->first()");

		String message = "Empty Sequence defined in extension";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testFirstListMultipleTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->first()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testFirstOnNull() {
		final IValidationResult validationResult = validate("null->first()");

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testFirstSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->first()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testFirstSetMultipleTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->first()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testFirstEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->first()");

		String message = "Empty OrderedSet defined in extension";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testAtList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->at(1)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testAtListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->at(1)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testAtListOutOfBounds() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->at(3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testAtListZero() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->at(0)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testAtListNull() {
		final IValidationResult validationResult = validate("Sequence{1}->at(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
	}

	@Test
	public void testAtOnNull() {
		final IValidationResult validationResult = validate("null->asSequence()->at(0)");

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testSizeList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->size()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
	}

	@Test
	public void testSizeEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->size()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
	}

	@Test
	public void testSizeOnNull() {
		final IValidationResult validationResult = validate("Sequence{}->size()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
	}

	@Test
	public void testSizeSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->size()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
	}

	@Test
	public void testSizeEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->size()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Integer.class)), types);
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
	public void testAsSetSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->asSet()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(Integer.class))),
				types);
	}

	@Test
	public void testAsSetListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->asSet()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(Integer.class))),
				types);
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
	public void testAsOrderedSetSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->asOrderedSet()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(Integer.class))),
				types);
	}

	@Test
	public void testAsOrderedSetListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->asOrderedSet()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(Integer.class))),
				types);
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
	public void testAsSequenceSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->asSequence()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testAsSequenceListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->asSequence()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testSortedBySet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->sortedBy(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testSortedBySetContainingNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', null, 'world'}->sortedBy(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(null))), types);
	}

	@Test
	public void testSortedBySetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1, 1.5}->sortedBy(i | i)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(Integer.class)),
				setType(classType(Double.class))), types);
	}

	@Test
	public void testSortedBySetEObjectName() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		VariableBuilder variables = new VariableBuilder().addVar("eCls1",
				eClassifierType(EcorePackage.eINSTANCE.getEClass())).addVar("eCls2",
				eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("OrderedSet{eCls1, eCls2}->sortedBy(i | i.name)",
				variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testSortedBySetNoComparableLambda() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->sortedBy(i | i->asSequence())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testSortedBySetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->sortedBy(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testSortedByOnNull() {
		// FIXME "null.size()" shouldn't have been found
		final IValidationResult validationResult = validate("null->sortedBy(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
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
	public void testSortedByListContainingNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', null, 'world'}->sortedBy(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)), sequenceType(classType(null))),
				types);
	}

	@Test
	public void testSortedByListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1, 1.5}->sortedBy(i | i)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))), types);
	}

	@Test
	public void testSortedByListEObjectName() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		VariableBuilder variables = new VariableBuilder().addVar("eCls1",
				eClassifierType(EcorePackage.eINSTANCE.getEClass())).addVar("eCls2",
				eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("Sequence{eCls1, eCls2}->sortedBy(i | i.name)",
				variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))),
				types);
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
	public void testSortedByListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->sortedBy(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSelectList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->select(i | i.size() = 5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSelectListAlwaysTrue() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->select(i | i.oclIsKindOf(String))");

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of java.lang.String";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSelectListAlwaysFalse() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->select(i | not i.oclIsKindOf(Integer))");

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of java.lang.Integer";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSelectListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->select(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "expression in a select must return a boolean";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSelectListNotBooleanLambda() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->select(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "expression in a select must return a boolean";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSelectOnNull() {
		final IValidationResult validationResult = validate("null->select(i | i <> null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSelectSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->select(i | i.size() = 5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testSelectSetAlwaysTrue() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->select(i | i.oclIsKindOf(String))");

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of java.lang.String";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testSelectSetAlwaysFalse() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->select(i | i.oclIsKindOf(Integer))");

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of java.lang.Integer";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testSelectSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->select(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "expression in a select must return a boolean";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSelectSetNotBooleanLambda() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->select(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "expression in a select must return a boolean";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testRejectList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->reject(i | i.size() = 5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testRejectListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->reject(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "expression in a reject must return a boolean";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testRejectListNotBooleanLambda() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->reject(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "expression in a reject must return a boolean";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testRejectOnNull() {
		final IValidationResult validationResult = validate("null->reject(i | i.size() = 5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testRejectSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->reject(i | i.size() = 5)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testRejectSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->reject(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "expression in a reject must return a boolean";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testRejectSetNotBooleanLambda() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->reject(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "expression in a reject must return a boolean";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testCollectList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->collect(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testCollectListImplicitFlatten() {
		final IValidationResult validationResult = validate("Sequence{Sequence{'hello'}, Sequence{OrderedSet{1}}}->collect(i | i)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)),
				sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testCollectListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->collect(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// FIXME this shouldn't even parse in the first place
		assertEquals(ImmutableSet.of(), types);

		// String message = "expression in a reject must return a boolean";
		// assertEquals(1, types.size());
		// IType type = types.iterator().next();
		// assertTrue(type instanceof SequenceType);
		// assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		// assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testCollectListNullPruned() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1, 3.1}->collect(i | if i.oclIsKindOf(String) then i else null endif)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testCollectListAlwaysTrueBoolean() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->collect(i | i.oclIsKindOf(String))");

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of java.lang.String";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Boolean.class))), types);
	}

	@Test
	public void testCollectListAlwaysFalseBoolean() {
		final IValidationResult validationResult = validate("Sequence{1, 2}->collect(i | i.oclIsKindOf(String))");

		String message = "Always false:\nNothing inferred when i (java.lang.Integer) is kind of java.lang.String";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Boolean.class))), types);
	}

	@Test
	public void testCollectOnNull() {
		final IValidationResult validationResult = validate("null->collect(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testCollectSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->collect(i | i.size())");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Integer.class))), types);
	}

	@Test
	public void testCollectSetImplicitFlatten() {
		final IValidationResult validationResult = validate("OrderedSet{Sequence{'hello'}, Sequence{OrderedSet{1}}}->collect(i | i)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class)), setType(classType(Integer.class))),
				types);
	}

	@Test
	public void testCollectSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->collect(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// FIXME this shouldn't even parse in the first place
		assertEquals(ImmutableSet.of(), types);

		// String message = "expression in a reject must return a boolean";
		// assertEquals(1, types.size());
		// IType type = types.iterator().next();
		// assertTrue(type instanceof SetType);
		// assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		// assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testCollectSetNullPruned() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1, 3.1}->collect(i | if i.oclIsKindOf(String) then i else null endif)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(String.class))), types);
	}

	@Test
	public void testCollectSetAlwaysTrueBoolean() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->collect(i | i.oclIsKindOf(String))");

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of java.lang.String";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Boolean.class))), types);
	}

	@Test
	public void testCollectSetAlwaysFalseBoolean() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2}->collect(i | i.oclIsKindOf(String))");

		String message = "Always false:\nNothing inferred when i (java.lang.Integer) is kind of java.lang.String";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.INFO, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(classType(Boolean.class))), types);
	}

	@Test
	public void testClosureList() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("Sequence{pkg}->closure(i | i.eSubpackages)",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage()))), types);
	}

	@Test
	public void testClosureListMultipleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}->closure(i | i.eSubpackages->union(i.eClassifiers))", new VariableBuilder()
						.addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage())).build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testClosureListNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("Sequence{pkg}->closure(null)",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// FIXME shouldn't parse
		assertEquals(ImmutableSet.of(setType(classType(null))), types);
	}

	@Test
	public void testClosureOnNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("null->closure(i : ecore::EPackage | i.eSubpackages)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testClosureSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("OrderedSet{pkg}->closure(i | i.eSubpackages)",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage()))), types);
	}

	@Test
	public void testClosureSetMultipleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}->closure(i | i.eSubpackages->union(i.eClassifiers))", new VariableBuilder()
						.addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage())).build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testClosureSetNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("OrderedSet{pkg}->closure(null)",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// FIXME shouldn't parse
		assertEquals(ImmutableSet.of(setType(classType(null))), types);
	}

	@Test
	public void testFilterList() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter(ecore::EClass)", new VariableBuilder().addVar("pkg",
						eClassifierType(EcorePackage.eINSTANCE.getEPackage())).build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))),
				types);
	}

	@Test
	public void testFilterListMultipleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter({ecore::EClass | ecore::EDataType})",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEDataType()))), types);
	}

	@Test
	public void testFilterListMultipleTypesSameHierarchy() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter({ecore::EClass | ecore::EClassifier})",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testFilterListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->filter(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing will be left after calling filter:\nEClassifier on filter cannot be null.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testFilterOnNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("null->filter(ecore::EClass)",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing will be left after calling filter:\nThe Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testFilterSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter(ecore::EClass)", new VariableBuilder().addVar("pkg",
						eClassifierType(EcorePackage.eINSTANCE.getEPackage())).build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testFilterSetMultipleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter({ecore::EClass | ecore::EDataType})",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				setType(eClassifierType(EcorePackage.eINSTANCE.getEDataType()))), types);
	}

	@Test
	public void testFilterSetMultipleTypesSameHierarchy() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter({ecore::EClass | ecore::EClassifier})",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
				setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testFilterSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->filter(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "Nothing will be left after calling filter:\nEClassifier on filter cannot be null.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSepList() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(' ')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSepListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)), sequenceType(classType(null))),
				types);
	}

	@Test
	public void testSepOnNull() {
		final IValidationResult validationResult = validate("null->sep(' ')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(2, types.size());
		Iterator<IType> typeItr = types.iterator();
		IType type = typeItr.next();
		if (!type.equals(sequenceType(classType(String.class)))) {
			assertTrue(type instanceof SequenceType);
			assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
			assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
			type = typeItr.next();
			assertEquals(sequenceType(classType(String.class)), type);
		} else {
			type = typeItr.next();
			assertTrue(type instanceof SequenceType);
			assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
			assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
		}
	}

	@Test
	public void testSepSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(' ')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSepSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)), sequenceType(classType(null))),
				types);
	}

	@Test
	public void testSepPrefixSuffixListNullPrefixNullSeparatorNullSuffix() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(null, null, null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)), sequenceType(classType(null))),
				types);
	}

	@Test
	public void testSepPrefixSuffixListNullSeparatorNullSuffix() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(1, null, null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullSuffix() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(1, 2, null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullSeparator() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(1, null, 3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullPrefixNullSuffix() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(null, 2, null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullPrefixNullSeparator() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(null, null, 3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullPrefix() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(null, 2, 3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixList() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(1, 2, 3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSepPrefixSuffixOnNull() {
		final IValidationResult validationResult = validate("null->sep(1, 2, 3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(2, types.size());
		Iterator<IType> typeItr = types.iterator();
		IType type = typeItr.next();
		if (!type.equals(sequenceType(classType(Integer.class)))) {
			assertTrue(type instanceof SequenceType);
			assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
			assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
			type = typeItr.next();
			assertEquals(sequenceType(classType(Integer.class)), type);
		} else {
			type = typeItr.next();
			assertTrue(type instanceof SequenceType);
			assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
			assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
		}
	}

	@Test
	public void testSepPrefixSuffixSetNullPrefixNullSeparatorNullSuffix() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(null, null, null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(String.class)), sequenceType(classType(null))),
				types);
	}

	@Test
	public void testSepPrefixSuffixSetNullSeparatorNullSuffix() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(1, null, null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullSuffix() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(1, 2, null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullSeparator() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(1, null, 3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullPrefixNullSuffix() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(null, 2, null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullPrefixNullSeparator() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(null, null, 3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullPrefix() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(null, 2, 3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(1, 2, 3)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(sequenceType(classType(Integer.class)),
				sequenceType(classType(String.class))), types);
	}

	@Test
	public void testLastList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->last()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testLastEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->last()");

		String message = "Empty Sequence defined in extension";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testLastListMultipleTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->last()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testLastOnNull() {
		final IValidationResult validationResult = validate("null->last()");

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testLastSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->last()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testLastSetMultipleTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->last()");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testLastEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->last()");

		String message = "Empty OrderedSet defined in extension";
		assertFalse(validationResult.getMessages().isEmpty());
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(message, validationResult.getMessages().get(0).getMessage());
		assertEquals(ValidationMessageLevel.ERROR, validationResult.getMessages().get(0).getLevel());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testExcludesList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->excludes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->excludes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesListMultipleTypes() {
		final IValidationResult validationResult = validate("Sequence{1, 'hello', 2.0}->excludes('world')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->excludes(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesOnNull() {
		final IValidationResult validationResult = validate("null->excludes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->excludes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesSetList() {
		final IValidationResult validationResult = validate("OrderedSet{}->excludes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesSetMultipleTypes() {
		final IValidationResult validationResult = validate("OrderedSet{1, 'hello', 2.0}->excludes('world')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->excludes(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->includes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->includes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesListMultipleTypes() {
		final IValidationResult validationResult = validate("Sequence{1, 'hello', 2.0}->includes('world')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->includes(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesOnNull() {
		final IValidationResult validationResult = validate("null->includes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->includes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesSetList() {
		final IValidationResult validationResult = validate("OrderedSet{}->includes('hello')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesSetMultipleTypes() {
		final IValidationResult validationResult = validate("OrderedSet{1, 'hello', 2.0}->includes('world')");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->includes(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		AstResult ast = validationResult.getAstResult();
		Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
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
