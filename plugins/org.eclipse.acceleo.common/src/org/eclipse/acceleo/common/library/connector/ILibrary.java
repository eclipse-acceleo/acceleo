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

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;

/**
 * A library of queries. Implementation should be tied to a custom implementation of IQuery.
 * <p>
 * {@link #setURI(URI)} must be called first, then {@link #prepare(EPackage...)} and finally you can call
 * {@link #getQueries()}.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public interface ILibrary {
	/**
	 * Optional method for preparing queries.
	 * 
	 * @param ePackages
	 *            The ePackage on which the queries of this library may apply.
	 */
	void prepare(EPackage... ePackages);

	/**
	 * Sets the uri of this library.
	 * 
	 * @param uri
	 *            The uri of this library.
	 */
	void setURI(URI uri);

	/**
	 * Returns the uri of this library.
	 * 
	 * @return The uri of this library
	 */
	URI getURI();

	/**
	 * Returns all queries defined in this library.
	 * 
	 * @return All queries defined in this library.
	 */
	Set<IQuery> getQueries();
}
