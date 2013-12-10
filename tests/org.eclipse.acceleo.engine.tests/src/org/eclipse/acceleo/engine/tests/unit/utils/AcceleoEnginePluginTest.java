/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the engine plug-in activator. Will mainly test behavior of utility methods it declares.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoEnginePluginTest {
	/** Error messages to use for these tests. */
	private static final String[] ERROR_MESSAGES = {"NullPointerException has been thrown.",
			"failed to build.", "\u00ec", "test", };

	/** Possible severities for an exception. */
	private static final int[] ERROR_SEVERITIES = {IStatus.WARNING, IStatus.ERROR, IStatus.INFO, };

	/** This will keep track of the last {@link IStatus} that has been logged. */
	protected IStatus loggedStatus;

	/** This listener will be used to keep track of {@link IStatus}es logged. */
	private ILogListener logListener;

	/** The output stream that will be used as a temporary System.err. */
	private PrintStream temporaryErr;

	/** File that will serve as an output for the temporary System.err. */
	private File temporaryLog;

	/**
	 * Tests the behavior of {@link AcceleoEnginePlugin#log(Exception, boolean)} passing an arbitrary
	 * exception other than {@link NullPointerException} and {@link CoreException} to be logged. Expects the
	 * exception to be logged with the specified severity. The error message should be the one specified in
	 * org.eclipse.acceleo.engine.acceleoenginemessages.properties with key
	 * &quot;AcceleoEnginePlugin.JavaException&quot;.
	 */
	@Test
	public void testLogExceptionArbitraryException() {
		boolean blocker = false;
		for (String message : ERROR_MESSAGES) {
			final PrintStream systemErr = System.err;
			// disables standard error to avoid all logged exception to be displayed in console.
			final Exception exception = new IllegalArgumentException(message);
			System.setErr(temporaryErr);
			AcceleoEnginePlugin.log(exception, blocker);
			System.setErr(systemErr);

			final int expectedSeverity;
			if (blocker) {
				expectedSeverity = IStatus.ERROR;
			} else {
				expectedSeverity = IStatus.WARNING;
			}
			blocker = !blocker;

			assertEquals("Unexpected message of the logged exception.", exception.getMessage(), loggedStatus
					.getMessage());
			assertEquals("Unexpected severity of the logged exception.", expectedSeverity, loggedStatus
					.getSeverity());
			assertEquals("Exception logged with unexpected plug-in ID.", AcceleoEnginePlugin.PLUGIN_ID,
					loggedStatus.getPlugin());
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEnginePlugin#log(Exception, boolean)} passing a
	 * {@link CoreException} to be logged. Expects the exception to be logged with the specified error message
	 * and severity.
	 */
	@Test
	public void testLogExceptionCoreException() {
		for (int severity : ERROR_SEVERITIES) {
			for (String message : ERROR_MESSAGES) {
				final Status coreExceptionStatus = new Status(severity, AcceleoEngineTestPlugin.PLUGIN_ID,
						message);
				// disables standard error to avoid all logged exception to be displayed in console.
				final PrintStream systemErr = System.err;
				System.setErr(temporaryErr);
				AcceleoEnginePlugin.log(new CoreException(coreExceptionStatus), true);
				System.setErr(systemErr);

				assertEquals("Unexpected message of the logged core exception.", message, loggedStatus
						.getMessage());
				assertEquals("Unexpected severity of the logged core exception.", severity, loggedStatus
						.getSeverity());
				assertEquals("Core exception logged with unexpected plug-in ID.",
						AcceleoEngineTestPlugin.PLUGIN_ID, loggedStatus.getPlugin());
			}
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEnginePlugin#log(Exception, boolean)} with <code>null</code> as the
	 * exception argument. Expects a {@link NullPointerException} to be thrown.
	 */
	@Test
	public void testLogExceptionNullException() {
		try {
			AcceleoEnginePlugin.log((Exception)null, true);
			fail("Logging null didn't throw expected NullPointerException.");
		} catch (NullPointerException e) {
			// This was expected behavior
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEnginePlugin#log(Exception, boolean)} passing a
	 * {@link NullPointerException} to be logged. Expects the exception to be logged with the specified
	 * severity. The error message should be the one specified in
	 * org.eclipse.acceleo.engine.acceleoenginemessages.properties with key
	 * &quot;AcceleoEnginePlugin.ElementNotFound&quot;.
	 */
	@Test
	public void testLogExceptionNullPointerException() {
		boolean blocker = false;
		for (String message : ERROR_MESSAGES) {
			// disables standard error to avoid all logged exception to be displayed in console.
			final PrintStream systemErr = System.err;
			System.setErr(temporaryErr);
			AcceleoEnginePlugin.log(new NullPointerException(message), blocker);
			System.setErr(systemErr);

			final String expectedMessage = AcceleoEngineMessages
					.getString("AcceleoEnginePlugin.ElementNotFound");
			final int expectedSeverity;
			if (blocker) {
				expectedSeverity = IStatus.ERROR;
			} else {
				expectedSeverity = IStatus.WARNING;
			}
			blocker = !blocker;

			assertEquals("Unexpected message of the logged NullPointerException.", expectedMessage,
					loggedStatus.getMessage());
			assertEquals("Unexpected severity of the logged NullPointerException.", expectedSeverity,
					loggedStatus.getSeverity());
			assertEquals("NullPointerException logged with unexpected plug-in ID.",
					AcceleoEnginePlugin.PLUGIN_ID, loggedStatus.getPlugin());
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEnginePlugin#log(String, boolean)} with <code>null</code> as the
	 * message to be logged. Expects a new entry to be logged with the given severity and the message
	 * specified in org.eclipse.acceleo.engine.acceleoenginemessages.properties with key
	 * &quot;AcceleoEnginePlugin.UnexpectedException&quot;.
	 */
	@Test
	public void testLogMessageNullMessage() {
		boolean blocker = false;
		for (int i = 0; i < ERROR_MESSAGES.length; i++) {
			final PrintStream systemErr = System.err;
			// disables standard error to avoid all logged exception to be displayed in console.
			System.setErr(temporaryErr);
			AcceleoEnginePlugin.log((String)null, blocker);
			System.setErr(systemErr);

			final String expectedMessage = AcceleoEngineMessages
					.getString("AcceleoEnginePlugin.UnexpectedException");
			final int expectedSeverity;
			if (blocker) {
				expectedSeverity = IStatus.ERROR;
			} else {
				expectedSeverity = IStatus.WARNING;
			}
			blocker = !blocker;

			assertEquals("Unexpected message of the logged message.", expectedMessage, loggedStatus
					.getMessage());
			assertEquals("Unexpected severity of the logged message.", expectedSeverity, loggedStatus
					.getSeverity());
			assertEquals("Message logged with unexpected plug-in ID.", AcceleoEnginePlugin.PLUGIN_ID,
					loggedStatus.getPlugin());
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEnginePlugin#log(String, boolean)} with a valid message to be
	 * logged. Expects a new entry to be logged with the given severity and message.
	 */
	@Test
	public void testLogMessageValidMessage() {
		boolean blocker = false;
		for (String message : ERROR_MESSAGES) {
			final PrintStream systemErr = System.err;
			// disables standard error to avoid all logged exception to be displayed in console.
			System.setErr(temporaryErr);
			AcceleoEnginePlugin.log(message, blocker);
			System.setErr(systemErr);

			final int expectedSeverity;
			if (blocker) {
				expectedSeverity = IStatus.ERROR;
			} else {
				expectedSeverity = IStatus.WARNING;
			}
			blocker = !blocker;

			assertEquals("Unexpected message logged.", message, loggedStatus.getMessage());
			assertEquals("Unexpected severity of the logged message.", expectedSeverity, loggedStatus
					.getSeverity());
			assertEquals("Message logged with unexpected plug-in ID.", AcceleoEnginePlugin.PLUGIN_ID,
					loggedStatus.getPlugin());
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEnginePlugin#log(IStatus)} with <code>null</code> as the status to
	 * be logged. Expects a {@link NullPointerException} to be thrown with the given status' error message.
	 */
	@Test
	public void testLogStatusNullStatus() {
		try {
			AcceleoEnginePlugin.log(null);
			fail("Logging null status didn't throw expected NullPointerException.");
		} catch (NullPointerException e) {
			// This ws expected behavior
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEnginePlugin#log(IStatus)} with a valid status to be logged.
	 * Expects the status to be logged with the specified severity, error message and source plugin.
	 */
	@Test
	public void testLogStatusValidStatus() {
		for (int severity : ERROR_SEVERITIES) {
			for (String message : ERROR_MESSAGES) {
				final Status status = new Status(severity, AcceleoEngineTestPlugin.PLUGIN_ID, message);
				final PrintStream systemErr = System.err;
				// disables standard error to avoid all logged exception to be displayed in console.
				System.setErr(temporaryErr);
				AcceleoEnginePlugin.log(status);
				System.setErr(systemErr);

				assertEquals("Unexpected message of the logged exception.", message, loggedStatus
						.getMessage());
				assertEquals("Unexpected severity of the logged exception.", severity, loggedStatus
						.getSeverity());
				assertEquals("Exception logged with unexpected plug-in ID.",
						AcceleoEngineTestPlugin.PLUGIN_ID, loggedStatus.getPlugin());
			}
		}
	}

	@Before
	public void setUp() {
		// Creates a log listener that will update the field loggedStatus as needed
		logListener = new ILogListener() {
			public void logging(IStatus status, String message) {
				loggedStatus = status;
			}
		};
		// Then sets it to listen to the log
		AcceleoEnginePlugin.getDefault().getLog().addLogListener(logListener);

		try {
			// Creates temporary error log
			final File dataDir = new File(FileLocator.toFileURL(
					AcceleoEngineTestPlugin.getDefault().getBundle().getEntry("/data")).getFile());
			temporaryLog = new File(dataDir.getAbsolutePath() + "/testLogErrorLog");
			temporaryErr = new PrintStream(temporaryLog);
		} catch (IOException e) {
			fail("Couldn't create temporary error log.");
		}
	}

	@After
	public void tearDown() {
		AcceleoEnginePlugin.getDefault().getLog().removeLogListener(logListener);
		if (temporaryErr != null) {
			temporaryErr.close();
		}
		if (temporaryLog.exists()) {
			temporaryLog.delete();
		}
	}
}
