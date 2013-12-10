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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.common.tests.AcceleoCommonTestPlugin;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the behavior of {@link ModelUtils#getModelsFrom(File)}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class GetModelsFromTest {
	/** Full path to the directory containing the models. */
	private static final String INPUT_DIRECTORY = "/data/modelutils";

	/**
	 * This array contains references to all subdirectories of {@link #INPUT_DIRECTORY} that does not contain
	 * models.
	 */
	private ArrayList<File> invalidDirectories = new ArrayList<File>();

	/**
	 * This array contains references to all subdirectories of {@link #INPUT_DIRECTORY} directly containing
	 * non-regression models.
	 */
	private ArrayList<File> modelDirectories = new ArrayList<File>();

	/** Names of directories we shouldn't be able to read. */
	private String[] unreadableDirectories = {"", "\r\n", "/lost+found/", };

	/**
	 * Tests {@link ModelUtils#getModelsFrom(File)} with invalid directories. Expects an empty list to be
	 * returned.
	 */
	@Test
	public void testGetModelsFromInvalidDirectory() {
		List<EObject> expectedResult = Collections.emptyList();
		for (File invalidDirectory : invalidDirectories) {
			try {
				assertEquals("Unexpected result of getModelsFrom() with invalid directory.", expectedResult,
						ModelUtils.getModelsFrom(invalidDirectory, new ResourceSetImpl()));
			} catch (IOException e) {
				fail("Unexpected IOException thrown while loading models from an invalid directory.");
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#getModelsFrom(File)} with <code>null</code> directory. Expects a
	 * {@link NullPointerException} to be thrown.
	 */
	@Test
	public void testGetModelsFromNullDirectory() {
		try {
			ModelUtils.getModelsFrom(null, new ResourceSetImpl());
			fail("Expected NullPointerException hasn't been thrown.");
		} catch (NullPointerException e) {
			// We expected this
		} catch (IOException e) {
			fail("Unexpected IOException has been thrown.");
		}
	}

	/**
	 * Tests {@link ModelUtils#getModelsFrom(File)} with directories that cannot be read. Expects an empty
	 * list to be returned.
	 */
	@Test
	public void testGetModelsFromUnreadableDirectory() {
		List<EObject> expectedResult = Collections.emptyList();
		for (String unreadableDirectory : unreadableDirectories) {
			try {
				assertEquals("Unexpected result of getModelsFrom() with unreadable directory.",
						expectedResult, ModelUtils.getModelsFrom(new File(unreadableDirectory),
								new ResourceSetImpl()));
			} catch (IOException e) {
				fail("Unexpected IOException has been thrown.");
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#getModelsFrom(File)} with valid directories. Expects the returned list to have
	 * a size equal to the number of files contained by the given directory, and all of the list's objects to
	 * have an associated resource.
	 */
	@Test
	public void testGetModelsFromValidDirectory() {
		for (File modelDirectory : modelDirectories) {
			List<EObject> result = null;
			final ResourceSet resourceSet = new ResourceSetImpl();
			try {
				result = ModelUtils.getModelsFrom(modelDirectory, resourceSet);
			} catch (IOException e) {
				fail("Unexpected IOException thrown while loading models from a valid folder.");
			}

			// Keeps compiler happy
			assert result != null;

			int expectedSize = 0;
			for (File aFile : modelDirectory.listFiles()) {
				if (!aFile.isDirectory() && !aFile.getName().startsWith(String.valueOf('.'))) {
					expectedSize++;
				}
			}
			assertSame("The returned list doesn't contain the expected number of objects.", expectedSize,
					result.size());

			for (EObject loadedModel : result) {
				assertNotNull("Loaded EObject isn't associated to a resource.", loadedModel.eResource());
				assertEquals("Model hasn't been loaded in the accurate resource set.", resourceSet,
						loadedModel.eResource().getResourceSet());
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#getModelsFrom(File)} with valid directories but invalid file extensions.
	 * Expects the returned list to be empty.
	 */
	@Test
	public void testGetModelsFromValidDirectoryInvalidExtension() {
		for (File modelDirectory : modelDirectories) {
			List<EObject> result = null;
			final ResourceSet resourceSet = new ResourceSetImpl();
			try {
				result = ModelUtils.getModelsFrom(modelDirectory, "ThisCannotBeAValidExtension", resourceSet);
			} catch (IOException e) {
				fail("Unexpected IOException thrown while loading models with invalid extensions.");
			}

			// Keeps compiler happy
			assert result != null;

			assertSame("Resulting list doesn't contain the expected number of objects.", 0, result.size());
		}
	}

	/**
	 * Tests {@link ModelUtils#getModelsFrom(File)} with valid directories and no resourceSet. Expects the
	 * returned list to have a size equal to the number of files contained by the given directory, and all of
	 * the list's objects to have an associated resource. All resources are expected to be contained by the
	 * same resourceSet.
	 */
	@Test
	public void testGetModelsFromValidDirectoryNullResourceSet() {
		for (File modelDirectory : modelDirectories) {
			List<EObject> result = null;
			try {
				result = ModelUtils.getModelsFrom(modelDirectory, null);
			} catch (IOException e) {
				fail("Unexpected IOException thrown while loading models.");
			}

			// Keeps compiler happy
			assert result != null;

			int expectedSize = 0;
			for (File aFile : modelDirectory.listFiles()) {
				if (!aFile.isDirectory() && !aFile.getName().startsWith(String.valueOf('.'))) {
					expectedSize++;
				}
			}
			assertSame("Resulting list doesn't contain the expected number of objects.", expectedSize, result
					.size());

			ResourceSet resourceSet = null;
			for (EObject loadedModel : result) {
				assertNotNull("Loaded EObject hasn't been associated to a resource.", loadedModel.eResource());
				if (resourceSet == null) {
					resourceSet = loadedModel.eResource().getResourceSet();
				} else {
					assertEquals("Model hasn't been loaded in the accurate resource set.", resourceSet,
							loadedModel.eResource().getResourceSet());
				}
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#getModelsFrom(File)} with valid directories and a valid file extension. Expects
	 * the returned list to have a size equal to the number of files of that given extension contained by the
	 * given directory, and all of the list's objects to have an associated resource.
	 */
	@Test
	public void testGetModelsFromValidDirectoryValidExtension() {
		for (File modelDirectory : modelDirectories) {
			List<EObject> result = null;
			final ResourceSet resourceSet = new ResourceSetImpl();
			final String fileExtension = "ecore";
			try {
				result = ModelUtils.getModelsFrom(modelDirectory, fileExtension, resourceSet);
			} catch (IOException e) {
				fail("Unexpected IOException thrown while loading models.");
			}

			// Keeps compiler happy
			assert result != null;

			int expectedSize = 0;
			for (File aFile : modelDirectory.listFiles()) {
				if (!aFile.isDirectory() && !aFile.getName().startsWith(String.valueOf('.'))
						&& aFile.getName().endsWith(fileExtension)) {
					expectedSize++;
				}
			}
			assertSame("Resulting list doesn't contain the expected number of objects for this extension.",
					expectedSize, result.size());

			for (EObject loadedModel : result) {
				assertNotNull("Loaded EObject isn't associated to a resource.", loadedModel.eResource());
				assertEquals("Model hasn't been loaded in the expected resource set.", resourceSet,
						loadedModel.eResource().getResourceSet());
			}
		}
	}

	/**
	 * Default constructor. Scans for model files in {@link #INPUT_DIRECTORY}.
	 */
	@Before
	public void setUp() {
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
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() {
		invalidDirectories.clear();
		modelDirectories.clear();
	}

	/**
	 * Called from setup, this method allows retrieval of references to the files corresponding to the
	 * non-regression models.
	 * 
	 * @param folder
	 *            Folder in which model files are to be found.
	 */
	private void scanForModels(File folder) {
		final File[] subFolders = listDirectories(folder);
		if (subFolders.length != 0) {
			invalidDirectories.add(folder);
			for (File aSubFolder : subFolders) {
				scanForModels(aSubFolder);
			}
		} else if (folder.listFiles().length == 0) {
			invalidDirectories.add(folder);
		} else if (folder.exists() && folder.isDirectory()) {
			final File[] files = folder.listFiles();
			for (File aFile : files) {
				// All directories containing at least one non-folder file is considered to contain models.
				if (!aFile.isDirectory() && !aFile.getName().startsWith(String.valueOf('.'))) {
					modelDirectories.add(folder);
					break;
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
