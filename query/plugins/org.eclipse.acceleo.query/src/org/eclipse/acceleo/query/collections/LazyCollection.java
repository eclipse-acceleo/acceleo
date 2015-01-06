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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
//CHECKSTYLE:OFF
import java.util.List;

//CHECLSTYLE:ON
/**
 * {@link LazyList} instances can be used to lazyly iterate over a list build on an underlying iterable.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 * @param <E>
 *            the element parameter type.
 */
public abstract class LazyCollection<E> implements Collection<E> {
	/**
	 * Message thrown when an operation that is not supported is called.
	 */
	static final String OPERATION_NOT_SUPPORTED_MESSAGE = "operation isn't supported in LazyList instances";

	/**
	 * Default step used when realizing objects from the iterator.
	 */
	private static final int REALIZATION_STEP_SIZE = 20;

	/**
	 * step used when realizing objects from the iterator.
	 */
	private int realizationStep;

	/**
	 * Elements that are already realized.
	 */
	private Collection<E> realizedElements;

	/**
	 * The internal iterator.
	 */
	private Iterator<E> iterator;

	/**
	 * The original iterable.
	 */
	private Iterable<E> iterable;

	/**
	 * Creates a new LazyList given an iterator and a realization step. The realization step is the number of
	 * list elements that are realized at once. This parameter can be used to limit the overhead due to
	 * Laziness.
	 * 
	 * @param iterator
	 *            the iterator to use as the underlying content.
	 * @param step
	 *            the realization step used for this list.
	 */
	public LazyCollection(Iterable<E> iterable) {
		this.iterable = iterable;
		this.iterator = iterable.iterator();
		this.realizedElements = createRealizedCollection();
		this.realizationStep = REALIZATION_STEP_SIZE;
	}

	protected abstract Collection<E> createRealizedCollection();

	Collection<E> getRealizedElements() {
		return realizedElements;
	}

	/**
	 * Creates a new LazyList given an iterator.
	 * 
	 * @param iterator
	 *            the iterator to use as the underlying content.
	 */
	public LazyCollection(Iterable<E> iterable, int step) {
		this(iterable);
		this.realizationStep = step;
	}

	/**
	 * Realizes the specified number of elements.
	 * 
	 * @param number
	 *            the number of elements to realize.
	 */
	protected void realize(int number) {
		while (sourceHasNext() && realizedElements.size() < number) {
			realizeNext();
		}
	}

	/**
	 * Realizes the next element.
	 */
	protected List<E> realizeNext() {
		int i = 0;
		List<E> result = new ArrayList<E>(this.realizationStep);
		while (sourceHasNext() && i < this.realizationStep) {
			E element = iterator.next();
			realizedElements.add(element);
			result.add(element);
			i++;
		}
		return result;
	}

	protected void runFull(Operation<E> op) {
		Iterator<E> realizedIterator = realizedElements.iterator();
		while (realizedIterator.hasNext()) {
			op.execute(realizedIterator.next());
		}
		while (sourceHasNext()) {
			E element = iterator.next();
			realizedElements.add(element);
			op.execute(element);
		}
	}

	@Override
	public boolean add(E arg0) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public boolean addAll(Collection<? extends E> arg0) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		for (Object obj : arg0) {
			if (!contains(obj)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return realizedElements.isEmpty() && !sourceHasNext();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_MESSAGE);
	}

	@Override
	public int size() {
		while (sourceHasNext()) {
			realizeNext();
		}
		return realizedElements.size();
	}

	@Override
	public Object[] toArray() {
		while (sourceHasNext()) {
			realizeNext();
		}
		return realizedElements.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		while (sourceHasNext()) {
			realizeNext();
		}
		return realizedElements.toArray(a);
	}

	@Override
	public boolean contains(Object arg0) {
		boolean found = false;
		if (realizedElements.contains(arg0)) {
			found = true;
		} else {
			while (!found && sourceHasNext()) {
				List<E> elements = realizeNext();
				if (elements.contains(arg0)) {
					found = true;
				}
			}
		}
		return found;
	}

	/**
	 * Tells if the source {@link Iterator} {@link Iterator#hasNext() has a next} element.
	 * 
	 * @return <code>true</code> if the source {@link Iterator} {@link Iterator#hasNext() has a next} element,
	 *         <code>false</code> otherwise
	 */
	protected boolean sourceHasNext() {
		final boolean result;

		if (iterator != null) {
			if (iterator.hasNext()) {
				result = true;
			} else {
				result = false;
				iterable = null;
				iterator = null;
			}
		} else {
			result = false;
		}

		return result;
	}

	@Override
	public Iterator<E> iterator() {
		final Iterator<E> result;

		if (iterable != null) {
			result = iterable.iterator();
		} else {
			result = realizedElements.iterator();
		}

		return result;
	}

}
