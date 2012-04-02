/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.workspace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.util.IClassFileReader;

/**
 * This will be used to process the given resource delta and retrieve from it the list of changed class files.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
final class AcceleoDeltaVisitor implements IResourceDeltaVisitor {
	/** Keeps the list of the changed classes' qualified name. */
	private List<String> changedClasses = new ArrayList<String>();

	/**
	 * This will be populated with all projects which class files have been changed (even if only partially).
	 */
	private List<IProject> changedProjects = new ArrayList<IProject>();

	/**
	 * Returns the qualified names of all changed class files.
	 * 
	 * @return The qualified names of all changed class files.
	 */
	public List<String> getChangedClasses() {
		return changedClasses;
	}

	/**
	 * Returns the list of all projects which class files have been modified.
	 * 
	 * @return The list of all projects which class files have been modified.
	 */
	public List<IProject> getChangedProjects() {
		return changedProjects;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
	 */
	public boolean visit(IResourceDelta delta) throws CoreException {
		if (delta == null || 0 == (delta.getKind() & IResourceDelta.CHANGED)) {
			return false;
		}
		boolean visit = false;
		IResource resource = delta.getResource();
		if (resource != null) {
			switch (resource.getType()) {
				case IResource.FILE:
					if (0 == (delta.getFlags() & IResourceDelta.CONTENT)) {
						break;
					}
					if ("class".equals(resource.getFullPath().getFileExtension())) { //$NON-NLS-1$
						final IClassFileReader reader = ToolFactory.createDefaultClassFileReader(resource
								.getLocation().toOSString(), IClassFileReader.CLASSFILE_ATTRIBUTES);
						changedClasses.add(new String(reader.getClassName()).replace("/", ".")); //$NON-NLS-1$ //$NON-NLS-2$
					}
					break;
				case IResource.PROJECT:
					changedProjects.add((IProject)resource);
					visit = true;
					break;
				default:
					visit = true;
			}
		}
		return visit;
	}
}
