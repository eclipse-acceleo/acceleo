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
package org.eclipse.acceleo.internal.ide.ui.launching.strategy;

import org.eclipse.acceleo.ide.ui.launching.strategy.IAcceleoLaunchingStrategy;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;

/**
 * Java standalone launching strategy. It is used to launch an Acceleo application in a Java standalone way.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoJavaLaunchingStrategy implements IAcceleoLaunchingStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.launching.strategy.IAcceleoLaunchingStrategy#launch(org.eclipse.debug.core.ILaunchConfiguration,
	 *      java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {

	}

}
