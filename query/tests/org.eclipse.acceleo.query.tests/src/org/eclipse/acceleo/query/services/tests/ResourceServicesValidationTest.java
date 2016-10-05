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
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.ResourceServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the resource-related services' validation.
 */
public class ResourceServicesValidationTest extends AbstractServicesTest {
	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService> services = ServiceUtils
				.getServices(getQueryEnvironment(), ResourceServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void testEResource() {
		final IService service = serviceLookUp("eResource", new Object[] {EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
					argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), Resource.class), it.next());
			assertFalse(it.hasNext());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), Resource.class), it.next());
			assertFalse(it.hasNext());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testGetURI() {
		final IService service = serviceLookUp("getURI", new Object[] {EcorePackage.eINSTANCE.eResource() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEResource()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
					argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), URI.class), it.next());
			assertFalse(it.hasNext());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), URI.class), it.next());
			assertFalse(it.hasNext());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testGetContents() {
		final IService service = serviceLookUp("getContents", new Object[] {EcorePackage.eINSTANCE
				.eResource() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEResource()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
					argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
			assertFalse(it.hasNext());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
			assertFalse(it.hasNext());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testGetContentsFiltered() {
		final IService service = serviceLookUp("getContents", new Object[] {
				EcorePackage.eINSTANCE.eResource(), EcorePackage.eINSTANCE.getEPackage() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEResource()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
					argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertFalse(it.hasNext());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testLastSegment() {
		final IService service = serviceLookUp("lastSegment", new Object[] {URI.createFileURI("") });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), URI.class));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
					argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
			assertFalse(it.hasNext());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
			assertFalse(it.hasNext());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testFileExtension() {
		final IService service = serviceLookUp("fileExtension", new Object[] {URI.createFileURI("") });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), URI.class));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
					argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
			assertFalse(it.hasNext());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
			assertFalse(it.hasNext());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testIsPlatformResource() {
		final IService service = serviceLookUp("isPlatformResource", new Object[] {URI.createFileURI("") });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), URI.class));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
					argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
			assertFalse(it.hasNext());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
			assertFalse(it.hasNext());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testIsPlatformPlugin() {
		final IService service = serviceLookUp("isPlatformPlugin", new Object[] {URI.createFileURI("") });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new ClassType(getQueryEnvironment(), URI.class));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(),
					argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
			assertFalse(it.hasNext());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
			assertFalse(it.hasNext());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}
}
