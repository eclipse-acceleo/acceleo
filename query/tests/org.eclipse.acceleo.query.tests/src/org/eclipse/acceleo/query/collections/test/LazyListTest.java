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
package org.eclipse.acceleo.query.collections.test;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.query.collections.LazyList;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author rguider
 */
@Ignore
public class LazyListTest {

	private static final List<String> testList = Lists.newArrayList("elt0", "elt1", "elt2", "elt3", "elt4",
			"elt5", "elt6", "elt7", "elt8", "elt9", "elt10", "elt11", "elt12", "elt13", "elt14", "elt15",
			"elt16", "elt17", "elt18", "elt19");

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#LazyList(java.util.Iterator)} .
	 */
	@Test
	public void testLazyList() {
		LazyList<String> list = new LazyList<String>(Lists.newArrayList("elt1", "elt2"));
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#add(java.lang.Object)} .
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testAddE() {
		new LazyList<String>(Lists.newArrayList("elt1", "elt2")).add("I will never get :-{");
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#add(int, java.lang.Object)} .
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testAddIntE() {
		new LazyList<String>(Lists.newArrayList("elt1", "elt2")).add(1, "I will never get :-{");
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#addAll(java.util.Collection)} .
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testAddAllCollectionOfQextendsE() {
		new LazyList<String>(Lists.newArrayList("elt1", "elt2")).addAll(Lists.newArrayList(
				"i will never get in :-{", "i will never get in :-{ either"));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.acceleo.query.collections.LazyList#addAll(int, java.util.Collection)} .
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testAddAllIntCollectionOfQextendsE() {
		new LazyList<String>(Lists.newArrayList("elt1", "elt2")).addAll(1, Lists.newArrayList(
				"i will never get in :-{", "i will never get in :-{ either"));
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#clear()}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testClear() {
		new LazyList<String>(Lists.newArrayList("elt1", "elt2")).clear();
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#contains(java.lang.Object)} .
	 */
	@Test
	public void testContains() {
		assertTrue(new LazyList<String>(testList, 5).contains("elt1"));
		assertFalse(new LazyList<String>(testList, 5).contains("elt30"));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.acceleo.query.collections.LazyList#containsAll(java.util.Collection)} .
	 */
	@Test
	public void testContainsAll() {
		assertTrue(new LazyList<String>(testList, 5).containsAll(Lists.newArrayList("elt1", "elt2")));
		assertFalse(new LazyList<String>(testList, 5).containsAll(Lists.newArrayList("elt1", "elt32")));
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#get(int)}.
	 */
	@Test
	public void testGet() {
		assertEquals("elt1", new LazyList<String>(testList, 5).get(1));
		assertEquals("elt12", new LazyList<String>(testList, 5).get(12));

	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#indexOf(java.lang.Object)} .
	 */
	@Test
	public void testIndexOf() {
		assertEquals(12, new LazyList<String>(testList, 5).indexOf("elt12"));
		assertEquals(0, new LazyList<String>(testList, 5).indexOf("elt0"));
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(new LazyList<String>(new ArrayList<String>()).isEmpty());
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#iterator()}.
	 */
	@Test
	public void testIterator() {
		LazyList<String> list = new LazyList<String>(testList, 5);
		list.get(13);
		list.get(16);
		Iterator<String> iterator = list.iterator();
		assertTrue(iterator.hasNext());
		assertEquals("elt0", iterator.next());
		assertEquals("elt1", iterator.next());
		assertEquals("elt2", iterator.next());
		assertEquals("elt3", iterator.next());
		assertEquals("elt4", iterator.next());
		assertEquals("elt5", iterator.next());
		assertEquals("elt6", iterator.next());
		assertEquals("elt7", iterator.next());
		assertEquals("elt8", iterator.next());
		assertEquals("elt9", iterator.next());
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		assertEquals("elt14", iterator.next());
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		assertEquals("elt19", iterator.next());
		assertFalse(iterator.hasNext());
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#lastIndexOf(java.lang.Object)} .
	 */
	@Test
	public void testLastIndexOf() {
		assertEquals(12, new LazyList<String>(testList).lastIndexOf("elt12"));
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#listIterator()}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testListIterator() {
		new LazyList<String>(testList).listIterator();
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#listIterator(int)}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testListIteratorInt() {
		new LazyList<String>(testList).listIterator(10);
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#remove(java.lang.Object)} .
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveObject() {
		new LazyList<String>(testList).remove("elt3");
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#remove(int)}.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveInt() {
		new LazyList<String>(testList).remove(3);
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#removeAll(java.util.Collection)}
	 * .
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveAll() {
		new LazyList<String>(testList).removeAll(Lists.newArrayList("elt1", "elt2"));
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#retainAll(java.util.Collection)}
	 * .
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testRetainAll() {
		new LazyList<String>(testList).retainAll(Lists.newArrayList("elt1", "elt2"));
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#set(int, java.lang.Object)} .
	 */
	@Test(expected = UnsupportedOperationException.class)
	public void testSet() {
		new LazyList<String>(testList).set(3, "");
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#size()}.
	 */
	@Test
	public void testSize() {
		assertEquals(20, new LazyList<String>(testList).size());
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#subList(int, int)}.
	 */
	@Test
	public void testSubList() {
		List<String> subList = new LazyList<String>(testList, 5).subList(5, 15);
		assertEquals(10, subList.size());
		assertEquals("elt5", subList.get(0));
		assertEquals("elt14", subList.get(subList.size() - 1));
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#toArray()}.
	 */
	@Test
	public void testToArray() {
		Object[] stringArray = new LazyList<String>(testList, 5).toArray();
		assertEquals("elt0", stringArray[0]);
		assertEquals("elt1", stringArray[1]);
		assertEquals("elt2", stringArray[2]);
		assertEquals("elt4", stringArray[4]);
		assertEquals("elt5", stringArray[5]);
		assertEquals("elt12", stringArray[12]);
		assertEquals("elt13", stringArray[13]);
		assertEquals("elt15", stringArray[15]);
	}

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#toArray(T[])}.
	 */
	@Test
	public void testToArrayTArray() {
		String[] stringArray = new String[20];
		new LazyList<String>(testList, 5).toArray(stringArray);
		assertEquals("elt0", stringArray[0]);
		assertEquals("elt1", stringArray[1]);
		assertEquals("elt2", stringArray[2]);
		assertEquals("elt4", stringArray[4]);
		assertEquals("elt5", stringArray[5]);
		assertEquals("elt12", stringArray[12]);
		assertEquals("elt13", stringArray[13]);
		assertEquals("elt15", stringArray[15]);
	}

}
