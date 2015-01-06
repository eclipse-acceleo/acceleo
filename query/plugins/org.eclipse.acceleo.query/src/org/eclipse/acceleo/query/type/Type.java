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

public abstract class Type {

	private TypeId id;

	public Type(TypeId id) {
		this.id = id;
	}

	public TypeId getId() {
		return id;
	}

	public abstract Type merge(Type type);
}
