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
package org.eclipse.acceleo.query.runtime;

import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.impl.QueryBuilderEngine;

/**
 * Static utility methods pertaining to the parsing of Acceleo queries.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
public final class QueryParsing {

	private QueryParsing() {

	}

	/**
	 * Create a new {@link IQueryBuilderEngine} suitable for parsing {@link String} instances into
	 * {@link AstResult}.
	 * 
	 * @param environment
	 *            the query environment to use.
	 * @return a new {@link IQueryBuilderEngine} suitable for parsing {@link String} instances into
	 *         {@link AstResult}.
	 */
	public static IQueryBuilderEngine newBuilder(IQueryEnvironment environment) {
		return new QueryBuilderEngine(environment);
	}

}
