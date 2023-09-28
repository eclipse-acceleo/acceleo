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
package org.eclipse.acceleo.query.ide.tests;

import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.services.configurator.HTTPServiceConfigurator;
import org.eclipse.acceleo.query.services.configurator.IServicesConfigurator;
import org.eclipse.core.runtime.Platform;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the OSGi bundle.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class BundleTests {

	@Before
	public void setUp() {
		// Make sure the org.eclipse.acceleo.query is loaded
		AQLUtils.class.getName();
		// Make sure the org.eclipse.acceleo.query.ide is loaded
		QueryPlugin.getPlugin();
	}

	@Test
	public void notSingleton() {
		final Bundle bundle = Platform.getBundle("org.eclipse.acceleo.query");
		assertNotNull(bundle);
		assertNull(bundle.getHeaders().get("singleton"));
	}

	@Test
	public void isRegisteredHTTPServiceConfigurator() {
		boolean hasHTTPServiceConfigurator = false;
		for (IServicesConfigurator configurator : AQLUtils.getServicesConfigurators(AQLUtils.AQL_LANGUAGE)) {
			if (configurator instanceof HTTPServiceConfigurator) {
				hasHTTPServiceConfigurator = true;
				break;
			}
		}
		assertTrue(hasHTTPServiceConfigurator);
	}

}
