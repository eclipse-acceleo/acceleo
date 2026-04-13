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
package org.eclipse.acceleo.query.ide.tests.runtime.impl.namespace;

import java.util.Set;

import org.eclipse.acceleo.query.ide.runtime.impl.namespace.OSGiQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Test;
import org.osgi.framework.Bundle;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class OSGiQualifiedNameResolverTests {

	@Test
	public void getAvailableQualifiedName() {
		final Bundle bundle = Platform.getBundle("org.eclipse.emf.ecore");
		OSGiQualifiedNameResolver resolver = new OSGiQualifiedNameResolver(bundle, EPackage.Registry.INSTANCE,
				"::");
		ILoader loader = new JavaLoader("::", false);
		resolver.addLoader(loader);

		final Set<String> qualifiedNames = resolver.getAvailableQualifiedNames();

		assertTrue(qualifiedNames.contains("org::eclipse::emf::ecore::EcorePackage"));
	}

}
