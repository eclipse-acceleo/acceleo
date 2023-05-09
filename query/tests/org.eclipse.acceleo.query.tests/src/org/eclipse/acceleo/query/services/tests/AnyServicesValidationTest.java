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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnyServicesValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(), new AnyServices(
				getQueryEnvironment()));
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void testEqualsPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(boolean.class), classType(boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "equals", parameterTypes);
	}

	@Test
	public void testEquals() {
		final IType[] parameterTypes = new IType[] {classType(Boolean.class), classType(Boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "equals", parameterTypes);
	}

	@Test
	public void testDiffersPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(boolean.class), classType(boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "differs", parameterTypes);
	}

	@Test
	public void testDiffers() {
		final IType[] parameterTypes = new IType[] {classType(Boolean.class), classType(Boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "differs", parameterTypes);
	}

	@Test
	public void testAddAnyObjectStringPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(boolean.class), classType(String.class) };

		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAddAnyObjectString() {
		final IType[] parameterTypes = new IType[] {classType(Object.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAddAnyStringObjectPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAddAnyStringObject() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(Object.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testOCLIsKindOfClass() {
		final IType[] parameterTypes = new IType[] {classType(Object.class), classType(Class.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "oclIsKindOf", parameterTypes);
	}

	@Test
	public void testOCLIsKindOfEClass() {
		final IType[] parameterTypes = new IType[] {classType(Object.class), classType(EClass.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "oclIsKindOf", parameterTypes);
	}

	@Test
	public void testOCLIsKindOfEDataType() {
		final IType[] parameterTypes = new IType[] {classType(Boolean.class), eClassifierType(
				EcorePackage.eINSTANCE.getEBoolean()) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "oclIsKindOf", parameterTypes);
	}

	@Test
	public void testOCLIsTypeOfClass() {
		final IType[] parameterTypes = new IType[] {classType(Object.class), classType(Class.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "oclIsTypeOf", parameterTypes);
	}

	@Test
	public void testOCLIsTypeOfEClass() {
		final IType[] parameterTypes = new IType[] {classType(Object.class), classType(EClass.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "oclIsTypeOf", parameterTypes);
	}

	@Test
	public void testOCLAsTypeClass() {
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {new Object(), Integer.class });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(classType(Object.class));
		argTypes.add(classType(Integer.class));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(classType(Integer.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(classType(Integer.class), it.next());
	}

	@Test
	public void testOCLAsTypeEClassClass() {
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(classType(Object.class));
		argTypes.add(classType(EClass.class));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(classType(EClass.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(classType(EClass.class), it.next());
	}

	@Test
	public void testOCLAsTypeEClassEClassifier() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(classType(Object.class));
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEClass()));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(eClassifierType(EcorePackage.eINSTANCE.getEClass()), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(eClassifierType(EcorePackage.eINSTANCE.getEClass()), it.next());
	}

	@Test
	public void testOCLAsTypeEClassEClassifierUnregistered() {
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(classType(Object.class));
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEClass()));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		String message = ((NothingType)next).getMessage();
		assertTrue(message.contains("EClass"));
		assertTrue(message.endsWith("is not registered within the current environment."));

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		next = it.next();
		assertTrue(next instanceof NothingType);
		String allTypesMesg = ((NothingType)next).getMessage();
		assertTrue(allTypesMesg.startsWith("Nothing will be left after calling oclAsType:"));
		assertTrue(allTypesMesg.contains("EClass"));
		assertTrue(allTypesMesg.endsWith("is not registered within the current environment."));
	}

	@Test
	public void testOCLAsTypeObjectToEInt() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEInt() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(classType(Object.class));
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEInt()));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(eClassifierType(EcorePackage.eINSTANCE.getEInt()), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(eClassifierType(EcorePackage.eINSTANCE.getEInt()), it.next());
	}

	@Test
	public void testOCLAsTypeObjectToEIntUnregistered() {
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {new Object(),
				EcorePackage.eINSTANCE.getEInt() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(classType(Object.class));
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEInt()));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		String message = ((NothingType)next).getMessage();
		assertTrue(message.contains("EInt"));
		assertTrue(message.endsWith("is not registered within the current environment."));

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		next = it.next();
		assertTrue(next instanceof NothingType);
		String allTypesMesg = ((NothingType)next).getMessage();
		assertTrue(allTypesMesg.startsWith("Nothing will be left after calling oclAsType:"));
		assertTrue(allTypesMesg.contains("EInt"));
		assertTrue(allTypesMesg.endsWith("is not registered within the current environment."));
	}

	@Test
	public void oclAsTypeCommonSubTypes() {
		// A X
		// ^ ^
		// B
		final EPackage ePackage = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePackage.setName("test");
		ePackage.setNsURI("test");
		ePackage.setNsPrefix("test");
		final EClass a = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		a.setName("A");
		final EClass x = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		x.setName("X");
		final EClass b = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		b.setName("B");
		b.getESuperTypes().add(a);
		b.getESuperTypes().add(x);

		ePackage.getEClassifiers().add(a);
		ePackage.getEClassifiers().add(x);
		ePackage.getEClassifiers().add(b);

		getQueryEnvironment().registerEPackage(ePackage);

		final IService<?> service = serviceLookUp("oclAsType", new Object[] {EcoreUtil.create(a), x });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(eClassifierType(a));
		argTypes.add(eClassifierType(x));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		assertEquals("EClassifier=A is not compatible with type EClassifier=X", ((NothingType)next)
				.getMessage());

		next = it.next();
		assertTrue(next instanceof EClassifierType);
		assertEquals(b, ((EClassifierType)next).getType());
	}

	@Test
	public void testOCLAsTypeEIntToObject() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {EcorePackage.eINSTANCE.getEInt(),
				new Object(), });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEInt()));
		argTypes.add(classType(Object.class));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(classType(Object.class), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		assertEquals(classType(Object.class), it.next());
	}

	@Test
	public void testOCLAsTypeEIntToObjectUnregistered() {
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {EcorePackage.eINSTANCE.getEInt(),
				new Object(), });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEInt()));
		argTypes.add(classType(Object.class));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		String message = ((NothingType)next).getMessage();
		assertTrue(message.contains("EInt"));
		assertTrue(message.endsWith("is not registered within the current environment."));

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		next = it.next();
		assertTrue(next instanceof NothingType);
		String allTypesMesg = ((NothingType)next).getMessage();
		assertTrue(allTypesMesg.startsWith("Nothing will be left after calling oclAsType:"));
		assertTrue(allTypesMesg.contains("EInt"));
		assertTrue(allTypesMesg.endsWith("is not registered within the current environment."));
	}

	@Test
	public void testOCLAsTypeIncompatibleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {EcorePackage.eINSTANCE
				.getEClass(), EcorePackage.eINSTANCE.getEPackage() });
		assertTrue(service != null);

		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEPackage()));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		String message = ((NothingType)next).getMessage();
		assertEquals(argTypes.get(0) + " is not compatible with type " + argTypes.get(1), message);

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		next = it.next();
		assertTrue(next instanceof NothingType);
		String allTypesMesg = ((NothingType)next).getMessage();
		assertTrue(allTypesMesg.startsWith("Nothing will be left after calling oclAsType:"));
		assertTrue(allTypesMesg.endsWith(argTypes.get(0) + " is not compatible with type " + argTypes.get(
				1)));
	}

	@Test
	public void testOCLAsTypeIncompatibleTypesUnregistered() {
		final IService<?> service = serviceLookUp("oclAsType", new Object[] {EcorePackage.eINSTANCE
				.getEClass(), EcorePackage.eINSTANCE.getEPackage() });
		assertTrue(service != null);

		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEClass()));
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEPackage()));

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
				argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		String message = ((NothingType)next).getMessage();
		assertTrue(message.contains("EClass"));
		assertTrue(message.endsWith("is not registered within the current environment."));

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(1, types.size());
		it = types.iterator();
		next = it.next();
		assertTrue(next instanceof NothingType);
		String allTypesMesg = ((NothingType)next).getMessage();
		assertTrue(allTypesMesg.startsWith("Nothing will be left after calling oclAsType:"));
		assertTrue(allTypesMesg.contains("EClass"));
		assertTrue(allTypesMesg.endsWith("is not registered within the current environment."));
	}

	@Test
	public void testToStringPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "toString", parameterTypes);
	}

	@Test
	public void testToString() {
		final IType[] parameterTypes = new IType[] {classType(Boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "toString", parameterTypes);
	}

}
