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
package org.eclipse.acceleo.standalone.generators.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.acceleo.module.example.uml2java.helios.GenerateJava;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class GeneratorsTest {
	/**
	 * The user.dir property key.
	 */
	private static final String USERDIR = "user.dir";

	@Test
	public void testUMLtoJava() {
		String path = System.getProperty(USERDIR);
		path = path + "/generators/uml2java/model/example.uml";
		URI modelURI = URI.createFileURI(URI.decode(path));

		File targetFolder = new File(System.getProperty(USERDIR) + "/generators/uml2java/result");

		File[] listFiles = targetFolder.listFiles();
		if (listFiles != null) {
			for (File file : listFiles) {
				removeDirectory(file);
			}
		}

		try {
			assertFalse(EMFPlugin.IS_ECLIPSE_RUNNING);
			GenerateJava generator = new GenerateJava(modelURI, targetFolder, new ArrayList<Object>());
			generator.doGenerate(new BasicMonitor());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		listFiles = targetFolder.listFiles();
		if (listFiles != null) {
			assertEquals(1, listFiles.length);
		}

	}

	public static boolean removeDirectory(File directory) {
		// System.out.println("removeDirectory " + directory);

		if (directory == null) {
			return false;
		}
		if (!directory.exists()) {
			return true;
		}
		if (!directory.isDirectory()) {
			return false;
		}

		String[] list = directory.list();

		// Some JVMs return null for File.list() when the
		// directory is empty.
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File entry = new File(directory, list[i]);

				// System.out.println("\tremoving entry " + entry);

				if (entry.isDirectory()) {
					if (!removeDirectory(entry)) {
						return false;
					}
				} else {
					if (!entry.delete()) {
						return false;
					}
				}
			}
		}

		return directory.delete();
	}
}
