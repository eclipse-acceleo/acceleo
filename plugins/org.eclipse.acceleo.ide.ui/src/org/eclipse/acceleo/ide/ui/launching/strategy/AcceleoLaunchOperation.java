/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ide.ui.launching.strategy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.engine.service.AbstractAcceleoGenerator;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;

/**
 * To launch an Acceleo application as an Eclipse plug-in operation.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 * @since 3.0
 */
public class AcceleoLaunchOperation implements IWorkspaceRunnable {
	/**
	 * The project that contains the Generator that's to be launched.
	 */
	private final IProject project;

	/**
	 * Qualified name of the class that's to be launched.
	 */
	private final String qualifiedName;

	/**
	 * The model URI.
	 */
	private String model;

	/**
	 * The target folder.
	 */
	private File targetFolder;

	/**
	 * The other arguments of the code generation.
	 */
	private List<String> args;

	/**
	 * Constructor.
	 * 
	 * @param project
	 *            the project where the module is located.
	 * @param qualifiedName
	 *            the module Java name (the first character may be in upper case)
	 * @param model
	 *            the model
	 * @param targetFolder
	 *            the target folder
	 * @param args
	 *            the other arguments of the code generation
	 */
	public AcceleoLaunchOperation(IProject project, String qualifiedName, String model, File targetFolder,
			List<String> args) {
		super();
		this.project = project;
		this.qualifiedName = qualifiedName;
		this.model = model;
		this.targetFolder = targetFolder;
		this.args = args;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
	 */

	public void run(IProgressMonitor monitor) throws CoreException {
		AcceleoWorkspaceUtil.INSTANCE.addWorkspaceContribution(project);
		final Class<?> generatorClass = AcceleoWorkspaceUtil.INSTANCE.getClass(qualifiedName, false);

		if (generatorClass == null) {
			final IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, AcceleoUIMessages
					.getString("AcceleoLaunchOperation.ClassNotFound", qualifiedName, //$NON-NLS-1$
							project.getName()));
			AcceleoUIActivator.getDefault().getLog().log(status);
			return;
		}

		try {
			AbstractAcceleoGenerator generator = null;
			if (AbstractAcceleoGenerator.class.isAssignableFrom(generatorClass)) {
				generator = safeInstantiate(generatorClass);
			}

			if (generator != null) {
				URI modelURI = URI.createFileURI(ResourcesPlugin.getWorkspace().getRoot().getLocation()
						.append(model).toString());
				modelURI = URI.createURI(URI.decode(modelURI.toString()));
				generator.initialize(modelURI, targetFolder, args);
				generator.doGenerate(BasicMonitor.toMonitor(monitor));
			} else {
				// We know the generated class has a "main()" method.
				final Method main = generatorClass.getDeclaredMethod("main", String[].class); //$NON-NLS-1$
				final String[] invocationArgs = new String[2 + args.size()];
				invocationArgs[0] = ResourcesPlugin.getWorkspace().getRoot().getLocation().append(model)
						.toString();
				invocationArgs[1] = targetFolder.getAbsolutePath();
				for (int i = 0; i < args.size(); i++) {
					invocationArgs[i + 2] = args.get(i);

				}
				main.invoke(null, new Object[] {invocationArgs, });
			}
		} catch (NoSuchMethodException e) {
			final IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		} catch (IllegalArgumentException e) {
			final IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		} catch (IllegalAccessException e) {
			final IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		} catch (InvocationTargetException e) {
			final IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		} catch (IOException e) {
			final IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		} finally {
			AcceleoWorkspaceUtil.INSTANCE.reset();
		}
	}

	/**
	 * Tries and create the {@link AbstractAcceleoGenerator} instance.
	 * 
	 * @param generatorClass
	 *            The class to instantiate.
	 * @return The create instance, <code>null</code> if we couldn't instantiate it.
	 */
	protected AbstractAcceleoGenerator safeInstantiate(Class<?> generatorClass) {
		AbstractAcceleoGenerator generator = null;
		try {
			generator = (AbstractAcceleoGenerator)generatorClass.newInstance();
		} catch (InstantiationException e) {
			final IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		} catch (IllegalAccessException e) {
			final IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		}

		return generator;
	}
}
