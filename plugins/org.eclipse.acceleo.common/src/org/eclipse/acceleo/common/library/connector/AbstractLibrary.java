/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.library.connector;

import org.eclipse.emf.ecore.EPackage;

/**
 * An abstract library is a intermediate library that implements a default {@link #prepare} method with no
 * behavior.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public abstract class AbstractLibrary implements ILibrary {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.common.library.connector.ILibrary#prepare(org.eclipse.emf.ecore.EPackage[])
	 */
	public void prepare(EPackage... ePackages) {
		// No implementation
	}
}
