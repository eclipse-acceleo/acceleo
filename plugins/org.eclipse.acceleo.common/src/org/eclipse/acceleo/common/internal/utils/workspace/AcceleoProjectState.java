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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.core.resources.IProject;

/**
 * The state of an Acceleo project.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoProjectState {

	/**
	 * The name of the project.
	 */
	private String projectName;

	/**
	 * The timestamp of the last structural build time.
	 */
	private long lastStructuralBuildTime;

	/**
	 * Returns the last structural build time.
	 * 
	 * @return The last structural build time
	 */
	public long getLaststructuralBuildTime() {
		return lastStructuralBuildTime;
	}

	/**
	 * Sets the last structural build time.
	 * 
	 * @param lastStructuralBuildTime
	 *            The last structural build time.
	 */
	public void setLastStructuralBuildTime(long lastStructuralBuildTime) {
		this.lastStructuralBuildTime = lastStructuralBuildTime;
	}

	/**
	 * Returns the name of the project.
	 * 
	 * @return The name of the project
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Sets the name of the project.
	 * 
	 * @param projectName
	 *            The new name of the project
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Write the state of the project in the given data output stream.
	 * 
	 * @param out
	 *            The given data output stream.
	 * @throws IOException
	 *             In case of serialization issues
	 */
	public void write(DataOutputStream out) throws IOException {
		out.writeUTF(this.projectName);
		out.writeLong(this.lastStructuralBuildTime);
	}

	/**
	 * Reads the state of the project from the given data input stream.
	 * 
	 * @param project
	 *            The project
	 * @param in
	 *            The data input stream
	 * @return The state of the project from the given data input stream
	 * @throws IOException
	 *             In case of deserialization issues
	 */
	public static AcceleoProjectState read(IProject project, DataInputStream in) throws IOException {
		AcceleoProjectState newState = new AcceleoProjectState();
		newState.projectName = in.readUTF();
		if (!project.getName().equals(newState.projectName)) {
			AcceleoCommonPlugin.log(AcceleoCommonMessages
					.getString("AcceleoProjectState.ProjectDoesNotMatch"), false); //$NON-NLS-1$
			return null;
		}
		newState.lastStructuralBuildTime = in.readLong();
		return newState;
	}

}
