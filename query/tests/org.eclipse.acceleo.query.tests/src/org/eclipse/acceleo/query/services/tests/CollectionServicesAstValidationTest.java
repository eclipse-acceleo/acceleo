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
package org.eclipse.acceleo.query.services.tests;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.parser.tests.ValidationTest;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectionServicesAstValidationTest extends AbstractServicesValidationTest {

	@Test
	public void testConcatEmptyListEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->concat(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n Empty Sequence defined in extension",
				10, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n Empty Sequence defined in extension",
				10, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	@Test
	public void testConcatEmptyListEmptySet() {
		final IValidationResult validationResult = validate("Sequence{}->concat(OrderedSet{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n Empty OrderedSet defined in extension",
				10, 32);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n Empty OrderedSet defined in extension",
				10, 32);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	@Test
	public void testConcatListNull() {
		final IValidationResult validationResult = validate("Sequence{}->concat(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n concat can only be called on collections, but null was used as its argument.",
				10, 24);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n concat can only be called on collections, but null was used as its argument.",
				10, 24);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				23, 37);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 38);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 38);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				23, 37);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				23, 32);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				23, 32);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty Sequence defined in extension",
				4, 24);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty Sequence defined in extension",
				4, 24);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty OrderedSet defined in extension",
				4, 26);

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty OrderedSet defined in extension",
				4, 26);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n Empty OrderedSet defined in extension",
				12, 34);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n Empty OrderedSet defined in extension",
				12, 34);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testConcatEmptySetEmptyList() {
		final IValidationResult validationResult = validate("OrderedSet{}->concat(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n Empty Sequence defined in extension",
				12, 32);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n Empty Sequence defined in extension",
				12, 32);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testConcatSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{}->concat(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n concat can only be called on collections, but null was used as its argument.",
				12, 26);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n concat can only be called on collections, but null was used as its argument.",
				12, 26);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				25, 39);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 40);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 40);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				25, 39);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				25, 34);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after concat:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				25, 34);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty Sequence defined in extension\n Empty Sequence defined in extension",
				10, 29);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty Sequence defined in extension\n Empty Sequence defined in extension",
				10, 29);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	@Test
	public void testUnionListNull() {
		final IValidationResult validationResult = validate("Sequence{}->union(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty Sequence defined in extension\n union can only be called on collections, but null was used as its argument.",
				10, 23);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty Sequence defined in extension\n union can only be called on collections, but null was used as its argument.",
				10, 23);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				22, 36);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 37);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 37);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				22, 36);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty OrderedSet defined in extension",
				4, 25);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty OrderedSet defined in extension",
				4, 25);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty OrderedSet defined in extension\n Empty OrderedSet defined in extension",
				12, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty OrderedSet defined in extension\n Empty OrderedSet defined in extension",
				12, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testUnionSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{}->union(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty OrderedSet defined in extension\n union can only be called on collections, but null was used as its argument.",
				12, 25);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty OrderedSet defined in extension\n union can only be called on collections, but null was used as its argument.",
				12, 25);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				24, 33);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 34);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after union:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 34);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				24, 33);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Empty", 0, 4);

		assertEquals(0, validationResult.getMessages(ast).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddImplicitSetNothingStringAsSet() {
		VariableBuilder variables = new VariableBuilder().addVar("self", nothingType("Empty"));
		final IValidationResult validationResult = validate("self->add('hello'->asSet())", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Empty", 0, 4);

		assertEquals(0, validationResult.getMessages(ast).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddImplicitSetStringSequenceOfNothing() {
		VariableBuilder variables = new VariableBuilder().addVar("self", sequenceType(nothingType("Empty")));
		final IValidationResult validationResult = validate("'hello'->add(self)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty", 13, 17);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO, "Empty collection: Empty", 13, 17);

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testAddImplicitSetStringSetOfNothing() {
		VariableBuilder variables = new VariableBuilder().addVar("self", setType(nothingType("Empty")));
		final IValidationResult validationResult = validate("'hello'->add(self)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty", 13, 17);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO, "Empty collection: Empty", 13, 17);

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testAddImplicitSet() {
		final IValidationResult validationResult = validate("'hello'->add(Sequence{1,2,3})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testAddSetNothingString() {
		VariableBuilder variables = new VariableBuilder().addVar("self", setType(nothingType("Empty")));
		final IValidationResult validationResult = validate("self->add('hello')", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty", 0, 4);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO, "Empty collection: Empty", 0, 4);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAddSetStringNothing() {
		VariableBuilder variables = new VariableBuilder().addVar("self", nothingType("Empty"));
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->add(self)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Empty", 25, 29);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Empty", 25, 29);

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->add(OrderedSet{1,2,3})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testAddListNothingString() {
		VariableBuilder variables = new VariableBuilder().addVar("self", sequenceType(nothingType("Empty")));
		final IValidationResult validationResult = validate("self->add('hello')", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty", 0, 4);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO, "Empty collection: Empty", 0, 4);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAddListStringNothing() {
		VariableBuilder variables = new VariableBuilder().addVar("self", nothingType("Empty"));
		final IValidationResult validationResult = validate("Sequence{'hello'}->add(self)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Empty", 23, 27);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Empty", 23, 27);

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->add(Sequence{1,2,3})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(Integer.class))),
				types);
	}

	@Test
	public void testSubEmptyListEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->sub(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 27);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 27);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testSubEmptyListEmptySet() {
		final IValidationResult validationResult = validate("Sequence{}->sub(OrderedSet{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 29);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 29);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testSubListNull() {
		final IValidationResult validationResult = validate("Sequence{}->sub(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 21);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 21);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				20, 34);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				20, 34);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				20, 29);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty Sequence defined in extension", 0, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				20, 29);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				0, 21);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				0, 21);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				0, 23);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				0, 23);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testSubEmptySetEmptyList() {
		final IValidationResult validationResult = validate("OrderedSet{}->sub(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				29);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				29);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testSubSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{}->sub(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				23);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				23);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				22, 36);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				37);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				37);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				22, 36);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				22, 31);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				32);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Empty OrderedSet defined in extension", 0,
				32);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				22, 31);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testIncludingListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{1}->including('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class))),
				types);
	}

	@Test
	public void testIncludingListNull() {
		final IValidationResult validationResult = validate("Sequence{1}->including(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testIncludingOnNull() {
		final IValidationResult validationResult = validate("null->including(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(null))), types);
	}

	@Test
	public void testIncludingSet() {
		final IValidationResult validationResult = validate("OrderedSet{1}->including(2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testIncludingSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{1}->including('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(String.class))), types);
	}

	@Test
	public void testIncludingSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{1}->including(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(null))), types);
	}

	@Test
	public void testExcludingList() {
		final IValidationResult validationResult = validate("Sequence{1}->excluding(2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testExcludingListNull() {
		final IValidationResult validationResult = validate("Sequence{1}->excluding(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testExcludingOnNull() {
		final IValidationResult validationResult = validate("null->excluding(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 21);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 21);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testExcludingSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{1}->excluding(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testReverseList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->reverse()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testReverseOnNull() {
		final IValidationResult validationResult = validate("null->reverse()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 15);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 15);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testIsEmptyList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->isEmpty()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsEmptyOnNull() {
		final IValidationResult validationResult = validate("null->isEmpty()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->isEmpty()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testNotEmptyList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->notEmpty()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testNotEmptyOnNull() {
		final IValidationResult validationResult = validate("null->notEmpty()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testNotEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->notEmpty()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testFirstList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->first()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testFirstEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->first()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Empty Sequence defined in extension";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 10, 19);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 10, 19);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testFirstListMultipleTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->first()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testFirstOnNull() {
		final IValidationResult validationResult = validate("null->first()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 4, 13);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 4, 13);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testFirstSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->first()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testFirstSetMultipleTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->first()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testFirstEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->first()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Empty OrderedSet defined in extension";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 12, 21);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 12, 21);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testAtList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->at(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAtListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->at(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testAtListOutOfBounds() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->at(3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAtListZero() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->at(0)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAtListNull() {
		final IValidationResult validationResult = validate("Sequence{1}->at(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testAtListOnNull() {
		final IValidationResult validationResult = validate("null->asSequence()->at(0)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 18);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.ERROR, message, 18, 25);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 18, 25);
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 18);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testAtSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->at(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAtSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->at(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testAtSetOutOfBounds() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->at(3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAtSetZero() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->at(0)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAtSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{1}->at(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testAtSetOnNull() {
		final IValidationResult validationResult = validate("null->asOrderedSet()->at(0)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 20);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.ERROR, message, 20, 27);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 20, 27);
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 20);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testSizeList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->size()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testSizeEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->size()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testSizeOnNull() {
		final IValidationResult validationResult = validate("Sequence{}->size()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testSizeSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->size()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testSizeEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->size()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testAsSetSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->asSet()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testAsSetList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->asSet()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testAsSetSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->asSet()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testAsSetListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->asSet()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testAsOrderedSetSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->asOrderedSet()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testAsOrderedSetList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->asOrderedSet()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testAsOrderedSetSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->asOrderedSet()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testAsOrderedSetListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->asOrderedSet()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testAsSequenceSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->asSequence()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testAsSequenceList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->asSequence()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testAsSequenceSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->asSequence()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(Integer.class))),
				types);
	}

	@Test
	public void testAsSequenceListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->asSequence()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(Integer.class))),
				types);
	}

	@Test
	public void testSortedBySet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->sortedBy(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testSortedBySetContainingNull() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', null, 'world'}->sortedBy(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(null))), types);
	}

	@Test
	public void testSortedBySetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1, 1.5}->sortedBy(i | i)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class)), setType(
				classType(Double.class))), types);
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
		VariableBuilder variables = new VariableBuilder().addVar("eCls1", eClassifierType(
				EcorePackage.eINSTANCE.getEClass())).addVar("eCls2", eClassifierType(EcorePackage.eINSTANCE
						.getEClass()));
		final IValidationResult validationResult = validate("OrderedSet{eCls1, eCls2}->sortedBy(i | i.name)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testSortedBySetEObjectNameClass() {
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
		VariableBuilder variables = new VariableBuilder().addVar("eCls1", classType(EClass.class)).addVar(
				"eCls2", classType(EClass.class));
		final IValidationResult validationResult = validate("OrderedSet{eCls1, eCls2}->sortedBy(i | i.name)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(EClass.class))), types);
	}

	@Test
	public void testSortedBySetEObjectNameEClassifierLiteral() {
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
		VariableBuilder variables = new VariableBuilder().addVar("eCls1", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClass())).addVar("eCls2", eClassifierLiteralType(
						EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("OrderedSet{eCls1, eCls2}->sortedBy(i | i.name)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testSortedBySetNoComparableLambda() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello'}->sortedBy(i | i->asSequence())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testSortedBySetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->sortedBy(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "The sortedBy service takes a lambda as parameter: v | v...",
				19, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals("The sortedBy service takes a lambda as parameter: v | v...", ((NothingType)type)
				.getMessage());
	}

	@Test
	public void testSortedByOnNull() {
		// FIXME "null.size()" shouldn't have been found
		final IValidationResult validationResult = validate("null->sortedBy(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 28);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 28);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSortedByListContainingNull() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', null, 'world'}->sortedBy(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSortedByListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1, 1.5}->sortedBy(i | i)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(Integer.class)),
				sequenceType(classType(Double.class))), types);
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
		VariableBuilder variables = new VariableBuilder().addVar("eCls1", eClassifierType(
				EcorePackage.eINSTANCE.getEClass())).addVar("eCls2", eClassifierType(EcorePackage.eINSTANCE
						.getEClass()));
		final IValidationResult validationResult = validate("Sequence{eCls1, eCls2}->sortedBy(i | i.name)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testSortedByListEObjectNameClass() {
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
		VariableBuilder variables = new VariableBuilder().addVar("eCls1", classType(EClass.class)).addVar(
				"eCls2", classType(EClass.class));
		final IValidationResult validationResult = validate("Sequence{eCls1, eCls2}->sortedBy(i | i.name)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(EClass.class))), types);
	}

	@Test
	public void testSortedByListEObjectNameEClassifierLiteral() {
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
		VariableBuilder variables = new VariableBuilder().addVar("eCls1", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClass())).addVar("eCls2", eClassifierLiteralType(
						EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("Sequence{eCls1, eCls2}->sortedBy(i | i.name)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testSortedByListNoComparableLambda() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello'}->sortedBy(i | i->asSequence())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSortedByListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->sortedBy(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "The sortedBy service takes a lambda as parameter: v | v...",
				17, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals("The sortedBy service takes a lambda as parameter: v | v...", ((NothingType)type)
				.getMessage());
	}

	@Test
	public void testSelectList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->select(i | i.size() = 5)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSelectListAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello'}->select(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 30, 51);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 30,
				51);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSelectListAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello'}->select(i | not i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 34, 56);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(1, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).getArguments().get(0)).get(0),
				ValidationMessageLevel.INFO, message, 34, 56);
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSelectListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->select(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The select service takes a lambda as parameter: v | v...", 17, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The select service takes a lambda as parameter: v | v...", 17, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "The select service takes a lambda as parameter: v | v...";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSelectListNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->select(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: expression in a select must return a boolean",
				26, 48);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: expression in a select must return a boolean",
				26, 48);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 27);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 27);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testSelectSetAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello'}->select(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 32, 53);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 32,
				53);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testSelectSetAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello'}->select(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 32, 54);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 32,
				54);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testSelectSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->select(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The select service takes a lambda as parameter: v | v...", 19, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The select service takes a lambda as parameter: v | v...", 19, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "The select service takes a lambda as parameter: v | v...";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSelectSetNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->select(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: expression in a select must return a boolean",
				28, 50);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: expression in a select must return a boolean",
				28, 50);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testRejectListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->reject(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The reject service takes a lambda as parameter: v | v...", 17, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The reject service takes a lambda as parameter: v | v...", 17, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "The reject service takes a lambda as parameter: v | v...";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testRejectListNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->reject(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: expression in a reject must return a boolean",
				26, 48);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: expression in a reject must return a boolean",
				26, 48);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testRejectSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->reject(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The reject service takes a lambda as parameter: v | v...", 19, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The reject service takes a lambda as parameter: v | v...", 19, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "The reject service takes a lambda as parameter: v | v...";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testRejectSetNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->reject(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: expression in a reject must return a boolean",
				28, 50);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: expression in a reject must return a boolean",
				28, 50);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testCollectListImplicitFlatten() {
		final IValidationResult validationResult = validate(
				"Sequence{Sequence{'hello'}, Sequence{OrderedSet{1}}}->collect(i | i)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(Integer.class))),
				types);
	}

	@Test
	public void testCollectListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->collect(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The collect service takes a lambda as parameter: v | v...", 17, 32);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "The collect service takes a lambda as parameter: v | v...";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testCollectListNullPruned() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 1, 3.1}->collect(i | if i.oclIsKindOf(String) then i else null endif)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testCollectListAlwaysTrueBoolean() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->collect(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 40, 61);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 40,
				61);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Boolean.class))), types);
	}

	@Test
	public void testCollectListAlwaysFalseBoolean() {
		final IValidationResult validationResult = validate(
				"Sequence{1, 2}->collect(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.Integer) is kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 28, 49);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 28,
				49);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Boolean.class))), types);
	}

	@Test
	public void testCollectOnNull() {
		final IValidationResult validationResult = validate("null->collect(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 27);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 27);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testCollectSetImplicitFlatten() {
		final IValidationResult validationResult = validate(
				"OrderedSet{Sequence{'hello'}, Sequence{OrderedSet{1}}}->collect(i | i)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testCollectSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->collect(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The collect service takes a lambda as parameter: v | v...", 19, 34);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "The collect service takes a lambda as parameter: v | v...";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testCollectSetNullPruned() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 1, 3.1}->collect(i | if i.oclIsKindOf(String) then i else null endif)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testCollectSetAlwaysTrueBoolean() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->collect(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 42, 63);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 42,
				63);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Boolean.class))), types);
	}

	@Test
	public void testCollectSetAlwaysFalseBoolean() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 2}->collect(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.Integer) is kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 30, 51);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 30,
				51);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Boolean.class))), types);
	}

	@Test
	public void testClosureList() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("Sequence{pkg}->closure(i | i.eSubpackages)",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage()))), types);
	}

	@Test
	public void testClosureListClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("Sequence{pkg}->closure(i | i.eSubpackages)",
				new VariableBuilder().addVar("pkg", classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage()))), types);
	}

	@Test
	public void testClosureListEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("Sequence{pkg}->closure(i | i.eSubpackages)",
				new VariableBuilder().addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE
						.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage()))), types);
	}

	@Test
	public void testClosureListMultipleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}->closure(i | i.eSubpackages->union(i.eClassifiers))", new VariableBuilder()
						.addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testClosureListMultipleTypesClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}->closure(i | i.eSubpackages->union(i.eClassifiers))", new VariableBuilder()
						.addVar("pkg", classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testClosureListMultipleTypesEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}->closure(i | i.eSubpackages->union(i.eClassifiers))", new VariableBuilder()
						.addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testClosureListNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("Sequence{pkg}->closure(null)",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "The closure service takes a lambda as parameter: v | v...", 13,
				28);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		final IType type = types.iterator().next();
		assertEquals(NothingType.class, type.getClass());
		assertEquals("The closure service takes a lambda as parameter: v | v...", ((NothingType)type)
				.getMessage());
	}

	@Test
	public void testClosureListNullClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("Sequence{pkg}->closure(null)",
				new VariableBuilder().addVar("pkg", classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "The closure service takes a lambda as parameter: v | v...", 13,
				28);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		final IType type = types.iterator().next();
		assertEquals(NothingType.class, type.getClass());
		assertEquals("The closure service takes a lambda as parameter: v | v...", ((NothingType)type)
				.getMessage());
	}

	@Test
	public void testClosureListNullEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("Sequence{pkg}->closure(null)",
				new VariableBuilder().addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE
						.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "The closure service takes a lambda as parameter: v | v...", 13,
				28);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		final IType type = types.iterator().next();
		assertEquals(NothingType.class, type.getClass());
		assertEquals("The closure service takes a lambda as parameter: v | v...", ((NothingType)type)
				.getMessage());
	}

	@Test
	public void testClosureOnNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"null->closure(i : ecore::EPackage | i.eSubpackages)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 51);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 51);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage()))), types);
	}

	@Test
	public void testClosureSetClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("OrderedSet{pkg}->closure(i | i.eSubpackages)",
				new VariableBuilder().addVar("pkg", classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage()))), types);
	}

	@Test
	public void testClosureSetEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("OrderedSet{pkg}->closure(i | i.eSubpackages)",
				new VariableBuilder().addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE
						.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage()))), types);
	}

	@Test
	public void testClosureSetMultipleTypesEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}->closure(i | i.eSubpackages->union(i.eClassifiers))", new VariableBuilder()
						.addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testClosureSetNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("OrderedSet{pkg}->closure(null)",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "The closure service takes a lambda as parameter: v | v...", 15,
				30);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		final IType type = types.iterator().next();
		assertEquals(NothingType.class, type.getClass());
		assertEquals("The closure service takes a lambda as parameter: v | v...", ((NothingType)type)
				.getMessage());
	}

	@Test
	public void testClosureSetNullCass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("OrderedSet{pkg}->closure(null)",
				new VariableBuilder().addVar("pkg", classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "The closure service takes a lambda as parameter: v | v...", 15,
				30);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		final IType type = types.iterator().next();
		assertEquals(NothingType.class, type.getClass());
		assertEquals("The closure service takes a lambda as parameter: v | v...", ((NothingType)type)
				.getMessage());
	}

	@Test
	public void testClosureSetNullEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate("OrderedSet{pkg}->closure(null)",
				new VariableBuilder().addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE
						.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "The closure service takes a lambda as parameter: v | v...", 15,
				30);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		final IType type = types.iterator().next();
		assertEquals(NothingType.class, type.getClass());
		assertEquals("The closure service takes a lambda as parameter: v | v...", ((NothingType)type)
				.getMessage());
	}

	@Test
	public void testFilterList() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter(ecore::EClass)", new VariableBuilder().addVar("pkg",
						eClassifierType(EcorePackage.eINSTANCE.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testFilterListClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter(ecore::EClass)", new VariableBuilder().addVar("pkg",
						classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testFilterListECLassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter(ecore::EClass)", new VariableBuilder().addVar("pkg",
						eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testFilterListMultipleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter({ecore::EClass | ecore::EDataType})",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), sequenceType(
				eClassifierType(EcorePackage.eINSTANCE.getEDataType()))), types);
	}

	@Test
	public void testFilterListMultipleTypesClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter({ecore::EClass | ecore::EDataType})",
				new VariableBuilder().addVar("pkg", classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), sequenceType(
				eClassifierType(EcorePackage.eINSTANCE.getEDataType()))), types);
	}

	@Test
	public void testFilterListMultipleTypesEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter({ecore::EClass | ecore::EDataType})",
				new VariableBuilder().addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE
						.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), sequenceType(
				eClassifierType(EcorePackage.eINSTANCE.getEDataType()))), types);
	}

	@Test
	public void testFilterListMultipleTypesSameHierarchy() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter({ecore::EClass | ecore::EClassifier})",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), sequenceType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testFilterListMultipleTypesSameHierarchyClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter({ecore::EClass | ecore::EClassifier})",
				new VariableBuilder().addVar("pkg", classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), sequenceType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testFilterListMultipleTypesSameHierarchyEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"Sequence{pkg}.eClassifiers->filter({ecore::EClass | ecore::EClassifier})",
				new VariableBuilder().addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE
						.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), sequenceType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testFilterListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->filter(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling filter:\nEClassifier on filter cannot be null.",
				17, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling filter:\nEClassifier on filter cannot be null.",
				17, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
				new VariableBuilder().build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling filter:\nThe Collection was empty due to a null value being wrapped as a Collection.",
				4, 27);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling filter:\nThe Collection was empty due to a null value being wrapped as a Collection.",
				4, 27);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testFilterSetClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter(ecore::EClass)", new VariableBuilder().addVar("pkg",
						classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testFilterSetEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter(ecore::EClass)", new VariableBuilder().addVar("pkg",
						eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass()))), types);
	}

	@Test
	public void testFilterSetMultipleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter({ecore::EClass | ecore::EDataType})",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEDataType()))), types);
	}

	@Test
	public void testFilterSetMultipleTypesClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter({ecore::EClass | ecore::EDataType})",
				new VariableBuilder().addVar("pkg", classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEDataType()))), types);
	}

	@Test
	public void testFilterSetMultipleTypesEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter({ecore::EClass | ecore::EDataType})",
				new VariableBuilder().addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE
						.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEDataType()))), types);
	}

	@Test
	public void testFilterSetMultipleTypesSameHierarchy() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter({ecore::EClass | ecore::EClassifier})",
				new VariableBuilder().addVar("pkg", eClassifierType(EcorePackage.eINSTANCE.getEPackage()))
						.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testFilterSetMultipleTypesSameHierarchyClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter({ecore::EClass | ecore::EClassifier})",
				new VariableBuilder().addVar("pkg", classType(EPackage.class)).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testFilterSetMultipleTypesSameHierarchyEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IValidationResult validationResult = validate(
				"OrderedSet{pkg}.eClassifiers->filter({ecore::EClass | ecore::EClassifier})",
				new VariableBuilder().addVar("pkg", eClassifierLiteralType(EcorePackage.eINSTANCE
						.getEPackage())).build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())), setType(
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()))), types);
	}

	@Test
	public void testFilterSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->filter(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling filter:\nEClassifier on filter cannot be null.",
				19, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling filter:\nEClassifier on filter cannot be null.",
				19, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSepListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepOnNull() {
		final IValidationResult validationResult = validate("null->sep(' ')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 14);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 14);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testSepSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullPrefixNullSeparatorNullSuffix() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->sep(null, null, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullSeparatorNullSuffix() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(1, null, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullSuffix() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(1, 2, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullSeparator() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(1, null, 3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullPrefixNullSuffix() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(null, 2, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullPrefixNullSeparator() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(null, null, 3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixListNullPrefix() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(null, 2, 3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixList() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->sep(1, 2, 3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class))),
				types);
	}

	@Test
	public void testSepPrefixSuffixOnNull() {
		final IValidationResult validationResult = validate("null->sep(1, 2, 3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 18);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 18);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

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
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->sep(null, null, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullSeparatorNullSuffix() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->sep(1, null, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullSuffix() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(1, 2, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullSeparator() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(1, null, 3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullPrefixNullSuffix() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->sep(null, 2, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullPrefixNullSeparator() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->sep(null, null, 3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSetNullPrefix() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(null, 2, 3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class)),
				sequenceType(classType(null))), types);
	}

	@Test
	public void testSepPrefixSuffixSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->sep(1, 2, 3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(3)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(String.class))),
				types);
	}

	@Test
	public void testLastList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->last()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testLastEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->last()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Empty Sequence defined in extension";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 10, 18);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 10, 18);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testLastListMultipleTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 1}->last()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testLastOnNull() {
		final IValidationResult validationResult = validate("null->last()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 4, 12);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 4, 12);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testLastSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->last()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testLastSetMultipleTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 1}->last()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class), classType(Integer.class)), types);
	}

	@Test
	public void testLastEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->last()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Empty OrderedSet defined in extension";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 12, 20);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 12, 20);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testExcludesList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->excludes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->excludes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesListMultipleTypes() {
		final IValidationResult validationResult = validate("Sequence{1, 'hello', 2.0}->excludes('world')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->excludes(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesOnNull() {
		final IValidationResult validationResult = validate("null->excludes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->excludes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesSetList() {
		final IValidationResult validationResult = validate("OrderedSet{}->excludes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesSetMultipleTypes() {
		final IValidationResult validationResult = validate("OrderedSet{1, 'hello', 2.0}->excludes('world')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->excludes(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->includes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->includes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesListMultipleTypes() {
		final IValidationResult validationResult = validate("Sequence{1, 'hello', 2.0}->includes('world')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->includes(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesOnNull() {
		final IValidationResult validationResult = validate("null->includes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->includes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesSetList() {
		final IValidationResult validationResult = validate("OrderedSet{}->includes('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesSetMultipleTypes() {
		final IValidationResult validationResult = validate("OrderedSet{1, 'hello', 2.0}->includes('world')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->includes(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testAnyList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->any(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAnyListAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->any(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 36, 57);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 36,
				57);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAnyListAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->any(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 36, 58);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 36,
				58);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAnyListDifferentTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->any(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class), classType(Integer.class), classType(Double.class)),
				types);
	}

	@Test
	public void testAnyListDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->any(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAnyListNotBooleanLambda() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->any(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "expression in an any must return a boolean";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 26, 45);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 26, 45);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testAnyListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->any(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The any service takes a lambda as parameter: v | v...";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 26, 37);

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 26, 37);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testAnyOnNull() {
		final IValidationResult validationResult = validate("null->any(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 4, 24);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 4, 24);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testAnySet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->any(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAnySetAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->any(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 38, 59);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 38,
				59);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAnySetAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->any(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 38, 60);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 38,
				60);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAnySetDifferentTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->any(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class), classType(Integer.class), classType(Double.class)),
				types);
	}

	@Test
	public void testAnySetDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->any(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(String.class)), types);
	}

	@Test
	public void testAnySetNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->any(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "expression in an any must return a boolean";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 47);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 47);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testAnySetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->any(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The any service takes a lambda as parameter: v | v...";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 39);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 39);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testCountList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 'map', 'map', 'hello'}->count('map')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testCountListDifferentTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2, 1, 3}->count(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testCountListNull() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 'map', 'map', 'hello'}->count(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testCountOnNull() {
		final IValidationResult validationResult = validate("null->count(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testCountSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 'map', 'map', 'hello'}->count('map')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testCountSetDifferentTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2, 1, 3}->count(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testCountSetNull() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 'map', 'map', 'hello'}->count(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testExistsList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->exists(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsListAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->exists(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 39, 60);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 39,
				60);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsListAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->exists(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 39, 61);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 39,
				61);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsListDifferentTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->exists(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsListDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->exists(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsListNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->exists(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "expression in exists must return a boolean";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 26, 48);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 26, 48);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testExistsListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->exists(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The exists service takes a lambda as parameter: v | v...";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 26, 40);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 26, 40);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testExistsOnNull() {
		final IValidationResult validationResult = validate("null->exists(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->exists(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsSetAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->exists(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 41, 62);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 41,
				62);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsSetAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->exists(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 41, 63);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 41,
				63);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsSetDifferentTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->exists(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsSetDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->exists(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExistsSetNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->exists(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "expression in exists must return a boolean";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 50);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 50);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testExistsSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->exists(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The exists service takes a lambda as parameter: v | v...";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 42);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 42);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testForAllList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->forAll(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllListAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->forAll(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 39, 60);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 39,
				60);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllListAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->forAll(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 39, 61);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 39,
				61);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllListDifferentTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->forAll(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllListDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->forAll(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllListNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->forAll(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "expression in forAll must return a boolean";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 26, 48);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 26, 48);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testForAllListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->forAll(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The forAll service takes a lambda as parameter: v | v...";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 26, 40);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 26, 40);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testForAllOnNull() {
		final IValidationResult validationResult = validate("null->forAll(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->forAll(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllSetAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->forAll(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 41, 62);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 41,
				62);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllSetAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->forAll(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 41, 63);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 41,
				63);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllSetDifferentTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->forAll(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllSetDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->forAll(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testForAllSetNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->forAll(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "expression in forAll must return a boolean";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 50);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 50);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testForAllSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->forAll(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The forAll service takes a lambda as parameter: v | v...";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 42);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 42);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testExcludesAllListList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello'}->excludesAll(Sequence{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllListSet() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello'}->excludesAll(OrderedSet{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllEmptyListList() {
		final IValidationResult validationResult = validate(
				"Sequence{}->excludesAll(Sequence{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllEmptyListSet() {
		final IValidationResult validationResult = validate(
				"Sequence{}->excludesAll(OrderedSet{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllListEmptyList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->excludesAll(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllListEmptySet() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->excludesAll(OrderedSet{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllListListMultipleTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{1, 'hello', 2.0}->excludesAll(Sequence{'world', null})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllListSetMultipleTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{1, 'hello', 2.0}->excludesAll(OrderedSet{'world', null})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->excludesAll(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllOnNullList() {
		final IValidationResult validationResult = validate("null->excludesAll(Sequence{'hello'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllOnNullSet() {
		final IValidationResult validationResult = validate("null->excludesAll(OrderedSet{'hello'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllSetList() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello'}->excludesAll(Sequence{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllSetSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello'}->excludesAll(OrderedSet{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllEmptySetList() {
		final IValidationResult validationResult = validate(
				"OrderedSet{}->excludesAll(Sequence{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllEmptySetSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{}->excludesAll(OrderedSet{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllSetEmptyList() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->excludesAll(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllSetEmptySet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->excludesAll(OrderedSet{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllSetListMultipleTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 'hello', 2.0}->excludesAll(Sequence{'world', null})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllSetSetMultipleTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 'hello', 2.0}->excludesAll(OrderedSet{'world', null})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testExcludesAllSetNull() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->excludesAll(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllListList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello'}->includesAll(Sequence{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllListSet() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello'}->includesAll(OrderedSet{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllEmptyListList() {
		final IValidationResult validationResult = validate(
				"Sequence{}->includesAll(Sequence{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllEmptyListSet() {
		final IValidationResult validationResult = validate(
				"Sequence{}->includesAll(OrderedSet{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllListEmptyList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->includesAll(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllListEmptySet() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->includesAll(OrderedSet{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllListListMultipleTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{1, 'hello', 2.0}->includesAll(Sequence{'world', null})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllListSetMultipleTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{1, 'hello', 2.0}->includesAll(OrderedSet{'world', null})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->includesAll(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllOnNullList() {
		final IValidationResult validationResult = validate("null->includesAll(Sequence{'hello'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllOnNullSet() {
		final IValidationResult validationResult = validate("null->includesAll(OrderedSet{'hello'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllSetList() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello'}->includesAll(Sequence{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllSetSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello'}->includesAll(OrderedSet{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllEmptySetList() {
		final IValidationResult validationResult = validate(
				"OrderedSet{}->includesAll(Sequence{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllEmptySetSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{}->includesAll(OrderedSet{'hello', 'world'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllSetEmptyList() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->includesAll(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllSetEmptySet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->includesAll(OrderedSet{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllSetListMultipleTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 'hello', 2.0}->includesAll(Sequence{'world', null})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllSetSetMultipleTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 'hello', 2.0}->includesAll(OrderedSet{'world', null})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIncludesAllSetNull() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->includesAll(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->isUnique(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueListAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->isUnique(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 41, 62);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 41,
				62);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueListAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->isUnique(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 41, 63);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 41,
				63);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueListDifferentTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->isUnique(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueListDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->isUnique(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueListNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->isUnique(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->isUnique(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "The isUnique service takes a lambda as parameter: v | v...",
				26, 42);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals("The isUnique service takes a lambda as parameter: v | v...", ((NothingType)type)
				.getMessage());
	}

	@Test
	public void testIsUniqueOnNull() {
		final IValidationResult validationResult = validate("null->isUnique(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->isUnique(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueSetAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->isUnique(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 43, 64);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 43,
				64);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueSetAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->isUnique(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 43, 65);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 43,
				65);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueSetDifferentTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->isUnique(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueSetDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->isUnique(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueSetNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->isUnique(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testIsUniqueSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->isUnique(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The isUnique service takes a lambda as parameter: v | v...";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 44);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testOneList() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->one(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneListAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->one(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 36, 57);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 36,
				57);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneListAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->one(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 36, 58);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 36,
				58);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneListDifferentTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->one(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneListDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world', 1, 2.0}->one(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneListNotBooleanLambda() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->one(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "expression in one must return a boolean";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 26, 45);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 26, 45);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testOneListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->one(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The one service takes a lambda as parameter: v | v...";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 26, 37);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 26, 37);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testOneOnNull() {
		final IValidationResult validationResult = validate("null->one(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->one(i | i.size() > 2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneSetAlwaysTrue() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->one(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always true:\nNothing inferred when i (java.lang.String) is not kind of ClassLiteral=java.lang.String";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 38, 59);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 38,
				59);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneSetAlwaysFalse() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->one(i | i.oclIsKindOf(Integer))");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "Always false:\nNothing inferred when i (java.lang.String) is kind of ClassLiteral=java.lang.Integer";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, message, 38, 60);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Lambda)((Call)ast)
				.getArguments().get(1)).getExpression()).get(0), ValidationMessageLevel.INFO, message, 38,
				60);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneSetDifferentTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->one(i | i <> null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneSetDifferentTypesNarrowing() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world', 1, 2.0}->one(i | i.oclIsKindOf(String))");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Boolean.class)), types);
	}

	@Test
	public void testOneSetNotBooleanLambda() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->one(i | i.size())");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "expression in one must return a boolean";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 47);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 47);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testOneSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->one(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "The one service takes a lambda as parameter: v | v...";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 39);

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 39);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testSumListIntegers() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->sum()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Long.class)), types);
	}

	@Test
	public void testSumListTypeMix() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3.0, 4.0}->sum()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Double.class)), types);
	}

	@Test
	public void testSumListNotNumber() {
		final IValidationResult validationResult = validate("Sequence{1, 'potatoes', 2.0}->sum()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "sum can only be used on a collection of numbers.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testSumOnNull() {
		final IValidationResult validationResult = validate("null->sum()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "sum can only be used on a collection of numbers.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 4, 11);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 4, 11);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testSumSetIntegers() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3}->sum()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Long.class)), types);
	}

	@Test
	public void testSumSetTypeMix() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3.0, 4.0}->sum()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Double.class)), types);
	}

	@Test
	public void testSumSetNotNumber() {
		final IValidationResult validationResult = validate("OrderedSet{1, 'potatoes', 2.0}->sum()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "sum can only be used on a collection of numbers.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 30, 37);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 30, 37);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testMinListIntegers() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->min()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Long.class)), types);
	}

	@Test
	public void testMinListTypeMix() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3.0, 4.0}->min()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Double.class)), types);
	}

	@Test
	public void testMinListNotNumber() {
		final IValidationResult validationResult = validate("Sequence{1, 'potatoes', 2.0}->min()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "min can only be used on a collection of numbers.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 28, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testMinOnNull() {
		final IValidationResult validationResult = validate("null->min()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "min can only be used on a collection of numbers.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 4, 11);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 4, 11);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testMinSetIntegers() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3}->min()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Long.class)), types);
	}

	@Test
	public void testMinSetTypeMix() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3.0, 4.0}->min()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Double.class)), types);
	}

	@Test
	public void testMinSetNotNumber() {
		final IValidationResult validationResult = validate("OrderedSet{1, 'potatoes', 2.0}->min()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "min can only be used on a collection of numbers.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 30, 37);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 30, 37);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testMaxListIntegers() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->max()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Long.class)), types);
	}

	@Test
	public void testMaxListTypeMix() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3.0, 4.0}->max()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Double.class)), types);
	}

	@Test
	public void testMaxListNotNumber() {
		final IValidationResult validationResult = validate("Sequence{1, 'potatoes', 2.0}->max()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "max can only be used on a collection of numbers.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 28, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testMaxOnNull() {
		final IValidationResult validationResult = validate("null->max()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "max can only be used on a collection of numbers.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 4, 11);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 4, 11);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testMaxSetIntegers() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3}->max()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Long.class)), types);
	}

	@Test
	public void testMaxSetTypeMix() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3.0, 4.0}->max()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Double.class)), types);
	}

	@Test
	public void testMaxSetNotNumber() {
		final IValidationResult validationResult = validate("OrderedSet{1, 'potatoes', 2.0}->max()");
		final Expression ast = validationResult.getAstResult().getAst();

		String message = "max can only be used on a collection of numbers.";
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, message, 30, 37);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, message, 30, 37);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof NothingType);
		assertEquals(message, ((NothingType)type).getMessage());
	}

	@Test
	public void testIndexOfList() {
		final IValidationResult validationResult = validate("Sequence{'hello'}->indexOf('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testIndexOfEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->indexOf('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testIndexOfListMultipleTypes() {
		final IValidationResult validationResult = validate("Sequence{1, 'hello', 2.0}->indexOf('world')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testIndexOfListNull() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->indexOf(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testIndexOfOnNull() {
		final IValidationResult validationResult = validate("null->indexOf('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testIndexOfSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello'}->indexOf('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testIndexOfSetList() {
		final IValidationResult validationResult = validate("OrderedSet{}->indexOf('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testIndexOfSetMultipleTypes() {
		final IValidationResult validationResult = validate("OrderedSet{1, 'hello', 2.0}->indexOf('world')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testIndexOfSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->indexOf(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(classType(Integer.class)), types);
	}

	@Test
	public void testInsertAtList() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->insertAt(1, ' ')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->insertAt(1, 1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(Integer.class))),
				types);
	}

	@Test
	public void testInsertAtListUnderLowerBound() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->insertAt(-1, 'newString')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtListOverUpperBound() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->insertAt(3, 'newString')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtListLowerBound() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->insertAt(0, 'newString')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtListUpperBound() {
		final IValidationResult validationResult = validate(
				"Sequence{'hello', 'world'}->insertAt(2, 'newString')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtListNull() {
		final IValidationResult validationResult = validate("Sequence{1, 2}->insertAt(1, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testInsertAtOnNull() {
		final IValidationResult validationResult = validate("null->insertAt(0, 'hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->insertAt(1, ' ')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->insertAt(1, 1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testInsertAtSetUnderLowerBound() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->insertAt(-1, 'newString')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtSetOverUpperBound() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->insertAt(3, 'newString')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtSetLowerBound() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->insertAt(0, 'newString')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtSetUpperBound() {
		final IValidationResult validationResult = validate(
				"OrderedSet{'hello', 'world'}->insertAt(2, 'newString')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testInsertAtSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2}->insertAt(1, null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(null))), types);
	}

	@Test
	public void testAppendList() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->append(' ')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testAppendListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->append(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(Integer.class))),
				types);
	}

	@Test
	public void testAppendListNull() {
		final IValidationResult validationResult = validate("Sequence{1, 2}->append(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testAppendOnNull() {
		final IValidationResult validationResult = validate("null->append('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testAppendSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->append(' ')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testAppendSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->append(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testAppendSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2}->append(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(null))), types);
	}

	@Test
	public void testPrependList() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->prepend(' ')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class))), types);
	}

	@Test
	public void testPrependListDifferentTypes() {
		final IValidationResult validationResult = validate("Sequence{'hello', 'world'}->prepend(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(String.class)), sequenceType(classType(Integer.class))),
				types);
	}

	@Test
	public void testPrependListNull() {
		final IValidationResult validationResult = validate("Sequence{1, 2}->prepend(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(null))), types);
	}

	@Test
	public void testPrependOnNull() {
		final IValidationResult validationResult = validate("null->prepend('hello')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testPrependSet() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->prepend(' ')");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class))), types);
	}

	@Test
	public void testPrependSetDifferentTypes() {
		final IValidationResult validationResult = validate("OrderedSet{'hello', 'world'}->prepend(1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(String.class)), setType(classType(Integer.class))), types);
	}

	@Test
	public void testPrependSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2}->prepend(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(null))), types);
	}

	@Test
	public void testIntersectionListList() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->intersection(Sequence{2})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testIntersectionEmptyListEmptyList() {
		final IValidationResult validationResult = validate("Sequence{}->intersection(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n Empty Sequence defined in extension",
				10, 36);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n Empty Sequence defined in extension",
				10, 36);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty Sequence defined in extension\n"
				+ " Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionIntListRealList() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->intersection(Sequence{2.0})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Integer) and Sequence(java.lang.Double)",
				17, 46);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Integer) and Sequence(java.lang.Double)",
				17, 46);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Integer) and Sequence(java.lang.Double)";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionListListIncompatibleTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{1.0, 2.0, 3.0}->intersection(Sequence{'hello'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Double) and Sequence(java.lang.String)",
				23, 56);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Double) and Sequence(java.lang.String)",
				23, 56);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n"
				+ " Nothing left after intersection of Sequence(java.lang.Double) and Sequence(java.lang.String)";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionListListMixedTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{1, 2.0, 3}->intersection(Sequence{'hello', 3})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testIntersectionListSet() {
		final IValidationResult validationResult = validate("Sequence{1, 2, 3}->intersection(OrderedSet{2})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertTrue(validationResult.getMessages().isEmpty());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testIntersectionEmptyListEmptySet() {
		final IValidationResult validationResult = validate("Sequence{}->intersection(OrderedSet{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n Empty OrderedSet defined in extension",
				10, 38);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n Empty OrderedSet defined in extension",
				10, 38);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty Sequence defined in extension\n"
				+ " Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionIntListRealSet() {
		final IValidationResult validationResult = validate(
				"Sequence{1, 2, 3}->intersection(OrderedSet{2.0})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Integer) and Set(java.lang.Double)",
				17, 48);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Integer) and Set(java.lang.Double)",
				17, 48);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Integer) and Set(java.lang.Double)";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionListSetIncompatibleTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{1.0, 2.0, 3.0}->intersection(OrderedSet{'hello'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Double) and Set(java.lang.String)",
				23, 58);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Sequence(java.lang.Double) and Set(java.lang.String)",
				23, 58);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n"
				+ " Nothing left after intersection of Sequence(java.lang.Double) and Set(java.lang.String)";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionListSetMixedTypes() {
		final IValidationResult validationResult = validate(
				"Sequence{1, 2.0, 3}->intersection(OrderedSet{'hello', 3})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class))), types);
	}

	@Test
	public void testIntersectionListNull() {
		final IValidationResult validationResult = validate("Sequence{}->intersection(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n intersection can only be called on collections, but null was used as its argument.",
				10, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n intersection can only be called on collections, but null was used as its argument.",
				10, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty Sequence defined in extension\n"
				+ " intersection can only be called on collections, but null was used as its argument.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionListNullAsList() {
		final IValidationResult validationResult = validate("Sequence{}->intersection(null->asSequence())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				29, 43);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 44);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 44);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				29, 43);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty Sequence defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionListNullAsSet() {
		final IValidationResult validationResult = validate("Sequence{}->intersection(null->asSet())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				29, 38);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 39);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty Sequence defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				10, 39);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				29, 38);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty Sequence defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SequenceType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionNullList() {
		final IValidationResult validationResult = validate("null->intersection(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty Sequence defined in extension",
				4, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty Sequence defined in extension",
				4, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n The Collection was empty due to a null value being wrapped as a Collection.\n"
				+ " Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionNullSet() {
		final IValidationResult validationResult = validate("null->intersection(OrderedSet{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty OrderedSet defined in extension",
				4, 32);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n The Collection was empty due to a null value being wrapped as a Collection.\n Empty OrderedSet defined in extension",
				4, 32);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n The Collection was empty due to a null value being wrapped as a Collection.\n"
				+ " Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionSetSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 2, 3}->intersection(OrderedSet{2})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testIntersectionEmptySetEmptySet() {
		final IValidationResult validationResult = validate("OrderedSet{}->intersection(OrderedSet{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n Empty OrderedSet defined in extension",
				12, 40);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n Empty OrderedSet defined in extension",
				12, 40);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty OrderedSet defined in extension\n"
				+ " Empty OrderedSet defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionIntSetRealSet() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 2, 3}->intersection(OrderedSet{2.0})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Set(java.lang.Integer) and Set(java.lang.Double)",
				19, 50);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Set(java.lang.Integer) and Set(java.lang.Double)",
				19, 50);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Nothing left after intersection of Set(java.lang.Integer) and Set(java.lang.Double)";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionSetSetIncompatibleTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1.0, 2.0, 3.0}->intersection(OrderedSet{'hello'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Set(java.lang.Double) and Set(java.lang.String)",
				25, 60);

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Set(java.lang.Double) and Set(java.lang.String)",
				25, 60);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n"
				+ " Nothing left after intersection of Set(java.lang.Double) and Set(java.lang.String)";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionSetSetMixedTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 2.0, 3}->intersection(OrderedSet{'hello', 3})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testIntersectionSetList() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2, 3}->intersection(Sequence{2})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testIntersectionEmptySetEmptyList() {
		final IValidationResult validationResult = validate("OrderedSet{}->intersection(Sequence{})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n Empty Sequence defined in extension",
				12, 38);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n Empty Sequence defined in extension",
				12, 38);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty OrderedSet defined in extension\n"
				+ " Empty Sequence defined in extension";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionIntSetRealList() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 2, 3}->intersection(Sequence{2.0})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, "Empty collection: Nothing left after intersection:\n"
						+ " Nothing left after intersection of Set(java.lang.Integer) and Sequence(java.lang.Double)",
				19, 48);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Empty collection: Nothing left after intersection:\n"
						+ " Nothing left after intersection of Set(java.lang.Integer) and Sequence(java.lang.Double)",
				19, 48);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Nothing left after intersection of Set(java.lang.Integer) and Sequence(java.lang.Double)";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionSetListIncompatibleTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1.0, 2.0, 3.0}->intersection(Sequence{'hello'})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Set(java.lang.Double) and Sequence(java.lang.String)",
				25, 58);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Nothing left after intersection of Set(java.lang.Double) and Sequence(java.lang.String)",
				25, 58);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n"
				+ " Nothing left after intersection of Set(java.lang.Double) and Sequence(java.lang.String)";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionSetListMixedTypes() {
		final IValidationResult validationResult = validate(
				"OrderedSet{1, 2.0, 3}->intersection(Sequence{'hello', 3})");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class))), types);
	}

	@Test
	public void testIntersectionSetNull() {
		final IValidationResult validationResult = validate("OrderedSet{}->intersection(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n intersection can only be called on collections, but null was used as its argument.",
				12, 32);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n intersection can only be called on collections, but null was used as its argument.",
				12, 32);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty OrderedSet defined in extension\n"
				+ " intersection can only be called on collections, but null was used as its argument.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionSetNullAsList() {
		final IValidationResult validationResult = validate("OrderedSet{}->intersection(null->asSequence())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				31, 45);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 46);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 46);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				31, 45);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty OrderedSet defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testIntersectionSetNullAsSet() {
		final IValidationResult validationResult = validate("OrderedSet{}->intersection(null->asSet())");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				31, 40);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 41);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: Nothing left after intersection:\n Empty OrderedSet defined in extension\n The Collection was empty due to a null value being wrapped as a Collection.",
				12, 41);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				31, 40);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "Nothing left after intersection:\n Empty OrderedSet defined in extension\n"
				+ " The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubOrderedSet() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2.0, 3}->subOrderedSet(2,3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testSubOrderedSetStartUnderLowerBound() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2.0, 3}->subOrderedSet(0,2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testSubOrderedSetStartAboveUpperBound() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2.0, 3}->subOrderedSet(4,2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testSubOrderedSetEndUnderLowerBound() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2.0, 3}->subOrderedSet(2,0)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testSubOrderedSetEndAboveUpperBound() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2.0, 3}->subOrderedSet(2,4)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testSubOrderedSetStartHigherThanEnd() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2.0, 3}->subOrderedSet(3,2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testSubOrderedSetStartEqualsEnd() {
		final IValidationResult validationResult = validate("OrderedSet{1, 2.0, 3}->subOrderedSet(2,2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(setType(classType(Integer.class)), setType(classType(Double.class))), types);
	}

	@Test
	public void testSubOrderedSetOnNull() {
		final IValidationResult validationResult = validate("null->subOrderedSet(1,1)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 24);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Empty collection: The Collection was empty due to a null value being wrapped as a Collection.",
				4, 24);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		String message = "The Collection was empty due to a null value being wrapped as a Collection.";
		assertEquals(1, types.size());
		IType type = types.iterator().next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof NothingType);
		assertEquals(message, ((NothingType)((SetType)type).getCollectionType()).getMessage());
	}

	@Test
	public void testSubSequence() {
		final IValidationResult validationResult = validate("Sequence{1, 2.0, 3}->subSequence(2,3)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	@Test
	public void testSubSequenceStartUnderLowerBound() {
		final IValidationResult validationResult = validate("Sequence{1, 2.0, 3}->subSequence(0,2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	@Test
	public void testSubSequenceStartAboveUpperBound() {
		final IValidationResult validationResult = validate("Sequence{1, 2.0, 3}->subSequence(4,2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	@Test
	public void testSubSequenceEndUnderLowerBound() {
		final IValidationResult validationResult = validate("Sequence{1, 2.0, 3}->subSequence(2,0)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	@Test
	public void testSubSequenceEndAboveUpperBound() {
		final IValidationResult validationResult = validate("Sequence{1, 2.0, 3}->subSequence(2,4)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	@Test
	public void testSubSequenceStartHigherThanEnd() {
		final IValidationResult validationResult = validate("Sequence{1, 2.0, 3}->subSequence(3,2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	@Test
	public void testSubSequenceStartEqualsEnd() {
		final IValidationResult validationResult = validate("Sequence{1, 2.0, 3}->subSequence(2,2)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(2)).size());

		Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(newSet(sequenceType(classType(Integer.class)), sequenceType(classType(Double.class))),
				types);
	}

	private static <T> Set<T> newSet(@SuppressWarnings("unchecked") T... elements) {
		Set<T> set = new LinkedHashSet<T>();
		set.addAll(Arrays.asList(elements));
		return set;
	}

	private static class VariableBuilder {
		private Map<String, Set<IType>> variables;

		public VariableBuilder() {
			variables = new LinkedHashMap<String, Set<IType>>();
		}

		public VariableBuilder addVar(String name, IType... types) {
			variables.put(name, newSet(types));
			return this;
		}

		public Map<String, Set<IType>> build() {
			return variables;
		}
	}
}
