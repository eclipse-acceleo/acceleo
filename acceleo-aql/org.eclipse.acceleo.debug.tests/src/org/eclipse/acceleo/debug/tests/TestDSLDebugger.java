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
package org.eclipse.acceleo.debug.tests;

import java.util.Map;

import org.eclipse.acceleo.debug.AbstractDSLDebugger;
import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.emf.ecore.EObject;

/**
 * An {@link AbstractDSLDebugger} for test purpose.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TestDSLDebugger extends AbstractDSLDebugger {

	/**
	 * An interpreter.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private class Interpreter implements Runnable {
		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			spawnRunningThread(Thread.currentThread().getId(), Thread.currentThread().getName(),
					INSTRUCTION_1);
			boolean terminated = false;
			while (!terminated) {
				for (int i = 0; i < INSTRUCTIONS.length && !terminated; ++i) {
					terminated = control(Thread.currentThread().getId(), INSTRUCTIONS[i]);
				}
			}
			if (!isTerminated(Thread.currentThread().getId())) {
				terminated(Thread.currentThread().getId());
			}
		}
	}

	/**
	 * The thread name.
	 */
	public static final String THREAD_NAME_1 = "THREAD_1";

	/**
	 * The thread name.
	 */
	public static final String THREAD_NAME_2 = "THREAD_2";

	/**
	 * The first {@link EObject instruction}.
	 */
	public static final EObject INSTRUCTION_1 = DebugPackage.eINSTANCE.getDebugFactory().createVariable();

	/**
	 * The second {@link EObject instruction}.
	 */
	public static final EObject INSTRUCTION_2 = DebugPackage.eINSTANCE.getDebugFactory().createVariable();

	/**
	 * The third {@link EObject instruction}.
	 */
	public static final EObject INSTRUCTION_3 = DebugPackage.eINSTANCE.getDebugFactory().createVariable();

	/**
	 * Instructions of the test interpreter.
	 */
	public static final EObject[] INSTRUCTIONS = new EObject[] {INSTRUCTION_1, INSTRUCTION_2,
			INSTRUCTION_3, };

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#disconnect()} call has been made.
	 */
	private boolean disconnectCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#start()} call has been made.
	 */
	private boolean startCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#stepInto(String)} call has been made.
	 */
	private boolean stepIntoCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#stepOver(String)} call has been made.
	 */
	private boolean stepOverCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#stepReturn(String)} call has been made.
	 */
	private boolean stepReturnCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#resume()} call has been made.
	 */
	private boolean resumeCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#resume(String)} call has been made.
	 */
	private boolean resumeThreadCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#suspend()} call has been made.
	 */
	private boolean suspendCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#suspend(String)} call has been made.
	 */
	private boolean suspendThreadCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#terminate()} call has been made.
	 */
	private boolean terminateCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#terminate(String)} call has been made.
	 */
	private boolean terminateThreadCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#updateState(String, EObject)} call has been
	 * made.
	 */
	private boolean updateStateCall;

	/**
	 * A Call to {@link org.eclipse.acceleo.debug.IDSLDebugger#getState()} call has been made.
	 */
	private boolean getStateCall;

	/**
	 * A call to
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#getNextInstruction(String, EObject, org.eclipse.acceleo.debug.IDSLDebugger.Stepping)}
	 * call has been made.
	 */
	private boolean getNextInstructionCall;

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#validateVariableValue(String, String, String)}
	 * call has been made.
	 */
	private boolean validateVariableValueCall;

	/**
	 * A call to
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#getVariableValue(String, String, String, String)} call
	 * has been made.
	 */
	private boolean getVariableValueCall;

	/**
	 * A call to
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#setVariableValue(String, String, String, Object)} call
	 * has been made.
	 */
	private boolean setVariableValueCall;

	/**
	 * Constructor.
	 * 
	 * @param target
	 *            the {@link org.eclipse.acceleo.debug.event.DSLDebugEventDispatcher dispatcher} for
	 *            asynchronous communication or the {@link org.eclipse.acceleo.debug.ide.DSLDebugTargetAdapter
	 *            target} for synchronous communication
	 */
	public TestDSLDebugger(IDSLDebugEventProcessor target) {
		super(target);
	}

	public void start(boolean noDebug, Map<String, Object> arguments) {
		startCall = true;
		new Thread(new Interpreter(), THREAD_NAME_1).start();
		new Thread(new Interpreter(), THREAD_NAME_2).start();
	}

	public void disconnect() {
		disconnectCall = true;
	}

	@Override
	public void stepInto(Long threadID) {
		stepIntoCall = true;
		super.stepInto(threadID);
	}

	@Override
	public void stepOver(Long threadID) {
		stepOverCall = true;
		super.stepOver(threadID);
	}

	@Override
	public void stepReturn(Long threadID) {
		stepReturnCall = true;
		super.stepReturn(threadID);
	}

	@Override
	public void resume() {
		resumeCall = true;
		super.resume();
	}

	@Override
	public void resume(Long threadID) {
		resumeThreadCall = true;
		super.resume(threadID);
	}

	@Override
	public void suspend() {
		suspendCall = true;
		super.suspend();
	}

	@Override
	public void suspend(Long threadID) {
		suspendThreadCall = true;
		super.suspend(threadID);
	}

	@Override
	public void terminate() {
		terminateCall = true;
		super.terminate();
	}

	@Override
	public void terminate(Long threadID) {
		terminateThreadCall = true;
		super.terminate(threadID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.org.eclipse.acceleo.debug.ide.IDSLDebugger#canStepInto(java.lang.String,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public boolean canStepInto(Long threadID, EObject instruction) {
		return false;
	}

	public void updateState(Long threadID, EObject instruction) {
		updateStateCall = true;
	}

	public DebugTarget getState() {
		getStateCall = true;
		return null;
	}

	@Override
	public EObject getNextInstruction(Long threadID, EObject currentInstruction, Stepping stepping) {
		getNextInstructionCall = true;
		return null;
	}

	public boolean validateVariableValue(Long threadID, String variableName, String value) {
		validateVariableValueCall = true;
		return false;
	}

	public Object getVariableValue(Long threadID, String stackName, String variableName, String value) {
		getVariableValueCall = true;
		return null;
	}

	public void setVariableValue(Long threadID, String stackName, String variableName, Object value) {
		setVariableValueCall = true;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#disconnect()} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#disconnect()} has
	 *         been made.
	 */
	public boolean hasDisconnectCall() {
		return disconnectCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#start()} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#start()} has been
	 *         made.
	 */
	public boolean hasStartCall() {
		return startCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#stepInto(String)} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#stepInto(String)}
	 *         has been made.
	 */
	public boolean hasStepIntoCall() {
		return stepIntoCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#stepOver(String)} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#stepOver(String)}
	 *         has been made.
	 */
	public boolean hasStepOverCall() {
		return stepOverCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#stepReturn(String)} call has been made.
	 * 
	 * @return <code>true</code> if a call to
	 *         {@link org.eclipse.acceleo.debug.IDSLDebugger#stepReturn(String)} has been made.
	 */
	public boolean hasStepReturnCall() {
		return stepReturnCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#resume()} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#resume()} has been
	 *         made.
	 */
	public boolean hasResumeCall() {
		return resumeCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#resume(String)} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#resume(String)}
	 *         has been made.
	 */
	public boolean hasResumeThreadCall() {
		return resumeThreadCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#suspend()} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#suspend()} has
	 *         been made.
	 */
	public boolean hasSuspendCall() {
		return suspendCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#suspend(String)} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#suspend(String)}
	 *         has been made.
	 */
	public boolean hasSuspendThreadCall() {
		return suspendThreadCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#terminate()} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#terminate()} has
	 *         been made.
	 */
	public boolean hasTerminateCall() {
		return terminateCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#terminate(String)} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#terminate(String)}
	 *         has been made.
	 */
	public boolean hasTerminateThreadCall() {
		return terminateThreadCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#updateState(String, EObject)} call has been
	 * made.
	 * 
	 * @return <code>true</code> if a call to
	 *         {@link org.eclipse.acceleo.debug.IDSLDebugger#updateState(String, EObject)} has been made
	 */
	public boolean hasUpdateStateCall() {
		return updateStateCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#getState()} call has been made.
	 * 
	 * @return <code>true</code> if a call to {@link org.eclipse.acceleo.debug.IDSLDebugger#getState()} has
	 *         been made
	 */
	public boolean hasGetStateCall() {
		return getStateCall;
	}

	/**
	 * A call to
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#getNextInstruction(String, EObject, org.eclipse.acceleo.debug.IDSLDebugger.Stepping)}
	 * call has been made.
	 * 
	 * @return <code>true</code> if a call to
	 *         {@link org.eclipse.acceleo.debug.IDSLDebugger#getNextInstruction(String, EObject, org.eclipse.acceleo.debug.IDSLDebugger.Stepping)}
	 *         has been made
	 */
	public boolean hasGetNextInstructionCall() {
		return getNextInstructionCall;
	}

	/**
	 * A call to {@link org.eclipse.acceleo.debug.IDSLDebugger#validateVariableValue(String, String, String)}
	 * call has been made.
	 * 
	 * @return <code>true</code> if a call to
	 *         {@link org.eclipse.acceleo.debug.IDSLDebugger#validateVariableValue(String, String, String)}
	 *         call has been made
	 */
	public boolean hasValidateVariableValueCall() {
		return validateVariableValueCall;
	}

	/**
	 * A call to
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#getVariableValue(String, String, String, String)} call
	 * has been made.
	 */
	public boolean hasGetVariableValueCall() {
		return getVariableValueCall;
	}

	/**
	 * A call to
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#setVariableValue(String, String, String, Object)} call
	 * has been made.
	 */
	public boolean hasSetVariableValueCall() {
		return setVariableValueCall;
	}

}
