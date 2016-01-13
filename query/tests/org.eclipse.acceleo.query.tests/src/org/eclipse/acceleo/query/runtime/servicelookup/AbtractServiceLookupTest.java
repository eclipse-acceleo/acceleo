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
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public abstract class AbtractServiceLookupTest {

	private static final Class<?>[] NO_ARG = {};

	abstract ILookupEngine getEngine();

	/**
	 * Tests that the empty engine behaves as expected (returns null to any lookup).
	 */
	@Test
	public void emptyEngineTest() {
		final ILookupEngine engine = getEngine();

		assertNull(engine.lookup("method", NO_ARG));
		assertNull(engine.lookup("method", new Class<?>[] {Object.class, Integer.class }));
	}

	/**
	 * Tests that the engine behaves as expected with respect to no argument methods : returns expected method
	 * with they exist and returns null when looking up inexistant methods.
	 */
	@Test
	public void leafTest() {
		final ILookupEngine engine = getEngine();

		assertEquals("service0", engine.lookup("service0", new Class<?>[] {String.class }).getName());
		assertEquals("service1", engine.lookup("service1", new Class<?>[] {String.class }).getName());
		assertEquals("service2", engine.lookup("service2", new Class<?>[] {String.class }).getName());
		assertNull(engine.lookup("noService", NO_ARG));
	}

	/**
	 * Looking up a one argument single method with the same class as the declared parameter. Should return
	 * the expected method.
	 */
	@Test
	public void oneArgSingleMethodSameClassTest() {
		final ILookupEngine engine = getEngine();

		assertEquals("service3", engine.lookup("service3", new Class<?>[] {Object.class }).getName());
	}

	/**
	 * Looking up a one argument single method with null typing information as arguments (which is bound to
	 * happen when for instance the receiver is null)
	 */
	@Test
	public void nullValueSingleMethodSameClassTest() {
		final ILookupEngine engine = getEngine();

		/*
		 * We should not fail if there is no ambiguity regarding which method we have to call, even if we
		 * don't have type information regarding arguments.
		 */
		assertEquals("service3", engine.lookup("service3", new Class<?>[] {null }).getName());
	}

	/**
	 * Looking up a one argument single method with a subclass of the declared parameter. Should return the
	 * expected method.
	 */
	@Test
	public void oneArgSingleMethodSubClassTest() {
		final ILookupEngine engine = getEngine();

		assertEquals("service3", engine.lookup("service3", new Class<?>[] {Integer.class }).getName());
	}

	/**
	 * Looking up a multi-method with the same classes as the declared parameters.
	 */
	@Test
	public void oneArgMultiMethodSameClassTest() {
		final ILookupEngine engine = getEngine();
		final IReadOnlyQueryEnvironment queryEnvironment = Query.newEnvironment(null);

		IService service = engine.lookup("service4", new Class<?>[] {Integer.class });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, Integer.class), service.getParameterTypes(
				queryEnvironment).get(0));

		service = engine.lookup("service4", new Class<?>[] {Object.class });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, Object.class), service.getParameterTypes(
				queryEnvironment).get(0));

		service = engine.lookup("service4", new Class<?>[] {String.class });

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
		final ILookupEngine engine = getEngine();
		final IReadOnlyQueryEnvironment queryEnvironment = Query.newEnvironment(null);

		IService service = engine.lookup("service4", new Class<?>[] {Integer.class });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, Integer.class), service.getParameterTypes(
				queryEnvironment).get(0));

		service = engine.lookup("service4", new Class<?>[] {Double.class });

		assertEquals("service4", service.getName());
		assertEquals(1, service.getNumberOfParameters());
		assertEquals(new ClassType(queryEnvironment, Number.class), service.getParameterTypes(
				queryEnvironment).get(0));

		service = engine.lookup("service4", new Class<?>[] {java.util.List.class });

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
		final Class<?>[] args = {String.class, String.class, String.class };

		final IService service = getEngine().lookup("service5", args);

		assertNull(service);
	}

	/**
	 * Looks up the "service5" multi-method with the first argument being a string and two others being
	 * doubles. Result must be <code>service5(Object,Number,Number)</code>.
	 */
	@Test
	public void threeArgsTest2() {
		final Class<?>[] args = {String.class, Double.class, Double.class };
		final IReadOnlyQueryEnvironment queryEnvironment = Query.newEnvironment(null);

		final IService service = getEngine().lookup("service5", args);

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
		Class<?>[] args = {Integer.class, Integer.class, Double.class };
		final IReadOnlyQueryEnvironment queryEnvironment = Query.newEnvironment(null);

		final IService method = getEngine().lookup("service5", args);

		assertNotNull(method);
		assertEquals(new ClassType(queryEnvironment, Integer.class), method.getParameterTypes(
				queryEnvironment).get(0));
		assertEquals(new ClassType(queryEnvironment, Integer.class), method.getParameterTypes(
				queryEnvironment).get(1));
		assertEquals(new ClassType(queryEnvironment, Number.class), method
				.getParameterTypes(queryEnvironment).get(2));
	}

	/**
	 * Looks up the "service5" multi-method with the two three arguments being integers. Result must be
	 * <code>service5(Integer,Integer,Integer)</code>.
	 */
	@Test
	public void threeArgsTest4() {
		final Class<?>[] args = {Integer.class, Integer.class, Integer.class };
		final IReadOnlyQueryEnvironment queryEnvironment = Query.newEnvironment(null);

		final IService method = getEngine().lookup("service5", args);

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
		final Class<?>[] args = {String.class, Integer.class, String.class };
		final IReadOnlyQueryEnvironment queryEnvironment = Query.newEnvironment(null);

		final IService method = getEngine().lookup("service5", args);

		assertNotNull(method);
		assertEquals(new ClassType(queryEnvironment, String.class), method
				.getParameterTypes(queryEnvironment).get(0));
		assertEquals(new ClassType(queryEnvironment, Number.class), method
				.getParameterTypes(queryEnvironment).get(1));
		assertEquals(new ClassType(queryEnvironment, String.class), method
				.getParameterTypes(queryEnvironment).get(2));
	}

}
