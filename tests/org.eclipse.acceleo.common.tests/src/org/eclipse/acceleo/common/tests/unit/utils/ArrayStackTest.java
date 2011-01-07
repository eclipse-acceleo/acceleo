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
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import junit.framework.TestCase;

import org.eclipse.acceleo.common.utils.ArrayStack;
import org.eclipse.acceleo.common.utils.Stack;

/**
 * Tests for the {@link ArrayStack} behavior.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ArrayStackTest extends TestCase {
	/**
	 * Tests the two constructors of the ArrayStack that initialize an empty stack.
	 */
	public void testEmptyInstantiation() {
		Stack<Object> stack = new ArrayStack<Object>();
		assertTrue(stack.isEmpty());
		assertSame(0, stack.size());
		// Default capacity is 16
		assertEquals(16, getInternalCapacity(stack));

		stack = new ArrayStack<Object>((1 << 10) - 1);
		assertTrue(stack.isEmpty());
		assertSame(0, stack.size());
		assertEquals(1 << 10, getInternalCapacity(stack));
	}

	/**
	 * Tests that the copy constructor of the ArrayStack creates a stack with all objects of the initial
	 * collection.
	 */
	public void testCopyInstantiation() {
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> stackString40 = randomStringStack(40);

		Stack<Object> stack = new ArrayStack<Object>(listInt10);
		assertFalse(stack.isEmpty());
		assertEquals(listInt10.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(listInt10.size()), getInternalCapacity(stack));

		stack = new ArrayStack<Object>(setString20);
		assertFalse(stack.isEmpty());
		assertEquals(setString20.size(), stack.size());
		assertTrue(stack.containsAll(setString20));
		assertEquals(getNextPowerOfTwo(setString20.size()), getInternalCapacity(stack));

		stack = new ArrayStack<Object>(stackString40);
		assertFalse(stack.isEmpty());
		assertEquals(stackString40.size(), stack.size());
		assertTrue(stack.containsAll(stackString40));
		assertEquals(getNextPowerOfTwo(stackString40.size()), getInternalCapacity(stack));
	}

	/**
	 * Tests the behavior of {@link ArrayStack#add(Object)} with random elements.
	 */
	public void testAdd() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Stack<Object> stack = new ArrayStack<Object>();

		boolean modified = stack.add(integer1);
		assertTrue(modified);
		assertSame(1, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		modified = stack.add(integer2);
		assertTrue(modified);
		assertSame(2, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(integer2, stack.getLast());
		assertEquals(integer2, stack.peek());

		modified = stack.add(string1);
		assertTrue(modified);
		assertSame(3, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(string1, stack.getLast());
		assertEquals(string1, stack.peek());

		modified = stack.add(string2);
		assertTrue(modified);
		assertSame(4, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(string2, stack.getLast());
		assertEquals(string2, stack.peek());

		modified = stack.add(object1);
		assertTrue(modified);
		assertSame(5, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(object1, stack.getLast());
		assertEquals(object1, stack.peek());

		modified = stack.add(object2);
		assertTrue(modified);
		assertSame(6, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(object2, stack.getLast());
		assertEquals(object2, stack.peek());

		modified = stack.add(null);
		assertTrue(modified);
		assertSame(7, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertSame(null, stack.getLast());
		assertSame(null, stack.peek());

		stack.pop();
		assertSame(6, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(object2, stack.getLast());
		assertEquals(object2, stack.peek());

		modified = stack.add(null);
		assertTrue(modified);
		assertSame(7, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertSame(null, stack.getLast());
		assertSame(null, stack.peek());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			stack.add(rand);
			assertSame(7 + i + 1, stack.size());
			assertEquals(integer1, stack.getFirst());
			assertSame(rand, stack.getLast());
			assertSame(rand, stack.peek());
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#addAll(Collection)} with random elements.
	 */
	public void testAddAll() {
		Collection<Object> emptyCollection = Collections.emptyList();
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> stackString40 = randomStringStack(40);

		Stack<Object> stack = new ArrayStack<Object>();

		boolean modified = stack.addAll(listInt10);
		assertTrue(modified);
		assertEquals(listInt10.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));

		modified = stack.addAll(emptyCollection);
		assertFalse(modified);
		assertEquals(listInt10.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));

		modified = stack.addAll(setString20);
		assertTrue(modified);
		assertEquals(listInt10.size() + setString20.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));

		modified = stack.addAll(stackString40);
		assertTrue(modified);
		assertEquals(listInt10.size() + setString20.size() + stackString40.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertTrue(stack.containsAll(stackString40));
		int expectedCapacity = getNextPowerOfTwo(stack.size());
		assertEquals(expectedCapacity, getInternalCapacity(stack));

		for (@SuppressWarnings("unused")
		Integer val : listInt10) {
			stack.removeFirst();
		}
		assertEquals(setString20.size() + stackString40.size(), stack.size());
		assertFalse(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertTrue(stack.containsAll(stackString40));
		assertEquals(expectedCapacity, getInternalCapacity(stack));

		modified = stack.addAll(listInt10);
		assertTrue(modified);
		assertEquals(listInt10.size() + setString20.size() + stackString40.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertTrue(stack.containsAll(stackString40));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));

		modified = stack.addAll(emptyCollection);
		assertFalse(modified);
		assertEquals(listInt10.size() + setString20.size() + stackString40.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertTrue(stack.containsAll(stackString40));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));
	}

	/**
	 * Tests the behavior of {@link ArrayStack#addLast(Object)} with random elements.
	 */
	public void testAddLast() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Stack<Object> stack = new ArrayStack<Object>();

		stack.addLast(integer1);
		assertSame(1, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.addLast(integer2);
		assertSame(2, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(integer2, stack.getLast());
		assertEquals(integer2, stack.peek());

		stack.addLast(string1);
		assertSame(3, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(string1, stack.getLast());
		assertEquals(string1, stack.peek());

		stack.addLast(string2);
		assertSame(4, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(string2, stack.getLast());
		assertEquals(string2, stack.peek());

		stack.addLast(object1);
		assertSame(5, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(object1, stack.getLast());
		assertEquals(object1, stack.peek());

		stack.addLast(object2);
		assertSame(6, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(object2, stack.getLast());
		assertEquals(object2, stack.peek());

		stack.addLast(null);
		assertSame(7, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertSame(null, stack.getLast());
		assertSame(null, stack.peek());

		stack.pop();
		assertSame(6, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(object2, stack.getLast());
		assertEquals(object2, stack.peek());

		stack.addLast(null);
		assertSame(7, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertSame(null, stack.getLast());
		assertSame(null, stack.peek());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			stack.addLast(rand);
			assertSame(7 + i + 1, stack.size());
			assertEquals(integer1, stack.getFirst());
			assertSame(rand, stack.getLast());
			assertSame(rand, stack.peek());
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#addFirst(Object)} with random elements.
	 */
	public void testAddFirst() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Stack<Object> stack = new ArrayStack<Object>();

		stack.addFirst(integer1);
		assertSame(1, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.addFirst(integer2);
		assertSame(2, stack.size());
		assertEquals(integer2, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.addFirst(string1);
		assertSame(3, stack.size());
		assertEquals(string1, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.addFirst(string2);
		assertSame(4, stack.size());
		assertEquals(string2, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.addFirst(object1);
		assertSame(5, stack.size());
		assertEquals(object1, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.addFirst(object2);
		assertSame(6, stack.size());
		assertEquals(object2, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.addFirst(null);
		assertSame(7, stack.size());
		assertSame(null, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.removeFirst();
		assertSame(6, stack.size());
		assertEquals(object2, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.addFirst(null);
		assertSame(7, stack.size());
		assertSame(null, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			stack.addFirst(rand);
			assertSame(7 + i + 1, stack.size());
			assertEquals(rand, stack.getFirst());
			assertSame(integer1, stack.getLast());
			assertSame(integer1, stack.peek());
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#push(Object) with random elements.
	 */
	public void testPush() {
		Integer integer1 = getRandomInteger();
		Integer integer2 = getRandomInteger();
		String string1 = getRandomString();
		String string2 = getRandomString();
		Object object1 = new Object();
		Object object2 = new Object();

		Stack<Object> stack = new ArrayStack<Object>();

		stack.push(integer1);
		assertSame(1, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(integer1, stack.getLast());
		assertEquals(integer1, stack.peek());

		stack.push(integer2);
		assertSame(2, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(integer2, stack.getLast());
		assertEquals(integer2, stack.peek());

		stack.push(string1);
		assertSame(3, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(string1, stack.getLast());
		assertEquals(string1, stack.peek());

		stack.push(string2);
		assertSame(4, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(string2, stack.getLast());
		assertEquals(string2, stack.peek());

		stack.push(object1);
		assertSame(5, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(object1, stack.getLast());
		assertEquals(object1, stack.peek());

		stack.push(object2);
		assertSame(6, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(object2, stack.getLast());
		assertEquals(object2, stack.peek());

		stack.push(null);
		assertSame(7, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertSame(null, stack.getLast());
		assertSame(null, stack.peek());

		stack.pop();
		assertSame(6, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertEquals(object2, stack.getLast());
		assertEquals(object2, stack.peek());

		stack.push(null);
		assertSame(7, stack.size());
		assertEquals(integer1, stack.getFirst());
		assertSame(null, stack.getLast());
		assertSame(null, stack.peek());

		// Ensure we go above capacity
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			stack.push(rand);
			assertSame(7 + i + 1, stack.size());
			assertEquals(integer1, stack.getFirst());
			assertSame(rand, stack.getLast());
			assertSame(rand, stack.peek());
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#iterator()} with random elements.
	 */
	public void testIterator() {
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> stackString40 = randomStringStack(40);

		Stack<Object> stack = new ArrayStack<Object>();

		Iterator<Object> emptyIterator = stack.iterator();
		assertFalse(emptyIterator.hasNext());
		try {
			emptyIterator.next();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
		try {
			emptyIterator.remove();
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$
		} catch (UnsupportedOperationException e) {
			// expected
		}

		Iterator<Object> concurrentIterator = stack.iterator();
		stack.addFirst(null);
		try {
			concurrentIterator.next();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.remove();
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$
		} catch (UnsupportedOperationException e) {
			// expected
		}
		stack.pop();

		concurrentIterator = stack.iterator();
		stack.addLast(null);
		try {
			concurrentIterator.next();
			fail("Expected ConcurrentModificationException hasn't been thrown"); //$NON-NLS-1$
		} catch (ConcurrentModificationException e) {
			// expected
		}
		try {
			concurrentIterator.remove();
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$
		} catch (UnsupportedOperationException e) {
			// expected
		}
		stack.pop();

		stack.addAll(listInt10);
		stack.addAll(setString20);
		stack.addAll(stackString40);

		Iterator<Integer> listIterator = listInt10.iterator();
		Iterator<String> setIterator = setString20.iterator();
		Iterator<String> stackIterator = stackString40.iterator();
		Iterator<Object> containedValues = stack.iterator();
		while (listIterator.hasNext()) {
			assertEquals(listIterator.next(), containedValues.next());
		}
		try {
			containedValues.remove();
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$
		} catch (UnsupportedOperationException e) {
			// expected
		}
		assertTrue(containedValues.hasNext());
		while (setIterator.hasNext()) {
			assertEquals(setIterator.next(), containedValues.next());
		}
		try {
			containedValues.remove();
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$
		} catch (UnsupportedOperationException e) {
			// expected
		}
		assertTrue(containedValues.hasNext());
		while (stackIterator.hasNext()) {
			assertEquals(stackIterator.next(), containedValues.next());
		}
		try {
			containedValues.remove();
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$
		} catch (UnsupportedOperationException e) {
			// expected
		}
		assertFalse(containedValues.hasNext());

		for (@SuppressWarnings("unused")
		Integer val : listInt10) {
			stack.removeFirst();
		}
		stack.addAll(listInt10);

		listIterator = listInt10.iterator();
		setIterator = setString20.iterator();
		stackIterator = stackString40.iterator();
		containedValues = stack.iterator();
		while (setIterator.hasNext()) {
			assertEquals(setIterator.next(), containedValues.next());
		}
		try {
			containedValues.remove();
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$
		} catch (UnsupportedOperationException e) {
			// expected
		}
		assertTrue(containedValues.hasNext());
		while (stackIterator.hasNext()) {
			assertEquals(stackIterator.next(), containedValues.next());
		}
		try {
			containedValues.remove();
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$
		} catch (UnsupportedOperationException e) {
			// expected
		}
		assertTrue(containedValues.hasNext());
		while (listIterator.hasNext()) {
			assertEquals(listIterator.next(), containedValues.next());
		}
		try {
			containedValues.remove();
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$
		} catch (UnsupportedOperationException e) {
			// expected
		}
		assertFalse(containedValues.hasNext());
	}

	/**
	 * Tests the behavior of {@link ArrayStack#clear()} with random elements.
	 */
	public void testClear() {
		Stack<Object> stack = new ArrayStack<Object>();

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			stack.push(rand);
		}
		stack.push(null);
		assertFalse(stack.isEmpty());
		assertSame(101, stack.size());
		int expectedCapacity = getNextPowerOfTwo(stack.size());
		assertEquals(expectedCapacity, getInternalCapacity(stack));

		stack.clear();
		assertTrue(stack.isEmpty());
		assertSame(0, stack.size());
		assertEquals(expectedCapacity, getInternalCapacity(stack));
	}

	/**
	 * Tests the behavior of {@link ArrayStack#contains()} with random elements.
	 */
	public void testContains() {
		Stack<Object> stack = new ArrayStack<Object>();

		stack.push(null);
		assertTrue(stack.contains(null));
		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			stack.push(rand);
			assertTrue(stack.contains(null));
			assertTrue(stack.contains(rand));
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#containsAll()} with random elements.
	 */
	public void testContainsAll() {
		Stack<Object> stack = new ArrayStack<Object>();

		Collection<Object> objects1 = new ArrayList<Object>();
		objects1.add(null);
		objects1.addAll(randomStringStack(40));
		objects1.add(null);
		assertSame(42, objects1.size());

		stack.pushAll(objects1);
		assertSame(42, stack.size());
		assertTrue(stack.containsAll(objects1));

		Collection<Object> objects2 = new ArrayList<Object>();
		objects2.add(null);
		objects2.addAll(randomStringSet(40));
		objects2.add(null);
		assertSame(42, objects2.size());

		stack.pushAll(objects2);
		assertSame(objects1.size() + objects2.size(), stack.size());
		assertTrue(stack.containsAll(objects1));
		assertTrue(stack.containsAll(objects2));
	}

	/**
	 * Tests the behavior of {@link ArrayStack#equals()} with random elements.
	 */
	public void testEquals() {
		Stack<Object> stack1 = new ArrayStack<Object>();
		Stack<Object> stack2 = new ArrayStack<Object>();

		Collection<Object> objects1 = new ArrayList<Object>();
		objects1.add(null);
		objects1.addAll(randomStringStack(40));
		objects1.add(null);
		assertSame(42, objects1.size());

		Collection<Object> objects2 = new ArrayList<Object>();
		objects2.add(null);
		objects2.addAll(randomStringSet(40));
		objects2.add(null);
		assertSame(42, objects2.size());

		stack1.pushAll(objects1);
		stack2.pushAll(objects1);

		Stack<Object> stack3 = new ArrayStack<Object>(stack1);

		assertTrue(stack1.equals(stack2));
		assertTrue(stack1.equals(stack3));
		assertTrue(stack2.equals(stack1));
		assertTrue(stack2.equals(stack3));
		assertTrue(stack3.equals(stack1));
		assertTrue(stack3.equals(stack2));

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			stack1.push(rand);
			stack2.push(rand);
		}
		assertTrue(stack1.equals(stack2));
		assertFalse(stack1.equals(stack3));
		assertTrue(stack2.equals(stack1));
		assertFalse(stack2.equals(stack3));
		assertFalse(stack3.equals(stack1));
		assertFalse(stack3.equals(stack2));

		Object removed = stack1.pop();
		assertFalse(stack1.equals(stack2));
		assertFalse(stack1.equals(stack3));
		assertFalse(stack2.equals(stack1));
		assertFalse(stack2.equals(stack3));
		assertFalse(stack3.equals(stack1));
		assertFalse(stack3.equals(stack2));

		stack1.addFirst(removed);
		assertFalse(stack1.equals(stack2));
		assertFalse(stack1.equals(stack3));
		assertFalse(stack2.equals(stack1));
		assertFalse(stack2.equals(stack3));
		assertFalse(stack3.equals(stack1));
		assertFalse(stack3.equals(stack2));

		stack1.removeFirst();
		stack2.pop();
		assertTrue(stack1.equals(stack2));
		assertFalse(stack1.equals(stack3));
		assertTrue(stack2.equals(stack1));
		assertFalse(stack2.equals(stack3));
		assertFalse(stack3.equals(stack1));
		assertFalse(stack3.equals(stack2));

		stack1.push(removed);
		stack2.push(removed);
		assertTrue(stack1.equals(stack2));
		assertFalse(stack1.equals(stack3));
		assertTrue(stack2.equals(stack1));
		assertFalse(stack2.equals(stack3));
		assertFalse(stack3.equals(stack1));
		assertFalse(stack3.equals(stack2));
	}

	/**
	 * Tests the behavior of {@link ArrayStack#getFirst()} with random elements.
	 */
	public void testGetFirst() {
		Stack<Object> stack = new ArrayStack<Object>();

		try {
			stack.getFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		stack.push(null);
		assertSame(null, stack.getFirst());
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			stack.push(rand);
			assertSame(null, stack.getFirst());
		}
		List<Object> lastAdded = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			stack.addFirst(rand);
			lastAdded.add(rand);
			assertEquals(rand, stack.getFirst());
		}
		for (int i = 0; i < 20; i++) {
			stack.removeFirst();
			if (i < 19) {
				assertEquals(lastAdded.get(lastAdded.size() - i - 2), stack.getFirst());
			} else {
				assertSame(null, stack.getFirst());
			}
		}

		stack.clear();
		try {
			stack.getFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#getLast()} with random elements.
	 */
	public void testGetLast() {
		Stack<Object> stack = new ArrayStack<Object>();

		try {
			stack.getLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		stack.push(null);
		assertSame(null, stack.getLast());
		List<Object> lastAdded = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			stack.push(rand);
			lastAdded.add(rand);
			assertEquals(rand, stack.getLast());
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			stack.addFirst(rand);
			assertEquals(lastAdded.get(lastAdded.size() - 1), stack.getLast());
		}
		for (int i = 0; i < 20; i++) {
			stack.removeLast();
			if (i < 19) {
				assertEquals(lastAdded.get(lastAdded.size() - i - 2), stack.getLast());
			} else {
				assertSame(null, stack.getLast());
			}
		}

		stack.clear();
		try {
			stack.getLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#hashCode()} with random elements.
	 */
	public void testHashCode() {
		Stack<Object> stack1 = new ArrayStack<Object>();
		Stack<Object> stack2 = new ArrayStack<Object>();

		Collection<Object> objects1 = new ArrayList<Object>();
		objects1.add(null);
		objects1.addAll(randomStringStack(40));
		objects1.add(null);
		assertSame(42, objects1.size());

		Collection<Object> objects2 = new ArrayList<Object>();
		objects2.add(null);
		objects2.addAll(randomStringSet(40));
		objects2.add(null);
		assertSame(42, objects2.size());

		stack1.pushAll(objects1);
		stack2.pushAll(objects1);

		Stack<Object> stack3 = new ArrayStack<Object>(stack1);

		assertEquals(stack1.hashCode(), stack2.hashCode());
		assertEquals(stack1.hashCode(), stack3.hashCode());
		assertEquals(stack2.hashCode(), stack1.hashCode());
		assertEquals(stack2.hashCode(), stack3.hashCode());
		assertEquals(stack3.hashCode(), stack1.hashCode());
		assertEquals(stack3.hashCode(), stack2.hashCode());

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			stack1.push(rand);
			stack2.push(rand);
		}
		assertEquals(stack1.hashCode(), stack2.hashCode());
		assertFalse(stack1.hashCode() == stack3.hashCode());
		assertEquals(stack2.hashCode(), stack1.hashCode());
		assertFalse(stack2.hashCode() == stack3.hashCode());
		assertFalse(stack3.hashCode() == stack1.hashCode());
		assertFalse(stack3.hashCode() == stack2.hashCode());

		Object removed = stack1.pop();
		assertFalse(stack1.hashCode() == stack2.hashCode());
		assertFalse(stack1.hashCode() == stack3.hashCode());
		assertFalse(stack2.hashCode() == stack1.hashCode());
		assertFalse(stack2.hashCode() == stack3.hashCode());
		assertFalse(stack3.hashCode() == stack1.hashCode());
		assertFalse(stack3.hashCode() == stack2.hashCode());

		stack1.addFirst(removed);
		assertFalse(stack1.hashCode() == stack2.hashCode());
		assertFalse(stack1.hashCode() == stack3.hashCode());
		assertFalse(stack2.hashCode() == stack1.hashCode());
		assertFalse(stack2.hashCode() == stack3.hashCode());
		assertFalse(stack3.hashCode() == stack1.hashCode());
		assertFalse(stack3.hashCode() == stack2.hashCode());

		stack1.removeFirst();
		stack2.pop();
		assertEquals(stack1.hashCode(), stack2.hashCode());
		assertFalse(stack1.hashCode() == stack3.hashCode());
		assertEquals(stack2.hashCode(), stack1.hashCode());
		assertFalse(stack2.hashCode() == stack3.hashCode());
		assertFalse(stack3.hashCode() == stack1.hashCode());
		assertFalse(stack3.hashCode() == stack2.hashCode());

		stack1.push(removed);
		stack2.push(removed);
		assertEquals(stack1.hashCode(), stack2.hashCode());
		assertFalse(stack1.hashCode() == stack3.hashCode());
		assertEquals(stack2.hashCode(), stack1.hashCode());
		assertFalse(stack2.hashCode() == stack3.hashCode());
		assertFalse(stack3.hashCode() == stack1.hashCode());
		assertFalse(stack3.hashCode() == stack2.hashCode());
	}

	/**
	 * Tests the behavior of {@link ArrayStack#isEmpty()} with random elements.
	 */
	public void testIsEmpty() {
		Stack<Object> stack = new ArrayStack<Object>();
		assertTrue(stack.isEmpty());

		stack.push(null);
		assertFalse(stack.isEmpty());

		for (int i = 0; i < 100; i++) {
			stack.push(getRandomString());
			assertFalse(stack.isEmpty());
		}
		for (int i = 0; i < 100; i++) {
			stack.pop();
			assertFalse(stack.isEmpty());
		}

		stack.pop();
		assertTrue(stack.isEmpty());

		for (int i = 0; i < 100; i++) {
			stack.push(getRandomString());
			assertFalse(stack.isEmpty());
		}

		stack.clear();
		assertTrue(stack.isEmpty());
	}

	/**
	 * Tests the behavior of {@link ArrayStack#peek()} with random elements.
	 */
	public void testPeek() {
		Stack<Object> stack = new ArrayStack<Object>();

		try {
			stack.peek();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}

		stack.push(null);
		assertSame(null, stack.peek());
		List<Object> lastAdded = new ArrayList<Object>();
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			stack.push(rand);
			lastAdded.add(rand);
			assertEquals(rand, stack.peek());
		}
		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			stack.addFirst(rand);
			assertEquals(lastAdded.get(lastAdded.size() - 1), stack.peek());
		}
		for (int i = 0; i < 20; i++) {
			stack.removeLast();
			if (i < 19) {
				assertEquals(lastAdded.get(lastAdded.size() - i - 2), stack.peek());
			} else {
				assertSame(null, stack.peek());
			}
		}

		stack.clear();
		try {
			stack.peek();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#pop()} with random elements.
	 */
	public void testPop() {
		Stack<Object> stack = new ArrayStack<Object>();
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
		assertFalse(stack.containsAll(objectsFirst));
		assertFalse(stack.containsAll(objectsLast));

		try {
			stack.pop();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		for (Object o : objectsLast) {
			stack.push(o);
		}
		for (Object o : objectsFirst) {
			stack.addFirst(o);
		}
		assertTrue(stack.containsAll(objectsFirst));
		assertTrue(stack.containsAll(objectsLast));

		for (int i = objectsLast.size() - 1; i >= 0; i--) {
			assertEquals(objectsLast.get(i), stack.pop());
		}
		for (Object o : objectsFirst) {
			assertEquals(o, stack.pop());
		}

		try {
			stack.pop();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		stack.pushAll(objectsFirst);
		stack.pushAll(objectsLast);
		stack.clear();
		try {
			stack.pop();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#pushAll(Collection)} with random elements.
	 */
	public void testPushAll() {
		Collection<Object> emptyCollection = Collections.emptyList();
		Collection<Integer> listInt10 = randomIntegerList(10);
		Collection<String> setString20 = randomStringSet(20);
		Collection<String> stackString40 = randomStringStack(40);

		Stack<Object> stack = new ArrayStack<Object>();

		stack.pushAll(listInt10);
		assertEquals(listInt10.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));

		stack.pushAll(emptyCollection);
		assertEquals(listInt10.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));

		stack.pushAll(setString20);
		assertEquals(listInt10.size() + setString20.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));

		stack.pushAll(stackString40);
		assertEquals(listInt10.size() + setString20.size() + stackString40.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertTrue(stack.containsAll(stackString40));
		int expectedCapacity = getNextPowerOfTwo(stack.size());
		assertEquals(expectedCapacity, getInternalCapacity(stack));

		for (@SuppressWarnings("unused")
		Integer val : listInt10) {
			stack.removeFirst();
		}
		assertEquals(setString20.size() + stackString40.size(), stack.size());
		assertFalse(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertTrue(stack.containsAll(stackString40));
		assertEquals(expectedCapacity, getInternalCapacity(stack));

		stack.pushAll(listInt10);
		assertEquals(listInt10.size() + setString20.size() + stackString40.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertTrue(stack.containsAll(stackString40));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));

		stack.pushAll(emptyCollection);
		assertEquals(listInt10.size() + setString20.size() + stackString40.size(), stack.size());
		assertTrue(stack.containsAll(listInt10));
		assertTrue(stack.containsAll(setString20));
		assertTrue(stack.containsAll(stackString40));
		assertEquals(getNextPowerOfTwo(stack.size()), getInternalCapacity(stack));
	}

	/**
	 * Tests the behavior of {@link ArrayStack#remove(Object)} with random elements.
	 */
	public void testRemove() {
		Stack<Object> stack = new ArrayStack<Object>();
		List<Object> objects = new ArrayList<Object>();

		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objects.add(rand);
		}
		assertFalse(stack.containsAll(objects));

		for (Object o : objects) {
			try {
				stack.remove(o);
				fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$			
			} catch (UnsupportedOperationException e) {
				// expected
			}
		}

		for (Object o : objects) {
			stack.push(o);
		}
		assertTrue(stack.containsAll(objects));

		for (Object o : objects) {
			try {
				stack.remove(o);
				fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$			
			} catch (UnsupportedOperationException e) {
				// expected
			}
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#removeAll(Collection)} with random elements.
	 */
	public void testRemoveAll() {
		Stack<Object> stack = new ArrayStack<Object>();
		List<Object> objects = new ArrayList<Object>();

		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objects.add(rand);
		}
		assertFalse(stack.containsAll(objects));

		try {
			stack.removeAll(objects);
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$			
		} catch (UnsupportedOperationException e) {
			// expected
		}

		for (Object o : objects) {
			stack.push(o);
		}
		assertTrue(stack.containsAll(objects));

		try {
			stack.removeAll(objects);
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$			
		} catch (UnsupportedOperationException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#removeFirst()} with random elements.
	 */
	public void testRemoveFirst() {
		Stack<Object> stack = new ArrayStack<Object>();
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
		assertFalse(stack.containsAll(objectsFirst));
		assertFalse(stack.containsAll(objectsLast));

		try {
			stack.removeFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		for (Object o : objectsLast) {
			stack.push(o);
		}
		for (Object o : objectsFirst) {
			stack.addFirst(o);
		}
		assertTrue(stack.containsAll(objectsFirst));
		assertTrue(stack.containsAll(objectsLast));

		for (int i = objectsFirst.size() - 1; i >= 0; i--) {
			assertEquals(objectsFirst.get(i), stack.removeFirst());
		}
		for (Object o : objectsLast) {
			assertEquals(o, stack.removeFirst());
		}

		try {
			stack.removeFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		stack.pushAll(objectsFirst);
		stack.pushAll(objectsLast);
		stack.clear();
		try {
			stack.removeFirst();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#removeLast()} with random elements.
	 */
	public void testRemoveLast() {
		Stack<Object> stack = new ArrayStack<Object>();
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
		assertFalse(stack.containsAll(objectsFirst));
		assertFalse(stack.containsAll(objectsLast));

		try {
			stack.removeLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		for (Object o : objectsLast) {
			stack.push(o);
		}
		for (Object o : objectsFirst) {
			stack.addFirst(o);
		}
		assertTrue(stack.containsAll(objectsFirst));
		assertTrue(stack.containsAll(objectsLast));

		for (int i = objectsLast.size() - 1; i >= 0; i--) {
			assertEquals(objectsLast.get(i), stack.removeLast());
		}
		for (Object o : objectsFirst) {
			assertEquals(o, stack.removeLast());
		}

		try {
			stack.removeLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}

		stack.pushAll(objectsFirst);
		stack.pushAll(objectsLast);
		stack.clear();
		try {
			stack.removeLast();
			fail("Expected NoSuchElementException hasn't been thrown"); //$NON-NLS-1$			
		} catch (NoSuchElementException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#retainAll(Collection)} with random elements.
	 */
	public void testRetainAll() {
		Stack<Object> stack = new ArrayStack<Object>();
		List<Object> objects = new ArrayList<Object>();

		for (int i = 0; i < 20; i++) {
			String rand = getRandomString();
			objects.add(rand);
		}
		assertFalse(stack.containsAll(objects));

		try {
			stack.retainAll(objects);
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$			
		} catch (UnsupportedOperationException e) {
			// expected
		}

		for (Object o : objects) {
			stack.push(o);
		}
		assertTrue(stack.containsAll(objects));

		try {
			stack.retainAll(objects);
			fail("Expected UnsupportedOperationException hasn't been thrown"); //$NON-NLS-1$			
		} catch (UnsupportedOperationException e) {
			// expected
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#size()} with random elements.
	 */
	public void testSize() {
		Stack<Object> stack = new ArrayStack<Object>();

		int size = 0;
		assertSame(size, stack.size());

		stack.push(null);
		assertSame(++size, stack.size());

		for (int i = 0; i < 100; i++) {
			String rand = getRandomString();
			stack.push(rand);
			assertSame(++size, stack.size());
		}

		for (int i = 0; i < 100; i++) {
			stack.pop();
			assertSame(--size, stack.size());
		}
	}

	/**
	 * Tests the behavior of {@link ArrayStack#toArray()} with random elements.
	 */
	public void testToArray() {
		Stack<Object> stack = new ArrayStack<Object>();
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

		Object[] array = stack.toArray();
		assertSame(0, array.length);

		stack.pushAll(objectsLast);
		array = stack.toArray();
		assertSame(stack.size(), array.length);
		Iterator<Object> stackIterator = stack.iterator();
		for (int i = 0; i < array.length; i++) {
			assertEquals(stackIterator.next(), array[i]);
		}
		stack.clear();

		stack.pushAll(objectsLast);
		for (Object o : objectsFirst) {
			stack.addFirst(o);
		}
		array = stack.toArray();
		assertSame(stack.size(), array.length);
		stackIterator = stack.iterator();
		for (int i = 0; i < array.length; i++) {
			assertEquals(stackIterator.next(), array[i]);
		}

		stack.clear();
		array = stack.toArray();
		assertEquals(getNextPowerOfTwo(40), getInternalCapacity(stack));
		assertSame(0, array.length);
	}

	/**
	 * Tests the behavior of {@link ArrayStack#toArray(Object[])} with random elements.
	 */
	public void testParameterizedToArray() {
		Stack<Object> stack = new ArrayStack<Object>();
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

		Object[] array = stack.toArray(new Object[0]);
		assertSame(0, array.length);
		array = stack.toArray(new Object[21]);
		assertSame(21, array.length);
		for (Object o : array) {
			assertNull(o);
		}

		stack.pushAll(objectsLast);
		array = stack.toArray(new Object[0]);
		assertSame(stack.size(), array.length);
		Iterator<Object> stackIterator = stack.iterator();
		for (int i = 0; i < stack.size(); i++) {
			assertEquals(stackIterator.next(), array[i]);
		}
		array = new String[21];
		stack.toArray(array);
		assertSame(21, array.length);
		stackIterator = stack.iterator();
		for (int i = 0; i < stack.size(); i++) {
			assertEquals(stackIterator.next(), array[i]);
		}
		stack.clear();

		stack.pushAll(objectsLast);
		for (Object o : objectsFirst) {
			stack.addFirst(o);
		}
		array = stack.toArray(new Object[0]);
		assertSame(stack.size(), array.length);
		stackIterator = stack.iterator();
		for (int i = 0; i < stack.size(); i++) {
			assertEquals(stackIterator.next(), array[i]);
		}
		array = new String[41];
		stack.toArray(array);
		assertSame(41, array.length);
		stackIterator = stack.iterator();
		for (int i = 0; i < stack.size(); i++) {
			assertEquals(stackIterator.next(), array[i]);
		}

		stack.clear();
		assertEquals(getNextPowerOfTwo(40), getInternalCapacity(stack));
		array = stack.toArray(new Object[0]);
		assertSame(0, array.length);
		array = stack.toArray(new Object[41]);
		assertSame(41, array.length);
		for (Object o : array) {
			assertNull(o);
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
		// Start with 16 : the default initial capacity of the ArrayStack.
		int powerOfTwo = 16;
		while (number >= powerOfTwo) {
			powerOfTwo = powerOfTwo << 1;
		}
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
			list.add(getRandomInteger());
		}
		return list;
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
			set.add(getRandomString());
		}
		return set;
	}

	/**
	 * Returns a stack containing <code>size</code> random Strings.
	 * 
	 * @param size
	 *            Size of the stack to create.
	 * @return A stack containing <code>size</code> random Strings.
	 */
	private Stack<String> randomStringStack(int size) {
		Stack<String> stack = new ArrayStack<String>(size);
		for (int i = 0; i < size; i++) {
			stack.add(getRandomString());
		}
		return stack;
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
	 * Makes the "data" field of the given stack public in order to retrieve its current size.
	 * 
	 * @param stack
	 *            The stack we need the capacity of.
	 * @return The capacity of the given stack.
	 */
	private int getInternalCapacity(Stack<?> stack) {
		Field dataField = null;
		for (Field field : stack.getClass().getDeclaredFields()) {
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
			data = (Object[])dataField.get(stack);
		} catch (IllegalArgumentException e) {
			// carry on
		} catch (IllegalAccessException e) {
			// carry on
		}
		if (data == null) {
			fail("could not retrieve capacity of " + stack); //$NON-NLS-1$
		} else {
			return data.length;
		}
		return -1;
	}
}
