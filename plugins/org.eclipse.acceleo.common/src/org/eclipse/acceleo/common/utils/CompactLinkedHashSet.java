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

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This implementation is similar to the {@link java.util.LinkedHashSet}, except that it does not rely on an
 * underlying map but on the {@link CompactHashSet} instead.
 * <p>
 * <b>Note: great care must be exercised if mutable objects are inserted in this set.</b> The behavior of the
 * {@link CompactLinkedHashSet} is not specified if the value of one of its content is changed in a manner
 * that affects {@link Object#equals(Object)} comparisons.
 * </p>
 * <p>
 * The {@link CompactLinkedHashSet} has been implemented to be a memory-efficient replacement for the
 * {@link java.util.LinkedHashSet}. However, its speed performance, though on par with that of the
 * {@link java.util.LinkedHashSet} 's in most cases, is not guaranteed to be equivalent in all use cases.
 * </p>
 * <p>
 * The {@link java.util.LinkedHashSet} uses open hashing to resolve hash collisions, the
 * {@link CompactLinkedHashSet} relies on linear probing closed hashing instead. See the documentation of
 * {@link CompactHashSet} for more details on this.
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
public final class CompactLinkedHashSet<E> extends CompactHashSet<E> {
	/** The head of our linked list. */
	transient LinkedListHeader header;

	/**
	 * Constructs an empty set with default capacity and load factor.
	 * 
	 * @see CompactHashSet#CompactHashSet()
	 */
	public CompactLinkedHashSet() {
		super();
	}

	/**
	 * Constructs a new set containing the elements in the given collection. The set is created with the
	 * default load factor and an initial capacity sufficient for holding all of the values contained by the
	 * given collection.
	 * 
	 * @param collection
	 *            The collection which values are to be placed in the new set.
	 * @see CompactHashSet#CompactHashSet(Collection)
	 */
	public CompactLinkedHashSet(Collection<? extends E> collection) {
		super(collection);
	}

	/**
	 * Constructs an empty set with an initial capacity sufficient for holding the given number of elements
	 * and the default load factor.
	 * 
	 * @param elementCount
	 *            The number of elements we expect to hold in the new set.
	 * @see CompactHashSet#CompactHashSet(int)
	 */
	public CompactLinkedHashSet(int elementCount) {
		super(elementCount);
	}

	/**
	 * Constructs an empty set with an initial capacity sufficient for holding the given number of elements
	 * and the given load factor.
	 * 
	 * @param elementCount
	 *            The number of elements we expect to hold in the new set.
	 * @param loadFactor
	 *            The load factor of the new set.
	 * @see CompactHashSet#CompactHashSet(int, float)
	 */
	public CompactLinkedHashSet(int elementCount, float loadFactor) {
		super(elementCount, loadFactor);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.CompactHashSet#clear()
	 */
	@Override
	public void clear() {
		super.clear();
		header.next = header;
		header.last = header;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.CompactHashSet#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		return new LinkedSetIterator();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.CompactHashSet#deleteIndex(int)
	 */
	@Override
	protected void deleteIndex(int index) {
		super.deleteIndex(index);

		Entry previous = header;
		Entry entry = header.next;
		while (entry.index != index && entry != header) {
			previous = entry;
			entry = entry.next;
		}
		/*
		 * TODO entry == header would mean we haven't found the index to delete in the linked list. How could
		 * we be in such a state? Try and reproduce [341596].
		 */
		if (entry != header) {
			previous.next = entry.next;
			if (entry == header.last) {
				header.last = previous;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.CompactHashSet#init()
	 */
	@Override
	protected void init() {
		header = new LinkedListHeader();
		header.next = header;
		header.last = header;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.CompactHashSet#rehash(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void rehash(int newCapacity) {
		final int mask = newCapacity - 1;
		final E[] temp = (E[])new Object[newCapacity];

		for (Entry entry = header.next; entry != header; entry = entry.next) {
			E value = data[entry.index];
			final int hash = supplementalHash(value.hashCode());
			int insertionIndex = hash & mask;

			while (temp[insertionIndex] != null) {
				insertionIndex = (insertionIndex + 1) & mask;
			}

			entry.index = insertionIndex;
			temp[insertionIndex] = value;
		}

		data = temp;
		threshold = (int)(newCapacity * loadFactor);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.utils.CompactHashSet#setIndex(int, java.lang.Object)
	 */
	@Override
	protected void setIndex(int index, E element) {
		super.setIndex(index, element);

		Entry newEntry = new Entry(index);
		header.last.next = newEntry;
		header.last = newEntry;
		newEntry.next = header;
	}

	/**
	 * This will serve as the header of our singly linked list.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private static final class LinkedListHeader extends Entry {
		/** References the last entry of this list. */
		Entry last;

		/**
		 * Increases visibility of the default constructor.
		 */
		LinkedListHeader() {
			super(-1);
		}
	}

	/**
	 * This implementation of a linked list entry will allow us to track the insertion order of this set's
	 * values.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private static class Entry {
		/** Index in the set's underlying array where this entry is located. */
		int index;

		/** Keeps a reference to the next entry of this list. */
		Entry next;

		/**
		 * Constructs a list entry given its value.
		 * 
		 * @param index
		 *            Index in the set's underlying array where this entry is located.
		 */
		Entry(int index) {
			this.index = index;
		}
	}

	/**
	 * Implementation of an {@link Iterator} for the {@link CompactLinkedHashSet}.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private final class LinkedSetIterator implements Iterator<E> {
		/**
		 * Keeps track of the value of {@link #modCount} this iterator expects. This will allow for the
		 * {@link #next()} and {@link #remove()} to fail in {@link ConcurrentModificationException} fast
		 * whenever the set has been modified without using this iterator's {@link #remove()} method.
		 */
		private int expectedModCount = modCount;

		/** Last element returned by a call to {@link #next()}. */
		private Entry lastReturned;

		/** Next element to be returned by this iterator. */
		private Entry nextElem;

		/**
		 * Default constructor.
		 */
		LinkedSetIterator() {
			nextElem = header.next;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return nextElem != header;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#next()
		 */
		public E next() {
			checkComodification();
			if (nextElem == header) {
				throw new NoSuchElementException();
			}

			E result = data[nextElem.index];
			lastReturned = nextElem;
			nextElem = nextElem.next;
			return unmaskNull(result);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Iterator#remove()
		 */
		public void remove() {
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			checkComodification();

			deleteIndex(lastReturned.index);
			expectedModCount++;
			lastReturned = null;
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
