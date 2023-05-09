/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.runtime.impl.namespace;

import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

/**
 * OSGi resolver.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class OSGiQualifiedNameResolver extends ClassLoaderQualifiedNameResolver {

	/**
	 * Constructor.
	 * 
	 * @param bundle
	 *            the bundle containing the modules and services we'll need during the generation
	 * @param qualifierSeparator
	 *            the qualifier name separator
	 */
	public OSGiQualifiedNameResolver(Bundle bundle, String qualifierSeparator) {
		super(createBundleClassLoader(bundle), qualifierSeparator);
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
