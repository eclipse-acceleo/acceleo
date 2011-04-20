/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.classpath;

import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.engine.generation.AcceleoEngine;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
	 * The reference entry string.
	 */
	private static final String REFERENCCE_ENTRY = "reference:file:"; //$NON-NLS-1$

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
			entries = new IClasspathEntry[] {getAcceleoEngineLibraryEntry() };
		} else if (ACCELEO_CLASSPATH_CONTAINER_PATH_PARSER.equals(path)) {
			entries = new IClasspathEntry[] {getAcceleoParserLibraryEntry() };
		} else if (ACCELEO_CLASSPATH_CONTAINER_PATH_RUNTIME.equals(path)) {
			entries = new IClasspathEntry[] {getAcceleoParserLibraryEntry(), getAcceleoEngineLibraryEntry() };
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
	public static IClasspathEntry getAcceleoParserLibraryEntry() {
		Bundle bundle = AcceleoWorkspaceUtil.getBundle(AcceleoParser.class);
		String bundlePath = bundle.getLocation();
		if (bundlePath.startsWith(REFERENCCE_ENTRY)) {
			bundlePath = bundlePath.substring(REFERENCCE_ENTRY.length());
		}
		IPath path = new Path(bundlePath);

		System.out.println(path);

		IClasspathEntry libraryEntry = JavaCore.newLibraryEntry(path, null, null, new IAccessRule[0],
				new IClasspathAttribute[0], false);

		return libraryEntry;
	}

	/**
	 * Returns the Acceleo engine library entry.
	 * 
	 * @return The Acceleo engine library entry.
	 */
	public static IClasspathEntry getAcceleoEngineLibraryEntry() {
		Bundle bundle = AcceleoWorkspaceUtil.getBundle(AcceleoEngine.class);
		String bundlePath = bundle.getLocation();
		if (bundlePath.startsWith(REFERENCCE_ENTRY)) {
			bundlePath = bundlePath.substring(REFERENCCE_ENTRY.length());
		}
		IPath path = new Path(bundlePath);

		System.out.println(path);

		IClasspathEntry libraryEntry = JavaCore.newLibraryEntry(path, null, null, new IAccessRule[0],
				new IClasspathAttribute[0], false);

		return libraryEntry;
	}

}
