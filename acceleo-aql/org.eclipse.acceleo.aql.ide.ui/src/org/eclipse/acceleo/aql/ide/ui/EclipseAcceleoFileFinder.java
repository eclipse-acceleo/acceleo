/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * An {@link IResourceVisitor} implementation in charge of determining whether an {@link IResource} from the
 * workspace is an Acceleo document or not.
 * 
 * @author Florent Latombe
 */
public class EclipseAcceleoFileFinder implements IResourceVisitor {

	/**
	 * The {@link List} of {@link IFile Acceleo files} found while visiting.
	 */
	private List<IFile> acceleoDocuments;

	@Override
	public boolean visit(IResource resource) throws CoreException {
		if (resource.getType() == IResource.FILE && resource.getFileExtension().equals(
				AcceleoParser.MODULE_FILE_EXTENSION)) {
			IFile acceleoFile = (IFile)resource;
			if (this.acceleoDocuments == null) {
				this.acceleoDocuments = new ArrayList<>();
			}
			this.acceleoDocuments.add(acceleoFile);
		}
		return true;
	}

	/**
	 * After having {@link IResource#accept(IResourceVisitor) visited} an {@link IResource} recursively,
	 * provides the {@link List} of {@link IFile Acceleo documents} found.
	 * 
	 * @return the {@link List} of found {@link IFile Acceleo documents}.
	 */
	public List<IFile> getAcceleoDocuments() {
		List<IFile> foundAcceleoDocuments = this.acceleoDocuments;
		this.acceleoDocuments = null;
		return foundAcceleoDocuments;
	}
}
