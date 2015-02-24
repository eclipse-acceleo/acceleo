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
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnyServicesValidationTest extends AbstractServicesTest {

	@Override
	public void before() throws Exception {
		super.before();
		getLookupEngine().addServices(AnyServices.class);
	}

	@Test
	public void testEqualsPrimitive() {
		final IService service = serviceLookUp("equals", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(boolean.class));
		argTypes.add(new ClassType(boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testEquals() {
		final IService service = serviceLookUp("equals", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Boolean.class));
		argTypes.add(new ClassType(Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testDiffersPrimitive() {
		final IService service = serviceLookUp("differs", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(boolean.class));
		argTypes.add(new ClassType(boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testDiffers() {
		final IService service = serviceLookUp("differs", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Boolean.class));
		argTypes.add(new ClassType(Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testLessThanPrimitive() {
		final IService service = serviceLookUp("lessThan", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(boolean.class));
		argTypes.add(new ClassType(boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testLessThan() {
		final IService service = serviceLookUp("lessThan", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Boolean.class));
		argTypes.add(new ClassType(Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testGreaterThanPrimitive() {
		final IService service = serviceLookUp("greaterThan", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(boolean.class));
		argTypes.add(new ClassType(boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testGreaterThan() {
		final IService service = serviceLookUp("greaterThan", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Boolean.class));
		argTypes.add(new ClassType(Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testLessThanEqualPrimitive() {
		final IService service = serviceLookUp("lessThanEqual", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(boolean.class));
		argTypes.add(new ClassType(boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testLessThanEqual() {
		final IService service = serviceLookUp("lessThanEqual", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Boolean.class));
		argTypes.add(new ClassType(Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testGreaterThanEqualPrimitive() {
		final IService service = serviceLookUp("greaterThanEqual", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(boolean.class));
		argTypes.add(new ClassType(boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testGreaterThanEqual() {
		final IService service = serviceLookUp("greaterThanEqual",
				new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Boolean.class));
		argTypes.add(new ClassType(Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testAddAnyObjectStringPrimitive() {
		final IService service = serviceLookUp("add", new Object[] {true, "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(boolean.class));
		argTypes.add(new ClassType(String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(String.class), it.next());
	}

	@Test
	public void testAddAnyObjectString() {
		final IService service = serviceLookUp("add", new Object[] {Boolean.TRUE, "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Boolean.class));
		argTypes.add(new ClassType(String.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(String.class), it.next());
	}

	@Test
	public void testAddAnyStringObjectPrimitive() {
		final IService service = serviceLookUp("add", new Object[] {"", true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(String.class));
		argTypes.add(new ClassType(boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(String.class), it.next());
	}

	@Test
	public void testAddAnyStringObject() {
		final IService service = serviceLookUp("add", new Object[] {"", Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(String.class));
		argTypes.add(new ClassType(Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(String.class), it.next());
	}

	@Test
	public void testOCLIsKindOfClassPrimitive() {
		final IService service = serviceLookUp("oclIsKindOf", new Object[] {new Object(), int.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new ClassType(Class.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testOCLIsKindOfClass() {
		final IService service = serviceLookUp("oclIsKindOf", new Object[] {new Object(), Integer.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new ClassType(Class.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testOCLIsKindOfEClass() {
		final IService service = serviceLookUp("oclIsKindOf", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new ClassType(EClass.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testOCLIsTypeOfClassPrimitive() {
		final IService service = serviceLookUp("oclIsTypeOf", new Object[] {new Object(), int.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new ClassType(Class.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testOCLIsTypeOfClass() {
		final IService service = serviceLookUp("oclIsTypeOf", new Object[] {new Object(), Integer.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new ClassType(Class.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testOCLIsTypeOfEClass() {
		final IService service = serviceLookUp("oclIsTypeOf", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new ClassType(EClass.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Boolean.class), it.next());
	}

	@Test
	public void testOCLAsTypeClassPrimitive() {
		final IService service = serviceLookUp("oclAsType", new Object[] {new Object(), int.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new ClassType(int.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(int.class), it.next());
	}

	@Test
	public void testOCLAsTypeClass() {
		final IService service = serviceLookUp("oclAsType", new Object[] {new Object(), Integer.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new ClassType(Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(Integer.class), it.next());
	}

	@Test
	public void testOCLAsTypeEClassClass() {
		final IService service = serviceLookUp("oclAsType", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new ClassType(EClass.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(EClass.class), it.next());
	}

	@Test
	public void testOCLAsTypeEClassEClassifier() {
		final IService service = serviceLookUp("oclAsType", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Object.class));
		argTypes.add(new EClassifierType(EcorePackage.eINSTANCE.getEClass()));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new EClassifierType(EcorePackage.eINSTANCE.getEClass()), it.next());
	}

	@Test
	public void testToStringPrimitive() {
		final IService service = serviceLookUp("toString", new Boolean[] {true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(String.class), it.next());
	}

	@Test
	public void testToString() {
		final IService service = serviceLookUp("toString", new Boolean[] {Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(Boolean.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment()
				.getEPackageProvider(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(String.class), it.next());
	}

}
