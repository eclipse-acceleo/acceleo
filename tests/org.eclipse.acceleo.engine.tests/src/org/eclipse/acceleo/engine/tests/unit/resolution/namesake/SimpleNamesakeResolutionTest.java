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
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This will test the behavior of the Acceleo engine when resolving &quot;simple&quot; name conflicts in
 * template calls. These are considered simple conflicts since no overriding or changes in parameter count is
 * present.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class SimpleNamesakeResolutionTest extends AbstractAcceleoTest {
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
		return "NamesakeResolution/Simple"; //$NON-NLS-1$
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its extended module. We expect it to select the one present on the current module.
	 */
	public void testNameResolutionConflictOnExtended() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeExtendConflict")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeExtendConflict")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_extend", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local one
			assertTrue(content.contains("local.namesake_extend")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its imported module. We expect it to select the one present on the current module.
	 */
	public void testNameResolutionConflictOnImported() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeImportConflict")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeImportConflict")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_import", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local one
			assertTrue(content.contains("local.namesake_import")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template not present ont
	 * the current module but with namesakes on its extended and imported modules. We expect it to select the
	 * one present on the extended module.
	 */
	public void testNameResolutionExternalConflict() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeExternalConflict")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeExternalConflict")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_external", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the extend one
			assertTrue(content.contains("extended.namesake_external")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test tge behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * sharing parameters with distinct names. We expect the engine to select the most specific template
	 * regardless of parameter names.
	 */
	public void testNameResolutionDistinctParamNames() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakeDistinctParameterNames")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakeDistinctParameterNames")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_distinct_param_name", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local one
			assertTrue(content.contains("local.namesake_param_names")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on each three possible places : extended, current and imported module. We expect it to select the one
	 * present on the current module.
	 */
	public void testNameResolutionPriority() throws IOException {
		generationRoot = new File(getGenerationRootPath("NamesakePriority")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("NamesakePriority")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_namesake_3", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local one
			assertTrue(content.contains("local.namesake_3")); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		String localTemplateLocation = "data/Resolution/Namesake/Simple/local.mtl"; //$NON-NLS-1$
		String importedTemplateLocation = "data/Resolution/Namesake/Simple/imported.mtl"; //$NON-NLS-1$
		String extendedTemplateLocation = "data/Resolution/Namesake/Simple/extended.mtl"; //$NON-NLS-1$

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
