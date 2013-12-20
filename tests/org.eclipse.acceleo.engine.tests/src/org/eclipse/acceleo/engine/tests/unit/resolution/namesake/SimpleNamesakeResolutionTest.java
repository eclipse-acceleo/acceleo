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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "NamesakeResolution"; //$NON-NLS-1$
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its extended module. We expect it to select the one present on the current module.
	 */
	@Test
	public void testNameResolutionConflictOnExtended() {
		this.init("NamesakeExtendConflict"); //$NON-NLS-1$
		this.generate("test_namesake_extend", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on its imported module. We expect it to select the one present on the current module.
	 */
	@Test
	public void testNameResolutionConflictOnImported() {
		this.init("NamesakeImportConflict"); //$NON-NLS-1$
		this.generate("test_namesake_import", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template not present ont
	 * the current module but with namesakes on its extended and imported modules. We expect it to select the
	 * one present on the extended module.
	 */
	@Test
	public void testNameResolutionExternalConflict() {
		this.init("NamesakeExternalConflict"); //$NON-NLS-1$
		this.generate("test_namesake_external", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test tge behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * sharing parameters with distinct names. We expect the engine to select the most specific template
	 * regardless of parameter names.
	 */
	@Test
	public void testNameResolutionDistinctParamNames() {
		this.init("NamesakeDistinctParameterNames"); //$NON-NLS-1$
		this.generate("test_namesake_distinct_param_name", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * This will test the behavior of the Acceleo evaluation engine when resolving a template with namesakes
	 * on each three possible places : extended, current and imported module. We expect it to select the one
	 * present on the current module.
	 */
	@Test
	public void testNameResolutionPriority() {
		this.init("NamesakePriority"); //$NON-NLS-1$
		this.generate("test_namesake_3", defaultStrategy); //$NON-NLS-1$
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
			fail("Failed to parse the templates."); //$NON-NLS-1$
		}

		defaultStrategy = new DefaultStrategy();
		previewStrategy = new PreviewStrategy();
	}
}
