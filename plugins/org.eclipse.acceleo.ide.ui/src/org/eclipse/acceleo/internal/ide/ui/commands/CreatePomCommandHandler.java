/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
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
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPom;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoPomDependency;
import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelFactory;
import org.eclipse.acceleo.internal.ide.ui.builders.runner.CreateRunnableAcceleoOperation;
import org.eclipse.acceleo.internal.ide.ui.generators.AcceleoUIGenerator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;

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
		AcceleoProject acceleoProject = new AcceleoProject(project);
		AcceleoPom acceleoPom = AcceleowizardmodelFactory.eINSTANCE.createAcceleoPom();
		acceleoPom.setArtifactId(project.getName());
		EList<AcceleoPomDependency> pomDependencies = acceleoPom.getDependencies();

		IPath eclipseWorkspace = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		IPath eclipseHome = new Path(Platform.getInstallLocation().getURL().getPath());

		IPath eclipsePathRelativeToFile = CreateRunnableAcceleoOperation.computeEclipsePath();

		Iterator<IPath> entries = acceleoProject.getResolvedClasspath().iterator();
		while (entries.hasNext()) {
			IPath iPath = entries.next();
			String classpathEntry = null;
			if (eclipseWorkspace.isPrefixOf(iPath)) {
				classpathEntry = iPath.toString().substring(eclipseWorkspace.toString().length());
			} else if (eclipseHome.isPrefixOf(iPath)) {
				classpathEntry = iPath.toString().substring(eclipseHome.toString().length());
			}

			if (classpathEntry == null) {
				continue;
			}

			AcceleoPomDependency acceleoPomDependency = AcceleowizardmodelFactory.eINSTANCE
					.createAcceleoPomDependency();

			String artifactId = classpathEntry;
			if (artifactId.contains("_")) { //$NON-NLS-1$
				artifactId = artifactId.substring(0, artifactId.indexOf('_'));
			}
			if (artifactId.startsWith("plugins/")) { //$NON-NLS-1$
				artifactId = artifactId.substring("plugins/".length()); //$NON-NLS-1$
			}
			acceleoPomDependency.setArtifactId(artifactId);
			acceleoPomDependency.setGroupId("eclipse"); //$NON-NLS-1$

			String version = classpathEntry;
			if (version.contains("_") && version.indexOf('_') <= version.length()) { //$NON-NLS-1$
				version = version.substring(version.indexOf('_') + 1);
			}
			if (version.endsWith(".jar")) { //$NON-NLS-1$
				version = version.substring(0, version.length() - ".jar".length()); //$NON-NLS-1$
			}
			acceleoPomDependency.setVersion(version);
			acceleoPomDependency.setSystemPath("${basedir}/" //$NON-NLS-1$
					+ AcceleoProject.makeRelativeTo(eclipsePathRelativeToFile, project.getLocation())
							.toString() + '/' + classpathEntry);
			pomDependencies.add(acceleoPomDependency);
		}
		AcceleoUIGenerator.getDefault().generatePom(acceleoPom, project);

		try {
			// Add the dependency to org.eclipse.acceleo.parser
			// IPluginModelBase plugin = PluginRegistry.findModel(project);
			// IPluginModelFactory factory = plugin.getPluginFactory();
			// IPluginImport importNode = factory.createImport();
			//importNode.setId("org.eclipse.acceleo.parser"); //$NON-NLS-1$
			// IPluginBase pluginBase = plugin.getPluginBase();
			// pluginBase.add(importNode);

			IJavaProject javaProject = JavaCore.create(project);
			IFolder sourceFolder = project.getFolder("src-acceleo-build"); //$NON-NLS-1$
			sourceFolder.create(false, true, null);
			IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(sourceFolder);
			IClasspathEntry[] oldEntries = javaProject.getRawClasspath();
			IClasspathEntry[] newEntries = new IClasspathEntry[oldEntries.length + 1];
			System.arraycopy(oldEntries, 0, newEntries, 0, oldEntries.length);
			newEntries[oldEntries.length] = JavaCore.newSourceEntry(root.getPath());
			javaProject.setRawClasspath(newEntries, null);

			StringTokenizer tokenizer = new StringTokenizer(project.getName(), "."); //$NON-NLS-1$
			while (tokenizer.hasMoreTokens()) {
				String nextToken = tokenizer.nextToken();
				sourceFolder = sourceFolder.getFolder(nextToken);
				if (!sourceFolder.exists()) {
					sourceFolder.create(true, true, new NullProgressMonitor());
				}
			}

			org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject acceleoModelProject = AcceleowizardmodelFactory.eINSTANCE
					.createAcceleoProject();
			acceleoModelProject.setName(project.getName());
			AcceleoUIGenerator.getDefault().generateAcceleoCompiler(acceleoModelProject, sourceFolder);
		} catch (CoreException e) {
			AcceleoUIActivator.log(e, true);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 */
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
