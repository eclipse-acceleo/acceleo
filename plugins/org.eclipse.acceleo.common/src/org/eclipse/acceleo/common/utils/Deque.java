/*******************************************************************************
 * Copyright (c) 2006 Sun Microsystems, Inc and others.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Contributors:
 *     Sun Microsystems - initial API and implementation
 *     Obeo - backporting to JDK 5 for Acceleo
 *******************************************************************************/
package org.eclipse.acceleo.common.utils;

import java.util.Iterator;
import java.util.Queue;

/* 
 * CHECKSTYLE:OFF
 * Disabling checkstyle, this is a copy/paste from a JDK 1.6 class
 * TODO As soon as Acceleo goes on to JDK1.6 compatibility, delete this and use the standard implementation
 */
/**
 * This is a copy/paste from the JDK 1.6 implementation of java.util.Deque.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a> - Backporting
 * @author Josh Bloch and Doug Lea
 * @param <E>
 *            the type of elements held in this collection
 */
public interface Deque<E> extends Queue<E> {
	/**
	 * Inserts the specified element at the front of this deque if it is possible to do so immediately without
	 * violating capacity restrictions. When using a capacity-restricted deque, it is generally preferable to
	 * use method {@link #offerFirst}.
	 * 
	 * @param e
	 *            the element to add
	 * @throws IllegalStateException
	 *             if the element cannot be added at this time due to capacity restrictions
	 * @throws ClassCastException
	 *             if the class of the specified element prevents it from being added to this deque
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements
	 * @throws IllegalArgumentException
	 *             if some property of the specified element prevents it from being added to this deque
	 */
	void addFirst(E e);

	/**
	 * Inserts the specified element at the end of this deque if it is possible to do so immediately without
	 * violating capacity restrictions. When using a capacity-restricted deque, it is generally preferable to
	 * use method {@link #offerLast}.
	 * <p>
	 * This method is equivalent to {@link #add}.
	 * 
	 * @param e
	 *            the element to add
	 * @throws IllegalStateException
	 *             if the element cannot be added at this time due to capacity restrictions
	 * @throws ClassCastException
	 *             if the class of the specified element prevents it from being added to this deque
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements
	 * @throws IllegalArgumentException
	 *             if some property of the specified element prevents it from being added to this deque
	 */
	void addLast(E e);

	/**
	 * Inserts the specified element at the front of this deque unless it would violate capacity restrictions.
	 * When using a capacity-restricted deque, this method is generally preferable to the {@link #addFirst}
	 * method, which can fail to insert an element only by throwing an exception.
	 * 
	 * @param e
	 *            the element to add
	 * @return <tt>true</tt> if the element was added to this deque, else <tt>false</tt>
	 * @throws ClassCastException
	 *             if the class of the specified element prevents it from being added to this deque
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements
	 * @throws IllegalArgumentException
	 *             if some property of the specified element prevents it from being added to this deque
	 */
	boolean offerFirst(E e);

	/**
	 * Inserts the specified element at the end of this deque unless it would violate capacity restrictions.
	 * When using a capacity-restricted deque, this method is generally preferable to the {@link #addLast}
	 * method, which can fail to insert an element only by throwing an exception.
	 * 
	 * @param e
	 *            the element to add
	 * @return <tt>true</tt> if the element was added to this deque, else <tt>false</tt>
	 * @throws ClassCastException
	 *             if the class of the specified element prevents it from being added to this deque
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements
	 * @throws IllegalArgumentException
	 *             if some property of the specified element prevents it from being added to this deque
	 */
	boolean offerLast(E e);

	/**
	 * Retrieves and removes the first element of this deque. This method differs from {@link #pollFirst
	 * pollFirst} only in that it throws an exception if this deque is empty.
	 * 
	 * @return the head of this deque
	 * @throws java.util.NoSuchElementException
	 *             if this deque is empty
	 */
	E removeFirst();

	/**
	 * Retrieves and removes the last element of this deque. This method differs from {@link #pollLast
	 * pollLast} only in that it throws an exception if this deque is empty.
	 * 
	 * @return the tail of this deque
	 * @throws java.util.NoSuchElementException
	 *             if this deque is empty
	 */
	E removeLast();

	/**
	 * Retrieves and removes the first element of this deque, or returns <tt>null</tt> if this deque is empty.
	 * 
	 * @return the head of this deque, or <tt>null</tt> if this deque is empty
	 */
	E pollFirst();

	/**
	 * Retrieves and removes the last element of this deque, or returns <tt>null</tt> if this deque is empty.
	 * 
	 * @return the tail of this deque, or <tt>null</tt> if this deque is empty
	 */
	E pollLast();

	/**
	 * Retrieves, but does not remove, the first element of this deque. This method differs from
	 * {@link #peekFirst peekFirst} only in that it throws an exception if this deque is empty.
	 * 
	 * @return the head of this deque
	 * @throws java.util.NoSuchElementException
	 *             if this deque is empty
	 */
	E getFirst();

	/**
	 * Retrieves, but does not remove, the last element of this deque. This method differs from
	 * {@link #peekLast peekLast} only in that it throws an exception if this deque is empty.
	 * 
	 * @return the tail of this deque
	 * @throws java.util.NoSuchElementException
	 *             if this deque is empty
	 */
	E getLast();

	/**
	 * Retrieves, but does not remove, the first element of this deque, or returns <tt>null</tt> if this deque
	 * is empty.
	 * 
	 * @return the head of this deque, or <tt>null</tt> if this deque is empty
	 */
	E peekFirst();

	/**
	 * Retrieves, but does not remove, the last element of this deque, or returns <tt>null</tt> if this deque
	 * is empty.
	 * 
	 * @return the tail of this deque, or <tt>null</tt> if this deque is empty
	 */
	E peekLast();

	/**
	 * Removes the first occurrence of the specified element from this deque. If the deque does not contain
	 * the element, it is unchanged. More formally, removes the first element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt> (if such an element exists). Returns
	 * <tt>true</tt> if this deque contained the specified element (or equivalently, if this deque changed as
	 * a result of the call).
	 * 
	 * @param o
	 *            element to be removed from this deque, if present
	 * @return <tt>true</tt> if an element was removed as a result of this call
	 * @throws ClassCastException
	 *             if the class of the specified element is incompatible with this deque (optional)
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements (optional)
	 */
	boolean removeFirstOccurrence(Object o);

	/**
	 * Removes the last occurrence of the specified element from this deque. If the deque does not contain the
	 * element, it is unchanged. More formally, removes the last element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt> (if such an element exists). Returns
	 * <tt>true</tt> if this deque contained the specified element (or equivalently, if this deque changed as
	 * a result of the call).
	 * 
	 * @param o
	 *            element to be removed from this deque, if present
	 * @return <tt>true</tt> if an element was removed as a result of this call
	 * @throws ClassCastException
	 *             if the class of the specified element is incompatible with this deque (optional)
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements (optional)
	 */
	boolean removeLastOccurrence(Object o);

	// *** Queue methods ***

	/**
	 * Inserts the specified element into the queue represented by this deque (in other words, at the tail of
	 * this deque) if it is possible to do so immediately without violating capacity restrictions, returning
	 * <tt>true</tt> upon success and throwing an <tt>IllegalStateException</tt> if no space is currently
	 * available. When using a capacity-restricted deque, it is generally preferable to use
	 * {@link #offer(Object) offer}.
	 * <p>
	 * This method is equivalent to {@link #addLast}.
	 * 
	 * @param e
	 *            the element to add
	 * @return <tt>true</tt> (as specified by {@link java.util.Collection#add})
	 * @throws IllegalStateException
	 *             if the element cannot be added at this time due to capacity restrictions
	 * @throws ClassCastException
	 *             if the class of the specified element prevents it from being added to this deque
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements
	 * @throws IllegalArgumentException
	 *             if some property of the specified element prevents it from being added to this deque
	 */
	boolean add(E e);

	/**
	 * Inserts the specified element into the queue represented by this deque (in other words, at the tail of
	 * this deque) if it is possible to do so immediately without violating capacity restrictions, returning
	 * <tt>true</tt> upon success and <tt>false</tt> if no space is currently available. When using a
	 * capacity-restricted deque, this method is generally preferable to the {@link #add} method, which can
	 * fail to insert an element only by throwing an exception.
	 * <p>
	 * This method is equivalent to {@link #offerLast}.
	 * 
	 * @param e
	 *            the element to add
	 * @return <tt>true</tt> if the element was added to this deque, else <tt>false</tt>
	 * @throws ClassCastException
	 *             if the class of the specified element prevents it from being added to this deque
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements
	 * @throws IllegalArgumentException
	 *             if some property of the specified element prevents it from being added to this deque
	 */
	boolean offer(E e);

	/**
	 * Retrieves and removes the head of the queue represented by this deque (in other words, the first
	 * element of this deque). This method differs from {@link #poll poll} only in that it throws an exception
	 * if this deque is empty.
	 * <p>
	 * This method is equivalent to {@link #removeFirst()}.
	 * 
	 * @return the head of the queue represented by this deque
	 * @throws java.util.NoSuchElementException
	 *             if this deque is empty
	 */
	E remove();

	/**
	 * Retrieves and removes the head of the queue represented by this deque (in other words, the first
	 * element of this deque), or returns <tt>null</tt> if this deque is empty.
	 * <p>
	 * This method is equivalent to {@link #pollFirst()}.
	 * 
	 * @return the first element of this deque, or <tt>null</tt> if this deque is empty
	 */
	E poll();

	/**
	 * Retrieves, but does not remove, the head of the queue represented by this deque (in other words, the
	 * first element of this deque). This method differs from {@link #peek peek} only in that it throws an
	 * exception if this deque is empty.
	 * <p>
	 * This method is equivalent to {@link #getFirst()}.
	 * 
	 * @return the head of the queue represented by this deque
	 * @throws java.util.NoSuchElementException
	 *             if this deque is empty
	 */
	E element();

	/**
	 * Retrieves, but does not remove, the head of the queue represented by this deque (in other words, the
	 * first element of this deque), or returns <tt>null</tt> if this deque is empty.
	 * <p>
	 * This method is equivalent to {@link #peekFirst()}.
	 * 
	 * @return the head of the queue represented by this deque, or <tt>null</tt> if this deque is empty
	 */
	E peek();

	// *** Stack methods ***

	/**
	 * Pushes an element onto the stack represented by this deque (in other words, at the head of this deque)
	 * if it is possible to do so immediately without violating capacity restrictions, returning <tt>true</tt>
	 * upon success and throwing an <tt>IllegalStateException</tt> if no space is currently available.
	 * <p>
	 * This method is equivalent to {@link #addFirst}.
	 * 
	 * @param e
	 *            the element to push
	 * @throws IllegalStateException
	 *             if the element cannot be added at this time due to capacity restrictions
	 * @throws ClassCastException
	 *             if the class of the specified element prevents it from being added to this deque
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements
	 * @throws IllegalArgumentException
	 *             if some property of the specified element prevents it from being added to this deque
	 */
	void push(E e);

	/**
	 * Pops an element from the stack represented by this deque. In other words, removes and returns the first
	 * element of this deque.
	 * <p>
	 * This method is equivalent to {@link #removeFirst()}.
	 * 
	 * @return the element at the front of this deque (which is the top of the stack represented by this
	 *         deque)
	 * @throws java.util.NoSuchElementException
	 *             if this deque is empty
	 */
	E pop();

	// *** Collection methods ***

	/**
	 * Removes the first occurrence of the specified element from this deque. If the deque does not contain
	 * the element, it is unchanged. More formally, removes the first element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt> (if such an element exists). Returns
	 * <tt>true</tt> if this deque contained the specified element (or equivalently, if this deque changed as
	 * a result of the call).
	 * <p>
	 * This method is equivalent to {@link #removeFirstOccurrence}.
	 * 
	 * @param o
	 *            element to be removed from this deque, if present
	 * @return <tt>true</tt> if an element was removed as a result of this call
	 * @throws ClassCastException
	 *             if the class of the specified element is incompatible with this deque (optional)
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements (optional)
	 */
	boolean remove(Object o);

	/**
	 * Returns <tt>true</tt> if this deque contains the specified element. More formally, returns
	 * <tt>true</tt> if and only if this deque contains at least one element <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 * 
	 * @param o
	 *            element whose presence in this deque is to be tested
	 * @return <tt>true</tt> if this deque contains the specified element
	 * @throws ClassCastException
	 *             if the type of the specified element is incompatible with this deque (optional)
	 * @throws NullPointerException
	 *             if the specified element is null and this deque does not permit null elements (optional)
	 */
	boolean contains(Object o);

	/**
	 * Returns the number of elements in this deque.
	 * 
	 * @return the number of elements in this deque
	 */
	public int size();

	/**
	 * Returns an iterator over the elements in this deque in proper sequence. The elements will be returned
	 * in order from first (head) to last (tail).
	 * 
	 * @return an iterator over the elements in this deque in proper sequence
	 */
	Iterator<E> iterator();

	/**
	 * Returns an iterator over the elements in this deque in reverse sequential order. The elements will be
	 * returned in order from last (tail) to first (head).
	 * 
	 * @return an iterator over the elements in this deque in reverse sequence
	 */
	Iterator<E> descendingIterator();
}
