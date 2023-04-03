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
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.NumberServices;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumberServicesTest extends AbstractServicesTest {

	private NumberServices numServices;

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> servicesToRegister = ServiceUtils.getServices(getQueryEnvironment(),
				NumberServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), servicesToRegister);
		numServices = new NumberServices();
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testUnaryMinIntegerNull() {
		numServices.unaryMin((Integer)null);
	}

	@Test
	public void testUnaryMinInteger() {
		Integer integer1 = Integer.valueOf(1);
		Integer integer2 = Integer.valueOf(5);

		assertEquals("-1", numServices.unaryMin(integer1).toString());
		assertEquals("-5", numServices.unaryMin(integer2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testUnaryMinDoubleNull() {
		numServices.unaryMin((Double)null);
	}

	@Test
	public void testUnaryMinDouble() {
		Double double1 = Double.valueOf(1);
		Double double2 = Double.valueOf(5);

		assertEquals("-1.0", numServices.unaryMin(double1).toString());
		assertEquals("-5.0", numServices.unaryMin(double2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddIntegerNullNull() {
		numServices.add((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddIntegerIntegerNull() {
		Integer integer0 = Integer.valueOf(0);

		numServices.add(integer0, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddIntegerNullInteger() {
		Integer integer0 = Integer.valueOf(0);

		numServices.add((Integer)null, integer0);
	}

	@Test
	public void testAddInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer integer0 = Integer.valueOf(0);
		Integer integer1 = Integer.valueOf(1);

		assertEquals("2", numServices.add(integer1, integer1).toString());
		assertEquals("1", numServices.add(integer1, integer0).toString());
		assertEquals("1", numServices.add(integer0, integer1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubIntegerNullNull() {
		numServices.sub((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubIntegerIntegerNull() {
		Integer integer0 = Integer.valueOf(0);

		numServices.sub(integer0, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubIntegerNullInteger() {
		Integer integer0 = Integer.valueOf(0);

		numServices.sub((Integer)null, integer0);
	}

	@Test
	public void testSubInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer integer0 = Integer.valueOf(0);
		Integer integer1 = Integer.valueOf(1);
		Integer integer3 = Integer.valueOf(3);

		assertEquals("-1", numServices.sub(integer0, integer1).toString());
		assertEquals("2", numServices.sub(integer3, integer1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivIntegerNullNull() {
		numServices.div((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivIntegerIntegerNull() {
		Integer integer0 = Integer.valueOf(0);

		numServices.div(integer0, (Integer)null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testDivIntegerNullInteger() {
		Integer integer0 = Integer.valueOf(0);

		numServices.div((Integer)null, integer0);
	}

	@Test
	public void testDivInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer integer1 = Integer.valueOf(1);
		Integer integer2 = Integer.valueOf(2);
		Integer integer4 = Integer.valueOf(4);

		assertEquals("2", numServices.divOp(integer4, integer2).toString());
		assertEquals("0", numServices.divOp(integer1, integer2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultIntegerNullNull() {
		numServices.mult((Integer)null, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultIntegerIntegerNull() {
		Integer integer0 = Integer.valueOf(0);

		numServices.mult(integer0, (Integer)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultIntegerNullInteger() {
		Integer integer0 = Integer.valueOf(0);

		numServices.mult((Integer)null, integer0);
	}

	@Test
	public void testMultInteger() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Integer integer0 = Integer.valueOf(0);
		Integer integer1 = Integer.valueOf(1);
		Integer integer2 = Integer.valueOf(2);
		Integer integer4 = Integer.valueOf(4);

		assertEquals("8", numServices.mult(integer4, integer2).toString());
		assertEquals("2", numServices.mult(integer1, integer2).toString());
		assertEquals("0", numServices.mult(integer0, integer2).toString());
		assertEquals("0", numServices.mult(integer2, integer0).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddDoubleNullNull() {
		numServices.add((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddDoubleDoubleNull() {
		Double integer0 = Double.valueOf(0);

		numServices.add(integer0, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddDoubleNullDouble() {
		Double integer0 = Double.valueOf(0);

		numServices.add((Double)null, integer0);
	}

	@Test
	public void testAddDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double0 = Double.valueOf(0);
		Double double1 = Double.valueOf(1);

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
		Double integer0 = Double.valueOf(0);

		numServices.sub(integer0, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubDoubleNullDouble() {
		Double integer0 = Double.valueOf(0);

		numServices.sub((Double)null, integer0);
	}

	@Test
	public void testSubDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double0 = Double.valueOf(0);
		Double double1 = Double.valueOf(1);
		Double double3 = Double.valueOf(3);

		assertEquals("-1.0", numServices.sub(double0, double1).toString());
		assertEquals("2.0", numServices.sub(double3, double1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivDoubleNullNull() {
		numServices.div((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivDoubleDoubleNull() {
		Double integer0 = Double.valueOf(0);

		numServices.div(integer0, (Double)null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testDivDoubleNullDouble() {
		Double integer0 = Double.valueOf(0);

		numServices.div((Double)null, integer0);
	}

	@Test
	public void testDivDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double1 = Double.valueOf(1);
		Double double2 = Double.valueOf(2);
		Double double4 = Double.valueOf(4);

		assertEquals("2.0", numServices.divOp(double4, double2).toString());
		assertEquals("0.5", numServices.divOp(double1, double2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultDoubleNullNull() {
		numServices.mult((Double)null, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultDoubleDoubleNull() {
		Double integer0 = Double.valueOf(0);

		numServices.mult(integer0, (Double)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultDoubleNullDouble() {
		Double integer0 = Double.valueOf(0);

		numServices.mult((Double)null, integer0);
	}

	@Test
	public void testMultDouble() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Double double0 = Double.valueOf(0);
		Double double1 = Double.valueOf(1);
		Double double2 = Double.valueOf(2);
		Double double4 = Double.valueOf(4);

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

	@Test(expected = java.lang.NullPointerException.class)
	public void testUnaryMinLongNull() {
		numServices.unaryMin((Long)null);
	}

	@Test
	public void testUnaryMinLong() {
		Long long1 = Long.valueOf(1);
		Long long2 = Long.valueOf(5);

		assertEquals("-1", numServices.unaryMin(long1).toString());
		assertEquals("-5", numServices.unaryMin(long2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testUnaryMinFloatNull() {
		numServices.unaryMin((Float)null);
	}

	@Test
	public void testUnaryMinFloat() {
		Float float1 = Float.valueOf(1);
		Float float2 = Float.valueOf(5);

		assertEquals("-1.0", numServices.unaryMin(float1).toString());
		assertEquals("-5.0", numServices.unaryMin(float2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddLongNullNull() {
		numServices.add((Long)null, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddLongLongNull() {
		Long long0 = Long.valueOf(0);

		numServices.add(long0, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddLongNullLong() {
		Long long0 = Long.valueOf(0);

		numServices.add((Long)null, long0);
	}

	@Test
	public void testAddLong() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Long long0 = Long.valueOf(0);
		Long long1 = Long.valueOf(1);

		assertEquals("2", numServices.add(long1, long1).toString());
		assertEquals("1", numServices.add(long1, long0).toString());
		assertEquals("1", numServices.add(long0, long1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubLongNullNull() {
		numServices.sub((Long)null, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubLongLongNull() {
		Long long0 = Long.valueOf(0);

		numServices.sub(long0, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubLongNullLong() {
		Long long0 = Long.valueOf(0);

		numServices.sub((Long)null, long0);
	}

	@Test
	public void testSubLong() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Long long0 = Long.valueOf(0);
		Long long1 = Long.valueOf(1);
		Long long3 = Long.valueOf(3);

		assertEquals("-1", numServices.sub(long0, long1).toString());
		assertEquals("2", numServices.sub(long3, long1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivLongNullNull() {
		numServices.div((Long)null, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivLongLongNull() {
		Long long0 = Long.valueOf(0);

		numServices.div(long0, (Long)null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testDivLongNullLong() {
		Long long0 = Long.valueOf(0);

		numServices.div((Long)null, long0);
	}

	@Test
	public void testDivLong() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Long long1 = Long.valueOf(1);
		Long long2 = Long.valueOf(2);
		Long long4 = Long.valueOf(4);

		assertEquals("2", numServices.divOp(long4, long2).toString());
		assertEquals("0", numServices.divOp(long1, long2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultLongNullNull() {
		numServices.mult((Long)null, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultLongLongNull() {
		Long long0 = Long.valueOf(0);

		numServices.mult(long0, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultLongNullLong() {
		Long long0 = Long.valueOf(0);

		numServices.mult((Long)null, long0);
	}

	@Test
	public void testMultLong() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Long long0 = Long.valueOf(0);
		Long long1 = Long.valueOf(1);
		Long long2 = Long.valueOf(2);
		Long long4 = Long.valueOf(4);

		assertEquals("8", numServices.mult(long4, long2).toString());
		assertEquals("2", numServices.mult(long1, long2).toString());
		assertEquals("0", numServices.mult(long0, long2).toString());
		assertEquals("0", numServices.mult(long2, long0).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddFloatNullNull() {
		numServices.add((Float)null, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddFloatFloatNull() {
		Float long0 = Float.valueOf(0);

		numServices.add(long0, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAddFloatNullFloat() {
		Float long0 = Float.valueOf(0);

		numServices.add((Float)null, long0);
	}

	@Test
	public void testAddFloat() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Float float0 = Float.valueOf(0);
		Float float1 = Float.valueOf(1);

		assertEquals("2.0", numServices.add(float1, float1).toString());
		assertEquals("1.0", numServices.add(float1, float0).toString());
		assertEquals("1.0", numServices.add(float0, float1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubFloatNullNull() {
		numServices.sub((Float)null, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubFloatFloatNull() {
		Float long0 = Float.valueOf(0);

		numServices.sub(long0, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSubFloatNullFloat() {
		Float long0 = Float.valueOf(0);

		numServices.sub((Float)null, long0);
	}

	@Test
	public void testSubFloat() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Float float0 = Float.valueOf(0);
		Float float1 = Float.valueOf(1);
		Float float3 = Float.valueOf(3);

		assertEquals("-1.0", numServices.sub(float0, float1).toString());
		assertEquals("2.0", numServices.sub(float3, float1).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivFloatNullNull() {
		numServices.div((Float)null, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testDivFloatFloatNull() {
		Float long0 = Float.valueOf(0);

		numServices.div(long0, (Float)null);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void testDivFloatNullFloat() {
		Float long0 = Float.valueOf(0);

		numServices.div((Float)null, long0);
	}

	@Test
	public void testDivFloat() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Float float1 = Float.valueOf(1);
		Float float2 = Float.valueOf(2);
		Float float4 = Float.valueOf(4);

		assertEquals("2.0", numServices.divOp(float4, float2).toString());
		assertEquals("0.5", numServices.divOp(float1, float2).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultFloatNullNull() {
		numServices.mult((Float)null, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultFloatFloatNull() {
		Float long0 = Float.valueOf(0);

		numServices.mult(long0, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testMultFloatNullFloat() {
		Float long0 = Float.valueOf(0);

		numServices.mult((Float)null, long0);
	}

	@Test
	public void testMultFloat() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Float float0 = Float.valueOf(0);
		Float float1 = Float.valueOf(1);
		Float float2 = Float.valueOf(2);
		Float float4 = Float.valueOf(4);

		assertEquals("8.0", numServices.mult(float4, float2).toString());
		assertEquals("2.0", numServices.mult(float1, float2).toString());
		assertEquals("0.0", numServices.mult(float0, float2).toString());
		assertEquals("0.0", numServices.mult(float2, float0).toString());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void absFloatNull() {
		numServices.abs((Float)null);
	}

	@Test
	public void absFloatPositive() {
		Float result = numServices.abs(Float.valueOf(3.14f));
		assertEquals(Float.valueOf(3.14f), result);
	}

	@Test
	public void absFloatNegative() {
		Float result = numServices.abs(Float.valueOf(-3.14f));
		assertEquals(Float.valueOf(3.14f), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void absLongNull() {
		numServices.abs((Long)null);
	}

	@Test
	public void absLongPositive() {
		Long result = numServices.abs(Long.valueOf(3));
		assertEquals(Long.valueOf(3), result);
	}

	@Test
	public void absLongNegative() {
		Long result = numServices.abs(Long.valueOf(-3));
		assertEquals(Long.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void floorFloatNull() {
		numServices.floor((Float)null);
	}

	@Test
	public void floorFloat() {
		Integer result = numServices.floor(Float.valueOf(3.14f));
		assertEquals(Integer.valueOf(3), result);
	}

	@Test
	public void floorLongNull() {
		Long result = numServices.floor((Long)null);
		assertEquals(null, result);
	}

	@Test
	public void floorLong() {
		Long result = numServices.floor(Long.valueOf(3));
		assertEquals(Long.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxFloatNullNull() {
		numServices.max((Float)null, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxFloatFloatNull() {
		numServices.max(Float.valueOf(3.14f), (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxFloatNullFloat() {
		numServices.max((Float)null, Float.valueOf(3.14f));
	}

	@Test
	public void maxFloat() {
		Float result = numServices.max(Float.valueOf(6.28f), Float.valueOf(3.14f));
		assertEquals(Float.valueOf(6.28f), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxLongNullNull() {
		numServices.max((Long)null, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxLongLongNull() {
		numServices.max(Long.valueOf(3), (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void maxLongNullLong() {
		numServices.max((Long)null, Long.valueOf(3));
	}

	@Test
	public void maxLong() {
		Long result = numServices.max(Long.valueOf(6), Long.valueOf(3));
		assertEquals(Long.valueOf(6), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minFloatNullNull() {
		numServices.min((Float)null, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minFloatFloatNull() {
		numServices.min(Float.valueOf(3.14f), (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minFloatNullFloat() {
		numServices.min((Float)null, Float.valueOf(3.14f));
	}

	@Test
	public void minFloat() {
		Float result = numServices.min(Float.valueOf(6.28f), Float.valueOf(3.14f));
		assertEquals(Float.valueOf(3.14f), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minLongNullNull() {
		numServices.min((Long)null, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minLongLongNull() {
		numServices.min(Long.valueOf(3), (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void minLongNullLong() {
		numServices.min((Long)null, Long.valueOf(3));
	}

	@Test
	public void minLong() {
		Long result = numServices.min(Long.valueOf(6), Long.valueOf(3));
		assertEquals(Long.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void roundFloatNull() {
		numServices.round((Float)null);
	}

	@Test
	public void roundFloat() {
		Integer result = numServices.round(Float.valueOf(3.64f));
		assertEquals(Integer.valueOf(4), result);
	}

	@Test
	public void roundLongNull() {
		Long result = numServices.round((Long)null);
		assertEquals(null, result);
	}

	@Test
	public void roundLong() {
		Long result = numServices.round(Long.valueOf(3));
		assertEquals(Long.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divFloatNullNull() {
		numServices.div((Float)null, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divFloatFloatNull() {
		numServices.div(Float.valueOf(3.14f), (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divFloatNullFloat() {
		numServices.div((Float)null, Float.valueOf(3.14f));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void divFloatByZero() {
		Integer result = numServices.div(Float.valueOf(3.14f), Float.valueOf(0));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void divFloat() {
		Integer result = numServices.div(Float.valueOf(3.14f * 7 + 1), Float.valueOf(3.14f));
		assertEquals(Integer.valueOf(7), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divLongNullNull() {
		numServices.div((Long)null, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divLongLongNull() {
		numServices.div(Long.valueOf(3), (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void divLongNullLong() {
		numServices.div((Long)null, Long.valueOf(3));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void divLongByZero() {
		Long result = numServices.div(Long.valueOf(3), Long.valueOf(0));
		assertEquals(Long.valueOf(0), result);
	}

	@Test
	public void divLong() {
		Long result = numServices.div(Long.valueOf(3 * 7 + 1), Long.valueOf(3));
		assertEquals(Long.valueOf(7), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modFloatNullNull() {
		numServices.mod((Float)null, (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modFloatFloatNull() {
		numServices.mod(Float.valueOf(3.14f), (Float)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modFloatNullFloat() {
		numServices.mod((Float)null, Float.valueOf(3.14f));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void modFloatByZero() {
		Integer result = numServices.mod(Float.valueOf(3.14f), Float.valueOf(0));
		assertEquals(Integer.valueOf(0), result);
	}

	@Test
	public void modFloat() {
		Integer result = numServices.mod(Float.valueOf(3.13f * 7 + 1), Float.valueOf(3.13f));
		assertEquals(Integer.valueOf(1), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modLongNullNull() {
		numServices.mod((Long)null, (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modLongLongNull() {
		numServices.mod(Long.valueOf(3), (Long)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void modLongNullLong() {
		numServices.mod((Long)null, Long.valueOf(3));
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void modLongByZero() {
		Long result = numServices.mod(Long.valueOf(3), Long.valueOf(0));
		assertEquals(Long.valueOf(0), result);
	}

	@Test
	public void modLong() {
		Long result = numServices.mod(Long.valueOf(3 * 7 + 1), Long.valueOf(3));
		assertEquals(Long.valueOf(1), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void toDoubleIntegerNull() {
		numServices.toDouble((Integer)null);
	}

	@Test
	public void toDoubleInteger() {
		Double result = numServices.toDouble(Integer.valueOf(3));
		assertEquals(Double.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void toDoubleDoubleNull() {
		numServices.toDouble((Double)null);
	}

	@Test
	public void toDoubleDouble() {
		Double result = numServices.toDouble(Double.valueOf(3.14f));
		assertTrue((result.doubleValue() - 3.14) < 0.001);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void toDoubleFloatNull() {
		numServices.toDouble((Float)null);
	}

	@Test
	public void toDoubleFloat() {
		Double result = numServices.toDouble(Float.valueOf(3.14f));
		assertTrue((result.doubleValue() - 3.14) < 0.001);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void toLongLongNull() {
		numServices.toLong((Long)null);
	}

	@Test
	public void toLongLong() {
		Long result = numServices.toLong(Long.valueOf(3));
		assertEquals(Long.valueOf(3), result);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void toLongIntegerNull() {
		numServices.toLong((Integer)null);
	}

	@Test
	public void toLongInteger() {
		Long result = numServices.toLong(Integer.valueOf(3));
		assertEquals(Long.valueOf(3), result);
	}

}
