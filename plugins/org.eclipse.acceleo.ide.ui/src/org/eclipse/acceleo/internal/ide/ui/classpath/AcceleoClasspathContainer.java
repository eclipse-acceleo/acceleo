/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.classpath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.Bundle;

/**
 * The Acceleo Classpath Container.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoClasspathContainer implements IClasspathContainer {

	/**
	 * The classpath container ID.
	 */
	public static final String ACCELEO_CLASSPATH_CONTAINER = "org.eclipse.acceleo.ide.ui.ACCELEO_RUNTIME"; //$NON-NLS-1$

	/**
	 * The engine classpath container path.
	 */
	public static final IPath ACCELEO_CLASSPATH_CONTAINER_PATH_ENGINE = new Path(ACCELEO_CLASSPATH_CONTAINER)
			.append("ENGINE"); //$NON-NLS-1$

	/**
	 * The parser classpath container path.
	 */
	public static final IPath ACCELEO_CLASSPATH_CONTAINER_PATH_PARSER = new Path(ACCELEO_CLASSPATH_CONTAINER)
			.append("PARSER"); //$NON-NLS-1$

	/**
	 * The runtime classpath container path.
	 */
	public static final IPath ACCELEO_CLASSPATH_CONTAINER_PATH_RUNTIME = new Path(ACCELEO_CLASSPATH_CONTAINER)
			.append("RUNTIME"); //$NON-NLS-1$

	/**
	 * The path.
	 */
	private IPath path;

	/**
	 * The constructor.
	 * 
	 * @param path
	 *            The classpath
	 * @param project
	 *            The project
	 */
	public AcceleoClasspathContainer(IPath path, IJavaProject project) {
		this.path = path;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.core.IClasspathContainer#getClasspathEntries()
	 */
	public IClasspathEntry[] getClasspathEntries() {
		IClasspathEntry[] entries = new IClasspathEntry[0];
		if (ACCELEO_CLASSPATH_CONTAINER_PATH_ENGINE.equals(path)) {
			List<IClasspathEntry> entriesList = new ArrayList<IClasspathEntry>();
			entriesList.addAll(getAcceleoEngineLibraryEntry());
			entries = entriesList.toArray(new IClasspathEntry[entriesList.size()]);
		} else if (ACCELEO_CLASSPATH_CONTAINER_PATH_PARSER.equals(path)) {
			List<IClasspathEntry> entriesList = new ArrayList<IClasspathEntry>();
			entriesList.addAll(getAcceleoParserLibraryEntry());
			entries = entriesList.toArray(new IClasspathEntry[entriesList.size()]);
		} else if (ACCELEO_CLASSPATH_CONTAINER_PATH_RUNTIME.equals(path)) {
			Set<IClasspathEntry> entriesSet = new HashSet<IClasspathEntry>();
			entriesSet.addAll(getAcceleoEngineLibraryEntry());
			entriesSet.addAll(getAcceleoParserLibraryEntry());
			entries = entriesSet.toArray(new IClasspathEntry[entriesSet.size()]);
		}
		return entries;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.core.IClasspathContainer#getDescription()
	 */
	public String getDescription() {
		String description = AcceleoUIMessages.getString("AcceleoClasspathContainer.RuntimeDescription"); //$NON-NLS-1$
		if (ACCELEO_CLASSPATH_CONTAINER_PATH_ENGINE.equals(path)) {
			description = AcceleoUIMessages.getString("AcceleoClasspathContainer.EngineDescription"); //$NON-NLS-1$
		} else if (ACCELEO_CLASSPATH_CONTAINER_PATH_PARSER.equals(path)) {
			description = AcceleoUIMessages.getString("AcceleoClasspathContainer.ParserDescription"); //$NON-NLS-1$
		}
		return description;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.core.IClasspathContainer#getKind()
	 */
	public int getKind() {
		return IClasspathContainer.K_APPLICATION;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.core.IClasspathContainer#getPath()
	 */
	public IPath getPath() {
		return this.path;
	}

	/**
	 * Returns the Acceleo parser library entry.
	 * 
	 * @return The Acceleo parser library entry.
	 */
	public static List<IClasspathEntry> getAcceleoParserLibraryEntry() {
		List<IClasspathEntry> libraryEntries = new ArrayList<IClasspathEntry>();

		IClasspathEntry classpathEntry = computeEntryFromClass("org.eclipse.acceleo.parser"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.acceleo.common"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.acceleo.model"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.emf.ecore"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.emf.ecore.xmi"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.emf.common"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.ocl.ecore"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.ocl"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("lpg.runtime.java"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}

		classpathEntry = computeEntryFromClass("org.eclipse.core.runtime"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.osgi"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.equinox.common"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.core.jobs"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.equinox.registry"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.equinox.preferences"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.core.contenttype"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.equinox.app"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.apache.ant"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		return libraryEntries;
	}

	/**
	 * Returns the Acceleo engine library entry.
	 * 
	 * @return The Acceleo engine library entry.
	 */
	public static List<IClasspathEntry> getAcceleoEngineLibraryEntry() {
		List<IClasspathEntry> libraryEntries = new ArrayList<IClasspathEntry>();

		IClasspathEntry classpathEntry = computeEntryFromClass("org.eclipse.acceleo.engine"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.acceleo.profiler"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.acceleo.common"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.acceleo.model"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.emf.ecore"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.emf.ecore.xmi"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.emf.common"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.ocl.ecore"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("org.eclipse.ocl"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		classpathEntry = computeEntryFromClass("lpg.runtime.java"); //$NON-NLS-1$
		if (classpathEntry != null) {
			libraryEntries.add(classpathEntry);
		}
		return libraryEntries;
	}

	/**
	 * Computes the classpath entry from a given bundle name.
	 * 
	 * @param name
	 *            The name of the bundle
	 * @return The classpath entry from a given bundle name.
	 */
	private static IClasspathEntry computeEntryFromClass(String name) {
		final String root = "/"; //$NON-NLS-1$
		final String jarfile = "jar:file:"; //$NON-NLS-1$
		final String file = "file:"; //$NON-NLS-1$
		final String suffix = "!/"; //$NON-NLS-1$

		try {
			Bundle bundle = Platform.getBundle(name);
			if (bundle != null && bundle.getResource(root) != null) {
				String bundlePath = FileLocator.resolve(bundle.getResource(root)).toString();
				if (bundlePath.startsWith(jarfile)) {
					bundlePath = bundlePath.substring(jarfile.length());
				} else if (bundlePath.startsWith(file)) {
					bundlePath = bundlePath.substring(file.length());
				}
				if (bundlePath.endsWith(suffix)) {
					bundlePath = bundlePath.substring(0, bundlePath.length() - suffix.length());
				}

				return JavaCore.newLibraryEntry(new Path(bundlePath), null, null, new IAccessRule[0],
						new IClasspathAttribute[0], false);
			}
		} catch (IOException e) {
			AcceleoUIActivator.log(e, true);
		}
		return null;
	}

}
