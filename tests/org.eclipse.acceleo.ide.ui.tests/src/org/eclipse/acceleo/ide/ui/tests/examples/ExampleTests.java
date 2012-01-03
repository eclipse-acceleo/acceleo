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
package org.eclipse.acceleo.ide.ui.tests.examples;

import org.eclipse.acceleo.ide.ui.tests.AbstractSWTBotTests;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ExampleTests extends AbstractSWTBotTests {

	@AfterClass
	public static void tearDown() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject iProject : projects) {
			deleteProject(iProject);
		}
	}

	@Test
	public void testUMLtoJavaGeneration() {
		// Import the example
		importAcceleoExample(AbstractSWTBotTests.UML_TO_Java);
		botWait(5000);

		IProject iProject = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"org.eclipse.acceleo.module.example.uml2java.helios"); //$NON-NLS-1$

		try {
			iProject.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
		botWait(5000);

		// Launch the generation
		generate("org.eclipse.acceleo.module.example.uml2java.helios", //$NON-NLS-1$
				"org.eclipse.acceleo.module.example.uml2java.helios.GenerateJava", //$NON-NLS-1$
				"/org.eclipse.acceleo.module.example.uml2java.helios/model/example.uml", //$NON-NLS-1$
				"/org.eclipse.acceleo.module.example.uml2java.helios/src", false, null, false); //$NON-NLS-1$

		// Check the result
		botWait(5000);
		IFolder outputFolder = iProject
				.getFolder("src").getFolder("org").getFolder("eclipse").getFolder("acceleo").getFolder("java"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$//$NON-NLS-5$
		assertTrue(outputFolder.isAccessible());
		try {
			assertEquals(6, outputFolder.members().length);
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
}
