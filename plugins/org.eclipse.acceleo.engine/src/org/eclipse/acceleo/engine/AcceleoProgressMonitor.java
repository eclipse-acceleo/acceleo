/*******************************************************************************
 * Copyright (c) 2006, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine;

/**
 * Encapsulates an {@link org.eclipse.core.runtime.IProgressMonitor IProgressMonitor} to allow us to run out
 * of eclipse where no progress monitors are accessible.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 0.8
 */
public class AcceleoProgressMonitor {
	/** Progress monitor wrapped within this instance. Can be <code>null</code>. */
	private Object progressMonitor;

	/**
	 * Allows the default constructor to be used. This will have no progress monitor set. This is a standalone
	 * equivalent to eclipse's {@link org.eclipse.core.runtime.NullProgressMonitor}.
	 */
	public AcceleoProgressMonitor() {
		super();
	}

	/**
	 * Encapsulates the given progress monitor.
	 * 
	 * @param theProgressMonitor
	 *            Progress monitor to wrap within this instance. Can be <code>null</code>.
	 */
	public AcceleoProgressMonitor(Object theProgressMonitor) {
		progressMonitor = theProgressMonitor;
	}

	/**
	 * Notifies the wrapped progress monitor that a task begins.
	 * 
	 * @param name
	 *            Name of the beginning task.
	 * @param totalWork
	 *            Total number of work units.
	 */
	public void beginTask(String name, int totalWork) {
		if (progressMonitor != null) {
			((org.eclipse.core.runtime.IProgressMonitor)progressMonitor).beginTask(name, totalWork);
		}
	}

	/**
	 * Notifies the wrapped progress monitor that a task has been canceled.
	 * 
	 * @return <code>True</code> if cancellation has been requested, and <code>False</code> otherwise
	 */
	public boolean isCanceled() {
		if (progressMonitor != null) {
			return ((org.eclipse.core.runtime.IProgressMonitor)progressMonitor).isCanceled();
		}
		return false;
	}

	/**
	 * Notifies the wrapped progress monitor that a subtask begins.
	 * 
	 * @param name
	 *            Name of the beginning subtask.
	 */
	public void subTask(String name) {
		if (progressMonitor != null) {
			((org.eclipse.core.runtime.IProgressMonitor)progressMonitor).subTask(name);
		}
	}

	/**
	 * Notifies the wrapped progress monitor that a task has advanced.
	 * 
	 * @param work
	 *            Number of work units just completed.
	 */
	public void worked(int work) {
		if (progressMonitor != null) {
			((org.eclipse.core.runtime.IProgressMonitor)progressMonitor).worked(work);
		}
	}
}
