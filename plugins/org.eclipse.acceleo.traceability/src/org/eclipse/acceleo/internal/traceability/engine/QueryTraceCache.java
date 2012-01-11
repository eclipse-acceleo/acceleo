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
package org.eclipse.acceleo.internal.traceability.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.model.mtl.Query;

/**
 * Query results are cached, thus we won't have evaluation traces for subsequent calls. This cache will be
 * used to maintain this information.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <C>
 *            see {@link #org.eclipse.ocl.AbstractEvaluationVisitor}.
 */
public class QueryTraceCache<C> {
	/** The actual backing map for this cache. */
	private final Map<Query, Map<List<Object>, ExpressionTrace<C>>> queryTraceCache = new HashMap<Query, Map<List<Object>, ExpressionTrace<C>>>();

	/**
	 * This will return the cached trace for this query, if any.
	 * 
	 * @param query
	 *            Query for which invocation we seek a cached trace.
	 * @param parameters
	 *            Parameters of the invocation we seek the trace of.
	 * @return The cached trace for this query, if any.
	 */
	public ExpressionTrace<C> getCachedTrace(Query query, List<Object> parameters) {
		if (!AcceleoPreferences.isQueryCacheEnabled() || !queryTraceCache.containsKey(query)) {
			return null;
		}

		Map<List<Object>, ExpressionTrace<C>> cache = queryTraceCache.get(query);
		return cache.get(parameters);
	}

	/**
	 * This will cache the given trace for the given invocation.
	 * 
	 * @param query
	 *            Query for which we need to cache a trace.
	 * @param parameters
	 *            Parameters of the invocation we need to cache the trace of.
	 * @param trace
	 *            Trace that is to be cached.
	 */
	public void cacheTrace(Query query, List<Object> parameters, ExpressionTrace<C> trace) {
		if (!AcceleoPreferences.isQueryCacheEnabled() || trace.getTraces().isEmpty()) {
			return;
		}
		Map<List<Object>, ExpressionTrace<C>> cache = queryTraceCache.get(query);
		if (cache == null) {
			cache = new HashMap<List<Object>, ExpressionTrace<C>>();
			queryTraceCache.put(query, cache);
		}

		cache.put(parameters, trace);
	}
}
