/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.resolver;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.resolver.ClassloaderModuleResolver;
import org.eclipse.acceleo.aql.resolver.IModuleResolver;
import org.eclipse.acceleo.query.runtime.Query;
import org.junit.Test;

public class ClassloaderModuleResolverTests {

	/**
	 * The {@link ClassloaderModuleResolver} to test.
	 */
	private final IModuleResolver resolver = new ClassloaderModuleResolver(getClass().getClassLoader(), Query
			.newEnvironmentWithDefaultServices(null));

	@Test
	public void resolveModuleNotExistingModule() throws IOException {
		final Module module = resolver.resolveModule("not::existing");
		assertEquals(null, module);
	}

	@Test
	public void resolveModule() throws IOException {
		final Module module = resolver.resolveModule("org::eclipse::acceleo::tests::resolver::nominal");
		assertEquals("myModule", module.getName());
	}

}
