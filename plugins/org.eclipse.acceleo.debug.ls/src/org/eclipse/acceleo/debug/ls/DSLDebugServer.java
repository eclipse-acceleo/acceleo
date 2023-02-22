/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
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
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

import org.eclipse.acceleo.debug.DSLSource;
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.lsp4j.debug.Breakpoint;
import org.eclipse.lsp4j.debug.Capabilities;
import org.eclipse.lsp4j.debug.CompletionsArguments;
import org.eclipse.lsp4j.debug.CompletionsResponse;
import org.eclipse.lsp4j.debug.ConfigurationDoneArguments;
import org.eclipse.lsp4j.debug.ContinueArguments;
import org.eclipse.lsp4j.debug.ContinueResponse;
import org.eclipse.lsp4j.debug.ContinuedEventArguments;
import org.eclipse.lsp4j.debug.DataBreakpointInfoArguments;
import org.eclipse.lsp4j.debug.DataBreakpointInfoResponse;
import org.eclipse.lsp4j.debug.DisassembleArguments;
import org.eclipse.lsp4j.debug.DisassembleResponse;
import org.eclipse.lsp4j.debug.DisconnectArguments;
import org.eclipse.lsp4j.debug.EvaluateArguments;
import org.eclipse.lsp4j.debug.EvaluateResponse;
import org.eclipse.lsp4j.debug.ExceptionInfoArguments;
import org.eclipse.lsp4j.debug.ExceptionInfoResponse;
import org.eclipse.lsp4j.debug.FunctionBreakpoint;
import org.eclipse.lsp4j.debug.GotoArguments;
import org.eclipse.lsp4j.debug.GotoTargetsArguments;
import org.eclipse.lsp4j.debug.GotoTargetsResponse;
import org.eclipse.lsp4j.debug.InitializeRequestArguments;
import org.eclipse.lsp4j.debug.LoadedSourcesArguments;
import org.eclipse.lsp4j.debug.LoadedSourcesResponse;
import org.eclipse.lsp4j.debug.ModulesArguments;
import org.eclipse.lsp4j.debug.ModulesResponse;
import org.eclipse.lsp4j.debug.NextArguments;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.PauseArguments;
import org.eclipse.lsp4j.debug.ReadMemoryArguments;
import org.eclipse.lsp4j.debug.ReadMemoryResponse;
import org.eclipse.lsp4j.debug.RestartArguments;
import org.eclipse.lsp4j.debug.RestartFrameArguments;
import org.eclipse.lsp4j.debug.ReverseContinueArguments;
import org.eclipse.lsp4j.debug.RunInTerminalRequestArguments;
import org.eclipse.lsp4j.debug.RunInTerminalResponse;
import org.eclipse.lsp4j.debug.Scope;
import org.eclipse.lsp4j.debug.ScopesArguments;
import org.eclipse.lsp4j.debug.ScopesResponse;
import org.eclipse.lsp4j.debug.SetBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetDataBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetDataBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetExceptionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetExceptionBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetExpressionArguments;
import org.eclipse.lsp4j.debug.SetExpressionResponse;
import org.eclipse.lsp4j.debug.SetFunctionBreakpointsArguments;
import org.eclipse.lsp4j.debug.SetFunctionBreakpointsResponse;
import org.eclipse.lsp4j.debug.SetVariableArguments;
import org.eclipse.lsp4j.debug.SetVariableResponse;
import org.eclipse.lsp4j.debug.Source;
import org.eclipse.lsp4j.debug.SourceArguments;
import org.eclipse.lsp4j.debug.SourceBreakpoint;
import org.eclipse.lsp4j.debug.SourceResponse;
import org.eclipse.lsp4j.debug.StackFrame;
import org.eclipse.lsp4j.debug.StackTraceArguments;
import org.eclipse.lsp4j.debug.StackTraceResponse;
import org.eclipse.lsp4j.debug.StepBackArguments;
import org.eclipse.lsp4j.debug.StepInArguments;
import org.eclipse.lsp4j.debug.StepInTargetsArguments;
import org.eclipse.lsp4j.debug.StepInTargetsResponse;
import org.eclipse.lsp4j.debug.StepOutArguments;
import org.eclipse.lsp4j.debug.StoppedEventArguments;
import org.eclipse.lsp4j.debug.StoppedEventArgumentsReason;
import org.eclipse.lsp4j.debug.TerminateArguments;
import org.eclipse.lsp4j.debug.TerminateThreadsArguments;
import org.eclipse.lsp4j.debug.TerminatedEventArguments;
import org.eclipse.lsp4j.debug.Thread;
import org.eclipse.lsp4j.debug.ThreadEventArguments;
import org.eclipse.lsp4j.debug.ThreadEventArgumentsReason;
import org.eclipse.lsp4j.debug.ThreadsResponse;
import org.eclipse.lsp4j.debug.Variable;
import org.eclipse.lsp4j.debug.VariablesArguments;
import org.eclipse.lsp4j.debug.VariablesResponse;
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

	/**
	 * Tells if the was {@link #launch(Map) launched}.
	 */
	private boolean launched;

	/**
	 * The {@link #launch(Map) launch}/{@link #attach(Map)attach} arguments.
	 */
	private Map<String, Object> arguments;

	/**
	 * Mapping from a frame ID to its variables.
	 */
	private final Map<Integer, Map<String, Object>> frameIDToVariables = new HashMap<Integer, Map<String, Object>>();

	/**
	 * The EMF {@link ILabelProvider}.
	 */
	private final ILabelProvider eLabelProvider;

	/**
	 * The {@link CountDownLatch} released after initialization.
	 */
	private final CountDownLatch intialiaztionCountDownLatch = new CountDownLatch(1);

	/**
	 * Constructor.
	 */
	public DSLDebugServer() {
		final ComposedAdapterFactory factory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		eLabelProvider = new AdapterFactoryLabelProvider(factory);
	}

	/**
	 * Sets the {@link IDSLDebugger}.
	 * 
	 * @param debugger
	 *            the {@link IDSLDebugger}
	 */
	public void setDebugger(IDSLDebugger debugger) {
		this.debugger = debugger;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#initialize(org.eclipse.lsp4j.debug.InitializeRequestArguments)
	 */
	public CompletableFuture<Capabilities> initialize(InitializeRequestArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<Capabilities>() {

			public Capabilities get() {
				final Capabilities res = getCapabilities();

				client.initialized();
				return res;
			}

		});
	}

	/**
	 * Gets the {@link Capabilities} of the debugger.
	 * 
	 * @return the {@link Capabilities} of the debugger
	 */
	private Capabilities getCapabilities() {
		final Capabilities res = new Capabilities();

		// TODO
		res.setSupportsCompletionsRequest(true);
		res.setSupportsConditionalBreakpoints(true);
		res.setSupportsConfigurationDoneRequest(true);
		res.setSupportsDataBreakpoints(false);
		res.setSupportsDelayedStackTraceLoading(false);
		res.setSupportsDisassembleRequest(false);
		res.setSupportsEvaluateForHovers(false);
		res.setSupportsExceptionInfoRequest(false);
		res.setSupportsExceptionOptions(false);
		res.setSupportsFunctionBreakpoints(false);
		res.setSupportsGotoTargetsRequest(false);
		res.setSupportsHitConditionalBreakpoints(true);
		res.setSupportsLoadedSourcesRequest(false);
		res.setSupportsLogPoints(false);
		res.setSupportsModulesRequest(false);
		res.setSupportsReadMemoryRequest(false);
		res.setSupportsRestartFrame(false);
		res.setSupportsRestartRequest(false);
		res.setSupportsSetExpression(false);
		res.setSupportsSetVariable(false);
		res.setSupportsStepBack(false);
		res.setSupportsStepInTargetsRequest(false);
		res.setSupportsTerminateRequest(true);
		res.setSupportsTerminateThreadsRequest(true);
		res.setSupportsValueFormattingOptions(false);
		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#setBreakpoints(org.eclipse.lsp4j.debug.SetBreakpointsArguments)
	 */
	public CompletableFuture<SetBreakpointsResponse> setBreakpoints(final SetBreakpointsArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<SetBreakpointsResponse>() {

			public SetBreakpointsResponse get() {
				return getSetBreakpointResponse(args);
			}

		});
	}

	/**
	 * Gets the {@link SetBreakpointsResponse}.
	 * 
	 * @param args
	 *            the {@link SetBreakpointsArguments}
	 * @return the {@link SetBreakpointsResponse}
	 */
	private SetBreakpointsResponse getSetBreakpointResponse(final SetBreakpointsArguments args) {
		final SetBreakpointsResponse res = new SetBreakpointsResponse();
		List<Breakpoint> responseBreakpoints = new ArrayList<Breakpoint>();
		debugger.clearBreakPoints();
		try {
			intialiaztionCountDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (SourceBreakpoint requestedBreakpoint : args.getBreakpoints()) {
			final long column;
			if (requestedBreakpoint.getColumn() != null) {
				column = requestedBreakpoint.getColumn();
			} else {
				column = 0;
			}
			final EObject instruction = debugger.getInstruction(args.getSource().getPath(),
					requestedBreakpoint.getLine(), column);
			if (instruction != null) {
				debugger.addBreakPoint(EcoreUtil.getURI(instruction));
				final Breakpoint responseBreakpoint = new Breakpoint();
				final DSLSource dslSource = debugger.getSource(instruction);
				if (dslSource != null) {
					final Source source = new Source();
					source.setPath(dslSource.getPath());
					responseBreakpoint.setVerified(true);
					responseBreakpoint.setSource(source);
					responseBreakpoint.setLine((int)dslSource.getStartLine());
					responseBreakpoint.setColumn((int)dslSource.getStartColumn());
					responseBreakpoint.setEndLine((int)dslSource.getEndLine());
					responseBreakpoint.setEndColumn((int)dslSource.getEndColumn());
					responseBreakpoints.add(responseBreakpoint);
				}
			}
		}
		res.setBreakpoints(responseBreakpoints.toArray(new Breakpoint[responseBreakpoints.size()]));

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#setFunctionBreakpoints(org.eclipse.lsp4j.debug.SetFunctionBreakpointsArguments)
	 */
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#setExceptionBreakpoints(org.eclipse.lsp4j.debug.SetExceptionBreakpointsArguments)
	 */
	public CompletableFuture<SetExceptionBreakpointsResponse> setExceptionBreakpoints(
			SetExceptionBreakpointsArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<SetExceptionBreakpointsResponse>() {
			public SetExceptionBreakpointsResponse get() {
				// TODO ?
				return null;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#configurationDone(org.eclipse.lsp4j.debug.ConfigurationDoneArguments)
	 */
	public CompletableFuture<Void> configurationDone(ConfigurationDoneArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				if (launched) {
					debugger.start();
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#launch(java.util.Map)
	 */
	public CompletableFuture<Void> launch(final Map<String, Object> args) {
		launched = true;
		arguments = args;
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				final Object noDebug = arguments.get("noDebug");
				if (noDebug instanceof Boolean && (Boolean)noDebug) {
					debugger.initialize(true, args);
					debugger.start();
				} else {
					debugger.initialize(false, args);
					intialiaztionCountDownLatch.countDown();
				}
				// Opens & focus the console
				OutputEventArguments args = new OutputEventArguments();
				args.setOutput(new String());
				client.output(args);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#attach(java.util.Map)
	 */
	public CompletableFuture<Void> attach(final Map<String, Object> args) {
		arguments = args;
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				// TODO add debugger.attach() ?
				// suspend the debugger until configurationDone() ?
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#pause(org.eclipse.lsp4j.debug.PauseArguments)
	 */
	public CompletableFuture<Void> pause(final PauseArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.suspend((long)args.getThreadId());
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#continue_(org.eclipse.lsp4j.debug.ContinueArguments)
	 */
	// CHECKSTYLE:OFF inherited
	public CompletableFuture<ContinueResponse> continue_(final ContinueArguments args) {
		// CHECKSTYLE:ON inherited
		return CompletableFuture.supplyAsync(new Supplier<ContinueResponse>() {

			public ContinueResponse get() {
				final ContinueResponse res = new ContinueResponse();

				debugger.resume((long)args.getThreadId());
				res.setAllThreadsContinued(false);

				return res;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#next(org.eclipse.lsp4j.debug.NextArguments)
	 */
	public CompletableFuture<Void> next(final NextArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.stepOver((long)args.getThreadId());
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#stepIn(org.eclipse.lsp4j.debug.StepInArguments)
	 */
	public CompletableFuture<Void> stepIn(final StepInArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.stepInto((long)args.getThreadId());
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#setVariable(org.eclipse.lsp4j.debug.SetVariableArguments)
	 */
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#terminateThreads(org.eclipse.lsp4j.debug.TerminateThreadsArguments)
	 */
	public CompletableFuture<Void> terminateThreads(final TerminateThreadsArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				for (Integer threadId : args.getThreadIds()) {
					debugger.terminate((long)threadId);
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#terminate(org.eclipse.lsp4j.debug.TerminateArguments)
	 */
	public CompletableFuture<Void> terminate(final TerminateArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.terminate();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#disconnect(org.eclipse.lsp4j.debug.DisconnectArguments)
	 */
	public CompletableFuture<Void> disconnect(final DisconnectArguments args) {
		eLabelProvider.dispose();
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				if (launched || args.getTerminateDebuggee()) {
					debugger.terminate();
				}
				debugger.disconnect();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#completions(org.eclipse.lsp4j.debug.CompletionsArguments)
	 */
	public CompletableFuture<CompletionsResponse> completions(CompletionsArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#dataBreakpointInfo(org.eclipse.lsp4j.debug.DataBreakpointInfoArguments)
	 */
	public CompletableFuture<DataBreakpointInfoResponse> dataBreakpointInfo(
			DataBreakpointInfoArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#disassemble(org.eclipse.lsp4j.debug.DisassembleArguments)
	 */
	public CompletableFuture<DisassembleResponse> disassemble(DisassembleArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#evaluate(org.eclipse.lsp4j.debug.EvaluateArguments)
	 */
	public CompletableFuture<EvaluateResponse> evaluate(EvaluateArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#exceptionInfo(org.eclipse.lsp4j.debug.ExceptionInfoArguments)
	 */
	public CompletableFuture<ExceptionInfoResponse> exceptionInfo(ExceptionInfoArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	// CHECKSTYLE:OFF inherited
	public CompletableFuture<Void> goto_(GotoArguments args) {
		// CHECKSTYLE:ON inherited
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#gotoTargets(org.eclipse.lsp4j.debug.GotoTargetsArguments)
	 */
	public CompletableFuture<GotoTargetsResponse> gotoTargets(GotoTargetsArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#loadedSources(org.eclipse.lsp4j.debug.LoadedSourcesArguments)
	 */
	public CompletableFuture<LoadedSourcesResponse> loadedSources(LoadedSourcesArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#modules(org.eclipse.lsp4j.debug.ModulesArguments)
	 */
	public CompletableFuture<ModulesResponse> modules(ModulesArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#readMemory(org.eclipse.lsp4j.debug.ReadMemoryArguments)
	 */
	public CompletableFuture<ReadMemoryResponse> readMemory(ReadMemoryArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#restart(org.eclipse.lsp4j.debug.RestartArguments)
	 */
	public CompletableFuture<Void> restart(RestartArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#restartFrame(org.eclipse.lsp4j.debug.RestartFrameArguments)
	 */
	public CompletableFuture<Void> restartFrame(RestartFrameArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#reverseContinue(org.eclipse.lsp4j.debug.ReverseContinueArguments)
	 */
	public CompletableFuture<Void> reverseContinue(ReverseContinueArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#runInTerminal(org.eclipse.lsp4j.debug.RunInTerminalRequestArguments)
	 */
	public CompletableFuture<RunInTerminalResponse> runInTerminal(RunInTerminalRequestArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#scopes(org.eclipse.lsp4j.debug.ScopesArguments)
	 */
	public CompletableFuture<ScopesResponse> scopes(final ScopesArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<ScopesResponse>() {

			public ScopesResponse get() {
				return getScopesResponse(args);
			}

		});
	}

	/**
	 * Gets the {@link ScopesResponse}.
	 * 
	 * @param args
	 *            the {@link ScopesArguments}
	 * @return the {@link ScopesResponse}
	 */
	private ScopesResponse getScopesResponse(final ScopesArguments args) {
		System.out.println(args);
		final ScopesResponse res = new ScopesResponse();

		final Scope scope = new Scope();
		scope.setName("Variables");
		scope.setVariablesReference(args.getFrameId() + 1);
		res.setScopes(new Scope[] {scope });

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#setDataBreakpoints(org.eclipse.lsp4j.debug.SetDataBreakpointsArguments)
	 */
	public CompletableFuture<SetDataBreakpointsResponse> setDataBreakpoints(
			SetDataBreakpointsArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#setExpression(org.eclipse.lsp4j.debug.SetExpressionArguments)
	 */
	public CompletableFuture<SetExpressionResponse> setExpression(SetExpressionArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#source(org.eclipse.lsp4j.debug.SourceArguments)
	 */
	public CompletableFuture<SourceResponse> source(SourceArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#stackTrace(org.eclipse.lsp4j.debug.StackTraceArguments)
	 */
	public CompletableFuture<StackTraceResponse> stackTrace(final StackTraceArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<StackTraceResponse>() {

			public StackTraceResponse get() {
				return getStackTraceResponse(args);
			}

		});
	}

	/**
	 * Gets the {@link StackTraceResponse}.
	 * 
	 * @param args
	 *            the {@link StackTraceArguments}
	 * @return the {@link StackTraceResponse}
	 */
	private StackTraceResponse getStackTraceResponse(StackTraceArguments args) {
		final StackTraceResponse res = new StackTraceResponse();

		final List<StackFrame> resFrames = new ArrayList<StackFrame>();
		Deque<org.eclipse.acceleo.debug.util.StackFrame> stackFrames = debugger.getStackFrame((long)args
				.getThreadId());
		final Iterator<org.eclipse.acceleo.debug.util.StackFrame> it = stackFrames.descendingIterator();
		int id = 0;
		while (it.hasNext()) {
			final org.eclipse.acceleo.debug.util.StackFrame currentFrame = it.next();
			final StackFrame resFrame = new StackFrame();
			frameIDToVariables.put(id, currentFrame.getVariables());
			resFrame.setId(id);
			resFrame.setName(eLabelProvider.getText(currentFrame.getContext()));
			// TODO ? resFrame.setPresentationHint(presentationHint);
			// resFrame.setInstructionPointerReference(instructionPointerReference);
			// TODO ? resFrame.setModuleId(moduleId);
			final DSLSource dslSource = debugger.getSource(currentFrame.getInstruction());
			if (dslSource != null) {
				resFrame.setLine((int)dslSource.getStartLine());
				resFrame.setColumn((int)dslSource.getStartColumn());
				resFrame.setEndLine((int)dslSource.getEndLine());
				resFrame.setEndColumn((int)dslSource.getEndColumn());
				final Source source = new Source();
				source.setName(dslSource.getPath());
				source.setPath(dslSource.getPath());
				resFrame.setSource(source);
				resFrames.add(resFrame);
				id++; // TODO id
			}
		}
		res.setTotalFrames(resFrames.size());
		res.setStackFrames(resFrames.toArray(new StackFrame[resFrames.size()]));

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#threads()
	 */
	public CompletableFuture<ThreadsResponse> threads() {
		return CompletableFuture.supplyAsync(new Supplier<ThreadsResponse>() {

			public ThreadsResponse get() {
				final ThreadsResponse res = new ThreadsResponse();

				final List<Thread> threads = new ArrayList<Thread>();
				for (Entry<Long, String> entry : debugger.getThreads().entrySet()) {
					final Thread thread = new Thread();
					thread.setId(entry.getKey().intValue());
					thread.setName(entry.getValue());
					threads.add(thread);
				}
				res.setThreads(threads.toArray(new Thread[threads.size()]));

				return res;
			}

		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#stepBack(org.eclipse.lsp4j.debug.StepBackArguments)
	 */
	public CompletableFuture<Void> stepBack(StepBackArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#stepInTargets(org.eclipse.lsp4j.debug.StepInTargetsArguments)
	 */
	public CompletableFuture<StepInTargetsResponse> stepInTargets(StepInTargetsArguments args) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#stepOut(org.eclipse.lsp4j.debug.StepOutArguments)
	 */
	public CompletableFuture<Void> stepOut(final StepOutArguments args) {
		return CompletableFuture.runAsync(new Runnable() {

			public void run() {
				debugger.stepReturn((long)args.getThreadId());
			}
		});
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.lsp4j.debug.services.IDebugProtocolServer#variables(org.eclipse.lsp4j.debug.VariablesArguments)
	 */
	public CompletableFuture<VariablesResponse> variables(final VariablesArguments args) {
		return CompletableFuture.supplyAsync(new Supplier<VariablesResponse>() {

			public VariablesResponse get() {
				return getVariablesResponse(args);
			}

		});
	}

	private VariablesResponse getVariablesResponse(VariablesArguments args) {
		final VariablesResponse res = new VariablesResponse();

		List<Variable> variables = new ArrayList<Variable>();
		final Map<String, Object> vars = frameIDToVariables.get(args.getVariablesReference() - 1);
		for (Entry<String, Object> entry : vars.entrySet()) {
			final Variable variable = new Variable();
			variable.setName(entry.getKey());
			variable.setEvaluateName(entry.getKey());
			variable.setType(entry.getValue().getClass().getSimpleName());
			variable.setValue(entry.getValue().toString());
			variables.add(variable);
		}

		res.setVariables(variables.toArray(new Variable[variables.size()]));

		return res;
	}

	/**
	 * Connect the given {@link IDebugProtocolClient}.
	 * 
	 * @param debugProtocolClient
	 *            the {@link IDebugProtocolClient}
	 */
	public void connect(IDebugProtocolClient debugProtocolClient) {
		this.client = debugProtocolClient;
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
		final ContinuedEventArguments eventArguments = new ContinuedEventArguments();

		eventArguments.setThreadId(resumingReply.getThreadID().intValue());
		eventArguments.setAllThreadsContinued(false);

		// client.continued(eventArguments);
	}

	@Override
	protected void notifyClientStepOverResumingReply(StepOverResumingReply resumingReply) {
		final ContinuedEventArguments eventArguments = new ContinuedEventArguments();

		eventArguments.setThreadId(resumingReply.getThreadID().intValue());
		eventArguments.setAllThreadsContinued(false);

		// client.continued(eventArguments);
	}

	@Override
	protected void notifyClientStepReturnResumingReply(StepReturnResumingReply resumingReply) {
		final ContinuedEventArguments eventArguments = new ContinuedEventArguments();

		eventArguments.setThreadId(resumingReply.getThreadID().intValue());
		eventArguments.setAllThreadsContinued(false);

		// client.continued(eventArguments);
	}

	@Override
	protected void notifyClientResumedReply(ResumingReply resumingReply) {
		final ContinuedEventArguments eventArguments = new ContinuedEventArguments();

		eventArguments.setThreadId(resumingReply.getThreadID().intValue());
		eventArguments.setAllThreadsContinued(false);

		// client.continued(eventArguments);
	}

	@Override
	protected void notifyClientSpawnRunningThreadReply(SpawnRunningThreadReply spawnThreadReply) {
		final ThreadEventArguments eventArguments = new ThreadEventArguments();

		eventArguments.setThreadId(spawnThreadReply.getThreadID().intValue());
		eventArguments.setReason(ThreadEventArgumentsReason.STARTED);

		client.thread(eventArguments);
	}

	@Override
	protected void notifyClientTerminatedReply(TerminatedReply terminatedReply) {
		if (terminatedReply.getThreadID() != null) {
			final ThreadEventArguments eventArguments = new ThreadEventArguments();

			eventArguments.setThreadId(terminatedReply.getThreadID().intValue());
			eventArguments.setReason(ThreadEventArgumentsReason.EXITED);

			client.thread(eventArguments);
		} else {
			final TerminatedEventArguments eventArguments = new TerminatedEventArguments();

			eventArguments.setRestart(false);

			client.terminated(eventArguments);
		}
	}

	@Override
	protected void notifyClientSteppedReply(SteppedReply suspendReply) {
		final StoppedEventArguments argument = new StoppedEventArguments();

		argument.setThreadId(suspendReply.getThreadID().intValue());
		argument.setDescription("Paused after a step.");
		argument.setPreserveFocusHint(true);
		argument.setReason(StoppedEventArgumentsReason.STEP);
		argument.setAllThreadsStopped(false);

		client.stopped(argument);
	}

	@Override
	protected void notifyClientBreakpointReply(BreakpointReply suspendReply) {
		final StoppedEventArguments argument = new StoppedEventArguments();

		argument.setThreadId(suspendReply.getThreadID().intValue());
		argument.setDescription("Paused after hitting a breakpoint.");
		argument.setPreserveFocusHint(true);
		argument.setReason(StoppedEventArgumentsReason.BREAKPOINT);
		argument.setAllThreadsStopped(false);

		client.stopped(argument);
	}

	@Override
	protected void notifyClientSuspendedReply(SuspendedReply suspendReply) {
		final StoppedEventArguments argument = new StoppedEventArguments();

		argument.setThreadId(suspendReply.getThreadID().intValue());
		argument.setDescription("Paused after a client request.");
		argument.setPreserveFocusHint(true);
		argument.setReason(StoppedEventArgumentsReason.PAUSE);
		argument.setAllThreadsStopped(false);

		client.stopped(argument);
	}

}
