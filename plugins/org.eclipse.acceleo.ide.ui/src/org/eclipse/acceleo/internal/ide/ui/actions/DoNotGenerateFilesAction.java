/*******************************************************************************
 * Copyright (c) 2008, 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.actions;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * This action will generate a file that will contain the paths of the resources to ignore for future
 * generations.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class DoNotGenerateFilesAction implements IObjectActionDelegate {

	/**
	 * The current selection.
	 */
	private IStructuredSelection structuredSelection;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		List<IResource> resources = new ArrayList<IResource>();

		// Compute all the resources that are selected
		Iterator<?> iterator = this.structuredSelection.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof IResource) {
				resources.add((IResource)next);
			}
		}

		final List<IFile> files = new ArrayList<IFile>();

		// Find all the files that they contain
		for (IResource iResource : resources) {
			if (iResource instanceof IFile) {
				files.add((IFile)iResource);
			} else if (iResource instanceof IContainer) {
				IContainer iContainer = (IContainer)iResource;
				try {
					iContainer.accept(new IResourceVisitor() {

						public boolean visit(IResource resource) throws CoreException {
							if (resource instanceof IFile) {
								files.add((IFile)resource);
							}
							return true;
						}
					});
				} catch (CoreException e) {
					AcceleoUIActivator.log(e, true);
				}
			}
		}

		final Multimap<IProject, IFile> projects2files = ArrayListMultimap.create();
		for (IFile iFile : files) {
			projects2files.put(iFile.getProject(), iFile);
		}

		IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				Set<IProject> projects = projects2files.keySet();
				for (IProject iProject : projects) {
					StringBuffer buffer = new StringBuffer();
					Collection<IFile> filesNotToGenerate = projects2files.get(iProject);
					for (IFile fileNotToGenerate : filesNotToGenerate) {
						buffer.append(fileNotToGenerate.getFullPath() + System.getProperty("line.separator")); //$NON-NLS-1$
					}
					try {
						IFile doNotGenerateFile = iProject
								.getFile(IAcceleoConstants.DO_NOT_GENERATE_FILENAME);
						if (doNotGenerateFile.exists()) {
							doNotGenerateFile.setContents(new ByteArrayInputStream(buffer.toString()
									.getBytes()), true, true, monitor);
						} else {
							doNotGenerateFile.create(new ByteArrayInputStream(buffer.toString().getBytes()),
									true, monitor);
						}
					} catch (CoreException e) {
						AcceleoUIActivator.log(e, true);
					}
				}
			}
		};

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(true, true, runnableWithProgress);
		} catch (InvocationTargetException e) {
			AcceleoUIActivator.log(e, true);
		} catch (InterruptedException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.structuredSelection = (IStructuredSelection)selection;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// do nothing

	}

}
