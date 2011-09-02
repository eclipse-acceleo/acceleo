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

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.ui.notification.NotificationDialogUtil;
import org.eclipse.acceleo.common.ui.notification.NotificationType;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.traceability.AcceleoTraceabilityPlugin;
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.ILogListener#logging(org.eclipse.core.runtime.IStatus, java.lang.String)
	 */
	public void logging(final IStatus status, final String plugin) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				delegateLog(status, plugin);
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
		String title = ""; //$NON-NLS-1$
		if (AcceleoCommonPlugin.PLUGIN_ID.equals(plugin)) {
			title = AcceleoUIMessages.getString("AcceleoNotifications.CommonTitle"); //$NON-NLS-1$
		} else if (AcceleoEnginePlugin.PLUGIN_ID.equals(plugin)) {
			title = AcceleoUIMessages.getString("AcceleoNotifications.EngineTitle"); //$NON-NLS-1$
		} else if (AcceleoUIActivator.PLUGIN_ID.equals(plugin)) {
			title = AcceleoUIMessages.getString("AcceleoNotifications.UITitle"); //$NON-NLS-1$
		} else if (AcceleoTraceabilityPlugin.PLUGIN_ID.equals(plugin)) {
			title = AcceleoUIMessages.getString("AcceleoNotifications.TraceabilityTitle"); //$NON-NLS-1$
		}

		NotificationType type = null;
		int severity = status.getSeverity();
		switch (severity) {
			case IStatus.CANCEL:
				type = NotificationType.CANCEL;
				break;
			case IStatus.WARNING:
				type = NotificationType.WARNING;
				break;
			case IStatus.ERROR:
				type = NotificationType.ERROR;
				break;
			case IStatus.INFO:
				type = NotificationType.INFO;
				break;
			case IStatus.OK:
				type = NotificationType.OK;
				break;
			default:
				type = NotificationType.ERROR;
				break;
		}
		NotificationDialogUtil.notify(title, status.getMessage(), type);
	}
}
