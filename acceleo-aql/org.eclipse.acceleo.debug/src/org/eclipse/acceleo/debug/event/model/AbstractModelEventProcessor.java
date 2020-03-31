/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.model;

import java.util.List;

import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.Thread;
import org.eclipse.acceleo.debug.Variable;
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
import org.eclipse.acceleo.debug.util.DebugTargetUtils;
import org.eclipse.acceleo.debug.util.IDSLCurrentInstructionListener;
import org.eclipse.acceleo.debug.util.IModelUpdater;
import org.eclipse.acceleo.debug.util.ModelUpdater;

/**
 * The {@link DebugTarget} DSL debug model.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractModelEventProcessor implements IDSLDebugEventProcessor {

	/**
	 * The {@link ModelUpdater}.
	 */
	private final IModelUpdater modelUpdater;

	/**
	 * The {@link List} of {@link IDSLCurrentInstructionListener}.
	 */
	private List<IDSLCurrentInstructionListener> currentInstructionListeners;

	/**
	 * Constructor.
	 * 
	 * @param modelUpdater
	 *            the {@link IModelUpdater}
	 */
	public AbstractModelEventProcessor(IModelUpdater modelUpdater) {
		this.modelUpdater = modelUpdater;
	}

	/**
	 * Gets the {@link DebugTarget}.
	 * 
	 * @return the {@link DebugTarget}
	 */
	protected abstract DebugTarget getHost();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)
	 */
	public Object handleEvent(IDSLDebugEvent event) {
		Object res = null;

		if (event instanceof SuspendedReply) {
			handleSuspendReply((SuspendedReply)event);
		} else if (event instanceof TerminatedReply) {
			handleTerminatedReply((TerminatedReply)event);
		} else if (event instanceof SpawnRunningThreadReply) {
			handleSpawnRunningThreadReply((SpawnRunningThreadReply)event);
		} else if (event instanceof ResumingReply) {
			handleResumingReply((ResumingReply)event);
		} else if (event instanceof VariableReply) {
			handleVariableReply((VariableReply)event);
		} else if (event instanceof DeleteVariableReply) {
			handleDeleteVariableReply((DeleteVariableReply)event);
		} else if (event instanceof PushStackFrameReply) {
			handlePushStackFrameReply((PushStackFrameReply)event);
		} else if (event instanceof PopStackFrameReply) {
			handlePopStackFrameReply((PopStackFrameReply)event);
		} else if (event instanceof SetCurrentInstructionReply) {
			handleSetCurrentInstructionReply((SetCurrentInstructionReply)event);
		} else if (event instanceof SetVariableValueReply) {
			handleSetVariableValueReply((SetVariableValueReply)event);
		}

		return res;
	}

	/**
	 * Handles the given {@link SetVariableValueReply}.
	 * 
	 * @param variableValueReply
	 *            the given {@link SetVariableValueReply}
	 */
	private void handleSetVariableValueReply(SetVariableValueReply variableValueReply) {
		final Thread eThread = DebugTargetUtils.getThread(getHost(), variableValueReply.getThreadName());
		final StackFrame eFrame = DebugTargetUtils.getStackFrame(eThread, variableValueReply.getStackName());
		final Variable eVariable = DebugTargetUtils.getVariable(eFrame, variableValueReply.getVariableName());
		// EMF model change
		modelUpdater.setVariableValueReply(eVariable, variableValueReply.getValue());
		// Client change
		notifyClientSetVariableValueReply(variableValueReply);
	}

	/**
	 * Notifies the client of a {@link SetVariableValueReply}.
	 * 
	 * @param variableValueReply
	 *            the {@link SetVariableValueReply}
	 */
	protected abstract void notifyClientSetVariableValueReply(SetVariableValueReply variableValueReply);

	/**
	 * Handles the given {@link SetCurrentInstructionReply}.
	 * 
	 * @param setCurrentInstructionReply
	 *            the {@link SetCurrentInstructionReply}.
	 */
	private void handleSetCurrentInstructionReply(SetCurrentInstructionReply setCurrentInstructionReply) {
		final Thread eThread = DebugTargetUtils.getThread(getHost(), setCurrentInstructionReply
				.getThreadName());
		// EMF model change
		modelUpdater.setCurrentInstructionReply(eThread, setCurrentInstructionReply.getInstruction(),
				setCurrentInstructionReply.isCanStepInto());
		// Client change
		notifyClientSetCurrentInstructionReply(setCurrentInstructionReply);
		// notify current instruction listeners
		fireCurrentInstructionChangedEvent(eThread.getTopStackFrame());
	}

	/**
	 * Notifies the client of a {@link SetCurrentInstructionReply}
	 * 
	 * @param setCurrentInstructionReply
	 *            the {@link SetCurrentInstructionReply}
	 */
	protected abstract void notifyClientSetCurrentInstructionReply(
			SetCurrentInstructionReply setCurrentInstructionReply);

	/**
	 * Handles the given {@link PopStackFrameReply}.
	 * 
	 * @param popStackFrameReply
	 *            the {@link PopStackFrameReply}
	 */
	private void handlePopStackFrameReply(PopStackFrameReply popStackFrameReply) {
		final Thread eThread = DebugTargetUtils.getThread(getHost(), popStackFrameReply.getThreadName());
		// EMF model change
		final StackFrame eFrame = modelUpdater.popStackFrameReply(eThread);
		// Client change
		notifyClientPopStackFrameReply(popStackFrameReply);
		// notify current instruction listeners
		fireCurrentInstructionTerminatedEvent(eFrame);
	}

	/**
	 * Notifies the client of a {@link PopStackFrameReply}.
	 * 
	 * @param popStackFrameReply
	 *            the {@link PopStackFrameReply}
	 */
	protected abstract void notifyClientPopStackFrameReply(PopStackFrameReply popStackFrameReply);

	/**
	 * Handles the given {@link PushStackFrameReply}.
	 * 
	 * @param pushStackFrameReply
	 *            the {@link PushStackFrameReply}
	 */
	private void handlePushStackFrameReply(PushStackFrameReply pushStackFrameReply) {
		final Thread eThread = DebugTargetUtils.getThread(getHost(), pushStackFrameReply.getThreadName());
		// EMF model change
		final StackFrame eFrame = modelUpdater.pushStackFrameReply(eThread, pushStackFrameReply.getName(),
				pushStackFrameReply.getContext(), pushStackFrameReply.getCurrentInstruction(),
				pushStackFrameReply.isCanStepInto());
		// Client change
		notifyClientPushStackFrameReply(pushStackFrameReply);
		// notify current instruction listeners
		fireCurrentInstructionChangedEvent(eFrame);
	}

	/**
	 * Notifies the client of a {@link PushStackFrameReply}
	 * 
	 * @param pushStackFrameReply
	 *            the {@link PushStackFrameReply}
	 */
	protected abstract void notifyClientPushStackFrameReply(PushStackFrameReply pushStackFrameReply);

	/**
	 * Handles the given {@link DeleteVariableReply}.
	 * 
	 * @param deleteVariableReply
	 *            the {@link DeleteVariableReply}
	 */
	private void handleDeleteVariableReply(DeleteVariableReply deleteVariableReply) {
		final Thread eThread = DebugTargetUtils.getThread(getHost(), deleteVariableReply.getThreadName());
		// EMF model change
		modelUpdater.deleteVariableReply(eThread, deleteVariableReply.getName());
		// Client change
		notifyClientDeleteVariableReply(deleteVariableReply);
	}

	/**
	 * Notifies the client of a {@link DeleteVariableReply}
	 * 
	 * @param deleteVariableReply
	 *            the {@link DeleteVariableReply}
	 */
	protected abstract void notifyClientDeleteVariableReply(DeleteVariableReply deleteVariableReply);

	/**
	 * Handles the given {@link VariableReply}.
	 * 
	 * @param variableReply
	 *            the {@link VariableReply}
	 */
	private void handleVariableReply(VariableReply variableReply) {
		final Thread eThread = DebugTargetUtils.getThread(getHost(), variableReply.getThreadName());
		final StackFrame eStackFrame = DebugTargetUtils.getStackFrame(eThread, variableReply.getStackName());
		// EMF model change
		modelUpdater.setVariableReply(eStackFrame, variableReply.getDeclarationTypeName(), variableReply
				.getVariableName(), variableReply.getValue(), variableReply.supportModifications());
		// Client change
		notifyClientVariableReply(variableReply);
	}

	/**
	 * Notifies the client of a {@link VariableReply}
	 * 
	 * @param variableReply
	 *            the {@link VariableReply}
	 */
	protected abstract void notifyClientVariableReply(VariableReply variableReply);

	/**
	 * Handles the given {@link ResumingReply}.
	 * 
	 * @param resumingReply
	 *            the {@link ResumingReply}
	 */
	private void handleResumingReply(ResumingReply resumingReply) {
		final Thread eThread = DebugTargetUtils.getThread(getHost(), resumingReply.getThreadName());
		if (resumingReply instanceof StepIntoResumingReply) {
			// EMF model change
			modelUpdater.stepIntoReply(eThread);
			// Client change
			notifyClientStepIntoResumingReply((StepIntoResumingReply)resumingReply);
		} else if (resumingReply instanceof StepOverResumingReply) {
			// EMF model change
			modelUpdater.stepOverReply(eThread);
			// Client change
			notifyClientStepOverResumingReply((StepOverResumingReply)resumingReply);
		} else if (resumingReply instanceof StepReturnResumingReply) {
			// EMF model change
			modelUpdater.stepReturnReply(eThread);
			// Client change
			notifyClientStepReturnResumingReply((StepReturnResumingReply)resumingReply);
		} else {
			// EMF model change
			modelUpdater.resumedReply(eThread);
			// Client change
			notifyClientResumedReply(resumingReply);
		}
	}

	/**
	 * Notifies the client of a {@link ResumingReply}.
	 * 
	 * @param resumingReply
	 *            the {@link ResumingReply}
	 */
	protected abstract void notifyClientStepIntoResumingReply(StepIntoResumingReply resumingReply);

	/**
	 * Notifies the client of a {@link StepOverResumingReply}.
	 * 
	 * @param resumingReply
	 *            the {@link StepOverResumingReply}
	 */
	protected abstract void notifyClientStepOverResumingReply(StepOverResumingReply resumingReply);

	/**
	 * Notifies the client of a {@link StepReturnResumingReply}.
	 * 
	 * @param resumingReply
	 *            the {@link StepReturnResumingReply}
	 */
	protected abstract void notifyClientStepReturnResumingReply(StepReturnResumingReply resumingReply);

	/**
	 * Notifies the client of a {@link ResumingReply}.
	 * 
	 * @param resumingReply
	 *            the {@link ResumingReply}
	 */
	protected abstract void notifyClientResumedReply(ResumingReply resumingReply);

	/**
	 * Handles the given {@link SpawnRunningThreadReply}.
	 * 
	 * @param spawnThreadReply
	 *            the {@link SpawnRunningThreadReply}
	 */
	private void handleSpawnRunningThreadReply(SpawnRunningThreadReply spawnThreadReply) {
		// EMF model change
		modelUpdater.spawnRunningThreadReply(getHost(), spawnThreadReply.getThreadName(), spawnThreadReply
				.getContext());
		// Client change
		notifyClientSpawnRunningThreadReply(spawnThreadReply);
	}

	/**
	 * Notifies the client of a {@link SpawnRunningThreadReply}.
	 * 
	 * @param spawnThreadReply
	 *            the {@link SpawnRunningThreadReply}
	 */
	protected abstract void notifyClientSpawnRunningThreadReply(SpawnRunningThreadReply spawnThreadReply);

	/**
	 * Handles the given {@link TerminatedReply}.
	 * 
	 * @param terminatedReply
	 *            the {@link TerminatedReply}
	 */
	private void handleTerminatedReply(TerminatedReply terminatedReply) {
		final String threadName = terminatedReply.getThreadName();
		if (threadName == null) {
			// EMF model change
			modelUpdater.terminatedReply(getHost());
			// Eclipse change
			notifyClientTerminatedReply(terminatedReply);
		} else {
			// EMF model change
			Thread eThread = DebugTargetUtils.getThread(getHost(), threadName);
			modelUpdater.terminatedReply(eThread);
			// Client change
			notifyClientTerminatedReply(terminatedReply);
			// notify current instruction listeners
			StackFrame eFrame = eThread.getTopStackFrame();
			while (eFrame != null) {
				fireCurrentInstructionTerminatedEvent(eFrame);
				eFrame = eFrame.getParentFrame();
			}
		}
	}

	/**
	 * Notifies the client of a {@link TerminatedReply}
	 * 
	 * @param terminatedReply
	 *            the {@link TerminatedReply}
	 */
	protected abstract void notifyClientTerminatedReply(TerminatedReply terminatedReply);

	/**
	 * Handles the given {@link SuspendedReply}.
	 * 
	 * @param suspendReply
	 *            the {@link SuspendedReply}
	 */
	private void handleSuspendReply(SuspendedReply suspendReply) {
		final Thread eThread = DebugTargetUtils.getThread(getHost(), suspendReply.getThreadName());

		// EMF model change
		modelUpdater.suspendedReply(eThread);
		// Client change
		if (suspendReply instanceof SteppedReply) {
			notifyClientSteppedReply((SteppedReply)suspendReply);
		} else if (suspendReply instanceof BreakpointReply) {
			notifyClientBreakpointReply((BreakpointReply)suspendReply);
		} else {
			notifyClientSuspendedReply(suspendReply);
		}
	}

	protected abstract void notifyClientSteppedReply(SteppedReply suspendReply);

	protected abstract void notifyClientBreakpointReply(BreakpointReply suspendReply);

	protected abstract void notifyClientSuspendedReply(SuspendedReply suspendReply);

	/**
	 * Adds the given {@link IDSLSuspendListener}.
	 * 
	 * @param listener
	 *            the {@link IDSLSuspendListener} to add
	 */
	public void addCurrentInstructionListener(IDSLCurrentInstructionListener listener) {
		currentInstructionListeners.add(listener);
	}

	/**
	 * Removes the given {@link IDSLSuspendListener}.
	 * 
	 * @param listener
	 *            the {@link IDSLSuspendListener} to remove
	 */
	public void removeCurrentInstructionListener(IDSLCurrentInstructionListener listener) {
		currentInstructionListeners.remove(listener);
	}

	/**
	 * Notifies a change of current instruction for the given {@link Thread}.
	 * 
	 * @param frame
	 *            the {@link StackFrame}
	 */
	protected void fireCurrentInstructionChangedEvent(StackFrame frame) {
		for (IDSLCurrentInstructionListener listener : currentInstructionListeners) {
			listener.currentInstructionChanged(getModelIdentifier(), frame);
		}
	}

	/**
	 * Notifies a change of current instruction for the given {@link StackFrame}.
	 * 
	 * @param frame
	 *            the {@link StackFrame}
	 */
	protected void fireCurrentInstructionTerminatedEvent(StackFrame frame) {
		for (IDSLCurrentInstructionListener listener : currentInstructionListeners) {
			listener.terminated(getModelIdentifier(), frame);
		}
	}

	/**
	 * Gets the model identifier.
	 * 
	 * @return the model identifier
	 */
	protected abstract String getModelIdentifier();

}
