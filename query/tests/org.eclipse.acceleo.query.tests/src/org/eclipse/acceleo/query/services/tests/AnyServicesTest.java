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
package org.eclipse.acceleo.query.services.tests;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Caliber;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnyServicesTest extends AbstractServicesTest {

	public AnyServices any;

	@Override
	public void before() throws Exception {
		super.before();
		getQueryEnvironment().registerServicePackage(AnyServices.class);
		any = new AnyServices(getQueryEnvironment());
	}

	@Test
	public void equalsNullObject() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertFalse(any.equals(null, new Object()));
	}

	@Test
	public void equalsObjectNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertFalse(any.equals(new Object(), null));
	}

	@Test
	public void equalsNullNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertTrue(any.equals(null, null));
	}

	/**
	 * Tests {@link AnyServices#equals(Object, Object)} method.</br>
	 * <ul>
	 * <li>"str1" must not be equal to "str2".</li>
	 * <li>An object must be equal to itself .</li>
	 * </ul>
	 */
	@Test
	public void equalsObjectObject() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertFalse(any.equals(new Object(), new Object()));
		Object obj = new Object();
		assertTrue(any.equals(obj, obj));
	}

	@Test
	public void differsNullObject() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertTrue(any.differs(null, new Object()));
	}

	@Test
	public void differsObjectNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertTrue(any.differs(new Object(), null));
	}

	@Test
	public void differsNullNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertFalse(any.differs(null, null));
	}

	/**
	 * Tests {@link AnyServices#differs(Object, Object)} method.</br>
	 * <ul>
	 * <li>"str1" must be different than "str2".</li>
	 * <li>An object must not be different than itself .</li>
	 * </ul>
	 */
	@Test
	public void differs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		assertTrue(any.differs("str1", "str2"));
		Object obj = new Object();
		assertFalse(any.differs(obj, obj));
	}

	private class ObjectCustom {

		@Override
		public String toString() {
			return "test";
		}

	}

	private class ObjectCustomNullToString {

		@Override
		public String toString() {
			return null;
		}

	}

	@Test
	public void addStringObject() {
		assertEquals("test test", any.add("test ", new ObjectCustom()));
	}

	@Test
	public void addStringNullToString() {
		assertEquals("test ", any.add("test ", new ObjectCustomNullToString()));
	}

	@Test
	public void addStringNull() {
		assertEquals("test ", any.add("test ", (Object)null));
	}

	@Test
	public void addStringNothing() {
		assertEquals("test ", any.add("test ", EvaluationServices.NOTHING));
	}

	@Test
	public void oclAsTypeNullNull() {
		assertEquals(null, any.oclAsType(null, null));
	}

	@Test
	public void oclAsTypeObjectNull() {
		final Object self = new Object();
		assertEquals(self, any.oclAsType(self, null));
	}

	@Test
	public void oclAsTypeObjectClass() {
		final Object self = new Object();
		assertEquals(self, any.oclAsType(self, Object.class));
	}

	@Test
	public void oclAsTypeObjectEClass() {
		final Object self = new Object();
		assertEquals(self, any.oclAsType(self, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void addStringCollection() {
		final List<Object> list = new ArrayList<Object>();
		list.add("test");
		list.add(new ObjectCustom());
		list.add(new ObjectCustomNullToString());
		list.add(null);
		list.add(EvaluationServices.NOTHING);
		final List<Object> subList = new ArrayList<Object>();
		subList.add("test3");
		list.add(subList);

		assertEquals("test testtesttest3", any.add("test ", list));
	}

	@Test
	public void addStringStringObject() {
		assertEquals("test some string", any.add("test ", (Object)"some string"));
	}

	@Test
	public void addObjectString() {
		assertEquals("test test", any.add(new ObjectCustom(), " test"));
	}

	@Test
	public void addNullToStringString() {
		assertEquals(" test", any.add(new ObjectCustomNullToString(), " test"));
	}

	@Test
	public void addNullString() {
		assertEquals(" test", any.add((Object)null, " test"));
	}

	@Test
	public void addNothingString() {
		assertEquals(" test", any.add(EvaluationServices.NOTHING, " test"));
	}

	@Test
	public void addCollectionString() {
		final List<Object> list = new ArrayList<Object>();
		list.add("test");
		list.add(new ObjectCustom());
		list.add(new ObjectCustomNullToString());
		list.add(null);
		list.add(EvaluationServices.NOTHING);
		final List<Object> subList = new ArrayList<Object>();
		subList.add("test3");
		list.add(subList);

		assertEquals("testtesttest3 test", any.add(list, " test"));
	}

	@Test
	public void addStringObjectString() {
		assertEquals("some string test", any.add((Object)"some string", " test"));
	}

	@Test
	public void oclIsKindOfNullNull() {
		assertFalse(any.oclIsKindOf(null, null));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsKindOfNullEClassNotRegistered() {
		assertFalse(any.oclIsKindOf(null, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclIsKindOfNullEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(null, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsKindOfNullEEnumNotRegistered() {
		assertFalse(any.oclIsKindOf(null, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsKindOfNullEEnum() {
		getQueryEnvironment().registerEPackage(AnydslPackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(null, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsKindOfNullEDataTypeNotRegistered() {
		assertFalse(any.oclIsKindOf(null, EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void oclIsKindOfNullEDataType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(null, EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void oclIsKindOfObjectObject() {
		assertFalse(any.oclIsKindOf(new Object(), null));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsKindOfObjectEClassNotRegistered() {
		assertFalse(any.oclIsKindOf(new Object(), EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclIsKindOfObjectEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(new Object(), EcorePackage.eINSTANCE.getEClass()));
		assertTrue(any.oclIsKindOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE.getEClass()));
		assertTrue(any.oclIsKindOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
				.getEClassifier()));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsKindOfObjectEEnumNotRegistered() {
		assertFalse(any.oclIsKindOf(new Object(), AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsKindOfObjectEEnum() {
		getQueryEnvironment().registerEPackage(AnydslPackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(new Object(), AnydslPackage.eINSTANCE.getCaliber()));
		assertTrue(any.oclIsKindOf(Caliber.L, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsKindOfObjectEDataTypeNotRegistered() {
		assertFalse(any.oclIsKindOf(new Object(), EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void oclIsKindOfObjectEDataType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(new Object(), EcorePackage.eINSTANCE.getEString()));
		assertTrue(any.oclIsKindOf("a string", EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void oclIsTypeOfNullNull() {
		assertFalse(any.oclIsTypeOf(null, null));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsTypeOfNullEClassNotRegistered() {
		assertFalse(any.oclIsTypeOf(null, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclIsTypeOfNullEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(null, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsTypeOfNullEEnumNotRegistered() {
		assertFalse(any.oclIsTypeOf(null, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsTypeOfNullEEnum() {
		getQueryEnvironment().registerEPackage(AnydslPackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(null, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsTypeOfNullEDataTypeNotRegistered() {
		assertFalse(any.oclIsTypeOf(null, EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void oclIsTypeOfNullEDataType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(null, EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void oclIsTypeOfObjectObject() {
		assertFalse(any.oclIsTypeOf(new Object(), null));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsTypeOfObjectEClassNotRegistered() {
		assertFalse(any.oclIsTypeOf(new Object(), EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclIsTypeOfObjectEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(new Object(), EcorePackage.eINSTANCE.getEClass()));
		assertTrue(any.oclIsTypeOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE.getEClass()));
		assertFalse(any.oclIsTypeOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
				.getEClassifier()));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsTypeOfObjectEEnumNotRegistered() {
		assertFalse(any.oclIsTypeOf(new Object(), AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsTypeOfObjectEEnum() {
		getQueryEnvironment().registerEPackage(AnydslPackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(new Object(), AnydslPackage.eINSTANCE.getCaliber()));
		assertTrue(any.oclIsTypeOf(Caliber.L, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void oclIsTypeOfObjectEDataTypeNotRegistered() {
		assertFalse(any.oclIsTypeOf(new Object(), EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void oclIsTypeOfObjectEDataType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(new Object(), EcorePackage.eINSTANCE.getEString()));
		assertTrue(any.oclIsTypeOf("a string", EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void toStringObject() {
		assertEquals("test", any.toString(new ObjectCustom()));
	}

	@Test
	public void toStringNullToString() {
		assertEquals("", any.toString(new ObjectCustomNullToString()));
	}

	@Test
	public void toStringNull() {
		assertEquals("", any.toString(null));
	}

	@Test
	public void toStringNothing() {
		assertEquals("", any.toString(EvaluationServices.NOTHING));
	}

	@Test
	public void toStringCollection() {
		final List<Object> list = new ArrayList<Object>();
		list.add("test");
		list.add(new ObjectCustom());
		list.add(new ObjectCustomNullToString());
		list.add(null);
		list.add(EvaluationServices.NOTHING);
		final List<Object> subList = new ArrayList<Object>();
		subList.add("test3");
		list.add(subList);

		assertEquals("testtesttest3", any.toString(list));
	}

	@Test
	public void toStringString() {
		assertEquals("some string", any.toString("some string"));
	}

}
