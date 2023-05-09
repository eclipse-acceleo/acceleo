/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime;

import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;

/**
 * Static utility methods pertaining to the parsing of Acceleo queries.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public final class QueryParsing {
	/** Hides the default constructor. */
	private QueryParsing() {
		// Shouldn't be instantiated.
	}

	/**
	 * Create a new {@link IQueryBuilderEngine} suitable for parsing {@link String} instances into the given
	 * {@link IQueryEnvironment}.
	 * 
	 * @param environment
	 *            the query environment to use.
	 * @return a new {@link IQueryBuilderEngine} suitable for parsing {@link String} instances into the given
	 *         {@link IQueryEnvironment}.
	 * @deprecated see {@link #newBuilder()}
	 */
	public static IQueryBuilderEngine newBuilder(IQueryEnvironment environment) {
		return newBuilder();
	}

	/**
	 * Create a new {@link IQueryBuilderEngine} suitable for parsing {@link String} instances.
	 * 
	 * @return a new {@link IQueryBuilderEngine} suitable for parsing {@link String} instances.
	 */
	public static IQueryBuilderEngine newBuilder() {
		return new QueryBuilderEngine();
	}

}
