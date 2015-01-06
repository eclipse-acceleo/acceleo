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
package org.eclipse.acceleo.query.type;

/**
 * The type representation used to validate queries.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class Any extends Type {
	/**
	 * Create a new {@link Any} instance.
	 */
	public Any() {
		super(TypeId.ANY);
	}

	@Override
	public Type merge(Type type) {
		return this;
	}
}
