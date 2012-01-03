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
package org.eclipse.acceleo.engine.tests.unit.generation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.AcceleoRuntimeException;
import org.eclipse.acceleo.engine.generation.AcceleoEngine;
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;

/**
 * This will be used to test the behavior of the generic Acceleo engine.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoGenericEngineTest extends AbstractAcceleoTest {
	/** Error message displayed for test failure when expected AcceleoEvaluationExceptions aren't thrown. */
	private static final String EVALUATION_EXCEPTION_FAILURE = "Expected AcceleoEvaluationException hasn't been thrown by the evaluation engine.";

	/** Error message displayed for test failure when expected NPEs aren't thrown. */
	private static final String NPE_FAILURE = "Expected NullPointerException hasn't been thrown by the evaluation engine.";

	/** List of invalid arguments for the template call. */
	private List<Object> invalidArguments = new ArrayList<Object>();

	/** The template that is to be called for tests on a private one. */
	private Template privateTemplate;

	/** The template that is to be called for tests on a public, guarded template (guard evaluates to false). */
	private Template publicGuardedTemplate;

	/** The template that is to be called for tests on a public one. */
	private Template publicTemplate;

	/** List of valid arguments for the template call. */
	private List<Object> validArguments = new ArrayList<Object>();

	/** This initializer allows us to initialize all required input. */
	{
		try {
			final URI inputModelURI = URI.createPlatformPluginURI('/' + AcceleoEngineTestPlugin.PLUGIN_ID
					+ '/' + "data/abstractClass.ecore", true);
			inputModel = ModelUtils.load(inputModelURI, resourceSet);
		} catch (IOException e) {
			fail("Error loading the input model.");
		}
		validArguments.add(inputModel);
		validArguments.add("aString");

		invalidArguments.add(inputModel);
		invalidArguments.add(Integer.valueOf(5));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/GenericEngine/generic_engine.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "GenericEngine";
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} on a public
	 * template which guard evaluates to false.
	 * <p>
	 * Whatever the arguments (when falling in "valid" categories as tested otherwise for NPEs), expects the
	 * evaluation not to generate a single file and the evaluation preview to be empty.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateGuardedTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("GuardedTemplate"));

		// preview mode
		Map<String, String> previewMode = new AcceleoEngine().evaluate(publicGuardedTemplate, validArguments,
				generationRoot, previewStrategy, new BasicMonitor());

		assertTrue("Preview map should have been empty", previewMode.isEmpty());
		assertSame("There shouldn't have been generated files", 0, getFiles(generationRoot).length);

		// generation mode
		Map<String, String> generationMode = new AcceleoEngine().evaluate(publicGuardedTemplate,
				validArguments, generationRoot, defaultStrategy, new BasicMonitor());

		assertTrue("Preview map should have been empty", generationMode.isEmpty());
		assertSame("There shouldn't have been generated files", 0, getFiles(generationRoot).length);
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} on a valid template
	 * with mismatching arguments when a guard must be evaluated.
	 * <p>
	 * Expects an {@link AcceleoEvaluationException} to be thrown.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateGuardedTemplateMismatchingArgs() throws IOException {
		generationRoot = new File(getGenerationRootPath("GuardedTemplate"));
		try {
			new AcceleoEngine().evaluate(publicGuardedTemplate, invalidArguments, generationRoot,
					defaultStrategy, new BasicMonitor());
			fail(EVALUATION_EXCEPTION_FAILURE);
		} catch (AcceleoEvaluationException e) {
			// Expected behavior
		}
		try {
			new AcceleoEngine().evaluate(publicGuardedTemplate, invalidArguments, generationRoot,
					previewStrategy, new BasicMonitor());
			fail(EVALUATION_EXCEPTION_FAILURE);
		} catch (AcceleoEvaluationException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} with a
	 * <code>null</code> list of arguments.
	 * <p>
	 * Expects a NullPointerException to be thrown.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateNullArgs() throws IOException {
		generationRoot = new File(getGenerationRootPath("InvalidNullArgs"));
		try {
			new AcceleoEngine().evaluate(publicTemplate, null, generationRoot, previewStrategy,
					new BasicMonitor());
			fail(NPE_FAILURE);
		} catch (NullPointerException e) {
			// expected behavior
		}
		try {
			new AcceleoEngine().evaluate(privateTemplate, null, generationRoot, defaultStrategy,
					new BasicMonitor());
			fail(NPE_FAILURE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} with a
	 * <code>null</code> generationRoot and not in preview mode.
	 * <p>
	 * Expects a NullPointerException to be thrown.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateNullRootNoPreview() throws IOException {
		try {
			new AcceleoEngine().evaluate(publicTemplate, validArguments, null, defaultStrategy,
					new BasicMonitor());
			fail(NPE_FAILURE);
		} catch (AcceleoRuntimeException e) {
			Throwable cause = e.getCause();
			// expected behavior
			assertTrue(cause instanceof NullPointerException);
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} with a
	 * <code>null</code> template.
	 * <p>
	 * Expects a NullPointerException to be thrown.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluateNullTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("InvalidNullTemplate"));
		try {
			new AcceleoEngine().evaluate(null, validArguments, generationRoot, previewStrategy,
					new BasicMonitor());
			fail(NPE_FAILURE);
		} catch (NullPointerException e) {
			// expected behavior
		}
		try {
			new AcceleoEngine().evaluate(null, invalidArguments, generationRoot, defaultStrategy,
					new BasicMonitor());
			fail(NPE_FAILURE);
		} catch (NullPointerException e) {
			// expected behavior
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} on a private
	 * template. Regardless of the arguments (when falling in "valid" categories as tested otherwise for
	 * NPEs), we expect an {@link AcceleoEvaluationException} to be thrown.
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluatePrivateTemplate() throws IOException {
		generationRoot = new File(getGenerationRootPath("PrivateTemplate"));
		try {
			new AcceleoEngine().evaluate(privateTemplate, validArguments, generationRoot, defaultStrategy,
					new BasicMonitor());
			fail(EVALUATION_EXCEPTION_FAILURE);
		} catch (AcceleoEvaluationException e) {
			// Expected behavior
		}
		try {
			new AcceleoEngine().evaluate(privateTemplate, validArguments, generationRoot, previewStrategy,
					new BasicMonitor());
			fail(EVALUATION_EXCEPTION_FAILURE);
		} catch (AcceleoEvaluationException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} on a valid template
	 * with mismatching arguments.
	 * <p>
	 * Expects an {@link AcceleoEvaluationException} to be thrown.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluatePublicTemplateMismatchingArgs() throws IOException {
		generationRoot = new File(getGenerationRootPath("MismatchingArgs"));
		try {
			new AcceleoEngine().evaluate(publicTemplate, invalidArguments, generationRoot, defaultStrategy,
					new BasicMonitor());
			fail(EVALUATION_EXCEPTION_FAILURE);
		} catch (AcceleoEvaluationException e) {
			// Expected behavior
		}
		try {
			new AcceleoEngine().evaluate(publicTemplate, invalidArguments, generationRoot, previewStrategy,
					new BasicMonitor());
			fail(EVALUATION_EXCEPTION_FAILURE);
		} catch (AcceleoEvaluationException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} with a valid
	 * generationRoot when not in preview mode. Valid arguments are passed to the engine and a public template
	 * is called.
	 * <p>
	 * Expects the generation to run accurately and create the reference file.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluatePublicTemplateValidArgs() throws IOException {
		generationRoot = new File(getGenerationRootPath("PublicTemplateValidArgs"));
		referenceRoot = new File(getReferenceRootPath("PublicTemplateValidArgs"));

		cleanGenerationRoot();

		Map<String, String> preview = new AcceleoEngine().evaluate(publicTemplate, validArguments,
				generationRoot, defaultStrategy, new BasicMonitor());

		assertTrue("Preview map should have been empty", preview.isEmpty());
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			assertTrue(content.contains("constant output")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} with a
	 * <code>null</code> generationRoot in preview mode. Valid arguments are passed to the engine and a public
	 * template is called.
	 * <p>
	 * Expects the generation to run accurately and return the generation preview.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluatePublicTemplateValidArgsNullRootPreview() throws IOException {
		generationRoot = new File(getGenerationRootPath("PublicTemplateValidArgsNullRootPreview"));

		Map<String, String> preview = new AcceleoEngine().evaluate(publicTemplate, validArguments, null,
				previewStrategy, new BasicMonitor());

		assertFalse("Preview map was empty", preview.isEmpty());
		assertSame("There should have been a single result for preview", 1, preview.size());
		assertSame("There shouldn't have been generated files", 0, getFiles(generationRoot).length);

		Entry<String, String> entry = preview.entrySet().iterator().next();
		assertEquals("Preview didn't contain the accurate file preview.", "test_generic_engine", entry
				.getKey());
		assertTrue("Preview didn't contain the accurate output.", entry.getValue().toString().contains(
				"constant output"));
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} with a valid
	 * generationRoot in preview mode. Valid arguments are passed to the engine and a public template is
	 * called.
	 * <p>
	 * Expects the generation to run accurately and create the accurate preview.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluatePublicTemplateValidArgsPreview() throws IOException {
		generationRoot = new File(getGenerationRootPath("PublicTemplateValidArgsPreview"));

		Map<String, String> preview = new AcceleoEngine().evaluate(publicTemplate, validArguments,
				generationRoot, previewStrategy, new BasicMonitor());

		assertFalse("Preview map was empty", preview.isEmpty());
		assertSame("There should have been a single result for preview", 1, preview.size());
		assertSame("There shouldn't have been generated files", 0, getFiles(generationRoot).length);

		Entry<String, String> entry = preview.entrySet().iterator().next();
		assertEquals("Preview didn't contain the accurate file preview.", generationRoot.getPath()
				+ File.separatorChar + "test_generic_engine", entry.getKey());
		assertTrue("Preview didn't contain the accurate output.", entry.getValue().toString().contains(
				"constant output"));
	}

	/**
	 * Tests the behavior of {@link AcceleoEngine#evaluate(Template, List, File, boolean)} on a valid template
	 * with a wrong number of arguments.
	 * <p>
	 * Whether we provide too many arguments or too few, expects an {@link AcceleoEvaluationException} to be
	 * thrown.
	 * </p>
	 * 
	 * @throws IOException
	 *             Thrown if the evaluation fails unexpectedly.
	 */
	public void testEvaluatePublicTemplateWrongArgCount() throws IOException {
		generationRoot = new File(getGenerationRootPath("WrongArgCount"));
		// Too many args
		validArguments.add(Integer.valueOf(5));
		try {
			new AcceleoEngine().evaluate(publicTemplate, validArguments, generationRoot, defaultStrategy,
					new BasicMonitor());
			fail(EVALUATION_EXCEPTION_FAILURE);
		} catch (AcceleoEvaluationException e) {
			// Expected behavior
		}
		// Too few arguments
		validArguments.remove(0);
		validArguments.remove(0);
		try {
			new AcceleoEngine().evaluate(publicTemplate, validArguments, generationRoot, previewStrategy,
					new BasicMonitor());
			fail(EVALUATION_EXCEPTION_FAILURE);
		} catch (AcceleoEvaluationException e) {
			// Expected behavior
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		for (ModuleElement element : module.getOwnedModuleElement()) {
			if (element instanceof Template) {
				Template candidate = (Template)element;
				if (candidate.getVisibility() == VisibilityKind.PUBLIC
						&& candidate.getName().endsWith("guard")) {
					publicGuardedTemplate = candidate;
				} else if (candidate.getVisibility() == VisibilityKind.PUBLIC) {
					publicTemplate = candidate;
				} else {
					privateTemplate = candidate;
				}
			}
		}
	}
}
