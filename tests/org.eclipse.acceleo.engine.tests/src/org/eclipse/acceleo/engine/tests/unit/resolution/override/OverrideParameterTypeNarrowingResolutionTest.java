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
package org.eclipse.acceleo.engine.tests.unit.resolution.override;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This will test the behavior of the Acceleo engine when resolving overriding template calls. Overriding
 * templates narrow their parameter types.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class OverrideParameterTypeNarrowingResolutionTest extends AbstractAcceleoTest {
	{
		try {
			final URI inputModelURI = URI.createPlatformPluginURI('/' + AcceleoEngineTestPlugin.PLUGIN_ID
					+ '/' + "data/abstractClass.ecore", true); //$NON-NLS-1$
			inputModel = ModelUtils.load(inputModelURI, resourceSet);
		} catch (IOException e) {
			fail("Error loading the input model."); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		// useless for this test as we won't call super#setup()
		return ""; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "OverrideResolution/ParameterTypeNarrowing"; //$NON-NLS-1$
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * imported module yet overriden in both the current module and the extended module.
	 */
	public void testOverrideParameterNarrowing() throws IOException {
		generationRoot = new File(getGenerationRootPath("ParameterNarrowing")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ParameterNarrowing")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_resolution_override_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local override
			assertTrue(content.contains("local.parameter_narrowing_3_local_override")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * exended module and overriden in the current module.
	 */
	public void testOverrideParameterNarrowingLocal() throws IOException {
		generationRoot = new File(getGenerationRootPath("ParameterNarrowingLocal")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ParameterNarrowingLocal")); //$NON-NLS-1$

		cleanGenerationRoot();

		new AcceleoService().doGenerate(module, "test_resolution_local_override", inputModel, generationRoot, //$NON-NLS-1$
				new BasicMonitor());
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local override
			assertTrue(content.contains("local.parameter_narrowing_local_override")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * imported module yet overriden in the extended module.
	 */
	public void testOverrideParameterNarrowingExternal() throws IOException {
		generationRoot = new File(getGenerationRootPath("ParameterNarrowingExternal")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ParameterNarrowingExternal")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_resolution_external_override", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the extend override
			assertTrue(content.contains("extended.parameter_narrowing_external_override")); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		String localTemplateLocation = "data/Resolution/Override/ParameterTypeNarrowing/local.mtl"; //$NON-NLS-1$
		String importedTemplateLocation = "data/Resolution/Override/ParameterTypeNarrowing/imported.mtl"; //$NON-NLS-1$
		String extendedTemplateLocation = "data/Resolution/Override/ParameterTypeNarrowing/extended.mtl"; //$NON-NLS-1$

		// parse imported template before since it is extended by the extended template which in turn is
		// extended by the local
		parse(importedTemplateLocation);
		parse(extendedTemplateLocation);
		Resource localResource = parse(localTemplateLocation);

		EObject rootTemplate = localResource.getContents().get(0);
		if (rootTemplate instanceof Module) {
			module = (Module)rootTemplate;
		} else {
			Assert.fail("Failed to parse the templates."); //$NON-NLS-1$
		}

		defaultStrategy = new DefaultStrategy();
		previewStrategy = new PreviewStrategy();
	}
}
