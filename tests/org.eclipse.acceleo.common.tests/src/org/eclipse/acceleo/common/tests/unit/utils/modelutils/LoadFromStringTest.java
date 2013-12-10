/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.tests.unit.utils.modelutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.common.tests.AcceleoCommonTestPlugin;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.Test;

/**
 * Tests the behavior of {@link ModelUtils#load(String)}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class LoadFromStringTest {
	/** This holds paths to unreadable or inexistant files. */
	private static final String[] INVALID_PATHS = {"/etc/shadow", "/etc/sudoers",
			"/inputs/attribute/attributeChange/v1.ecore",
			File.listRoots()[0].getAbsolutePath() + "projectname/modelname.extension", };

	/** Message displayed when an unexpected {@link IOException} is thrown. */
	private static final String MESSAGE_IOEXCEPTION_UNEXPECTED = "Unexpected IOException has been thrown.";

	/** Message displayed when an unexpected {@link NullPointerException} is thrown. */
	private static final String MESSAGE_NPE_UNEXPECTED = "Unexpected NullPointerException has been thrown.";

	/** Prefix of all the valid paths. */
	private static final String PATH_PREFIX = '/' + AcceleoCommonTestPlugin.PLUGIN_ID + '/'
			+ "data/modelutils";

	/** This holds strings referencing valid model locations. */
	private static final String[] VALID_PATHS = {PATH_PREFIX + "/testModels/attribute.ecore",
			PATH_PREFIX + "/testModels/volatileAttribute.ecore",
			"platform:/plugin/org.eclipse.acceleo.model/model/mtl.ecore", };

	/**
	 * Tries and call {@link ModelUtils#load(String, ResourceSet)} with an invalid path and no resourceSet.
	 * <p>
	 * Expects an NPE to be thrown.
	 * </p>
	 */
	@Test
	public void testLoadInvalidPathNullResourceSet() {
		for (String path : INVALID_PATHS) {
			try {
				ModelUtils.load(path, null);
				fail("Expected NullPointerException hasn't been thrown.");
			} catch (NullPointerException e) {
				// Expected behavior
			} catch (IOException e) {
				fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
			}
		}
	}

	/**
	 * Tries and call {@link ModelUtils#load(String, ResourceSet)} with an invalid path but a valid
	 * resourceSet.
	 * <p>
	 * Expects an IOException to be thrown.
	 * </p>
	 */
	@Test
	public void testLoadInvalidPathValidResourceSet() {
		for (String path : INVALID_PATHS) {
			final ResourceSet resourceSet = new ResourceSetImpl();
			try {
				ModelUtils.load(path, resourceSet);
				fail("Expected IOException hasn't been thrown.");
			} catch (IOException e) {
				// Expected behavior
			} catch (WrappedException e) {
				if (e.getCause() instanceof IOException) {
					// This was expected
				} else {
					throw e;
				}
			}
		}
	}

	/**
	 * Tries and call {@link ModelUtils#load(String, ResourceSet)} with <code>null</code> arguments or an
	 * empty String.
	 * <p>
	 * Expects an Illegal argument exception to be thrown.
	 * </p>
	 */
	@Test
	public void testLoadNullPath() {
		final String errMsg = "Expected IllegalArgumentException hasn't been thrown.";
		try {
			ModelUtils.load((String)null, null);
			fail(errMsg);
		} catch (NullPointerException e) {
			fail(MESSAGE_NPE_UNEXPECTED);
		} catch (IOException e) {
			fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
		try {
			ModelUtils.load("", null);
			fail(errMsg);
		} catch (NullPointerException e) {
			fail(MESSAGE_NPE_UNEXPECTED);
		} catch (IOException e) {
			fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}

	/**
	 * Tries and call {@link ModelUtils#load(String, ResourceSet)} with a valid path but no resourceSet.
	 * <p>
	 * Expects an NPE to be thrown.
	 * </p>
	 */
	@Test
	public void testLoadValidPathNullResourceSet() {
		final String errMsg = "Expected NullPointerException hasn't been thrown.";
		for (String path : VALID_PATHS) {
			try {
				ModelUtils.load(path, null);
				fail(errMsg);
			} catch (NullPointerException e) {
				// Expected behavior
			} catch (IOException e) {
				fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
			}
		}
	}

	/**
	 * Tries and call {@link ModelUtils#load(String, ResourceSet)} with both a valid path and a valid
	 * resourceSet.
	 * <p>
	 * Expects a non-null EObject associated to the resourceSet to be returned.
	 * </p>
	 */
	@Test
	public void testLoadValidPathValidResourceSet() {
		for (String path : VALID_PATHS) {
			final ResourceSet resourceSet = new ResourceSetImpl();
			try {
				final EObject result = ModelUtils.load(path, resourceSet);
				assertNotNull("ModelUtils didn't load the expected model.", result);
				assertEquals("Loaded model hasn't been associated with the expected resourceSet.",
						resourceSet, result.eResource().getResourceSet());
				assertTrue("Loaded model isn't the expected one.", result.eResource().getURI().toString()
						.endsWith(path));
			} catch (IOException e) {
				fail(MESSAGE_IOEXCEPTION_UNEXPECTED);
			}
		}
	}
}
