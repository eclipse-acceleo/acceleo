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
package org.eclipse.acceleo.query.collections;

//CHECKSTYLE:OFF
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Set;

//CHECKSTYLE:ON
/**
 * Implementation of {@link Set} interface that forwards operations to an underlying iterator thus allowing to
 * preserve the lazyness of it if it is so.
 * 
 * @param <E>
 *            the type of the set elements.
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class LazySet<E> extends LazyCollection<E> implements Set<E> {
	/**
	 * Creates a new {@link LazySet} instance given an iterator.
	 * 
	 * @param iterable
	 *            the underlying iterable.
	 */
	public LazySet(Iterable<E> iterable) {
		super(iterable);
	}

	/**
	 * Creates a new {@link LazySet} given an iterator and a realization step.
	 * 
	 * @param iterable
	 *            the underlying iterable.
	 * @param step
	 *            the realization step to use.
	 */
	public LazySet(Iterable<E> iterable, int step) {
		super(iterable, step);
	}

	@Override
	protected Collection<E> createRealizedCollection() {
		return Sets.newLinkedHashSet();
	}

}
