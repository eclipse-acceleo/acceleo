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

import com.google.common.base.Supplier;

/**
 * This can be used to create multimaps using Deques as their collection kind.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <E>
 *            Type of the elements contained by this supplier's Deques.
 * @since 3.2
 */
public class DequeSupplier<E> implements Supplier<Deque<E>> {
	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.common.base.Supplier#get()
	 */
	public Deque<E> get() {
		return new CircularArrayDeque<E>();
	}
}
