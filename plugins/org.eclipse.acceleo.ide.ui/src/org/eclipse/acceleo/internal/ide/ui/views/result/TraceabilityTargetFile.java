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
package org.eclipse.acceleo.internal.ide.ui.views.result;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * The current generated file. The root object to store traceability information.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class TraceabilityTargetFile extends TraceabilityContainer {

	/**
	 * The target file full path. It is relative to the workspace if it is possible. It can also be an
	 * absolute file path if the target file isn't located in the workspace.
	 */
	private IPath targetFileFullPath;

	/**
	 * Constructor.
	 * 
	 * @param targetFileFullPath
	 *            the target file full path
	 */
	public TraceabilityTargetFile(String targetFileFullPath) {
		this.targetFileFullPath = new Path(targetFileFullPath);
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(this.targetFileFullPath);
		if (file != null) {
			this.targetFileFullPath = file.getFullPath();
		}
	}

	/**
	 * Gets the target file full path.
	 * 
	 * @return the target file full path
	 */
	public IPath getTargetFileFullPath() {
		return targetFileFullPath;
	}

	/**
	 * Gets the target file if it is generated in the workspace.
	 * 
	 * @return the target file, or null if it doesn't exist
	 */
	public IFile getTargetFile() {
		if (targetFileFullPath.segmentCount() > 1) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(targetFileFullPath);
			if (file.isAccessible()) {
				return file;
			}
		}
		return null;
	}

}
