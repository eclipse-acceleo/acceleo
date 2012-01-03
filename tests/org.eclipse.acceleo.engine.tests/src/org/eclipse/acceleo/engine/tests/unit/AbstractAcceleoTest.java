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
package org.eclipse.acceleo.engine.tests.unit;

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
import java.util.Map;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.framework.Bundle;

/**
 * Abstract class for Acceleo Engine unit tests.
 * 
 * @author <a href="mailto:freddy.allilaire@obeo.fr">Freddy Allilaire</a>
 */
public abstract class AbstractAcceleoTest extends TestCase {
	/**
	 * EMTL extension.
	 */
	protected static final String EMTL_EXTENSION = "emtl"; //$NON-NLS-1$

	/**
	 * Location of the target model. Clients can load a distinct model in their tests' default constructor if
	 * needed.
	 */
	private static final String DEFAULT_MODEL_LOCATION = "data/target.ecore"; //$NON-NLS-1$

	/**
	 * Plugin bundle.
	 */
	protected final Bundle bundle = Platform.getBundle(AcceleoEngineTestPlugin.PLUGIN_ID);

	/**
	 * Error message to report if compareDirectories method fails.
	 */
	protected String errorMessageForCompareDirectoriesMethod = "I/O exception while comparing reference and generated directories"; //$NON-NLS-1$

	/**
	 * File of the generation root.
	 */
	protected File generationRoot;

	/** This is the input model as loaded from initializer. */
	protected EObject inputModel;

	/** This is the module loaded from {@link #moduleLocation} at setup. */
	protected Module module;

	/**
	 * Acceleo Engine Plugin Test root URL.
	 */
	protected final URL pluginRoot = bundle.getEntry("/"); //$NON-NLS-1$

	/**
	 * File of the reference root.
	 */
	protected File referenceRoot;

	/**
	 * Path of the reference root.
	 */
	protected String referenceRootPath;

	/** Resource set we'll use throughout the test sub-classes. */
	protected final ResourceSet resourceSet = new ResourceSetImpl();

	/** Instance of the default generation strategy. */
	protected IAcceleoGenerationStrategy defaultStrategy;

	/** Instance of the preview generation strategy. */
	protected IAcceleoGenerationStrategy previewStrategy;

	{
		try {
			final URI inputModelURI = URI.createPlatformPluginURI('/' + AcceleoEngineTestPlugin.PLUGIN_ID
					+ '/' + DEFAULT_MODEL_LOCATION, true);
			inputModel = ModelUtils.load(inputModelURI, resourceSet);
		} catch (IOException e) {
			fail("Error loading the input model."); //$NON-NLS-1$
		}
	}

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
	 * Returns the location of the module on which tests templates will be called.
	 * 
	 * @return Location of the test module.
	 */
	public abstract String getModuleLocation();

	/**
	 * This must be overriden to return the save location for the test reference and generation results.
	 * 
	 * @return Save location of the results.
	 */
	public abstract String getResultPath();

	/**
	 * This should be called by each test to empty its target generation root prior to the test.
	 */
	protected void cleanGenerationRoot() {
		if (generationRoot.isDirectory()) {
			final File[] children = getFiles(generationRoot);
			for (File child : children) {
				deleteFile(child);
			}
		}
	}

	/**
	 * This will call the Acceleo service to generate the given template.
	 * 
	 * @param templateName
	 *            Name of the template that is to be evaluated.
	 * @param strategy
	 *            Strategy that's to be used for this generation.
	 */
	protected Map<String, String> generate(String templateName, IAcceleoGenerationStrategy strategy) {
		return new AcceleoService(strategy).doGenerate(module, templateName, inputModel, generationRoot,
				new BasicMonitor());
	}

	/**
	 * This will call the Acceleo service to generate the given template.
	 * 
	 * @param service
	 *            Instance of the AcceleoService that is to be used to launch the generation.
	 * @param templateName
	 *            Name of the templat that is to be evaluated.
	 */
	protected Map<String, String> generate(AcceleoService service, String templateName) {
		return service.doGenerate(module, templateName, inputModel, generationRoot, new BasicMonitor());
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
				if (!pathname.getAbsolutePath().matches("^.*(CVS|\\\\.svn)$")) { //$NON-NLS-1$
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

		if (children != null) {
			for (File child : children) {
				String dir2fileName = genDir.getAbsolutePath() + File.separator + child.getName();
				if (child.isDirectory()) {
					compareDirectories(child, new File(dir2fileName));
				} else if (child.getName().endsWith(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION)) {
					assertEquals(dir2fileName, deleteWhitespaces(removeTimeStamp(getAbsoluteFileContent(child
							.getAbsolutePath()))),
							deleteWhitespaces(removeTimeStamp(getAbsoluteFileContent(dir2fileName))));
				} else {
					assertEquals(dir2fileName, deleteWhitespaces(getAbsoluteFileContent(child
							.getAbsolutePath())), deleteWhitespaces(getAbsoluteFileContent(dir2fileName)));
					compared = true;
				}
			}
		}
		if (!compared) {
			fail("Couldn't compare the reference at " + refDir.getAbsolutePath() + " for result " //$NON-NLS-1$ //$NON-NLS-2$
					+ genDir.getAbsolutePath());
		}
	}

	/**
	 * Compare two directories, ensuring the files are the same according to the given encoding.
	 * 
	 * @param refDir
	 *            reference directory containing reference results
	 * @param genDir
	 *            generation directory
	 * @param charset
	 *            Charset of the files.
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected void compareDirectories(File refDir, File genDir, String charset) throws IOException {
		boolean compared = false;

		// Does not accept directories named ".svn" or "CVS"
		File[] children = getFiles(refDir);

		if (children != null) {
			for (File child : children) {
				String dir2fileName = genDir.getAbsolutePath() + File.separator + child.getName();
				if (child.isDirectory()) {
					compareDirectories(child, new File(dir2fileName), charset);
				} else if (child.getName().endsWith(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION)) {
					assertEquals(dir2fileName, deleteWhitespaces(removeTimeStamp(getAbsoluteFileContent(child
							.getAbsolutePath(), charset))),
							deleteWhitespaces(removeTimeStamp(getAbsoluteFileContent(dir2fileName, charset))));
				} else {
					assertEquals(dir2fileName, deleteWhitespaces(getAbsoluteFileContent(child
							.getAbsolutePath(), charset)), deleteWhitespaces(getAbsoluteFileContent(
							dir2fileName, charset)));
					compared = true;
				}
			}
		}
		if (!compared) {
			fail("Couldn't compare the reference at " + refDir.getAbsolutePath() + " for result " //$NON-NLS-1$ //$NON-NLS-2$
					+ genDir.getAbsolutePath());
		}
	}

	/**
	 * Removes the time stamp inserted at the beginning of lost files.
	 * 
	 * @param text
	 *            lost file content.
	 */
	private String removeTimeStamp(String text) {
		if (text.contains("===============================================================")) { //$NON-NLS-1$
			return text.replaceAll("[^=]*?={10,}(?:\\n|\\r|\\r\\n)?(?s)(.*)", "$1"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return text;
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
	 * Returns generation root path.
	 * 
	 * @param unitTestName
	 *            is the name of current unit test
	 * @return generation root path
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected String getGenerationRootPath(String unitTestName) throws IOException {
		return FileLocator.resolve(pluginRoot).getFile() + "/src-gen/" + getResultPath() + "/GenerationTest/" //$NON-NLS-1$ //$NON-NLS-2$
				+ unitTestName;
	}

	/**
	 * Returns reference root path.
	 * 
	 * @param unitTestName
	 *            is the name of current unit test
	 * @return reference root path
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected String getReferenceRootPath(String unitTestName) throws IOException {
		return FileLocator.resolve(pluginRoot).getFile() + "/src-gen/" //$NON-NLS-1$
				+ getResultPath() + "/ReferenceResult/" + unitTestName; //$NON-NLS-1$
	}

	/**
	 * This methods parses an Acceleo file and creates a model representation of it (.emtl).
	 * 
	 * @param mtlPath
	 *            is the path of Acceleo file
	 * @param resourceSet
	 *            is the resourceSet
	 * @return a model representation of Acceleo file
	 */
	protected Resource parse(String mtlPath) {
		File file = createFile(mtlPath);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(file);

		final URI moduleURI = URI.createPlatformPluginURI('/' + AcceleoEngineTestPlugin.PLUGIN_ID + '/'
				+ (new Path(mtlPath)).removeFileExtension().addFileExtension(EMTL_EXTENSION), true);

		Resource modelResource = ModelUtils.createResource(moduleURI, resourceSet);
		AcceleoParser parser = new AcceleoParser();
		assertNull(parser.getProblems(file));
		parser.parse(source, modelResource, new ArrayList<URI>());
		return modelResource;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		Resource modelResource = parse(getModuleLocation());
		EObject rootTemplate = modelResource.getContents().get(0);
		if (rootTemplate instanceof Module) {
			module = (Module)rootTemplate;
		} else {
			Assert.fail("Couldn't load the input template."); //$NON-NLS-1$
		}

		defaultStrategy = new DefaultStrategy();
		previewStrategy = new PreviewStrategy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		// Unload all but the input model
		for (Resource resource : resourceSet.getResources()) {
			if (resource != inputModel.eResource()) {
				resource.unload();
			}
		}
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
