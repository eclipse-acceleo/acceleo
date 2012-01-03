/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.debug.core;

import org.eclipse.acceleo.engine.internal.debug.ASTFragment;

/**
 * Interface for a template evaluation debugger.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public interface ITemplateDebugger {

	/**
	 * Resumed state.
	 */
	int RESUMED = 0;

	/**
	 * Suspended state.
	 */
	int SUSPENDED = 1;

	/**
	 * To resume the evaluation of the current template.
	 */
	void resume();

	/**
	 * To suspend the evaluation of the current template.
	 */
	void suspend();

	/**
	 * Go into the next evaluation step.
	 */
	void stepInto();

	/**
	 * Go over the next evaluation step.
	 */
	void stepOver();

	/**
	 * Go to the parent evaluation step.
	 */
	void stepReturn();

	/**
	 * Terminate the evaluation.
	 */
	void terminate();

	/**
	 * Adds a breakpoint.
	 * 
	 * @param astFragment
	 *            the fragment URI of an AST node
	 */
	void addBreakpoint(ASTFragment astFragment);

	/**
	 * Removes a breakpoint.
	 * 
	 * @param astFragment
	 *            the fragment URI of an AST node
	 */
	void removeBreakpoint(ASTFragment astFragment);

	/**
	 * Returns the state of the debugger.
	 * 
	 * @return the state of the debugger
	 */
	int getState();

	/**
	 * Adds a template listener.
	 * 
	 * @param aListener
	 *            is the listener to add
	 */
	void addListener(ITemplateDebuggerListener aListener);

	/**
	 * Removes a template listener.
	 * 
	 * @param aListener
	 *            is the listener to remove
	 */
	void removeListener(ITemplateDebuggerListener aListener);

	/**
	 * To start the debugger.
	 */
	void start();

	/**
	 * To finish the debugger.
	 */
	void end();

	/**
	 * Returns the current execution stack.
	 * 
	 * @return the execution stack
	 */
	StackInfo[] getStack();

	/**
	 * Pushes a new execution context.
	 * 
	 * @param astFragment
	 *            the fragment URI of an AST node
	 */
	void pushStack(ASTFragment astFragment);

	/**
	 * Pops the last added execution context.
	 * 
	 * @param astFragment
	 *            the fragment URI of an AST node
	 */
	void popStack(ASTFragment astFragment);

}
