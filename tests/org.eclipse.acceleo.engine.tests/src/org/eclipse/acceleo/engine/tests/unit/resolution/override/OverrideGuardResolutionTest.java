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
 * This will test the behavior of the Acceleo engine when resolving overriding template calls. Overriding
 * templates define guards, some of which <code>true</code>.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class OverrideGuardResolutionTest extends AbstractAcceleoTest {
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
		return "OverrideGuardResolution"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "OverrideResolution"; //$NON-NLS-1$
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * imported module yet overriden in both the current module and the extended module. Local and Extended
	 * are the most specific, but their guard evaluates to false. We expect the imported template to be
	 * called.
	 */
	@Test
	public void testOverrideSpecificGuarded() {
		this.init("SpecificGuarded"); //$NON-NLS-1$
		this.generate("test_resolution_override_specific_guarded", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * exended module and overriden in the current module. The Local template is the most specific and its
	 * super has its guard evaluated to false. We expect the local template to be called.
	 */
	@Test
	public void testOverrideLocalDefinitionGuarded() {
		this.init("LocalDefinitionGuarded"); //$NON-NLS-1$
		this.generate("test_resolution_local_override_definition_guarded", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template defined in the
	 * imported module yet overriden in the extended module. The imported module's template has its guard
	 * evaluating to false, we then expect the extended one to be called.
	 */
	@Test
	public void testOverrideParameterNarrowingExternal() {
		this.init("ExternalDefinitionGuarded"); //$NON-NLS-1$
		this.generate("test_resolution_external_override_definition_guarded", defaultStrategy); //$NON-NLS-1$
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
		String localTemplateLocation = "data/Resolution/Override/Guard/local.mtl"; //$NON-NLS-1$
		String importedTemplateLocation = "data/Resolution/Override/Guard/imported.mtl"; //$NON-NLS-1$
		String extendedTemplateLocation = "data/Resolution/Override/Guard/extended.mtl"; //$NON-NLS-1$

		// parse imported template before since it is extended by the extended template which in turn is
		// extended by the local
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
