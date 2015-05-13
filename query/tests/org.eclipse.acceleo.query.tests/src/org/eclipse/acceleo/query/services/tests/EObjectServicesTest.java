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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * EObject services tests.
 * 
 * @author pguilet
 */
public class EObjectServicesTest extends AbstractEngineInitializationWithCrossReferencer {
	public EObjectServices eObjectServices;

	public Resource reverseModel;

	@Before
	public void setup() throws URISyntaxException, IOException {
		this.eObjectServices = new EObjectServices();
		this.reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
	}

	@Test
	public void testEInverse() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);
		ILookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(clazz2)
				.getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(clazz2);
		assertEquals("Unexpected count of inverse references returned", 2, inversedSequence.size());

		final Iterator<EObject> children = inversedSequence.iterator();
		assertEquals("The first inverse reference on the second EClass should have been the first EClass",
				clazz, children.next());
		assertTrue("The second inverse reference on the second EClass should have been a GenericType",
				children.next() instanceof EGenericType);

	}

	@Test
	public void testEInverseNullReceiver() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);
		ILookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(clazz2)
				.getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(null);
		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFilter() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);
		ILookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(clazz2)
				.getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(clazz2, EcorePackage.eINSTANCE
				.getEGenericType());

		assertEquals("Unexpected count of inverse references returned", 1, inversedSequence.size());
		final Iterator<EObject> children = inversedSequence.iterator();
		assertTrue("The inverse reference on the second EClass should have been a GenericType", children
				.next() instanceof EGenericType);

	}

	@Test
	public void testEInverseWithFilterNullReceiver() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);
		ILookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(clazz2)
				.getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(null, EcorePackage.eINSTANCE
				.getEGenericType());

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFilterNullFilter() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);
		ILookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(clazz2)
				.getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(clazz2, (EClassifier)null);

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFeature() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);
		ILookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(clazz2)
				.getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(clazz2, "eRawType");

		assertEquals("Unexpected count of inverse references returned", 1, inversedSequence.size());
		final Iterator<EObject> children = inversedSequence.iterator();
		assertTrue("The inverse reference on the second EClass should have been a GenericType", children
				.next() instanceof EGenericType);

	}

	@Test
	public void testEInverseWithFeatureNullReceiver() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);
		ILookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(clazz2)
				.getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(null, "eRawType");

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFeatureNullFeatureName() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);
		ILookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(clazz2)
				.getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(clazz2, (String)null);

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFeatureNotAFeature() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);
		ILookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(clazz2)
				.getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(clazz2, "notAFeaure");

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	/**
	 * Tests the {@link EObjectServices#eClass(EObject)} method. This test uses the
	 * "resources/ecore/reverse.ecore" model.</br> It ensures that the {@link EObjectServices#eClass(EObject)}
	 * returns the eClass of an eObject.
	 */
	@Test
	public void testEClass() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();
		EObject resultExpectation = query.eAllContents().next();
		EObject eObjectListResult = resultExpectation.eAllContents().next();

		assertEquals(queries.eClass(), eObjectServices.eClass(queries));
		assertEquals(query.eClass(), eObjectServices.eClass(query));
		assertEquals(resultExpectation.eClass(), eObjectServices.eClass(resultExpectation));
		assertEquals(eObjectListResult.eClass(), eObjectServices.eClass(eObjectListResult));

		try {
			eObjectServices.eClass(null);
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link EObjectServices#eContainer(EObject, EClass)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model to test if the eContainer is calculated correctly.
	 * <ul>
	 * <li>For the EClass "unused" it must return the EPackage "full-sirius-code" as container.</li>
	 * <li>For the EAttribute "newEReference1" it must return the EClass "unused" as container if the filter
	 * is the EClass.</li>
	 * <li>For the EAttribute "newEReference1" it must return the EPackage "full-sirius-code" as container if
	 * the filter is the EPackage.</li>
	 * </ul>
	 */
	@Test
	public void testEContainer() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		assertEquals(fullSiriusCodePackage, eObjectServices
				.eContainer(unused, fullSiriusCodePackage.eClass()));

		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();

		assertEquals(unused, eObjectServices.eContainer(newEReference1, unused.eClass()));
		assertEquals(fullSiriusCodePackage, eObjectServices.eContainer(newEReference1, fullSiriusCodePackage
				.eClass()));

		try {
			eObjectServices.eContainer(newEReference1, null);
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}

		try {
			eObjectServices.eContainer(null, unused.eClass());
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link EObjectServices#eContainerOrSelf(EObject, EClass)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model to test if the eContainerOrSelf is calculated correctly.
	 * <ul>
	 * <li>For the EClass "unused" it must return the EPackage "full-sirius-code" as container.</li>
	 * <li>For the EAttribute "newEReference1" it must return the EClass "unused" as container if the filter
	 * is the EClass.</li>
	 * <li>For the EAttribute "newEReference1" it must return the EPackage "full-sirius-code" as container if
	 * the filter is the EPackage.</li>
	 * </ul>
	 */
	@Test
	public void testEContainerOrSelf() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		assertEquals(fullSiriusCodePackage, eObjectServices.eContainerOrSelf(unused, fullSiriusCodePackage
				.eClass()));

		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();

		assertEquals(unused, eObjectServices.eContainerOrSelf(newEReference1, unused.eClass()));
		assertEquals(fullSiriusCodePackage, eObjectServices.eContainerOrSelf(newEReference1,
				fullSiriusCodePackage.eClass()));

		try {
			eObjectServices.eContainerOrSelf(newEReference1, null);
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}

		try {
			eObjectServices.eContainerOrSelf(null, unused.eClass());
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link EObjectServices#eContainer(EObject)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model to test if the eContainer is calculated correctly.
	 * <ul>
	 * <li>For the EClass "unused" it must return the EPackage "full-sirius-code" as container.</li>
	 * <li>For the EAttribute "newEReference1" it must return the EClass "unused" as container.</li>
	 * </ul>
	 */
	@Test
	public void testEContainerNoEClass() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();

		assertEquals(fullSiriusCodePackage, eObjectServices.eContainer(unused));
		assertEquals(unused, eObjectServices.eContainer(newEReference1));

		try {
			eObjectServices.eContainer(null);
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link EObjectServices#eContents(EObject)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model to test if the eContents list is calculated correctly.
	 */
	@Test
	public void testEContentsNoEClassFilter() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();

		assertEquals(unused.eContents(), eObjectServices.eContents(unused));
		assertEquals(fullSiriusCodePackage.eContents(), eObjectServices.eContents(fullSiriusCodePackage));
		assertEquals(newEReference1.eContents(), eObjectServices.eContents(newEReference1));

		try {
			eObjectServices.eContents(null);
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link EObjectServices#eContents(EObject, EClass)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model to test if the eContents list is calculated correctly.
	 */
	@Test
	public void testEContents() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();

		List<EObject> contents = eObjectServices.eContents(fullSiriusCodePackage, unused.eClass());
		assertEquals(31, contents.size());
		contents = eObjectServices.eContents(fullSiriusCodePackage, newEReference1.eClass());
		assertEquals(0, contents.size());
		contents = eObjectServices.eContents(unused, newEReference1.eClass());
		assertEquals(1, contents.size());
		try {
			eObjectServices.eContents(null);
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	private int eAllContentSize(EObject eObject) {
		int result = 0;
		TreeIterator<EObject> eAllContents = eObject.eAllContents();
		while (eAllContents.hasNext()) {
			result++;
			eAllContents.next();
		}
		return result;
	}

	/**
	 * Tests {@link EObjectServices#eAllContents(EObject)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model to test if the eAllContents list is calculated correctly.
	 */
	@Test
	public void testEAllContentsNoEClassFilter() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();

		assertEquals(eAllContentSize(fullSiriusCodePackage), eObjectServices.eAllContents(
				fullSiriusCodePackage).size());
		assertEquals(eAllContentSize(unused), eObjectServices.eAllContents(unused).size());
		assertEquals(1, eObjectServices.eAllContents(newEReference1).size());

		try {
			eObjectServices.eAllContents(null);
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link EObjectServices#eAllContents(EObject, EClass)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model to test if the eAllContents list is calculated correctly.
	 */
	@Test
	public void testEAllContents() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();

		List<EObject> contents = eObjectServices.eAllContents(fullSiriusCodePackage, unused.eClass());
		assertEquals(31, contents.size());
		contents = eObjectServices.eAllContents(fullSiriusCodePackage, newEReference1.eClass());
		assertEquals(31, contents.size());
		contents = eObjectServices.eAllContents(unused, newEReference1.eClass());
		assertEquals(1, contents.size());
		try {
			eObjectServices.eAllContents(null);
			fail("The 'eClass' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * * Tests {@link EObjectServices#eGet(EObject, String)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the
	 * {@link EObjectServices#eGet(EObject, String)} returns the expected feature value.
	 * <ul>
	 * We expect to get:
	 * <li>for an instance of "Queries" with "name" feature filter: the eGet must return null because the
	 * concerned feature is not specified in the model.</li>
	 * <li>for an instance of "Query" with "name" feature filter: the eGet must return null because the
	 * concerned feature does not exist for a "Query".</li>
	 * <li>for an instance of "Query" with "expression" feature filter: the eGet must return the value of the
	 * "expression" feature in the model = "[self/]".</li>
	 * <li>for an instance of "EObjectListResult" with "query" feature filter: the eGet must return the value
	 * of the "query" feature in the model = the container query.</li>
	 * <li>for an instance of "Queries" with "null" feature filter: the eGet must return null.</li>
	 * <li>for a "null" EObject with "query" feature filter: the eGet must throw a NPE.</li>
	 * </ul>
	 */
	@Test
	public void testEGet() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();
		EObject resultExpectation = query.eAllContents().next();
		EObject eObjectListResult = resultExpectation.eAllContents().next();

		// The queries "name" feature exists but in the current model it is not
		// specified
		assertEquals(null, eObjectServices.eGet(queries, "name"));
		// The query "name" feature does not exist
		assertEquals(null, eObjectServices.eGet(query, "name"));

		// The query "expression" feature does not exist
		assertEquals("[self/]", eObjectServices.eGet(query, "expression"));
		// The eObjectListResult "Query" feature does not exist
		assertEquals(null, eObjectServices.eGet(eObjectListResult, "query"));

		assertEquals(null, eObjectServices.eGet(queries, null));

		try {
			eObjectServices.eGet(null, "query");
			fail("The 'eGet' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}
}
