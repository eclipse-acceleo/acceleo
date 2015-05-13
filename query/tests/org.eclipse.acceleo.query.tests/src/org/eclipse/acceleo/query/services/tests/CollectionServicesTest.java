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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.AstBuilder;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.CrossReferencerToAQL;
import org.eclipse.acceleo.query.runtime.impl.LambdaValue;
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
import static org.junit.Assert.assertSame;
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
	public void testReverseSet() {
		Set<Object> set = new LinkedHashSet<Object>();
		assertEquals(0, collectionServices.reverse(set).size());
		Object elt = new Object();
		Object elt2 = new Object();
		set.add(elt);
		set.add(elt2);
		assertEquals(2, collectionServices.reverse(set).size());

		Iterator<Object> iterator = collectionServices.reverse(set).iterator();
		assertSame(elt2, iterator.next());
		assertSame(elt, iterator.next());
		Object elt3 = new Object();
		set.add(elt3);
		iterator = collectionServices.reverse(set).iterator();
		assertSame(elt3, iterator.next());
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
		list.add(new Object());
		assertEquals(elt, collectionServices.first(list));
	}

	@Test
	public void testFirstEmptyList() {
		List<Object> list = Lists.newArrayList();
		assertEquals(null, collectionServices.first(list));
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
	public void testReverse() {
		List<Object> list = Lists.newArrayList();
		assertEquals(0, collectionServices.reverse(list).size());
		Object elt = new Object();
		Object elt2 = new Object();
		list.add(elt);
		list.add(elt);
		list.add(elt);
		list.add(elt2);
		assertEquals(4, collectionServices.reverse(list).size());

		List<Object> result = collectionServices.reverse(list);
		assertSame(elt2, result.get(0));
		assertSame(elt, result.get(1));
		Object elt3 = new Object();
		list.add(elt3);
		result = collectionServices.reverse(list);
		assertSame(elt3, result.get(0));
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
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		List<Object> nullList = null;
		try {
			collectionServices.select(nullList, new LambdaValue(lambda, evaluator));
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

		newList = collectionServices.select(list, new LambdaValue(lambda, evaluator));
		assertEquals(0, newList.size());
	}

	private IQueryEnvironment createEnvironment() {
		return Query.newEnvironmentWithDefaultServices(createEInverseCrossreferencer(EcorePackage.eINSTANCE));
	}

	@Test
	public void testRejectList() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		List<Object> nullList = null;
		try {
			collectionServices.reject(nullList, new LambdaValue(lambda, evaluator));
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

		newList = collectionServices.reject(list, new LambdaValue(lambda, evaluator));
		assertEquals(0, newList.size());
	}

	@Test
	public void testRejectSet() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		Set<Object> nullList = null;
		try {
			collectionServices.reject(nullList, new LambdaValue(lambda, evaluator));
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

		newList = collectionServices.reject(set, new LambdaValue(lambda, evaluator));
		assertEquals(0, newList.size());
	}

	@Test
	public void testCollectList() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		List<Object> nullList = null;
		try {
			collectionServices.reject(nullList, new LambdaValue(lambda, evaluator));
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

		newList = collectionServices.reject(list, new LambdaValue(lambda, evaluator));
		assertEquals(0, newList.size());
	}

	@Test
	public void testCollectSet() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		Set<Object> nullList = null;
		try {
			collectionServices.reject(nullList, new LambdaValue(lambda, evaluator));
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

		newList = collectionServices.reject(set, new LambdaValue(lambda, evaluator));
		assertEquals(0, newList.size());
	}

	@Test
	public void testSelectSet() throws URISyntaxException, IOException {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		Set<Object> nullList = null;
		try {
			collectionServices.select(nullList, new LambdaValue(lambda, evaluator));
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

		newList = collectionServices.select(set, new LambdaValue(lambda, evaluator));
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
		assertTrue(result.contains(EcorePackage.eINSTANCE));
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
		assertTrue(result.contains(EcorePackage.eINSTANCE));
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

	@Test
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

		assertTrue(collectionServices.excludes(list, this));

		list.add(this);

		assertEquals(false, collectionServices.excludes(list, this));
	}

	@Test
	public void testIncludes() {
		final List<Object> list = Lists.newArrayList();

		assertEquals(false, collectionServices.includes(list, this));

		list.add(this);

		assertTrue(collectionServices.includes(list, this));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAnyNullCollectionNullLambda() {
		collectionServices.any(null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAnyNullCollection() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		collectionServices.any(null, new LambdaValue(lambda, evaluator));
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
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Object result = collectionServices.any(set, new LambdaValue(lambda, evaluator));
		assertEquals(null, result);
	}

	@Test
	public void testAnyListNotBooleanLambda() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Object result = collectionServices.any(list, new LambdaValue(lambda, evaluator));
		assertEquals(null, result);
	}

	@Test
	public void testAnySet() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Object result = collectionServices.any(set, new LambdaValue(lambda, evaluator));
		assertEquals(Integer.valueOf(3), result);
	}

	@Test
	public void testAnyList() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Object result = collectionServices.any(list, new LambdaValue(lambda, evaluator));
		assertEquals(Integer.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testCountSetNullNull() {
		collectionServices.count((Set<Object>)null, null);
	}

	@Test
	public void testCountSetNull() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(null);

		Integer result = collectionServices.count(set, null);
		assertEquals(Integer.valueOf(1), result);
	}

	@Test
	public void testCountSet() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(null);

		Integer result = collectionServices.count(set, Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testCountListNullNull() {
		collectionServices.count((List<Object>)null, null);
	}

	@Test
	public void testCountListNull() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(null);

		Integer result = collectionServices.count(list, null);
		assertEquals(Integer.valueOf(1), result);
	}

	@Test
	public void testCountList() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(null);

		Integer result = collectionServices.count(list, Integer.valueOf(1));
		assertEquals(Integer.valueOf(1), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExistsNullCollection() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		collectionServices.exists(null, new LambdaValue(lambda, evaluator));
	}

	@Test
	public void testExistsSetNullLambda() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(list, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExistsListNullLambda() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(set, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExistsSetNotBooleanLambda() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(set, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExistsListNotBooleanLambda() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(list, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExistsSet() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(set, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testExistsList() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(list, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.TRUE, result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testForAllNullCollection() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		collectionServices.forAll(null, new LambdaValue(lambda, evaluator));
	}

	@Test
	public void testForAllSetNullLambda() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(list, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testForAllListNullLambda() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(set, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testForAllSetNotBooleanLambda() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(set, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testForAllListNotBooleanLambda() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.featureAccess(builder.varRef("self"), "expression"),
				selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(list, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testForAllSetTrue() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(0)), selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(set, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testForAllListTrue() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(0)), selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(list, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testForAllSetFalse() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(set, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testForAllListFalse() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(list, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllNullNull() {
		collectionServices.excludesAll(null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllSetNull() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.excludesAll(set, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllListNull() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.excludesAll(list, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllNullSet() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.excludesAll(null, set);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllNullList() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.excludesAll(null, list);
	}

	@Test
	public void testExcludesAllSetSetFalse() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(set, set1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExcludesAllSetSetTrue() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(6));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(set, set1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testExcludesAllSetListFalse() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(set, list1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExcludesAllSetListTrue() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(6));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(set, list1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testExcludesAllListSetFalse() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(list, set1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExcludesAllListSetTrue() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(6));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(list, set1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testExcludesAllListListFalse() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(list, list1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExcludesAllListListTrue() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(6));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(list, list1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIncludesAllNullNull() {
		collectionServices.includesAll(null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIncludesAllSetNull() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.includesAll(set, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIncludesAllListNull() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.includesAll(list, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIncludesAllNullSet() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.includesAll(null, set);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIncludesAllNullList() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.includesAll(null, list);
	}

	@Test
	public void testIncludesAllSetSetFalse() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.includesAll(set, set1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIncludesAllSetSetTrue() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));

		Boolean result = collectionServices.includesAll(set, set1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testIncludesAllSetListFalse() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.includesAll(set, list1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIncludesAllSetListTrue() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));

		Boolean result = collectionServices.includesAll(set, list1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testIncludesAllListSetFalse() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.includesAll(list, set1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIncludesAllListSetTrue() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));

		Boolean result = collectionServices.includesAll(list, set1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testIncludesAllListListFalse() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.includesAll(list, list1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIncludesAllListListTrue() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));

		Boolean result = collectionServices.includesAll(list, list1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIsUniqueNullNull() {
		collectionServices.isUnique(null, null);
	}

	@Test
	public void testIsUniqueSetNull() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.isUnique(set, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIsUniqueListNull() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.isUnique(list, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIsUniqueSetTrue() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.isUnique(set, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testIsUniqueSetFalse() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.isUnique(set, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIsUniqueListTrue() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.isUnique(list, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testIsUniqueListFalse() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.isUnique(list, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testOneNullNull() {
		collectionServices.one(null, null);
	}

	@Test
	public void testOneSetNull() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(set, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testOneListNull() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(list, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testOneSetTrue() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(set, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testOneSetFalse() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(3)), selfDeclaration);

		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(set, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testOneListTrue() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(2)), selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(list, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testOneListFalse() {
		AstBuilder builder = new AstBuilder();
		IQueryEnvironment environment = createEnvironment();
		AstEvaluator evaluator = new AstEvaluator(environment);
		VariableDeclaration selfDeclaration = (VariableDeclaration)EcoreUtil
				.create(AstPackage.Literals.VARIABLE_DECLARATION);
		selfDeclaration.setName("self");
		Lambda lambda = builder.lambda(builder.callService(CallType.CALLSERVICE, "greaterThan", builder
				.varRef("self"), builder.integerLiteral(3)), selfDeclaration);

		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(list, new LambdaValue(lambda, evaluator));
		assertEquals(Boolean.FALSE, result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSumNull() {
		collectionServices.sum(null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSumSetNotNumber() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add("potatoes");
		set.add(Integer.valueOf(3));

		collectionServices.sum(set);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSumListNotNumber() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add("potatoes");
		list.add(Integer.valueOf(3));

		collectionServices.sum(list);
	}

	@Test
	public void testSumSet() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Double.valueOf(2));
		set.add(Integer.valueOf(3));

		Double result = collectionServices.sum(set);
		assertEquals(Double.valueOf(6), result);
	}

	@Test
	public void testSumList() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Double.valueOf(2));
		list.add(Integer.valueOf(3));

		Double result = collectionServices.sum(list);
		assertEquals(Double.valueOf(6), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIndexOfNullNull() {
		collectionServices.indexOf(null, null);
	}

	@Test
	public void testIndexOfListNull() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Integer result = collectionServices.indexOf(list, null);
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void testIndexOfListNotInList() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Integer result = collectionServices.indexOf(list, Integer.valueOf(7));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void testIndexOfListInList() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Integer result = collectionServices.indexOf(list, Integer.valueOf(2));
		assertEquals(Integer.valueOf(2), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testInsetAtNull() {
		collectionServices.insertAt(null, 1, null);
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testInsetAtUnderLowerBound() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		collectionServices.insertAt(list, 0, null);
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testInsetAtOverUpperBound() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		collectionServices.insertAt(list, 5, null);
	}

	@Test
	public void testInsetAt() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		final List<Object> result = collectionServices.insertAt(list, 1, null);
		assertTrue(list != result);
		assertEquals(4, result.size());
		assertEquals(null, result.get(0));
		assertEquals(Integer.valueOf(1), result.get(1));
		assertEquals(Integer.valueOf(2), result.get(2));
		assertEquals(Integer.valueOf(3), result.get(3));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testPrependNull() {
		collectionServices.prepend(null, null);
	}

	@Test
	public void testPrepend() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		final List<Object> result = collectionServices.prepend(list, null);
		assertTrue(list != result);
		assertEquals(4, result.size());
		assertEquals(null, result.get(0));
		assertEquals(Integer.valueOf(1), result.get(1));
		assertEquals(Integer.valueOf(2), result.get(2));
		assertEquals(Integer.valueOf(3), result.get(3));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionSetNullNull() {
		collectionServices.intersection((Set<?>)null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionSetSetNull() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		collectionServices.intersection(set, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionSetNullSet() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		collectionServices.intersection((Set<?>)null, set);
	}

	@Test
	public void testIntersectionSetSameSize() {
		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));
		set1.add(Integer.valueOf(3));

		Set<Object> set2 = Sets.newLinkedHashSet();
		set2.add(Integer.valueOf(3));
		set2.add(Integer.valueOf(4));
		set2.add(Integer.valueOf(1));

		Set<Object> result = collectionServices.intersection(set1, set2);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first set)
		Iterator<Object> it = result.iterator();
		assertEquals(1, it.next());
		assertEquals(3, it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIntersectionSetLongestFirst() {
		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));
		set1.add(Integer.valueOf(3));
		set1.add(Integer.valueOf(4));

		Set<Object> set2 = Sets.newLinkedHashSet();
		set2.add(Integer.valueOf(3));
		set2.add(Integer.valueOf(5));
		set2.add(Integer.valueOf(1));

		Set<Object> result = collectionServices.intersection(set1, set2);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first set)
		Iterator<Object> it = result.iterator();
		assertEquals(1, it.next());
		assertEquals(3, it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIntersectionSetLongestSecond() {
		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));
		set1.add(Integer.valueOf(3));

		Set<Object> set2 = Sets.newLinkedHashSet();
		set2.add(Integer.valueOf(3));
		set2.add(Integer.valueOf(4));
		set2.add(Integer.valueOf(1));
		set2.add(Integer.valueOf(5));

		Set<Object> result = collectionServices.intersection(set1, set2);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first set)
		Iterator<Object> it = result.iterator();
		assertEquals(1, it.next());
		assertEquals(3, it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIntersectionSetDifferentClasses() {
		Set<Object> set1 = Sets.newLinkedHashSet();
		set1.add("aString");
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));
		set1.add("anotherString");
		set1.add("aThirdString");

		Set<String> set2 = Sets.newLinkedHashSet();
		set2.add("aThirdString");
		set2.add("a");
		set2.add("b");
		set2.add("anotherString");
		set2.add("c");

		Set<Object> result = collectionServices.intersection(set1, set2);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first set)
		Iterator<Object> it = result.iterator();
		assertEquals("anotherString", it.next());
		assertEquals("aThirdString", it.next());
		assertFalse(it.hasNext());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionListNullNull() {
		collectionServices.intersection((List<?>)null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionListSetNull() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		collectionServices.intersection(list, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionListNullSet() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		collectionServices.intersection((List<?>)null, list);
	}

	@Test
	public void testIntersectionListSameSize() {
		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));
		list1.add(Integer.valueOf(3));

		List<Object> list2 = Lists.newArrayList();
		list2.add(Integer.valueOf(3));
		list2.add(Integer.valueOf(4));
		list2.add(Integer.valueOf(1));

		List<Object> result = collectionServices.intersection(list1, list2);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first list)
		Iterator<Object> it = result.iterator();
		assertEquals(1, it.next());
		assertEquals(3, it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIntersectionListLongestFirst() {
		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));
		list1.add(Integer.valueOf(3));
		list1.add(Integer.valueOf(4));

		List<Object> list2 = Lists.newArrayList();
		list2.add(Integer.valueOf(3));
		list2.add(Integer.valueOf(5));
		list2.add(Integer.valueOf(1));

		List<Object> result = collectionServices.intersection(list1, list2);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first list)
		Iterator<Object> it = result.iterator();
		assertEquals(1, it.next());
		assertEquals(3, it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIntersectionListLongestSecond() {
		List<Object> list1 = Lists.newArrayList();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));
		list1.add(Integer.valueOf(3));

		List<Object> list2 = Lists.newArrayList();
		list2.add(Integer.valueOf(3));
		list2.add(Integer.valueOf(4));
		list2.add(Integer.valueOf(1));
		list2.add(Integer.valueOf(5));

		List<Object> result = collectionServices.intersection(list1, list2);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first list)
		Iterator<Object> it = result.iterator();
		assertEquals(1, it.next());
		assertEquals(3, it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIntersectionListDifferentClasses() {
		List<Object> list1 = Lists.newArrayList();
		list1.add("aString");
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));
		list1.add("anotherString");
		list1.add("aThirdString");

		List<String> list2 = Lists.newArrayList();
		list2.add("aThirdString");
		list2.add("a");
		list2.add("b");
		list2.add("anotherString");
		list2.add("c");

		List<Object> result = collectionServices.intersection(list1, list2);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first list)
		Iterator<Object> it = result.iterator();
		assertEquals("anotherString", it.next());
		assertEquals("aThirdString", it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIntersectionListDuplicatesInFirst() {
		List<String> list1 = Lists.newArrayList();
		list1.add("a");
		list1.add("b");
		list1.add("a");
		list1.add("c");
		list1.add("b");

		List<String> list2 = Lists.newArrayList();
		list2.add("b");
		list2.add("d");
		list2.add("c");

		List<String> result = collectionServices.intersection(list1, list2);
		assertEquals(3, result.size());
		// make sure the result is in the order we want (order of the first list)
		Iterator<String> it = result.iterator();
		assertEquals("b", it.next());
		assertEquals("c", it.next());
		assertEquals("b", it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIntersectionListDuplicatesInSecond() {
		List<String> list1 = Lists.newArrayList();
		list1.add("b");
		list1.add("d");
		list1.add("c");

		List<String> list2 = Lists.newArrayList();
		list2.add("a");
		list2.add("b");
		list2.add("a");
		list2.add("c");
		list2.add("b");

		List<String> result = collectionServices.intersection(list1, list2);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first list)
		Iterator<String> it = result.iterator();
		assertEquals("b", it.next());
		assertEquals("c", it.next());
		assertFalse(it.hasNext());
	}

	@Test
	public void testIntersectionListDuplicatesOrder() {
		List<String> list1 = Lists.newArrayList();
		list1.add("a");
		list1.add("b");
		list1.add("a");
		list1.add("c");
		list1.add("b");

		List<String> list2 = Lists.newArrayList();
		list2.add("b");
		list2.add("a");
		list2.add("d");
		list2.add("b");

		List<String> result = collectionServices.intersection(list1, list2);
		assertEquals(4, result.size());
		// make sure the result is in the order we want (order of the first list)
		Iterator<String> it = result.iterator();
		assertEquals("a", it.next());
		assertEquals("b", it.next());
		assertEquals("a", it.next());
		assertEquals("b", it.next());
		assertFalse(it.hasNext());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void TestSubOrderedSetNull() {
		collectionServices.subOrderedSet(null, Integer.valueOf(1), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void TestSubOrderedSetStartTooLow() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.subOrderedSet(set, Integer.valueOf(0), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void TestSubOrderedSetStartTooHi() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.subOrderedSet(set, Integer.valueOf(5), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void TestSubOrderedSetEndTooLow() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.subOrderedSet(set, Integer.valueOf(1), Integer.valueOf(0));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void TestSubOrderedSetEndTooHi() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.subOrderedSet(set, Integer.valueOf(1), Integer.valueOf(5));
	}

	@Test
	public void TestSubOrderedSetStartEqualsEnd() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<Object> result = collectionServices.subOrderedSet(set, Integer.valueOf(3), Integer
				.valueOf(3));
		assertEquals(1, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(Integer.valueOf(3), it.next());
	}

	@Test
	public void TestSubOrderedSet() {
		Set<Object> set = Sets.newLinkedHashSet();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<Object> result = collectionServices.subOrderedSet(set, Integer.valueOf(2), Integer
				.valueOf(4));
		assertEquals(3, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(4), it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void TestSubSequenceNull() {
		collectionServices.subSequence(null, Integer.valueOf(1), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void TestSubSequenceStartTooLow() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.subSequence(list, Integer.valueOf(0), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void TestSubSequenceStartTooHi() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.subSequence(list, Integer.valueOf(5), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void TestSubSequenceEndTooLow() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.subSequence(list, Integer.valueOf(1), Integer.valueOf(0));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void TestSubSequenceEndTooHi() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.subSequence(list, Integer.valueOf(1), Integer.valueOf(5));
	}

	@Test
	public void TestSubSequenceStartEqualsEnd() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		final List<Object> result = collectionServices.subSequence(list, Integer.valueOf(3), Integer
				.valueOf(3));
		assertEquals(1, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(Integer.valueOf(3), it.next());
	}

	@Test
	public void TestSubSequence() {
		List<Object> list = Lists.newArrayList();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		final List<Object> result = collectionServices.subSequence(list, Integer.valueOf(2), Integer
				.valueOf(4));
		assertEquals(3, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(4), it.next());
	}
}
