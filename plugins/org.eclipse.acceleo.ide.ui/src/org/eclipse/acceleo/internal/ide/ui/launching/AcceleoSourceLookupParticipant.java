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
package org.eclipse.acceleo.internal.ide.ui.launching;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.debug.model.AcceleoStackFrame;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.launching.sourcelookup.containers.JavaSourceLookupParticipant;

/**
 * A source lookup participant that searches for an Acceleo source code.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoSourceLookupParticipant extends JavaSourceLookupParticipant {

	/**
	 * Returns the source name associated with the given object, or <code>null</code> if none.
	 * 
	 * @param object
	 *            is an Acceleo debug model element
	 * @return the source name associated with the given object, or <code>null</code> if none
	 * @exception CoreException
	 *                if unable to retrieve the source name
	 */
	@Override
	public String getSourceName(Object object) throws CoreException {
		if (object instanceof AcceleoStackFrame) {
			return ((AcceleoStackFrame)object).getSourceName();
		}
		return super.getSourceName(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant#findSourceElements(java.lang.Object)
	 */
	@Override
	public Object[] findSourceElements(Object object) throws CoreException {
		Object[] result = super.findSourceElements(object);
		for (int i = 0; i < result.length; i++) {
			if (result[i] instanceof IFile
					&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)result[i]).getFileExtension())) {
				IFile file = (IFile)result[i];
				AcceleoProject acceleoProject = new AcceleoProject(file.getProject());
				IPath realFilePath = acceleoProject.getInputFilePath(file.getFullPath());
				if (realFilePath != null && realFilePath.segmentCount() > 1) {
					IFile realFile = ResourcesPlugin.getWorkspace().getRoot().getFile(realFilePath);
					if (realFile.exists()) {
						result[i] = realFile;
					}
				}
			}
		}
		return result;
	}
}
