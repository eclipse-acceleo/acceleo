/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.model;

import org.eclipse.acceleo.debug.event.IDSLDebugEvent;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.acceleo.debug.event.debugger.BreakpointReply;
import org.eclipse.acceleo.debug.event.debugger.DeleteVariableReply;
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

/**
 * The DSL debug model.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractModelEventProcessor implements IDSLDebugEventProcessor {

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
			notifyClientSpawnRunningThreadReply((SpawnRunningThreadReply)event);
		} else if (event instanceof ResumingReply) {
			handleResumingReply((ResumingReply)event);
		} else if (event instanceof VariableReply) {
			notifyClientVariableReply((VariableReply)event);
		} else if (event instanceof DeleteVariableReply) {
			notifyClientDeleteVariableReply((DeleteVariableReply)event);
		} else if (event instanceof SetCurrentInstructionReply) {
			notifyClientSetCurrentInstructionReply((SetCurrentInstructionReply)event);
		} else if (event instanceof SetVariableValueReply) {
			notifyClientSetVariableValueReply((SetVariableValueReply)event);
		}

		return res;
	}

	/**
	 * Notifies the client of a {@link SetVariableValueReply}.
	 * 
	 * @param variableValueReply
	 *            the {@link SetVariableValueReply}
	 */
	protected abstract void notifyClientSetVariableValueReply(SetVariableValueReply variableValueReply);

	/**
	 * Notifies the client of a {@link SetCurrentInstructionReply}.
	 * 
	 * @param setCurrentInstructionReply
	 *            the {@link SetCurrentInstructionReply}
	 */
	protected abstract void notifyClientSetCurrentInstructionReply(
			SetCurrentInstructionReply setCurrentInstructionReply);

	/**
	 * Notifies the client of a {@link DeleteVariableReply}.
	 * 
	 * @param deleteVariableReply
	 *            the {@link DeleteVariableReply}
	 */
	protected abstract void notifyClientDeleteVariableReply(DeleteVariableReply deleteVariableReply);

	/**
	 * Notifies the client of a {@link VariableReply}.
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
		if (resumingReply instanceof StepIntoResumingReply) {
			notifyClientStepIntoResumingReply((StepIntoResumingReply)resumingReply);
		} else if (resumingReply instanceof StepOverResumingReply) {
			notifyClientStepOverResumingReply((StepOverResumingReply)resumingReply);
		} else if (resumingReply instanceof StepReturnResumingReply) {
			notifyClientStepReturnResumingReply((StepReturnResumingReply)resumingReply);
		} else {
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
		notifyClientTerminatedReply(terminatedReply);
	}

	/**
	 * Notifies the client of a {@link TerminatedReply}.
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

}
