/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.jdt;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.ide.runtime.impl.namespace.EclipseQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.osgi.container.Module;

/**
 * Eclipse JDT {@link ClassLoaderQualifiedNameResolver}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EclipseJDTQualifiedNameResolver extends ClassLoaderQualifiedNameResolver {

	/**
	 * The {@link IJavaProject}.
	 */
	private final IJavaProject project;

	/**
	 * Constructor.
	 * 
	 * @param classLoader
	 *            the {@link ClassLoader}
	 * @param project
	 *            the {@link IProject}
	 * @param qualifierSeparator
	 *            the qualifier name separator
	 */
	public EclipseJDTQualifiedNameResolver(ClassLoader classLoader, IProject project,
			String qualifierSeparator) {
		super(createProjectClassLoader(classLoader, project), qualifierSeparator);
		this.project = JavaCore.create(project);
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
							EclipseQualifiedNameResolver.CAN_T_LOAD_FROM_WORKSPACE, e));
					res = EclipseQualifiedNameResolver.createProjectClassLoader(classLoader, project);
				} catch (MalformedURLException e) {
					Activator.getPlugin().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							EclipseQualifiedNameResolver.CAN_T_LOAD_FROM_WORKSPACE, e));
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

	@Override
	public URL getSourceURL(String qualifiedName) {
		URL res;

		if (project != null) {
			try {
				final URL foundURL = getSourceURL(project, qualifiedName);
				if (foundURL != null) {
					res = foundURL;
				} else {
					res = super.getSourceURL(qualifiedName);
				}
			} catch (JavaModelException e) {
				res = super.getSourceURL(qualifiedName);
			}
		} else {
			res = super.getSourceURL(qualifiedName);
		}

		return res;
	}

	/**
	 * Gets the source {@link URL} corresponding to the given {@link Module} qualified name for the given
	 * {@link IJavaProject}.
	 * 
	 * @param javaProject
	 *            the {@link IJavaProject}
	 * @param qualifiedName
	 *            the qualified name (e.g. <code>qualified::path::to::module</code>)
	 * @throws JavaModelException
	 *             if the class path of the given {@link IJavaProject} can't be resolved.
	 * @return the source {@link URL} corresponding to the given {@link Module} qualified name for the given
	 *         {@link IJavaProject} if any, <code>null</code> otherwise
	 */
	private URL getSourceURL(IJavaProject javaProject, String qualifiedName) throws JavaModelException {
		URL res = null;
		found: for (IClasspathEntry entry : javaProject.getResolvedClasspath(true)) {
			if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				try {
					final File workspaceRoot = ResourcesPlugin.getWorkspace().getRoot().getLocation()
							.toFile();
					final URI srcFolderURI = new URI(workspaceRoot.toURI().toString().substring(0,
							workspaceRoot.toURI().toString().length() - 1) + entry.getPath().toString());
					final List<String> relativePathes = getPossibleResourceNames(qualifiedName);
					for (String relativePath : relativePathes) {
						final URI uri = new URI(srcFolderURI.toString() + "/" + relativePath);
						final File file = new File(uri);
						if (file.isFile() && file.exists()) {
							try {
								res = file.toURI().toURL();
								break found;
							} catch (MalformedURLException e) {
								// nothing to do here
							}
						}
					}
				} catch (URISyntaxException e) {
					// nothing to do here
				}
			} else if (entry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
				final IProject childProject = ResourcesPlugin.getWorkspace().getRoot().getProject(entry
						.getPath().lastSegment());
				if (childProject != null) {
					final IJavaProject childJavaProject = JavaCore.create(childProject);
					res = getSourceURL(childJavaProject, qualifiedName);
				}
			}
		}

		return res;
	}

	/**
	 * Gets the {@link IProject}.
	 * 
	 * @return the {@link IProject}
	 */
	protected IJavaProject getProject() {
		return project;
	}

}
