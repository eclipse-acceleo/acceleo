/*******************************************************************************
 * Copyright (c) 2015, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.tests;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstEPackageTests {

	@Test
	public void ePackageRegistration() {
		assertNotNull(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/acceleo/query/8.0"));
	}

	@Test
	public void genModelRegistration() {
		assertNotNull(EcorePlugin.getEPackageNsURIToGenModelLocationMap(true).get(
				"http://www.eclipse.org/acceleo/query/8.0").toString());
	}
}
