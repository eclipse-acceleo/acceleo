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

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public abstract class AbtractServiceLookupTest {

	private static final Class<?>[] NO_ARG = {};

	abstract BasicLookupEngine getEngine();

	/**
	 * Tests that the empty engine behaves as expected (returns null to any lookup).
	 */
	@Test
	public void emptyEngineTest() {
		BasicLookupEngine engine = getEngine();
		assertNull(engine.lookup("method", NO_ARG));
		assertNull(engine.lookup("method", new Class<?>[] {Object.class, Integer.class }));
	}

	/**
	 * Tests that the engine behaves as expected with respect to no argument methods : returns expected method
	 * with they exist and returns null when looking up inexistant methods.
	 */
	@Test
	public void leafTest() {
		BasicLookupEngine engine = getEngine();
		assertEquals("service0", engine.lookup("service0", NO_ARG).getServiceMethod().getName());
		assertEquals("service1", engine.lookup("service1", NO_ARG).getServiceMethod().getName());
		assertEquals("service2", engine.lookup("service2", NO_ARG).getServiceMethod().getName());
		assertNull(engine.lookup("noService", NO_ARG));
	}

	/**
	 * Looking up a one argument single method with the same class as the declared parameter. Should return
	 * the expected method.
	 */
	@Test
	public void oneArgSingleMethodSameClassTest() {
		BasicLookupEngine engine = getEngine();
		assertEquals("service3", engine.lookup("service3", new Class<?>[] {Object.class }).getServiceMethod()
				.getName());

	}

	/**
	 * Looking up a one argument single method with null typing information as arguments (which is bound to
	 * happen when for instance the receiver is null)
	 */
	@Test
	public void nullValueSingleMethodSameClassTest() {
		BasicLookupEngine engine = getEngine();
		/*
		 * We should not fail if there is no ambiguity regarding which method we have to call, even if we
		 * don't have type information regarding arguments.
		 */
		assertEquals("service3", engine.lookup("service3", new Class<?>[] {null }).getServiceMethod()
				.getName());

	}

	/**
	 * Looking up a one argument single method with a subclass of the declared parameter. Should return the
	 * expected method.
	 */
	@Test
	public void oneArgSingleMethodSubClassTest() {
		BasicLookupEngine engine = getEngine();
		assertEquals("service3", engine.lookup("service3", new Class<?>[] {Integer.class })
				.getServiceMethod().getName());
	}

	/**
	 * Looking up a multi-method with the same classes as the declared parameters.
	 */
	@Test
	public void oneArgMultiMethodSameClassTest() {
		BasicLookupEngine engine = getEngine();
		IService service = engine.lookup("service4", new Class<?>[] {Integer.class });
		assertEquals("service4", service.getServiceMethod().getName());
		assertEquals(1, service.getServiceMethod().getParameterTypes().length);
		assertEquals(Integer.class, service.getServiceMethod().getParameterTypes()[0]);
		service = engine.lookup("service4", new Class<?>[] {Object.class });
		assertEquals("service4", service.getServiceMethod().getName());
		assertEquals(1, service.getServiceMethod().getParameterTypes().length);
		assertEquals(Object.class, service.getServiceMethod().getParameterTypes()[0]);
		service = engine.lookup("service4", new Class<?>[] {String.class });
		assertEquals("service4", service.getServiceMethod().getName());
		assertEquals(1, service.getServiceMethod().getParameterTypes().length);
		assertEquals(String.class, service.getServiceMethod().getParameterTypes()[0]);
	}

	/**
	 * Looking up a multi-method with the same classes as the declared parameters.
	 */
	@Test
	public void oneArgMultiMethodSubClassTest() {
		BasicLookupEngine engine = getEngine();
		IService service = engine.lookup("service4", new Class<?>[] {Integer.class });
		assertEquals("service4", service.getServiceMethod().getName());
		assertEquals(1, service.getServiceMethod().getParameterTypes().length);
		assertEquals(Integer.class, service.getServiceMethod().getParameterTypes()[0]);
		service = engine.lookup("service4", new Class<?>[] {Double.class });
		assertEquals("service4", service.getServiceMethod().getName());
		assertEquals(1, service.getServiceMethod().getParameterTypes().length);
		assertEquals(Number.class, service.getServiceMethod().getParameterTypes()[0]);
		service = engine.lookup("service4", new Class<?>[] {java.util.List.class });
		assertEquals("service4", service.getServiceMethod().getName());
		assertEquals(1, service.getServiceMethod().getParameterTypes().length);
		assertEquals(Object.class, service.getServiceMethod().getParameterTypes()[0]);
	}

	/**
	 * Looks up the "service5" multi-method with the three arguments being strings. Result must be
	 * <code>null</code>.
	 */
	@Test
	public void threeArgsTest1() {
		Class<?>[] args = {String.class, String.class, String.class };
		IService method = getEngine().lookup("service5", args);
		assertNull(method);
	}

	/**
	 * Looks up the "service5" multi-method with the first argument being a string and two others being
	 * doubles. Result must be <code>service5(Object,Number,Number)</code>.
	 */
	@Test
	public void threeArgsTest2() {
		Class<?>[] args = {String.class, Double.class, Double.class };
		IService method = getEngine().lookup("service5", args);
		assertNotNull(method);
		assertEquals(Object.class, method.getServiceMethod().getParameterTypes()[0]);
		assertEquals(Number.class, method.getServiceMethod().getParameterTypes()[1]);
		assertEquals(Number.class, method.getServiceMethod().getParameterTypes()[2]);
	}

	/**
	 * Looks up the "service5" multi-method with the two first arguments being integers and two last being a
	 * double. Result must be <code>service5(Integer,Integer,Number)</code>.
	 */
	@Test
	public void threeArgsTest3() {
		Class<?>[] args = {Integer.class, Integer.class, Double.class };
		IService method = getEngine().lookup("service5", args);
		assertNotNull(method);
		assertEquals(Integer.class, method.getServiceMethod().getParameterTypes()[0]);
		assertEquals(Integer.class, method.getServiceMethod().getParameterTypes()[1]);
		assertEquals(Number.class, method.getServiceMethod().getParameterTypes()[2]);
	}

	/**
	 * Looks up the "service5" multi-method with the two three arguments being integers. Result must be
	 * <code>service5(Integer,Integer,Integer)</code>.
	 */
	@Test
	public void threeArgsTest4() {
		Class<?>[] args = {Integer.class, Integer.class, Integer.class };
		IService method = getEngine().lookup("service5", args);
		assertNotNull(method);
		assertEquals(Integer.class, method.getServiceMethod().getParameterTypes()[0]);
		assertEquals(Integer.class, method.getServiceMethod().getParameterTypes()[1]);
		assertEquals(Integer.class, method.getServiceMethod().getParameterTypes()[2]);
	}

	/**
	 * Looks up the "service5" multi-method with the these arguments : {String, Integer,String}. Result must
	 * be <code>service5(String,Number,String)</code>.
	 */
	@Test
	public void threeArgsTest5() {
		Class<?>[] args = {String.class, Integer.class, String.class };
		IService method = getEngine().lookup("service5", args);
		assertNotNull(method);
		assertEquals(String.class, method.getServiceMethod().getParameterTypes()[0]);
		assertEquals(Number.class, method.getServiceMethod().getParameterTypes()[1]);
		assertEquals(String.class, method.getServiceMethod().getParameterTypes()[2]);
	}

}
