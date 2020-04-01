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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.IDSLDebugger;
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
import org.eclipse.acceleo.debug.event.model.AbstractModelEventProcessor;
import org.eclipse.lsp4j.debug.Breakpoint;
import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.ConfigurationDoneArguments;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ContinueResponse;
import org.eclipse.lsp4j.debug.ContinuedEventArguments;
import org.eclipse.lsp4j.debug.DisconnectArguments;
import org.eclipse.lsp4j.debug.FunctionBreakpoint;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.NextArguments;
import org.eclipse.lsp4j.debug.PauseArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetExceptionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetFunctionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetFunctionBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetVariableArguments;
import org.eclipse.lsp4j.debug.SetVariableResponse;
import org.eclipse.lsp4j.debug.SourceBreakpoint;
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

	/**
	 * The {@link IDSLDebugger}.
	 */
	private IDSLDebugger debugger;

	private final DebugTarget host = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();

	/**
	 * Tells if the was {@link #launch(Map) launched}.
	 */
	private boolean launched = false;

	/**
	 * Sets the {@link IDSLDebugger}.
	 * 
	 * @param debugger
	 *            the {@link IDSLDebugger}
	 */
	public void setDebugger(IDSLDebugger debugger) {
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

				client.initialized();
				return res;
			}
		});
	}

	public CompletableFuture<SetBreakpointsResponse> setBreakpoints(final SetBreakpointsArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<SetBreakpointsResponse>() {

			public SetBreakpointsResponse get() {
				final SetBreakpointsResponse res = new SetBreakpointsResponse();

				List<Breakpoint> responseBreakpoints = new ArrayList<Breakpoint>();

				for (SourceBreakpoint requestedBreakpoint : args.getBreakpoints()) {
					// TODO debugger.addBreakPoint(instruction);
					// final Breakpoint responseBreakpoint = new Breakpoint();
					// TODO configure the responseBreakpoint
					// responseBreakpoints.add(responseBreakpoint);
				}

				res.setBreakpoints(responseBreakpoints.toArray(new Breakpoint[responseBreakpoints.size()]));

				return res;
			}
		});
	}

	public CompletableFuture<SetFunctionBreakpointsResponse> setFunctionBreakpoints(
			final SetFunctionBreakpointsArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<SetFunctionBreakpointsResponse>() {

			public SetFunctionBreakpointsResponse get() {
				final SetFunctionBreakpointsResponse res = new SetFunctionBreakpointsResponse();

				List<Breakpoint> responseBreakpoints = new ArrayList<Breakpoint>();

				for (FunctionBreakpoint requestedBreakpoint : args.getBreakpoints()) {
					// TODO debugger.addBreakPoint(instruction);
					// final Breakpoint responseBreakpoint = new Breakpoint();
					// TODO configure the responseBreakpoint
					// responseBreakpoints.add(responseBreakpoint);
				}

				res.setBreakpoints(responseBreakpoints.toArray(new Breakpoint[responseBreakpoints.size()]));

				return res;
			}
		});
	}

	public CompletableFuture<Void> setExceptionBreakpoints(SetExceptionBreakpointsArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				// TODO ?
			}
		});
	}

	public CompletableFuture<Void> configurationDone(ConfigurationDoneArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				// TODO ?
			}
		});
	}

	public CompletableFuture<Void> launch(final Map<String, Object> args) {
		launched = true;
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				final Object noDebug = args.get("noDebug");
				if (noDebug instanceof Boolean && (Boolean)noDebug) {
					debugger.start(true, args);
				} else {
					debugger.start(false, args);
				}
			}
		});
	}

	public CompletableFuture<Void> attach(Map<String, Object> args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				// TODO add debugger.attach() ?
			}
		});
	}

	public CompletableFuture<Void> pause(final PauseArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.suspend(args.getThreadId());
			}
		});
	}

	public CompletableFuture<ContinueResponse> continue_(final ContinueArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<ContinueResponse>() {

			public ContinueResponse get() {
				final ContinueResponse res = new ContinueResponse();

				debugger.resume(args.getThreadId());
				res.setAllThreadsContinued(false);

				return res;
			}
		});
	}

	public CompletableFuture<Void> next(final NextArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.stepOver(args.getThreadId());
			}
		});
	}

	public CompletableFuture<Void> stepIn(final StepInArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.stepInto(args.getThreadId());
			}
		});
	}

	public CompletableFuture<SetVariableResponse> setVariable(final SetVariableArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<SetVariableResponse>() {

			public SetVariableResponse get() {
				final SetVariableResponse res = new SetVariableResponse();

				// TODO debugger.setVariableValue(args.getThreadId(), stackName, variableName,
				// value);
				// res.set...

				return res;
			}
		});
	}

	public CompletableFuture<Void> terminateThreads(final TerminateThreadsArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				for (Long threadId : args.getThreadIds()) {
					debugger.terminate(threadId);
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

	public CompletableFuture<Void> disconnect(final DisconnectArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				if (launched || args.getTerminateDebuggee()) {
					debugger.terminate();
				}
				debugger.disconnect();
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

		arguments.setThreadId(Long.valueOf(resumingReply.getThreadID()));
		arguments.setAllThreadsContinued(false);

		client.continued(arguments);
	}

	@Override
	protected void notifyClientStepOverResumingReply(StepOverResumingReply resumingReply) {
		final ContinuedEventArguments arguments = new ContinuedEventArguments();

		arguments.setThreadId(Long.valueOf(resumingReply.getThreadID()));
		arguments.setAllThreadsContinued(false);

		client.continued(arguments);
	}

	@Override
	protected void notifyClientStepReturnResumingReply(StepReturnResumingReply resumingReply) {
		final ContinuedEventArguments arguments = new ContinuedEventArguments();

		arguments.setThreadId(Long.valueOf(resumingReply.getThreadID()));
		arguments.setAllThreadsContinued(false);

		client.continued(arguments);
	}

	@Override
	protected void notifyClientResumedReply(ResumingReply resumingReply) {
		final ContinuedEventArguments arguments = new ContinuedEventArguments();

		arguments.setThreadId(Long.valueOf(resumingReply.getThreadID()));
		arguments.setAllThreadsContinued(false);

		client.continued(arguments);
	}

	@Override
	protected void notifyClientSpawnRunningThreadReply(SpawnRunningThreadReply spawnThreadReply) {
		final ThreadEventArguments arguments = new ThreadEventArguments();

		arguments.setThreadId(Long.valueOf(spawnThreadReply.getThreadID()));
		arguments.setReason(ThreadEventArgumentsReason.STARTED);

		client.thread(arguments);
	}

	@Override
	protected void notifyClientTerminatedReply(TerminatedReply terminatedReply) {
		if (terminatedReply.getThreadID() != null) {
			final ThreadEventArguments arguments = new ThreadEventArguments();

			arguments.setThreadId(Long.valueOf(terminatedReply.getThreadID()));
			arguments.setReason(ThreadEventArgumentsReason.EXITED);

			client.thread(arguments);
		} else {
			// TODO
		}
	}

	@Override
	protected void notifyClientSteppedReply(SteppedReply suspendReply) {
		final StoppedEventArguments argument = new StoppedEventArguments();

		argument.setThreadId(Long.valueOf(suspendReply.getThreadID()));
		argument.setDescription("Paused after a step.");
		argument.setPreserveFocusHint(true);
		argument.setReason(StoppedEventArgumentsReason.STEP);
		argument.setAllThreadsStopped(false);

		client.stopped(argument);
	}

	@Override
	protected void notifyClientBreakpointReply(BreakpointReply suspendReply) {
		final StoppedEventArguments argument = new StoppedEventArguments();

		argument.setThreadId(Long.valueOf(suspendReply.getThreadID()));
		argument.setDescription("Paused after hitting a breakpoint.");
		argument.setPreserveFocusHint(true);
		argument.setReason(StoppedEventArgumentsReason.BREAKPOINT);
		argument.setAllThreadsStopped(false);

		client.stopped(argument);
	}

	@Override
	protected void notifyClientSuspendedReply(SuspendedReply suspendReply) {
		final StoppedEventArguments argument = new StoppedEventArguments();

		argument.setThreadId(Long.valueOf(suspendReply.getThreadID()));
		argument.setDescription("Paused after a client request.");
		argument.setPreserveFocusHint(true);
		argument.setReason(StoppedEventArgumentsReason.PAUSE);
		argument.setAllThreadsStopped(false);

		client.stopped(argument);
	}

}
