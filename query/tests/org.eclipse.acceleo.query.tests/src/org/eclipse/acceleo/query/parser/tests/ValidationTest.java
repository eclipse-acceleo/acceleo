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

import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.QueryValidationEngine;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.services.EObjectServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationTest {

	QueryValidationEngine engine;

	IQueryEnvironment queryEnvironment;

	/**
	 * Variable types.
	 */
	Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		queryEnvironment = new QueryEnvironment(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		queryEnvironment.registerServicePackage(EObjectServices.class);
		engine = new QueryValidationEngine(queryEnvironment);

		variableTypes.clear();
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("self", selfTypes);
		final Set<IType> stuffTypes = new LinkedHashSet<IType>();
		stuffTypes.add(new EClassifierType(EcorePackage.eINSTANCE.getEPackage()));
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
				"ErrorExpression", -1, -1);
	}

	@Test
	public void variableTest() {
		final IValidationResult validationResult = engine.validate("self", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
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
				"Couldn't find the notExisting variable", -1, -1);
	}

	@Test
	public void featureAccessTest() {
		final IValidationResult validationResult = engine.validate("self.name", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
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
				"Feature notExisting not found in EClass EClass", -1, -1);
	}

	@Test
	public void intliteralTest() {
		final IValidationResult validationResult = engine.validate("2", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
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
		assertEquals(true, possibleType instanceof EClassifierType);
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
		assertEquals(true, possibleType instanceof EClassifierType);
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
		assertEquals(true, possibleType instanceof EClassifierType);
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
		assertEquals(true, possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());
		possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
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
		assertEquals(true, possibleType instanceof EClassifierType);
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
				"Couldn't find the and(EClassifier=EIntegerObject,EClassifier=EString) service", -1, -1);
		assertValidationMessage(validationResult.getMessages().get(1), ValidationMessageLevel.ERROR,
				"Couldn't find the and(EClassifier=EIntegerObject,EClassifier=SingleString) service", -1, -1);
	}

	@Test
	public void operatorNullParameterTypeTest() {
		final IValidationResult validationResult = engine.validate("true and null", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
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
		assertEquals(true, possibleType instanceof EClassifierType);
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
				-1, -1);
	}

	@Test
	public void serviceNullParameterTypeTest() {
		final IValidationResult validationResult = engine.validate("self.someService(null)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
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
		assertEquals(true, possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), possibleType.getType());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(
				validationResult.getMessages().get(0),
				ValidationMessageLevel.WARNING,
				"Couldn't find the getEClassifier(EClassifier=EPackage,EClassifier=SingleString) service or EOperation",
				-1, -1);
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
				-1, -1);
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
		assertEquals(true, possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsEClassifier() {
		final IValidationResult validationResult = engine.validate("ecore::EClass", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierLiteralType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsEEnumLiteral() {
		final IValidationResult validationResult = engine.validate("Part::Other", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
		assertEquals(AnydslPackage.eINSTANCE.getPart(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsError() {
		final IValidationResult validationResult = engine.validate("anydsl::EClass", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"ErrorTypeLiteral", -1, -1);
	}

	@Test
	public void enumLiteralOrEClassifierThreeSegments() {
		final IValidationResult validationResult = engine.validate("anydsl::Part::Other", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
		assertEquals(AnydslPackage.eINSTANCE.getPart(), possibleType.getType());
		assertEquals(0, validationResult.getMessages().size());
	}

	@Test
	public void enumLiteralOrEClassifierThreeSegmentsError() {
		final IValidationResult validationResult = engine
				.validate("anydsl::Part::NotExisting", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(0, possibleTypes.size());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"ErrorTypeLiteral", -1, -1);
	}

	@Test
	public void testNullLiteral() {
		final IValidationResult validationResult = engine.validate("null", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof ClassType);
		assertEquals(null, ((ClassType)possibleType).getType());
	}

	@Test
	public void testSetInExtensionLiteral() {
		final IValidationResult validationResult = engine
				.validate("{self, self, true, false}", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof SetType);
		assertEquals(true, ((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertEquals(true, possibleType instanceof SetType);
		assertEquals(true, ((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEBooleanObject(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
	}

	@Test
	public void testSequenceInExtensionLiteral() {
		final IValidationResult validationResult = engine
				.validate("[self, self, true, false]", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof SequenceType);
		assertEquals(true, ((SequenceType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)((SequenceType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertEquals(true, possibleType instanceof SequenceType);
		assertEquals(true, ((SequenceType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEBooleanObject(),
				((EClassifierType)((SequenceType)possibleType).getCollectionType()).getType());
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
	 * @param expectedLine
	 *            the expected {@link IValidationMessage#getLocationLine() line}
	 * @param expectedColumn
	 *            the expected {@link IValidationMessage#getLocationColumn() column}
	 */
	private void assertValidationMessage(IValidationMessage message, ValidationMessageLevel expectedLevel,
			String expectedMessage, int expectedLine, int expectedColumn) {
		assertEquals(expectedLevel, message.getLevel());
		assertEquals(expectedMessage, message.getMessage());
		assertEquals(expectedLine, message.getLocationLine());
		assertEquals(expectedColumn, message.getLocationColumn());
	}

}