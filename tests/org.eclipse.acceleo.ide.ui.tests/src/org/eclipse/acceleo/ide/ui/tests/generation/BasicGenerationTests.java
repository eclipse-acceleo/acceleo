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
package org.eclipse.acceleo.ide.ui.tests.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.acceleo.ide.ui.tests.AbstractSWTBotTests;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicGenerationTests extends AbstractSWTBotTests {

	private static IProject project;

	private static IFolder modelFolder;

	private static IFolder targetFolder;

	private static IFile model;

	private static String fileName = "MyModule"; //$NON-NLS-1$

	@BeforeClass
	public static void setUp() {
		AbstractSWTBotTests.setUp();
		project = createProjectWithFile("org.eclipse.acceleo.ide.ui.tests.generation", ECORE_URI, fileName); //$NON-NLS-1$
	}

	@AfterClass
	public static void tearDown() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject iProject : projects) {
			deleteProject(iProject);
		}
	}

	@Test
	public void testGenerationSimpleEcore() {
		botWait(5000);
		modelFolder = createFolder(project.getName() + "/", "model"); //$NON-NLS-1$ //$NON-NLS-2$
		botWait(5000);
		targetFolder = createFolder(project.getName() + "/", "target"); //$NON-NLS-1$//$NON-NLS-2$
		botWait(5000);
		model = createFile(modelFolder.getFullPath().toString(),
				"gen1.myEcore", getFileContent("/data/generation/gen1.ecore")); //$NON-NLS-1$//$NON-NLS-2$
		botWait(5000);
		generate(project.getName(), project.getName() + ".files." + fileName, model.getFullPath().toString(), //$NON-NLS-1$
				targetFolder.getFullPath().toString());
		botWait(5000);
		try {
			IResource[] members = targetFolder.members();
			assertEquals(3, members.length);

			IFile file1 = targetFolder.getFile("class1"); //$NON-NLS-1$
			assertTrue(file1.exists()
					&& "\tclass1\n".equals(FileContent.getFileContent(file1.getLocation().toFile()).toString())); //$NON-NLS-1$

			IFile file2 = targetFolder.getFile("class2"); //$NON-NLS-1$
			assertTrue(file2.exists()
					&& "\tclass2\n".equals(FileContent.getFileContent(file2.getLocation().toFile()).toString())); //$NON-NLS-1$

			IFile file3 = targetFolder.getFile("class3"); //$NON-NLS-1$
			assertTrue(file3.exists()
					&& "\tclass3\n".equals(FileContent.getFileContent(file3.getLocation().toFile()).toString())); //$NON-NLS-1$
		} catch (CoreException e) {
			fail();
		}
	}
}
