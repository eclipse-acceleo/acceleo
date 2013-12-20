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

import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.Test;

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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "Template"; //$NON-NLS-1$
	}

	/**
	 * Tests the "super" call of an overriding template.
	 */
	@Test
	public void testSuperCall() {
		this.init("Super"); //$NON-NLS-1$
		this.generate("test_super", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests calls to a local protected template.
	 */
	@Test
	public void testProtectedCall() {
		this.init("ProtectedVisibility"); //$NON-NLS-1$
		this.generate("test_protected_visibility", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests calls to a local private template.
	 */
	@Test
	public void testPrivateCall() {
		this.init("PrivateVisibility"); //$NON-NLS-1$
		this.generate("test_private_visibility", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the effect of the "before" attribute of a template invocation.
	 */
	@Test
	public void testBefore() {
		this.init("Before"); //$NON-NLS-1$
		this.generate("test_before", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the effect of the "after" attribute of a template invocation.
	 */
	@Test
	public void testAfter() {
		this.init("After"); //$NON-NLS-1$
		this.generate("test_after", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of a template call when calling a template which guard evaluates to false.
	 */
	@Test
	public void testFalseGuard() {
		this.init("FalseGuard"); //$NON-NLS-1$
		this.generate("test_false_guard", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of a template call when calling a template which guard evaluates to true.
	 */
	@Test
	public void testTrueGuard() {
		this.init("TrueGuard"); //$NON-NLS-1$
		this.generate("test_true_guard", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of a template call when calling a template overriding a protected one.
	 */
	@Test
	public void testProtectOverride() {
		this.init("ProtectOverride"); //$NON-NLS-1$
		this.generate("test_protect_override", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of the engine when calling a template imported from an extended module.
	 */
	@Test
	public void testImport() {
		this.init("Import"); //$NON-NLS-1$
		this.generate("test_import", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of the engine when calling a template in a collection.
	 */
	@Test
	public void testTemplateCollection() {
		this.init("TemplateCollection"); //$NON-NLS-1$
		this.generate("test_template_collection", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of the engine when calling a template in a collection and then inside another
	 * collection.
	 */
	@Test
	public void testTemplateCollectionCollection() {
		this.init("TemplateCollectionCollection"); //$NON-NLS-1$
		this.generate("test_template_collection_collection", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of the engine when calling a template in a collection and then inside another
	 * collection...
	 */
	@Test
	public void testTemplateCollectionCollectionCollectionCollection() {
		this.init("TemplateCollectionCollectionCollectionCollection"); //$NON-NLS-1$
		this.generate("test_template_collection_collection_collection_collection", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	@Test
	public void testCollectionTemplate() {
		this.init("TemplateOverrideCollection"); //$NON-NLS-1$
		this.generate("test_collection_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	@Test
	public void testSequenceTemplate() {
		this.init("TemplateOverrideSequence"); //$NON-NLS-1$
		this.generate("test_sequence_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	@Test
	public void testSetTemplate() {
		this.init("TemplateOverrideSet"); //$NON-NLS-1$
		this.generate("test_set_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	@Test
	public void testBagTemplate() {
		this.init("TemplateOverrideBag"); //$NON-NLS-1$
		this.generate("test_bag_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests the behavior of the engine when calling a template extending another template with a collection
	 * as parameter.
	 */
	@Test
	public void testOrderedSetTemplate() {
		this.init("TemplateOverrideOrderedSet"); //$NON-NLS-1$
		this.generate("test_ordered_set_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	@Before
	@Override
	public void setUp() {
		String localTemplateLocation = "data/Template/local.mtl"; //$NON-NLS-1$
		String extendedTemplateLocation = "data/Template/extended.mtl"; //$NON-NLS-1$

		parse(extendedTemplateLocation);
		Resource localResource = parse(localTemplateLocation);

		EObject rootTemplate = localResource.getContents().get(0);
		if (rootTemplate instanceof Module) {
			module = (Module)rootTemplate;
		} else {
			fail("Failed to parse the templates."); //$NON-NLS-1$
		}

		defaultStrategy = new DefaultStrategy();
		previewStrategy = new PreviewStrategy();
	}
}
