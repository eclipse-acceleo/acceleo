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

public class JavaObjectType extends PrimitiveType {

	private final Class<?> instanceClass;

	public JavaObjectType(Class<?> instanceClass) {
		super(TypeId.JAVAOBJECT);
		this.instanceClass = instanceClass;
	}

	@Override
	public Type merge(Type type) {
		if (type instanceof JavaObjectType && type.getId() == getId()) {
			return ((JavaObjectType)type).instanceClass == instanceClass ? this : new Any();
		} else {
			return new Any();
		}
	}

	public Class<?> getInstanceClass() {
		return instanceClass;
	}
}
