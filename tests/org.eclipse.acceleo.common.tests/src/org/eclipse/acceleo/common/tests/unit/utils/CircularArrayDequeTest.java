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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CircularArrayDeque;
import org.eclipse.acceleo.common.utils.Deque;
import org.junit.Test;

/**
 * Tests for the {@link CircularArrayDeque} behavior.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CircularArrayDequeTest {
	/**
	 * Tests the behavior of {@link CircularArrayDeque#add(Object)} with random elements.
	 */
	@Test
	public void testAdd() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Deque<Object> deque = new CircularArrayDeque<Object>();

		boolean modified = deque.add(integer1);
		assertTrue(modified);
		assertSame(1, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.getLast());
		assertEquals(integer1, deque.peek());

		modified = deque.add(integer2);
		assertTrue(modified);
		assertSame(2, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(integer2, deque.getLast());

		modified = deque.add(string1);
		assertTrue(modified);
		assertSame(3, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(string1, deque.getLast());

		modified = deque.add(string2);
		assertTrue(modified);
		assertSame(4, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(string2, deque.getLast());

		modified = deque.add(object1);
		assertTrue(modified);
		assertSame(5, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object1, deque.getLast());

		modified = deque.add(object2);
		assertTrue(modified);
		assertSame(6, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object2, deque.getLast());

		modified = deque.add(null);
		assertTrue(modified);
		assertSame(7, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertSame(null, deque.getLast());

		deque.pop();
		assertSame(6, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object2, deque.getLast());

		modified = deque.add(null);
		assertTrue(modified);
		assertSame(7, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertSame(null, deque.getLast());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque.add(rand);
			assertSame(7 + i + 1, deque.size());
			assertEquals(integer1, deque.getFirst());
			assertEquals(integer1, deque.peek());
			assertSame(rand, deque.getLast());
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#addAll(Collection)} with random elements.
	 */
	@Test
	public void testAddAll() {
		Collection<Object> emptyCollection = Collections.emptyList();
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);

		Deque<Object> deque = new CircularArrayDeque<Object>();

		boolean modified = deque.addAll(listInt10);
		assertTrue(modified);
		assertEquals(listInt10.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

		modified = deque.addAll(emptyCollection);
		assertFalse(modified);
		assertEquals(listInt10.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

		modified = deque.addAll(setString20);
		assertTrue(modified);
		assertEquals(listInt10.size() + setString20.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

		modified = deque.addAll(dequeString40);
		assertTrue(modified);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		int expectedCapacity = getNextPowerOfTwo(deque.size());
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		for (@SuppressWarnings("unused")
		Integer val : listInt10) {
			deque.removeFirst();
		}
		assertEquals(setString20.size() + dequeString40.size(), deque.size());
		assertFalse(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		modified = deque.addAll(listInt10);
		assertTrue(modified);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

		modified = deque.addAll(emptyCollection);
		assertFalse(modified);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#addAll(Collection)} with random elements.
	 */
	@Test
	public void testAddAllRandomAccess() {
		Collection<Object> emptyCollection = Collections.emptyList();
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);

		/*
		 * we'll do this five times : one by adding at the beginning (0), one by adding at the end
		 * (deque.size), one by adding in the first half (deque.size / 4), one by adding in the last half
		 * (deque.size / 4 * 3) and finally by adding at the middle (deque.size / 2).
		 */
		for (int i = 0; i < 5; i++) {
			Deque<Object> deque = new CircularArrayDeque<Object>();

			int insertionIndex = 0;
			if (i == 1) {
				insertionIndex = deque.size();
			} else if (i == 2) {
				insertionIndex = deque.size() / 4;
			} else if (i == 3) {
				insertionIndex = deque.size() / 4 * 3;
			} else if (i == 4) {
				insertionIndex = deque.size() / 2;
			}
			boolean modified = deque.addAll(insertionIndex, emptyCollection);
			assertFalse(modified);
			assertEquals(0, deque.size());
			assertEquals(16, getInternalCapacity(deque));

			insertionIndex = 0;
			if (i == 1) {
				insertionIndex = deque.size();
			} else if (i == 2) {
				insertionIndex = deque.size() / 4;
			} else if (i == 3) {
				insertionIndex = deque.size() / 4 * 3;
			} else if (i == 4) {
				insertionIndex = deque.size() / 2;
			}
			modified = deque.addAll(insertionIndex, listInt10);
			assertTrue(modified);
			assertEquals(listInt10.size(), deque.size());
			assertTrue(deque.containsAll(listInt10));
			assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

			insertionIndex = 0;
			if (i == 1) {
				insertionIndex = deque.size();
			} else if (i == 2) {
				insertionIndex = deque.size() / 4;
			} else if (i == 3) {
				insertionIndex = deque.size() / 4 * 3;
			} else if (i == 4) {
				insertionIndex = deque.size() / 2;
			}
			modified = deque.addAll(insertionIndex, setString20);
			assertTrue(modified);
			assertEquals(listInt10.size() + setString20.size(), deque.size());
			assertTrue(deque.containsAll(listInt10));
			assertTrue(deque.containsAll(setString20));
			assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

			insertionIndex = 0;
			if (i == 1) {
				insertionIndex = deque.size();
			} else if (i == 2) {
				insertionIndex = deque.size() / 4;
			} else if (i == 3) {
				insertionIndex = deque.size() / 4 * 3;
			} else if (i == 4) {
				insertionIndex = deque.size() / 2;
			}
			modified = deque.addAll(insertionIndex, dequeString40);
			assertTrue(modified);
			assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), deque.size());
			assertTrue(deque.containsAll(listInt10));
			assertTrue(deque.containsAll(setString20));
			assertTrue(deque.containsAll(dequeString40));
			assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#addAll(int, Collection)} with random elements.
	 */
	@Test
	public void testAddAllRandomAccessLeftRotate() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		for (int i = 0; i < testObjects.length; i++) {
			List<Object> testCollection = new ArrayList<Object>();
			testCollection.add(testObjects[i]);
			testCollection.add(testObjects[testObjects.length - 1 - i]);
			internalTestAddAllRandomAccessLeftRotate(testCollection);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#addAll(int, Collection)} with random elements.
	 */
	@Test
	public void testAddAllRandomAccessRightRotate() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		for (int i = 0; i < testObjects.length; i++) {
			List<Object> testCollection = new ArrayList<Object>();
			testCollection.add(testObjects[i]);
			testCollection.add(testObjects[testObjects.length - 1 - i]);
			internalTestAddAllRandomAccessRightRotate(testCollection);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#addAll(int, Collection)} with random elements but with
	 * out of bounds indices.
	 */
	@Test
	public void testAddAllRandomOutOfBounds() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		for (int i = 0; i < testObjects.length; i++) {
			List<Object> testCollection = new ArrayList<Object>();
			testCollection.add(testObjects[i]);
			testCollection.add(testObjects[testObjects.length - 1 - i]);

			try {
				deque.addAll(-1, testCollection);
				fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
			} catch (IndexOutOfBoundsException e) {
				// expected
			}
			try {
				deque.addAll(deque.size() + 1, testCollection);
				fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
			} catch (IndexOutOfBoundsException e) {
				// expected
			}
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#addFirst(Object)} with random elements.
	 */
	@Test
	public void testAddFirst() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Deque<Object> deque = new CircularArrayDeque<Object>();

		deque.addFirst(integer1);
		assertSame(1, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.addFirst(integer2);
		assertSame(2, deque.size());
		assertEquals(integer2, deque.getFirst());
		assertEquals(integer2, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.addFirst(string1);
		assertSame(3, deque.size());
		assertEquals(string1, deque.getFirst());
		assertEquals(string1, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.addFirst(string2);
		assertSame(4, deque.size());
		assertEquals(string2, deque.getFirst());
		assertEquals(string2, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.addFirst(object1);
		assertSame(5, deque.size());
		assertEquals(object1, deque.getFirst());
		assertEquals(object1, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.addFirst(object2);
		assertSame(6, deque.size());
		assertEquals(object2, deque.getFirst());
		assertEquals(object2, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.addFirst(null);
		assertSame(7, deque.size());
		assertSame(null, deque.getFirst());
		assertSame(null, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.removeFirst();
		assertSame(6, deque.size());
		assertEquals(object2, deque.getFirst());
		assertEquals(object2, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.addFirst(null);
		assertSame(7, deque.size());
		assertSame(null, deque.getFirst());
		assertSame(null, deque.peek());
		assertEquals(integer1, deque.getLast());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque.addFirst(rand);
			assertSame(7 + i + 1, deque.size());
			assertEquals(rand, deque.getFirst());
			assertEquals(rand, deque.peek());
			assertSame(integer1, deque.getLast());
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#addLast(Object)} with random elements.
	 */
	@Test
	public void testAddLast() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Deque<Object> deque = new CircularArrayDeque<Object>();

		deque.addLast(integer1);
		assertSame(1, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.getLast());
		assertEquals(integer1, deque.peek());

		deque.addLast(integer2);
		assertSame(2, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(integer2, deque.getLast());

		deque.addLast(string1);
		assertSame(3, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(string1, deque.getLast());

		deque.addLast(string2);
		assertSame(4, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(string2, deque.getLast());

		deque.addLast(object1);
		assertSame(5, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object1, deque.getLast());

		deque.addLast(object2);
		assertSame(6, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object2, deque.getLast());

		deque.addLast(null);
		assertSame(7, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertSame(null, deque.getLast());

		deque.pop();
		assertSame(6, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object2, deque.getLast());

		deque.addLast(null);
		assertSame(7, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertSame(null, deque.getLast());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque.addLast(rand);
			assertSame(7 + i + 1, deque.size());
			assertEquals(integer1, deque.getFirst());
			assertEquals(integer1, deque.peek());
			assertSame(rand, deque.getLast());
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#add(int, Object)} with random elements.
	 */
	@Test
	public void testAddRandomAccessLeftRotate() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		for (int i = 0; i < testObjects.length; i++) {
			internalTestAddRandomAccessLeftRotate(testObjects[i]);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#add(int, Object)} with random elements but with out of
	 * bounds indices.
	 */
	@Test
	public void testAddRandomAccessOutOfBounds() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		for (int i = 0; i < testObjects.length; i++) {
			try {
				deque.add(-1, testObjects[i]);
				fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
			} catch (IndexOutOfBoundsException e) {
				// expected
			}
			try {
				deque.add(deque.size() + 1, testObjects[i]);
				fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
			} catch (IndexOutOfBoundsException e) {
				// expected
			}
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#add(int, Object)} with random elements.
	 */
	@Test
	public void testAddRandomAccessRightRotate() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		for (int i = 0; i < testObjects.length; i++) {
			internalTestAddRandomAccessRightRotate(testObjects[i]);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#clear()} with random elements.
	 */
	@Test
	public void testClear() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		assertSame(0, deque.size());
		assertTrue(deque.isEmpty());
		assertEquals(16, getInternalCapacity(deque));

		deque.clear();
		assertSame(0, deque.size());
		assertTrue(deque.isEmpty());
		assertEquals(16, getInternalCapacity(deque));

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque.offer(rand);
		}
		deque.offer(null);
		assertSame(101, deque.size());
		assertFalse(deque.isEmpty());
		int expectedCapacity = getNextPowerOfTwo(deque.size());
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		deque.clear();
		assertTrue(deque.isEmpty());
		assertSame(0, deque.size());
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		deque.clear();
		assertTrue(deque.isEmpty());
		assertSame(0, deque.size());
		assertEquals(expectedCapacity, getInternalCapacity(deque));
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#contains()} with random elements.
	 */
	@Test
	public void testContains() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		deque.offer(null);
		assertTrue(deque.contains(null));
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque.offer(rand);
			assertTrue(deque.contains(null));
			assertTrue(deque.contains(rand));
		}

		Deque<Object> dequeCopy = new CircularArrayDeque<Object>(deque);
		assertTrue(deque.contains(null));
		for (Object o : dequeCopy) {
			assertTrue(deque.contains(o));
		}

		deque.clear();
		assertFalse(deque.contains(null));
		for (Object o : dequeCopy) {
			assertFalse(deque.contains(o));
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#containsAll()} with random elements.
	 */
	@Test
	public void testContainsAll() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		Collection<Object> objects1 = new ArrayList<Object>();
		objects1.add(null);
		objects1.addAll(randomStringDeque(40));
		objects1.add(null);
		assertSame(42, objects1.size());

		assertFalse(deque.containsAll(objects1));

		deque.addAll(objects1);
		assertSame(42, deque.size());
		assertTrue(deque.containsAll(objects1));

		Collection<Object> objects2 = new ArrayList<Object>();
		objects2.add(null);
		objects2.addAll(randomStringSet(40));
		objects2.add(null);
		assertSame(42, objects2.size());

		assertFalse(deque.containsAll(objects2));

		deque.addAll(objects2);
		assertSame(objects1.size() + objects2.size(), deque.size());
		assertTrue(deque.containsAll(objects1));
		assertTrue(deque.containsAll(objects2));

		deque.clear();
		assertFalse(deque.containsAll(objects1));
		assertFalse(deque.containsAll(objects2));
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#element()} with random elements.
	 */
	@Test
	public void testElement() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		try {
			deque.element();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		deque.offer(null);
		assertSame(null, deque.element());
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.offer(rand);
			assertSame(null, deque.element());
		}
		List<Object> lastAdded = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.addFirst(rand);
			lastAdded.add(rand);
			assertEquals(rand, deque.element());
		}
		for (int i = 0; i < 20; i++) {
			deque.removeFirst();
			if (i < 19) {
				assertEquals(lastAdded.get(lastAdded.size() - i - 2), deque.element());
			} else {
				assertSame(null, deque.element());
			}
		}

		deque.clear();
		try {
			deque.element();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#equals()} with random elements.
	 */
	@Test
	public void testEquals() {
		Deque<Object> deque1 = new CircularArrayDeque<Object>();
		Deque<Object> deque2 = new CircularArrayDeque<Object>();

		Collection<Object> objects1 = new ArrayList<Object>();
		objects1.add(null);
		objects1.addAll(randomStringDeque(40));
		objects1.add(null);
		assertSame(42, objects1.size());

		deque1.addAll(objects1);
		deque2.addAll(objects1);

		Deque<Object> deque3 = new CircularArrayDeque<Object>(deque1);

		assertTrue(deque1.equals(deque2));
		assertTrue(deque1.equals(deque3));
		assertTrue(deque2.equals(deque1));
		assertTrue(deque2.equals(deque3));
		assertTrue(deque3.equals(deque1));
		assertTrue(deque3.equals(deque2));

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque1.offer(rand);
			deque2.offer(rand);
		}
		assertTrue(deque1.equals(deque2));
		assertFalse(deque1.equals(deque3));
		assertTrue(deque2.equals(deque1));
		assertFalse(deque2.equals(deque3));
		assertFalse(deque3.equals(deque1));
		assertFalse(deque3.equals(deque2));

		Object removed = deque1.pop();
		assertFalse(deque1.equals(deque2));
		assertFalse(deque1.equals(deque3));
		assertFalse(deque2.equals(deque1));
		assertFalse(deque2.equals(deque3));
		assertFalse(deque3.equals(deque1));
		assertFalse(deque3.equals(deque2));

		deque1.addFirst(removed);
		assertFalse(deque1.equals(deque2));
		assertFalse(deque1.equals(deque3));
		assertFalse(deque2.equals(deque1));
		assertFalse(deque2.equals(deque3));
		assertFalse(deque3.equals(deque1));
		assertFalse(deque3.equals(deque2));

		deque1.removeFirst();
		deque2.pop();
		assertTrue(deque1.equals(deque2));
		assertFalse(deque1.equals(deque3));
		assertTrue(deque2.equals(deque1));
		assertFalse(deque2.equals(deque3));
		assertFalse(deque3.equals(deque1));
		assertFalse(deque3.equals(deque2));

		deque1.offer(removed);
		deque2.offer(removed);
		assertTrue(deque1.equals(deque2));
		assertFalse(deque1.equals(deque3));
		assertTrue(deque2.equals(deque1));
		assertFalse(deque2.equals(deque3));
		assertFalse(deque3.equals(deque1));
		assertFalse(deque3.equals(deque2));
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#get(int))} with random indices.
	 */
	@Test
	public void testGet() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		List<Object> objects = new ArrayList<Object>();
		int max = 100000;

		try {
			deque.get(0);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// expected
		}

		for (int i = 0; i < max; i++) {
			String rand = getRandomString();
			deque.add(rand);
			objects.add(rand);
		}

		try {
			deque.get(-1);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// expected
		}

		for (int i = 0; i < max; i++) {
			assertEquals(objects.get(i), deque.get(i));
			assertEquals(objects.get(max - 1 - i), deque.get(max - 1 - i));
		}

		try {
			deque.get(deque.size());
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#getFirst()} with random elements.
	 */
	@Test
	public void testGetFirst() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		try {
			deque.getFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		deque.offer(null);
		assertSame(null, deque.getFirst());
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.offer(rand);
			assertSame(null, deque.getFirst());
		}
		List<Object> lastAdded = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.addFirst(rand);
			lastAdded.add(rand);
			assertEquals(rand, deque.getFirst());
		}
		for (int i = 0; i < 20; i++) {
			deque.removeFirst();
			if (i < 19) {
				assertEquals(lastAdded.get(lastAdded.size() - i - 2), deque.getFirst());
			} else {
				assertSame(null, deque.getFirst());
			}
		}

		deque.clear();
		try {
			deque.getFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#getLast()} with random elements.
	 */
	@Test
	public void testGetLast() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		try {
			deque.getLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		deque.offer(null);
		assertSame(null, deque.getLast());
		List<Object> lastAdded = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.offer(rand);
			lastAdded.add(rand);
			assertEquals(rand, deque.getLast());
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.addFirst(rand);
			assertEquals(lastAdded.get(lastAdded.size() - 1), deque.getLast());
		}
		for (int i = 0; i < 20; i++) {
			deque.removeLast();
			if (i < 19) {
				assertEquals(lastAdded.get(lastAdded.size() - i - 2), deque.getLast());
			} else {
				assertSame(null, deque.getLast());
			}
		}

		deque.clear();
		try {
			deque.getLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#hashCode()} with random elements.
	 */
	@Test
	public void testHashCode() {
		Deque<Object> deque1 = new CircularArrayDeque<Object>();
		Deque<Object> deque2 = new CircularArrayDeque<Object>();

		Collection<Object> objects1 = new ArrayList<Object>();
		objects1.add(null);
		objects1.addAll(randomStringDeque(40));
		objects1.add(null);
		assertSame(42, objects1.size());

		Collection<Object> objects2 = new ArrayList<Object>();
		objects2.add(null);
		objects2.addAll(randomStringSet(40));
		objects2.add(null);
		assertSame(42, objects2.size());

		deque1.addAll(objects1);
		deque2.addAll(objects1);

		Deque<Object> deque3 = new CircularArrayDeque<Object>(deque1);

		assertEquals(deque1.hashCode(), deque2.hashCode());
		assertEquals(deque1.hashCode(), deque3.hashCode());
		assertEquals(deque2.hashCode(), deque1.hashCode());
		assertEquals(deque2.hashCode(), deque3.hashCode());
		assertEquals(deque3.hashCode(), deque1.hashCode());
		assertEquals(deque3.hashCode(), deque2.hashCode());

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque1.offer(rand);
			deque2.offer(rand);
		}
		assertEquals(deque1.hashCode(), deque2.hashCode());
		assertFalse(deque1.hashCode() == deque3.hashCode());
		assertEquals(deque2.hashCode(), deque1.hashCode());
		assertFalse(deque2.hashCode() == deque3.hashCode());
		assertFalse(deque3.hashCode() == deque1.hashCode());
		assertFalse(deque3.hashCode() == deque2.hashCode());

		Object removed = deque1.pop();
		assertFalse(deque1.hashCode() == deque2.hashCode());
		assertFalse(deque1.hashCode() == deque3.hashCode());
		assertFalse(deque2.hashCode() == deque1.hashCode());
		assertFalse(deque2.hashCode() == deque3.hashCode());
		assertFalse(deque3.hashCode() == deque1.hashCode());
		assertFalse(deque3.hashCode() == deque2.hashCode());

		deque1.addFirst(removed);
		assertFalse(deque1.hashCode() == deque2.hashCode());
		assertFalse(deque1.hashCode() == deque3.hashCode());
		assertFalse(deque2.hashCode() == deque1.hashCode());
		assertFalse(deque2.hashCode() == deque3.hashCode());
		assertFalse(deque3.hashCode() == deque1.hashCode());
		assertFalse(deque3.hashCode() == deque2.hashCode());

		deque1.removeFirst();
		deque2.pop();
		assertEquals(deque1.hashCode(), deque2.hashCode());
		assertFalse(deque1.hashCode() == deque3.hashCode());
		assertEquals(deque2.hashCode(), deque1.hashCode());
		assertFalse(deque2.hashCode() == deque3.hashCode());
		assertFalse(deque3.hashCode() == deque1.hashCode());
		assertFalse(deque3.hashCode() == deque2.hashCode());

		deque1.offer(removed);
		deque2.offer(removed);
		assertEquals(deque1.hashCode(), deque2.hashCode());
		assertFalse(deque1.hashCode() == deque3.hashCode());
		assertEquals(deque2.hashCode(), deque1.hashCode());
		assertFalse(deque2.hashCode() == deque3.hashCode());
		assertFalse(deque3.hashCode() == deque1.hashCode());
		assertFalse(deque3.hashCode() == deque2.hashCode());
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#indexOf(Object)} with random elements.
	 */
	@Test
	public void testIndexOf() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 5.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		Deque<Object> deque = new CircularArrayDeque<Object>();
		for (int i = 0; i < testObjects.length; i++) {
			assertEquals(Integer.valueOf(-1), Integer.valueOf(deque.indexOf(testObjects[i])));
		}

		for (Object o : testObjects) {
			deque.offer(o);
		}

		Object[] expected = new Object[16];
		for (int i = 0; i < testObjects.length; i++) {
			expected[i] = testObjects[i];
			assertEquals(Integer.valueOf(i), Integer.valueOf(deque.indexOf(testObjects[i])));
		}
		assertEqualContent(expected, getInternalArray(deque));

		deque.clear();
		for (int i = 0; i < testObjects.length; i++) {
			assertEquals(Integer.valueOf(-1), Integer.valueOf(deque.indexOf(testObjects[i])));
		}

		deque = new CircularArrayDeque<Object>();
		deque.offer("a"); //$NON-NLS-1$
		for (Object o : testObjects) {
			deque.offer(o);
		}
		deque.removeFirst();
		int offset = 1;
		expected = new Object[16];
		for (int i = 0; i < testObjects.length; i++) {
			expected[i + offset] = testObjects[i];
			assertEquals(Integer.valueOf(i), Integer.valueOf(deque.indexOf(testObjects[i])));
		}
		assertEqualContent(expected, getInternalArray(deque));

		deque = new CircularArrayDeque<Object>();
		for (int i = 0; i < 10; i++) {
			deque.offer("a"); //$NON-NLS-1$			
		}
		for (int i = 0; i < 9; i++) {
			deque.removeFirst();
		}
		for (Object o : testObjects) {
			deque.offer(o);
		}
		deque.removeFirst();
		offset = 10;
		expected = new Object[16];
		for (int i = 0; i < testObjects.length; i++) {
			expected[(i + offset) & (expected.length - 1)] = testObjects[i];
			assertEquals(Integer.valueOf(i), Integer.valueOf(deque.indexOf(testObjects[i])));
		}
		assertEqualContent(expected, getInternalArray(deque));

		deque = new CircularArrayDeque<Object>();
		for (Object o : testObjects) {
			deque.offer(o);
		}
		for (Object o : testObjects) {
			deque.offer(o);
		}
		expected = new Object[getNextPowerOfTwo(testObjects.length * 2)];
		for (int i = 0; i < testObjects.length; i++) {
			expected[i] = testObjects[i];
			expected[i + testObjects.length] = testObjects[i];
			assertEquals(Integer.valueOf(i), Integer.valueOf(deque.indexOf(testObjects[i])));
			assertEquals(Integer.valueOf(i + testObjects.length), Integer.valueOf(deque
					.lastIndexOf(testObjects[i])));
		}
		assertEqualContent(expected, getInternalArray(deque));
	}

	/**
	 * Tests that the copy constructor of the Deque creates a deque with all objects of the initial
	 * collection.
	 */
	@Test
	public void testInstantiationCopy() {
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);

		Deque<Object> deque = new CircularArrayDeque<Object>(listInt10);
		assertFalse(deque.isEmpty());
		assertEquals(listInt10.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(listInt10.size()), getInternalCapacity(deque));

		deque = new CircularArrayDeque<Object>(setString20);
		assertFalse(deque.isEmpty());
		assertEquals(setString20.size(), deque.size());
		assertTrue(deque.containsAll(setString20));
		assertEquals(getNextPowerOfTwo(setString20.size()), getInternalCapacity(deque));

		deque = new CircularArrayDeque<Object>(dequeString40);
		assertFalse(deque.isEmpty());
		assertEquals(dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(getNextPowerOfTwo(dequeString40.size()), getInternalCapacity(deque));
	}

	/**
	 * Tests the two constructors of the Deque that initialize an empty deque.
	 */
	@Test
	public void testInstantiationEmpty() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		assertTrue(deque.isEmpty());
		assertSame(0, deque.size());
		// Default capacity is 16
		assertEquals(16, getInternalCapacity(deque));

		deque = new CircularArrayDeque<Object>((1 << 10) - 1);
		assertTrue(deque.isEmpty());
		assertSame(0, deque.size());
		assertEquals(1 << 10, getInternalCapacity(deque));

		deque = new CircularArrayDeque<Object>(1 << 31);
		assertTrue(deque.isEmpty());
		assertSame(0, deque.size());
		assertEquals(4, getInternalCapacity(deque));

		deque = new CircularArrayDeque<Object>(-1);
		assertTrue(deque.isEmpty());
		assertSame(0, deque.size());
		assertEquals(4, getInternalCapacity(deque));
	}

	/**
	 * Tests the constructors of the Deque with out of bounds or erroneous values.
	 */
	@Test
	public void testInstantiationError() {
		try {
			new CircularArrayDeque<Object>(Integer.MAX_VALUE);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}
		try {
			/*
			 * The last possible size for our internal array is 2^30, trying to hold that much elements will
			 * yield a size of -1, which is invalid.
			 */
			new CircularArrayDeque<Object>((1 << 30) + 1);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#isEmpty()} with random elements.
	 */
	@Test
	public void testIsEmpty() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		assertTrue(deque.isEmpty());

		deque.offer(null);
		assertFalse(deque.isEmpty());

		for (int i = 0; i < 100; i++) {
			deque.offer(getRandomString());
			assertFalse(deque.isEmpty());
		}
		for (int i = 0; i < 100; i++) {
			deque.pop();
			assertFalse(deque.isEmpty());
		}

		deque.pop();
		assertTrue(deque.isEmpty());

		for (int i = 0; i < 100; i++) {
			deque.offer(getRandomString());
			assertFalse(deque.isEmpty());
		}

		deque.clear();
		assertTrue(deque.isEmpty());
	}

	/**
	 * Tests the behavior of the serialization and deserialization support of the {@link CircularArrayDeque}.
	 * FIXME test deserialization from file to ensure we do not break support of older serialized deques.
	 */
	@Test
	public void testIsSerializable() {
		Collection<Object> emptyCollection = Collections.emptyList();
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);
		Collection<Object> duplicatesList = new ArrayList<Object>();
		for (int i = 0; i < 40; i++) {
			int dupe = i / 2;
			duplicatesList.add(Integer.valueOf(dupe));
			duplicatesList.add(String.valueOf(dupe));
		}
		duplicatesList.add(null);
		duplicatesList.add(null);

		Deque<Object> deque = new CircularArrayDeque<Object>();

		byte[] serialized = writeDeque(deque);
		assertTrue(serialized.length > 0);
		Deque<Object> read = readDeque(serialized);
		assertNotNull(read);
		assertEquals(deque, read);

		deque.addAll(emptyCollection);
		serialized = writeDeque(deque);
		assertTrue(serialized.length > 0);
		read = readDeque(serialized);
		assertNotNull(read);
		assertEquals(deque, read);

		deque.addAll(listInt10);
		serialized = writeDeque(deque);
		assertTrue(serialized.length > 0);
		read = readDeque(serialized);
		assertNotNull(read);
		assertEquals(deque, read);

		deque.addAll(setString20);
		serialized = writeDeque(deque);
		assertTrue(serialized.length > 0);
		read = readDeque(serialized);
		assertNotNull(read);
		assertEquals(deque, read);

		deque.addAll(dequeString40);
		serialized = writeDeque(deque);
		assertTrue(serialized.length > 0);
		read = readDeque(serialized);
		assertNotNull(read);
		assertEquals(deque, read);

		deque.addAll(duplicatesList);
		serialized = writeDeque(deque);
		assertTrue(serialized.length > 0);
		read = readDeque(serialized);
		assertNotNull(read);
		assertEquals(deque, read);
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#iterator()} with random elements.
	 */
	@Test
	public void testIterator() {
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);

		Deque<Object> deque = new CircularArrayDeque<Object>();

		Iterator<Object> emptyIterator = deque.iterator();
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

		Iterator<Object> concurrentIterator = deque.iterator();
		deque.addFirst(null);
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
		deque.pop();

		concurrentIterator = deque.iterator();
		deque.addLast(null);
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
		deque.pop();

		deque.addLast(null);
		concurrentIterator = deque.iterator();
		assertNull(concurrentIterator.next());
		deque.addLast(null);
		try {
			concurrentIterator.remove();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		deque.clear();

		deque.addAll(listInt10);
		deque.addAll(setString20);
		deque.addAll(dequeString40);

		Iterator<Integer> listIterator = listInt10.iterator();
		Iterator<String> setIterator = setString20.iterator();
		Iterator<String> dequeIterator = dequeString40.iterator();
		Iterator<Object> containedValues = deque.iterator();
		while (listIterator.hasNext()) {
			assertEquals(listIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (setIterator.hasNext()) {
			assertEquals(setIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (dequeIterator.hasNext()) {
			assertEquals(dequeIterator.next(), containedValues.next());
		}
		assertFalse(containedValues.hasNext());

		for (@SuppressWarnings("unused")
		Integer val : listInt10) {
			deque.removeFirst();
		}
		deque.addAll(listInt10);

		listIterator = listInt10.iterator();
		setIterator = setString20.iterator();
		dequeIterator = dequeString40.iterator();
		containedValues = deque.iterator();
		while (setIterator.hasNext()) {
			assertEquals(setIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (dequeIterator.hasNext()) {
			assertEquals(dequeIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (listIterator.hasNext()) {
			assertEquals(listIterator.next(), containedValues.next());
		}
		assertFalse(containedValues.hasNext());
	}

	/**
	 * Tests the removal of elements of the Deque through the iterator.remove method.
	 */
	@Test
	public void testIteratorRemove() {
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);

		Deque<Object> deque = new CircularArrayDeque<Object>();

		Iterator<Object> emptyIterator = deque.iterator();
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

		Iterator<Object> concurrentIterator = deque.iterator();
		deque.addFirst(null);
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
		deque.pop();

		concurrentIterator = deque.iterator();
		deque.addLast(null);
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
		deque.pop();

		deque.addAll(listInt10);
		deque.addAll(setString20);
		deque.addAll(dequeString40);

		Iterator<Integer> listIterator = listInt10.iterator();
		Iterator<String> setIterator = setString20.iterator();
		Iterator<String> dequeIterator = dequeString40.iterator();
		Iterator<Object> containedValues = deque.iterator();
		while (listIterator.hasNext()) {
			assertEquals(listIterator.next(), containedValues.next());
			containedValues.remove();
		}
		assertTrue(containedValues.hasNext());
		while (setIterator.hasNext()) {
			assertEquals(setIterator.next(), containedValues.next());
			containedValues.remove();
		}
		assertTrue(containedValues.hasNext());
		while (dequeIterator.hasNext()) {
			assertEquals(dequeIterator.next(), containedValues.next());
			containedValues.remove();
		}
		assertFalse(containedValues.hasNext());
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#lastIndexOf(Object)} with random elements.
	 */
	@Test
	public void testLastIndexOf() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 5.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		Deque<Object> deque = new CircularArrayDeque<Object>();
		for (int i = 0; i < testObjects.length; i++) {
			assertEquals(Integer.valueOf(-1), Integer.valueOf(deque.lastIndexOf(testObjects[i])));
		}

		for (Object o : testObjects) {
			deque.offer(o);
		}

		Object[] expected = new Object[16];
		for (int i = 0; i < testObjects.length; i++) {
			expected[i] = testObjects[i];
			assertEquals(Integer.valueOf(i), Integer.valueOf(deque.lastIndexOf(testObjects[i])));
		}
		assertEqualContent(expected, getInternalArray(deque));

		deque.clear();
		for (int i = 0; i < testObjects.length; i++) {
			assertEquals(Integer.valueOf(-1), Integer.valueOf(deque.lastIndexOf(testObjects[i])));
		}

		deque = new CircularArrayDeque<Object>();
		deque.offer("a"); //$NON-NLS-1$
		for (Object o : testObjects) {
			deque.offer(o);
		}
		deque.removeFirst();
		int offset = 1;
		expected = new Object[16];
		for (int i = 0; i < testObjects.length; i++) {
			expected[i + offset] = testObjects[i];
			assertEquals(Integer.valueOf(i), Integer.valueOf(deque.lastIndexOf(testObjects[i])));
		}
		assertEqualContent(expected, getInternalArray(deque));

		deque = new CircularArrayDeque<Object>();
		for (int i = 0; i < 10; i++) {
			deque.offer("a"); //$NON-NLS-1$			
		}
		for (int i = 0; i < 9; i++) {
			deque.removeFirst();
		}
		for (Object o : testObjects) {
			deque.offer(o);
		}
		deque.removeFirst();
		offset = 10;
		expected = new Object[16];
		for (int i = 0; i < testObjects.length; i++) {
			expected[(i + offset) & (expected.length - 1)] = testObjects[i];
			assertEquals(Integer.valueOf(i), Integer.valueOf(deque.lastIndexOf(testObjects[i])));
		}
		assertEqualContent(expected, getInternalArray(deque));

		deque = new CircularArrayDeque<Object>();
		for (Object o : testObjects) {
			deque.offer(o);
		}
		for (Object o : testObjects) {
			deque.offer(o);
		}
		expected = new Object[getNextPowerOfTwo(testObjects.length * 2)];
		for (int i = 0; i < testObjects.length; i++) {
			expected[i] = testObjects[i];
			expected[i + testObjects.length] = testObjects[i];
			assertEquals(Integer.valueOf(i), Integer.valueOf(deque.indexOf(testObjects[i])));
			assertEquals(Integer.valueOf(i + testObjects.length), Integer.valueOf(deque
					.lastIndexOf(testObjects[i])));
		}
		assertEqualContent(expected, getInternalArray(deque));
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#listIterator()} with random elements.
	 */
	@Test
	public void testListIterator() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		ListIterator<Object> emptyIterator = deque.listIterator();
		assertFalse(emptyIterator.hasNext());
		assertFalse(emptyIterator.hasPrevious());
		assertSame(0, emptyIterator.nextIndex());
		assertSame(-1, emptyIterator.previousIndex());
		try {
			emptyIterator.next();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
		try {
			emptyIterator.previous();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
		try {
			emptyIterator.set(new Object());
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}
		try {
			emptyIterator.remove();
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}
		Object a = new Object();
		emptyIterator.add(a);
		assertSame(1, deque.size());
		assertFalse(emptyIterator.hasNext());
		assertTrue(emptyIterator.hasPrevious());
		assertSame(1, emptyIterator.nextIndex());
		assertSame(0, emptyIterator.previousIndex());

		assertEquals(a, emptyIterator.previous());
		assertTrue(emptyIterator.hasNext());
		assertFalse(emptyIterator.hasPrevious());
		assertSame(0, emptyIterator.nextIndex());
		assertSame(-1, emptyIterator.previousIndex());

		assertEquals(a, emptyIterator.next());
		assertFalse(emptyIterator.hasNext());
		assertTrue(emptyIterator.hasPrevious());
		assertSame(1, emptyIterator.nextIndex());
		assertSame(0, emptyIterator.previousIndex());

		Object b = new Object();
		emptyIterator.add(b);
		assertSame(2, deque.size());
		assertFalse(emptyIterator.hasNext());
		assertTrue(emptyIterator.hasPrevious());
		assertSame(2, emptyIterator.nextIndex());
		assertSame(1, emptyIterator.previousIndex());

		assertEquals(b, emptyIterator.previous());
		assertTrue(emptyIterator.hasNext());
		assertTrue(emptyIterator.hasPrevious());
		assertSame(1, emptyIterator.nextIndex());
		assertSame(0, emptyIterator.previousIndex());

		Object c = new Object();
		emptyIterator.set(c);
		assertSame(2, deque.size());
		assertTrue(emptyIterator.hasNext());
		assertTrue(emptyIterator.hasPrevious());
		assertSame(1, emptyIterator.nextIndex());
		assertSame(0, emptyIterator.previousIndex());

		assertEquals(c, emptyIterator.next());
		assertFalse(emptyIterator.hasNext());
		assertTrue(emptyIterator.hasPrevious());
		assertSame(2, emptyIterator.nextIndex());
		assertSame(1, emptyIterator.previousIndex());

		assertEquals(c, emptyIterator.previous());
		assertTrue(emptyIterator.hasNext());
		assertTrue(emptyIterator.hasPrevious());
		assertSame(1, emptyIterator.nextIndex());
		assertSame(0, emptyIterator.previousIndex());

		emptyIterator.remove();
		assertSame(1, deque.size());
		assertFalse(emptyIterator.hasNext());
		assertTrue(emptyIterator.hasPrevious());
		assertSame(1, emptyIterator.nextIndex());
		assertSame(0, emptyIterator.previousIndex());

		try {
			emptyIterator.next();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		assertEquals(a, emptyIterator.previous());
		assertTrue(emptyIterator.hasNext());
		assertFalse(emptyIterator.hasPrevious());
		assertSame(0, emptyIterator.nextIndex());
		assertSame(-1, emptyIterator.previousIndex());

		emptyIterator.remove();
		assertSame(0, deque.size());
		assertFalse(emptyIterator.hasNext());
		assertFalse(emptyIterator.hasPrevious());
		assertSame(0, emptyIterator.nextIndex());
		assertSame(-1, emptyIterator.previousIndex());

		try {
			emptyIterator.next();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
		try {
			emptyIterator.previous();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
		try {
			emptyIterator.set(new Object());
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}
		try {
			emptyIterator.remove();
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}

		ListIterator<Object> concurrentIterator = deque.listIterator();
		deque.addFirst(null);
		try {
			concurrentIterator.next();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.previous();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.add(new Object());
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.set(new Object());
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}
		try {
			concurrentIterator.remove();
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}
		deque.pop();

		concurrentIterator = deque.listIterator();
		deque.addLast(null);
		try {
			concurrentIterator.next();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.previous();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.add(new Object());
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.set(new Object());
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}
		try {
			concurrentIterator.remove();
			fail("Expected IllegalStateException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalStateException e) {
			// expected
		}
		deque.pop();

		deque.addLast(null);
		concurrentIterator = deque.listIterator();
		assertNull(concurrentIterator.next());
		deque.addLast(null);
		try {
			concurrentIterator.next();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.previous();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.add(new Object());
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.set(new Object());
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.remove();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		deque.clear();

		List<Integer> listInt10 = randomIntegerList(10);
		Set<String> setString20 = randomStringSet(20);
		List<String> dequeString40 = randomStringDeque(40);

		deque.addAll(listInt10);
		deque.addAll(setString20);
		deque.addAll(dequeString40);

		Iterator<Integer> listIterator = listInt10.iterator();
		Iterator<String> setIterator = setString20.iterator();
		Iterator<String> dequeIterator = dequeString40.iterator();
		ListIterator<Object> containedValues = deque.listIterator();
		while (listIterator.hasNext()) {
			assertEquals(listIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (setIterator.hasNext()) {
			assertEquals(setIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (dequeIterator.hasNext()) {
			assertEquals(dequeIterator.next(), containedValues.next());
		}
		assertFalse(containedValues.hasNext());
		assertTrue(containedValues.hasPrevious());
		for (int i = dequeString40.size() - 1; i >= 0 && containedValues.hasPrevious(); i--) {
			assertEquals(dequeString40.get(i), containedValues.previous());
		}
		assertTrue(containedValues.hasPrevious());
		List<Object> setCopy = new ArrayList<Object>(setString20);
		for (int i = setString20.size() - 1; i >= 0 && containedValues.hasPrevious(); i--) {
			assertEquals(setCopy.get(i), containedValues.previous());
		}
		assertTrue(containedValues.hasPrevious());
		for (int i = listInt10.size() - 1; i >= 0 && containedValues.hasPrevious(); i--) {
			assertEquals(listInt10.get(i), containedValues.previous());
		}
		assertFalse(containedValues.hasPrevious());

		for (@SuppressWarnings("unused")
		Integer val : listInt10) {
			deque.removeFirst();
		}
		deque.addAll(listInt10);

		listIterator = listInt10.iterator();
		setIterator = setString20.iterator();
		dequeIterator = dequeString40.iterator();
		containedValues = deque.listIterator();
		while (setIterator.hasNext()) {
			assertEquals(setIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (dequeIterator.hasNext()) {
			assertEquals(dequeIterator.next(), containedValues.next());
		}
		assertTrue(containedValues.hasNext());
		while (listIterator.hasNext()) {
			assertEquals(listIterator.next(), containedValues.next());
		}
		assertFalse(containedValues.hasNext());
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#listIterator(int)} with random elements.
	 */
	@Test
	public void testListIteratorStartAt() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		try {
			deque.listIterator(-1);
			fail("Expected IndexOutOfBoundsException  hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// expected
		}
		try {
			deque.listIterator(deque.size() + 1);
			fail("Expected IndexOutOfBoundsException  hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// expected
		}

		for (Object o : testObjects) {
			deque.add(o);
		}
		try {
			deque.listIterator(-1);
			fail("Expected IndexOutOfBoundsException  hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// expected
		}

		ListIterator<Object> iterator = deque.listIterator(0);
		assertFalse(iterator.hasPrevious());
		for (Object o : testObjects) {
			assertTrue(iterator.hasNext());
			assertEquals(o, iterator.next());
		}
		assertFalse(iterator.hasNext());
		for (int i = testObjects.length - 1; i >= 0; i--) {
			assertTrue(iterator.hasPrevious());
			assertEquals(testObjects[i], iterator.previous());
		}
		assertFalse(iterator.hasPrevious());

		iterator = deque.listIterator(deque.size());
		assertFalse(iterator.hasNext());
		for (int i = testObjects.length - 1; i >= 0; i--) {
			assertTrue(iterator.hasPrevious());
			assertEquals(testObjects[i], iterator.previous());
		}
		assertFalse(iterator.hasPrevious());
		for (Object o : testObjects) {
			assertTrue(iterator.hasNext());
			assertEquals(o, iterator.next());
		}
		assertFalse(iterator.hasNext());

		try {
			deque.listIterator(deque.size() + 1);
			fail("Expected IndexOutOfBoundsException  hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#offer(Object) with random elements.
	 */
	@Test
	public void testOffer() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Deque<Object> deque = new CircularArrayDeque<Object>();

		deque.offer(integer1);
		assertSame(1, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.getLast());
		assertEquals(integer1, deque.peek());

		deque.offer(integer2);
		assertSame(2, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(integer2, deque.getLast());

		deque.offer(string1);
		assertSame(3, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(string1, deque.getLast());

		deque.offer(string2);
		assertSame(4, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(string2, deque.getLast());

		deque.offer(object1);
		assertSame(5, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object1, deque.getLast());

		deque.offer(object2);
		assertSame(6, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object2, deque.getLast());

		deque.offer(null);
		assertSame(7, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertSame(null, deque.getLast());

		deque.pop();
		assertSame(6, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object2, deque.getLast());

		deque.offer(null);
		assertSame(7, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertSame(null, deque.getLast());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque.offer(rand);
			assertSame(7 + i + 1, deque.size());
			assertEquals(integer1, deque.getFirst());
			assertEquals(integer1, deque.peek());
			assertSame(rand, deque.getLast());
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#offerAll(Collection)} with random elements.
	 */
	@Test
	public void testOfferAll() {
		Collection<Object> emptyCollection = Collections.emptyList();
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);

		Deque<Object> deque = new CircularArrayDeque<Object>();

		deque.offerAll(listInt10);
		assertEquals(listInt10.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

		deque.offerAll(emptyCollection);
		assertEquals(listInt10.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

		deque.offerAll(setString20);
		assertEquals(listInt10.size() + setString20.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

		deque.offerAll(dequeString40);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		int expectedCapacity = getNextPowerOfTwo(deque.size());
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		for (@SuppressWarnings("unused")
		Integer val : listInt10) {
			deque.removeFirst();
		}
		assertEquals(setString20.size() + dequeString40.size(), deque.size());
		assertFalse(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		deque.offerAll(listInt10);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));

		deque.offerAll(emptyCollection);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(getNextPowerOfTwo(deque.size()), getInternalCapacity(deque));
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#offerFirst(Object)} with random elements.
	 */
	@Test
	public void testOfferFirst() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Deque<Object> deque = new CircularArrayDeque<Object>();

		deque.offerFirst(integer1);
		assertSame(1, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.offerFirst(integer2);
		assertSame(2, deque.size());
		assertEquals(integer2, deque.getFirst());
		assertEquals(integer2, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.offerFirst(string1);
		assertSame(3, deque.size());
		assertEquals(string1, deque.getFirst());
		assertEquals(string1, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.offerFirst(string2);
		assertSame(4, deque.size());
		assertEquals(string2, deque.getFirst());
		assertEquals(string2, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.offerFirst(object1);
		assertSame(5, deque.size());
		assertEquals(object1, deque.getFirst());
		assertEquals(object1, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.offerFirst(object2);
		assertSame(6, deque.size());
		assertEquals(object2, deque.getFirst());
		assertEquals(object2, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.offerFirst(null);
		assertSame(7, deque.size());
		assertSame(null, deque.getFirst());
		assertSame(null, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.removeFirst();
		assertSame(6, deque.size());
		assertEquals(object2, deque.getFirst());
		assertEquals(object2, deque.peek());
		assertEquals(integer1, deque.getLast());

		deque.offerFirst(null);
		assertSame(7, deque.size());
		assertSame(null, deque.getFirst());
		assertSame(null, deque.peek());
		assertEquals(integer1, deque.getLast());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque.offerFirst(rand);
			assertSame(7 + i + 1, deque.size());
			assertEquals(rand, deque.getFirst());
			assertEquals(rand, deque.peek());
			assertSame(integer1, deque.getLast());
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#offerLast(Object)} with random elements.
	 */
	@Test
	public void testOfferLast() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Deque<Object> deque = new CircularArrayDeque<Object>();

		deque.offerLast(integer1);
		assertSame(1, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.getLast());
		assertEquals(integer1, deque.peek());

		deque.offerLast(integer2);
		assertSame(2, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(integer2, deque.getLast());

		deque.offerLast(string1);
		assertSame(3, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(string1, deque.getLast());

		deque.offerLast(string2);
		assertSame(4, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(string2, deque.getLast());

		deque.offerLast(object1);
		assertSame(5, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object1, deque.getLast());

		deque.offerLast(object2);
		assertSame(6, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object2, deque.getLast());

		deque.offerLast(null);
		assertSame(7, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertSame(null, deque.getLast());

		deque.pop();
		assertSame(6, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertEquals(object2, deque.getLast());

		deque.offerLast(null);
		assertSame(7, deque.size());
		assertEquals(integer1, deque.getFirst());
		assertEquals(integer1, deque.peek());
		assertSame(null, deque.getLast());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque.offerLast(rand);
			assertSame(7 + i + 1, deque.size());
			assertEquals(integer1, deque.getFirst());
			assertEquals(integer1, deque.peek());
			assertSame(rand, deque.getLast());
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#toArray(Object[])} with random elements.
	 */
	@Test
	public void testParameterizedToArray() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		List<Object> objectsFirst = new ArrayList<Object>();
		List<Object> objectsLast = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsFirst.add(rand);
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsLast.add(rand);
		}

		Object[] array = deque.toArray(new Object[0]);
		assertSame(0, array.length);
		array = deque.toArray(new Object[21]);
		assertSame(21, array.length);
		for (Object o : array) {
			assertNull(o);
		}

		deque.addAll(objectsLast);
		array = deque.toArray(new Object[0]);
		assertSame(deque.size(), array.length);
		Iterator<Object> dequeIterator = deque.iterator();
		for (int i = 0; i < deque.size(); i++) {
			assertEquals(dequeIterator.next(), array[i]);
		}
		array = new String[21];
		deque.toArray(array);
		assertSame(21, array.length);
		dequeIterator = deque.iterator();
		for (int i = 0; i < deque.size(); i++) {
			assertEquals(dequeIterator.next(), array[i]);
		}
		deque.clear();

		deque.addAll(objectsLast);
		for (Object o : objectsFirst) {
			deque.addFirst(o);
		}
		array = deque.toArray(new Object[0]);
		assertSame(deque.size(), array.length);
		dequeIterator = deque.iterator();
		for (int i = 0; i < deque.size(); i++) {
			assertEquals(dequeIterator.next(), array[i]);
		}
		array = new String[41];
		deque.toArray(array);
		assertSame(41, array.length);
		dequeIterator = deque.iterator();
		for (int i = 0; i < deque.size(); i++) {
			assertEquals(dequeIterator.next(), array[i]);
		}

		deque.clear();
		assertEquals(getNextPowerOfTwo(40), getInternalCapacity(deque));
		array = deque.toArray(new Object[0]);
		assertSame(0, array.length);
		array = deque.toArray(new Object[41]);
		assertSame(41, array.length);
		for (Object o : array) {
			assertNull(o);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#peek()} with random elements.
	 */
	@Test
	public void testPeek() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		assertNull(deque.peek());

		deque.offer(null);
		assertSame(null, deque.peek());
		List<Object> lastAdded = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.offer(rand);
			assertSame(null, deque.peek());
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.addFirst(rand);
			lastAdded.add(rand);
			assertEquals(rand, deque.peek());
		}
		for (int i = 0; i < 20; i++) {
			assertEquals(lastAdded.get(lastAdded.size() - i - 1), deque.peek());
			deque.removeFirst();
		}
		assertSame(null, deque.peek());

		deque.addAll(lastAdded);
		deque.clear();

		assertNull(deque.peek());
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#peekFirst()} with random elements.
	 */
	@Test
	public void testPeekFirst() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		try {
			deque.peekFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		deque.offer(null);
		assertSame(null, deque.peekFirst());
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.offer(rand);
			assertSame(null, deque.peekFirst());
		}
		List<Object> lastAdded = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.addFirst(rand);
			lastAdded.add(rand);
			assertEquals(rand, deque.peekFirst());
		}
		for (int i = 0; i < 20; i++) {
			deque.removeFirst();
			if (i < 19) {
				assertEquals(lastAdded.get(lastAdded.size() - i - 2), deque.peekFirst());
			} else {
				assertSame(null, deque.peekFirst());
			}
		}

		deque.clear();
		try {
			deque.peekFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#peekLast()} with random elements.
	 */
	@Test
	public void testPeekLast() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		try {
			deque.peekLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		deque.offer(null);
		assertSame(null, deque.peekLast());
		List<Object> lastAdded = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.offer(rand);
			lastAdded.add(rand);
			assertEquals(rand, deque.peekLast());
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			deque.addFirst(rand);
			assertEquals(lastAdded.get(lastAdded.size() - 1), deque.peekLast());
		}
		for (int i = 0; i < 20; i++) {
			deque.removeLast();
			if (i < 19) {
				assertEquals(lastAdded.get(lastAdded.size() - i - 2), deque.peekLast());
			} else {
				assertSame(null, deque.peekLast());
			}
		}

		deque.clear();
		try {
			deque.peekLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#poll()} with random elements.
	 */
	@Test
	public void testPoll() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		List<Object> objectsFirst = new ArrayList<Object>();
		List<Object> objectsLast = new ArrayList<Object>();

		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsFirst.add(rand);
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsLast.add(rand);
		}
		assertFalse(deque.containsAll(objectsFirst));
		assertFalse(deque.containsAll(objectsLast));

		assertNull(deque.poll());

		for (Object o : objectsLast) {
			deque.offer(o);
		}
		for (Object o : objectsFirst) {
			deque.addFirst(o);
		}
		assertTrue(deque.containsAll(objectsFirst));
		assertTrue(deque.containsAll(objectsLast));

		for (int i = objectsFirst.size() - 1; i >= 0; i--) {
			assertEquals(objectsFirst.get(i), deque.poll());
		}
		for (Object o : objectsLast) {
			assertEquals(o, deque.poll());
		}

		assertNull(deque.poll());

		deque.addAll(objectsFirst);
		deque.addAll(objectsLast);
		deque.clear();

		assertNull(deque.poll());
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#pop()} with random elements.
	 */
	@Test
	public void testPop() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		List<Object> objectsFirst = new ArrayList<Object>();
		List<Object> objectsLast = new ArrayList<Object>();

		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsFirst.add(rand);
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsLast.add(rand);
		}
		assertFalse(deque.containsAll(objectsFirst));
		assertFalse(deque.containsAll(objectsLast));

		try {
			deque.pop();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		for (Object o : objectsLast) {
			deque.offer(o);
		}
		for (Object o : objectsFirst) {
			deque.addFirst(o);
		}
		assertTrue(deque.containsAll(objectsFirst));
		assertTrue(deque.containsAll(objectsLast));

		for (int i = objectsLast.size() - 1; i >= 0; i--) {
			assertEquals(objectsLast.get(i), deque.pop());
		}
		for (Object o : objectsFirst) {
			assertEquals(o, deque.pop());
		}

		try {
			deque.pop();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		deque.addAll(objectsFirst);
		deque.addAll(objectsLast);
		deque.clear();
		try {
			deque.pop();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#remove(Object)} with random elements.
	 */
	@Test
	public void testRemove() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		List<Object> objects = new ArrayList<Object>();

		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objects.add(rand);
		}
		assertFalse(deque.containsAll(objects));

		for (Object o : objects) {
			boolean removed = deque.remove(o);
			assertFalse(removed);
			assertFalse(deque.contains(o));
		}

		for (Object o : objects) {
			deque.offer(o);
		}
		assertTrue(deque.containsAll(objects));

		for (Object o : objects) {
			assertTrue(deque.contains(o));
			boolean removed = deque.remove(o);
			assertTrue(removed);
			assertFalse(deque.contains(o));
		}

		assertFalse(deque.containsAll(objects));

		for (Object o : objects) {
			boolean removed = deque.remove(o);
			assertFalse(removed);
			assertFalse(deque.contains(o));
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#removeAll(Collection)} with random elements.
	 */
	@Test
	public void testRemoveAll() {
		Collection<Object> emptyCollection = Collections.emptyList();
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);

		Deque<Object> deque = new CircularArrayDeque<Object>();

		deque.addAll(listInt10);
		deque.addAll(emptyCollection);
		deque.addAll(setString20);
		deque.addAll(dequeString40);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		int expectedCapacity = getNextPowerOfTwo(deque.size());
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		boolean modified = deque.removeAll(listInt10);
		assertTrue(modified);
		assertEquals(setString20.size() + dequeString40.size(), deque.size());
		assertFalse(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		modified = deque.removeAll(emptyCollection);
		assertFalse(modified);
		assertEquals(setString20.size() + dequeString40.size(), deque.size());
		assertFalse(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		deque.addAll(listInt10);
		assertEquals(listInt10.size() + setString20.size() + dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertTrue(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		modified = deque.removeAll(setString20);
		assertTrue(modified);
		assertEquals(listInt10.size() + dequeString40.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertFalse(deque.containsAll(setString20));
		assertTrue(deque.containsAll(dequeString40));
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		modified = deque.removeAll(dequeString40);
		assertTrue(modified);
		assertEquals(listInt10.size(), deque.size());
		assertTrue(deque.containsAll(listInt10));
		assertFalse(deque.containsAll(setString20));
		assertFalse(deque.containsAll(dequeString40));
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		modified = deque.removeAll(listInt10);
		assertTrue(modified);
		assertEquals(0, deque.size());
		assertFalse(deque.containsAll(listInt10));
		assertFalse(deque.containsAll(setString20));
		assertFalse(deque.containsAll(dequeString40));
		assertEquals(expectedCapacity, getInternalCapacity(deque));

		assertTrue(deque.isEmpty());

		modified = deque.removeAll(listInt10);
		assertFalse(modified);
		modified = deque.removeAll(setString20);
		assertFalse(modified);
		modified = deque.removeAll(dequeString40);
		assertFalse(modified);

		deque.add(null);
		assertTrue(deque.contains(null));
		assertFalse(deque.isEmpty());
		deque.removeAll(Collections.singleton(null));
		assertFalse(deque.contains(null));
		assertTrue(deque.isEmpty());
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#removeAll(Collection)} with random elements.
	 */
	@Test
	public void testRemoveAllBothRotate() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		for (int i = 0; i < testObjects.length; i++) {
			List<Object> testCollection = new ArrayList<Object>();
			testCollection.add(testObjects[i]);
			testCollection.add(testObjects[testObjects.length - 1 - i]);
			internalTestRemoveAllBothRotate(testCollection);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#removeAll(Collection)} with random elements.
	 */
	@Test
	public void testRemoveAllLeftRotate() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		for (int i = 0; i < testObjects.length; i++) {
			List<Object> testCollection = new ArrayList<Object>();
			testCollection.add(testObjects[i]);
			testCollection.add(testObjects[testObjects.length - 1 - i]);
			internalTestRemoveAllLeftRotate(testCollection);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#removeAll(Collection)} with random elements.
	 */
	@Test
	public void testRemoveAllRightRotate() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };

		for (int i = 0; i < testObjects.length; i++) {
			List<Object> testCollection = new ArrayList<Object>();
			testCollection.add(testObjects[i]);
			testCollection.add(testObjects[testObjects.length - 1 - i]);
			internalTestRemoveAllRightRotate(testCollection);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#removeFirst()} with random elements.
	 */
	@Test
	public void testRemoveFirst() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		List<Object> objectsFirst = new ArrayList<Object>();
		List<Object> objectsLast = new ArrayList<Object>();

		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsFirst.add(rand);
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsLast.add(rand);
		}
		assertFalse(deque.containsAll(objectsFirst));
		assertFalse(deque.containsAll(objectsLast));

		try {
			deque.removeFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		for (Object o : objectsLast) {
			deque.offer(o);
		}
		for (Object o : objectsFirst) {
			deque.addFirst(o);
		}
		assertTrue(deque.containsAll(objectsFirst));
		assertTrue(deque.containsAll(objectsLast));

		for (int i = objectsFirst.size() - 1; i >= 0; i--) {
			assertEquals(objectsFirst.get(i), deque.removeFirst());
		}
		for (Object o : objectsLast) {
			assertEquals(o, deque.removeFirst());
		}

		try {
			deque.removeFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		deque.addAll(objectsFirst);
		deque.addAll(objectsLast);
		deque.clear();
		try {
			deque.removeFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#removeLast()} with random elements.
	 */
	@Test
	public void testRemoveLast() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		List<Object> objectsFirst = new ArrayList<Object>();
		List<Object> objectsLast = new ArrayList<Object>();

		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsFirst.add(rand);
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsLast.add(rand);
		}
		assertFalse(deque.containsAll(objectsFirst));
		assertFalse(deque.containsAll(objectsLast));

		try {
			deque.removeLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		for (Object o : objectsLast) {
			deque.offer(o);
		}
		for (Object o : objectsFirst) {
			deque.addFirst(o);
		}
		assertTrue(deque.containsAll(objectsFirst));
		assertTrue(deque.containsAll(objectsLast));

		for (int i = objectsLast.size() - 1; i >= 0; i--) {
			assertEquals(objectsLast.get(i), deque.removeLast());
		}
		for (Object o : objectsFirst) {
			assertEquals(o, deque.removeLast());
		}

		try {
			deque.removeLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		deque.addAll(objectsFirst);
		deque.addAll(objectsLast);
		deque.clear();
		try {
			deque.removeLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#remove(Object)} with random elements.
	 */
	@Test
	public void testRemoveLeftRotate() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };
		for (int i = 0; i < testObjects.length; i++) {
			internalTestRemoveLeftRotate(testObjects[i]);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#remove()} with random elements.
	 */
	@Test
	public void testRemoveQueue() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		List<Object> objectsFirst = new ArrayList<Object>();
		List<Object> objectsLast = new ArrayList<Object>();

		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsFirst.add(rand);
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsLast.add(rand);
		}
		assertFalse(deque.containsAll(objectsFirst));
		assertFalse(deque.containsAll(objectsLast));

		try {
			deque.remove();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		for (Object o : objectsLast) {
			deque.offer(o);
		}
		for (Object o : objectsFirst) {
			deque.addFirst(o);
		}
		assertTrue(deque.containsAll(objectsFirst));
		assertTrue(deque.containsAll(objectsLast));

		for (int i = objectsFirst.size() - 1; i >= 0; i--) {
			assertEquals(objectsFirst.get(i), deque.remove());
		}
		for (Object o : objectsLast) {
			assertEquals(o, deque.remove());
		}

		try {
			deque.remove();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		deque.addAll(objectsFirst);
		deque.addAll(objectsLast);
		deque.clear();
		try {
			deque.remove();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#remove(Object)} with random elements.
	 */
	@Test
	public void testRemoveRightRotate() {
		Object[] testObjects = new Object[] {"abcd", "", "*", "?", "\n", '\'', null, 4, 4.3, 5L, 4.3d, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				new Object() };
		for (int i = 0; i < testObjects.length; i++) {
			internalTestRemoveRightRotate(testObjects[i]);
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#retainAll(Collection)} with random elements.
	 */
	@Test
	public void testRetainAll() {
		Collection<Object> emptyCollection = Collections.emptyList();
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);

		Deque<Object> deque = new CircularArrayDeque<Object>();
		deque.addAll(listInt10);
		deque.addAll(emptyCollection);
		deque.addAll(setString20);
		deque.addAll(dequeString40);

		boolean modified = deque.retainAll(dequeString40);
		assertTrue(modified);
		assertSame(dequeString40.size(), deque.size());
		for (Integer integer : listInt10) {
			assertFalse(deque.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(deque.contains(string));
		}
		for (String string : dequeString40) {
			assertTrue(deque.contains(string));
		}

		deque = new CircularArrayDeque<Object>();
		deque.addAll(listInt10);
		deque.addAll(emptyCollection);
		deque.addAll(setString20);
		deque.addAll(dequeString40);

		modified = deque.retainAll(setString20);
		assertTrue(modified);
		assertSame(setString20.size(), deque.size());
		for (Integer integer : listInt10) {
			assertFalse(deque.contains(integer));
		}
		for (String string : setString20) {
			assertTrue(deque.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(deque.contains(string));
		}

		deque = new CircularArrayDeque<Object>();
		deque.addAll(listInt10);
		deque.addAll(emptyCollection);
		deque.addAll(setString20);
		deque.addAll(dequeString40);

		modified = deque.retainAll(listInt10);
		assertTrue(modified);
		assertSame(listInt10.size(), deque.size());
		for (Integer integer : listInt10) {
			assertTrue(deque.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(deque.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(deque.contains(string));
		}

		deque = new CircularArrayDeque<Object>();
		deque.addAll(listInt10);
		deque.addAll(emptyCollection);
		deque.addAll(setString20);
		deque.addAll(dequeString40);

		modified = deque.retainAll(emptyCollection);
		assertTrue(modified);
		assertSame(0, deque.size());
		for (Integer integer : listInt10) {
			assertFalse(deque.contains(integer));
		}
		for (String string : setString20) {
			assertFalse(deque.contains(string));
		}
		for (String string : dequeString40) {
			assertFalse(deque.contains(string));
		}

		deque = new CircularArrayDeque<Object>();
		modified = deque.retainAll(emptyCollection);
		assertFalse(modified);
		assertSame(0, deque.size());

		modified = deque.retainAll(listInt10);
		assertFalse(modified);
		assertSame(0, deque.size());

		modified = deque.retainAll(dequeString40);
		assertFalse(modified);
		assertSame(0, deque.size());
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#set(int, Object)} with random elements.
	 */
	@Test
	public void testSet() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Deque<Object> deque = new CircularArrayDeque<Object>();

		int[] invalidIndices = new int[] {0, -1, Integer.MIN_VALUE, deque.size() + 1 };
		for (int i : invalidIndices) {
			try {
				deque.set(i, string2);
				fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
			} catch (IndexOutOfBoundsException e) {
				// expected
			}
		}

		deque.add(integer1);
		deque.add(string1);
		deque.add(object1);

		invalidIndices = new int[] {-1, Integer.MIN_VALUE, deque.size() + 1 };
		for (int i : invalidIndices) {
			try {
				deque.set(i, string2);
				fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
			} catch (IndexOutOfBoundsException e) {
				// expected
			}
		}

		assertEquals(integer1, deque.set(0, integer2));
		assertEquals(integer2, deque.get(0));
		assertFalse(deque.contains(integer1));
		assertSame(3, deque.size());

		assertEquals(string1, deque.set(1, string2));
		assertEquals(string2, deque.get(1));
		assertFalse(deque.contains(string1));
		assertSame(3, deque.size());

		assertEquals(object1, deque.set(2, object2));
		assertEquals(object2, deque.get(2));
		assertFalse(deque.contains(object1));
		assertSame(3, deque.size());

		assertEquals(string2, deque.set(1, integer2));
		assertEquals(integer2, deque.get(1));
		assertFalse(deque.contains(string2));
		assertSame(3, deque.size());

		assertEquals(object2, deque.set(2, integer2));
		assertEquals(integer2, deque.get(2));
		assertFalse(deque.contains(object2));
		assertSame(3, deque.size());

		invalidIndices = new int[] {-1, Integer.MIN_VALUE, deque.size() + 1 };
		for (int i : invalidIndices) {
			try {
				deque.set(i, string2);
				fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
			} catch (IndexOutOfBoundsException e) {
				// expected
			}
		}

		deque.clear();

		invalidIndices = new int[] {0, -1, Integer.MIN_VALUE, deque.size() + 1 };
		for (int i : invalidIndices) {
			try {
				deque.set(i, string2);
				fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
			} catch (IndexOutOfBoundsException e) {
				// expected
			}
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#size()} with random elements.
	 */
	@Test
	public void testSize() {
		Deque<Object> deque = new CircularArrayDeque<Object>();

		int size = 0;
		assertSame(size, deque.size());

		deque.offer(null);
		assertSame(++size, deque.size());

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			deque.offer(rand);
			assertSame(++size, deque.size());
		}

		for (int i = 0; i < 100; i++) {
			deque.pop();
			assertSame(--size, deque.size());
		}
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#subList(int, int)} with random elements. As this is
	 * fully inherited from AbstractList, we'll only test that the operation is accessible.
	 */
	@Test
	public void testSubList() {
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);

		Deque<Object> deque = new CircularArrayDeque<Object>();

		List<Object> subList = deque.subList(0, 0);
		assertEquals(subList, deque);
		assertTrue(subList.isEmpty());

		try {
			deque.subList(0, 5);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}

		deque.addAll(listInt10);
		deque.addAll(setString20);

		subList = deque.subList(0, deque.size());
		assertEquals(subList, deque);

		subList = deque.subList(0, listInt10.size());
		assertSame(listInt10.size(), subList.size());
		assertTrue(subList.containsAll(listInt10));

		subList = deque.subList(0, listInt10.size() + setString20.size());
		assertSame(listInt10.size() + setString20.size(), subList.size());
		assertTrue(subList.containsAll(listInt10));
		assertTrue(subList.containsAll(setString20));

		try {
			deque.subList(-1, 10);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}

		try {
			deque.subList(5, 0);
			fail("Expected IllegalArgumentException hasn't been thrown"); //$NON-NLS-1$
		} catch (IllegalArgumentException e) {
			// Expected
		}

		subList = deque.subList(5, 5);
		assertSame(0, subList.size());
		assertTrue(subList.isEmpty());
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#toArray()} with random elements.
	 */
	@Test
	public void testToArray() {
		Deque<Object> deque = new CircularArrayDeque<Object>();
		List<Object> objectsFirst = new ArrayList<Object>();
		List<Object> objectsLast = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsFirst.add(rand);
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objectsLast.add(rand);
		}

		Object[] array = deque.toArray();
		assertSame(0, array.length);

		deque.addAll(objectsLast);
		array = deque.toArray();
		assertSame(deque.size(), array.length);
		Iterator<Object> dequeIterator = deque.iterator();
		for (int i = 0; i < array.length; i++) {
			assertEquals(dequeIterator.next(), array[i]);
		}
		deque.clear();

		deque.addAll(objectsLast);
		for (Object o : objectsFirst) {
			deque.addFirst(o);
		}
		array = deque.toArray();
		assertSame(deque.size(), array.length);
		dequeIterator = deque.iterator();
		for (int i = 0; i < array.length; i++) {
			assertEquals(dequeIterator.next(), array[i]);
		}

		deque.clear();
		array = deque.toArray();
		assertEquals(getNextPowerOfTwo(40), getInternalCapacity(deque));
		assertSame(0, array.length);
	}

	/**
	 * Checks that the two arrays contain the same content.
	 * 
	 * @param expected
	 *            First of the two array to compare.
	 * @param actual
	 *            Second of the two array to compare.
	 */
	private void assertEqualContent(Object[] expected, Object[] actual) {
		assertEquals(expected.length, actual.length);
		for (int i = 0; i < expected.length; i++) {
			if (expected[i] == null) {
				assertSame(expected[i], actual[i]);
			} else {
				assertEquals(expected[i], actual[i]);
			}
		}
	}

	/**
	 * Makes the "data" field of the given deque public in order to retrieve it.
	 * 
	 * @param deque
	 *            The deque we need the internal array of.
	 * @return The internal array of the given deque.
	 */
	private Object[] getInternalArray(Deque<?> deque) {
		Field dataField = null;
		for (Field field : deque.getClass().getDeclaredFields()) {
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
			data = (Object[])dataField.get(deque);
		} catch (IllegalArgumentException e) {
			// carry on
		} catch (IllegalAccessException e) {
			// carry on
		}
		if (data == null) {
			fail("could not retrieve capacity of " + deque); //$NON-NLS-1$
		}
		return data;
	}

	/**
	 * Makes the "data" field of the given deque public in order to retrieve its current size.
	 * 
	 * @param deque
	 *            The deque we need the capacity of.
	 * @return The capacity of the given deque.
	 */
	private int getInternalCapacity(Deque<?> deque) {
		Object[] data = getInternalArray(deque);
		return data.length;
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

	/**
	 * Tests the behavior of {@link CircularArrayDeque#addAll(int, Collection)} with the given Collection. For
	 * the sake of this test, all collections will have a size of 2.
	 * 
	 * @param collection
	 *            Collection to insert into the deque.
	 */
	@SuppressWarnings("nls")
	private void internalTestAddAllRandomAccessLeftRotate(List<Object> collection) {
		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ a a a a a a _ _ //
		// expect: _ _ _ _ _ _ a a ? ? a a a a _ _ //
		// //////////////////////////////////////////
		Deque<Object> deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 14; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 8; i++) {
			deque.removeFirst();
		}

		deque.addAll(2, collection);
		Object[] data = getInternalArray(deque);
		Object[] expected = new Object[] {null, null, null, null, null, null, "a", "a", collection.get(0),
				collection.get(1), "a", "a", "a", "a", null, null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : _ a a a a a a _ _ _ _ _ _ _ _ _ //
		// expect: a ? ? a a a a _ _ _ _ _ _ _ _ a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 7; i++) {
			deque.offer("a");
		}
		deque.removeFirst();

		deque.addAll(2, collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", collection.get(0), collection.get(1), "a", "a", "a", "a", null, null,
				null, null, null, null, null, null, "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a a a a a _ _ _ _ _ _ _ _ _ //
		// expect: a ? ? a a a a _ _ _ _ _ _ _ a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 7; i++) {
			deque.offer("a");
		}

		deque.addAll(3, collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", collection.get(0), collection.get(1), "a", "a", "a", "a", null, null,
				null, null, null, null, null, "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a a a a _ _ _ _ _ _ _ _ _ a //
		// expect: ? ? a a a a _ _ _ _ _ _ _ a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 14; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 7; i++) {
			deque.offer("a");
		}
		deque.removeFirst();

		deque.addAll(3, collection);
		data = getInternalArray(deque);
		expected = new Object[] {collection.get(0), collection.get(1), "a", "a", "a", "a", null, null, null,
				null, null, null, null, "a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a a a _ _ _ _ _ _ _ _ _ a a //
		// expect: ? a a a a _ _ _ _ _ _ _ a a a ? //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 14; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 6; i++) {
			deque.offer("a");
		}

		deque.addAll(3, collection);
		data = getInternalArray(deque);
		expected = new Object[] {collection.get(1), "a", "a", "a", "a", null, null, null, null, null, null,
				null, "a", "a", "a", collection.get(0) };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a a _ _ _ _ _ _ _ _ _ a a a //
		// expect: a a a a _ _ _ _ _ _ _ a a a ? ? //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 13; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 5; i++) {
			deque.offer("a");
		}

		deque.addAll(3, collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", null, null, null, null, null, null, null, "a", "a", "a",
				collection.get(0), collection.get(1) };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////////////////////////////////////
		// Array : a a a a a a a _ a a a a a a a a //
		// expect: a a ? ? a a a a a a a a a a a _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ a a //
		// //////////////////////////////////////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 8; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 8; i++) {
			deque.offer("a");
		}

		deque.addAll(4, collection);
		data = getInternalArray(deque);
		expected = new Object[32];
		for (int i = 0; i < 2; i++) {
			expected[i] = "a";
		}
		expected[2] = collection.get(0);
		expected[3] = collection.get(1);
		for (int i = 4; i < 15; i++) {
			expected[i] = "a";
		}
		expected[30] = "a";
		expected[31] = "a";
		assertEqualContent(expected, data);
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#addAll(int, Collection)} with the given Collection. For
	 * the sake of this test, all collections will have a size of 2.
	 * 
	 * @param collection
	 *            Collection to insert into the deque.
	 */
	@SuppressWarnings("nls")
	private void internalTestAddAllRandomAccessRightRotate(List<Object> collection) {
		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ a a a a a a _ _ //
		// expect: _ _ _ _ _ _ _ _ a a a a ? ? a a //
		// //////////////////////////////////////////
		Deque<Object> deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 14; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 8; i++) {
			deque.removeFirst();
		}

		deque.addAll(4, collection);
		Object[] data = getInternalArray(deque);
		Object[] expected = new Object[] {null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				collection.get(0), collection.get(1), "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ _ a a a a a a _ //
		// expect: a _ _ _ _ _ _ _ _ a a a a ? ? a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 9; i++) {
			deque.removeFirst();
		}

		deque.addAll(4, collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				collection.get(0), collection.get(1), "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ _ a a a a a a a //
		// expect: a a _ _ _ _ _ _ _ a a a a ? ? a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 9; i++) {
			deque.removeFirst();
		}
		deque.offer("a");

		deque.addAll(4, collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", null, null, null, null, null, null, null, "a", "a", "a", "a",
				collection.get(0), collection.get(1), "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a _ _ _ _ _ _ _ _ _ a a a a a a //
		// expect: a a a _ _ _ _ _ _ _ a a a a ? ? //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 10; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.offer("a");

		deque.addAll(4, collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", null, null, null, null, null, null, null, "a", "a", "a", "a",
				collection.get(0), collection.get(1) };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a _ _ _ _ _ _ _ _ _ a a a a a //
		// expect: ? a a a _ _ _ _ _ _ _ a a a a ? //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 11; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.offer("a");
		deque.offer("a");

		deque.addAll(4, collection);
		data = getInternalArray(deque);
		expected = new Object[] {collection.get(1), "a", "a", "a", null, null, null, null, null, null, null,
				"a", "a", "a", "a", collection.get(0) };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a _ _ _ _ _ _ _ _ _ _ a a a a //
		// expect: ? ? a a _ _ _ _ _ _ _ _ a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 12; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.offer("a");
		deque.offer("a");

		deque.addAll(4, collection);
		data = getInternalArray(deque);
		expected = new Object[] {collection.get(0), collection.get(1), "a", "a", null, null, null, null,
				null, null, null, null, "a", "a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////////////////////////////////////
		// Array : a a a a a a a _ a a a a a a a a //
		// expect: a a a a a a a a a a a ? ? a a a a _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ //
		// //////////////////////////////////////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 7; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 7; i++) {
			deque.offer("a");
		}

		deque.addAll(11, collection);
		data = getInternalArray(deque);
		expected = new Object[32];
		for (int i = 0; i < 11; i++) {
			expected[i] = "a";
		}
		expected[11] = collection.get(0);
		expected[12] = collection.get(1);
		for (int i = 13; i < 17; i++) {
			expected[i] = "a";
		}
		assertEqualContent(expected, data);
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#add(int, Object)} with the given element.
	 * 
	 * @param element
	 *            Element to insert into the deque.
	 */
	@SuppressWarnings("nls")
	private void internalTestAddRandomAccessLeftRotate(Object element) {
		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ a a a a a a a _ //
		// expect: _ _ _ _ _ _ _ a a a ? a a a a _ //
		// //////////////////////////////////////////
		Deque<Object> deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 8; i++) {
			deque.removeFirst();
		}

		deque.add(3, element);
		Object[] data = getInternalArray(deque);
		Object[] expected = new Object[] {null, null, null, null, null, null, null, "a", "a", "a", element,
				"a", "a", "a", "a", null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a a a a a _ _ _ _ _ _ _ _ _ //
		// expect: a a ? a a a a _ _ _ _ _ _ _ _ a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 7; i++) {
			deque.offer("a");
		}

		deque.add(3, element);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", element, "a", "a", "a", "a", null, null, null, null, null, null,
				null, null, "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a a a _ _ _ _ _ _ _ _ _ a a //
		// expect: ? a a a a _ _ _ _ _ _ _ _ a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 14; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 6; i++) {
			deque.offer("a");
		}

		deque.add(3, element);
		data = getInternalArray(deque);
		expected = new Object[] {element, "a", "a", "a", "a", null, null, null, null, null, null, null, null,
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a a _ _ _ _ _ _ _ _ _ a a a //
		// expect: a a a a _ _ _ _ _ _ _ _ a a a ? //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 13; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 5; i++) {
			deque.offer("a");
		}

		deque.add(3, element);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", null, null, null, null, null, null, null, null, "a",
				"a", "a", element };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////////////////////////////////////
		// Array : a a a a a a a _ a a a a a a a a //
		// expect: a a a ? a a a a a a a a a a a a _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ //
		// //////////////////////////////////////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 7; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 7; i++) {
			deque.offer("a");
		}

		deque.add(3, element);
		data = getInternalArray(deque);
		expected = new Object[32];
		for (int i = 0; i < 3; i++) {
			expected[i] = "a";
		}
		expected[3] = element;
		for (int i = 4; i < 16; i++) {
			expected[i] = "a";
		}
		assertEqualContent(expected, data);
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#add(int, Object)} with the given element.
	 * 
	 * @param element
	 *            Element to insert into the deque.
	 */
	@SuppressWarnings("nls")
	private void internalTestAddRandomAccessRightRotate(Object element) {
		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ a a a a a a a _ //
		// expect: _ _ _ _ _ _ _ _ a a a a ? a a a //
		// //////////////////////////////////////////
		Deque<Object> deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 8; i++) {
			deque.removeFirst();
		}

		deque.add(4, element);
		Object[] data = getInternalArray(deque);
		Object[] expected = new Object[] {null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				element, "a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ _ a a a a a a a //
		// expect: a _ _ _ _ _ _ _ _ a a a a ? a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 9; i++) {
			deque.removeFirst();
		}
		deque.offer("a");

		deque.add(4, element);
		data = getInternalArray(deque);
		expected = new Object[] {"a", null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				element, "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a _ _ _ _ _ _ _ _ _ a a a a a //
		// expect: a a a _ _ _ _ _ _ _ _ a a a a ? //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 11; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.offer("a");
		deque.offer("a");

		deque.add(4, element);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", null, null, null, null, null, null, null, null, "a", "a",
				"a", "a", element };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a _ _ _ _ _ _ _ _ _ a a a a //
		// expect: ? a a a _ _ _ _ _ _ _ _ a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 12; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.offer("a");
		deque.offer("a");
		deque.offer("a");

		deque.add(4, element);
		data = getInternalArray(deque);
		expected = new Object[] {element, "a", "a", "a", null, null, null, null, null, null, null, null, "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////////////////////////////////////
		// Array : a a a a a a a _ a a a a a a a a //
		// expect: a a a a a a a a a a a ? a a a a _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ //
		// //////////////////////////////////////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 7; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 7; i++) {
			deque.offer("a");
		}

		deque.add(11, element);
		data = getInternalArray(deque);
		expected = new Object[32];
		for (int i = 0; i < 11; i++) {
			expected[i] = "a";
		}
		expected[11] = element;
		for (int i = 12; i < 16; i++) {
			expected[i] = "a";
		}
		assertEqualContent(expected, data);
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#removeAll(Collection)} with the given Collection. For
	 * the sake of this test, all collections will have a size of 2.
	 * 
	 * @param collection
	 *            Collection to remove from the deque.
	 */
	@SuppressWarnings("nls")
	private void internalTestRemoveAllBothRotate(List<Object> collection) {
		// //////////////////////////////////////////
		// Array : _ _ _ _ _ a a a ? ? a a a _ _ _ //
		// expect: _ _ _ _ _ _ a a a a a a _ _ _ _ //
		// //////////////////////////////////////////
		Deque<Object> deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 13; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 5; i++) {
			deque.removeFirst();
		}
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		Object[] data = getInternalArray(deque);
		Object[] expected = new Object[] {null, null, null, null, null, null, "a", "a", "a", "a", "a", "a",
				null, null, null, null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ a a a ? ? a a a //
		// expect: _ _ _ _ _ _ _ _ _ a a a a a a _ //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 8; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {null, null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				"a", "a", null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a _ _ _ _ _ _ _ _ a a a ? ? a a //
		// expect: _ _ _ _ _ _ _ _ _ _ a a a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 9; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 2; i++) {
			deque.offer("a");
		}
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {null, null, null, null, null, null, null, null, null, null, "a", "a", "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a _ _ _ _ _ _ _ _ a a a ? ? a //
		// expect: a _ _ _ _ _ _ _ _ _ _ a a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 10; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 3; i++) {
			deque.offer("a");
		}
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", null, null, null, null, null, null, null, null, null, null, "a", "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a _ _ _ _ _ _ _ _ a a a ? ? //
		// expect: a a _ _ _ _ _ _ _ _ _ _ a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 11; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", null, null, null, null, null, null, null, null, null, null, "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : ? a a a _ _ _ _ _ _ _ _ a a a ? //
		// expect: a a a _ _ _ _ _ _ _ _ _ _ a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 12; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 5; i++) {
			deque.offer("a");
		}
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", null, null, null, null, null, null, null, null, null, null,
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : ? ? a a a _ _ _ _ _ _ _ _ a a a //
		// expect: a a a a _ _ _ _ _ _ _ _ _ _ a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 13; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 6; i++) {
			deque.offer("a");
		}
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", null, null, null, null, null, null, null, null, null,
				null, "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a ? ? a a a _ _ _ _ _ _ _ _ a a //
		// expect: a a a a a _ _ _ _ _ _ _ _ _ _ a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 14; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 7; i++) {
			deque.offer("a");
		}
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", "a", null, null, null, null, null, null, null, null,
				null, null, "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a ? ? a a a _ _ _ _ _ _ _ _ a //
		// expect: a a a a a a _ _ _ _ _ _ _ _ _ _ //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		deque.removeFirst();
		deque.offer("a");
		for (int i = 0; i < 14; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 7; i++) {
			deque.offer("a");
		}
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", "a", "a", null, null, null, null, null, null, null,
				null, null, null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a ? ? a a a _ _ _ _ _ _ _ _ //
		// expect: _ a a a a a a _ _ _ _ _ _ _ _ _ //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 8; i++) {
			deque.offer("a");
		}
		deque.set(3, collection.get(0));
		deque.set(4, collection.get(1));

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {null, "a", "a", "a", "a", "a", "a", null, null, null, null, null, null,
				null, null, null };
		assertEqualContent(expected, data);
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#removeAll(Collection)} with the given Collection. For
	 * the sake of this test, all collections will have a size of 2.
	 * 
	 * @param collection
	 *            Collection to remove from the deque.
	 */
	@SuppressWarnings("nls")
	private void internalTestRemoveAllLeftRotate(List<Object> collection) {
		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ a a a a ? ? a a //
		// expect: _ _ _ _ _ _ _ _ a a a a a a _ _ //
		// //////////////////////////////////////////
		Deque<Object> deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 12; i++) {
			deque.offer("a");
		}
		deque.addAll(collection);
		for (int i = 0; i < 8; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 2; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		Object[] data = getInternalArray(deque);
		Object[] expected = new Object[] {null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				"a", "a", null, null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a _ _ _ _ _ _ _ _ a a a a ? ? a //
		// expect: _ _ _ _ _ _ _ _ _ a a a a a a _ //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 13; i++) {
			deque.offer("a");
		}
		deque.addAll(collection);
		for (int i = 0; i < 9; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 2; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {null, null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				"a", "a", null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a _ _ _ _ _ _ a a a a a ? ? a //
		// expect: _ _ _ _ _ _ _ _ a a a a a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 13; i++) {
			deque.offer("a");
		}
		deque.addAll(collection);
		for (int i = 0; i < 8; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 3; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {null, null, null, null, null, null, null, null, "a", "a", "a", "a", "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a _ _ _ _ _ _ a a a a a ? ? //
		// expect: a _ _ _ _ _ _ _ _ a a a a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 14; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 9; i++) {
			deque.removeFirst();
		}
		deque.addAll(collection);
		for (int i = 0; i < 3; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : ? a a a _ _ _ _ _ _ a a a a a ? //
		// expect: a a _ _ _ _ _ _ _ _ a a a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 10; i++) {
			deque.removeFirst();
		}
		deque.addAll(collection);
		for (int i = 0; i < 3; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", null, null, null, null, null, null, null, null, "a", "a", "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : ? ? a a _ _ _ _ _ _ _ a a a a a //
		// expect: a a _ _ _ _ _ _ _ _ _ a a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 11; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.addAll(collection);
		deque.offer("a");
		deque.offer("a");

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", null, null, null, null, null, null, null, null, null, "a", "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#removeAll(Collection)} with the given Collection. For
	 * the sake of this test, all collections will have a size of 2.
	 * 
	 * @param collection
	 *            Collection to remove from the deque.
	 */
	@SuppressWarnings("nls")
	private void internalTestRemoveAllRightRotate(List<Object> collection) {
		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ a a ? ? a a a a _ _ //
		// expect: _ _ _ _ _ _ _ _ a a a a a a _ _ //
		// //////////////////////////////////////////
		Deque<Object> deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 8; i++) {
			deque.offer("a");
		}
		deque.addAll(collection);
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 6; i++) {
			deque.removeFirst();
		}

		deque.removeAll(collection);
		Object[] data = getInternalArray(deque);
		Object[] expected = new Object[] {null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				"a", "a", null, null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a ? ? a a a a _ _ _ _ _ _ _ _ a //
		// expect: _ a a a a a a _ _ _ _ _ _ _ _ _ //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		deque.removeFirst();
		deque.offer("a");
		for (int i = 0; i < 14; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.addAll(collection);
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {null, "a", "a", "a", "a", "a", "a", null, null, null, null, null, null,
				null, null, null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a ? ? a a a a _ _ _ _ _ _ _ a a //
		// expect: a a a a a a a _ _ _ _ _ _ _ _ _ //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 14; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.offer("a");
		deque.addAll(collection);
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", "a", "a", "a", null, null, null, null, null, null, null,
				null, null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : ? ? a a a a _ _ _ _ _ _ _ a a a //
		// expect: a a a a a a _ _ _ _ _ _ _ _ _ a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 13; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.addAll(collection);
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", "a", "a", null, null, null, null, null, null, null,
				null, null, "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : ? a a a a _ _ _ _ _ _ _ a a a ? //
		// expect: a a a a a _ _ _ _ _ _ _ _ _ a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 12; i++) {
			deque.removeFirst();
		}
		deque.addAll(collection);
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", "a", null, null, null, null, null, null, null, null,
				null, "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a a _ _ _ _ _ _ _ a a a ? ? //
		// expect: a a a a _ _ _ _ _ _ _ _ _ a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 14; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 11; i++) {
			deque.removeFirst();
		}
		deque.addAll(collection);
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}

		deque.removeAll(collection);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", null, null, null, null, null, null, null, null, null,
				"a", "a", "a" };
		assertEqualContent(expected, data);
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#remove(Object)} with the given element.
	 * 
	 * @param element
	 *            Element to remove from the deque.
	 */
	@SuppressWarnings("nls")
	private void internalTestRemoveLeftRotate(Object element) {
		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ _ a a a a ? a a a //
		// expect: _ _ _ _ _ _ _ _ a a a a a a a _ //
		// //////////////////////////////////////////
		Deque<Object> deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 12; i++) {
			deque.offer("a");
		}
		deque.offer(element);
		for (int i = 0; i < 8; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 3; i++) {
			deque.offer("a");
		}

		deque.remove(element);
		Object[] data = getInternalArray(deque);
		Object[] expected = new Object[] {null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				"a", "a", "a", null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a _ _ _ _ _ _ _ _ a a a a ? a a //
		// expect: _ _ _ _ _ _ _ _ _ a a a a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 13; i++) {
			deque.offer("a");
		}
		deque.offer(element);
		for (int i = 0; i < 9; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 3; i++) {
			deque.offer("a");
		}

		deque.remove(element);
		data = getInternalArray(deque);
		expected = new Object[] {null, null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a _ _ _ _ _ _ _ _ a a a a ? //
		// expect: a a _ _ _ _ _ _ _ _ _ a a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 11; i++) {
			deque.removeFirst();
		}
		deque.offer(element);
		for (int i = 0; i < 3; i++) {
			deque.offer("a");
		}

		deque.remove(element);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", null, null, null, null, null, null, null, null, null, "a", "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : ? a a a _ _ _ _ _ _ _ _ a a a a //
		// expect: a a a _ _ _ _ _ _ _ _ _ a a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 12; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.offer(element);
		for (int i = 0; i < 3; i++) {
			deque.offer("a");
		}

		deque.remove(element);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", null, null, null, null, null, null, null, null, null, "a",
				"a", "a", "a" };
		assertEqualContent(expected, data);
	}

	/**
	 * Tests the behavior of {@link CircularArrayDeque#remove(Object)} with the given element.
	 * 
	 * @param element
	 *            Element to remove from the deque.
	 */
	@SuppressWarnings("nls")
	private void internalTestRemoveRightRotate(Object element) {
		// //////////////////////////////////////////
		// Array : _ _ _ _ _ _ _ a a a ? a a a a _ //
		// expect: _ _ _ _ _ _ _ _ a a a a a a a _ //
		// //////////////////////////////////////////
		Deque<Object> deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 10; i++) {
			deque.offer("a");
		}
		deque.offer(element);
		for (int i = 0; i < 7; i++) {
			deque.removeFirst();
		}
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}

		deque.remove(element);
		Object[] data = getInternalArray(deque);
		Object[] expected = new Object[] {null, null, null, null, null, null, null, null, "a", "a", "a", "a",
				"a", "a", "a", null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a ? a a a a _ _ _ _ _ _ _ _ a //
		// expect: a a a a a a a _ _ _ _ _ _ _ _ _ //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 14; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.removeFirst();
		for (int i = 0; i < 2; i++) {
			deque.offer("a");
		}
		deque.offer(element);
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}

		deque.remove(element);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", "a", "a", "a", null, null, null, null, null, null, null,
				null, null };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : ? a a a a _ _ _ _ _ _ _ _ a a a //
		// expect: a a a a a _ _ _ _ _ _ _ _ _ a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 13; i++) {
			deque.removeFirst();
		}
		deque.offer("a");
		deque.offer(element);
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}

		deque.remove(element);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", "a", null, null, null, null, null, null, null, null,
				null, "a", "a" };
		assertEqualContent(expected, data);

		// //////////////////////////////////////////
		// Array : a a a a _ _ _ _ _ _ _ _ a a a ? //
		// expect: a a a a _ _ _ _ _ _ _ _ _ a a a //
		// //////////////////////////////////////////
		deque = new CircularArrayDeque<Object>();

		for (int i = 0; i < 15; i++) {
			deque.offer("a");
		}
		for (int i = 0; i < 12; i++) {
			deque.removeFirst();
		}
		deque.offer(element);
		for (int i = 0; i < 4; i++) {
			deque.offer("a");
		}

		deque.remove(element);
		data = getInternalArray(deque);
		expected = new Object[] {"a", "a", "a", "a", null, null, null, null, null, null, null, null, null,
				"a", "a", "a" };
		assertEqualContent(expected, data);
	}

	/**
	 * Returns a list containing <code>size</code> random Integers.
	 * 
	 * @param size
	 *            Size of the list to create.
	 * @return A list containing <code>size</code> random Integers.
	 */
	private List<Integer> randomIntegerList(int size) {
		List<Integer> list = new ArrayList<Integer>(size);
		for (int i = 0; i < size; i++) {
			Integer integer = getRandomInteger();
			while (list.contains(integer)) {
				integer = getRandomInteger();
			}
			list.add(integer);
		}
		return list;
	}

	/**
	 * Returns a deque containing <code>size</code> random Strings.
	 * 
	 * @param size
	 *            Size of the deque to create.
	 * @return A deque containing <code>size</code> random Strings.
	 */
	private Deque<String> randomStringDeque(int size) {
		Deque<String> deque = new CircularArrayDeque<String>(size);
		for (int i = 0; i < size; i++) {
			String s = getRandomString();
			while (deque.contains(s)) {
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
	 * @return A set containing <code>size</code> random Strings.
	 */
	private Set<String> randomStringSet(int size) {
		Set<String> set = new HashSet<String>(size);
		for (int i = 0; i < size; i++) {
			String s = getRandomString();
			while (set.contains(s)) {
				s = getRandomString();
			}
			set.add(s);
		}
		return set;
	}

	/**
	 * Reads a Deque from the given string.
	 * 
	 * @param serialized
	 *            The serialized deque we are to read.
	 * @return The read Deque.
	 */
	@SuppressWarnings("unchecked")
	private Deque<Object> readDeque(byte[] serialized) {
		try {
			InputStream bais = new ByteArrayInputStream(serialized);
			ObjectInputStream stream = new ObjectInputStream(bais);
			Object read = stream.readObject();
			stream.close();
			if (read instanceof Deque<?>) {
				return (Deque<Object>)read;
			}
			fail("The read Object wasn't a Deque"); //$NON-NLS-1$
		} catch (ClassNotFoundException e) {
			fail("Unexpected ClassNotFoundException thrown"); //$NON-NLS-1$
		} catch (IOException e) {
			fail("Unexpected IOException thrown"); //$NON-NLS-1$	
		}
		return null;
	}

	/**
	 * Serializes the given deque to a String.
	 * 
	 * @param deque
	 *            The deque we are to serialize.
	 * @return The serialized deque.
	 */
	private byte[] writeDeque(Deque<Object> deque) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream stream = new ObjectOutputStream(baos);
			stream.writeObject(deque);
			stream.close();
			return baos.toByteArray();
		} catch (IOException e) {
			fail("Unexpected IOException thrown"); //$NON-NLS-1$
		}
		return new byte[0];
	}
}
