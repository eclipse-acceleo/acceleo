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
package org.eclipse.acceleo.internal.ide.ui.natures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarkerUtils;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;

/**
 * The action to toggle (add/remove) the Acceleo nature.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoToggleNatureAction extends AbstractHandler {

	/**
	 * The current selection.
	 */
	private ISelection selection;

	/**
	 * Indicating if the action is enabled.
	 */
	private boolean enabled;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@SuppressWarnings("unchecked")
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Object applicationContext = null;
		if (event != null) {
			applicationContext = event.getApplicationContext();
		}
		List<IProject> projects = new ArrayList<IProject>();
		if (applicationContext instanceof IEvaluationContext) {
			IEvaluationContext context = (IEvaluationContext)applicationContext;
			Object defaultVariable = context.getDefaultVariable();
			if (defaultVariable instanceof List) {
				List<Object> variables = (List<Object>)defaultVariable;
				for (Object object : variables) {
					if (object instanceof IProject) {
						IProject project = (IProject)object;
						projects.add(project);
					} else if (object instanceof JavaProject) {
						JavaProject javaProject = (JavaProject)object;
						projects.add(javaProject.getProject());
					} else if (Platform.getAdapterManager().getAdapter(object, IProject.class) instanceof IProject) {
						projects.add((IProject)Platform.getAdapterManager()
								.getAdapter(object, IProject.class));
					}
				}
			} else if (defaultVariable instanceof TreeSelection
					&& ((TreeSelection)defaultVariable).size() > 0) {
				TreeSelection tSelection = (TreeSelection)defaultVariable;
				List<?> list = tSelection.toList();
				for (Object object : list) {
					if (object instanceof IProject) {
						IProject project = (IProject)object;
						projects.add(project);
					} else if (object instanceof JavaProject) {
						JavaProject javaProject = (JavaProject)object;
						projects.add(javaProject.getProject());
					} else if (Platform.getAdapterManager().getAdapter(object, IProject.class) instanceof IProject) {
						projects.add((IProject)Platform.getAdapterManager()
								.getAdapter(object, IProject.class));
					}
				}
			}
		}

		if (!projects.isEmpty()) {
			selection = new StructuredSelection(projects);
		}

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
		return null;
	}

	/**
	 * Sets the selection.
	 * 
	 * @param s
	 *            The new selection.
	 */
	public void setSelection(ISelection s) {
		this.selection = s;
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
				if (IAcceleoConstants.ACCELEO_NATURE_ID.equals(natures[i])) {
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
							file.deleteMarkers(AcceleoMarkerUtils.PROBLEM_MARKER_ID, false,
									IResource.DEPTH_ZERO);
							file.deleteMarkers(AcceleoMarkerUtils.WARNING_MARKER_ID, false,
									IResource.DEPTH_ZERO);
							file.deleteMarkers(AcceleoMarkerUtils.INFO_MARKER_ID, false, IResource.DEPTH_ZERO);
						} catch (CoreException e) {
							AcceleoUIActivator.getDefault().getLog()
									.log(new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e
											.getMessage(), e));
						}
					}
					return;
				}
			}
			// Add the nature
			String[] newNatures = new String[natures.length + 2];
			System.arraycopy(natures, 0, newNatures, 2, natures.length);

			newNatures[0] = "org.eclipse.pde.PluginNature"; //$NON-NLS-1$
			newNatures[1] = IAcceleoConstants.ACCELEO_NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);

			// Override the ".project" anyway
			AcceleoProject acceleoProject = AcceleowizardmodelFactory.eINSTANCE.createAcceleoProject();
			acceleoProject.setName(project.getName());
			acceleoProject.setJre("J2SE-1.5"); //$NON-NLS-1$
			AcceleoUIGenerator.getDefault().generateDotProject(acceleoProject, project);

			IFile buildProperties = project.getFile("build.properties"); //$NON-NLS-1$
			if (!buildProperties.exists()) {
				AcceleoUIGenerator.getDefault().generateBuildProperties(acceleoProject, project);
			}

			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
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

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		if (evaluationContext instanceof IEvaluationContext) {
			IEvaluationContext context = (IEvaluationContext)evaluationContext;
			Object defaultVariable = context.getDefaultVariable();
			if (defaultVariable instanceof List && ((List)defaultVariable).size() > 0) {
				List<Object> variables = (List<Object>)defaultVariable;
				for (Object object : variables) {
					if (object instanceof IProject) {
						enabled = true;
					} else if (object instanceof JavaProject) {
						enabled = true;
					} else if (Platform.getAdapterManager().getAdapter(object, IProject.class) instanceof IProject) {
						enabled = true;
					}
				}
			} else if (defaultVariable instanceof TreeSelection
					&& ((TreeSelection)defaultVariable).size() > 0) {
				TreeSelection tSelection = (TreeSelection)defaultVariable;
				List<?> list = tSelection.toList();
				for (Object object : list) {
					if (object instanceof IProject) {
						enabled = true;
					} else if (object instanceof IJavaProject) {
						enabled = true;
					} else if (Platform.getAdapterManager().getAdapter(object, IProject.class) instanceof IProject) {
						enabled = true;
					}
				}
			}
		} else {
			this.enabled = true;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
