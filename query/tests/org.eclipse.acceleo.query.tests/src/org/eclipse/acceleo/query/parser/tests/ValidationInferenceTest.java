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
package org.eclipse.acceleo.query.parser.tests;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.QueryValidationEngine;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import inference.A;
import inference.B;
import inference.C;
import inference.InferencePackage;
import inference.Z;

public class ValidationInferenceTest {

	private final EPackage ePackage = InferencePackage.eINSTANCE;

	private final EClass a = InferencePackage.eINSTANCE.getA();

	private final EClass b = InferencePackage.eINSTANCE.getB();

	private final EClass c = InferencePackage.eINSTANCE.getC();

	private final EClass y = InferencePackage.eINSTANCE.getY();

	private final EClass z = InferencePackage.eINSTANCE.getZ();

	QueryValidationEngine engine;

	IQueryEnvironment queryEnvironment;

	/**
	 * Variable types.
	 */
	Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();

	@Before
	public void setup() {
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
		final Set<IType> varZTypes = new LinkedHashSet<IType>();
		varZTypes.add(new EClassifierType(queryEnvironment, z));
		variableTypes.put("varZ", varZTypes);

		final Set<IType> varAClassTypes = new LinkedHashSet<IType>();
		varAClassTypes.add(new ClassType(queryEnvironment, A.class));
		variableTypes.put("varAClass", varAClassTypes);
		final Set<IType> varBClassTypes = new LinkedHashSet<IType>();
		varBClassTypes.add(new ClassType(queryEnvironment, B.class));
		variableTypes.put("varBClass", varBClassTypes);
		final Set<IType> varCClassTypes = new LinkedHashSet<IType>();
		varCClassTypes.add(new ClassType(queryEnvironment, C.class));
		variableTypes.put("varCClass", varCClassTypes);
		final Set<IType> varZClassTypes = new LinkedHashSet<IType>();
		varZClassTypes.add(new ClassType(queryEnvironment, Z.class));
		variableTypes.put("varZClass", varZClassTypes);

		final Set<IType> varNothing = new LinkedHashSet<IType>();
		varNothing.add(new NothingType("Nothing"));
		variableTypes.put("varNothing", varNothing);
	}

	@Test
	public void oclIsKindOf_varNothing_B() {
		String varName = "varNothing";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);

		assertNull(inferredWhenTrue);

		assertNotNull(inferredWhenFalse);
		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals("Nothing", ((NothingType)type).getMessage());

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 10);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varNothing (Nothing(Nothing)) is kind of EClassifierLiteral=B",
				0, 36);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varNothing (Nothing(Nothing)) is kind of EClassifierLiteral=B",
				0, 36);
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 10);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varEObject_B() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		String varName = "container";
		Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();
		IType eObjectType = new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject());
		variableTypes.put(varName, new HashSet<IType>(Arrays.asList(eObjectType)));

		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::B)",
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
		assertEquals(EcorePackage.eINSTANCE.getEObject(), ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varEObjectClass_B() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		String varName = "container";
		Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();
		IType eObjectType = new ClassType(queryEnvironment, EObject.class);
		variableTypes.put(varName, new HashSet<IType>(Arrays.asList(eObjectType)));

		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::B)",
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
		assertTrue(type instanceof ClassType);
		assertEquals(EObject.class, ((ClassType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varA_B() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::B)",
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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varAClass_B() {
		String varName = "varAClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::B)",
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
		assertTrue(type instanceof ClassType);
		assertEquals(A.class, ((ClassType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varB_B() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::B)",
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
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varB (EClassifier=B) is not kind of EClassifierLiteral=B",
				0, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varB (EClassifier=B) is not kind of EClassifierLiteral=B",
				0, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varBClass_B() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::B)",
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
		assertTrue(type instanceof ClassType);
		assertEquals(B.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varBClass (inference.B) is not kind of EClassifierLiteral=B",
				0, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varBClass (inference.B) is not kind of EClassifierLiteral=B",
				0, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varC_B() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::B)",
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
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varC (EClassifier=C) is not kind of EClassifierLiteral=B",
				0, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varC (EClassifier=C) is not kind of EClassifierLiteral=B",
				0, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varCClass_B() {
		String varName = "varCClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::B)",
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
		assertTrue(type instanceof ClassType);
		assertEquals(C.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varCClass (inference.C) is not kind of EClassifierLiteral=B",
				0, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varCClass (inference.C) is not kind of EClassifierLiteral=B",
				0, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varC_O() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::O)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is kind of EClassifierLiteral=O",
				0, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is kind of EClassifierLiteral=O",
				0, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varCClass_O() {
		String varName = "varCClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::O)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof ClassType);
		assertEquals(C.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is kind of EClassifierLiteral=O",
				0, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is kind of EClassifierLiteral=O",
				0, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varNothing_B() {
		String varName = "varNothing";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals("Nothing", ((NothingType)type).getMessage());

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 0, 10);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varNothing (Nothing(Nothing)) is type of EClassifierLiteral=B",
				0, 36);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varNothing (Nothing(Nothing)) is type of EClassifierLiteral=B",
				0, 36);
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.ERROR, "Nothing", 0, 10);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varEObject_B() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		String varName = "container";
		Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();
		IType eObjectType = new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEObject());
		variableTypes.put(varName, new HashSet<IType>(Arrays.asList(eObjectType)));

		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::B)",
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
		assertEquals(EcorePackage.eINSTANCE.getEObject(), ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varEObjectClass_B() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		String varName = "container";
		Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();
		IType eObjectType = new ClassType(queryEnvironment, EObject.class);
		variableTypes.put(varName, new HashSet<IType>(Arrays.asList(eObjectType)));

		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::B)",
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
		assertTrue(type instanceof ClassType);
		assertEquals(EObject.class, ((ClassType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varA_B() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varAClass_B() {
		String varName = "varAClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());

		assertEquals(1, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof ClassType);
		assertEquals(A.class, ((ClassType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varA_X() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::X)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(a, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varA (EClassifier=A) is type of EClassifierLiteral=X",
				0, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varA (EClassifier=A) is type of EClassifierLiteral=X",
				0, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varAClass_X() {
		String varName = "varAClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::X)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof ClassType);
		assertEquals(A.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varAClass (inference.A) is type of EClassifierLiteral=X",
				0, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varAClass (inference.A) is type of EClassifierLiteral=X",
				0, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varB_B() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());

		assertEquals(2, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(y, ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varBClass_B() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());

		assertEquals(2, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(y, ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varZ_Z() {
		String varName = "varZ";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::Z)",
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
		assertEquals(z, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varZ (EClassifier=Z) is not type of EClassifierLiteral=Z",
				0, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varZ (EClassifier=Z) is not type of EClassifierLiteral=Z",
				0, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varZClass_Z() {
		String varName = "varZClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::Z)",
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
		assertEquals(z, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varZClass (inference.Z) is not type of EClassifierLiteral=Z",
				0, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varZClass (inference.Z) is not type of EClassifierLiteral=Z",
				0, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varC_B() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=B",
				0, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=B",
				0, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varCClass_B() {
		String varName = "varCClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::B)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof ClassType);
		assertEquals(C.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is type of EClassifierLiteral=B",
				0, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is type of EClassifierLiteral=B",
				0, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varC_O() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::O)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=O",
				0, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=O",
				0, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsTypeOf_varCClass_O() {
		String varName = "varCClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsTypeOf(inference::O)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof ClassType);
		assertEquals(C.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is type of EClassifierLiteral=O",
				0, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(ast).get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is type of EClassifierLiteral=O",
				0, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void not_oclIsKindOf_varNothing_B() {
		String varName = "varNothing";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsKindOf(inference::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);

		assertNotNull(inferredWhenTrue);
		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof NothingType);
		assertEquals("Nothing", ((NothingType)type).getMessage());

		assertNull(inferredWhenFalse);

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 4, 14);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varNothing (Nothing(Nothing)) is kind of EClassifierLiteral=B",
				4, 40);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varNothing (Nothing(Nothing)) is kind of EClassifierLiteral=B",
				4, 40);
		assertEquals(1, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)((Call)ast).getArguments()
				.get(0)).getArguments().get(0)).get(0), ValidationMessageLevel.ERROR, "Nothing", 4, 14);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsKindOf_varA_B() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsKindOf(inference::B)", variableTypes);
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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsKindOf_varAClass_B() {
		String varName = "varAClass";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsKindOf(inference::B)", variableTypes);
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
		assertTrue(type instanceof ClassType);
		assertEquals(A.class, ((ClassType)type).getType());

		assertEquals(1, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void oclIsKindOf_varA_X() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::X)",
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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void oclIsKindOf_varAClass_X() {
		String varName = "varAClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::X)",
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
		assertTrue(type instanceof ClassType);
		assertEquals(A.class, ((ClassType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void not_oclIsKindOf_varB_B() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsKindOf(inference::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varB (EClassifier=B) is not kind of EClassifierLiteral=B",
				4, 34);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varB (EClassifier=B) is not kind of EClassifierLiteral=B",
				4, 34);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsKindOf_varBClass_B() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsKindOf(inference::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof ClassType);
		assertEquals(B.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varBClass (inference.B) is not kind of EClassifierLiteral=B",
				4, 39);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varBClass (inference.B) is not kind of EClassifierLiteral=B",
				4, 39);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsKindOf_varC_B() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsKindOf(inference::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varC (EClassifier=C) is not kind of EClassifierLiteral=B",
				4, 34);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varC (EClassifier=C) is not kind of EClassifierLiteral=B",
				4, 34);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsKindOf_varCClass_B() {
		String varName = "varCClass";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsKindOf(inference::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof ClassType);
		assertEquals(C.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varCClass (inference.C) is not kind of EClassifierLiteral=B",
				4, 39);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varCClass (inference.C) is not kind of EClassifierLiteral=B",
				4, 39);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsKindOf_varC_O() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsKindOf(inference::O)", variableTypes);
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
				4, 34);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is kind of EClassifierLiteral=O",
				4, 34);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsKindOf_varCClass_O() {
		String varName = "varCClass";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsKindOf(inference::O)", variableTypes);
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
		assertTrue(type instanceof ClassType);
		assertEquals(C.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is kind of EClassifierLiteral=O",
				4, 39);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is kind of EClassifierLiteral=O",
				4, 39);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varNothing_B() {
		String varName = "varNothing";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::B)", variableTypes);
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
		assertTrue(type instanceof NothingType);
		assertEquals("Nothing", ((NothingType)type).getMessage());

		assertEquals(2, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR, "Nothing", 4, 14);
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(1),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varNothing (Nothing(Nothing)) is type of EClassifierLiteral=B",
				4, 40);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varNothing (Nothing(Nothing)) is type of EClassifierLiteral=B",
				4, 40);
		assertEquals(1, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)((Call)ast).getArguments()
				.get(0)).getArguments().get(0)).get(0), ValidationMessageLevel.ERROR, "Nothing", 4, 14);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varA_B() {
		String varName = "varA";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varAClass_B() {
		String varName = "varAClass";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenTrue.size());
		Iterator<IType> it = inferredWhenTrue.iterator();
		IType type = it.next();
		assertTrue(type instanceof ClassType);
		assertEquals(A.class, ((ClassType)type).getType());

		assertEquals(1, inferredWhenFalse.size());
		it = inferredWhenFalse.iterator();
		type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varB_B() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varBClass_B() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::B)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNotNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varZ_Z() {
		String varName = "varZ";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::Z)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(z, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varZ (EClassifier=Z) is not type of EClassifierLiteral=Z",
				4, 34);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varZ (EClassifier=Z) is not type of EClassifierLiteral=Z",
				4, 34);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varZClass_Z() {
		String varName = "varZClass";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::Z)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNotNull(inferredWhenFalse);

		assertEquals(1, inferredWhenFalse.size());
		Iterator<IType> it = inferredWhenFalse.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(z, ((EClassifierType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varZClass (inference.Z) is not type of EClassifierLiteral=Z",
				4, 39);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always true:\nNothing inferred when varZClass (inference.Z) is not type of EClassifierLiteral=Z",
				4, 39);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varC_B() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::B)", variableTypes);
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
				4, 34);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=B",
				4, 34);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varCClass_B() {
		String varName = "varCClass";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::B)", variableTypes);
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
		assertTrue(type instanceof ClassType);
		assertEquals(C.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is type of EClassifierLiteral=B",
				4, 39);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is type of EClassifierLiteral=B",
				4, 39);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varC_O() {
		String varName = "varC";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::O)", variableTypes);
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
				4, 34);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varC (EClassifier=C) is type of EClassifierLiteral=O",
				4, 34);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void not_oclIsTypeOf_varCClass_O() {
		String varName = "varCClass";
		final IValidationResult validationResult = engine.validate("not " + varName
				+ ".oclIsTypeOf(inference::O)", variableTypes);
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
		assertTrue(type instanceof ClassType);
		assertEquals(C.class, ((ClassType)type).getType());

		assertEquals(1, validationResult.getMessages().size());
		ValidationTest.assertValidationMessage(validationResult.getMessages().get(0),
				ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is type of EClassifierLiteral=O",
				4, 39);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		ValidationTest.assertValidationMessage(validationResult.getMessages(((Call)ast).getArguments().get(0))
				.get(0), ValidationMessageLevel.INFO,
				"Always false:\nNothing inferred when varCClass (inference.C) is type of EClassifierLiteral=O",
				4, 39);
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
	}

	@Test
	public void or() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::C) or "
				+ varName + ".oclIsKindOf(inference::Y)", variableTypes);
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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void orClass() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::C) or "
				+ varName + ".oclIsKindOf(inference::Y)", variableTypes);
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
		assertTrue(type instanceof ClassType);
		assertEquals(B.class, ((ClassType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void xor() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::C) xor "
				+ varName + ".oclIsKindOf(inference::Y)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNull(inferredWhenFalse);

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void xorClass() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::C) xor "
				+ varName + ".oclIsKindOf(inference::Y)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		final Set<IType> inferredWhenTrue = validationResult.getInferredVariableTypes(ast, Boolean.TRUE).get(
				varName);
		final Set<IType> inferredWhenFalse = validationResult.getInferredVariableTypes(ast, Boolean.FALSE)
				.get(varName);
		assertNull(inferredWhenTrue);
		assertNull(inferredWhenFalse);

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void and() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::C) and "
				+ varName + ".oclIsKindOf(inference::Y)", variableTypes);
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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void andClass() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate(varName + ".oclIsKindOf(inference::C) and "
				+ varName + ".oclIsKindOf(inference::Y)", variableTypes);
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
		assertTrue(type instanceof ClassType);
		assertEquals(B.class, ((ClassType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void conditionnalTrueBranch() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate("if " + varName
				+ ".oclIsKindOf(inference::C) then " + varName + " else " + varName + " endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertTrue(ast instanceof Conditional);
		final Conditional conditional = (Conditional)ast;
		final Set<IType> types = validationResult.getPossibleTypes(conditional.getTrueBranch());
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getPredicate()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Conditional)ast).getPredicate()).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Conditional)ast).getPredicate()).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getTrueBranch()).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getFalseBranch()).size());
	}

	@Test
	public void conditionnalTrueBranchClass() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate("if " + varName
				+ ".oclIsKindOf(inference::C) then " + varName + " else " + varName + " endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertTrue(ast instanceof Conditional);
		final Conditional conditional = (Conditional)ast;
		final Set<IType> types = validationResult.getPossibleTypes(conditional.getTrueBranch());
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getPredicate()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Conditional)ast).getPredicate()).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Conditional)ast).getPredicate()).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getTrueBranch()).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getFalseBranch()).size());
	}

	@Test
	public void conditionnalFalseBranch() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate("if not " + varName
				+ ".oclIsKindOf(inference::C) then " + varName + " else " + varName + " endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertTrue(ast instanceof Conditional);
		final Conditional conditional = (Conditional)ast;
		final Set<IType> types = validationResult.getPossibleTypes(conditional.getFalseBranch());
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getPredicate()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Conditional)ast).getPredicate()).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Conditional)ast).getPredicate())
				.getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Conditional)ast).getPredicate())
				.getArguments().get(0)).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getTrueBranch()).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getFalseBranch()).size());
	}

	@Test
	public void conditionnalFalseBranchClass() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate("if not " + varName
				+ ".oclIsKindOf(inference::C) then " + varName + " else " + varName + " endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertTrue(ast instanceof Conditional);
		final Conditional conditional = (Conditional)ast;
		final Set<IType> types = validationResult.getPossibleTypes(conditional.getFalseBranch());
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getPredicate()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Conditional)ast).getPredicate()).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Conditional)ast).getPredicate())
				.getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Conditional)ast).getPredicate())
				.getArguments().get(0)).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getTrueBranch()).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getFalseBranch()).size());
	}

	@Test
	public void any() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName
				+ "->any(i | i.oclIsKindOf(inference::C))", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> types = validationResult.getPossibleTypes(ast);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

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
	}

	@Test
	public void anyClass() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate(varName
				+ "->any(i | i.oclIsKindOf(inference::C))", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> types = validationResult.getPossibleTypes(ast);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)type).getType());

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
	}

	@Test
	public void select() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName
				+ "->select(i | i.oclIsKindOf(inference::C))", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> types = validationResult.getPossibleTypes(ast);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof SetType);
		final IType rawType = ((SetType)type).getCollectionType();
		assertTrue(rawType instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)rawType).getType());

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
	}

	@Test
	public void selectClass() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate(varName
				+ "->select(i | i.oclIsKindOf(inference::C))", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> types = validationResult.getPossibleTypes(ast);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof SetType);
		final IType rawType = ((SetType)type).getCollectionType();
		assertTrue(rawType instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)rawType).getType());

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
	}

	@Test
	public void reject() {
		String varName = "varB";
		final IValidationResult validationResult = engine.validate(varName
				+ "->reject(i | not i.oclIsKindOf(inference::C))", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> types = validationResult.getPossibleTypes(ast);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof SetType);
		final IType rawType = ((SetType)type).getCollectionType();
		assertTrue(rawType instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)rawType).getType());

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
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).getArguments().get(1)).size());
	}

	@Test
	public void rejectClass() {
		String varName = "varBClass";
		final IValidationResult validationResult = engine.validate(varName
				+ "->reject(i | not i.oclIsKindOf(inference::C))", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> types = validationResult.getPossibleTypes(ast);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType type = it.next();
		assertTrue(type instanceof SetType);
		final IType rawType = ((SetType)type).getCollectionType();
		assertTrue(rawType instanceof EClassifierType);
		assertEquals(c, ((EClassifierType)rawType).getType());

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
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).getArguments().get(1)).size());
	}

	@Test
	public void andWithSubTypeAttribute() {
		final IValidationResult validationResult = engine.validate(
				"varB.oclIsKindOf(inference::C) and varB.cAttr = null", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(1))
				.getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(1))
				.getArguments().get(0)).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void andWithSubTypeAttributeClass() {
		final IValidationResult validationResult = engine.validate(
				"varBClass.oclIsKindOf(inference::C) and varBClass.cAttr = null", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(1))
				.getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(1))
				.getArguments().get(0)).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void orWithSubTypeAttribute() {
		final IValidationResult validationResult = engine.validate(
				"not varB.oclIsKindOf(inference::C) or varB.cAttr = null", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(0)).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(1))
				.getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(1))
				.getArguments().get(0)).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void orWithSubTypeAttributeClass() {
		final IValidationResult validationResult = engine.validate(
				"not varBClass.oclIsKindOf(inference::C) or varBClass.cAttr = null", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(0))
				.getArguments().get(0)).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(1))
				.getArguments().get(0)).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)((Call)ast).getArguments().get(1))
				.getArguments().get(0)).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
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
