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

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.debug.core.ITemplateDebugger;
import org.eclipse.acceleo.internal.ide.ui.debug.core.ITemplateDebuggerListener;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.IBreakpointManagerListener;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IMemoryBlock;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;

/**
 * This is a debuggable execution context for an Acceleo Application. A debug target may represent a
 * debuggable process or a virtual machine. A debug target is the root of the debug element hierarchy. A debug
 * target contains threads. Minimally, a debug target supports the following:
 * <ul>
 * <li>terminate
 * <li>suspend/resume
 * <li>breakpoints
 * <li>disconnect
 * </ul>
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class AcceleoDebugTarget extends AbstractDebugElement implements IDebugTarget, IBreakpointManagerListener {
	/**
	 * The thread.
	 */
	protected AcceleoThread thread;

	/**
	 * The process.
	 */
	private AcceleoDebugProcess process;

	/**
	 * The current launch configuration which is the result of launching a debug session.
	 */
	private ILaunch launch;

	/**
	 * The template evaluation debugger.
	 */
	private ITemplateDebugger debugger;

	/**
	 * Indicates if the thread is terminated.
	 */
	private boolean terminated;

	/**
	 * The debugger events listener during a template evaluation.
	 */
	private ITemplateDebuggerListener debuggerListener;

	/**
	 * Debugger target events listener.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	protected class TemplateDebuggerListener implements ITemplateDebuggerListener {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.ide.ui.debug.core.ITemplateDebuggerListener#resumeClient()
		 */
		public void resumeClient() {
			thread.setSuspended(false);
			thread.setStepping(false);
			thread.fireResumeEvent(DebugEvent.CLIENT_REQUEST);

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.ide.ui.debug.core.ITemplateDebuggerListener#suspendBreakpoint()
		 */
		public void suspendBreakpoint() {
			thread.setSuspended(true);
			thread.setStepping(false);
			thread.fireSuspendEvent(DebugEvent.BREAKPOINT);

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.ide.ui.debug.core.ITemplateDebuggerListener#resumeStep()
		 */
		public void resumeStep() {
			thread.setSuspended(false);
			thread.setStepping(true);
			thread.fireResumeEvent(DebugEvent.STEP_OVER);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.ide.ui.debug.core.ITemplateDebuggerListener#suspendStep()
		 */
		public void suspendStep() {
			thread.setSuspended(true);
			thread.setStepping(false);
			thread.fireSuspendEvent(DebugEvent.STEP_END);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.ide.ui.debug.core.ITemplateDebuggerListener#start()
		 */
		public void start() {
			thread.fireCreationEvent();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.ide.ui.debug.core.ITemplateDebuggerListener#end()
		 */
		public void end() {
			try {
				terminate();
			} catch (DebugException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param aLaunch
	 *            is the current launch configuration
	 * @param aTemplateDebugger
	 *            is the template evaluation debugger
	 */
	public AcceleoDebugTarget(ILaunch aLaunch, ITemplateDebugger aTemplateDebugger) {
		super(null);
		launch = aLaunch;
		debugger = aTemplateDebugger;
		process = new AcceleoDebugProcess(this);
		launch.addProcess(process);
		thread = new AcceleoThread(this);
		IBreakpointManager breakpointManager = getBreakpointManager();
		breakpointManager.addBreakpointListener(this);
		breakpointManager.addBreakpointManagerListener(this);
		IBreakpoint[] breakpoints = getBreakpointManager().getBreakpoints();
		for (int i = 0; i < breakpoints.length; ++i) {
			if (breakpoints[i] instanceof AcceleoLineBreakpoint) {
				AcceleoLineBreakpoint lineBreakpoint = (AcceleoLineBreakpoint)breakpoints[i];
				try {
					if (lineBreakpoint.isEnabled()) {
						debugger.addBreakpoint(lineBreakpoint.getASTFragment());
					}
				} catch (CoreException e) {
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				}
			}
		}
		debuggerListener = new TemplateDebuggerListener();
		debugger.addListener(debuggerListener);
	}

	/**
	 * Gets the template evaluation debugger.
	 * 
	 * @return the template evaluation debugger
	 */
	public ITemplateDebugger getDebugger() {
		return debugger;
	}

	/**
	 * Go into the next evaluation step.
	 */
	public void stepInto() {
		debugger.stepInto();

	}

	/**
	 * Go over the next evaluation step.
	 */
	public void stepOver() {
		debugger.stepOver();

	}

	/**
	 * Go to the parent evaluation step.
	 */
	public void stepReturn() {
		debugger.stepReturn();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.debug.model.AbstractDebugElement#getLaunch()
	 */
	@Override
	public ILaunch getLaunch() {
		return launch;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getName()
	 */
	public String getName() throws DebugException {
		return process.getLabel();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getProcess()
	 */
	public IProcess getProcess() {
		return process;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#getThreads()
	 */
	public IThread[] getThreads() throws DebugException {
		IThread[] ret;
		if (thread != null) {
			ret = new IThread[] {thread };
		} else {
			ret = new IThread[] {};
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#hasThreads()
	 */
	public boolean hasThreads() throws DebugException {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDebugTarget#supportsBreakpoint(org.eclipse.debug.core.model.IBreakpoint)
	 */
	public boolean supportsBreakpoint(IBreakpoint breakpoint) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		return !terminated;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return terminated;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		thread = null;
		terminated = true;
		debugger.terminate();
		fireTerminateEvent();
		debugger.removeListener(debuggerListener);
		IBreakpointManager breakpointManager = getBreakpointManager();
		breakpointManager.removeBreakpointListener(this);
		breakpointManager.removeBreakpointManagerListener(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	public boolean canResume() {
		return !isTerminated();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	public boolean canSuspend() {
		return !isTerminated();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public boolean isSuspended() {
		return !isTerminated() && thread.isSuspended();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public void resume() throws DebugException {
		debugger.resume();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public void suspend() throws DebugException {
		debugger.suspend();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.IBreakpointListener#breakpointAdded(org.eclipse.debug.core.model.IBreakpoint)
	 */
	public void breakpointAdded(IBreakpoint breakpoint) {
		if (breakpoint instanceof AcceleoLineBreakpoint) {
			AcceleoLineBreakpoint acceleoLineBreakpoint = (AcceleoLineBreakpoint)breakpoint;
			debugger.addBreakpoint(acceleoLineBreakpoint.getASTFragment());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.IBreakpointListener#breakpointChanged(org.eclipse.debug.core.model.IBreakpoint,
	 *      org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointChanged(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (breakpoint instanceof AcceleoLineBreakpoint) {
			AcceleoLineBreakpoint acceleoLineBreakpoint = (AcceleoLineBreakpoint)breakpoint;
			try {
				if (acceleoLineBreakpoint.isEnabled()) {
					debugger.addBreakpoint(acceleoLineBreakpoint.getASTFragment());
				} else {
					debugger.removeBreakpoint(acceleoLineBreakpoint.getASTFragment());
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.IBreakpointListener#breakpointRemoved(org.eclipse.debug.core.model.IBreakpoint,
	 *      org.eclipse.core.resources.IMarkerDelta)
	 */
	public void breakpointRemoved(IBreakpoint breakpoint, IMarkerDelta delta) {
		if (breakpoint instanceof AcceleoLineBreakpoint) {
			AcceleoLineBreakpoint acceleoLineBreakpoint = (AcceleoLineBreakpoint)breakpoint;
			debugger.removeBreakpoint(acceleoLineBreakpoint.getASTFragment());
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#canDisconnect()
	 */
	public boolean canDisconnect() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#disconnect()
	 */
	public void disconnect() throws DebugException {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IDisconnect#isDisconnected()
	 */
	public boolean isDisconnected() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#getMemoryBlock(long, long)
	 */
	public IMemoryBlock getMemoryBlock(long startAddress, long length) throws DebugException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IMemoryBlockRetrieval#supportsStorageRetrieval()
	 */
	public boolean supportsStorageRetrieval() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.IBreakpointManagerListener#breakpointManagerEnablementChanged(boolean)
	 */
	public void breakpointManagerEnablementChanged(boolean enabled) {
		IBreakpoint[] breakpoints = getBreakpointManager().getBreakpoints(getModelIdentifier());
		for (int i = 0; i < breakpoints.length; i++) {
			if (enabled) {
				breakpointAdded(breakpoints[i]);
			} else {
				breakpointRemoved(breakpoints[i], null);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.debug.model.AbstractDebugElement#getDebugTarget()
	 */
	@Override
	public IDebugTarget getDebugTarget() {
		return this;
	}

}
