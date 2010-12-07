/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.tests.unit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.ocl.expressions.OCLExpression;
import org.junit.After;
import org.osgi.framework.Bundle;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import static org.junit.Assert.fail;

/**
 * This will be used as the base class for our traceability tests. It adds functionality to locate files
 * within plugin fragments.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public abstract class AbstractTraceabilityTest {

	/**
	 * ID of the traceability tests plugin.
	 **/
	protected final static String PLUGIN_ID = "org.eclipse.acceleo.traceability.tests"; //$NON-NLS-1$

	/**
	 * Plugin bundle.
	 */
	protected final Bundle bundle = Platform.getBundle(AbstractTraceabilityTest.PLUGIN_ID);

	/**
	 * This is the input model as loaded from initializer.
	 **/
	protected EObject inputModel;

	/**
	 * This is the module loaded from {@link #moduleLocation} at setup.
	 **/
	protected Module module;

	/**
	 * Resource set we'll use throughout the test sub-classes.
	 **/
	protected final ResourceSet resourceSet = new ResourceSetImpl();

	/**
	 * The uri of the module.
	 */
	protected URI moduleURI;

	/**
	 * Launch the parsing and the generation.
	 * 
	 * @param mtlPath
	 *            is the path of Acceleo file
	 * @param moduleBuffer
	 *            The buffer of the module.
	 * @param templateName
	 *            The name of the main template
	 * @param modelPath
	 *            The path of the model
	 * @param listenGenerationEnd
	 *            Indicates if we will listen to the end of the generation
	 * @return The generation listener
	 */
	protected AcceleoTraceabilityListener parseAndGenerate(String mtlPath, StringBuffer moduleBuffer,
			String templateName, String modelPath, boolean listenGenerationEnd) {
		// Parse the file
		Resource modelResource = parse(mtlPath, moduleBuffer);
		EObject rootTemplate = modelResource.getContents().get(0);
		if (rootTemplate instanceof Module) {
			module = (Module)rootTemplate;
		} else {
			Assert.fail("Couldn't load the input template."); //$NON-NLS-1$
		}

		// Activate the traceability
		AcceleoTraceabilityListener traceabilityListener = new AcceleoTraceabilityListener(
				listenGenerationEnd);
		AcceleoPreferences.switchTraceability(true);
		AcceleoService.addStaticListener(traceabilityListener);

		// Load the model
		try {
			final URI inputModelURI = URI.createPlatformPluginURI('/' + AbstractTraceabilityTest.PLUGIN_ID
					+ '/' + modelPath, true);
			inputModel = ModelUtils.load(inputModelURI, resourceSet);
		} catch (IOException e) {
			fail("Error loading the input model."); //$NON-NLS-1$
		}

		// Launch the generation
		new AcceleoService(new PreviewStrategy()).doGenerate(module, templateName, inputModel, null,
				new BasicMonitor());

		// Desactivate the traceability
		AcceleoPreferences.switchTraceability(false);
		AcceleoService.removeStaticListener(traceabilityListener);

		// Return the traceability listener
		return traceabilityListener;
	}

	/**
	 * This methods parses an Acceleo file and creates a model representation of it (.emtl).
	 * 
	 * @param mtlPath
	 *            is the path of Acceleo file
	 * @param mtlBuffer
	 *            The buffer of the file
	 * @param resourceSet
	 *            is the resourceSet
	 * @return a model representation of Acceleo file
	 */
	protected Resource parse(String mtlPath, StringBuffer mtlBuffer) {
		File file = createFile(mtlPath);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(mtlBuffer);

		moduleURI = URI.createPlatformPluginURI('/'
				+ AbstractTraceabilityTest.PLUGIN_ID
				+ '/'
				+ (new Path(mtlPath)).removeFileExtension().addFileExtension(
						IAcceleoConstants.EMTL_FILE_EXTENSION), true);

		Resource modelResource = ModelUtils.createResource(moduleURI, resourceSet);
		AcceleoParser parser = new AcceleoParser();
		assertNull(parser.getProblems(file));
		assertNull(parser.getWarnings(file));
		assertNull(parser.getInfos(file));
		parser.parse(source, modelResource, new ArrayList<URI>());
		return modelResource;
	}

	/**
	 * Launch the parsing and the generation.
	 * 
	 * @param modulePath
	 *            The path of the module.
	 * @param templateName
	 *            The name of the main template
	 * @param modelPath
	 *            The path of the model
	 * @param listenGenerationEnd
	 *            Indicates if we will listen to the end of the generation
	 * @return The generation listener
	 */
	protected AcceleoTraceabilityListener parseAndGenerate(String modulePath, String templateName,
			String modelPath, boolean listenGenerationEnd) {

		// Parse the file
		Resource modelResource = parse(modulePath);
		EObject rootTemplate = modelResource.getContents().get(0);
		if (rootTemplate instanceof Module) {
			module = (Module)rootTemplate;
		} else {
			Assert.fail("Couldn't load the input template."); //$NON-NLS-1$
		}

		// Activate the traceability
		AcceleoTraceabilityListener traceabilityListener = new AcceleoTraceabilityListener(
				listenGenerationEnd);
		AcceleoPreferences.switchTraceability(true);
		AcceleoService.addStaticListener(traceabilityListener);

		// Load the model
		try {
			final URI inputModelURI = URI.createPlatformPluginURI('/' + AbstractTraceabilityTest.PLUGIN_ID
					+ '/' + modelPath, true);
			inputModel = ModelUtils.load(inputModelURI, resourceSet);
		} catch (IOException e) {
			fail("Error loading the input model."); //$NON-NLS-1$
		}

		// Launch the generation
		new AcceleoService(new PreviewStrategy()).doGenerate(module, templateName, inputModel, null,
				new BasicMonitor());

		// Desactivate the traceability
		AcceleoPreferences.switchTraceability(false);
		AcceleoService.removeStaticListener(traceabilityListener);

		// Return the traceability listener
		return traceabilityListener;
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

		moduleURI = URI.createPlatformPluginURI('/'
				+ AbstractTraceabilityTest.PLUGIN_ID
				+ '/'
				+ (new Path(mtlPath)).removeFileExtension().addFileExtension(
						IAcceleoConstants.EMTL_FILE_EXTENSION), true);

		Resource modelResource = ModelUtils.createResource(moduleURI, resourceSet);
		AcceleoParser parser = new AcceleoParser();
		parser.parse(source, modelResource, new ArrayList<URI>());

		assertEquals("[]", parser.getProblems(file).getList().toString()); //$NON-NLS-1$
		assertEquals("[]", parser.getWarnings(file).getList().toString()); //$NON-NLS-1$
		assertEquals("[]", parser.getInfos(file).getList().toString()); //$NON-NLS-1$
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

	protected Object getObjectFromVariable(VariableExp variableExp) {
		Object object = null;

		OCLExpression<EClassifier> initExpression = variableExp.getReferredVariable().getInitExpression();
		try {
			Field ref = null;
			Field[] declaredFields = initExpression.getClass().getDeclaredFields();
			for (Field field : declaredFields) {
				if ("referredExpression".equals(field.getName())) { //$NON-NLS-1$
					ref = field;
					break;
				}
			}

			if (ref != null) {
				ref.setAccessible(true);
				object = ref.get(initExpression);
			} else {
				fail();
			}
		} catch (SecurityException e) {
			fail();
		} catch (IllegalArgumentException e) {
			fail();
		} catch (IllegalAccessException e) {
			fail();
		}
		return object;
	}

	@After
	public void tearDown() {
		// Unload all but the input model
		for (Resource resource : resourceSet.getResources()) {
			if (resource != inputModel.eResource()) {
				resource.unload();
			}
		}

		if (this.moduleURI != null) {
			String path = this.moduleURI.toString();
			path = path.substring("platform:/plugin".length()); //$NON-NLS-1$
			try {
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));
				file.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
