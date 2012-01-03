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
package org.eclipse.acceleo.internal.ide.ui.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;

/**
 * Computes a default source lookup path for Acceleo applications. The source path provider associated with a
 * launch configuration is consulted to compute a source lookup path.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoSourcePathComputer implements ISourcePathComputerDelegate {

	/**
	 * Unique identifier for the Acceleo source path computer.
	 */
	public static final String ID = "org.eclipse.acceleo.ide.ui.launching.AcceleoSourcePathComputer"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate#computeSourceContainers(org.eclipse.debug.core.ILaunchConfiguration,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ISourceContainer[] computeSourceContainers(ILaunchConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		IRuntimeClasspathEntry[] entries = JavaRuntime.computeUnresolvedSourceLookupPath(configuration);
		IRuntimeClasspathEntry[] resolved = JavaRuntime.resolveSourceLookupPath(entries, configuration);
		return JavaRuntime.getSourceContainers(resolved);
	}

}
