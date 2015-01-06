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

public class PrimitiveType extends Type {

	public PrimitiveType(TypeId id) {
		super(id);
		switch (id) {
			case BOOLEAN:
			case INTEGER:
			case STRING:
			case REAL:
			case TYPE:
				return;
			case JAVAOBJECT:
				if (this instanceof JavaObjectType) {
					return;
				} else {
					throw new IllegalArgumentException("The specified type id ain't no primitive type id!");
				}
			default:
				throw new IllegalArgumentException("The specified type id ain't no primitive type id!");
		}
	}

	@Override
	public Type merge(Type type) {
		switch (type.getId()) {
			case BOOLEAN:
			case INTEGER:
			case REAL:
			case STRING:
			case JAVAOBJECT:
			case COMPARABLE:
			case TYPE:
			case PRIMITIVE:
				if (type.getId() == this.getId()) {
					return this;
				} else {
					return new PrimitiveType(TypeId.PRIMITIVE);
				}
			default:
				return new Any();
		}
	}
}
