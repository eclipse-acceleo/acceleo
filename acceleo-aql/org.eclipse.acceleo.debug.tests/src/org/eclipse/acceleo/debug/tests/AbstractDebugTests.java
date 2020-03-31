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

import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.State;
import org.eclipse.acceleo.debug.Thread;

/**
 * Class containing tests utility methods.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractDebugTests {

	/**
	 * The {@link State#RUNNING} {@link Thread} index when calling
	 * {@link DebugTargetUtilsTests#createThreads(DebugTarget)}.
	 */
	protected final int runningThreadIndex = 0;

	/**
	 * The {@link State#SUSPENDING} {@link Thread} index when calling
	 * {@link DebugTargetUtilsTests#createThreads(DebugTarget)}.
	 */
	protected final int suspendingThreadIndex = 1;

	/**
	 * The {@link State#SUSPENDED} {@link Thread} index when calling
	 * {@link DebugTargetUtilsTests#createThreads(DebugTarget)}.
	 */
	protected final int suspendedThreadIndex = 2;

	/**
	 * The {@link State#STEPPING_INTO} {@link Thread} index when calling
	 * {@link DebugTargetUtilsTests#createThreads(DebugTarget)}.
	 */
	protected final int steppingIntoThreadIndex = 3;

	/**
	 * The {@link State#STEPPING_OVER} {@link Thread} index when calling
	 * {@link DebugTargetUtilsTests#createThreads(DebugTarget)}.
	 */
	protected final int steppingOverThreadIndex = 4;

	/**
	 * The {@link State#STEPPING_RETURN} {@link Thread} index when calling
	 * {@link DebugTargetUtilsTests#createThreads(DebugTarget)}.
	 */
	protected final int steppingReturnThreadIndex = 5;

	/**
	 * The {@link State#TERMINATING} {@link Thread} index when calling
	 * {@link DebugTargetUtilsTests#createThreads(DebugTarget)}.
	 */
	protected final int terminatingThreadIndex = 6;

	/**
	 * The {@link State#TERMINATED} {@link Thread} index when calling
	 * {@link DebugTargetUtilsTests#createThreads(DebugTarget)}.
	 */
	protected final int terminatedThreadIndex = 7;

	/**
	 * Creates {@link Thread} in the given {@link org.eclipse.acceleo.debug.DebugTargetState
	 * DebugTargetState}:
	 * <ul>
	 * <li>0 {@link State#RUNNING}.</li>
	 * <li>1 {@link State#SUSPENDING}.</li>
	 * <li>2 {@link State#SUSPENDED}.</li>
	 * <li>3 {@link State#STEPPING_INTO}.</li>
	 * <li>4 {@link State#STEPPING_OVER}.</li>
	 * <li>5 {@link State#STEPPING_RETURN}.</li>
	 * <li>6 {@link State#TERMINATING}.</li>
	 * <li>7 {@link State#TERMINATED}.</li>
	 * </ul>
	 * 
	 * @param target
	 *            the {@link org.eclipse.acceleo.debug.DebugTargetState DebugTargetState} to populate.
	 */
	protected void createThreads(DebugTarget target) {
		createRunningThread(target);
		createSuspendingThread(target);
		createSuspendedThread(target);
		createSteppingIntoThread(target);
		createSteppingOverThread(target);
		createSteppingReturnThread(target);
		createTerminatingThread(target);
		createTerminatedThread(target);
	}

	/**
	 * Creates a {@link State#TERMINATED} {@link Thread} in the given
	 * {@link org.eclipse.acceleo.debug.DebugTargetState DebugTargetState}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	protected void createTerminatedThread(DebugTarget target) {
		final Thread terminatedThread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		terminatedThread.setName("terminated thread");
		terminatedThread.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		terminatedThread.setDebugTarget(target);
		terminatedThread.setState(State.TERMINATED);
		StackFrame terminatedFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		terminatedFrame.setName("terminated frame");
		terminatedFrame.setContext(terminatedThread.getContext());
		terminatedThread.setBottomStackFrame(terminatedFrame);
		terminatedThread.setTopStackFrame(terminatedFrame);
	}

	/**
	 * Creates a {@link State#TERMINATING} {@link Thread} in the given
	 * {@link org.eclipse.acceleo.debug.DebugTargetState DebugTargetState}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	protected void createTerminatingThread(DebugTarget target) {
		final Thread terminatingThread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		terminatingThread.setName("terminating thread");
		terminatingThread.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		terminatingThread.setDebugTarget(target);
		terminatingThread.setState(State.TERMINATING);
		StackFrame terminatingFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		terminatingFrame.setName("terminating frame");
		terminatingFrame.setContext(terminatingThread.getContext());
		terminatingThread.setBottomStackFrame(terminatingFrame);
		terminatingThread.setTopStackFrame(terminatingFrame);
	}

	/**
	 * Creates a {@link State#STEPPING_RETURN} {@link Thread} in the given
	 * {@link org.eclipse.acceleo.debug.DebugTargetState DebugTargetState}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	protected void createSteppingReturnThread(DebugTarget target) {
		final Thread steppingReturnThread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		steppingReturnThread.setName("stepping return thread");
		steppingReturnThread.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		steppingReturnThread.setDebugTarget(target);
		steppingReturnThread.setState(State.STEPPING_RETURN);
		StackFrame steppingReturnFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		steppingReturnFrame.setName("stepping return frame");
		steppingReturnFrame.setContext(steppingReturnThread.getContext());
		steppingReturnThread.setBottomStackFrame(steppingReturnFrame);
		steppingReturnThread.setTopStackFrame(steppingReturnFrame);
	}

	/**
	 * Creates a {@link State#STEPPING_OVER} {@link Thread} in the given
	 * {@link org.eclipse.acceleo.debug.DebugTargetState DebugTargetState}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	protected void createSteppingOverThread(DebugTarget target) {
		final Thread steppingOverThread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		steppingOverThread.setName("stepping over thread");
		steppingOverThread.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		steppingOverThread.setDebugTarget(target);
		steppingOverThread.setState(State.STEPPING_OVER);
		StackFrame steppingOverFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		steppingOverFrame.setName("stepping over frame");
		steppingOverFrame.setContext(steppingOverThread.getContext());
		steppingOverThread.setBottomStackFrame(steppingOverFrame);
		steppingOverThread.setTopStackFrame(steppingOverFrame);
	}

	/**
	 * Creates a {@link State#STEPPING_INTO} {@link Thread} in the given
	 * {@link org.eclipse.acceleo.debug.DebugTargetState DebugTargetState}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	protected void createSteppingIntoThread(DebugTarget target) {
		final Thread steppingIntoThread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		steppingIntoThread.setName("stepping into thread");
		steppingIntoThread.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		steppingIntoThread.setDebugTarget(target);
		steppingIntoThread.setState(State.STEPPING_INTO);
		StackFrame steppingIntoFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		steppingIntoFrame.setName("stepping into frame");
		steppingIntoFrame.setContext(steppingIntoThread.getContext());
		steppingIntoThread.setBottomStackFrame(steppingIntoFrame);
		steppingIntoThread.setTopStackFrame(steppingIntoFrame);
	}

	/**
	 * Creates a {@link State#SUSPENDED} {@link Thread} in the given
	 * {@link org.eclipse.acceleo.debug.DebugTargetState DebugTargetState}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	protected void createSuspendedThread(DebugTarget target) {
		final Thread suspendedThread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		suspendedThread.setName("suspended thread");
		suspendedThread.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		suspendedThread.setDebugTarget(target);
		suspendedThread.setState(State.SUSPENDED);
		StackFrame suspendedFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		suspendedFrame.setName("suspended frame");
		suspendedFrame.setContext(suspendedThread.getContext());
		suspendedThread.setBottomStackFrame(suspendedFrame);
		suspendedThread.setTopStackFrame(suspendedFrame);
	}

	/**
	 * Creates a {@link State#SUSPENDING} {@link Thread} in the given
	 * {@link org.eclipse.acceleo.debug.DebugTargetState DebugTargetState}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	protected void createSuspendingThread(DebugTarget target) {
		final Thread suspendingThread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		suspendingThread.setName("suspending thread");
		suspendingThread.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		suspendingThread.setDebugTarget(target);
		suspendingThread.setState(State.SUSPENDING);
		StackFrame suspendingFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		suspendingFrame.setName("suspending frame");
		suspendingFrame.setContext(suspendingThread.getContext());
		suspendingThread.setBottomStackFrame(suspendingFrame);
		suspendingThread.setTopStackFrame(suspendingFrame);
	}

	/**
	 * Creates a {@link State#RUNNING} {@link Thread} in the given
	 * {@link org.eclipse.acceleo.debug.DebugTargetState DebugTargetState}.
	 * 
	 * @param target
	 *            the {@link DebugTarget}
	 */
	protected void createRunningThread(DebugTarget target) {
		final Thread runningThread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		runningThread.setName("runnning thread");
		runningThread.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		runningThread.setDebugTarget(target);
		runningThread.setState(State.RUNNING);
		StackFrame runningFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		runningFrame.setName("running frame");
		runningFrame.setContext(runningThread.getContext());
		runningThread.setBottomStackFrame(runningFrame);
		runningThread.setTopStackFrame(runningFrame);
	}

}
