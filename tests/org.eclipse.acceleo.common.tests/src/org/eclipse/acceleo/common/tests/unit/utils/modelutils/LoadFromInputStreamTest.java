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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.acceleo.common.tests.AcceleoCommonTestPlugin;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.junit.Test;

/**
 * Tests the behavior of {@link ModelUtils#load(InputStream, String, ResourceSet)}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class LoadFromInputStreamTest {
	/** Full path to the directory containing the non-regression models. */
	private static final String INPUT_DIRECTORY = "/data/modelutils";

	/** Contains invalid file names for model loading. */
	private static final String[] INVALID_FILENAMES = {"", "model", "ecaure", };

	/** Message displayed when an unexpected {@link IOException} is thrown. */
	private static final String MESSAGE_FILENOTFOUNDEXCEPTION_UNEXPECTED = "Unexpected FileNotFoundException has been thrown while loading models from stream.";

	/** Message displayed when an unexpected {@link IOException} is thrown. */
	private static final String MESSAGE_IOEXCEPTION_UNEXPECTED = "Unexpected IOException has been thrown.";

	/** Message displayed when an expected {@link NullPointerException} isn't thrown. */
	private static final String MESSAGE_NULLPOINTER_EXPECTED = "Expected NullPointerException hasn't been thrown.";

	/**
	 * This array contains references to all the models contained by {@link INPUT_DIRECTORY} or its
	 * subfolders.
	 */
	private List<File> models = new ArrayList<File>();

	/**
	 * Default constructor. Scans for model files in {@link #INPUT_DIRECTORY}.
	 */
	public LoadFromInputStreamTest() {
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
	 * Tests {@link ModelUtils#load(InputStream, String, ResourceSet)} with <code>null</code> as its
	 * InputStream. Expects a {@link NullPointerException} to be thrown whatever the two other specified
	 * arguments are.
	 */
	@Test
	public void testLoadModelFromNullInputStream() {
		for (String invalidFileName : INVALID_FILENAMES) {
			// First try will be with null resourceSet
			try {
				ModelUtils.load(null, invalidFileName, null);
				fail(MESSAGE_NULLPOINTER_EXPECTED);
			} catch (NullPointerException e) {
				// This was expected
			} catch (IOException e) {
				fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
			}

			// We then try with a valid resourceSet
			final ResourceSet resourceSet = new ResourceSetImpl();
			try {
				ModelUtils.load(null, invalidFileName, resourceSet);
				fail(MESSAGE_NULLPOINTER_EXPECTED);
			} catch (NullPointerException e) {
				// This was expected
			} catch (IOException e) {
				fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#load(InputStream, String, ResourceSet)} with valid inputStream and resourceSet.
	 * As the filename given is invalid, expects the model to be loaded as an XMI file.
	 * 
	 * @throws IOException
	 *             Allows us not to catch it. Test just fails if thrown.
	 */
	@Test
	public void testLoadModelFromValidInputInvalidExtension() throws IOException {
		for (File model : models) {
			try {
				for (String invalidFileName : INVALID_FILENAMES) {
					final FileInputStream fsInput = new FileInputStream(model);
					final BufferedInputStream buffInput = new BufferedInputStream(fsInput);
					final ResourceSet resourceSet = new ResourceSetImpl();
					final EObject result = ModelUtils.load(buffInput, invalidFileName, resourceSet);

					assertNotNull("ModelUtils didn't load its target model.", result);
					assertEquals("Loaded object was associated to an unexpected resourceSet.", resourceSet,
							result.eResource().getResourceSet());
					assertTrue("Model loaded as an unexpected type of resource.",
							result.eResource() instanceof XMIResource);

					buffInput.close();
					fsInput.close();
				}
			} catch (FileNotFoundException e) {
				fail(MESSAGE_FILENOTFOUNDEXCEPTION_UNEXPECTED);
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#load(InputStream, String, ResourceSet)} with a valid InputStream and
	 * <code>null</code> as a resourceSet. Expects a {@link NullPointerException} to be thrown no matter what
	 * the filename is.
	 * 
	 * @throws IOException
	 *             Allows us not to catch it. Test just fails if thrown.
	 */
	@Test
	public void testLoadModelFromValidInputNullResourceSet() throws IOException {
		for (File model : models) {
			FileInputStream fsInput = null;
			BufferedInputStream buffInput = null;
			try {
				fsInput = new FileInputStream(model);
				buffInput = new BufferedInputStream(fsInput);

				// First executes tests with invalid file names
				for (String invalidFileName : INVALID_FILENAMES) {
					try {
						ModelUtils.load(buffInput, invalidFileName, null);
						fail(MESSAGE_NULLPOINTER_EXPECTED);
					} catch (NullPointerException e) {
						// This was expected
					}
				}

				// Then with valid file names
				try {
					ModelUtils.load(buffInput, model.getName(), null);
					fail(MESSAGE_NULLPOINTER_EXPECTED);
				} catch (NullPointerException e) {
					// This was expected
				}
			} catch (FileNotFoundException e) {
				fail(MESSAGE_FILENOTFOUNDEXCEPTION_UNEXPECTED);
			} finally {
				// fsInput cannot be null if buffInput isn't, but test keeps compiler happy
				if (buffInput != null && fsInput != null) {
					buffInput.close();
					fsInput.close();
				}
			}
		}
	}

	/**
	 * Tests {@link ModelUtils#load(InputStream, String, ResourceSet)} with valid inputStream and resourceSet.
	 * As the filename given is valid, expects the model to be loaded in an appropriate resource.
	 * 
	 * @throws IOException
	 *             Allows us not to catch it. Test just fails if thrown.
	 */
	@Test
	public void testLoadModelFromValidInputValidExtension() throws IOException {
		for (File model : models) {
			try {
				final FileInputStream fsInput = new FileInputStream(model);
				final BufferedInputStream buffInput = new BufferedInputStream(fsInput);
				final ResourceSet resourceSet = new ResourceSetImpl();
				final EObject result = ModelUtils.load(buffInput, model.getName(), resourceSet);

				assertNotNull("ModelUtils didn't load its target model.", result);
				assertEquals("Loaded object was associated to an unexpected resourceSet.", resourceSet,
						result.eResource().getResourceSet());

				buffInput.close();
				fsInput.close();
			} catch (FileNotFoundException e) {
				fail(MESSAGE_FILENOTFOUNDEXCEPTION_UNEXPECTED);
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
