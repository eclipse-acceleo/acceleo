/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.workspace;

import org.eclipse.core.resources.IProject;

/**
 * Utility class to hold project information.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoProjectInfo {

	/**
	 * The project.
	 */
	private IProject project;

	/**
	 * The state saved.
	 */
	private AcceleoProjectState savedState;

	/**
	 * Indicates if the information have been read from the disk.
	 */
	private boolean hasBeenRead;

	/**
	 * The constructor.
	 * 
	 * @param project
	 *            The project from which the information will be taken
	 */
	public AcceleoProjectInfo(IProject project) {
		this.project = project;
	}

	/**
	 * Returns the project.
	 * 
	 * @return The project.
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * Returns the saved state.
	 * 
	 * @return The saved state.
	 */
	public AcceleoProjectState getSavedState() {
		return this.savedState;
	}

	/**
	 * Sets the saved state.
	 * 
	 * @param savedState
	 *            The saved state
	 */
	public void setSavedState(AcceleoProjectState savedState) {
		this.savedState = savedState;
	}

	/**
	 * Mark the information as having been read from the disk.
	 */
	public void markAsRead() {
		this.hasBeenRead = true;
	}

	/**
	 * Returns <code>true</code> if the information has been read from the disk, <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if the information has been read from the disk, <code>false</code> otherwise.
	 */
	public boolean hasBeenRead() {
		return this.hasBeenRead;
	}
}
