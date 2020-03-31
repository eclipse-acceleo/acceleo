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

import java.util.Objects;

import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.DebugTargetState;
import org.eclipse.acceleo.debug.Register;
import org.eclipse.acceleo.debug.RegisterGroup;
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.State;
import org.eclipse.acceleo.debug.Thread;
import org.eclipse.acceleo.debug.Variable;
import org.eclipse.emf.ecore.EObject;

/**
 * {@link DebugTarget} utility class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class DebugTargetUtils {

	/**
	 * Constructor.
	 */
	private DebugTargetUtils() {
		// nothing to do here.
	}

	/**
	 * Creates a {@link DebugTarget} with the given {@link DebugTarget#getName() name} and
	 * {@link DebugTarget#getContext() context}.
	 * 
	 * @param name
	 *            the {@link DebugTarget#getName() debug target name}
	 * @param context
	 *            {@link DebugTarget#getContext() debug target context}
	 * @return the created {@link DebugTarget} with the given {@link DebugTarget#getName() name} and
	 *         {@link DebugTarget#getContext() context}
	 */
	public static DebugTarget createDebugTarget(String name, EObject context) {
		final DebugTarget res = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();

		Objects.requireNonNull(name, "the debug target name can't be null.");
		Objects.requireNonNull(context, "the debug target context can't be null.");

		res.setName(name);
		res.setContext(context);
		res.setState(DebugTargetState.CONNECTED);

		return res;
	}

	/**
	 * Request resuming of the given {@link DebugTarget}. The {@link DebugTarget}
	 * {@link DebugTarget#getState() state} must be {@link DebugTargetState#CONNECTED connected}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	public static void resumeRequest(DebugTarget target) {
		if (target.getState() != DebugTargetState.CONNECTED) {
			throw new IllegalStateException("can't resume a not connected debug target.");
		}

		if (canResume(target)) {
			for (Thread thread : target.getThreads()) {
				if (ThreadUtils.canResume(thread)) {
					ThreadUtils.resumedReply(thread);
				}
			}
		}
	}

	/**
	 * Tells if a {@link DebugTarget} can be resumed.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 * @return <code>true</code> if {@link DebugTarget} {@link DebugTarget#getState() state} is
	 *         {@link DebugTargetState#CONNECTED connected} and at least one of its
	 *         {@link DebugTarget#getThreads() threads} {@link ThreadUtils#canResume(Thread) can be resumed}.
	 */
	public static boolean canResume(DebugTarget target) {
		boolean res = false;

		if (target.getState() == DebugTargetState.CONNECTED) {
			for (Thread thread : target.getThreads()) {
				if (ThreadUtils.canResume(thread)) {
					res = true;
					break;
				}
			}
		}

		return res;
	}

	/**
	 * Request suspension of the given {@link DebugTarget}. The {@link DebugTarget}
	 * {@link DebugTarget#getState() state} must be {@link DebugTargetState#CONNECTED connected}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	public static void suspendRequest(DebugTarget target) {
		if (target.getState() != DebugTargetState.CONNECTED) {
			throw new IllegalStateException("can't suspend a not connected debug target.");
		}

		if (canSuspend(target)) {
			for (Thread thread : target.getThreads()) {
				if (ThreadUtils.canSuspend(thread)) {
					ThreadUtils.suspendRequest(thread);
				}
			}
		}
	}

	/**
	 * Tells if a {@link DebugTarget} can be suspended.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 * @return <code>true</code> if {@link DebugTarget} {@link DebugTarget#getState() state} is
	 *         {@link DebugTargetState#CONNECTED connected} and at least one of its
	 *         {@link DebugTarget#getThreads() threads} {@link ThreadUtils#canSuspend(Thread) can be
	 *         suspended} .
	 */
	public static boolean canSuspend(DebugTarget target) {
		boolean res = false;

		if (target.getState() == DebugTargetState.CONNECTED) {
			for (Thread thread : target.getThreads()) {
				if (ThreadUtils.canSuspend(thread)) {
					res = true;
					break;
				}
			}
		}

		return res;
	}

	/**
	 * Requests the termination the given {@link DebugTarget}. The {@link DebugTarget}
	 * {@link DebugTarget#getState() state} must be {@link DebugTargetState#CONNECTED connected}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	public static void terminateRequest(DebugTarget target) {
		if (canTerminate(target)) {
			target.setState(DebugTargetState.TERMINATING);
		} else {
			throw new IllegalStateException("can't terminate a not connected debug target.");
		}
	}

	/**
	 * Tells if the given {@link DebugTarget} can be terminated.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 * @return <code>true</code> if the given {@link DebugTarget} {@link DebugTarget#getState() state} is
	 *         {@link DebugTargetState#CONNECTED connected}
	 */
	public static boolean canTerminate(DebugTarget target) {
		return target.getState() == DebugTargetState.CONNECTED;
	}

	/**
	 * Notify the {@link DebugTargetUtils#terminateRequest(DebugTarget) termination} is done for the given
	 * {@link DebugTarget}. The {@link DebugTarget} {@link DebugTarget#getState() state} must be
	 * {@link DebugTargetState#TERMINATING terminating}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	public static void terminatedReply(DebugTarget target) {
		if (target.getState() != DebugTargetState.TERMINATED && target
				.getState() != DebugTargetState.DISCONNECTED) {
			target.setState(DebugTargetState.TERMINATED);
		} else {
			throw new IllegalStateException("can't terminale a not connected or a terminated debug target.");
		}
	}

	/**
	 * Requests disconnection the given {@link DebugTarget}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	public static void disconnectRequest(DebugTarget target) {
		if (canDisconnect(target)) {
			target.setState(DebugTargetState.DISCONNECTED);
		} else {
			throw new IllegalStateException("can't disconnect a not connected debug target.");
		}
	}

	/**
	 * Tells if the given {@link DebugTarget} can be disconnected.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 * @return <code>true</code> if the given {@link DebugTarget} {@link DebugTarget#getState() state} is
	 *         {@link DebugTargetState#CONNECTED connected}
	 */
	public static boolean canDisconnect(DebugTarget target) {
		return target.getState() == DebugTargetState.CONNECTED;
	}

	/**
	 * Spawn a running {@link Thread} in the given {@link DebugTarget}.
	 * 
	 * @param target
	 *            the {@link DebugTarget} that will contains the spawned {@link Thread}
	 * @param threadName
	 *            the {@link Thread#getName() thread name}
	 * @param threadContext
	 *            the {@link Thread#getContext() thread context}
	 * @return the created Thread
	 */
	public static Thread spawnRunningThreadReply(DebugTarget target, String threadName,
			EObject threadContext) {
		if (canSpawnThread(target, threadName)) {
			final Thread thread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
			thread.setName(threadName);
			thread.setContext(threadContext);
			thread.setState(State.RUNNING);
			target.getThreads().add(thread);
			return thread;
		} else {
			throw new IllegalStateException(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.");
		}
	}

	/**
	 * Spawn a suspended {@link Thread} in the given {@link DebugTarget}.
	 * 
	 * @param target
	 *            the {@link DebugTarget} that will contains the spawned {@link Thread}
	 * @param threadName
	 *            the {@link Thread#getName() thread name}
	 * @param frameName
	 *            the {@link StackFrame#getName() stack frame name}
	 * @param threadContext
	 *            the {@link Thread#getContext() thread context}
	 * @param frameInstruction
	 *            the {@link StackFrame#getCurrentInstruction() stack frame current instruction}
	 * @param canStepInto
	 *            tells if the given {@link StackFrame#getCurrentInstruction() instruction} can be stepped
	 *            into
	 */
	public static void spawnSuspendedThreadReply(DebugTarget target, String threadName, String frameName,
			EObject threadContext, EObject frameInstruction, boolean canStepInto) {
		if (canSpawnThread(target, threadName)) {
			final Thread thread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
			thread.setName(threadName);
			thread.setContext(threadContext);
			thread.setState(State.SUSPENDED);
			final StackFrame topFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
			topFrame.setName(frameName);
			topFrame.setCurrentInstruction(frameInstruction);
			topFrame.setCanStepIntoCurrentInstruction(canStepInto);
			topFrame.setCurrentInstruction(frameInstruction);
			topFrame.setContext(thread.getContext());
			thread.setBottomStackFrame(topFrame);
			thread.setTopStackFrame(topFrame);
			target.getThreads().add(thread);
		} else {
			throw new IllegalStateException(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.");
		}
	}

	/**
	 * Tells if a {@link Thread} with the given {@link Thread#getName() name} can be spawned in the given
	 * {@link DebugTarget}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 * @param name
	 *            the {@link Thread#getName() thread name}
	 * @return <code>true</code> if the {@link DebugTarget} is connected and has no {@link Thread} with the
	 *         given {@link Thread#getName() name}, <code>false</code> otherwise
	 */
	public static boolean canSpawnThread(DebugTarget target, String name) {
		return target.getState() == DebugTargetState.CONNECTED && getThread(target, name) == null;
	}

	/**
	 * Gets the {@link Thread} with the given {@link Thread#getName() name} in the given {@link DebugTarget}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 * @param name
	 *            the {@link Thread} {@link Thread#getName() name}
	 * @return the {@link Thread} with the given {@link Thread#getName() name} in the given
	 *         {@link DebugTarget} if any, <code>null</code> otherwise
	 */
	public static Thread getThread(DebugTarget target, String name) {
		Thread res = null;

		// TODO optimize with a map adapter
		for (Thread thread : target.getThreads()) {
			if (name.equals(thread.getName())) {
				res = thread;
				break;
			}
		}

		return res;
	}

	/**
	 * Gets the {@link StackFrame} with the given {@link StackFrame#getName() name} from the given
	 * {@link Thread}.
	 * 
	 * @param thread
	 *            the {@link Thread}
	 * @param name
	 *            the {@link StackFrame#getName() name}
	 * @return the {@link StackFrame} with the given {@link StackFrame#getName() name} from the given
	 *         {@link Thread} if any, <code>null</code> otherwise
	 */
	public static StackFrame getStackFrame(Thread thread, String name) {
		StackFrame res = null;

		StackFrame currentFrame = thread.getBottomStackFrame();
		while (currentFrame != null) {
			if (name.equals(currentFrame.getName())) {
				res = currentFrame;
				break;
			}
			currentFrame = currentFrame.getChildFrame();
		}

		return res;
	}

	/**
	 * Gets the {@link Variable} with the given {@link Variable#getName() name} from the given
	 * {@link StackFrame}.
	 * 
	 * @param frame
	 *            the {@link StackFrame}
	 * @param name
	 *            the {@link Variable#getName() variable name}
	 * @return the {@link Variable} with the given {@link Variable#getName() name} from the given
	 *         {@link StackFrame} if any, <code>null</code> otherwise
	 */
	public static Variable getVariable(StackFrame frame, String name) {
		Variable res = null;

		// TODO optimize with a map adapter
		for (Variable variable : frame.getVariables()) {
			if (name.equals(variable.getName())) {
				res = variable;
				break;
			}
		}

		return res;
	}

	/**
	 * Sets the {@link Variable} with the given {@link Variable#getName() name} from the given
	 * {@link StackFrame} to the given {@link Object value}.
	 * 
	 * @param frame
	 *            the {@link StackFrame}
	 * @param declarationTypeName
	 *            the {@link Variable#getDeclarationType() declaration type name}
	 * @param name
	 *            the {@link Variable#getName() variable name}
	 * @param value
	 *            the {@link Object value} to set
	 * @param supportModifications
	 *            tells if the value can be changed
	 */
	public static void setVariable(StackFrame frame, String declarationTypeName, String name, Object value,
			boolean supportModifications) {
		Variable variable = getVariable(frame, name);
		if (variable == null) {
			variable = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
			variable.setDeclarationType(declarationTypeName);
			variable.setName(name);
			variable.setSupportModifications(supportModifications);
			frame.getVariables().add(variable);
		} else {
			variable.setValueChanged(true);
		}

		variable.setValue(value);
	}

	/**
	 * Gets the {@link RegisterGroup} with the given {@link RegisterGroup#getName() name} from the given
	 * {@link StackFrame}.
	 * 
	 * @param frame
	 *            the {@link StackFrame}
	 * @param name
	 *            the {@link RegisterGroup#getName() register group name}
	 * @return the {@link RegisterGroup} with the given {@link RegisterGroup#getName() name} from the given
	 *         {@link StackFrame} if any, <code>null</code> otherwise
	 */
	public static RegisterGroup getRegisterGroup(StackFrame frame, String name) {
		RegisterGroup res = null;

		// TODO optimize with a map adapter
		for (RegisterGroup registerGroup : frame.getRegisterGroups()) {
			if (name.equals(registerGroup.getName())) {
				res = registerGroup;
				break;
			}
		}

		return res;
	}

	/**
	 * Gets the {@link Register} with the given {@link Register#getName() name} from the given
	 * {@link RegisterGroup}.
	 * 
	 * @param group
	 *            the {@link RegisterGroup}
	 * @param name
	 *            the {@link Register#getName() register name}
	 * @return the {@link Register} with the given {@link Register#getName() name} from the given
	 *         {@link RegisterGroup} if any, <code>null</code> otherwise
	 */
	public static Register getRegister(RegisterGroup group, String name) {
		Register res = null;

		// TODO optimize with a map adapter
		for (Register register : group.getRegisters()) {
			if (name.equals(register.getName())) {
				res = register;
				break;
			}
		}

		return res;
	}

	/**
	 * Gets the {@link Register} with the given {@link Register#getName() name} from the given
	 * {@link RegisterGroup}.
	 * 
	 * @param group
	 *            the {@link RegisterGroup}
	 * @param name
	 *            the {@link Register#getName() register name}
	 * @param value
	 *            the {@link Object value} to set
	 */
	public static void setRegister(RegisterGroup group, String name, Object value) {
		Register register = getRegister(group, name);
		if (register == null) {
			register = DebugPackage.eINSTANCE.getDebugFactory().createRegister();
			register.setName(name);
			group.getRegisters().add(register);
		}

		register.setValue(value);
	}

	/**
	 * Tells if the given {@link DebugTarget} is suspended.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 * @return <code>true</code> if all {@link DebugTarget#getThreads() its threads} are
	 *         {@link ThreadUtils#isSuspended(Thread) suspended}.
	 */
	public static boolean isSuspended(DebugTarget target) {
		boolean res = true;

		for (Thread thread : target.getThreads()) {
			if (!ThreadUtils.isSuspended(thread)) {
				res = false;
				break;
			}
		}

		return res;
	}

	/**
	 * Tells if the given {@link DebugTarget} is {@link DebugTargetState#TERMINATED terminated}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 * @return <code>true</code> if the {@link DebugTarget} {@link DebugTarget#getState() state} is
	 *         {@link DebugTargetState#TERMINATED terminated}, <code>false</code> otherwise
	 */
	public static boolean isTerminated(DebugTarget target) {
		return target.getState() == DebugTargetState.TERMINATED;
	}

}
