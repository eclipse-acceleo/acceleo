/*******************************************************************************
 * Copyright (c) 2009, 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;

/**
 * A "logger" facade for Acceleo, as we need to avoid classes importing org.eclipse.core.resources when
 * running in standalone. Logging from here will either delegate to AcceleoCommonPlugin when eclipse is
 * running to log in the error log, or display the logs on the {@link System#err standard error stream}
 * otherwise.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoLogger {
	/** hides default constructor. */
	private AcceleoLogger() {
		// hides default constructor
	}

	/**
	 * Trace an Exception in the error log.
	 * 
	 * @param e
	 *            Exception to log.
	 * @param blocker
	 *            <code>True</code> if the exception must be logged as error, <code>False</code> to log it as
	 *            a warning.
	 */
	public static void log(Exception e, boolean blocker) {
		if (e == null) {
			throw new NullPointerException(AcceleoCommonMessages.getString("AcceleoLogger.LogNullException")); //$NON-NLS-1$
		}

		if (getCommonPlugin() == null) {
			// We are out of eclipse. Prints the stack trace on standard error.
			// CHECKSTYLE:OFF
			e.printStackTrace();
			// CHECKSTYLE:ON
		} else if (e instanceof CoreException) {
			log(((CoreException)e).getStatus());
		} else if (e instanceof NullPointerException) {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			log(new Status(severity, AcceleoCommonPlugin.PLUGIN_ID, severity, AcceleoCommonMessages
					.getString("AcceleoLogger.ElementNotFound"), e)); //$NON-NLS-1$
		} else {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			log(new Status(severity, AcceleoCommonPlugin.PLUGIN_ID, severity, e.getMessage(), e));
		}
	}

	/**
	 * Puts the given status in the error log view.
	 * 
	 * @param status
	 *            Error Status.
	 */
	public static void log(IStatus status) {
		// Eclipse platform displays NullPointer on standard error instead of throwing it.
		// We'll handle this by throwing it ourselves.
		if (status == null) {
			throw new NullPointerException(AcceleoCommonMessages.getString("AcceleoLogger.LogNullStatus")); //$NON-NLS-1$
		}

		if (getCommonPlugin() != null) {
			getCommonPlugin().getLog().log(status);
		} else {
			// We are out of eclipse. Prints the message on standard error.
			// CHECKSTYLE:OFF
			System.err.println(status.getMessage());
			status.getException().printStackTrace();
			// CHECKSTYLE:ON
		}
	}

	/**
	 * Puts the given message in the error log view, as error or warning.
	 * 
	 * @param message
	 *            The message to put in the error log view.
	 * @param blocker
	 *            <code>True</code> if the message must be logged as error, <code>False</code> to log it as a
	 *            warning.
	 */
	public static void log(String message, boolean blocker) {
		if (getCommonPlugin() == null) {
			// We are out of eclipse. Prints the message on standard error.
			// CHECKSTYLE:OFF
			System.err.println(message);
			// CHECKSTYLE:ON
		} else {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			String errorMessage = message;
			if (errorMessage == null || "".equals(errorMessage)) { //$NON-NLS-1$
				errorMessage = AcceleoCommonMessages.getString("AcceleoLogger.UnexpectedException"); //$NON-NLS-1$
			}
			log(new Status(severity, AcceleoCommonPlugin.PLUGIN_ID, errorMessage));
		}
	}

	/**
	 * Traces an exception in the error log with the given log message.
	 * <p>
	 * This is a convenience method fully equivalent to using
	 * <code>log(new Status(int, PLUGIN_ID, message, cause)</code>.
	 * </p>
	 * 
	 * @param message
	 *            The message that is to be displayed in the error log view.
	 * @param cause
	 *            Exception that is to be logged.
	 * @param blocker
	 *            <code>True</code> if the exception must be logged as error, <code>False</code> to log it as
	 *            a warning.
	 * @since 0.8
	 */
	public static void log(String message, Exception cause, boolean blocker) {
		final int severity;
		if (blocker) {
			severity = IStatus.ERROR;
		} else {
			severity = IStatus.WARNING;
		}
		log(new Status(severity, AcceleoCommonPlugin.PLUGIN_ID, message, cause));
	}

	/**
	 * Returns the plugin instance if eclipse is running.
	 * 
	 * @return The plugin instance if eclipse is running.
	 */
	private static Plugin getCommonPlugin() {
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			return AcceleoCommonPlugin.getDefault();
		}
		return null;
	}
}
