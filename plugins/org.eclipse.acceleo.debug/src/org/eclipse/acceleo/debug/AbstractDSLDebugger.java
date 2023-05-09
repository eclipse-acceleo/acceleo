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
package org.eclipse.acceleo.debug;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.eclipse.acceleo.debug.event.model.InitializeRequest;
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
import org.eclipse.acceleo.debug.util.StackFrame;
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
	 * Mapping from the thread id to its name.
	 */
	private final Map<Long, String> threads = new LinkedHashMap<Long, String>();

	/**
	 * Instructions marked as breakpoints with their attributes.
	 */
	private final Map<URI, Map<String, Serializable>> breakpoints = new HashMap<URI, Map<String, Serializable>>();

	/**
	 * Mapping from the thread id to is {@link StackFrame}.
	 */
	private final Map<Long, Deque<StackFrame>> stackFrames = new HashMap<Long, Deque<StackFrame>>();

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
		} else if (event instanceof InitializeRequest) {
			initialize(((InitializeRequest)event).isNoDebug(), ((InitializeRequest)event).getArguments());
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#stepped(java.lang.Long)
	 */
	public void stepped(final Long threadID) {
		target.handleEvent(new SteppedReply(threadID));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#suspended(java.lang.Long)
	 */
	public void suspended(Long threadID) {
		target.handleEvent(new SuspendedReply(threadID));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#breaked(java.lang.Long)
	 */
	public void breaked(Long threadID) {
		target.handleEvent(new BreakpointReply(threadID));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#resuming(java.lang.Long)
	 */
	public void resuming(Long threadID) {
		target.handleEvent(new ResumingReply(threadID));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#steppingInto(java.lang.Long)
	 */
	public void steppingInto(Long threadID) {
		target.handleEvent(new StepIntoResumingReply(threadID));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#steppingOver(java.lang.Long)
	 */
	public void steppingOver(Long threadID) {
		target.handleEvent(new StepOverResumingReply(threadID));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#steppingReturn(java.lang.Long)
	 */
	public void steppingReturn(Long threadID) {
		target.handleEvent(new StepReturnResumingReply(threadID));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#terminated()
	 */
	public void terminated() {
		setTerminated(true);
		target.handleEvent(new TerminatedReply());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#spawnRunningThread(java.lang.Long, java.lang.String,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public void spawnRunningThread(Long threadID, String threadName, EObject context) {
		target.handleEvent(new SpawnRunningThreadReply(threadID, threadName, context));
		controllers.put(threadID, createThreadController(threadID));
		threads.put(threadID, threadName);
		stackFrames.put(threadID, new ArrayDeque<StackFrame>());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#getThreads()
	 */
	public Map<Long, String> getThreads() {
		return new LinkedHashMap<Long, String>(threads);
	}

	/**
	 * Creates a {@link ThreadController} for the given thread id.
	 * 
	 * @param threadID
	 *            the thread id
	 * @return if the thread is a new Java {@link Thread} a new instance should be created
	 */
	protected ThreadController createThreadController(Long threadID) {
		return new ThreadController(this, threadID);
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public boolean isTerminated() {
		return terminated;
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
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#clearBreakPoints()
	 */
	public void clearBreakPoints() {
		breakpoints.clear();
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
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#control(java.lang.Long, org.eclipse.emf.ecore.EObject)
	 */
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#resume(java.lang.Long)
	 */
	public void resume(Long threadID) {
		controllers.get(threadID).resume();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#stepInto(java.lang.Long)
	 */
	public void stepInto(Long threadID) {
		controllers.get(threadID).stepInto();
	};

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#stepOver(java.lang.Long)
	 */
	public void stepOver(Long threadID) {
		controllers.get(threadID).stepOver();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#stepReturn(java.lang.Long)
	 */
	public void stepReturn(Long threadID) {
		controllers.get(threadID).stepReturn();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#suspend(java.lang.Long)
	 */
	public void suspend(Long threadID) {
		controllers.get(threadID).suspend();
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
		threads.clear();
		stackFrames.clear();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#terminate(java.lang.Long)
	 */
	public void terminate(Long threadID) {
		if (controllers.containsKey(threadID)) {
			controllers.get(threadID).terminate();
		}
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
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#variable(java.lang.Long, java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.Object, boolean)
	 */
	public void variable(Long threadID, String stackName, String declarationTypeName, String variableName,
			Object value, boolean supportModifications) {
		target.handleEvent(new VariableReply(threadID, stackName, declarationTypeName, variableName, value,
				supportModifications));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#deleteVariable(java.lang.Long, java.lang.String)
	 */
	public void deleteVariable(Long threadID, String name) {
		target.handleEvent(new DeleteVariableReply(threadID, name));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#terminated(java.lang.Long)
	 */
	public void terminated(Long threadID) {
		target.handleEvent(new TerminatedReply(threadID));
		controllers.remove(threadID);
		threads.remove(threadID);
		stackFrames.remove(threadID);
		if (controllers.size() == 0) {
			setTerminated(true);
			terminated();
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#isTerminated(java.lang.Long)
	 */
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

	/**
	 * Tells if no debug is needed.
	 * 
	 * @return <code>true</code> if no debug is needed, <code>false</code> otherwise
	 */
	public boolean isNoDebug() {
		return noDebug;
	}

	/**
	 * Push a new {@link StackFrame} with the given context.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @param context
	 *            the context of the frame
	 */
	protected void pushStackFrame(Long threadID, EObject context) {
		if (isTerminated()) {
			return;
		}
		stackFrames.get(threadID).addLast(new StackFrame(context));
	}

	/**
	 * Peeks the current {@link StackFrame} of the given {@link Thread}.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @return the current {@link StackFrame} of the given {@link Thread}
	 */
	protected StackFrame peekStackFrame(Long threadID) {
		if (isTerminated()) {
			return null;
		}
		return stackFrames.get(threadID).getLast();
	}

	/**
	 * Pops the current {@link StackFrame} of the given {@link Thread}.
	 * 
	 * @param threadID
	 *            the {@link Thread#getThreadID() ID}
	 * @return the current {@link StackFrame} of the given {@link Thread}
	 */
	protected StackFrame popStackFrame(Long threadID) {
		if (isTerminated()) {
			return null;
		}
		return stackFrames.get(threadID).removeLast();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.debug.IDSLDebugger#getStackFrame(java.lang.Long)
	 */
	public Deque<StackFrame> getStackFrame(Long threadID) {
		if (isTerminated()) {
			return new ArrayDeque<StackFrame>();
		}
		return stackFrames.get(threadID);
	}

}
