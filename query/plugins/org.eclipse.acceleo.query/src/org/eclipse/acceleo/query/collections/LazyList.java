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
package org.eclipse.acceleo.query.collections;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
//CHECKSTYLE:OFF

//CHECLSTYLE:ON
/**
 * {@link LazyList} instances can be used to lazyly iterate over a list build on an underlying iterable.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 * @param <E>
 *            the element parameter type.
 */
public class LazyList<E> extends LazyCollection<E> implements List<E> {
	/**
	 * Creates a new LazyList given an iterator and a realization step. The realization step is the number of
	 * list elements that are realized at once. This parameter can be used to limit the overhead due to
	 * lazyness.
	 * 
	 * @param iterator
	 *            the iterator to use as the underlying content.
	 * @param step
	 *            the realization step used for this list.
	 */
	public LazyList(Iterable<E> iterable) {
		super(iterable);
	}

	/**
	 * Creates a new LazyList given an iterator.
	 * 
	 * @param iterator
	 *            the iterator to use as the underlying content.
	 */
	public LazyList(Iterable<E> iterable, int step) {
		super(iterable, step);
	}

	@Override
	protected Collection<E> createRealizedCollection() {
		return Lists.newArrayList();
	}

	@Override
	public void add(int arg0, E arg1) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public E get(int rank) {
		realize(rank);
		return ((List<E>)getRealizedElements()).get(rank);
	}

	@Override
	public int indexOf(Object o) {
		boolean found = false;
		int currentIndex = ((List<E>)getRealizedElements()).indexOf(o);
		if (currentIndex < 0) {
			currentIndex = getRealizedElements().size();
			while (!found && sourceHasNext()) {
				List<E> elements = realizeNext();
				if (elements.contains(o)) {
					found = true;
					currentIndex += elements.indexOf(o);
				} else {
					currentIndex += elements.size();
				}
			}
		}
		if (!found) {
			currentIndex = -1;
		}
		return currentIndex;
	}

	class LastIndexOperation<E> implements Operation<E> {
		int lastIndex = 0;

		int currentIndex = 0;

		Object searchedElt;

		LastIndexOperation(Object elt) {
			this.searchedElt = elt;
		}

		@Override
		public void execute(E element) {
			if (searchedElt.equals(element)) {
				lastIndex = currentIndex;
			}
			currentIndex++;
		}

	}

	/**
	 * {@link ListIterator} instances are used to create a new iterator to an existing list. Thus, they can be
	 * used by {@link LazyList} and preserve the lazyness of the underlying list.
	 * 
	 * @param <E>
	 *            the iterator element's type.
	 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
	 */
	class LazyListIterator<E> implements Iterator<E> {
		/**
		 * The underlying iterated list.
		 */
		private List<E> iterated;

		/**
		 * The rank of the next element.
		 */
		private int currentRank;

		/**
		 * Creates a new {@link ListIterator} instance given a list.
		 * 
		 * @param list
		 *            the list that is iterated.
		 */
		LazyListIterator(List<E> list) {
			this.iterated = list;
		}

		@Override
		public boolean hasNext() {
			return currentRank < iterated.size();
		}

		@Override
		public E next() {
			return iterated.get(currentRank++);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("removal operatino is not supported");
		}

	}

	@Override
	public Iterator<E> iterator() {
		return new LazyListIterator<E>(this);
	}

	@Override
	public int lastIndexOf(Object o) {
		final Object elt = o;
		LastIndexOperation<E> operation = new LastIndexOperation<E>(o);
		runFull(operation);
		return operation.lastIndex;
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		while (sourceHasNext() && getRealizedElements().size() < toIndex) {
			realizeNext();
		}
		return ((List<E>)getRealizedElements()).subList(fromIndex, toIndex);
	}

}
