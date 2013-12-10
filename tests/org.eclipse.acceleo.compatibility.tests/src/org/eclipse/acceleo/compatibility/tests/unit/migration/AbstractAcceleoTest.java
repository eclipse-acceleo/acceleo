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
package org.eclipse.acceleo.compatibility.tests.unit.migration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import junit.framework.AssertionFailedError;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.compatibility.tests.AcceleoCompatibilityTestPlugin;
import org.eclipse.acceleo.internal.compatibility.mtl.gen.Mt2mtl;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.framework.Bundle;

/**
 * Abstract class for Acceleo Engine unit tests.
 * 
 * @author <a href="mailto:freddy.allilaire@obeo.fr">Freddy Allilaire</a>
 */
@SuppressWarnings("nls")
public abstract class AbstractAcceleoTest {
	/**
	 * Plugin bundle test.
	 */
	protected final Bundle bundle = Platform.getBundle(AcceleoCompatibilityTestPlugin.PLUGIN_ID);

	/**
	 * Error message to report if compareDirectories method fails.
	 */
	protected String errorMessageForCompareDirectoriesMethod = "I/O exception while comparing reference and generated directories";

	/**
	 * Path of emtl model.
	 */
	protected String emtlModel;

	/**
	 * Path of mtl expected folder.
	 */
	protected String mtlExpected;

	/**
	 * File of the generation root.
	 */
	protected File generationRoot;

	/**
	 * File of the emtl model root.
	 */
	protected File emtlModelRoot;

	/**
	 * File of the mtl expected root.
	 */
	protected File mtlExpectedRoot;

	/**
	 * Acceleo Compatibility Plugin Test root URL.
	 */
	protected final URL pluginRoot = bundle.getEntry("/");

	/**
	 * File of the expected root.
	 */
	protected File expectedRoot;

	/**
	 * Path of the reference root.
	 */
	protected String referenceRootPath;

	/** Resource set we'll use throughout the test sub-classes. */
	protected final ResourceSet resourceSet = new ResourceSetImpl();

	/**
	 * A tool method to get the content of the file specified by its absolute path.
	 * 
	 * @param fileName
	 *            the absolute path of the file
	 * @return the content of the file
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected static String getAbsoluteFileContent(String fileName) throws IOException {
		return getAbsoluteFileContent(fileName, null);
	}

	/**
	 * A tool method to get the content of the file specified by its absolute path. Uses the given encoding to
	 * read the file.
	 * 
	 * @param fileName
	 *            the absolute path of the file
	 * @param charset
	 *            Charset that's to be used for reading this file.
	 * @return the content of the file
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected static String getAbsoluteFileContent(String fileName, String charset) throws IOException {
		StringBuilder buffer = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			Reader reader;
			if (charset != null) {
				InputStream inputStream = new FileInputStream(fileName);
				reader = new InputStreamReader(inputStream, charset);
			} else {
				reader = new FileReader(fileName);
			}
			bufferedReader = new BufferedReader(reader);
			String line = bufferedReader.readLine();
			while (line != null) {
				buffer.append(line);
				line = bufferedReader.readLine();
			}
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		return buffer.toString();
	}

	/**
	 * This must be overriden to return the test name.
	 * 
	 * @return test name of the results.
	 */
	public abstract String getTestName();

	/**
	 * Get the folder where files are generated.
	 * 
	 * @return the folder path.
	 */
	public String getResultPath() {
		return "/data/" + getTestName() + "/mtlGenerated";
	}

	/**
	 * Get the folder where are located expected mtl file.
	 * 
	 * @return the folder path.
	 */
	public String getMtlExpectedPath() {
		return "/data/" + getTestName() + "/mtlExpected";
	}

	/**
	 * Get the path of emt model file.
	 * 
	 * @return the emt path file.
	 */
	public String getEmtlModelPath() {
		return "/data/" + getTestName() + "/emt/chain.emt";
	}

	/**
	 * This should be called by each test to empty its target generation root prior to the test.
	 */
	protected void cleanMtlGenerationRoot() {
		File file;
		try {
			file = new File(getMtlTargetRootPath());
			if (file.isDirectory()) {
				final File[] children = getFiles(file);
				for (File child : children) {
					deleteFile(child);
				}
			}
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * This will call the Acceleo service to generate conversion from mt to mtl.
	 * 
	 * @param strModel
	 *            Path of emt model.
	 * @param strTraget
	 *            Path of folder where generate mtl.
	 */
	protected void generateEmt() {
		URI model = null;

		File folder = generationRoot;
		Mt2mtl mt2mtl;
		try {
			model = URI.createFileURI(getEmtlModelRootPath());
			List<String> arguments = new ArrayList<String>();
			mt2mtl = new Mt2mtl(model, folder, arguments);
			mt2mtl.doGenerate(null);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Generic Test launcher.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	public void genericTest() throws IOException {

		generationRoot = new File(getMtlTargetRootPath());
		mtlExpectedRoot = new File(getMtlExpectedRootPath());

		cleanMtlGenerationRoot();
		try {
			generateEmt();
			compareDirectories(mtlExpectedRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}
	}

	/**
	 * Return the files contained by <code>directory</code>, not including VCS directories.
	 * 
	 * @param directory
	 *            The directory we seek children of.
	 * @return All children of the given directory that do not correspond to VCS directories.
	 */
	protected File[] getFiles(File directory) {
		if (!directory.exists()) {
			return new File[0];
		}
		File[] children = directory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if (!pathname.getAbsolutePath().matches("^.*(CVS|\\\\.svn)$")) {
					return true;
				}
				return false;
			}
		});
		return children;
	}

	/**
	 * Compare two directories.
	 * 
	 * @param refDir
	 *            reference directory containing reference results
	 * @param genDir
	 *            generation directory
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected void compareDirectories(File refDir, File genDir) throws IOException {
		boolean compared = false;

		// Does not accept directories named ".svn" or "CVS"
		File[] children = getFiles(refDir);
		File refDirChild = null;
		if (children != null) {
			for (File child : children) {
				String dir2fileName = genDir.getAbsolutePath() + File.separator + child.getName();
				if (child.isDirectory()) {
					refDirChild = new File(dir2fileName);
					if (refDirChild.exists()) {
						compareDirectories(child, refDirChild);
						compared = true;
					} else {
						compared = false;
					}
				} else if (!child.getName().endsWith(IAcceleoConstants.EMTL_FILE_EXTENSION)) {
					assertEquals(dir2fileName, deleteWhitespaces(getAbsoluteFileContent(child
							.getAbsolutePath())), deleteWhitespaces(getAbsoluteFileContent(dir2fileName)));
					compared = true;
				} else {
					compared = true;
				}
			}
		}
		if (!compared) {
			fail("The reference at " + refDir.getAbsolutePath() + " isn't the same of result at "
					+ genDir.getAbsolutePath());
		}
	}

	/**
	 * Deletes all white spaces of the given string.
	 * 
	 * @param text
	 *            is the text
	 * @return the text without white spaces
	 */
	private String deleteWhitespaces(String text) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (!Character.isWhitespace(c)) {
				result.append(c);
			}
		}
		return result.toString();
	}

	/**
	 * Creates a File from a file path.
	 * 
	 * @param pathName
	 *            is the path of the file
	 * @return a File from a file path
	 */
	protected File createFile(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			return new File(fileLocation);
		} catch (IOException e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	/**
	 * Creates an URI from a file path.
	 * 
	 * @param pathName
	 *            is the path of the file
	 * @return an URI from a file path
	 */
	protected URI createFileURI(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			return URI.createFileURI(fileLocation);
		} catch (IOException e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	/**
	 * Returns reference root path.
	 * 
	 * @return Mtl generation folder root path
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected String getMtlTargetRootPath() throws IOException {
		return FileLocator.resolve(pluginRoot).getFile() + getResultPath();
	}

	/**
	 * Returns reference root path.
	 * 
	 * @return Mtl generation folder root path
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected String getMtlExpectedRootPath() throws IOException {
		return FileLocator.resolve(pluginRoot).getFile() + getMtlExpectedPath();
	}

	/**
	 * Returns emtl model root path.
	 * 
	 * @return emtl model root path
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected String getEmtlModelRootPath() throws IOException {
		return FileLocator.resolve(pluginRoot).getFile() + "/" + getEmtlModelPath();
	}

	/**
	 * Deletes the given {@link File}. If <code>file</code> is a directory, it will also recursively delete
	 * all of its content prior to deletion.
	 * 
	 * @param file
	 *            The file that is to be deleted.
	 */
	private void deleteFile(File file) {
		if (file.isDirectory()) {
			final File[] children = getFiles(file);
			for (File child : children) {
				deleteFile(child);
			}
		} else {
			file.delete();
		}
	}
}
