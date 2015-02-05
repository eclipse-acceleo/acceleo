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
package org.eclipse.acceleo.query.services.tests;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.AstBuilder;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.CrossReferencerToAQL;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.CrossReferencer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CollectionServicesTest {
	CollectionServices collectionServices;

	/**
	 * This will create the cross referencer that's to be used by the "eInverse" library. It will attempt to
	 * create the cross referencer on the target's resourceSet. If it is null, we'll then attempt to create
	 * the cross referencer on the target's resource. When the resource too is null, we'll create the cross
	 * referencer on the target's root container.
	 * 
	 * @param target
	 *            Target of the cross referencing.
	 */
	private CrossReferenceProvider createEInverseCrossreferencer(EObject target) {
		Resource res = null;
		ResourceSet rs = null;
		CrossReferencer crossReferencer;
		if (target.eResource() != null) {
			res = target.eResource();
		}
		if (res != null && res.getResourceSet() != null) {
			rs = res.getResourceSet();
		}

		if (rs != null) {
			// Manually add the ecore.ecore resource in the list of cross
			// referenced notifiers
			final Resource ecoreResource = EcorePackage.eINSTANCE.getEClass().eResource();
			final Collection<Notifier> notifiers = new ArrayList<Notifier>();
			for (Resource crossReferenceResource : rs.getResources()) {
				if (!"emtl".equals(crossReferenceResource.getURI().fileExtension())) {
					notifiers.add(crossReferenceResource);
				}
			}
			notifiers.add(ecoreResource);

			crossReferencer = new CrossReferencer(notifiers) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
					done();
				}
			};
		} else if (res != null) {
			crossReferencer = new CrossReferencer(res) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
					done();
				}
			};
		} else {
			EObject targetObject = EcoreUtil.getRootContainer(target);
			crossReferencer = new CrossReferencer(targetObject) {
				/** Default SUID. */
				private static final long serialVersionUID = 1L;

				// static initializer
				{
					crossReference();
					done();
				}
			};
		}
		return new CrossReferencerToAQL(crossReferencer);
	}

	@Before
	public void setup() {
		collectionServices = new CollectionServices();
	}

	@Test
	public void testConcat() {
		List<Object> list1 = Lists.newArrayList();
		List<Object> list2 = Lists.newArrayList();

		assertEquals(0, collectionServices.concat(list1, list2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		list1.add(obj1);
		list1.add(obj2);

		list2.add(obj3);

		List<Object> list3 = collectionServices.concat(list1, list2);
		assertEquals(3, list3.size());
		assertEquals(obj1, list3.get(0));
		assertEquals(obj2, list3.get(1));
		assertEquals(obj3, list3.get(2));

		try {
			collectionServices.concat(list1, null);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
		try {
			collectionServices.concat(null, list2);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
	}

	@Test
	public void testAddList() {
		List<Object> list1 = Lists.newArrayList();
		List<Object> list2 = Lists.newArrayList();

		assertEquals(0, collectionServices.add(list1, list2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		list1.add(obj1);
		list1.add(obj2);

		list2.add(obj3);

		List<Object> list3 = collectionServices.add(list1, list2);
		assertEquals(3, list3.size());
		assertEquals(obj1, list3.get(0));
		assertEquals(obj2, list3.get(1));
		assertEquals(obj3, list3.get(2));

		try {
			collectionServices.add(list1, null);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
		try {
			collectionServices.add(null, list2);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
	}

	@Test
	public void testSubList() {
		List<Object> list1 = Lists.newArrayList();
		List<Object> list2 = Lists.newArrayList();

		assertEquals(0, collectionServices.sub(list1, list2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		list1.add(obj1);
		list1.add(obj2);
		List<Object> list3 = collectionServices.sub(list1, list2);
		assertEquals(2, list3.size());
		assertEquals(obj1, list3.get(0));
		assertEquals(obj2, list3.get(1));

		list2.add(obj2);
		list2.add(obj3);

		list3 = collectionServices.sub(list1, list2);
		assertEquals(1, list3.size());
		assertEquals(obj1, list3.get(0));

		try {
			collectionServices.sub(list1, null);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
		try {
			collectionServices.sub(null, list2);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
	}

	@Test
	public void testAddSet() {
		Set<Object> set1 = new HashSet<Object>();
		Set<Object> set2 = new HashSet<Object>();
		assertEquals(0, collectionServices.add(set1, set2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();
		set1.add(obj1);
		set1.add(obj2);
		set2.add(obj3);
		Set<Object> list3 = collectionServices.add(set1, set2);
		assertEquals(3, list3.size());
		assertTrue(list3.contains(obj1));
		assertTrue(list3.contains(obj2));
		assertTrue(list3.contains(obj3));

		try {
			collectionServices.add(set1, null);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
		try {
			collectionServices.add(null, set2);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
	}

	@Test
	public void testSubSet() {
		Set<Object> set1 = new HashSet<Object>();
		Set<Object> set2 = new HashSet<Object>();

		assertEquals(0, collectionServices.sub(set1, set2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		set1.add(obj1);
		set1.add(obj2);
		Set<Object> list3 = collectionServices.sub(set1, set2);
		assertEquals(2, list3.size());
		assertTrue(list3.contains(obj1));
		assertTrue(list3.contains(obj2));

		set2.add(obj2);
		set2.add(obj3);

		list3 = collectionServices.sub(set1, set2);
		assertEquals(1, list3.size());
		assertTrue(list3.contains(obj1));

		try {
			collectionServices.sub(set1, null);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
		try {
			collectionServices.sub(null, set2);
			fail("The collectionServices must trow a NullPointerException");
		} catch (Exception exception) {
			// Do noting we expect to get a NPE
		}
	}

	@Test
	public void testIncludingSet() {
		Set<Object> set = new HashSet<Object>();
		assertEquals(1, collectionServices.including(set, null).size());

		Object elt = new Object();
		Object elt2 = new Object();
		set.add(elt);
		Set<Object> result = collectionServices.including(set, elt2);
		assertTrue(result.contains(elt2));
		assertTrue(result.contains(elt));
		assertEquals(2, result.size());

		result = collectionServices.including(set, elt);
		assertEquals(set, result);
		assertTrue(result.contains(elt));
		assertEquals(1, result.size());
	}

	@Test
	public void testExcludingSet() {
		Set<Object> set = new HashSet<Object>();
		Object elt = new Object();
		Object elt2 = new Object();
		set.add(elt);
		set.add(elt2);
		Set<Object> result = collectionServices.excluding(set, elt2);
		assertFalse(result.contains(elt2));
		assertTrue(result.contains(elt));
		assertEquals(1, result.size());

		set = new HashSet<Object>();
		set.add(elt);
		result = collectionServices.excluding(set, elt2);
		assertEquals(set, result);
		assertTrue(result.contains(elt));
		assertEquals(1, result.size());

		set = new HashSet<Object>();
		assertEquals(0, collectionServices.excluding(set, null).size());
	}

	@Test
	public void testEmpty() {
		assertTrue(collectionServices.isEmpty(Lists.newArrayList()));
	}

	@Test
	public void testNotEmpty() {
		List<Object> list = Lists.newArrayList();
		assertFalse(collectionServices.notEmpty(list));
		list.add(new Object());
		assertTrue(collectionServices.notEmpty(list));
	}

	@Test
	public void testFirst() {
		List<Object> list = Lists.newArrayList();
		Object elt = new Object();
		list.add(elt);
		assertEquals(elt, collectionServices.first(list));
	}

	@Test
	public void testAt() {
		List<Object> list = Lists.newArrayList();
		Object elt = new Object();
		list.add(elt);
		assertEquals(elt, collectionServices.at(list, 1));
		Object elt2 = new Object();
		list.add(elt2);
		assertEquals(elt2, collectionServices.at(list, 2));
		Object elt3 = new Object();
		list.add(elt3);
		list.add(elt3);
		Object elt4 = new Object();
		list.add(elt4);
		assertEquals(elt4, collectionServices.at(list, 5));

		try {
			assertEquals(elt4, collectionServices.at(list, 6));
			fail("The 'at' operation service must throw a NPE.");
		} catch (Exception e) {
			// Do nothing the exception is expected here
		}
	}

	@Test
	public void testRemoveAll() {
		Collection<Object> list1 = Lists.newArrayList();
		Object elt = new Object();
		list1.add(elt);
		Object elt2 = new Object();
		list1.add(elt2);
		Object elt3 = new Object();
		list1.add(elt3);
		list1.add(elt3);
		Object elt4 = new Object();
		list1.add(elt4);

		Collection<Object> list2 = Lists.newArrayList();
		list2.add(elt);
		list2.add(elt2);

		Collection<Object> expectedlist = Lists.newArrayList();
		expectedlist.add(elt3);
		expectedlist.add(elt3);
		expectedlist.add(elt4);

		Collection<?> obtainedList = collectionServices.removeAll(list1, list2);
		Iterator<?> iterator = obtainedList.iterator();
		assertEquals(expectedlist.size(), obtainedList.size());
		assertEquals(elt3, iterator.next());
		assertEquals(elt3, iterator.next());
		assertEquals(elt4, iterator.next());
		assertFalse(iterator.hasNext());

		assertEquals(list1, collectionServices.removeAll(list1, Lists.newArrayList()));
		assertEquals(0, collectionServices.removeAll(Lists.newArrayList(), list2).size());
	}

	@Test
	public void testSize() {
		List<Object> list = Lists.newArrayList();
		Object elt = new Object();
		list.add(elt);
		assertEquals(new Integer(1), collectionServices.size(list));
	}

	@Test
	public void testIncluding() {
		List<Object> list = Lists.newArrayList();
		Object elt = new Object();
		Object elt2 = new Object();
		list.add(elt);
		List<Object> result = collectionServices.including(list, elt2);
		assertTrue(result.contains(elt2));
		assertTrue(result.contains(elt));
		assertEquals(2, result.size());

		result = collectionServices.including(list, elt);
		assertEquals(list, result);
		assertTrue(result.contains(elt));
		assertEquals(1, result.size());
	}

	@Test
	public void testExcluding() {
		List<Object> list = Lists.newArrayList();
		Object elt = new Object();
		Object elt2 = new Object();
		list.add(elt);
		list.add(elt2);
		List<Object> result = collectionServices.excluding(list, elt2);
		assertFalse(result.contains(elt2));
		assertTrue(result.contains(elt));
		assertEquals(1, result.size());

		list = Lists.newArrayList();
		list.add(elt);
		result = collectionServices.excluding(list, elt2);
		assertEquals(list, result);
		assertTrue(result.contains(elt));
		assertEquals(1, result.size());
	}

	@Test
	public void testAsSet() {
		List<Object> list = Lists.newArrayList();
		Object elt = new Object();
		list.add(elt);
		Boolean isSet = false;
		if (collectionServices.asSet(list) instanceof Set) {
			isSet = true;
		}
		assertTrue(isSet);

		Set<Object> set = new HashSet<Object>();
		set.add(elt);
		isSet = false;
		if (collectionServices.asSet(set) instanceof Set) {
			isSet = true;
		}
		assertTrue(isSet);
	}

	@Test
	public void testAsSequence() {
		List<Object> list = Lists.newArrayList();
		Object elt = new Object();
		list.add(elt);
		Boolean isSequence = false;
		if (collectionServices.asSequence(list) instanceof List) {
			isSequence = true;
		}
		assertTrue(isSequence);

		Set<Object> set = new HashSet<Object>();
		set.add(elt);
		isSequence = false;
		if (collectionServices.asSequence(set) instanceof List) {
			isSequence = true;
		}
		assertTrue(isSequence);
	}

	@Test
	public void testSelectList() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				evaluator, selfDeclaration);

		List<Object> nullList = null;
		try {
			collectionServices.select(nullList, lambda);
			fail("The collectionServices must throw a NPE");
		} catch (Exception exception) {
			// Do nothing we expect the NPE
		}

		List<Object> list = Lists.newArrayList();
		collectionServices.select(list, null);

		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		list.add(iterator.next());
		list.add(iterator.next());
		list.add(iterator.next());

		List<Object> newList = collectionServices.select(list, null);
		assertEquals(0, newList.size());

		newList = collectionServices.select(list, lambda);
		assertEquals(0, newList.size());
	}

	@Test
	public void testRejectList() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				evaluator, selfDeclaration);

		List<Object> nullList = null;
		try {
			collectionServices.reject(nullList, lambda);
			fail("The collectionServices must throw a NPE");
		} catch (Exception exception) {
			// Do nothing we expect the NPE
		}

		List<Object> list = Lists.newArrayList();
		collectionServices.reject(list, null);

		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		list.add(iterator.next());
		list.add(iterator.next());
		list.add(iterator.next());

		List<Object> newList = collectionServices.reject(list, null);
		assertEquals(0, newList.size());

		newList = collectionServices.reject(list, lambda);
		assertEquals(0, newList.size());
	}

	@Test
	public void testRejectSet() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				evaluator, selfDeclaration);

		Set<Object> nullList = null;
		try {
			collectionServices.reject(nullList, lambda);
			fail("The collectionServices must throw a NPE");
		} catch (Exception exception) {
			// Do nothing we expect the NPE
		}

		Set<Object> set = new HashSet<Object>();
		collectionServices.reject(set, null);

		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		set.add(iterator.next());
		set.add(iterator.next());
		set.add(iterator.next());

		Set<Object> newList = collectionServices.reject(set, null);
		assertEquals(0, newList.size());

		newList = collectionServices.reject(set, lambda);
		assertEquals(0, newList.size());
	}

	@Test
	public void testCollectList() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				evaluator, selfDeclaration);

		List<Object> nullList = null;
		try {
			collectionServices.reject(nullList, lambda);
			fail("The collectionServices must throw a NPE");
		} catch (Exception exception) {
			// Do nothing we expect the NPE
		}

		List<Object> list = Lists.newArrayList();
		collectionServices.reject(list, null);

		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		list.add(iterator.next());
		list.add(iterator.next());
		list.add(iterator.next());

		List<Object> newList = collectionServices.collect(list, null);
		assertEquals(0, newList.size());

		newList = collectionServices.reject(list, lambda);
		assertEquals(0, newList.size());
	}

	@Test
	public void testCollectSet() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				evaluator, selfDeclaration);

		Set<Object> nullList = null;
		try {
			collectionServices.reject(nullList, lambda);
			fail("The collectionServices must throw a NPE");
		} catch (Exception exception) {
			// Do nothing we expect the NPE
		}

		Set<Object> set = new HashSet<Object>();
		collectionServices.reject(set, null);

		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		set.add(iterator.next());
		set.add(iterator.next());
		set.add(iterator.next());

		Set<Object> newList = collectionServices.collect(set, null);
		assertEquals(0, newList.size());

		newList = collectionServices.reject(set, lambda);
		assertEquals(0, newList.size());
	}

	@Test
	public void testSelectSet() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				evaluator, selfDeclaration);

		Set<Object> nullList = null;
		try {
			collectionServices.select(nullList, lambda);
			fail("The collectionServices must throw a NPE");
		} catch (Exception exception) {
			// Do nothing we expect the NPE
		}

		Set<Object> set = new HashSet<Object>();
		collectionServices.select(set, null);

		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		set.add(iterator.next());
		set.add(iterator.next());
		set.add(iterator.next());

		Set<Object> newList = collectionServices.select(set, null);
		assertEquals(0, newList.size());

		newList = collectionServices.select(set, lambda);
		assertEquals(0, newList.size());
	}

	@Test
	public void testFilterSetNullSetAndNullType() {
		final Set<Object> result = collectionServices.filter((Set<Object>)null, null);

		assertEquals(null, result);
	}

	@Test
	public void testFilterSetNullSet() {
		final Set<Object> result = collectionServices.filter((Set<Object>)null, EcorePackage.eINSTANCE
				.getEClass());

		assertEquals(null, result);
	}

	@Test
	public void testFilterSetNullType() {
		final Set<Object> set = Sets.newLinkedHashSet();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		final Set<Object> result = collectionServices.filter(set, null);

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterSet() {
		final Set<Object> set = Sets.newLinkedHashSet();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		final Set<Object> result = collectionServices.filter(set, EcorePackage.eINSTANCE.getEPackage());

		assertEquals(1, result.size());
		assertEquals(true, result.contains(EcorePackage.eINSTANCE));
	}

	@Test
	public void testFilterListNullListAndNullType() {
		final List<Object> result = collectionServices.filter((List<Object>)null, null);

		assertEquals(null, result);
	}

	@Test
	public void testFilterListNullList() {
		final List<Object> result = collectionServices.filter((List<Object>)null, EcorePackage.eINSTANCE
				.getEClass());

		assertEquals(null, result);
	}

	@Test
	public void testFilterListNullType() {
		final List<Object> list = Lists.newArrayList();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final List<Object> result = collectionServices.filter(list, null);

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterList() {
		final List<Object> list = Lists.newArrayList();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final List<Object> result = collectionServices.filter(list, EcorePackage.eINSTANCE.getEPackage());

		assertEquals(1, result.size());
		assertEquals(true, result.contains(EcorePackage.eINSTANCE));
	}

	@Test
	public void testSepNullListNullSeparator() {
		final List<Object> result = collectionServices.sep(null, null);

		assertEquals(null, result);
	}

	@Test
	public void testSepNullList() {
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep(null, separator);

		assertEquals(null, result);
	}

	@Test
	public void testSepNullSeparator() {
		final List<Object> list = Lists.newArrayList();

		final List<Object> result = collectionServices.sep(list, null);

		assertEquals(0, result.size());
	}

	@Test
	public void testSep() {
		final List<Object> list = Lists.newArrayList();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep(list, separator);

		assertEquals(7, result.size());
		assertEquals(this, result.get(0));
		assertEquals(separator, result.get(1));
		assertEquals("", result.get(2));
		assertEquals(separator, result.get(3));
		assertEquals(EcorePackage.eINSTANCE, result.get(4));
		assertEquals(separator, result.get(5));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result.get(6));
	}

	@Test
	public void testSepPrefixSuffixNullListNullSeparator() {
		final List<Object> result = collectionServices.sep(null, null);

		assertEquals(null, result);
	}

	@Test
	public void testSepPrefixSuffixNullList() {
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep(null, separator);

		assertEquals(null, result);
	}

	@Test
	public void testSepPrefixSuffixNullSeparator() {
		final List<Object> list = Lists.newArrayList();

		final List<Object> result = collectionServices.sep(list, null);

		assertEquals(0, result.size());
	}

	@Test
	public void testSepPrefixSuffix() {
		final List<Object> list = Lists.newArrayList();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());
		final Object prefix = new Object();
		final Object separator = new Object();
		final Object suffix = new Object();

		final List<Object> result = collectionServices.sep(list, prefix, separator, suffix);

		assertEquals(9, result.size());
		assertEquals(prefix, result.get(0));
		assertEquals(this, result.get(1));
		assertEquals(separator, result.get(2));
		assertEquals("", result.get(3));
		assertEquals(separator, result.get(4));
		assertEquals(EcorePackage.eINSTANCE, result.get(5));
		assertEquals(separator, result.get(6));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result.get(7));
		assertEquals(suffix, result.get(8));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testLastNullList() {
		Object result = collectionServices.last(null);

		assertEquals(null, result);
	}

	@Test(expected = java.lang.ArrayIndexOutOfBoundsException.class)
	public void testLastEmptyList() {
		final List<Object> list = Lists.newArrayList();

		Object result = collectionServices.last(list);

		assertEquals(null, result);
	}

	@Test
	public void testLast() {
		final List<Object> list = Lists.newArrayList();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		Object result = collectionServices.last(list);

		assertEquals(EcorePackage.eINSTANCE.getEClass(), result);
	}

	@Test
	public void testExcludes() {
		final List<Object> list = Lists.newArrayList();

		assertEquals(true, collectionServices.excludes(list, this));

		list.add(this);

		assertEquals(false, collectionServices.excludes(list, this));
	}

	@Test
	public void testIncludes() {
		final List<Object> list = Lists.newArrayList();

		assertEquals(false, collectionServices.includes(list, this));

		list.add(this);

		assertEquals(true, collectionServices.includes(list, this));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAnyNullCollectionNullLambda() {
		collectionServices.any(null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAnyNullCollection() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				evaluator, selfDeclaration);

		collectionServices.any(null, lambda);
	}

	@Test
	public void testAnySetNullLambda() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Object result = collectionServices.any(list, null);
		assertEquals(null, result);
	}

	@Test
	public void testAnyListNullLambda() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Object result = collectionServices.any(set, null);
		assertEquals(null, result);
	}

	@Test
	public void testAnySetNotBooleanLambda() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				evaluator, selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Object result = collectionServices.any(set, lambda);
		assertEquals(null, result);
	}

	@Test
	public void testAnyListNotBooleanLambda() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				evaluator, selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Object result = collectionServices.any(list, lambda);
		assertEquals(null, result);
	}

	@Test
	public void testAnySet() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), evaluator, selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Object result = collectionServices.any(set, lambda);
		assertEquals(Integer.valueOf(3), result);
	}

	@Test
	public void testAnyList() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = new QueryEnvironment(
				createEInverseCrossreferencer(EcorePackage.eINSTANCE), Logger.getLogger("AstEvaluatorTest"));
		AstEvaluator evaluator = new AstEvaluator(new EvaluationServices(environment, true));
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), evaluator, selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Object result = collectionServices.any(list, lambda);
		assertEquals(Integer.valueOf(3), result);
	}

}
