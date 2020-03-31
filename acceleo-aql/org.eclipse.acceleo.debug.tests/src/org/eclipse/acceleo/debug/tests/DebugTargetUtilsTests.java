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

import org.eclipse.acceleo.debug.DebugPackage;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.DebugTargetState;
import org.eclipse.acceleo.debug.State;
import org.eclipse.acceleo.debug.Thread;
import org.eclipse.acceleo.debug.util.DebugTargetUtils;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests the {@link DebugTargetUtils} class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DebugTargetUtilsTests extends AbstractDebugTests {
	/**
	 * Tests {@link DebugTargetUtils#createDebugTarget(String, org.eclipse.emf.ecore.EObject)} with
	 * <code>null</code> name and context.
	 */
	@Test
	public void createDebugTargetNullNameNullContext() {
		try {
			DebugTargetUtils.createDebugTarget(null, null);
			fail();
		} catch (NullPointerException e) {
			assertEquals("the debug target name can't be null.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#createDebugTarget(String, org.eclipse.emf.ecore.EObject)} with
	 * <code>null</code> name.
	 */
	@Test
	public void createDebugTargetNullNameNotNullContext() {
		try {
			DebugTargetUtils.createDebugTarget(null, DebugPackage.eINSTANCE.getDebugFactory()
					.createVariable());
			fail();
		} catch (NullPointerException e) {
			assertEquals("the debug target name can't be null.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#createDebugTarget(String, org.eclipse.emf.ecore.EObject)} with
	 * <code>null</code> context.
	 */
	@Test
	public void createDebugTargetNotNullNameNullContext() {
		try {
			DebugTargetUtils.createDebugTarget("target", null);
			fail();
		} catch (NullPointerException e) {
			assertEquals("the debug target context can't be null.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#createDebugTarget(String, org.eclipse.emf.ecore.EObject)} with
	 * <code>null</code> context.
	 */
	@Test
	public void createDebugTarget() {
		final String name = "target";
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();

		final DebugTarget target = DebugTargetUtils.createDebugTarget(name, context);

		assertEquals(name, target.getName());
		assertEquals(context, target.getContext());
		assertEquals(DebugTargetState.CONNECTED, target.getState());
		assertEquals(0, target.getThreads().size());
	}

	/**
	 * Tests {@link DebugTargetUtils#resumeRequest(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void resumeRequestDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		try {
			DebugTargetUtils.resumeRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#resumeRequest(DebugTarget)} in {@link DebugTargetState#TERMINATED}.
	 */
	@Test
	public void resumeRequestDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		try {
			DebugTargetUtils.resumeRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#resumeRequest(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void resumeRequestDebugTargetDisconnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		try {
			DebugTargetUtils.resumeRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#resumeRequest(DebugTarget)} in {@link DebugTargetState#CONNECTED}. With
	 * no {@link Thread}.
	 */
	@Test
	public void resumeRequestDebugTargetConnectedNoThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);

		DebugTargetUtils.resumeRequest(target);

		assertEquals(DebugTargetState.CONNECTED, target.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#resumeRequest(DebugTarget)} in {@link DebugTargetState#CONNECTED}. With
	 * {@link Thread}.
	 */
	@Test
	public void resumeRequestDebugTargetConnectedThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		DebugTargetUtils.resumeRequest(target);

		assertEquals(DebugTargetState.CONNECTED, target.getState());
		assertEquals(8, target.getThreads().size());
		for (Thread thread : target.getThreads()) {
			assertEquals(thread.getBottomStackFrame(), thread.getTopStackFrame());
			assertTrue(thread.getState() != State.SUSPENDED);
		}

		Thread suspendedThread = target.getThreads().get(suspendedThreadIndex);
		assertEquals(State.RUNNING, suspendedThread.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#canResume(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canResumeDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		assertEquals(false, DebugTargetUtils.canResume(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canResume(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canResumeDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		assertEquals(false, DebugTargetUtils.canResume(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canResume(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canResumeDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		assertEquals(false, DebugTargetUtils.canResume(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canResume(DebugTarget)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canResumeDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(true, DebugTargetUtils.canResume(target));

		target.getThreads().remove(suspendedThreadIndex);

		assertEquals(false, DebugTargetUtils.canResume(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#suspendRequest(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void suspendRequestDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		try {
			DebugTargetUtils.suspendRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't suspend a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#suspendRequest(DebugTarget)} in {@link DebugTargetState#TERMINATED}.
	 */
	@Test
	public void suspendRequestDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		try {
			DebugTargetUtils.suspendRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't suspend a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#suspendRequest(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void suspendRequestDebugTargetDisconnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		try {
			DebugTargetUtils.suspendRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't suspend a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#suspendRequest(DebugTarget)} in {@link DebugTargetState#CONNECTED}. With
	 * no {@link Thread}.
	 */
	@Test
	public void suspendRequestDebugTargetConnectedNoThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);

		DebugTargetUtils.suspendRequest(target);

		assertEquals(DebugTargetState.CONNECTED, target.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#suspendRequest(DebugTarget)} in {@link DebugTargetState#CONNECTED}. With
	 * {@link Thread}.
	 */
	@Test
	public void suspendRequestDebugTargetConnectedThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		DebugTargetUtils.suspendRequest(target);

		assertEquals(DebugTargetState.CONNECTED, target.getState());
		assertEquals(8, target.getThreads().size());
		for (Thread thread : target.getThreads()) {
			assertEquals(thread.getBottomStackFrame(), thread.getTopStackFrame());
			assertTrue(thread.getState() != State.RUNNING);
			assertTrue(thread.getState() != State.STEPPING_INTO);
			assertTrue(thread.getState() != State.STEPPING_OVER);
			assertTrue(thread.getState() != State.STEPPING_RETURN);
		}

		Thread runnningThread = target.getThreads().get(runningThreadIndex);
		assertEquals(State.SUSPENDING, runnningThread.getState());

		Thread steppingIntoThread = target.getThreads().get(steppingIntoThreadIndex);
		assertEquals(State.SUSPENDING, steppingIntoThread.getState());

		Thread steppingOverThread = target.getThreads().get(steppingOverThreadIndex);
		assertEquals(State.SUSPENDING, steppingOverThread.getState());

		Thread steppingReturnThread = target.getThreads().get(steppingReturnThreadIndex);
		assertEquals(State.SUSPENDING, steppingReturnThread.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#canSuspend(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canSuspendDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		assertEquals(false, DebugTargetUtils.canSuspend(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canSuspend(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canSuspendDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		assertEquals(false, DebugTargetUtils.canSuspend(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canSuspend(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canSuspendDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		assertEquals(false, DebugTargetUtils.canSuspend(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canSuspend(DebugTarget)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canSuspendDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(true, DebugTargetUtils.canSuspend(target));

		target.getThreads().remove(steppingReturnThreadIndex);
		target.getThreads().remove(steppingOverThreadIndex);
		target.getThreads().remove(steppingIntoThreadIndex);
		target.getThreads().remove(runningThreadIndex);

		assertEquals(false, DebugTargetUtils.canSuspend(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#terminateRequest(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void terminateRequestDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		try {
			DebugTargetUtils.terminateRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#terminateRequest(DebugTarget)} in {@link DebugTargetState#TERMINATED}.
	 */
	@Test
	public void terminateRequestDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		try {
			DebugTargetUtils.terminateRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#terminateRequest(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void terminateRequestDebugTargetDisconnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		try {
			DebugTargetUtils.terminateRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#terminateRequest(DebugTarget)} in {@link DebugTargetState#CONNECTED}.
	 * With no {@link Thread}.
	 */
	@Test
	public void terminateRequestDebugTargetConnectedNoThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);

		DebugTargetUtils.terminateRequest(target);

		assertEquals(DebugTargetState.TERMINATING, target.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#terminateRequest(DebugTarget)} in {@link DebugTargetState#CONNECTED}.
	 * With {@link Thread}.
	 */
	@Test
	public void terminateRequestDebugTargetConnectedThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		DebugTargetUtils.terminateRequest(target);

		assertEquals(DebugTargetState.TERMINATING, target.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#canTerminate(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canTerminateDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		assertEquals(false, DebugTargetUtils.canTerminate(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canTerminate(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canTerminateDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		assertEquals(false, DebugTargetUtils.canTerminate(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canTerminate(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canTerminateDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		assertEquals(false, DebugTargetUtils.canTerminate(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canTerminate(DebugTarget)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canTerminateDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(true, DebugTargetUtils.canTerminate(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#terminatedReply(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void terminatedEventDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		DebugTargetUtils.terminatedReply(target);

		assertEquals(DebugTargetState.TERMINATED, target.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#terminatedReply(DebugTarget)} in {@link DebugTargetState#TERMINATED}.
	 */
	@Test
	public void terminatedEventDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		try {
			DebugTargetUtils.terminatedReply(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminale a not connected or a terminated debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#terminatedReply(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void terminatedEventDebugTargetDisconnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		try {
			DebugTargetUtils.terminatedReply(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminale a not connected or a terminated debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#terminatedReply(DebugTarget)} in {@link DebugTargetState#CONNECTED}. With
	 * no {@link Thread}.
	 */
	@Test
	public void terminatedEventDebugTargetConnectedNoThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);

		DebugTargetUtils.terminatedReply(target);
		assertEquals(DebugTargetState.TERMINATED, target.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#terminatedReply(DebugTarget)} in {@link DebugTargetState#CONNECTED}. With
	 * {@link Thread}.
	 */
	@Test
	public void terminatedEventDebugTargetConnectedThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		DebugTargetUtils.terminatedReply(target);
		assertEquals(DebugTargetState.TERMINATED, target.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#disconnectRequest(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void disconnectRequestDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		try {
			DebugTargetUtils.disconnectRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't disconnect a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#disconnectRequest(DebugTarget)} in {@link DebugTargetState#TERMINATED}.
	 */
	@Test
	public void disconnectRequestDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		try {
			DebugTargetUtils.disconnectRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't disconnect a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#disconnectRequest(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void disconnectRequestDebugTargetDisconnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		try {
			DebugTargetUtils.disconnectRequest(target);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't disconnect a not connected debug target.", e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#disconnectRequest(DebugTarget)} in {@link DebugTargetState#CONNECTED}.
	 * With no {@link Thread}.
	 */
	@Test
	public void disconnectRequestDebugTargetConnectedNoThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);

		DebugTargetUtils.disconnectRequest(target);

		assertEquals(DebugTargetState.DISCONNECTED, target.getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#disconnectRequest(DebugTarget)} in {@link DebugTargetState#CONNECTED}.
	 * With {@link Thread}.
	 */
	@Test
	public void disconnectRequestDebugTargetConnectedThreads() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		DebugTargetUtils.disconnectRequest(target);

		assertEquals(DebugTargetState.DISCONNECTED, target.getState());
		assertEquals(8, target.getThreads().size());

		assertEquals(State.SUSPENDED, target.getThreads().get(suspendedThreadIndex).getState());
		assertEquals(State.SUSPENDING, target.getThreads().get(suspendingThreadIndex).getState());
		assertEquals(State.TERMINATED, target.getThreads().get(terminatedThreadIndex).getState());
		assertEquals(State.TERMINATING, target.getThreads().get(terminatingThreadIndex).getState());
		assertEquals(State.RUNNING, target.getThreads().get(runningThreadIndex).getState());
		assertEquals(State.STEPPING_INTO, target.getThreads().get(steppingIntoThreadIndex).getState());
		assertEquals(State.STEPPING_OVER, target.getThreads().get(steppingOverThreadIndex).getState());
		assertEquals(State.STEPPING_RETURN, target.getThreads().get(steppingReturnThreadIndex).getState());
	}

	/**
	 * Tests {@link DebugTargetUtils#canDisconnect(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canDisconnectDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		assertEquals(false, DebugTargetUtils.canDisconnect(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canDisconnect(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canDisconnectDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		assertEquals(false, DebugTargetUtils.canDisconnect(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canDisconnect(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canDisconnectDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		assertEquals(false, DebugTargetUtils.canDisconnect(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#canDisconnect(DebugTarget)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canDisconnectDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);

		assertEquals(true, DebugTargetUtils.canDisconnect(target));
	}

	/**
	 * Tests {@link DebugTargetUtils#spawnRunningThreadEvent(DebugTarget)} in
	 * {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void spawnRunningThreadEventDebugTargetTerminating() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		try {
			DebugTargetUtils.spawnRunningThreadReply(target, "thread", context);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#spawnRunningThreadEvent(DebugTarget)} in
	 * {@link DebugTargetState#TERMINATED}.
	 */
	@Test
	public void spawnRunningThreadEventDebugTargetTerminated() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		try {
			DebugTargetUtils.spawnRunningThreadReply(target, "thread", context);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#spawnRunningThreadEvent(DebugTarget)} in
	 * {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void spawnRunningThreadEventDebugTargetDisconnected() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		try {
			DebugTargetUtils.spawnRunningThreadReply(target, "thread", context);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#spawnRunningThreadEvent(DebugTarget)} in
	 * {@link DebugTargetState#CONNECTED}. With {@link Thread}.
	 */
	@Test
	public void spawnRunningThreadEventDebugTargetConnectedSpawnRunningThread() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);

		DebugTargetUtils.spawnRunningThreadReply(target, "thread", context);

		assertEquals(DebugTargetState.CONNECTED, target.getState());
		assertEquals(1, target.getThreads().size());

		Thread thread = target.getThreads().get(0);
		assertEquals("thread", thread.getName());
		assertEquals(context, thread.getContext());
		assertEquals(State.RUNNING, thread.getState());

		try {
			DebugTargetUtils.spawnRunningThreadReply(target, "thread", context);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#spawnSuspendedThreadEvent(DebugTarget)} in
	 * {@link DebugTargetState#TERMINATING} .
	 */
	@Test
	public void spawnSuspendedThreadEventDebugTargetTerminating() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final EObject instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		try {
			DebugTargetUtils.spawnSuspendedThreadReply(target, "thread", "stack frame", context, instruction,
					true);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#spawnSuspendedThreadEvent(DebugTarget)} in
	 * {@link DebugTargetState#TERMINATED}.
	 */
	@Test
	public void spawnSuspendedThreadEventDebugTargetTerminated() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final EObject instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		try {
			DebugTargetUtils.spawnSuspendedThreadReply(target, "thread", "stack frame", context, instruction,
					true);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#spawnSuspendedThreadEvent(DebugTarget)} in
	 * {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void spawnSuspendedThreadEventDebugTargetDisconnected() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final EObject instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		try {
			DebugTargetUtils.spawnSuspendedThreadReply(target, "thread", "stack frame", context, instruction,
					true);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#spawnSuspendedThreadEvent(DebugTarget)} in
	 * {@link DebugTargetState#CONNECTED}. With {@link Thread}.
	 */
	@Test
	public void spawnSuspendedThreadEventDebugTargetConnectedSpawnRunningThread() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final EObject instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);

		DebugTargetUtils.spawnSuspendedThreadReply(target, "thread", "stack frame", context, instruction,
				true);

		assertEquals(DebugTargetState.CONNECTED, target.getState());
		assertEquals(1, target.getThreads().size());

		Thread thread = target.getThreads().get(0);
		assertEquals("thread", thread.getName());
		assertEquals(context, thread.getContext());
		assertEquals("stack frame", thread.getBottomStackFrame().getName());
		assertEquals("stack frame", thread.getTopStackFrame().getName());
		assertEquals(instruction, thread.getTopStackFrame().getCurrentInstruction());
		assertEquals(State.SUSPENDED, thread.getState());

		try {
			DebugTargetUtils.spawnSuspendedThreadReply(target, "thread", "stack frame", context, instruction,
					true);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't spawn a thread for a not connected debug target or with a name already used by an other thread.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link DebugTargetUtils#canSpawnThread(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canSpawnThreadDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);

		assertEquals(false, DebugTargetUtils.canSpawnThread(target, "thread"));
	}

	/**
	 * Tests {@link DebugTargetUtils#canSpawnThread(DebugTarget)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canSpawnThreadDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);

		assertEquals(false, DebugTargetUtils.canSpawnThread(target, "thread"));
	}

	/**
	 * Tests {@link DebugTargetUtils#canSpawnThread(DebugTarget)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canSpawnThreadDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);

		assertEquals(false, DebugTargetUtils.canSpawnThread(target, "thread"));
	}

	/**
	 * Tests {@link DebugTargetUtils#canSpawnThread(DebugTarget)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canSpawnThreadDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);

		assertEquals(true, DebugTargetUtils.canSpawnThread(target, "thread"));

		final Thread thread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		thread.setName("thread");
		thread.setDebugTarget(target);

		assertEquals(false, DebugTargetUtils.canSpawnThread(target, "thread"));
	}

	/**
	 * Tests {@link DebugTargetUtils#getThread(DebugTarget, String)} with a <code>null</code>
	 * {@link DebugTarget} and a <code>null</code> name.
	 */
	@Test(expected = NullPointerException.class)
	public void getThreadNullTargetNullName() {
		DebugTargetUtils.getThread(null, null);
	}

	/**
	 * Tests {@link DebugTargetUtils#getThread(DebugTarget, String)} with a <code>null</code> name.
	 */
	@Test(expected = NullPointerException.class)
	public void getThreadNotNullTargetNullName() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		DebugTargetUtils.getThread(target, null);
	}

	/**
	 * Tests {@link DebugTargetUtils#getThread(DebugTarget, String)}.
	 */
	@Test
	public void getThread() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(null, DebugTargetUtils.getThread(target, "not existing"));

		final Thread thread = DebugTargetUtils.getThread(target, "terminating thread");
		assertTrue(thread != null);
		assertEquals("terminating thread", thread.getName());
	}

	/**
	 * Tests {@link DebugTargetUtils#isTerminated(DebugTarget)}.
	 */
	@Test
	public void isTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");

		target.setState(DebugTargetState.CONNECTED);
		assertTrue(!DebugTargetUtils.isTerminated(target));

		target.setState(DebugTargetState.TERMINATED);
		assertTrue(DebugTargetUtils.isTerminated(target));
	}

}
