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
package org.eclipse.acceleo.ide.ui.tests;

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
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.BeforeClass;
import org.osgi.framework.Bundle;

import static org.junit.Assert.fail;

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

	public static final String INITIALIZE_CONTENT = "Initialize Contents"; //$NON-NLS-1$

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

	public static final String YES = "Yes"; //$NON-NLS-1$

	public static final String NO = "No"; //$NON-NLS-1$

	public static final String ERROR_BUILD_PATH_JAVA = "There are errors in the project: Build path specifies execution environment J2SE-1.5. There are no JREs installed in the workspace that are strictly compatible with this environment."; //$NON-NLS-1$

	public static final String MODULE_NAME = "Module Name:"; //$NON-NLS-1$

	public static final String QUERY_NAME = "Query Name:"; //$NON-NLS-1$

	public static final String TEMPLATE_NAME = "Template Name:"; //$NON-NLS-1$

	public static final String GENERATE_DOCUMENTATION = "Generate documentation"; //$NON-NLS-1$

	public static final String QUERY = "Query"; //$NON-NLS-1$

	public static final String TEMPLATE = "Template"; //$NON-NLS-1$

	public static final String OK = "OK"; //$NON-NLS-1$

	public static final String CANCEL = "Cancel"; //$NON-NLS-1$

	public static final String TYPE = "Type:"; //$NON-NLS-1$

	public static final String ADD_METAMODEL_BUTTON_TOOLTIP = "Add metamodels to the module definition"; //$NON-NLS-1$

	public static final String REMOVE_METAMODEL_BUTTON_TOOLTIP = "Remove metamodels from the module definition"; //$NON-NLS-1$

	public static final String JAVA = "Java"; //$NON-NLS-1$

	public static final String METAMODEL_TYPE_TOOLTIP = "Select the type of the first parameter of the module element."; //$NON-NLS-1$

	public static final String MAIN_TEMPLATE = "Main template"; //$NON-NLS-1$

	public static final String GENERATE_FILE = "Generate file"; //$NON-NLS-1$

	public static final String EXAMPLE = "Example..."; //$NON-NLS-1$

	public static final String ACCELEO_PLUGINS = "Acceleo Plug-ins"; //$NON-NLS-1$

	public static final String UML_TO_Java = "UML to Java (requires the installation of the UML project)"; //$NON-NLS-1$

	public static final String ECORE_TO_PYTHON = "Ecore to Python"; //$NON-NLS-1$

	public static final String ECORE_TO_UNIT_TESTS = "Ecore to Unit tests"; //$NON-NLS-1$

	public static final String ENABLE_PROFILING = "Enable profiling"; //$NON-NLS-1$

	public static final String PROFILE_RESULT = "Profile result:"; //$NON-NLS-1$

	public static final String CONTRIBUTE_TRACEABILITY = "Contribute traceability information to Result View"; //$NON-NLS-1$

	public static final String RUNNER = "Runner:"; //$NON-NLS-1$

	public static final String NEW_EXAMPLE = "New Example"; //$NON-NLS-1$

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
		List<SWTBotView> views = bot.views();
		for (SWTBotView view : views) {
			if (WELCOME.equals(view.getTitle())) {
				view.close();
			}
		}
		bot.perspectiveByLabel(JAVA).activate();
	}

	/**
	 * Creates a new project with SWTBot.
	 * 
	 * @param projectName
	 *            The name of the project
	 * @return The project created
	 */
	protected static IProject createProject(String projectName, boolean switchPerspective) {
		bot.menu(FILE).menu(NEW).menu(PROJECT).click();

		SWTBotShell shell = bot.shell(NEW_PROJECT);
		shell.activate();
		bot.tree().expandNode(ACCELEO_MODEL_TO_TEXT).getNode(ACCELEO_PROJECT).select();
		bot.button(NEXT).click();

		bot.textWithLabel(PROJECT_NAME).setText(projectName);
		bot.button(FINISH).click();

		if (switchPerspective) {
			bot.button(YES).click();
		} else {
			bot.button(NO).click();
		}

		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		try {
			iProject.close(new NullProgressMonitor());
			iProject.open(new NullProgressMonitor());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		return iProject;
	}

	/**
	 * Creates a new project with SWTBot with a new module.
	 * 
	 * @param projectName
	 *            The name of the project
	 */
	protected static void startProjectCreation(String projectName) {
		bot.menu(FILE).menu(NEW).menu(PROJECT).click();

		SWTBotShell shell = bot.shell(NEW_PROJECT);
		shell.activate();
		bot.tree().expandNode(ACCELEO_MODEL_TO_TEXT).getNode(ACCELEO_PROJECT).select();
		bot.button(NEXT).click();

		bot.textWithLabel(PROJECT_NAME).setText(projectName);
		bot.button(NEXT).click();
	}

	protected static void continueWithModuleCreationWithQuery(String moduleName, String queryName,
			String type, boolean hasDocumentation, String serviceInit, String... metamodelURIs) {
		// Module name
		bot.textWithLabel(MODULE_NAME).setText(moduleName);

		// Metamodel
		bot.buttonWithTooltip(ADD_METAMODEL_BUTTON_TOOLTIP).click();
		for (String metamodelURI : metamodelURIs) {
			bot.table().getTableItem(metamodelURI).select();
		}
		bot.button(OK).click();

		// Query name
		bot.radio(QUERY).click();
		bot.textWithLabel(QUERY_NAME).setText(queryName);

		// Type
		bot.comboBox(0).setSelection(type);

		// Documentation
		if (hasDocumentation) {
			bot.checkBox(GENERATE_DOCUMENTATION).click();
		}

		if (serviceInit != null) {
			bot.checkBox(INITIALIZE_CONTENT).click();
			bot.comboBox(1).setSelection(1);
			bot.textWithLabel(FILE).setText(serviceInit);
		}
	}

	protected static void continueWithModuleCreationWithTemplate(String moduleName, String templateName,
			String type, boolean hasDocumentation, boolean isMain, boolean generatesFile,
			String initFilePath, String... metamodelURIs) {
		// Module name
		bot.textWithLabel(MODULE_NAME).setText(moduleName);

		// Metamodel
		bot.buttonWithTooltip(ADD_METAMODEL_BUTTON_TOOLTIP).click();
		for (String metamodelURI : metamodelURIs) {
			bot.table().getTableItem(metamodelURI).select();
		}
		bot.button(OK).click();

		// Template name
		bot.radio(TEMPLATE).click();
		bot.textWithLabel(TEMPLATE_NAME).setText(templateName);

		// Type
		bot.comboBox(0).setSelection(type);

		// Documentation
		if (hasDocumentation) {
			bot.checkBox(GENERATE_DOCUMENTATION).click();
		}

		if (isMain) {
			bot.checkBox(MAIN_TEMPLATE).click();
		}

		if (generatesFile) {
			bot.checkBox(GENERATE_FILE).click();
		}

		if (initFilePath != null) {
			bot.checkBox(INITIALIZE_CONTENT).click();
			bot.comboBox(1).setSelection(0);
			bot.textWithLabel(FILE).setText(initFilePath);
		}
	}

	protected static void finish() {
		bot.button(FINISH).click();
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
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
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
	 * Creates a file's path from a project's name.
	 * 
	 * @param projectName
	 *            the name of the project
	 * @return the path of the root of the file in the project
	 */
	protected static String createRequestRootPath(String projectName) {
		return "/" + projectName + "/src/" + projectName.replaceAll("\\.", "/") + "/request/"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
	}

	/**
	 * Creates a file's path from a project's name.
	 * 
	 * @param projectName
	 *            the name of the project
	 * @return the path of the root of the file in the project
	 */
	protected static String createCommonRootPath(String projectName) {
		return "/" + projectName + "/src/" + projectName.replaceAll("\\.", "/") + "/common/"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
	}

	/**
	 * Creates a file's path from a project's name.
	 * 
	 * @param projectName
	 *            the name of the project
	 * @return the path of the root of the file in the project
	 */
	protected static String createMainRootPath(String projectName) {
		return "/" + projectName + "/src/" + projectName.replaceAll("\\.", "/") + "/main/"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
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
	 *            The name of the project
	 * @param javaClass
	 *            The name of the Java launcher
	 * @param model
	 *            The workspace relative path of the model
	 * @param targetFolder
	 *            The workspace relative path of the target folder.
	 * @param standAlone
	 *            Indicates if we should run in stand alone mode.
	 * @param profilePath
	 *            The path of the profile model (<code>null</code> indicates that the profiling should not be
	 *            activated)
	 * @param traceability
	 *            Indicates if we should active the traceability
	 */
	protected static void generate(String projectName, String javaClass, String model, String targetFolder,
			boolean standAlone, String profilePath, boolean traceability) {
		bot.menu(RUN).menu(RUN_CONFIGURATIONS).click();
		SWTBotShell shell = bot.shell(RUN_CONFIGURATIONS_SHELL);
		shell.activate();

		bot.tree().getTreeItem(ACCELEO_APPLICATION).click().select().setFocus();
		bot.tree().getTreeItem(ACCELEO_APPLICATION).click().select().doubleClick();

		botWait(5000);

		bot.textInGroup(PROJECT_LABEL).setText(projectName);
		bot.textInGroup(MAIN_CLASS_LABEL).setText(javaClass);
		bot.textInGroup(MODEL_LABEL).setText(model);
		bot.textInGroup(TARGET_LABEL).setText(targetFolder);

		if (profilePath != null) {
			bot.checkBoxWithLabel(ENABLE_PROFILING).click();
			bot.textInGroup(PROFILE_RESULT).setText(profilePath);
		}

		if (traceability) {
			bot.checkBoxWithLabel(CONTRIBUTE_TRACEABILITY).click();
		}

		if (standAlone) {
			bot.comboBox(RUNNER).setSelection(1);
		}

		bot.button(APPLY).click();

		botWait(2000);

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

	protected static void importAcceleoExample(String name) {
		bot.menu(FILE).menu(NEW).menu(EXAMPLE).click();

		SWTBotShell shell = bot.shell(NEW_EXAMPLE);
		shell.activate();
		bot.tree().expandNode(ACCELEO_PLUGINS).getNode(name).select();
		bot.button(FINISH).click();
	}

}
