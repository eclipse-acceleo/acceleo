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
import org.eclipse.acceleo.debug.StackFrame;
import org.eclipse.acceleo.debug.State;
import org.eclipse.acceleo.debug.Thread;
import org.eclipse.acceleo.debug.Variable;
import org.eclipse.acceleo.debug.util.ThreadUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests the {@link ThreadUtils} class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ThreadUtilsTests extends AbstractDebugTests {

	/**
	 * Tests {@link ThreadUtils#canResume(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canResumeDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canResume(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canResumeDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canResume(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canResumeDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canResume(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canResumeDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(true, ThreadUtils.canResume(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canResume(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#resumedReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void resumeReplyDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		try {
			ThreadUtils.resumedReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#resumedReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void resumeReplyDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.resumedReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#resumedReply(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void resumeReplyDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.resumedReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#resumedReply(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void resumeReplyDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		try {
			ThreadUtils.resumedReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}
		try {
			ThreadUtils.resumedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't resume a not suspended thread or a thread in a not connected debug target.", e
					.getMessage());
		}

		ThreadUtils.resumedReply(target.getThreads().get(suspendedThreadIndex));

		assertEquals(State.RUNNING, target.getThreads().get(suspendedThreadIndex).getState());
	}

	/**
	 * Tests {@link ThreadUtils#canSuspend(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canSuspendDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canSuspend(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canSuspendDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canSuspend(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canSuspendDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canSuspend(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canSuspendDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(true, ThreadUtils.canSuspend(target.getThreads().get(runningThreadIndex)));
		assertEquals(true, ThreadUtils.canSuspend(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(true, ThreadUtils.canSuspend(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(true, ThreadUtils.canSuspend(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canSuspend(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#suspendRequest(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void suspendRequestDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		try {
			ThreadUtils.suspendRequest(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#suspendRequest(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void suspendRequestDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.suspendRequest(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#suspendRequest(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void suspendRequestDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.suspendRequest(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#suspendRequest(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void suspendRequestDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		try {
			ThreadUtils.suspendRequest(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendRequest(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't suspend a not running or stepping thread or a thread in a not connected debug target.",
					e.getMessage());
		}

		ThreadUtils.suspendRequest(target.getThreads().get(runningThreadIndex));
		assertEquals(State.SUSPENDING, target.getThreads().get(runningThreadIndex).getState());

		ThreadUtils.suspendRequest(target.getThreads().get(steppingIntoThreadIndex));
		assertEquals(State.SUSPENDING, target.getThreads().get(steppingIntoThreadIndex).getState());

		ThreadUtils.suspendRequest(target.getThreads().get(steppingOverThreadIndex));
		assertEquals(State.SUSPENDING, target.getThreads().get(steppingOverThreadIndex).getState());

		ThreadUtils.suspendRequest(target.getThreads().get(steppingReturnThreadIndex));
		assertEquals(State.SUSPENDING, target.getThreads().get(steppingReturnThreadIndex).getState());
	}

	/**
	 * Tests {@link ThreadUtils#suspendedEvent(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void suspendedReplyDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		try {
			ThreadUtils.suspendedReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#suspendedEvent(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void suspendedReplyDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.suspendedReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#suspendedEvent(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void suspendedReplyDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.suspendedReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#suspendedEvent(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void suspendedReplyDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		try {
			ThreadUtils.suspendedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		ThreadUtils.suspendedReply(target.getThreads().get(suspendingThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(suspendingThreadIndex).getState());

		ThreadUtils.suspendedReply(target.getThreads().get(runningThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(runningThreadIndex).getState());

		ThreadUtils.suspendedReply(target.getThreads().get(steppingIntoThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(steppingIntoThreadIndex).getState());

		ThreadUtils.suspendedReply(target.getThreads().get(steppingOverThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(steppingOverThreadIndex).getState());

		ThreadUtils.suspendedReply(target.getThreads().get(steppingReturnThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(steppingReturnThreadIndex).getState());
	}

	/**
	 * Tests {@link ThreadUtils#suspendedEvent(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void suspendedReplyDebugTargetConnnectedWithVariableNotChanged() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		final Variable variable = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		variable.setName("variable");
		variable.setValueChanged(false);

		for (Thread thread : target.getThreads()) {
			thread.getTopStackFrame().getVariables().add(EcoreUtil.copy(variable));
		}

		try {
			ThreadUtils.suspendedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		ThreadUtils.suspendedReply(target.getThreads().get(suspendingThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(suspendingThreadIndex).getState());
		assertEquals(1, target.getThreads().get(suspendingThreadIndex).getTopStackFrame().getVariables()
				.size());
		assertEquals(false, target.getThreads().get(suspendingThreadIndex).getTopStackFrame().getVariables()
				.get(0).isValueChanged());

		ThreadUtils.suspendedReply(target.getThreads().get(runningThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(runningThreadIndex).getState());
		assertEquals(1, target.getThreads().get(runningThreadIndex).getTopStackFrame().getVariables().size());
		assertEquals(false, target.getThreads().get(runningThreadIndex).getTopStackFrame().getVariables().get(
				0).isValueChanged());

		ThreadUtils.suspendedReply(target.getThreads().get(steppingIntoThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(steppingIntoThreadIndex).getState());
		assertEquals(1, target.getThreads().get(steppingIntoThreadIndex).getTopStackFrame().getVariables()
				.size());
		assertEquals(false, target.getThreads().get(steppingIntoThreadIndex).getTopStackFrame().getVariables()
				.get(0).isValueChanged());

		ThreadUtils.suspendedReply(target.getThreads().get(steppingOverThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(steppingOverThreadIndex).getState());
		assertEquals(1, target.getThreads().get(steppingOverThreadIndex).getTopStackFrame().getVariables()
				.size());
		assertEquals(false, target.getThreads().get(steppingOverThreadIndex).getTopStackFrame().getVariables()
				.get(0).isValueChanged());

		ThreadUtils.suspendedReply(target.getThreads().get(steppingReturnThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(steppingReturnThreadIndex).getState());
		assertEquals(1, target.getThreads().get(steppingReturnThreadIndex).getTopStackFrame().getVariables()
				.size());
		assertEquals(false, target.getThreads().get(steppingReturnThreadIndex).getTopStackFrame()
				.getVariables().get(0).isValueChanged());
	}

	/**
	 * Tests {@link ThreadUtils#suspendedEvent(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void suspendedReplyDebugTargetConnnectedWithVariableChanged() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		final Variable variable = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		variable.setName("variable");
		variable.setValueChanged(true);

		for (Thread thread : target.getThreads()) {
			thread.getTopStackFrame().getVariables().add(EcoreUtil.copy(variable));
		}

		try {
			ThreadUtils.suspendedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		try {
			ThreadUtils.suspendedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a suspend reply must happend when the thread is suspending, running, or stepping and the debug target is connected.",
					e.getMessage());
		}
		ThreadUtils.suspendedReply(target.getThreads().get(suspendingThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(suspendingThreadIndex).getState());
		assertEquals(1, target.getThreads().get(suspendingThreadIndex).getTopStackFrame().getVariables()
				.size());
		assertEquals(false, target.getThreads().get(suspendingThreadIndex).getTopStackFrame().getVariables()
				.get(0).isValueChanged());

		ThreadUtils.suspendedReply(target.getThreads().get(runningThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(runningThreadIndex).getState());
		assertEquals(1, target.getThreads().get(runningThreadIndex).getTopStackFrame().getVariables().size());
		assertEquals(false, target.getThreads().get(runningThreadIndex).getTopStackFrame().getVariables().get(
				0).isValueChanged());

		ThreadUtils.suspendedReply(target.getThreads().get(steppingIntoThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(steppingIntoThreadIndex).getState());
		assertEquals(1, target.getThreads().get(steppingIntoThreadIndex).getTopStackFrame().getVariables()
				.size());
		assertEquals(false, target.getThreads().get(steppingIntoThreadIndex).getTopStackFrame().getVariables()
				.get(0).isValueChanged());

		ThreadUtils.suspendedReply(target.getThreads().get(steppingOverThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(steppingOverThreadIndex).getState());
		assertEquals(1, target.getThreads().get(steppingOverThreadIndex).getTopStackFrame().getVariables()
				.size());
		assertEquals(false, target.getThreads().get(steppingOverThreadIndex).getTopStackFrame().getVariables()
				.get(0).isValueChanged());

		ThreadUtils.suspendedReply(target.getThreads().get(steppingReturnThreadIndex));
		assertEquals(State.SUSPENDED, target.getThreads().get(steppingReturnThreadIndex).getState());
		assertEquals(1, target.getThreads().get(steppingReturnThreadIndex).getTopStackFrame().getVariables()
				.size());
		assertEquals(false, target.getThreads().get(steppingReturnThreadIndex).getTopStackFrame()
				.getVariables().get(0).isValueChanged());
	}

	/**
	 * Tests {@link ThreadUtils#terminateRequest(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void terminateRequestDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		try {
			ThreadUtils.terminateRequest(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#terminateRequest(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void terminateRequestDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.terminateRequest(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#terminateRequest(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void terminateRequestDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.terminateRequest(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
		try {
			ThreadUtils.terminateRequest(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't terminate a thread if this debug target is not connected.", e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#terminateRequest(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void terminateRequestDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		ThreadUtils.terminateRequest(target.getThreads().get(suspendedThreadIndex));
		assertEquals(State.TERMINATING, target.getThreads().get(suspendedThreadIndex).getState());

		ThreadUtils.terminateRequest(target.getThreads().get(suspendingThreadIndex));
		assertEquals(State.TERMINATING, target.getThreads().get(suspendingThreadIndex).getState());

		ThreadUtils.terminateRequest(target.getThreads().get(terminatedThreadIndex));
		assertEquals(State.TERMINATING, target.getThreads().get(terminatedThreadIndex).getState());

		ThreadUtils.terminateRequest(target.getThreads().get(terminatingThreadIndex));
		assertEquals(State.TERMINATING, target.getThreads().get(terminatingThreadIndex).getState());

		ThreadUtils.terminateRequest(target.getThreads().get(runningThreadIndex));
		assertEquals(State.TERMINATING, target.getThreads().get(runningThreadIndex).getState());

		ThreadUtils.terminateRequest(target.getThreads().get(steppingIntoThreadIndex));
		assertEquals(State.TERMINATING, target.getThreads().get(steppingIntoThreadIndex).getState());

		ThreadUtils.terminateRequest(target.getThreads().get(steppingOverThreadIndex));
		assertEquals(State.TERMINATING, target.getThreads().get(steppingOverThreadIndex).getState());

		ThreadUtils.terminateRequest(target.getThreads().get(steppingReturnThreadIndex));
		assertEquals(State.TERMINATING, target.getThreads().get(steppingReturnThreadIndex).getState());
	}

	/**
	 * Tests {@link ThreadUtils#terminatedReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void terminatedReplyDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		ThreadUtils.terminatedReply(target.getThreads().get(runningThreadIndex));
		assertEquals(State.TERMINATED, target.getThreads().get(runningThreadIndex).getState());

		ThreadUtils.terminatedReply(target.getThreads().get(steppingIntoThreadIndex));
		assertEquals(State.TERMINATED, target.getThreads().get(steppingIntoThreadIndex).getState());

		ThreadUtils.terminatedReply(target.getThreads().get(steppingOverThreadIndex));
		assertEquals(State.TERMINATED, target.getThreads().get(steppingOverThreadIndex).getState());

		ThreadUtils.terminatedReply(target.getThreads().get(steppingReturnThreadIndex));
		assertEquals(State.TERMINATED, target.getThreads().get(steppingReturnThreadIndex).getState());

		ThreadUtils.terminatedReply(target.getThreads().get(suspendedThreadIndex));
		assertEquals(State.TERMINATED, target.getThreads().get(suspendedThreadIndex).getState());

		ThreadUtils.terminatedReply(target.getThreads().get(suspendingThreadIndex));
		assertEquals(State.TERMINATED, target.getThreads().get(suspendingThreadIndex).getState());

		try {
			ThreadUtils.terminatedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}

		ThreadUtils.terminatedReply(target.getThreads().get(terminatingThreadIndex));
		assertEquals(State.TERMINATED, target.getThreads().get(terminatingThreadIndex).getState());
	}

	/**
	 * Tests {@link ThreadUtils#terminatedReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void terminatedReplyDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.terminatedReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#terminatedReply(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void terminatedReplyDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.terminatedReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#terminatedReply(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void terminatedReplyDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		try {
			ThreadUtils.terminatedReply(target.getThreads().get(runningThreadIndex));
			assertEquals(State.TERMINATED, target.getThreads().get(runningThreadIndex).getState());
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(steppingIntoThreadIndex));
			assertEquals(State.TERMINATED, target.getThreads().get(steppingIntoThreadIndex).getState());
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(steppingOverThreadIndex));
			assertEquals(State.TERMINATED, target.getThreads().get(steppingOverThreadIndex).getState());
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(steppingReturnThreadIndex));
			assertEquals(State.TERMINATED, target.getThreads().get(steppingReturnThreadIndex).getState());
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(suspendedThreadIndex));
			assertEquals(State.TERMINATED, target.getThreads().get(suspendedThreadIndex).getState());
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(suspendingThreadIndex));
			assertEquals(State.TERMINATED, target.getThreads().get(suspendingThreadIndex).getState());
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		try {
			ThreadUtils.terminatedReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"a terminate reply can't happend when the debug target is not connected, terminating or if the thread is already terminated.",
					e.getMessage());
		}
		ThreadUtils.terminatedReply(target.getThreads().get(terminatingThreadIndex));
		assertEquals(State.TERMINATED, target.getThreads().get(terminatingThreadIndex).getState());
	}

	/**
	 * Tests {@link ThreadUtils#canTerminate(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canTerminateDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canTerminate(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canTerminateDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canTerminate(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canTerminateDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canTerminate(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canTerminate(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canTerminateDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(true, ThreadUtils.canTerminate(target.getThreads().get(runningThreadIndex)));
		assertEquals(true, ThreadUtils.canTerminate(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(true, ThreadUtils.canTerminate(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(true, ThreadUtils.canTerminate(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(true, ThreadUtils.canTerminate(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(true, ThreadUtils.canTerminate(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(true, ThreadUtils.canTerminate(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(true, ThreadUtils.canTerminate(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#stepOverReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void stepOverReplyDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		try {
			ThreadUtils.stepOverReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#stepOverReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void stepOverReplyDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.stepOverReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#stepOverReply(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void stepOverReplyDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.stepOverReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#stepOverReply(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void stepOverReplyDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		ThreadUtils.stepOverReply(target.getThreads().get(suspendedThreadIndex));
		assertEquals(State.STEPPING_OVER, target.getThreads().get(suspendedThreadIndex).getState());

		try {
			ThreadUtils.stepOverReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepOverReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step over when the thread is not in suspended mode or the debug target is disconnected.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#canStepOver(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canStepOverDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canStepOver(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canStepOverDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canStepOver(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canStepOverDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canStepOver(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canStepOverDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(true, ThreadUtils.canStepOver(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepOver(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#stepIntoReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void stepIntoReplyDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#stepIntoReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void stepIntoReplyDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#stepIntoReply(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void stepIntoReplyDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#stepIntoReply(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void stepIntoReplyDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}

		// Add an instruction and tell that we can step into it.
		target.getThreads().get(suspendedThreadIndex).getTopStackFrame().setCurrentInstruction(
				DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.getThreads().get(suspendedThreadIndex).getTopStackFrame().setCanStepIntoCurrentInstruction(
				true);
		ThreadUtils.stepIntoReply(target.getThreads().get(suspendedThreadIndex));
		assertEquals(State.STEPPING_INTO, target.getThreads().get(suspendedThreadIndex).getState());

		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepIntoReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step into when the thread is not in suspended mode or the debug target is disconnected or the current instruction don't support step into.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#canStepInto(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canStepIntoDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canStepInto(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canStepIntoDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canStepInto(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canStepIntoDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canStepInto(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canStepIntoDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(suspendedThreadIndex)));

		// Add an instruction and tell that we can step into it.
		target.getThreads().get(suspendedThreadIndex).getTopStackFrame().setCurrentInstruction(
				DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.getThreads().get(suspendedThreadIndex).getTopStackFrame().setCanStepIntoCurrentInstruction(
				true);
		assertEquals(true, ThreadUtils.canStepInto(target.getThreads().get(suspendedThreadIndex)));

		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepInto(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#stepReturnReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void stepReturnReplyDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#stepReturnReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void stepReturnReplyDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#stepReturnReply(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void stepReturnReplyDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#stepReturnReply(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void stepReturnReplyDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(suspendedThreadIndex));
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}

		// Add a parent frame to be able to step return.
		StackFrame newFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		newFrame.setName("newFrame");
		newFrame.setParentFrame(target.getThreads().get(suspendedThreadIndex).getTopStackFrame());
		target.getThreads().get(suspendedThreadIndex).setTopStackFrame(newFrame);

		ThreadUtils.stepReturnReply(target.getThreads().get(suspendedThreadIndex));
		assertEquals(State.STEPPING_RETURN, target.getThreads().get(suspendedThreadIndex).getState());

		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
		try {
			ThreadUtils.stepReturnReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't step return when the thread is not in suspended mode or the debug target is disconnected or the current stack frame don't have a parent frame.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#canStepReturn(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canStepReturnDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canStepReturn(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canStepReturnDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canStepReturn(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canStepReturnDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canStepReturn(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canStepReturnDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(suspendedThreadIndex)));

		// Add a parent frame to be able to step return.
		StackFrame newFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		newFrame.setName("newFrame");
		newFrame.setParentFrame(target.getThreads().get(suspendedThreadIndex).getTopStackFrame());
		target.getThreads().get(suspendedThreadIndex).setTopStackFrame(newFrame);
		assertEquals(true, ThreadUtils.canStepReturn(target.getThreads().get(suspendedThreadIndex)));

		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canStepReturn(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#pushStackFrameEvent(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void pushStackFrameEventDebugTargetTerminating() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final EObject instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(runningThreadIndex), "new frame", context,
					instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingIntoThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingOverThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingReturnThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(suspendedThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(suspendingThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(terminatedThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(terminatingThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#pushStackFrameEvent(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void pushStackFrameEventDebugTargetTerminated() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final EObject instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(runningThreadIndex), "new frame", context,
					instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingIntoThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingOverThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingReturnThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(suspendedThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(suspendingThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(terminatedThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(terminatingThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#pushStackFrameEvent(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void pushStackFrameEventDebugTargetDisconnnected() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final EObject instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(runningThreadIndex), "new frame", context,
					instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingIntoThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingOverThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingReturnThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(suspendedThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(suspendingThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(terminatedThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(terminatingThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#pushStackFrameEvent(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void pushStackFrameEventDebugTargetConnnected() {
		final EObject context = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final EObject instruction = DebugPackage.eINSTANCE.getDebugFactory().createVariable();
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		ThreadUtils.pushStackFrameReply(target.getThreads().get(suspendedThreadIndex), "new frame", context,
				instruction, false);
		assertEquals("new frame", target.getThreads().get(suspendedThreadIndex).getTopStackFrame().getName());
		assertEquals(target.getThreads().get(suspendedThreadIndex).getBottomStackFrame(), target.getThreads()
				.get(suspendedThreadIndex).getTopStackFrame().getParentFrame());

		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(suspendingThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(terminatedThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(terminatingThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}

		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(runningThreadIndex), "new frame", context,
					instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingIntoThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingOverThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.pushStackFrameReply(target.getThreads().get(steppingReturnThreadIndex), "new frame",
					context, instruction, false);
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't push a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#canPushFrame(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canPushFrameDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canPushFrame(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canPushFrameDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canPushFrame(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canPushFrameDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canPushFrame(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canPushFrameDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(true, ThreadUtils.canPushFrame(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canPushFrame(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#popStackFrameReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void popStackFrameEventDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#popStackFrameReply(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void popStackFrameEventDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#popStackFrameReply(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void popStackFrameEventDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(suspendedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#popStackFrameReply(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void popStackFrameEventDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(suspendedThreadIndex));
		} catch (IllegalStateException e) {
			fail();
		}

		StackFrame newFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		newFrame.setName("new frame");
		newFrame.setParentFrame(target.getThreads().get(suspendedThreadIndex).getTopStackFrame());
		target.getThreads().get(suspendedThreadIndex).setTopStackFrame(newFrame);
		ThreadUtils.popStackFrameReply(target.getThreads().get(suspendedThreadIndex));
		assertEquals(target.getThreads().get(suspendedThreadIndex).getBottomStackFrame(), target.getThreads()
				.get(suspendedThreadIndex).getTopStackFrame());

		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(suspendingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(terminatedThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(terminatingThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}

		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}

		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(runningThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingIntoThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingOverThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
		try {
			ThreadUtils.popStackFrameReply(target.getThreads().get(steppingReturnThreadIndex));
			fail();
		} catch (IllegalStateException e) {
			assertEquals(
					"can't pop a stack frame when the debug target is not connected or the thread is not suspended.",
					e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#canPopFrame(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canPopFrameDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canPopFrame(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void canPopFrameDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canPopFrame(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void canPopFrameDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#canPopFrame(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void canPopFrameDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(steppingReturnThreadIndex)));

		assertEquals(true, ThreadUtils.canPopFrame(target.getThreads().get(suspendedThreadIndex)));

		StackFrame newFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		newFrame.setName("new frame");
		newFrame.setParentFrame(target.getThreads().get(suspendedThreadIndex).getTopStackFrame());
		target.getThreads().get(suspendedThreadIndex).setTopStackFrame(newFrame);
		assertEquals(true, ThreadUtils.canPopFrame(target.getThreads().get(suspendedThreadIndex)));

		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.canPopFrame(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isActive(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void isActiveDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isActive(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void isActiveDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isActive(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void isActiveDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isActive(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void isActiveDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(true, ThreadUtils.isActive(target.getThreads().get(runningThreadIndex)));
		assertEquals(true, ThreadUtils.isActive(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(true, ThreadUtils.isActive(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(true, ThreadUtils.isActive(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isActive(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isStepping(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void isSteppingDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isStepping(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void isSteppingDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isStepping(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void isSteppingDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isStepping(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void isSteppingDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(runningThreadIndex)));
		assertEquals(true, ThreadUtils.isStepping(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(true, ThreadUtils.isStepping(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(true, ThreadUtils.isStepping(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isStepping(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isSuspended(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void isSuspendedDebugTargetTerminating() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATING);
		createThreads(target);

		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(true, ThreadUtils.isSuspended(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isSuspended(Thread)} in {@link DebugTargetState#TERMINATING}.
	 */
	@Test
	public void isSuspendedDebugTargetTerminated() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.TERMINATED);
		createThreads(target);

		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(true, ThreadUtils.isSuspended(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isSuspended(Thread)} in {@link DebugTargetState#DISCONNECTED}.
	 */
	@Test
	public void isSuspendedDebugTargetDisconnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.DISCONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(true, ThreadUtils.isSuspended(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#isSuspended(Thread)} in {@link DebugTargetState#CONNECTED}.
	 */
	@Test
	public void isSuspendedDebugTargetConnnected() {
		final DebugTarget target = DebugPackage.eINSTANCE.getDebugFactory().createDebugTarget();
		target.setContext(DebugPackage.eINSTANCE.getDebugFactory().createVariable());
		target.setName("target");
		target.setState(DebugTargetState.CONNECTED);
		createThreads(target);

		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(runningThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingIntoThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingOverThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(steppingReturnThreadIndex)));
		assertEquals(true, ThreadUtils.isSuspended(target.getThreads().get(suspendedThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(suspendingThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(terminatedThreadIndex)));
		assertEquals(false, ThreadUtils.isSuspended(target.getThreads().get(terminatingThreadIndex)));
	}

	/**
	 * Tests {@link ThreadUtils#getThread(StackFrame)}.
	 */
	@Test
	public void getThreadNoContainer() {
		final StackFrame frame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		frame.setName("frame");

		assertEquals(null, ThreadUtils.getThread(frame));
	}

	/**
	 * Tests {@link ThreadUtils#getThread(StackFrame)}.
	 */
	@Test
	public void getThreadThreadContainer() {
		final Thread thread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		thread.setName("thread");
		final StackFrame frame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		frame.setName("frame");
		thread.setBottomStackFrame(frame);
		thread.setTopStackFrame(frame);

		assertEquals(thread, ThreadUtils.getThread(frame));

	}

	/**
	 * Tests {@link ThreadUtils#getThread(StackFrame)}.
	 */
	@Test
	public void getThreadFrameContainerNoThread() {
		final StackFrame containingFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		containingFrame.setName("containingFrame");
		final StackFrame frame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		frame.setName("frame");
		containingFrame.setChildFrame(frame);

		assertEquals(null, ThreadUtils.getThread(frame));
	}

	/**
	 * Tests {@link ThreadUtils#getThread(StackFrame)}.
	 */
	@Test
	public void getThreadFrameContainer() {
		final Thread thread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		thread.setName("thread");
		final StackFrame containingFrame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		containingFrame.setName("containingFrame");
		final StackFrame frame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		frame.setName("frame");
		thread.setBottomStackFrame(containingFrame);
		thread.setTopStackFrame(containingFrame);
		containingFrame.setChildFrame(frame);

		assertEquals(thread, ThreadUtils.getThread(frame));
	}

	/**
	 * Tests {@link ThreadUtils#setCurrentInstructionReply(Thread, EObject, boolean)}.
	 */
	@Test(expected = NullPointerException.class)
	public void setCurrentInstructionReplyNullThread() {
		ThreadUtils.setCurrentInstructionReply(null, EcorePackage.eINSTANCE, true);
	}

	/**
	 * Tests {@link ThreadUtils#setCurrentInstructionReply(Thread, EObject, boolean)}.
	 */
	@Test
	public void setCurrentInstructionReplyNoStackFrame() {
		final Thread thread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		thread.setName("thread");

		try {
			ThreadUtils.setCurrentInstructionReply(thread, EcorePackage.eINSTANCE, true);
			fail();
		} catch (IllegalStateException e) {
			assertEquals("can't set current instrcution when there is no top stack frame.", e.getMessage());
		}
	}

	/**
	 * Tests {@link ThreadUtils#setCurrentInstructionReply(Thread, EObject, boolean)}.
	 */
	@Test
	public void setCurrentInstructionReply() {
		final Thread thread = DebugPackage.eINSTANCE.getDebugFactory().createThread();
		thread.setName("thread");
		final StackFrame frame = DebugPackage.eINSTANCE.getDebugFactory().createStackFrame();
		frame.setName("frame");
		frame.setContext(EcorePackage.eINSTANCE);
		frame.setCurrentInstruction(null);
		frame.setCanStepIntoCurrentInstruction(false);
		thread.setBottomStackFrame(frame);
		thread.setTopStackFrame(frame);

		ThreadUtils.setCurrentInstructionReply(thread, EcorePackage.eINSTANCE, true);

		assertEquals(EcorePackage.eINSTANCE, thread.getTopStackFrame().getCurrentInstruction());
		assertEquals(true, thread.getTopStackFrame().isCanStepIntoCurrentInstruction());
	}

}
