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
import java.util.List;
import java.util.Queue;

/**
 * Interface of the Double-ended queue used throughout Acceleo.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <E>
 *            Type of the elements contained by this Deque.
 * @since 3.0
 */
public interface Deque<E> extends Queue<E>, List<E> {
	/**
	 * Appends the given element at the head of this queue.
	 * 
	 * @param element
	 *            The element to be added at the head of this queue.
	 */
	void addFirst(E element);

	/**
	 * Appends the given element at the tail of this queue.
	 * 
	 * @param element
	 *            The element to be added at the tail of this queue.
	 */
	void addLast(E element);

	/**
	 * Looks at the element at the head of this queue without removing it.
	 * 
	 * @return The element at the head of this queue.
	 */
	E getFirst();

	/**
	 * Looks at the element at the tail of this queue without removing it. Has the same effect as
	 * {@link #peek()}.
	 * 
	 * @return The element at the tail of this queue.
	 */
	E getLast();

	/**
	 * Appends all of the elements contained by the given collection at the tail of this queue. These elements
	 * will be added in the same order as the collection's iterator order. Has the same effect as
	 * {@link #addAll(Collection)}.
	 * 
	 * @param collection
	 *            Collection whose elements are to be pushed at the tail of this queue.
	 */
	void offerAll(Collection<? extends E> collection);

	/**
	 * Appends the given element at the head of this queue. Has the same effect as {@link #addFirst(Object)}.
	 * 
	 * @param element
	 *            The element to be added at the head of this queue.
	 */
	void offerFirst(E element);

	/**
	 * Appends the given element at the tail of this queue. Has the same effect as {@link #addLast(Object)}.
	 * 
	 * @param element
	 *            The element to be added at the tail of this queue.
	 */
	void offerLast(E element);

	/**
	 * Looks at the element at the head of this queue without removing it. Has the same effect as
	 * {@link #getFirst()}.
	 * 
	 * @return The element at the head of this queue.
	 */
	E peekFirst();

	/**
	 * Looks at the element at the tail of this queue without removing it. Has the same effect as
	 * {@link #getLast()}.
	 * 
	 * @return The element at the tail of this queue.
	 */
	E peekLast();

	/**
	 * Retrieves and removes the tail of this queue. Has the same effect as {@link #removeLast()}.
	 * 
	 * @return the tail of this queue.
	 */
	E pop();

	/**
	 * Removes the element at the head of this queue.
	 * 
	 * @return The element that was at the head of this queue.
	 */
	E removeFirst();

	/**
	 * Removes the element at the tail of this queue. Has the same effect as {@link #pop()}.
	 * 
	 * @return The element that was at the tail of this queue.
	 */
	E removeLast();
}
