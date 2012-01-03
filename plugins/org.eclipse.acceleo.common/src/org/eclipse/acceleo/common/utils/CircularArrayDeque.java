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
package org.eclipse.acceleo.common.utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/**
 * Array-based implementation of a double-ended queue.
 * <p>
 * This {@link Deque} accepts <code>null</code> values, however as the {@link java.util.Queue} interfaces
 * specifies it, calls to {@link #peek()} or {@link #poll()} will both return <code>null</code> when this
 * Queue is empty. Calls to {@link #peekFirst()} and {@link #peekLast()}, however, will throw
 * {@link NoSuchElementException}s when the queue is empty.
 * </p>
 * <p>
 * Most operations on the {@link CircularArrayDeque} execute in constant time. This includes
 * {@link #addFirst(Object)}, {@link #addLast(Object)}, {@link #removeFirst()}, {@link #removeLast()} and
 * {@link #get(int)}. Other random-access operations such as {@link #add(Object)} and {@link #remove(Object)}
 * execute in amortized linear time, with the constant being lower than for the {@link java.util.ArrayList}.
 * </p>
 * <p>
 * This implementation of a double-ended queue is backed by an array which size we'll always maintain to be a
 * power of two. Adding and removing elements from both ends we'll end up moving the "head" and "tail"
 * pointers by one to either the left or the right. This deque is considered empty when these two pointers are
 * equal.
 * </p>
 * <p>
 * Whenever "head" or "tail" go below 0 or above the underlying array's length, we'll use a modulo in order
 * for it to circle through. As we know the length of the array is always a power of two, we're optimizing the
 * modulo by using a binary mask (x % 2^y == x & (2^y - 1)).
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <E>
 *            Type of the elements contained by this Deque.
 * @since 3.0
 */
public final class CircularArrayDeque<E> extends AbstractList<E> implements Deque<E>, Externalizable, RandomAccess {
	/** The default initial capacity of our Deques. */
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	/** Serial version UID, this will be used during deserialization. */
	private static final long serialVersionUID = 5259962911151126606L;

	/**
	 * Array in which the elements of this deque are stored. The capacity of a {@link CircularArrayDeque} is
	 * the length of this array, which will be maintained as a power of two. Note that the array will never be
	 * full as will double its size whenever we reach its full capacity.
	 */
	transient E[] data;

	/** Index in the backing array of the first element of this deque. */
	transient int head;

	/** Index in the backing array of the last element of this deque. */
	transient int tail;

	/**
	 * Constructs an empty deque with an initial capacity of {@value #DEFAULT_INITIAL_CAPACITY} elements.
	 */
	@SuppressWarnings("unchecked")
	public CircularArrayDeque() {
		data = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
	}

	/**
	 * Constructs a deque holding a copy of the given collection. The order of the elements in this deque will
	 * be the same as the given collection's iterator order.
	 * 
	 * @param collection
	 *            The collection whose elements are to be pushed on this deque.
	 */
	public CircularArrayDeque(Collection<? extends E> collection) {
		initialize(collection.size());
		addAll(collection);
	}

	/**
	 * Constructs an empty deque with an initial capacity sufficient to hold the given number of elements.
	 * <p>
	 * If the expected element count is negative, the initial capacity of this deque will be <code>4</code>.
	 * </p>
	 * 
	 * @param elementCount
	 *            The number of elements we expect to hold in this deque.
	 */
	public CircularArrayDeque(int elementCount) {
		initialize(elementCount);
	}

	/**
	 * Get the closest power of two greater than <code>number</code>.
	 * 
	 * @param number
	 *            Number for which we seek the closest power of two.
	 * @return The closest power of two greater than <code>number</code>.
	 */
	private static int getNextPowerOfTwo(int number) {
		// Checkstyle needs variables...
		final int pow3 = 8;
		final int pow4 = 16;
		int powerOfTwo = number - 1;
		powerOfTwo |= powerOfTwo >> 1;
		powerOfTwo |= powerOfTwo >> 2;
		powerOfTwo |= powerOfTwo >> 4;
		powerOfTwo |= powerOfTwo >> pow3;
		powerOfTwo |= powerOfTwo >> pow4;
		powerOfTwo++;
		return powerOfTwo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#add(java.lang.Object)
	 */
	@Override
	public boolean add(E element) {
		addLast(element);
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	@Override
	public void add(int index, E element) {
		int size = size();

		if (index == 0) {
			addFirst(element);
		} else if (index == size) {
			addLast(element);
		} else {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException(String.valueOf(index));
			}

			/*
			 * If we are close to head, we'll move elements to the left. If we are closer to tail, we'll move
			 * them to the right.
			 */
			final int mask = data.length - 1;
			final int insertionIndex;

			if (index > (size / 2)) {
				insertionIndex = (head + index) & mask;
				for (int i = tail; i != insertionIndex; i = (i - 1) & mask) {
					data[i] = data[(i - 1) & mask];
				}
				tail = (tail + 1) & mask;
			} else {
				head = (head - 1) & mask;
				insertionIndex = (head + index) & mask;
				for (int i = head; i != insertionIndex; i = (i + 1) & mask) {
					data[i] = data[(i + 1) & mask];
				}
			}

			data[insertionIndex] = element;
			modCount++;
			if (head == tail) {
				doubleCapacity();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> collection) {
		for (E element : collection) {
			addLast(element);
		}
		return !collection.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean addAll(int index, Collection<? extends E> collection) {
		int size = size();
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		if (collection.isEmpty()) {
			return false;
		}

		ensureCapacity(size + collection.size());

		if (index == 0) {
			E[] newValues = (E[])new Object[collection.size()];
			newValues = collection.toArray(newValues);
			for (int i = newValues.length - 1; i >= 0; i--) {
				addFirst(newValues[i]);
			}
		} else if (index == size) {
			Iterator<? extends E> iterator = collection.iterator();
			while (iterator.hasNext()) {
				addLast(iterator.next());
			}
		} else {
			/*
			 * If we are close to head, we'll move elements to the left. If we are closer to tail, we'll move
			 * them to the right.
			 */
			final int mask = data.length - 1;
			int insertionIndex;

			if (index > (size / 2)) {
				insertionIndex = (head + index) & mask;
				tail = (tail + collection.size()) & mask;
				for (int i = tail; i != ((insertionIndex + collection.size()) & mask); i = (i - 1) & mask) {
					data[(i - 1) & mask] = data[(i - 1 - collection.size()) & mask];
				}
			} else {
				head = (head - collection.size()) & mask;
				insertionIndex = (head + index) & mask;
				for (int i = head; i != insertionIndex; i = (i + 1) & mask) {
					data[i] = data[(i + collection.size()) & mask];
				}
			}

			Iterator<? extends E> iterator = collection.iterator();
			while (iterator.hasNext()) {
				data[insertionIndex] = iterator.next();
				modCount++;
				insertionIndex = (insertionIndex + 1) & mask;
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#addFirst(java.lang.Object)
	 */
	public void addFirst(E element) {
		head = (head - 1) & (data.length - 1);
		data[head] = element;
		modCount++;
		if (head == tail) {
			doubleCapacity();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#addLast(java.lang.Object)
	 */
	public void addLast(E element) {
		data[tail] = element;
		modCount++;
		tail = (tail + 1) & (data.length - 1);
		if (head == tail) {
			doubleCapacity();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#clear()
	 */
	@Override
	public void clear() {
		modCount++;
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
	 * @see java.util.List#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	@Override
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
	 * @see java.util.Queue#element()
	 */
	public E element() {
		return getFirst();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#get(int)
	 */
	@Override
	public E get(int index) {
		rangeCheck(index);

		return data[(head + index) & (data.length - 1)];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#getFirst()
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
	 * @see org.eclipse.acceleo.common.utils.Deque#getLast()
	 */
	public E getLast() {
		if (head == tail) {
			throw new NoSuchElementException();
		}
		return data[(tail - 1) & data.length - 1];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	@Override
	public int indexOf(Object o) {
		int result = -1;
		final int mask = data.length - 1;
		if (o == null) {
			for (int i = head; i != tail; i = (i + 1) & mask) {
				if (data[i] == null) {
					result = (i - head) & mask;
					break;
				}
			}
		} else {
			for (int i = head; i != tail; i = (i + 1) & mask) {
				if (o.equals(data[i])) {
					result = (i - head) & mask;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return head == tail;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return new DequeIterator();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	@Override
	public int lastIndexOf(Object o) {
		int result = -1;
		final int mask = data.length - 1;
		final int start = (tail - 1) & mask;
		final int end = (head - 1) & mask;
		if (o == null) {
			for (int i = start; i != end; i = (i - 1) & mask) {
				if (data[i] == null) {
					result = (i - head) & mask;
					break;
				}
			}
		} else {
			for (int i = start; i != end; i = (i - 1) & mask) {
				if (o.equals(data[i])) {
					result = (i - head) & mask;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#listIterator()
	 */
	@Override
	public ListIterator<E> listIterator() {
		return new DequeListIterator(0);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#listIterator(int)
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		return new DequeListIterator(index);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Queue#offer(java.lang.Object)
	 */
	public boolean offer(E element) {
		addLast(element);
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#offerAll(java.util.Collection)
	 */
	public void offerAll(Collection<? extends E> collection) {
		addAll(collection);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#offerFirst(java.lang.Object)
	 */
	public void offerFirst(E element) {
		addFirst(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#offerLast(java.lang.Object)
	 */
	public void offerLast(E element) {
		addLast(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Queue#peek()
	 */
	public E peek() {
		if (head == tail) {
			return null;
		}
		return getFirst();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#peekFirst()
	 */
	public E peekFirst() {
		return getFirst();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#peekLast()
	 */
	public E peekLast() {
		return getLast();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#poll()
	 */
	public E poll() {
		if (head == tail) {
			return null;
		}
		return removeFirst();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#pop()
	 */
	public E pop() {
		return removeLast();
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
	 * {@inheritDoc}
	 * 
	 * @see java.util.Queue#remove()
	 */
	public E remove() {
		return removeFirst();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#remove(int)
	 */
	@Override
	public E remove(int index) {
		rangeCheck(index);

		final int actualIndex = (head + index) & (data.length - 1);
		E old = data[actualIndex];

		deleteIndex(actualIndex);

		return old;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object element) {
		final int mask = data.length - 1;
		boolean result = false;
		if (element == null) {
			for (int i = head; i != tail; i = (i + 1) & mask) {
				if (data[i] == null) {
					deleteIndex(i);
					result = true;
					break;
				}
			}
		} else {
			for (int i = head; i != tail; i = (i + 1) & mask) {
				if (element.equals(data[i])) {
					deleteIndex(i);
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> collection) {
		final int mask = data.length - 1;
		boolean result = false;
		int[] indices = new int[collection.size()];
		int cursor = 0;
		for (Object element : collection) {
			if (element == null) {
				for (int i = head; i != tail; i = (i + 1) & mask) {
					if (data[i] == null) {
						indices[cursor++] = i;
						break;
					}
				}
			} else {
				for (int i = head; i != tail; i = (i + 1) & mask) {
					if (element.equals(data[i])) {
						indices[cursor++] = i;
						break;
					}
				}
			}
		}
		if (cursor > 0) {
			result = true;
			if (indices.length == 1) {
				deleteIndex(indices[0]);
			} else {
				delete(indices);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#removeFirst()
	 */
	public E removeFirst() {
		if (head == tail) {
			throw new NoSuchElementException();
		}
		final int mask = data.length - 1;
		final E result = data[head];
		data[head] = null;
		modCount++;
		head = (head + 1) & mask;
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.Deque#removeLast()
	 */
	public E removeLast() {
		if (head == tail) {
			throw new NoSuchElementException();
		}
		tail = (tail - 1) & (data.length - 1);
		final E result = data[tail];
		data[tail] = null;
		modCount++;
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	@Override
	public E set(int index, E element) {
		rangeCheck(index);

		final int actualIndex = (head + index) & (data.length - 1);
		E old = data[actualIndex];
		data[actualIndex] = element;
		return old;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#size()
	 */
	@Override
	public int size() {
		return (tail - head) & (data.length - 1);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#toArray()
	 */
	@Override
	public Object[] toArray() {
		final int size = size();
		Object[] result = new Object[size];
		if (head < tail) {
			System.arraycopy(data, head, result, 0, size);
		} else if (head != tail) {
			int headLength = data.length - head;
			// Copy all elements at the right of "head"...
			System.arraycopy(data, head, result, 0, headLength);
			// ...Then all elements at the left of "head"
			System.arraycopy(data, 0, result, headLength, tail);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.List#toArray(T[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends Object> T[] toArray(T[] a) {
		final int size = size();
		final T[] temp;
		if (a.length > size) {
			temp = a;
		} else {
			temp = (T[])Array.newInstance(a.getClass().getComponentType(), size);
		}
		/*
		 * "head" could be located somewhere _after_ tail as we could have pushed elements on the back of our
		 * deque. Reorder them now.
		 */
		if (head < tail) {
			System.arraycopy(data, head, temp, 0, size);
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
	 * Removes all of the values located at the given indices at once.
	 * 
	 * @param indices
	 *            Indices (in the actual array) of the elements that are to be deleted from this Queue.
	 */
	private void delete(int[] indices) {
		final int mask = data.length - 1;
		int cursor = 0;
		while (cursor < indices.length
				&& ((tail - indices[cursor]) & mask) > ((indices[cursor] - head) & mask)) {
			cursor++;
		}
		final int median = cursor;

		// All indices closer to tail than they are to head will trigger moves to the left
		if (median > 0) {
			deleteRightRotate(median - 1, indices);
		}
		if (median < indices.length) {
			deleteLeftRotate(median, indices);
		}
	}

	/**
	 * This will be used internally to delete the value of the given bucket.
	 * 
	 * @param index
	 *            The index (in the actual array) of the value we are to delete.
	 */
	private void deleteIndex(int index) {
		final int mask = data.length - 1;
		if (index == head) {
			removeFirst();
		} else if (index == ((tail - 1) & mask)) {
			removeLast();
		} else {
			/*
			 * If we are close to head, we'll move elements to the right. If we are closer to tail, we'll move
			 * them to the left. Note that System.arrayCopy is way faster than iteration over the array.
			 */
			final int elementsAhead = (tail - index) & mask;
			final int elementsBehind = (index - head) & mask;
			// Are we closer to head?
			if (elementsAhead > elementsBehind) {
				if (head < index) {
					modCount++;
					System.arraycopy(data, head, data, head + 1, elementsBehind);
					data[head] = null;
					head = head + 1;
				} else {
					modCount++;
					// Rotate all elements at the left of index by one to the right ...
					System.arraycopy(data, 0, data, 1, index);
					// ... copy the last element ...
					data[0] = data[mask];
					// ... and finally all elements from head to the last (-1) by one to the right
					System.arraycopy(data, head, data, head + 1, mask - head);
					data[head] = null;
					head = (head + 1) & mask;
				}
			} else {
				if (index < tail) {
					modCount++;
					System.arraycopy(data, index + 1, data, index, elementsAhead);
					tail = tail - 1;
				} else {
					modCount++;
					// Rotate all elements at the right of index by one to the left ...
					System.arraycopy(data, index + 1, data, index, mask - index);
					// ... copy the first element ...
					data[mask] = data[0];
					// ... and finally all elements at the left of tail by one to the right
					System.arraycopy(data, 1, data, 0, tail);
					tail = (tail - 1) & mask;
				}
			}
		}
	}

	/**
	 * This will be used from {@link #delete(int[])} in order to remove the indices from the given array
	 * (starting from <code>startIndex</code>) while rotating {@link #data} elements to the left.
	 * 
	 * @param startIndex
	 *            Index from which to start removing values.
	 * @param indices
	 *            Indices (in the actual array) of the elements that are to be deleted from this Queue.
	 */
	private void deleteLeftRotate(int startIndex, int[] indices) {
		final int mask = data.length - 1;
		// Take care of the indices corresponding to a removeLast
		int cursor = indices.length - 1;
		while (cursor >= startIndex && indices[cursor] == ((tail - 1) & mask)) {
			removeLast();
			cursor--;
		}
		// How many indices still to delete?
		if (cursor == startIndex) {
			// Only 1
			modCount++;
			tail = (tail - 1) & mask;
			for (int i = indices[startIndex]; i != tail; i = (i + 1) & mask) {
				data[i] = data[(i + 1) & mask];
			}
			data[tail] = null;
		} else if (cursor > startIndex) {
			tail = (tail - 1) & mask;
			int gap = 1;
			int fence = cursor;
			cursor = startIndex + 1;
			for (int i = indices[startIndex]; i != tail; i = (i + 1) & mask) {
				while (cursor <= fence && ((i + gap) & mask) == indices[cursor]) {
					cursor++;
					gap++;
				}
				data[i] = data[(i + gap) & mask];
			}
			int oldTail = tail;
			tail = (tail - (gap - 1)) & mask;
			for (int i = oldTail; i != tail; i = (i - 1) & mask) {
				data[i] = null;
				modCount++;
			}
		}
	}

	/**
	 * This will be used from {@link #delete(int[])} in order to remove the indices from the given array
	 * (starting from <code>startIndex</code>) while rotating {@link #data} elements to the right.
	 * 
	 * @param startIndex
	 *            Index from which to start removing values.
	 * @param indices
	 *            Indices (in the actual array) of the elements that are to be deleted from this Queue.
	 */
	private void deleteRightRotate(int startIndex, int[] indices) {
		final int mask = data.length - 1;
		// Take care of the indices corresponding to a removeLast
		int cursor = 0;
		while (cursor <= startIndex && indices[cursor] == head) {
			removeFirst();
			cursor++;
		}
		// How many indices still to delete?
		if (cursor == startIndex) {
			// Only 1
			modCount++;
			for (int i = indices[startIndex]; i != head; i = (i - 1) & mask) {
				data[i] = data[(i - 1) & mask];
			}
			data[head] = null;
			head = (head - 1) & mask;
		} else if (cursor < startIndex) {
			int gap = 1;
			int fence = cursor;
			cursor = startIndex - 1;
			for (int i = indices[startIndex]; i != head; i = (i - 1) & mask) {
				while (cursor >= fence && ((i - gap) & mask) == indices[cursor]) {
					cursor--;
					gap++;
				}
				data[i] = data[(i - gap) & mask];
			}
			int oldHead = head;
			head = (head + gap) & mask;
			for (int i = oldHead; i != head; i = (i + 1) & mask) {
				data[i] = null;
				modCount++;
			}
		}
	}

	/**
	 * Double the capacity of this deque.
	 */
	private void doubleCapacity() {
		final int newCapacity = data.length << 1;
		if (newCapacity < 0) {
			throw new IndexOutOfBoundsException();
		}

		setCapacity(newCapacity);
	}

	/**
	 * This will ensure that the underlying array is big enough to hold the given number of elements.
	 * 
	 * @param elementCount
	 *            The number of elements we expect to hold in this deque.
	 */
	private void ensureCapacity(int elementCount) {
		if (elementCount >= data.length) {
			final int newCapacity = getNextPowerOfTwo(elementCount);
			if (newCapacity < 0) {
				throw new IndexOutOfBoundsException();
			}

			setCapacity(newCapacity);
		}
	}

	/**
	 * Initializes the backing array to a capacity sufficient to hold the given number of elements.
	 * 
	 * @param elementCount
	 *            The number of elements we expect to hold in this deque.
	 */
	@SuppressWarnings("unchecked")
	private void initialize(int elementCount) {
		final int initialCapacity;
		if (elementCount <= 1) {
			initialCapacity = 4;
		} else {
			initialCapacity = getNextPowerOfTwo(elementCount);
		}
		if (initialCapacity < 0) {
			throw new IndexOutOfBoundsException();
		}
		data = (E[])new Object[initialCapacity];
	}

	/**
	 * Checks that the given index is in the range of this deque. This will be used before any random access
	 * use.
	 * 
	 * @param index
	 *            The index we are to check.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if the given index is either negative or out of this deque's bounds.
	 */
	private void rangeCheck(int index) throws IndexOutOfBoundsException {
		if (head == tail || index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
	}

	/**
	 * Resizes the underlying array to the given new capacity.
	 * 
	 * @param newCapacity
	 *            New capacity to resize our underlying array to.
	 */
	@SuppressWarnings("unchecked")
	private void setCapacity(int newCapacity) {
		final int newTail;
		if (head == tail) {
			newTail = data.length;
		} else {
			newTail = size();
		}
		E[] temp = (E[])new Object[newCapacity];

		int headLength = data.length - head;
		// Copy all elements at the right of "head"...
		System.arraycopy(data, head, temp, 0, headLength);
		// ...Then all elements at the left of "head"
		System.arraycopy(data, 0, temp, headLength, head);

		data = temp;
		head = 0;
		tail = newTail;
	}

	/**
	 * Implementation of an {@link Iterator} for the {@link CircularArrayDeque}. It will allow iteration from
	 * the first element of the deque ({@link #head}) to the last ({@link #tail}).
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class DequeIterator implements Iterator<E> {
		/** Keeps track of the value of {@link #modCount} this iterator expects. */
		protected int expectedModCount = modCount;

		/** Index of the last element returned by a call to {@link #next()}. */
		protected int lastReturned = -1;

		/** Index of the next element to be returned by this iterator. */
		protected int next = head;

		/** Improves visibility of the default constructor. */
		DequeIterator() {
			// Improves visibility of the default constructor.
		}

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
			if (next == tail) {
				throw new NoSuchElementException();
			}
			final E result = data[next];
			lastReturned = next;
			next = (next + 1) & (data.length - 1);
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			if (lastReturned == -1) {
				throw new IllegalStateException();
			}
			checkComodification();

			final boolean increment = next == head;
			int deletionIndex = (lastReturned - head) & (data.length - 1);
			CircularArrayDeque.this.remove(deletionIndex);
			expectedModCount++;
			final int mask = data.length - 1;
			if (increment) {
				next = (next + 1) & mask;
			} else if (deletionIndex >= size() / 2 && size() > 1) {
				next = (next - 1) & mask;
			}
			lastReturned = -1;
		}

		/**
		 * Checks for modification of the deque since the creation of this iterator.
		 * 
		 * @throws ConcurrentModificationException
		 *             Thrown if the deque this iterator traverses has been modified since the creation of the
		 *             iterator.
		 */
		protected void checkComodification() throws ConcurrentModificationException {
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
		}
	}

	/**
	 * Implementation of an {@link ListIterator} for the {@link CircularArrayDeque}. It will allow iteration
	 * from the first element of the deque ({@link #head}) to the last ({@link #tail}) and back.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class DequeListIterator extends DequeIterator implements ListIterator<E> {
		/**
		 * Instantiates our list iterator given the index of the first element to be returned by a call to
		 * {@link #next()}.
		 * 
		 * @param index
		 *            Index of the first element to be returned from the list iterator (by a call to
		 *            {@link #next()}).
		 */
		DequeListIterator(int index) {
			next = (head + index) & (data.length - 1);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.ListIterator#add(java.lang.Object)
		 */
		public void add(E element) {
			checkComodification();

			final boolean increment = next != head;
			CircularArrayDeque.this.add((next - head) & (data.length - 1), element);
			expectedModCount++;
			if (increment) {
				next = (next + 1) & (data.length - 1);
			}
			lastReturned = -1;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.ListIterator#hasPrevious()
		 */
		public boolean hasPrevious() {
			return next != head;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.ListIterator#nextIndex()
		 */
		public int nextIndex() {
			return (next - head) & (data.length - 1);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.ListIterator#previous()
		 */
		public E previous() {
			checkComodification();
			final int mask = data.length - 1;
			if (next == head) {
				throw new NoSuchElementException();
			}
			next = (next - 1) & mask;
			final E result = data[next];
			lastReturned = next;
			return result;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.ListIterator#previousIndex()
		 */
		public int previousIndex() {
			return ((next - head) & (data.length - 1)) - 1;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.ListIterator#set(java.lang.Object)
		 */
		public void set(E element) {
			if (lastReturned == -1) {
				throw new IllegalStateException();
			}
			checkComodification();

			CircularArrayDeque.this.set((lastReturned - head) & (data.length - 1), element);
		}
	}
}
