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
package org.eclipse.acceleo.internal.ide.ui.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoMainClass;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.builders.runner.CreateRunnableAcceleoOperation;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.TreeSelection;

/**
 * The handler of the command used to create the buildstandalone.xml file.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class CreateAntCommandHandler extends AbstractHandler {
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
		Object applicationContext = event.getApplicationContext();
		if (applicationContext instanceof IEvaluationContext) {
			IEvaluationContext context = (IEvaluationContext)applicationContext;
			Object defaultVariable = context.getDefaultVariable();
			List<IProject> projects = new ArrayList<IProject>();
			if (defaultVariable instanceof List) {
				List<Object> variables = (List<Object>)defaultVariable;
				for (Object object : variables) {
					if (object instanceof IProject) {
						IProject project = (IProject)object;
						projects.add(project);
					} else if (object instanceof IJavaProject) {
						IJavaProject javaProject = (IJavaProject)object;
						projects.add(javaProject.getProject());
					} else if (Platform.getAdapterManager().getAdapter(object, IProject.class) instanceof IProject) {
						projects.add((IProject)Platform.getAdapterManager()
								.getAdapter(object, IProject.class));
					}
				}
			} else if (defaultVariable instanceof TreeSelection
					&& ((TreeSelection)defaultVariable).size() > 0) {
				TreeSelection selection = (TreeSelection)defaultVariable;
				List<?> list = selection.toList();
				for (Object object : list) {
					if (object instanceof IProject) {
						IProject project = (IProject)object;
						projects.add(project);
					} else if (object instanceof IJavaProject) {
						IJavaProject javaProject = (IJavaProject)object;
						projects.add(javaProject.getProject());
					} else if (Platform.getAdapterManager().getAdapter(object, IProject.class) instanceof IProject) {
						projects.add((IProject)Platform.getAdapterManager()
								.getAdapter(object, IProject.class));
					}
				}
			}

			for (IProject iProject : projects) {
				try {
					if (iProject.isAccessible() && iProject.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
						generateAnt(iProject);
					}
				} catch (CoreException e) {
					AcceleoUIActivator.log(e, true);
				}
			}
		}
		return applicationContext;
	}

	/**
	 * Generates the buildstandalone.xml file in the given project.
	 * 
	 * @param iProject
	 *            The project.
	 */
	private void generateAnt(IProject iProject) {
		AcceleoProject acceleoProject = new AcceleoProject(iProject);
		List<String> resolvedClasspath = new ArrayList<String>();
		Iterator<IPath> entries = acceleoProject.getResolvedClasspath().iterator();
		IPath eclipseWorkspace = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		IPath eclipseHome = new Path(Platform.getInstallLocation().getURL().getPath());
		while (entries.hasNext()) {
			IPath path = entries.next();
			if (eclipseWorkspace.isPrefixOf(path)) {
				resolvedClasspath.add("${ECLIPSE_WORKSPACE}/" //$NON-NLS-1$
						+ path.toString().substring(eclipseWorkspace.toString().length()));
			} else if (eclipseHome.isPrefixOf(path)) {
				resolvedClasspath.add("${ECLIPSE_HOME}/" //$NON-NLS-1$
						+ path.toString().substring(eclipseHome.toString().length()));
			}
		}

		AcceleoMainClass acceleoMainClass = AcceleowizardmodelFactory.eINSTANCE.createAcceleoMainClass();
		acceleoMainClass.setProjectName(iProject.getName());
		List<String> classPath = acceleoMainClass.getResolvedClassPath();
		classPath.addAll(resolvedClasspath);

		IPath workspacePathRelativeToFile = CreateRunnableAcceleoOperation.computeWorkspacePath();
		IPath eclipsePathRelativeToFile = CreateRunnableAcceleoOperation.computeEclipsePath();

		AcceleoUIGenerator.getDefault()
				.generateBuildXML(
						acceleoMainClass,
						AcceleoProject.makeRelativeTo(eclipsePathRelativeToFile, iProject.getLocation())
								.toString(),
						AcceleoProject.makeRelativeTo(workspacePathRelativeToFile, iProject.getLocation())
								.toString(), iProject);
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
					try {
						if (object instanceof IProject) {
							IProject project = (IProject)object;
							enabled = project.isAccessible()
									&& project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID);
						} else if (object instanceof IJavaProject) {
							IProject project = ((IJavaProject)object).getProject();
							enabled = project.isAccessible()
									&& project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID);
						} else if (Platform.getAdapterManager().getAdapter(object, IProject.class) instanceof IProject) {
							IProject project = (IProject)Platform.getAdapterManager().getAdapter(object,
									IProject.class);
							enabled = project.isAccessible()
									&& project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID);
						}
					} catch (CoreException e) {
						AcceleoUIActivator.log(e, true);
					}
				}
			} else if (defaultVariable instanceof TreeSelection
					&& ((TreeSelection)defaultVariable).size() > 0) {
				TreeSelection selection = (TreeSelection)defaultVariable;
				List<?> list = selection.toList();
				for (Object object : list) {
					try {
						if (object instanceof IProject) {
							IProject project = (IProject)object;
							enabled = project.isAccessible()
									&& project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID);
						} else if (object instanceof IJavaProject) {
							IProject project = ((IJavaProject)object).getProject();
							enabled = project.isAccessible()
									&& project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID);
						} else if (Platform.getAdapterManager().getAdapter(object, IProject.class) instanceof IProject) {
							IProject project = (IProject)Platform.getAdapterManager().getAdapter(object,
									IProject.class);
							enabled = project.isAccessible()
									&& project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID);
						}
					} catch (CoreException e) {
						AcceleoUIActivator.log(e, true);
					}
				}
			}
		} else {
			// Eclipse 4, let's consider that it's true.
			enabled = true;
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
