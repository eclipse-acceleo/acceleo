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
package org.eclipse.acceleo.query.runtime.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EvaluationServicesTest {

	private static final String LOCAL_MODEL_PATH = "ecore/reverse.ecore";

	private Map<String, Object> variables;

	IQueryEnvironment queryEnvironment;

	ILookupEngine engine;

	EvaluationServices services;

	private LinkedHashSet<Object> createSet(Object... elements) {
		return new LinkedHashSet<Object>(Arrays.asList(elements));
	}

	@Before
	public void setup() {
		queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		final Set<IService<?>> servicesToRegister = ServiceUtils.getServices(queryEnvironment,
				TestServiceDefinition.class);
		ServiceUtils.registerServices(queryEnvironment, servicesToRegister);
		variables = new HashMap<String, Object>();
		variables.put("x", 1);
		variables.put("y", 2);
		variables.put("z", null);
		services = new EvaluationServices(queryEnvironment);
	}

	/**
	 * query the value of an existing variable. Expected result : the value set for the variable.
	 */
	@Test
	public void getVariableValueNotNull() {
		Diagnostic status = new BasicDiagnostic();

		assertEquals(1, services.getVariableValue(variables, "x", status));

		assertEquals(Diagnostic.OK, status.getSeverity());
		assertTrue(status.getChildren().isEmpty());
	}

	@Test
	public void getVariableValueNull() {
		Diagnostic status = new BasicDiagnostic();

		assertEquals(null, services.getVariableValue(variables, "z", status));

		assertEquals(Diagnostic.OK, status.getSeverity());
		assertTrue(status.getChildren().isEmpty());
	}

	/**
	 * query the value of a variable that doesn't exist. Expected result : nothing and the expected log
	 * message.
	 */
	@Test
	public void getVariableValueNonExisting() {
		Diagnostic status = new BasicDiagnostic();

		assertTrue(services.getVariableValue(variables, "xx", status) instanceof Nothing);

		assertEquals(Diagnostic.ERROR, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(Diagnostic.ERROR, child.getSeverity());
		assertNull(child.getException());
		assertTrue(child.getMessage().contains("'xx' variable"));
	}

	/**
	 * Test feature access on an EObject with an existing feature. Expected result : the value of the feature
	 * in the specified {@link EObject} instance.
	 */
	@Test
	public void testExistingFeatureAccessOnEObject() {
		EAttribute attribute = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute.setName("attr0");

		Diagnostic status = new BasicDiagnostic();
		assertEquals("attr0", services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {attribute, "name" }, status));
		assertEquals(Diagnostic.OK, status.getSeverity());
		assertTrue(status.getChildren().isEmpty());
	}

	/**
	 * Test feature access on an EObject with a feature that does not exist. Expected result : nothing.
	 */
	@Test
	public void testNonExistingFeatureAccessOnEObject() {
		EAttribute attribute = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute.setName("attr0");

		Diagnostic status = new BasicDiagnostic();
		assertTrue(services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false, new Object[] {
				attribute, "noname" }, status) instanceof Nothing);
		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(Diagnostic.WARNING, child.getSeverity());
		assertTrue(child.getMessage().contains("Feature noname"));
	}

	/**
	 * Test feature access on an EObject with a feature that does not exist. Expected result : nothing.
	 */
	@Test
	public void testFeatureAccessOnObject() {
		Diagnostic status = new BasicDiagnostic();
		assertTrue(services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false, new Object[] {
				Integer.valueOf(3), "noname" }, status) instanceof Nothing);
		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(Diagnostic.WARNING, child.getSeverity());
		assertNull(child.getException());
		assertEquals("Couldn't find the 'aqlFeatureAccess(java.lang.Integer,java.lang.String)' service", child
				.getMessage());
	}

	/**
	 * Test feature access on an {@link EObject} with an existing feature name but wich value is unset.
	 * Expected result : null.
	 */
	@Test
	public void testUnsetFeature() {
		EAttribute attribute = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);

		Diagnostic status = new BasicDiagnostic();
		assertNull(services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false, new Object[] {
				attribute, "eType" }, status));
		assertEquals(Diagnostic.OK, status.getSeverity());
		assertTrue(status.getChildren().isEmpty());
	}

	/**
	 * Test feature access on a {@link List} containing EObjects with a feature of the specified name.
	 * Expected result : a list containing the result of getting the specified feature on all the elements of
	 * the specified argument list.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFeatureAccessOnList() {
		EAttribute attribute0 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute0.setName("attr0");
		EAttribute attribute1 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute1.setName("attr1");

		List<EAttribute> list = new ArrayList<EAttribute>();
		list.add(attribute0);
		list.add(attribute1);

		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {list, "name" }, status);
		assertTrue(result instanceof List);
		assertEquals("attr0", ((List<Object>)result).get(0));
		assertEquals("attr1", ((List<Object>)result).get(1));
		assertEquals(Diagnostic.OK, status.getSeverity());
		assertTrue(status.getChildren().isEmpty());
	}

	/**
	 * Test feature access on a {@link Set} containing EObjects with a feature of the specified name. Expected
	 * result : a set containing the result of getting the specified feature on all the elements of the
	 * specified argument list.
	 */
	@Test
	public void testFeatureAccessOnSet() {
		EAttribute attribute0 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute0.setName("attr0");
		EAttribute attribute1 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute1.setName("attr1");

		Set<Object> set = createSet(attribute0, attribute1);
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {set, "name" }, status);
		assertTrue(result instanceof Set);
		@SuppressWarnings("unchecked")
		Iterator<Object> iterator = ((Set<Object>)result).iterator();
		assertEquals("attr0", iterator.next());
		assertEquals("attr1", iterator.next());
		assertEquals(Diagnostic.OK, status.getSeverity());
		assertTrue(status.getChildren().isEmpty());
	}

	/**
	 * Test feature access on an empty list. Expected result : empty list.
	 */
	@Test
	public void testFeatureAccessOnEmptyList() {
		List<EAttribute> list = new ArrayList<EAttribute>();

		Diagnostic status = new BasicDiagnostic();
		final Object listResult = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {list, "noname" }, status);
		assertEquals(true, listResult instanceof List);
		assertEquals(0, ((List<?>)listResult).size());
		assertEquals(Diagnostic.OK, status.getSeverity());
		assertTrue(status.getChildren().isEmpty());
	}

	/**
	 * Test feature access on an empty set. Expected result : empty set.
	 */
	@Test
	public void testFeatureAccessOnEmptySet() {
		Set<EAttribute> set = new LinkedHashSet<EAttribute>();

		Diagnostic status = new BasicDiagnostic();
		final Object setResult = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {set, "noname" }, status);

		assertTrue(setResult instanceof Set);
		assertEquals(0, ((Set<?>)setResult).size());
		assertEquals(Diagnostic.OK, status.getSeverity());
		assertTrue(status.getChildren().isEmpty());
	}

	/**
	 * Test feature access on a {@link List} containing an EObject with the correct feature and one non-model
	 * object.
	 * <p>
	 * Expected : a list with a NOTHING and the EObject's value for that feature
	 * </p>
	 */
	public void testFeatureAccessOnListOneObjectOneNonModel() {
		List<Object> list = new ArrayList<Object>(Arrays.asList(1));
		EAttribute attribute0 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute0.setName("attr0");
		list.add(attribute0);

		Diagnostic status = new BasicDiagnostic();
		Object listResult = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {list, "name" }, status);

		assertEquals(true, listResult instanceof List);
		assertEquals(2, ((List<?>)listResult).size());
		assertTrue(((List<?>)listResult).contains("attr0"));
		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(Diagnostic.WARNING, child.getSeverity());
		assertNull(child.getException());
		assertTrue(child.getMessage().contains("feature"));
		assertTrue(child.getMessage().contains("name"));
		assertTrue(child.getMessage().contains("non ModelObject"));
	}

	/**
	 * Test feature access on a {@link List} containing two EObject with the correct feature and one non-model
	 * object.
	 * <p>
	 * Expected : a list with a NOTHING and the two EObjects' value for that feature
	 * </p>
	 */
	public void testFeatureAccessOnListTwoObjectsOneNonModel() {
		List<Object> list = new ArrayList<Object>();
		EAttribute attribute0 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute0.setName("attr0");
		EAttribute attribute1 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute1.setName("attr1");

		list.add(attribute0);
		list.add(1);
		list.add(attribute1);

		Diagnostic status = new BasicDiagnostic();
		Object listResult = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {list, "name" }, status);

		assertEquals(true, listResult instanceof List);
		assertEquals(3, ((List<?>)listResult).size());
		assertTrue(((List<?>)listResult).contains("attr0"));
		assertTrue(((List<?>)listResult).contains("attr1"));
		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(Diagnostic.WARNING, child.getSeverity());
		assertNull(child.getException());
		assertTrue(child.getMessage().contains("feature"));
		assertTrue(child.getMessage().contains("name"));
		assertTrue(child.getMessage().contains("non ModelObject"));
	}

	/**
	 * Test feature access on a {@link Set} containing EObjects with a feature of the specified name and an
	 * Object. Expected result : a set containing the result of getting the specified feature on all the
	 * elements of the specified argument list excepted the Object element.
	 */
	@Test
	public void testFeatureAccessOnSetWithOneObject() {
		Set<Object> set = createSet(1);

		Diagnostic status = new BasicDiagnostic();
		Object setResult = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {set, "noname" }, status);

		assertTrue(setResult instanceof Set);
		assertEquals(0, ((Set<?>)setResult).size());
	}

	/**
	 * Test feature access on a {@link List} containing EObjects with a feature of the specified name and an
	 * Object. Expected result : a list containing the result of getting the specified feature on all the
	 * elements of the specified argument list excepted the EObject with the feature missing.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFeatureAccessOnListWithOneEObjectMissingFeature() {
		EAttribute attr = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attr.setName("attr0");
		EReference ref = (EReference)EcoreUtil.create(EcorePackage.Literals.EREFERENCE);
		ref.setContainment(true);

		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>(Arrays.asList(attr, ref));
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {list, "containment" }, status);
		assertTrue(result instanceof List);
		assertEquals(1, ((List<Object>)result).size());
		assertTrue((Boolean)((List<Object>)result).get(0));
	}

	/**
	 * Test feature access on a {@link Set} containing EObjects with a feature of the specified name and an
	 * EObject missing the specified feature. Expected result : a set containing the result of getting the
	 * specified feature on all the elements of the specified argument list excepted the EObject with the
	 * feature missing.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFeatureAccessOnSetWithOneEObjectMissingFeature() {
		EAttribute attr = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attr.setName("attr0");
		EReference ref = (EReference)EcoreUtil.create(EcorePackage.Literals.EREFERENCE);
		ref.setContainment(true);

		Set<Object> set = createSet(attr, ref);
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {set, "containment" }, status);
		assertTrue(result instanceof Set);
		assertEquals(1, ((Set<Object>)result).size());
		Iterator<Object> iterator = ((Set<Object>)result).iterator();
		assertTrue((Boolean)iterator.next());
	}

	/**
	 * test feature access on a list containing {@link EObject} instance with the specified feature and a
	 * nested list containing an {@link EObject} instance with the specified feature. Expected result : a list
	 * with the values of the feature and a nested list containing the value of the feature of the next
	 * {@link EObject} instance.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFeatureAccessOnListWithOneNestedList() {
		EAttribute attribute0 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute0.setName("attr0");
		EAttribute attribute1 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute1.setName("attr1");

		List<Object> list1 = new ArrayList<Object>(Arrays.asList((Object)attribute1));
		List<Object> list0 = new ArrayList<Object>(Arrays.asList(attribute0, list1));
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {list0, "name" }, status);
		assertTrue(result instanceof List);
		List<Object> listResult = (List<Object>)result;
		assertEquals(2, listResult.size());
		assertEquals("attr0", listResult.get(0));
		assertEquals("attr1", listResult.get(1));
	}

	/**
	 * test feature access on a list containing {@link EObject} instance with the specified feature and a
	 * nested set containing an {@link EObject} instance with the specified feature.Expected result : a list
	 * with the values of the feature and a nested set containing the value of the feature of the next
	 * {@link EObject} instance.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFeatureAccessOnListWithOneNestedSet() {
		EAttribute attribute0 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute0.setName("attr0");
		EAttribute attribute1 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute1.setName("attr1");

		Set<Object> list1 = createSet(attribute1);
		List<Object> list0 = new ArrayList<Object>(Arrays.asList(attribute0, list1));
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {list0, "name" }, status);
		assertTrue(result instanceof List);
		List<Object> listResult = (List<Object>)result;
		assertEquals(2, listResult.size());
		assertEquals("attr0", listResult.get(0));
		assertEquals("attr1", listResult.get(1));
	}

	/**
	 * test feature access on a list containing {@link EObject} instance with the specified feature and a
	 * nested list containing an {@link EObject} instance without the specified feature. Expected result : a
	 * list with the values of the feature of the {@link EObject} instances at the first level and no empty
	 * nested list.
	 */
	@Test
	public void testFeatureAccessOnListWithOneNestedListContainingBadObject() {
		EAttribute attribute0 = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute0.setName("attr0");

		List<Object> list1 = new ArrayList<Object>(Arrays.asList((Object)1));
		List<Object> list0 = new ArrayList<Object>(Arrays.asList(attribute0, list1));
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {list0, "name" }, status);
		assertTrue(result instanceof List);
		List<?> listResult = (List<?>)result;
		assertEquals(1, listResult.size());
		assertEquals("attr0", listResult.get(0));
	}

	/**
	 * Tests that the result of calling a service that returns null is NOTHING
	 */
	@Test
	public void serviceReturnsNullTest() {
		Diagnostic status = new BasicDiagnostic();
		assertNull(services.call("serviceReturnsNull", false, new Object[] {1 }, status));
	}

	/**
	 * Tests that the result of calling a service that doesn't exist is NOTHING
	 */
	@Test
	public void serviceNotFoundReturnsNothing() {
		Diagnostic status = new BasicDiagnostic();
		assertTrue(services.call("noService", false, new Object[] {1 }, status) instanceof Nothing);
	}

	/**
	 * Tests that the result of calling a service that throws an exception is NOTHING.
	 */
	@Test
	public void serviceThrowsException() {
		Diagnostic status = new BasicDiagnostic();
		assertTrue(services.call("serviceThrowsException", false, new Object[] {1 },
				status) instanceof Nothing);

	}

	/**
	 * Tests that calling the add service on Integer(1) and Integer(2) yields Integer(3).
	 */
	@Test
	public void serviceCallTest() {
		Object[] args = {Integer.valueOf(1), Integer.valueOf(2) };
		Diagnostic status = new BasicDiagnostic();
		assertEquals(3, services.call("add", false, args, status));
	}

	/**
	 * Tests that calling callOrApply on a scalar value ends up calling the specified service on the scalar
	 * value. More precisely, tests that calling add on Integer(1) and Integer(2) yields Integer(3).
	 */
	@Test
	public void callOrApplyOnScalarValueTest() {
		Object[] args = {Integer.valueOf(1), Integer.valueOf(2) };
		Diagnostic status = new BasicDiagnostic();
		assertEquals(3, services.callOrApply("add", false, args, status));

	}

	/**
	 * Test callOrApply on an empty list. Expected result : NOTHING.
	 */
	@Test
	public void callOrApplyOnEmptyList() {
		Object[] args = {new ArrayList<Object>() };

		Diagnostic status = new BasicDiagnostic();
		final Object listResult = services.callOrApply("add", false, args, status);

		assertTrue(listResult instanceof List);
		assertEquals(0, ((List<?>)listResult).size());
	}

	/**
	 * Test callOrApply on an empty set. Expected result : NOTHING.
	 */
	@Test
	public void callOrApplyOnEmptySet() {
		Object[] args = {new LinkedHashSet<Object>() };

		Diagnostic status = new BasicDiagnostic();
		final Object setResult = services.callOrApply("add", false, args, status);

		assertTrue(setResult instanceof Set);
		assertEquals(0, ((Set<?>)setResult).size());
	}

	/**
	 * Test callOrApply toString on a [1,2]. Expected result : ["1","2"].
	 */
	@Test
	public void callOrApplyOnListTest() {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 2));
		Object[] args = {list };
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply("toString", false, args, status);
		assertTrue(result instanceof List);
		@SuppressWarnings("unchecked")
		List<Object> listResult = (List<Object>)result;
		assertEquals(2, listResult.size());
		assertEquals("1", listResult.get(0));
		assertEquals("2", listResult.get(1));
	}

	/**
	 * Test callOrApply toString on a {1,2}. Expected result : {"1","2"}.
	 */
	@Test
	public void callOrApplyOnSetTest() {
		Set<Object> set = createSet(1, 2);
		Object[] args = {set };
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply("toString", false, args, status);
		assertTrue(result instanceof Set);
		@SuppressWarnings("unchecked")
		Set<Object> setResult = (Set<Object>)result;
		assertEquals(2, setResult.size());
		Iterator<Object> iterator = setResult.iterator();
		assertEquals("1", iterator.next());
		assertEquals("2", iterator.next());
	}

	/**
	 * Test callOrApply the toString service on this list : [1,2,[3,4]]. Expected result : ["1","2",["3","4"]]
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void callOrApplyOnNestedList() {
		List<Integer> list2 = new ArrayList<Integer>(Arrays.asList(3, 4));
		List<Object> list1 = new ArrayList<Object>(Arrays.asList(1, 2, list2));
		Object[] args = {list1 };
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply("toString", false, args, status);
		assertTrue(result instanceof List);
		List<Object> listResult = (List<Object>)result;
		assertEquals(4, listResult.size());
		assertEquals("1", listResult.get(0));
		assertEquals("2", listResult.get(1));
		assertEquals("3", listResult.get(2));
		assertEquals("4", listResult.get(3));
	}

	/**
	 * Test callOrApply the toString service on this list : [1,2,{3,4}]. Expected result : ["1","2",{"3","4"}]
	 */
	@Test
	public void callOrApplyOnNestedSet() {
		Set<Object> list2 = createSet(3, 4);
		List<Object> list1 = new ArrayList<Object>(Arrays.asList(1, 2, list2));
		Object[] args = {list1 };
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply("toString", false, args, status);
		assertTrue(result instanceof List);
		@SuppressWarnings("unchecked")
		List<Object> listResult = (List<Object>)result;
		assertEquals(4, listResult.size());
		assertEquals("1", listResult.get(0));
		assertEquals("2", listResult.get(1));
		assertEquals("3", listResult.get(2));
		assertEquals("4", listResult.get(3));
	}

	/**
	 * Test callOrApply the 'special' service on the following list : [1,2,[3]]. 'special' returns null for 3
	 * and the value for 1 and 2. Expected result : [1,2]
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void callOrApplySpecialOnNestedList() {
		List<Integer> list2 = new ArrayList<Integer>(Arrays.asList(3));
		List<Object> list1 = new ArrayList<Object>(Arrays.asList(1, 2, list2));
		Object[] args = {list1 };
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply("special", false, args, status);
		assertTrue(result instanceof List);
		List<Object> listResult = (List<Object>)result;
		assertEquals(2, listResult.size());
		assertEquals(1, listResult.get(0));
		assertEquals(2, listResult.get(1));
	}

	/**
	 * Tests that nothing ain't inserted in the result lists. Call 'special' on [1,2,3]. Expected result :
	 * [1,2].
	 */
	@Test
	public void callOrapplySpecialOnList() {
		List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		Object[] args = {list1 };
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply("special", false, args, status);
		assertTrue(result instanceof List);
		@SuppressWarnings("unchecked")
		List<Object> listResult = (List<Object>)result;
		assertEquals(2, listResult.size());
		assertEquals(1, listResult.get(0));
		assertEquals(2, listResult.get(1));
	}

	/**
	 * Tests that nothing ain't inserted in the result sets. Call 'special' on {1,2,3}. Expected result :
	 * {1,2}.
	 */
	@Test
	public void callOrapplySpecialOnSet() {
		Set<Object> set = createSet(1, 2, 3);
		Object[] args = {set };
		Diagnostic status = new BasicDiagnostic();
		Object result = services.callOrApply("special", false, args, status);
		assertTrue(result instanceof Set);
		@SuppressWarnings("unchecked")
		Set<Object> setResult = (Set<Object>)result;
		assertEquals(2, setResult.size());
		Iterator<Object> iterator = setResult.iterator();
		assertEquals(1, iterator.next());
		assertEquals(2, iterator.next());
	}

	/**
	 * Checks that an empty argument list result in an AcceleoQueryEvaluationException being thrown.
	 */
	@Test(expected = AcceleoQueryEvaluationException.class)
	public void testEmptyArgumentCall() {
		Diagnostic status = new BasicDiagnostic();
		services.call("toString", false, new Object[] {}, status);
	}

	/**
	 * Checks that an empty argument list result in an AcceleoQueryEvaluationException being thrown.
	 */
	@Test(expected = AcceleoQueryEvaluationException.class)
	public void testEmptyArgumentCallOrApply() {
		Diagnostic status = new BasicDiagnostic();
		services.callOrApply("toString", false, new Object[] {}, status);
	}

	/**
	 * Checks that a null argument can be valid.
	 */
	@Test
	public void testNullArgumentCall() {
		Diagnostic status = new BasicDiagnostic();
		final Object result = services.call("toString", false, new Object[] {null }, status);
		assertTrue(result instanceof Nothing);
		assertEquals(Diagnostic.ERROR, status.getSeverity());
		assertEquals(1, status.getChildren().size());
		assertTrue(status.getChildren().get(0).getException() instanceof AcceleoQueryEvaluationException);
		assertTrue(status.getChildren().get(0).getException().getCause() instanceof NullPointerException);
	}

	/**
	 * Checks that a null argument can be valid.
	 */
	@Test
	public void testNullArgumentCallOrApply() {
		Diagnostic status = new BasicDiagnostic();
		final Object result = services.callOrApply("toString", false, new Object[] {null }, status);
		assertTrue(result instanceof Nothing);
		assertEquals(Diagnostic.ERROR, status.getSeverity());
		assertEquals(1, status.getChildren().size());
		assertTrue(status.getChildren().get(0).getException() instanceof AcceleoQueryEvaluationException);
		assertTrue(status.getChildren().get(0).getException().getCause() instanceof NullPointerException);
	}

	/**
	 * Checks a null context.
	 */
	@Test
	public void testNullArgumentFeatureAccess() {
		Diagnostic status = new BasicDiagnostic();
		services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false, new Object[] {null,
				"name" }, status);
	}

	/**
	 * Checks that an empty argument list result in an AcceleoQueryEvaluationException being thrown.
	 */
	@Test(expected = AcceleoQueryEvaluationException.class)
	public void testEmptyArgumentCollectionServiceCall() {
		Diagnostic status = new BasicDiagnostic();
		services.collectionServiceCall("toString", false, new Object[] {}, status);
	}

	/**
	 * Checks that an empty argument list result in an AcceleoQueryEvaluationException being thrown.
	 */
	@Test(expected = AcceleoQueryEvaluationException.class)
	public void testNullArgumentCollectionServiceCall() {
		Diagnostic status = new BasicDiagnostic();
		services.collectionServiceCall("toString", false, null, status);
	}

	/**
	 * "null" in the argument list will be reflected as an empty collection for the subsequent call.
	 * null->methodOrServiceCall() is supposed to have the same behavior as Sequence{}->methodOrServiceCall().
	 */
	@Test
	public void testNullLiteralAsArgumentListCollectionServiceCall() {
		Diagnostic status = new BasicDiagnostic();
		Object result = services.collectionServiceCall("toString", false, new Object[] {null, }, status);
		assertEquals("[]", result);
		assertEquals(Diagnostic.OK, status.getSeverity());

		result = services.collectionServiceCall("size", false, new Object[] {null, }, status);
		assertEquals(Integer.valueOf(0), result);
		assertEquals(Diagnostic.OK, status.getSeverity());

		result = services.collectionServiceCall("first", false, new Object[] {null, }, status);
		assertNull(result);
		assertEquals(Diagnostic.OK, status.getSeverity());
		assertEquals(0, status.getChildren().size());
	}

	@Test
	public void testMapFeatureAccess() throws URISyntaxException, IOException {
		ResourceSet set = new Setup().newConfiguredResourceSet();
		URL url = this.getClass().getClassLoader().getResource(LOCAL_MODEL_PATH);
		URI uri = URI.createURI(url.toURI().toString());
		Resource res = set.getResource(uri, true);
		res.load(Collections.EMPTY_MAP);
		Object target = ((EPackage)res.getContents().get(0)).getEClassifier("newDescription4")
				.getEAnnotations();
		assertTrue(target instanceof List);
		EAnnotation annotation = (EAnnotation)((List<?>)target).get(0);
		Entry<String, String> entry = annotation.getDetails().get(0);
		Diagnostic status = new BasicDiagnostic();
		assertEquals("archetype", services.callOrApply(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME, false,
				new Object[] {entry, "key" }, status));
	}

	@Test
	public void testEOperationGeneratedClass() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		Diagnostic status = new BasicDiagnostic();
		final Object result = services.call("getEClassifier", false, new Object[] {EcorePackage.eINSTANCE,
				"EClass", }, status);
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result);
	}

	@Test
	public void testEOperationGeneratedClassWithEObjectParameter() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		Diagnostic status = new BasicDiagnostic();
		final Object result = services.call("isSuperTypeOf", false, new Object[] {EcorePackage.eINSTANCE
				.getEClass(), EcorePackage.eINSTANCE.getEPackage(), }, status);
		assertEquals(false, result);
	}

	@Test
	public void testEAttributeDynamicClass() {
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
		eAttribute.setChangeable(true);
		eCls.getEStructuralFeatures().add(eAttribute);
		eAttribute.setDefaultValue("SomeValue");

		queryEnvironment.registerEPackage(ePkg);

		queryEnvironment.registerEPackage(ePkg);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		final EObject receiver = EcoreUtil.create(eCls);

		Diagnostic status = new BasicDiagnostic();
		final Object result = services.call("aqlFeatureAccess", false, new Object[] {receiver,
				"dynamicEAttribute", }, status);

		queryEnvironment.removeEPackage(ePkg);

		assertEquals("SomeValue", result);
	}

	@Test
	public void testEOperationDynamicClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("dynamic");
		ePkg.setNsURI("dynamic");
		ePkg.setNsPrefix("dynamic");
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls.setName("DynamicEClass");
		ePkg.getEClassifiers().add(eCls);
		final EOperation eOperation = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		eOperation.setName("dynamicEOperation");
		eOperation.setEType(EcorePackage.eINSTANCE.getEcoreFactory().createEClass());
		final EParameter eParameter = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
		eParameter.setName("dynamicParameter");
		eParameter.setEType(EcorePackage.eINSTANCE.getEString());
		eOperation.getEParameters().add(eParameter);
		eCls.getEOperations().add(eOperation);
		((EOperation.Internal)eOperation).setInvocationDelegate(new EOperation.Internal.InvocationDelegate() {

			@Override
			public Object dynamicInvoke(InternalEObject target, EList<?> arguments)
					throws InvocationTargetException {
				return eCls;
			}

		});
		queryEnvironment.registerEPackage(ePkg);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		final EObject receiver = EcoreUtil.create(eCls);
		Diagnostic status = new BasicDiagnostic();
		final Object result = services.call("dynamicEOperation", false, new Object[] {receiver, "EClass", },
				status);
		assertEquals(eCls, result);
	}

	@Test
	public void testEOperationDynamicClassWithEObjectParameter() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("dynamic");
		ePkg.setNsURI("dynamic");
		ePkg.setNsPrefix("dynamic");
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls.setName("DynamicEClass");
		ePkg.getEClassifiers().add(eCls);
		final EOperation eOperation = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		eOperation.setName("dynamicEOperation");
		eOperation.setEType(EcorePackage.eINSTANCE.getEcoreFactory().createEClass());
		final EParameter eParameter = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
		eParameter.setName("dynamicParameter");
		eParameter.setEType(eCls);
		eOperation.getEParameters().add(eParameter);
		eCls.getEOperations().add(eOperation);
		((EOperation.Internal)eOperation).setInvocationDelegate(new EOperation.Internal.InvocationDelegate() {

			@Override
			public Object dynamicInvoke(InternalEObject target, EList<?> arguments)
					throws InvocationTargetException {
				return eCls;
			}

		});
		queryEnvironment.registerEPackage(ePkg);
		final EObject receiver = EcoreUtil.create(eCls);
		Diagnostic status = new BasicDiagnostic();
		final Object result = services.call("dynamicEOperation", false, new Object[] {receiver, receiver, },
				status);
		assertEquals(eCls, result);
	}

}
