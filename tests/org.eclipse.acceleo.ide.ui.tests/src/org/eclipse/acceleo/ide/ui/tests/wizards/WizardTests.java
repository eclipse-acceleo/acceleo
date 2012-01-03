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
package org.eclipse.acceleo.ide.ui.tests.wizards;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.tests.AbstractSWTBotTests;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class WizardTests extends AbstractSWTBotTests {

	private static IProject project;

	@BeforeClass
	public static void setUp() {
		AbstractSWTBotTests.setUp();
		bot.menu(FILE).menu(NEW).menu(PROJECT).click();

		SWTBotShell shell = bot.shell(NEW_PROJECT);
		shell.activate();
		bot.tree().getTreeItem("Java Project").select(); //$NON-NLS-1$
		bot.button(NEXT).click();

		bot.textWithLabel(PROJECT_NAME).setText("javatestproject"); //$NON-NLS-1$
		bot.button(FINISH).click();

		botWait(5000);

		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject("javatestproject"); //$NON-NLS-1$

		// PDE Nature
		try {
			IProjectDescription description = iProject.getDescription();
			String[] natures = description.getNatureIds();
			String[] newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
			newNatures[natures.length] = "org.eclipse.pde.PluginNature"; //$NON-NLS-1$
			description.setNatureIds(newNatures);
			iProject.setDescription(description, new NullProgressMonitor());
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		IFolder iFolder = iProject.getFolder("src"); //$NON-NLS-1$
		iFolder = iFolder.getFolder("test"); //$NON-NLS-1$
		try {
			iFolder.create(true, false, new NullProgressMonitor());
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		if (iFolder.isAccessible()) {
			IFile iFile = iFolder.getFile("Test.java"); //$NON-NLS-1$

			StringBuffer buffer = new StringBuffer();
			buffer.append("package test;\n"); //$NON-NLS-1$
			buffer.append("public class Test {\n"); //$NON-NLS-1$
			buffer.append("\tpublic static String doStuff(String str) {\n"); //$NON-NLS-1$
			buffer.append("\t\treturn null;\n"); //$NON-NLS-1$
			buffer.append("\t}\n"); //$NON-NLS-1$
			buffer.append("}"); //$NON-NLS-1$

			try {
				ByteArrayInputStream source = new ByteArrayInputStream(buffer.toString().getBytes("UTF8")); //$NON-NLS-1$
				iFile.create(source, true, new NullProgressMonitor());
				iFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (UnsupportedEncodingException e) {
				fail(e.getMessage());
			} catch (CoreException e) {
				fail(e.getMessage());
			}

			try {
				iFolder = iProject.getFolder("META-INF"); //$NON-NLS-1$
				iFolder.create(true, false, new NullProgressMonitor());

				iFile = iFolder.getFile("MANIFEST.MF"); //$NON-NLS-1$

				buffer = new StringBuffer();
				buffer.append("Manifest-Version: 1.0\n"); //$NON-NLS-1$
				buffer.append("Bundle-ManifestVersion: 2\n"); //$NON-NLS-1$
				buffer.append("Bundle-Name: %pluginName\n"); //$NON-NLS-1$
				buffer.append("Bundle-SymbolicName: javatestproject\n"); //$NON-NLS-1$
				buffer.append("Bundle-Version: 1.0.0.qualifier\n"); //$NON-NLS-1$
				buffer.append("Bundle-ClassPath: .\n"); //$NON-NLS-1$
				buffer.append("Bundle-Vendor: %providerName\n"); //$NON-NLS-1$
				buffer.append("Bundle-RequiredExecutionEnvironment: J2SE-1.5\n"); //$NON-NLS-1$
				buffer.append("Eclipse-LazyStart: true\n"); //$NON-NLS-1$
				buffer.append("Bundle-ActivationPolicy: lazy\n"); //$NON-NLS-1$
				buffer.append("Export-Package: test\n"); //$NON-NLS-1$
				buffer.append("\n"); //$NON-NLS-1$

				ByteArrayInputStream source = new ByteArrayInputStream(buffer.toString().getBytes("UTF8")); //$NON-NLS-1$
				iFile.create(source, true, new NullProgressMonitor());
				iFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());

				iFile = iProject.getFile("build.properties"); //$NON-NLS-1$
				buffer = new StringBuffer();
				buffer.append("source.. = src/\n"); //$NON-NLS-1$
				buffer.append("output.. = bin/\n"); //$NON-NLS-1$
				buffer.append("bin.includes = META-INF/\n"); //$NON-NLS-1$
				buffer.append("Bundle-ClassPath: .\n"); //$NON-NLS-1$
				buffer.append("\n"); //$NON-NLS-1$
				source = new ByteArrayInputStream(buffer.toString().getBytes("UTF8")); //$NON-NLS-1$
				iFile.create(source, true, new NullProgressMonitor());
				iFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CoreException e) {
				fail(e.getMessage());
			} catch (UnsupportedEncodingException e) {
				fail(e.getMessage());
			}
		}
	}

	@AfterClass
	public static void tearDown() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject iProject : projects) {
			deleteProject(iProject);
		}
	}

	@Test
	public void testCreateProject() {
		IProject iProject = createProject("org.eclipse.acceleo.ide.ui.tests.wizard", false); //$NON-NLS-1$
		assertTrue("The project has not been created correctly", iProject.exists() //$NON-NLS-1$
				&& iProject.isAccessible());
		botWait(5000);

		try {
			IMarker[] markers = iProject.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithPerspectiveSwitch() {
		IProject iProject = createProject("org.eclipse.acceleo.ide.ui.tests.wizard.perspective", true); //$NON-NLS-1$
		assertTrue("The project has not been created correctly", iProject.exists() //$NON-NLS-1$
				&& iProject.isAccessible());
		botWait(5000);

		try {
			IMarker[] markers = iProject.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleWithQuery() {
		String projectName = "org.eclipse.acceleo.ide.ui.tests.wizard.query"; //$NON-NLS-1$
		String fileName = "MyModule"; //$NON-NLS-1$
		String queryName = "myQuery"; //$NON-NLS-1$
		String type = "EOperation"; //$NON-NLS-1$
		boolean hasDocumentation = false;
		String serviceInit = null;
		String[] metamodelURIs = {ECORE_URI };

		startProjectCreation(projectName);
		continueWithModuleCreationWithQuery(fileName, queryName, type, hasDocumentation, serviceInit,
				metamodelURIs);
		finish();

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createCommonRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleWithQueryAndDocumentation() {
		String projectName = "org.eclipse.acceleo.ide.ui.tests.wizard.query.documentation"; //$NON-NLS-1$
		String fileName = "MyModule"; //$NON-NLS-1$
		String queryName = "myQuery"; //$NON-NLS-1$
		String type = "EOperation"; //$NON-NLS-1$
		boolean hasDocumentation = true;
		String serviceInit = null;
		String[] metamodelURIs = {ECORE_URI };

		startProjectCreation(projectName);
		continueWithModuleCreationWithQuery(fileName, queryName, type, hasDocumentation, serviceInit,
				metamodelURIs);
		finish();

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createCommonRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleWithQueryAndInitService() {
		String projectName = "org.eclipse.acceleo.ide.ui.tests.wizard.query.service"; //$NON-NLS-1$
		String fileName = "MyModule"; //$NON-NLS-1$
		String queryName = "myQuery"; //$NON-NLS-1$
		String type = "EOperation"; //$NON-NLS-1$
		boolean hasDocumentation = false;
		String serviceInit = "/javatestproject/src/test/Test.java"; //$NON-NLS-1$
		String[] metamodelURIs = {ECORE_URI };

		startProjectCreation(projectName);
		continueWithModuleCreationWithQuery(fileName, queryName, type, hasDocumentation, serviceInit,
				metamodelURIs);
		finish();

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createCommonRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleWithQueryAndDocumentationAndInitService() {
		String projectName = "org.eclipse.acceleo.ide.ui.tests.wizard.query.documentation.service"; //$NON-NLS-1$
		String fileName = "MyModule"; //$NON-NLS-1$
		String queryName = "myQuery"; //$NON-NLS-1$
		String type = "EOperation"; //$NON-NLS-1$
		boolean hasDocumentation = true;
		String serviceInit = "/javatestproject/src/test/Test.java"; //$NON-NLS-1$
		String[] metamodelURIs = {ECORE_URI };

		startProjectCreation(projectName);
		continueWithModuleCreationWithQuery(fileName, queryName, type, hasDocumentation, serviceInit,
				metamodelURIs);
		finish();

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createCommonRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleWithTemplate() {
		String projectName = "org.eclipse.acceleo.ide.ui.tests.wizard.template"; //$NON-NLS-1$
		String fileName = "MyModule"; //$NON-NLS-1$
		String templateName = "myTemplate"; //$NON-NLS-1$
		String type = "EOperation"; //$NON-NLS-1$
		boolean hasDocumentation = false;
		boolean isMain = false;
		boolean generatesFile = false;
		String initFilePath = null;
		String[] metamodelURIs = {ECORE_URI };

		startProjectCreation(projectName);
		continueWithModuleCreationWithTemplate(fileName, templateName, type, hasDocumentation, isMain,
				generatesFile, initFilePath, metamodelURIs);

		finish();

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createCommonRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleWithMainTemplate() {
		String projectName = "org.eclipse.acceleo.ide.ui.tests.wizard.template.main"; //$NON-NLS-1$
		String fileName = "MyModule"; //$NON-NLS-1$
		String templateName = "myTemplate"; //$NON-NLS-1$
		String type = "EOperation"; //$NON-NLS-1$
		boolean hasDocumentation = false;
		boolean isMain = true;
		boolean generatesFile = false;
		String initFilePath = null;
		String[] metamodelURIs = {ECORE_URI };

		startProjectCreation(projectName);
		continueWithModuleCreationWithTemplate(fileName, templateName, type, hasDocumentation, isMain,
				generatesFile, initFilePath, metamodelURIs);

		finish();

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createMainRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleWithTemplateWithDocumentation() {
		String projectName = "org.eclipse.acceleo.ide.ui.tests.wizard.template.documentation"; //$NON-NLS-1$
		String fileName = "MyModule"; //$NON-NLS-1$
		String templateName = "myTemplate"; //$NON-NLS-1$
		String type = "EOperation"; //$NON-NLS-1$
		boolean hasDocumentation = true;
		boolean isMain = false;
		boolean generatesFile = false;
		String initFilePath = null;
		String[] metamodelURIs = {ECORE_URI };

		startProjectCreation(projectName);
		continueWithModuleCreationWithTemplate(fileName, templateName, type, hasDocumentation, isMain,
				generatesFile, initFilePath, metamodelURIs);

		finish();

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createCommonRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleWithFileTemplate() {
		String projectName = "org.eclipse.acceleo.ide.ui.tests.wizard.template.file"; //$NON-NLS-1$
		String fileName = "MyModule"; //$NON-NLS-1$
		String templateName = "myTemplate"; //$NON-NLS-1$
		String type = "EOperation"; //$NON-NLS-1$
		boolean hasDocumentation = false;
		boolean isMain = false;
		boolean generatesFile = true;
		String initFilePath = null;
		String[] metamodelURIs = {ECORE_URI };

		startProjectCreation(projectName);
		continueWithModuleCreationWithTemplate(fileName, templateName, type, hasDocumentation, isMain,
				generatesFile, initFilePath, metamodelURIs);

		finish();

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createFileRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateProjectWithModuleWithMainFileTemplate() {
		String projectName = "org.eclipse.acceleo.ide.ui.tests.wizard.template.main.file"; //$NON-NLS-1$
		String fileName = "MyModule"; //$NON-NLS-1$
		String templateName = "myTemplate"; //$NON-NLS-1$
		String type = "EOperation"; //$NON-NLS-1$
		boolean hasDocumentation = false;
		boolean isMain = true;
		boolean generatesFile = true;
		String initFilePath = null;
		String[] metamodelURIs = {ECORE_URI };

		startProjectCreation(projectName);
		continueWithModuleCreationWithTemplate(fileName, templateName, type, hasDocumentation, isMain,
				generatesFile, initFilePath, metamodelURIs);

		finish();

		project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		botWait(5000);

		assertTrue("The project has not been created correctly", project.exists() //$NON-NLS-1$
				&& project.isAccessible());

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(createMainRootPath(project.getName()) + fileName + "." //$NON-NLS-1$
						+ IAcceleoConstants.MTL_FILE_EXTENSION));

		assertTrue("The file has not been created correctly", iFile.exists() && iFile.isAccessible()); //$NON-NLS-1$

		try {
			IMarker[] markers = project.findMarkers(IMarker.PROBLEM, false, IResource.DEPTH_INFINITE);
			if (markers.length > 0) {
				if (!(markers.length == 1 && markers[0]
						.getAttribute(IMarker.MESSAGE, "").equals(ERROR_BUILD_PATH_JAVA))) { //$NON-NLS-1$
					String markersMessage = ""; //$NON-NLS-1$
					for (IMarker iMarker : markers) {
						markersMessage += iMarker.getAttribute(IMarker.MESSAGE, "") + " "; //$NON-NLS-1$//$NON-NLS-2$
					}
					fail("There are errors in the project: " + markersMessage); //$NON-NLS-1$
				}
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
}
