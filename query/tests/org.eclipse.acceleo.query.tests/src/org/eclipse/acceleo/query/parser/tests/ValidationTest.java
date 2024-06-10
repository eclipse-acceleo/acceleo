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
package org.eclipse.acceleo.query.parser.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.ECrossReferenceAdapterCrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.impl.QueryValidationEngine;
import org.eclipse.acceleo.query.runtime.impl.ResourceSetRootEObjectProvider;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.services.EObjectServices;
import org.eclipse.acceleo.query.tests.services.ReceiverServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import nooperationreflection.NooperationreflectionPackage;

public class ValidationTest {

	QueryValidationEngine engine;

	IQueryEnvironment queryEnvironment;

	/**
	 * Variable types.
	 */
	Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();

	@Before
	public void setup() {
		final ResourceSet dummyResourceSet = new ResourceSetImpl();
		final ECrossReferenceAdapterCrossReferenceProvider crossReferenceProvider = new ECrossReferenceAdapterCrossReferenceProvider(
				ECrossReferenceAdapter.getCrossReferenceAdapter(dummyResourceSet));
		final ResourceSetRootEObjectProvider rootProvider = new ResourceSetRootEObjectProvider(
				dummyResourceSet);

		queryEnvironment = Query.newEnvironmentWithDefaultServices(crossReferenceProvider, rootProvider);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		queryEnvironment.registerEPackage(NooperationreflectionPackage.eINSTANCE);
		final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, EObjectServices.class);
		ServiceUtils.registerServices(queryEnvironment, services);
		engine = new QueryValidationEngine(queryEnvironment);

		variableTypes.clear();
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("self", selfTypes);
		final Set<IType> stuffTypes = new LinkedHashSet<IType>();
		stuffTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("stuff", stuffTypes);
		final Set<IType> noReflexionTypes = new LinkedHashSet<IType>();
		noReflexionTypes.add(new EClassifierType(queryEnvironment, NooperationreflectionPackage.eINSTANCE
				.getNoOperationReflection()));
		variableTypes.put("noReflexion", noReflexionTypes);

	}

	@Test
	public void nullTest() {
		final IValidationResult validationResult = engine.validate(null, variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"null or empty string.", -1, -1);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"null or empty string.", -1, -1);
	}

	@Test
	public void emptyTest() {
		final IValidationResult validationResult = engine.validate("", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"null or empty string.", 0, 0);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"null or empty string.", 0, 0);
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

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void variableNotRegisteredEClassifierTest() {
		queryEnvironment.removeEPackage(EcorePackage.eINSTANCE);

		final IValidationResult validationResult = engine.validate("self", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"EClassifier=EClass is not registered in the current environment", 0, 4);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"EClassifier=EClass is not registered in the current environment", 0, 4);
	}

	@Test
	public void variableNotExistingTest() {
		final IValidationResult validationResult = engine.validate("notExisting", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'notExisting' variable", 0, 11);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'notExisting' variable", 0, 11);
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

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void featureNotExistingAccessTest() {
		final IValidationResult validationResult = engine.validate("self.notExisting", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Feature notExisting not found in EClass EClass", 4, 16);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"Feature notExisting not found in EClass EClass", 4, 16);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void flattenSequenceNothingFeatureNotExistingAccessTest() {
		final IValidationResult validationResult = engine.validate("self->asSequence().notExisting",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(1, possibleTypes.size());
		final IType type = possibleTypes.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertNothingType("Feature notExisting not found in EClass EClass", ((SequenceType)type)
				.getCollectionType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature notExisting not found in EClass EClass", 18, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature notExisting not found in EClass EClass", 18, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
	}

	@Test
	public void flattenSetNothingFeatureNotExistingAccessTest() {
		final IValidationResult validationResult = engine.validate("self->asSet().notExisting",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(1, possibleTypes.size());
		final IType type = possibleTypes.iterator().next();
		assertTrue(type instanceof SetType);
		assertNothingType("Feature notExisting not found in EClass EClass", ((SetType)type)
				.getCollectionType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature notExisting not found in EClass EClass", 13, 25);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature notExisting not found in EClass EClass", 13, 25);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
	}

	@Test
	public void flattenSequenceNothingOclAsTypeTest() {
		final IValidationResult validationResult = engine.validate(
				"self->asSequence().oclAsType(ecore::EPackage)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(1, possibleTypes.size());
		final IType type = possibleTypes.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertNothingType(
				"Nothing will be left after calling oclAsType:\nEClassifier=EClass is not compatible with type EClassifierLiteral=EPackage",
				((SequenceType)type).getCollectionType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling oclAsType:\nEClassifier=EClass is not compatible with type EClassifierLiteral=EPackage",
				18, 45);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling oclAsType:\nEClassifier=EClass is not compatible with type EClassifierLiteral=EPackage",
				18, 45);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void flattenSetNothingOclAsTypeTest() {
		final IValidationResult validationResult = engine.validate(
				"self->asSequence().oclAsType(ecore::EPackage)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(1, possibleTypes.size());
		final IType type = possibleTypes.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertNothingType(
				"Nothing will be left after calling oclAsType:\nEClassifier=EClass is not compatible with type EClassifierLiteral=EPackage",
				((SequenceType)type).getCollectionType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling oclAsType:\nEClassifier=EClass is not compatible with type EClassifierLiteral=EPackage",
				18, 45);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling oclAsType:\nEClassifier=EClass is not compatible with type EClassifierLiteral=EPackage",
				18, 45);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void flattenSequenceNothingMultipleTypesNotExistingOnAnyAccessTest() {
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("multiType", selfTypes);

		final IValidationResult validationResult = engine.validate("multiType->asSequence().notExisting",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(1, possibleTypes.size());
		final IType type = possibleTypes.iterator().next();
		assertTrue(type instanceof SequenceType);
		assertNothingType(
				"Feature notExisting not found in EClass EClass\nFeature notExisting not found in EClass EPackage",
				((SequenceType)type).getCollectionType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature notExisting not found in EClass EClass\nFeature notExisting not found in EClass EPackage",
				23, 35);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature notExisting not found in EClass EClass\nFeature notExisting not found in EClass EPackage",
				23, 35);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
	}

	@Test
	public void flattenSetNothingMultipleTypesNotExistingOnAnyAccessTest() {
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("multiType", selfTypes);

		final IValidationResult validationResult = engine.validate("multiType->asSet().notExisting",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(1, possibleTypes.size());
		final IType type = possibleTypes.iterator().next();
		assertTrue(type instanceof SetType);
		assertNothingType(
				"Feature notExisting not found in EClass EClass\nFeature notExisting not found in EClass EPackage",
				((SetType)type).getCollectionType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature notExisting not found in EClass EClass\nFeature notExisting not found in EClass EPackage",
				18, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature notExisting not found in EClass EClass\nFeature notExisting not found in EClass EPackage",
				18, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
	}

	@Test
	public void flattenSequenceNothingMultipleTypesNotExistingOnOneAccessTest() {
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("multiType", selfTypes);

		final IValidationResult validationResult = engine.validate("multiType->asSequence().eClassifiers",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType type = it.next();
		assertTrue(type instanceof SequenceType);
		assertTrue(((SequenceType)type).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), ((EClassifierType)((SequenceType)type)
				.getCollectionType()).getType());
		type = it.next();
		assertTrue(type instanceof SequenceType);
		assertNothingType("Feature eClassifiers not found in EClass EClass", ((SequenceType)type)
				.getCollectionType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature eClassifiers not found in EClass EClass", 23, 36);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature eClassifiers not found in EClass EClass", 23, 36);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
	}

	@Test
	public void flattenSetNothingMultipleTypesNotExistingOnOneAccessTest() {
		final Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		selfTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEPackage()));
		variableTypes.put("multiType", selfTypes);

		final IValidationResult validationResult = engine.validate("multiType->asSet().eClassifiers",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType type = it.next();
		assertTrue(type instanceof SetType);
		assertTrue(((SetType)type).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), ((EClassifierType)((SetType)type)
				.getCollectionType()).getType());
		type = it.next();
		assertTrue(type instanceof SetType);
		assertNothingType("Feature eClassifiers not found in EClass EClass", ((SetType)type)
				.getCollectionType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature eClassifiers not found in EClass EClass", 18, 31);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.INFO,
				"Empty collection: Feature eClassifiers not found in EClass EClass", 18, 31);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
	}

	@Test
	public void intliteralTest() {
		final IValidationResult validationResult = engine.validate("2", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Integer.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void realliteralTest() {
		final IValidationResult validationResult = engine.validate("1.0", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Double.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void trueliteralTest() {
		final IValidationResult validationResult = engine.validate("true", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Boolean.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void falseliteralTest() {
		final IValidationResult validationResult = engine.validate("false", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Boolean.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void stringliteralTest() {
		final IValidationResult validationResult = engine.validate("'acceleo query is great'", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(String.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void operatorTest() {
		final IValidationResult validationResult = engine.validate("1<=2", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Boolean.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void operatorWrongArgumentTypeTest() {
		final IValidationResult validationResult = engine.validate("1 and '3'", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'and(java.lang.Integer,java.lang.String)' service", 0, 9);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'and(java.lang.Integer,java.lang.String)' service", 0, 9);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void operatorNullParameterTypeTest() {
		final IValidationResult validationResult = engine.validate("true and null", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Boolean.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void serviceTest() {
		final IValidationResult validationResult = engine.validate("self.someService('a')", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(int.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void serviceWrongArgumentTypeTest() {
		final IValidationResult validationResult = engine.validate("self.someService(true)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'someService(EClassifier=EClass,java.lang.Boolean)' service", 4, 22);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'someService(EClassifier=EClass,java.lang.Boolean)' service", 4, 22);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void serviceNullParameterTypeTest() {
		final IValidationResult validationResult = engine.validate("self.someService(null)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(int.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void eOperationTest() {
		final IValidationResult validationResult = engine.validate("stuff.getEClassifier('EClass')",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void eOperationWrongArgumentTypeTest() {
		final IValidationResult validationResult = engine.validate("stuff.getEClassifier(1)", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'getEClassifier(EClassifier=EPackage,java.lang.Integer)' service", 5, 23);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'getEClassifier(EClassifier=EPackage,java.lang.Integer)' service", 5, 23);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
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

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
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

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void classifierTypeError() {
		final IValidationResult validationResult = engine.validate("anydsl::EClass", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"invalid type literal anydsl::EClass", 0, 14);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"invalid type literal anydsl::EClass", 0, 14);
	}

	@Test
	public void classifierTypeErrorMissingOneColon() {
		final IValidationResult validationResult = engine.validate("anydsl:", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"invalid type literal anydsl:", 0, 7);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"invalid type literal anydsl:", 0, 7);
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

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void enumLiteralInSelectWithMissingClosingParenthesis() {
		final IValidationResult validationResult = engine.validate(
				"self->select(s | s = anydsl::Color::black", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof NothingType);
		assertEquals("missing ')'", ((NothingType)possibleType).getMessage());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"missing ')'", 4, 41);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"missing ')'", 4, 41);
	}

	@Test
	public void enumLiteralError() {
		final IValidationResult validationResult = engine.validate("anydsl::Part::NotExisting",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"invalid enum literal: no literal registered with this name", 0, 25);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"invalid enum literal: no literal registered with this name", 0, 25);
	}

	@Test
	public void enumLiteralErrorMissingOneColon() {
		final IValidationResult validationResult = engine.validate("anydsl::Part:", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"invalid enum literal: ':' instead of '::'", 0, 13);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"invalid enum literal: ':' instead of '::'", 0, 13);
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

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
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
		assertTrue(((SetType)possibleType).getCollectionType() instanceof ClassType);
		assertEquals(Boolean.class, ((ClassType)((SetType)possibleType).getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((SetInExtensionLiteral)ast).getValues().get(0)).size());
		assertEquals(0, validationResult.getMessages(((SetInExtensionLiteral)ast).getValues().get(1)).size());
		assertEquals(0, validationResult.getMessages(((SetInExtensionLiteral)ast).getValues().get(2)).size());
		assertEquals(0, validationResult.getMessages(((SetInExtensionLiteral)ast).getValues().get(3)).size());
	}

	@Test
	public void setInExtensionLiteralEmpty() {
		final IValidationResult validationResult = engine.validate("OrderedSet{}", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof ClassType);
		assertEquals(null, ((ClassType)((SetType)possibleType).getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
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
		assertTrue(((SequenceType)possibleType).getCollectionType() instanceof ClassType);
		assertEquals(Boolean.class, ((ClassType)((SequenceType)possibleType).getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((SequenceInExtensionLiteral)ast).getValues().get(0))
				.size());
		assertEquals(0, validationResult.getMessages(((SequenceInExtensionLiteral)ast).getValues().get(1))
				.size());
		assertEquals(0, validationResult.getMessages(((SequenceInExtensionLiteral)ast).getValues().get(2))
				.size());
		assertEquals(0, validationResult.getMessages(((SequenceInExtensionLiteral)ast).getValues().get(3))
				.size());
	}

	@Test
	public void sequenceInExtensionLiteralEmpty() {
		final IValidationResult validationResult = engine.validate("Sequence{}", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SequenceType);
		assertTrue(((SequenceType)possibleType).getCollectionType() instanceof ClassType);
		assertEquals(null, ((ClassType)((SequenceType)possibleType).getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void testConditionNothingCondition() {
		final Set<IType> selectorTypes = new LinkedHashSet<IType>();
		selectorTypes.add(new NothingType("nothing"));
		variableTypes.put("selector", selectorTypes);
		final IValidationResult validationResult = engine.validate("if selector then self else stuff endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(2, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"nothing", 3, 11);
		assertValidationMessage(validationResult.getMessages().get(1), ValidationMessageLevel.ERROR,
				"The predicate never evaluates to a boolean type ([]).", 0, 38);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"The predicate never evaluates to a boolean type ([]).", 0, 38);
		assertEquals(1, validationResult.getMessages(((Conditional)ast).getPredicate()).size());
		assertValidationMessage(validationResult.getMessages(((Conditional)ast).getPredicate()).get(0),
				ValidationMessageLevel.ERROR, "nothing", 3, 11);
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getTrueBranch()).size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getFalseBranch()).size());
	}

	@Test
	public void testConditionNotBooleanCondition() {
		final Set<IType> selectorTypes = new LinkedHashSet<IType>();
		selectorTypes.add(new EClassifierType(queryEnvironment, EcorePackage.eINSTANCE.getEClass()));
		variableTypes.put("selector", selectorTypes);
		final IValidationResult validationResult = engine.validate("if selector then self else stuff endif",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"The predicate never evaluates to a boolean type ([EClassifier=EClass]).", 0, 38);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"The predicate never evaluates to a boolean type ([EClassifier=EClass]).", 0, 38);
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getPredicate()).size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getTrueBranch()).size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getFalseBranch()).size());
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
		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getPredicate()).size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getTrueBranch()).size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getFalseBranch()).size());
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
		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getPredicate()).size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getTrueBranch()).size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getFalseBranch()).size());
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

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), possibleType.getType());
		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.WARNING,
				"The predicate may evaluate to a value that is not a boolean type ([java.lang.Boolean, java.lang.Object]).",
				0, 38);
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getPredicate()).size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getTrueBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getTrueBranch()).size());
		assertNotNull(validationResult.getPossibleTypes(((Conditional)ast).getFalseBranch()));
		assertEquals(0, validationResult.getMessages(((Conditional)ast).getFalseBranch()).size());
	}

	@Test
	public void testLetMaskingVariable() {
		final IValidationResult validationResult = engine.validate("let stuff = self in stuff",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(1, possibleTypes.size());
		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(1, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertValidationMessage(validationResult.getMessages(((Let)ast).getBindings().get(0)).get(0),
				ValidationMessageLevel.WARNING, "Variable stuff overrides an existing value.", 4, 16);
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBody()).size());
	}

	@Test
	public void testLetMaskingBinding() {
		final IValidationResult validationResult = engine.validate("let a = 1, a = 2 in self", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(0, stripNothingTypes(possibleTypes).size());
		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(true, possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), possibleType.getType());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertEquals(1, validationResult.getMessages(((Let)ast).getBindings().get(1)).size());
		assertValidationMessage(validationResult.getMessages(((Let)ast).getBindings().get(1)).get(0),
				ValidationMessageLevel.WARNING, "Variable a overrides an existing value.", 11, 16);
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(1).getValue()).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBody()).size());
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

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBody()).size());
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

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void typeSetLiteralDuplicates() {
		final IValidationResult validationResult = engine.validate(
				"{ecore::EClass | ecore::EPackage | ecore::EAttribute | ecore::EPackage}", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
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

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.WARNING,
				"EClassifierLiteral=EPackage is duplicated in the type set literal.", 0, 71);
	}

	@Test
	public void testLetBindingCompatibleType() {
		final IValidationResult validationResult = engine.validate(
				"let newVar : ecore::EClass = self in newVar", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)possibleType).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBody()).size());
	}

	@Test
	public void testLetBindingCompatibleTypeTypeSetLiteral() {
		final IValidationResult validationResult = engine.validate(
				"let newVar : {ecore::EPackage | ecore::EClass} = self in newVar", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)possibleType).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getType()).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBody()).size());
	}

	@Test
	public void testLetBindingIncompatibleType() {
		final IValidationResult validationResult = engine.validate(
				"let newVar : ecore::EPackage = self in newVar", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)possibleType).getType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [EClassifier=EPackage].", 39, 45);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getType()).size());
		assertEquals(1, validationResult.getMessages(((Let)ast).getBody()).size());
		assertValidationMessage(validationResult.getMessages(((Let)ast).getBody()).get(0),
				ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [EClassifier=EPackage].", 39, 45);
	}

	@Test
	public void testLetBindingInvalidType() {
		final IValidationResult validationResult = engine.validate(
				"let newVar : invalid::Type = self in newVar", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)possibleType).getType());

		assertEquals(2, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"invalid type literal invalid::Type", 13, 26);
		assertValidationMessage(validationResult.getMessages().get(1), ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [].", 37, 43);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertEquals(1, validationResult.getMessages(((Let)ast).getBindings().get(0).getType()).size());
		assertValidationMessage(validationResult.getMessages(((Let)ast).getBindings().get(0).getType()).get(
				0), ValidationMessageLevel.ERROR, "invalid type literal invalid::Type", 13, 26);
		assertEquals(1, validationResult.getMessages(((Let)ast).getBody()).size());
		assertValidationMessage(validationResult.getMessages(((Let)ast).getBody()).get(0),
				ValidationMessageLevel.WARNING, "EClassifier=EClass is incompatible with declaration [].", 37,
				43);
	}

	@Test
	public void testLetBindingIncompatibleTypeTypeSetLiteral() {
		final IValidationResult validationResult = engine.validate(
				"let newVar : {ecore::EPackage | ecore::EReference} = self in newVar", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)possibleType).getType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [EClassifier=EPackage, EClassifier=EReference].",
				61, 67);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getType()).size());
		assertEquals(1, validationResult.getMessages(((Let)ast).getBody()).size());
		assertValidationMessage(validationResult.getMessages(((Let)ast).getBody()).get(0),
				ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [EClassifier=EPackage, EClassifier=EReference].",
				61, 67);
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
				"Couldn't find the 'notAVariable' variable", 32, 44);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBody()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Let)ast).getBody()).getArguments().get(0))
				.size());
		assertEquals(1, validationResult.getMessages(((Call)((Let)ast).getBody()).getArguments().get(1))
				.size());
		assertValidationMessage(validationResult.getMessages(((Call)((Let)ast).getBody()).getArguments().get(
				1)).get(0), ValidationMessageLevel.ERROR, "Couldn't find the 'notAVariable' variable", 32,
				44);
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
				"Couldn't find the 'notAVariable' variable", 13, 25);
		assertValidationMessage(validationResult.getMessages().get(1), ValidationMessageLevel.ERROR,
				"The newVar variable has no types", 29, 35);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0)).size());
		assertEquals(1, validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).size());
		assertValidationMessage(validationResult.getMessages(((Let)ast).getBindings().get(0).getValue()).get(
				0), ValidationMessageLevel.ERROR, "Couldn't find the 'notAVariable' variable", 13, 25);
		assertEquals(0, validationResult.getMessages(((Let)ast).getBindings().get(0).getType()).size());
		assertEquals(1, validationResult.getMessages(((Let)ast).getBody()).size());
		assertValidationMessage(validationResult.getMessages(((Let)ast).getBody()).get(0),
				ValidationMessageLevel.ERROR, "The newVar variable has no types", 29, 35);
	}

	@Test
	public void eInverseFeatureNameStringLiteral() {
		final IValidationResult validationResult = engine.validate("self.eInverse('ePackage')",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void eInverseFeatureNameExpression() {
		final IValidationResult validationResult = engine.validate("self.eInverse('ePac'+'kage')",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(8, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEOperation(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEReference(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEStructuralFeature(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getETypedElement(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEGenericType(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void eInverse() {
		final IValidationResult validationResult = engine.validate("self.eInverse()", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(8, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEOperation(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEReference(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEStructuralFeature(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getETypedElement(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEGenericType(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void eInverseFiltered() {
		final IValidationResult validationResult = engine.validate("self.eInverse(ecore::EStructuralFeature)",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEReference(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());
		possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEStructuralFeature(), ((EClassifierType)((SetType)possibleType)
				.getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void booleanLambdaCheck_484375() {
		final IValidationResult validationResult = engine.validate("Sequence{'hello'}->any(i | i = 'hello')",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(String.class, ((ClassType)possibleType).getType());

		possibleTypes = validationResult.getPossibleTypes(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression());
		assertEquals(1, possibleTypes.size());
		it = possibleTypes.iterator();
		possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Boolean.class, ((ClassType)possibleType).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
	}

	@Test
	public void variableDefinitionCompatibleType() {
		final IValidationResult validationResult = engine.validate(
				"self->select(newVar : ecore::EClass | newVar <> null)", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((SetType)possibleType).getCollectionType()
				.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getExpression()).size());
		assertEquals(0, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getType()).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());
	}

	@Test
	public void variableDefinitionCompatibleTypeTypeSetLiteral() {
		final IValidationResult validationResult = engine.validate(
				"self->select(newVar : {ecore::EPackage | ecore::EClass} | newVar <> null)", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((SetType)possibleType).getCollectionType()
				.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getExpression()).size());
		assertEquals(0, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getType()).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());
	}

	@Test
	public void variableDefinitionIncompatibleType() {
		final IValidationResult validationResult = engine.validate(
				"self->select(newVar : ecore::EPackage | newVar <> null)", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((SetType)possibleType).getCollectionType()
				.getType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [EClassifier=EPackage].", 40, 46);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getExpression()).size());
		assertEquals(0, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getType()).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(1, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertValidationMessage(validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(
				1)).getExpression()).getArguments().get(0)).get(0), ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [EClassifier=EPackage].", 40, 46);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());
	}

	@Test
	public void variableDefinitionInvalidType() {
		final IValidationResult validationResult = engine.validate(
				"self->select(newVar : invalid::Type | newVar <> null)", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((SetType)possibleType).getCollectionType()
				.getType());

		assertEquals(2, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"invalid type literal invalid::Type", 22, 35);
		assertValidationMessage(validationResult.getMessages().get(1), ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [].", 38, 44);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getExpression()).size());
		assertEquals(1, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getType()).size());
		assertValidationMessage(validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast)
				.getArguments().get(1)).getParameters().get(0)).getType()).get(0),
				ValidationMessageLevel.ERROR, "invalid type literal invalid::Type", 22, 35);
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(1, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertValidationMessage(validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(
				1)).getExpression()).getArguments().get(0)).get(0), ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [].", 38, 44);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());
	}

	@Test
	public void variableDefinitionIncompatibleTypeTypeSetLiteral() {
		final IValidationResult validationResult = engine.validate(
				"self->select(newVar : {ecore::EPackage | ecore::EReference} | newVar <> null)",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((SetType)possibleType).getCollectionType()
				.getType());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [EClassifier=EPackage, EClassifier=EReference].",
				62, 68);

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getParameters().get(0)).size());
		assertEquals(0, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getExpression()).size());
		assertEquals(0, validationResult.getMessages(((VariableDeclaration)((Lambda)((Call)ast).getArguments()
				.get(1)).getParameters().get(0)).getType()).size());
		assertEquals(0, validationResult.getMessages(((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).size());
		assertEquals(1, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(0)).size());
		assertValidationMessage(validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(
				1)).getExpression()).getArguments().get(0)).get(0), ValidationMessageLevel.WARNING,
				"EClassifier=EClass is incompatible with declaration [EClassifier=EPackage, EClassifier=EReference].",
				62, 68);
		assertEquals(0, validationResult.getMessages(((Call)((Lambda)((Call)ast).getArguments().get(1))
				.getExpression()).getArguments().get(1)).size());
	}

	@Test
	public void testCollectionCallOnNull_toString() {
		final IValidationResult validationResult = engine.validate("null->toString()", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(String.class, possibleType.getType());
	}

	@Test
	public void testCollectionCallOnNull_size() {
		final IValidationResult validationResult = engine.validate("null->size()", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Integer.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void testCollectionCallOnNull_first() {
		final IValidationResult validationResult = engine.validate("null->first()", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"The Collection was empty due to a null value being wrapped as a Collection.", 4, 13);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"The Collection was empty due to a null value being wrapped as a Collection.", 4, 13);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
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

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
	}

	@Test
	public void eOperationLookupNoEClassifierForClassType() {
		final Set<IType> nonEMFTypes = new LinkedHashSet<IType>();
		nonEMFTypes.add(new ClassType(queryEnvironment, Query.class));
		variableTypes.put("nonEMF", nonEMFTypes);

		final IValidationResult validationResult = engine.validate("self.triggerEOperationLookUp(nonEMF)",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();

		Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);
		assertEquals(1, stripNothingTypes(possibleTypes).size());
		assertEquals(0, possibleTypes.size());

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'triggerEOperationLookUp(EClassifier=EClass,org.eclipse.acceleo.query.runtime.Query)' service",
				4, 36);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"Couldn't find the 'triggerEOperationLookUp(EClassifier=EClass,org.eclipse.acceleo.query.runtime.Query)' service",
				4, 36);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void collectionTypeLiteralSequence() {
		final IValidationResult validationResult = engine.validate("Sequence(String)", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SequenceType);
		assertTrue(((SequenceType)possibleType).getCollectionType() instanceof ClassType);
		assertEquals(String.class, ((SequenceType)possibleType).getCollectionType().getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((CollectionTypeLiteral)ast).getElementType()).size());
	}

	@Test
	public void collectionTypeLiteralOrderedSet() {
		final IValidationResult validationResult = engine.validate("OrderedSet(String)", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SetType);
		assertTrue(((SetType)possibleType).getCollectionType() instanceof ClassType);
		assertEquals(String.class, ((SetType)possibleType).getCollectionType().getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((CollectionTypeLiteral)ast).getElementType()).size());
	}

	@Test
	public void oclAsTypeManyTypesNoneMatching() {
		final IValidationResult validationResult = engine.validate("self.eContainer().oclAsType(ecore::EInt)",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertEquals(1, possibleTypes.size());

		assertNothingType("Nothing will be left after calling oclAsType:\n"
				+ "EClassifier=EPackage is not compatible with type EClassifierLiteral=EInt\n"
				+ "EClassifier=EAnnotation is not compatible with type EClassifierLiteral=EInt",
				possibleType);

		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.ERROR,
				"Nothing will be left after calling oclAsType:\n"
						+ "EClassifier=EPackage is not compatible with type EClassifierLiteral=EInt\n"
						+ "EClassifier=EAnnotation is not compatible with type EClassifierLiteral=EInt", 17,
				40);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.ERROR,
				"Nothing will be left after calling oclAsType:\n"
						+ "EClassifier=EPackage is not compatible with type EClassifierLiteral=EInt\n"
						+ "EClassifier=EAnnotation is not compatible with type EClassifierLiteral=EInt", 17,
				40);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(0)).getArguments()
				.get(0)).size());
	}

	@Test
	public void eGetWithStringLiteral() {
		final IValidationResult validationResult = engine.validate("self.eGet('name')", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void eGetWithExpression() {
		final IValidationResult validationResult = engine.validate("self.eGet('na'+'me')", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Object.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)((Call)ast).getArguments().get(1)).getArguments()
				.get(1)).size());
	}

	@Test
	public void eOperationNoReflectionTest() {
		final IValidationResult validationResult = engine.validate(
				"noReflexion.eOperationNoReflection('text')", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void eOperationNoReflectionSubParameterTypeTest() {
		final IValidationResult validationResult = engine.validate(
				"noReflexion.eOperationNoReflectionSubParameterType(ecore::EClass)", variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void eOperationNoReflectionListParameterTest() {
		final IValidationResult validationResult = engine.validate(
				"noReflexion.eOperationNoReflectionListParameter(Sequence{ecore::EClass, ecore::EOperation})",
				variableTypes);

		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void javaMethodReceiverServiceNoArg() {
		Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new ClassType(queryEnvironment, ReceiverServices.class));
		variableTypes.put("self", selfTypes);
		ServiceUtils.registerServices(queryEnvironment, ServiceUtils.getReceiverServices(queryEnvironment,
				ReceiverServices.class));

		final IValidationResult validationResult = engine.validate("self.noArg()", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(String.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void javaMethodReceiverServiceArg() {
		Set<IType> selfTypes = new LinkedHashSet<IType>();
		selfTypes.add(new ClassType(queryEnvironment, ReceiverServices.class));
		variableTypes.put("self", selfTypes);
		ServiceUtils.registerServices(queryEnvironment, ServiceUtils.getReceiverServices(queryEnvironment,
				ReceiverServices.class));

		final IValidationResult validationResult = engine.validate("self.arg('arg')", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(String.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(1)).size());
	}

	@Test
	public void dynamicEClass() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();

		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("dynamic");
		ePkg.setNsURI("dynamic");
		ePkg.setNsPrefix("dynamic");
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls.setName("DynamicEClass");
		ePkg.getEClassifiers().add(eCls);
		final EAttribute eAttribute = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eAttribute.setName("dynamicEAttribute");
		eAttribute.setEType(EcorePackage.eINSTANCE.getEString());
		eCls.getEStructuralFeatures().add(eAttribute);

		queryEnvironment.registerEPackage(ePkg);

		selfType.add(new EClassifierType(queryEnvironment, eCls));
		types.put("self", selfType);

		final Set<IType> oldSelfType = variableTypes.put("self", selfType);

		final IValidationResult validationResult = engine.validate("self", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		queryEnvironment.removeEPackage(ePkg);
		variableTypes.put("self", oldSelfType);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(eCls, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
	}

	@Test
	public void dynamicEAttribute() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();

		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("dynamic");
		ePkg.setNsURI("dynamic");
		ePkg.setNsPrefix("dynamic");
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls.setName("DynamicEClass");
		ePkg.getEClassifiers().add(eCls);
		final EAttribute eAttribute = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eAttribute.setName("dynamicEAttribute");
		eAttribute.setEType(EcorePackage.eINSTANCE.getEString());
		eCls.getEStructuralFeatures().add(eAttribute);

		queryEnvironment.registerEPackage(ePkg);

		selfType.add(new EClassifierType(queryEnvironment, eCls));
		types.put("self", selfType);

		final Set<IType> oldSelfType = variableTypes.put("self", selfType);

		final IValidationResult validationResult = engine.validate("self.dynamicEAttribute", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		queryEnvironment.removeEPackage(ePkg);
		variableTypes.put("self", oldSelfType);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void dynamicEOperation() {
		final Map<String, Set<IType>> types = new LinkedHashMap<String, Set<IType>>();
		final Set<IType> selfType = new LinkedHashSet<IType>();

		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("dynamic");
		ePkg.setNsURI("dynamic");
		ePkg.setNsPrefix("dynamic");
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls.setName("DynamicEClass");
		ePkg.getEClassifiers().add(eCls);
		final EOperation eOperation = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		eOperation.setName("dynamicEOperation");
		eOperation.setEType(EcorePackage.eINSTANCE.getEString());
		eCls.getEOperations().add(eOperation);

		queryEnvironment.registerEPackage(ePkg);

		selfType.add(new EClassifierType(queryEnvironment, eCls));
		types.put("self", selfType);

		final Set<IType> oldSelfType = variableTypes.put("self", selfType);

		final IValidationResult validationResult = engine.validate("self.dynamicEOperation()", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		queryEnvironment.removeEPackage(ePkg);
		variableTypes.put("self", oldSelfType);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEString(), possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void allInstancesTestNoRootProvider() {
		final IQueryEnvironment queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		queryEnvironment.registerEPackage(NooperationreflectionPackage.eINSTANCE);
		final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, EObjectServices.class);
		ServiceUtils.registerServices(queryEnvironment, services);
		final QueryValidationEngine engine = new QueryValidationEngine(queryEnvironment);

		final IValidationResult validationResult = engine.validate("ecore::EPackage.allInstances()",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SequenceType);
		assertNothingType("Nothing will be left after calling allInstances:\n"
				+ "No IRootEObjectProvider registered", ((SequenceType)possibleType).getCollectionType());

		assertNothingType("Nothing will be left after calling allInstances:\n"
				+ "No IRootEObjectProvider registered", ((SequenceType)possibleType).getCollectionType());
		assertEquals(1, validationResult.getMessages().size());
		assertValidationMessage(validationResult.getMessages().get(0), ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling allInstances:\n"
						+ "No IRootEObjectProvider registered", 15, 30);

		assertEquals(1, validationResult.getMessages(ast).size());
		assertValidationMessage(validationResult.getMessages(ast).get(0), ValidationMessageLevel.INFO,
				"Empty collection: Nothing will be left after calling allInstances:\n"
						+ "No IRootEObjectProvider registered", 15, 30);
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void allInstancesTest() {
		final IValidationResult validationResult = engine.validate("ecore::EPackage.allInstances()",
				variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SequenceType);
		assertTrue(((SequenceType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), ((EClassifierType)((SequenceType)possibleType)
				.getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void allInstancesSetTest() {
		final IValidationResult validationResult = engine.validate(
				"{ecore::EPackage | ecore::EClass}->allInstances()", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(2, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof SequenceType);
		assertTrue(((SequenceType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), ((EClassifierType)((SequenceType)possibleType)
				.getCollectionType()).getType());

		possibleType = it.next();
		assertTrue(possibleType instanceof SequenceType);
		assertTrue(((SequenceType)possibleType).getCollectionType() instanceof EClassifierType);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), ((EClassifierType)((SequenceType)possibleType)
				.getCollectionType()).getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
	}

	@Test
	public void bug152() {
		final IValidationResult validationResult = engine.validate(
				"Sequence{'1','2'}.toInteger()->first() > '1'.toInteger()", variableTypes);
		final Expression ast = validationResult.getAstResult().getAst();
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(ast);

		assertEquals(1, possibleTypes.size());
		final Iterator<IType> it = possibleTypes.iterator();
		IType possibleType = it.next();
		assertTrue(possibleType instanceof ClassType);
		assertEquals(Boolean.class, possibleType.getType());

		assertEquals(0, validationResult.getMessages().size());

		assertEquals(0, validationResult.getMessages(ast).size());
		assertEquals(0, validationResult.getMessages(((Call)ast).getArguments().get(0)).size());
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
		assertEquals(expectedMessage, unifiedLines(message.getMessage()));
		assertEquals(expectedStartPosition, message.getStartPosition());
		assertEquals(expectedEndPosition, message.getEndPosition());
	}

	/**
	 * Removes all "Nothing" types from the given collection and returns them.
	 * 
	 * @param types
	 *            The list of types to trim.
	 * @return The list of NothingTypes we found the input collection.
	 */
	public List<IType> stripNothingTypes(Collection<IType> types) {
		List<IType> nothings = new ArrayList<IType>();
		Iterator<IType> typeItr = types.iterator();
		while (typeItr.hasNext()) {
			IType type = typeItr.next();
			if (type instanceof NothingType) {
				nothings.add(type);
				typeItr.remove();
			}
		}
		return nothings;
	}

	/**
	 * Asserts that a provided type is Nothing Type with expected message.
	 * 
	 * @param expectedMessage
	 *            to assert
	 * @param evaluated
	 *            element to test
	 */
	public static void assertNothingType(String expectedMessage, IType evaluated) {
		assertTrue(evaluated instanceof NothingType);
		assertEquals("Unexpected NothingType message", expectedMessage, unifiedLines(((NothingType)evaluated)
				.getMessage()));
	}

	/**
	 * Clean message from specific line separator, always using '\n'.
	 * 
	 * @param text
	 *            to clean
	 * @return cleaned text
	 */
	public static String unifiedLines(String text) {
		if (text == null || text.length() == 0) {
			return null;
		}
		return text.replace(System.lineSeparator(), "\n");
	}
}
