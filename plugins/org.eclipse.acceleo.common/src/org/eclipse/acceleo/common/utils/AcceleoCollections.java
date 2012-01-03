/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.utils;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

import java.util.Collection;

/**
 * This can create multimaps based on Acceleo collections.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
 */
public final class AcceleoCollections {
	/**
	 * We don't need a default constructor on a factory class.
	 */
	private AcceleoCollections() {
		// Hides default constructor
	}

	/**
	 * Creates a new Multimap using {@link CircularArrayDeque}s as its backing collection.
	 * 
	 * @param <K>
	 *            Type of the new map's keys.
	 * @param <V>
	 *            Type of the new map's values.
	 * @return A new Multimap using {@link CircularArrayDeque}s as its backing collection.
	 */
	public static <K, V> ListMultimap<K, V> newCircularArrayDequeMultimap() {
		return Multimaps.<K, V> newListMultimap(Maps.<K, Collection<V>> newHashMap(), new DequeSupplier<V>());
	}

	/**
	 * Creates a new Multimap using {@link CompactHashSet}s as its backing collection.
	 * 
	 * @param <K>
	 *            Type of the new map's keys.
	 * @param <V>
	 *            Type of the new map's values.
	 * @return A new Multimap using {@link CompactHashSet}s as its backing collection.
	 * @since 3.3
	 */
	public static <K, V> SetMultimap<K, V> newCompactLinkedHashSetMultimap() {
		return Multimaps.<K, V> newSetMultimap(Maps.<K, Collection<V>> newHashMap(),
				new CompactLinkedHashSupplier<V>());
	}
}
