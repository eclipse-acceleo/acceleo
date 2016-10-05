/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.tests.unit.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CircularArrayDeque;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.Deque;
import org.junit.Test;

/**
 * Tests for the {@link CompactHashSet} behavior.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CompactHashSetTest {
	/**
	 * This will test the behavior of {@link CompactHashSet#add(Object)} with random elements.
	 */
	@Test
	public void testAdd() {
		Collection<Integer> listInt10 = randomIntegerListNotIn(10, Collections.<Integer> emptySet());
		Collection<String> setString20 = randomStringSetNotIn(20, Collections.<String> emptySet());
		Collection<String> dequeString40 = randomStringDequeNotIn(40, setString20);
		Collection<Object> duplicatesList = new ArrayList<Object>();
		for (int i = 0; i < 40; i++) {
			// our random integers and strings are values from 0-100000.
			// Make sure we cannot have a conflict
			int dupe = ((i / 2) + 1) * 100001;
			duplicatesList.add(Integer.valueOf(dupe));
			duplicatesList.add(String.valueOf(dupe));
		}
		duplicatesList.add(null);
		duplicatesList.add(null);

		Set<Object> set = createSet();

		int size = 0;
		for (Integer integer : listInt10) {
			boolean modified = set.add(integer);
			assertTrue(modified);
			assertTrue(set.contains(integer));
			assertEquals(++size, set.size());
		}
		assertTrue(set.containsAll(listInt10));
		assertFalse(set.contains(null));

		for (String string : setString20) {
			boolean modified = set.add(string);
			assertTrue(modified);
			assertTrue(set.contains(string));
			assertEquals(++size, set.size());
		}
		assertTrue(set.containsAll(setString20));
		assertFalse(set.contains(null));

		for (String string : dequeString40) {
			boolean modified = set.add(string);
			assertTrue(modified);
			assertTrue(set.contains(string));
			assertEquals(++size, set.size());
		}
		assertTrue(set.containsAll(dequeString40));
		assertFalse(set.contains(null));

		for (Object o : duplicatesList) {
			if (set.contains(o)) {
				boolean modified = set.add(o);
				assertFalse(modified);
				assertTrue(set.contains(o));
				assertEquals(size, set.size());
			} else {
				boolean modified = set.add(o);
				assertTrue(modified);
				assertTrue(set.contains(o));
				assertEquals(++size, set.size());
			}
		}
		assertTrue(set.containsAll(duplicatesList));
		assertTrue(set.contains(null));

		assertTrue(set.containsAll(listInt10));
		assertTrue(set.containsAll(setString20));
		assertTrue(set.containsAll(dequeString40));
		assertTrue(set.containsAll(duplicatesList));
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size()
				+ (duplicatesList.size() / 2), set.size());
	}

	/**
	 * This will test the behavior of {@link CompactHashSet#addAll(Collection)} with random elements.
	 */
	@Test
	public void testAddAll() {
		Collection<Integer> listInt10 = randomIntegerListNotIn(10, Collections.<Integer> emptySet());
		Collection<String> setString20 = randomStringSetNotIn(20, Collections.<String> emptySet());
		Collection<String> dequeString40 = randomStringDequeNotIn(40, setString20);
		Collection<Object> duplicatesList = new ArrayList<Object>();
		for (int i = 0; i < 40; i++) {
			// our random integers and strings are values from 0-100000.
			// Make sure we cannot have a conflict
			int dupe = ((i / 2) + 1) * 100001;
			duplicatesList.add(Integer.valueOf(dupe));
			duplicatesList.add(String.valueOf(dupe));
		}
		duplicatesList.add(null);
		duplicatesList.add(null);

		Set<Object> set = createSet();

		boolean modified = set.addAll(listInt10);
		int expectedCapacity = 16;
		assertTrue(modified);
		assertEquals(listInt10.size(), set.size());
		assertTrue(set.containsAll(listInt10));
		assertFalse(set.containsAll(setString20));
		assertFalse(set.containsAll(dequeString40));
		assertFalse(set.containsAll(duplicatesList));
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		modified = set.addAll(setString20);
		expectedCapacity = 64;
		assertTrue(modified);
		assertEquals(listInt10.size() + setString20.size(), set.size());
		assertTrue(set.containsAll(listInt10));
		assertTrue(set.containsAll(setString20));
		assertFalse(set.containsAll(dequeString40));
		assertFalse(set.containsAll(duplicatesList));
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		modified = set.addAll(dequeString40);
		expectedCapacity = 128;
		assertTrue(modified);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), set.size());
		assertTrue(set.containsAll(listInt10));
		assertTrue(set.containsAll(setString20));
		assertTrue(set.containsAll(dequeString40));
		assertFalse(set.containsAll(duplicatesList));
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		modified = set.addAll(duplicatesList);
		expectedCapacity = 256;
		assertTrue(modified);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size()
				+ (duplicatesList.size() / 2), set.size());
		assertTrue(set.containsAll(listInt10));
		assertTrue(set.containsAll(setString20));
		assertTrue(set.containsAll(dequeString40));
		assertTrue(set.containsAll(duplicatesList));
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertTrue(set.contains(o));
		}
		assertTrue(set.contains(null));

		set.removeAll(setString20);
		assertEquals(listInt10.size() + dequeString40.size() + (duplicatesList.size() / 2), set.size());
		assertTrue(set.containsAll(listInt10));
		assertFalse(set.containsAll(setString20));
		assertTrue(set.containsAll(dequeString40));
		assertTrue(set.containsAll(duplicatesList));
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertTrue(set.contains(o));
		}
		assertTrue(set.contains(null));

		set.addAll(setString20);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size()
				+ (duplicatesList.size() / 2), set.size());
		assertTrue(set.containsAll(listInt10));
		assertTrue(set.containsAll(setString20));
		assertTrue(set.containsAll(dequeString40));
		assertTrue(set.containsAll(duplicatesList));
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertTrue(set.contains(o));
		}
		assertTrue(set.contains(null));
	}

	/**
	 * This will test the behavior of {@link CompactHashSet#clear()} with random elements.
	 */
	@Test
	public void testClear() {
		Collection<Integer> listInt10 = randomIntegerListNotIn(10, Collections.<Integer> emptySet());
		Collection<String> setString20 = randomStringSetNotIn(20, Collections.<String> emptySet());
		Collection<String> dequeString40 = randomStringDequeNotIn(40, setString20);
		Collection<Object> duplicatesList = new ArrayList<Object>();
		for (int i = 0; i < 40; i++) {
			// our random integers and strings are values from 0-100000.
			// Make sure we cannot have a conflict
			int dupe = ((i / 2) + 1) * 100001;
			duplicatesList.add(Integer.valueOf(dupe));
			duplicatesList.add(String.valueOf(dupe));
		}
		duplicatesList.add(null);
		duplicatesList.add(null);

		Set<Object> set = createSet();

		int expectedCapacity = 16;
		set.clear();
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertFalse(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.addAll(listInt10);
		set.clear();
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertFalse(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.addAll(listInt10);
		set.addAll(setString20);
		expectedCapacity = 64;
		set.clear();
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertFalse(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.addAll(listInt10);
		set.addAll(setString20);
		set.addAll(dequeString40);
		expectedCapacity = 128;
		set.clear();
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertFalse(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.addAll(listInt10);
		set.addAll(setString20);
		set.addAll(dequeString40);
		set.addAll(duplicatesList);
		expectedCapacity = 256;
		set.clear();
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertFalse(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.clear();
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertEquals(expectedCapacity, getInternalCapacity(set));
		for (Integer integer : listInt10) {
			assertFalse(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));
	}

	/**
	 * This will test the behavior of {@link CompactHashSet#contains(Object)} with random elements.
	 */
	@Test
	public void testContains() {
		Collection<Integer> listInt10 = randomIntegerListNotIn(10, Collections.<Integer> emptySet());
		Collection<String> setString20 = randomStringSetNotIn(20, Collections.<String> emptySet());
		Collection<String> dequeString40 = randomStringDequeNotIn(40, setString20);
		Collection<Object> duplicatesList = new ArrayList<Object>();
		for (int i = 0; i < 40; i++) {
			// our random integers and strings are values from 0-100000.
			// Make sure we cannot have a conflict
			int dupe = ((i / 2) + 1) * 100001;
			duplicatesList.add(Integer.valueOf(dupe));
			duplicatesList.add(String.valueOf(dupe));
		}
		duplicatesList.add(null);
		duplicatesList.add(null);

		Set<Object> set = createSet();

		for (Integer integer : listInt10) {
			assertFalse(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.addAll(listInt10);
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.addAll(setString20);
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.addAll(dequeString40);
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.addAll(duplicatesList);
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertTrue(set.contains(o));
		}
		assertTrue(set.contains(null));

		set.removeAll(setString20);
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertTrue(set.contains(o));
		}
		assertTrue(set.contains(null));

		set.clear();
		for (Integer integer : listInt10) {
			assertFalse(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));

		set.addAll(listInt10);
		set.addAll(setString20);
		set.addAll(dequeString40);
		set.addAll(duplicatesList);
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertTrue(set.contains(o));
		}
		assertTrue(set.contains(null));

		set.removeAll(listInt10);
		set.removeAll(setString20);
		set.removeAll(dequeString40);
		set.removeAll(duplicatesList);
		for (Integer integer : listInt10) {
			assertFalse(set.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(set.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(set.contains(string));
		}
		for (Object o : duplicatesList) {
			assertFalse(set.contains(o));
		}
		assertFalse(set.contains(null));
	}

	/**
	 * This will test the behavior of {@link CompactHashSet#containsAll(Object)} with random elements.
	 */
	@Test
	public void testContainsAll() {
		Collection<Integer> listInt10 = randomIntegerListNotIn(10, Collections.<Integer> emptySet());
		Collection<String> setString20 = randomStringSetNotIn(20, Collections.<String> emptySet());
		Collection<String> dequeString40 = randomStringDequeNotIn(40, setString20);
		Collection<Object> duplicatesList = new ArrayList<Object>();
		for (int i = 0; i < 40; i++) {
			// our random integers and strings are values from 0-100000.
			// Make sure we cannot have a conflict
			int dupe = ((i / 2) + 1) * 100001;
			duplicatesList.add(Integer.valueOf(dupe));
			duplicatesList.add(String.valueOf(dupe));
		}
		duplicatesList.add(null);
		duplicatesList.add(null);

		Set<Object> set = createSet();

		assertFalse(set.containsAll(listInt10));
		assertFalse(set.containsAll(setString20));
		assertFalse(set.containsAll(dequeString40));
		assertFalse(set.containsAll(duplicatesList));

		set.addAll(listInt10);
		assertTrue(set.containsAll(listInt10));
		assertFalse(set.containsAll(setString20));
		assertFalse(set.containsAll(dequeString40));
		assertFalse(set.containsAll(duplicatesList));

		set.addAll(setString20);
		assertTrue(set.containsAll(listInt10));
		assertTrue(set.containsAll(setString20));
		assertFalse(set.containsAll(dequeString40));
		assertFalse(set.containsAll(duplicatesList));

		set.addAll(dequeString40);
		assertTrue(set.containsAll(listInt10));
		assertTrue(set.containsAll(setString20));
		assertTrue(set.containsAll(dequeString40));
		assertFalse(set.containsAll(duplicatesList));

		set.addAll(duplicatesList);
		assertTrue(set.containsAll(listInt10));
		assertTrue(set.containsAll(setString20));
		assertTrue(set.containsAll(dequeString40));
		assertTrue(set.containsAll(duplicatesList));

		set.removeAll(setString20);
		assertTrue(set.containsAll(listInt10));
		assertFalse(set.containsAll(setString20));
		assertTrue(set.containsAll(dequeString40));
		assertTrue(set.containsAll(duplicatesList));

		set.clear();
		assertFalse(set.containsAll(listInt10));
		assertFalse(set.containsAll(setString20));
		assertFalse(set.containsAll(dequeString40));
		assertFalse(set.containsAll(duplicatesList));

		set.addAll(listInt10);
		set.addAll(setString20);
		set.addAll(dequeString40);
		set.addAll(duplicatesList);
		assertTrue(set.containsAll(listInt10));
		assertTrue(set.containsAll(setString20));
		assertTrue(set.containsAll(dequeString40));
		assertTrue(set.containsAll(duplicatesList));

		set.removeAll(listInt10);
		set.removeAll(setString20);
		set.removeAll(dequeString40);
		set.removeAll(duplicatesList);
		assertFalse(set.containsAll(listInt10));
		assertFalse(set.containsAll(setString20));
		assertFalse(set.containsAll(dequeString40));
		assertFalse(set.containsAll(duplicatesList));
	}

	/**
	 * Tests the behavior of {@link CompactHashSet#equals(Object)} with random elements.
	 */
	@Test
	public void testEquals() {
		Set<Object> set1 = createSet();
		Set<Object> set2 = createSet();

		Collection<Object> objects1 = new ArrayList<Object>();
		objects1.add(null);
		objects1.addAll(randomStringDequeNotIn(40, Collections.<String> emptySet()));
		objects1.add(null);
		assertSame(42, objects1.size());

		assertTrue(set1.equals(set2));
		assertTrue(set2.equals(set1));

		set1.addAll(objects1);
		set2.addAll(objects1);

		Set<Object> set3 = createSet(set1);

		assertTrue(set1.equals(set2));
		assertTrue(set1.equals(set3));
		assertTrue(set2.equals(set1));
		assertTrue(set2.equals(set3));
		assertTrue(set3.equals(set1));
		assertTrue(set3.equals(set2));

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			while (set1.contains(rand)) {
				rand = getRandomString();
			}
			set1.add(rand);
			set2.add(rand);
		}
		set1.add(null);
		set2.add(null);
		assertTrue(set1.equals(set2));
		assertFalse(set1.equals(set3));
		assertTrue(set2.equals(set1));
		assertFalse(set2.equals(set3));
		assertFalse(set3.equals(set1));
		assertFalse(set3.equals(set2));

		set1.remove(null);
		assertFalse(set1.equals(set2));
		assertFalse(set1.equals(set3));
		assertFalse(set2.equals(set1));
		assertFalse(set2.equals(set3));
		assertFalse(set3.equals(set1));
		assertFalse(set3.equals(set2));

		set1.add(null);
		assertTrue(set1.equals(set2));
		assertFalse(set1.equals(set3));
		assertTrue(set2.equals(set1));
		assertFalse(set2.equals(set3));
		assertFalse(set3.equals(set1));
		assertFalse(set3.equals(set2));

		set1.remove(null);
		set2.remove(null);
		assertTrue(set1.equals(set2));
		assertFalse(set1.equals(set3));
		assertTrue(set2.equals(set1));
		assertFalse(set2.equals(set3));
		assertFalse(set3.equals(set1));
		assertFalse(set3.equals(set2));

		set1.clear();
		set2.clear();
		assertTrue(set1.equals(set2));
		assertFalse(set1.equals(set3));
		assertTrue(set2.equals(set1));
		assertFalse(set2.equals(set3));
		assertFalse(set3.equals(set1));
		assertFalse(set3.equals(set2));

		set1.addAll(objects1);
		set2.addAll(objects1);
		assertTrue(set1.equals(set2));
		assertTrue(set1.equals(set3));
		assertTrue(set2.equals(set1));
		assertTrue(set2.equals(set3));
		assertTrue(set3.equals(set1));
		assertTrue(set3.equals(set2));

		set1.clear();
		set2.clear();
		set3.clear();
		assertTrue(set1.equals(set2));
		assertTrue(set1.equals(set3));
		assertTrue(set2.equals(set1));
		assertTrue(set2.equals(set3));
		assertTrue(set3.equals(set1));
		assertTrue(set3.equals(set2));
	}

	/**
	 * Tests the behavior of {@link CompactHashSet#hashCode()} with random elements.
	 */
	@Test
	public void testHashCode() {
		Set<Object> set1 = createSet();
		Set<Object> set2 = createSet();

		Collection<Object> objects1 = new ArrayList<Object>();
		objects1.add(null);
		objects1.addAll(randomStringDequeNotIn(40, Collections.<String> emptySet()));
		objects1.add(null);
		assertSame(42, objects1.size());

		assertTrue(set1.hashCode() == set2.hashCode());
		assertTrue(set2.hashCode() == set1.hashCode());

		set1.addAll(objects1);
		set2.addAll(objects1);

		Set<Object> set3 = createSet(set1);

		assertTrue(set1.hashCode() == set2.hashCode());
		assertTrue(set1.hashCode() == set3.hashCode());
		assertTrue(set2.hashCode() == set1.hashCode());
		assertTrue(set2.hashCode() == set3.hashCode());
		assertTrue(set3.hashCode() == set1.hashCode());
		assertTrue(set3.hashCode() == set2.hashCode());

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			while (set1.contains(rand)) {
				rand = getRandomString();
			}
			set1.add(rand);
			set2.add(rand);
		}
		set1.add(null);
		set2.add(null);
		assertTrue(set1.hashCode() == set2.hashCode());
		assertFalse(set1.hashCode() == set3.hashCode());
		assertTrue(set2.hashCode() == set1.hashCode());
		assertFalse(set2.hashCode() == set3.hashCode());
		assertFalse(set3.hashCode() == set1.hashCode());
		assertFalse(set3.hashCode() == set2.hashCode());

		set1.remove(null);
		assertTrue(set1.hashCode() == set2.hashCode());
		assertFalse(set1.hashCode() == set3.hashCode());
		assertTrue(set2.hashCode() == set1.hashCode());
		assertFalse(set2.hashCode() == set3.hashCode());
		assertFalse(set3.hashCode() == set1.hashCode());
		assertFalse(set3.hashCode() == set2.hashCode());

		set1.add(null);
		assertTrue(set1.hashCode() == set2.hashCode());
		assertFalse(set1.hashCode() == set3.hashCode());
		assertTrue(set2.hashCode() == set1.hashCode());
		assertFalse(set2.hashCode() == set3.hashCode());
		assertFalse(set3.hashCode() == set1.hashCode());
		assertFalse(set3.hashCode() == set2.hashCode());

		set1.remove(null);
		set2.remove(null);
		assertTrue(set1.hashCode() == set2.hashCode());
		assertFalse(set1.hashCode() == set3.hashCode());
		assertTrue(set2.hashCode() == set1.hashCode());
		assertFalse(set2.hashCode() == set3.hashCode());
		assertFalse(set3.hashCode() == set1.hashCode());
		assertFalse(set3.hashCode() == set2.hashCode());

		set1.clear();
		set2.clear();
		assertTrue(set1.hashCode() == set2.hashCode());
		assertFalse(set1.hashCode() == set3.hashCode());
		assertTrue(set2.hashCode() == set1.hashCode());
		assertFalse(set2.hashCode() == set3.hashCode());
		assertFalse(set3.hashCode() == set1.hashCode());
		assertFalse(set3.hashCode() == set2.hashCode());

		set1.addAll(objects1);
		set2.addAll(objects1);
		assertTrue(set1.hashCode() == set2.hashCode());
		assertTrue(set1.hashCode() == set3.hashCode());
		assertTrue(set2.hashCode() == set1.hashCode());
		assertTrue(set2.hashCode() == set3.hashCode());
		assertTrue(set3.hashCode() == set1.hashCode());
		assertTrue(set3.hashCode() == set2.hashCode());

		set1.clear();
		set2.clear();
		set3.clear();
		assertTrue(set1.hashCode() == set2.hashCode());
		assertTrue(set1.hashCode() == set3.hashCode());
		assertTrue(set2.hashCode() == set1.hashCode());
		assertTrue(set2.hashCode() == set3.hashCode());
		assertTrue(set3.hashCode() == set1.hashCode());
		assertTrue(set3.hashCode() == set2.hashCode());
	}

	/**
	 * Tests the copy constructor of our set.
	 */
	@Test
	public void testInstantiationCopy() {
		Collection<Integer> listInt10 = randomIntegerListNotIn(10, Collections.<Integer> emptySet());
		Collection<String> setString20 = randomStringSetNotIn(20, Collections.<String> emptySet());
		Collection<String> dequeString40 = randomStringDequeNotIn(40, setString20);
		Collection<Object> duplicatesList = new ArrayList<Object>();
		for (int i = 0; i < 40; i++) {
			int dupe = i / 2;
			duplicatesList.add(Integer.valueOf(dupe));
			duplicatesList.add(String.valueOf(dupe));
		}
		duplicatesList.add(null);
		duplicatesList.add(null);

		Set<Object> set = createSet(listInt10);
		assertFalse(set.isEmpty());
		assertSame(listInt10.size(), set.size());
		assertSame(getNextPowerOfTwo(listInt10.size()), getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);
		assertTrue(set.containsAll(listInt10));
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}

		set = createSet(setString20);
		assertFalse(set.isEmpty());
		assertSame(setString20.size(), set.size());
		assertSame(getNextPowerOfTwo(setString20.size()), getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);
		assertTrue(set.containsAll(setString20));
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}

		set = createSet(dequeString40);
		assertFalse(set.isEmpty());
		assertSame(dequeString40.size(), set.size());
		assertSame(getNextPowerOfTwo(dequeString40.size()), getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);
		assertTrue(set.containsAll(dequeString40));
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}

		set = createSet(duplicatesList);
		final int expectedSize = duplicatesList.size() / 2;
		assertFalse(set.isEmpty());
		assertSame(expectedSize, set.size());
		assertEquals(getNextPowerOfTwo(duplicatesList.size()), getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);
		assertTrue(set.containsAll(duplicatesList));
		for (Object o : duplicatesList) {
			assertTrue(set.contains(o));
		}
		assertTrue(set.contains(null));
	}

	/**
	 * Tests the empty set constructor.
	 */
	@Test
	public void testInstantiationEmpty() {
		Set<Object> set = createSet();
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(16, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);
	}

	/**
	 * Tests the {@link CompactHashSet#CompactHashSet(int, float)} constructor of our set.
	 */
	@Test
	public void testInstantiationLoadFactor() {
		int[] validCapacities = new int[] {10, 0, (1 << 10) - 1, 1 << 31, -10 };
		float[] validLoadFactors = new float[] {0.000001f, 0.5f, 0.75f, 1f, Float.MIN_VALUE };
		int[] invalidCapacities = new int[] {Integer.MAX_VALUE, (1 << 30) + 1 };
		float[] invalidLoadFactors = new float[] {0f, -1f, 1.000001f, 5f, Float.NEGATIVE_INFINITY,
				Float.POSITIVE_INFINITY, Float.NaN, Float.MAX_VALUE };

		for (int capacity : validCapacities) {
			for (float loadFactor : validLoadFactors) {
				Set<Object> set = createSet(capacity, loadFactor);
				assertTrue(set.isEmpty());
				assertSame(0, set.size());
				assertEquals(Math.max(4, getNextPowerOfTwo(capacity)), getInternalCapacity(set));
				assertEquals(loadFactor, getInternalLoadFactor(set), 0.001);
			}
			for (float loadFactor : invalidLoadFactors) {
				try {
					createSet(capacity, loadFactor);
					if (loadFactor <= 0f || loadFactor > 1f || Float.isNaN(loadFactor)) {
						fail("Expected IllegalArgumentException hasn't been thrown"); //$NON-NLS-1$
					} else {
						fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
					}
				} catch (IndexOutOfBoundsException e) {
					// Expected
				} catch (IllegalArgumentException e) {
					// Expected
				}
			}
		}

		for (int capacity : invalidCapacities) {
			for (float loadFactor : validLoadFactors) {
				try {
					createSet(capacity, loadFactor);
					fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
				} catch (IndexOutOfBoundsException e) {
					// Expected
				}
			}
			for (float loadFactor : invalidLoadFactors) {
				try {
					createSet(capacity, loadFactor);
					if (loadFactor <= 0f || loadFactor > 1f || Float.isNaN(loadFactor)) {
						fail("Expected IllegalArgumentException hasn't been thrown"); //$NON-NLS-1$
					} else {
						fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
					}
				} catch (IndexOutOfBoundsException e) {
					// Expected
				} catch (IllegalArgumentException e) {
					// Expected
				}
			}
		}
	}

	/**
	 * Tests the {@link CompactHashSet#CompactHashSet(int)} constructor of our set.
	 */
	@Test
	public void testInstantiationSize() {
		Set<Object> set = createSet(10);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(16, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);

		set = createSet(0);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(4, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);

		set = createSet((1 << 10) - 1);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertEquals(1 << 10, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);

		set = createSet(1 << 31);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(4, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);

		set = createSet(-10);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(4, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set), 0.001);

		try {
			set = createSet(Integer.MAX_VALUE);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}
		try {
			/*
			 * The last possible size for our internal array is 2^30, trying to hold that much elements will
			 * yield a size of -1, which is invalid.
			 */
			set = createSet((1 << 30) + 1);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}
	}

	/**
	 * Tests the behavior of {@link CompactHashSet#isEmpty()} with random elements.
	 */
	@Test
	public void testIsEmpty() {
		Set<Object> set = createSet();

		assertTrue(set.isEmpty());

		set.add(null);
		assertFalse(set.isEmpty());

		Collection<String> elements = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			elements.add(rand);
			set.add(rand);
			assertFalse(set.isEmpty());
		}
		for (String rand : elements) {
			set.remove(rand);
			assertFalse(set.isEmpty());
		}

		set.remove(null);
		assertTrue(set.isEmpty());

		for (int i = 0; i < 100; i++) {
			set.add(getRandomString());
			assertFalse(set.isEmpty());
		}

		set.clear();
		assertTrue(set.isEmpty());
	}

	/**
	 * Tests the behavior of {@link CompactHashSet#iterator()} with random elements.
	 */
	@Test
	public void testIterator() {
		Collection<Integer> listInt10 = randomIntegerListNotIn(10, Collections.<Integer> emptySet());
		Collection<String> setString20 = randomStringSetNotIn(20, Collections.<String> emptySet());
		Collection<String> dequeString40 = randomStringDequeNotIn(40, setString20);

		Set<Object> set = createSet();

		Iterator<Object> emptyIterator = set.iterator();
		assertFalse(emptyIterator.hasNext());
		try {
			emptyIterator.next();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
		try {
			emptyIterator.remove();
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}

		Iterator<Object> concurrentIterator = set.iterator();
		set.add(null);
		try {
			concurrentIterator.next();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.remove();
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}
		set.clear();

		set.add(null);
		concurrentIterator = set.iterator();
		assertNull(concurrentIterator.next());
		set.add(new Object());
		try {
			concurrentIterator.remove();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		set.clear();

		set.addAll(listInt10);
		set.addAll(setString20);
		set.addAll(dequeString40);

		Iterator<Object> containedValues = set.iterator();
		while (containedValues.hasNext()) {
			Object next = containedValues.next();
			assertTrue(listInt10.contains(next) || setString20.contains(next) || dequeString40.contains(next));
		}
		assertFalse(containedValues.hasNext());

		for (Integer val : listInt10) {
			set.remove(val);
		}
		set.addAll(listInt10);

		containedValues = set.iterator();
		while (containedValues.hasNext()) {
			Object next = containedValues.next();
			assertTrue(listInt10.contains(next) || setString20.contains(next) || dequeString40.contains(next));
		}
		assertFalse(containedValues.hasNext());
		try {
			containedValues.next();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		set.clear();
		assertFalse(set.iterator().hasNext());
		containedValues = set.iterator();
		try {
			containedValues.next();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the removal of elements of the Set through the iterator.remove method.
	 */
	@Test
	public void testIteratorRemove() {
		Collection<Integer> listInt10 = randomIntegerListNotIn(10, Collections.<Integer> emptySet());
		Collection<String> setString20 = randomStringSetNotIn(20, Collections.<String> emptySet());
		Collection<String> dequeString40 = randomStringDequeNotIn(40, setString20);

		Set<Object> set = createSet();

		Iterator<Object> emptyIterator = set.iterator();
		assertFalse(emptyIterator.hasNext());
		try {
			emptyIterator.next();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
		try {
			emptyIterator.remove();
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}

		Iterator<Object> concurrentIterator = set.iterator();
		set.add(null);
		try {
			concurrentIterator.next();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.remove();
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}
		set.clear();

		set.addAll(listInt10);
		set.addAll(setString20);
		set.addAll(dequeString40);

		Iterator<Object> containedValues = set.iterator();
		int size = set.size();
		while (containedValues.hasNext()) {
			final Object next = containedValues.next();
			assertTrue(listInt10.contains(next) || setString20.contains(next) || dequeString40.contains(next));
			assertSame(size, set.size());
			containedValues.remove();
			assertFalse(set.contains(next));
			assertSame(--size, set.size());
		}
		assertFalse(containedValues.hasNext());
		assertSame(0, set.size());
	}

	/**
	 * This will try and ensure that rehashing works properly both when we have a high count of "deleted"
	 * entries and when no entry has been deleted.
	 */
	@Test
	public void testRehashing() {
		/*
		 * We're using for this test values which we know the hashCode so as to prevent all collisions. We
		 * need to test the rehashing when adding a new value into a set which only contains deleted
		 * elements... rehashing won't happen if the value's hash collides with a deleted element.
		 */
		Set<Object> set1 = createSet();
		Set<Object> set2 = createSet();

		List<Integer> integers = new ArrayList<Integer>();
		for (int i = 1; i < 14; i++) {
			integers.add(Integer.valueOf(i));
		}

		assertSame(0, set1.size());
		assertSame(16, getInternalCapacity(set1));
		assertSame(0, set2.size());
		assertSame(16, getInternalCapacity(set2));

		/*
		 * With the default capacity and load factor, the rehashing takes place at 13 insertions. For now, add
		 * 12 elements in each set.
		 */
		for (int i = 0; i < 12; i++) {
			set1.add(integers.get(i));
			set2.add(integers.get(i));
		}

		// Make sure that the rehashing did not take place
		assertSame(12, set1.size());
		assertSame(16, getInternalCapacity(set1));
		assertSame(12, set2.size());
		assertSame(16, getInternalCapacity(set2));

		// Delete 11 elements from set 1
		for (int i = 0; i < 11; i++) {
			set1.remove(integers.get(i));
		}

		assertSame(1, set1.size());
		assertSame(16, getInternalCapacity(set1));
		assertSame(12, set2.size());
		assertSame(16, getInternalCapacity(set2));

		// Now, add a 13th element to both sets
		set1.add(integers.get(12));
		set2.add(integers.get(12));

		/*
		 * And ensure that the first set's capacity hasn't been increased while the second set's has been
		 * doubled.
		 */
		assertSame(2, set1.size());
		assertSame(16, getInternalCapacity(set1));
		assertSame(13, set2.size());
		assertSame(32, getInternalCapacity(set2));
	}

	/**
	 * Tests the behavior of {@link CompactHashSet#remove(Object)} with random elements.
	 */
	@Test
	public void testRemove() {
		Set<Object> set = createSet();
		List<String> objects = randomStringListNotIn(20, Collections.<String> emptySet());

		assertFalse(set.containsAll(objects));

		for (Object o : objects) {
			boolean removed = set.remove(o);
			assertFalse(removed);
			assertFalse(set.contains(o));
		}

		for (Object o : objects) {
			set.add(o);
		}
		assertTrue(set.containsAll(objects));

		for (Object o : objects) {
			assertTrue(set.contains(o));
			boolean removed = set.remove(o);
			assertTrue(removed);
			assertFalse(set.contains(o));
		}

		assertFalse(set.containsAll(objects));

		for (Object o : objects) {
			boolean removed = set.remove(o);
			assertFalse(removed);
			assertFalse(set.contains(o));
		}
	}

	/**
	 * Creates an empty set on which to execute these tests.
	 * 
	 * @return The set to execute these tests on.
	 */
	protected Set<Object> createSet() {
		return new CompactHashSet<Object>();
	}

	/**
	 * Creates a set containing all of the elements from the given collection.
	 * 
	 * @param collection
	 *            The collection which elements are to be added to the new set.
	 * @return The set to execute these tests on.
	 */
	protected Set<Object> createSet(Collection<? extends Object> collection) {
		return new CompactHashSet<Object>(collection);
	}

	/**
	 * Creates a set using the single int constructor.
	 * 
	 * @param elementCount
	 *            Number of elements this set is meant to contain.
	 * @return The set to execute these tests on.
	 */
	protected Set<Object> createSet(int elementCount) {
		return new CompactHashSet<Object>(elementCount);
	}

	/**
	 * Creates a new set given its initial capacity and load factor.
	 * 
	 * @param elementCount
	 *            Number of elements this set is meant to contain.
	 * @param loadFactor
	 *            Load factor of the new set.
	 * @return The set to execute these tests on.
	 */
	protected Set<Object> createSet(int elementCount, float loadFactor) {
		return new CompactHashSet<Object>(elementCount, loadFactor);
	}

	/**
	 * Returns a list containing <code>size</code> random Integers.
	 * 
	 * @param size
	 *            Size of the list to create.
	 * @param excluded
	 *            Values that cannot be in our returned list.
	 * @return A list containing <code>size</code> random Integers.
	 */
	protected List<Integer> randomIntegerListNotIn(int size, Collection<?> excluded) {
		List<Integer> list = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++) {
			Integer integer = getRandomInteger();
			while (list.contains(integer) || excluded.contains(integer)) {
				integer = getRandomInteger();
			}
			list.add(integer);
		}
		return list;
	}

	/**
	 * Returns a list containing <code>size</code> random Strings.
	 * 
	 * @param size
	 *            Size of the list to create.
	 * @param excluded
	 *            Values that cannot be in our returned list.
	 * @return A list containing <code>size</code> random Strings.
	 */
	protected List<String> randomStringListNotIn(int size, Collection<?> excluded) {
		List<String> list = new ArrayList<String>(size);
		for (int i = 0; i < size; i++) {
			String string = getRandomString();
			while (list.contains(string) || excluded.contains(string)) {
				string = getRandomString();
			}
			list.add(string);
		}
		return list;
	}

	/**
	 * Returns a deque containing <code>size</code> random Strings.
	 * 
	 * @param size
	 *            Size of the deque to create.
	 * @param excluded
	 *            Values that cannot be in our returned list.
	 * @return A deque containing <code>size</code> random Strings.
	 */
	protected Deque<String> randomStringDequeNotIn(int size, Collection<?> excluded) {
		Deque<String> deque = new CircularArrayDeque<String>(size);
		for (int i = 0; i < size; i++) {
			String s = getRandomString();
			while (deque.contains(s) || excluded.contains(s)) {
				s = getRandomString();
			}
			deque.add(s);
		}
		return deque;
	}

	/**
	 * Returns a set containing <code>size</code> random Strings.
	 * 
	 * @param size
	 *            Size of the set to create.
	 * @param excluded
	 *            Values that cannot be in our returned list.
	 * @return A set containing <code>size</code> random Strings.
	 */
	protected Set<String> randomStringSetNotIn(int size, Collection<?> excluded) {
		Set<String> set = new HashSet<String>(size);
		for (int i = 0; i < size; i++) {
			String s = getRandomString();
			while (set.contains(s) || excluded.contains(s)) {
				s = getRandomString();
			}
			set.add(s);
		}
		return set;
	}

	/**
	 * Makes the "data" field of the given set public in order to retrieve it.
	 * 
	 * @param set
	 *            The set we need the internal array of.
	 * @return The internal array of the given set.
	 */
	private Object[] getInternalArray(Set<?> set) {
		if (!(set instanceof CompactHashSet<?>)) {
			fail("Unexpected set implementation"); //$NON-NLS-1$
		}
		Field dataField = null;
		for (Field field : CompactHashSet.class.getDeclaredFields()) {
			if (field.getName().equals("data")) { //$NON-NLS-1$
				dataField = field;
				break;
			}
		}
		assertNotNull(dataField);
		assert dataField != null;
		dataField.setAccessible(true);
		Object[] data = null;
		try {
			data = (Object[])dataField.get(set);
		} catch (IllegalArgumentException e) {
			// carry on
		} catch (IllegalAccessException e) {
			// carry on
		}
		if (data == null) {
			fail("could not retrieve internal data array of " + set); //$NON-NLS-1$
		}
		return data;
	}

	/**
	 * Makes the "data" field of the given set public in order to retrieve its current size.
	 * 
	 * @param set
	 *            The set we need the capacity of.
	 * @return The capacity of the given set.
	 */
	private int getInternalCapacity(Set<?> set) {
		Object[] data = getInternalArray(set);
		return data.length;
	}

	/**
	 * Makes the "loadFactor" field of the given set public in order to retrieve it.
	 * 
	 * @param set
	 *            The set we need the internal loadFactor of.
	 * @return The loadFactor of the given set.
	 */
	private float getInternalLoadFactor(Set<?> set) {
		if (!(set instanceof CompactHashSet<?>)) {
			fail("Unexpected set implementation"); //$NON-NLS-1$
		}
		Field loadFactorField = null;
		for (Field field : CompactHashSet.class.getDeclaredFields()) {
			if (field.getName().equals("loadFactor")) { //$NON-NLS-1$
				loadFactorField = field;
				break;
			}
		}
		assertNotNull(loadFactorField);
		assert loadFactorField != null;
		loadFactorField.setAccessible(true);
		Float loadFactor = null;
		try {
			loadFactor = (Float)loadFactorField.get(set);
		} catch (IllegalArgumentException e) {
			// carry on
		} catch (IllegalAccessException e) {
			// carry on
		}
		if (loadFactor == null) {
			fail("could not retrieve load factor of " + set); //$NON-NLS-1$
		}
		assert loadFactor != null;
		return loadFactor.floatValue();
	}

	/**
	 * Get the closest power of two greater than <code>number</code>.
	 * 
	 * @param number
	 *            Number for which we seek the closest power of two.
	 * @return The closest power of two greater than <code>number</code>.
	 */
	private int getNextPowerOfTwo(int number) {
		int powerOfTwo = number--;
		powerOfTwo |= powerOfTwo >> 1;
		powerOfTwo |= powerOfTwo >> 2;
		powerOfTwo |= powerOfTwo >> 4;
		powerOfTwo |= powerOfTwo >> 8;
		powerOfTwo |= powerOfTwo >> 16;
		powerOfTwo++;
		return powerOfTwo;
	}

	/**
	 * Returns a random Integer between 0 and 100000.
	 * 
	 * @return A random Integer between 0 and 100000.
	 */
	private Integer getRandomInteger() {
		return Integer.valueOf(Double.valueOf(Math.random() * 100000d).intValue());
	}

	/**
	 * Returns a random String representing an integer between 0 and 100000.
	 * 
	 * @return A random String representing an integer between 0 and 100000.
	 */
	private String getRandomString() {
		return getRandomInteger().toString();
	}
}
