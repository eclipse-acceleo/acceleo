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
import org.eclipse.acceleo.query.services.NumberServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NumberServicesValidationTest extends AbstractServicesTest {

	@Override
	public void before() throws Exception {
		super.before();
		getLookupEngine().addServices(NumberServices.class);
	}

	@Test
	public void testUnaryMinInteger() {
		final IService service = serviceLookUp("unaryMin", new Object[] {Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testUnaryMinDouble() {
		final IService service = serviceLookUp("unaryMin", new Object[] {Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Double.class), it.next());
	}

	@Test
	public void testAddInteger() {
		final IService service = serviceLookUp("add", new Object[] {Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testAddDouble() {
		final IService service = serviceLookUp("add", new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Double.class), it.next());
	}

	@Test
	public void testSubInteger() {
		final IService service = serviceLookUp("sub", new Object[] {Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testSubDouble() {
		final IService service = serviceLookUp("sub", new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Double.class), it.next());
	}

	@Test
	public void testMultInteger() {
		final IService service = serviceLookUp("mult", new Object[] {Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testMultDouble() {
		final IService service = serviceLookUp("mult", new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Double.class), it.next());
	}

	@Test
	public void testDivOpInteger() {
		final IService service = serviceLookUp("divOp",
				new Object[] {Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testDivOpDouble() {
		final IService service = serviceLookUp("divOp", new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Double.class), it.next());
	}

	@Test
	public void testLessThanInteger() {
		final IService service = serviceLookUp("lessThan", new Object[] {Integer.valueOf(0),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testLessThanDouble() {
		final IService service = serviceLookUp("lessThan",
				new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testGreaterThanInteger() {
		final IService service = serviceLookUp("greaterThan", new Object[] {Integer.valueOf(0),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testGreaterThanDouble() {
		final IService service = serviceLookUp("greaterThan", new Object[] {Double.valueOf(0),
				Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testGreaterThanEqualInteger() {
		final IService service = serviceLookUp("greaterThanEqual", new Object[] {Integer.valueOf(0),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testGreaterThanEqualDouble() {
		final IService service = serviceLookUp("greaterThanEqual", new Object[] {Double.valueOf(0),
				Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testEqualsInteger() {
		final IService service = serviceLookUp("equals",
				new Object[] {Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testEqualsDouble() {
		final IService service = serviceLookUp("equals", new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testDiffersInteger() {
		final IService service = serviceLookUp("differs", new Object[] {Integer.valueOf(0),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testDiffersDouble() {
		final IService service = serviceLookUp("differs",
				new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testLessThanEqualInteger() {
		final IService service = serviceLookUp("lessThanEqual", new Object[] {Integer.valueOf(0),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testLessThanEqualDouble() {
		final IService service = serviceLookUp("lessThanEqual", new Object[] {Double.valueOf(0),
				Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testAbsInteger() {
		final IService service = serviceLookUp("abs", new Object[] {Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testAbsDouble() {
		final IService service = serviceLookUp("abs", new Object[] {Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Double.class), it.next());
	}

	@Test
	public void testFloorInteger() {
		final IService service = serviceLookUp("floor", new Object[] {Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testFloorDouble() {
		final IService service = serviceLookUp("floor", new Object[] {Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testMaxInteger() {
		final IService service = serviceLookUp("max", new Object[] {Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testMaxDouble() {
		final IService service = serviceLookUp("max", new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Double.class), it.next());
	}

	@Test
	public void testMinInteger() {
		final IService service = serviceLookUp("min", new Object[] {Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testMinDouble() {
		final IService service = serviceLookUp("min", new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Double.class), it.next());
	}

	@Test
	public void testRoundInteger() {
		final IService service = serviceLookUp("round", new Object[] {Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testRoundDouble() {
		final IService service = serviceLookUp("round", new Object[] {Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testDivInteger() {
		final IService service = serviceLookUp("div", new Object[] {Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testDivDouble() {
		final IService service = serviceLookUp("div", new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testModInteger() {
		final IService service = serviceLookUp("mod", new Object[] {Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Integer.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testModDouble() {
		final IService service = serviceLookUp("mod", new Object[] {Double.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Double.class));
		argTypes.add(new ClassType(Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

}
