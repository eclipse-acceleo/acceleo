/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.tests.unit.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.acceleo.common.utils.CircularArrayDeque;
import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.common.utils.Deque;

/**
 * Tests for the {@link CompactHashSet} behavior.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CompactHashSetTest extends TestCase {
	/**
	 * Tests the empty set constructor.
	 */
	public void testInstantiationEmpty() {
		Set<Object> set = new CompactHashSet<Object>();
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(16, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));
	}

	/**
	 * Tests the copy constructor of our set.
	 */
	public void testInstantiationCopy() {
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> dequeString40 = randomStringDeque(40);
		Collection<Object> duplicatesList = new ArrayList<Object>();
		for (int i = 0; i < 40; i++) {
			int dupe = i / 2;
			duplicatesList.add(Integer.valueOf(dupe));
			duplicatesList.add(String.valueOf(dupe));
		}

		Set<Object> set = new CompactHashSet<Object>(listInt10);
		assertFalse(set.isEmpty());
		assertSame(listInt10.size(), set.size());
		assertSame(getNextPowerOfTwo(listInt10.size()), getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));
		assertTrue(set.containsAll(listInt10));
		for (Integer integer : listInt10) {
			assertTrue(set.contains(integer));
		}

		set = new CompactHashSet<Object>(setString20);
		assertFalse(set.isEmpty());
		assertSame(setString20.size(), set.size());
		assertSame(getNextPowerOfTwo(setString20.size()), getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));
		assertTrue(set.containsAll(setString20));
		for (String string : setString20) {
			assertTrue(set.contains(string));
		}

		set = new CompactHashSet<Object>(dequeString40);
		assertFalse(set.isEmpty());
		assertSame(dequeString40.size(), set.size());
		assertSame(getNextPowerOfTwo(dequeString40.size()), getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));
		assertTrue(set.containsAll(dequeString40));
		for (String string : dequeString40) {
			assertTrue(set.contains(string));
		}

		set = new CompactHashSet<Object>(duplicatesList);
		final int expectedSize = duplicatesList.size() / 2;
		assertFalse(set.isEmpty());
		assertSame(expectedSize, set.size());
		assertEquals(getNextPowerOfTwo(duplicatesList.size()), getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));
		assertTrue(set.containsAll(duplicatesList));
		for (Object o : duplicatesList) {
			assertTrue(set.contains(o));
		}
	}

	/**
	 * Tests the {@link CompactHashSet#CompactHashSet(int)} constructor of our set.
	 */
	public void testInstantiationSize() {
		Set<Object> set = new CompactHashSet<Object>(10);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(16, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));

		set = new CompactHashSet<Object>(0);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(4, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));

		set = new CompactHashSet<Object>((1 << 10) - 1);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertEquals(1 << 10, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));

		set = new CompactHashSet<Object>(1 << 31);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(4, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));

		set = new CompactHashSet<Object>(-10);
		assertTrue(set.isEmpty());
		assertSame(0, set.size());
		assertSame(4, getInternalCapacity(set));
		assertEquals(0.75f, getInternalLoadFactor(set));

		try {
			set = new CompactHashSet<Object>(Integer.MAX_VALUE);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}
		try {
			/*
			 * The last possible size for our internal array is 2^30, trying to hold that much elements will
			 * yield a size of -1, which is invalid.
			 */
			set = new CompactHashSet<Object>((1 << 30) + 1);
			fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
		} catch (IndexOutOfBoundsException e) {
			// Expected
		}
	}

	/**
	 * Tests the {@link CompactHashSet#CompactHashSet(int, float)} constructor of our set.
	 */
	public void testInstantiationLoadFactor() {
		int[] validCapacities = new int[] {10, 0, (1 << 10) - 1, 1 << 31, -10 };
		float[] validLoadFactors = new float[] {0.000001f, 0.5f, 0.75f, 1f, Float.MIN_VALUE };
		int[] invalidCapacities = new int[] {Integer.MAX_VALUE, (1 << 30) + 1 };
		float[] invalidLoadFactors = new float[] {0f, -1f, 1.000001f, 5f, Float.NEGATIVE_INFINITY,
				Float.POSITIVE_INFINITY, Float.NaN, Float.MAX_VALUE };

		for (int capacity : validCapacities) {
			for (float loadFactor : validLoadFactors) {
				Set<Object> set = new CompactHashSet<Object>(capacity, loadFactor);
				assertTrue(set.isEmpty());
				assertSame(0, set.size());
				assertEquals(Math.max(4, getNextPowerOfTwo(capacity)), getInternalCapacity(set));
				assertEquals(loadFactor, getInternalLoadFactor(set));
			}
			for (float loadFactor : invalidLoadFactors) {
				try {
					new CompactHashSet<Object>(capacity, loadFactor);
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
					new CompactHashSet<Object>(capacity, loadFactor);
					fail("Expected IndexOutOfBoundsException hasn't been thrown"); //$NON-NLS-1$
				} catch (IndexOutOfBoundsException e) {
					// Expected
				}
			}
			for (float loadFactor : invalidLoadFactors) {
				try {
					new CompactHashSet<Object>(capacity, loadFactor);
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
		for (Field field : set.getClass().getDeclaredFields()) {
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
		for (Field field : set.getClass().getDeclaredFields()) {
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
	 * Creates an empty set on which to execute these tests.
	 * 
	 * @return The set to execute these tests on.
	 */
	protected Set<Object> createSet() {
		return new CompactHashSet<Object>();
	}
}
