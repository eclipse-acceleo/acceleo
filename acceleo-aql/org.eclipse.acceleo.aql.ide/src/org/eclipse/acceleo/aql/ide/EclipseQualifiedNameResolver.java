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
package org.eclipse.acceleo.aql.ide;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.eclipse.acceleo.aql.resolver.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class EclipseQualifiedNameResolver extends ClassLoaderQualifiedNameResolver {

	/**
	 * Can't load from workspace message.
	 */
	protected static final String CAN_T_LOAD_FROM_WORKSPACE = "can't load from workspace.";

	/**
	 * Constructor.
	 * 
	 * @param classLoader
	 *            the {@link ClassLoader}
	 * @param queryEnvironment
	 *            The AQL environment to use when parsing resolved modules.
	 * @param project
	 *            the context {@link IProject}
	 */
	public EclipseQualifiedNameResolver(ClassLoader classLoader, IReadOnlyQueryEnvironment queryEnvironment,
			IProject project) {
		super(createProjectClassLoader(classLoader, project), queryEnvironment);
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
	protected static ClassLoader createProjectClassLoader(ClassLoader classLoader, IProject project) {
		ClassLoader res;

		try {
			final URL[] urls = new URL[] {project.getLocation().toFile().toURI().toURL() };
			res = new URLClassLoader(urls, classLoader);
		} catch (MalformedURLException e) {
			Activator.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
					CAN_T_LOAD_FROM_WORKSPACE, e));
			res = classLoader;
		}

		return res;
	}

}
