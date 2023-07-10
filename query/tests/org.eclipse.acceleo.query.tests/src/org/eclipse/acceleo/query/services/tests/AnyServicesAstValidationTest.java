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
import java.util.Iterator;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.tests.ValidationTest;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnyServicesAstValidationTest extends AbstractServicesValidationTest {

	@Test
	public void testAddNullString() {
		final IValidationResult validationResult = validate("null + 'string'");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

	@Test
	public void testAddStringNull() {
		final IValidationResult validationResult = validate("'string' + null");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

	@Test
	public void testAddNothingString() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing + 'string'", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddStringNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("'string' + nothing", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 11, 18);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 11, 18);

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testAddObjectString() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object + 'string'", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

	@Test
	public void testAddStringObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("'string' + object", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

	@Test
	public void testDiffersNullNull() {
		final IValidationResult validationResult = validate("null <> null");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testDiffersNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null <> nothing", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 8, 15);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 8, 15);

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testDiffersNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing <> null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testDiffersNullObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("null <> object", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testDiffersObjectNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object <> null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testDiffersObjectObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object <> object", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testEqualsNullNull() {
		final IValidationResult validationResult = validate("null = null");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testEqualsNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null = nothing", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 7, 14);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 7, 14);

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testEqualsNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing = null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testEqualsNullObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("null = object", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testEqualsObjectNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object = null", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testEqualsObjectObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object = object", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclAsTypeNullNull() {
		final IValidationResult validationResult = validate("null.oclAsType(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Nothing will be left after calling oclAsType:\n"
				+ "null is not compatible with type null";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 20);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 20);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

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
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclAsTypeNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null.oclAsType(nothing)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 15, 22);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 15, 22);

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclAsTypeNothingNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclAsType(nothing)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.ERROR, "Nothing", 18, 25);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 18, 25);

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclAsTypeSameType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclAsType(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(eClassifierType(EcorePackage.eINSTANCE.getEClass())), types);
	}

	@Test
	public void testOclAsTypeSameTypeClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classType(String.class));
		final IValidationResult validationResult = validate("var.oclAsType(String)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

	@Test
	public void testOclAsTypeSameTypeClassLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classLiteralType(String.class));
		final IValidationResult validationResult = validate("var.oclAsType(String)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

	@Test
	public void testOclAsTypeSameTypeEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclAsType(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(eClassifierType(EcorePackage.eINSTANCE.getEClass())), types);
	}

	@Test
	public void testOclAsTypeNotCompatibleTypeClassLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classLiteralType(String.class));
		final IValidationResult validationResult = validate("var.oclAsType(Integer)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Nothing will be left after calling oclAsType:\n"
				+ "ClassLiteral=java.lang.String is not compatible with type ClassLiteral=java.lang.Integer";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 3, 22);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 3, 22);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testOclAsTypeNotCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclAsType(ecore::EPackage)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Nothing will be left after calling oclAsType:\n"
				+ "EClassifier=EClass is not compatible with type EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testOclAsTypeNotCompatibleTypeClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", classType(EClass.class));
		final IValidationResult validationResult = validate("eCls.oclAsType(ecore::EPackage)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Nothing will be left after calling oclAsType:\n"
				+ "org.eclipse.emf.ecore.EClass is not compatible with type EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testOclAsTypeNotCompatibleTypeECLassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclAsType(ecore::EPackage)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Nothing will be left after calling oclAsType:\n"
				+ "EClassifierLiteral=EClass is not compatible with type EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.ERROR, expectedMessage, 4, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(1, types.size());
		final Iterator<IType> it = types.iterator();
		final IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals(expectedMessage, ((NothingType)type).getMessage());
	}

	@Test
	public void testOclAsTypeMayBeCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier", eClassifierType(
				EcorePackage.eINSTANCE.getEClassifier()));
		final IValidationResult validationResult = validate("eClasssifier.oclAsType(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(eClassifierType(EcorePackage.eINSTANCE.getEClass())), types);
	}

	@Test
	public void testOclAsTypeMayBeCompatibleTypeClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier", classType(
				EClassifier.class));
		final IValidationResult validationResult = validate("eClasssifier.oclAsType(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(eClassifierType(EcorePackage.eINSTANCE.getEClass())), types);
	}

	@Test
	public void testOclAsTypeMayBeCompatibleTypeECLassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClassifier()));
		final IValidationResult validationResult = validate("eClasssifier.oclAsType(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(eClassifierType(EcorePackage.eINSTANCE.getEClass())), types);
	}

	@Test
	public void testOclIsKindOfNullNull() {
		final IValidationResult validationResult = validate("null.oclIsKindOf(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// ClassType(Boolean) because ecore is not registered
		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclIsKindOf(null)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO, "Always false:\n"
						+ "Nothing inferred when nothing (Nothing(Nothing)) is kind of null", 0, 25);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Always false:\n"
						+ "Nothing inferred when nothing (Nothing(Nothing)) is kind of null", 0, 25);
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsKindOfNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null.oclIsKindOf(nothing)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 17, 24);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 17, 24);

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsKindOfNothingNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclIsKindOf(nothing)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.ERROR, "Nothing", 20, 27);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 20, 27);

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsKindOfSameTypeClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classType(String.class));
		final IValidationResult validationResult = validate("var.oclIsKindOf(String)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when var (java.lang.String) is not kind of ClassLiteral=java.lang.String";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 23);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 23);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfSameTypeClassLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classLiteralType(String.class));
		final IValidationResult validationResult = validate("var.oclIsKindOf(String)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when var (ClassLiteral=java.lang.String) is not kind of ClassLiteral=java.lang.String";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 23);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 23);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfSameType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsKindOf(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when eCls (EClassifier=EClass) is not kind of EClassifierLiteral=EClass";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfSameTypeEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsKindOf(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when eCls (EClassifierLiteral=EClass) is not kind of EClassifierLiteral=EClass";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfNotCompatibleTypeClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classType(String.class));
		final IValidationResult validationResult = validate("var.oclIsKindOf(Integer)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when var (java.lang.String) is kind of ClassLiteral=java.lang.Integer";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 24);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 24);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfNotCompatibleTypeClassLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classLiteralType(String.class));
		final IValidationResult validationResult = validate("var.oclIsKindOf(Integer)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when var (ClassLiteral=java.lang.String) is kind of ClassLiteral=java.lang.Integer";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 24);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 24);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfNotCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsKindOf(ecore::EPackage)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when eCls (EClassifier=EClass) is kind of EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfNotCompatibleTypeClassClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", classType(EClass.class));
		final IValidationResult validationResult = validate("eCls.oclIsKindOf(ecore::EPackage)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when eCls (org.eclipse.emf.ecore.EClass) is kind of EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfNotCompatibleTypeEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsKindOf(ecore::EPackage)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when eCls (EClassifierLiteral=EClass) is kind of EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfMayBeCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier", eClassifierType(
				EcorePackage.eINSTANCE.getEClassifier()));
		final IValidationResult validationResult = validate("eClasssifier.oclIsKindOf(ecore::EClass)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfMayBeCompatibleTypeClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier", classType(
				EClassifier.class));
		final IValidationResult validationResult = validate("eClasssifier.oclIsKindOf(ecore::EClass)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsKindOfMayBeCompatibleTypeEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClassifier()));
		final IValidationResult validationResult = validate("eClasssifier.oclIsKindOf(ecore::EClass)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfNullNull() {
		final IValidationResult validationResult = validate("null.oclIsTypeOf(null)");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// ClassType(Boolean) because ecore is not registered
		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfNothingNull() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclIsTypeOf(null)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO, "Always false:\n"
						+ "Nothing inferred when nothing (Nothing(Nothing)) is type of null", 0, 25);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, "Always false:\n"
						+ "Nothing inferred when nothing (Nothing(Nothing)) is type of null", 0, 25);
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsTypeOfNullNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("null.oclIsTypeOf(nothing)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 17, 24);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 17, 24);

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsTypeOfNothingNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.oclIsTypeOf(nothing)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.ERROR, "Nothing", 20, 27);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(1))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 20, 27);

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		// the NothingType is removed from possible types then no lookup is done because of empty combination
		// set
		assertTrue(types.isEmpty());
	}

	@Test
	public void testOclIsTypeOfSameTypeClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classType(String.class));
		final IValidationResult validationResult = validate("var.oclIsTypeOf(String)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when var (java.lang.String) is not type of ClassLiteral=java.lang.String";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 23);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 23);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfSameTypeClassLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classLiteralType(String.class));
		final IValidationResult validationResult = validate("var.oclIsTypeOf(String)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when var (ClassLiteral=java.lang.String) is not type of ClassLiteral=java.lang.String";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 23);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 23);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfSameType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsTypeOf(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when eCls (EClassifier=EClass) is not type of EClassifierLiteral=EClass";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfSameTypeClassClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", classType(EClass.class));
		final IValidationResult validationResult = validate("eCls.oclIsTypeOf(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when eCls (org.eclipse.emf.ecore.EClass) is not type of EClassifierLiteral=EClass";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfSameTypeEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsTypeOf(ecore::EClass)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always true:\n"
				+ "Nothing inferred when eCls (EClassifierLiteral=EClass) is not type of EClassifierLiteral=EClass";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfNotCompatibleTypeClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classType(String.class));
		final IValidationResult validationResult = validate("var.oclIsTypeOf(Integer)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when var (java.lang.String) is type of ClassLiteral=java.lang.Integer";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 24);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 24);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfNotCompatibleTypeClassLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("var", classLiteralType(String.class));
		final IValidationResult validationResult = validate("var.oclIsTypeOf(Integer)", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when var (ClassLiteral=java.lang.String) is type of ClassLiteral=java.lang.Integer";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 24);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 24);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfNotCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsTypeOf(ecore::EPackage)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when eCls (EClassifier=EClass) is type of EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfNotCompatibleTypeEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eCls", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClass()));
		final IValidationResult validationResult = validate("eCls.oclIsTypeOf(ecore::EPackage)", variables
				.build());
		final Expression ast = validationResult.getAstResult().getAst();

		final String expectedMessage = "Always false:\n"
				+ "Nothing inferred when eCls (EClassifierLiteral=EClass) is type of EClassifierLiteral=EPackage";

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO, expectedMessage, 0, 33);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfMayBeCompatibleType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier", eClassifierType(
				EcorePackage.eINSTANCE.getEClassifier()));
		final IValidationResult validationResult = validate("eClasssifier.oclIsTypeOf(ecore::EClass)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfMayBeCompatibleTypeClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier", classType(
				EClassifier.class));
		final IValidationResult validationResult = validate("eClasssifier.oclIsTypeOf(ecore::EClass)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testOclIsTypeOfMayBeCompatibleTypeEClassifierLiteral() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final VariableBuilder variables = new VariableBuilder().addVar("eClasssifier", eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEClassifier()));
		final IValidationResult validationResult = validate("eClasssifier.oclIsTypeOf(ecore::EClass)",
				variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(Boolean.class)), types);
	}

	@Test
	public void testToStringNull() {
		final IValidationResult validationResult = validate("null.toString()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

	@Test
	public void testToStringNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.toString()", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
	}

	@Test
	public void testToStringObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object.trace()", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

	@Test
	public void testTraceNull() {
		final IValidationResult validationResult = validate("null.trace()");
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

	@Test
	public void testTraceNothing() {
		final VariableBuilder variables = new VariableBuilder().addVar("nothing", nothingType("Nothing"));
		final IValidationResult validationResult = validate("nothing.trace()", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 7);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 7);
	}

	@Test
	public void testTraceObject() {
		final VariableBuilder variables = new VariableBuilder().addVar("object", classType(Object.class));
		final IValidationResult validationResult = validate("object.trace()", variables.build());
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());

		final AstResult astResult = validationResult.getAstResult();
		final Set<IType> types = validationResult.getPossibleTypes(astResult.getAst());

		assertEquals(Collections.singleton(classType(String.class)), types);
	}

}
