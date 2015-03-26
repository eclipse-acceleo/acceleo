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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.services.ComparableServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ComparableServicesValidationTest extends AbstractServicesTest {

	@Override
	public void before() throws Exception {
		super.before();
		getQueryEnvironment().registerServicePackage(ComparableServices.class);
	}

	@Test
	public void testLower() {
		final IService service = serviceLookUp("lower", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testLowerEqual() {
		final IService service = serviceLookUp("lowerEqual", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testGreater() {
		final IService service = serviceLookUp("greater", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testGreaterEqual() {
		final IService service = serviceLookUp("greaterEqual", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
	}

	@Test
	public void testEquals() {
		final IService service = serviceLookUp("equals", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testDiffers() {
		final IService service = serviceLookUp("differs", new Object[] {"", "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

}
