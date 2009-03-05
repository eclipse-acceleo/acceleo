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
package org.eclipse.acceleo.internal.ide.ui.launching;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.launching.strategy.IAcceleoLaunchingStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * To launch an Acceleo application as an Eclipse plug-in operation.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoLaunchOperation implements IWorkspaceRunnable {

	/**
	 * The EMTL model file URI. The generation module is the root object of this model.
	 */
	private URI moduleURI;

	/**
	 * The model URI.
	 */
	private URI modelURI;

	/**
	 * The target folder.
	 */
	private File targetFolder;

	/**
	 * The other arguments of the code generation.
	 */
	private List<? extends Object> args;

	/**
	 * The launching strategy. An internal extension point is defined to specify multiple launching
	 * strategies. It is used to define a specific way of launching an Acceleo generation.
	 */
	private IAcceleoLaunchingStrategy launchingStrategy;

	/**
	 * Constructor.
	 * 
	 * @param project
	 *            the project where the module is located.
	 * @param moduleJavaName
	 *            the module Java name (the first character may be in upper case)
	 * @param modelURI
	 *            the model URI
	 * @param targetFolder
	 *            the target folder
	 * @param args
	 *            the other arguments of the code generation
	 * @param launchingStrategy
	 *            the launching strategy used to define a specific way of launching an Acceleo generation
	 */
	public AcceleoLaunchOperation(IProject project, String moduleJavaName, URI modelURI, File targetFolder,
			List<? extends Object> args, IAcceleoLaunchingStrategy launchingStrategy) {
		super();
		this.moduleURI = createModuleURI(project, moduleJavaName);
		this.modelURI = modelURI;
		this.targetFolder = targetFolder;
		this.args = args;
		this.launchingStrategy = launchingStrategy;
	}

	/**
	 * Creates the module file URI (EMTL file) which has the given name in the project.
	 * 
	 * @param aProject
	 *            is the project that contains the EMTL file
	 * @param moduleJavaName
	 *            is the name of the module to search in the project
	 * @return the module file URI
	 */
	private URI createModuleURI(IProject aProject, String moduleJavaName) {
		IFolder outputFolder = getOutputFolder(aProject);
		if (outputFolder != null && moduleJavaName.length() > 0) {
			IPath modulePath = new Path(moduleJavaName.replace('.', '/'))
					.addFileExtension(IAcceleoConstants.EMTL_FILE_EXTENSION);
			IFolder container;
			if (modulePath.segmentCount() == 1) {
				container = outputFolder;
			} else {
				container = outputFolder.getFolder(modulePath.removeLastSegments(1));
			}
			if (container.exists()) {
				try {
					String moduleShortName = modulePath.lastSegment().toLowerCase();
					IResource[] members = container.members(IResource.FILE);
					for (int i = 0; i < members.length; i++) {
						if (members[i].getName().toLowerCase().equals(moduleShortName)) {
							modulePath = modulePath.removeLastSegments(1).append(members[i].getName());
							break;
						}
					}
				} catch (CoreException e) {
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				}
			}
			return URI.createPlatformResourceURI(outputFolder.getFile(modulePath).getFullPath().toString(),
					false);
		}
		return null;
	}

	/**
	 * Gets the Java output folder (/bin) of the given project.
	 * 
	 * @param aProject
	 *            is the project that contains EMTL files
	 * @return the Java output folder
	 */
	private IFolder getOutputFolder(IProject aProject) {
		final IJavaProject javaProject = JavaCore.create(aProject);
		try {
			IPath output = javaProject.getOutputLocation();
			if (output != null && output.segmentCount() > 1) {
				IFolder folder = aProject.getWorkspace().getRoot().getFolder(output);
				if (folder.isAccessible()) {
					return folder;
				}
			}
		} catch (JavaModelException e) {
			// continue
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor) throws CoreException {
		try {
			ResourceSet moduleResourceSet = new ResourceSetImpl();
			EObject module;
			module = ModelUtils.load(moduleURI, moduleResourceSet);
			ResourceSet modelResourceSet = new ResourceSetImpl();
			EObject model = ModelUtils.load(modelURI, modelResourceSet);
			if (module instanceof Module && model != null && targetFolder != null) {
				List<String> templateNames = new ArrayList<String>();
				computesMainTemplateNames(templateNames, module);
				if (launchingStrategy != null) {
					launchingStrategy.doGenerate((Module)module, templateNames, model, args, targetFolder);
				} else {
					for (int i = 0; i < templateNames.size(); i++) {
						AcceleoService.doGenerate((Module)module, templateNames.get(i), model, args,
								targetFolder, false);
					}
				}
			} else {
				String message = AcceleoUIMessages.getString("AcceleoLaunchOperation.BadConfiguration"); //$NON-NLS-1$
				Status status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, message);
				AcceleoUIActivator.getDefault().getLog().log(status);
			}
		} catch (IOException e) {
			Status status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		}
	}

	/**
	 * Gets all the templates that contain the main tag (@main).
	 * 
	 * @param mainTemplateNames
	 *            are the templates that contain the main tag (output parameter)
	 * @param eObject
	 *            is the object to browse
	 */
	private void computesMainTemplateNames(List<String> mainTemplateNames, EObject eObject) {
		if (eObject instanceof Template) {
			Template eTemplate = (Template)eObject;
			if (eTemplate.isMain() && !mainTemplateNames.contains(eTemplate.getName())) {
				mainTemplateNames.add(eTemplate.getName());
			}
		} else if (eObject != null) {
			Iterator<EObject> eContentsIt = eObject.eContents().iterator();
			while (eContentsIt.hasNext()) {
				EObject eContent = eContentsIt.next();
				computesMainTemplateNames(mainTemplateNames, eContent);
			}
		}
	}

}
