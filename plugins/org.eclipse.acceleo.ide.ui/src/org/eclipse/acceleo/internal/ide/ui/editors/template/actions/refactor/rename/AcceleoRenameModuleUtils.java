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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import java.io.IOException;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * Utility class needed to perform the refactoring of a module.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class AcceleoRenameModuleUtils {

	/**
	 * Slash.
	 */
	private static final String SLASH = "/"; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	private AcceleoRenameModuleUtils() {
		// hides constructor.
	}

	/**
	 * Gets the output folder of the project. For example : '/MyProject/bin'.
	 * 
	 * @param aProject
	 *            is a project of the workspace
	 * @return the output folder of the project, or null if it doesn't exist
	 * @see org.eclipse.acceleo.ide.ui.resources.AcceleoProject.getOutputFolder(IProject aProject)
	 */
	static IPath getOutputFolder(IProject aProject) {
		final IJavaProject javaProject = JavaCore.create(aProject);
		try {
			IPath output = javaProject.getOutputLocation();
			if (output != null && output.segmentCount() > 1) {
				IFolder folder = aProject.getWorkspace().getRoot().getFolder(output);
				if (folder.isAccessible()) {
					return folder.getFullPath();
				}
			}
		} catch (JavaModelException e) {
			// continue
		}
		return null;
	}

	/**
	 * Returns the file that defines the module.
	 * 
	 * @param project
	 *            The project.
	 * @param module
	 *            The module.
	 * @return The file that defines the module.
	 */
	static IFile getFileFromModule(final IProject project, final Module module) {
		final String uri = module.getNsURI();
		AcceleoProject acceleoProject = new AcceleoProject(project);
		IFile result = null;

		final String pathStr = uri.replaceAll(IAcceleoConstants.NAMESPACE_SEPARATOR, SLASH);
		final IPath path = new Path(pathStr);

		try {
			for (IFile file : acceleoProject.getInputFiles()) {
				IPath p = file.getProjectRelativePath().removeFileExtension();
				if (p.removeFirstSegments(1).equals(path)) {
					result = file;
				}
			}
		} catch (CoreException e) {
			// do nothing
		}

		return result;
	}

	/**
	 * Returns the java compilation unit corresponding to the module file.
	 * 
	 * @param project
	 *            The project in which we are looking for our file.
	 * @param moduleFile
	 *            The file of our module.
	 * @return The java compilation unit corresponding to the module file.
	 */
	static ICompilationUnit getJavaCompilationUnitFromModuleFile(final IProject project,
			final IFile moduleFile) {
		ICompilationUnit compilationUnit = null;

		IPath path = moduleFile.getProjectRelativePath().removeLastSegments(1);
		String str = path.toString();

		String moduleName = moduleFile.getName().substring(0,
				moduleFile.getName().length() - (moduleFile.getFileExtension().length() + 1));

		str += SLASH + moduleName.substring(0, 1).toUpperCase() + moduleName.substring(1);
		path = new Path(str);

		IFile file = project.getFile(path.addFileExtension("java")); //$NON-NLS-1$
		if (file.exists()) {
			compilationUnit = JavaCore.createCompilationUnitFrom(file);
		}

		return compilationUnit;
	}

	/**
	 * Returns the java file corresponding to the module file.
	 * 
	 * @param project
	 *            The project in which we are looking for our file.
	 * @param moduleFile
	 *            The file of our module.
	 * @return The java file corresponding to the module file.
	 */
	static IFile getJavaFileFromModuleFile(final IProject project, final IFile moduleFile) {
		IFile file = null;

		IPath path = moduleFile.getProjectRelativePath().removeFileExtension();
		path = path.addFileExtension("java"); //$NON-NLS-1$

		file = project.getFile(path);

		return file;
	}

	/**
	 * Returns the module that match the given MTL file.
	 * 
	 * @param moduleFile
	 *            The MTL file.
	 * @return The matching module.
	 */
	static Module getModuleFromFile(final IFile moduleFile) {
		Module mod = null;

		AcceleoProject acceleoProject = new AcceleoProject(moduleFile.getProject());
		List<URI> uriList = acceleoProject.getOutputFiles();
		ResourceSet newResourceSet = new ResourceSetImpl();

		for (URI uri : uriList) {
			IPath path = acceleoProject.getOutputFilePath(moduleFile);
			URI moduleFileURI = null;
			if (path != null) {
				moduleFileURI = URI.createPlatformResourceURI(path.toString(), true);
			}

			if (uri.equals(moduleFileURI)) {
				try {
					ModelUtils.load(uri, newResourceSet);

					EcoreUtil.resolveAll(newResourceSet);
					for (Resource resource : newResourceSet.getResources()) {
						if (resource.getContents().size() > 0
								&& resource.getContents().get(0) instanceof Module) {
							mod = (Module)resource.getContents().get(0);
							break;
						}
					}
				} catch (IOException e) {
					// do nothing
				}
			}
		}

		return mod;
	}
}
