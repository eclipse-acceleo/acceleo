/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.tests;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstEPackageTests {

	@Test
	public void ePackageRegistration() {
		assertNotNull(EPackage.Registry.INSTANCE.getEPackage("http://www.eclipse.org/acceleo/query/1.0"));
	}

	@Test
	public void genModelRegistration() {
		assertEquals("platform:/plugin/org.eclipse.acceleo.query/model/ast.genmodel", EcorePlugin
				.getEPackageNsURIToGenModelLocationMap(true).get("http://www.eclipse.org/acceleo/query/1.0")
				.toString());
	}
}
