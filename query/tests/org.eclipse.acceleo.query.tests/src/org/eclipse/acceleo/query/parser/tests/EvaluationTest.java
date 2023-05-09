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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.impl.QueryEvaluationEngine;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Food;
import org.eclipse.acceleo.query.tests.nestedpackages.root.RootEClass;
import org.eclipse.acceleo.query.tests.nestedpackages.root.RootFactory;
import org.eclipse.acceleo.query.tests.nestedpackages.root.RootPackage;
import org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildEClass;
import org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildFactory;
import org.eclipse.acceleo.query.tests.nestedpackages.root.child.ChildPackage;
import org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.GrandChildEClass;
import org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.Grand_childFactory;
import org.eclipse.acceleo.query.tests.nestedpackages.root.child.grand_child.Grand_childPackage;
import org.eclipse.acceleo.query.tests.services.EObjectServices;
import org.eclipse.acceleo.query.tests.services.ReceiverServices;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import Real.Keyword;
import Real.RealPackage;
import nooperationreflection.NoOperationReflection;
import nooperationreflection.NooperationreflectionPackage;

public class EvaluationTest {

	QueryEvaluationEngine engine;

	IQueryEnvironment queryEnvironment;

	IQueryBuilderEngine builder;

	@Before
	public void setup() {
		queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		queryEnvironment.registerEPackage(RootPackage.eINSTANCE);
		queryEnvironment.registerEPackage(NooperationreflectionPackage.eINSTANCE);
		final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, EObjectServices.class);
		ServiceUtils.registerServices(queryEnvironment, services);
		engine = new QueryEvaluationEngine(queryEnvironment);
		builder = new QueryBuilderEngine();
	}

	@Test
	public void testNestedPackages() {
		RootFactory rootFactory = RootPackage.eINSTANCE.getRootFactory();
		RootEClass rootEClass = rootFactory.createRootEClass();

		ChildFactory childFactory = ChildPackage.eINSTANCE.getChildFactory();
		ChildEClass childEClass = childFactory.createChildEClass();

		Grand_childFactory grand_childFactory = Grand_childPackage.eINSTANCE.getGrand_childFactory();
		GrandChildEClass grandChildEClass = grand_childFactory.createGrandChildEClass();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("rootEClass", rootEClass);
		variables.put("childEClass", childEClass);
		variables.put("grandChildEClass", grandChildEClass);

		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build(
				"rootEClass.oclIsKindOf(root::RootEClass)"), variables));
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build(
				"childEClass.oclIsKindOf(child::ChildEClass)"), variables));
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build(
				"grandChildEClass.oclIsKindOf(grand_child::GrandChildEClass)"), variables));
	}

	@Test
	public void variableTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("x", 1);
		assertOKResultEquals(Integer.valueOf(1), engine.eval(builder.build("x"), variables));
	}

	@Test
	public void featureAccessTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.eINSTANCE);
		assertOKResultEquals("ecore", engine.eval(builder.build("self.name"), variables));
	}

	@Test
	public void featureAccessNotExistingFeatureTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.eINSTANCE);

		final EvaluationResult result = engine.eval(builder.build("self.notExistingFeature"), variables);

		assertEquals(null, result.getResult());

		assertEquals(Diagnostic.WARNING, result.getDiagnostic().getSeverity());
		assertEquals(1, result.getDiagnostic().getChildren().size());

		assertEquals(Diagnostic.WARNING, result.getDiagnostic().getChildren().get(0).getSeverity());
		result.getDiagnostic().getChildren().get(0).getMessage().startsWith("aqlFeatureAccess");
		result.getDiagnostic().getChildren().get(0).getMessage().endsWith(
				"Feature notExistingFeature not found in EClass EPackage");
	}

	@Test
	public void intliteralTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(Integer.valueOf(2), engine.eval(builder.build("2"), variables));
	}

	@Test
	public void realliteralTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(Double.valueOf(1d), engine.eval(builder.build("1.0"), variables));
	}

	@Test
	public void trueliteralTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build("true"), variables));
	}

	@Test
	public void falseliteralTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(Boolean.FALSE, engine.eval(builder.build("false"), variables));
	}

	@Test
	public void stringliteralTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals("acceleo query is great", engine.eval(builder.build("'acceleo query is great'"),
				variables));
	}

	@Test
	public void lowerEqualTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build("1<=2"), variables));
	}

	@Test
	public void lowerTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build("1<2"), variables));
	}

	@Test
	public void greaterEqualTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(Boolean.FALSE, engine.eval(builder.build("1>=2"), variables));
	}

	@Test
	public void greaterTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(Boolean.FALSE, engine.eval(builder.build("1>2"), variables));
	}

	@Test
	public void addTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.eINSTANCE);
		assertOKResultEquals("ecore.ecore", engine.eval(builder.build("self.nsPrefix + '.' + self.name"),
				variables));
	}

	@Test
	public void orTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.eINSTANCE);
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build(
				"self.nsPrefix = 'ecore' or self.name='autrechose'"), variables));
	}

	@Test
	public void andTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.eINSTANCE);
		assertOKResultEquals(Boolean.FALSE, engine.eval(builder.build(
				"self.nsPrefix = 'ecore' and self.name='autrechose'"), variables));
	}

	@Test
	public void notTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.eINSTANCE);
		// TODO check priorities. This test fails because "not" takes priority over "=".
		// boolean operations should probably have a lower priorty than other services
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build("not (self.name='autrechose')"),
				variables));
	}

	@Test
	public void multTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(Integer.valueOf(4), engine.eval(builder.build("2*2"), variables));
		assertOKResultEquals(Double.valueOf(4d), engine.eval(builder.build("2.0*2.0"), variables));
	}

	@Test
	public void compComplexTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.eINSTANCE);
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build("self.name.size()=self.nsPrefix.size()"),
				variables));
		assertOKResultEquals(Boolean.FALSE, engine.eval(builder.build(
				"self.name.size()<self.nsPrefix.size()-1"), variables));
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build(
				"self.name.size()<self.nsPrefix.size()+1"), variables));
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build(
				"self.name.size()+2>self.nsPrefix.size()+1"), variables));
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build(
				"self.name.size()<=self.nsPrefix.size()+1"), variables));
		assertOKResultEquals(Boolean.TRUE, engine.eval(builder.build(
				"self.name.size()>=self.nsPrefix.size()-1"), variables));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void minusTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.Literals.ECLASS);
		EvaluationResult result = engine.eval(builder.build(
				"self.eAllSuperTypes->including(self)-self.eAllSuperTypes"), variables);
		assertTrue(result.getResult() instanceof List);
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
		List<Object> listResult = (List<Object>)result.getResult();
		assertEquals(1, listResult.size());
		assertEquals(EcorePackage.Literals.ECLASS, listResult.get(0));
	}

	@Test
	public void testSelect0() {
		String expr = "self.oclAsType(ecore::EClass).eAllSuperTypes->select(e | e.oclIsKindOf(ecore::EClass))";
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.Literals.ECLASS);
		EvaluationResult result = engine.eval(builder.build(expr), variables);
		assertTrue(result.getResult() instanceof List);
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
		List<?> listResult = (List<?>)result.getResult();
		assertTrue(listResult.contains(EcorePackage.Literals.ECLASSIFIER));
		assertTrue(listResult.contains(EcorePackage.Literals.ENAMED_ELEMENT));
		assertTrue(listResult.contains(EcorePackage.Literals.EMODEL_ELEMENT));
	}

	@Test
	public void nullTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		EvaluationResult result = engine.eval(builder.build(null), variables);
		assertOKResultEquals(null, result);
	}

	@Test
	public void emtpyTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		EvaluationResult result = engine.eval(builder.build(""), variables);
		assertOKResultEquals(null, result);
	}

	@Test
	public void nullLiteralTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(null, engine.eval(builder.build("null"), variables));
	}

	@Test
	public void serviceTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.eINSTANCE.getEClass());
		assertOKResultEquals(Integer.valueOf(1), engine.eval(builder.build("self.someService('a')"),
				variables));
	}

	@Test
	public void eOperationTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		final Food self = AnydslPackage.eINSTANCE.getAnydslFactory().createFood();
		variables.put("self", self);
		assertOKResultEquals("text", engine.eval(builder.build("self.preferredLabel('text')"), variables));
	}

	@Test
	public void eOperationEObjectParameterTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		final Food self = AnydslPackage.eINSTANCE.getAnydslFactory().createFood();
		self.setName("self berry");
		final Food other = AnydslPackage.eINSTANCE.getAnydslFactory().createFood();
		other.setName("other berry");
		variables.put("self", self);
		variables.put("other", other);
		assertOKResultEquals(true, engine.eval(builder.build("self.identity(other) == other"), variables));
	}

	@Test
	public void eOperationNoReflectionTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		final NoOperationReflection self = NooperationreflectionPackage.eINSTANCE
				.getNooperationreflectionFactory().createNoOperationReflection();
		variables.put("self", self);
		assertOKResultEquals("text", engine.eval(builder.build("self.eOperationNoReflection('text')"),
				variables));
	}

	@Test
	public void eOperationNoReflectionSubParameterTypeTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		final NoOperationReflection self = NooperationreflectionPackage.eINSTANCE
				.getNooperationreflectionFactory().createNoOperationReflection();
		variables.put("self", self);
		assertOKResultEquals("EClass", engine.eval(builder.build(
				"self.eOperationNoReflectionSubParameterType(ecore::EClass)"), variables));
	}

	@Test
	public void eOperationNoReflectionListParameterTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		final NoOperationReflection self = NooperationreflectionPackage.eINSTANCE
				.getNooperationreflectionFactory().createNoOperationReflection();
		variables.put("self", self);
		assertOKResultEquals("EClassEOperation", engine.eval(builder.build(
				"self.eOperationNoReflectionListParameter(Sequence{ecore::EClass, ecore::EOperation})"),
				variables));
	}

	@Test
	public void eOperationNoReflectionEObjectParameterTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		final NoOperationReflection self = NooperationreflectionPackage.eINSTANCE
				.getNooperationreflectionFactory().createNoOperationReflection();
		final NoOperationReflection other = NooperationreflectionPackage.eINSTANCE
				.getNooperationreflectionFactory().createNoOperationReflection();
		variables.put("self", self);
		variables.put("other", other);
		assertOKResultEquals(true, engine.eval(builder.build("self.identity(other) == other"), variables));
	}

	@Test
	public void serviceNullParameterTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", EcorePackage.eINSTANCE.getEClass());
		assertOKResultEquals(1, engine.eval(builder.build("self.someService(null)"), variables));
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsEClassifier() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(EcorePackage.eINSTANCE.getEClass(), engine.eval(builder.build("ecore::EClass"),
				variables));
	}

	@Test
	public void enumLiteralOrEClassifierThreeSegments() {
		Map<String, Object> variables = new HashMap<String, Object>();
		assertOKResultEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other").getInstance(), engine
				.eval(builder.build("anydsl::Part::Other"), variables));
	}

	@Test
	public void enumLiteralNotExisting() {
		Map<String, Object> variables = new HashMap<String, Object>();

		final EvaluationResult result = engine.eval(builder.build("anydsl::Part::NotExisting"), variables);

		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getSeverity());
		assertEquals(1, result.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getChildren().get(0).getSeverity());
		String message = result.getDiagnostic().getChildren().get(0).getMessage();
		assertEquals("Invalid enum literal.", message);
		assertNull(result.getResult());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSetInExtensionLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("self", EcorePackage.Literals.ECLASS);

		final EvaluationResult result = engine.eval(builder.build("OrderedSet{self, self, true, false}"),
				varDefinitions);
		assertTrue(result.getResult() instanceof Set);
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
		Set<Object> setResult = (Set<Object>)result.getResult();
		assertEquals(3, setResult.size());
		Iterator<Object> it = setResult.iterator();
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(Boolean.TRUE, it.next());
		assertEquals(Boolean.FALSE, it.next());
		assertFalse(it.hasNext());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSequenceInExtensionLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("self", EcorePackage.Literals.ECLASS);

		final EvaluationResult result = engine.eval(builder.build("Sequence{self, self, true, false}"),
				varDefinitions);
		assertTrue(result.getResult() instanceof List);
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
		List<Object> listResult = (List<Object>)result.getResult();
		assertEquals(4, listResult.size());
		Iterator<Object> it = listResult.iterator();
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(Boolean.TRUE, it.next());
		assertEquals(Boolean.FALSE, it.next());
		assertFalse(it.hasNext());
	}

	/**
	 * This test ensures variable definitions are pushed into the scope of lambda that are defined within the
	 * variable scope.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testVariableUsedInLambda() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("selector", "str");
		EvaluationResult result = engine.eval(builder.build(
				"Sequence{'str1','str2','out'}->select(i | i.startsWith(selector))"), varDefinitions);
		assertTrue(result.getResult() instanceof List);
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
		final List<Object> listResult = (List<Object>)result.getResult();
		assertEquals(2, listResult.size());
		assertEquals("str1", listResult.get(0));
		assertEquals("str2", listResult.get(1));
	}

	@Test
	public void testConditionnalTrue() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals("trueBranch", engine.eval(builder.build(
				"if true then 'trueBranch' else 'falseBranch' endif"), varDefinitions));
	}

	@Test
	public void testConditionnalFalse() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals("falseBranch", engine.eval(builder.build(
				"if false then 'trueBranch' else 'falseBranch' endif"), varDefinitions));
	}

	@Test
	public void testConditionnalCompletePredicate() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("x", Integer.valueOf(1));
		assertOKResultEquals("trueBranch", engine.eval(builder.build(
				"if x > 0 then 'trueBranch' else 'falseBranch' endif"), varDefinitions));
		varDefinitions.put("x", Integer.valueOf(0));
		assertOKResultEquals("falseBranch", engine.eval(builder.build(
				"if x > 0 then 'trueBranch' else 'falseBranch' endif"), varDefinitions));
	}

	@Test
	public void testConditionnalNotBoolean() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		EvaluationResult result = engine.eval(builder.build(
				"if 'notboolean' then 'trueBranch' else 'falseBranch' endif"), varDefinitions);
		assertEquals(null, result.getResult());
		assertEquals(Diagnostic.WARNING, result.getDiagnostic().getSeverity());
		assertEquals(1, result.getDiagnostic().getChildren().size());
		String message = result.getDiagnostic().getChildren().get(0).getMessage();
		assertTrue(message.contains("Conditional"));
		assertTrue(message.contains("boolean"));
		assertTrue(message.contains("notboolean"));
	}

	@Test
	public void testLetOneDefinition() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals("prefixsuffix", engine.eval(builder.build(
				"let x='prefix' in x.concat('suffix')"), varDefinitions));
	}

	@Test
	public void testLetBasic() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		assertOKResultEquals("prefixsuffix", engine.eval(builder.build(
				"let x='prefix', y='suffix' in x.concat(y)"), varDefinitions));
	}

	@Test
	public void testNotRecursiveLet() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("x", "suffix");
		assertOKResultEquals("prefixsuffix", engine.eval(builder.build("let x='prefix', y=x in x.concat(y)"),
				varDefinitions));
	}

	@Test
	public void letWithUnusedNothingBoundTest() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		EvaluationResult result = engine.eval(builder.build("let x='prefix', y=x, z='suffix' in x.concat(z)"),
				varDefinitions);
		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getSeverity());
		assertEquals(1, result.getDiagnostic().getChildren().size());
		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getChildren().get(0).getSeverity());
		String message = result.getDiagnostic().getChildren().get(0).getMessage();
		assertTrue(message.contains("Couldn't find the 'x' variable"));
		assertEquals("prefixsuffix", result.getResult());
	}

	@Test
	public void letWithNothingBoundTest() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		EvaluationResult result = engine.eval(builder.build(
				"let x='prefix', y=x, z='suffix' in x.concat(z).concat(y)"), varDefinitions);
		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getSeverity());
		assertEquals(2, result.getDiagnostic().getChildren().size());
		String message1 = result.getDiagnostic().getChildren().get(0).getMessage();
		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getChildren().get(0).getSeverity());
		assertTrue(message1.contains("Couldn't find the 'x' variable"));
		assertEquals(Diagnostic.WARNING, result.getDiagnostic().getChildren().get(1).getSeverity());
		String message2 = result.getDiagnostic().getChildren().get(1).getMessage();
		assertTrue(message2.contains("Couldn't find the 'concat"));
		assertEquals(null, result.getResult());
	}

	@Test
	public void letWithAffixNotation() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		varDefinitions.put("x", "suffix");
		assertOKResultEquals("prefixsuffix", engine.eval(builder.build("let x='prefix', y='suffix' in x+y"),
				varDefinitions));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void typeSetLiteral() {
		Map<String, Object> varDefinitions = new HashMap<String, Object>();
		EvaluationResult result = engine.eval(builder.build(
				"{ecore::EClass | ecore::EPackage | ecore::EAttribute}"), varDefinitions);

		assertTrue(result.getResult() instanceof Set);
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
		Set<Object> listResult = (Set<Object>)result.getResult();
		assertEquals(3, listResult.size());
		Iterator<Object> it = listResult.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEClass(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEAttribute(), it.next());
	}

	@Test
	public void callEObjectMethod() {
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();
		EClass class1 = EcoreFactory.eINSTANCE.createEClass();
		EClass class2 = EcoreFactory.eINSTANCE.createEClass();
		pack.getEClassifiers().add(class1);
		pack.getEClassifiers().add(class2);

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", pack);
		EvaluationResult result = engine.eval(builder.build(
				"self.oclAsType(ecore::EObject).eCrossReferences()"), variables);

		assertEquals(pack.eCrossReferences(), result.getResult());
	}

	@Test
	public void isKindOfEObject() {
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", pack);
		EvaluationResult result = engine.eval(builder.build("self.oclIsKindOf(ecore::EObject)"), variables);

		assertEquals(Boolean.TRUE, result.getResult());
	}

	@Test
	public void sequenceToString_480753() {
		Map<String, Object> variables = new HashMap<String, Object>();

		final EvaluationResult result = engine.eval(builder.build("Sequence{'hello','world'}->toString()"),
				variables);

		assertEquals("helloworld", result.getResult());
	}

	@Test
	public void setToString_480753() {
		Map<String, Object> variables = new HashMap<String, Object>();

		final EvaluationResult result = engine.eval(builder.build("OrderedSet{'hello','world'}->toString()"),
				variables);

		assertEquals("helloworld", result.getResult());
	}

	@Test
	public void eOperationWithNothingParameter_487245() {
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", pack);
		EvaluationResult result = engine.eval(builder.build("self.eGet(notExisting)"), variables);

		assertEquals(null, result.getResult());

		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getSeverity());
		assertEquals(2, result.getDiagnostic().getChildren().size());

		assertEquals(Diagnostic.ERROR, result.getDiagnostic().getChildren().get(0).getSeverity());
		assertEquals("Couldn't find the 'notExisting' variable", result.getDiagnostic().getChildren().get(0)
				.getMessage());

		assertEquals(Diagnostic.WARNING, result.getDiagnostic().getChildren().get(1).getSeverity());
		assertEquals(
				"Couldn't find the 'eGet(EClassifier=EPackage,org.eclipse.acceleo.query.runtime.impl.Nothing)' service",
				result.getDiagnostic().getChildren().get(1).getMessage());
	}

	private void assertOKResultEquals(Object expected, EvaluationResult result) {
		assertEquals(expected, result.getResult());
		assertEquals(Diagnostic.OK, result.getDiagnostic().getSeverity());
		assertTrue(result.getDiagnostic().getChildren().isEmpty());
	}

	@Test
	public void emoji() {
		Map<String, Object> variables = new HashMap<String, Object>();
		EvaluationResult result = engine.eval(builder.build(
				"Sequence{'\u1F61C','\u1F62D','\u1F63D','\u1F1EB\u1F1F7'}->sep(' ')->toString()"), variables);

		assertEquals("\u1F61C \u1F62D \u1F63D \u1F1EB\u1F1F7", result.getResult());
	}

	@Test
	public void javaMethodReceiverServiceNoArg() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", new ReceiverServices());
		ServiceUtils.registerServices(queryEnvironment, ServiceUtils.getReceiverServices(queryEnvironment,
				ReceiverServices.class));
		EvaluationResult result = engine.eval(builder.build("self.noArg()"), variables);

		assertEquals("noArgResult", result.getResult());
	}

	@Test
	public void javaMethodReceiverServiceArg() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", new ReceiverServices());
		ServiceUtils.registerServices(queryEnvironment, ServiceUtils.getReceiverServices(queryEnvironment,
				ReceiverServices.class));
		EvaluationResult result = engine.eval(builder.build("self.arg('arg')"), variables);

		assertEquals("argResultarg", result.getResult());
	}

	@Test
	public void eClassifierWithKeyword() {
		Map<String, Object> variables = new HashMap<String, Object>();

		queryEnvironment.registerEPackage(RealPackage.eINSTANCE);

		EvaluationResult result = engine.eval(builder.build("_Real::_String"), variables);

		queryEnvironment.removeEPackage(RealPackage.eINSTANCE);

		assertEquals(RealPackage.eINSTANCE.getString(), result.getResult());
	}

	@Test
	public void eEnumLiteralWithKeyword() {
		Map<String, Object> variables = new HashMap<String, Object>();

		queryEnvironment.registerEPackage(RealPackage.eINSTANCE);

		EvaluationResult result = engine.eval(builder.build("_Real::_String::_Integer"), variables);

		queryEnvironment.removeEPackage(RealPackage.eINSTANCE);

		assertEquals(Real.String.INTEGER, result.getResult());
	}

	@Test
	public void eAttributeWithKeyword() {
		Map<String, Object> variables = new HashMap<String, Object>();
		Keyword self = RealPackage.eINSTANCE.getRealFactory().createKeyword();
		self.setIsUnique("is unique value");

		variables.put("self", self);

		queryEnvironment.registerEPackage(RealPackage.eINSTANCE);

		EvaluationResult result = engine.eval(builder.build("self._isUnique"), variables);

		queryEnvironment.removeEPackage(RealPackage.eINSTANCE);

		assertEquals("is unique value", result.getResult());
	}

	@Test
	public void eOperationWithKeyword() {
		Map<String, Object> variables = new HashMap<String, Object>();
		Keyword self = RealPackage.eINSTANCE.getRealFactory().createKeyword();

		variables.put("self", self);

		queryEnvironment.registerEPackage(RealPackage.eINSTANCE);

		EvaluationResult result = engine.eval(builder.build("self._select()"), variables);

		queryEnvironment.removeEPackage(RealPackage.eINSTANCE);

		assertEquals("Select EOperation called successfully", result.getResult());
	}

}
