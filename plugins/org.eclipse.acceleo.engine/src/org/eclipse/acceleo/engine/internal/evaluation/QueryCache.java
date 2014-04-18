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
package org.eclipse.acceleo.engine.internal.evaluation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.emf.common.EMFPlugin;

/**
 * This will act as a cache for the query invocation so that invoking the same query with the same arguments
 * multiple times will always yield the same result, with the best possible performance.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class QueryCache {
	/*
	 * we need this one public as the traceability will need to bypass the cache in some cases. For example,
	 * we do not record traces for the evaluation of an "if" condition, yet we cache the result. We could then
	 * have "empty" traces each time this query is evaluated thereafter.
	 */
	/** This instance will be used as a place holder when a query hasn't been run yet. */
	public static final Object NO_CACHED_RESULT = new Object();

	/** This instance will be used as the cached result of a query when it is undefined. */
	private static final Object INVALID_QUERY_RESULT = new Object();

	/** This instance will be used as the cached result of a query when it is null. */
	private static final Object NULL_QUERY_RESULT = new Object();

	/** <code>invalid</code> instance of the environment for which this cache is used. */
	private final Object invalid;

	/** The actual Map backing this cache. */
	private final Map<Query, Map<Object, Object>> queryResults = new HashMap<Query, Map<Object, Object>>();

	/**
	 * We need to know the "invalid" instance.
	 * 
	 * @param invalid
	 *            <code>invalid</code> instance for this environment.
	 */
	public QueryCache(Object invalid) {
		this.invalid = invalid;
	}

	/**
	 * This will return <code>true</code> if the given result is not the same as {@link #NO_CACHED_RESULT}.
	 * 
	 * @param result
	 *            The result we are to check.
	 * @return <code>true</code> if <code>result != {@link #NO_CACHED_RESULT}</code>, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isCachedResult(Object result) {
		return result != NO_CACHED_RESULT;
	}

	/**
	 * This will return <code>true</code> if the given query result is {@link #INVALID_QUERY_RESULT}.
	 * 
	 * @param result
	 *            The result we are to check.
	 * @return <code>true</code> if <code>result == {@link #INVALID_QUERY_RESULT}</code>, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isInvalid(Object result) {
		return result == INVALID_QUERY_RESULT;
	}

	/**
	 * This will return <code>true</code> if the given query result is {@link #NULL_QUERY_RESULT}.
	 * 
	 * @param result
	 *            The result we are to check.
	 * @return <code>true</code> if <code>result == {@link #NULL_QUERY_RESULT}</code>, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isNull(Object result) {
		return result == NULL_QUERY_RESULT;
	}

	/**
	 * This will add the given result to the cache for this query.
	 * 
	 * @param query
	 *            Query for which we are to cache a result.
	 * @param params
	 *            Parameters of the invocation.
	 * @param result
	 *            Result we are to cache.
	 */
	public void cacheResult(Query query, List<Object> params, Object result) {
		if (EMFPlugin.IS_ECLIPSE_RUNNING && !AcceleoPreferences.isQueryCacheEnabled()) {
			return;
		}

		Object key = params;
		if (params.size() == 1) {
			key = params.get(0);
		}

		Map<Object, Object> cache = queryResults.get(query);
		if (cache == null) {
			cache = new HashMap<Object, Object>();
			queryResults.put(query, cache);
		}

		if (result == invalid) {
			cache.put(key, INVALID_QUERY_RESULT);
		} else if (result == null) {
			cache.put(key, NULL_QUERY_RESULT);
		} else {
			cache.put(key, result);
		}
	}

	/**
	 * Returns the cached result for the given invocation.
	 * 
	 * @param query
	 *            Query which cache is to be queried.
	 * @param params
	 *            Parameters of the invocation.
	 * @return The cached result if any; might be {@link #NULL_QUERY_RESULT} (<code>null</code>),
	 *         {@link #INVALID_QUERY_RESULT} (<code>invalid</code>) or {@link #NO_CACHED_RESULT} if this query
	 *         hasn't been run yet.
	 */
	public Object getResult(Query query, List<Object> params) {
		if ((EMFPlugin.IS_ECLIPSE_RUNNING && !AcceleoPreferences.isQueryCacheEnabled())
				|| !queryResults.containsKey(query)) {
			return NO_CACHED_RESULT;
		}

		Object key = params;
		if (params.size() == 1) {
			key = params.get(0);
		}

		Map<Object, Object> cache = queryResults.get(query);

		Object result = cache.get(key);
		if (result == null) {
			result = NO_CACHED_RESULT;
		}
		return result;
	}
}
