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

import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.services.NumberServices;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumberServicesTest extends AbstractServicesTest {

	public NumberServicesTest() {
	}

	private NumberServices numServices;

	NumberServices services = new NumberServices();

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		getLookupEngine().addServices(NumberServices.class);
		numServices = new NumberServices();
	}

	@Test
	public void testUnaryMinInteger() {
		Integer int1 = new Integer(1);
		Integer int2 = new Integer(5);

		assertEquals("-1", numServices.unaryMin(int1).toString());
		assertEquals("-5", numServices.unaryMin(int2).toString());
	}

	@Test
	public void testUnaryMinDouble() {
		Double double1 = new Double(1);
		Double double2 = new Double(5);

		assertEquals("-1.0", numServices.unaryMin(double1).toString());
		assertEquals("-5.0", numServices.unaryMin(double2).toString());
	}

	@Test
	public void testIntegerAdd() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int0 = new Integer(0);
		Integer int1 = new Integer(1);

		assertEquals("2", numServices.add(int1, int1).toString());
		assertEquals("1", numServices.add(int1, int0).toString());
		assertEquals("1", numServices.add(int0, int1).toString());
	}

	@Test
	public void testIntegerSub() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int0 = new Integer(0);
		Integer int1 = new Integer(1);
		Integer int3 = new Integer(3);

		assertEquals("-1", numServices.sub(int0, int1).toString());
		assertEquals("2", numServices.sub(int3, int1).toString());
	}

	@Test
	public void testIntegerDiv() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int1 = new Integer(1);
		Integer int2 = new Integer(2);
		Integer int4 = new Integer(4);

		assertEquals("2", numServices.div(int4, int2).toString());
		assertEquals("0", numServices.div(int1, int2).toString());
	}

	@Test
	public void testIntegerMult() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int0 = new Integer(0);
		Integer int1 = new Integer(1);
		Integer int2 = new Integer(2);
		Integer int4 = new Integer(4);

		assertEquals("8", numServices.mult(int4, int2).toString());
		assertEquals("2", numServices.mult(int1, int2).toString());
		assertEquals("0", numServices.mult(int0, int2).toString());
		assertEquals("0", numServices.mult(int2, int0).toString());
	}

	@Test
	public void testDoubleAdd() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double0 = new Double(0);
		Double double1 = new Double(1);

		assertEquals("2.0", numServices.add(double1, double1).toString());
		assertEquals("1.0", numServices.add(double1, double0).toString());
		assertEquals("1.0", numServices.add(double0, double1).toString());
	}

	@Test
	public void testDoubleSub() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double0 = new Double(0);
		Double double1 = new Double(1);
		Double double3 = new Double(3);

		assertEquals("-1.0", numServices.sub(double0, double1).toString());
		assertEquals("2.0", numServices.sub(double3, double1).toString());
	}

	@Test
	public void testDoubleDiv() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double1 = new Double(1);
		Double double2 = new Double(2);
		Double double4 = new Double(4);

		assertEquals("2.0", numServices.div(double4, double2).toString());
		assertEquals("0.5", numServices.div(double1, double2).toString());
	}

	@Test
	public void testDoubleMult() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double0 = new Double(0);
		Double double1 = new Double(1);
		Double double2 = new Double(2);
		Double double4 = new Double(4);

		assertEquals("8.0", numServices.mult(double4, double2).toString());
		assertEquals("2.0", numServices.mult(double1, double2).toString());
		assertEquals("0.0", numServices.mult(double0, double2).toString());
		assertEquals("0.0", numServices.mult(double2, double0).toString());
	}

	@Test
	public void testLowerInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int1 = new Integer(1);
		Integer int11 = new Integer(1);
		Integer int2 = new Integer(2);
		Integer int21 = new Integer(2);

		assertFalse(numServices.lessThan(int2, int1));
		assertFalse(numServices.lessThan(int1, int11));
		assertTrue(numServices.lessThan(int1, int2));
		assertFalse(numServices.lessThan(int2, int21));
	}

	@Test
	public void testLowerEqualInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int1 = new Integer(1);
		Integer int11 = new Integer(1);
		Integer int2 = new Integer(2);
		Integer int21 = new Integer(2);

		assertFalse(numServices.lessThanEqual(int2, int1));
		assertTrue(numServices.lessThanEqual(int1, int11));
		assertTrue(numServices.lessThanEqual(int1, int2));
		assertTrue(numServices.lessThanEqual(int2, int21));
	}

	@Test
	public void testGreaterInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int1 = new Integer(1);
		Integer int11 = new Integer(1);
		Integer int2 = new Integer(2);
		Integer int21 = new Integer(2);

		assertTrue(numServices.greaterThan(int2, int1));
		assertFalse(numServices.greaterThan(int1, int11));
		assertFalse(numServices.greaterThan(int1, int2));
		assertFalse(numServices.greaterThan(int2, int21));
	}

	@Test
	public void testGreaterEqualInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int1 = new Integer(1);
		Integer int11 = new Integer(1);
		Integer int2 = new Integer(2);
		Integer int21 = new Integer(2);

		assertTrue(numServices.greaterThanEqual(int2, int1));
		assertTrue(numServices.greaterThanEqual(int1, int11));
		assertFalse(numServices.greaterThanEqual(int1, int2));
		assertTrue(numServices.greaterThanEqual(int2, int21));
	}

	@Test
	public void testEqualsInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int1 = new Integer(1);
		Integer int11 = new Integer(1);
		Integer int2 = new Integer(2);
		Integer int21 = new Integer(2);

		assertFalse(numServices.equals(int2, int1));
		assertTrue(numServices.equals(int1, int11));
		assertFalse(numServices.equals(int1, int2));
		assertTrue(numServices.equals(int2, int21));
	}

	@Test
	public void testDiffersEqualInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int1 = new Integer(1);
		Integer int11 = new Integer(1);
		Integer int2 = new Integer(2);
		Integer int21 = new Integer(2);

		assertTrue(numServices.differs(int2, int1));
		assertFalse(numServices.differs(int1, int11));
		assertTrue(numServices.differs(int1, int2));
		assertFalse(numServices.differs(int2, int21));
	}

	@Test
	public void testLowerDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double1 = new Double(1);
		Double double11 = new Double(1);
		Double double2 = new Double(2);
		Double double21 = new Double(2);

		assertFalse(numServices.lessThan(double2, double1));
		assertFalse(numServices.lessThan(double1, double11));
		assertTrue(numServices.lessThan(double1, double2));
		assertFalse(numServices.lessThan(double2, double21));
	}

	@Test
	public void testLowerEqualDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double1 = new Double(1);
		Double double11 = new Double(1);
		Double double2 = new Double(2);
		Double double21 = new Double(2);

		assertFalse(numServices.lessThanEqual(double2, double1));
		assertTrue(numServices.lessThanEqual(double1, double11));
		assertTrue(numServices.lessThanEqual(double1, double2));
		assertTrue(numServices.lessThanEqual(double2, double21));
	}

	@Test
	public void testGreaterDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double1 = new Double(1);
		Double double11 = new Double(1);
		Double double2 = new Double(2);
		Double double21 = new Double(2);

		assertTrue(numServices.greaterThan(double2, double1));
		assertFalse(numServices.greaterThan(double1, double11));
		assertFalse(numServices.greaterThan(double1, double2));
		assertFalse(numServices.greaterThan(double2, double21));
	}

	@Test
	public void testGreaterEqualDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double1 = new Double(1);
		Double double11 = new Double(1);
		Double double2 = new Double(2);
		Double double21 = new Double(2);

		assertTrue(numServices.greaterThanEqual(double2, double1));
		assertTrue(numServices.greaterThanEqual(double1, double11));
		assertFalse(numServices.greaterThanEqual(double1, double2));
		assertTrue(numServices.greaterThanEqual(double2, double21));
	}

	@Test
	public void testEqualsDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double1 = new Double(1);
		Double double11 = new Double(1);
		Double double2 = new Double(2);
		Double double21 = new Double(2);

		assertFalse(numServices.equals(double2, double1));
		assertTrue(numServices.equals(double1, double11));
		assertFalse(numServices.equals(double1, double2));
		assertTrue(numServices.equals(double2, double21));
	}

	@Test
	public void testDiffersEqualDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double1 = new Double(1);
		Double double11 = new Double(1);
		Double double2 = new Double(2);
		Double double21 = new Double(2);

		assertTrue(numServices.differs(double2, double1));
		assertFalse(numServices.differs(double1, double11));
		assertTrue(numServices.differs(double1, double2));
		assertFalse(numServices.differs(double2, double21));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void absDoubleNull() {
		numServices.abs((Double)null);
	}

	@Test
	public void absDoublePositive() {
		Double result = numServices.abs(Double.valueOf(3.14));
		assertEquals(Double.valueOf(3.14), result);
	}

	@Test
	public void absDoubleNegative() {
		Double result = numServices.abs(Double.valueOf(-3.14));
		assertEquals(Double.valueOf(3.14), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void absIntegerNull() {
		numServices.abs((Integer)null);
	}

	@Test
	public void absIntegerPositive() {
		Integer result = numServices.abs(Integer.valueOf(3));
		assertEquals(Integer.valueOf(3), result);
	}

	@Test
	public void absIntegerNegative() {
		Integer result = numServices.abs(Integer.valueOf(-3));
		assertEquals(Integer.valueOf(3), result);
	}

}
