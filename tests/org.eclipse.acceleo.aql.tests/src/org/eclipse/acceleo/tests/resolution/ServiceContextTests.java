/*******************************************************************************
 * Copyright (c) 2026 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.resolution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.QualifiedNameQueryEnvironment;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;

/**
 * Tests service context lookup across validation environments.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ServiceContextTests {

	private final AcceleoParser parser = new AcceleoParser();

	private final ClassLoaderQualifiedNameResolver resolver = new ClassLoaderQualifiedNameResolver(getClass()
			.getClassLoader(), EPackage.Registry.INSTANCE, AcceleoParser.QUALIFIER_SEPARATOR);

	public ServiceContextTests() {
		resolver.addLoader(new ModuleLoader(parser, null));
	}

	@Test
	public void equalServicesKeepTheirOwnContext() {
		final Module module = parseModule();
		final IService<?> first = getService(module, "first");
		final IService<?> second = getService(module, "second");

		assertNotSame(first, second);
		assertEquals(first, second);
		assertEquals("first", resolver.getContextQualifiedName(first));
		assertEquals("second", resolver.getContextQualifiedName(second));

		resolver.cleanContextQualifiedName("first");
		assertNull(resolver.getContextQualifiedName(first));
		assertEquals("second", resolver.getContextQualifiedName(second));
	}

	@Test
	public void clearingContextRemovesServicesFromEveryLoad() {
		final IService<?> first = getService(parseModule(), "test");
		final IService<?> second = getService(parseModule(), "test");

		assertEquals("test", resolver.getContextQualifiedName(first));
		assertEquals("test", resolver.getContextQualifiedName(second));

		resolver.cleanContextQualifiedName("test");
		assertNull(resolver.getContextQualifiedName(first));
		assertNull(resolver.getContextQualifiedName(second));
	}

	private IService<?> getService(Module module, String context) {
		return resolver.getServices(new QualifiedNameQueryEnvironment(resolver).getLookupEngine(), module,
				context).iterator().next();
	}

	private Module parseModule() {
		return parser.parse("[module test('http://www.eclipse.org/emf/2002/Ecore')/]\n"
				+ "[query public echo(value : String) : String = value /]", "UTF-8", "test").getModule();
	}
}
