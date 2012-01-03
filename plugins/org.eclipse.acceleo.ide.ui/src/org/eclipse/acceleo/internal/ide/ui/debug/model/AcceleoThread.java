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
package org.eclipse.acceleo.internal.ide.ui.debug.model;

import org.eclipse.acceleo.internal.ide.ui.debug.core.ITemplateDebugger;
import org.eclipse.acceleo.internal.ide.ui.debug.core.StackInfo;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;

/**
 * An Acceleo thread that works like a VM for an Acceleo application. The Acceleo VM is single threaded.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoThread extends AbstractDebugElement implements IThread {

	/**
	 * Breakpoints this thread is suspended at or <code>null</code> if none.
	 */
	private IBreakpoint[] fBreakpoints;

	/**
	 * Whether this thread is stepping.
	 */
	private boolean fStepping;

	/**
	 * Whether this thread is suspended.
	 */
	private boolean suspended;

	/**
	 * Constructor. It constructs a new thread for the given debug target.
	 * 
	 * @param target
	 *            is the debug target
	 */
	public AcceleoThread(AcceleoDebugTarget target) {
		super(target);
	}

	/**
	 * Sets whether this thread is stepping.
	 * 
	 * @param stepping
	 *            indicates whether this thread is stepping
	 */
	protected void setStepping(boolean stepping) {
		fStepping = stepping;
	}

	/**
	 * Sets whether this thread is suspended.
	 * 
	 * @param suspended
	 *            whether this thread is suspended
	 */
	protected void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getStackFrames()
	 */
	public IStackFrame[] getStackFrames() throws DebugException {
		AcceleoDebugTarget debugTarget = (AcceleoDebugTarget)getDebugTarget();
		ITemplateDebugger debugger = debugTarget.getDebugger();
		StackInfo[] stack = debugger.getStack();
		IStackFrame[] ret = new IStackFrame[stack.length];
		for (int i = 0; i < ret.length; ++i) {
			ret[i] = new AcceleoStackFrame(this, stack[stack.length - i - 1]);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IThread#hasStackFrames()
	 */
	public boolean hasStackFrames() throws DebugException {
		return isSuspended();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getPriority()
	 */
	public int getPriority() throws DebugException {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getTopStackFrame()
	 */
	public IStackFrame getTopStackFrame() throws DebugException {
		IStackFrame[] frames = getStackFrames();
		if (frames.length > 0) {
			return frames[0];
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getName()
	 */
	public String getName() throws DebugException {
		return "Thread[1]"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IThread#getBreakpoints()
	 */
	public IBreakpoint[] getBreakpoints() {
		if (fBreakpoints == null) {
			return new IBreakpoint[0];
		}
		return fBreakpoints;
	}

	/**
	 * Sets the breakpoints this thread is suspended at, or <code>null</code> if none.
	 * 
	 * @param breakpoints
	 *            the breakpoints this thread is suspended at, or <code>null</code> if none
	 */
	protected void setBreakpoints(IBreakpoint[] breakpoints) {
		fBreakpoints = breakpoints;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	public boolean canResume() {
		return isSuspended();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	public boolean canSuspend() {
		return !isSuspended();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public boolean isSuspended() {
		return suspended;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public void resume() throws DebugException {
		getDebugTarget().resume();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public void suspend() throws DebugException {
		getDebugTarget().suspend();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepInto()
	 */
	public boolean canStepInto() {
		return isSuspended();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepOver()
	 */
	public boolean canStepOver() {
		return isSuspended();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepReturn()
	 */
	public boolean canStepReturn() {
		return isSuspended();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#isStepping()
	 */
	public boolean isStepping() {
		return fStepping;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepInto()
	 */
	public void stepInto() throws DebugException {
		((AcceleoDebugTarget)getDebugTarget()).stepInto();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepOver()
	 */
	public void stepOver() throws DebugException {
		((AcceleoDebugTarget)getDebugTarget()).stepOver();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepReturn()
	 */
	public void stepReturn() throws DebugException {
		((AcceleoDebugTarget)getDebugTarget()).stepReturn();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		return !isTerminated();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return getDebugTarget().isTerminated();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		getDebugTarget().terminate();
	}

}
