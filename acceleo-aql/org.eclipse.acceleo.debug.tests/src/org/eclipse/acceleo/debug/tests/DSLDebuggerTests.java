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
package org.eclipse.acceleo.debug.tests;

import java.util.Collections;

import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.Variable;
import org.eclipse.acceleo.debug.event.IDSLDebugEvent;
import org.eclipse.acceleo.debug.event.debugger.TerminatedReply;
import org.eclipse.acceleo.debug.event.model.DisconnectRequest;
import org.eclipse.acceleo.debug.event.model.ResumeRequest;
import org.eclipse.acceleo.debug.event.model.SetVariableValueRequest;
import org.eclipse.acceleo.debug.event.model.StartRequest;
import org.eclipse.acceleo.debug.event.model.StepIntoRequest;
import org.eclipse.acceleo.debug.event.model.StepOverRequest;
import org.eclipse.acceleo.debug.event.model.StepReturnRequest;
import org.eclipse.acceleo.debug.event.model.SuspendRequest;
import org.eclipse.acceleo.debug.event.model.TerminateRequest;
import org.eclipse.acceleo.debug.event.model.ValidateVariableValueRequest;
import org.eclipse.acceleo.debug.tests.event.TestEventProcessor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link org.eclipse.acceleo.debug.AbstractDSLDebugger AbstractDSLDebugger} class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DSLDebuggerTests {

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventStartRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		debugger.handleEvent(new StartRequest(false, Collections.<String, Object> emptyMap()));

		assertTrue(debugger.hasStartCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventTerminateRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		debugger.handleEvent(new TerminateRequest());

		assertTrue(debugger.hasTerminateCall());
		assertTrue(debugger.isTerminated());
		assertEquals(1, target.getEvents().size());
		IDSLDebugEvent event = target.getEvents().get(0);
		assertTrue(event instanceof TerminatedReply);
		assertEquals(null, ((TerminatedReply)event).getThreadID());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventTerminateThreadRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		debugger.spawnRunningThread(Long.valueOf(1), "thread", DebugPackage.eINSTANCE.getDebugFactory()
				.createVariable());
		debugger.handleEvent(new TerminateRequest(Long.valueOf(1)));

		assertTrue(debugger.hasTerminateThreadCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventSuspendRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		debugger.handleEvent(new SuspendRequest());

		assertTrue(debugger.hasSuspendCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventSuspendThreadRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		debugger.spawnRunningThread(Long.valueOf(1), "thread", DebugPackage.eINSTANCE.getDebugFactory()
				.createVariable());
		debugger.handleEvent(new SuspendRequest(Long.valueOf(1)));

		assertTrue(debugger.hasSuspendThreadCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventResume() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		debugger.handleEvent(new ResumeRequest());

		assertTrue(debugger.hasResumeCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventResumeThreadRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		debugger.spawnRunningThread(Long.valueOf(1), "thread", DebugPackage.eINSTANCE.getDebugFactory()
				.createVariable());
		debugger.handleEvent(new ResumeRequest(Long.valueOf(1)));

		assertTrue(debugger.hasResumeThreadCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventStepIntoRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		final Variable instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		debugger.spawnRunningThread(Long.valueOf(1), "thread", instruction);
		debugger.handleEvent(new StepIntoRequest(Long.valueOf(1), instruction));

		assertTrue(debugger.hasStepIntoCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventStepOverRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		final Variable instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		debugger.spawnRunningThread(Long.valueOf(1), "thread", instruction);
		debugger.handleEvent(new StepOverRequest(Long.valueOf(1), instruction));

		assertTrue(debugger.hasStepOverCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventStepReturnRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		final Variable instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		debugger.spawnRunningThread(Long.valueOf(1), "thread", instruction);
		debugger.handleEvent(new StepReturnRequest(Long.valueOf(1), instruction));

		assertTrue(debugger.hasStepReturnCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventDisconnectRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		final Variable instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		debugger.spawnRunningThread(Long.valueOf(1), "thread", instruction);
		debugger.handleEvent(new DisconnectRequest());

		assertTrue(debugger.hasDisconnectCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventSetVariableValueRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		final Variable instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		debugger.spawnRunningThread(Long.valueOf(1), "thread", instruction);
		debugger.variable(Long.valueOf(1), "frame", "int", "variable", "value", false);
		debugger.handleEvent(new SetVariableValueRequest(Long.valueOf(1), "frame", "variable", "value2"));

		assertTrue(debugger.hasSetVariableValueCall());
	}

	/**
	 * Tests
	 * {@link org.eclipse.acceleo.debug.IDSLDebugger#handleEvent(org.eclipse.acceleo.debug.event.IDSLDebugEvent)}.
	 */
	@Test
	public void handleEventValidateVariableValueRequest() {
		final TestEventProcessor target = new TestEventProcessor();
		final TestDSLDebugger debugger = new TestDSLDebugger(target);

		final Variable instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		debugger.spawnRunningThread(Long.valueOf(1), "thread", instruction);
		debugger.variable(Long.valueOf(1), "frame", "int", "variable", "value", false);
		debugger.handleEvent(new ValidateVariableValueRequest(Long.valueOf(1), "frame", "variable",
				"value2"));

		assertTrue(debugger.hasValidateVariableValueCall());
	}

}
