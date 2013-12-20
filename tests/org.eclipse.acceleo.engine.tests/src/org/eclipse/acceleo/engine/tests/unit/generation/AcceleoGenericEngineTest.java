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

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.AcceleoRuntimeException;
import org.eclipse.acceleo.engine.generation.AcceleoEngine;
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This will be used to test the behavior of the generic Acceleo engine.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
@Ignore
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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
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
	@Test
	public void testEvaluateGuardedTemplate() throws IOException {
		this.init("GuardedTemplate"); //$NON-NLS-1$

		// preview mode
		Map<String, String> previewMode = new AcceleoEngine().evaluate(publicGuardedTemplate, validArguments,
				generationRoot, previewStrategy, new BasicMonitor());

		assertTrue("Preview map should have been empty", previewMode.isEmpty());
		assertSame("There shouldn't have been generated files", 0, generationRoot.listFiles().length);

		// generation mode
		Map<String, String> generationMode = new AcceleoEngine().evaluate(publicGuardedTemplate,
				validArguments, generationRoot, defaultStrategy, new BasicMonitor());

		assertTrue("Preview map should have been empty", generationMode.isEmpty());
		assertSame("There shouldn't have been generated files", 0, generationRoot.listFiles().length);
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
	@Test
	public void testEvaluateGuardedTemplateMismatchingArgs() throws IOException {
		this.init("GuardedTemplate"); //$NON-NLS-1$
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
	@Test
	public void testEvaluateNullArgs() throws IOException {
		this.init("InvalidNullArgs"); //$NON-NLS-1$
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
	@Test
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
	@Test
	public void testEvaluateNullTemplate() throws IOException {
		this.init("InvalidNullTemplate"); //$NON-NLS-1$
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
	@Test
	public void testEvaluatePrivateTemplate() throws IOException {
		this.init("PrivateTemplate"); //$NON-NLS-1$
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
	@Test
	public void testEvaluatePublicTemplateMismatchingArgs() throws IOException {
		this.init("MismatchingArgs"); //$NON-NLS-1$
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
	@Test
	public void testEvaluatePublicTemplateValidArgs() throws IOException {
		this.init("PublicTemplateValidArgs"); //$NON-NLS-1$
		this.generate("public_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
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
	@Test
	public void testEvaluatePublicTemplateValidArgsNullRootPreview() throws IOException {
		this.init("PublicTemplateValidArgsNullRootPreview"); //$NON-NLS-1$
		this.generate("public_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
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
	@Test
	public void testEvaluatePublicTemplateValidArgsPreview() throws IOException {
		this.init("PublicTemplateValidArgsPreview"); //$NON-NLS-1$
		this.generate("public_template", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
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
	@Test
	public void testEvaluatePublicTemplateWrongArgCount() throws IOException {
		this.init("WrongArgCount"); //$NON-NLS-1$

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
}
