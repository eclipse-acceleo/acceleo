/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.util;

import org.eclipse.acceleo.debug.IDSLDebugger;
import org.eclipse.acceleo.debug.IDSLDebugger.Stepping;
import org.eclipse.emf.ecore.EObject;

/**
 * Controls the execution of a Java {@link Thread}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ThreadController {

	/**
	 * The {@link IDSLDebugger} responsible for the Java {@link Thread}.
	 */
	private final IDSLDebugger debugger;

	/**
	 * The {@link Thread#getId() thread ID}.
	 */
	private final Long threadID;

	/**
	 * The last suspended instruction depth.
	 */
	private int lastSuspendedInstructionDepth;

	/**
	 * Determines if execution is suspended.
	 */
	private boolean suspended;

	/**
	 * Determines if execution is terminated.
	 */
	private boolean terminated;

	/**
	 * Determines if the execution is terminated.
	 */
	private Stepping stepping = Stepping.NONE;

	/**
	 * Tells if we were signaled with {@link Object#notify()}.
	 */
	private boolean wasSignalled;

	/**
	 * Constructor.
	 * 
	 * @param debugger
	 *            the {@link IDSLDebugger} responsible for the Java {@link Thread}
	 * @param threadID
	 *            the name of the thread
	 */
	public ThreadController(IDSLDebugger debugger, Long threadID) {
		this.debugger = debugger;
		this.threadID = threadID;
	}

	/**
	 * Suspend on the next {@link ThreadController#control(EObject) controlled} {@link EObject instruction}.
	 */
	public void suspend() {
		this.suspended = true;
		stepping = Stepping.NONE;
	}

	/**
	 * Resume the execution.
	 */
	public synchronized void resume() {
		suspended = false;
		stepping = Stepping.NONE;
		wakeUp();
	}

	/**
	 * Terminates.
	 */
	public synchronized void terminate() {
		terminated = true;
		suspended = false;
		stepping = Stepping.NONE;
		wakeUp();
	}

	/**
	 * Resumes for a step into.
	 */
	public synchronized void stepInto() {
		this.suspended = false;
		stepping = Stepping.STEP_INTO;
		wakeUp();
	}

	/**
	 * Resumes for a step over.
	 */
	public synchronized void stepOver() {
		this.suspended = false;
		stepping = Stepping.STEP_OVER;
		wakeUp();
	}

	/**
	 * Resumes for a step return.
	 */
	public synchronized void stepReturn() {
		this.suspended = false;
		stepping = Stepping.STEP_RETURN;
		wakeUp();
	}

	/**
	 * Wakes up the {@link Thread}.
	 */
	public synchronized void wakeUp() {
		wasSignalled = true;
		notify();
	}

	/**
	 * Puts the {@link Thread} asleep.
	 */
	public synchronized void putAsleep() {
		while (!wasSignalled) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO log ? throw ? is it important ?
				e.printStackTrace();
			}
		}
		wasSignalled = false;
	}

	/**
	 * Controls the given instruction.
	 * 
	 * @param instruction
	 *            the {@link EObject instruction}
	 * @return <code>false</code> if execution shall stop, <code>true</code> if execution shall continue
	 */
	public synchronized boolean control(EObject instruction) {
		boolean res = !debugger.isTerminated() && !terminated;
		if (res) {
			if (this.suspended) { // client request
				debugger.suspended(threadID);
				putAsleep();
				if (!debugger.isTerminated() && !terminated) {
					resuming(instruction);
				}
			} else if (debugger.shouldBreak(instruction)) { // breakpoint
				debugger.breaked(threadID);
				putAsleep();
				if (!debugger.isTerminated() && !terminated) {
					resuming(instruction);
				}
			} else if (stepping != Stepping.NONE && shouldStep()) { // stepping
				debugger.stepped(threadID);
				putAsleep();
				if (!debugger.isTerminated() && !terminated) {
					resuming(instruction);
				}
			}
		}
		return res;
	}

	private boolean shouldStep() {
		final boolean res;

		final int currentInstructionDepth = debugger.getStackFrame(threadID).size();
		switch (stepping) {
			case STEP_INTO:
				res = true;
				break;

			case STEP_OVER:
				res = currentInstructionDepth <= lastSuspendedInstructionDepth;
				break;

			case STEP_RETURN:
				res = currentInstructionDepth < lastSuspendedInstructionDepth;
				break;

			default:
				res = false;
				break;
		}

		return res;
	}

	/**
	 * Resumes.
	 * 
	 * @param instruction
	 *            the {@link EObject instruction}
	 */
	private void resuming(EObject instruction) {

		lastSuspendedInstructionDepth = debugger.getStackFrame(threadID).size();
		switch (stepping) {
			case STEP_INTO:
				debugger.steppingInto(threadID);
				break;

			case STEP_OVER:
				debugger.steppingOver(threadID);
				break;

			case STEP_RETURN:
				debugger.steppingReturn(threadID);
				break;

			default:
				if (!terminated) {
					debugger.resuming(threadID); // client resume request
				} else {
					debugger.terminated(threadID); // terminated
				}
				break;
		}

	}

}
