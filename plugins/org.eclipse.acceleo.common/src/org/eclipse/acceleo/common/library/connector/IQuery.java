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

import org.eclipse.emf.ecore.EClassifier;

/**
 * This interface is used as a wrapper for other model transformation language queries like ATL, QVTO, etc.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public interface IQuery {
	/**
	 * Returns the name of this query.
	 * 
	 * @return The name of this query
	 */
	String getName();

	/**
	 * Returns the type of the receiver of this query.
	 * 
	 * @return The type of the receiver of this query.
	 */
	EClassifier getContextType();

	/**
	 * Executes this query on the object <code>self</code>. Typically, <code>self</code> will be an EObject or
	 * a value object (object that wraps a primitive value e.g. an {@link java.lang.Integer Integer} or an
	 * {@link java.lang.String String}).
	 * 
	 * @param self
	 *            The context object on which the query will be executed
	 * @param args
	 *            The optional arguments to pass to the query.
	 * @return The return value of the execution of this query (can be a {@link java.lang.Collection
	 *         Collection} or a single object depending on the query).
	 */
	Object execute(Object self, Object... args);
}
