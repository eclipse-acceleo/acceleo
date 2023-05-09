/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.ArrayDeque;
import java.util.Deque;

import org.eclipse.acceleo.ModuleElement;

/**
 * This will represent a stack of calls as known by Acceleo.
 * <p>
 * We need to keep a reference to the "starting point" of a given stack in order to be able to go back down
 * into overriden modules all the way to there, since this starting point might very well not be the module
 * containing the first module element of our stack. For example, if a module M1 imports M2 which extends M3,
 * M1 tries to call a template that's only available in M3, the first module element called on that stack will
 * be the one from M3, but if that template calls another that is overriden in M2, we need to be able to go
 * back down to M2.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoCallStack {
	/** The module qualified name that was the starting point of us creating this stack. */
	private final String startingModuleQualifiedName;

	/** The underlying call stack. */
	private final Deque<ModuleElement> stack;

	/**
	 * Creates a stack starting at the given module.
	 * 
	 * @param startingModuleQualifiedName
	 *            The module qualified name that will be considered the "starting point" of this stack.
	 */
	public AcceleoCallStack(String startingModuleQualifiedName) {
		this.startingModuleQualifiedName = startingModuleQualifiedName;
		this.stack = new ArrayDeque<ModuleElement>();
	}

	public Deque<ModuleElement> getStack() {
		return stack;
	}

	public String getStartingModuleQualifiedName() {
		return startingModuleQualifiedName;
	}

	/**
	 * Adds the given {@link ModuleElement} to the top of this stack.
	 * 
	 * @param moduleElement
	 *            The {@link ModuleElement} we're pushing atop the stack.
	 */
	public void push(ModuleElement moduleElement) {
		stack.addLast(moduleElement);
	}

	/**
	 * Retrieves and removes the {@link ModuleElement} atop this stack.
	 * 
	 * @return The {@link ModuleElement} that was at the top of this stack if any, <code>null</code>
	 *         otherwise.
	 */
	public ModuleElement pop() {
		return stack.pollLast();
	}

	/**
	 * Retrieves the {@link ModuleElement} atop this stack without removing it.
	 * 
	 * @return The {@link ModuleElement} at the top of this stack if any, <code>null</code> otherwise.
	 */
	public ModuleElement peek() {
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
