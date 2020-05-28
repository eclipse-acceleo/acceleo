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
package org.eclipse.acceleo.aql.ide.jdt;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.aql.ide.EclipseQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;

public class EclipseJDTQualifiedNameResolver extends EclipseQualifiedNameResolver {

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
	public EclipseJDTQualifiedNameResolver(ClassLoader classLoader, IReadOnlyQueryEnvironment queryEnvironment,
			IProject project) {
		super(createProjectClassLoader(classLoader, project), queryEnvironment, project);
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

		if (project.exists() && project.isOpen()) {
			final IJavaProject javaProject = JavaCore.create(project);
			if (javaProject != null) {
				try {
					final String[] classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(javaProject);
					final List<URL> urlList = new ArrayList<URL>();
					for (String entry : classPathEntries) {
						final IPath path = new Path(entry);
						final URL url = path.toFile().toURI().toURL();
						urlList.add(url);
					}
					final URL[] urls = (URL[])urlList.toArray(new URL[urlList.size()]);
					res = new URLClassLoader(urls, classLoader);
				} catch (CoreException e) {
					Activator.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							CAN_T_LOAD_FROM_WORKSPACE, e));
					res = EclipseQualifiedNameResolver.createProjectClassLoader(classLoader, project);
				} catch (MalformedURLException e) {
					Activator.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							CAN_T_LOAD_FROM_WORKSPACE, e));
					res = EclipseQualifiedNameResolver.createProjectClassLoader(classLoader, project);
				}
			} else {
				res = EclipseQualifiedNameResolver.createProjectClassLoader(classLoader, project);
			}
		} else {
			res = classLoader;
		}

		return res;
	}

}
