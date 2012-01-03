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
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This will test the behavior of the Acceleo engine when resolving &quot;simple&quot; overriding template
 * calls. These are considered simple overrides since no parameter narrowing is done.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class SimpleOverridesResolutionTest extends AbstractAcceleoTest {
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
		return "OverrideResolution/Simple"; //$NON-NLS-1$
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * imported module but overriden in the extended module called directly.
	 */
	public void testExtendOverrideDirectCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("ExtendOverrideDirectCall")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ExtendOverrideDirectCall")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_extend_overriden_direct_call", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the actually called template to be the extend override
			assertTrue(content.contains("extended.extend_overriden")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * imported module but overriden in the extended module called indirectly.
	 */
	public void testExtendOverrideIndirectCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("ExtendOverrideIndirectCall")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ExtendOverrideIndirectCall")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_extend_overriden_indirect_call", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the actually called template to be the extend override
			assertTrue(content.contains("extended.extend_overriden")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * extended module yet locally overriden called directly.
	 */
	public void testLocalOverrideDirectCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("LocalOverrideDirectCall")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("LocalOverrideDirectCall")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_local_overriden_direct_call", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the actually called template to be the local override
			assertTrue(content.contains("local.local_override")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * extended module yet locally overriden called indirectly
	 */
	public void testLocalOverrideIndirectCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("LocalOverrideIndirectCall")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("LocalOverrideIndirectCall")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_local_overriden_indirect_call", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the actually called template to be the local override
			assertTrue(content.contains("local.local_override")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * imported module yet overriden in both the current module and the extended module and called directly
	 * from the current module.
	 */
	public void testOverridePriorityDirectCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("OverridePriorityDirectCall")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("OverridePriorityDirectCall")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_override_priority_direct", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local override
			assertTrue(content.contains("local.local_override_3")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * imported module yet overriden in both the current module and the extended module and called indirectly
	 * in the imported module.
	 */
	public void testOverridePriorityIndirectCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("OverridePriorityIndirectCall")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("OverridePriorityIndirectCall")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_override_priority_indirect", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local override
			assertTrue(content.contains("local.local_override_3")); //$NON-NLS-1$
		}
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * imported module yet overriden in both the current module and the extended module and called indirectly
	 * in the extended module.
	 */
	public void testOverridePriorityMidIndirectCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("OverridePriorityMidIndirectCall")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("OverridePriorityMidIndirectCall")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_override_priority_indirect_extend", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect the called template to be the local override
			assertTrue(content.contains("local.local_override_3")); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		String localTemplateLocation = "data/Resolution/Override/Simple/local.mtl"; //$NON-NLS-1$
		String importedTemplateLocation = "data/Resolution/Override/Simple/imported.mtl"; //$NON-NLS-1$
		String extendedTemplateLocation = "data/Resolution/Override/Simple/extended.mtl"; //$NON-NLS-1$

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
