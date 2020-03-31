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
 * An EMF {@link IModelUpdater}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ModelUpdater implements IModelUpdater {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#terminateRequest(org.eclipse.acceleo.debug.DebugTarget)
	 */
	public void terminateRequest(DebugTarget target) {
		DebugTargetUtils.terminateRequest(target);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#disconnectRequest(org.eclipse.acceleo.debug.DebugTarget)
	 */
	public void disconnectRequest(DebugTarget target) {
		DebugTargetUtils.disconnectRequest(target);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#spawnRunningThreadReply(org.eclipse.acceleo.debug.DebugTarget,
	 *      java.lang.String, org.eclipse.emf.ecore.EObject)
	 */
	public Thread spawnRunningThreadReply(DebugTarget target, String threadName, EObject threadContext) {
		return DebugTargetUtils.spawnRunningThreadReply(target, threadName, threadContext);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#terminatedReply(org.eclipse.acceleo.debug.DebugTarget)
	 */
	public void terminatedReply(DebugTarget target) {
		DebugTargetUtils.terminatedReply(target);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#deleteVariableReply(org.eclipse.acceleo.debug.Thread,
	 *      java.lang.String)
	 */
	public void deleteVariableReply(Thread thread, String name) {
		ThreadUtils.deleteVariableReply(thread, name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#popStackFrameReply(org.eclipse.acceleo.debug.Thread)
	 */
	public StackFrame popStackFrameReply(Thread thread) {
		return ThreadUtils.popStackFrameReply(thread);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#pushStackFrameReply(org.eclipse.acceleo.debug.Thread,
	 *      java.lang.String, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, boolean)
	 */
	public StackFrame pushStackFrameReply(Thread thread, String name, EObject context, EObject instruction,
			boolean canStepInto) {
		return ThreadUtils.pushStackFrameReply(thread, name, context, instruction, canStepInto);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#stepIntoReply(org.eclipse.acceleo.debug.Thread)
	 */
	public void stepIntoReply(Thread thread) {
		ThreadUtils.stepIntoReply(thread);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#stepOverReply(org.eclipse.acceleo.debug.Thread)
	 */
	public void stepOverReply(Thread thread) {
		ThreadUtils.stepOverReply(thread);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#stepReturnReply(org.eclipse.acceleo.debug.Thread)
	 */
	public void stepReturnReply(Thread thread) {
		ThreadUtils.stepReturnReply(thread);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#resumedReply(org.eclipse.acceleo.debug.Thread)
	 */
	public void resumedReply(Thread thread) {
		ThreadUtils.resumedReply(thread);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#setCurrentInstructionReply(org.eclipse.acceleo.debug.Thread,
	 *      org.eclipse.emf.ecore.EObject, boolean)
	 */
	public void setCurrentInstructionReply(Thread thread, EObject instruction, boolean canStepInto) {
		ThreadUtils.setCurrentInstructionReply(thread, instruction, canStepInto);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#suspendedReply(org.eclipse.acceleo.debug.Thread)
	 */
	public void suspendedReply(Thread thread) {
		ThreadUtils.suspendedReply(thread);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#terminatedReply(org.eclipse.acceleo.debug.Thread)
	 */
	public void terminatedReply(Thread thread) {
		ThreadUtils.terminatedReply(thread);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#setVariableReply(org.eclipse.acceleo.debug.StackFrame,
	 *      java.lang.String, java.lang.String, java.lang.Object, boolean)
	 */
	public void setVariableReply(StackFrame stackFrame, String declarationTypeName, String variableName,
			Object value, boolean supportModifications) {
		ThreadUtils.setVariableReply(stackFrame, declarationTypeName, variableName, value,
				supportModifications);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#setVariableValueReply(org.eclipse.acceleo.debug.Variable,
	 *      java.lang.Object)
	 */
	public void setVariableValueReply(Variable variable, Object value) {
		ThreadUtils.setVariableValueReply(variable, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.util.IModelUpdater#terminateRequest(org.eclipse.acceleo.debug.Thread)
	 */
	public void terminateRequest(Thread thread) {
		ThreadUtils.terminateRequest(thread);
	}

}
