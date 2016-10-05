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
 * This can be used to create multimaps using CompactHashSet as their collection kind.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @param <E>
 *            Type of the elements contained by this supplier's Sets.
 * @since 3.3
 */
public class CompactLinkedHashSupplier<E> implements Supplier<CompactHashSet<E>> {
	/**
	 * {@inheritDoc}
	 * 
	 * @see com.google.common.base.Supplier#get()
	 */
	public CompactHashSet<E> get() {
		return new CompactLinkedHashSet<E>();
	}
}
