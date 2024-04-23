/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug;

import java.util.Deque;
import java.util.Map;

import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.debug.util.FrameVariable;
import org.eclipse.acceleo.debug.util.StackFrame;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

/**
 * The debugger.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IDSLDebugger extends IDSLDebugEventProcessor {

	/**
	 * Stepping modes.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	enum Stepping {
		/**
		 * Not stepping.
		 */
		NONE,
		/**
		 * Stepping over.
		 */
		STEP_OVER,
		/**
		 * Stepping into.
		 */
		STEP_INTO,
		/**
		 * Stepping return.
		 */
		STEP_RETURN;
	}

	/**
	 * Initializes the debugger.
	 * 
	 * @param noDebug
	 *            <code>true</code> if no debug is needed
	 * @param arguments
	 *            the {@link Map} of arguments
	 */
	void initialize(boolean noDebug, Map<String, Object> arguments);

	/**
	 * Starts the debugger.
	 */
	void start();

	/**
	 * Terminates the debugger.
	 */
	void terminate();

	/**
	 * Suspends the debugger.
	 */
	void suspend();

	/**
	 * The thread is suspended with the given state.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void suspended(Long threadID);

	/**
	 * Resumes the debugger.
	 */
	void resume();

	/**
	 * Disconnect the debugger.
	 */
	void disconnect();

	/**
	 * Tells if we can step into the given the {@link EObject} representing an instruction for the given
	 * thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @param instruction
	 *            the {@link EObject} representing an instruction
	 * @return <code>true</code> if we can step into the given instruction, <code>false</code> otherwise
	 */
	boolean canStepInto(Long threadID, EObject instruction);

	/**
	 * Step into the current instruction of the given thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void stepInto(Long threadID);

	/**
	 * The thread is stepping into.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void steppingInto(Long threadID);

	/**
	 * Step over the current instruction of the given thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void stepOver(Long threadID);

	/**
	 * The thread is stepping over.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void steppingOver(Long threadID);

	/**
	 * Step return from the current stack frame of the given thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void stepReturn(Long threadID);

	/**
	 * The thread is stepping return.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void steppingReturn(Long threadID);

	/**
	 * The thread is stepped with the given state.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void stepped(Long threadID);

	/**
	 * Resumes the given thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void resume(Long threadID);

	/**
	 * The thread is resuming.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void resuming(Long threadID);

	/**
	 * Suspends the given thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void suspend(Long threadID);

	/**
	 * Terminates the given thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void terminate(Long threadID);

	/**
	 * Adds the given {@link URI} pointing an {@link EObject instruction} as a break point.
	 * 
	 * @param instruction
	 *            the {@link URI} pointing an {@link EObject instruction}
	 */
	void addBreakPoint(URI instruction);

	/**
	 * Removes the given {@link URI} pointing an {@link EObject instruction} as a break point.
	 * 
	 * @param instruction
	 *            {@link URI} pointing an {@link EObject instruction}
	 */
	void removeBreakPoint(URI instruction);

	/**
	 * Clears all breakpoints.
	 */
	void clearBreakPoints();

	/**
	 * Gets the {@link EObject instruction} for the given position.
	 * 
	 * @param path
	 *            the source path
	 * @param line
	 *            the start line
	 * @param column
	 *            the start column
	 * @return the {@link EObject instruction} for the given position
	 */
	EObject getInstruction(String path, long line, long column);

	/**
	 * Gets the {@link DSLSource} for a given {@link EObject instruction}.
	 * 
	 * @param instruction
	 *            the {@link EObject instruction}
	 * @return the {@link DSLSource} for a given {@link EObject instruction}
	 */
	DSLSource getSource(EObject instruction);

	/**
	 * Changes the given attribute value for the given break point.
	 * 
	 * @param instruction
	 *            {@link URI} pointing an {@link EObject instruction}
	 * @param attribute
	 *            the attribute
	 * @param value
	 *            the value
	 */
	void changeBreakPoint(URI instruction, String attribute, String value);

	/**
	 * The thread suspended on a breakpoint with the given state.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void breaked(Long threadID);

	/**
	 * Notify the debug model that our debugger is terminated.
	 */
	void terminated();

	/**
	 * Method that can be called by a particular execution engine to delegate control of execution flow of the
	 * given thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @param instruction
	 *            the given {@link EObject} representing an instruction, can't be <code>null</code>
	 * @return <code>false</code> if execution shall stop, <code>true</code> if execution shall continue
	 */
	boolean control(Long threadID, EObject instruction);

	/**
	 * Spawn a running thread in the model.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @param threadName
	 *            the {@link org.eclipse.acceleo.debug.Thread#getName() thread name}
	 * @param context
	 *            the {@link EObject} representing the current context of the thread
	 */
	void spawnRunningThread(Long threadID, String threadName, EObject context);

	/**
	 * Gets the mapping from thread {@link Thread#getThreadID() ID} to
	 * {@link org.eclipse.acceleo.debug.Thread#getName() thread name}.
	 * 
	 * @return the mapping from thread {@link Thread#getThreadID() ID} to
	 *         {@link org.eclipse.acceleo.debug.Thread#getName() thread name}
	 */
	Map<Long, String> getThreads();

	/**
	 * Tells is the debugger is terminated.
	 * 
	 * @return <code>true</code> if the debugger is terminated, <code>false</code> otherwise
	 */
	boolean isTerminated();

	/**
	 * Set the debugger to terminated or not.
	 * 
	 * @param terminated
	 *            the new value
	 */
	void setTerminated(boolean terminated);

	/**
	 * Tells if we should break on the given instruction.
	 * 
	 * @param instruction
	 *            the {@link EObject instruction}
	 * @return <code>true</code> if we should break, <code>false</code> otherwise
	 */
	boolean shouldBreak(EObject instruction);

	/**
	 * Sets the value of the variable with the given name in the given thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @param stackName
	 *            the stack frame name
	 * @param declarationTypeName
	 *            the variable declaration type name
	 * @param variableName
	 *            the name of the variable
	 * @param value
	 *            the value
	 * @param supportModifications
	 *            tells if the value can be changed
	 */
	void variable(Long threadID, String stackName, String declarationTypeName, String variableName,
			Object value, boolean supportModifications);

	/**
	 * Deletes the variable with the given name for the given thread.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @param name
	 *            the variable name
	 */
	void deleteVariable(Long threadID, String name);

	/**
	 * Gets the stack of {@link StackFrame} for the given {@link Thread#getThreadID() ID}.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @return the stack of {@link StackFrame} for the given {@link Thread#getThreadID() ID}
	 */
	Deque<StackFrame> getStackFrame(Long threadID);

	/**
	 * The given thread is terminated.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 */
	void terminated(Long threadID);

	/**
	 * Tells if the given thread is terminated.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @return <code>true</code> if the given thread is terminated, <code>false</code> otherwise
	 */
	boolean isTerminated(Long threadID);

	/**
	 * Validates a variable value.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @param variableName
	 *            the variable name
	 * @param value
	 *            the value to validate
	 * @return <code>true</code> if the value is valid, <code>false</code> otherwise
	 */
	boolean validateVariableValue(Long threadID, String variableName, String value);

	/**
	 * Gets the variable value after {@link IDSLDebugger#validateVariableValue(String, String, String)
	 * validation} returned <code>true</code>.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @param stackName
	 *            the stack frame name
	 * @param variableName
	 *            the variable name
	 * @param value
	 *            the value to validate
	 * @return the variable value
	 */
	Object getVariableValue(Long threadID, String stackName, String variableName, String value);

	/**
	 * Sets the variable value after {@link IDSLDebugger#validateVariableValue(String, String, String)
	 * validation} returned <code>true</code>.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @param stackName
	 *            the stack frame name
	 * @param variableName
	 *            the variable name
	 * @param value
	 *            the value to validate
	 */
	void setVariableValue(Long threadID, String stackName, String variableName, Object value);

	/**
	 * Prints the given text to the console.
	 * 
	 * @param text
	 *            the text to print
	 */
	void consolePrint(String text);

	/**
	 * Creates the {@link FrameVariable} for the given {@link Object value}.
	 * 
	 * @param name
	 *            the variable name
	 * @param value
	 *            the {@link Object value}
	 * @return the {@link FrameVariable} for the given {@link Object value}
	 */
	FrameVariable getFrameVariable(String name, Object value);

}
