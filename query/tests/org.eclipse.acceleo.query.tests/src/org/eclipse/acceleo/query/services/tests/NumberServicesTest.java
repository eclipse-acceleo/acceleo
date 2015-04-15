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

import org.eclipse.acceleo.query.services.NumberServices;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberServicesTest extends AbstractServicesTest {

	private NumberServices numServices;

	NumberServices services = new NumberServices();

	@Override
	public void before() throws Exception {
		super.before();
		getQueryEnvironment().registerServicePackage(NumberServices.class);
		numServices = new NumberServices();
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testUnaryMinIntegerNull() {
		numServices.unaryMin((Integer)null);
	}

	@Test
	public void testUnaryMinInteger() {
		Integer int1 = new Integer(1);
		Integer int2 = new Integer(5);

		assertEquals("-1", numServices.unaryMin(int1).toString());
		assertEquals("-5", numServices.unaryMin(int2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testUnaryMinDoubleNull() {
		numServices.unaryMin((Double)null);
	}

	@Test
	public void testUnaryMinDouble() {
		Double double1 = new Double(1);
		Double double2 = new Double(5);

		assertEquals("-1.0", numServices.unaryMin(double1).toString());
		assertEquals("-5.0", numServices.unaryMin(double2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddIntegerNullNull() {
		numServices.add((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddIntegerIntegerNull() {
		Integer int0 = new Integer(0);

		numServices.add(int0, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddIntegerNullInteger() {
		Integer int0 = new Integer(0);

		numServices.add((Integer)null, int0);
	}

	@Test
	public void testAddInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int0 = new Integer(0);
		Integer int1 = new Integer(1);

		assertEquals("2", numServices.add(int1, int1).toString());
		assertEquals("1", numServices.add(int1, int0).toString());
		assertEquals("1", numServices.add(int0, int1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubIntegerNullNull() {
		numServices.sub((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubIntegerIntegerNull() {
		Integer int0 = new Integer(0);

		numServices.sub(int0, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubIntegerNullInteger() {
		Integer int0 = new Integer(0);

		numServices.sub((Integer)null, int0);
	}

	@Test
	public void testSubInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int0 = new Integer(0);
		Integer int1 = new Integer(1);
		Integer int3 = new Integer(3);

		assertEquals("-1", numServices.sub(int0, int1).toString());
		assertEquals("2", numServices.sub(int3, int1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivIntegerNullNull() {
		numServices.div((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivIntegerIntegerNull() {
		Integer int0 = new Integer(0);

		numServices.div(int0, (Integer)null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testDivIntegerNullInteger() {
		Integer int0 = new Integer(0);

		numServices.div((Integer)null, int0);
	}

	@Test
	public void testDivInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer int1 = new Integer(1);
		Integer int2 = new Integer(2);
		Integer int4 = new Integer(4);

		assertEquals("2", numServices.divOp(int4, int2).toString());
		assertEquals("0", numServices.divOp(int1, int2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultIntegerNullNull() {
		numServices.mult((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultIntegerIntegerNull() {
		Integer int0 = new Integer(0);

		numServices.mult(int0, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultIntegerNullInteger() {
		Integer int0 = new Integer(0);

		numServices.mult((Integer)null, int0);
	}

	@Test
	public void testMultInteger() throws IllegalAccessException, IllegalArgumentException,
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

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddDoubleNullNull() {
		numServices.add((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddDoubleDoubleNull() {
		Double int0 = new Double(0);

		numServices.add(int0, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddDoubleNullDouble() {
		Double int0 = new Double(0);

		numServices.add((Double)null, int0);
	}

	@Test
	public void testAddDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double0 = new Double(0);
		Double double1 = new Double(1);

		assertEquals("2.0", numServices.add(double1, double1).toString());
		assertEquals("1.0", numServices.add(double1, double0).toString());
		assertEquals("1.0", numServices.add(double0, double1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubDoubleNullNull() {
		numServices.sub((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubDoubleDoubleNull() {
		Double int0 = new Double(0);

		numServices.sub(int0, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubDoubleNullDouble() {
		Double int0 = new Double(0);

		numServices.sub((Double)null, int0);
	}

	@Test
	public void testSubDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double0 = new Double(0);
		Double double1 = new Double(1);
		Double double3 = new Double(3);

		assertEquals("-1.0", numServices.sub(double0, double1).toString());
		assertEquals("2.0", numServices.sub(double3, double1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivDoubleNullNull() {
		numServices.div((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivDoubleDoubleNull() {
		Double int0 = new Double(0);

		numServices.div(int0, (Double)null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testDivDoubleNullDouble() {
		Double int0 = new Double(0);

		numServices.div((Double)null, int0);
	}

	@Test
	public void testDivDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double1 = new Double(1);
		Double double2 = new Double(2);
		Double double4 = new Double(4);

		assertEquals("2.0", numServices.divOp(double4, double2).toString());
		assertEquals("0.5", numServices.divOp(double1, double2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultDoubleNullNull() {
		numServices.mult((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultDoubleDoubleNull() {
		Double int0 = new Double(0);

		numServices.mult(int0, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultDoubleNullDouble() {
		Double int0 = new Double(0);

		numServices.mult((Double)null, int0);
	}

	@Test
	public void testMultDouble() throws IllegalAccessException, IllegalArgumentException,
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

	@Test(expected = java.lang.NullPointerException.class)
	public void floorDoubleNull() {
		numServices.floor((Double)null);
	}

	@Test
	public void floorDouble() {
		Integer result = numServices.floor(Double.valueOf(3.14));
		assertEquals(Integer.valueOf(3), result);
	}

	@Test
	public void floorIntegerNull() {
		Integer result = numServices.floor((Integer)null);
		assertEquals(null, result);
	}

	@Test
	public void floorInteger() {
		Integer result = numServices.floor(Integer.valueOf(3));
		assertEquals(Integer.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxDoubleNullNull() {
		numServices.max((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxDoubleDoubleNull() {
		numServices.max(Double.valueOf(3.14), (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxDoubleNullDouble() {
		numServices.max((Double)null, Double.valueOf(3.14));
	}

	@Test
	public void maxDouble() {
		Double result = numServices.max(Double.valueOf(6.28), Double.valueOf(3.14));
		assertEquals(Double.valueOf(6.28), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxIntegerNullNull() {
		numServices.max((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxIntegerIntegerNull() {
		numServices.max(Integer.valueOf(3), (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxIntegerNullInteger() {
		numServices.max((Integer)null, Integer.valueOf(3));
	}

	@Test
	public void maxInteger() {
		Integer result = numServices.max(Integer.valueOf(6), Integer.valueOf(3));
		assertEquals(Integer.valueOf(6), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minDoubleNullNull() {
		numServices.min((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minDoubleDoubleNull() {
		numServices.min(Double.valueOf(3.14), (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minDoubleNullDouble() {
		numServices.min((Double)null, Double.valueOf(3.14));
	}

	@Test
	public void minDouble() {
		Double result = numServices.min(Double.valueOf(6.28), Double.valueOf(3.14));
		assertEquals(Double.valueOf(3.14), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minIntegerNullNull() {
		numServices.min((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minIntegerIntegerNull() {
		numServices.min(Integer.valueOf(3), (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minIntegerNullInteger() {
		numServices.min((Integer)null, Integer.valueOf(3));
	}

	@Test
	public void minInteger() {
		Integer result = numServices.min(Integer.valueOf(6), Integer.valueOf(3));
		assertEquals(Integer.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void roundDoubleNull() {
		numServices.round((Double)null);
	}

	@Test
	public void roundDouble() {
		Integer result = numServices.round(Double.valueOf(3.64));
		assertEquals(Integer.valueOf(4), result);
	}

	@Test
	public void roundIntegerNull() {
		Integer result = numServices.round((Integer)null);
		assertEquals(null, result);
	}

	@Test
	public void roundInteger() {
		Integer result = numServices.round(Integer.valueOf(3));
		assertEquals(Integer.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divDoubleNullNull() {
		numServices.div((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divDoubleDoubleNull() {
		numServices.div(Double.valueOf(3.14), (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divDoubleNullDouble() {
		numServices.div((Double)null, Double.valueOf(3.14));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void divDoubleByZero() {
		Integer result = numServices.div(Double.valueOf(3.14), Double.valueOf(0));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void divDouble() {
		Integer result = numServices.div(Double.valueOf(3.14 * 7 + 1), Double.valueOf(3.14));
		assertEquals(Integer.valueOf(7), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divIntegerNullNull() {
		numServices.div((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divIntegerIntegerNull() {
		numServices.div(Integer.valueOf(3), (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divIntegerNullInteger() {
		numServices.div((Integer)null, Integer.valueOf(3));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void divIntegerByZero() {
		Integer result = numServices.div(Integer.valueOf(3), Integer.valueOf(0));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void divInteger() {
		Integer result = numServices.div(Integer.valueOf(3 * 7 + 1), Integer.valueOf(3));
		assertEquals(Integer.valueOf(7), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modDoubleNullNull() {
		numServices.mod((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modDoubleDoubleNull() {
		numServices.mod(Double.valueOf(3.14), (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modDoubleNullDouble() {
		numServices.mod((Double)null, Double.valueOf(3.14));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void modDoubleByZero() {
		Integer result = numServices.mod(Double.valueOf(3.14), Double.valueOf(0));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void modDouble() {
		Integer result = numServices.mod(Double.valueOf(3.14 * 7 + 1), Double.valueOf(3.14));
		assertEquals(Integer.valueOf(1), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modIntegerNullNull() {
		numServices.mod((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modIntegerIntegerNull() {
		numServices.mod(Integer.valueOf(3), (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modIntegerNullInteger() {
		numServices.mod((Integer)null, Integer.valueOf(3));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void modIntegerByZero() {
		Integer result = numServices.mod(Integer.valueOf(3), Integer.valueOf(0));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void modInteger() {
		Integer result = numServices.mod(Integer.valueOf(3 * 7 + 1), Integer.valueOf(3));
		assertEquals(Integer.valueOf(1), result);
	}

}
