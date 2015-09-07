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

public enum TypeId {

	ANY("Any"), PRIMITIVE("Primitive"), TYPE("Type"), JAVAOBJECT("JavaObject"), INTEGER("Integer"), REAL(
			"Real"), BOOLEAN("Boolean"), STRING("String"), COLLECTION("Collection"), SEQUENCE("Sequence"), ORDEREDSET(
			"OrderedSet"), MODELOBJECT("ModelObject"), LAMBDA("Lambda"), NOTHING("Nothing"), ENUM("Enum"), ECLASS(
			"EClass"), COMPARABLE("Comparable");

	private String name;

	public String getName() {
		return name;
	}

	private TypeId(String name) {
		this.name = name;
	}

}
