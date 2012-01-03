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

import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This implementation of the {@link java.util.Set} interface uses the same hashing functions as the
 * {@link java.util.HashSet} does. However it does not rely on an underlying map.
 * <p>
 * <b>Note: great care must be exercised if mutable objects are inserted in this set.</b> The behavior of the
 * {@link CompactHashSet} is not specified if the value of one of its content is changed in a manner that
 * affects {@link Object#equals(Object)} comparisons.
 * </p>
 * <p>
 * The {@link CompactHashSet} has been implemented in order to be a memory-efficient replacement for the
 * {@link java.util.HashSet}. However, its speed performance, though on par with that of the
 * {@link java.util.HashSet} 's in most use cases, is not guaranteed to be equivalent in all use cases.
 * </p>
 * <p>
 * The {@link java.util.HashSet} uses open hashing to resolve hash collisions : each bucket of the underlying
 * array points to a Linked List containing the actual elements. The {@link CompactHashSet} uses closed
 * hashing, storing the actual elements in their own bucket and using linear probing (with a step of
 * <code>1</code>) to determine the bucket in case of collisions.
 * </p>
 * <p>
 * The {@link CompactHashSet} implementation has been designed for minimal memory footprint. It does not keep
 * a cache of the inserted elements' hashCodes, and thus we need to actually use the
 * {@link Object#equals(Object)} method when searching for an existing entry without checking the hashCode
 * values beforehand.
 * </p>
 * <p>
 * This class offers constant time performance for the basic {@link #add(Object)}, {@link #contains(Object)}
 * and {@link #remove(Object)} operations if the {@link Object#hashCode()} function of the contained Objects
 * evenly disperses the elements. Iteration over this set's elements requires time proportional to its
 * internal array's length (which is equal to the next power of two greater than the highest {@link #size()}
 * this set attained).
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <E>
 *            Type of the elements contained by this Set.
 * @since 3.1
 */
public class CompactHashSet<E> extends AbstractSet<E> {
	/** This object will be used as a place holder for deleted values. */
	static final Object DELETED_VALUE = new Object();

	/** The default initial capacity of our Sets. */
	private static final int DEFAULT_INITIAL_CAPACITY = 16;

	/** The default load factor of our sets. */
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	/** This object will be used whenever the user tries to add <code>null</code> into this set. */
	private static final Object NULL_VALUE = new Object();

	/**
	 * The array in which the elements held by this Set are stored. We'll always maintain this array's size to
	 * be a power of two.
	 */
	transient E[] data;

	/** This will hold the count of buckets holding the {@link #DELETED_VALUE}. */
	transient int deleted;

	/** The load factor of this set. */
	transient float loadFactor;

	/**
	 * The number of times this set has been structurally modified. Structural modifications are those that
	 * change the number of elements in the set. This field is used to make iterators on Collection-views of
	 * the {@link CompactHashSet} fail-fast.
	 * 
	 * @See {@link ConcurrentModificationException}
	 */
	transient volatile int modCount;

	/** The number of elements contained in this set. */
	transient int size;

	/** The number of elements we'll allow before resizing the underlying array (capacity * loadFactor). */
	transient int threshold;

	/**
	 * Constructs an empty set with an initial capacity of {@value #DEFAULT_INITIAL_CAPACITY} elements and the
	 * default load factor ({@value #DEFAULT_LOAD_FACTOR}).
	 */
	@SuppressWarnings("unchecked")
	public CompactHashSet() {
		data = (E[])new Object[DEFAULT_INITIAL_CAPACITY];
		loadFactor = DEFAULT_LOAD_FACTOR;
		threshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
		init();
	}

	/**
	 * Constructs a new set containing the elements in the given collection. The set is created with the
	 * default load factor of {@value #DEFAULT_LOAD_FACTOR} and an initial capacity sufficient for holding all
	 * of the values contained by the given collection.
	 * 
	 * @param collection
	 *            The collection which values are to be placed in the new set.
	 */
	public CompactHashSet(Collection<? extends E> collection) {
		initialize(Math.max(collection.size() + 1, DEFAULT_INITIAL_CAPACITY));
		loadFactor = DEFAULT_LOAD_FACTOR;
		threshold = (int)(data.length * DEFAULT_LOAD_FACTOR);
		init();
		for (E element : collection) {
			addForCreate(element);
		}
	}

	/**
	 * Constructs an empty set with an initial capacity sufficient for holding the given number of elements
	 * and the default load factor of {@value #DEFAULT_LOAD_FACTOR}.
	 * 
	 * @param elementCount
	 *            The number of elements we expect to hold in the new set.
	 */
	public CompactHashSet(int elementCount) {
		this(elementCount, DEFAULT_LOAD_FACTOR);
	}

	/**
	 * Constructs an empty set with an initial capacity sufficient for holding the given number of elements
	 * and the given load factor.
	 * 
	 * @param elementCount
	 *            The number of elements we expect to hold in the new set.
	 * @param loadFactor
	 *            The load factor of the new set.
	 */
	public CompactHashSet(int elementCount, float loadFactor) {
		if (loadFactor <= 0f || loadFactor > 1f || Float.isNaN(loadFactor)) {
			throw new IllegalArgumentException(Float.toString(loadFactor));
		}
		initialize(elementCount);
		this.loadFactor = loadFactor;
		threshold = (int)(data.length * loadFactor);
		init();
	}

	/**
	 * Applies a supplemental hash function to defend against hash code functions that do not differ in lower
	 * bits. Borrowed from {@link java.util.HashMap}.
	 * 
	 * @param hashCode
	 *            The hash code alter.
	 * @return The altered hash code.
	 */
	protected static int supplementalHash(int hashCode) {
		int newHash = hashCode;
		// CHECKSTYLE:OFF This has been borrowed from HashMap#newHash(int)
		newHash ^= (newHash >>> 20) ^ (newHash >>> 12);
		return newHash ^ (newHash >>> 7) ^ (newHash >>> 4);
		// CHECKSTYLE:ON
	}

	/**
	 * This set supports <code>null</code> insertions. We'll have to unmask this value before returning it.
	 * 
	 * @param <T>
	 *            Type of the element we are masking.
	 * @param element
	 *            The element to mask.
	 * @return {@link #NULL_VALUE} if <code>element</code> is <code>null</code>, <code>element</code>
	 *         otherwise.
	 */
	protected static <T> T unmaskNull(T element) {
		if (element == NULL_VALUE) {
			return null;
		}
		return element;
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
	 * This set supports <code>null</code> insertions. We'll have to mask this value in order to be able to
	 * add it.
	 * 
	 * @param <T>
	 *            Type of the element we are masking.
	 * @param element
	 *            The element to mask.
	 * @return {@link #NULL_VALUE} if <code>element</code> is <code>null</code>, <code>element</code>
	 *         otherwise.
	 */
	@SuppressWarnings("unchecked")
	private static <T> T maskNull(T element) {
		if (element == null) {
			return (T)NULL_VALUE;
		}
		return element;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(E element) {
		if (element == null) {
			return addNull();
		}
		return addValue(element);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		modCount++;
		for (int i = 0; i < data.length; i++) {
			data[i] = null;
		}
		size = 0;
		deleted = 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object element) {
		return indexOf(element) >= 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Set#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return new SetIterator();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object element) {
		int index = indexOf(element);

		if (index < 0) {
			return false;
		}
		deleteIndex(index);
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.util.Set#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Deletes the value located at the given index.
	 * 
	 * @param index
	 *            Index of the value which is to be deleted.
	 */
	@SuppressWarnings("unchecked")
	protected void deleteIndex(int index) {
		data[index] = (E)DELETED_VALUE;
		modCount++;
		deleted++;
		size--;
	}

	/**
	 * Subclasses hook. This will be called from the constructors before any insertions is made.
	 */
	protected void init() {
		// empty implementation.
	}

	/**
	 * Rehashes the contents of this set into a new array of the given capacity.
	 * 
	 * @param newCapacity
	 *            The capacity of the array into which we are to rehash the contents.
	 */
	@SuppressWarnings("unchecked")
	protected void rehash(int newCapacity) {
		final int mask = newCapacity - 1;
		final int length = data.length;
		final E[] temp = (E[])new Object[newCapacity];

		for (int i = 0; i < length; i++) {
			if (data[i] != null && data[i] != DELETED_VALUE) {
				final int hash = supplementalHash(data[i].hashCode());
				int insertionIndex = hash & mask;

				while (temp[insertionIndex] != null) {
					insertionIndex = (insertionIndex + 1) & mask;
				}

				temp[insertionIndex] = data[i];
			}
		}

		data = temp;
		threshold = (int)(newCapacity * loadFactor);
	}

	/**
	 * Sets the value of the given index in the underlying array to the given element.
	 * 
	 * @param index
	 *            The index at which this element will be set.
	 * @param element
	 *            Element added to the set.
	 */
	protected void setIndex(int index, E element) {
		data[index] = element;
	}

	/**
	 * This will be used internally to add an element without checking invariants.
	 * 
	 * @param element
	 *            The element that is to be added.
	 */
	private void addForCreate(E element) {
		final E actualElement = maskNull(element);
		final int mask = data.length - 1;
		final int hash = supplementalHash(actualElement.hashCode());
		int insertionIndex = hash & mask;

		while (data[insertionIndex] != null) {
			if (data[insertionIndex].equals(actualElement)) {
				return;
			}
			insertionIndex = (insertionIndex + 1) & mask;
		}

		setIndex(insertionIndex, actualElement);
		modCount++;
		size++;
	}

	/**
	 * Adds the null value in this set.
	 * 
	 * @return <code>true</code> if this set did not already contain the null value.
	 */
	@SuppressWarnings("unchecked")
	private boolean addNull() {
		final int mask = data.length - 1;
		final int hash = supplementalHash(NULL_VALUE.hashCode());
		int index = hash & mask;
		// If we find a deleted bucket while searching for the insertion element, we'll use it instead.
		int deletedIndex = -1;

		while (data[index] != null) {
			E value = data[index];
			if (value == NULL_VALUE) {
				return false;
			} else if (deletedIndex < 0 && value == DELETED_VALUE) {
				deletedIndex = index;
			}
			index = (index + 1) & mask;
		}

		// If we're here, the set doesn't contain the null value
		if (deletedIndex >= 0) {
			setIndex(deletedIndex, (E)NULL_VALUE);
			modCount++;
			deleted--;
			size++;
		} else {
			setIndex(index, (E)NULL_VALUE);
			modCount++;
			if ((size++ + deleted) >= threshold) {
				rehash();
			}
		}
		return true;
	}

	/**
	 * Adds the given non <code>null</code> value to the set.
	 * 
	 * @param element
	 *            The element to add in the set. Cannot be <code>null</code>.
	 * @return <code>true</code> if this set did not already contain this element.
	 */
	private boolean addValue(E element) {
		final int mask = data.length - 1;
		final int hash = supplementalHash(element.hashCode());
		int index = hash & mask;
		// If we find a deleted bucket while searching for the insertion element, we'll use it instead.
		int deletedIndex = -1;

		while (data[index] != null) {
			E value = data[index];
			if (value.equals(element)) {
				return false;
			} else if (deletedIndex < 0 && value == DELETED_VALUE) {
				deletedIndex = index;
			}
			index = (index + 1) & mask;
		}

		// If we're here, the set doesn't contain the element
		if (deletedIndex >= 0) {
			setIndex(deletedIndex, element);
			modCount++;
			deleted--;
			size++;
		} else {
			setIndex(index, element);
			modCount++;
			if ((size++ + deleted) >= threshold) {
				rehash();
			}
		}
		return true;
	}

	/**
	 * Returns the index of the given element in the underlying array.
	 * 
	 * @param element
	 *            The element we seek index of.
	 * @return The index of the given element in the underlying array if present, <code>-1</code> otherwise.
	 */
	private int indexOf(Object element) {
		final Object actualElement = maskNull(element);
		final int mask = data.length - 1;
		final int hash = supplementalHash(actualElement.hashCode());
		int index = hash & mask;

		while (data[index] != null) {
			if (data[index].equals(actualElement)) {
				return index;
			}
			index = (index + 1) & mask;
		}

		return -1;
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
		if (elementCount <= 0) {
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
	 * This will rehash the contents of this set. Whether we double the capacity of the underlying array or
	 * only rehash will depend on the number of cells we can free.
	 * <p>
	 * Namely, if the array's capacity is less than or equal to 64 and the number of {@link #deleted} buckets
	 * is greater than 10, or if the capacity is greater than 64 and the number of {@link #deleted} buckets
	 * represents more than 10% of the underlying array, we'll simply remove the deleted values and rehash the
	 * contents.
	 * </p>
	 */
	private void rehash() {
		final int length = data.length;
		final int deletedThreshold = Math.max((int)(length * 0.1), 10);
		if (deleted > deletedThreshold) {
			rehash(length);
		} else {
			final int newCapacity = data.length << 1;
			if (newCapacity < 0) {
				throw new IndexOutOfBoundsException();
			}
			rehash(newCapacity);
		}
	}

	/**
	 * Implementation of an {@link Iterator} for the {@link CompactHashSet}.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private final class SetIterator implements Iterator<E> {
		/**
		 * Keeps track of the value of {@link #modCount} this iterator expects. This will allow for the
		 * {@link #next()} and {@link #remove()} to fail in {@link ConcurrentModificationException} fast
		 * whenever the set has been modified without using this iterator's {@link #remove()} method.
		 */
		private int expectedModCount = modCount;

		/** Index of the last element returned by a call to {@link #next()}. */
		private int lastReturned = -1;

		/** Index of the next element to be returned by this iterator. */
		private int next;

		/** Improves visibility of the default constructor. */
		SetIterator() {
			if (!isEmpty()) {
				while (next < data.length && (data[next] == null || data[next] == DELETED_VALUE)) {
					next++;
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return next < data.length && data[next] != null && data[next] != DELETED_VALUE;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#next()
		 */
		public E next() {
			checkComodification();
			if (next == data.length) {
				throw new NoSuchElementException();
			}
			E result = data[next];
			if (result == null) {
				throw new NoSuchElementException();
			}

			lastReturned = next++;
			while (next < data.length && (data[next] == null || data[next] == DELETED_VALUE)) {
				next++;
			}
			return unmaskNull(result);
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

			CompactHashSet.this.remove(data[lastReturned]);
			expectedModCount++;
			lastReturned = -1;
		}

		/**
		 * Checks for concurrent modifications.
		 * 
		 * @throws ConcurrentModificationException
		 *             Thrown if the set this iterator traverses has been concurrently modified.
		 */
		private void checkComodification() throws ConcurrentModificationException {
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
		}
	}
}
