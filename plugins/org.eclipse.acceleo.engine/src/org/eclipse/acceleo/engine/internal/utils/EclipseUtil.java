/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.utils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.Monitor;

/**
 * This will provide eclipse-specific utility methods. <b>Note</b> that none of these methods can be used
 * standalone and a test for
 * 
 * <pre>
 * EMFPlugin.IS_ECLIPSE_RUNNING == true
 * </pre>
 * 
 * should be made before even invoking them.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class EclipseUtil {
	/**
	 * Utility classes don't need to be instantiated.
	 */
	private EclipseUtil() {
		// prevents instantiation
	}

	/**
	 * This will create an EMF {@link Monitor progress monitor} that can be used standalone to display
	 * operation progress to the user. If <code>delegate</code> isn't <code>null</code>, the created monitor
	 * will delegate all calls to it.
	 * 
	 * @param delegate
	 *            The delegate progress monitor. Can be <code>null</code> or Eclipse specific monitors.
	 * @return The created progress monitor.
	 */
	public static Monitor createProgressMonitor(Object delegate) {
		final Monitor monitor;
		if (delegate instanceof IProgressMonitorWithBlocking) {
			monitor = BasicMonitor.toMonitor((IProgressMonitorWithBlocking)delegate);
		} else if (delegate instanceof IProgressMonitor) {
			monitor = BasicMonitor.toMonitor((IProgressMonitor)delegate);
		} else {
			monitor = new BasicMonitor();
		}
		return monitor;
	}
}
