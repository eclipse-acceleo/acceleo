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
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.tests.ValidationTest;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ComparableServicesAstValidationTest extends AbstractServicesValidationTest {

	@Test
	public void testGreaterThanNullNull() {
		final IValidationResult validationResult = validate("null > null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing > null", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testGreaterThanNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null > nothing", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 7, 14);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testGreaterThanNullComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("null > comparable", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanComparableNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable > null", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanComparableComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable > comparable", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanEqualNullNull() {
		final IValidationResult validationResult = validate("null >= null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanEqualNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing >= null", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testGreaterThanEqualNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null >= nothing", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 8, 15);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testGreaterThanEqualNullComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("null >= comparable", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanEqualComparableNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable >= null", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanEqualComparableComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable >= comparable", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanNullNull() {
		final IValidationResult validationResult = validate("null < null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing < null", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testLessThanNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null < nothing", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 7, 14);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testLessThanNullComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("null < comparable", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanComparableNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable < null", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanComparableComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable < comparable", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanEqualNullNull() {
		final IValidationResult validationResult = validate("null <= null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanEqualNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing <= null", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testLessThanEqualNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null <= nothing", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 8, 15);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testLessThanEqualNullComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("null <= comparable", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanEqualComparableNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable <= null", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanEqualComparableComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable <= comparable", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

}
