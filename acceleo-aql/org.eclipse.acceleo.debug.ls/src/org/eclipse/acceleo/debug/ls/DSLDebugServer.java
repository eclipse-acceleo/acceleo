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
package org.eclipse.acceleo.debug.ls;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.IDSLDebugger;
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
import org.eclipse.acceleo.debug.event.model.AbstractModelEventProcessor;
import org.eclipse.acceleo.debug.util.IModelUpdater;
import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.ContinuedEventArguments;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.NextArguments;
import org.eclipse.lsp4j.debug.StepInArguments;
import org.eclipse.lsp4j.debug.StoppedEventArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.TerminateArguments;
import org.eclipse.lsp4j.debug.TerminateThreadsArguments;
import org.eclipse.lsp4j.debug.ThreadEventArguments;
import org.eclipse.lsp4j.debug.ThreadEventArgumentsReason;
import org.eclipse.lsp4j.debug.services.IDebugProtocolClient;
import org.eclipse.lsp4j.debug.services.IDebugProtocolServer;

public class DSLDebugServer extends AbstractModelEventProcessor implements IDebugProtocolServer {

	/**
	 * The {@link IDebugProtocolClient}.
	 */
	private IDebugProtocolClient client;

	private final IDSLDebugger debugger;

	private final DebugTarget host = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();

	public DSLDebugServer(IModelUpdater modelUpdater, IDSLDebugger debugger) {
		super(modelUpdater);
		this.debugger = debugger;
	}

	public CompletableFuture<Capabilities> initialize(InitializeRequestArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<Capabilities>() {

			public Capabilities get() {
				final Capabilities res = new Capabilities();

				// TODO
				res.setSupportsStepBack(false);
				res.setSupportsStepInTargetsRequest(false);
				res.setSupportsTerminateRequest(true);
				res.setSupportsTerminateThreadsRequest(true);
				res.setSupportsRestartRequest(false);

				return res;
			}
		});
	}

	public CompletableFuture<Void> next(final NextArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.stepOver(args.getThreadId().toString());
			}
		});
	}

	public CompletableFuture<Void> stepIn(final StepInArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.stepInto(args.getThreadId().toString());
			}
		});
	}

	public CompletableFuture<Void> terminateThreads(final TerminateThreadsArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				for (Long threadId : args.getThreadIds()) {
					debugger.terminate(threadId.toString());
				}
			}
		});
	}

	public CompletableFuture<Void> terminate(final TerminateArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.terminate();
			}
		});
	}

	public void connect(IDebugProtocolClient client) {
		this.client = client;
	}

	@Override
	protected DebugTarget getHost() {
		return host;
	}

	@Override
	protected void notifyClientSetVariableValueReply(SetVariableValueReply variableValueReply) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void notifyClientSetCurrentInstructionReply(
			SetCurrentInstructionReply setCurrentInstructionReply) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void notifyClientPopStackFrameReply(PopStackFrameReply popStackFrameReply) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void notifyClientPushStackFrameReply(PushStackFrameReply pushStackFrameReply) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void notifyClientDeleteVariableReply(DeleteVariableReply deleteVariableReply) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void notifyClientVariableReply(VariableReply variableReply) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void notifyClientStepIntoResumingReply(StepIntoResumingReply resumingReply) {
		final ContinuedEventArguments arguments = new ContinuedEventArguments();

		arguments.setThreadId(Long.valueOf(resumingReply.getThreadName()));
		arguments.setAllThreadsContinued(false);

		client.continued(arguments);
	}

	@Override
	protected void notifyClientStepOverResumingReply(StepOverResumingReply resumingReply) {
		final ContinuedEventArguments arguments = new ContinuedEventArguments();

		arguments.setThreadId(Long.valueOf(resumingReply.getThreadName()));
		arguments.setAllThreadsContinued(false);

		client.continued(arguments);
	}

	@Override
	protected void notifyClientStepReturnResumingReply(StepReturnResumingReply resumingReply) {
		final ContinuedEventArguments arguments = new ContinuedEventArguments();

		arguments.setThreadId(Long.valueOf(resumingReply.getThreadName()));
		arguments.setAllThreadsContinued(false);

		client.continued(arguments);
	}

	@Override
	protected void notifyClientResumedReply(ResumingReply resumingReply) {
		final ContinuedEventArguments arguments = new ContinuedEventArguments();

		arguments.setThreadId(Long.valueOf(resumingReply.getThreadName()));
		arguments.setAllThreadsContinued(false);

		client.continued(arguments);
	}

	@Override
	protected void notifyClientSpawnRunningThreadReply(SpawnRunningThreadReply spawnThreadReply) {
		final ThreadEventArguments arguments = new ThreadEventArguments();

		arguments.setThreadId(Long.valueOf(spawnThreadReply.getThreadName()));
		arguments.setReason(ThreadEventArgumentsReason.STARTED);

		client.thread(arguments);
	}

	@Override
	protected void notifyClientTerminatedReply(TerminatedReply terminatedReply) {
		if (terminatedReply.getThreadName() != null) {
			final ThreadEventArguments arguments = new ThreadEventArguments();

			arguments.setThreadId(Long.valueOf(terminatedReply.getThreadName()));
			arguments.setReason(ThreadEventArgumentsReason.EXITED);

			client.thread(arguments);
		} else {
			// TODO
		}
	}

	@Override
	protected void notifyClientSteppedReply(SteppedReply suspendReply) {
		final StoppedEventArguments argument = new StoppedEventArguments();

		argument.setThreadId(Long.valueOf(suspendReply.getThreadName()));
		argument.setDescription("Paused after a step.");
		argument.setPreserveFocusHint(true);
		argument.setReason(StoppedEventArgumentsReason.STEP);
		argument.setAllThreadsStopped(false);

		client.stopped(argument);
	}

	@Override
	protected void notifyClientBreakpointReply(BreakpointReply suspendReply) {
		final StoppedEventArguments argument = new StoppedEventArguments();

		argument.setThreadId(Long.valueOf(suspendReply.getThreadName()));
		argument.setDescription("Paused after hitting a breakpoint.");
		argument.setPreserveFocusHint(true);
		argument.setReason(StoppedEventArgumentsReason.BREAKPOINT);
		argument.setAllThreadsStopped(false);

		client.stopped(argument);
	}

	@Override
	protected void notifyClientSuspendedReply(SuspendedReply suspendReply) {
		final StoppedEventArguments argument = new StoppedEventArguments();

		argument.setThreadId(Long.valueOf(suspendReply.getThreadName()));
		argument.setDescription("Paused after a client request.");
		argument.setPreserveFocusHint(true);
		argument.setReason(StoppedEventArgumentsReason.PAUSE);
		argument.setAllThreadsStopped(false);

		client.stopped(argument);
	}

	@Override
	protected String getModelIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
}
