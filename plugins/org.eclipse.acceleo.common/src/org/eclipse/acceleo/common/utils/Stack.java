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

import java.util.Collection;

/**
 * Interface for the Stacks used throughout Acceleo.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <E>
 *            Type of the elements contained by this Stack.
 * @since 3.0
 */
public interface Stack<E> extends Collection<E> {
	/**
	 * Pushes the given element on the top of this stack. Has the same effect as {@link #addLast(Object)}.
	 * 
	 * @param element
	 *            The element to be pushed on the top of this stack.
	 */
	void push(E element);

	/**
	 * Pushes all of the elements contained by the given collection on top of this stack. These elements will
	 * be pushed in the same order as the collection's iterator order. Has the same effect as
	 * {@link #addAll(Collection)}.
	 * 
	 * @param collection
	 *            Collection whose elements are to be pushed on top of this stack.
	 */
	void pushAll(Collection<? extends E> collection);

	/**
	 * Pushes the given element on the back of this stack.
	 * 
	 * @param element
	 *            The element to be pushed on the back of this stack.
	 */
	void addFirst(E element);

	/**
	 * Pushes the given element on the top of this stack. Has the same effect as {@link #push(Object)}.
	 * 
	 * @param element
	 *            The element to be pushed on the top of this stack.
	 */
	void addLast(E element);

	/**
	 * Removes the element at the top of this stack. Has the same effect as {@link #removeLast()}.
	 * 
	 * @return The element that was at the top of this stack.
	 */
	E pop();

	/**
	 * Removes the element at the back of this stack.
	 * 
	 * @return The element that was at the back of this stack.
	 */
	E removeFirst();

	/**
	 * Removes the element at the top of this stack. Has the same effect as {@link #pop()}.
	 * 
	 * @return The element that was at the top of this stack.
	 */
	E removeLast();

	/**
	 * Looks at the element at the top of this stack without removing it. Has the same effect as
	 * {@link #getLast()}.
	 * 
	 * @return The element at the top of this stack.
	 */
	E peek();

	/**
	 * Looks at the element at the back of this stack without removing it.
	 * 
	 * @return The element at the back of this stack.
	 */
	E getFirst();

	/**
	 * Looks at the element at the top of this stack without removing it. Has the same effect as
	 * {@link #peek()}.
	 * 
	 * @return The element at the top of this stack.
	 */
	E getLast();
}
