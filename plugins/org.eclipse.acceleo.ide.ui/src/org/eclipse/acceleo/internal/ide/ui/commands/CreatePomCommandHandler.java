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

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.TreeSelection;

/**
 * The handler of the command used to create the pom.xml file.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class CreatePomCommandHandler extends AbstractHandler {
	/**
	 * The parent suffix.
	 */
	private static final String PARENT_SUFFIX = ".parent"; //$NON-NLS-1$

	/**
	 * The line separator.
	 */
	private static final String LS = System.getProperty("line.separator"); //$NON-NLS-1$

	/**
	 * The XML header.
	 */
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; //$NON-NLS-1$

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
						generatePom(iProject);
					}
				} catch (CoreException e) {
					AcceleoUIActivator.log(e, true);
				}
			}
		}
		return applicationContext;
	}

	/**
	 * Generates the pom.xml file for the given project.
	 * 
	 * @param project
	 *            The project.
	 */
	private void generatePom(IProject project) {
		IProgressMonitor monitor = new NullProgressMonitor();

		AcceleoPom acceleoPom = AcceleowizardmodelFactory.eINSTANCE.createAcceleoPom();
		acceleoPom.setArtifactId(project.getName());

		boolean areNotificationsForcedDisabled = AcceleoPreferences.areNotificationsForcedDisabled();
		if (!areNotificationsForcedDisabled) {
			AcceleoPreferences.switchForceDeactivationNotifications(true);
		}
		this.generateFeatureProject(project, acceleoPom, monitor);
		this.generateUpdateSiteProject(project, acceleoPom, monitor);
		this.generateParentProject(project, acceleoPom, monitor);

		// Generates regular pom.xml
		AcceleoUIGenerator.getDefault().generatePomChild(acceleoPom, project,
				project.getName() + PARENT_SUFFIX);

		AcceleoPreferences.switchForceDeactivationNotifications(areNotificationsForcedDisabled);
	}

	/**
	 * Generate the feature project.
	 * 
	 * @param project
	 *            The project
	 * @param acceleoPom
	 *            The pom.xml
	 * @param monitor
	 *            The progress monitor
	 */
	private void generateFeatureProject(IProject project, AcceleoPom acceleoPom, IProgressMonitor monitor) {
		// Generate feature project
		IProject featureProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
				project.getName() + ".feature"); //$NON-NLS-1$
		if (!featureProject.exists()) {
			try {
				// Creation of the project
				featureProject.create(monitor);
				featureProject.open(monitor);
				IProjectDescription description = featureProject.getDescription();
				String[] oldNatureIds = description.getNatureIds();
				String[] newNatureIds = new String[oldNatureIds.length + 1];
				System.arraycopy(oldNatureIds, 0, newNatureIds, 0, oldNatureIds.length);
				newNatureIds[oldNatureIds.length] = "org.eclipse.pde.FeatureNature"; //$NON-NLS-1$
				description.setNatureIds(newNatureIds);
				featureProject.setDescription(description, monitor);

				// Creation of the file build.properties
				IFile buildProperties = featureProject.getFile("build.properties"); //$NON-NLS-1$
				ByteArrayInputStream inputStream = new ByteArrayInputStream(
						("bin.includes = feature.xml" + LS).getBytes()); //$NON-NLS-1$
				buildProperties.create(inputStream, true, monitor);

				// Creation of the file feature.xml
				IFile featureXML = featureProject.getFile("feature.xml"); //$NON-NLS-1$
				StringBuffer buffer = new StringBuffer(XML_HEADER + LS);
				buffer.append("<feature" + LS); //$NON-NLS-1$
				buffer.append("      id=\"" + project.getName() + ".feature\"" + LS); //$NON-NLS-1$ //$NON-NLS-2$
				buffer.append("      label=\"Feature\"" + LS); //$NON-NLS-1$
				buffer.append("      version=\"1.0.0.qualifier\">" + LS); //$NON-NLS-1$
				buffer.append(LS);
				buffer.append("   <description url=\"http://www.example.com/description\">" + LS); //$NON-NLS-1$
				buffer.append("      [Enter Feature Description here.]" + LS); //$NON-NLS-1$
				buffer.append("   </description>" + LS); //$NON-NLS-1$
				buffer.append(LS);
				buffer.append("   <copyright url=\"http://www.example.com/copyright\">" + LS); //$NON-NLS-1$
				buffer.append("      [Enter Copyright Description here.]" + LS); //$NON-NLS-1$
				buffer.append("   </copyright>" + LS); //$NON-NLS-1$
				buffer.append(LS);
				buffer.append("   <license url=\"http://www.example.com/license\">" + LS); //$NON-NLS-1$
				buffer.append("      [Enter License Description here.]" + LS); //$NON-NLS-1$
				buffer.append("   </license>" + LS); //$NON-NLS-1$
				buffer.append(LS);
				buffer.append("   <plugin" + LS); //$NON-NLS-1$
				buffer.append("         id=\"" + project.getName() + "\"" + LS); //$NON-NLS-1$ //$NON-NLS-2$
				buffer.append("         download-size=\"0\"" + LS); //$NON-NLS-1$
				buffer.append("         install-size=\"0\"" + LS); //$NON-NLS-1$
				buffer.append("         version=\"0.0.0\"" + LS); //$NON-NLS-1$
				buffer.append("         unpack=\"false\"/>" + LS); //$NON-NLS-1$
				buffer.append(LS);
				buffer.append("</feature>" + LS); //$NON-NLS-1$
				inputStream = new ByteArrayInputStream(buffer.toString().getBytes());
				featureXML.create(inputStream, true, monitor);

				AcceleoUIGenerator.getDefault().generatePomFeature(acceleoPom, featureProject,
						project.getName() + PARENT_SUFFIX);
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
			}

		}
	}

	/**
	 * Generates the update site project.
	 * 
	 * @param project
	 *            The project.
	 * @param acceleoPom
	 *            The pom.xml.
	 * @param monitor
	 *            the progress monitor.
	 */
	private void generateUpdateSiteProject(IProject project, AcceleoPom acceleoPom, IProgressMonitor monitor) {
		// Generate update site project
		IProject updateSiteProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
				project.getName() + ".updatesite"); //$NON-NLS-1$
		if (!updateSiteProject.exists()) {
			try {
				// Creation of the project
				updateSiteProject.create(monitor);
				updateSiteProject.open(monitor);
				IProjectDescription description = updateSiteProject.getDescription();
				String[] oldNatureIds = description.getNatureIds();
				String[] newNatureIds = new String[oldNatureIds.length + 1];
				System.arraycopy(oldNatureIds, 0, newNatureIds, 0, oldNatureIds.length);
				newNatureIds[oldNatureIds.length] = "org.eclipse.pde.UpdateSiteNature"; //$NON-NLS-1$
				description.setNatureIds(newNatureIds);
				updateSiteProject.setDescription(description, monitor);

				// Creation of the file category.xml
				IFile categoryXML = updateSiteProject.getFile("category.xml"); //$NON-NLS-1$
				StringBuffer buffer = new StringBuffer(XML_HEADER + LS);
				buffer.append("<site>" + LS); //$NON-NLS-1$
				buffer.append("   <feature url=\"features/" + project.getName() //$NON-NLS-1$
						+ ".feature_1.0.0.qualifier.jar\" id=\"" + project.getName() //$NON-NLS-1$
						+ ".feature\" version=\"1.0.0.qualifier\">" + LS); //$NON-NLS-1$
				buffer.append("      <category name=\"" + project.getName() + ".category.id\"/>" + LS); //$NON-NLS-1$//$NON-NLS-2$
				buffer.append("   </feature>" + LS); //$NON-NLS-1$
				buffer.append("   <category-def name=\"" + project.getName() //$NON-NLS-1$
						+ ".category.id\" label=\"Acceleo\"/>" + LS); //$NON-NLS-1$
				buffer.append("</site>" + LS); //$NON-NLS-1$
				ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer.toString().getBytes());
				categoryXML.create(inputStream, true, monitor);

				IFile siteXML = updateSiteProject.getFile("site.xml"); //$NON-NLS-1$
				buffer = new StringBuffer(XML_HEADER + LS);
				buffer.append("<site>" + LS); //$NON-NLS-1$
				buffer.append("</site>" + LS); //$NON-NLS-1$
				siteXML.create(new ByteArrayInputStream(buffer.toString().getBytes()), true, monitor);

				AcceleoUIGenerator.getDefault().generatePomUpdateSite(acceleoPom, updateSiteProject,
						project.getName() + PARENT_SUFFIX);
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
			}
		}
	}

	/**
	 * Generates the parent project with its pom.xml file.
	 * 
	 * @param project
	 *            The project
	 * @param acceleoPom
	 *            The pom.xml file
	 * @param monitor
	 *            The progress monitor
	 */
	private void generateParentProject(IProject project, AcceleoPom acceleoPom, IProgressMonitor monitor) {
		// Creation of the parent project
		IProject parentProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
				project.getName() + PARENT_SUFFIX);
		if (!parentProject.exists()) {
			try {
				parentProject.create(monitor);
				parentProject.open(monitor);

				// Generates parent pom.xml
				AcceleoUIGenerator.getDefault().generatePom(acceleoPom, parentProject,
						project.getName() + PARENT_SUFFIX);

			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
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
