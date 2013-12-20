/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.service.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.generation.strategy.DefaultStrategy;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This will be used to test the behavior of the Acceleo engine facade.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoServiceTest extends AbstractAcceleoTest {
	/** This will be used as the arguments of the tested templates. */
	private List<Object> arguments;

	/** Error message for an unthrown expected Evaluation Exception. */
	private final String FAIL_EXPECTED_AEE = "The service previously threw an AcceleoEvaluationException in such cases.";

	/** Error message for an unthrown expected NPE. */
	private final String FAIL_EXPECTED_NPE = "The service previously threw an NPE in such cases.";

	/** This is the expected content of the generated files. */
	private final String FILE_OUTPUT = "constant output";

	/** Module loaded from "data/Service/service_module_1". */
	private Module module1;

	/** Module loaded from "data/Service/service_module_2". */
	private Module module2;

	/** References template "template_1_3" from module1. */
	private Template privateTemplate;

	/** References template "template_1_1" from module1. */
	private Template publicTemplate;

	/** References template "template_2_2" from module2. */
	private Template multipleArgumentTemplate;

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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "Service";
	}

	/**
	 * Tests the behavior of the method
	 * {@link AcceleoService#doGenerate(java.util.Map, EObject, java.io.File, boolean, org.eclipse.emf.common.util.Monitor)}
	 * in generation mode (preview = false).
	 */
	@Test
	@Ignore
	public void testDoGenerateModuleMapNoPreview() {
		this.init("ModuleMap"); //$NON-NLS-1$
		this.generate("template_1_1", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Checks that the behavior when sending null as the generation root when not in preview mode doesn't
	 * change.
	 */
	@Test
	public void testDoGenerateModuleMapNoPreviewNoGenerationRoot() {
		try {
			new AcceleoService().doGenerate(getTemplateMap(), inputModel, null, new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that the behavior when sending null input EObjects doesn't change.
	 */
	@Test
	public void testDoGenerateModuleMapNullInput() {
		try {
			new AcceleoService(previewStrategy).doGenerate(getTemplateMap(), null, null, new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that the behavior when sending null maps doesn't change.
	 */
	@Test
	public void testDoGenerateModuleMapNullMap() {
		try {
			new AcceleoService(previewStrategy).doGenerate((Map<Module, Set<String>>)null, inputModel, null,
					new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Tests the behavior of the method
	 * {@link AcceleoService#doGenerate(java.util.Map, EObject, java.io.File, boolean, org.eclipse.emf.common.util.Monitor)}
	 * in preview mode.
	 */
	@Test
	public void testDoGenerateModuleMapPreview() {
		final Map<Module, Set<String>> templates = getTemplateMap();
		final Map<String, String> preview = new AcceleoService(previewStrategy).doGenerate(templates,
				inputModel, null, null);
		assertNotNull("A preview should have been generated by the service", preview);
		assertFalse("The preview shouldn't be empty", preview.isEmpty());

		// We're generating one file per template, each having the same name as the template itself.
		int templateCount = 0;
		for (Map.Entry<Module, Set<String>> moduleEntry : templates.entrySet()) {
			for (String templateName : moduleEntry.getValue()) {
				templateCount++;
				final String writer = preview.get(templateName);
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
	 * Checks that trying to call a template which name doesn't match with any of the templates in the given
	 * module fails.
	 */
	@Test
	public void testDoGenerateModuleMapUnmatchedTemplate() {
		try {
			final Map<Module, Set<String>> templates = new HashMap<Module, Set<String>>(1);
			final Set<String> module1Templates = new HashSet<String>(2);
			module1Templates.add("unmatched_name");
			templates.put(module1, module1Templates);
			new AcceleoService(previewStrategy).doGenerate(templates, inputModel, null, new BasicMonitor());
			fail(FAIL_EXPECTED_AEE);
		} catch (AcceleoEvaluationException e) {
			// expected behavior
		}
	}

	/**
	 * Tests the behavior of the method {@link AcceleoService#doGenerate(org.eclipse.acceleo.model.mtl.Module,
	 * java.lang.String, EObject, List<? extends Object>, java.io.File, boolean,
	 * org.eclipse.emf.common.util.Monitor)} in generation mode (preview = false).
	 */
	@Test
	@Ignore
	public void testDoGenerateModuleNoPreview() {
		this.init("Module"); //$NON-NLS-1$
		this.generate("template_1_1", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Checks that the behavior when sending null as the generation root when not in preview mode doesn't
	 * change.
	 */
	@Test
	public void testDoGenerateModuleNoPreviewNoGenerationRoot() {
		try {
			new AcceleoService().doGenerate(module1, "template_1_1", inputModel, null, new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that the behavior when sending null input EObjects doesn't change.
	 */
	@Test
	public void testDoGenerateModuleNullInput() {
		try {
			new AcceleoService(previewStrategy).doGenerate(module1, "template_1_1", null, null,
					new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that the behavior when sending null as the module doesn't change.
	 */
	@Test
	public void testDoGenerateModuleNullModule() {
		try {
			new AcceleoService(previewStrategy).doGenerate((Module)null, "template_1_1", inputModel, null,
					new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Tests the behavior of the method {@link AcceleoService#doGenerate(org.eclipse.acceleo.model.mtl.Module,
	 * java.lang.String, EObject, List<? extends Object>, java.io.File, boolean,
	 * org.eclipse.emf.common.util.Monitor)} in preview mode.
	 */
	@Test
	public void testDoGenerateModulePreview() {
		final Map<String, String> preview = new AcceleoService(previewStrategy).doGenerate(module1,
				"template_1_1", inputModel, null, null);
		assertNotNull("A preview should have been generated by the service", preview);
		assertFalse("The preview shouldn't be empty", preview.isEmpty());

		// We're generating a single file which name is the same as the template itself.
		assertSame("Unexpected number of templates evaluated.", 1, preview.size());
		final Map.Entry<String, String> previewEntry = preview.entrySet().iterator().next();
		assertEquals("Unexpected name for the template", "template_1_1", previewEntry.getKey());
		final String writer = previewEntry.getValue();
		if (writer != null) {
			assertEquals("Unexpected content for the generated file.", FILE_OUTPUT, writer.toString().trim());
		} else {
			fail("A file should have been generated.");
		}
	}

	/**
	 * Calls a public template which argument is not the same as the input model.
	 */
	@Test
	public void testDoGenerateModulePreviewContainedTarget() {
		final Map<String, String> preview = new AcceleoService(previewStrategy).doGenerate(module1,
				"template_1_2", inputModel, null, null);
		assertNotNull("A preview should have been generated by the service", preview);
		assertFalse("The preview shouldn't be empty", preview.isEmpty());

		// We had three targets, yet the file name is always the same. Expects a single preview
		assertSame("Unexpected number of templates evaluated.", 1, preview.size());
		final Map.Entry<String, String> previewEntry = preview.entrySet().iterator().next();
		assertEquals("Unexpected name for the template", "template_1_2", previewEntry.getKey());
		final String writer = previewEntry.getValue();
		if (writer != null) {
			assertEquals("Unexpected content for the generated file.", FILE_OUTPUT, writer.toString().trim());
		} else {
			fail("A file should have been generated.");
		}
	}

	/**
	 * Checks that trying to call a private template indeed fails.
	 */
	@Test
	public void testDoGenerateModulePrivateTemplate() {
		try {
			new AcceleoService(previewStrategy).doGenerate(module1, "template_1_3", inputModel, null,
					new BasicMonitor());
			fail(FAIL_EXPECTED_AEE);
		} catch (AcceleoEvaluationException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that trying to call a template which name doesn't match with any of the templates in the given
	 * module fails.
	 */
	@Test
	public void testDoGenerateModuleUnmatchedTemplate() {
		try {
			new AcceleoService(previewStrategy).doGenerate(module1, "unmatched_name", inputModel, null,
					new BasicMonitor());
			fail(FAIL_EXPECTED_AEE);
		} catch (AcceleoEvaluationException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that trying a direct call on an unmatched template fails.
	 */
	@Test
	public void testDoGenerateTemplateModuleUnmatchedTemplate() {
		try {
			arguments.add("test");
			arguments.add("test");
			new AcceleoService(previewStrategy).doGenerateTemplate(module2, "template_2_2", arguments, null,
					new BasicMonitor());
			fail(FAIL_EXPECTED_AEE);
		} catch (AcceleoEvaluationException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that trying to call a private template with multiple arguments defined without passing them
	 * fails.
	 */
	@Test
	public void testDoGenerateTemplateMultipleArguments() {
		try {
			new AcceleoService(previewStrategy).doGenerate(multipleArgumentTemplate, inputModel, null,
					new BasicMonitor());
			fail(FAIL_EXPECTED_AEE);
		} catch (AcceleoEvaluationException e) {
			// expected behavior
		}
	}

	/**
	 * Tests the behavior of the method
	 * {@link AcceleoService#doGenerate(org.eclipse.acceleo.model.mtl.Template, EObject, java.io.File, boolean, org.eclipse.emf.common.util.Monitor)}
	 * in generation mode (preview = false).
	 */
	@Test
	@Ignore
	public void testDoGenerateTemplateNoPreview() {
		this.init("Template"); //$NON-NLS-1$
		this.generate("template_1_1", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Checks that the behavior when sending null as the generation root when not in preview mode doesn't
	 * change.
	 */
	@Test
	public void testDoGenerateTemplateNoPreviewNoGenerationRoot() {
		try {
			new AcceleoService().doGenerate(publicTemplate, inputModel, null, new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that the behavior when sending null input EObjects doesn't change.
	 */
	@Test
	public void testDoGenerateTemplateNullInput() {
		try {
			new AcceleoService(previewStrategy).doGenerate(publicTemplate, null, null, new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Checks that the behavior when sending null as the template doesn't change.
	 */
	@Test
	public void testDoGenerateTemplateNullTemplate() {
		try {
			new AcceleoService(previewStrategy).doGenerate((Template)null, inputModel, null,
					new BasicMonitor());
			fail(FAIL_EXPECTED_NPE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Tests the behavior of the method
	 * {@link AcceleoService#doGenerate(org.eclipse.acceleo.model.mtl.Template, EObject, java.io.File, boolean, org.eclipse.emf.common.util.Monitor)}
	 * in preview mode.
	 */
	@Test
	public void testDoGenerateTemplatePreview() {
		final Map<String, String> preview = new AcceleoService(previewStrategy).doGenerate(publicTemplate,
				inputModel, null, null);
		assertNotNull("A preview should have been generated by the service", preview);
		assertFalse("The preview shouldn't be empty", preview.isEmpty());

		// We're generating a single file which name is the same as the template itself.
		assertSame("Unexpected number of templates evaluated.", 1, preview.size());
		final Map.Entry<String, String> previewEntry = preview.entrySet().iterator().next();
		assertEquals("Unexpected name for the template", publicTemplate.getName(), previewEntry.getKey());
		final String writer = previewEntry.getValue();
		if (writer != null) {
			assertEquals("Unexpected content for the generated file.", FILE_OUTPUT, writer.toString().trim());
		} else {
			fail("A file should have been generated.");
		}
	}

	/**
	 * Checks that trying to call a private template indeed fails.
	 */
	@Test
	public void testDoGenerateTemplatePrivateTemplate() {
		try {
			new AcceleoService(previewStrategy).doGenerate(privateTemplate, inputModel, null,
					new BasicMonitor());
			fail(FAIL_EXPECTED_AEE);
		} catch (AcceleoEvaluationException e) {
			// expected behavior
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Before
	@Override
	public void setUp() {
		// shortcuts inherited behavior
		String modulePath = "data/Service/Facade/service_module_1.mtl";
		Resource modelResource = parse(modulePath);
		EObject rootModule = modelResource.getContents().get(0);
		if (rootModule instanceof Module) {
			module1 = (Module)rootModule;
		} else {
			fail("Couldn't load the input template " + modulePath); //$NON-NLS-1$
		}
		for (ModuleElement element : module1.getOwnedModuleElement()) {
			if (element instanceof Template) {
				Template template = (Template)element;
				if ("template_1_1".equals(template.getName())) {
					publicTemplate = template;
				} else if ("template_1_3".equals(template.getName())) {
					privateTemplate = template;
				}
			}
		}
		modulePath = "data/Service/Facade/service_module_2.mtl";
		modelResource = parse(modulePath);
		rootModule = modelResource.getContents().get(0);
		if (rootModule instanceof Module) {
			module2 = (Module)rootModule;
		} else {
			fail("Couldn't load the input template " + modulePath); //$NON-NLS-1$
		}
		for (ModuleElement element : module2.getOwnedModuleElement()) {
			if (element instanceof Template) {
				Template template = (Template)element;
				if ("template_2_2".equals(template.getName())) {
					multipleArgumentTemplate = template;
				}
			}
		}
		arguments = new ArrayList<Object>();

		defaultStrategy = new DefaultStrategy();
		previewStrategy = new PreviewStrategy();
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
}
