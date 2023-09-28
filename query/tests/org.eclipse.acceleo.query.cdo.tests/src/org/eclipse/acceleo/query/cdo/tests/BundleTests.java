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
package org.eclipse.acceleo.query.cdo.tests;

import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.cdo.AqlCDOUtils;
import org.eclipse.acceleo.query.cdo.services.configurator.CDOResourceSetConfigurator;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.services.configurator.IResourceSetConfigurator;
import org.junit.Before;
import org.junit.Test;

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
		// Make sure the org.eclipse.acceleo.query.sirius is loaded
		AqlCDOUtils.class.getName();
	}

	@Test
	public void isRegisteredHTTPServiceConfigurator() {
		boolean hasCDOResourceSetConfigurator = false;
		for (IResourceSetConfigurator configurator : AQLUtils.getResourceSetConfigurators()) {
			if (configurator instanceof CDOResourceSetConfigurator) {
				hasCDOResourceSetConfigurator = true;
				break;
			}
		}
		assertTrue(hasCDOResourceSetConfigurator);
	}

}
