/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.natures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.natures.AcceleoNature;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarker;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * The action to toggle (add/remove) the Acceleo nature.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoToggleNatureAction implements IObjectActionDelegate {

	/**
	 * The current selection.
	 */
	private ISelection selection;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		if (selection instanceof IStructuredSelection) {
			for (Iterator<?> it = ((IStructuredSelection)selection).iterator(); it.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject)element;
				} else if (element instanceof IAdaptable) {
					project = (IProject)((IAdaptable)element).getAdapter(IProject.class);
				}
				if (project != null) {
					toggleNature(project);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection aSelection) {
		this.selection = aSelection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * Toggles sample nature on a project.
	 * 
	 * @param project
	 *            to have sample nature added or removed
	 */
	private void toggleNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();
			for (int i = 0; i < natures.length; ++i) {
				if (AcceleoNature.NATURE_ID.equals(natures[i])) {
					// Remove the nature
					String[] newNatures = new String[natures.length - 1];
					System.arraycopy(natures, 0, newNatures, 0, i);
					System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					List<IFile> files = new ArrayList<IFile>();
					members(files, project);
					for (Iterator<IFile> itFiles = files.iterator(); itFiles.hasNext();) {
						IFile file = itFiles.next();
						try {
							file.deleteMarkers(AcceleoMarker.PROBLEM_MARKER, false, IResource.DEPTH_ZERO);
						} catch (CoreException e) {
							AcceleoUIActivator.getDefault().getLog()
									.log(
											new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e
													.getMessage(), e));
						}
					}
					return;
				}
			}
			// Add the nature
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 1, natures.length);
			newNatures[0] = AcceleoNature.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		}
	}

	/**
	 * Computes a list of existing member files (Acceleo files only) in a container.
	 * 
	 * @param files
	 *            an output parameter to get all the Acceleo files
	 * @param container
	 *            is the container to browse
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void members(List<IFile> files, IContainer container) throws CoreException {
		if (container != null) {
			IResource[] children = container.members();
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile
							&& IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)resource)
									.getFileExtension())) {
						files.add((IFile)resource);
					} else if (resource instanceof IContainer) {
						members(files, (IContainer)resource);
					}
				}
			}
		}
	}

}
