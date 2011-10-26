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

import java.io.File;
import java.util.ArrayList;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.common.ui.notification.NotificationDialogUtil;
import org.eclipse.acceleo.common.ui.notification.NotificationType;
import org.eclipse.acceleo.common.ui.notification.NotificationUtils;
import org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

/**
 * The Acceleo log listener.
 * 
 * @author <a href="mailto:stephane.begaudeau@gmail.com">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoGenerationListener extends AbstractAcceleoTextGenerationListener {

	/**
	 * The list of the files generated.
	 */
	private ArrayList<String> filesGenerated = new ArrayList<String>();

	/**
	 * The start time of the generation.
	 */
	private long start;

	@Override
	public void generationStart(Monitor monitor, File targetFolder) {
		AcceleoLogListener.resetCounters();
		start = System.currentTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener#generationCompleted()
	 */
	@Override
	public void generationCompleted() {
		super.generationCompleted();
		if (filesGenerated.size() > 0 && AcceleoPreferences.areNotificationsEnabled()
				&& !AcceleoPreferences.areNotificationsForcedDisabled()
				&& AcceleoPreferences.areSuccessNotificationsEnabled()) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					Double finalTime = Double.valueOf(Double.valueOf(System.currentTimeMillis() - start)
							.doubleValue() / 1000d);
					int errors = AcceleoLogListener.getErrors();
					int warnings = AcceleoLogListener.getWarnings();
					int infos = AcceleoLogListener.getInfos();
					if (errors == 0 && warnings == 0 && infos == 0) {
						NotificationDialogUtil.notify(
								AcceleoUIMessages.getString("AcceleoNotifications.FilesGeneratedTitle"), //$NON-NLS-1$
								AcceleoUIMessages.getString(
										"AcceleoNotifications.FilesGeneratedMessage", Integer //$NON-NLS-1$
												.valueOf(filesGenerated.size()), finalTime),
								NotificationType.SUCCESS);
					} else {
						NotificationDialogUtil.notify(AcceleoUIMessages
								.getString("AcceleoNotifications.FilesGeneratedTitle"), AcceleoUIMessages //$NON-NLS-1$
								.getString("AcceleoNotifications.FilesGeneratedMessageWithErrors", Integer //$NON-NLS-1$
										.valueOf(filesGenerated.size()), finalTime, Integer.valueOf(errors),
										Integer.valueOf(warnings), Integer.valueOf(infos)),
								NotificationType.SUCCESS, new AcceleoHyperLinkListener(), NotificationUtils
										.getDefaultPreferences());
					}
				}
			});
		}

		filesGenerated.clear();
		AcceleoLogListener.resetCounters();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener#listensToGenerationEnd()
	 */
	@Override
	public boolean listensToGenerationEnd() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener#fileGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	@Override
	public void fileGenerated(final AcceleoTextGenerationEvent event) {
		super.fileGenerated(event);
		File file = new File(event.getText());
		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
				new Path(file.getAbsolutePath()));
		if (iFile != null && AcceleoGenerationListener.shouldNotify(iFile)) {
			String text = event.getText();
			filesGenerated.add(new Path(text).lastSegment());
		}
	}

	/**
	 * Indicates if we should fire a notification for the generation of this file.
	 * 
	 * @param iFile
	 *            The file.
	 * @return <code>true</code> if a notification should be fired, or <code>false</code> otherwise.
	 */
	private static boolean shouldNotify(IFile iFile) {
		boolean result = true;
		try {
			if (iFile.getProject().isAccessible()
					&& iFile.getProject().hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
				// TODO Detection of the files generated for which a notification should not be fired
				result = false;
			}
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
		return result;
	}

	/**
	 * The hyperlink listener that listens to any click on <a>Error Log</a>.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 */
	public class AcceleoHyperLinkListener implements SelectionListener {

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(SelectionEvent e) {
			this.openErrorLogView(e);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetDefaultSelected(SelectionEvent e) {
			this.openErrorLogView(e);
		}

		/**
		 * Opens the error log view.
		 * 
		 * @param e
		 *            The selection event
		 */
		private void openErrorLogView(SelectionEvent e) {
			if ("Error Log".equals(e.text)) { //$NON-NLS-1$
				try {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(
							"org.eclipse.pde.runtime.LogView"); //$NON-NLS-1$
				} catch (PartInitException e1) {
					AcceleoUIActivator.log(e1, true);
				}
			}
		}
	}

}
