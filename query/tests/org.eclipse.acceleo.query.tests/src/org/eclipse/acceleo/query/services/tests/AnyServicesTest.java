/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.tests;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Caliber;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnyServicesTest extends AbstractServicesTest {

	/**
	 * Line separator constant.
	 */
	private static final String LINE_SEP = System.getProperty("line.separator");

	public AnyServices any;

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(), new AnyServices(
				getQueryEnvironment()));
		ServiceUtils.registerServices(getQueryEnvironment(), services);
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
		assertEquals("test ", any.add("test ", new Nothing("whatever the message")));
	}

	@Test(expected = ClassCastException.class)
	public void oclAsTypeNullNull() {
		any.oclAsType(null, null);
	}

	@Test(expected = ClassCastException.class)
	public void oclAsTypeNullClass() {
		any.oclAsType(null, null);
	}

	@Test
	public void oclAsTypeObjectNull() {
		assertEquals(null, any.oclAsType(null, Object.class));
	}

	@Test
	public void oclAsTypeObjectClass() {
		final Object self = new Object();
		assertEquals(self, any.oclAsType(self, Object.class));
	}

	@Test
	public void oclAsTypeClass() {
		final Object self = new Object();
		assertEquals(self, any.oclAsType(self, Object.class));
	}

	@Test(expected = ClassCastException.class)
	public void oclAsTypeObjectToEClassClass() {
		final Object self = new Object();
		any.oclAsType(self, EClass.class);
	}

	@Test
	public void oclAsTypeEClassToEClassClass() {
		final Object self = EcoreFactory.eINSTANCE.createEClass();
		assertEquals(self, any.oclAsType(self, EClass.class));
	}

	@Test(expected = ClassCastException.class)
	public void oclAsTypeObjectToEClassEClassifier() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final Object self = new Object();
		any.oclAsType(self, EcorePackage.eINSTANCE.getEClass());
	}

	@Test
	public void oclAsTypeEclassToEClassEClassifier() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final Object self = EcoreFactory.eINSTANCE.createEClass();
		assertEquals(self, any.oclAsType(self, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclAsTypeEclassToEClassEClassifierUnregistered() {
		final Object self = EcoreFactory.eINSTANCE.createEClass();
		assertEquals(self, any.oclAsType(self, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test(expected = ClassCastException.class)
	public void oclAsTypeObjectToEClassifierUnregistered() {
		final Object self = new Object();
		any.oclAsType(self, EcorePackage.eINSTANCE.getEClass());
	}

	@Test(expected = ClassCastException.class)
	public void oclAsTypeObjectToEInt() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final Object self = new Object();
		any.oclAsType(self, EcorePackage.eINSTANCE.getEInt());
	}

	@Test(expected = ClassCastException.class)
	public void oclAsTypeObjectToEIntUnregistered() {
		final Object self = new Object();
		any.oclAsType(self, EcorePackage.eINSTANCE.getEInt());
	}

	@Test
	public void oclAsTypeIntToObject() {
		final Integer i = Integer.valueOf(1);
		assertEquals(Integer.valueOf(1), any.oclAsType(i, Object.class));
	}

	@Test
	public void oclAsTypeIntToEInt() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final Integer i = Integer.valueOf(1);
		assertEquals(Integer.valueOf(1), any.oclAsType(i, EcorePackage.eINSTANCE.getEInt()));
	}

	@Test
	public void oclAsTypeIntToEIntUnregistered() {
		final Integer i = Integer.valueOf(1);
		assertEquals(Integer.valueOf(1), any.oclAsType(i, EcorePackage.eINSTANCE.getEInt()));
	}

	@Test
	public void oclAsTypeCompatibleClassifiers() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		assertEquals(eClass, any.oclAsType(eClass, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclAsTypeCompatibleClassifiersUnregistered() {
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		assertEquals(eClass, any.oclAsType(eClass, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test(expected = ClassCastException.class)
	public void oclAsTypeIncompatibleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		any.oclAsType(eClass, EcorePackage.eINSTANCE.getEPackage());
	}

	@Test(expected = ClassCastException.class)
	public void oclAsTypeIncompatibleTypesUnregistered() {
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		any.oclAsType(eClass, EcorePackage.eINSTANCE.getEPackage());
	}

	@Test
	public void addStringCollection() {
		final List<Object> list = new ArrayList<Object>();
		list.add("test");
		list.add(new ObjectCustom());
		list.add(new ObjectCustomNullToString());
		list.add(null);
		list.add(new Nothing("whatever the message"));
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
		assertEquals(" test", any.add(new Nothing("whatever the message"), " test"));
	}

	@Test
	public void addCollectionString() {
		final List<Object> list = new ArrayList<Object>();
		list.add("test");
		list.add(new ObjectCustom());
		list.add(new ObjectCustomNullToString());
		list.add(null);
		list.add(new Nothing("whatever the message"));
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

	@Test
	public void oclIsKindOfNullEClassNotRegistered() {
		assertFalse(any.oclIsKindOf(null, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclIsKindOfNullEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(null, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclIsKindOfNullEEnumNotRegistered() {
		assertFalse(any.oclIsKindOf(null, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsKindOfNullEEnum() {
		getQueryEnvironment().registerEPackage(AnydslPackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(null, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
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

	@Test
	public void oclIsKindOfObjectEClassNotRegistered() {
		assertFalse(any.oclIsKindOf(new Object(), EcorePackage.eINSTANCE.getEClass()));
		assertTrue(any.oclIsKindOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE.getEClass()));
		assertTrue(any.oclIsKindOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
				.getEClassifier()));
	}

	@Test
	public void oclIsKindOfObjectEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(new Object(), EcorePackage.eINSTANCE.getEClass()));
		assertTrue(any.oclIsKindOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE.getEClass()));
		assertTrue(any.oclIsKindOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
				.getEClassifier()));
	}

	@Test
	public void oclIsKindOfObjectEEnumNotRegistered() {
		assertFalse(any.oclIsKindOf(new Object(), AnydslPackage.eINSTANCE.getCaliber()));
		assertTrue(any.oclIsKindOf(Caliber.L, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsKindOfObjectEEnum() {
		getQueryEnvironment().registerEPackage(AnydslPackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(new Object(), AnydslPackage.eINSTANCE.getCaliber()));
		assertTrue(any.oclIsKindOf(Caliber.L, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsKindOfObjectEDataTypeNotRegistered_Object() {
		assertFalse(any.oclIsKindOf(new Object(), EcorePackage.eINSTANCE.getEString()));
		assertTrue(any.oclIsKindOf("a string", EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void oclIsKindOfObjectEDataType() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(new Object(), EcorePackage.eINSTANCE.getEString()));
		assertTrue(any.oclIsKindOf("a string", EcorePackage.eINSTANCE.getEString()));
	}

	@Test
	public void oclIsKindOfObjectEDataType_Primitive() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsKindOf(new Object(), EcorePackage.eINSTANCE.getEBoolean()));
		assertTrue(any.oclIsKindOf(true, EcorePackage.eINSTANCE.getEBoolean()));
	}

	@Test
	public void oclIsTypeOfNullNull() {
		assertFalse(any.oclIsTypeOf(null, null));
	}

	@Test
	public void oclIsTypeOfNullEClassNotRegistered() {
		assertFalse(any.oclIsTypeOf(null, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclIsTypeOfNullEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(null, EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void oclIsTypeOfNullEEnumNotRegistered() {
		assertFalse(any.oclIsTypeOf(null, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsTypeOfNullEEnum() {
		getQueryEnvironment().registerEPackage(AnydslPackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(null, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
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

	@Test
	public void oclIsTypeOfObjectEClassNotRegistered() {
		assertFalse(any.oclIsTypeOf(new Object(), EcorePackage.eINSTANCE.getEClass()));
		assertTrue(any.oclIsTypeOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE.getEClass()));
		assertFalse(any.oclIsTypeOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
				.getEClassifier()));
	}

	@Test
	public void oclIsTypeOfObjectEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(new Object(), EcorePackage.eINSTANCE.getEClass()));
		assertTrue(any.oclIsTypeOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE.getEClass()));
		assertFalse(any.oclIsTypeOf(EcorePackage.eINSTANCE.getEClass(), EcorePackage.eINSTANCE
				.getEClassifier()));
	}

	@Test
	public void oclIsTypeOfObjectEEnumNotRegistered() {
		assertFalse(any.oclIsTypeOf(new Object(), AnydslPackage.eINSTANCE.getCaliber()));
		assertTrue(any.oclIsTypeOf(Caliber.L, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsTypeOfObjectEEnum() {
		getQueryEnvironment().registerEPackage(AnydslPackage.eINSTANCE);
		assertFalse(any.oclIsTypeOf(new Object(), AnydslPackage.eINSTANCE.getCaliber()));
		assertTrue(any.oclIsTypeOf(Caliber.L, AnydslPackage.eINSTANCE.getCaliber()));
	}

	@Test
	public void oclIsTypeOfObjectEDataTypeNotRegistered() {
		assertFalse(any.oclIsTypeOf(new Object(), EcorePackage.eINSTANCE.getEString()));
		assertTrue(any.oclIsTypeOf("a string", EcorePackage.eINSTANCE.getEString()));
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
	public void testToStringNothing() {
		assertEquals("", any.toString(new Nothing("whatever the message")));
	}

	@Test
	public void toStringCollection() {
		final List<Object> list = new ArrayList<Object>();
		list.add("test");
		list.add(new ObjectCustom());
		list.add(new ObjectCustomNullToString());
		list.add(null);
		list.add(new Nothing("whatever the message"));
		final List<Object> subList = new ArrayList<Object>();
		subList.add("test3");
		list.add(subList);

		assertEquals("testtesttest3", any.toString(list));
	}

	@Test
	public void toStringString() {
		assertEquals("some string", any.toString("some string"));
	}

	@Test
	public void trace() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final String result = any.trace(42);
		assertTrue(result.contains("Metamodels:" + LINE_SEP + "\thttp://www.eclipse.org/emf/2002/Ecore"
				+ LINE_SEP));
		assertTrue(result.contains("Services:" + LINE_SEP));
		assertTrue(result.contains("\t\t"
				+ "public java.lang.Boolean org.eclipse.acceleo.query.services.AnyServices.differs(java.lang.Object,java.lang.Object)"
				+ LINE_SEP));
		assertTrue(result.contains("receiver: 42" + LINE_SEP));
	}

}
