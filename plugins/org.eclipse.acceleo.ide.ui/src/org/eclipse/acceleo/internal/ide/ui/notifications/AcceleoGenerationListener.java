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
import org.eclipse.acceleo.common.ui.notification.NotificationDialogUtil;
import org.eclipse.acceleo.common.ui.notification.NotificationType;
import org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;

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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.AbstractAcceleoTextGenerationListener#generationCompleted()
	 */
	@Override
	public void generationCompleted() {
		super.generationCompleted();
		if (filesGenerated.size() > 0) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					StringBuffer buffer = new StringBuffer(AcceleoUIMessages
							.getString("AcceleoNotifications.FollowingFilesGenerated") + "\n"); //$NON-NLS-1$//$NON-NLS-2$
					for (String file : filesGenerated) {
						buffer.append(file);
						buffer.append("\n"); //$NON-NLS-1$
					}

					NotificationDialogUtil.notify(AcceleoUIMessages
							.getString("AcceleoNotifications.FollowingFilesGenerated"), buffer.toString(), //$NON-NLS-1$
							NotificationType.SUCCESS);
				}
			});
		}

		filesGenerated.clear();
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
				result = false;
				// TODO Detection of the files generated for which a notification should not be fired
			}
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
		return result;
	}
}
