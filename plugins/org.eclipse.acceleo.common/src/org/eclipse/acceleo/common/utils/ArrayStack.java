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
package org.eclipse.acceleo.common.utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of the {@link Stack} interface.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <E>
 *            Type of the elements contained by this Stack.
 * @since 3.0
 */
public class ArrayStack<E> implements Stack<E>, Externalizable {
	/** Serial version UID, this will be used during deserialization. */
	private static final long serialVersionUID = 5259962911151126606L;

	/** The default initial capacity of our ArrayStacks. */
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * Array in which the elements of this stack are stored. The capacity of an {@link ArrayStack} is the
	 * length of this array, which will be maintained as a power of two. Note that the array will never be
	 * full as will double its size whenever we reach its full capacity.
	 */
	private transient E[] data;

	/** Index in the backing array of the first element of this stack. */
	private transient int head;

	/** Index in the backing array of the last element of this stack. */
	private transient int tail;

	/**
	 * Constructs an empty stack with an initial capacity of {@value #DEFAULT_INITIAL_CAPACITY} elements.
	 */
	@SuppressWarnings("unchecked")
	public ArrayStack() {
		data = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
	}

	/**
	 * Constructs a stack holding a copy of the given collection. The order of the elements in this stack will
	 * be the same as the given collection's iterator order.
	 * 
	 * @param collection
	 *            The collection whose elements are to be pushed on this stack.
	 */
	public ArrayStack(Collection<? extends E> collection) {
		initialize(collection.size());
		pushAll(collection);
	}

	/**
	 * Constructs an empty stack with an initial capacity sufficient to hold the given number of elements.
	 * 
	 * @param elementCount
	 *            The number of elements we expect to hold in this stack.
	 */
	public ArrayStack(int elementCount) {
		initialize(elementCount);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(E element) {
		addLast(element);
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends E> collection) {
		for (E element : collection) {
			addLast(element);
		}
		return !collection.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#addFirst(java.lang.Object)
	 */
	public synchronized void addFirst(E element) {
		final int mask = data.length - 1;
		head = (head - 1) & mask;
		data[head] = element;
		if (head == tail) {
			doubleCapacity();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#addLast(java.lang.Object)
	 */
	public synchronized void addLast(E element) {
		final int mask = data.length - 1;
		data[tail] = element;
		tail = (tail + 1) & mask;
		if (head == tail) {
			doubleCapacity();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Collection#clear()
	 */
	public void clear() {
		final int mask = data.length - 1;
		for (int i = head; i != tail; i = (i + 1) & mask) {
			data[i] = null;
		}
		head = 0;
		tail = 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		final int mask = data.length - 1;
		for (int i = head; i != tail; i = (i + 1) & mask) {
			E current = data[i];
			if ((o == null && current == null) || o.equals(current)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> collection) {
		for (Object element : collection) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		boolean result = other instanceof ArrayStack<?> && size() == ((ArrayStack<?>)other).size();
		if (other != this) {
			Iterator<E> thisItr = iterator();
			Iterator<?> otherItr = ((ArrayStack<?>)other).iterator();
			while (thisItr.hasNext() && otherItr.hasNext()) {
				E thisObj = thisItr.next();
				Object otherObj = otherItr.next();
				if ((thisObj == null && otherObj != null) || (thisObj != null && !thisObj.equals(otherObj))) {
					return false;
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#getFirst()
	 */
	public E getFirst() {
		if (head == tail) {
			throw new NoSuchElementException();
		}
		return data[head];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#getLast()
	 */
	public E getLast() {
		if (head == tail) {
			throw new NoSuchElementException();
		}
		final int mask = data.length - 1;
		return data[(tail - 1) & mask];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int hashCode = 1;
		final int mask = data.length - 1;
		for (int i = head; i != tail; i = (i + 1) & mask) {
			hashCode = prime * hashCode;
			if (data[i] != null) {
				hashCode += data[i].hashCode();
			}
		}
		return hashCode;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty() {
		return head == tail;
	}

	/**
	 * Returns an iterator over the elements in this stack. The elements will be iterated over in the order
	 * they were pushed in the stack. The returned iterator does not support the remove operation.
	 * 
	 * @return An <tt>Iterator</tt> over the elements in this stack.
	 */
	public Iterator<E> iterator() {
		return new StackIterator();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#peek()
	 */
	public E peek() {
		return getLast();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#pop()
	 */
	public synchronized E pop() {
		return removeLast();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#push(java.lang.Object)
	 */
	public synchronized void push(E element) {
		addLast(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#pushAll(java.util.Collection)
	 */
	public synchronized void pushAll(Collection<? extends E> collection) {
		addAll(collection);
	}

	/**
	 * Removing a random element from this tack is not supported.
	 * 
	 * @param element
	 *            Element that is to be removed from this stack.
	 * @return <code>true</code> if the stack has been modified by this operation.
	 * @throws UnsupportedOperationException
	 *             Remove is not supported by this stack.
	 */
	public boolean remove(Object element) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * Removing arbitrary elements from the stack is not supported.
	 * 
	 * @param collection
	 *            The collection whose elements are to be removed from the stack.
	 * @return <code>true</code> if the stack has been modified by this operation.
	 * @throws UnsupportedOperationException
	 *             Remove is not supported by this stack.
	 */
	public boolean removeAll(Collection<?> collection) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#removeFirst()
	 */
	public synchronized E removeFirst() {
		if (head == tail) {
			throw new NoSuchElementException();
		}
		final int mask = data.length - 1;
		final E result = data[head];
		data[head] = null;
		head = (head + 1) & mask;
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Stack#removeLast()
	 */
	public synchronized E removeLast() {
		if (head == tail) {
			throw new NoSuchElementException();
		}
		final int mask = data.length - 1;
		tail = (tail - 1) & mask;
		final E result = data[tail];
		data[tail] = null;
		return result;
	}

	/**
	 * Removing arbitrary elements from the stack is not supported.
	 * 
	 * @param collection
	 *            The collection whose elements are to be retained in the stack.
	 * @return <code>true</code> if the stack has been modified by this operation.
	 * @throws UnsupportedOperationException
	 *             Remove is not supported by this stack.
	 */
	public boolean retainAll(Collection<?> collection) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Collection#size()
	 */
	public int size() {
		return (tail - head) & (data.length - 1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		return toArray(new Object[size()]);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Collection#toArray(T[])
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T[] toArray(T[] a) {
		final T[] temp;
		if (a.length > size()) {
			temp = a;
		} else {
			temp = (T[])Array.newInstance(a.getClass().getComponentType(), size());
		}
		/*
		 * "head" could be located somewhere _after_ tail as we could have pushed elements on the back of our
		 * stack. Reorder them now.
		 */
		if (head < tail) {
			System.arraycopy(data, head, temp, 0, size());
		} else if (head != tail) {
			int headLength = data.length - head;
			// Copy all elements at the right of "head"...
			System.arraycopy(data, head, temp, 0, headLength);
			// ...Then all elements at the left of "head"
			System.arraycopy(data, 0, temp, headLength, tail);
		}
		return temp;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Externalizable#writeExternal(java.io.ObjectOutput)
	 */
	public void writeExternal(ObjectOutput out) throws IOException {
		final int mask = data.length - 1;
		out.writeInt(size());
		for (int i = head; i != tail; i = (i + 1) & mask) {
			out.writeObject(data[i]);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.io.Externalizable#readExternal(java.io.ObjectInput)
	 */
	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		int size = in.readInt();
		initialize(size);
		head = 0;
		tail = size;

		for (int i = 0; i < size; i++) {
			data[i] = (E)in.readObject();
		}
	}

	/**
	 * Double the capacity of this stack.
	 */
	@SuppressWarnings("unchecked")
	private void doubleCapacity() {
		final int capacity = data.length;
		final int newCapacity = capacity << 1;
		if (newCapacity < 0) {
			// FIXME throw out of bounds
		}

		E[] temp = (E[])new Object[newCapacity];
		int headLength = data.length - head;
		// Copy all elements at the right of "head"...
		System.arraycopy(data, head, temp, 0, headLength);
		// ...Then all elements at the left of "head"
		System.arraycopy(data, 0, temp, headLength, head);

		data = temp;
		head = 0;
		tail = capacity;
	}

	/**
	 * Initializes the backing array to a capacity sufficient to hold the given number of elements.
	 * 
	 * @param elementCount
	 *            The number of elements we expect to hold in this stack.
	 */
	@SuppressWarnings("unchecked")
	private void initialize(int elementCount) {
		int initialCapacity = DEFAULT_INITIAL_CAPACITY;
		while (elementCount >= initialCapacity) {
			initialCapacity = initialCapacity << 1;
		}
		data = (E[])new Object[initialCapacity];
	}

	/**
	 * Implementation of an {@link Iterator} for the {@link ArrayStack}. It will allow iteration from the
	 * first element of the stack ({@link #head}) to the last ({@link #tail}). This iterator does not support
	 * the remove operation.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class StackIterator implements Iterator<E> {
		/** Keeps track of the value of {@link #head} when this iterator was created. */
		private int initialHead = head;

		/** Keeps track of the value of {@link #tail} when this iterator was created. */
		private int initialTail = tail;

		/** Index of the next element to be returned by this iterator. */
		private int next = head;

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return next != tail;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#next()
		 */
		public E next() {
			checkComodification();
			if (head == tail) {
				throw new NoSuchElementException();
			}
			final int mask = data.length - 1;
			final E result = data[next];
			next = (next + 1) & mask;
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}

		/**
		 * Checks for modification of the stack since the creation of this iterator.
		 * 
		 * @throws ConcurrentModificationException
		 *             Thrown if the stack this iterator traverses has been modified since the creation of the
		 *             iterator.
		 */
		private void checkComodification() throws ConcurrentModificationException {
			if (head != initialHead || tail != initialTail) {
				throw new ConcurrentModificationException();
			}
		}
	}
}
