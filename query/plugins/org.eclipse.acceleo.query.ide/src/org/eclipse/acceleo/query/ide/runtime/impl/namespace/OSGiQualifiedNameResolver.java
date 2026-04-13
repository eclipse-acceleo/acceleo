/*******************************************************************************
 * Copyright (c) 2020, 2026 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.runtime.impl.namespace;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.impl.namespace.CallStack;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.emf.ecore.EPackage;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWire;
import org.osgi.framework.wiring.BundleWiring;

/**
 * OSGi resolver. The resolution takes place in the {@link IQualifiedNameLookupEngine#getCurrentContext()
 * current context}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class OSGiQualifiedNameResolver extends ClassLoaderQualifiedNameResolver {

	/**
	 * The mapping from a qualified name and its {@link BundleWiring}.
	 */
	private final Map<String, Bundle> qualifiedNameToBundle = new HashMap<>();

	/**
	 * The {@link Bundle}.
	 */
	private final Bundle bundle;

	/**
	 * Constructor.
	 * 
	 * @param bundle
	 *            the bundle containing the modules and services we'll need during the generation
	 * @param ePackageRegistry
	 *            the {@link EPackage.Registry} used to resolve {@link EPackage#getNsURI() nsURI}
	 * @param qualifierSeparator
	 *            the qualifier name separator
	 */
	public OSGiQualifiedNameResolver(Bundle bundle, EPackage.Registry ePackageRegistry,
			String qualifierSeparator) {
		super(createBundleClassLoader(bundle), ePackageRegistry, qualifierSeparator);
		this.bundle = bundle;
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

	@Override
	protected void register(ILoader loader, String qualifiedName, Object object) {
		super.register(loader, qualifiedName, object);
		final String resourceName = loader.resourceName(qualifiedName);
		final Bundle resourceBundle = getBundle(bundle, resourceName);
		qualifiedNameToBundle.put(qualifiedName, resourceBundle);
	}

	/**
	 * Gets the {@link Bundle} declaring the given resource. Either the given {@link Bundle} or one of its
	 * direct or indirect dependency.
	 * 
	 * @param root
	 *            the root {@link Bundle}
	 * @param resource
	 *            the resource to look for
	 * @return the {@link Bundle} declaring the given resource. Either the given {@link Bundle} or one of its
	 *         direct or indirect dependency.
	 */
	private Bundle getBundle(Bundle root, String resource) {
		Bundle res = null;

		final URL entry = root.getResource(resource);
		if (isLocalResource(root, entry)) {
			res = root;
		} else {
			final BundleWiring rootWiring = root.adapt(BundleWiring.class);
			for (BundleWire requirement : rootWiring.getRequiredWires(null)) {
				final Bundle requiredBundle = requirement.getProviderWiring().getBundle();
				final URL requiredBundleEntry = requiredBundle.getResource(resource);
				if (isLocalResource(requiredBundle, requiredBundleEntry)) {
					res = requiredBundle;
					break;
				}
			}
		}

		return res;
	}

	/**
	 * Tells if the given resource {@link URL} is local to the given {@link Bundle}.
	 * 
	 * @param bundle
	 *            the {@link Bundle}
	 * @param resource
	 *            the resource {@link URL}
	 * @return <code>true</code> if the given resource {@link URL} is local to the given {@link Bundle},
	 *         <code>false</code> otherwise
	 */
	private boolean isLocalResource(Bundle bundle, URL resource) {
		return resource != null && resource.toString().startsWith(bundle.getResource("/").toString());
	}

	@Override
	protected ClassLoader getClassLoader() {
		final String contextQualifiedName = getContextQualifiedName();
		final BundleWiring contextBundleWiring = qualifiedNameToBundle.getOrDefault(contextQualifiedName,
				bundle).adapt(BundleWiring.class);

		return contextBundleWiring.getClassLoader();
	}

	/**
	 * Gets the context qualified name.
	 * 
	 * @return the context qualified name
	 */
	protected String getContextQualifiedName() {
		final String res;

		final CallStack currentContext = getLookupEngine().getCurrentContext();
		if (currentContext != null) {
			if (!currentContext.isEmpty()) {
				res = currentContext.peek();
			} else {
				res = currentContext.getStartingQualifiedName();
			}
		} else {
			res = null;
		}

		return res;
	}

	@Override
	public Set<String> getAvailableQualifiedNames() {
		final Set<String> res = new LinkedHashSet<>();

		if (!loaders.isEmpty()) {
			final Enumeration<URL> entries = bundle.findEntries("/", "*", true);
			res.addAll(getQualifiedNamesForEntries(entries));
		}

		return res;
	}

	/**
	 * Gets the {@link Set} of qualified names for the given entries.
	 * 
	 * @param entries
	 *            the entries
	 * @return the {@link Set} of qualified names for the given entries
	 */
	private Set<String> getQualifiedNamesForEntries(Enumeration<URL> entries) {
		final Set<String> res = new LinkedHashSet<>();

		if (entries != null) {
			while (entries.hasMoreElements()) {
				final URL entry = entries.nextElement();
				for (ILoader loader : loaders) {
					final String resourceName = entry.getPath().substring(1);
					final String qualifiedName = loader.qualifiedName(resourceName);
					if (qualifiedName != null) {
						res.add(qualifiedName);
					}
				}
			}
		}

		return res;
	}

}
