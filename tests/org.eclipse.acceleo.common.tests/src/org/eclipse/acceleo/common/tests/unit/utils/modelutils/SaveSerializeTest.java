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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.acceleo.common.tests.AcceleoCommonTestPlugin;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Test;

/**
 * Tests the behavior of {@link ModelUtils#save(EObject, String)} and {@link ModelUtils#serialize(EObject)}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class SaveSerializeTest {
	/** Full path to the directory containing the non-regression models. */
	private static final String INPUT_DIRECTORY = "/data/modelutils";

	/**
	 * This array contains references to the root of all the models contained by {@link INPUT_DIRECTORY} or
	 * its subfolders.
	 */
	private List<EObject> models = new ArrayList<EObject>();

	/** Full path to the directory where we'll put the temporary saved files. */
	private String outputDirectory;

	/**
	 * Default constructor. Scans for model files in {@link #INPUT_DIRECTORY}.
	 */
	public SaveSerializeTest() {
		File inputDir = null;
		try {
			inputDir = new File(FileLocator.toFileURL(
					AcceleoCommonTestPlugin.getDefault().getBundle().getEntry(INPUT_DIRECTORY)).getFile());
			outputDirectory = FileLocator.toFileURL(
					AcceleoCommonTestPlugin.getDefault().getBundle().getEntry("/data")).getFile();
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
	 * Tests {@link ModelUtils#save(EObject, String)} with <code>null</code> as the object to save. Expects a
	 * {@link NullPointerException} to be thrown.
	 */
	@Test
	public void testSaveNullRoot() {
		try {
			ModelUtils.save(null, outputDirectory);
			fail("Expected NullPointerException hasn't been thrown by save().");
		} catch (NullPointerException e) {
			// We were expecting this
		} catch (IOException e) {
			fail("Unexpected IOException has been thrown by save().");
		}
	}

	/**
	 * Tests {@link ModelUtils#save(EObject, String)} with a valid EObject and a valid path where it has to be
	 * saved. Expects a non-empty File to be created at the specified path.
	 */
	@Test
	public void testSaveValidEObject() {
		for (EObject modelRoot : models) {
			FileInputStream fsInput = null;
			File savedFile = null;
			try {
				final String filePath = modelRoot.eResource().getURI().toFileString();
				final String filename = filePath.substring(filePath.lastIndexOf(File.separatorChar) + 1);
				ModelUtils.save(modelRoot, outputDirectory + File.separatorChar + filename);

				savedFile = new File(outputDirectory + File.separatorChar + filename);
				assertTrue("File hasn't been saved.", savedFile.exists() && savedFile.isFile());

				fsInput = new FileInputStream(savedFile);
				assertNotSame("Saved file is empty.", -1, fsInput.read());

				// Cleans up before next loop

			} catch (IOException e) {
				fail("Unexpected IOException has been thrown by a valid call to save().");
			} finally {
				if (fsInput != null) {
					try {
						fsInput.close();
					} catch (IOException e) {
						fail("Unexpected IOException has been thrown by a valid call to save().");
					}
				}
				if (savedFile != null) {
					savedFile.delete();
				}
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#save(EObject, String)} with <code>null</code> as the path where to save.
	 * Expects a {@link NullPointerException} to be thrown.
	 */
	@Test
	public void testSaveValidEObjectNullPath() {
		for (EObject modelRoot : models) {
			try {
				ModelUtils.save(modelRoot, null);
				fail("Expected NullPointerException hasn't been thrown by save().");
			} catch (NullPointerException e) {
				// We were expecting this
			} catch (IOException e) {
				fail("Unexpected IOException has been thrown by save().");
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#serialize(EObject)} with <code>null</code> as the object to serialize. Expects
	 * a {@link NullPointerException} to be thrown.
	 */
	@Test
	public void testSerializeNullRoot() {
		try {
			ModelUtils.serialize(null);
			fail("Expected NullPointerException hasn't been thrown by serialize() operation.");
		} catch (NullPointerException e) {
			// We were expecting this
		} catch (IOException e) {
			fail("Unexpected IOException has been thrown by serialize() operation.");
		}
	}

	/**
	 * Tests {@link ModelUtils#serialize(EObject)} with a valid EObject to serialize. Expects a non-empty
	 * String to be returned.
	 */
	@Test
	public void testSerializeValidEObject() {
		for (EObject modelRoot : models) {
			try {
				final String result = ModelUtils.serialize(modelRoot);
				assertNotNull("EObject hasn't been serialized by serialize().", result);
				assertFalse("EObject has been serialized as an empty String", "".equals(result));
				assertTrue("EObject hasn't been serialized as an XML object.", result.startsWith("<?xml"));
			} catch (IOException e) {
				fail("Unexpected IOException has been thrown by a valid call to save().");
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
					try {
						models.add(ModelUtils.load(aFile, new ResourceSetImpl()));
					} catch (IOException e) {
						// Shouldn't happen
						assert false;
					}
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
