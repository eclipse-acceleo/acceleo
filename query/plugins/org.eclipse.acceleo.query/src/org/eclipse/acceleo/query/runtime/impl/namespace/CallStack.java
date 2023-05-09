/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * This will represent a stack of calls as known by Acceleo Query Language.
 * <p>
 * We need to keep a reference to the "starting point" of a given stack in order to be able to go back down
 * into overridden name spaces all the way to there, since this starting point might very well not be the
 * qualified name containing the first qualified name element of our stack. For example, if a qualified M1
 * imports M2 which extends M3, M1 tries to call a service that's only available in M3, the first qualified
 * name element called on that stack will be the one from M3, but if that service calls another that is
 * overriden in M2, we need to be able to go back down to M2.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class CallStack {
	/** The qualified name that was the starting point of us creating this stack. */
	private final String startingQualifiedName;

	/** The underlying call stack. */
	private final Deque<String> stack = new ArrayDeque<String>();

	/**
	 * Creates a stack starting at the given name space.
	 * 
	 * @param startingQualifiedName
	 *            The qualified name that will be considered the "starting point" of this stack.
	 */
	public CallStack(String startingQualifiedName) {
		this.startingQualifiedName = startingQualifiedName;
	}

	public Deque<String> getStack() {
		return stack;
	}

	public String getStartingQualifiedName() {
		return startingQualifiedName;
	}

	/**
	 * Adds the given qualified name to the top of this stack.
	 * 
	 * @param qualifiedName
	 *            The qualified name we're pushing atop the stack.
	 */
	public void push(String qualifiedName) {
		stack.addLast(qualifiedName);
	}

	/**
	 * Retrieves and removes the qualified name atop this stack.
	 * 
	 * @return The qualified name that was at the top of this stack if any, <code>null</code> otherwise.
	 */
	public String pop() {
		return stack.pollLast();
	}

	/**
	 * Retrieves the qualified name atop this stack without removing it.
	 * 
	 * @return The qualified name at the top of this stack if any, <code>null</code> otherwise.
	 */
	public String peek() {
		return stack.peekLast();
	}

	/**
	 * Returns <code>true</code> if this stack contains no element.
	 * 
	 * @return <code>true</code> if this stack contains no element.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
}
