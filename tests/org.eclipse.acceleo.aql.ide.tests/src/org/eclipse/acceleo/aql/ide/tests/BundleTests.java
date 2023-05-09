/*******************************************************************************
 * Copyright (c) 2017, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * Tests bundle behavior.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class BundleTests {

	@Before
	public void setUp() {
		// Make sure the org.eclipse.acceleo.aql is loaded
		AcceleoUtil.class.getClass();
		// Make sure the org.eclipse.acceleo.aql.ide is loaded
		AcceleoPlugin.getPlugin();
	}

	@Test
	public void notSingleton() {
		final Bundle bundle = Platform.getBundle("org.eclipse.acceleo.aql");
		assertNotNull(bundle);
		assertNull(bundle.getHeaders().get("singleton"));
	}

	@Test
	public void isRegistredEcore() {
		assertNotNull(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/acceleo/4.0"));
	}
}
