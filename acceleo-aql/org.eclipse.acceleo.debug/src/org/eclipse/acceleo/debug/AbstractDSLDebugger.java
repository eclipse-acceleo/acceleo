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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.acceleo.debug.event.IDSLDebugEvent;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.debug.event.debugger.BreakpointReply;
import org.eclipse.acceleo.debug.event.debugger.DeleteVariableReply;
import org.eclipse.acceleo.debug.event.debugger.PopStackFrameReply;
import org.eclipse.acceleo.debug.event.debugger.PushStackFrameReply;
import org.eclipse.acceleo.debug.event.debugger.ResumingReply;
import org.eclipse.acceleo.debug.event.debugger.SetCurrentInstructionReply;
import org.eclipse.acceleo.debug.event.debugger.SetVariableValueReply;
import org.eclipse.acceleo.debug.event.debugger.SpawnRunningThreadReply;
import org.eclipse.acceleo.debug.event.debugger.StepIntoResumingReply;
import org.eclipse.acceleo.debug.event.debugger.StepOverResumingReply;
import org.eclipse.acceleo.debug.event.debugger.StepReturnResumingReply;
import org.eclipse.acceleo.debug.event.debugger.SteppedReply;
import org.eclipse.acceleo.debug.event.debugger.SuspendedReply;
import org.eclipse.acceleo.debug.event.debugger.TerminatedReply;
import org.eclipse.acceleo.debug.event.debugger.VariableReply;
import org.eclipse.acceleo.debug.event.model.AbstractBreakpointRequest;
import org.eclipse.acceleo.debug.event.model.AbstractStepRequest;
import org.eclipse.acceleo.debug.event.model.AddBreakpointRequest;
import org.eclipse.acceleo.debug.event.model.ChangeBreakPointRequest;
import org.eclipse.acceleo.debug.event.model.DisconnectRequest;
import org.eclipse.acceleo.debug.event.model.RemoveBreakpointRequest;
import org.eclipse.acceleo.debug.event.model.ResumeRequest;
import org.eclipse.acceleo.debug.event.model.SetVariableValueRequest;
import org.eclipse.acceleo.debug.event.model.StartRequest;
import org.eclipse.acceleo.debug.event.model.StepIntoRequest;
import org.eclipse.acceleo.debug.event.model.StepOverRequest;
import org.eclipse.acceleo.debug.event.model.StepReturnRequest;
import org.eclipse.acceleo.debug.event.model.SuspendRequest;
import org.eclipse.acceleo.debug.event.model.TerminateRequest;
import org.eclipse.acceleo.debug.event.model.ValidateVariableValueRequest;
import org.eclipse.acceleo.debug.util.ThreadController;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Base {@link IDSLDebugger debugger} implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractDSLDebugger implements IDSLDebugger {

	/**
	 * The {@link org.eclipse.acceleo.debug.event.DSLDebugEventDispatcher dispatcher} for asynchronous
	 * communication or the {@link org.eclipse.acceleo.debug.ide.DSLDebugTargetAdapter target} for
	 * synchronous communication.
	 */
	private final IDSLDebugEventProcessor target;

	/**
	 * Thread name to current instruction. For check purpose only.
	 */
	private final Map<String, EObject> currentInstructions = new HashMap<String, EObject>();

	/**
	 * Tells if the debugger is terminated.
	 */
	private boolean terminated;

	/**
	 * Mapping form thread name to the thread controller.
	 */
	private final Map<String, ThreadController> controllers = new ConcurrentHashMap<String, ThreadController>();

	/**
	 * Instructions marked as breakpoints with their attributes.
	 */
	private final Map<URI, Map<String, Serializable>> breakpoints = new HashMap<URI, Map<String, Serializable>>();

	/**
	 * Constructor.
	 * 
	 * @param target
	 *            the {@link org.eclipse.acceleo.debug.event.DSLDebugEventDispatcher dispatcher} for
	 *            asynchronous communication or the
	 *            {@link org.eclipse.acceleo.debug.ide.DSLDebugTargetAdapter target} for synchronous
	 *            communication
	 */
	public AbstractDSLDebugger(IDSLDebugEventProcessor target) {
		this.target = target;
	}

	/**
	 * Gets the mapping of instructions marked as breakpoints to their attributes.
	 * 
	 * @return the mapping of instructions marked as breakpoints to their attributes
	 */
	protected Map<URI, Map<String, Serializable>> getBreakpoints() {
		return breakpoints;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)
	 */
	public Object handleEvent(IDSLDebugEvent event) {
		Object res = null;

		if (event instanceof DisconnectRequest) {
			disconnect();
		} else if (event instanceof AbstractStepRequest) {
			handleStepRequest((AbstractStepRequest)event);
		} else if (event instanceof ResumeRequest) {
			handleResumeRequest((ResumeRequest)event);
		} else if (event instanceof SuspendRequest) {
			handleSuspendRequest((SuspendRequest)event);
		} else if (event instanceof TerminateRequest) {
			handleTerminateRequest((TerminateRequest)event);
		} else if (event instanceof AbstractBreakpointRequest) {
			handleBreakpointRequest((AbstractBreakpointRequest)event);
		} else if (event instanceof ValidateVariableValueRequest) {
			res = handleValidateVariableValueRequest((ValidateVariableValueRequest)event);
		} else if (event instanceof SetVariableValueRequest) {
			handleSetVariableValueRequest((SetVariableValueRequest)event);
		} else if (event instanceof StartRequest) {
			start();
		}

		return res;
	}

	/**
	 * Handles {@link SetVariableValueRequest}.
	 * 
	 * @param event
	 *            the {@link SetVariableValueRequest}
	 */
	private void handleSetVariableValueRequest(SetVariableValueRequest event) {
		final Object value = getVariableValue(event.getThreadName(), event.getStackName(), event
				.getVariableName(), event.getValue());
		setVariableValue(event.getThreadName(), event.getStackName(), event.getVariableName(), value);
		target.handleEvent(new SetVariableValueReply(event.getThreadName(), event.getStackName(), event
				.getVariableName(), value));
	}

	/**
	 * Handles {@link ValidateVariableValueRequest}.
	 * 
	 * @param event
	 *            the {@link ValidateVariableValueRequest}
	 * @return <code>true</code> if the value is valid, <code>false</code> otherwise
	 */
	private Object handleValidateVariableValueRequest(ValidateVariableValueRequest event) {
		return Boolean.valueOf(validateVariableValue(event.getThreadName(), event.getVariableName(), event
				.getValue()));
	}

	/**
	 * Handles {@link AbstractBreakpointRequest}.
	 * 
	 * @param breakpointRequest
	 *            the {@link AbstractBreakpointRequest}
	 */
	private void handleBreakpointRequest(AbstractBreakpointRequest breakpointRequest) {
		if (breakpointRequest instanceof AddBreakpointRequest) {
			addBreakPoint(breakpointRequest.getURI());
		} else if (breakpointRequest instanceof RemoveBreakpointRequest) {
			removeBreakPoint(breakpointRequest.getURI());
		} else if (breakpointRequest instanceof ChangeBreakPointRequest) {
			changeBreakPoint(breakpointRequest.getURI(), ((ChangeBreakPointRequest)breakpointRequest)
					.getAttribute(), ((ChangeBreakPointRequest)breakpointRequest).getValue());
		}
	}

	/**
	 * Handles {@link TerminateRequest}.
	 * 
	 * @param terminateRequest
	 *            the {@link TerminateRequest}
	 */
	private void handleTerminateRequest(TerminateRequest terminateRequest) {
		final String threadName = terminateRequest.getThreadName();
		if (threadName != null) {
			terminate(threadName);
			// target.handleEvent(new TerminatedReply(threadName));
		} else {
			terminate();
			target.handleEvent(new TerminatedReply());
		}
	}

	/**
	 * Handles {@link SuspendRequest}.
	 * 
	 * @param suspendRequest
	 *            the {@link SuspendRequest}
	 */
	private void handleSuspendRequest(SuspendRequest suspendRequest) {
		final String threadName = suspendRequest.getThreadName();
		if (threadName != null) {
			suspend(threadName);
		} else {
			suspend();
		}
	}

	/**
	 * Handles {@link ResumeRequest}.
	 * 
	 * @param resumeRequest
	 *            the {@link ResumeRequest}
	 */
	private void handleResumeRequest(ResumeRequest resumeRequest) {
		final String threadName = resumeRequest.getThreadName();
		if (threadName != null) {
			resume(threadName);
		} else {
			resume();
		}
	}

	/**
	 * Handles {@link AbstractStepRequest}.
	 * 
	 * @param stepRequest
	 *            the {@link AbstractStepRequest}
	 */
	private void handleStepRequest(AbstractStepRequest stepRequest) {
		final String threadName = stepRequest.getThreadName();
		if (stepRequest.getInstrcution() != currentInstructions.get(threadName)) {
			throw new IllegalStateException("instruction desynchronization.");
		}
		if (stepRequest instanceof StepIntoRequest) {
			stepInto(threadName);
		} else if (stepRequest instanceof StepOverRequest) {
			stepOver(threadName);
		} else if (stepRequest instanceof StepReturnRequest) {
			stepReturn(threadName);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#stepped(java.lang.String)
	 */
	public void stepped(final String threadName) {
		target.handleEvent(new SteppedReply(threadName));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#suspended(java.lang.String)
	 */
	public void suspended(String threadName) {
		target.handleEvent(new SuspendedReply(threadName));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#breaked(java.lang.String)
	 */
	public void breaked(String threadName) {
		target.handleEvent(new BreakpointReply(threadName));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#resuming(java.lang.String)
	 */
	public void resuming(String threadName) {
		target.handleEvent(new ResumingReply(threadName));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#steppingInto(java.lang.String)
	 */
	public void steppingInto(String threadName) {
		target.handleEvent(new StepIntoResumingReply(threadName));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#steppingOver(java.lang.String)
	 */
	public void steppingOver(String threadName) {
		target.handleEvent(new StepOverResumingReply(threadName));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#steppingReturn(java.lang.String)
	 */
	public void steppingReturn(String threadName) {
		target.handleEvent(new StepReturnResumingReply(threadName));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#terminated()
	 */
	public void terminated() {
		target.handleEvent(new TerminatedReply());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#spawnRunningThread(java.lang.String,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void spawnRunningThread(String threadName, EObject context) {
		target.handleEvent(new SpawnRunningThreadReply(threadName, context));
		controllers.put(threadName, createThreadHandler(threadName));
	}

	/**
	 * Creates a {@link ThreadController} for the given thread. if the thread is a new Java {@link Thread} a
	 * new instance should be created, if not the {@link ThreadController} for the existing Java
	 * {@link Thread} should be returned.
	 * 
	 * @param threadName
	 *            the thread name
	 * @return if the thread is a new Java {@link Thread} a new instance should be created, if not the
	 *         {@link ThreadController} for the existing Java {@link Thread} should be returned
	 */
	protected ThreadController createThreadHandler(String threadName) {
		return new ThreadController(this, threadName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#setTerminated(boolean)
	 */
	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#isTerminated()
	 */
	public boolean isTerminated() {
		return terminated;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#getNextInstruction(java.lang.String,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.acceleo.debug.IDSLDebugger.Stepping)
	 */
	public EObject getNextInstruction(String threadName, EObject currentInstruction, Stepping stepping) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#shouldBreak(org.eclipse.emf.ecore.EObject)
	 */
	public boolean shouldBreak(EObject instruction) {
		return breakpoints.containsKey(EcoreUtil.getURI(instruction));
	}

	/**
	 * Gets the value of the given breakpoint attribute.
	 * 
	 * @param instruction
	 *            the instruction referenced in the breakpoint
	 * @param attribute
	 *            the attribute
	 * @return the value of the given breakpoint attribute if any, <code>null</code> otherwise
	 */
	protected Serializable getBreakpointAttributes(EObject instruction, String attribute) {
		final Serializable res;

		Map<String, Serializable> attributes = breakpoints.get(EcoreUtil.getURI(instruction));
		if (attributes != null) {
			res = attributes.get(attribute);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#addBreakPoint(org.eclipse.emf.common.util.URI)
	 */
	public void addBreakPoint(URI instruction) {
		breakpoints.put(instruction, new HashMap<String, Serializable>());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#removeBreakPoint(org.eclipse.emf.common.util.URI)
	 */
	public void removeBreakPoint(URI instruction) {
		breakpoints.remove(instruction);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#changeBreakPoint(org.eclipse.emf.common.util.URI,
	 *      java.lang.String, java.io.Serializable)
	 */
	public void changeBreakPoint(URI instruction, String attribute, Serializable value) {
		final Map<String, Serializable> attributes = breakpoints.get(instruction);
		attributes.put(attribute, value);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#control(java.lang.String, org.eclipse.emf.ecore.EObject)
	 */
	public boolean control(String threadName, EObject instruction) {
		final boolean res;
		if (!isTerminated()) {
			res = controllers.get(threadName).control(instruction);
		} else {
			res = false;
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#resume(java.lang.String)
	 */
	public void resume(String threadName) {
		controllers.get(threadName).resume();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#stepInto(java.lang.String)
	 */
	public void stepInto(String threadName) {
		controllers.get(threadName).stepInto();
	};

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#stepOver(java.lang.String)
	 */
	public void stepOver(String threadName) {
		controllers.get(threadName).stepOver();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#stepReturn(java.lang.String)
	 */
	public void stepReturn(String threadName) {
		controllers.get(threadName).stepReturn();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#suspend(java.lang.String)
	 */
	public void suspend(String threadName) {
		controllers.get(threadName).suspend();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#terminate()
	 */
	public void terminate() {
		setTerminated(true);
		for (ThreadController controler : controllers.values()) {
			synchronized(controler) {
				controler.wakeUp();
			}
		}
		controllers.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#terminate(java.lang.String)
	 */
	public void terminate(String threadName) {
		controllers.get(threadName).terminate();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#suspend()
	 */
	public void suspend() {
		for (ThreadController controler : controllers.values()) {
			controler.suspend();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#resume()
	 */
	public void resume() {
		for (ThreadController controler : controllers.values()) {
			controler.resume();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#variable(java.lang.String, java.lang.String,
	 *      java.lang.String, java.lang.Object, boolean)
	 */
	public void variable(String threadName, String stackName, String declarationTypeName, String variableName,
			Object value, boolean supportModifications) {
		target.handleEvent(new VariableReply(threadName, stackName, declarationTypeName, variableName, value,
				supportModifications));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#deleteVariable(java.lang.String, java.lang.String)
	 */
	public void deleteVariable(String threadName, String name) {
		target.handleEvent(new DeleteVariableReply(threadName, name));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#pushStackFrame(java.lang.String, java.lang.String,
	 *      org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	public void pushStackFrame(String threadName, String frameName, EObject context, EObject instruction) {
		currentInstructions.put(threadName, instruction);
		target.handleEvent(new PushStackFrameReply(threadName, frameName, context, instruction, canStepInto(
				threadName, instruction)));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#popStackFrame(java.lang.String)
	 */
	public void popStackFrame(String threadName) {
		target.handleEvent(new PopStackFrameReply(threadName));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#setCurrentInstruction(java.lang.String,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void setCurrentInstruction(String threadName, EObject instruction) {
		currentInstructions.put(threadName, instruction);
		target.handleEvent(new SetCurrentInstructionReply(threadName, instruction, canStepInto(threadName,
				instruction)));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#terminated(java.lang.String)
	 */
	public void terminated(String threadName) {
		target.handleEvent(new TerminatedReply(threadName));
		controllers.remove(threadName);
		if (controllers.size() == 0) {
			setTerminated(true);
			terminated();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#isTerminated(java.lang.String)
	 */
	public boolean isTerminated(String threadName) {
		return !controllers.containsKey(threadName);
	}

}
