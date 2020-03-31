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
package org.eclipse.acceleo.debug.util;

import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.Thread;
import org.eclipse.acceleo.debug.Variable;
import org.eclipse.emf.ecore.EObject;

/**
 * Class responsible for EMF debug model updates.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IModelUpdater {

	/**
	 * Requests the termination the given {@link DebugTarget}. The {@link DebugTarget}
	 * {@link DebugTarget#getorg.eclipse.acceleo.debug.State() org.eclipse.acceleo.debug.State} must be
	 * {@link org.eclipse.acceleo.debug.DebugTargetorg.eclipse.acceleo.debug.State#CONNECTED connected}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	void terminateRequest(DebugTarget target);

	/**
	 * Requests disconnection the given {@link DebugTarget}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	void disconnectRequest(DebugTarget target);

	/**
	 * Spawn a running {@link Thread} in the given {@link DebugTarget}.
	 * 
	 * @param target
	 *            the {@link DebugTarget} that will contains the spawned {@link Thread}
	 * @param threadName
	 *            the {@link Thread#getName() thread name}
	 * @param threadContext
	 *            the {@link Thread#getContext() thread context}
	 */
	Thread spawnRunningThreadReply(DebugTarget target, String threadName, EObject threadContext);

	/**
	 * Notify the {@link org.eclipse.acceleo.debug.util.DebugTargetUtils#terminateRequest(DebugTarget)
	 * termination} is done for the given {@link DebugTarget}. The {@link DebugTarget}
	 * {@link DebugTarget#getorg.eclipse.acceleo.debug.State() org.eclipse.acceleo.debug.State} must be
	 * {@link org.eclipse.acceleo.debug.DebugTargetorg.eclipse.acceleo.debug.State#TERMINATING
	 * terminating}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	void terminatedReply(DebugTarget target);

	/**
	 * Notify the deletion of the {@link org.eclipse.acceleo.debug.Variable Variable} with the given
	 * {@link org.eclipse.acceleo.debug.Variable#getName() name} in the given {@link Thread}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 * @param name
	 *            the {@link org.eclipse.acceleo.debug.Variable#getName() variable name}
	 */
	void deleteVariableReply(Thread thread, String name);

	/**
	 * Notify popping the {@link Thread#getTopStackFrame() top stack frame} of the given {@link Thread}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 * @return the popped {@link StackFrame}
	 */
	StackFrame popStackFrameReply(Thread thread);

	/**
	 * Notify pushing a new {@link org.eclipse.acceleo.debug.StackFrame StackFrame} with the given
	 * {@link org.eclipse.acceleo.debug.StackFrame#getName() name} in the given {@link Thread}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 * @param name
	 *            the {@link org.eclipse.acceleo.debug.StackFrame#getName() stack frame name}
	 * @param context
	 *            the {@link org.eclipse.acceleo.debug.StackFrame#getContext() context}
	 * @param instruction
	 *            the {@link org.eclipse.acceleo.debug.StackFrame#getCurrentInstruction() current
	 *            instruction}
	 * @param canStepInto
	 *            tells if we can
	 *            {@link org.eclipse.acceleo.debug.StackFrame#isCanStepIntoCurrentInstruction() step into}
	 *            the current instruction
	 * @return the pushed {@link StackFrame}
	 */
	StackFrame pushStackFrameReply(Thread thread, String name, EObject context, EObject instruction,
			boolean canStepInto);

	/**
	 * Request to step into the given {@link Thread}. The {@link Thread}
	 * {@link Thread#getorg.eclipse.acceleo.debug.State() org.eclipse.acceleo.debug.State} must be
	 * {@link org.eclipse.acceleo.debug.State#SUSPENDED suspended}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 */
	void stepIntoReply(Thread thread);

	/**
	 * Request to step over the given {@link Thread}. The {@link Thread}
	 * {@link Thread#getorg.eclipse.acceleo.debug.State() org.eclipse.acceleo.debug.State} must be
	 * {@link org.eclipse.acceleo.debug.State#SUSPENDED suspended}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 */
	void stepOverReply(Thread thread);

	/**
	 * Request to step return the given {@link Thread}. The {@link Thread}
	 * {@link Thread#getorg.eclipse.acceleo.debug.State() org.eclipse.acceleo.debug.State} must be
	 * {@link org.eclipse.acceleo.debug.State#SUSPENDED suspended} and the {@link Thread#getTopStackFrame()
	 * top stack frame} must has a {@link org.eclipse.acceleo.debug.StackFrame#getParentFrame() parent
	 * frame}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 */
	void stepReturnReply(Thread thread);

	/**
	 * Requests resuming the given {@link Thread}. The {@link Thread} org.eclipse.acceleo.debug.State must
	 * be {@link org.eclipse.acceleo.debug.State#SUSPENDED suspended}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 */
	void resumedReply(Thread thread);

	/**
	 * Sets the {@link org.eclipse.acceleo.debug.StackFrame#getCurrentInstruction() current instruction} of
	 * the {@link Thread#getTopStackFrame() top stack frame} of the given {@link Thread}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 * @param instruction
	 *            the {@link org.eclipse.acceleo.debug.StackFrame#getCurrentInstruction() current
	 *            instruction}
	 * @param canStepInto
	 *            tells if we can
	 *            {@link org.eclipse.acceleo.debug.StackFrame#isCanStepIntoCurrentInstruction() step into
	 *            the current instruction}
	 */
	void setCurrentInstructionReply(Thread thread, EObject instruction, boolean canStepInto);

	/**
	 * Notify the suspension of the given {@link Thread} to the given
	 * {@link org.eclipse.acceleo.debug.StackFrame#getCurrentInstruction() instruction}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 */
	void suspendedReply(Thread thread);

	/**
	 * Notify the {@link terminateRequest termination} is done for the given {@link Thread} . The
	 * {@link Thread} {@link Thread#getorg.eclipse.acceleo.debug.State() org.eclipse.acceleo.debug.State}
	 * must be {@link org.eclipse.acceleo.debug.State#TERMINATING terminating}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 */
	void terminatedReply(Thread thread);

	/**
	 * Notify sets the {@link org.eclipse.acceleo.debug.Variable Variable} with the given
	 * {@link org.eclipse.acceleo.debug.Variable#getName() name} from the {@link Thread#getTopStackFrame()
	 * top stack frame} of the given {@link Thread} to the given {@link EObject value}.
	 * 
	 * @param stackFrame
	 *            the {@link StackFrame}
	 * @param declarationTypeName
	 *            the {@link org.eclipse.acceleo.debug.Variable#getDeclarationType() declaration type name}
	 * @param variableName
	 *            the {@link org.eclipse.acceleo.debug.Variable#getName() variable name}
	 * @param value
	 *            the {@link Object value} to set
	 * @param supportModifications
	 *            tells if the value can be changed
	 */
	void setVariableReply(StackFrame stackFrame, String declarationTypeName, String variableName,
			Object value, boolean supportModifications);

	/**
	 * Notifies a value change for the given {@link Variable} after a request.
	 * 
	 * @param variable
	 *            the {@link Variable}
	 * @param value
	 *            the {@link Object value} to set
	 */
	void setVariableValueReply(Variable variable, Object value);

	/**
	 * Requests the termination the given {@link Thread}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 */
	void terminateRequest(Thread thread);
}
