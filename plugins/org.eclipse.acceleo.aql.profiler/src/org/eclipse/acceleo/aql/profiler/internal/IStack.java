/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.acceleo.aql.profiler.internal;

/**
 * Internal stack specification.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @param <T>
 *            the type stored in the stack
 */
public interface IStack<T> {

	/**
	 * Add the given element on the top of the stack.
	 * 
	 * @param elem
	 *            the element to add
	 */
	void push(T elem);

	/**
	 * Gets the element on the top of the stack. The element is kept on the top of the stack.
	 * 
	 * @return the element on the top of the stack
	 */
	T peek();

	/**
	 * Gets the element on the top of the stack. The element is removed from the stack.
	 * 
	 * @return the element on the top of the stack
	 */
	T pop();

	/**
	 * Gets the element at the given position.
	 * 
	 * @param position
	 *            the position of the element
	 * @return the element at the given position
	 */
	T get(int position);

	/**
	 * Empties the stack.
	 */
	void clear();

	/**
	 * Gets the number of elements stored in the stack.
	 * 
	 * @return the number of elements stored in the stack
	 */
	int size();
}
