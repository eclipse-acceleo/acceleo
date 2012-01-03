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
package org.eclipse.acceleo.engine.tests.unit.blocks.template;

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
 * This will test the behavior of the Acceleo engine on all aspects of the "template" Acceleo block. This
 * includes template invocation specific features such as "before" and "after".
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class TemplateTest extends AbstractAcceleoTest {
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
		return "Template"; //$NON-NLS-1$
	}

	/**
	 * Tests the "super" call of an overriding template.
	 */
	public void testSuperCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("Super")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("Super")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_super", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("overriding call to")); //$NON-NLS-1$
			assertTrue(content.contains("extended.super_extended_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests calls to a local protected template.
	 */
	public void testProtectedCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("ProtectedVisibility")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ProtectedVisibility")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_protected_visibility", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("local.protected_overriding_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests calls to a local private template.
	 */
	public void testPrivateCall() throws IOException {
		generationRoot = new File(getGenerationRootPath("PrivateVisibility")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("PrivateVisibility")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_private_visibility", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("local.private_local_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the effect of the "before" attribute of a template invocation.
	 */
	public void testBefore() throws IOException {
		generationRoot = new File(getGenerationRootPath("Before")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("Before")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_before", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("before")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the effect of the "after" attribute of a template invocation.
	 */
	public void testAfter() throws IOException {
		generationRoot = new File(getGenerationRootPath("After")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("After")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_after", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("after")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of a template call when calling a template which guard evaluates to false.
	 */
	public void testFalseGuard() throws IOException {
		generationRoot = new File(getGenerationRootPath("FalseGuard")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("FalseGuard")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_false_guard", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.trim().length() == 0);
		}
	}

	/**
	 * Tests the behavior of a template call when calling a template which guard evaluates to true.
	 */
	public void testTrueGuard() throws IOException {
		generationRoot = new File(getGenerationRootPath("TrueGuard")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("TrueGuard")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_true_guard", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("local.true_guard_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of a template call when calling a template overriding a protected one.
	 */
	public void testProtectOverride() throws IOException {
		generationRoot = new File(getGenerationRootPath("ProtectOverride")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ProtectOverride")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_protect_override", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("local.protected_overriding_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of the engine when calling a template imported from an extended module.
	 */
	public void testImport() throws IOException {
		generationRoot = new File(getGenerationRootPath("Import")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("Import")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_import", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("extended.imported_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of the engine when calling a template in a collection.
	 */
	public void testTemplateCollection() throws IOException {
		generationRoot = new File(getGenerationRootPath("TemplateCollection")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("TemplateCollection")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_template_collection", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("template_call_collection")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of the engine when calling a template in a collection and then inside another
	 * collection.
	 */
	public void testTemplateCollectionCollection() throws IOException {
		generationRoot = new File(getGenerationRootPath("TemplateCollectionCollection")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("TemplateCollectionCollection")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_template_collection_collection", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("template_call_collection_collection")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of the engine when calling a template in a collection and then inside another
	 * collection...
	 */
	public void testTemplateCollectionCollectionCollectionCollection() throws IOException {
		generationRoot = new File(getGenerationRootPath("TemplateCollectionCollectionCollectionCollection")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("TemplateCollectionCollectionCollectionCollection")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_template_collection_collection_collection_collection", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("template_call_collection_collection_collection_collection")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	public void testCollectionTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("TemplateOverrideCollection")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("TemplateOverrideCollection")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_collection_template", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("extending_collection_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	public void testSequenceTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("TemplateOverrideSequence")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("TemplateOverrideSequence")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_sequence_template", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("extending_sequence_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	public void testSetTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("TemplateOverrideSet")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("TemplateOverrideSet")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_set_template", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("extending_set_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	public void testBagTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("TemplateOverrideBag")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("TemplateOverrideBag")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_bag_template", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("extending_bag_template")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	public void testOrderedSetTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("TemplateOverrideOrderedSet")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("TemplateOverrideOrderedSet")); //$NON-NLS-1$

		cleanGenerationRoot();

		generate("test_ordered_set_template", defaultStrategy); //$NON-NLS-1$
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("extending_ordered_set_template")); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		String localTemplateLocation = "data/Template/local.mtl"; //$NON-NLS-1$
		String extendedTemplateLocation = "data/Template/extended.mtl"; //$NON-NLS-1$

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
