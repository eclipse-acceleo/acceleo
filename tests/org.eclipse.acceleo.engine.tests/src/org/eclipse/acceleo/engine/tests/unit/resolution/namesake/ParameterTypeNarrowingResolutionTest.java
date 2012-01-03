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
 * This will test the behavior of the Acceleo engine when resolving name conflicts in template calls.
 * Templates will narrow the parameter type : some have parameter types "EClassifier" while namesakes have
 * "EClass".
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ParameterTypeNarrowingResolutionTest extends AbstractAcceleoTest {
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
		return "NamesakeResolution/ParameterNarrowing"; //$NON-NLS-1$
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on both its extended module and its imported module. We expect it to select the most specific one which
	 * is here on the extended template.
	 */
	public void testNamesakeResolutionExtendMostSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeExtendMostSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeExtendMostSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_3_extend_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the extended one
			assertTrue(content.contains("extended.namesake_3_extend_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined with the
	 * same name on both its extended module and its imported module yet not present on the local module. We
	 * expect it to select the most specific one which is here on the extended template.
	 */
	public void testNamesakeResolutionExternalExtendSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeExternalExtendSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeExternalExtendSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_external_extend_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the extended one
			assertTrue(content.contains("extended.namesake_external_extend_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined with the
	 * same name on both its extended module and its imported module yet not present on the local module. We
	 * expect it to select the most specific one which is here on the imported template.
	 */
	public void testNamesakeResolutionExternalImportSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeExternalImportSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeExternalImportSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_external_import_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the imported one
			assertTrue(content.contains("imported.namesake_external_import_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on both its extended module and its imported module. We expect it to select the most specific one which
	 * is here on the imported template.
	 */
	public void testNamesakeResolutionImportMostSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeImportMostSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeImportMostSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_3_import_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the imported one
			assertTrue(content.contains("imported.namesake_3_import_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on both its extended module and its imported module. We expect it to select the most specific one which
	 * is here on the local template.
	 */
	public void testNamesakeResolutionLocalMostSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeLocalMostSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeLocalMostSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		new AcceleoService(defaultStrategy).doGenerate(module,
				"test_namesake_3_local_specific", inputModel, generationRoot, //$NON-NLS-1$
				new BasicMonitor());
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local one
			assertTrue(content.contains("local.namesake_3_local_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its extended module. We expect it to select the most specific one which is here on the extended
	 * template.
	 */
	public void testNamesakeResolutionOnExtendExtendSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeOnExtendExtendSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeOnExtendExtendSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_extend_extend_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the extended one
			assertTrue(content.contains("extended.namesake_extend_extend_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its extended module. We expect it to select the most specific one which is here on the local
	 * template.
	 */
	public void testNamesakeResolutionOnExtendLocalSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeOnExtendLocalSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeOnExtendLocalSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_extend_local_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local one
			assertTrue(content.contains("local.namesake_extend_local_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its imported module. We expect it to select the most specific one which is here on the imported
	 * template.
	 */
	public void testNamesakeResolutionOnImportImportSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeOnImportImportSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeOnImportImportSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_import_import_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the imported one
			assertTrue(content.contains("imported.namesake_import_import_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its imported module. We expect it to select the most specific one which is here on the local
	 * template.
	 */
	public void testNamesakeResolutionOnImportLocalSpecific() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeOnImportLocalSpecific")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeOnImportLocalSpecific")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_import_local_specific", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local one
			assertTrue(content.contains("local.namesake_import_local_specific")); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		String localTemplateLocation = "data/Resolution/Namesake/ParameterTypeNarrowing/local.mtl"; //$NON-NLS-1$
		String importedTemplateLocation = "data/Resolution/Namesake/ParameterTypeNarrowing/imported.mtl"; //$NON-NLS-1$
		String extendedTemplateLocation = "data/Resolution/Namesake/ParameterTypeNarrowing/extended.mtl"; //$NON-NLS-1$

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
