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
package org.eclipse.acceleo.aql.ide.resolver;

import org.eclipse.acceleo.aql.resolver.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

public class OSGiQualifiedNameResolver extends ClassLoaderQualifiedNameResolver {

	/**
	 * Constructs a qualified name resolver for the specified bundle.
	 * 
	 * @param bundle
	 *            The bundle containing the modules and services we'll need during the generation.
	 * @param queryEnvironment
	 *            The AQL environment to use when parsing resolved modules.
	 */
	public OSGiQualifiedNameResolver(Bundle bundle, IReadOnlyQueryEnvironment queryEnvironment) {
		super(createBundleClassLoader(bundle), queryEnvironment);
	}

	/**
	 * Retrieve the ClassLoader of the specified bundle.
	 * 
	 * @param bundle
	 *            Bundle for which we need a class loader.
	 * @return The Bundle's class loader.
	 */
	protected static ClassLoader createBundleClassLoader(Bundle bundle) {
		return bundle.adapt(BundleWiring.class).getClassLoader();
	}

}
