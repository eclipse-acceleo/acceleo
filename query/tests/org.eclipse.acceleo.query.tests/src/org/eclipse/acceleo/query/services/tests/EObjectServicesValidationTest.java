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
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * EObject services validation tests.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EObjectServicesValidationTest extends AbstractServicesTest {

	@Override
	public void before() throws Exception {
		super.before();
		getQueryEnvironment().registerServicePackage(EObjectServices.class);
	}

	@Test
	public void testAncestorsNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("ancestors", new Object[] {EcoreUtil.create(eCls1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't be contained", ((NothingType)((ICollectionType)next)
					.getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't be contained", ((NothingType)((ICollectionType)next)
					.getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testAncestors() {
		final IService service = serviceLookUp("ancestors", new Object[] {EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(7, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEModelElement())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnum())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEOperation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(7, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEModelElement())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnum())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEOperation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testAncestorsOnEObject() {
		final IService service = serviceLookUp("ancestors", new Object[] {EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testAncestorsFilteredNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("ancestors", new Object[] {EcoreUtil.create(eCls2), eCls1 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls2));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling ancestors:\nEClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testAncestorsFiltered() {
		final IService service = serviceLookUp("ancestors", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testAncestorsFilteredOnEObject() {
		final IService service = serviceLookUp("ancestors", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testAncestorsFilteredLoweredByTypes() {
		final IService service = serviceLookUp("ancestors", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEModelElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getETypedElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(2, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getETypedElement())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEOperation())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(2, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getETypedElement())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEOperation())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testAncestorsFilteredLoweredByFilter() {
		final IService service = serviceLookUp("ancestors", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testAncestorsFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("ancestors", new Object[] {EcoreUtil.create(eCls1), eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling ancestors:\nEClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsNoContainingEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("eAllContents", new Object[] {EcoreUtil.create(eCls1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 doesn't contain any other EClass",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 doesn't contain any other EClass",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEAllContents() {
		final IService service = serviceLookUp("eAllContents", new Object[] {EcorePackage.eINSTANCE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(11, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStringToStringMapEntry())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getETypeParameter())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEOperation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStructuralFeature())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEGenericType())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnumLiteral())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEParameter())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(11, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStringToStringMapEntry())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getETypeParameter())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEOperation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStructuralFeature())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEGenericType())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnumLiteral())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEParameter())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsOnEObject() {
		final IService service = serviceLookUp("eAllContents", new Object[] {EcorePackage.eINSTANCE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFilteredNoContainingEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("eAllContents", new Object[] {EcoreUtil.create(eCls1), eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty or indirectly",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling eAllContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty or indirectly",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFiltered() {
		final IService service = serviceLookUp("eAllContents", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEPackage() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFilteredOnEObject() {
		final IService service = serviceLookUp("eAllContents", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEPackage() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFilteredLoweredByTypes() {
		final IService service = serviceLookUp("eAllContents", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEModelElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEModelElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(9, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEModelElement())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getETypeParameter())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEOperation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStructuralFeature())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnumLiteral())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEParameter())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(9, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEModelElement())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getETypeParameter())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEOperation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStructuralFeature())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnumLiteral())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEParameter())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFilteredLoweredByFilter() {
		final IService service = serviceLookUp("eAllContents", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("eAllContents", new Object[] {EcoreUtil.create(eCls1), eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty or indirectly",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling eAllContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty or indirectly",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEClass() {
		final IService service = serviceLookUp("eClass", new Object[] {EcorePackage.eINSTANCE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
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
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("eContainer", new Object[] {EcoreUtil.create(eCls1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't be contained", ((NothingType)next).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling eContainer:\nEClassifier=eCls1 can't be contained",
					((NothingType)next).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContainer() {
		final IService service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(2, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()), it
					.next());
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEAnnotation()),
					it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(2, types.size());
			it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()), it
					.next());
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEAnnotation()),
					it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	// TODO move to BasicLookupEngineTest see https://git.eclipse.org/r/#/c/44251/
	@Test
	public void testEContainerOnEStringToStringMapEntryBasicMapping() {
		final List<Set<IType>> argTypes = new ArrayList<Set<IType>>();
		argTypes.add(createTypeSet(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEStringToStringMapEntry())));
		argTypes.add(createTypeSet(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEAnnotation())));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Set<IType> types = getValidationServices().callType("eContainer", argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType type = it.next();
			assertTrue(type instanceof NothingType);
			assertEquals(
					"Couldn't find the eContainer(EClassifier=EStringToStringMapEntry,EClassifierLiteral=EAnnotation) service or EOperation",
					((NothingType)type).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	// TODO move to BasicLookupEngineTest see https://git.eclipse.org/r/#/c/44251/
	@Test
	public void testEContainerOnEStringToStringMapEntryCustomMapping() {
		final List<Set<IType>> argTypes = new ArrayList<Set<IType>>();
		argTypes.add(createTypeSet(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEStringToStringMapEntry())));
		argTypes.add(createTypeSet(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEAnnotation())));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			getQueryEnvironment().registerCustomClassMapping(
					EcorePackage.eINSTANCE.getEStringToStringMapEntry(), EStringToStringMapEntryImpl.class);
			final Set<IType> types = getValidationServices().callType("eContainer", argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEAnnotation()),
					it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerOnEObject() {
		final IService service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()), it
					.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()), it
					.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerFilteredNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("eContainer", new Object[] {EcoreUtil.create(eCls2), eCls1 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls2));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2",
					((NothingType)next).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling eContainer:\nEClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2",
					((NothingType)next).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContainerFiltered() {
		final IService service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()), it
					.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()), it
					.next());
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerFilteredOnEObject() {
		final IService service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
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
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerFilteredLoweredByTypes() {
		final IService service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEModelElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getETypedElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(2, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(
					new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getETypedElement()), it
							.next());
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEOperation()),
					it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(2, types.size());
			it = types.iterator();
			assertEquals(
					new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getETypedElement()), it
							.next());
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEOperation()),
					it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerFilteredLoweredByFilter() {
		final IService service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
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
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("eContainer", new Object[] {EcoreUtil.create(eCls1), eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1",
					((NothingType)next).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling eContainer:\nEClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1",
					((NothingType)next).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContentsNoContainingEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("eContents", new Object[] {EcoreUtil.create(eCls1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 doesn't contain any other EClass",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 doesn't contain any other EClass",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContents() {
		final IService service = serviceLookUp("eContents", new Object[] {EcorePackage.eINSTANCE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(3, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(3, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsOnEObject() {
		final IService service = serviceLookUp("eContents", new Object[] {EcorePackage.eINSTANCE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFilteredNoContainingEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("eContents", new Object[] {EcoreUtil.create(eCls1), eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling eContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFiltered() {
		final IService service = serviceLookUp("eContents", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEPackage() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFilteredOnEObject() {
		final IService service = serviceLookUp("eContents", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEPackage() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFilteredLoweredByTypes() {
		final IService service = serviceLookUp("eContents", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEModelElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEModelElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(3, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(3, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFilteredLoweredByFilter() {
		final IService service = serviceLookUp("eContents", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("eContents", new Object[] {EcoreUtil.create(eCls1), eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling eContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEGet() {
		final IService service = serviceLookUp("eGet", new Object[] {EcorePackage.eINSTANCE, "name" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEJavaObject()),
					it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEJavaObject()),
					it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverse() {
		final IService service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(4, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEFactory())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(4, types.size());
			it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEFactory())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFeatureName() {
		final IService service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE, "ePackage" });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(4, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEFactory())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(4, types.size());
			it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEFactory())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFiltered() {
		final IService service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFilteredLoweredByFilter() {
		final IService service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEEnum() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEEnum()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnum())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnum())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFilteredLoweredByTypes() {
		final IService service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEEnum() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEModelElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(4, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEFactory())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(4, types.size());
			it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEFactory())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseOnEObject() {
		final IService service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFilteredOnEObject() {
		final IService service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEEnum() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEEnum()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnum())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEEnum())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFilteredNoType() {
		final IService service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEEnum() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEOperation()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SetType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=EPackage don't have inverse to EClassifierLiteral=EOperation",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SetType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling eInverse:\nEClassifier=EPackage don't have inverse to EClassifierLiteral=EOperation",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testFollowingSiblingsNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("followingSiblings", new Object[] {EcoreUtil.create(eCls1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't have following siblings",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't have following siblings",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testFollowingSiblings() {
		final IService service = serviceLookUp("followingSiblings", new Object[] {EcorePackage.eINSTANCE
				.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(3, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(3, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testFollowingSiblingsOnEObject() {
		final IService service = serviceLookUp("followingSiblings", new Object[] {EcorePackage.eINSTANCE
				.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("followingSiblings", new Object[] {EcoreUtil.create(eCls2),
				eCls1 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls2));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls1 can't be a following sibling of EClassifier=eCls2",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling followingSiblings:\nEClassifierLiteral=eCls1 can't be a following sibling of EClassifier=eCls2",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testFollowingSiblingsFiltered() {
		final IService service = serviceLookUp("followingSiblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClassifier() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEClassifier()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredOnEObject() {
		final IService service = serviceLookUp("followingSiblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredLoweredByTypes() {
		final IService service = serviceLookUp("followingSiblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getENamedElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getENamedElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(2, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getENamedElement())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(2, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getENamedElement())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredLoweredByFilter() {
		final IService service = serviceLookUp("followingSiblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("followingSiblings", new Object[] {EcoreUtil.create(eCls1),
				eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls2 can't be a following sibling of EClassifier=eCls1",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling followingSiblings:\nEClassifierLiteral=eCls2 can't be a following sibling of EClassifier=eCls1",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testPrecedingSiblingsNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("precedingSiblings", new Object[] {EcoreUtil.create(eCls1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't have preceding siblings",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't have preceding siblings",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testPrecedingSiblings() {
		final IService service = serviceLookUp("precedingSiblings", new Object[] {EcorePackage.eINSTANCE
				.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(4, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStringToStringMapEntry())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(4, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStringToStringMapEntry())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testPrecedingSiblingsOnEObject() {
		final IService service = serviceLookUp("precedingSiblings", new Object[] {EcorePackage.eINSTANCE
				.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("precedingSiblings", new Object[] {EcoreUtil.create(eCls2),
				eCls1 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls2));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls1 can't be a preceding sibling of EClassifier=eCls2",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling precedingSiblings:\nEClassifierLiteral=eCls1 can't be a preceding sibling of EClassifier=eCls2",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testPrecedingSiblingsFiltered() {
		final IService service = serviceLookUp("precedingSiblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClassifier() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEClassifier()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredOnEObject() {
		final IService service = serviceLookUp("precedingSiblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredLoweredByTypes() {
		final IService service = serviceLookUp("precedingSiblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getENamedElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getENamedElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(3, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getENamedElement())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(3, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getENamedElement())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredLoweredByFilter() {
		final IService service = serviceLookUp("precedingSiblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("precedingSiblings", new Object[] {EcoreUtil.create(eCls1),
				eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls2 can't be a preceding sibling of EClassifier=eCls1",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling precedingSiblings:\nEClassifierLiteral=eCls2 can't be a preceding sibling of EClassifier=eCls1",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testSiblingsNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("siblings", new Object[] {EcoreUtil.create(eCls1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't have siblings", ((NothingType)((ICollectionType)next)
					.getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifier=eCls1 can't have siblings", ((NothingType)((ICollectionType)next)
					.getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testSiblings() {
		final IService service = serviceLookUp("siblings", new Object[] {EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(5, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStringToStringMapEntry())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(5, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEAnnotation())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEStringToStringMapEntry())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testSiblingsOnEObject() {
		final IService service = serviceLookUp("siblings", new Object[] {EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEObject())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testSiblingsFilteredNotContainedEClass() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("siblings", new Object[] {EcoreUtil.create(eCls2), eCls1 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls2));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls1));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls1 can't be a sibling of EClassifier=eCls2",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling siblings:\nEClassifierLiteral=eCls1 can't be a sibling of EClassifier=eCls2",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testSiblingsFiltered() {
		final IService service = serviceLookUp("siblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClassifier() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEClassifier()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testSiblingsFilteredOnEObject() {
		final IService service = serviceLookUp("siblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEObject()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testSiblingsFilteredLoweredByTypes() {
		final IService service = serviceLookUp("siblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getENamedElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getENamedElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(3, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getENamedElement())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(3, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClassifier())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEPackage())), it.next());
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getENamedElement())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testSiblingsFilteredLoweredByFilter() {
		final IService service = serviceLookUp("siblings", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
					EcorePackage.eINSTANCE.getEClass())), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testSiblingsFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		final IService service = serviceLookUp("siblings", new Object[] {EcoreUtil.create(eCls1), eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);
			Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals("EClassifierLiteral=eCls2 can't be a sibling of EClassifier=eCls1",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());

			final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
			allTypes.put(argTypes, types);
			types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
			assertEquals(1, types.size());
			it = types.iterator();
			next = it.next();
			assertTrue(next instanceof SequenceType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing will be left after calling siblings:\nEClassifierLiteral=eCls2 can't be a sibling of EClassifier=eCls1",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

}
