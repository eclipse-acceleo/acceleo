/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;

/**
 * Acceleo environment.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IAcceleoEnvironment {

	/**
	 * Tells if the given {@link Module} qualified name exists.
	 * 
	 * @param qualifiedName
	 *            the {@link Module} qualified name
	 * @return <code>true</code> if the given {@link Module} qualified name exists, <code>false</code>
	 *         otherwise
	 */
	boolean hasModule(String qualifiedName);

	/**
	 * Gets the {@link IQueryEnvironment}.
	 * 
	 * @return the {@link IQueryEnvironment}
	 */
	IQueryEnvironment getQueryEnvironment();

}
