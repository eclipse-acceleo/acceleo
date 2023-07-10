/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
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

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Expression;
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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing > null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testGreaterThanNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null > nothing", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 7, 14);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 7, 14);

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testGreaterThanNullComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("null > comparable", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanComparableNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable > null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanComparableComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable > comparable", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanEqualNullNull() {
		final IValidationResult validationResult = validate("null >= null");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanEqualNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing >= null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testGreaterThanEqualNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null >= nothing", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 8, 15);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 8, 15);

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testGreaterThanEqualNullComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("null >= comparable", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanEqualComparableNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable >= null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testGreaterThanEqualComparableComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable >= comparable", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanNullNull() {
		final IValidationResult validationResult = validate("null < null");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing < null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testLessThanNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null < nothing", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 7, 14);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 7, 14);

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testLessThanNullComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("null < comparable", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanComparableNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable < null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanComparableComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable < comparable", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanEqualNullNull() {
		final IValidationResult validationResult = validate("null <= null");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanEqualNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing <= null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testLessThanEqualNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null <= nothing", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 8, 15);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 8, 15);

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testLessThanEqualNullComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("null <= comparable", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanEqualComparableNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable <= null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testLessThanEqualComparableComparable() {
		final VariableBuilder variables = new VariableBuilder().addVar("comparable", classType(
				Comparable.class));
		final IValidationResult validationResult = validate("comparable <= comparable", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final Set<IType> types = validationResult.getPossibleTypes(ast);

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

}
