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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

import junit.framework.AssertionFailedError;

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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.After;
import org.junit.Before;
import org.osgi.framework.Bundle;

import com.google.common.io.Files;

/**
 * Abstract class for Acceleo Engine unit tests.
 * 
 * @author <a href="mailto:freddy.allilaire@obeo.fr">Freddy Allilaire</a>
 */
public abstract class AbstractAcceleoTest {
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

	/** Resource set we'll use throughout the test sub-classes. */
	protected final ResourceSet resourceSet = new ResourceSetImpl();

	/** Instance of the default generation strategy. */
	protected IAcceleoGenerationStrategy defaultStrategy;

	/** Instance of the preview generation strategy. */
	protected IAcceleoGenerationStrategy previewStrategy;

	protected URL referenceRootUrl;

	protected IProject project;

	protected String unitTestName;

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
	 * Returns the location of the module on which tests templates will be called.
	 * 
	 * @return Location of the test module.
	 */
	public abstract String getModuleLocation();

	/**
	 * This must be overriden to return the save location for the test reference.
	 * 
	 * @return Save location of the test reference.
	 */
	public abstract String getReferencePath();

	protected void init(String testName) {
		this.unitTestName = testName;
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(this.getReferencePath());
		if (project.exists()) {
			try {
				project.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				fail(e.getMessage());
			}
		}

		try {
			project.create(new NullProgressMonitor());
		} catch (CoreException e) {
			fail(e.getMessage());
		}

		this.generationRoot = project.getFolder(unitTestName).getLocation().toFile();
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

	protected void compareDirectories() {
		String bundlePath = "/src-gen/" + this.getReferencePath() + "/ReferenceResult/" + this.unitTestName; //$NON-NLS-1$//$NON-NLS-2$
		Enumeration<URL> entries = this.bundle.findEntries(bundlePath, "*", true); //$NON-NLS-1$
		while (entries.hasMoreElements()) {
			URL element = entries.nextElement();
			String fileContent = readFileFromURL(element);

			String fileName = element.getFile();
			int indexOf = fileName.lastIndexOf('/');
			if (indexOf != -1) {
				fileName = fileName.substring(indexOf + 1);
			}
			File file = this.project.getFolder(this.unitTestName).getFile(fileName).getLocation().toFile();
			try {
				String generatedFileContent = Files.toString(file, Charset.defaultCharset());
				this.compareFiles(fileName, fileContent, generatedFileContent);
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}
	}

	private void compareFiles(String fileName, String referenceFileContent, String generatedFileContent) {
		if (fileName.endsWith(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION)) {
			assertEquals(fileName, deleteWhitespaces(removeTimeStamp(referenceFileContent)),
					deleteWhitespaces(removeTimeStamp(generatedFileContent)));
		} else {
			assertEquals(fileName, deleteWhitespaces(referenceFileContent),
					deleteWhitespaces(generatedFileContent));
		}
	}

	protected String readFileFromURL(URL url) {
		String result = ""; //$NON-NLS-1$

		InputStream openStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			openStream = url.openStream();
		} catch (IOException e) {
			// do nothing, openStream is null
		}

		if (openStream != null) {
			inputStreamReader = new InputStreamReader(openStream);
			bufferedReader = new BufferedReader(inputStreamReader);

			StringBuilder stringBuilder = new StringBuilder();

			String line = null;
			try {
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						openStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			result = stringBuilder.toString();
		}

		return result;
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
	 * Returns reference root path.
	 * 
	 * @return reference root path
	 * @throws IOException
	 *             if I/O error occurs
	 */
	protected String getReferenceRootPath() throws IOException {
		return FileLocator.resolve(pluginRoot).getFile() + "/src-gen/" //$NON-NLS-1$
				+ getReferencePath() + "/ReferenceResult/" + unitTestName; //$NON-NLS-1$
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
		URL entry = bundle.getEntry(mtlPath);
		String content = this.readFileFromURL(entry);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(new StringBuffer(content));

		final URI moduleURI = URI.createPlatformPluginURI('/' + AcceleoEngineTestPlugin.PLUGIN_ID + '/'
				+ (new Path(mtlPath)).removeFileExtension().addFileExtension(EMTL_EXTENSION), true);

		Resource modelResource = ModelUtils.createResource(moduleURI, resourceSet);
		AcceleoParser parser = new AcceleoParser();
		parser.parse(source, modelResource, new ArrayList<URI>());
		return modelResource;
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

	@Before
	public void setUp() {
		Resource modelResource = parse(getModuleLocation());
		EObject rootTemplate = modelResource.getContents().get(0);
		if (rootTemplate instanceof Module) {
			module = (Module)rootTemplate;
		} else {
			fail("Couldn't load the input template."); //$NON-NLS-1$
		}

		defaultStrategy = new DefaultStrategy();
		previewStrategy = new PreviewStrategy();
	}

	@After
	public void tearDown() {
		// Unload all but the input model
		for (Resource resource : resourceSet.getResources()) {
			if (resource != inputModel.eResource()) {
				resource.unload();
			}
		}
	}
}
