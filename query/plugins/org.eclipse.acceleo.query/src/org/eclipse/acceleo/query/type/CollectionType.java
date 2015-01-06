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
 * Represents collection types.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class CollectionType extends Type {
	/**
	 * Type of the elements.
	 */
	private final Type elementType;

	/**
	 * Creates a new {@link CollectionType} instance given it's element's type and a type id(set or sequence).
	 * 
	 * @param id
	 *            this type's type id.
	 * @param elementType
	 *            the type of elements.
	 */
	public CollectionType(TypeId id, Type elementType) {
		super(id);
		this.elementType = elementType;
	}

	public Type getElementType() {
		return elementType;
	}

	@Override
	public Type merge(Type type) {
		if (type instanceof CollectionType) {
			TypeId id;
			if (type.getId() == getId()) {
				id = getId();
			} else {
				id = TypeId.COLLECTION;
			}
			return new CollectionType(id, this.elementType.merge(((CollectionType)type).getElementType()));
		} else {
			return new Any();
		}
	}
}
