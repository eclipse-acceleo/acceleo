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
package org.eclipse.acceleo.ide.ui.tests.wizards;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.tests.AbstractSWTBotTests;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.AfterClass;
import org.junit.Test;

public class WizardTests extends AbstractSWTBotTests {

	private static IProject project;

	@AfterClass
	public static void tearDown() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject iProject : projects) {
			deleteProject(iProject);
		}
	}

	@Test
	public void testCreateProject() {
		IProject iProject = createProject("org.eclipse.acceleo.ide.ui.tests.wizard"); //$NON-NLS-1$
		assertTrue("The project has not been created correctly", iProject.exists() //$NON-NLS-1$
				&& iProject.isAccessible());
		botWait(5000);

		try {
			IMarker[] markers = iProject.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				fail("There are errors in the project"); //$NON-NLS-1$
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModule() {
		String fileName = "MyModule"; //$NON-NLS-1$
		project = createProjectWithFile("org.eclipse.acceleo.ide.ui.tests.wizard2", ECORE_URI, fileName); //$NON-NLS-1$

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		botWait(5000);

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createFileRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		botWait(5000);

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				fail("There are errors in the project"); //$NON-NLS-1$
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateModule() {
		String fileName = "MyModule2"; //$NON-NLS-1$
		IFile iFile = createAcceleoModule(fileName, createFileRootPath(project.getName()), ECORE_URI);
		botWait(5000);
		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$
	}

	@Test
	public void testCreateProjectWithMultipleModule() {
		String[] fileNames = new String[] {"MyModule1", "MyModule2", "MyModule3", "MyModule4", "MyModule5", }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		project = createProjectWithFiles("org.eclipse.acceleo.ide.ui.tests.wizard3", ECORE_URI, fileNames); //$NON-NLS-1$

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		botWait(5000);

		for (String name : fileNames) {
			IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
					new Path(createFileRootPath(project.getName()) + name + "." //$NON-NLS-1$
							+ IAcceleoConstants.MTL_FILE_EXTENSION));

			assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$
		}

		botWait(5000);

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				fail("There are errors in the project"); //$NON-NLS-1$
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleCopyContent() {
		String fileName = "MyModule"; //$NON-NLS-1$
		project = createProjectWithFile("org.eclipse.acceleo.ide.ui.tests.wizard4", ECORE_URI, fileName); //$NON-NLS-1$

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		botWait(5000);

		String path = project.getName() + "/"; //$NON-NLS-1$
		IFolder iFolder = createFolder(path, "examples"); //$NON-NLS-1$

		assertTrue("The folder has not been created correctly", iFolder.exists() && iFolder.isAccessible()); //$NON-NLS-1$

		botWait(5000);

		StringBuffer content = getFileContent("data/examples/documentation.html"); //$NON-NLS-1$
		IFile iFile = createFile(iFolder.getFullPath().toString(), "example.html", content); //$NON-NLS-1$
		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		IFile moduleFromExample = createdAcceleoModuleFromExample(
				"ModuleFromExample", createFileRootPath(project.getName()), //$NON-NLS-1$
				ECORE_URI, "/" + project.getName() + "/examples/example.html"); //$NON-NLS-1$//$NON-NLS-2$

		botWait(5000);

		assertTrue("The file has not been created correctly", moduleFromExample.exists() //$NON-NLS-1$
				&& moduleFromExample.isAccessible());

	}
}
