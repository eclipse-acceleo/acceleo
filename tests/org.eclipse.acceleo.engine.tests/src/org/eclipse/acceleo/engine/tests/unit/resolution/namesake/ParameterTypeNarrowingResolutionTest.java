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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "NamesakeResolution"; //$NON-NLS-1$
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on both its extended module and its imported module. We expect it to select the most specific one which
	 * is here on the extended template.
	 */
	@Test
	public void testNamesakeResolutionExtendMostSpecific() {
		this.init("NamesakeExtendMostSpecific"); //$NON-NLS-1$
		this.generate("test_namesake_3_extend_specific", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined with the
	 * same name on both its extended module and its imported module yet not present on the local module. We
	 * expect it to select the most specific one which is here on the extended template.
	 */
	@Test
	public void testNamesakeResolutionExternalExtendSpecific() {
		this.init("NamesakeExternalExtendSpecific"); //$NON-NLS-1$
		this.generate("test_namesake_external_extend_specific", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined with the
	 * same name on both its extended module and its imported module yet not present on the local module. We
	 * expect it to select the most specific one which is here on the imported template.
	 */
	@Test
	public void testNamesakeResolutionExternalImportSpecific() {
		this.init("NamesakeExternalImportSpecific"); //$NON-NLS-1$
		this.generate("test_namesake_external_import_specific", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on both its extended module and its imported module. We expect it to select the most specific one which
	 * is here on the imported template.
	 */
	@Test
	public void testNamesakeResolutionImportMostSpecific() {
		this.init("NamesakeImportMostSpecific"); //$NON-NLS-1$
		this.generate("test_namesake_3_import_specific", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on both its extended module and its imported module. We expect it to select the most specific one which
	 * is here on the local template.
	 */
	@Test
	public void testNamesakeResolutionLocalMostSpecific() {
		this.init("NamesakeLocalMostSpecific"); //$NON-NLS-1$
		this.generate("test_namesake_3_local_specific", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its extended module. We expect it to select the most specific one which is here on the extended
	 * template.
	 */
	@Test
	public void testNamesakeResolutionOnExtendExtendSpecific() {
		this.init("NamesakeOnExtendExtendSpecific"); //$NON-NLS-1$
		this.generate("test_namesake_extend_extend_specific", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its extended module. We expect it to select the most specific one which is here on the local
	 * template.
	 */
	@Test
	public void testNamesakeResolutionOnExtendLocalSpecific() {
		this.init("NamesakeOnExtendLocalSpecific"); //$NON-NLS-1$
		this.generate("test_namesake_extend_local_specific", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its imported module. We expect it to select the most specific one which is here on the imported
	 * template.
	 */
	@Test
	public void testNamesakeResolutionOnImportImportSpecific() {
		this.init("NamesakeOnImportImportSpecific"); //$NON-NLS-1$
		this.generate("test_namesake_import_import_specific", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its imported module. We expect it to select the most specific one which is here on the local
	 * template.
	 */
	@Test
	public void testNamesakeResolutionOnImportLocalSpecific() {
		this.init("NamesakeOnImportLocalSpecific"); //$NON-NLS-1$
		this.generate("test_namesake_import_local_specific", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Before
	@Override
	public void setUp() {
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
			fail("Failed to parse the templates."); //$NON-NLS-1$
		}

		defaultStrategy = new DefaultStrategy();
		previewStrategy = new PreviewStrategy();
	}
}
