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
package org.eclipse.acceleo.ide.ui.tests;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.BeforeClass;
import org.osgi.framework.Bundle;

/**
 * Class containing all the utility methods used by SWTBot tests.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AbstractSWTBotTests {

	public static final String ECORE_URI = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$

	public static final String WELCOME = "Welcome"; //$NON-NLS-1$

	public static final String FILE = "File"; //$NON-NLS-1$

	public static final String NEW = "New"; //$NON-NLS-1$

	public static final String PROJECT = "Project..."; //$NON-NLS-1$

	public static final String NEW_PROJECT = "New Project"; //$NON-NLS-1$

	public static final String ACCELEO_MODEL_TO_TEXT = "Acceleo Model to Text"; //$NON-NLS-1$

	public static final String ACCELEO_PROJECT = "Acceleo Project"; //$NON-NLS-1$

	public static final String NEXT = "Next >"; //$NON-NLS-1$

	public static final String PROJECT_NAME = "Project name:"; //$NON-NLS-1$

	public static final String FINISH = "Finish"; //$NON-NLS-1$

	public static final String NAME = "Name:"; //$NON-NLS-1$

	public static final String METAMODEL_URI = "Metamodel URI:"; //$NON-NLS-1$

	public static final String OTHER = "Other..."; //$NON-NLS-1$

	public static final String ACCELEO_MODULE_FILE = "Acceleo Module File"; //$NON-NLS-1$

	public static final String PARENT_FOLDER = "Parent Folder:"; //$NON-NLS-1$

	public static final String ADD = "Add"; //$NON-NLS-1$

	public static final String FOLDER = "Folder"; //$NON-NLS-1$

	public static final String NEW_FOLDER = "New Folder"; //$NON-NLS-1$

	public static final String PACKAGE_EXPLORER = "Package Explorer"; //$NON-NLS-1$

	public static final String FOLDER_NAME = "Folder name:"; //$NON-NLS-1$

	public static final String FILE_NAME = "File name:"; //$NON-NLS-1$

	public static final String NEW_FILE = "New File"; //$NON-NLS-1$

	public static final String ADVANCED = "Advanced >>"; //$NON-NLS-1$

	public static final String INITIALIZE_CONTENT = "Initialize contents"; //$NON-NLS-1$

	public static final String FILE_COLON = "File:"; //$NON-NLS-1$

	public static final String RUN = "Run"; //$NON-NLS-1$

	public static final String RUN_CONFIGURATIONS = "Run Configurations..."; //$NON-NLS-1$

	public static final String RUN_CONFIGURATIONS_SHELL = "Run Configurations"; //$NON-NLS-1$

	public static final String ACCELEO_APPLICATION = "Acceleo Application"; //$NON-NLS-1$

	public static final String NEW_LAUNCH_CONFIGURATION = "New launch configuration"; //$NON-NLS-1$

	public static final String NEW_CONFIGURATION = "New_configuration"; //$NON-NLS-1$

	public static final String PROJECT_LABEL = "Project:"; //$NON-NLS-1$

	public static final String MAIN_CLASS_LABEL = "Main class:"; //$NON-NLS-1$

	public static final String MODEL_LABEL = "Model:"; //$NON-NLS-1$

	public static final String TARGET_LABEL = "Target:"; //$NON-NLS-1$

	public static final String APPLY = "Apply"; //$NON-NLS-1$

	/**
	 * The SWTBot.
	 */
	protected static SWTWorkbenchBot bot;

	private static Bundle bundle;

	/**
	 * Initialize the SWTBot.
	 */
	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.ide.ui.tests"); //$NON-NLS-1$		
		bot = new SWTWorkbenchBot();
		bot.viewByTitle(WELCOME).close();
	}

	/**
	 * Creates a new project with SWTBot.
	 * 
	 * @param projectName
	 *            The name of the project
	 * @return The project created
	 */
	protected static IProject createProject(String projectName) {
		bot.menu(FILE).menu(NEW).menu(PROJECT).click();

		SWTBotShell shell = bot.shell(NEW_PROJECT);
		shell.activate();
		bot.tree().expandNode(ACCELEO_MODEL_TO_TEXT).getNode(ACCELEO_PROJECT).select();
		bot.button(NEXT).click();

		bot.textWithLabel(PROJECT_NAME).setText(projectName);
		bot.button(FINISH).click();

		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return iProject;
	}

	/**
	 * Creates a new project with SWTBot with a new module.
	 * 
	 * @param projectName
	 *            The name of the project
	 * @param metamodelUri
	 *            The uri of the metamodel
	 * @param fileName
	 *            The name of the file
	 * @return The project created
	 */
	protected static IProject createProjectWithFile(String projectName, String metamodelUri, String fileName) {
		bot.menu(FILE).menu(NEW).menu(PROJECT).click();

		SWTBotShell shell = bot.shell(NEW_PROJECT);
		shell.activate();
		bot.tree().expandNode(ACCELEO_MODEL_TO_TEXT).getNode(ACCELEO_PROJECT).select();
		bot.button(NEXT).click();

		bot.textWithLabel(PROJECT_NAME).setText(projectName);
		bot.button(NEXT).click();

		bot.textWithLabel(NAME).setText(fileName);
		bot.textWithLabel(METAMODEL_URI).setText(metamodelUri);
		bot.button(FINISH).click();

		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return iProject;
	}

	protected static IProject createProjectWithFiles(String projectName, String metamodelUri,
			String... fileNames) {
		bot.menu(FILE).menu(NEW).menu(PROJECT).click();

		SWTBotShell shell = bot.shell(NEW_PROJECT);
		shell.activate();
		bot.tree().expandNode(ACCELEO_MODEL_TO_TEXT).getNode(ACCELEO_PROJECT).select();
		bot.button(NEXT).click();

		bot.textWithLabel(PROJECT_NAME).setText(projectName);
		bot.button(NEXT).click();

		if (fileNames.length > 0) {
			bot.textWithLabel(NAME).setText(fileNames[0]);
			bot.textWithLabel(METAMODEL_URI).setText(metamodelUri);
		}

		if (fileNames.length > 1) {
			for (int i = 1; i < fileNames.length; i++) {
				String name = fileNames[i];
				bot.button(ADD).click();
				bot.textWithLabel(NAME).setText(name);
				bot.textWithLabel(METAMODEL_URI).setText(metamodelUri);
			}
		}

		bot.button(FINISH).click();

		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		return iProject;
	}

	/**
	 * Creates an acceleo module in the current project.
	 * 
	 * @param moduleName
	 *            The name of the module.
	 * @param path
	 *            The path of the file
	 * @param metamodelUri
	 *            The uri of the metamodel needed by the module
	 * @return the created file
	 */
	protected static IFile createAcceleoModule(String moduleName, String path, String metamodelUri) {
		bot.menu(FILE).menu(NEW).menu(OTHER).click();

		SWTBotShell shell = bot.shell(NEW);
		shell.activate();
		bot.tree().expandNode(ACCELEO_MODEL_TO_TEXT).getNode(ACCELEO_MODULE_FILE).select();
		bot.button(NEXT).click();
		bot.textWithLabel(PARENT_FOLDER).setText(path);
		bot.textWithLabel(NAME).setText(moduleName);
		bot.textWithLabel(METAMODEL_URI).setText(metamodelUri);

		bot.button(FINISH).click();

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(path + moduleName + "." + IAcceleoConstants.MTL_FILE_EXTENSION)); //$NON-NLS-1$
		return iFile;
	}

	protected static IFile createdAcceleoModuleFromExample(String moduleName, String path,
			String metamodelUri, String examplePath) {
		bot.menu(FILE).menu(NEW).menu(OTHER).click();

		SWTBotShell shell = bot.shell(NEW);
		shell.activate();
		bot.tree().expandNode(ACCELEO_MODEL_TO_TEXT).getNode(ACCELEO_MODULE_FILE).select();
		bot.button(NEXT).click();
		bot.textWithLabel(PARENT_FOLDER).setText(path);
		bot.textWithLabel(NAME).setText(moduleName);
		bot.textWithLabel(METAMODEL_URI).setText(metamodelUri);

		bot.button(ADVANCED).click();
		bot.checkBox(0).click();
		bot.textWithLabel(FILE_COLON).setText(examplePath);

		bot.button(FINISH).click();

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(path + moduleName + "." + IAcceleoConstants.MTL_FILE_EXTENSION)); //$NON-NLS-1$
		return iFile;
	}

	/**
	 * Create a folder with the given name at the given path.
	 * 
	 * @param path
	 *            The path of the folder
	 * @param folderName
	 *            The name of the folder
	 */
	protected static IFolder createFolder(String path, String folderName) {
		bot.menu(FILE).menu(NEW).menu(FOLDER).click();
		SWTBotShell shell = bot.shell(NEW_FOLDER);
		shell.activate();

		bot.text(0).setText(path);
		bot.textWithLabel(FOLDER_NAME).setText(folderName);

		bot.button(FINISH).click();

		IFolder iFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(path + folderName));
		return iFolder;
	}

	/**
	 * Create a file at the given path with the given name and the given content.
	 * 
	 * @param path
	 *            The path of the file
	 * @param fileName
	 *            The name of the file
	 * @param content
	 *            The content of the file
	 * @return The file created
	 */
	protected static IFile createFile(String path, String fileName, StringBuffer content) {
		bot.menu(FILE).menu(NEW).menu(FILE).click();
		SWTBotShell shell = bot.shell(NEW_FILE);
		shell.activate();

		bot.text(0).setText(path);
		bot.textWithLabel(FILE_NAME).setText(fileName);

		bot.button(FINISH).click();

		List<? extends SWTBotEditor> editors = bot.editors();
		for (SWTBotEditor swtBotEditor : editors) {
			swtBotEditor.close();
		}

		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path + "/" + fileName)); //$NON-NLS-1$

		InputStream inputStream = new ByteArrayInputStream(content.toString().getBytes());
		try {
			iFile.setContents(inputStream, IResource.FORCE, new NullProgressMonitor());
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		return iFile;
	}

	/**
	 * Delete the given project.
	 * 
	 * @param project
	 *            the project to delete
	 */
	protected static void deleteProject(IProject project) {
		try {
			project.delete(true, new NullProgressMonitor());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Delete the given file.
	 * 
	 * @param file
	 *            the file to delete
	 */
	protected static void deleteFile(IFile file) {
		try {
			file.delete(true, new NullProgressMonitor());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Creates a file's path from a project's name.
	 * 
	 * @param projectName
	 *            the name of the project
	 * @return the path of the root of the file in the project
	 */
	protected static String createFileRootPath(String projectName) {
		return "/" + projectName + "/src/" + projectName.replaceAll("\\.", "/") + "/files/"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
	}

	/**
	 * Creates the file at the given path.
	 * 
	 * @param pathName
	 *            The path
	 * @return The created file
	 */
	private static File createFile(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			return new File(fileLocation);
		} catch (IOException e) {
			throw new AssertionFailedError(e.getMessage());
		} catch (NullPointerException e) {
			/*
			 * on the server the unit test fails with an NPE :S
			 */
			throw new AssertionFailedError(e.getMessage());
		}
	}

	/**
	 * Returns the content of the file at the given path.
	 * 
	 * @param path
	 *            The given path
	 * @return The content of the file at the given path
	 */
	protected static StringBuffer getFileContent(String path) {
		return FileContent.getFileContent(createFile(path));
	}

	/**
	 * Launch an acceleo generation with the given parameters.
	 * 
	 * @param projectName
	 * @param javaClass
	 * @param model
	 * @param targetFolder
	 */
	protected static void generate(String projectName, String javaClass, String model, String targetFolder) {
		bot.menu(RUN).menu(RUN_CONFIGURATIONS).click();
		SWTBotShell shell = bot.shell(RUN_CONFIGURATIONS_SHELL);
		shell.activate();

		bot.tree().getTreeItem(ACCELEO_APPLICATION).click().select().setFocus();
		bot.tree().getTreeItem(ACCELEO_APPLICATION).click().select().doubleClick();

		botWait(10000);

		bot.textInGroup(PROJECT_LABEL).setText(projectName);
		bot.textInGroup(MAIN_CLASS_LABEL).setText(javaClass);
		bot.textInGroup(MODEL_LABEL).setText(model);
		bot.textInGroup(TARGET_LABEL).setText(targetFolder);

		bot.button(APPLY).click();
		bot.button(RUN).click();
	}

	/**
	 * Tell SWTBot to wait for the given time.
	 * 
	 * @param millis
	 *            the time
	 */
	protected static void botWait(long millis) {
		bot.sleep(millis);
	}

}
