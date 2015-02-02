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
package org.eclipse.acceleo.query.runtime.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.acceleo.query.tests.Setup;
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
import static org.junit.Assert.assertTrue;

public class EvaluationServicesTest {

	private static final String LOCAL_MODEL_PATH = "ecore/reverse.ecore";

	Map<String, Object> variables = new HashMap<String, Object>();

	QueryEnvironment queryEnvironment;

	BasicLookupEngine engine;

	EvaluationServices services;

	private LinkedHashSet<Object> createSet(Object... elements) {
		return Sets.newLinkedHashSet(Lists.newArrayList(elements));
	}

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		queryEnvironment = new QueryEnvironment(null);
		queryEnvironment.registerServicePackage(TestServiceDefinition.class);
		variables.put("x", 1);
		variables.put("y", 2);
		services = new EvaluationServices(queryEnvironment, true);
	}

	/**
	 * query the value of an existing variable. Expected result : the value set for the variable.
	 */
	@Test
	public void getExistingVariableTest() {
		assertEquals(1, services.getVariableValue(variables, "x"));
	}

	/**
	 * query the value of a variable that doesn't exist. Expected result : nothing and the expected log
	 * message.
	 */
	@Test
	public void getNonExistingVariableTest() {
		assertEquals(EvaluationServices.NOTHING, services.getVariableValue(variables, "xx"));
	}

	/**
	 * Test feature access on an EObject with an existing feature. Expected result : the value of the feature
	 * in the specified {@link EObject} instance.
	 */
	@Test
	public void testExistingFeatureAccessOnEObject() {
		EAttribute attribute = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute.setName("attr0");
		assertEquals("attr0", services.featureAccess(attribute, "name"));
	}

	/**
	 * Test feature access on an EObject with a feature that does not exist. Expected result : nothing.
	 */
	@Test
	public void testNonExistingFeatureAccessOnEObject() {
		EAttribute attribute = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute.setName("attr0");
		assertEquals(EvaluationServices.NOTHING, services.featureAccess(attribute, "noname"));
	}

	/**
	 * Test feature access on an EObject with a feature that does not exist. Expected result : nothing.
	 */
	@Test
	public void testFeatureAccessOnObject() {
		assertEquals(EvaluationServices.NOTHING, services.featureAccess(new Integer(3), "noname"));
	}

	/**
	 * Test feature access on an {@link EObject} with an existing feature name but wich value is unset.
	 * Expected result : null.
	 */
	@Test
	public void testUnsetFeature() {
		EAttribute attribute = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		assertEquals(null, services.featureAccess(attribute, "eType"));
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
		Object result = services.featureAccess(list, "name");
		assertTrue(result instanceof List);
		assertEquals("attr0", ((List<Object>)result).get(0));
		assertEquals("attr1", ((List<Object>)result).get(1));
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
		Object result = services.featureAccess(set, "name");
		assertTrue(result instanceof Set);
		@SuppressWarnings("unchecked")
		Iterator<Object> iterator = ((Set<Object>)result).iterator();
		assertEquals("attr0", iterator.next());
		assertEquals("attr1", iterator.next());
	}

	/**
	 * Test feature access on an empty list. Expected result : NOTHING.
	 */
	@Test
	public void testFeatureAccessOnEmptyList() {
		List<EAttribute> list = new ArrayList<EAttribute>();

		final Object listResult = services.featureAccess(list, "noname");

		assertEquals(true, listResult instanceof List);
		assertEquals(0, ((List<?>)listResult).size());
	}

	/**
	 * Test feature access on an empty set. Expected result : NOTHING.
	 */
	@Test
	public void testFeatureAccessOnEmptySet() {
		Set<EAttribute> set = new LinkedHashSet<EAttribute>();

		final Object setResult = services.featureAccess(set, "noname");

		assertEquals(true, setResult instanceof Set);
		assertEquals(0, ((Set<?>)setResult).size());
	}

	/**
	 * Test feature access on a {@link List} containing EObjects with a feature of the specified name and an
	 * Object. Expected result : a list containing the result of getting the specified feature on all the
	 * elements of the specified argument list excepted the Object element.
	 */
	@Test
	public void testFeatureAccessOnListWithOneObject() {
		List<Integer> list = Lists.newArrayList(1);

		Object listResult = services.featureAccess(list, "noname");

		assertEquals(true, listResult instanceof List);
		assertEquals(0, ((List<?>)listResult).size());
	}

	/**
	 * Test feature access on a {@link Set} containing EObjects with a feature of the specified name and an
	 * Object. Expected result : a set containing the result of getting the specified feature on all the
	 * elements of the specified argument list excepted the Object element.
	 */
	@Test
	public void testFeatureAccessOnSetWithOneObject() {
		Set<Object> set = createSet(1);

		Object setResult = services.featureAccess(set, "noname");

		assertEquals(true, setResult instanceof Set);
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

		List<EStructuralFeature> list = Lists.newArrayList(attr, ref);
		Object result = services.featureAccess(list, "containment");
		assertTrue(result instanceof List);
		assertEquals(1, ((List<Object>)result).size());
		assertEquals(true, ((List<Object>)result).get(0));
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
		Object result = services.featureAccess(set, "containment");
		assertTrue(result instanceof Set);
		assertEquals(1, ((Set<Object>)result).size());
		Iterator<Object> iterator = ((Set<Object>)result).iterator();
		assertEquals(true, iterator.next());
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

		List<Object> list1 = Lists.newArrayList((Object)attribute1);
		List<Object> list0 = Lists.newArrayList(attribute0, list1);
		Object result = services.featureAccess(list0, "name");
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
		List<Object> list0 = Lists.newArrayList(attribute0, list1);
		Object result = services.featureAccess(list0, "name");
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

		List<Object> list1 = Lists.newArrayList((Object)1);
		List<Object> list0 = Lists.newArrayList(attribute0, list1);
		Object result = services.featureAccess(list0, "name");
		assertTrue(result instanceof List);
		@SuppressWarnings("unchecked")
		List<Object> listResult = (List<Object>)result;
		assertEquals(1, listResult.size());
		assertEquals("attr0", listResult.get(0));
	}

	/**
	 * Tests that the result of calling a service that returns null is NOTHING
	 */
	@Test
	public void serviceReturnsNullTest() {
		assertEquals(null, services.call("serviceReturnsNull", new Object[] {1 }));
	}

	/**
	 * Tests that the result of calling a service that doesn't exist is NOTHING
	 */
	@Test
	public void serviceNotFoundReturnsNothing() {
		assertEquals(EvaluationServices.NOTHING, services.call("noService", new Object[] {1 }));
	}

	/**
	 * Tests that the result of calling a service that throws an exception is NOTHING.
	 */
	@Test
	public void serviceThrowsException() {
		assertEquals(EvaluationServices.NOTHING, services.call("serviceThrowsException", new Object[] {1 }));

	}

	/**
	 * Tests that calling the add service on Integer(1) and Integer(2) yields Integer(3).
	 */
	@Test
	public void serviceCallTest() {
		Object[] args = {new Integer(1), new Integer(2) };
		assertEquals(3, services.call("add", args));
	}

	/**
	 * Tests that calling callOrApply on a scalar value ends up calling the specified service on the scalar
	 * value. More precisely, tests that calling add on Integer(1) and Integer(2) yields Integer(3).
	 */
	@Test
	public void callOrApplyOnScalarValueTest() {
		Object[] args = {new Integer(1), new Integer(2) };
		assertEquals(3, services.callOrApply("add", args));

	}

	/**
	 * Test callOrApply on an empty list. Expected result : NOTHING.
	 */
	@Test
	public void callOrApplyOnEmptyList() {
		Object[] args = {new ArrayList<Object>() };

		final Object listResult = services.callOrApply("add", args);

		assertEquals(true, listResult instanceof List);
		assertEquals(0, ((List<?>)listResult).size());
	}

	/**
	 * Test callOrApply on an empty set. Expected result : NOTHING.
	 */
	@Test
	public void callOrApplyOnEmptySet() {
		Object[] args = {new LinkedHashSet<Object>() };

		final Object setResult = services.callOrApply("add", args);

		assertEquals(true, setResult instanceof Set);
		assertEquals(0, ((Set<?>)setResult).size());
	}

	/**
	 * Test callOrApply toString on a [1,2]. Expected result : ["1","2"].
	 */
	@Test
	public void callOrApplyOnListTest() {
		List<Integer> list = Lists.newArrayList(1, 2);
		Object[] args = {list };
		Object result = services.callOrApply("toString", args);
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
		Object result = services.callOrApply("toString", args);
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
		List<Integer> list2 = Lists.newArrayList(3, 4);
		List<Object> list1 = Lists.newArrayList(1, 2, list2);
		Object[] args = {list1 };
		Object result = services.callOrApply("toString", args);
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
		List<Object> list1 = Lists.newArrayList(1, 2, list2);
		Object[] args = {list1 };
		Object result = services.callOrApply("toString", args);
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
		List<Integer> list2 = Lists.newArrayList(3);
		List<Object> list1 = Lists.newArrayList(1, 2, list2);
		Object[] args = {list1 };
		Object result = services.callOrApply("special", args);
		assertTrue(result instanceof List);
		List<Object> listResult = (List<Object>)result;
		assertEquals(3, listResult.size());
		assertEquals(1, listResult.get(0));
		assertEquals(2, listResult.get(1));
		assertEquals(null, listResult.get(2));
	}

	/**
	 * Tests that nothing ain't inserted in the result lists. Call 'special' on [1,2,3]. Expected result :
	 * [1,2].
	 */
	@Test
	public void callOrapplySpecialOnList() {
		List<Integer> list1 = Lists.newArrayList(1, 2, 3);
		Object[] args = {list1 };
		Object result = services.callOrApply("special", args);
		assertTrue(result instanceof List);
		@SuppressWarnings("unchecked")
		List<Object> listResult = (List<Object>)result;
		assertEquals(3, listResult.size());
		assertEquals(1, listResult.get(0));
		assertEquals(2, listResult.get(1));
		assertEquals(null, listResult.get(2));
	}

	/**
	 * Tests that nothing ain't inserted in the result sets. Call 'special' on {1,2,3}. Expected result :
	 * {1,2}.
	 */
	@Test
	public void callOrapplySpecialOnSet() {
		Set<Object> set = createSet(1, 2, 3);
		Object[] args = {set };
		Object result = services.callOrApply("special", args);
		assertTrue(result instanceof Set);
		@SuppressWarnings("unchecked")
		Set<Object> setResult = (Set<Object>)result;
		assertEquals(3, setResult.size());
		Iterator<Object> iterator = setResult.iterator();
		assertEquals(1, iterator.next());
		assertEquals(2, iterator.next());
		assertEquals(null, iterator.next());
	}

	/**
	 * Checks that an empty argument list result in an AcceleoQueryEvaluationException being thrown.
	 */
	@Test(expected = AcceleoQueryEvaluationException.class)
	public void testEmptyArgumentCall() {
		services.call("toString", new Object[] {});
	}

	/**
	 * Checks that an empty argument list result in an AcceleoQueryEvaluationException being thrown.
	 */
	@Test(expected = AcceleoQueryEvaluationException.class)
	public void testEmptyArgumentCallOrApply() {
		services.callOrApply("toString", new Object[] {});
	}

	/**
	 * Checks that a null argument can be valid.
	 */
	public void testNullArgumentCall() {
		final Object result = services.call("toString", new Object[] {null });
		assertEquals(null, result);
	}

	/**
	 * Checks that a null argument can be valid.
	 */
	public void testNullArgumentCallOrApply() {
		final Object result = services.callOrApply("toString", new Object[] {null });
		assertEquals(null, result);
	}

	/**
	 * Checks a null context.
	 */
	public void testNullArgumentFeatureAccess() {
		services.featureAccess(null, "name");
	}

	/**
	 * Checks that an empty argument list result in an AcceleoQueryEvaluationException being thrown.
	 */
	@Test(expected = AcceleoQueryEvaluationException.class)
	public void testEmptyArgumentCollectionServiceCall() {
		services.collectionServiceCall("toString", new Object[] {});
	}

	/**
	 * Checks that an empty argument list result in an AcceleoQueryEvaluationException being thrown.
	 */
	@Test(expected = AcceleoQueryEvaluationException.class)
	public void testNullArgumentcollectionServiceCall() {
		services.collectionServiceCall("toString", null);
	}

	@Test
	public void testCollectionServiceCallOnSet() {
		Set<Object> object = Sets.newLinkedHashSet();
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
		assertEquals("archetype", services.featureAccess(entry, "key"));
	}

	@Test
	public void testEOperationGeneratedClass() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		final Object result = services.call("getEClassifier",
				new Object[] {EcorePackage.eINSTANCE, "EClass", });
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result);
	}

	@Test
	public void testEOperationGeneratedClassWithEObjectParameter() {
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		final Object result = services.call("isSuperTypeOf", new Object[] {
				EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE.getEPackage(), });
		assertEquals(false, result);
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
		final Object result = services.call("dynamicEOperation", new Object[] {receiver, "EClass", });
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
		final Object result = services.call("dynamicEOperation", new Object[] {receiver, receiver, });
		assertEquals(eCls, result);
	}

}
