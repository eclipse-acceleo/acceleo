/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug;

import java.io.Serializable;

import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
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
	 * @param threadName
	 *            the thread name
	 */
	void suspended(String threadName);

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
	 * @param threadName
	 *            the thread name
	 * @param instruction
	 *            the {@link EObject} representing an instruction
	 * @return <code>true</code> if we can step into the given instruction, <code>false</code> otherwise
	 */
	boolean canStepInto(String threadName, EObject instruction);

	/**
	 * Step into the current instruction of the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void stepInto(String threadName);

	/**
	 * The thread is stepping into.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void steppingInto(String threadName);

	/**
	 * Step over the current instruction of the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void stepOver(String threadName);

	/**
	 * The thread is stepping over.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void steppingOver(String threadName);

	/**
	 * Step return from the current stack frame of the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void stepReturn(String threadName);

	/**
	 * The thread is stepping return.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void steppingReturn(String threadName);

	/**
	 * The thread is stepped with the given state.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void stepped(String threadName);

	/**
	 * Resumes the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void resume(String threadName);

	/**
	 * The thread is resuming.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void resuming(String threadName);

	/**
	 * Suspends the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void suspend(String threadName);

	/**
	 * Terminates the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void terminate(String threadName);

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
	 * Changes the given attribute value for the given break point.
	 * 
	 * @param instruction
	 *            {@link URI} pointing an {@link EObject instruction}
	 * @param attribute
	 *            the attribute
	 * @param value
	 *            the value
	 */
	void changeBreakPoint(URI instruction, String attribute, Serializable value);

	/**
	 * The thread suspended on a breakpoint with the given state.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void breaked(String threadName);

	/**
	 * Notify the debug model that our debugger is terminated.
	 */
	void terminated();

	/**
	 * Method that can be called by a particular execution engine to delegate control of execution flow of the
	 * given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 * @param instruction
	 *            the given {@link EObject} representing an instruction, can't be <code>null</code>
	 * @return <code>false</code> if execution shall stop, <code>true</code> if execution shall continue
	 */
	boolean control(String threadName, EObject instruction);

	/**
	 * Spawn a running thread in the model.
	 * 
	 * @param threadName
	 *            the {@link org.eclipse.acceleo.debug.Thread#getName() thread name}
	 * @param context
	 *            the {@link EObject} representing the current context of the thread
	 */
	void spawnRunningThread(String threadName, EObject context);

	/**
	 * Gets the next instruction to step on after the given {@link EObject current instruction} with the given
	 * {@link Stepping stepping mode}.
	 * 
	 * @param threadName
	 *            the thread name
	 * @param currentInstruction
	 *            the {@link EObject current instruction}
	 * @param stepping
	 *            the {@link Stepping stepping mode}
	 * @return the {@link EObject next instruction} to suspend, if <code>null</code> is returned, the very
	 *         next instruction will be used as the next instruction to suspend.
	 */
	EObject getNextInstruction(String threadName, EObject currentInstruction, Stepping stepping);

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
	 * @param threadName
	 *            the thread name
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
	void variable(String threadName, String stackName, String declarationTypeName, String variableName,
			Object value, boolean supportModifications);

	/**
	 * Deletes the variable with the given name for the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 * @param name
	 *            the variable name
	 */
	void deleteVariable(String threadName, String name);

	/**
	 * Updates data (stack frames, variables, ...) for the given thread and instruction. Call:
	 * <ul>
	 * <li>{@link IDSLDebugger#pushStackFrame(String, String, EObject, EObject)}</li>
	 * <li>{@link IDSLDebugger#popStackFrame(String)}</li>
	 * <li>{@link IDSLDebugger#setCurrentInstruction(String, EObject)}</li>
	 * <li>{@link IDSLDebugger#variable(String, String, String, Object)}</li>
	 * <li>{@link IDSLDebugger#deleteVariable(String, String)}</li>
	 * </ul>
	 * 
	 * @param threadName
	 *            the thread name
	 * @param instruction
	 *            the current instruction
	 */
	void updateData(String threadName, EObject instruction);

	/**
	 * Pushes a stack frame for the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 * @param frameName
	 *            the frame name
	 * @param context
	 *            the frame context
	 * @param instruction
	 *            the current instruction
	 */
	void pushStackFrame(String threadName, String frameName, EObject context, EObject instruction);

	/**
	 * Pops a stack frame for the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void popStackFrame(String threadName);

	/**
	 * Sets the current instruction for the given thread.
	 * 
	 * @param threadName
	 *            the thread name
	 * @param instruction
	 *            the current instruction.
	 */
	void setCurrentInstruction(String threadName, EObject instruction);

	/**
	 * The given thread is terminated.
	 * 
	 * @param threadName
	 *            the thread name
	 */
	void terminated(String threadName);

	/**
	 * Tells if the given thread is terminated.
	 * 
	 * @param threadName
	 *            the thread name
	 * @return <code>true</code> if the given thread is terminated, <code>false</code> otherwise
	 */
	boolean isTerminated(String threadName);

	/**
	 * Validates a variable value.
	 * 
	 * @param threadName
	 *            the thread name
	 * @param variableName
	 *            the variable name
	 * @param value
	 *            the value to validate
	 * @return <code>true</code> if the value is valid, <code>false</code> otherwise
	 */
	boolean validateVariableValue(String threadName, String variableName, String value);

	/**
	 * Gets the variable value after {@link IDSLDebugger#validateVariableValue(String, String, String)
	 * validation} returned <code>true</code>.
	 * 
	 * @param threadName
	 *            the thread name
	 * @param stackName
	 *            the stack frame name
	 * @param variableName
	 *            the variable name
	 * @param value
	 *            the value to validate
	 * @return the variable value
	 */
	Object getVariableValue(String threadName, String stackName, String variableName, String value);

	/**
	 * Sets the variable value after {@link IDSLDebugger#validateVariableValue(String, String, String)
	 * validation} returned <code>true</code>.
	 * 
	 * @param threadName
	 *            the thread name
	 * @param stackName
	 *            the stack frame name
	 * @param variableName
	 *            the variable name
	 * @param value
	 *            the value to validate
	 */
	void setVariableValue(String threadName, String stackName, String variableName, Object value);

}
