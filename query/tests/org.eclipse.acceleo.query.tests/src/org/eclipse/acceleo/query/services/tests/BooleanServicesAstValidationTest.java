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
package org.eclipse.acceleo.query.services.tests;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.tests.ValidationTest;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooleanServicesAstValidationTest extends AbstractServicesValidationTest {

	@Test
	public void testOrNullNull() {
		final IValidationResult validationResult = validate("null or null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOrBooleanNull() {
		final IValidationResult validationResult = validate("false or null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOrNullBoolean() {
		final IValidationResult validationResult = validate("null or false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOrIntegerInteger() {
		final IValidationResult validationResult = validate("1 or 1");

		final String expectedMessage = "Couldn't find the 'or(java.lang.Integer,java.lang.Integer)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 6);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testOrBooleanInteger() {
		final IValidationResult validationResult = validate("false or 1");

		final String expectedMessage = "Couldn't find the 'or(java.lang.Boolean,java.lang.Integer)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 10);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testOrIntegerBoolean() {
		final IValidationResult validationResult = validate("1 or false");

		final String expectedMessage = "Couldn't find the 'or(java.lang.Integer,java.lang.Boolean)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 10);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testOrFalseFalse() {
		final IValidationResult validationResult = validate("false or false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOrFalseTrue() {
		final IValidationResult validationResult = validate("false or true");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOrTrueFalse() {
		final IValidationResult validationResult = validate("true or false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOrTrueTrue() {
		final IValidationResult validationResult = validate("true or true");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testAndNullNull() {
		final IValidationResult validationResult = validate("null and null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testAndBooleanNull() {
		final IValidationResult validationResult = validate("false and null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testAndNullBoolean() {
		final IValidationResult validationResult = validate("null and false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testAndIntegerInteger() {
		final IValidationResult validationResult = validate("1 and 1");

		final String expectedMessage = "Couldn't find the 'and(java.lang.Integer,java.lang.Integer)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 7);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testAndBooleanInteger() {
		final IValidationResult validationResult = validate("false and 1");

		final String expectedMessage = "Couldn't find the 'and(java.lang.Boolean,java.lang.Integer)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 11);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testAndIntegerBoolean() {
		final IValidationResult validationResult = validate("1 and false");

		final String expectedMessage = "Couldn't find the 'and(java.lang.Integer,java.lang.Boolean)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 11);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testAndFalseFalse() {
		final IValidationResult validationResult = validate("false and false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testAndFalseTrue() {
		final IValidationResult validationResult = validate("false and true");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testAndTrueFalse() {
		final IValidationResult validationResult = validate("true and false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testAndTrueTrue() {
		final IValidationResult validationResult = validate("true and true");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testNotNull() {
		final IValidationResult validationResult = validate("not null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testNotInteger() {
		final IValidationResult validationResult = validate("not 1");

		final String expectedMessage = "Couldn't find the 'not(java.lang.Integer)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 5);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testNotFalse() {
		final IValidationResult validationResult = validate("not false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testNotTrue() {
		final IValidationResult validationResult = validate("not true");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testImpliesNullNull() {
		final IValidationResult validationResult = validate("null implies null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testImpliesBooleanNull() {
		final IValidationResult validationResult = validate("false implies null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testImpliesNullBoolean() {
		final IValidationResult validationResult = validate("null implies false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testImpliesIntegerInteger() {
		final IValidationResult validationResult = validate("1 implies 1");

		final String expectedMessage = "Couldn't find the 'implies(java.lang.Integer,java.lang.Integer)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 11);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testImpliesBooleanInteger() {
		final IValidationResult validationResult = validate("false implies 1");

		final String expectedMessage = "Couldn't find the 'implies(java.lang.Boolean,java.lang.Integer)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 15);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testImpliesIntegerBoolean() {
		final IValidationResult validationResult = validate("1 implies false");

		final String expectedMessage = "Couldn't find the 'implies(java.lang.Integer,java.lang.Boolean)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 15);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testImpliesFalseFalse() {
		final IValidationResult validationResult = validate("false implies false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testImpliesFalseTrue() {
		final IValidationResult validationResult = validate("false implies true");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testImpliesTrueFalse() {
		final IValidationResult validationResult = validate("true implies false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testImpliesTrueTrue() {
		final IValidationResult validationResult = validate("true implies true");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testXorNullNull() {
		final IValidationResult validationResult = validate("null xor null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testXorBooleanNull() {
		final IValidationResult validationResult = validate("false xor null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testXorNullBoolean() {
		final IValidationResult validationResult = validate("null xor false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testXorIntegerInteger() {
		final IValidationResult validationResult = validate("1 xor 1");

		final String expectedMessage = "Couldn't find the 'xor(java.lang.Integer,java.lang.Integer)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 7);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testXorBooleanInteger() {
		final IValidationResult validationResult = validate("false xor 1");

		final String expectedMessage = "Couldn't find the 'xor(java.lang.Boolean,java.lang.Integer)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 11);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testXorIntegerBoolean() {
		final IValidationResult validationResult = validate("1 xor false");

		final String expectedMessage = "Couldn't find the 'xor(java.lang.Integer,java.lang.Boolean)' service";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 0, 11);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testXorFalseFalse() {
		final IValidationResult validationResult = validate("false xor false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testXorFalseTrue() {
		final IValidationResult validationResult = validate("false xor true");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testXorTrueFalse() {
		final IValidationResult validationResult = validate("true xor false");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testXorTrueTrue() {
		final IValidationResult validationResult = validate("true xor true");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

}
