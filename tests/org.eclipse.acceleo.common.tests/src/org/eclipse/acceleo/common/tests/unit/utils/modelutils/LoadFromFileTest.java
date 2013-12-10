/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.tests.unit.utils.modelutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.acceleo.common.tests.AcceleoCommonTestPlugin;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Test;

/**
 * Tests the behavior of {@link ModelUtils#load(File)}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class LoadFromFileTest {
	/** Full path to the directory containing the non-regression models. */
	private static final String INPUT_DIRECTORY = "/data/modelutils";

	/** Message displayed when an unexpected {@link IOException} is thrown. */
	private static final String MESSAGE_IOEXCEPTION_UNEXPECTED = "Unexpected IOException has been thrown.";

	/** Message displayed when an expected {@link NullPointerException} isn't thrown. */
	private static final String MESSAGE_NULLPOINTER_EXPECTED = "Expected NullPointerException hasn't been thrown.";

	/** This array contains paths to unreadable or inexistant files. */
	private String[] invalidFiles = {"", "/etc/shadow", "/etc/sudoers", "\r\n",
			File.listRoots()[0].getAbsolutePath() + "nofolder", };

	/**
	 * This array contains references to all the models contained by {@link INPUT_DIRECTORY} or its
	 * subfolders.
	 */
	private List<File> models = new ArrayList<File>();

	/**
	 * Default constructor. Scans for model files in {@link #INPUT_DIRECTORY}.
	 */
	public LoadFromFileTest() {
		File inputDir = null;
		try {
			inputDir = new File(FileLocator.toFileURL(
					AcceleoCommonTestPlugin.getDefault().getBundle().getEntry(INPUT_DIRECTORY)).getFile());
		} catch (IOException e) {
			// shouldn't happen
			assert false;
		}

		final File[] directories = listDirectories(inputDir);
		if (directories != null) {
			for (int i = 0; i < directories.length; i++) {
				scanForModels(directories[i]);
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#load(File, ResourceSet)} with an invalid model file to be loaded and no
	 * resourceSet. Expects a {@link NullPointerException} to be thrown.
	 */
	@Test
	public void testLoadModelFromInvalidFileNullResourceSet() {
		for (String modelFile : invalidFiles) {
			try {
				ModelUtils.load(new File(modelFile), null);
				fail(MESSAGE_NULLPOINTER_EXPECTED);
			} catch (NullPointerException e) {
				// This was expected
			} catch (IOException e) {
				fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#load(File, ResourceSet)} with an invalid model file to be loaded and a valid
	 * resourceSet. Expects an {@link IOException} to be thrown.
	 */
	@Test
	public void testLoadModelFromInvalidFileValidResourceSet() {
		for (String modelFile : invalidFiles) {
			final String errMsg = "Resource '" + modelFile + "' does not exist.";
			try {
				final ResourceSet resourceSet = new ResourceSetImpl();
				ModelUtils.load(new File(modelFile), resourceSet);
				fail("Expected IOException hasn't been thrown.");
			} catch (NullPointerException e) {
				fail("Unexpected NullPointerException thrown when loading models from invalid files.");
			} catch (IOException e) {
				// This was expected
			} catch (WrappedException e) {
				if (e.getCause() instanceof IOException) {
					// This was expected
				} else if (e.getCause() instanceof CoreException && errMsg.equals(e.getCause().getMessage())) {
					// This was expected
				} else {
					throw e;
				}
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#load(File, ResourceSet)} with <code>null</code> as the file to be loaded.
	 * Expects a {@link NullPointerException} to be thrown no matter the specified resourceSet.
	 */
	@Test
	public void testLoadModelFromNullFile() {
		try {
			ModelUtils.load((File)null, null);
			fail(MESSAGE_NULLPOINTER_EXPECTED);
		} catch (NullPointerException e) {
			// This was expected
		} catch (IOException e) {
			fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
		}
	}

	/**
	 * Tests {@link ModelUtils#load(File, ResourceSet)} with a valid model file to be loaded and no
	 * resourceSet. Expects a {@link NullPointerException} to be thrown.
	 */
	@Test
	public void testLoadModelFromValidFileNullResourceSet() {
		for (File modelFile : models) {
			try {
				ModelUtils.load(modelFile, null);
				fail(MESSAGE_NULLPOINTER_EXPECTED);
			} catch (NullPointerException e) {
				// This was expected
			} catch (IOException e) {
				fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#load(File, ResourceSet)} with a valid model file and a valid resourceSet.
	 * Expects a non-null EObject associated to the given {@link ResourceSet} to be returned.
	 */
	@Test
	public void testLoadModelFromValidFileValidResourceSet() {
		for (File modelFile : models) {
			try {
				final ResourceSet resourceSet = new ResourceSetImpl();
				final EObject result = ModelUtils.load(modelFile, resourceSet);
				assertNotNull("ModelUtils didn't load its target model.", result);
				assertEquals("Loaded object was associated to an unexpected resourceSet.", resourceSet,
						result.eResource().getResourceSet());
				assertEquals("Loaded model isn't the expected one.", modelFile.getPath(), result.eResource()
						.getURI().toFileString());
			} catch (IOException e) {
				fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
			}
		}
	}

	/**
	 * Called from initializer, this method allows retrieval of references to the files corresponding to the
	 * non-regression models.
	 * 
	 * @param folder
	 *            Folder in which model files are to be found.
	 */
	private void scanForModels(File folder) {
		final File[] subFolders = listDirectories(folder);
		if (subFolders.length != 0) {
			for (File aSubFolder : subFolders) {
				scanForModels(aSubFolder);
			}
		} else if (folder.exists() && folder.isDirectory()) {
			final File[] files = folder.listFiles();
			for (File aFile : files) {
				// All files are considered models regardless of their extension
				if (!aFile.isDirectory() && !aFile.getName().startsWith(".")) {
					models.add(aFile);
				}
			}
		}
	}

	/**
	 * Lists all subdirectories contained within a given folder, with the exception of directories starting
	 * with a "." or directories named "CVS".
	 * 
	 * @param aDirectory
	 *            Directory from which we need to list subfolders.
	 * @return Array composed by all <code>aDirectory</code> subfolders.
	 */
	public static File[] listDirectories(File aDirectory) {
		File[] directories = null;

		if (aDirectory.exists() && aDirectory.isDirectory()) {
			directories = aDirectory.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.isDirectory() && !file.getName().startsWith(".") //$NON-NLS-1$
							&& !"CVS".equals(file.getName()); //$NON-NLS-1$
				}
			});
		}
		Arrays.sort(directories);
		return directories;
	}
}
