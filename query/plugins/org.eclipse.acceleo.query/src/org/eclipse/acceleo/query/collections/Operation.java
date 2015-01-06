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

/**
 * Interface to allows operations to be executed while realizing a collection.
 * 
 * @param <E>
 *            type of the elements.
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public interface Operation<E> {
	/**
	 * Execute the operation on the specified element.
	 * 
	 * @param element
	 *            the element on which to execute the operation.
	 */
	void execute(E element);

}
