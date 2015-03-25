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
import org.eclipse.acceleo.query.services.BooleanServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BooleanServicesValidationTest extends AbstractServicesTest {

	@Override
	public void before() throws Exception {
		super.before();
		getQueryEnvironment().registerServicePackage(BooleanServices.class);
	}

	@Test
	public void testOrPrimitive() {
		final IService service = serviceLookUp("or", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testOr() {
		final IService service = serviceLookUp("or", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testAndPrimitive() {
		final IService service = serviceLookUp("and", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testAnd() {
		final IService service = serviceLookUp("and", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testNotPrimitive() {
		final IService service = serviceLookUp("not", new Boolean[] {true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testNot() {
		final IService service = serviceLookUp("not", new Boolean[] {Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testImpliesPrimitive() {
		final IService service = serviceLookUp("implies", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testImplies() {
		final IService service = serviceLookUp("implies", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testXorPrimitive() {
		final IService service = serviceLookUp("xor", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testXor() {
		final IService service = serviceLookUp("xor", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

}
