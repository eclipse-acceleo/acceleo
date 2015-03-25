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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.LambdaType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectionServicesValidationTest extends AbstractServicesTest {

	@Override
	public void before() throws Exception {
		super.before();
		getQueryEnvironment().registerServicePackage(CollectionServices.class);
	}

	@Test
	public void testAddListNothingString() {
		final IService service = serviceLookUp("add", new Object[] {new ArrayList<Void>(),
				new ArrayList<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		final NothingType nothingType = new NothingType("Empty");
		argTypes.add(new SequenceType(getQueryEnvironment(), nothingType));
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), nothingType), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		final Set<IType> validatedTypes = service.validateAllType(getValidationServices(),
				getQueryEnvironment(), allTypes);
		assertEquals(1, validatedTypes.size());
		it = validatedTypes.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
	}

	@Test
	public void testAddListStringNothing() {
		final IService service = serviceLookUp("add", new Object[] {new ArrayList<String>(),
				new ArrayList<Void>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		final NothingType nothingType = new NothingType("Empty");
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new SequenceType(getQueryEnvironment(), nothingType));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), nothingType), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		final Set<IType> validatedTypes = service.validateAllType(getValidationServices(),
				getQueryEnvironment(), allTypes);
		assertEquals(1, validatedTypes.size());
		it = validatedTypes.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
	}

	@Test
	public void testAddList() {
		final IService service = serviceLookUp("add", new Object[] {new ArrayList<String>(),
				new ArrayList<Integer>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testAddSetNothingString() {
		final IService service = serviceLookUp("add", new Object[] {new LinkedHashSet<Void>(),
				new LinkedHashSet<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		final NothingType nothingType = new NothingType("Empty");
		argTypes.add(new SetType(getQueryEnvironment(), nothingType));
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), nothingType), it.next());
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		final Set<IType> validatedTypes = service.validateAllType(getValidationServices(),
				getQueryEnvironment(), allTypes);
		assertEquals(1, validatedTypes.size());
		it = validatedTypes.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
	}

	@Test
	public void testAddSetStringNothing() {
		final IService service = serviceLookUp("add", new Object[] {new LinkedHashSet<String>(),
				new LinkedHashSet<Void>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		final NothingType nothingType = new NothingType("Empty");
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new SetType(getQueryEnvironment(), nothingType));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
		assertEquals(new SetType(getQueryEnvironment(), nothingType), it.next());

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(argTypes, types);
		final Set<IType> validatedTypes = service.validateAllType(getValidationServices(),
				getQueryEnvironment(), allTypes);
		assertEquals(1, validatedTypes.size());
		it = validatedTypes.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
	}

	@Test
	public void testAddSet() {
		final IService service = serviceLookUp("add", new Object[] {new LinkedHashSet<String>(),
				new LinkedHashSet<Integer>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), Integer.class)),
				it.next());
	}

	@Test
	public void testAnyNoBooleanLambda() {
		final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
		final IService service = serviceLookUp("any", new Object[] {new ArrayList<String>(), lambda });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new LambdaType(getQueryEnvironment(),
				new ClassType(getQueryEnvironment(), String.class), new ClassType(getQueryEnvironment(),
						Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		assertEquals("expression in an any must return a boolean", ((NothingType)next).getMessage());
	}

	@Test
	public void testAnySet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
			final IService service = serviceLookUp("any", new Object[] {new LinkedHashSet<String>(), lambda });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SetType(getQueryEnvironment(),
					new ClassType(getQueryEnvironment(), String.class)));
			argTypes.add(new LambdaType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class), new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBoolean())));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testAnyList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
			final IService service = serviceLookUp("any", new Object[] {new ArrayList<String>(), lambda });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class)));
			argTypes.add(new LambdaType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class), new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBoolean())));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testAsOrderedSetSet() {
		final IService service = serviceLookUp("asOrderedSet", new Object[] {new LinkedHashSet<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
	}

	@Test
	public void testAsOrderedSetList() {
		final IService service = serviceLookUp("asOrderedSet", new Object[] {new ArrayList<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
	}

	@Test
	public void testAsSequenceSet() {
		final IService service = serviceLookUp("asSequence", new Object[] {new LinkedHashSet<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
	}

	@Test
	public void testAsSequenceList() {
		final IService service = serviceLookUp("asSequence", new Object[] {new ArrayList<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
	}

	@Test
	public void testAsSetSet() {
		final IService service = serviceLookUp("asSet", new Object[] {new LinkedHashSet<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
	}

	@Test
	public void testAsSetList() {
		final IService service = serviceLookUp("asSet", new Object[] {new ArrayList<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
	}

	@Test
	public void testAt() {
		final IService service = serviceLookUp("at", new Object[] {new ArrayList<String>(),
				Integer.valueOf(1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testCollectSet() {
		final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
		final IService service = serviceLookUp("collect", new Object[] {new LinkedHashSet<String>(), lambda });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new LambdaType(getQueryEnvironment(),
				new ClassType(getQueryEnvironment(), String.class), new ClassType(getQueryEnvironment(),
						Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), Integer.class)),
				it.next());
	}

	@Test
	public void testCollectList() {
		final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
		final IService service = serviceLookUp("collect", new Object[] {new ArrayList<String>(), lambda });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new LambdaType(getQueryEnvironment(),
				new ClassType(getQueryEnvironment(), String.class), new ClassType(getQueryEnvironment(),
						Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testConcat() {
		final IService service = serviceLookUp("concat", new Object[] {new ArrayList<String>(),
				new ArrayList<Integer>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testCountSet() {
		final IService service = serviceLookUp("count", new Object[] {new LinkedHashSet<String>(),
				Integer.valueOf(1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testCountList() {
		final IService service = serviceLookUp("count", new Object[] {new ArrayList<String>(),
				Integer.valueOf(1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testExcludesSet() {
		final IService service = serviceLookUp("excludes", new Object[] {new LinkedHashSet<String>(),
				Integer.valueOf(1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testExcludesList() {
		final IService service = serviceLookUp("excludes", new Object[] {new ArrayList<String>(),
				Integer.valueOf(1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testIncludesSet() {
		final IService service = serviceLookUp("includes", new Object[] {new LinkedHashSet<String>(),
				Integer.valueOf(1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testIncludesList() {
		final IService service = serviceLookUp("includes", new Object[] {new ArrayList<String>(),
				Integer.valueOf(1) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testIncludingList() {
		final IService service = serviceLookUp("including", new Object[] {new ArrayList<String>(),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testIncludingSet() {
		final IService service = serviceLookUp("including", new Object[] {new LinkedHashSet<String>(),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), Integer.class)),
				it.next());
	}

	@Test
	public void testIndexOf() {
		final IService service = serviceLookUp("indexOf", new Object[] {new ArrayList<String>(),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testInsertAt() {
		final IService service = serviceLookUp("insertAt", new Object[] {new ArrayList<String>(),
				Integer.valueOf(0), Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Double.class)), it.next());
	}

	@Test
	public void testIntersectionNothingLeft() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IService service = serviceLookUp("intersection", new Object[] {new LinkedHashSet<String>(),
					new LinkedHashSet<Integer>() });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(), eCls1)));
			argTypes.add(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(), eCls2)));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SetType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof NothingType);
			assertEquals(
					"Nothing left after intersection of Set(EClassifier=eCls1) and Set(EClassifier=eCls2)",
					((NothingType)((ICollectionType)next).getCollectionType()).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testIntersectionTopSubType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);
		final EClass eCls3 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls3.setName("eCls3");
		eCls3.getESuperTypes().add(eCls1);
		eCls3.getESuperTypes().add(eCls2);
		ePkg.getEClassifiers().add(eCls3);
		final EClass eCls4 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls4.setName("eCls4");
		eCls4.getESuperTypes().add(eCls1);
		eCls4.getESuperTypes().add(eCls2);
		eCls4.getESuperTypes().add(eCls3);
		ePkg.getEClassifiers().add(eCls4);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IService service = serviceLookUp("intersection", new Object[] {new LinkedHashSet<String>(),
					new LinkedHashSet<Integer>() });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(), eCls1)));
			argTypes.add(new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(), eCls2)));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType next = it.next();
			assertTrue(next instanceof SetType);
			assertTrue(((ICollectionType)next).getCollectionType() instanceof EClassifierType);
			assertEquals(eCls3, ((ICollectionType)next).getCollectionType().getType());
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testIntersectionSameType() {
		final IService service = serviceLookUp("intersection", new Object[] {new LinkedHashSet<String>(),
				new LinkedHashSet<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
	}

	@Test
	public void testIntersectionEClassEClassifier() {
		final IService service = serviceLookUp("intersection", new Object[] {new LinkedHashSet<EClass>(),
				new LinkedHashSet<EClassifier>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), EClass.class)));
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				EClassifier.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), EClass.class)),
				it.next());
	}

	@Test
	public void testIntersectionEClassifierEClass() {
		final IService service = serviceLookUp("intersection", new Object[] {
				new LinkedHashSet<EClassifier>(), new LinkedHashSet<EClass>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				EClassifier.class)));
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), EClass.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), EClass.class)),
				it.next());
	}

	@Test
	public void testIsEmptyList() {
		final IService service = serviceLookUp("isEmpty", new Object[] {new ArrayList<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testIsEmptySet() {
		final IService service = serviceLookUp("isEmpty", new Object[] {new LinkedHashSet<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testIsUniqueSet() {
		final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
		final IService service = serviceLookUp("isUnique",
				new Object[] {new LinkedHashSet<String>(), lambda });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new LambdaType(getQueryEnvironment(),
				new ClassType(getQueryEnvironment(), String.class), new EClassifierType(
						getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass())));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testIsUniqueList() {
		final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
		final IService service = serviceLookUp("isUnique", new Object[] {new ArrayList<String>(), lambda });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new LambdaType(getQueryEnvironment(),
				new ClassType(getQueryEnvironment(), String.class), new EClassifierType(
						getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass())));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testLast() {
		final IService service = serviceLookUp("last", new Object[] {new ArrayList<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), String.class), it.next());
	}

	@Test
	public void testNotEmptyList() {
		final IService service = serviceLookUp("notEmpty", new Object[] {new ArrayList<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testNotEmptySet() {
		final IService service = serviceLookUp("notEmpty", new Object[] {new LinkedHashSet<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Boolean.class), it.next());
	}

	@Test
	public void testOneNoBooleanLambda() {
		final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
		final IService service = serviceLookUp("one", new Object[] {new ArrayList<String>(), lambda });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new LambdaType(getQueryEnvironment(),
				new ClassType(getQueryEnvironment(), String.class), new ClassType(getQueryEnvironment(),
						Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		assertEquals("expression in one must return a boolean", ((NothingType)next).getMessage());
	}

	@Test
	public void testOneSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
			final IService service = serviceLookUp("one", new Object[] {new LinkedHashSet<String>(), lambda });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SetType(getQueryEnvironment(),
					new ClassType(getQueryEnvironment(), String.class)));
			argTypes.add(new LambdaType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class), new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBoolean())));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBooleanObject()), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testOneList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
			final IService service = serviceLookUp("one", new Object[] {new ArrayList<String>(), lambda });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class)));
			argTypes.add(new LambdaType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class), new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBoolean())));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBooleanObject()), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testPrepend() {
		final IService service = serviceLookUp("prepend", new Object[] {new ArrayList<String>(),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testRejectNoBooleanLambda() {
		final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
		final IService service = serviceLookUp("reject", new Object[] {new ArrayList<String>(), lambda });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new LambdaType(getQueryEnvironment(),
				new ClassType(getQueryEnvironment(), String.class), new ClassType(getQueryEnvironment(),
						Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		assertEquals("expression in a reject must return a boolean", ((NothingType)next).getMessage());
	}

	@Test
	public void testRejectSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
			final IService service = serviceLookUp("reject", new Object[] {new LinkedHashSet<String>(),
					lambda });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SetType(getQueryEnvironment(),
					new ClassType(getQueryEnvironment(), String.class)));
			argTypes.add(new LambdaType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class), new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBoolean())));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(),
					new ClassType(getQueryEnvironment(), String.class)), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testRejectList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
			final IService service = serviceLookUp("reject", new Object[] {new ArrayList<String>(), lambda });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class)));
			argTypes.add(new LambdaType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class), new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBoolean())));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class)), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testSelectNoBooleanLambda() {
		final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
		final IService service = serviceLookUp("select", new Object[] {new ArrayList<String>(), lambda });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new LambdaType(getQueryEnvironment(),
				new ClassType(getQueryEnvironment(), String.class), new ClassType(getQueryEnvironment(),
						Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		IType next = it.next();
		assertTrue(next instanceof NothingType);
		assertEquals("expression in a select must return a boolean", ((NothingType)next).getMessage());
	}

	@Test
	public void testSelectSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
			final IService service = serviceLookUp("select", new Object[] {new LinkedHashSet<String>(),
					lambda });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SetType(getQueryEnvironment(),
					new ClassType(getQueryEnvironment(), String.class)));
			argTypes.add(new LambdaType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class), new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBoolean())));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SetType(getQueryEnvironment(),
					new ClassType(getQueryEnvironment(), String.class)), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testSelectList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Lambda lambda = AstPackage.eINSTANCE.getAstFactory().createLambda();
			final IService service = serviceLookUp("select", new Object[] {new ArrayList<String>(), lambda });
			assertTrue(service != null);
			final List<IType> argTypes = new ArrayList<IType>();
			argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class)));
			argTypes.add(new LambdaType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class), new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
					.getEBoolean())));

			final Set<IType> types = service
					.getType(getValidationServices(), getQueryEnvironment(), argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
					String.class)), it.next());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testSep2List() {
		final IService service = serviceLookUp("sep", new Object[] {new ArrayList<String>(),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testSep2Set() {
		final IService service = serviceLookUp("sep", new Object[] {new LinkedHashSet<String>(),
				Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testSep4List() {
		final IService service = serviceLookUp("sep", new Object[] {new ArrayList<String>(),
				Integer.valueOf(0), "", Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(3, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testSep4Set() {
		final IService service = serviceLookUp("sep", new Object[] {new LinkedHashSet<String>(),
				Integer.valueOf(0), "", Double.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));
		argTypes.add(new ClassType(getQueryEnvironment(), String.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Double.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(3, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testSizeList() {
		final IService service = serviceLookUp("size", new Object[] {new ArrayList<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testSizeSet() {
		final IService service = serviceLookUp("size", new Object[] {new LinkedHashSet<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Integer.class), it.next());
	}

	@Test
	public void testSubList() {
		final IService service = serviceLookUp("sub", new Object[] {new ArrayList<String>(),
				new ArrayList<Integer>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
	}

	@Test
	public void testSubSet() {
		final IService service = serviceLookUp("sub", new Object[] {new LinkedHashSet<String>(),
				new LinkedHashSet<Integer>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
	}

	@Test
	public void testSubOrderedSet() {
		final IService service = serviceLookUp("subOrderedSet", new Object[] {new LinkedHashSet<String>(),
				Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
	}

	@Test
	public void testSubSequence() {
		final IService service = serviceLookUp("subSequence", new Object[] {new ArrayList<String>(),
				Integer.valueOf(0), Integer.valueOf(0) });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));
		argTypes.add(new ClassType(getQueryEnvironment(), Integer.class));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
	}

	@Test
	public void testSumList() {
		final IService service = serviceLookUp("sum", new Object[] {new ArrayList<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Double.class), it.next());
	}

	@Test
	public void testSumSet() {
		final IService service = serviceLookUp("sum", new Object[] {new LinkedHashSet<String>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(1, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new ClassType(getQueryEnvironment(), Double.class), it.next());
	}

	@Test
	public void testUnionList() {
		final IService service = serviceLookUp("union", new Object[] {new ArrayList<String>(),
				new ArrayList<Integer>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)));
		argTypes.add(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				String.class)), it.next());
		assertEquals(new SequenceType(getQueryEnvironment(), new ClassType(getQueryEnvironment(),
				Integer.class)), it.next());
	}

	@Test
	public void testUnionSet() {
		final IService service = serviceLookUp("union", new Object[] {new LinkedHashSet<String>(),
				new LinkedHashSet<Integer>() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)));
		argTypes.add(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), Integer.class)));

		final Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), argTypes);
		assertEquals(2, types.size());
		Iterator<IType> it = types.iterator();
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), String.class)),
				it.next());
		assertEquals(new SetType(getQueryEnvironment(), new ClassType(getQueryEnvironment(), Integer.class)),
				it.next());
	}

}
