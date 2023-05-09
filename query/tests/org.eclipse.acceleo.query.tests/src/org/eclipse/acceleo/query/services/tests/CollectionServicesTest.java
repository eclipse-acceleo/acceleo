/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.LambdaValue;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.impl.QueryEvaluationEngine;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.services.tests.ComparableServicesTest.TestComparable;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.acceleo.query.tests.anydsl.AnydslFactory;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Company;
import org.eclipse.acceleo.query.tests.anydsl.Food;
import org.eclipse.acceleo.query.tests.anydsl.World;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class CollectionServicesTest {
	CollectionServices collectionServices;

	@Before
	public void setup() {
		collectionServices = new CollectionServices();
	}

	@Test
	public void testConcatListList() {
		List<Object> list1 = new ArrayList<>();
		List<Object> list2 = new ArrayList<>();

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
	}

	@Test
	public void testConcatListSet() {
		List<Object> list = new ArrayList<>();
		Set<Object> set = new LinkedHashSet<>();

		assertEquals(0, collectionServices.concat(list, set).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		list.add(obj1);
		list.add(obj2);

		set.add(obj3);

		List<Object> list3 = collectionServices.concat(list, set);
		assertEquals(3, list3.size());
		assertEquals(obj1, list3.get(0));
		assertEquals(obj2, list3.get(1));
		assertEquals(obj3, list3.get(2));
	}

	@Test
	public void testConcatListListDifferentTypes() {
		List<String> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();

		assertEquals(0, collectionServices.concat(list1, list2).size());

		list1.add("a");
		list1.add("b");

		list2.add(1);

		List<? extends Object> list3 = collectionServices.concat(list1, list2);
		assertEquals(3, list3.size());
		assertEquals("a", list3.get(0));
		assertEquals("b", list3.get(1));
		assertEquals(1, list3.get(2));
	}

	@Test
	public void testConcatListSetDifferentTypes() {
		List<String> list = new ArrayList<>();
		Set<Integer> set = new LinkedHashSet<>();

		assertEquals(0, collectionServices.concat(list, set).size());

		list.add("a");
		list.add("b");

		set.add(1);

		List<? extends Object> list3 = collectionServices.concat(list, set);
		assertEquals(3, list3.size());
		assertEquals("a", list3.get(0));
		assertEquals("b", list3.get(1));
		assertEquals(1, list3.get(2));
	}

	@Test(expected = NullPointerException.class)
	public void testConcatListNull() {
		List<Object> list = new ArrayList<>();

		collectionServices.concat(list, null);
	}

	@Test(expected = NullPointerException.class)
	public void testConcatNullList() {
		List<Object> list = new ArrayList<>();

		collectionServices.concat(list, null);
	}

	@Test
	public void testConcatListsWithDuplicates() {
		List<Object> list1 = new ArrayList<>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		list1.add("c");
		list1.add("c");

		List<Object> list2 = new ArrayList<>();
		list2.add("c");
		list2.add("b");
		list2.add("a");

		List<Object> result = collectionServices.concat(list1, list2);
		assertEquals(8, result.size());
		assertEquals("a", result.get(0));
		assertEquals("b", result.get(1));
		assertEquals("c", result.get(2));
		assertEquals("c", result.get(3));
		assertEquals("c", result.get(4));
		assertEquals("c", result.get(5));
		assertEquals("b", result.get(6));
		assertEquals("a", result.get(7));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add("c");
		set1.add("b");
		set1.add("a");

		List<Object> result2 = collectionServices.concat(list1, set1);
		assertEquals(8, result2.size());
		assertEquals("a", result2.get(0));
		assertEquals("b", result2.get(1));
		assertEquals("c", result2.get(2));
		assertEquals("c", result2.get(3));
		assertEquals("c", result2.get(4));
		assertEquals("c", result2.get(5));
		assertEquals("b", result2.get(6));
		assertEquals("a", result2.get(7));
	}

	@Test
	public void testConcatSetList() {
		Set<Object> set = new LinkedHashSet<>();
		List<Object> list = new ArrayList<>();

		assertEquals(0, collectionServices.concat(set, list).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		set.add(obj1);
		set.add(obj2);

		list.add(obj3);

		Set<Object> res = collectionServices.concat(set, list);
		assertEquals(3, res.size());
		Iterator<Object> itr = res.iterator();
		assertEquals(obj1, itr.next());
		assertEquals(obj2, itr.next());
		assertEquals(obj3, itr.next());
	}

	@Test
	public void testConcatSetSet() {
		Set<Object> set1 = new LinkedHashSet<>();
		Set<Object> set2 = new LinkedHashSet<>();

		assertEquals(0, collectionServices.concat(set1, set2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		set1.add(obj1);
		set1.add(obj2);

		set2.add(obj3);

		Set<Object> res = collectionServices.concat(set1, set2);
		assertEquals(3, res.size());
		Iterator<Object> itr = res.iterator();
		assertEquals(obj1, itr.next());
		assertEquals(obj2, itr.next());
		assertEquals(obj3, itr.next());
	}

	@Test
	public void testConcatSetListDifferentTypes() {
		Set<String> set = new LinkedHashSet<>();
		List<Integer> list = new ArrayList<>();

		assertEquals(0, collectionServices.concat(set, list).size());

		set.add("a");
		set.add("b");

		list.add(1);

		Set<? extends Object> res = collectionServices.concat(set, list);
		assertEquals(3, res.size());
		Iterator<? extends Object> itr = res.iterator();
		assertEquals("a", itr.next());
		assertEquals("b", itr.next());
		assertEquals(1, itr.next());
	}

	@Test
	public void testConcatSetSetDifferentTypes() {
		Set<String> set1 = new LinkedHashSet<>();
		Set<Integer> set2 = new LinkedHashSet<>();

		assertEquals(0, collectionServices.concat(set1, set2).size());

		set1.add("a");
		set1.add("b");

		set2.add(1);

		Set<? extends Object> res = collectionServices.concat(set1, set2);
		assertEquals(3, res.size());
		Iterator<? extends Object> itr = res.iterator();
		assertEquals("a", itr.next());
		assertEquals("b", itr.next());
		assertEquals(1, itr.next());
	}

	@Test(expected = NullPointerException.class)
	public void testConcatSetNull() {
		Set<Object> set = new LinkedHashSet<>();

		collectionServices.concat(set, null);
	}

	@Test(expected = NullPointerException.class)
	public void testConcatNullSet() {
		Set<Object> set = new LinkedHashSet<>();

		collectionServices.concat(set, null);
	}

	@Test
	public void testConcatSetsWithDuplicates() {
		Set<Object> set1 = new LinkedHashSet<>();
		set1.add("a");
		set1.add("b");
		set1.add("c");
		set1.add("d");

		Set<String> set2 = new LinkedHashSet<>();
		set2.add("e");
		set2.add("d");
		set2.add("c");
		set2.add("b");

		Set<Object> result = collectionServices.concat(set1, set2);
		assertEquals(5, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals("a", itr.next());
		assertEquals("b", itr.next());
		assertEquals("c", itr.next());
		assertEquals("d", itr.next());
		assertEquals("e", itr.next());

		List<Object> list1 = new ArrayList<>();
		list1.add("e");
		list1.add("d");
		list1.add("c");
		list1.add("b");

		Set<Object> result2 = collectionServices.concat(set1, list1);
		assertEquals(5, result2.size());
		Iterator<Object> itr2 = result2.iterator();
		assertEquals("a", itr2.next());
		assertEquals("b", itr2.next());
		assertEquals("c", itr2.next());
		assertEquals("d", itr2.next());
		assertEquals("e", itr2.next());
	}

	@Test
	public void testUnionListList() {
		List<Object> list1 = new ArrayList<>();
		List<Object> list2 = new ArrayList<>();

		assertEquals(0, collectionServices.union(list1, list2).size());

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
	}

	@Test
	public void testUnionListListDifferentTypes() {
		List<String> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();

		assertEquals(0, collectionServices.union(list1, list2).size());

		list1.add("a");
		list1.add("b");

		list2.add(1);

		List<? extends Object> list3 = collectionServices.union(list1, list2);
		assertEquals(3, list3.size());
		assertEquals("a", list3.get(0));
		assertEquals("b", list3.get(1));
		assertEquals(1, list3.get(2));
	}

	@Test(expected = NullPointerException.class)
	public void testUnionListNull() {
		List<Object> list = new ArrayList<>();

		collectionServices.union(list, null);
	}

	@Test(expected = NullPointerException.class)
	public void testUnionNullList() {
		List<Object> list = new ArrayList<>();

		collectionServices.union(list, null);
	}

	@Test
	public void testUnionListsWithDuplicates() {
		List<Object> list1 = new ArrayList<>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		list1.add("c");
		list1.add("c");

		List<Object> list2 = new ArrayList<>();
		list2.add("c");
		list2.add("b");
		list2.add("a");

		List<Object> result = collectionServices.union(list1, list2);
		assertEquals(8, result.size());
		assertEquals("a", result.get(0));
		assertEquals("b", result.get(1));
		assertEquals("c", result.get(2));
		assertEquals("c", result.get(3));
		assertEquals("c", result.get(4));
		assertEquals("c", result.get(5));
		assertEquals("b", result.get(6));
		assertEquals("a", result.get(7));
	}

	@Test
	public void testUnionSetSet() {
		Set<Object> set1 = new LinkedHashSet<>();
		Set<Object> set2 = new LinkedHashSet<>();

		assertEquals(0, collectionServices.union(set1, set2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		set1.add(obj1);
		set1.add(obj2);

		set2.add(obj3);

		Set<Object> res = collectionServices.union(set1, set2);
		assertEquals(3, res.size());
		Iterator<Object> itr = res.iterator();
		assertEquals(obj1, itr.next());
		assertEquals(obj2, itr.next());
		assertEquals(obj3, itr.next());
	}

	@Test
	public void testUnionSetSetDifferentTypes() {
		Set<String> set1 = new LinkedHashSet<>();
		Set<Integer> set2 = new LinkedHashSet<>();

		assertEquals(0, collectionServices.union(set1, set2).size());

		set1.add("a");
		set1.add("b");

		set2.add(1);

		Set<? extends Object> res = collectionServices.union(set1, set2);
		assertEquals(3, res.size());
		Iterator<? extends Object> itr = res.iterator();
		assertEquals("a", itr.next());
		assertEquals("b", itr.next());
		assertEquals(1, itr.next());
	}

	@Test(expected = NullPointerException.class)
	public void testUnionSetNull() {
		Set<Object> set = new LinkedHashSet<>();

		collectionServices.union(set, null);
	}

	@Test(expected = NullPointerException.class)
	public void testUnionNullSet() {
		Set<Object> set = new LinkedHashSet<>();

		collectionServices.union(set, null);
	}

	@Test
	public void testUnionSetsWithDuplicates() {
		Set<Object> set1 = new LinkedHashSet<>();
		set1.add("a");
		set1.add("b");
		set1.add("c");
		set1.add("d");

		Set<String> set2 = new LinkedHashSet<>();
		set2.add("e");
		set2.add("d");
		set2.add("c");
		set2.add("b");

		Set<Object> result = collectionServices.union(set1, set2);
		assertEquals(5, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals("a", itr.next());
		assertEquals("b", itr.next());
		assertEquals("c", itr.next());
		assertEquals("d", itr.next());
		assertEquals("e", itr.next());
	}

	@Test
	public void testAddListList() {
		List<Object> list1 = new ArrayList<>();
		List<Object> list2 = new ArrayList<>();

		assertEquals(0, collectionServices.add(list1, list2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		list1.add(obj1);
		list1.add(obj2);

		list2.add(obj3);

		List<Object> res = collectionServices.add(list1, list2);
		assertEquals(3, res.size());
		assertEquals(obj1, res.get(0));
		assertEquals(obj2, res.get(1));
		assertEquals(obj3, res.get(2));
	}

	@Test
	public void testAddListSet() {
		List<Object> list = new ArrayList<>();
		Set<Object> set = new LinkedHashSet<>();

		assertEquals(0, collectionServices.add(list, set).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		list.add(obj1);
		list.add(obj2);

		set.add(obj3);

		List<Object> res = collectionServices.add(list, set);
		assertEquals(3, res.size());
		assertEquals(obj1, res.get(0));
		assertEquals(obj2, res.get(1));
		assertEquals(obj3, res.get(2));
	}

	@Test
	public void testAddListListDifferentTypes() {
		List<String> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();

		assertEquals(0, collectionServices.add(list1, list2).size());

		list1.add("a");
		list1.add("b");

		list2.add(1);

		List<? extends Object> res = collectionServices.add(list1, list2);
		assertEquals(3, res.size());
		assertEquals("a", res.get(0));
		assertEquals("b", res.get(1));
		assertEquals(1, res.get(2));
	}

	@Test
	public void testAddListSetDifferentTypes() {
		List<String> list = new ArrayList<>();
		Set<Integer> set = new LinkedHashSet<>();

		assertEquals(0, collectionServices.add(list, set).size());

		list.add("a");
		list.add("b");

		set.add(1);

		List<? extends Object> res = collectionServices.add(list, set);
		assertEquals(3, res.size());
		assertEquals("a", res.get(0));
		assertEquals("b", res.get(1));
		assertEquals(1, res.get(2));
	}

	@Test(expected = NullPointerException.class)
	public void testAddListNull() {
		List<Object> list = new ArrayList<>();

		collectionServices.add(list, null);
	}

	@Test(expected = NullPointerException.class)
	public void testAddNullList() {
		List<Object> list = new ArrayList<>();

		collectionServices.add((List<?>)null, list);
	}

	@Test
	public void testAddListWithDuplicates() {
		List<Object> list1 = new ArrayList<>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		list1.add("c");
		list1.add("c");

		List<Object> list2 = new ArrayList<>();
		list2.add("c");
		list2.add("b");
		list2.add("a");

		List<Object> result = collectionServices.add(list1, list2);
		assertEquals(8, result.size());
		assertEquals("a", result.get(0));
		assertEquals("b", result.get(1));
		assertEquals("c", result.get(2));
		assertEquals("c", result.get(3));
		assertEquals("c", result.get(4));
		assertEquals("c", result.get(5));
		assertEquals("b", result.get(6));
		assertEquals("a", result.get(7));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add("c");
		set1.add("b");
		set1.add("a");

		List<Object> result2 = collectionServices.add(list1, set1);
		assertEquals(8, result2.size());
		assertEquals("a", result2.get(0));
		assertEquals("b", result2.get(1));
		assertEquals("c", result2.get(2));
		assertEquals("c", result2.get(3));
		assertEquals("c", result2.get(4));
		assertEquals("c", result2.get(5));
		assertEquals("b", result2.get(6));
		assertEquals("a", result2.get(7));
	}

	@Test
	public void testAddSetList() {
		Set<Object> set = new LinkedHashSet<>();
		List<Object> list = new ArrayList<>();

		assertEquals(0, collectionServices.add(set, list).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		set.add(obj1);
		set.add(obj2);

		list.add(obj3);

		Set<Object> res = collectionServices.add(set, list);
		assertEquals(3, res.size());
		Iterator<Object> itr = res.iterator();
		assertEquals(obj1, itr.next());
		assertEquals(obj2, itr.next());
		assertEquals(obj3, itr.next());
	}

	@Test
	public void testAddSetSet() {
		Set<Object> set1 = new LinkedHashSet<>();
		Set<Object> set2 = new LinkedHashSet<>();

		assertEquals(0, collectionServices.add(set1, set2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		set1.add(obj1);
		set1.add(obj2);

		set2.add(obj3);

		Set<Object> res = collectionServices.add(set1, set2);
		assertEquals(3, res.size());
		Iterator<Object> itr = res.iterator();
		assertEquals(obj1, itr.next());
		assertEquals(obj2, itr.next());
		assertEquals(obj3, itr.next());
	}

	@Test
	public void testAddSetListDifferentTypes() {
		Set<String> set = new LinkedHashSet<>();
		List<Integer> list = new ArrayList<>();

		assertEquals(0, collectionServices.add(set, list).size());

		set.add("a");
		set.add("b");

		list.add(1);

		Set<? extends Object> res = collectionServices.add(set, list);
		assertEquals(3, res.size());
		Iterator<? extends Object> itr = res.iterator();
		assertEquals("a", itr.next());
		assertEquals("b", itr.next());
		assertEquals(1, itr.next());
	}

	@Test
	public void testAddSetSetDifferentTypes() {
		Set<String> set1 = new LinkedHashSet<>();
		Set<Integer> set2 = new LinkedHashSet<>();

		assertEquals(0, collectionServices.add(set1, set2).size());

		set1.add("a");
		set1.add("b");

		set2.add(1);

		Set<? extends Object> res = collectionServices.add(set1, set2);
		assertEquals(3, res.size());
		Iterator<? extends Object> itr = res.iterator();
		assertEquals("a", itr.next());
		assertEquals("b", itr.next());
		assertEquals(1, itr.next());
	}

	@Test(expected = NullPointerException.class)
	public void testAddSetNull() {
		Set<Object> set = new LinkedHashSet<>();

		collectionServices.add(set, null);
	}

	@Test(expected = NullPointerException.class)
	public void testAddNullSet() {
		Set<Object> set = new LinkedHashSet<>();

		collectionServices.add((Set<?>)null, set);
	}

	@Test
	public void testAddSetWithDuplicates() {
		Set<Object> set1 = new LinkedHashSet<>();
		set1.add("a");
		set1.add("b");
		set1.add("c");
		set1.add("d");

		Set<String> set2 = new LinkedHashSet<>();
		set2.add("e");
		set2.add("d");
		set2.add("c");
		set2.add("b");

		Set<Object> result = collectionServices.concat(set1, set2);
		assertEquals(5, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals("a", itr.next());
		assertEquals("b", itr.next());
		assertEquals("c", itr.next());
		assertEquals("d", itr.next());
		assertEquals("e", itr.next());

		List<Object> list1 = new ArrayList<>();
		list1.add("e");
		list1.add("d");
		list1.add("c");
		list1.add("b");

		Set<Object> result2 = collectionServices.concat(set1, list1);
		assertEquals(5, result2.size());
		Iterator<Object> itr2 = result2.iterator();
		assertEquals("a", itr2.next());
		assertEquals("b", itr2.next());
		assertEquals("c", itr2.next());
		assertEquals("d", itr2.next());
		assertEquals("e", itr2.next());
	}

	@Test
	public void testSubListList() {
		List<Object> list1 = new ArrayList<>();
		List<Object> list2 = new ArrayList<>();

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
	}

	@Test
	public void testSubListSet() {
		List<Object> list = new ArrayList<>();
		Set<Object> set = new LinkedHashSet<>();

		assertEquals(0, collectionServices.sub(list, set).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		list.add(obj1);
		list.add(obj2);

		List<Object> list3 = collectionServices.sub(list, set);
		assertEquals(2, list3.size());
		assertEquals(obj1, list3.get(0));
		assertEquals(obj2, list3.get(1));

		set.add(obj2);
		set.add(obj3);

		list3 = collectionServices.sub(list, set);
		assertEquals(1, list3.size());
		assertEquals(obj1, list3.get(0));
	}

	@Test
	public void testSubListListDifferentTypes() {
		List<Number> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();

		assertEquals(0, collectionServices.sub(list1, list2).size());

		list1.add(1);
		list1.add(2d);

		List<Number> list3 = collectionServices.sub(list1, list2);
		assertEquals(2, list3.size());
		assertEquals(1, list3.get(0));
		assertEquals(2d, list3.get(1));

		list2.add(1);
		list2.add(2);

		list3 = collectionServices.sub(list1, list2);
		assertEquals(1, list3.size());
		assertEquals(2d, list3.get(0));
	}

	@Test
	public void testSubListSetDifferentTypes() {
		List<Number> list = new ArrayList<>();
		Set<Integer> set = new LinkedHashSet<>();

		assertEquals(0, collectionServices.sub(list, set).size());

		list.add(1);
		list.add(2d);

		List<Number> list3 = collectionServices.sub(list, set);
		assertEquals(2, list3.size());
		assertEquals(1, list3.get(0));
		assertEquals(2d, list3.get(1));

		set.add(1);
		set.add(2);

		list3 = collectionServices.sub(list, set);
		assertEquals(1, list3.size());
		assertEquals(2d, list3.get(0));
	}

	@Test(expected = NullPointerException.class)
	public void testSubListNull() {
		List<Object> list = new ArrayList<>();
		list.add(new Object());

		collectionServices.sub(list, null);
	}

	@Test(expected = NullPointerException.class)
	public void testSubNullList() {
		List<Object> list = new ArrayList<>();
		list.add(new Object());

		collectionServices.sub((List<?>)null, list);
	}

	@Test(expected = NullPointerException.class)
	public void testSubListNullEmptyList() {
		List<Object> list = new ArrayList<>();

		collectionServices.sub(list, null);
	}

	@Test(expected = NullPointerException.class)
	public void testSubNullListEmptyList() {
		List<Object> list = new ArrayList<>();

		collectionServices.sub((List<?>)null, list);
	}

	@Test
	public void testSubListWithDuplicates() {
		List<Object> list1 = new ArrayList<>();
		list1.add("a");
		list1.add("b");
		list1.add("c");
		list1.add("c");
		list1.add("c");

		List<Object> list2 = new ArrayList<>();
		list2.add("c");

		List<Object> result = collectionServices.sub(list1, list2);
		assertEquals(2, result.size());
		assertEquals("a", result.get(0));
		assertEquals("b", result.get(1));
	}

	@Test
	public void testSubSetList() {
		Set<Object> set = new LinkedHashSet<>();
		List<Object> list = new ArrayList<>();

		assertEquals(0, collectionServices.sub(set, list).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		set.add(obj1);
		set.add(obj2);

		Set<Object> res = collectionServices.sub(set, list);
		assertEquals(2, res.size());
		Iterator<Object> itr = res.iterator();
		assertEquals(obj1, itr.next());
		assertEquals(obj2, itr.next());

		list.add(obj2);
		list.add(obj3);

		res = collectionServices.sub(set, list);
		assertEquals(1, res.size());
		itr = res.iterator();
		assertEquals(obj1, itr.next());
	}

	@Test
	public void testSubSetSet() {
		Set<Object> set1 = new LinkedHashSet<>();
		Set<Object> set2 = new LinkedHashSet<>();

		assertEquals(0, collectionServices.sub(set1, set2).size());

		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		set1.add(obj1);
		set1.add(obj2);

		Set<Object> res = collectionServices.sub(set1, set2);
		assertEquals(2, res.size());
		Iterator<Object> itr = res.iterator();
		assertEquals(obj1, itr.next());
		assertEquals(obj2, itr.next());

		set2.add(obj2);
		set2.add(obj3);

		res = collectionServices.sub(set1, set2);
		assertEquals(1, res.size());
		itr = res.iterator();
		assertEquals(obj1, itr.next());
	}

	@Test
	public void testSubSetListDifferentTypes() {
		Set<Number> set = new LinkedHashSet<>();
		List<Integer> list = new ArrayList<>();

		assertEquals(0, collectionServices.sub(set, list).size());

		set.add(1);
		set.add(2d);

		Set<Number> res = collectionServices.sub(set, list);
		assertEquals(2, res.size());
		Iterator<Number> itr = res.iterator();
		assertEquals(1, itr.next());
		assertEquals(2d, itr.next());

		list.add(1);
		list.add(2);

		res = collectionServices.sub(set, list);
		assertEquals(1, res.size());
		itr = res.iterator();
		assertEquals(2d, itr.next());
	}

	@Test
	public void testSubSetSetDifferentTypes() {
		Set<Number> set1 = new LinkedHashSet<>();
		Set<Integer> set2 = new LinkedHashSet<>();

		assertEquals(0, collectionServices.sub(set1, set2).size());

		set1.add(1);
		set1.add(2d);

		Set<Number> res = collectionServices.sub(set1, set2);
		assertEquals(2, res.size());
		Iterator<Number> itr = res.iterator();
		assertEquals(1, itr.next());
		assertEquals(2d, itr.next());

		set2.add(1);
		set2.add(2);

		res = collectionServices.sub(set1, set2);
		assertEquals(1, res.size());
		itr = res.iterator();
		assertEquals(2d, itr.next());
	}

	@Test(expected = NullPointerException.class)
	public void testSubSetNull() {
		Set<Object> set = new LinkedHashSet<>();

		collectionServices.sub(set, null);
	}

	@Test(expected = NullPointerException.class)
	public void testSubNullSet() {
		Set<Object> set = new LinkedHashSet<>();

		collectionServices.sub((Set<?>)null, set);
	}

	@Test
	public void testIncludingList() {
		List<Object> list = new ArrayList<>();
		Object elt = new Object();
		list.add(elt);

		Object elt2 = new Object();
		List<Object> result = collectionServices.including(list, elt2);
		assertEquals(2, result.size());
		assertEquals(result.get(0), elt);
		assertEquals(result.get(1), elt2);

		// make sure we didn't modify the original list
		result = collectionServices.including(list, elt);
		assertEquals(2, result.size());
		assertEquals(result.get(0), elt);
		assertEquals(result.get(1), elt);
	}

	@Test
	public void testIncludingListNull() {
		List<Object> list = new ArrayList<>();
		Object elt = new Object();
		list.add(elt);

		List<Object> result = collectionServices.including(list, null);
		assertEquals(2, result.size());
		assertEquals(result.get(0), elt);
		assertEquals(result.get(1), null);
	}

	@Test
	public void testIncludingSet() {
		Set<Object> set = new HashSet<Object>();
		Object elt = new Object();
		set.add(elt);

		Object elt2 = new Object();
		Set<Object> result = collectionServices.including(set, elt2);
		assertEquals(2, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals(elt, itr.next());
		assertEquals(elt2, itr.next());

		// make sure we didn't modify the original set
		result = collectionServices.including(set, elt);
		assertEquals(set, result);
		assertEquals(1, result.size());
		assertTrue(result.contains(elt));
	}

	@Test
	public void testIncludingSetNull() {
		Set<Object> set = new HashSet<Object>();
		Object elt = new Object();
		set.add(elt);

		Set<Object> result = collectionServices.including(set, null);
		assertEquals(2, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals(elt, itr.next());
		assertEquals(null, itr.next());
	}

	@Test
	public void testExcludingList() {
		List<Object> list = new ArrayList<>();
		Object elt = new Object();
		Object elt2 = new Object();
		list.add(elt);
		list.add(elt2);

		List<Object> result = collectionServices.excluding(list, elt2);
		assertEquals(1, result.size());
		assertFalse(result.contains(elt2));
		assertEquals(elt, result.get(0));

		list = new ArrayList<>();
		list.add(elt);
		result = collectionServices.excluding(list, elt2);
		assertEquals(list, result);
		assertEquals(1, result.size());
		assertEquals(elt, result.get(0));
	}

	@Test
	public void testExcludingListNull() {
		List<Object> list = new ArrayList<>();
		Object elt = new Object();
		list.add(elt);
		list.add(null);

		List<Object> result = collectionServices.excluding(list, null);
		assertEquals(1, result.size());
		assertFalse(result.contains(null));
		assertEquals(elt, result.get(0));

		list = new ArrayList<>();
		list.add(elt);
		result = collectionServices.excluding(list, null);
		assertEquals(list, result);
		assertEquals(1, result.size());
		assertEquals(elt, result.get(0));
	}

	@Test
	public void testExcludingWithDuplicates() {
		List<Object> list = new ArrayList<Object>();
		String a = "a";
		String b = "b";
		String c = "c";

		list.add(a);
		list.add(b);
		list.add(c);
		list.add(b);

		List<Object> result = collectionServices.excluding(list, b);
		assertEquals(2, result.size());
		assertFalse(result.contains(b));
		assertEquals(a, result.get(0));
		assertEquals(c, result.get(1));
	}

	@Test
	public void testExcludingSet() {
		Set<Object> set = new HashSet<Object>();
		Object elt = new Object();
		Object elt2 = new Object();
		set.add(elt);
		set.add(elt2);

		Set<Object> result = collectionServices.excluding(set, elt2);
		assertEquals(1, result.size());
		assertFalse(result.contains(elt2));
		assertTrue(result.contains(elt));

		set = new HashSet<Object>();
		set.add(elt);
		result = collectionServices.excluding(set, elt2);
		assertEquals(set, result);
		assertEquals(1, result.size());
		assertTrue(result.contains(elt));

		set = new HashSet<Object>();
		assertEquals(0, collectionServices.excluding(set, null).size());
	}

	@Test
	public void testExcludingSetNull() {
		Set<Object> set = new HashSet<Object>();
		Object elt = new Object();
		set.add(elt);
		set.add(null);

		Set<Object> result = collectionServices.excluding(set, null);
		assertEquals(1, result.size());
		assertFalse(result.contains(null));
		assertTrue(result.contains(elt));

		set = new HashSet<Object>();
		set.add(elt);
		result = collectionServices.excluding(set, null);
		assertEquals(set, result);
		assertEquals(1, result.size());
		assertTrue(result.contains(elt));
	}

	@Test
	public void testReverseList() {
		List<Object> list = new ArrayList<>();
		assertEquals(0, collectionServices.reverse(list).size());
		Object elt = new Object();
		Object elt2 = new Object();
		list.add(elt);
		list.add(elt);
		list.add(elt2);
		list.add(elt);
		assertEquals(4, collectionServices.reverse(list).size());

		List<Object> result = collectionServices.reverse(list);
		assertSame(elt, result.get(0));
		assertSame(elt2, result.get(1));
		assertSame(elt, result.get(2));
		assertSame(elt, result.get(3));

		Object elt3 = new Object();
		list.add(elt3);
		result = collectionServices.reverse(list);
		assertSame(elt3, result.get(0));
		assertSame(elt, result.get(1));
		assertSame(elt2, result.get(2));
		assertSame(elt, result.get(3));
		assertSame(elt, result.get(4));
	}

	@Test
	public void testReverseListCopy() {
		// make sure "reverse" returns a copy of the list
		List<Object> list = new ArrayList<>();
		assertEquals(0, collectionServices.reverse(list).size());
		Object elt = new Object();
		Object elt2 = new Object();
		list.add(elt);
		list.add(elt);
		list.add(elt2);
		list.add(elt);
		List<Object> result = collectionServices.reverse(list);

		list.remove(elt2);
		assertTrue(result.contains(elt2));
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
		assertSame(elt2, iterator.next());
		assertSame(elt, iterator.next());
	}

	@Test
	public void testIsEmptyList() {
		assertTrue(collectionServices.isEmpty(new ArrayList<>()));
		assertFalse(collectionServices.isEmpty(Collections.singleton(new Object())));
	}

	@Test
	public void testIsEmptySet() {
		assertTrue(collectionServices.isEmpty(new LinkedHashSet<>()));
		assertFalse(collectionServices.isEmpty(Collections.singleton(new Object())));
	}

	@Test
	public void testNotEmptyList() {
		List<Object> list = new ArrayList<>();
		assertFalse(collectionServices.notEmpty(list));
		list.add(new Object());
		assertTrue(collectionServices.notEmpty(list));
	}

	@Test
	public void testNotEmptySet() {
		Set<Object> set = new LinkedHashSet<>();
		assertFalse(collectionServices.notEmpty(set));
		set.add(new Object());
		assertTrue(collectionServices.notEmpty(set));
	}

	@Test
	public void testFirstList() {
		List<Object> list = new ArrayList<>();
		Object elt = new Object();
		list.add(elt);
		list.add(new Object());
		assertEquals(elt, collectionServices.first(list));
	}

	@Test
	public void testFirstEmptyList() {
		List<Object> list = new ArrayList<>();
		assertEquals(null, collectionServices.first(list));
	}

	@Test
	public void testFirstSet() {
		Set<Object> set = new LinkedHashSet<>();
		Object elt = new Object();
		set.add(elt);
		set.add(new Object());
		assertEquals(elt, collectionServices.first(set));
	}

	@Test
	public void testFirstEmptySet() {
		Set<Object> set = new LinkedHashSet<>();
		assertEquals(null, collectionServices.first(set));
	}

	@Test(expected = NullPointerException.class)
	public void testFirstNullCollection() {
		collectionServices.first(null);
	}

	@Test
	public void testAtList() {
		List<Object> list = new ArrayList<>();
		Object elt = new Object();
		Object elt2 = new Object();
		Object elt3 = new Object();
		Object elt4 = new Object();
		list.add(elt);
		list.add(elt2);
		list.add(elt3);
		list.add(elt3);
		list.add(elt4);
		assertEquals(elt, collectionServices.at(list, 1));
		assertEquals(elt2, collectionServices.at(list, 2));
		assertEquals(elt4, collectionServices.at(list, 5));

		list.add(0, elt4);
		assertEquals(elt4, collectionServices.at(list, 1));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAtListOutOfBounds() {
		List<Object> list = new ArrayList<>();
		list.add(new Object());
		collectionServices.at(list, 2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAtListZero() {
		List<Object> list = new ArrayList<>();
		list.add(new Object());
		collectionServices.at(list, 0);
	}

	@Test(expected = NullPointerException.class)
	public void testAtListNull() {
		collectionServices.at((List<?>)null, 1);
	}

	@Test
	public void testAtSet() {
		Set<Object> set = new LinkedHashSet<>();
		Object elt = 0;
		Object elt1 = 1;
		Object elt2 = 2;
		Object elt3 = 3;
		Object elt4 = 4;
		set.add(elt);
		set.add(elt1);
		set.add(elt2);
		set.add(elt3);
		set.add(elt3);
		set.add(elt4);
		assertEquals(elt, collectionServices.at(set, 1));
		assertEquals(elt2, collectionServices.at(set, 3));
		assertEquals(elt4, collectionServices.at(set, 5));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAtSetOutOfBounds() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(new Object());
		collectionServices.at(set, 2);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testAtSetZero() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(new Object());
		collectionServices.at(set, 0);
	}

	@Test(expected = NullPointerException.class)
	public void testAtSetNull() {
		collectionServices.at((Set<?>)null, 1);
	}

	@Test
	public void testSizeList() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		assertEquals(Integer.valueOf(1), collectionServices.size(list));

		list.add(2);
		list.add(3);
		assertEquals(Integer.valueOf(3), collectionServices.size(list));

		list.remove(1);
		assertEquals(Integer.valueOf(2), collectionServices.size(list));
	}

	@Test
	public void testSizeSet() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(1);
		assertEquals(Integer.valueOf(1), collectionServices.size(set));

		set.add(2);
		set.add(3);
		assertEquals(Integer.valueOf(3), collectionServices.size(set));

		set.remove(1);
		assertEquals(Integer.valueOf(2), collectionServices.size(set));
	}

	@Test(expected = NullPointerException.class)
	public void testAsSetNull() {
		collectionServices.asSet(null);
	}

	@Test
	public void testAsSetList() {
		List<Object> list = new ArrayList<>();
		Object elt = new Object();
		Object elt2 = new Object();
		list.add(elt);
		list.add(elt);
		list.add(elt2);

		Set<Object> asSet = collectionServices.asSet(list);
		assertNotNull(asSet);
		Iterator<Object> itr = asSet.iterator();
		assertEquals(elt, itr.next());
		assertEquals(elt2, itr.next());
		assertFalse(itr.hasNext());

		Object elt3 = new Object();
		list.add(elt3);

		// Make sure the returned set was a copy
		assertFalse(asSet.contains(elt3));
	}

	@Test
	public void testAsSetSet() {
		Set<Object> set = new LinkedHashSet<>();
		Object elt = new Object();
		Object elt2 = new Object();
		set.add(elt);
		set.add(elt2);

		Set<Object> asSet = collectionServices.asSet(set);
		assertNotNull(asSet);
		Iterator<Object> itr = asSet.iterator();
		assertEquals(elt, itr.next());
		assertEquals(elt2, itr.next());
		assertFalse(itr.hasNext());

		Object elt3 = new Object();
		set.add(elt3);

		// The given collection was a set, so "asSet" shouldn't have copied it
		assertTrue(asSet.contains(elt3));
	}

	@Test(expected = NullPointerException.class)
	public void testAsOrderedSetNull() {
		collectionServices.asOrderedSet(null);
	}

	@Test
	public void testAsOrderedSetList() {
		List<Object> list = new ArrayList<>();
		Object elt = new Object();
		Object elt2 = new Object();
		list.add(elt);
		list.add(elt);
		list.add(elt2);

		Set<Object> asSet = collectionServices.asOrderedSet(list);
		assertNotNull(asSet);
		Iterator<Object> itr = asSet.iterator();
		assertEquals(elt, itr.next());
		assertEquals(elt2, itr.next());
		assertFalse(itr.hasNext());

		Object elt3 = new Object();
		list.add(elt3);

		// Make sure the returned set was a copy
		assertFalse(asSet.contains(elt3));
	}

	@Test
	public void testAsOrderedSetSet() {
		Set<Object> set = new LinkedHashSet<>();
		Object elt = new Object();
		Object elt2 = new Object();
		set.add(elt);
		set.add(elt2);

		Set<Object> asSet = collectionServices.asOrderedSet(set);
		assertNotNull(asSet);
		Iterator<Object> itr = asSet.iterator();
		assertEquals(elt, itr.next());
		assertEquals(elt2, itr.next());
		assertFalse(itr.hasNext());

		Object elt3 = new Object();
		set.add(elt3);

		// The given collection was a set, so "asSet" shouldn't have copied it
		assertTrue(asSet.contains(elt3));
	}

	@Test(expected = NullPointerException.class)
	public void testAsSequenceNull() {
		collectionServices.asSequence(null);
	}

	@Test
	public void testAsSequenceList() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);

		List<Integer> asSequence = collectionServices.asSequence(list);
		assertNotNull(asSequence);

		assertEquals(Integer.valueOf(1), asSequence.get(0));
		assertEquals(Integer.valueOf(2), asSequence.get(1));
		assertEquals(Integer.valueOf(3), asSequence.get(2));

		// The collection was already a list, so it should not have been copied
		list.add(4);
		assertTrue(asSequence.contains(4));
	}

	@Test
	public void testAsSequenceSet() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(1);
		set.add(2);
		set.add(3);

		List<Integer> asSequence = collectionServices.asSequence(set);
		assertNotNull(asSequence);

		assertEquals(Integer.valueOf(1), asSequence.get(0));
		assertEquals(Integer.valueOf(2), asSequence.get(1));
		assertEquals(Integer.valueOf(3), asSequence.get(2));

		// Make sure the returned list was a copy
		set.add(4);
		assertFalse(asSequence.contains(4));
	}

	/*
	 * A lambda value that returns the length of the first argument if it's a string.
	 */
	private LambdaValue createStringLengthLambda() {
		return new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				if (args[0] instanceof String) {
					return ((String)args[0]).length();
				}
				return 0;
			}
		};
	}

	@Test
	public void testSortedByNullSet() {
		Set<Object> sortedBySet = collectionServices.sortedBy((Set<Object>)null, createStringLengthLambda());
		assertEquals(null, sortedBySet);
	}

	@Test
	public void testSortedByNullList() {
		List<Object> sortedByList = collectionServices.sortedBy((List<Object>)null,
				createStringLengthLambda());
		assertEquals(null, sortedByList);
	}

	@Test
	public void testSortedBySetNullLambda() {
		Set<Object> set = new LinkedHashSet<Object>();
		final TestComparable comp1 = new TestComparable(1);
		final TestComparable comp2 = new TestComparable(2);
		final TestComparable comp3 = new TestComparable(3);
		final TestComparable comp4 = new TestComparable(4);
		final TestComparable comp5 = new TestComparable(5);
		set.add(comp2);
		set.add(comp4);
		set.add(comp1);
		set.add(comp3);
		set.add(comp5);

		Set<Object> sortedBySet = collectionServices.sortedBy(set, null);
		assertSame(set, sortedBySet);
		assertEquals(5, sortedBySet.size());
	}

	@Test
	public void testSortedByListNullLambda() {
		List<Object> list = new ArrayList<Object>();
		final TestComparable comp1 = new TestComparable(1);
		final TestComparable comp2 = new TestComparable(2);
		final TestComparable comp3 = new TestComparable(3);
		final TestComparable comp4 = new TestComparable(4);
		final TestComparable comp5 = new TestComparable(5);
		list.add(comp2);
		list.add(comp4);
		list.add(comp1);
		list.add(comp3);
		list.add(comp5);

		List<Object> sortedByList = collectionServices.sortedBy(list, null);
		assertSame(list, sortedByList);
		assertEquals(5, sortedByList.size());
	}

	/*
	 * A lambda that returns the first argument.
	 */
	private LambdaValue createSelfLambda() {
		return new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				if (args.length >= 1) {
					return args[0];
				}
				return null;
			}
		};
	}

	/*
	 * A lambda that throw an exception.
	 */
	private LambdaValue createExceptionLambda(Diagnostic diagnostic) {
		return new LambdaValue(null, null, null, diagnostic) {
			@Override
			public Object eval(Object[] args) {
				throw new RuntimeException("Test runtime exception lambda.");
			}
		};
	}

	@Test
	public void testSortedBySetWithNull() {
		Set<Object> set = new LinkedHashSet<Object>();
		final TestComparable comp1 = new TestComparable(1);
		final TestComparable comp2 = new TestComparable(2);
		final TestComparable comp3 = new TestComparable(3);
		final TestComparable comp4 = new TestComparable(4);
		final TestComparable comp5 = new TestComparable(5);
		set.add(comp2);
		set.add(comp4);
		set.add(comp1);
		set.add(null);
		set.add(comp3);
		set.add(comp5);
		Set<Object> sortedBySet = collectionServices.sortedBy(set, createSelfLambda());
		assertEquals(6, sortedBySet.size());
		Iterator<Object> itr = sortedBySet.iterator();
		assertEquals(null, itr.next());
		assertEquals(comp1, itr.next());
		assertEquals(comp2, itr.next());
		assertEquals(comp3, itr.next());
		assertEquals(comp4, itr.next());
		assertEquals(comp5, itr.next());
	}

	@Test
	public void testSortedByListWithNull() {
		List<Object> list = new ArrayList<Object>();
		final TestComparable comp1 = new TestComparable(1);
		final TestComparable comp2 = new TestComparable(2);
		final TestComparable comp3 = new TestComparable(3);
		final TestComparable comp4 = new TestComparable(4);
		final TestComparable comp5 = new TestComparable(5);
		list.add(comp2);
		list.add(comp4);
		list.add(comp1);
		list.add(null);
		list.add(comp3);
		list.add(comp5);
		List<Object> sortedByList = collectionServices.sortedBy(list, createSelfLambda());
		assertEquals(6, sortedByList.size());
		assertEquals(null, sortedByList.get(0));
		assertEquals(comp1, sortedByList.get(1));
		assertEquals(comp2, sortedByList.get(2));
		assertEquals(comp3, sortedByList.get(3));
		assertEquals(comp4, sortedByList.get(4));
		assertEquals(comp5, sortedByList.get(5));
	}

	@Test
	public void testSortedBySet() {
		Set<String> set = new LinkedHashSet<String>();
		set.add("aa");
		set.add("bbb");
		set.add("c");
		Set<String> sortedBySet = collectionServices.sortedBy(set, createStringLengthLambda());
		assertEquals(3, sortedBySet.size());
		Iterator<String> itr = sortedBySet.iterator();
		assertEquals("c", itr.next());
		assertEquals("aa", itr.next());
		assertEquals("bbb", itr.next());
	}

	@Test
	public void testSortedByList() {
		List<String> list = new ArrayList<String>();
		list.add("aa");
		list.add("bbb");
		list.add("c");
		List<String> sortedByList = collectionServices.sortedBy(list, createStringLengthLambda());
		assertEquals(3, sortedByList.size());
		assertEquals("c", sortedByList.get(0));
		assertEquals("aa", sortedByList.get(1));
		assertEquals("bbb", sortedByList.get(2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSortedBySetDifferentTypes() {
		Set<Number> set = new LinkedHashSet<Number>();
		set.add(1);
		set.add(1.5d);
		collectionServices.sortedBy(set, createSelfLambda());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSortedByListDifferentTypes() {
		List<Number> list = new ArrayList<Number>();
		list.add(1);
		list.add(1.5d);
		collectionServices.sortedBy(list, createSelfLambda());
	}

	@Test
	public void testSortedByListExceptionLambda() {
		List<Number> list = new ArrayList<Number>();
		list.add(1);
		list.add(1.5d);

		Diagnostic diagnostic = new BasicDiagnostic();
		assertEquals(list, collectionServices.sortedBy(list, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(2, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
	}

	@Test
	public void testSortedBySetExceptionLambda() {
		Set<Number> set = new LinkedHashSet<Number>();
		set.add(1);
		set.add(1.5d);

		Diagnostic diagnostic = new BasicDiagnostic();
		assertEquals(set, collectionServices.sortedBy(set, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(2, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
	}

	/*
	 * A lambda that returns the name of the first argument if it's an ENamedElement
	 */
	private LambdaValue createEObjectNameLambda() {
		return new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				if (args[0] instanceof ENamedElement) {
					return ((ENamedElement)args[0]).getName();
				}
				return 0;
			}
		};
	}

	@Test
	public void testSortedByEObjectName() {
		List<Object> list = new ArrayList<Object>();
		list.add(EcorePackage.eINSTANCE.getEStructuralFeature());
		list.add(EcorePackage.eINSTANCE.getEAttribute());
		list.add(EcorePackage.eINSTANCE.getEClassifier());
		list.add(EcorePackage.eINSTANCE.getEAnnotation());
		list.add(EcorePackage.eINSTANCE.getENamedElement());
		List<Object> sortedByList = collectionServices.sortedBy(list, createEObjectNameLambda());
		assertEquals(5, sortedByList.size());
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), sortedByList.get(0));
		assertEquals(EcorePackage.eINSTANCE.getEAttribute(), sortedByList.get(1));
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), sortedByList.get(2));
		assertEquals(EcorePackage.eINSTANCE.getENamedElement(), sortedByList.get(3));
		assertEquals(EcorePackage.eINSTANCE.getEStructuralFeature(), sortedByList.get(4));
	}

	/*
	 * A lambda that'll check if the first argument is an instance of the given class
	 */
	private LambdaValue createInstanceOfLambda(final Class<?> clazz) {
		return new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				return clazz.isInstance(args[0]);
			}
		};
	}

	private final class SortedByCounter extends LambdaValue {
		private int counter = 0;

		/**
		 * @param literal
		 * @param variables
		 * @param envEvaluator
		 */
		private SortedByCounter(Lambda literal, Map<String, Object> variables, AstEvaluator envEvaluator) {
			super(literal, variables, envEvaluator, null);
		}

		@Override
		public Object eval(Object[] args) {
			counter++;
			return args[0];
		}
	}

	@Test
	public void sortByPerf() {
		final int size = 1000000;
		final List<Object> list = new ArrayList<Object>(size);

		final SortedByCounter nameLambda = new SortedByCounter(null, null, null);

		for (int i = 0; i < size; i++) {
			list.add(size - i - 1);
		}

		List<Object> sortedByList = collectionServices.sortedBy(list, nameLambda);
		assertEquals(size, nameLambda.counter);

		for (int i = 0; i < size; i++) {
			assertEquals(i, sortedByList.get(i));
		}
	}

	@Test
	public void testSelectList() throws URISyntaxException, IOException {
		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		List<EObject> list = new ArrayList<>();
		EObject queryWithExpression = iterator.next();
		list.add(queryWithExpression);
		list.add(iterator.next());
		list.add(iterator.next());

		List<EObject> filtered = collectionServices.select(list, null);
		assertTrue(filtered.isEmpty());

		filtered = collectionServices.select(list, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
		assertEquals(1, filtered.size());
		assertEquals(queryWithExpression, filtered.get(0));
	}

	@Test(expected = NullPointerException.class)
	public void testSelectNullList() {
		List<Object> nullList = null;
		collectionServices.select(nullList, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
	}

	@Test
	public void testSelectListNullLambda() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		List<String> filtered = collectionServices.select(list, null);
		assertTrue(filtered.isEmpty());
	}

	@Test
	public void testSelectListNotBooleanLambda() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		List<String> result = collectionServices.select(list, createSelfLambda());
		assertTrue(result.isEmpty());
	}

	@Test
	public void testSelectListExceptionLambda() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");

		Diagnostic diagnostic = new BasicDiagnostic();
		assertEquals(Collections.EMPTY_LIST, collectionServices.reject(list, createExceptionLambda(
				diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(3, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(2).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(2).getMessage());
	}

	@Test
	public void testSelectSet() throws URISyntaxException, IOException {
		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		Set<EObject> set = new LinkedHashSet<>();
		EObject queryWithExpression = iterator.next();
		set.add(queryWithExpression);
		set.add(iterator.next());
		set.add(iterator.next());

		Set<EObject> filtered = collectionServices.select(set, null);
		assertTrue(filtered.isEmpty());

		filtered = collectionServices.select(set, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
		assertEquals(1, filtered.size());
		assertEquals(queryWithExpression, filtered.iterator().next());
	}

	@Test(expected = NullPointerException.class)
	public void testSelectNullSet() {
		Set<Object> nullSet = null;
		collectionServices.select(nullSet, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
	}

	@Test
	public void testSelectSetNullLambda() {
		Set<String> set = new LinkedHashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");
		Set<String> filtered = collectionServices.select(set, null);
		assertTrue(filtered.isEmpty());
	}

	@Test
	public void testSelectSetNotBooleanLambda() {
		Set<String> set = new LinkedHashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");
		Set<String> result = collectionServices.select(set, createSelfLambda());
		assertTrue(result.isEmpty());
	}

	@Test
	public void testRejectList() throws URISyntaxException, IOException {
		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		List<EObject> list = new ArrayList<>();
		EObject queryWithExpression = iterator.next();
		EObject eObj2 = iterator.next();
		EObject eObj3 = iterator.next();
		list.add(queryWithExpression);
		list.add(eObj2);
		list.add(eObj3);

		List<EObject> filtered = collectionServices.reject(list, null);
		assertTrue(filtered.isEmpty());

		filtered = collectionServices.reject(list, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
		assertEquals(2, filtered.size());
		assertEquals(eObj2, filtered.get(0));
		assertEquals(eObj3, filtered.get(1));
	}

	@Test(expected = NullPointerException.class)
	public void testRejectNullList() {
		List<Object> nullList = null;
		collectionServices.reject(nullList, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
	}

	@Test
	public void testRejectListNullLambda() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		List<String> filtered = collectionServices.reject(list, null);
		assertTrue(filtered.isEmpty());
	}

	@Test
	public void testRejectListNotBooleanLambda() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		assertEquals(Collections.EMPTY_LIST, collectionServices.reject(list, createSelfLambda()));
	}

	@Test
	public void testRejectListExceptionLambda() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");

		Diagnostic diagnostic = new BasicDiagnostic();
		assertEquals(Collections.EMPTY_LIST, collectionServices.reject(list, createExceptionLambda(
				diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(3, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(2).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(2).getMessage());
	}

	@Test
	public void testRejectSet() throws URISyntaxException, IOException {
		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		Set<EObject> set = new LinkedHashSet<>();
		EObject queryWithExpression = iterator.next();
		EObject eObj2 = iterator.next();
		EObject eObj3 = iterator.next();
		set.add(queryWithExpression);
		set.add(eObj2);
		set.add(eObj3);

		Set<EObject> filtered = collectionServices.reject(set, null);
		assertTrue(filtered.isEmpty());

		filtered = collectionServices.reject(set, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
		assertEquals(2, filtered.size());
		Iterator<EObject> itr = filtered.iterator();
		assertEquals(eObj2, itr.next());
		assertEquals(eObj3, itr.next());
	}

	@Test(expected = NullPointerException.class)
	public void testRejectNullSet() {
		Set<Object> nullSet = null;
		collectionServices.reject(nullSet, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
	}

	@Test
	public void testRejectSetNullLambda() {
		Set<String> set = new LinkedHashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");
		Set<String> filtered = collectionServices.reject(set, null);
		assertTrue(filtered.isEmpty());
	}

	@Test
	public void testRejectSetNotBooleanLambda() {
		Set<String> set = new LinkedHashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");
		Set<String> result = collectionServices.reject(set, createSelfLambda());
		assertTrue(result.isEmpty());
	}

	@Test
	public void testRejectSetExceptionLambda() {
		Set<String> set = new LinkedHashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");

		Diagnostic diagnostic = new BasicDiagnostic();
		assertEquals(Collections.EMPTY_SET, collectionServices.reject(set, createExceptionLambda(
				diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(3, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(2).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(2).getMessage());
	}

	@Test
	public void testCollectList() throws URISyntaxException, IOException {
		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		List<Object> list = new ArrayList<>();
		list.add(iterator.next());
		list.add(iterator.next());
		list.add(iterator.next());

		List<Object> filtered = collectionServices.collect(list, null);
		assertTrue(filtered.isEmpty());

		filtered = collectionServices.collect(list, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
		assertEquals(3, filtered.size());
		assertEquals(Boolean.TRUE, filtered.get(0));
		assertEquals(Boolean.FALSE, filtered.get(1));
		assertEquals(Boolean.FALSE, filtered.get(2));
	}

	@Test(expected = NullPointerException.class)
	public void testCollectNullList() {
		List<Object> nullList = null;
		collectionServices.collect(nullList, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
	}

	/**
	 * Test that null is trimmed from the result of a collect iteration.
	 */
	@Test
	public void testCollectNothingNullList() throws URISyntaxException, IOException {
		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		List<Object> list = new ArrayList<>();
		Object queryWithExpression = iterator.next();
		assertTrue(queryWithExpression instanceof org.eclipse.acceleo.query.tests.qmodel.Query);
		list.add(queryWithExpression);
		list.add(iterator.next());
		list.add(iterator.next());

		List<Object> filtered = collectionServices.collect(list, null);
		assertTrue(filtered.isEmpty());

		filtered = collectionServices.collect(list, createQueryExpressionLambda());
		assertEquals(1, filtered.size());
		Iterator<Object> itr = filtered.iterator();
		assertEquals(((org.eclipse.acceleo.query.tests.qmodel.Query)queryWithExpression).getExpression(), itr
				.next());
	}

	@Test
	public void testCollectListNullLambda() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		List<Object> filtered = collectionServices.collect(list, null);
		assertTrue(filtered.isEmpty());
	}

	/**
	 * Test that the result of a call to collect on a list is flattened properly.
	 */
	@Test
	public void testCollectImplicitFlattenList() {
		LambdaValue lambdaValue = new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				return EcorePackage.eINSTANCE.getEClassifiers();
			}
		};

		List<Object> list = new ArrayList<Object>();
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE);
		List<Object> result = collectionServices.collect(list, lambdaValue);

		assertEquals(EcorePackage.eINSTANCE.getEClassifiers().size() * 2, result.size());
	}

	@Test
	public void testCollectSet() throws URISyntaxException, IOException {
		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		Set<Object> set = new LinkedHashSet<>();
		set.add(iterator.next());
		set.add(iterator.next());
		set.add(iterator.next());

		Set<Object> filtered = collectionServices.collect(set, null);
		assertTrue(filtered.isEmpty());

		filtered = collectionServices.collect(set, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
		// This "instance of" lambda will return true, false and false...
		// but our "set" result cannot hold a duplicated Boolean.FALSE
		assertEquals(2, filtered.size());
		Iterator<Object> itr = filtered.iterator();
		assertEquals(Boolean.TRUE, itr.next());
		assertEquals(Boolean.FALSE, itr.next());
	}

	@Test(expected = NullPointerException.class)
	public void testCollectNullSet() {
		Set<Object> nullSet = null;
		collectionServices.collect(nullSet, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
	}

	/**
	 * Test that null is trimmed from the result of a collect iteration.
	 */
	@Test
	public void testCollectNothingNullSet() throws URISyntaxException, IOException {
		Resource reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		EObject queries = reverseModel.getContents().get(0);
		TreeIterator<EObject> iterator = queries.eAllContents();

		Set<Object> set = new LinkedHashSet<>();
		Object queryWithExpression = iterator.next();
		assertTrue(queryWithExpression instanceof org.eclipse.acceleo.query.tests.qmodel.Query);
		set.add(queryWithExpression);
		set.add(iterator.next());
		set.add(iterator.next());

		Set<Object> filtered = collectionServices.collect(set, null);
		assertTrue(filtered.isEmpty());

		filtered = collectionServices.collect(set, createQueryExpressionLambda());
		assertEquals(1, filtered.size());
		Iterator<Object> itr = filtered.iterator();
		assertEquals(((org.eclipse.acceleo.query.tests.qmodel.Query)queryWithExpression).getExpression(), itr
				.next());
	}

	@Test
	public void testCollectSetNullLambda() {
		Set<String> set = new LinkedHashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");
		Set<Object> filtered = collectionServices.collect(set, null);
		assertTrue(filtered.isEmpty());
	}

	/**
	 * Test that the result of a call to collect on a set is flattened properly.
	 */
	@Test
	public void testCollectImplicitFlattenSet() {
		LambdaValue lambdaValue = new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				return EcorePackage.eINSTANCE.getEClassifiers();
			}
		};

		LinkedHashSet<Object> set = new LinkedHashSet<Object>();
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE);
		Set<Object> result = collectionServices.collect(set, lambdaValue);

		assertEquals(EcorePackage.eINSTANCE.getEClassifiers().size(), result.size());
	}

	private LambdaValue createQueryExpressionLambda() {
		return new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				if (args[0] instanceof org.eclipse.acceleo.query.tests.qmodel.Query) {
					return ((org.eclipse.acceleo.query.tests.qmodel.Query)args[0]).getExpression();
				}
				return null;
			}
		};
	}

	private static class TestClosure {
		final List<Object> refs = new ArrayList<Object>();

		public List<Object> getRefs() {
			return refs;
		}
	}

	@Test
	public void testClosure() {
		final LambdaValue lambdaValue = new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				final Object result;

				if (args[0] instanceof TestClosure) {
					result = ((TestClosure)args[0]).getRefs();
				} else {
					result = null;
				}

				return result;
			}
		};

		TestClosure a = new TestClosure();
		TestClosure b = new TestClosure();
		TestClosure c = new TestClosure();
		TestClosure d = new TestClosure();
		TestClosure e = new TestClosure();
		TestClosure f = new TestClosure();

		a.getRefs().add(b);

		b.getRefs().add(d);
		b.getRefs().add(c);

		c.getRefs().add(e);
		c.getRefs().add(f);

		LinkedHashSet<Object> set = new LinkedHashSet<Object>();
		set.add(a);
		Set<Object> result = collectionServices.closure(set, lambdaValue);

		assertEquals(5, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(b, it.next());
		assertEquals(d, it.next());
		assertEquals(c, it.next());
		assertEquals(e, it.next());
		assertEquals(f, it.next());
	}

	@Test
	public void testClosureRecursive() {
		final LambdaValue lambdaValue = new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				final Object result;

				if (args[0] instanceof TestClosure) {
					result = ((TestClosure)args[0]).getRefs();
				} else {
					result = null;
				}

				return result;
			}
		};

		TestClosure a = new TestClosure();
		TestClosure b = new TestClosure();
		TestClosure c = new TestClosure();
		TestClosure d = new TestClosure();
		TestClosure e = new TestClosure();
		TestClosure f = new TestClosure();

		a.getRefs().add(b);

		b.getRefs().add(d);
		b.getRefs().add(c);

		c.getRefs().add(e);
		c.getRefs().add(f);

		f.getRefs().add(b);

		LinkedHashSet<Object> set = new LinkedHashSet<Object>();
		set.add(a);
		Set<Object> result = collectionServices.closure(set, lambdaValue);

		assertEquals(5, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(b, it.next());
		assertEquals(d, it.next());
		assertEquals(c, it.next());
		assertEquals(e, it.next());
		assertEquals(f, it.next());
	}

	@Test
	public void testClosureNothingNull() {
		final LambdaValue nullLambdaValue = new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				return null;
			}
		};

		LinkedHashSet<Object> set = new LinkedHashSet<Object>();
		set.add(EcorePackage.eINSTANCE);
		Set<Object> result = collectionServices.closure(set, nullLambdaValue);
		assertEquals(0, result.size());

		final LambdaValue nothingLambdaValue = new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				return new Nothing("");
			}
		};

		set = new LinkedHashSet<Object>();
		set.add(EcorePackage.eINSTANCE);
		result = collectionServices.closure(set, nothingLambdaValue);
		assertEquals(0, result.size());
	}

	@Test
	public void testClosureSubPackages() {
		final EPackage pkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		pkg.setName("package1");
		final EPackage package11 = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		package11.setName("package11");
		final EPackage package12 = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		package12.setName("package12");
		final EPackage package111 = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		package111.setName("package111");
		final EPackage package121 = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		package121.setName("package121");
		final EPackage package1111 = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		package1111.setName("package1111");
		final EPackage package1112 = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		package1112.setName("package1112");

		pkg.getESubpackages().add(package11);
		pkg.getESubpackages().add(package12);

		package11.getESubpackages().add(package111);
		package12.getESubpackages().add(package121);

		package111.getESubpackages().add(package1111);
		package111.getESubpackages().add(package1112);

		final LambdaValue subPackagesLambdaValue = new LambdaValue(null, null, null, null) {
			@Override
			public Object eval(Object[] args) {
				if (args[0] instanceof EPackage)
					return ((EPackage)args[0]).getESubpackages();
				return null;
			}
		};

		Set<Object> result = collectionServices.closure(Collections.singleton(pkg), subPackagesLambdaValue);

		assertEquals(6, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(package11, it.next());
		assertEquals(package12, it.next());
		assertEquals(package111, it.next());
		assertEquals(package121, it.next());
		assertEquals(package1111, it.next());
		assertEquals(package1112, it.next());
	}

	@Test
	public void testFilterSetNullSetAndNullClass() {
		final Set<Object> result = collectionServices.filter((Set<Object>)null, (Class<?>)null);

		assertEquals(null, result);
	}

	@Test
	public void testFilterSetNullSetClass() {
		final Set<Object> result = collectionServices.filter((Set<Object>)null, String.class);

		assertEquals(null, result);
	}

	@Test
	public void testFilterSetNullClass() {
		final Set<Object> set = new LinkedHashSet<>();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		final Set<Object> result = collectionServices.filter(set, (Class<?>)null);

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterSetClass() {
		final Set<Object> set = new LinkedHashSet<>();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		final Set<Object> result = collectionServices.filter(set, String.class);

		assertEquals(1, result.size());
		assertTrue(result.contains(""));
	}

	@Test
	public void testFilterSetNullSetAndNullType() {
		final Set<Object> result = collectionServices.filter((Set<Object>)null, (EClassifier)null);

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
		final Set<Object> set = new LinkedHashSet<>();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		final Set<Object> result = collectionServices.filter(set, (EClassifier)null);

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterSet() {
		final Set<Object> set = new LinkedHashSet<>();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		final Set<Object> result = collectionServices.filter(set, EcorePackage.eINSTANCE.getEPackage());

		assertEquals(1, result.size());
		assertTrue(result.contains(EcorePackage.eINSTANCE));
	}

	@Test
	public void testFilterListNullListAndNullClass() {
		final List<Object> result = collectionServices.filter((List<Object>)null, (Class<?>)null);

		assertEquals(null, result);
	}

	@Test
	public void testFilterListNullListClass() {
		final List<Object> result = collectionServices.filter((List<Object>)null, String.class);

		assertEquals(null, result);
	}

	@Test
	public void testFilterListNullClass() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final List<Object> result = collectionServices.filter(list, (Class<?>)null);

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterListClass() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final List<Object> result = collectionServices.filter(list, EcorePackage.eINSTANCE.getEPackage());

		assertEquals(1, result.size());
		assertTrue(result.contains(EcorePackage.eINSTANCE));
	}

	@Test
	public void testFilterListNullListAndNullType() {
		final List<Object> result = collectionServices.filter((List<Object>)null, (EClassifier)null);

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
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final List<Object> result = collectionServices.filter(list, (EClassifier)null);

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterList() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final List<Object> result = collectionServices.filter(list, EcorePackage.eINSTANCE.getEPackage());

		assertEquals(1, result.size());
		assertTrue(result.contains(EcorePackage.eINSTANCE));
	}

	@Test
	public void testFilterSetNullSetAndNullTypeEClassifierSet() {
		final Set<Object> result = collectionServices.filter((Set<Object>)null, (Set<EClassifier>)null);

		assertEquals(null, result);
	}

	@Test
	public void testFilterSetNullSetEClassifierSet() {
		final Set<EClassifier> eClassifiers = new LinkedHashSet<EClassifier>();
		eClassifiers.add(EcorePackage.eINSTANCE.getEClass());
		final Set<Object> result = collectionServices.filter((Set<Object>)null, eClassifiers);

		assertEquals(null, result);
	}

	@Test
	public void testFilterSetNullTypeEClassifierSet() {
		final Set<Object> set = new LinkedHashSet<>();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		final Set<Object> result = collectionServices.filter(set, (Set<EClassifier>)null);

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterSetEmptyEClassifierSet() {
		final Set<Object> set = new LinkedHashSet<>();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		final Set<Object> result = collectionServices.filter(set, new LinkedHashSet<>());

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterSetEClassifierSet() {
		final Set<Object> set = new LinkedHashSet<>();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		final Set<EClassifier> eClassifiers = new LinkedHashSet<EClassifier>();
		eClassifiers.add(EcorePackage.eINSTANCE.getEPackage());
		eClassifiers.add(EcorePackage.eINSTANCE.getEClass());
		final Set<Object> result = collectionServices.filter(set, eClassifiers);

		assertEquals(2, result.size());
		assertTrue(result.contains(EcorePackage.eINSTANCE));
		assertTrue(result.contains(EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void testFilterListNullListAndNullTypeEClassifierSet() {
		final List<Object> result = collectionServices.filter((List<Object>)null, (Set<EClassifier>)null);

		assertEquals(null, result);
	}

	@Test
	public void testFilterListNullListEClassifierSet() {
		final Set<EClassifier> eClassifiers = new LinkedHashSet<EClassifier>();
		eClassifiers.add(EcorePackage.eINSTANCE.getEClass());
		final List<Object> result = collectionServices.filter((List<Object>)null, eClassifiers);

		assertEquals(null, result);
	}

	@Test
	public void testFilterListNullTypeEClassifierSet() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final List<Object> result = collectionServices.filter(list, (Set<EClassifier>)null);

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterListEmptyEClassifierSet() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final List<Object> result = collectionServices.filter(list, new LinkedHashSet<>());

		assertEquals(0, result.size());
	}

	@Test
	public void testFilterListEClassifierSet() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final Set<EClassifier> eClassifiers = new LinkedHashSet<EClassifier>();
		eClassifiers.add(EcorePackage.eINSTANCE.getEPackage());
		eClassifiers.add(EcorePackage.eINSTANCE.getEClass());
		final List<Object> result = collectionServices.filter(list, eClassifiers);

		assertEquals(2, result.size());
		assertTrue(result.contains(EcorePackage.eINSTANCE));
		assertTrue(result.contains(EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void testFilterOnEContents_ecore_477217() {
		EPackage rootPackage = EcoreFactory.eINSTANCE.createEPackage();
		EPackage subPackage = EcoreFactory.eINSTANCE.createEPackage();
		EClass nestedClass = EcoreFactory.eINSTANCE.createEClass();

		rootPackage.getEClassifiers().add(nestedClass);
		rootPackage.getESubpackages().add(subPackage);

		IQueryEnvironment queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);

		IQueryBuilderEngine queryBuilder = new QueryBuilderEngine();
		AstResult query = queryBuilder.build("self.eContents()->filter(ecore::EClass)");

		IQueryEvaluationEngine evaluationEngine = new QueryEvaluationEngine(queryEnvironment);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", rootPackage);
		EvaluationResult result = evaluationEngine.eval(query, variables);

		assertTrue(result.getResult() instanceof List<?>);
		assertEquals(1, ((List<?>)result.getResult()).size());
		assertTrue(((List<?>)result.getResult()).contains(nestedClass));
	}

	@Test
	public void testFilterOnEContents_anydsl_477217() {
		World world = AnydslFactory.eINSTANCE.createWorld();
		Company comp1 = AnydslFactory.eINSTANCE.createRestaurant();
		Company comp2 = AnydslFactory.eINSTANCE.createProductionCompany();
		Food food = AnydslFactory.eINSTANCE.createFood();

		world.getCompanies().add(comp1);
		world.getCompanies().add(comp2);
		world.getFoods().add(food);

		IQueryEnvironment queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		queryEnvironment.registerEPackage(AnydslPackage.eINSTANCE);

		IQueryBuilderEngine queryBuilder = new QueryBuilderEngine();
		AstResult query = queryBuilder.build("self.eContents()->filter(anydsl::Company)");

		IQueryEvaluationEngine evaluationEngine = new QueryEvaluationEngine(queryEnvironment);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("self", world);
		EvaluationResult result = evaluationEngine.eval(query, variables);

		assertTrue(result.getResult() instanceof List<?>);
		assertEquals(2, ((List<?>)result.getResult()).size());
		assertTrue(((List<?>)result.getResult()).contains(comp1));
		assertTrue(((List<?>)result.getResult()).contains(comp2));
	}

	@Test
	public void testSepNullListNullSeparator() {
		final List<Object> result = collectionServices.sep((List<?>)null, null);

		assertEquals(null, result);
	}

	@Test
	public void testSepNullList() {
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep((List<?>)null, separator);

		assertEquals(null, result);
	}

	@Test
	public void testSepEmptyList() {
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep(new ArrayList<>(), separator);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testSepListNullSeparator() {
		final List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");

		final List<Object> result = collectionServices.sep(list, null);

		assertEquals(5, result.size());
		assertEquals("a", result.get(0));
		assertEquals(null, result.get(1));
		assertEquals("b", result.get(2));
		assertEquals(null, result.get(3));
		assertEquals("c", result.get(4));
	}

	@Test
	public void testSepList() {
		final List<Object> list = new ArrayList<>();
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
	public void testSepNullSetNullSeparator() {
		final List<Object> result = collectionServices.sep((Set<?>)null, null);

		assertEquals(null, result);
	}

	@Test
	public void testSepNullSet() {
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep((Set<?>)null, separator);

		assertEquals(null, result);
	}

	@Test
	public void testSepEmptySet() {
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep(new LinkedHashSet<>(), separator);

		assertTrue(result.isEmpty());
	}

	@Test
	public void testSepSetNullSeparator() {
		final Set<String> set = new LinkedHashSet<>();
		set.add("a");
		set.add("b");
		set.add("c");

		final List<Object> result = collectionServices.sep(set, null);

		assertEquals(5, result.size());
		assertEquals("a", result.get(0));
		assertEquals(null, result.get(1));
		assertEquals("b", result.get(2));
		assertEquals(null, result.get(3));
		assertEquals("c", result.get(4));
	}

	@Test
	public void testSepSet() {
		final Set<Object> set = new LinkedHashSet<>();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep(set, separator);

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
	public void testSepPrefixSuffixNullListNullPrefixNullSeparatorNullSuffix() {
		final List<Object> result = collectionServices.sep((List<?>)null, null, null, null);

		List<Object> expected = new ArrayList<Object>();
		expected.add(null);
		expected.add(null);
		assertEquals(expected, result);
	}

	@Test
	public void testSepPrefixSuffixNullListNullSeparatorNullSuffix() {
		Object prefix = new Object();

		final List<Object> result = collectionServices.sep((List<?>)null, prefix, null, null);

		List<Object> expected = new ArrayList<Object>();
		expected.add(prefix);
		expected.add(null);
		assertEquals(expected, result);
	}

	@Test
	public void testSepPrefixSuffixNullListNullSuffix() {
		Object prefix = new Object();
		Object separator = new Object();

		final List<Object> result = collectionServices.sep((List<?>)null, prefix, separator, null);

		List<Object> expected = new ArrayList<Object>();
		expected.add(prefix);
		expected.add(null);
		assertEquals(expected, result);
	}

	@Test
	public void testSepPrefixSuffixNullListNullSeparator() {
		Object prefix = new Object();
		Object suffix = new Object();

		final List<Object> result = collectionServices.sep((List<?>)null, prefix, null, suffix);

		List<Object> expected = new ArrayList<Object>();
		expected.add(prefix);
		expected.add(suffix);
		assertEquals(expected, result);
	}

	@Test
	public void testSepPrefixSuffixNullList() {
		Object prefix = new Object();
		Object separator = new Object();
		Object suffix = new Object();

		final List<Object> result = collectionServices.sep((List<?>)null, prefix, separator, suffix);

		List<Object> expected = new ArrayList<Object>();
		expected.add(prefix);
		expected.add(suffix);
		assertEquals(expected, result);
	}

	@Test
	public void testSepPrefixSuffixNullListNullPrefixNullSuffix() {
		Object separator = new Object();

		final List<Object> result = collectionServices.sep((List<?>)null, null, separator, null);

		List<Object> expected = new ArrayList<Object>();
		expected.add(null);
		expected.add(null);
		assertEquals(expected, result);
	}

	@Test
	public void testSepPrefixSuffixNullListNullPrefixNullSeparator() {
		Object suffix = new Object();

		final List<Object> result = collectionServices.sep((List<?>)null, null, null, suffix);

		List<Object> expected = new ArrayList<Object>();
		expected.add(null);
		expected.add(suffix);
		assertEquals(expected, result);
	}

	@Test
	public void testSepPrefixSuffixNullListNullPrefix() {
		Object suffix = new Object();
		Object separator = new Object();

		final List<Object> result = collectionServices.sep((List<?>)null, null, separator, suffix);

		List<Object> expected = new ArrayList<Object>();
		expected.add(null);
		expected.add(suffix);
		assertEquals(expected, result);
	}

	@Test
	public void testSepPrefixSuffixNullPrefixNullSeparatorNullSuffix() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		final List<Object> result = collectionServices.sep(list, null, null, null);

		assertEquals(9, result.size());
		assertEquals(null, result.get(0));
		assertEquals(this, result.get(1));
		assertEquals(null, result.get(2));
		assertEquals("", result.get(3));
		assertEquals(null, result.get(4));
		assertEquals(EcorePackage.eINSTANCE, result.get(5));
		assertEquals(null, result.get(6));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result.get(7));
		assertEquals(null, result.get(8));
	}

	@Test
	public void testSepPrefixSuffixNullSeparatorNullSuffix() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());
		final Object prefix = new Object();

		final List<Object> result = collectionServices.sep(list, prefix, null, null);

		assertEquals(9, result.size());
		assertEquals(prefix, result.get(0));
		assertEquals(this, result.get(1));
		assertEquals(null, result.get(2));
		assertEquals("", result.get(3));
		assertEquals(null, result.get(4));
		assertEquals(EcorePackage.eINSTANCE, result.get(5));
		assertEquals(null, result.get(6));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result.get(7));
		assertEquals(null, result.get(8));
	}

	@Test
	public void testSepPrefixSuffixNullSuffix() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());
		final Object prefix = new Object();
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep(list, prefix, separator, null);

		assertEquals(9, result.size());
		assertEquals(prefix, result.get(0));
		assertEquals(this, result.get(1));
		assertEquals(separator, result.get(2));
		assertEquals("", result.get(3));
		assertEquals(separator, result.get(4));
		assertEquals(EcorePackage.eINSTANCE, result.get(5));
		assertEquals(separator, result.get(6));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result.get(7));
		assertEquals(null, result.get(8));
	}

	@Test
	public void testSepPrefixSuffixNullSeparator() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());
		final Object prefix = new Object();
		final Object suffix = new Object();

		final List<Object> result = collectionServices.sep(list, prefix, null, suffix);

		assertEquals(9, result.size());
		assertEquals(prefix, result.get(0));
		assertEquals(this, result.get(1));
		assertEquals(null, result.get(2));
		assertEquals("", result.get(3));
		assertEquals(null, result.get(4));
		assertEquals(EcorePackage.eINSTANCE, result.get(5));
		assertEquals(null, result.get(6));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result.get(7));
		assertEquals(suffix, result.get(8));
	}

	@Test
	public void testSepPrefixSuffix() {
		final List<Object> list = new ArrayList<>();
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

	@Test
	public void testSepPrefixSuffixNullPrefixNullSuffix() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());
		final Object separator = new Object();

		final List<Object> result = collectionServices.sep(list, null, separator, null);

		assertEquals(9, result.size());
		assertEquals(null, result.get(0));
		assertEquals(this, result.get(1));
		assertEquals(separator, result.get(2));
		assertEquals("", result.get(3));
		assertEquals(separator, result.get(4));
		assertEquals(EcorePackage.eINSTANCE, result.get(5));
		assertEquals(separator, result.get(6));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result.get(7));
		assertEquals(null, result.get(8));
	}

	@Test
	public void testSepPrefixSuffixNullPrefixNullSeparator() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());
		final Object suffix = new Object();

		final List<Object> result = collectionServices.sep(list, null, null, suffix);

		assertEquals(9, result.size());
		assertEquals(null, result.get(0));
		assertEquals(this, result.get(1));
		assertEquals(null, result.get(2));
		assertEquals("", result.get(3));
		assertEquals(null, result.get(4));
		assertEquals(EcorePackage.eINSTANCE, result.get(5));
		assertEquals(null, result.get(6));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), result.get(7));
		assertEquals(suffix, result.get(8));
	}

	@Test
	public void testSepPrefixSuffixNullPrefix() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());
		final Object separator = new Object();
		final Object suffix = new Object();

		final List<Object> result = collectionServices.sep(list, null, separator, suffix);

		assertEquals(9, result.size());
		assertEquals(null, result.get(0));
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
		Object result = collectionServices.last((List<?>)null);

		assertNull(result);
	}

	@Test
	public void testLastEmptyList() {
		final List<Object> list = new ArrayList<>();

		Object result = collectionServices.last(list);

		assertNull(result);
	}

	@Test
	public void testLastList() {
		final List<Object> list = new ArrayList<>();
		list.add(this);
		list.add("");
		list.add(EcorePackage.eINSTANCE);
		list.add(EcorePackage.eINSTANCE.getEClass());

		Object result = collectionServices.last(list);

		assertEquals(EcorePackage.eINSTANCE.getEClass(), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testLastNullSet() {
		Object result = collectionServices.last((Set<?>)null);

		assertNull(result);
	}

	@Test
	public void testLastEmptySet() {
		final Set<Object> set = new LinkedHashSet<>();

		Object result = collectionServices.last(set);

		assertNull(result);
	}

	@Test
	public void testLastSet() {
		final Set<Object> set = new LinkedHashSet<>();
		set.add(this);
		set.add("");
		set.add(EcorePackage.eINSTANCE);
		set.add(EcorePackage.eINSTANCE.getEClass());

		Object result = collectionServices.last(set);

		assertEquals(EcorePackage.eINSTANCE.getEClass(), result);
	}

	@Test
	public void testExcludesList() {
		final List<Object> list = new ArrayList<>();

		assertTrue(collectionServices.excludes(list, this));

		list.add(this);

		assertEquals(false, collectionServices.excludes(list, this));
	}

	@Test(expected = NullPointerException.class)
	public void testExcludesNullList() {
		collectionServices.excludes((List<?>)null, this);
	}

	@Test
	public void testExcludesSet() {
		final Set<Object> set = new LinkedHashSet<>();

		assertTrue(collectionServices.excludes(set, this));

		set.add(this);

		assertEquals(false, collectionServices.excludes(set, this));
	}

	@Test(expected = NullPointerException.class)
	public void testExcludesNullSet() {
		collectionServices.excludes((Set<?>)null, this);
	}

	@Test
	public void testIncludesList() {
		final List<Object> list = new ArrayList<>();

		assertEquals(false, collectionServices.includes(list, this));

		list.add(this);

		assertTrue(collectionServices.includes(list, this));
	}

	@Test(expected = NullPointerException.class)
	public void testIncludesNullList() {
		collectionServices.excludes((List<?>)null, this);
	}

	@Test
	public void testIncludesSet() {
		final Set<Object> set = new LinkedHashSet<>();

		assertEquals(false, collectionServices.includes(set, this));

		set.add(this);

		assertTrue(collectionServices.includes(set, this));
	}

	@Test(expected = NullPointerException.class)
	public void testIncludesNullSet() {
		collectionServices.excludes((Set<?>)null, this);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAnyNullCollectionNullLambda() {
		collectionServices.any(null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAnyNullList() {
		collectionServices.any((List<?>)null, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAnyNullSet() {
		collectionServices.any((Set<?>)null, createInstanceOfLambda(
				org.eclipse.acceleo.query.tests.qmodel.Query.class));
	}

	@Test
	public void testAnySetNullLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Object result = collectionServices.any(list, null);
		assertEquals(null, result);
	}

	@Test
	public void testAnyListNullLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Object result = collectionServices.any(set, null);
		assertEquals(null, result);
	}

	@Test
	public void testAnySetNotBooleanLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		assertEquals(null, collectionServices.any(set, createSelfLambda()));
	}

	@Test
	public void testAnySetExceptionLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Diagnostic diagnostic = new BasicDiagnostic();
		assertEquals(null, collectionServices.any(set, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(4, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(2).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(2).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(3).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(3).getMessage());
	}

	@Test
	public void testAnyListNotBooleanLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		assertEquals(null, collectionServices.any(list, createSelfLambda()));
	}

	@Test
	public void testAnyListExceptionLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Diagnostic diagnostic = new BasicDiagnostic();
		assertEquals(null, collectionServices.any(list, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(4, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(2).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(2).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(3).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(3).getMessage());
	}

	@Test
	public void testAnySet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Object result = collectionServices.any(set, createInstanceOfLambda(String.class));
		assertNull(result);

		set.add("s");
		result = collectionServices.any(set, createInstanceOfLambda(String.class));
		assertEquals("s", result);
	}

	@Test
	public void testAnyList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Object result = collectionServices.any(list, createInstanceOfLambda(String.class));
		assertNull(result);

		list.add("s");
		result = collectionServices.any(list, createInstanceOfLambda(String.class));
		assertEquals("s", result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testCountSetNullNull() {
		collectionServices.count((Set<Object>)null, null);
	}

	@Test
	public void testCountSetNull() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(null);

		Integer result = collectionServices.count(set, null);
		assertEquals(Integer.valueOf(1), result);
	}

	@Test
	public void testCountSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(null);

		Integer result = collectionServices.count(set, Integer.valueOf(4));
		assertEquals(Integer.valueOf(0), result);

		set.add(Integer.valueOf(4));
		result = collectionServices.count(set, Integer.valueOf(4));
		assertEquals(Integer.valueOf(1), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testCountListNullNull() {
		collectionServices.count((List<Object>)null, null);
	}

	@Test
	public void testCountListNull() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(null);

		Integer result = collectionServices.count(list, null);
		assertEquals(Integer.valueOf(1), result);
	}

	@Test
	public void testCountList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(null);
		list.add(Integer.valueOf(1));

		Integer result = collectionServices.count(list, Integer.valueOf(1));
		assertEquals(Integer.valueOf(2), result);

		result = collectionServices.count(list, Integer.valueOf(4));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void testExistsNullCollection() {
		assertEquals(Boolean.FALSE, collectionServices.exists(null, createInstanceOfLambda(Integer.class)));
	}

	@Test
	public void testExistsSetNullLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(list, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExistsListNullLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(set, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExistsSetNotBooleanLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(set, createSelfLambda());
		assertFalse(result);
	}

	@Test
	public void testExistsSetExceptionLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Diagnostic diagnostic = new BasicDiagnostic();
		assertFalse(collectionServices.exists(set, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(4, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(2).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(2).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(3).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(3).getMessage());

	}

	@Test
	public void testExistsListNotBooleanLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(list, createSelfLambda());
		assertFalse(result);
	}

	@Test
	public void testExistsListExceptionLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Diagnostic diagnostic = new BasicDiagnostic();
		assertFalse(collectionServices.exists(list, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(4, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(2).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(2).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(3).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(3).getMessage());
	}

	@Test
	public void testExistsSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(set, createInstanceOfLambda(String.class));
		assertEquals(Boolean.FALSE, result);

		set.add("s");
		result = collectionServices.exists(set, createInstanceOfLambda(String.class));
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testExistsList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.exists(list, createInstanceOfLambda(String.class));
		assertEquals(Boolean.FALSE, result);

		list.add("s");
		result = collectionServices.exists(list, createInstanceOfLambda(String.class));
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testForAllNullCollection() {
		assertEquals(Boolean.FALSE, collectionServices.forAll(null, createSelfLambda()));
	}

	@Test
	public void testForAllListNullLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(list, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testForAllSetNullLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(set, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testForAllSetNotBooleanLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(set, createSelfLambda());
		assertFalse(result);
	}

	@Test
	public void testForAllSetExceptionLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Diagnostic diagnostic = new BasicDiagnostic();
		assertFalse(collectionServices.forAll(set, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(1, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
	}

	@Test
	public void testForAllListNotBooleanLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(list, createSelfLambda());
		assertFalse(result);
	}

	@Test
	public void testForAllListExceptionLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(list, createSelfLambda());
		assertFalse(result);

		Diagnostic diagnostic = new BasicDiagnostic();
		assertFalse(collectionServices.forAll(list, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(1, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
	}

	@Test
	public void testForAllSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(set, createInstanceOfLambda(Integer.class));
		assertEquals(Boolean.TRUE, result);

		set.add("s");
		result = collectionServices.forAll(set, createInstanceOfLambda(Integer.class));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testForAllList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Boolean result = collectionServices.forAll(list, createInstanceOfLambda(Integer.class));
		assertEquals(Boolean.TRUE, result);

		list.add("s");
		result = collectionServices.forAll(list, createInstanceOfLambda(Integer.class));
		assertEquals(Boolean.FALSE, result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllNullNull() {
		collectionServices.excludesAll(null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllSetNull() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.excludesAll(set, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllListNull() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.excludesAll(list, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllNullSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.excludesAll(null, set);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testExcludesAllNullList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.excludesAll(null, list);
	}

	@Test
	public void testExcludesAllSetSetFalse() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(set, set1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExcludesAllSetSetTrue() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(6));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(set, set1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testExcludesAllSetListFalse() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(set, list1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExcludesAllSetListTrue() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(6));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(set, list1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testExcludesAllListSetFalse() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(list, set1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExcludesAllListSetTrue() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(6));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(list, set1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testExcludesAllListListFalse() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.excludesAll(list, list1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testExcludesAllListListTrue() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		List<Object> list1 = new ArrayList<>();
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
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.includesAll(set, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIncludesAllListNull() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.includesAll(list, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIncludesAllNullSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.includesAll(null, set);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIncludesAllNullList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.includesAll(null, list);
	}

	@Test
	public void testIncludesAllSetSetFalse() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.includesAll(set, set1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIncludesAllSetSetTrue() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));

		Boolean result = collectionServices.includesAll(set, set1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testIncludesAllSetListFalse() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.includesAll(set, list1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIncludesAllSetListTrue() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));

		Boolean result = collectionServices.includesAll(set, list1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testIncludesAllListSetFalse() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(5));

		Boolean result = collectionServices.includesAll(list, set1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIncludesAllListSetTrue() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));

		Boolean result = collectionServices.includesAll(list, set1);
		assertEquals(Boolean.TRUE, result);
	}

	@Test
	public void testIncludesAllListListFalse() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(5));

		Boolean result = collectionServices.includesAll(list, list1);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIncludesAllListListTrue() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		List<Object> list1 = new ArrayList<>();
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
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.isUnique(set, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIsUniqueListNull() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.isUnique(list, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIsUniqueSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));

		Boolean result = collectionServices.isUnique(set, createInstanceOfLambda(String.class));
		assertEquals(Boolean.TRUE, result);

		set.add("a");
		result = collectionServices.isUnique(set, createInstanceOfLambda(String.class));
		assertEquals(Boolean.TRUE, result);

		set.add("b");
		result = collectionServices.isUnique(set, createInstanceOfLambda(String.class));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testIsUniqueList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));

		Boolean result = collectionServices.isUnique(list, createInstanceOfLambda(String.class));
		assertEquals(Boolean.TRUE, result);

		list.add("a");
		result = collectionServices.isUnique(list, createInstanceOfLambda(String.class));
		assertEquals(Boolean.TRUE, result);

		list.add("b");
		result = collectionServices.isUnique(list, createInstanceOfLambda(String.class));
		assertEquals(Boolean.FALSE, result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testOneNullNull() {
		collectionServices.one(null, null);
	}

	@Test
	public void testOneSetNull() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(set, null);
		assertEquals(Boolean.FALSE, result);

		set.add(null);
		result = collectionServices.one(set, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testOneListNull() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(list, null);
		assertEquals(Boolean.FALSE, result);

		list.add(null);
		result = collectionServices.one(list, null);
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testOneSetNotBooleanLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(set, createSelfLambda());
		assertFalse(result);
	}

	@Test
	public void testOneSetExceptionLambda() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(3));

		Diagnostic diagnostic = new BasicDiagnostic();
		assertFalse(collectionServices.one(set, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(2, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
	}

	@Test
	public void testOneListNotBooleanLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(list, createSelfLambda());
		assertFalse(result);
	}

	@Test
	public void testOneListExceptionLambda() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(3));

		Diagnostic diagnostic = new BasicDiagnostic();
		assertFalse(collectionServices.one(list, createExceptionLambda(diagnostic)));
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(2, diagnostic.getChildren().size());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(0).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(0).getMessage());
		assertEquals(Diagnostic.WARNING, diagnostic.getChildren().get(1).getSeverity());
		assertEquals("Test runtime exception lambda.", diagnostic.getChildren().get(1).getMessage());
	}

	@Test
	public void testOneSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(set, createInstanceOfLambda(String.class));
		assertEquals(Boolean.FALSE, result);

		set.add("s");
		result = collectionServices.one(set, createInstanceOfLambda(String.class));
		assertEquals(Boolean.TRUE, result);

		set.add("a");
		result = collectionServices.one(set, createInstanceOfLambda(String.class));
		assertEquals(Boolean.FALSE, result);
	}

	@Test
	public void testOneList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(3));

		Boolean result = collectionServices.one(list, createInstanceOfLambda(String.class));
		assertEquals(Boolean.FALSE, result);

		list.add("s");
		result = collectionServices.one(list, createInstanceOfLambda(String.class));
		assertEquals(Boolean.TRUE, result);

		list.add("a");
		result = collectionServices.one(list, createInstanceOfLambda(String.class));
		assertEquals(Boolean.FALSE, result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSumNull() {
		collectionServices.sum(null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSumSetNotNumber() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add("potatoes");
		set.add(Integer.valueOf(3));

		collectionServices.sum(set);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testSumListNotNumber() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add("potatoes");
		list.add(Integer.valueOf(3));

		collectionServices.sum(list);
	}

	@Test
	public void testSumSetIntegers() {
		Set<Number> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Number result = collectionServices.sum(set);
		assertTrue(result instanceof Long);
		assertEquals(Long.valueOf(6), result);
	}

	@Test
	public void testSumSetTypeMix() {
		Set<Number> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Long.valueOf(2));
		set.add(Double.valueOf(3));
		set.add(Float.valueOf(4));

		Number result = collectionServices.sum(set);
		assertTrue(result instanceof Double);
		assertEquals(Double.valueOf(10), result);
	}

	@Test
	public void testSumListIntegers() {
		List<Number> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Number result = collectionServices.sum(list);
		assertTrue(result instanceof Long);
		assertEquals(Long.valueOf(6), result);
	}

	@Test
	public void testSumListTypeMix() {
		List<Number> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Double.valueOf(2));
		list.add(Float.valueOf(3));

		Number result = collectionServices.sum(list);
		assertTrue(result instanceof Double);
		assertEquals(Double.valueOf(6), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMinNull() {
		collectionServices.min(null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testMinSetNotNumber() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add("potatoes");
		set.add(Integer.valueOf(3));

		collectionServices.min(set);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testMinListNotNumber() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add("potatoes");
		list.add(Integer.valueOf(3));

		collectionServices.min(list);
	}

	@Test
	public void testMinSetIntegers() {
		Set<Number> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Number result = collectionServices.min(set);
		assertTrue(result instanceof Long);
		assertEquals(Long.valueOf(1), result);
	}

	@Test
	public void testMinSetTypeMix() {
		Set<Number> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Long.valueOf(2));
		set.add(Double.valueOf(3));
		set.add(Float.valueOf(4));

		Number result = collectionServices.min(set);
		assertTrue(result instanceof Double);
		assertEquals(Double.valueOf(1), result);
	}

	@Test
	public void testMinListIntegers() {
		List<Number> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Number result = collectionServices.min(list);
		assertTrue(result instanceof Long);
		assertEquals(Long.valueOf(1), result);
	}

	@Test
	public void testMinListTypeMix() {
		List<Number> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Double.valueOf(2));
		list.add(Float.valueOf(3));

		Number result = collectionServices.min(list);
		assertTrue(result instanceof Double);
		assertEquals(Double.valueOf(1), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMaxNull() {
		collectionServices.max(null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testMaxSetNotNumber() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add("potatoes");
		set.add(Integer.valueOf(3));

		collectionServices.max(set);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testMaxListNotNumber() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add("potatoes");
		list.add(Integer.valueOf(3));

		collectionServices.max(list);
	}

	@Test
	public void testMaxSetIntegers() {
		Set<Number> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Number result = collectionServices.max(set);
		assertTrue(result instanceof Long);
		assertEquals(Long.valueOf(3), result);
	}

	@Test
	public void testMaxSetTypeMix() {
		Set<Number> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Long.valueOf(2));
		set.add(Double.valueOf(3));
		set.add(Float.valueOf(4));

		Number result = collectionServices.max(set);
		assertTrue(result instanceof Double);
		assertEquals(Double.valueOf(4), result);
	}

	@Test
	public void testMaxListIntegers() {
		List<Number> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Number result = collectionServices.max(list);
		assertTrue(result instanceof Long);
		assertEquals(Long.valueOf(3), result);
	}

	@Test
	public void testMaxListTypeMix() {
		List<Number> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Double.valueOf(2));
		list.add(Float.valueOf(3));

		Number result = collectionServices.max(list);
		assertTrue(result instanceof Double);
		assertEquals(Double.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIndexOfNullListNull() {
		collectionServices.indexOf((List<?>)null, null);
	}

	@Test
	public void testIndexOfListNull() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Integer result = collectionServices.indexOf(list, null);
		assertEquals(Integer.valueOf(0), result);

		list.add(2, null);
		result = collectionServices.indexOf(list, null);
		assertEquals(Integer.valueOf(3), result);
	}

	@Test
	public void testIndexOfList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Integer result = collectionServices.indexOf(list, Integer.valueOf(7));
		assertEquals(Integer.valueOf(0), result);

		result = collectionServices.indexOf(list, Integer.valueOf(3));
		assertEquals(Integer.valueOf(3), result);

		list.remove(Integer.valueOf(3));
		result = collectionServices.indexOf(list, Integer.valueOf(3));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIndexOfNullSetNull() {
		collectionServices.indexOf((Set<?>)null, null);
	}

	@Test
	public void testIndexOfSetNull() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Integer result = collectionServices.indexOf(set, null);
		assertEquals(Integer.valueOf(0), result);

		set.add(null);
		result = collectionServices.indexOf(set, null);
		assertEquals(Integer.valueOf(4), result);
	}

	@Test
	public void testIndexOfSet() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(null);
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Integer result = collectionServices.indexOf(set, Integer.valueOf(7));
		assertEquals(Integer.valueOf(0), result);

		result = collectionServices.indexOf(set, Integer.valueOf(3));
		assertEquals(Integer.valueOf(4), result);

		set.remove(Integer.valueOf(3));
		result = collectionServices.indexOf(set, Integer.valueOf(3));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void testIndexOfSetEquality() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(null);
		set.add(Collections.singleton("s"));
		set.add(Integer.valueOf(1));

		Integer result = collectionServices.indexOf(set, Integer.valueOf(7));
		assertEquals(Integer.valueOf(0), result);

		result = collectionServices.indexOf(set, Collections.singleton("s"));
		assertEquals(Integer.valueOf(2), result);

		set.remove(Collections.singleton("s"));
		result = collectionServices.indexOf(set, Collections.singleton("s"));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testLastIndexOfNullListNull() {
		collectionServices.lastIndexOf((List<?>)null, null);
	}

	@Test
	public void testLastIndexOfListNull() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		Integer result = collectionServices.lastIndexOf(list, null);
		assertEquals(Integer.valueOf(0), result);

		list.add(2, null);
		result = collectionServices.lastIndexOf(list, null);
		assertEquals(Integer.valueOf(3), result);
	}

	@Test
	public void testLastIndexOfList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		list.add(Integer.valueOf(3));

		Integer result = collectionServices.lastIndexOf(list, Integer.valueOf(7));
		assertEquals(Integer.valueOf(0), result);

		result = collectionServices.lastIndexOf(list, Integer.valueOf(3));
		assertEquals(Integer.valueOf(5), result);

		list.remove(Integer.valueOf(3));
		result = collectionServices.lastIndexOf(list, Integer.valueOf(3));
		assertEquals(Integer.valueOf(4), result);

		list.remove(Integer.valueOf(3));
		result = collectionServices.lastIndexOf(list, Integer.valueOf(3));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testLastIndexOfNullSetNull() {
		collectionServices.lastIndexOf((Set<?>)null, null);
	}

	@Test
	public void testLastIndexOfSetNull() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Integer result = collectionServices.lastIndexOf(set, null);
		assertEquals(Integer.valueOf(0), result);

		set.add(null);
		result = collectionServices.lastIndexOf(set, null);
		assertEquals(Integer.valueOf(4), result);
	}

	@Test
	public void testLastIndexOfSet() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(null);
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Integer result = collectionServices.lastIndexOf(set, Integer.valueOf(7));
		assertEquals(Integer.valueOf(0), result);

		result = collectionServices.lastIndexOf(set, Integer.valueOf(3));
		assertEquals(Integer.valueOf(4), result);

		set.remove(Integer.valueOf(3));
		result = collectionServices.lastIndexOf(set, Integer.valueOf(3));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void testLastIndexOfSetEquality() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(null);
		set.add(Collections.singleton("s"));
		set.add(Integer.valueOf(1));

		Integer result = collectionServices.lastIndexOf(set, Integer.valueOf(7));
		assertEquals(Integer.valueOf(0), result);

		result = collectionServices.lastIndexOf(set, Collections.singleton("s"));
		assertEquals(Integer.valueOf(2), result);

		set.remove(Collections.singleton("s"));
		result = collectionServices.lastIndexOf(set, Collections.singleton("s"));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testInsertAtNullList() {
		collectionServices.insertAt((List<?>)null, 1, null);
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testInsertAtListUnderLowerBound() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		collectionServices.insertAt(list, 0, null);
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testInsertAtListOverUpperBound() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		collectionServices.insertAt(list, 5, null);
	}

	@Test
	public void testInsertAtList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		final List<Object> result = collectionServices.insertAt(list, 2, null);
		assertTrue(list != result);
		assertEquals(4, result.size());
		assertEquals(Integer.valueOf(1), result.get(0));
		assertEquals(null, result.get(1));
		assertEquals(Integer.valueOf(2), result.get(2));
		assertEquals(Integer.valueOf(3), result.get(3));
	}

	@Test
	public void testInsertAtExtremityList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		List<Object> result = collectionServices.insertAt(list, 1, null);
		assertTrue(list != result);
		assertEquals(4, result.size());
		assertEquals(null, result.get(0));
		assertEquals(Integer.valueOf(1), result.get(1));
		assertEquals(Integer.valueOf(2), result.get(2));
		assertEquals(Integer.valueOf(3), result.get(3));

		result = collectionServices.insertAt(result, 5, Double.valueOf(10));
		assertEquals(5, result.size());
		assertEquals(null, result.get(0));
		assertEquals(Integer.valueOf(1), result.get(1));
		assertEquals(Integer.valueOf(2), result.get(2));
		assertEquals(Integer.valueOf(3), result.get(3));
		assertEquals(Double.valueOf(10), result.get(4));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testInsertAtNullSet() {
		collectionServices.insertAt((Set<?>)null, 1, null);
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testInsertAtSetUnderLowerBound() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		collectionServices.insertAt(set, 0, null);
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testInsertAtSetOverUpperBound() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		collectionServices.insertAt(set, 5, null);
	}

	@Test
	public void testInsertAtSet() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		final Set<Integer> result = collectionServices.insertAt(set, 2, null);
		assertTrue(set != result);
		assertEquals(4, result.size());
		Iterator<Integer> itr = result.iterator();
		assertEquals(Integer.valueOf(1), itr.next());
		assertEquals(null, itr.next());
		assertEquals(Integer.valueOf(2), itr.next());
		assertEquals(Integer.valueOf(3), itr.next());
	}

	@Test
	public void testInsertAtExtremitySet() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		Set<Integer> result = collectionServices.insertAt(set, 1, null);
		assertTrue(set != result);
		assertEquals(4, result.size());
		Iterator<Integer> itr = result.iterator();
		assertEquals(null, itr.next());
		assertEquals(Integer.valueOf(1), itr.next());
		assertEquals(Integer.valueOf(2), itr.next());
		assertEquals(Integer.valueOf(3), itr.next());

		result = collectionServices.insertAt(result, 5, Integer.valueOf(10));
		assertEquals(5, result.size());
		itr = result.iterator();
		assertEquals(null, itr.next());
		assertEquals(Integer.valueOf(1), itr.next());
		assertEquals(Integer.valueOf(2), itr.next());
		assertEquals(Integer.valueOf(3), itr.next());
		assertEquals(Integer.valueOf(10), itr.next());
	}

	@Test
	public void testInsertAtSetDuplicate() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		final Set<Integer> result = collectionServices.insertAt(set, 1, Integer.valueOf(3));
		assertTrue(set != result);
		assertEquals(3, result.size());
		Iterator<Integer> itr = result.iterator();
		assertEquals(Integer.valueOf(3), itr.next());
		assertEquals(Integer.valueOf(1), itr.next());
		assertEquals(Integer.valueOf(2), itr.next());
	}

	@Test
	public void testInsertAtSetDuplicateEquality() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Collections.singleton("s"));
		set.add(Integer.valueOf(2));

		final Set<Object> result = collectionServices.insertAt(set, 3, Collections.singleton("s"));
		assertTrue(set != result);
		assertEquals(3, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals(Integer.valueOf(1), itr.next());
		assertEquals(Integer.valueOf(2), itr.next());
		assertEquals(Collections.singleton("s"), itr.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAppendNullList() {
		collectionServices.append((List<?>)null, null);
	}

	@Test
	public void testAppendList() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		final List<Object> result = collectionServices.append(list, null);
		assertTrue(list != result);
		assertEquals(4, result.size());
		assertEquals(Integer.valueOf(1), result.get(0));
		assertEquals(Integer.valueOf(2), result.get(1));
		assertEquals(Integer.valueOf(3), result.get(2));
		assertEquals(null, result.get(3));
	}

	@Test
	public void testAppendListDuplicate() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		final List<Object> result = collectionServices.append(list, Integer.valueOf(2));
		assertTrue(list != result);
		assertEquals(4, result.size());
		assertEquals(Integer.valueOf(1), result.get(0));
		assertEquals(Integer.valueOf(2), result.get(1));
		assertEquals(Integer.valueOf(3), result.get(2));
		assertEquals(Integer.valueOf(2), result.get(3));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAppendNullSet() {
		collectionServices.append((Set<?>)null, null);
	}

	@Test
	public void testAppendSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		final Set<Object> result = collectionServices.append(set, null);
		assertTrue(set != result);
		assertEquals(4, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals(Integer.valueOf(1), itr.next());
		assertEquals(Integer.valueOf(2), itr.next());
		assertEquals(Integer.valueOf(3), itr.next());
		assertEquals(null, itr.next());
	}

	@Test
	public void testAppendSetDuplicate() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		final Set<Object> result = collectionServices.append(set, Integer.valueOf(2));
		assertTrue(set != result);
		assertEquals(3, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals(Integer.valueOf(1), itr.next());
		assertEquals(Integer.valueOf(3), itr.next());
		assertEquals(Integer.valueOf(2), itr.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testPrependNullList() {
		collectionServices.prepend((List<?>)null, null);
	}

	@Test
	public void testPrependList() {
		List<Object> list = new ArrayList<>();
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

	@Test
	public void testPrependListDuplicate() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		final List<Object> result = collectionServices.prepend(list, Integer.valueOf(2));
		assertTrue(list != result);
		assertEquals(4, result.size());
		assertEquals(Integer.valueOf(2), result.get(0));
		assertEquals(Integer.valueOf(1), result.get(1));
		assertEquals(Integer.valueOf(2), result.get(2));
		assertEquals(Integer.valueOf(3), result.get(3));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testPrependNullSet() {
		collectionServices.prepend((Set<?>)null, null);
	}

	@Test
	public void testPrependSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		final Set<Object> result = collectionServices.prepend(set, null);
		assertTrue(set != result);
		assertEquals(4, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals(null, itr.next());
		assertEquals(Integer.valueOf(1), itr.next());
		assertEquals(Integer.valueOf(2), itr.next());
		assertEquals(Integer.valueOf(3), itr.next());
	}

	@Test
	public void testPrependSetDuplicate() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		final Set<Object> result = collectionServices.prepend(set, Integer.valueOf(2));
		assertTrue(set != result);
		assertEquals(3, result.size());
		Iterator<Object> itr = result.iterator();
		assertEquals(Integer.valueOf(2), itr.next());
		assertEquals(Integer.valueOf(1), itr.next());
		assertEquals(Integer.valueOf(3), itr.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionSetNullNull() {
		collectionServices.intersection((Set<?>)null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionSetSetNull() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		collectionServices.intersection(set, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionSetNullSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		collectionServices.intersection((Set<?>)null, set);
	}

	@Test
	public void testIntersectionSetSameSize() {
		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));
		set1.add(Integer.valueOf(3));

		Set<Object> set2 = new LinkedHashSet<>();
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
		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));
		set1.add(Integer.valueOf(3));
		set1.add(Integer.valueOf(4));

		Set<Object> set2 = new LinkedHashSet<>();
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
		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));
		set1.add(Integer.valueOf(3));

		Set<Object> set2 = new LinkedHashSet<>();
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
	public void testIntersectionSetIntegerDouble() {
		Set<Object> set1 = new LinkedHashSet<>();
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));
		set1.add(Integer.valueOf(3));
		set1.add(Integer.valueOf(4));

		Set<Object> set2 = new LinkedHashSet<>();
		set2.add(Double.valueOf(3));
		set2.add(Double.valueOf(5));
		set2.add(Double.valueOf(1));

		Set<Object> result = collectionServices.intersection(set1, set2);
		assertEquals(0, result.size());
	}

	@Test
	public void testIntersectionSetDifferentClasses() {
		Set<Object> set1 = new LinkedHashSet<>();
		set1.add("aString");
		set1.add(Integer.valueOf(1));
		set1.add(Integer.valueOf(2));
		set1.add("anotherString");
		set1.add("aThirdString");

		Set<String> set2 = new LinkedHashSet<>();
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

	@Test
	public void testIntersectionSetList() {
		Set<Integer> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		List<Integer> list = new ArrayList<>();
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(5));
		list.add(Integer.valueOf(1));

		Set<Integer> result = collectionServices.intersection(set, list);
		assertEquals(2, result.size());
		// make sure the result is in the order we want (order of the first set)
		Iterator<Integer> it = result.iterator();
		assertEquals(Integer.valueOf(1), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertFalse(it.hasNext());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionListNullNull() {
		collectionServices.intersection((List<?>)null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionListSetNull() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		collectionServices.intersection(list, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testIntersectionListNullSet() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));

		collectionServices.intersection((List<?>)null, list);
	}

	@Test
	public void testIntersectionListSameSize() {
		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));
		list1.add(Integer.valueOf(3));

		List<Object> list2 = new ArrayList<>();
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
		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));
		list1.add(Integer.valueOf(3));
		list1.add(Integer.valueOf(4));

		List<Object> list2 = new ArrayList<>();
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
		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));
		list1.add(Integer.valueOf(3));

		List<Object> list2 = new ArrayList<>();
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
	public void testIntersectionListIntegerDouble() {
		List<Object> list1 = new ArrayList<>();
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));
		list1.add(Integer.valueOf(3));
		list1.add(Integer.valueOf(4));

		List<Object> list2 = new ArrayList<>();
		list2.add(Double.valueOf(3));
		list2.add(Double.valueOf(5));
		list2.add(Double.valueOf(1));

		List<Object> result = collectionServices.intersection(list1, list2);
		assertEquals(0, result.size());
	}

	@Test
	public void testIntersectionListDifferentClasses() {
		List<Object> list1 = new ArrayList<>();
		list1.add("aString");
		list1.add(Integer.valueOf(1));
		list1.add(Integer.valueOf(2));
		list1.add("anotherString");
		list1.add("aThirdString");

		List<String> list2 = new ArrayList<>();
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
		List<String> list1 = new ArrayList<>();
		list1.add("a");
		list1.add("b");
		list1.add("a");
		list1.add("c");
		list1.add("b");

		List<String> list2 = new ArrayList<>();
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
		List<String> list1 = new ArrayList<>();
		list1.add("b");
		list1.add("d");
		list1.add("c");

		List<String> list2 = new ArrayList<>();
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
		List<String> list1 = new ArrayList<>();
		list1.add("a");
		list1.add("b");
		list1.add("a");
		list1.add("c");
		list1.add("b");

		List<String> list2 = new ArrayList<>();
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

	@Test
	public void testIntersectionListSet() {
		List<Integer> list = new ArrayList<>();
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(5));
		list.add(Integer.valueOf(1));

		Set<Integer> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));

		List<Integer> result = collectionServices.intersection(list, set);
		assertEquals(3, result.size());
		// make sure the result is in the order we want (order of the first list)
		Iterator<Integer> it = result.iterator();
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(1), it.next());
		assertEquals(Integer.valueOf(1), it.next());
		assertFalse(it.hasNext());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubOrderedSetNull() {
		collectionServices.subOrderedSet(null, Integer.valueOf(1), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubOrderedSetStartTooLow() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.subOrderedSet(set, Integer.valueOf(0), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubOrderedSetStartTooHigh() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.subOrderedSet(set, Integer.valueOf(5), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubOrderedSetEndTooLow() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.subOrderedSet(set, Integer.valueOf(1), Integer.valueOf(0));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubOrderedSetEndTooHigh() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.subOrderedSet(set, Integer.valueOf(1), Integer.valueOf(5));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubOrderedSetEndLowerThanStart() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.subOrderedSet(set, Integer.valueOf(3), Integer.valueOf(2));
	}

	@Test
	public void testSubOrderedSetStartEqualsEnd() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<Object> result = collectionServices.subOrderedSet(set, Integer.valueOf(3), Integer.valueOf(
				3));
		assertEquals(1, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(Integer.valueOf(3), it.next());
	}

	@Test
	public void testSubOrderedSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<Object> result = collectionServices.subOrderedSet(set, Integer.valueOf(2), Integer.valueOf(
				4));
		assertEquals(3, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(4), it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubSequenceNull() {
		collectionServices.subSequence(null, Integer.valueOf(1), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubSequenceStartTooLow() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.subSequence(list, Integer.valueOf(0), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubSequenceStartTooHigh() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.subSequence(list, Integer.valueOf(5), Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubSequenceEndTooLow() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.subSequence(list, Integer.valueOf(1), Integer.valueOf(0));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubSequenceEndTooHigh() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.subSequence(list, Integer.valueOf(1), Integer.valueOf(5));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testSubSequenceEndLowerThanStart() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.subSequence(list, Integer.valueOf(3), Integer.valueOf(2));
	}

	@Test
	public void testSubSequenceStartEqualsEnd() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		final List<Object> result = collectionServices.subSequence(list, Integer.valueOf(3), Integer.valueOf(
				3));
		assertEquals(1, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(Integer.valueOf(3), it.next());
	}

	@Test
	public void testSubSequence() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		final List<Object> result = collectionServices.subSequence(list, Integer.valueOf(2), Integer.valueOf(
				4));
		assertEquals(3, result.size());
		Iterator<Object> it = result.iterator();
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(4), it.next());
	}

	@Test(expected = NullPointerException.class)
	public void testStartsWithNullNull() {
		collectionServices.startsWith(null, null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDropRightSequenceNull() {
		collectionServices.dropRight((List<?>)null, Integer.valueOf(1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropRightSequenceIndexZero() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.dropRight((List<?>)list, Integer.valueOf(0));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropRightSequenceIndexOne() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.dropRight((List<?>)list, Integer.valueOf(0));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropRightSequenceIndexOverSize() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.dropRight((List<?>)list, Integer.valueOf(list.size() + 2));
	}

	@Test
	public void testDropRightSequenceIndexSizePlusOne() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		final List<?> result = collectionServices.dropRight((List<?>)list, Integer.valueOf(list.size() + 1));

		assertEquals(4, result.size());
		assertEquals(Integer.valueOf(1), result.get(0));
		assertEquals(Integer.valueOf(2), result.get(1));
		assertEquals(Integer.valueOf(3), result.get(2));
		assertEquals(Integer.valueOf(4), result.get(3));
	}

	@Test
	public void testDropRightSequenceIndexSize() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		final List<?> result = collectionServices.dropRight((List<?>)list, Integer.valueOf(list.size()));

		assertEquals(3, result.size());
		assertEquals(Integer.valueOf(1), result.get(0));
		assertEquals(Integer.valueOf(2), result.get(1));
		assertEquals(Integer.valueOf(3), result.get(2));
	}

	@Test
	public void testDropRightSequence() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		final List<?> result = collectionServices.dropRight((List<?>)list, Integer.valueOf(2));

		assertEquals(1, result.size());
		assertEquals(Integer.valueOf(1), result.get(0));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropRightSetIndexZero() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.dropRight((Set<?>)set, Integer.valueOf(0));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropRightSetIndexOne() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.dropRight((Set<?>)set, Integer.valueOf(0));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropRightSetIndexOverSize() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.dropRight((Set<?>)set, Integer.valueOf(set.size() + 2));
	}

	@Test
	public void testDropRightSetIndexSizePlusOne() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<?> result = collectionServices.dropRight((Set<?>)set, Integer.valueOf(set.size() + 1));

		assertEquals(4, result.size());
		final Iterator<?> it = result.iterator();
		assertEquals(Integer.valueOf(1), it.next());
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(4), it.next());
	}

	@Test
	public void testDropRightSetIndexSize() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<?> result = collectionServices.dropRight((Set<?>)set, Integer.valueOf(set.size()));

		assertEquals(3, result.size());
		final Iterator<?> it = result.iterator();
		assertEquals(Integer.valueOf(1), it.next());
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
	}

	@Test
	public void testDropRightSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<?> result = collectionServices.dropRight((Set<?>)set, Integer.valueOf(2));

		assertEquals(1, result.size());
		final Iterator<?> it = result.iterator();
		assertEquals(Integer.valueOf(1), it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDropSequenceNull() {
		collectionServices.drop((List<?>)null, Integer.valueOf(1));
	}

	@Test
	public void testDropSequenceIndexZero() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.drop((List<?>)list, Integer.valueOf(0));
	}

	@Test
	public void testDropSequenceIndexOne() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		final List<?> result = collectionServices.drop((List<?>)list, Integer.valueOf(0));

		assertEquals(4, result.size());
		assertEquals(Integer.valueOf(1), result.get(0));
		assertEquals(Integer.valueOf(2), result.get(1));
		assertEquals(Integer.valueOf(3), result.get(2));
		assertEquals(Integer.valueOf(4), result.get(3));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropSequenceIndexOverSize() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.drop((List<?>)list, Integer.valueOf(list.size() + 2));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropSequenceIndexSizePlusOne() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.drop((List<?>)list, Integer.valueOf(list.size() + 1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropSequenceIndexSize() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		collectionServices.drop((List<?>)list, Integer.valueOf(list.size()));
	}

	@Test
	public void testDropSequence() {
		List<Object> list = new ArrayList<>();
		list.add(Integer.valueOf(1));
		list.add(Integer.valueOf(2));
		list.add(Integer.valueOf(3));
		list.add(Integer.valueOf(4));

		final List<?> result = collectionServices.drop((List<?>)list, Integer.valueOf(2));

		assertEquals(2, result.size());
		assertEquals(Integer.valueOf(3), result.get(0));
		assertEquals(Integer.valueOf(4), result.get(1));
	}

	@Test
	public void testDropSetIndexZero() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<?> result = collectionServices.drop((Set<?>)set, Integer.valueOf(0));

		assertEquals(4, result.size());
		final Iterator<?> it = result.iterator();
		assertEquals(Integer.valueOf(1), it.next());
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(4), it.next());
	}

	@Test
	public void testDropSetIndexOne() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<?> result = collectionServices.drop((Set<?>)set, Integer.valueOf(0));

		assertEquals(4, result.size());
		final Iterator<?> it = result.iterator();
		assertEquals(Integer.valueOf(1), it.next());
		assertEquals(Integer.valueOf(2), it.next());
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(4), it.next());
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropSetIndexOverSize() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.drop((Set<?>)set, Integer.valueOf(set.size() + 2));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropSetIndexSizePlusOne() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.drop((Set<?>)set, Integer.valueOf(set.size() + 1));
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void testDropSetIndexSize() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		collectionServices.drop((Set<?>)set, Integer.valueOf(set.size()));
	}

	@Test
	public void testDropSet() {
		Set<Object> set = new LinkedHashSet<>();
		set.add(Integer.valueOf(1));
		set.add(Integer.valueOf(2));
		set.add(Integer.valueOf(3));
		set.add(Integer.valueOf(4));

		final Set<?> result = collectionServices.drop((Set<?>)set, Integer.valueOf(2));

		assertEquals(2, result.size());
		final Iterator<?> it = result.iterator();
		assertEquals(Integer.valueOf(3), it.next());
		assertEquals(Integer.valueOf(4), it.next());
	}

	@Test
	public void testStartsWithSequenceSequence() {
		final List<Object> collection = new ArrayList<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final List<Object> other = new ArrayList<>();
		other.add(Integer.valueOf(1));
		other.add(Integer.valueOf(2));

		final Boolean result = collectionServices.startsWith(collection, other);

		assertTrue(result);
	}

	@Test
	public void testStartsWithOrederedSetSequence() {
		final Set<Object> collection = new LinkedHashSet<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final List<Object> other = new ArrayList<>();
		other.add(Integer.valueOf(1));
		other.add(Integer.valueOf(2));

		final Boolean result = collectionServices.startsWith(collection, other);

		assertTrue(result);
	}

	@Test
	public void testStartsWithSequenceOrderedSet() {
		final List<Object> collection = new ArrayList<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final Set<Object> other = new LinkedHashSet<>();
		other.add(Integer.valueOf(1));
		other.add(Integer.valueOf(2));

		final Boolean result = collectionServices.startsWith(collection, other);

		assertTrue(result);
	}

	@Test
	public void testStartsWithOrderedSetOrderedSet() {
		final Set<Object> collection = new LinkedHashSet<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final Set<Object> other = new LinkedHashSet<>();
		other.add(Integer.valueOf(1));
		other.add(Integer.valueOf(2));

		final Boolean result = collectionServices.startsWith(collection, other);

		assertTrue(result);
	}

	@Test(expected = NullPointerException.class)
	public void testendsWithNullNull() {
		collectionServices.endsWith(null, null);
	}

	@Test
	public void testendsWithSequenceSequence() {
		final List<Object> collection = new ArrayList<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final List<Object> other = new ArrayList<>();
		other.add(Integer.valueOf(3));
		other.add(Integer.valueOf(4));

		final Boolean result = collectionServices.endsWith(collection, other);

		assertTrue(result);
	}

	@Test
	public void testendsWithOrederedSetSequence() {
		final Set<Object> collection = new LinkedHashSet<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final List<Object> other = new ArrayList<>();
		other.add(Integer.valueOf(3));
		other.add(Integer.valueOf(4));

		final Boolean result = collectionServices.endsWith(collection, other);

		assertTrue(result);
	}

	@Test
	public void testendsWithSequenceOrderedSet() {
		final List<Object> collection = new ArrayList<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final Set<Object> other = new LinkedHashSet<>();
		other.add(Integer.valueOf(3));
		other.add(Integer.valueOf(4));

		final Boolean result = collectionServices.endsWith(collection, other);

		assertTrue(result);
	}

	@Test
	public void testendsWithOrderedSetOrderedSet() {
		final Set<Object> collection = new LinkedHashSet<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final Set<Object> other = new LinkedHashSet<>();
		other.add(Integer.valueOf(3));
		other.add(Integer.valueOf(4));

		final Boolean result = collectionServices.endsWith(collection, other);

		assertTrue(result);
	}

	@Test(expected = NullPointerException.class)
	public void testIndexOfSliceNullNull() {
		collectionServices.indexOfSlice(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void testIndexOfSliceListNull() {
		final List<Object> collection = new ArrayList<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		collectionServices.indexOfSlice(collection, null);
	}

	@Test
	public void testIndexOfSliceListList() {
		final List<Object> collection = new ArrayList<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final List<Object> other = new ArrayList<>();
		other.add(Integer.valueOf(2));
		other.add(Integer.valueOf(3));

		Integer result = collectionServices.indexOfSlice(collection, other);

		assertEquals(Integer.valueOf(2), result);

		other.clear();
		other.add(Integer.valueOf(1));
		other.add(Integer.valueOf(2));

		result = collectionServices.indexOfSlice(collection, other);

		assertEquals(Integer.valueOf(1), result);

		other.clear();
		other.add(Integer.valueOf(3));
		other.add(Integer.valueOf(4));

		result = collectionServices.indexOfSlice(collection, other);

		assertEquals(Integer.valueOf(3), result);
	}

	@Test(expected = NullPointerException.class)
	public void testLastIndexOfSliceNullNull() {
		collectionServices.lastIndexOfSlice(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void testLastIndexOfSliceListNull() {
		final List<Object> collection = new ArrayList<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		collectionServices.lastIndexOfSlice(collection, null);
	}

	@Test
	public void testLastIndexOfSliceListList() {
		final List<Object> collection = new ArrayList<>();
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));
		collection.add(Integer.valueOf(1));
		collection.add(Integer.valueOf(2));
		collection.add(Integer.valueOf(3));
		collection.add(Integer.valueOf(4));

		final List<Object> other = new ArrayList<>();
		other.add(Integer.valueOf(2));
		other.add(Integer.valueOf(3));

		Integer result = collectionServices.lastIndexOfSlice(collection, other);

		assertEquals(Integer.valueOf(6), result);

		other.clear();
		other.add(Integer.valueOf(1));
		other.add(Integer.valueOf(2));

		result = collectionServices.lastIndexOfSlice(collection, other);

		assertEquals(Integer.valueOf(5), result);

		other.clear();
		other.add(Integer.valueOf(3));
		other.add(Integer.valueOf(4));

		result = collectionServices.lastIndexOfSlice(collection, other);

		assertEquals(Integer.valueOf(7), result);
	}

}
