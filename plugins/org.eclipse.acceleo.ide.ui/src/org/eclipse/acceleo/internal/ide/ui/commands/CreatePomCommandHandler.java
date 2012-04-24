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
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IJavaProject;

/**
 * The handler of the command used to create the pom.xml file.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class CreatePomCommandHandler extends AbstractHandler {
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
		if (applicationContext instanceof EvaluationContext) {
			EvaluationContext context = (EvaluationContext)applicationContext;
			Object defaultVariable = context.getDefaultVariable();
			if (defaultVariable instanceof List) {
				List<Object> variables = (List<Object>)defaultVariable;
				List<IProject> projects = new ArrayList<IProject>();
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

				for (IProject iProject : projects) {
					try {
						if (iProject.isAccessible()
								&& iProject.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
							generatePom(iProject);
						}
					} catch (CoreException e) {
						AcceleoUIActivator.log(e, true);
					}
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
		final String lr = System.getProperty("line.separator"); //$NON-NLS-1$
		AcceleoPom acceleoPom = AcceleowizardmodelFactory.eINSTANCE.createAcceleoPom();
		acceleoPom.setArtifactId(project.getName());

		// Compute the dependencies that need to be built

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
						("bin.includes = feature.xml" + lr).getBytes()); //$NON-NLS-1$
				buildProperties.create(inputStream, true, monitor);

				// Creation of the file feature.xml
				IFile featureXML = featureProject.getFile("feature.xml"); //$NON-NLS-1$
				StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + lr); //$NON-NLS-1$
				buffer.append("<feature" + lr); //$NON-NLS-1$
				buffer.append("      id=\"" + project.getName() + ".feature\"" + lr); //$NON-NLS-1$ //$NON-NLS-2$
				buffer.append("      label=\"Feature\"" + lr); //$NON-NLS-1$
				buffer.append("      version=\"1.0.0.qualifier\">" + lr); //$NON-NLS-1$
				buffer.append(lr);
				buffer.append("   <description url=\"http://www.example.com/description\">" + lr); //$NON-NLS-1$
				buffer.append("      [Enter Feature Description here.]" + lr); //$NON-NLS-1$
				buffer.append("   </description>" + lr); //$NON-NLS-1$
				buffer.append(lr);
				buffer.append("   <copyright url=\"http://www.example.com/copyright\">" + lr); //$NON-NLS-1$
				buffer.append("      [Enter Copyright Description here.]" + lr); //$NON-NLS-1$
				buffer.append("   </copyright>" + lr); //$NON-NLS-1$
				buffer.append(lr);
				buffer.append("   <license url=\"http://www.example.com/license\">" + lr); //$NON-NLS-1$
				buffer.append("      [Enter License Description here.]" + lr); //$NON-NLS-1$
				buffer.append("   </license>" + lr); //$NON-NLS-1$
				buffer.append(lr);
				buffer.append("   <plugin" + lr); //$NON-NLS-1$
				buffer.append("         id=\"" + project.getName() + "\"" + lr); //$NON-NLS-1$ //$NON-NLS-2$
				buffer.append("         download-size=\"0\"" + lr); //$NON-NLS-1$
				buffer.append("         install-size=\"0\"" + lr); //$NON-NLS-1$
				buffer.append("         version=\"0.0.0\"" + lr); //$NON-NLS-1$
				buffer.append("         unpack=\"false\"/>" + lr); //$NON-NLS-1$
				buffer.append(lr);
				buffer.append("</feature>" + lr); //$NON-NLS-1$
				inputStream = new ByteArrayInputStream(buffer.toString().getBytes());
				featureXML.create(inputStream, true, monitor);

				AcceleoUIGenerator.getDefault().generatePomFeature(acceleoPom, featureProject,
						project.getName() + ".parent"); //$NON-NLS-1$
			} catch (CoreException e) {
				AcceleoUIActivator.log(e, true);
			}

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
					StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + lr); //$NON-NLS-1$
					buffer.append("<site>" + lr); //$NON-NLS-1$
					buffer.append("   <feature url=\"features/" + project.getName() //$NON-NLS-1$
							+ ".feature_1.0.0.qualifier.jar\" id=\"" + project.getName() //$NON-NLS-1$
							+ ".feature\" version=\"1.0.0.qualifier\">" + lr); //$NON-NLS-1$
					buffer.append("      <category name=\"" + project.getName() + ".category.id\"/>" + lr); //$NON-NLS-1$//$NON-NLS-2$
					buffer.append("   </feature>" + lr); //$NON-NLS-1$
					buffer.append("   <category-def name=\"" + project.getName() //$NON-NLS-1$
							+ ".category.id\" label=\"Acceleo\"/>" + lr); //$NON-NLS-1$
					buffer.append("</site>" + lr); //$NON-NLS-1$
					ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer.toString().getBytes());
					categoryXML.create(inputStream, true, monitor);

					IFile siteXML = updateSiteProject.getFile("site.xml"); //$NON-NLS-1$
					buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + lr); //$NON-NLS-1$
					buffer.append("<site>" + lr); //$NON-NLS-1$
					buffer.append("</site>" + lr); //$NON-NLS-1$
					siteXML.create(new ByteArrayInputStream(buffer.toString().getBytes()), true, monitor);

					AcceleoUIGenerator.getDefault().generatePomUpdateSite(acceleoPom, updateSiteProject,
							project.getName() + ".parent"); //$NON-NLS-1$
				} catch (CoreException e) {
					AcceleoUIActivator.log(e, true);
				}
			}

			// Creation of the parent project
			IProject parentProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
					project.getName() + ".parent"); //$NON-NLS-1$
			if (!parentProject.exists()) {
				try {
					parentProject.create(monitor);
					parentProject.open(monitor);

					// Generates parent pom.xml
					AcceleoUIGenerator.getDefault().generatePom(acceleoPom, parentProject,
							project.getName() + ".parent"); //$NON-NLS-1$

				} catch (CoreException e) {
					AcceleoUIActivator.log(e, true);
				}
			}
		}
		// Generates regular pom.xml
		AcceleoUIGenerator.getDefault().generatePomChild(acceleoPom, project, project.getName() + ".parent"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	@Override
	public void setEnabled(Object evaluationContext) {
		if (evaluationContext instanceof EvaluationContext) {
			EvaluationContext context = (EvaluationContext)evaluationContext;
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
			}
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
