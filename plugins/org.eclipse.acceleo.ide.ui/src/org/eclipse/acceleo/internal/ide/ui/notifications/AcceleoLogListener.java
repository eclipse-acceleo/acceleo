/*******************************************************************************
 * Copyright (c) 2011 Stephane Begaudeau.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Stephane Begaudeau - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.notifications;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Display;

/**
 * The Acceleo log listener.
 * 
 * @author <a href="mailto:stephane.begaudeau@gmail.com">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoLogListener implements ILogListener {

	/**
	 * The number of errors that occurred during the generation.
	 */
	private static int errors;

	/**
	 * The number of cancel that occurred during the generation.
	 */
	private static int cancels;

	/**
	 * The number of warnings that occurred during the generation.
	 */
	private static int warnings;

	/**
	 * The number of infos that occurred during the generation.
	 */
	private static int infos;

	/**
	 * Reset the counters.
	 */
	public static void resetCounters() {
		errors = 0;
		cancels = 0;
		warnings = 0;
		infos = 0;
	}

	/**
	 * Returns the number of errors found during the generation.
	 * 
	 * @return The number of errors found during the generation.
	 */
	public static int getErrors() {
		return errors;
	}

	/**
	 * Returns the number of warnings found during the generation.
	 * 
	 * @return The number of warnings found during the generation.
	 */
	public static int getWarnings() {
		return warnings;
	}

	/**
	 * Returns the number of cancels found during the generation.
	 * 
	 * @return The number of cancels found during the generation.
	 */
	public static int getCancels() {
		return cancels;
	}

	/**
	 * Returns the number of infos found during the generation.
	 * 
	 * @return The number of infos found during the generation.
	 */
	public static int getInfos() {
		return infos;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.ILogListener#logging(org.eclipse.core.runtime.IStatus, java.lang.String)
	 */
	public void logging(final IStatus status, final String plugin) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (AcceleoPreferences.areNotificationsEnabled()
						&& !AcceleoPreferences.areNotificationsForcedDisabled()) {
					delegateLog(status, plugin);
				}
			}
		});
	}

	/**
	 * Logs the event.
	 * 
	 * @param status
	 *            The status.
	 * @param plugin
	 *            The plugin launching the event.
	 */
	private static void delegateLog(IStatus status, String plugin) {
		if (AcceleoPreferences.areNotificationsForcedDisabled()
				|| !AcceleoPreferences.areNotificationsEnabled()) {
			return;
		}
		int severity = status.getSeverity();
		switch (severity) {
			case IStatus.CANCEL:
				if (AcceleoPreferences.areCancelNotificationsEnabled()) {
					cancels++;
				}
				break;
			case IStatus.WARNING:
				if (AcceleoPreferences.areWarningNotificationsEnabled()) {
					warnings++;
				}
				break;
			case IStatus.ERROR:
				if (AcceleoPreferences.areErrorNotificationsEnabled()) {
					errors++;
				}
				break;
			case IStatus.INFO:
				if (AcceleoPreferences.areInfoNotificationsEnabled()) {
					infos++;
				}
				break;
			default:
				if (AcceleoPreferences.areErrorNotificationsEnabled()) {
					errors++;
				}
				break;
		}
	}
}
