/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.workspace;

/**
 * A class providing the specific classloading support to use for the current environment.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 * @since 3.6
 */
public interface ClassLoadingCompanionProvider {
	/**
	 * return the classloading support to use.
	 * 
	 * @return the classloading support to use.
	 */
	ClassLoadingCompanion getCompanion();

}
