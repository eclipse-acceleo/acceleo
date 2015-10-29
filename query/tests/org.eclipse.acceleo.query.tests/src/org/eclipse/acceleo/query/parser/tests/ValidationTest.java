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
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.QueryValidationEngine;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.services.EObjectServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ValidationTest {

	QueryValidationEngine engine;

	IQueryEnvironment queryEnvironment;

	/**
	 * Variable types.
	 */
	Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		queryEnvironment.registerServicePackage(EObjectServices.class);
		engine = new QueryValidationEngine(queryEnvironment);

		variableTypes.clear();
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("self", selfTypes);
		final Set<IType> stuffTypes = new LinkedHashSet<IType>();
		stuffTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("stuff", stuffTypes);
	}

	@Test
	public void nullTest() {
		final IValidationResult validationResult = engine.validate(null, variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getPossibleTypes(ast).size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"ErrorExpression", -1, -1);
	}

	@Test
	public void emptyTest() {
		final IValidationResult validationResult = engine.validate("", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		assertEquals(0, validationResult.getPossibleTypes(ast).size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"ErrorExpression", 0, 0);
	}

	@Test
	public void variableTest() {
		final IValidationResult validationResult = engine.validate("self", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void variableNotExistingTest() {
		final IValidationResult validationResult = engine.validate("notExisting", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the notExisting variable", 0, 11);
	}

	@Test
	public void featureAccessTest() {
		final IValidationResult validationResult = engine.validate("self.name", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void featureNotExistingAccessTest() {
		final IValidationResult validationResult = engine.validate("self.notExisting", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Feature notExisting not found in EClass EClass", 4, 16);
	}

	@Test
	public void intliteralTest() {
		final IValidationResult validationResult = engine.validate("2", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEIntegerObject(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void realliteralTest() {
		final IValidationResult validationResult = engine.validate("1.0", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEDoubleObject(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void trueliteralTest() {
		final IValidationResult validationResult = engine.validate("true", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEBooleanObject(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void falseliteralTest() {
		final IValidationResult validationResult = engine.validate("false", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEBooleanObject(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void stringliteralTest() {
		final IValidationResult validationResult = engine.validate("'acceleo query is great'", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(AnydslPackage.eINSTANCE.getSingleString(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void operatorTest() {
		final IValidationResult validationResult = engine.validate("1<=2", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEBooleanObject(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void operatorWrongArgumentTypeTest() {
		final IValidationResult validationResult = engine.validate("1 and '3'", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(2, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the and(EClassifier=EIntegerObject,EClassifier=EString) service", 1, 9);
		assertValidationMessage(validationResult.getMessages().get(1), ValidationMessageLevel.ERROR,
				"Couldn't find the and(EClassifier=EIntegerObject,EClassifier=SingleString) service", 1, 9);
	}

	@Test
	public void operatorNullParameterTypeTest() {
		final IValidationResult validationResult = engine.validate("true and null", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEBooleanObject(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void serviceTest() {
		final IValidationResult validationResult = engine.validate("self.someService('a')", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEInt(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void serviceWrongArgumentTypeTest() {
		final IValidationResult validationResult = engine.validate("self.someService(true)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(
				validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR,
				"Couldn't find the someService(EClassifier=EClass,EClassifier=EBooleanObject) service or EOperation",
				4, 22);
	}

	@Test
	public void serviceNullParameterTypeTest() {
		final IValidationResult validationResult = engine.validate("self.someService(null)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEInt(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void eOperationTest() {
		final IValidationResult validationResult = engine.validate("stuff.getEClassifier('EClass')",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), possibleType.getType());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(
				validationResult.getMessages().get(0),
				ValidationMessageLevel.WARNING,
				"Couldn't find the getEClassifier(EClassifier=EPackage,EClassifier=SingleString) service or EOperation",
				5, 30);
	}

	@Test
	public void eOperationWrongArgumentTypeTest() {
		final IValidationResult validationResult = engine.validate("stuff.getEClassifier(1)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(
				validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR,
				"Couldn't find the getEClassifier(EClassifier=EPackage,EClassifier=EIntegerObject) service or EOperation",
				5, 23);
	}

	@Test
	public void eOperationNullParameterTypeTest() {
		final IValidationResult validationResult = engine.validate("stuff.getEClassifier(null)",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void classifierType() {
		final IValidationResult validationResult = engine.validate("ecore::EClass", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierLiteralType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void classifierTypeError() {
		final IValidationResult validationResult = engine.validate("anydsl::EClass", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"ErrorTypeLiteral", 0, 14);
	}

	@Test
	public void classifierTypeErrorMissingOneColon() {
		final IValidationResult validationResult = engine.validate("anydsl:", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"ErrorTypeLiteral", 0, 7);
	}

	@Test
	public void enumLiteral() {
		final IValidationResult validationResult = engine.validate("anydsl::Part::Other", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(AnydslPackage.eINSTANCE.getPart(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void enumLiteralError() {
		final IValidationResult validationResult = engine
				.validate("anydsl::Part::NotExisting", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"ErrorEnumLiteral", 0, 25);
	}

	@Test
	public void enumLiteralErrorMissingOneColon() {
		final IValidationResult validationResult = engine.validate("anydsl::Part:", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"ErrorEnumLiteral", 0, 13);
	}

	@Test
	public void testNullLiteral() {
		final IValidationResult validationResult = engine.validate("null", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(null, ((ClassType)possibleType).getType());
	}

	@Test
	public void testSetInExtensionLiteral() {
		final IValidationResult validationResult = engine.validate("OrderedSet{self, self, true, false}",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEBooleanObject(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
	}

	@Test
	public void testSequenceInExtensionLiteral() {
		final IValidationResult validationResult = engine.validate("Sequence{self, self, true, false}",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SequenceType);
		assertTrue(((SequenceType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)((SequenceType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SequenceType);
		assertTrue(((SequenceType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEBooleanObject(),
				((EClassifierType)((SequenceType)possibleType).getCollectionType()).getType());
	}

	@Test
	public void testConditionNothingCondition() {
		final Set<IType> selectorTypes = new LinkedHashSet<IType>();
		selectorTypes.add(new NothingType("nothing"));
		variableTypes.put("selector", selectorTypes);
		final IValidationResult validationResult = engine.validate("if selector then self else stuff endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(2, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"nothing", 3, 11);
		assertValidationMessage(validationResult.getMessages().get(1), ValidationMessageLevel.ERROR,
				"The predicate never evaluates to a boolean type ([]).", 0, 38);
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
	}

	@Test
	public void testConditionNotBooleanCondition() {
		final Set<IType> selectorTypes = new LinkedHashSet<IType>();
		selectorTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("selector", selectorTypes);
		final IValidationResult validationResult = engine.validate("if selector then self else stuff endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"The predicate never evaluates to a boolean type ([EClassifier=EClass]).", 0, 38);
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
	}

	@Test
	public void testConditionOneBooleanTypeCondition() {
		final Set<IType> selectorTypes = new LinkedHashSet<IType>();
		selectorTypes.add(new ClassType(queryEnvironment, Boolean.class));
		variableTypes.put("selector", selectorTypes);
		final IValidationResult validationResult = engine.validate("if selector then self else stuff endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
	}

	@Test
	public void testConditionManyBooleanTypesCondition() {
		final Set<IType> selectorTypes = new LinkedHashSet<IType>();
		selectorTypes.add(new ClassType(queryEnvironment, Boolean.class));
		selectorTypes.add(new ClassType(queryEnvironment, boolean.class));
		variableTypes.put("selector", selectorTypes);
		final IValidationResult validationResult = engine.validate("if selector then self else stuff endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
	}

	@Test
	public void testConditionNotOnlyBooleanTypesCondition() {
		final Set<IType> selectorTypes = new LinkedHashSet<IType>();
		selectorTypes.add(new ClassType(queryEnvironment, Boolean.class));
		selectorTypes.add(new ClassType(queryEnvironment, Object.class));
		variableTypes.put("selector", selectorTypes);
		final IValidationResult validationResult = engine.validate("if selector then self else stuff endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), possibleType.getType());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(
				validationResult.getMessages().get(0),
				ValidationMessageLevel.WARNING,
				"The predicate may evaluate to a value that is not a boolean type ([java.lang.Boolean, java.lang.Object]).",
				0, 38);
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
	}

	@Test
	public void testLetMaskingVariable() {
		final IValidationResult validationResult = engine
				.validate("let stuff = self in stuff", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.WARNING,
				"Variable stuff overrides an existing value.", 0, 25);
	}

	@Test
	public void testLetMaskingBinding() {
		final IValidationResult validationResult = engine.validate("let a = 1, a = 2 in self", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.WARNING,
				"Variable a overrides an existing value.", 0, 24);
	}

	@Test
	public void testLet() {
		final IValidationResult validationResult = engine.validate("let newVar = self in newVar",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void typeSetLiteral() {
		final IValidationResult validationResult = engine.validate(
				"{ecore::EClass | ecore::EPackage | ecore::EAttribute}", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierSetLiteralType);
		final EClassifierSetLiteralType eClassifierSetLiteralType = (EClassifierSetLiteralType)possibleType;
		assertEquals(3, eClassifierSetLiteralType.getEClassifiers().size());
		final Iterator<EClassifier> itECls = eClassifierSetLiteralType.getEClassifiers().iterator();
		assertEquals(EcorePackage.eINSTANCE.getEClass(), itECls.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), itECls.next());
		assertEquals(EcorePackage.eINSTANCE.getEAttribute(), itECls.next());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void typeSetLiteralDuplicates() {
		final IValidationResult validationResult = engine.validate(
				"{ecore::EClass | ecore::EPackage | ecore::EAttribute | ecore::EPackage}", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierSetLiteralType);
		final EClassifierSetLiteralType eClassifierSetLiteralType = (EClassifierSetLiteralType)possibleType;
		assertEquals(3, eClassifierSetLiteralType.getEClassifiers().size());
		final Iterator<EClassifier> itECls = eClassifierSetLiteralType.getEClassifiers().iterator();
		assertEquals(EcorePackage.eINSTANCE.getEClass(), itECls.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), itECls.next());
		assertEquals(EcorePackage.eINSTANCE.getEAttribute(), itECls.next());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.WARNING,
				"EClassifierLiteral=EPackage is duplicated in the type set literal.", 0, 71);
	}

	@Test
	public void testLetExpressionError() {
		final IValidationResult validationResult = engine.validate(
				"let newVar = 'text' in newVar + notAVariable", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the notAVariable variable", 32, 44);
	}

	@Test
	public void testLetBindingExpressionError() {
		final IValidationResult validationResult = engine.validate("let newVar = notAVariable in newVar",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(2, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the notAVariable variable", 13, 25);
		assertValidationMessage(validationResult.getMessages().get(1), ValidationMessageLevel.ERROR,
				"The newVar variable has no types", 29, 35);
	}

	@Test
	public void testCollectionCallOnNull_toString() {
		final IValidationResult validationResult = engine.validate("null->toString()", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(AnydslPackage.eINSTANCE.getSingleString(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void testCollectionCallOnNull_size() {
		final IValidationResult validationResult = engine.validate("null->size()", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEIntegerObject(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void testCollectionCallOnNull_first() {
		final IValidationResult validationResult = engine.validate("null->first()", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"The receiving Collection was empty due to a null value being wrapped as a Collection.", 4,
				13);
	}

	@Test
	public void testCollectionCallOnNullFromUnsetReference() {
		final Set<IType> operationTypes = new LinkedHashSet<IType>();
		operationTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEOperation()));
		variableTypes.put("operation", operationTypes);

		final IValidationResult validationResult = engine.validate("operation.eType->first()", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void eOperationLookupNoEClassifierForClassType() {
		queryEnvironment.removeEPackage(EcorePackage.eINSTANCE.getName());
		queryEnvironment.removeEPackage(AnydslPackage.eINSTANCE.getName());

		final IValidationResult validationResult = engine.validate("self.triggerEOperationLookUp('')",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(
				validationResult.getMessages().get(0),
				ValidationMessageLevel.ERROR,
				"Couldn't create EClassifier type for triggerEOperationLookUp(EClassifier=EClass,java.lang.String) parameter java.lang.String",
				4, 32);
	}

	/**
	 * Asserts the given {@link IValidationMessage} against expected values.
	 * 
	 * @param message
	 *            the {@link IValidationMessage} to assert
	 * @param expectedLevel
	 *            the expected {@link IValidationMessage#getLevel() level}
	 * @param expectedMessage
	 *            the expected {@link IValidationMessage#getMessage() message}
	 * @param expectedStartPosition
	 *            the expected {@link IValidationMessage#getStartPosition() start position}
	 * @param expectedEndPosition
	 *            the expected {@link IValidationMessage#getEndPosition() end position}
	 */
	public static void assertValidationMessage(IValidationMessage message,
			ValidationMessageLevel expectedLevel, String expectedMessage, int expectedStartPosition,
			int expectedEndPosition) {
		assertEquals(expectedLevel, message.getLevel());
		assertEquals(expectedMessage, message.getMessage());
		assertEquals(expectedStartPosition, message.getStartPosition());
		assertEquals(expectedEndPosition, message.getEndPosition());
	}

}
