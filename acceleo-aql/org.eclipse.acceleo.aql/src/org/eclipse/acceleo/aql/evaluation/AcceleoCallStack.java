/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation;

import java.util.ArrayDeque;
import java.util.Deque;

import org.eclipse.acceleo.Module;
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
	/** The module that was the starting point of us creating this stack. */
	private final Module startingModule;

	/** The underlying call stack. */
	private final Deque<ModuleElement> stack;

	/**
	 * Creates a stack starting at the given module.
	 * 
	 * @param startingModule
	 *            The module that will be considered the "starting point" of this stack.
	 */
	public AcceleoCallStack(Module startingModule) {
		this.startingModule = startingModule;
		this.stack = new ArrayDeque<ModuleElement>();
	}

	public Deque<ModuleElement> getStack() {
		return stack;
	}

	public Module getStartingModule() {
		return startingModule;
	}

	/**
	 * Adds the given module element to the top of this stack.
	 * 
	 * @param element
	 *            The element we're pushing atop the stack.
	 */
	public void addLast(ModuleElement element) {
		stack.addLast(element);
	}

	/**
	 * Retrieves and removes the element atop this stack.
	 * 
	 * @return The element that was at the top of this stack if any, <code>null</code> otherwise.
	 */
	public ModuleElement pollLast() {
		return stack.pollLast();
	}

	/**
	 * Retrieves the element atop this stack without removing it.
	 * 
	 * @return The element at the top of this stack if any, <code>null</code> otherwise.
	 */
	public ModuleElement peekLast() {
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
