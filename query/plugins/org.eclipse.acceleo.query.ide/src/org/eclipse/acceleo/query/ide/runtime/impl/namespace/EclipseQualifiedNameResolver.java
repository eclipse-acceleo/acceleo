/*******************************************************************************
 * Copyright (c) 2020, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.runtime.impl.namespace;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EPackage;

/**
 * Eclipse resolver.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */

public class EclipseQualifiedNameResolver extends ClassLoaderQualifiedNameResolver {
	/**
	 * Can't load from workspace message.
	 */
	public static final String CAN_T_LOAD_FROM_WORKSPACE = "can't load from workspace.";

	/**
	 * Constructor.
	 * 
	 * @param classLoader
	 *            the default {@link ClassLoader}
	 * @param ePackageRegistry
	 *            the {@link EPackage.Registry} used to resolve {@link EPackage#getNsURI() nsURI}
	 * @param project
	 *            the {@link IProject}
	 * @param qualifierSeparator
	 *            the qualifier name separator
	 */
	public EclipseQualifiedNameResolver(ClassLoader classLoader, EPackage.Registry ePackageRegistry,
			IProject project, String qualifierSeparator) {
		super(createProjectClassLoader(classLoader, project), ePackageRegistry, qualifierSeparator);
	}

	/**
	 * Creates the class loader for the given {@link IProject}.
	 * 
	 * @param classLoader
	 *            the parent {@link ClassLoader}
	 * @param project
	 *            the {@link IProject}
	 * @return the class loader for the given {@link IProject}
	 */
	public static ClassLoader createProjectClassLoader(ClassLoader classLoader, IProject project) {
		ClassLoader res;

		try {
			final URL[] urls = new URL[] {project.getLocation().toFile().toURI().toURL() };
			res = new URLClassLoader(urls, classLoader);
		} catch (MalformedURLException e) {
			QueryPlugin.getPlugin().getLog().log(new Status(IStatus.ERROR, QueryPlugin.PLUGIN_ID,
					CAN_T_LOAD_FROM_WORKSPACE, e));
			res = classLoader;
		}

		return res;
	}

}
