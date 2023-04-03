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
package org.eclipse.acceleo.query.runtime.servicelookup;

import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public abstract class AbtractServiceLookupTest {

	private static final IType[] NO_ARG = {};

	abstract IQueryEnvironment getQueryEnvironment();

	/**
	 * Tests that the empty engine behaves as expected (returns null to any lookup).
	 */
	@Test
	public void emptyEngineTest() {
		final IQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		assertNull(engine.lookup("method", NO_ARG));
		assertNull(engine.lookup("method", new IType[] {new ClassType(queryEnvironment, Object.class),
				new ClassType(queryEnvironment, Integer.class) }));
	}

	/**
	 * Tests that the engine behaves as expected with respect to no argument methods : returns expected method
	 * with they exist and returns null when looking up inexistant methods.
	 */
	@Test
	public void leafTest() {
		final IQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		assertEquals("service0", engine.lookup("service0", new IType[] {new ClassType(queryEnvironment,
				String.class), }).getName());
		assertEquals("service1", engine.lookup("service1", new IType[] {new ClassType(queryEnvironment,
				String.class), }).getName());
		assertEquals("service2", engine.lookup("service2", new IType[] {new ClassType(queryEnvironment,
				String.class), }).getName());
		assertNull(engine.lookup("noService", NO_ARG));
	}

	/**
	 * Looking up a one argument single method with the same class as the declared parameter. Should return
	 * the expected method.
	 */
	@Test
	public void oneArgSingleMethodSameClassTest() {
		final IQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		assertEquals("service3", engine.lookup("service3", new IType[] {new ClassType(queryEnvironment,
				Object.class) }).getName());
	}

	/**
	 * Looking up a one argument single method with null typing information as arguments (which is bound to
	 * happen when for instance the receiver is null)
	 */
	@Test
	public void nullValueSingleMethodSameClassTest() {
		final IQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		/*
		 * We should not fail if there is no ambiguity regarding which method we have to call, even if we
		 * don't have type information regarding arguments.
		 */
		assertEquals("service3", engine.lookup("service3", new IType[] {new ClassType(queryEnvironment,
				null) }).getName());
	}

	/**
	 * Looking up a one argument single method with a subclass of the declared parameter. Should return the
	 * expected method.
	 */
	@Test
	public void oneArgSingleMethodSubClassTest() {
		final IQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		assertEquals("service3", engine.lookup("service3", new IType[] {new ClassType(queryEnvironment,
				Integer.class) }).getName());
	}

	/**
	 * Looking up a multi-method with the same classes as the declared parameters.
	 */
	@Test
	public void oneArgMultiMethodSameClassTest() {
		final IQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		IService<?> service = engine.lookup("service4", new IType[] {new ClassType(queryEnvironment,
				Integer.class) });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, Integer.class), service.getParameterTypes(
				queryEnvironment).get(0));

		service = engine.lookup("service4", new IType[] {new ClassType(queryEnvironment, Object.class) });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, Object.class), service.getParameterTypes(
				queryEnvironment).get(0));

		service = engine.lookup("service4", new IType[] {new ClassType(queryEnvironment, String.class) });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, String.class), service.getParameterTypes(
				queryEnvironment).get(0));
	}

	/**
	 * Looking up a multi-method with the same classes as the declared parameters.
	 */
	@Test
	public void oneArgMultiMethodSubClassTest() {
		final IQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		IService<?> service = engine.lookup("service4", new IType[] {new ClassType(queryEnvironment,
				Integer.class) });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, Integer.class), service.getParameterTypes(
				queryEnvironment).get(0));

		service = engine.lookup("service4", new IType[] {new ClassType(queryEnvironment, Double.class) });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, Number.class), service.getParameterTypes(
				queryEnvironment).get(0));

		service = engine.lookup("service4", new IType[] {new ClassType(queryEnvironment,
				java.util.List.class) });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, Object.class), service.getParameterTypes(
				queryEnvironment).get(0));
	}

	/**
	 * Looks up the "service5" multi-method with the three arguments being strings. Result must be
	 * <code>null</code>.
	 */
	@Test
	public void threeArgsTest1() {
		final IQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		final IType[] args = {new ClassType(queryEnvironment, String.class), new ClassType(queryEnvironment,
				String.class), new ClassType(queryEnvironment, String.class) };

		final IService<?> service = engine.lookup("service5", args);

		assertNull(service);
	}

	/**
	 * Looks up the "service5" multi-method with the first argument being a string and two others being
	 * doubles. Result must be <code>service5(Object,Number,Number)</code>.
	 */
	@Test
	public void threeArgsTest2() {
		final IReadOnlyQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		final IType[] args = {new ClassType(queryEnvironment, String.class), new ClassType(queryEnvironment,
				Double.class), new ClassType(queryEnvironment, Double.class) };

		final IService<?> service = engine.lookup("service5", args);

		assertNotNull(service);
		assertEquals(new ClassType(queryEnvironment, Object.class), service.getParameterTypes(
				queryEnvironment).get(0));
		assertEquals(new ClassType(queryEnvironment, Number.class), service.getParameterTypes(
				queryEnvironment).get(1));
		assertEquals(new ClassType(queryEnvironment, Number.class), service.getParameterTypes(
				queryEnvironment).get(2));
	}

	/**
	 * Looks up the "service5" multi-method with the two first arguments being integers and two last being a
	 * double. Result must be <code>service5(Integer,Integer,Number)</code>.
	 */
	@Test
	public void threeArgsTest3() {
		final IReadOnlyQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		IType[] args = {new ClassType(queryEnvironment, Integer.class), new ClassType(queryEnvironment,
				Integer.class), new ClassType(queryEnvironment, Double.class) };

		final IService<?> method = engine.lookup("service5", args);

		assertNotNull(method);
		assertEquals(new ClassType(queryEnvironment, Integer.class), method.getParameterTypes(
				queryEnvironment).get(0));
		assertEquals(new ClassType(queryEnvironment, Integer.class), method.getParameterTypes(
				queryEnvironment).get(1));
		assertEquals(new ClassType(queryEnvironment, Number.class), method.getParameterTypes(queryEnvironment)
				.get(2));
	}

	/**
	 * Looks up the "service5" multi-method with the two three arguments being integers. Result must be
	 * <code>service5(Integer,Integer,Integer)</code>.
	 */
	@Test
	public void threeArgsTest4() {
		final IReadOnlyQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		final IType[] args = {new ClassType(queryEnvironment, Integer.class), new ClassType(queryEnvironment,
				Integer.class), new ClassType(queryEnvironment, Integer.class) };

		final IService<?> method = engine.lookup("service5", args);

		assertNotNull(method);
		assertEquals(new ClassType(queryEnvironment, Integer.class), method.getParameterTypes(
				queryEnvironment).get(0));
		assertEquals(new ClassType(queryEnvironment, Integer.class), method.getParameterTypes(
				queryEnvironment).get(1));
		assertEquals(new ClassType(queryEnvironment, Integer.class), method.getParameterTypes(
				queryEnvironment).get(2));
	}

	/**
	 * Looks up the "service5" multi-method with the these arguments : {String, Integer,String}. Result must
	 * be <code>service5(String,Number,String)</code>.
	 */
	@Test
	public void threeArgsTest5() {
		final IReadOnlyQueryEnvironment queryEnvironment = getQueryEnvironment();
		final ILookupEngine engine = queryEnvironment.getLookupEngine();

		final IType[] args = {new ClassType(queryEnvironment, String.class), new ClassType(queryEnvironment,
				Integer.class), new ClassType(queryEnvironment, String.class) };

		final IService<?> method = engine.lookup("service5", args);

		assertNotNull(method);
		assertEquals(new ClassType(queryEnvironment, String.class), method.getParameterTypes(queryEnvironment)
				.get(0));
		assertEquals(new ClassType(queryEnvironment, Number.class), method.getParameterTypes(queryEnvironment)
				.get(1));
		assertEquals(new ClassType(queryEnvironment, String.class), method.getParameterTypes(queryEnvironment)
				.get(2));
	}

}
