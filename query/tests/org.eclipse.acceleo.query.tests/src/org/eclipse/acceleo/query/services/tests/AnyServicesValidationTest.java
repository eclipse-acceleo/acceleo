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
		getQueryEnvironment().registerServicePackage(AnyServices.class);
	}

	@Test
	public void testEqualsPrimitive() {
		final IService service = serviceLookUp("equals", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));

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
	public void testEquals() {
		final IService service = serviceLookUp("equals", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));

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
	public void testDiffersPrimitive() {
		final IService service = serviceLookUp("differs", new Boolean[] {true, true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));

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
		final IService service = serviceLookUp("differs", new Boolean[] {Boolean.TRUE, Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));

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
	public void testAddAnyObjectStringPrimitive() {
		final IService service = serviceLookUp("add", new Object[] {true, "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testAddAnyObjectString() {
		final IService service = serviceLookUp("add", new Object[] {Boolean.TRUE, "" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testAddAnyStringObjectPrimitive() {
		final IService service = serviceLookUp("add", new Object[] {"", true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testAddAnyStringObject() {
		final IService service = serviceLookUp("add", new Object[] {"", Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testOCLIsKindOfClassPrimitive() {
		final IService service = serviceLookUp("oclIsKindOf", new Object[] {new Object(), int.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Class.class));

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
	public void testOCLIsKindOfClass() {
		final IService service = serviceLookUp("oclIsKindOf", new Object[] {new Object(), Integer.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Class.class));

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
	public void testOCLIsKindOfEClass() {
		final IService service = serviceLookUp("oclIsKindOf", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new ClassType(getQueryEnvironment(), EClass.class));

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
	public void testOCLIsTypeOfClassPrimitive() {
		final IService service = serviceLookUp("oclIsTypeOf", new Object[] {new Object(), int.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Class.class));

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
	public void testOCLIsTypeOfClass() {
		final IService service = serviceLookUp("oclIsTypeOf", new Object[] {new Object(), Integer.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Class.class));

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
	public void testOCLIsTypeOfEClass() {
		final IService service = serviceLookUp("oclIsTypeOf", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new ClassType(getQueryEnvironment(), EClass.class));

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
	public void testOCLAsTypeClassPrimitive() {
		final IService service = serviceLookUp("oclAsType", new Object[] {new Object(), int.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new ClassType(getQueryEnvironment(), int.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), int.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), int.class), it.next());
	}

	@Test
	public void testOCLAsTypeClass() {
		final IService service = serviceLookUp("oclAsType", new Object[] {new Object(), Integer.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testOCLAsTypeEClassClass() {
		final IService service = serviceLookUp("oclAsType", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new ClassType(getQueryEnvironment(), EClass.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), EClass.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), EClass.class), it.next());
	}

	@Test
	public void testOCLAsTypeEClassEClassifier() {
		final IService service = serviceLookUp("oclAsType", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Object.class));
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()), it
				.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()), it
				.next());
	}

	@Test
	public void testToStringPrimitive() {
		final IService service = serviceLookUp("toString", new Boolean[] {true });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), boolean.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testToString() {
		final IService service = serviceLookUp("toString", new Boolean[] {Boolean.TRUE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), Boolean.class));

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

}
