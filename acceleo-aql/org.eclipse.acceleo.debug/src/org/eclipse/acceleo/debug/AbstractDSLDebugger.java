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
import org.eclipse.acceleo.debug.event.debugger.ResumingReply;
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
	 * communication or the {@link org.eclipse.acceleo.debug.ide.DSLDebugTargetAdapter target} for synchronous
	 * communication.
	 */
	private final IDSLDebugEventProcessor target;

	/**
	 * Tells if the debugger is terminated.
	 */
	private boolean terminated;

	/**
	 * Mapping form thread name to the thread controller.
	 */
	private final Map<Long, ThreadController> controllers = new ConcurrentHashMap<Long, ThreadController>();

	/**
	 * Instructions marked as breakpoints with their attributes.
	 */
	private final Map<URI, Map<String, Serializable>> breakpoints = new HashMap<URI, Map<String, Serializable>>();

	/**
	 * Tells if no debug is needed.
	 */
	private boolean noDebug;

	/**
	 * Constructor.
	 * 
	 * @param target
	 *            the {@link org.eclipse.acceleo.debug.event.DSLDebugEventDispatcher dispatcher} for
	 *            asynchronous communication or the {@link org.eclipse.acceleo.debug.ide.DSLDebugTargetAdapter
	 *            target} for synchronous communication
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
			start(((StartRequest)event).isNoDebug(), ((StartRequest)event).getArguments());
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
		final Object value = getVariableValue(event.getThreadID(), event.getStackName(), event
				.getVariableName(), event.getValue());
		setVariableValue(event.getThreadID(), event.getStackName(), event.getVariableName(), value);
		target.handleEvent(new SetVariableValueReply(event.getThreadID(), event.getStackName(), event
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
		return Boolean.valueOf(validateVariableValue(event.getThreadID(), event.getVariableName(), event
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
		final Long threadID = terminateRequest.getThreadID();
		if (threadID != null) {
			terminate(threadID);
			// target.handleEvent(new TerminatedReply(threadID));
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
		final Long threadID = suspendRequest.getThreadID();
		if (threadID != null) {
			suspend(threadID);
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
		final Long threadID = resumeRequest.getThreadID();
		if (threadID != null) {
			resume(threadID);
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
		final Long threadID = stepRequest.getThreadID();
		if (stepRequest instanceof StepIntoRequest) {
			stepInto(threadID);
		} else if (stepRequest instanceof StepOverRequest) {
			stepOver(threadID);
		} else if (stepRequest instanceof StepReturnRequest) {
			stepReturn(threadID);
		}
	}

	public void stepped(final Long threadID) {
		target.handleEvent(new SteppedReply(threadID));
	}

	public void suspended(Long threadID) {
		target.handleEvent(new SuspendedReply(threadID));
	}

	public void breaked(Long threadID) {
		target.handleEvent(new BreakpointReply(threadID));
	}

	public void resuming(Long threadID) {
		target.handleEvent(new ResumingReply(threadID));
	}

	public void steppingInto(Long threadID) {
		target.handleEvent(new StepIntoResumingReply(threadID));
	}

	public void steppingOver(Long threadID) {
		target.handleEvent(new StepOverResumingReply(threadID));
	}

	public void steppingReturn(Long threadID) {
		target.handleEvent(new StepReturnResumingReply(threadID));
	}

	public void terminated() {
		target.handleEvent(new TerminatedReply());
	}

	public void spawnRunningThread(Long threadID, String threadName, EObject context) {
		target.handleEvent(new SpawnRunningThreadReply(threadID, threadName, context));
		controllers.put(threadID, createThreadHandler(threadID));
	}

	/**
	 * Creates a {@link ThreadController} for the given thread id.
	 * 
	 * @param threadID
	 *            the thread id
	 * @return if the thread is a new Java {@link Thread} a new instance should be created
	 */
	protected ThreadController createThreadHandler(Long threadID) {
		return new ThreadController(this, threadID);
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public boolean isTerminated() {
		return terminated;
	}

	public abstract EObject getNextInstruction(Long threadID, EObject currentInstruction, Stepping stepping);

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

	public void addBreakPoint(URI instruction) {
		breakpoints.put(instruction, new HashMap<String, Serializable>());
	}

	public void removeBreakPoint(URI instruction) {
		breakpoints.remove(instruction);
	}

	public void changeBreakPoint(URI instruction, String attribute, Serializable value) {
		final Map<String, Serializable> attributes = breakpoints.get(instruction);
		attributes.put(attribute, value);
	}

	public boolean control(Long threadID, EObject instruction) {
		final boolean res;

		if (noDebug) {
			res = true;
		} else {
			if (!isTerminated()) {
				res = controllers.get(threadID).control(instruction);
			} else {
				res = false;
			}
		}

		return res;
	}

	public void resume(Long threadID) {
		controllers.get(threadID).resume();
	}

	public void stepInto(Long threadID) {
		controllers.get(threadID).stepInto();
	};

	public void stepOver(Long threadID) {
		controllers.get(threadID).stepOver();
	}

	public void stepReturn(Long threadID) {
		controllers.get(threadID).stepReturn();
	}

	public void suspend(Long threadID) {
		controllers.get(threadID).suspend();
	}

	public void terminate() {
		setTerminated(true);
		for (ThreadController controler : controllers.values()) {
			synchronized(controler) {
				controler.wakeUp();
			}
		}
		controllers.clear();
	}

	public void terminate(Long threadID) {
		controllers.get(threadID).terminate();
	}

	public void suspend() {
		for (ThreadController controler : controllers.values()) {
			controler.suspend();
		}
	}

	public void resume() {
		for (ThreadController controler : controllers.values()) {
			controler.resume();
		}
	}

	public void variable(Long threadID, String stackName, String declarationTypeName, String variableName,
			Object value, boolean supportModifications) {
		target.handleEvent(new VariableReply(threadID, stackName, declarationTypeName, variableName, value,
				supportModifications));
	}

	public void deleteVariable(Long threadID, String name) {
		target.handleEvent(new DeleteVariableReply(threadID, name));
	}

	public void terminated(Long threadID) {
		target.handleEvent(new TerminatedReply(threadID));
		controllers.remove(threadID);
		if (controllers.size() == 0) {
			setTerminated(true);
			terminated();
		}
	}

	public boolean isTerminated(Long threadID) {
		return !controllers.containsKey(threadID);
	}

	/**
	 * Sets if no debug is needed.
	 * 
	 * @param noDebug
	 *            <code>true</code> if no debug is needed
	 */
	protected void setNoDebug(boolean noDebug) {
		this.noDebug = noDebug;
	}

}