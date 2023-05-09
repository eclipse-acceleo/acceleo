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

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.eclipse.acceleo.query.runtime.RootEObjectProvider;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.acceleo.query.tests.anydsl.AnydslFactory;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.anydsl.Recipe;
import org.eclipse.acceleo.query.tests.anydsl.Restaurant;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.Queries;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * EObject services tests.
 * 
 * @author pguilet
 */
public class EObjectServicesTest extends AbstractEngineInitializationWithCrossReferencer {

	public Resource reverseModel;

	private EClass inverseTestModelClass1;

	private EClass inverseTestModelClass2;

	@Before
	public void before() throws Exception {
		super.before();
		this.reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
	}

	@Test
	public void testEInverse() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(inverseTestModelClass2);
		assertEquals("Unexpected count of inverse references returned", 2, inversedSequence.size());

		final Iterator<EObject> children = inversedSequence.iterator();
		assertEquals("The first inverse reference on the second EClass should have been the first EClass",
				inverseTestModelClass1, children.next());
		assertTrue("The second inverse reference on the second EClass should have been a GenericType",
				children.next() instanceof EGenericType);
	}

	@Test
	public void testEInverseNullReceiver() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(null);
		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFilter() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(inverseTestModelClass2,
				EcorePackage.eINSTANCE.getEGenericType());

		assertEquals("Unexpected count of inverse references returned", 1, inversedSequence.size());
		final Iterator<EObject> children = inversedSequence.iterator();
		assertTrue("The inverse reference on the second EClass should have been a GenericType", children
				.next() instanceof EGenericType);
	}

	@Test
	public void testEInverseWithFilterImplicitRoot() {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(inverseTestModelClass2,
				EcorePackage.eINSTANCE.getEObject());

		// We expect the same result as the unfiltered eInverse call
		assertEquals("Unexpected count of inverse references returned", 2, inversedSequence.size());
		final Iterator<EObject> children = inversedSequence.iterator();
		assertEquals("The first inverse reference on the second EClass should have been the first EClass",
				inverseTestModelClass1, children.next());
		assertTrue("The second inverse reference on the second EClass should have been a GenericType",
				children.next() instanceof EGenericType);
	}

	@Test
	public void testEInverseWithFilterNullReceiver() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(null, EcorePackage.eINSTANCE
				.getEGenericType());

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFilterNullFilter() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(inverseTestModelClass2, (EClassifier)null);

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFeature() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(inverseTestModelClass2, "eRawType");

		assertEquals("Unexpected count of inverse references returned", 1, inversedSequence.size());
		final Iterator<EObject> children = inversedSequence.iterator();
		assertTrue("The inverse reference on the second EClass should have been a GenericType", children
				.next() instanceof EGenericType);

	}

	@Test
	public void testEInverseWithFeatureNullReceiver() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(null, "eRawType");

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFeatureNullFeatureName() throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(inverseTestModelClass2, (String)null);

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	@Test
	public void testEInverseWithFeatureNotAFeature() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		createModelForInverseTests();
		setQueryEnvironnementWithCrossReferencer(inverseTestModelClass2);
		final EObjectServices eObjectServices = new EObjectServices(queryEnvironment, crossReferencer, null);

		Set<EObject> inversedSequence = eObjectServices.eInverse(inverseTestModelClass2, "notAFeaure");

		assertEquals("Unexpected count of inverse references returned", 0, inversedSequence.size());
	}

	private void createModelForInverseTests() {
		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		inverseTestModelClass1 = EcoreFactory.eINSTANCE.createEClass();
		inverseTestModelClass2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		inverseTestModelClass1.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(inverseTestModelClass1);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(inverseTestModelClass2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		inverseTestModelClass1.getESuperTypes().add(inverseTestModelClass2);
	}

	/**
	 * Tests the {@link EObjectServices#eClass(EObject)} method. This test uses the
	 * "resources/ecore/reverse.ecore" model.</br>
	 * It ensures that the {@link EObjectServices#eClass(EObject)} returns the eClass of an eObject.
	 */
	@Test
	public void testEClass() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();
		EObject resultExpectation = query.eAllContents().next();
		EObject eObjectListResult = resultExpectation.eAllContents().next();
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

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
	 * Tests {@link EObjectServices#eContainer(EObject, EClass)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model to test if the eContainer is calculated
	 * correctly.
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
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		assertEquals(fullSiriusCodePackage, eObjectServices.eContainer(unused, fullSiriusCodePackage
				.eClass()));

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
	 * Tests {@link EObjectServices#eContainerOrSelf(EObject, EClass)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model to test if the eContainerOrSelf is calculated
	 * correctly.
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
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

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
	 * Tests {@link EObjectServices#eContainer(EObject)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model to test if the eContainer is calculated
	 * correctly.
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
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

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
	 * Tests {@link EObjectServices#eContents(EObject)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model to test if the eContents list is calculated
	 * correctly.
	 */
	@Test
	public void testFilteredEContentsNoEClassFilter() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		assertEquals(unused.eContents(), eObjectServices.eContents(unused));
		assertEquals(fullSiriusCodePackage.eContents(), eObjectServices.eContents(fullSiriusCodePackage));
		assertEquals(newEReference1.eContents(), eObjectServices.eContents(newEReference1));

		try {
			eObjectServices.eContents(null);
			fail("The 'eContents' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	/**
	 * Tests {@link EObjectServices#eContents(EObject, EClass)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model to test if the eContents list is calculated
	 * correctly.
	 */
	@Test
	public void testFilteredEContents() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		getQueryEnvironment().registerEPackage(QmodelPackage.eINSTANCE);
		List<EObject> contents = eObjectServices.eContents(fullSiriusCodePackage, unused.eClass());
		assertEquals(31, contents.size());
		contents = eObjectServices.eContents(fullSiriusCodePackage, newEReference1.eClass());
		assertEquals(0, contents.size());
		contents = eObjectServices.eContents(unused, newEReference1.eClass());
		assertEquals(1, contents.size());
		try {
			eObjectServices.eContents(null);
			fail("The 'eContents' operation service must throw a NPE.");
		} catch (NullPointerException exception) {
			// Do nothing the exception is expected
		}
	}

	@Test
	public void testEContents() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);

		getQueryEnvironment().registerEPackage(QmodelPackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		List<EObject> contents = eObjectServices.eContents(fullSiriusCodePackage);
		assertEquals(fullSiriusCodePackage.eContents(), contents);

		// make sure we return a view of the eObject's content
		contents.clear();
		assertFalse(eObjectServices.eContents(fullSiriusCodePackage).isEmpty());
	}

	@Test(expected = NullPointerException.class)
	public void testEContentsNullReceiver() {
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		eObjectServices.eContents(null);
	}

	@Test
	public void testEContentsNullAndNullTypeEClassifierSet() {
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);
		final List<EObject> contents = eObjectServices.eContents((EObject)null, (Set<EClass>)null);

		assertEquals(0, contents.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testEContentsNullTypeEClassifierSet() {
		final LinkedHashSet<EClass> types = new LinkedHashSet<EClass>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		eObjectServices.eContents(null, types);
	}

	@Test
	public void testEContentsEObjectTypeEClassifierSet() {
		final LinkedHashSet<EClass> types = new LinkedHashSet<EClass>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final List<EObject> contents = eObjectServices.eContents(EcorePackage.eINSTANCE, types);

		assertEquals(20, contents.size());
		assertEquals(EcorePackage.eINSTANCE.getEAttribute(), contents.get(0));
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), contents.get(1));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), contents.get(2));
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), contents.get(3));
		assertEquals(EcorePackage.eINSTANCE.getEDataType(), contents.get(4));
		assertEquals(EcorePackage.eINSTANCE.getEEnum(), contents.get(5));
		assertEquals(EcorePackage.eINSTANCE.getEEnumLiteral(), contents.get(6));
		assertEquals(EcorePackage.eINSTANCE.getEFactory(), contents.get(7));
		assertEquals(EcorePackage.eINSTANCE.getEModelElement(), contents.get(8));
		assertEquals(EcorePackage.eINSTANCE.getENamedElement(), contents.get(9));
		assertEquals(EcorePackage.eINSTANCE.getEObject(), contents.get(10));
		assertEquals(EcorePackage.eINSTANCE.getEOperation(), contents.get(11));
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), contents.get(12));
		assertEquals(EcorePackage.eINSTANCE.getEParameter(), contents.get(13));
		assertEquals(EcorePackage.eINSTANCE.getEReference(), contents.get(14));
		assertEquals(EcorePackage.eINSTANCE.getEStructuralFeature(), contents.get(15));
		assertEquals(EcorePackage.eINSTANCE.getETypedElement(), contents.get(16));
		assertEquals(EcorePackage.eINSTANCE.getEStringToStringMapEntry(), contents.get(17));
		assertEquals(EcorePackage.eINSTANCE.getEGenericType(), contents.get(18));
		assertEquals(EcorePackage.eINSTANCE.getETypeParameter(), contents.get(19));
	}

	@Test
	public void testEAllContents_classHierarchy() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);

		getQueryEnvironment().registerEPackage(QmodelPackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		List<EObject> contents = eObjectServices.eAllContents(fullSiriusCodePackage, QmodelPackage.eINSTANCE
				.getQueryEvaluationResult());
		assertEquals(52, contents.size());

		contents = eObjectServices.eAllContents(fullSiriusCodePackage, QmodelPackage.eINSTANCE
				.getSetResult());
		assertEquals(1, contents.size());
	}

	@Test
	public void testEAllContents_implicitRoot() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);

		getQueryEnvironment().registerEPackage(QmodelPackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		List<EObject> contents = eObjectServices.eAllContents(fullSiriusCodePackage, EcorePackage.eINSTANCE
				.getEObject());
		Iterable<EObject> iterable = () -> fullSiriusCodePackage.eAllContents();
		assertEquals(StreamSupport.stream(iterable.spliterator(), false).count(), contents.size());
	}

	@Test
	public void testEContents_classHierarchy() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);

		getQueryEnvironment().registerEPackage(QmodelPackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		// find a SetResult
		List<EObject> allContents = eObjectServices.eAllContents(fullSiriusCodePackage,
				QmodelPackage.eINSTANCE.getExpectation());
		allContents = eObjectServices.eAllContents(fullSiriusCodePackage, QmodelPackage.eINSTANCE
				.getSetResult());
		assertEquals(1, allContents.size());

		// from its container's content, make sure we can find it again
		EObject container = allContents.get(0).eContainer();
		List<EObject> contents = eObjectServices.eContents(container, QmodelPackage.eINSTANCE
				.getQueryEvaluationResult());
		assertEquals(1, contents.size());
		contents = eObjectServices.eContents(container, QmodelPackage.eINSTANCE.getSetResult());
		assertEquals(1, contents.size());
	}

	@Test
	public void testEContents_implicitRoot() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);

		getQueryEnvironment().registerEPackage(QmodelPackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		List<EObject> contents = eObjectServices.eContents(fullSiriusCodePackage, EcorePackage.eINSTANCE
				.getEObject());
		assertEquals(fullSiriusCodePackage.eContents().size(), contents.size());
	}

	@Test
	public void testEContainer_implicitRoot() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);

		getQueryEnvironment().registerEPackage(QmodelPackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		EObject container = eObjectServices.eContainer(fullSiriusCodePackage.eContents().get(0),
				EcorePackage.eINSTANCE.getEObject());
		assertEquals(fullSiriusCodePackage, container);
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
	 * Tests {@link EObjectServices#eAllContents(EObject)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model to test if the eAllContents list is calculated
	 * correctly.
	 */
	@Test
	public void testEAllContentsNoEClassFilter() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

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

	@Test
	public void testEAllContentsNullAndNullTypeEClassifierSet() {
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);
		final List<EObject> contents = eObjectServices.eAllContents((EObject)null, (Set<EClass>)null);

		assertEquals(0, contents.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testEAllContentsNullTypeEClassifierSet() {
		final LinkedHashSet<EClass> types = new LinkedHashSet<EClass>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		eObjectServices.eAllContents((EObject)null, types);
	}

	@Test
	public void testEAllContentsEObjectTypeEClassifierSet() {
		final LinkedHashSet<EClass> types = new LinkedHashSet<EClass>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		List<EObject> contents = eObjectServices.eAllContents(EcorePackage.eINSTANCE, types);

		assertEquals(20, contents.size());
		assertEquals(EcorePackage.eINSTANCE.getEAttribute(), contents.get(0));
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), contents.get(1));
		assertEquals(EcorePackage.eINSTANCE.getEClass(), contents.get(2));
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), contents.get(3));
		assertEquals(EcorePackage.eINSTANCE.getEDataType(), contents.get(4));
		assertEquals(EcorePackage.eINSTANCE.getEEnum(), contents.get(5));
		assertEquals(EcorePackage.eINSTANCE.getEEnumLiteral(), contents.get(6));
		assertEquals(EcorePackage.eINSTANCE.getEFactory(), contents.get(7));
		assertEquals(EcorePackage.eINSTANCE.getEModelElement(), contents.get(8));
		assertEquals(EcorePackage.eINSTANCE.getENamedElement(), contents.get(9));
		assertEquals(EcorePackage.eINSTANCE.getEObject(), contents.get(10));
		assertEquals(EcorePackage.eINSTANCE.getEOperation(), contents.get(11));
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), contents.get(12));
		assertEquals(EcorePackage.eINSTANCE.getEParameter(), contents.get(13));
		assertEquals(EcorePackage.eINSTANCE.getEReference(), contents.get(14));
		assertEquals(EcorePackage.eINSTANCE.getEStructuralFeature(), contents.get(15));
		assertEquals(EcorePackage.eINSTANCE.getETypedElement(), contents.get(16));
		assertEquals(EcorePackage.eINSTANCE.getEStringToStringMapEntry(), contents.get(17));
		assertEquals(EcorePackage.eINSTANCE.getEGenericType(), contents.get(18));
		assertEquals(EcorePackage.eINSTANCE.getETypeParameter(), contents.get(19));
	}

	/**
	 * Tests {@link EObjectServices#eAllContents(EObject, EClass)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model to test if the eAllContents list is calculated
	 * correctly.
	 */
	@Test
	public void testEAllContents() {
		EObject fullSiriusCodePackage = reverseModel.getContents().get(0);
		fullSiriusCodePackage.eAllContents().next();
		EObject unused = fullSiriusCodePackage.eAllContents().next();
		unused.eAllContents().next();
		EObject newEReference1 = unused.eAllContents().next();

		getQueryEnvironment().registerEPackage(QmodelPackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

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

	@Test
	public void eAllContentFilteredSingleContainementFeature() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("testPackage");
		ePkg.setNsPrefix("testPackage");
		ePkg.setNsURI("testPackage");

		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls.setName("TestCls");
		ePkg.getEClassifiers().add(eCls);
		final EReference reference = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		reference.setName("testReference");
		reference.setContainment(true);
		reference.setEType(eCls);
		reference.setUpperBound(1);
		eCls.getEStructuralFeatures().add(reference);

		final EObject eObj1 = EcoreUtil.create(eCls);
		final EObject eObj2 = EcoreUtil.create(eCls);
		final EObject eObj3 = EcoreUtil.create(eCls);
		eObj1.eSet(reference, eObj2);
		eObj2.eSet(reference, eObj3);

		getQueryEnvironment().registerEPackage(ePkg);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		try {
			final List<EObject> result = eObjectServices.eAllContents(eObj1, eCls);

			assertEquals(2, result.size());
			assertEquals(eObj2, result.get(0));
			assertEquals(eObj3, result.get(1));
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@SuppressWarnings({"unchecked" })
	@Test
	public void eAllContentFilteredMultiContainementFeature() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("testPackage");
		ePkg.setNsPrefix("testPackage");
		ePkg.setNsURI("testPackage");

		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls.setName("TestCls");
		ePkg.getEClassifiers().add(eCls);
		final EReference reference = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		reference.setName("testReference");
		reference.setContainment(true);
		reference.setEType(eCls);
		reference.setUpperBound(-1);
		eCls.getEStructuralFeatures().add(reference);

		final EObject eObj1 = EcoreUtil.create(eCls);
		final EObject eObj2 = EcoreUtil.create(eCls);
		final EObject eObj3 = EcoreUtil.create(eCls);
		final EObject eObj4 = EcoreUtil.create(eCls);
		((List<EObject>)eObj1.eGet(reference)).add(eObj2);
		((List<EObject>)eObj2.eGet(reference)).add(eObj3);
		((List<EObject>)eObj2.eGet(reference)).add(eObj4);

		getQueryEnvironment().registerEPackage(ePkg);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		try {
			final List<EObject> result = eObjectServices.eAllContents(eObj1, eCls);

			assertEquals(3, result.size());
			assertEquals(eObj2, result.get(0));
			assertEquals(eObj3, result.get(1));
			assertEquals(eObj4, result.get(2));
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	/**
	 * * Tests {@link EObjectServices#eGet(EObject, String)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model. </br>
	 * It ensures that the {@link EObjectServices#eGet(EObject, String)} returns the expected feature value.
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
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		// The queries "name" feature exists but in the current model it is not
		// specified
		assertEquals(null, eObjectServices.eGet(queries, "name"));
		// The query "name" feature does not exist
		assertEquals(null, eObjectServices.eGet(query, "name"));

		// The query "expression" feature does not exist
		assertEquals("[self/]", eObjectServices.eGet(query, "expression"));
		// The eObjectListResult "Query" feature does not exist
		assertEquals(null, eObjectServices.eGet(eObjectListResult, "query"));
	}

	/**
	 * * Tests {@link EObjectServices#eGet(EObject, EStructuralFeature)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model. </br>
	 * It ensures that the {@link EObjectServices#eGet(EObject, EStructuralFeature)} returns the expected
	 * feature value.
	 * <ul>
	 * We expect to get:
	 * <li>for an instance of "Queries" with "name" feature filter: the eGet must return null because the
	 * concerned feature is not specified in the model.</li>
	 * </ul>
	 */
	@Test
	public void testEGetFeature() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final EStructuralFeature nameFeature = queries.eClass().getEStructuralFeature("name");

		// The queries "name" feature exists but in the current model it is not
		// specified
		assertEquals(null, eObjectServices.eGet(queries, nameFeature));
	}

	/**
	 * * Tests {@link EObjectServices#eGet(EObject, EStructuralFeature, boolean)} method.</br>
	 * This test uses the "resources/ecore/reverse.ecore" model. </br>
	 * It ensures that the {@link EObjectServices#eGet(EObject, EStructuralFeature, boolean)} returns the
	 * expected feature value.
	 * <ul>
	 * We expect to get:
	 * <li>for an instance of "Queries" with "name" feature filter: the eGet must return null because the
	 * concerned feature is not specified in the model.</li>
	 * </ul>
	 */
	@Test
	public void testEGetFeatureResolve() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final EStructuralFeature nameFeature = queries.eClass().getEStructuralFeature("name");

		// The queries "name" feature exists but in the current model it is not
		// specified
		assertEquals(null, eObjectServices.eGet(queries, nameFeature, true));
	}

	@Test
	public void testEGetEList() {
		Queries fullSiriusCodePackage = (Queries)reverseModel.getContents().get(0);

		getQueryEnvironment().registerEPackage(QmodelPackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		Object queries = eObjectServices.eGet(fullSiriusCodePackage, "queries");
		assertTrue(queries instanceof List<?>);
		assertEquals(fullSiriusCodePackage.getQueries(), queries);

		// make sure we return a copy of the list and not the list itself
		((List<?>)queries).clear();
		assertFalse(fullSiriusCodePackage.getQueries().isEmpty());
	}

	@Test
	public void testEGetEMap() {
		getQueryEnvironment().registerEPackage(AnydslPackage.eINSTANCE);

		Restaurant restaurant = AnydslFactory.eINSTANCE.createRestaurant();
		EMap<String, Recipe> menu = restaurant.getMenu();
		menu.put("omelette", AnydslFactory.eINSTANCE.createRecipe());
		menu.put("kouign-amann", AnydslFactory.eINSTANCE.createRecipe());
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		Object menuFromEGet = eObjectServices.eGet(restaurant, "menu");
		assertTrue(menuFromEGet instanceof EMap<?, ?>);
		assertEqualEMaps(menu, (EMap<?, ?>)menuFromEGet);

		// make sure the returned map has been copied before being returned
		((EMap<?, ?>)menuFromEGet).clear();
		assertFalse(menu.isEmpty());

		menu.clear();
		menuFromEGet = eObjectServices.eGet(restaurant, "menu");
		assertTrue(menuFromEGet instanceof EMap<?, ?>);
		assertTrue(((EMap<?, ?>)menuFromEGet).isEmpty());
	}

	// We can't compare EMaps directly (483452)
	private void assertEqualEMaps(EMap<?, ?> map1, EMap<?, ?> map2) {
		assertEquals(map1.size(), ((EMap<?, ?>)map2).size());
		for (Map.Entry<?, ?> entry : ((EMap<?, ?>)map2).entrySet()) {
			assertTrue(map1.containsKey(entry.getKey()));
			assertEquals(map1.get(entry.getKey()), entry.getValue());
		}
	}

	@Test(expected = NullPointerException.class)
	public void testEGetNullFeature() {
		EObject queries = reverseModel.getContents().get(0);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		eObjectServices.eGet(queries, (String)null);
	}

	@Test(expected = NullPointerException.class)
	public void testEGetNullEObject() {
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		eObjectServices.eGet(null, "query");
	}

	@Test(expected = NullPointerException.class)
	public void testEGetNullEObjectNullFeature() {
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		eObjectServices.eGet(null, (String)null);
	}

	@Test
	public void testAllInstancesNoRootProviderEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final List<EObject> result = eObjectServices.allInstances(EcorePackage.eINSTANCE.getEAttribute());

		assertEquals(0, result.size());
	}

	@Test
	public void testAllInstancesNoRootProviderSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final Set<EClass> types = new LinkedHashSet<>();
		types.add(EcorePackage.eINSTANCE.getEAttribute());
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final List<EObject> result = eObjectServices.allInstances(types);

		assertEquals(0, result.size());
	}

	@Test
	public void testAllInstancesNoRootProviderNullEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final List<EObject> result = eObjectServices.allInstances((EClass)null);

		assertEquals(0, result.size());
	}

	@Test
	public void testAllInstancesNoRootProviderNullSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final List<EObject> result = eObjectServices.allInstances((Set<EClass>)null);

		assertEquals(0, result.size());
	}

	@Test
	public void testAllInstancesEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null,
				new RootEObjectProvider(EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE));

		final List<EObject> result = eObjectServices.allInstances(EcorePackage.eINSTANCE.getEAttribute());

		assertEquals(46, result.size());
	}

	@Test
	public void testAllInstancesEClassKeepRootElement() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null,
				new RootEObjectProvider(EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE));

		final List<EObject> result = eObjectServices.allInstances(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(2, result.size());
		assertEquals(EcorePackage.eINSTANCE, result.get(0));
		assertEquals(AnydslPackage.eINSTANCE, result.get(1));
	}

	@Test
	public void testAllInstancesSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null,
				new RootEObjectProvider(EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE));

		final Set<EClass> types = new LinkedHashSet<>();
		types.add(EcorePackage.eINSTANCE.getEAttribute());
		types.add(EcorePackage.eINSTANCE.getEReference());
		final List<EObject> result = eObjectServices.allInstances(types);

		assertEquals(113, result.size());
	}

	@Test
	public void testAllInstancesNullEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null,
				new RootEObjectProvider(EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE));

		final List<EObject> result = eObjectServices.allInstances((EClass)null);

		assertEquals(0, result.size());
	}

	@Test
	public void testAllInstancesNullSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null,
				new RootEObjectProvider(EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE));

		final List<EObject> result = eObjectServices.allInstances((Set<EClass>)null);

		assertEquals(0, result.size());
	}

	@Test
	public void aqlFeatureAccessNullNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final Object result = eObjectServices.aqlFeatureAccess(null, null);

		assertNull(result);
	}

	@Test
	public void aqlFeatureAccessNullName() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final Object result = eObjectServices.aqlFeatureAccess(null, "feature");

		assertNull(result);
	}

	@Test
	public void aqlFeatureAccessEObjectNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final Object result = eObjectServices.aqlFeatureAccess(EcorePackage.eINSTANCE, null);

		assertTrue(result instanceof Nothing);
		assertEquals("Feature null not found in EClass EPackage", ((Nothing)result).getMessage());
	}

	@Test
	public void aqlFeatureAccessEObjectNotExistingFeature() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final Object result = eObjectServices.aqlFeatureAccess(EcorePackage.eINSTANCE, "notExistingFeature");

		assertTrue(result instanceof Nothing);
		assertEquals("Feature notExistingFeature not found in EClass EPackage", ((Nothing)result)
				.getMessage());
	}

	@Test
	public void aqlFeatureAccess() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EObjectServices eObjectServices = new EObjectServices(getQueryEnvironment(), null, null);

		final Object result = eObjectServices.aqlFeatureAccess(EcorePackage.eINSTANCE, "name");

		assertEquals("ecore", result);
	}

}
