/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.service;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * This will be used to test the behavior of the Acceleo engine facade.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoServiceTest extends AbstractAcceleoTest {
	/** Error message for an unthrown expected NPE. */
	private final String FAIL_EXPECTED_NPE = "The service previously threw an NPE in such cases.";

	/** This is the expected content of the generated files. */
	private final String FILE_OUTPUT = "constant output";

	/** Module loaded from "data/Service/service_module_1". */
	private Module module1;

	/** Module loaded from "data/Service/service_module_2". */
	private Module module2;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		// Won't be used as we override #setUp().
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "Service";
	}

	/**
	 * Checks that the behavior when sending null maps doesn't change.
	 */
	public void testDoGenerateModuleMapNullMap() {
		try {
			AcceleoService.doGenerate((Map<Module, Set<String>>)null, inputModel, null, true,
					new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that the behavior when sending null input EObjects doesn't change.
	 */
	public void testDoGenerateModuleMapNullInput() {
		try {
			AcceleoService.doGenerate(getTemplateMap(), null, null, true, new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that the behavior when sending null as the generation root when not in preview mode doesn't
	 * change.
	 */
	public void testDoGenerateModuleMapNoPreviewNoGenerationRoot() {
		try {
			AcceleoService.doGenerate(getTemplateMap(), inputModel, null, false, new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Tests the behavior of the method
	 * {@link AcceleoService#doGenerate(java.util.Map, EObject, java.io.File, boolean, org.eclipse.emf.common.util.Monitor)}
	 * in generation mode (preview = false).
	 */
	public void testDoGenerateModuleMapNoPreview() throws IOException {
		generationRoot = new File(getGenerationRootPath("ModuleMap"));
		referenceRoot = new File(getReferenceRootPath("ModuleMap"));
		cleanGenerationRoot();

		final Map<Module, Set<String>> templates = getTemplateMap();
		final Map<String, Writer> preview = AcceleoService.doGenerate(templates, inputModel, generationRoot,
				false, null);
		assertTrue("The preview should be empty", preview.isEmpty());

		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		final File[] generatedFile = getFiles(generationRoot);
		for (File generated : generatedFile) {
			assertFalse("a lost file shouldn't have been created", generated.getName().endsWith(
					IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains(FILE_OUTPUT));
		}
		assertSame("Unexpected number of templates evaluated.", 3, generatedFile.length);
	}

	/**
	 * Tests the behavior of the method
	 * {@link AcceleoService#doGenerate(java.util.Map, EObject, java.io.File, boolean, org.eclipse.emf.common.util.Monitor)}
	 * in preview mode.
	 */
	public void testDoGenerateModuleMapPreview() {
		final Map<Module, Set<String>> templates = getTemplateMap();
		final Map<String, Writer> preview = AcceleoService
				.doGenerate(templates, inputModel, null, true, null);
		assertNotNull("A preview should have been generated by the service", preview);
		assertFalse("The preview shouldn't be empty", preview.isEmpty());

		// We're generating one file per template, each having the same name as the template itself.
		int templateCount = 0;
		for (Map.Entry<Module, Set<String>> moduleEntry : templates.entrySet()) {
			for (String templateName : moduleEntry.getValue()) {
				templateCount++;
				final Writer writer = preview.get(templateName);
				if (writer != null) {
					assertEquals("Unexpected content for the generated file.", FILE_OUTPUT, writer.toString()
							.trim());
				} else {
					fail("A file should have been generated.");
				}
			}
		}
		assertSame("Unexpected number of templates evaluated.", 3, templateCount);
	}

	/**
	 * Returns a map containing all input templates.
	 * 
	 * @return A map containing all input templates.
	 */
	private Map<Module, Set<String>> getTemplateMap() {
		final Map<Module, Set<String>> templates = new HashMap<Module, Set<String>>(2);
		final Set<String> module1Templates = new HashSet<String>(2);
		final Set<String> module2Templates = new HashSet<String>(2);
		module1Templates.add("template_1_1");
		module1Templates.add("template_1_2");
		module2Templates.add("template_2_1");
		templates.put(module1, module1Templates);
		templates.put(module2, module2Templates);
		return templates;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// shortcuts inherited behavior
		String modulePath = "data/Service/service_module_1.mtl";
		Resource modelResource = parse(modulePath);
		EObject rootModule = modelResource.getContents().get(0);
		if (rootModule instanceof Module) {
			module1 = (Module)rootModule;
		} else {
			Assert.fail("Couldn't load the input template " + modulePath); //$NON-NLS-1$
		}
		modulePath = "data/Service/service_module_2.mtl";
		modelResource = parse(modulePath);
		rootModule = modelResource.getContents().get(0);
		if (rootModule instanceof Module) {
			module2 = (Module)rootModule;
		} else {
			Assert.fail("Couldn't load the input template " + modulePath); //$NON-NLS-1$
		}
	}
}
