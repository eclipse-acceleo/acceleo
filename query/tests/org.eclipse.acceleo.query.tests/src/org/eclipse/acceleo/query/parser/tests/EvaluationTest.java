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

import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.QueryEvaluationEngine;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.services.EObjectServices;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EvaluationTest {

	QueryEvaluationEngine engine;

	IQueryEnvironment queryEnvironment;

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		queryEnvironment = new QueryEnvironment(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);
		queryEnvironment.registerServicePackage(EObjectServices.class);
		engine = new QueryEvaluationEngine(queryEnvironment);

	}

	@Test
	public void variableTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("x", 1);
		assertEquals(1, engine.eval("x", variables));
	}

	@Test
	public void featureAccessTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE);
		assertEquals("ecore", engine.eval("self.name", variables));
	}

	@Test
	public void intliteralTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertEquals(2, engine.eval("2", variables));
	}

	@Test
	public void realliteralTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertEquals(1.0, engine.eval("1.0", variables));
	}

	@Test
	public void trueliteralTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertTrue((Boolean)engine.eval("true", variables));
	}

	@Test
	public void falseliteralTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertFalse((Boolean)engine.eval("false", variables));
	}

	@Test
	public void stringliteralTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertEquals("acceleo query is great", engine.eval("'acceleo query is great'", variables));
	}

	@Test
	public void lowerEqualTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertEquals(true, engine.eval("1<=2", variables));
	}

	@Test
	public void lowerTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertEquals(true, engine.eval("1<2", variables));
	}

	@Test
	public void greaterEqualTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertEquals(false, engine.eval("1>=2", variables));
	}

	@Test
	public void greaterTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertEquals(false, engine.eval("1>2", variables));
	}

	@Test
	public void addTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE);
		assertEquals("ecore.ecore", engine.eval("self.nsPrefix + '.' + self.name", variables));
	}

	@Test
	public void orTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE);
		assertTrue((Boolean)engine.eval("self.nsPrefix = 'ecore' or self.name='autrechose'", variables));
	}

	@Test
	public void andTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE);
		assertFalse((Boolean)engine.eval("self.nsPrefix = 'ecore' and self.name='autrechose'", variables));
	}

	@Test
	public void notTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE);
		assertFalse((Boolean)engine.eval("not self.name='autrechose'", variables));
	}

	@Test
	public void multTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertEquals(4, engine.eval("2*2", variables));
		assertEquals(4.0, engine.eval("2.0*2.0", variables));
	}

	@Test
	public void affichage() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE);
		System.out.println(engine.eval("self.nsPrefix.size()", variables));
		System.out.println(engine.eval("self.name.size()", variables));
	}

	@Test
	public void compComplexTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE);
		assertTrue((Boolean)engine.eval("self.name.size()=self.nsPrefix.size()", variables));
		assertFalse((Boolean)engine.eval("self.name.size()<self.nsPrefix.size()-1", variables));
		assertTrue((Boolean)engine.eval("self.name.size()<self.nsPrefix.size()+1", variables));
		assertTrue((Boolean)engine.eval("self.name.size()+2>self.nsPrefix.size()+1", variables));
		assertTrue((Boolean)engine.eval("self.name.size()<=self.nsPrefix.size()+1", variables));
		assertTrue((Boolean)engine.eval("self.name.size()>=self.nsPrefix.size()-1", variables));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void minusTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.Literals.ECLASS);
		Object result = engine.eval("self.eAllSuperTypes->including(self)-self.eAllSuperTypes", variables);
		assertTrue(result instanceof List);
		assertEquals(EcorePackage.Literals.ECLASS, ((List<Object>)result).get(0));
	}

	@Test
	public void testSelect0() {
		String expr = "self.oclAsType(ecore::EClass).eAllSuperTypes->select(e | e.oclIsKindOf(ecore::EClass))";
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.Literals.ECLASS);
		Object result = engine.eval(expr, variables);
		System.out.println(result);

	}

	@Test
	public void testSelect1() {
		String expr = "self.oclAsType(ecore::EClass).eAllSuperTypes->including(self).eInverse()->select(e | e.oclIsKindOf(ecore::EClass) or self.oclIsKindOf(ecore::EReference))->reject(e | e.oclAsType(ecore::EClass).eAllStructuralFeatures->includes(self))->notEmpty()";
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.Literals.ECLASS);
		Object result = engine.eval(expr, variables);

	}

	@Test
	public void nullTest() {
		Map<String, Object> variables = Maps.newHashMap();
		Object result = engine.eval(null, variables);
		assertEquals(null, result);
	}

	@Test
	public void emtpyTest() {
		Map<String, Object> variables = Maps.newHashMap();
		Object result = engine.eval("", variables);
		assertEquals(null, result);
	}

	@Test
	public void nullLiteralTest() {
		Map<String, Object> variables = Maps.newHashMap();
		assertEquals(null, engine.eval("null", variables));
	}

	@Test
	public void serviceTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE.getEClass());
		assertEquals(1, engine.eval("self.someService('a')", variables));
	}

	@Test
	public void serviceNullParameterTest() {
		Map<String, Object> variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE.getEClass());
		assertEquals(1, engine.eval("self.someService(null)", variables));
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsEClassifier() {
		Map<String, Object> variables = Maps.newHashMap();

		assertEquals(EcorePackage.eINSTANCE.getEClass(), engine.eval("ecore::EClass", variables));
	}

	@Test
	public void enumLiteralOrEClassifierTwoSegmentsEEnumLiteral() {
		Map<String, Object> variables = Maps.newHashMap();

		assertEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other").getInstance(), engine.eval(
				"Part::Other", variables));
	}

	@Test
	public void enumLiteralOrEClassifierThreeSegments() {
		Map<String, Object> variables = Maps.newHashMap();

		assertEquals(AnydslPackage.eINSTANCE.getPart().getEEnumLiteral("Other").getInstance(), engine.eval(
				"anydsl::Part::Other", variables));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSetInExtensionLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();

		varDefinitions.put("self", EcorePackage.Literals.ECLASS);

		final Object result = engine.eval("{self, self, true, false}", varDefinitions);
		assertEquals(true, result instanceof Set);
		assertEquals(3, ((Set<Object>)result).size());
		Iterator<Object> it = ((Set<Object>)result).iterator();
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(Boolean.TRUE, it.next());
		assertEquals(Boolean.FALSE, it.next());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSequenceInExtensionLiteral() {
		Map<String, Object> varDefinitions = Maps.newHashMap();

		varDefinitions.put("self", EcorePackage.Literals.ECLASS);

		final Object result = engine.eval("[self, self, true, false]", varDefinitions);
		assertEquals(true, result instanceof List);
		assertEquals(4, ((List<Object>)result).size());
		Iterator<Object> it = ((List<Object>)result).iterator();
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(EcorePackage.Literals.ECLASS, it.next());
		assertEquals(Boolean.TRUE, it.next());
		assertEquals(Boolean.FALSE, it.next());
	}

}
