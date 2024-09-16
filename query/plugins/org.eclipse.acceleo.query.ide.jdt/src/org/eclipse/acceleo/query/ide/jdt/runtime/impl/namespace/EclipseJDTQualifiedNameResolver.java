/*******************************************************************************
 * Copyright (c) 2020, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.jdt.runtime.impl.namespace;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.ide.jdt.Activator;
import org.eclipse.acceleo.query.ide.runtime.impl.namespace.EclipseQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.osgi.container.Module;
import org.osgi.framework.Bundle;

/**
 * Eclipse JDT {@link ClassLoaderQualifiedNameResolver}. NOTE: when {@link #forWorkspace} is
 * <code>false</code>, metamodels OSGi bundles need to be deployed in the running Eclipse and not present in
 * the current workspace.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EclipseJDTQualifiedNameResolver extends ClassLoaderQualifiedNameResolver {

	/**
	 * A {@link ClassLoader} that delegate to the given array of dependency {@link Bundle} before
	 * {@link #findClass(String) finding a Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class BundleDelegatingClassLoader extends URLClassLoader {

		/**
		 * The array of dependency {@link Bundle}
		 */
		private final Bundle[] dependencyBundles;

		/**
		 * Constructor.
		 * 
		 * @param urls
		 *            the array of {@link URL}
		 * @param parent
		 *            the parent {@link ClassLoader}
		 * @param dependencyBundles
		 *            the array of dependency {@link Bundle}
		 */
		private BundleDelegatingClassLoader(URL[] urls, ClassLoader parent, Bundle[] dependencyBundles) {
			super(urls, parent);
			this.dependencyBundles = dependencyBundles;
		}

		@Override
		protected Class<?> findClass(String name) throws ClassNotFoundException {
			for (Bundle bundle : dependencyBundles) {
				try {
					final Class<?> cls = bundle.loadClass(name);
					if (cls != null) {
						return cls;
					}
				} catch (ClassNotFoundException e) {
					// nothing to do here
				}
			}
			return super.findClass(name);
		}
	}

	/**
	 * The {@link IJavaProject}.
	 */
	private final IJavaProject project;

	/**
	 * For workspace use, local project resolution only.
	 */
	private final boolean forWorkspace;

	/**
	 * Dependency project entries {@link URI}.
	 */
	private final URI[] dependencyProjectEntries;

	/**
	 * Constructor.String
	 * 
	 * @param classLoader
	 *            the {@link ClassLoader}
	 * @param project
	 *            the {@link IProject}
	 * @param qualifierSeparator
	 *            the qualifier name separator
	 * @param forWorkspace
	 *            <code>true</code> for workspace use, local project resolution only
	 * @param dependencyProjectEntries
	 *            dependency project entries {@link List}
	 */
	public EclipseJDTQualifiedNameResolver(ClassLoader classLoader, IProject project,
			String qualifierSeparator, boolean forWorkspace, List<String> dependencyProjectEntries) {
		super(createProjectClassLoader(classLoader, project, forWorkspace, dependencyProjectEntries),
				qualifierSeparator);
		this.project = JavaCore.create(project);
		this.forWorkspace = forWorkspace;

		final List<URI> uriList = new ArrayList<>();
		for (String entry : dependencyProjectEntries) {
			final IPath path = new Path(entry);
			final URI uri = path.toFile().toURI();
			uriList.add(uri);
		}
		this.dependencyProjectEntries = uriList.toArray(new URI[uriList.size()]);
	}

	/**
	 * Creates the class loader for the given {@link IProject}.
	 * 
	 * @param classLoader
	 *            the parent {@link ClassLoader}
	 * @param project
	 *            the {@link IProject}
	 * @param forWorspace
	 *            <code>true</code> for workspace use, local project resolution only
	 * @param dependencyProjectEntries
	 *            dependency project entries
	 * @return the class loader for the given {@link IProject}
	 */
	protected static ClassLoader createProjectClassLoader(ClassLoader classLoader, IProject project,
			boolean forWorspace, List<String> dependencyProjectEntries) {
		ClassLoader res;

		if (project.exists() && project.isOpen()) {
			final IJavaProject javaProject = JavaCore.create(project);
			if (javaProject != null && javaProject.exists()) {
				try {
					final List<Bundle> dependencyBundlesList = new ArrayList<>();
					final String[] classPathEntries = getClassPathes(javaProject, forWorspace,
							dependencyProjectEntries, dependencyBundlesList);
					final List<URL> urlList = new ArrayList<URL>();
					for (String entry : classPathEntries) {
						final IPath path = new Path(entry);
						final URL url = path.toFile().toURI().toURL();
						urlList.add(url);
					}
					final URL[] urls = urlList.toArray(new URL[urlList.size()]);
					if (forWorspace) {
						res = new URLClassLoader(urls, classLoader);
					} else {
						final Bundle[] dependencyBundles = dependencyBundlesList.toArray(
								new Bundle[dependencyBundlesList.size()]);
						res = new BundleDelegatingClassLoader(urls, classLoader, dependencyBundles);
					}
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

	// copied from JavaRuntime.computeDefaultRuntimeClassPath()
	private static String[] getClassPathes(IJavaProject javaProject, boolean forWorkspace,
			List<String> dependencyProjectEntries, List<Bundle> dependencyBundlesList) throws CoreException {
		IRuntimeClasspathEntry[] unresolved = JavaRuntime.computeUnresolvedRuntimeClasspath(javaProject);

		// 1. remove bootpath entries
		// 2. resolve & translate to local file system paths
		List<String> resolved = new ArrayList<>(unresolved.length);
		for (int i = 0; i < unresolved.length; i++) {
			IRuntimeClasspathEntry entry = unresolved[i];
			if (entry.getClasspathProperty() == IRuntimeClasspathEntry.USER_CLASSES) {
				IRuntimeClasspathEntry[] entries = JavaRuntime.resolveRuntimeClasspathEntry(entry,
						javaProject);
				for (int j = 0; j < entries.length; j++) {
					String location = entries[j].getLocation();
					if (location != null) {
						if (forWorkspace) {
							if (isDependencyProjectEntry(javaProject, entries[j])) {
								dependencyProjectEntries.add(location);
							}
							resolved.add(location);
						} else {
							final Bundle bundle = getBundle(location);
							if (bundle != null) {
								dependencyBundlesList.add(bundle);
							} else {
								resolved.add(location);
							}
						}
					}
				}
			}
		}

		return resolved.toArray(new String[resolved.size()]);

	}

	/**
	 * Gets the {@link Bundle} for the given location.
	 * 
	 * @param location
	 *            the {@link URL}
	 * @return the {@link Bundle} for the given location if any, <code>null</code> otherwise
	 */
	private static Bundle getBundle(String location) {
		final Bundle res;

		final String symbolicName = getBundleSymbolicName(location);
		if (symbolicName != null) {
			res = Platform.getBundle(symbolicName);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the {@link Bundle} {@link Bundle#getSymbolicName() symbolic name} for the given location.
	 * 
	 * @param location
	 *            the {@link URL}
	 * @return the {@link Bundle} {@link Bundle#getSymbolicName() symbolic name} for the given location
	 */
	private static String getBundleSymbolicName(String location) {
		final String res;

		final String[] segments = location.replace("\\", "/").split("/");
		final String lastSegment = segments[segments.length - 1];
		res = lastSegment.split("_")[0];

		return res;
	}

	/**
	 * Tells if the given {@link IRuntimeClasspathEntry} if from a project we depend on.
	 * 
	 * @param javaProject
	 *            the current {@link IJavaProject}
	 * @param entry
	 *            the {@link IRuntimeClasspathEntry}
	 * @return <code>true</code> if the given {@link IRuntimeClasspathEntry} if from a project we depend on,
	 *         <code>false</code> otherwise
	 */
	private static boolean isDependencyProjectEntry(IJavaProject javaProject, IRuntimeClasspathEntry entry) {
		final boolean res;

		if (entry.getType() == IRuntimeClasspathEntry.PROJECT && javaProject.getProject() != entry
				.getResource()) {
			res = true;
		} else {
			final IResource entryResource = entry.getResource();
			if (entryResource != null && entryResource.exists() && entryResource.getProject() != null
					&& !entryResource.getProject().equals(javaProject.getProject())) {
				res = true;
			} else {
				res = false;
			}
		}

		return res;
	}

	@Override
	protected Object load(String qualifiedName) {
		final Object res;

		if (!forWorkspace || !isInDependencyProject(super.getURI(qualifiedName))) {
			res = super.load(qualifiedName);
		} else {
			res = null;
			// we perform a dummy registration to prevent further loading
			dummyRegistration(qualifiedName);
		}

		return res;
	}

	private boolean isInDependencyProject(URI uri) {
		boolean res = false;

		if (uri != null) {
			for (URI entryURI : dependencyProjectEntries) {
				if (!entryURI.relativize(uri).equals(uri)) {
					res = true;
					break;
				}
			}
		}

		return res;
	}

	@Override
	public URI getSourceURI(String qualifiedName) {
		URI res;

		if (project != null) {
			try {
				final URI foundURI = getSourceURI(project, qualifiedName);
				if (foundURI != null) {
					res = foundURI;
				} else {
					res = super.getSourceURI(qualifiedName);
				}
			} catch (JavaModelException e) {
				res = super.getSourceURI(qualifiedName);
			}
		} else {
			res = super.getSourceURI(qualifiedName);
		}

		return res;
	}

	/**
	 * Gets the source {@link URI} corresponding to the given {@link Module} qualified name for the given
	 * {@link IJavaProject}.
	 * 
	 * @param javaProject
	 *            the {@link IJavaProject}
	 * @param qualifiedName
	 *            the qualified name (e.g. <code>qualified::path::to::module</code>)
	 * @throws JavaModelException
	 *             if the class path of the given {@link IJavaProject} can't be resolved.
	 * @return the source {@link URI} corresponding to the given {@link Module} qualified name for the given
	 *         {@link IJavaProject} if any, <code>null</code> otherwise
	 */
	private URI getSourceURI(IJavaProject javaProject, String qualifiedName) throws JavaModelException {
		URI res = null;

		found: for (IClasspathEntry entry : javaProject.getResolvedClasspath(true)) {
			if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				final IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
				final String srcFolder = entry.getPath().toString();
				final List<String> relativePathes = getPossibleResourceNames(qualifiedName);
				for (String relativePath : relativePathes) {
					final URI uri = workspaceRoot.getFile(new Path(srcFolder + "/" + relativePath))
							.getLocationURI();
					final File file = new File(uri);
					if (file.isFile() && file.exists()) {
						res = URI.create(org.eclipse.emf.common.util.URI.createFileURI((file
								.getAbsolutePath())).toString());
						break found;
					}
				}
			} else if (entry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
				final IProject childProject = ResourcesPlugin.getWorkspace().getRoot().getProject(entry
						.getPath().lastSegment());
				if (childProject != null) {
					final IJavaProject childJavaProject = JavaCore.create(childProject);
					res = getSourceURI(childJavaProject, qualifiedName);
				}
			}
		}

		return res;
	}

	@Override
	public URI getBinaryURI(URI sourceURI) {
		URI res;

		if (project != null) {
			try {
				final URI foundURI = getBinaryURI(project, sourceURI);
				if (foundURI != null) {
					res = foundURI;
				} else {
					res = super.getBinaryURI(sourceURI);
				}
			} catch (JavaModelException e) {
				res = super.getBinaryURI(sourceURI);
			}
		} else {
			res = super.getBinaryURI(sourceURI);
		}

		return res;
	}

	private URI getBinaryURI(IJavaProject javaProject, URI sourceURI) throws JavaModelException {
		final URI res;

		final IWorkspaceRoot workspaceRoot = javaProject.getProject().getWorkspace().getRoot();
		final IFile sourceFile = workspaceRoot.getFileForLocation(new Path(sourceURI.getPath()));
		final IClasspathEntry entry = getContainingEntry(javaProject, sourceFile);
		if (entry != null) {
			if (entry.getContentKind() == IPackageFragmentRoot.K_BINARY) {
				res = sourceURI;
			} else if (entry.getContentKind() == IPackageFragmentRoot.K_SOURCE) {
				// TODO check forWorkspace and the project to the class path entry
				final IPath relativePath = sourceFile.getFullPath().makeRelativeTo(entry.getPath());
				final IPath binaryPath = javaProject.getOutputLocation().append(relativePath);
				final IFile binaryFile = workspaceRoot.getFile(binaryPath);
				if (binaryFile.exists()) {
					res = binaryFile.getLocationURI();
				} else {
					res = null;
				}
			} else {
				throw new IllegalStateException("unknown classpath entry content kind.");
			}
		} else {
			res = null;
		}

		return res;
	}

	private IClasspathEntry getContainingEntry(IJavaProject javaProject, final IFile sourceFile)
			throws JavaModelException {
		final IClasspathEntry res;

		final IClasspathEntry foundEntry = javaProject.findContainingClasspathEntry(sourceFile);
		if (foundEntry != null) {
			res = foundEntry;
		} else {
			IClasspathEntry fallbackFound = null;
			for (IClasspathEntry entry : javaProject.getResolvedClasspath(true)) {
				if (entry.getPath().isPrefixOf(sourceFile.getFullPath())) {
					fallbackFound = entry;
					break;
				}
			}
			res = fallbackFound;
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
