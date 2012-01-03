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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarkerUtils;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.resource.AcceleoUIResourceSet;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISaveablesLifecycleListener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.SaveablesLifecycleEvent;

/**
 * Utility class needed to perform the refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class AcceleoRefactoringUtils {

	/**
	 * Slash.
	 */
	private static final String SLASH = "/"; //$NON-NLS-1$

	/**
	 * The constructor.
	 */
	private AcceleoRefactoringUtils() {
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
	public static IPath getOutputFolder(IProject aProject) {
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
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return null;
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
	public static ICompilationUnit getJavaCompilationUnitFromModuleFile(final IProject project,
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
	public static IFile getJavaFileFromModuleFile(final IProject project, final IFile moduleFile) {
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
	public static Module getModuleFromFile(final IFile moduleFile) {
		Module mod = null;

		AcceleoProject acceleoProject = new AcceleoProject(moduleFile.getProject());
		List<URI> uriList = acceleoProject.getOutputFiles();

		for (URI uri : uriList) {
			IPath path = acceleoProject.getOutputFilePath(moduleFile);
			URI moduleFileURI = null;
			if (path != null) {
				moduleFileURI = URI.createPlatformResourceURI(path.toString(), true);
			}

			if (uri.equals(moduleFileURI)) {
				try {
					EObject eObject = AcceleoUIResourceSet.getResource(moduleFileURI);

					EcoreUtil.resolveAll(eObject.eResource());
					if (eObject instanceof Module) {
						mod = (Module)eObject;
						break;
					}
				} catch (IOException e) {
					AcceleoUIActivator.log(e, true);
				}
			}
		}

		return mod;
	}

	/**
	 * Check if there are any acceleo errors in the file.
	 * 
	 * @param file
	 *            The current file.
	 * @return true if there is an error.
	 */
	public static boolean containsAcceleoError(final IFile file) {
		boolean result = false;
		try {
			IMarker[] markers = file.findMarkers(AcceleoMarkerUtils.PROBLEM_MARKER_ID, true,
					IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				result = true;
			}
		} catch (CoreException e) {
			result = true;
		}
		return result;
	}

	/**
	 * Force the editor to save to perform the refactoring.
	 * 
	 * @return If the editor has saved.
	 */
	public static boolean allResourceSaved() {
		final List<AcceleoEditor> dirtyEditorList = new ArrayList<AcceleoEditor>();
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			IEditorPart[] editors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getDirtyEditors();
			for (IEditorPart iEditorPart : editors) {
				if (iEditorPart instanceof AcceleoEditor) {
					dirtyEditorList.add((AcceleoEditor)iEditorPart);
				}
			}
		}

		if (dirtyEditorList.size() > 0) {
			for (AcceleoEditor acceleoEditor : dirtyEditorList) {
				ISaveablesLifecycleListener modelManager = (ISaveablesLifecycleListener)acceleoEditor
						.getSite().getWorkbenchWindow().getService(ISaveablesLifecycleListener.class);
				Saveable[] saveableArray = acceleoEditor.getSaveables();
				// Fires a "pre close" event so that the editors prompts us to save the dirty files.
				// None will really be closed.
				SaveablesLifecycleEvent event = new SaveablesLifecycleEvent(acceleoEditor,
						SaveablesLifecycleEvent.PRE_CLOSE, saveableArray, false);
				modelManager.handleLifecycleEvent(event);
			}

			boolean allSaved = true;
			for (AcceleoEditor acceleoEditor : dirtyEditorList) {
				if (acceleoEditor.isDirty()) {
					allSaved = false;
					break;
				}
			}

			return allSaved;
		}
		return true;
	}
}
