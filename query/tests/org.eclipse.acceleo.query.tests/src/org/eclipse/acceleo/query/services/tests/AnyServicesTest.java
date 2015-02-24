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

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Caliber;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AnyServicesTest extends AbstractServicesTest {
	private final String[] stringValues = new String[] {"a ", "\u00e9\u00e8\u0020\u00f1 ", " ", "Foehn12 ",
			"Standard sentence.", };

	public AnyServices any;

	public Resource reverseModel;

	@Override
	public void before() throws Exception {
		super.before();
		getLookupEngine().addServices(AnyServices.class);
		any = new AnyServices();
		this.reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
	}

	/**
	 * Tests {@link AnyServices#equals(Object, Object)} method.</br>
	 * <ul>
	 * <li>"str1" must not be equal to "str2".</li>
	 * <li>An object must be equal to itself .</li>
	 * <li>"null" operand must not be supported by the equals service.</li>
	 * </ul>
	 */
	@Test
	public void testEquals() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertFalse(any.equals(new Object(), new Object()));
		Object obj = new Object();
		assertTrue(any.equals(obj, obj));

		try {
			any.equals(null, new Object());
			fail("The 'equals' operation must throw a NullPointerException.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link AnyServices#differs(Object, Object)} method.</br>
	 * <ul>
	 * <li>"str1" must be different than "str2".</li>
	 * <li>An object must not be different than itself .</li>
	 * <li>"null" operand must not be supported by the differs service.</li>
	 * </ul>
	 */
	@Test
	public void testDiffers() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertTrue(any.differs("str1", "str2"));
		Object obj = new Object();
		assertFalse(any.differs(obj, obj));

		try {
			any.differs(null, new Object());
			fail("The 'equals' operation must throw a NullPointerException.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link AnyServices#lessThan(Comparable, Comparable)} method.</br>
	 * <ul>
	 * <li>"str1" must be less than "str2".</li>
	 * <li>"str3" must not be less than "str2".</li>
	 * <li>"str2" must not be less than "str2".</li>
	 * <li>"null" operand must not be supported by the less than service.</li>
	 * </ul>
	 */
	@Test
	public void testLessThan() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		assertTrue(any.lessThan("str1", "str2"));
		assertFalse(any.lessThan("str3", "str2"));
		assertFalse(any.lessThan("str2", "str2"));

		try {
			any.lessThan(null, "str2");
			fail("The 'lessThan' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}

		try {
			any.lessThan("str2", null);
			fail("The 'lessThan' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link AnyServices#greaterThan(Comparable, Comparable)} method.</br>
	 * <ul>
	 * <li>"str1" must not be greater than "str2".</li>
	 * <li>"str3" must be greater than "str2".</li>
	 * <li>"str2" must not be greater than "str2".</li>
	 * <li>"null" operand must not be supported by the greater than service.</li>
	 * </ul>
	 */
	@Test
	public void testGreaterThan() {
		assertFalse(any.greaterThan("str1", "str2"));
		assertTrue(any.greaterThan("str3", "str2"));
		assertFalse(any.greaterThan("str2", "str2"));

		try {
			any.greaterThan(null, "str2");
			fail("The 'greaterThan' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}

		try {
			any.greaterThan("str2", null);
			fail("The 'greaterThan' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link AnyServices#lessThanEqual(Comparable, Comparable)} method.</br>
	 * <ul>
	 * <li>"str1" must be less than "str2".</li>
	 * <li>"str3" must not be less than or equal to "str2".</li>
	 * <li>"str2" must be equal to "str2".</li>
	 * <li>"null" operand must not be supported by the less than or equal service.</li>
	 * </ul>
	 */
	@Test
	public void testLessThanEqual() {
		assertTrue(any.lessThanEqual("str1", "str2"));
		assertFalse(any.lessThanEqual("str3", "str2"));
		assertTrue(any.lessThanEqual("str2", "str2"));

		try {
			any.lessThanEqual(null, "str2");
			fail("The 'lessThanEqual' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}

		try {
			any.lessThanEqual("str2", null);
			fail("The 'lessThanEqual' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link AnyServices#greaterThanEqual(Comparable, Comparable)} method.</br>
	 * <ul>
	 * <li>"str1" must not be greater than or equal to "str2".</li>
	 * <li>"str3" must be greater than "str2".</li>
	 * <li>"str2" must be equal to "str2".</li>
	 * <li>"null" operand must not be supported by the greater than or equal service.</li>
	 * </ul>
	 */
	@Test
	public void testGreaterThanEqual() {
		assertFalse(any.greaterThanEqual("str1", "str2"));
		assertTrue(any.greaterThanEqual("str3", "str2"));
		assertTrue(any.greaterThanEqual("str2", "str2"));

		try {
			any.greaterThanEqual(null, "str2");
			fail("The 'greaterThanEqual' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}

		try {
			any.greaterThanEqual("str1", null);
			fail("The 'greaterThanEqual' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	private class ObjectCustom {

		@Override
		public String toString() {
			return "test";
		}

	}

	private class ObjectCustom2 {

		@Override
		public String toString() {
			return null;
		}

	}

	@Test
	public void testAddAnyString() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals("testeur", any.add(new ObjectCustom(), "eur"));
		assertEquals("test", any.add(new ObjectCustom(), ""));
		List<ObjectCustom> list = new ArrayList<ObjectCustom>();
		list.add(new ObjectCustom());
		list.add(new ObjectCustom());
		assertEquals("testtesteur", any.add(list, "eur"));
		assertEquals("eur", any.add(new ArrayList<ObjectCustom>(), "eur"));

		Object nullObject = null;
		assertEquals("eur", any.add(nullObject, "eur"));
		assertEquals("test", any.add(new ObjectCustom(), null));
		assertEquals("", any.add(nullObject, null));

		assertEquals("null", any.add(new ObjectCustom2(), null));

	}

	@Test
	public void testAddStringAny() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals("plantest", any.add("plan", new ObjectCustom()));
		List<ObjectCustom> list = new ArrayList<ObjectCustom>();
		list.add(new ObjectCustom());
		list.add(new ObjectCustom());
		assertEquals("plantesttest", any.add("plan", list));
		assertEquals("plan", any.add("plan", new ArrayList<ObjectCustom>()));

		assertEquals("plan", any.add("plan", (Object)null));
		assertEquals("test", any.add(null, new ObjectCustom()));
		assertEquals("", any.add(null, (Object)null));

		assertEquals("null", any.add(null, new ObjectCustom2()));

	}

	//
	// /**
	// * Tests {@link AnyServices#eContainer(EObject,
	// org.eclipse.emf.ecore.EClassifier)} method.</br> This
	// test
	// * uses the "resources/ecore/reverse.ecore" model to test if the
	// eContainer is calculated correctly.
	// * <ul>
	// * <li>For the EClass "unused" it must return the EPackage
	// "full-sirius-code" as container.</li>
	// * <li>For the EAttribute "newEReference1" it must return the EClass
	// "unused" as container if the filter
	// * is the EClass.</li>
	// * <li>For the EAttribute "newEReference1" it must return the EPackage
	// "full-sirius-code" as container
	// if
	// * the filter is the EPackage.</li>
	// * </ul>
	// */
	// @Test
	// public void testEContainer() {
	// EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
	// fullSiriusCodePackage.eAllContents().next();
	// EObject unused = fullSiriusCodePackage.eAllContents().next();
	// assertEquals(fullSiriusCodePackage, any.eContainer(unused,
	// fullSiriusCodePackage.eClass()));
	//
	// unused.eAllContents().next();
	// EObject newEReference1 = unused.eAllContents().next();
	// assertEquals(unused, any.eContainer(newEReference1, unused.eClass()));
	// assertEquals(fullSiriusCodePackage, any.eContainer(newEReference1,
	// fullSiriusCodePackage.eClass()));
	// }

	/**
	 * Tests {@link AnyServices#oclIsKindOf(Object, org.eclipse.emf.ecore.EClassifier)} method.</br> This test
	 * uses the "resources/ecore/reverse.ecore" model. </br> It ensures that an object is of type "classifier"
	 * or a subtype of "classifier".
	 * <ul>
	 * Using EObjects
	 * <ul>
	 * <li>the "query" EObject is not of kind "Queries".</li>
	 * <li>the "query" EObject is of kind "Query".</li>
	 * <li>a null classifier is not supported and must throw a NPE.</li>
	 * </ul>
	 * Using Objects
	 * <ul>
	 * <li>an Integer is of kind "Double".</li>
	 * </ul>
	 * Test on supertypes
	 * <ul>
	 * <li>an Integer is of kind "Number".</li>
	 * <li>an Integer is of kind "Object".</li> XXX: do we consider like in OCL that Real are a super type of
	 * Integer?
	 * </ul>
	 * </ul>
	 */
	@Test
	public void testOCLIsKindOfClass() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();

		Integer integer = new Integer(10);

		assertFalse(any.oclIsKindOf(new Object(), Integer.class));
		assertFalse(any.oclIsKindOf(new Object(), query.eClass()));
		assertFalse(any.oclIsKindOf(new Object(), null));

		// Test on superTypes
		assertTrue(any.oclIsKindOf(integer, Number.class));
		assertTrue(any.oclIsKindOf(integer, Object.class));
	}

	@Test
	public void testOCLIsKindOfEClass() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();

		assertFalse(any.oclIsKindOf(query, queries.eClass()));
		assertTrue(any.oclIsKindOf(query, query.eClass()));
		assertFalse(any.oclIsKindOf(query, null));

	}

	/**
	 * Tests that oclIsKindOf works correctly with {@link EEnum}. There are two cases to consider :
	 * <ul>
	 * <li>The case where there's no code generated : {@link EEnumLiteral} instances are used as values</li>
	 * <li>The case where there's some code generated : {@link Enumerator} instances are used as values</li>
	 * </ul>
	 */
	@Test
	public void testOCLIsKindOfEnum() {
		assertTrue(any.oclIsKindOf(AnydslPackage.Literals.CALIBER.getEEnumLiteral("S"),
				AnydslPackage.Literals.CALIBER));
		assertTrue(any.oclIsKindOf(Caliber.S, AnydslPackage.Literals.CALIBER));
		assertFalse(any.oclIsKindOf(AnydslPackage.Literals.COLOR.getEEnumLiteral(0),
				AnydslPackage.Literals.CALIBER));
		assertFalse(any.oclIsKindOf(Color.BLACK, AnydslPackage.Literals.CALIBER));

	}

	/**
	 * This test is ignored but should be treated : do we want it to be true or false? This is a semantics
	 * decision that can be taken latter.
	 */
	@Ignore
	@Test
	public void testDoubleInteger() {
		assertTrue(any.oclIsKindOf(new Integer(10), Double.class));
	}

	/**
	 * Tests {@link AnyServices#oclIsTypeOf(Object, org.eclipse.emf.ecore.EClassifier)} method.</br> This test
	 * uses the "resources/ecore/reverse.ecore" model. </br> It ensures that an object is of type "classifier"
	 * but not a subtype of the "classifier".
	 * <ul>
	 * Using EObjects
	 * <ul>
	 * <li>the "query" EObject is not of type "Queries".</li>
	 * <li>the "query" EObject is of type "Query".</li>
	 * <li>the "query" EObject is of type "null".</li>
	 * </ul>
	 * Using Objects
	 * <ul>
	 * <li>an object is not of type EDatatype "Integer".</li>
	 * <li>an Integer is of type EDatatype "Integer".</li>
	 * <li>an object is not of type "Query".</li>
	 * <li>an object is not of type "null".</li>
	 * </ul>
	 * Test on supertypes
	 * <ul>
	 * <li>an Integer is not of type EDatatype "Number".</li>
	 * <li>an Integer is not of type EDatatype "Object".</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void testOCLIsTypeOf() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();

		// Using EObject
		assertFalse(any.oclIsTypeOf(query, queries.eClass()));
		assertTrue(any.oclIsTypeOf(query, query.eClass()));
		assertFalse(any.oclIsTypeOf(query, null));

		assertFalse(any.oclIsTypeOf(query, Integer.class));

		Integer integer = new Integer(10);
		// Using Object
		assertFalse(any.oclIsTypeOf(new Object(), Integer.class));
		assertTrue(any.oclIsTypeOf(integer, Integer.class));
		assertFalse(any.oclIsTypeOf(new Object(), query.eClass()));
		assertFalse(any.oclIsTypeOf(new Object(), null));

		// Test on superTypes
		assertFalse(any.oclIsTypeOf(integer, Number.class));
		assertFalse(any.oclIsTypeOf(integer, Object.class));

	}

	/**
	 * Tests that oclIsTypeOf works correctly with {@link EEnum}. There are two cases to consider :
	 * <ul>
	 * <li>The case where there's no code generated : {@link EEnumLiteral} instances are used as values</li>
	 * <li>The case where there's some code generated : {@link Enumerator} instances are used as values</li>
	 * </ul>
	 */
	@Test
	public void testOCLIsTypeOfEEnum() {
		// Test using an EENum
		assertTrue(any.oclIsTypeOf(AnydslPackage.Literals.CALIBER.getEEnumLiteral("S"),
				AnydslPackage.Literals.CALIBER));
		assertTrue(any.oclIsTypeOf(Caliber.S, AnydslPackage.Literals.CALIBER));
		assertFalse(any.oclIsTypeOf(AnydslPackage.Literals.COLOR.getEEnumLiteral(0),
				AnydslPackage.Literals.CALIBER));
		assertFalse(any.oclIsTypeOf(Color.BLACK, AnydslPackage.Literals.CALIBER));
	}

	@Test
	public void testOCLIsTypeOfEDataType() {
		assertTrue(any.oclIsTypeOf("a string", AnydslPackage.Literals.SINGLE_STRING));
		assertFalse(any.oclIsTypeOf(new Integer(1), AnydslPackage.Literals.SINGLE_STRING));

	}

	@Test
	public void testOCLIsKindOfEDataType() {
		assertTrue(any.oclIsKindOf("a string", AnydslPackage.Literals.SINGLE_STRING));
		assertFalse(any.oclIsKindOf(new Integer(1), AnydslPackage.Literals.SINGLE_STRING));
	}

	/**
	 * Tests {@link AnyServices#oclAsType(EObject, EClass)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. We must return the same object regardless the EClass.
	 */
	@Test
	public void testOCLAsType() {
		Object obj = new Object();
		assertEquals(obj, any.oclAsType(obj, String.class));
		assertEquals(obj, any.oclAsType(obj, null));

		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();
		assertEquals(query, any.oclAsType(query, queries.eClass()));
		assertEquals(query, any.oclAsType(query, null));
	}

	/**
	 * Tests {@link AnyServices#toString(Object)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the {@link AnyServices#toString(Object)}
	 * returns the expected strings.
	 * <ul>
	 * We expect to get:
	 * <li>for objects: the object.toString() value.</li>
	 * <li>for collections: the concatenation of all string values in collections.</li>
	 * </ul>
	 */
	@Test
	public void testToString() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();
		String queryAsString = any.toString(query);
		assertTrue(queryAsString.startsWith("org.eclipse.acceleo.query.tests.qmodel.impl.QueryImpl"));
		assertTrue(queryAsString
				.contains("(expression: [self/], classesToImport: null, pluginsInClassPath: null)"));

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();

		// EObjects
		String rootAsString = any.toString(root);
		assertTrue(rootAsString.startsWith("org.eclipse.emf.ecore.impl.EPackageImpl"));
		assertTrue(rootAsString.contains("(name: null) (nsURI: null, nsPrefix: null)"));

		String clazzAsString = any.toString(clazz);
		assertTrue(clazzAsString.startsWith("org.eclipse.emf.ecore.impl.EClassImpl"));
		assertTrue(clazzAsString
				.contains("(name: null) (instanceClassName: null) (abstract: false, interface: false)"));

		String attributeAsString = any.toString(attribute);
		assertTrue(attributeAsString.startsWith("org.eclipse.emf.ecore.impl.EAttributeImpl"));
		assertTrue(attributeAsString
				.contains("(name: null) (ordered: true, unique: true, lowerBound: 0, upperBound: 1) (changeable: true, volatile: false, transient: false, defaultValueLiteral: null, unsettable: false, derived: false) (iD: false)"));

		assertTrue(any.toString(new Object()).startsWith("java.lang.Object"));

		for (int i = 0; i < stringValues.length; i++) {
			assertEquals(stringValues[i], any.toString(stringValues[i]));
		}

		assertEquals("a \u00e9\u00e8\u0020\u00f1  Foehn12 Standard sentence.", any.toString(Arrays
				.asList(stringValues)));
		assertEquals("0", any.toString("0"));
	}

	@Test
	public void testToStringNothing() {
		assertEquals("", any.toString(EvaluationServices.NOTHING));
	}

}
