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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.services.StringServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringServicesValidationTest extends AbstractServicesTest {

	@Override
	public void before() throws Exception {
		super.before();
		getQueryEnvironment().registerServicePackage(StringServices.class);
	}

	@Test
	public void testConcat() {
		final IService service = serviceLookUp("concat", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testAdd() {
		final IService service = serviceLookUp("add", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testReplace() {
		final IService service = serviceLookUp("replace", new Object[] {"", "", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testReplaceAll() {
		final IService service = serviceLookUp("replaceAll", new Object[] {"", "", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testPrefix() {
		final IService service = serviceLookUp("prefix", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testContains() {
		final IService service = serviceLookUp("contains", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testMatches() {
		final IService service = serviceLookUp("matches", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testEndsWith() {
		final IService service = serviceLookUp("endsWith", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testStartsWith() {
		final IService service = serviceLookUp("startsWith", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testEqualsIgnoreCase() {
		final IService service = serviceLookUp("equalsIgnoreCase", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testFirst() {
		final IService service = serviceLookUp("first", new Object[] {"", Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testLast() {
		final IService service = serviceLookUp("last", new Object[] {"", Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testLastIndex2() {
		final IService service = serviceLookUp("lastIndex", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testIndex2() {
		final IService service = serviceLookUp("index", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testIndex3() {
		final IService service = serviceLookUp("index", new Object[] {"", "", Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testLastIndex3() {
		final IService service = serviceLookUp("lastIndex", new Object[] {"", "", Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testToLower() {
		final IService service = serviceLookUp("toLower", new Object[] {"" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testToLowerFirst() {
		final IService service = serviceLookUp("toLowerFirst", new Object[] {"" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testToUpper() {
		final IService service = serviceLookUp("toUpper", new Object[] {"" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testToUpperFirst() {
		final IService service = serviceLookUp("toUpperFirst", new Object[] {"" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testIsAlpha() {
		final IService service = serviceLookUp("isAlpha", new Object[] {"" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testIsAlphaNum() {
		final IService service = serviceLookUp("isAlphaNum", new Object[] {"" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testSize() {
		final IService service = serviceLookUp("size", new Object[] {"" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testSubstring() {
		final IService service = serviceLookUp("substring", new Object[] {"", Integer.valueOf(0),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testToInteger() {
		final IService service = serviceLookUp("toInteger", new Object[] {"" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testToReal() {
		final IService service = serviceLookUp("toReal", new Object[] {"" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Double.class), it.next());
	}

}
