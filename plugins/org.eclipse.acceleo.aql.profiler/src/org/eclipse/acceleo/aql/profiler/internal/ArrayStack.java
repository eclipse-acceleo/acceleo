/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.acceleo.aql.profiler.internal;

/**
 * {@link IStack} implementation based on a simple array.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @param <T>
 *            the type stored in the stack
 */
public class ArrayStack<T> implements IStack<T> {

	/**
	 * Array used to store elements.
	 */
	private final T[] array;

	/**
	 * Position of the top of the stack.
	 */
	private int top;

	/**
	 * Constructor.
	 * 
	 * @param size
	 *            the maximum number of elements that can be stored in the stack at a given time.
	 */
	@SuppressWarnings("unchecked")
	public ArrayStack(int size) {
		array = (T[])new Object[size];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.obeo.agility.reverse.core.internal.IStack#push(java.lang.Object)
	 */
	public void push(T elem) {
		try {
			array[top++] = elem;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalStateException("No space left in stack.");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.obeo.agility.reverse.core.internal.IStack#peek()
	 */
	public T peek() {
		try {
			return array[top - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalStateException("Stack is empty.");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.obeo.agility.reverse.core.internal.IStack#pop()
	 */
	public T pop() {
		T res = null;
		try {
			res = array[--top];
			array[top] = null;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalStateException("Stack is empty.");
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.obeo.agility.reverse.core.internal.IStack#clear()
	 */
	public void clear() {
		while (top > 0) {
			array[--top] = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder(array.length * 10);

		for (int i = top - 1; i >= 0; --i) {
			res.append(array[i].toString());
			if (i != 0) {
				res.append("\n");
			}
		}

		return res.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.obeo.agility.reverse.core.internal.IStack#size()
	 */
	public int size() {
		return top;
	}

	/**
	 * Gets the element at the given index.
	 * 
	 * @param index
	 *            the index of the element
	 * @return the element
	 */
	public T get(int index) {
		if (index < top) {
			return array[index];
		} else {
			throw new IllegalStateException("Too few elements in the stack.");
		}
	}

	/**
	 * Tells if the given element is currently in the stack.
	 * 
	 * @param elem
	 *            the element to search for
	 * @return <code>true</code> if the element is present in the stack, <code>false</code> otherwise
	 */
	public boolean contains(T elem) {
		boolean res = false;
		for (int i = top - 1; i >= 0; --i) {
			if (array[i].equals(elem)) {
				res = true;
				break;
			}
		}
		return res;
	}

}
