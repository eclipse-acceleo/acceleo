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
import java.util.Set;

import org.eclipse.acceleo.query.parser.tests.ValidationTest;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnyServicesAstValidationTest extends AbstractServicesValidationTest {

	@Test
	public void testAddNullString() {
		final IValidationResult validationResult = validate("null + 'string'");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testAddStringNull() {
		final IValidationResult validationResult = validate("'string' + null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testAddNothingString() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing + 'string'", variables.build());

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
	public void testAddStringNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("'string' + nothing", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 11, 18);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddObjectString() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object + 'string'", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testAddStringObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("'string' + object", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testDiffersNullNull() {
		final IValidationResult validationResult = validate("null <> null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testDiffersNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null <> nothing", variables.build());

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
	public void testDiffersNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing <> null", variables.build());

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
	public void testDiffersNullObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("null <> object", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testDiffersObjectNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object <> null", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testDiffersObjectObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object <> object", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testEqualsNullNull() {
		final IValidationResult validationResult = validate("null = null");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testEqualsNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null = nothing", variables.build());

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
	public void testEqualsNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing = null", variables.build());

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
	public void testEqualsNullObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("null = object", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testEqualsObjectNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object = null", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testEqualsObjectObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object = object", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testOclAsTypeNullNull() {
		final IValidationResult validationResult = validate("null.oclAsType(null)");

		final String expectedMessage = "Nothing will be left after calling oclAsType:\n"
				+ "null is not compatible with type null";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 20);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testOclAsTypeNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclAsType(null)", variables.build());

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
	public void testOclAsTypeNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null.oclAsType(nothing)", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 15, 22);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclAsTypeNothingNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclAsType(nothing)", variables.build());

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.ERROR, "Nothing", 18, 25);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclAsTypeSameType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls",
				eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclAsType(ecore::EClass)", variables
				.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(eClassifierType(EcorePackage.eINSTANCE.getEClass())), types);
	}

	@Test
	public void testOclAsTypeNotCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls",
				eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclAsType(ecore::EPackage)", variables
				.build());

		final String expectedMessage = "Nothing will be left after calling oclAsType:\n"
				+ "EClassifier=EClass is not compatible with type EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 31);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testOclAsTypeMayBeCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier",
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()));
		final IValidationResult validationResult = validate("eClasssifier.oclAsType(ecore::EClass)",
				variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(eClassifierType(EcorePackage.eINSTANCE.getEClass())), types);
	}

	@Test
	public void testOclIsKindOfNullNull() {
		final IValidationResult validationResult = validate("null.oclIsKindOf(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// ClassType(Boolean) because ecore is not registered
		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclIsKindOf(null)", variables.build());

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO, "Always false:\n"
						+ "Nothing inferred when nothing (Nothing(Nothing)) is kind of null", 0, 25);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsKindOfNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null.oclIsKindOf(nothing)", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 17, 24);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsKindOfNothingNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclIsKindOf(nothing)", variables.build());

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.ERROR, "Nothing", 20, 27);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsKindOfSameType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls",
				eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsKindOf(ecore::EClass)", variables
				.build());

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when eCls (EClassifier=EClass) is not kind of EClassifierLiteral=EClass";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfNotCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls",
				eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsKindOf(ecore::EPackage)", variables
				.build());

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when eCls (EClassifier=EClass) is kind of EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfMayBeCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier",
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()));
		final IValidationResult validationResult = validate("eClasssifier.oclIsKindOf(ecore::EClass)",
				variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfNullNull() {
		final IValidationResult validationResult = validate("null.oclIsTypeOf(null)");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// ClassType(Boolean) because ecore is not registered
		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclIsTypeOf(null)", variables.build());

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO, "Always false:\n"
						+ "Nothing inferred when nothing (Nothing(Nothing)) is type of null", 0, 25);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsTypeOfNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null.oclIsTypeOf(nothing)", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 17, 24);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsTypeOfNothingNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclIsTypeOf(nothing)", variables.build());

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.ERROR, "Nothing", 20, 27);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsTypeOfSameType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls",
				eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsTypeOf(ecore::EClass)", variables
				.build());

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when eCls (EClassifier=EClass) is not type of EClassifierLiteral=EClass";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfNotCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls",
				eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsTypeOf(ecore::EPackage)", variables
				.build());

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when eCls (EClassifier=EClass) is type of EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfMayBeCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier",
				eClassifierType(EcorePackage.eINSTANCE.getEClassifier()));
		final IValidationResult validationResult = validate("eClasssifier.oclIsTypeOf(ecore::EClass)",
				variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(Boolean.class)), types);
	}

	@Test
	public void testToStringNull() {
		final IValidationResult validationResult = validate("null.toString()");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testToStringNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.toString()", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

	}

	@Test
	public void testToStringObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object.trace()", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testTraceNull() {
		final IValidationResult validationResult = validate("null.trace()");

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

	@Test
	public void testTraceNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.trace()", variables.build());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

	}

	@Test
	public void testTraceObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object.trace()", variables.build());

		assertTrue(validationResult.getMessages().isEmpty());

		final AstResult ast = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(ast.getAst());

		assertEquals(ImmutableSet.of(classType(String.class)), types);
	}

}
