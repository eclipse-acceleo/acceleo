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
package org.eclipse.acceleo.query.type.test;

import org.eclipse.acceleo.query.type.Any;
import org.eclipse.acceleo.query.type.JavaObjectType;
import org.eclipse.acceleo.query.type.PrimitiveType;
import org.eclipse.acceleo.query.type.Type;
import org.eclipse.acceleo.query.type.TypeId;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrimitiveTypeTests {

	@Test
	public void anyIdTest() {
		assertEquals(TypeId.ANY, new Any().getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveSequenceTest() {
		new PrimitiveType(TypeId.SEQUENCE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveSetTest() {
		new PrimitiveType(TypeId.ORDEREDSET);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveLambdaTest() {
		new PrimitiveType(TypeId.LAMBDA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveAnyTest() {
		new PrimitiveType(TypeId.ANY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveAllTest() {
		new PrimitiveType(TypeId.NOTHING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveEnumTest() {
		new PrimitiveType(TypeId.ENUM);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveCollectionTest() {
		new PrimitiveType(TypeId.COLLECTION);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveEClassTest() {
		new PrimitiveType(TypeId.ECLASS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveModelObjectTest() {
		new PrimitiveType(TypeId.MODELOBJECT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveJavaObjectTest() {
		Type type = new PrimitiveType(TypeId.JAVAOBJECT);
		assertEquals(TypeId.JAVAOBJECT, type.getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void primitiveComparableTest() {
		new PrimitiveType(TypeId.COMPARABLE);
	}

	@Test
	public void integerTypeIdTest() {
		Type type = new PrimitiveType(TypeId.INTEGER);
		assertEquals(TypeId.INTEGER, type.getId());
	}

	@Test
	public void booleanTypeIdTest() {
		Type type = new PrimitiveType(TypeId.BOOLEAN);
		assertEquals(TypeId.BOOLEAN, type.getId());
	}

	@Test
	public void realTypeIdTest() {
		Type type = new PrimitiveType(TypeId.REAL);
		assertEquals(TypeId.REAL, type.getId());
	}

	@Test
	public void stringTypeIdTest() {
		Type type = new PrimitiveType(TypeId.STRING);
		assertEquals(TypeId.STRING, type.getId());
	}

	@Test
	public void javaObjectTypeIdTest() {
		JavaObjectType type = new JavaObjectType(Object.class);
		assertEquals(TypeId.JAVAOBJECT, type.getId());
		assertEquals(Object.class, type.getInstanceClass());
	}
}
