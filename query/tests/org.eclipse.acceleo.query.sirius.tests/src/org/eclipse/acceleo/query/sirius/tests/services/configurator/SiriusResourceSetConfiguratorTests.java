/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.sirius.tests.services.configurator;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.sirius.AqlSiriusUtils;
import org.eclipse.acceleo.query.sirius.services.configurator.SiriusResourceSetConfigurator;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link SiriusResourceSetConfigurator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SiriusResourceSetConfiguratorTests {

	@Test(expected = IllegalArgumentException.class)
	public void createResourceSetForModelsWrongAirdURI() throws IOException {
		final SiriusResourceSetConfigurator configurator = new SiriusResourceSetConfigurator();
		final Map<String, String> options = new LinkedHashMap<String, String>();
		String currentPath = new java.io.File("resources/NotExisting.aird").getCanonicalPath();
		options.put(AQLUtils.BASE_URI_OPTION, URI.createFileURI(currentPath).toString());
		options.put(AqlSiriusUtils.SIRIUS_SESSION_OPTION, "NotExisting.aird");

		final ResourceSet rs = configurator.createResourceSetForModels(this, options);
		configurator.cleanResourceSetForModels(this);
	}

	// TODO issue with target platform resolution AQL 7.x is not present during build
	// @Test
	// public void createResourceSetForModels() throws IOException {
	// final SiriusResourceSetConfigurator configurator = new SiriusResourceSetConfigurator();
	// final Map<String, String> options = new LinkedHashMap<String, String>();
	// String currentPath = new java.io.File("resources/representations.aird").getCanonicalPath();
	// options.put(AQLUtils.BASE_URI_OPTION, URI.createFileURI(currentPath).toString());
	// options.put(AqlSiriusUtils.SIRIUS_SESSION_OPTION, "representations.aird");
	//
	// final ResourceSet rs = configurator.createResourceSetForModels(this, options);
	// configurator.cleanResourceSetForModels(this);
	//
	// assertEquals(true, rs != null);
	// }

	// TODO the modeling project is never found even with the modeling nature
	// @Test
	// public void getInitializedOptions() {
	// final SiriusResourceSetConfigurator configurator = new SiriusResourceSetConfigurator();
	//
	// final Map<String, String> options = new LinkedHashMap<String, String>();
	// options.put(AQLUtils.BASE_URI_OPTION, URI.createPlatformResourceURI(
	// "/org.eclipse.acceleo.query.sirius.tests/resources/", false).toString());
	//
	// final Map<String, String> initializedOptions = configurator.getInitializedOptions(options);
	//
	// assertEquals("", initializedOptions.get(AqlSiriusUtils.SIRIUS_SESSION_OPTION));
	// }

	@Test
	public void validateWrongAirdURI() throws IOException {
		final SiriusResourceSetConfigurator configurator = new SiriusResourceSetConfigurator();
		final Map<String, String> options = new LinkedHashMap<String, String>();
		String currentPath = new java.io.File("resources/NotExisting.aird").getCanonicalPath();
		options.put(AQLUtils.BASE_URI_OPTION, URI.createFileURI(currentPath).toString());
		options.put(AqlSiriusUtils.SIRIUS_SESSION_OPTION, "NotExisting.aird");

		final Map<String, List<Diagnostic>> diagnostics = configurator.validate(null, options);

		assertEquals(1, diagnostics.size());
		final List<Diagnostic> list = diagnostics.get(AqlSiriusUtils.SIRIUS_SESSION_OPTION);
		assertEquals(true, list != null);
		assertEquals(1, list.size());
		assertEquals(true, list.get(0).getMessage().contains("The Sirius session doesn't exist"));
		assertEquals(true, list.get(0).getMessage().contains("NotExisting.aird"));
	}

	@Test
	public void validate() throws IOException {
		final SiriusResourceSetConfigurator configurator = new SiriusResourceSetConfigurator();
		final Map<String, String> options = new LinkedHashMap<String, String>();
		String currentPath = new java.io.File("resources/representations.aird").getCanonicalPath();
		options.put(AQLUtils.BASE_URI_OPTION, URI.createFileURI(currentPath).toString());
		options.put(AqlSiriusUtils.SIRIUS_SESSION_OPTION, "representations.aird");

		final Map<String, List<Diagnostic>> diagnostics = configurator.validate(null, options);

		assertEquals(0, diagnostics.size());
	}

}
