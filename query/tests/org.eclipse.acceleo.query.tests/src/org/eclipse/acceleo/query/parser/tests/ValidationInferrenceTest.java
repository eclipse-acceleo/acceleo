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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.QueryValidationEngine;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ValidationInferrenceTest {

	final EPackage ePackage;

	final EClass o;

	final EClass a;

	final EClass b;

	final EClass c;

	final EClass x;

	final EClass y;

	final EClass z;

	QueryValidationEngine engine;

	IQueryEnvironment queryEnvironment;

	/**
	 * Variable types.
	 */
	Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();

	public ValidationInferrenceTest() {
		// O
		//
		// A X
		// ^ ^
		// B
		// ^ ^
		// C Y
		// ^ ^
		// Z
		ePackage = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePackage.setName("test");
		ePackage.setNsURI("test");
		ePackage.setNsPrefix("test");

		EDataType bool = EcorePackage.eINSTANCE.getEcoreFactory().createEDataType();
		bool.setName("bool");
		bool.setInstanceClassName(Boolean.class.getCanonicalName());
		ePackage.getEClassifiers().add(bool);
		bool.setInstanceClass(Boolean.class);
		o = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		o.setName("O");
		ePackage.getEClassifiers().add(o);
		a = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		a.setName("A");
		ePackage.getEClassifiers().add(a);
		b = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		b.setName("B");
		b.getESuperTypes().add(a);
		ePackage.getEClassifiers().add(b);
		c = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		c.setName("C");
		c.getESuperTypes().add(b);
		ePackage.getEClassifiers().add(c);

		x = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		x.setName("X");
		ePackage.getEClassifiers().add(x);
		b.getESuperTypes().add(x);
		y = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		y.setName("Y");
		y.getESuperTypes().add(b);
		ePackage.getEClassifiers().add(y);
		z = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		z.setName("Z");
		z.getESuperTypes().add(c);
		z.getESuperTypes().add(y);
		ePackage.getEClassifiers().add(z);
	}

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(ePackage);
		engine = new QueryValidationEngine(queryEnvironment);

		variableTypes.clear();
		final Set<IType> varATypes = new LinkedHashSet<IType>();
		varATypes.add(new EClassifierType(queryEnvironment, a));
		variableTypes.put("varA", varATypes);
		final Set<IType> varBTypes = new LinkedHashSet<IType>();
		varBTypes.add(new EClassifierType(queryEnvironment, b));
		variableTypes.put("varB", varBTypes);
		final Set<IType> varCTypes = new LinkedHashSet<IType>();
		varCTypes.add(new EClassifierType(queryEnvironment, c));
		variableTypes.put("varC", varCTypes);
		final Set<IType> varNothing = new LinkedHashSet<IType>();
		varNothing.add(new NothingType("Nothing"));
		variableTypes.put("varNothing", varNothing);
	}

	@Test
	public void oclIsKindOf_varNothing_B() {
		String varName = "varNothing";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(test::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(inferredWhenTrue, inferredWhenFalse);
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 10);
	}

	@Test
	public void oclIsKindOf_varA_B() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(test::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertDisjointTypes(inferredWhenTrue, inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());

		assertEquals(1, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(a, ((EClassifierType)type).getType());
	}

	@Test
	public void oclIsKindOf_varB_B() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(test::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNull(inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest
				.assertValidationMessage(
						validationResult.getMessages().get(0),
						ValidationMessageLevel.INFO,
						"Always true:\nNothing inferred when varB (EClassifier=B) is not kind of EClassifierLiteral=B",
						0, 25);
	}

	@Test
	public void oclIsKindOf_varC_B() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(test::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNull(inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest
				.assertValidationMessage(
						validationResult.getMessages().get(0),
						ValidationMessageLevel.INFO,
						"Always true:\nNothing inferred when varC (EClassifier=C) is not kind of EClassifierLiteral=B",
						0, 25);
	}

	@Test
	public void oclIsKindOf_varC_O() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(test::O)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is kind of EClassifierLiteral=O",
				0, 25);
		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
	}

	@Test
	public void oclIsTypeOf_varNothing_B() {
		String varName = "varNothing";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(test::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(inferredWhenTrue, inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals("Nothing", ((NothingType)type).getMessage());
	}

	@Test
	public void oclIsTypeOf_varA_B() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(test::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varA (EClassifier=A) is type of EClassifierLiteral=B",
				0, 25);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(a, ((EClassifierType)type).getType());
	}

	@Test
	public void oclIsTypeOf_varB_B() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(test::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNull(inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest
				.assertValidationMessage(
						validationResult.getMessages().get(0),
						ValidationMessageLevel.INFO,
						"Always true:\nNothing inferred when varB (EClassifier=B) is not type of EClassifierLiteral=B",
						0, 25);
	}

	@Test
	public void oclIsTypeOf_varC_B() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(test::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=B",
				0, 25);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
	}

	@Test
	public void oclIsTypeOf_varC_O() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(test::O)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=O",
				0, 25);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
	}

	@Test
	public void not_oclIsKindOf_varNothing_B() {
		String varName = "varNothing";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsKindOf(test::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(inferredWhenTrue, inferredWhenFalse);
		assertEquals(1, validationResult.getMessages().size());
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 4, 14);
	}

	@Test
	public void not_oclIsKindOf_varA_B() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsKindOf(test::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertDisjointTypes(inferredWhenTrue, inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(a, ((EClassifierType)type).getType());

		assertEquals(1, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());
	}

	@Test
	public void not_oclIsKindOf_varB_B() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsKindOf(test::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest
				.assertValidationMessage(
						validationResult.getMessages().get(0),
						ValidationMessageLevel.INFO,
						"Always true:\nNothing inferred when varB (EClassifier=B) is not kind of EClassifierLiteral=B",
						4, 29);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());
	}

	@Test
	public void not_oclIsKindOf_varC_B() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsKindOf(test::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest
				.assertValidationMessage(
						validationResult.getMessages().get(0),
						ValidationMessageLevel.INFO,
						"Always true:\nNothing inferred when varC (EClassifier=C) is not kind of EClassifierLiteral=B",
						4, 29);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
	}

	@Test
	public void not_oclIsKindOf_varC_O() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsKindOf(test::O)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNull(inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is kind of EClassifierLiteral=O",
				4, 29);
	}

	@Test
	public void not_oclIsTypeOf_varNothing_B() {
		String varName = "varNothing";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsTypeOf(test::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(inferredWhenTrue, inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals("Nothing", ((NothingType)type).getMessage());
	}

	@Test
	public void not_oclIsTypeOf_varA_B() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsTypeOf(test::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNull(inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(a, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varA (EClassifier=A) is type of EClassifierLiteral=B",
				4, 29);
	}

	@Test
	public void not_oclIsTypeOf_varB_B() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsTypeOf(test::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest
				.assertValidationMessage(
						validationResult.getMessages().get(0),
						ValidationMessageLevel.INFO,
						"Always true:\nNothing inferred when varB (EClassifier=B) is not type of EClassifierLiteral=B",
						4, 29);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());
	}

	@Test
	public void not_oclIsTypeOf_varC_B() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsTypeOf(test::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNull(inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=B",
				4, 29);
	}

	@Test
	public void not_oclIsTypeOf_varC_O() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(
				"not " + varName + ".oclIsTypeOf(test::O)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNull(inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=O",
				4, 29);
	}

	@Test
	public void or() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(test::C) or "
				+ varName + ".oclIsKindOf(test::Y)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertDisjointTypes(inferredWhenTrue, inferredWhenFalse);
		assertEquals(2, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(y, ((EClassifierType)type).getType());

		assertEquals(1, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());
	}

	@Test
	public void xor() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(test::C) xor "
				+ varName + ".oclIsKindOf(test::Y)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertDisjointTypes(inferredWhenTrue, inferredWhenFalse);
		assertEquals(2, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(y, ((EClassifierType)type).getType());

		assertEquals(1, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());
	}

	@Test
	public void and() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(test::C) and "
				+ varName + ".oclIsKindOf(test::Y)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);
		assertDisjointTypes(inferredWhenTrue, inferredWhenFalse);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(z, ((EClassifierType)type).getType());

		assertEquals(1, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());
	}

	@Test
	public void conditionnalTrueBranch() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate("if " + varName
				+ ".oclIsKindOf(test::C) then " + varName + " else " + varName + " endif", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertTrue(ast instanceof Conditional);
		final Conditional conditional = (Conditional)ast;
		final Set<IType> types = validationResult.getPossibleTypes(conditional.getTrueBranch());
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
	}

	@Test
	public void conditionnalFalseBranch() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate("if not " + varName
				+ ".oclIsKindOf(test::C) then " + varName + " else " + varName + " endif", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertTrue(ast instanceof Conditional);
		final Conditional conditional = (Conditional)ast;
		final Set<IType> types = validationResult.getPossibleTypes(conditional.getFalseBranch());
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
	}

	@Test
	public void any() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName
				+ "->any(i | i.oclIsKindOf(test::C))", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> types = validationResult.getPossibleTypes(ast);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
	}

	@Test
	public void select() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName
				+ "->select(i | i.oclIsKindOf(test::C))", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> types = validationResult.getPossibleTypes(ast);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof SetType);
		final IType rawType = ((SetType)type).getCollectionType();
		assertTrue(rawType instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)rawType).getType());
	}

	@Test
	public void reject() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName
				+ "->reject(i | not i.oclIsKindOf(test::C))", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> types = validationResult.getPossibleTypes(ast);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof SetType);
		final IType rawType = ((SetType)type).getCollectionType();
		assertTrue(rawType instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)rawType).getType());
	}

	private void assertDisjointTypes(Set<IType> inferredWhenTrue, Set<IType> inferredWhenFalse) {
		for (IType type : inferredWhenTrue) {
			assertFalse(inferredWhenFalse.contains(type));
		}
		for (IType type : inferredWhenFalse) {
			assertFalse(inferredWhenTrue.contains(type));
		}
	}

}
