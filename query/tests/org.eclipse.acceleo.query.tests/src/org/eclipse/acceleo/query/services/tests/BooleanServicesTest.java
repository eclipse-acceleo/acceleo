/*******************************************************************************
 * Copyright (c) 2015 Obeo.
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
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.BooleanServices;
import org.junit.Test;

public class BooleanServicesTest extends AbstractServicesTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(),
				BooleanServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testOrNullNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("or", Boolean.TRUE, new Boolean[] {null, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testOrFalseNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("or", Boolean.TRUE, new Boolean[] {Boolean.FALSE, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testOrTrueNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("or", Boolean.TRUE, new Boolean[] {Boolean.TRUE, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testOrNullFalse() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("or", Boolean.TRUE, new Boolean[] {null, Boolean.FALSE });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testOrNullTrue() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("or", Boolean.TRUE, new Boolean[] {null, Boolean.TRUE });
	}

	@Test
	public void testOr() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		runTest("or", Boolean.TRUE, new Boolean[] {Boolean.TRUE, Boolean.FALSE });
		runTest("or", Boolean.TRUE, new Boolean[] {Boolean.FALSE, Boolean.TRUE });
		runTest("or", Boolean.TRUE, new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		runTest("or", Boolean.FALSE, new Boolean[] {Boolean.FALSE, Boolean.FALSE });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAndNullNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("and", Boolean.TRUE, new Boolean[] {null, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAndFalseNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("and", Boolean.TRUE, new Boolean[] {Boolean.FALSE, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAndTrueNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("and", Boolean.TRUE, new Boolean[] {Boolean.TRUE, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAndNullFalse() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("and", Boolean.TRUE, new Boolean[] {null, Boolean.FALSE });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAndNullTrue() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("and", Boolean.TRUE, new Boolean[] {null, Boolean.TRUE });
	}

	@Test
	public void testAnd() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		runTest("and", Boolean.FALSE, new Boolean[] {Boolean.TRUE, Boolean.FALSE });
		runTest("and", Boolean.FALSE, new Boolean[] {Boolean.FALSE, Boolean.TRUE });
		runTest("and", Boolean.TRUE, new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		runTest("and", Boolean.FALSE, new Boolean[] {Boolean.FALSE, Boolean.FALSE });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testNotNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("not", Boolean.FALSE, new Boolean[] {null });
	}

	@Test
	public void testNot() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		runTest("not", Boolean.FALSE, new Boolean[] {Boolean.TRUE });
		runTest("not", Boolean.TRUE, new Boolean[] {Boolean.FALSE });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testImpliesNullNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("implies", Boolean.TRUE, new Boolean[] {null, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testImpliesFalseNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("implies", Boolean.TRUE, new Boolean[] {Boolean.FALSE, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testImpliesTrueNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("implies", Boolean.TRUE, new Boolean[] {Boolean.TRUE, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testImpliesNullFalse() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("implies", Boolean.TRUE, new Boolean[] {null, Boolean.FALSE });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testImpliesNullTrue() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("implies", Boolean.TRUE, new Boolean[] {null, Boolean.TRUE });
	}

	@Test
	public void testImplies() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("implies", Boolean.FALSE, new Boolean[] {Boolean.TRUE, Boolean.FALSE });
		runTest("implies", Boolean.TRUE, new Boolean[] {Boolean.FALSE, Boolean.TRUE });
		runTest("implies", Boolean.TRUE, new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		runTest("implies", Boolean.TRUE, new Boolean[] {Boolean.FALSE, Boolean.FALSE });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testXorNullNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("xor", Boolean.TRUE, new Boolean[] {null, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testXorFalseNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("xor", Boolean.TRUE, new Boolean[] {Boolean.FALSE, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testXorTrueNull() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("xor", Boolean.TRUE, new Boolean[] {Boolean.TRUE, null });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testXorNullFalse() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("xor", Boolean.TRUE, new Boolean[] {null, Boolean.FALSE });
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testXorNullTrue() throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		runTest("xor", Boolean.TRUE, new Boolean[] {null, Boolean.TRUE });
	}

	@Test
	public void testXor() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		runTest("xor", Boolean.TRUE, new Boolean[] {Boolean.TRUE, Boolean.FALSE });
		runTest("xor", Boolean.TRUE, new Boolean[] {Boolean.FALSE, Boolean.TRUE });
		runTest("xor", Boolean.FALSE, new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		runTest("xor", Boolean.FALSE, new Boolean[] {Boolean.FALSE, Boolean.FALSE });
	}

}
