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
package org.eclipse.acceleo.engine.tests.unit.resolution.namesake;

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
 * This will test the behavior of the Acceleo engine when resolving name conflicts in guarded template calls.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class NamesakeGuardResolutionTest extends AbstractAcceleoTest {
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
		return "NamesakeResolution/Guard"; //$NON-NLS-1$
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on both its extended module and its imported module. No template is more specific than the other as far
	 * as parameters are concerned, but only the imported template has its guard evaluated to true. We then
	 * expect the engine to use the imported template.
	 */
	public void testNamesakeGuard() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeGuard")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeGuard")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_guard", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the imported one
			assertTrue(content.contains("imported.namesake_guard")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on both its extended module and its imported module. The extended template is less specific than the
	 * local and imported ones, yet the local one has its guard evaluated to false. We then expect the engine
	 * to use the imported template.
	 */
	public void testNamesakeGuardImportSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeGuardImportSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeGuardImportSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_guard_import_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the imported one
			assertTrue(content.contains("imported.namesake_guard_import_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on both its extended module and its imported module. The imported template is less specific than the
	 * local and extended ones, yet both have their guards evaluated to false. We then expect the engine to
	 * use the imported template even though less specific.
	 */
	public void testNamesakeGuardedSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeGuardedSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeGuardedSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		new AcceleoService(defaultStrategy).doGenerate(module,
				"test_namesake_guarded_specific", inputModel, generationRoot, //$NON-NLS-1$
				new BasicMonitor());
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the imported one
			assertTrue(content.contains("imported.namesake_guarded_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		String localTemplateLocation = "data/Resolution/Namesake/Guard/local.mtl"; //$NON-NLS-1$
		String importedTemplateLocation = "data/Resolution/Namesake/Guard/imported.mtl"; //$NON-NLS-1$
		String extendedTemplateLocation = "data/Resolution/Namesake/Guard/extended.mtl"; //$NON-NLS-1$

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
