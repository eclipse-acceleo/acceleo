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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.eclipse.acceleo.query.collections.LazyList;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
		Logger logger = Logger.getLogger("testEInverse");
		BasicLookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(
				clazz2, logger).getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(clazz2);
		assertEquals("Unexpected count of inverse references returned", 2, ((Collection<?>)inversedSequence)
				.size());

		final Iterator<?> children = ((Collection<?>)inversedSequence).iterator();
		assertEquals("The first inverse reference on the second EClass should have been the first EClass",
				clazz, children.next());
		assertTrue("The second inverse reference on the second EClass should have been a GenericType",
				children.next() instanceof EGenericType);

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
		Logger logger = Logger.getLogger("testEInverseWithFilter");
		BasicLookupEngine queryEnvironnementWithCrossReferencer = getQueryEnvironnementWithCrossReferencer(
				clazz2, logger).getLookupEngine();
		eObjectServices.setCrossReferencer(queryEnvironnementWithCrossReferencer.getCrossReferencer());

		Set<EObject> inversedSequence = eObjectServices.eInverse(clazz2, EcorePackage.eINSTANCE
				.getEGenericType());

		assertEquals("Unexpected count of inverse references returned", 1, ((Collection<?>)inversedSequence)
				.size());
		final Iterator<?> children = ((Collection<?>)inversedSequence).iterator();
		assertTrue("The inverse reference on the second EClass should have been a GenericType", children
				.next() instanceof EGenericType);

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
		assertEquals(30, contents.size());
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
		assertEquals(30, contents.size());
		contents = eObjectServices.eAllContents(fullSiriusCodePackage, newEReference1.eClass());
		assertEquals(30, contents.size());
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
	 * Tests {@link EObjectServices#ancestors(EObject)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the
	 * {@link EObjectServices#ancestors(EObject)} returns the expected ancestors list.
	 * <ul>
	 * We expect to get:
	 * <li>for an instance of "Query": the list of ancestors is composed of one EObject {Queries}.</li>
	 * <li>for an instance of "EObjectListResult": the list of ancestors is composed of two EObjects {Query,
	 * Queries}.</li>
	 * <li>for an instance of "EDataType" with no container: the list of ancestors is an empty list.</li>
	 * </ul>
	 */
	@Test
	public void testAncestors_WithoutFilter() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();
		EObject resultExpectation = query.eAllContents().next();
		EObject eObjectListResult = resultExpectation.eAllContents().next();

		List<EObject> queryAncestors = eObjectServices.ancestors(query);
		assertEquals(1, queryAncestors.size());
		assertEquals(queries, queryAncestors.get(0));

		List<EObject> eObjectListResultAncestors = eObjectServices.ancestors(eObjectListResult);
		assertEquals(3, eObjectListResultAncestors.size());
		assertEquals(resultExpectation, eObjectListResultAncestors.get(0));
		assertEquals(query, eObjectListResultAncestors.get(1));
		assertEquals(queries, eObjectListResultAncestors.get(2));

		EClassifier eDatatype = EcoreFactory.eINSTANCE.createEDataType();
		assertEquals(0, eObjectServices.ancestors(eDatatype).size());
	}

	/**
	 * Tests {@link EObjectServices#ancestors(EObject, EClassifier)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the
	 * {@link EObjectServices#ancestors(EObject, EClassifier)} returns the expected ancestors list.
	 * <ul>
	 * We expect to get:
	 * <li>for an instance of "EObjectListResult" with "Query" EClassifier filter: the list of ancestors is
	 * composed of one EObject {Query}.</li>
	 * <li>for an instance of "EObjectListResult" with "Queries" EClassifier filter: the list of ancestors is
	 * composed of one EObject {Queries}.</li>
	 * <li>for an instance of "Query" with "Queries" EClassifier filter: the list of ancestors is composed of
	 * one EObject {Queries}.</li>
	 * <li>for an instance of "Query" with "Query" EClassifier filter: the list of ancestors is an empty list.
	 * </li>
	 * <li>for an instance of "Query" with "null" EClassifier filter: the list of ancestors is composed of one
	 * EObject {Queries}.</li>
	 * <li>for an instance of "Query" with "EDataType" EClassifier filter: the list of ancestors is an empty
	 * list.</li>
	 * </ul>
	 */
	@Test
	public void testAncestors() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();
		EObject resultExpectation = query.eAllContents().next();
		EObject eObjectListResult = resultExpectation.eAllContents().next();

		List<EObject> queryAncestors = eObjectServices.ancestors(query, query.eClass());
		assertTrue(queryAncestors.isEmpty());

		queryAncestors = eObjectServices.ancestors(query, queries.eClass());
		assertEquals(1, queryAncestors.size());
		assertEquals(queries, queryAncestors.get(0));

		queryAncestors = eObjectServices.ancestors(query, null);
		assertEquals(1, queryAncestors.size());
		assertEquals(queries, queryAncestors.get(0));

		List<EObject> eObjectListResultAncestors = eObjectServices.ancestors(eObjectListResult, query
				.eClass());
		assertEquals(1, eObjectListResultAncestors.size());
		assertEquals(query, eObjectListResultAncestors.get(0));

		eObjectListResultAncestors = eObjectServices.ancestors(eObjectListResult, queries.eClass());
		assertEquals(1, eObjectListResultAncestors.size());
		assertEquals(queries, eObjectListResultAncestors.get(0));

		EClassifier eDatatype = EcoreFactory.eINSTANCE.createEDataType();
		assertEquals(0, eObjectServices.ancestors(query, eDatatype).size());
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

	/**
	 * Tests {@link EObjectServices#siblings(EObject)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the
	 * {@link EObjectServices#siblings(EObject)} returns the expected value.
	 */
	@Test
	public void testSiblingsNoEClassFilter() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();
		EObject resultExpectation = query.eAllContents().next();
		EObject eObjectListResult = resultExpectation.eAllContents().next();

		Object result = eObjectServices.siblings(queries);
		assertNotNull(result);
		LazyList<EObject> listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());

		result = eObjectServices.siblings(query);
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(36, listResult.size());

		result = eObjectServices.siblings(resultExpectation);
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(1, listResult.size());

		result = eObjectServices.siblings(eObjectListResult);
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());
	}

	private int siblingsNumber(EObject eObject, EClass eClass) {
		List<EObject> content = eObject.eContainer() != null ? eObject.eContainer().eContents() : eObject
				.eResource().getContents();
		int result = 0;
		for (EObject elt : content) {
			if (eClass == null || eClass.isInstance(elt)) {
				result++;
			}
		}
		if (eClass == null || eClass.isInstance(eObject)) {
			result--;
		}
		return result;
	}

	/**
	 * Tests {@link EObjectServices#siblings(EObject, EClassifier)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the
	 * {@link EObjectServices#siblings(EObject, EClassifier)} returns the expected value.
	 */
	@Test
	public void testSiblings() {
		EObject queries = reverseModel.getContents().get(0);
		queries.eAllContents().next();
		EObject query = queries.eAllContents().next();
		EObject resultExpectation = query.eAllContents().next();
		EObject eObjectListResult = resultExpectation.eAllContents().next();

		Object result = eObjectServices.siblings(queries, queries.eClass());
		assertNotNull(result);
		LazyList<EObject> listResult = (LazyList<EObject>)result;
		assertEquals(siblingsNumber(queries, queries.eClass()), listResult.size());

		result = eObjectServices.siblings(query, query.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(siblingsNumber(query, query.eClass()), listResult.size());

		result = eObjectServices.siblings(query, queries.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(siblingsNumber(query, queries.eClass()), listResult.size());

		result = eObjectServices.siblings(resultExpectation, resultExpectation.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(siblingsNumber(resultExpectation, resultExpectation.eClass()), listResult.size());

		result = eObjectServices.siblings(eObjectListResult, eObjectListResult.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(siblingsNumber(eObjectListResult, eObjectListResult.eClass()), listResult.size());
	}

	/**
	 * Tests {@link EObjectServices#precedingSiblings(EObject)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the
	 * {@link EObjectServices#precedingSiblings(EObject)} returns the expected value.
	 */
	@Test
	public void testPrecedingSiblingsNoEClassFilter() {
		EObject queries = reverseModel.getContents().get(0);
		EObject query_1 = queries.eContents().get(0);
		EObject resultExpectation = query_1.eAllContents().next();
		EObject query_2 = queries.eContents().get(5);

		Object result = eObjectServices.precedingSiblings(queries);
		assertNotNull(result);
		LazyList<EObject> listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());

		result = eObjectServices.precedingSiblings(query_1);
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());

		result = eObjectServices.precedingSiblings(query_2);
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(5, listResult.size());

		result = eObjectServices.precedingSiblings(resultExpectation);
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());
	}

	/**
	 * Tests {@link EObjectServices#precedingSiblings(EObject, EClassifier)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the
	 * {@link EObjectServices#precedingSiblings(EObject, EClassifier)} returns the expected value.
	 */
	@Test
	public void testPrecedingSiblings() {
		EObject queries = reverseModel.getContents().get(0);
		EObject query_1 = queries.eContents().get(0);
		EObject resultExpectation = query_1.eAllContents().next();
		EObject query_2 = queries.eContents().get(5);

		Object result = eObjectServices.precedingSiblings(queries, queries.eClass());
		assertNotNull(result);
		LazyList<EObject> listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());

		result = eObjectServices.precedingSiblings(query_1, query_1.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());

		result = eObjectServices.precedingSiblings(query_2, query_2.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(5, listResult.size());

		result = eObjectServices.precedingSiblings(query_1, queries.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());

		result = eObjectServices.precedingSiblings(resultExpectation, resultExpectation.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());
	}

	private int followingSiblingsNumber(EObject eObject) {
		List<EObject> content = eObject.eContainer() != null ? eObject.eContainer().eContents() : eObject
				.eResource().getContents();
		return content.size() - content.indexOf(eObject) - 1;
	}

	private int followingSiblingsNumber(EObject eObject, EClass filter) {
		List<EObject> content = eObject.eContainer() != null ? eObject.eContainer().eContents() : eObject
				.eResource().getContents();
		int result = 0;
		int start = content.indexOf(eObject);
		if (start >= 0) {
			for (int i = start + 1; i < content.size(); i++) {
				if (filter == null || filter.isInstance(content.get(i))) {
					result++;
				}
			}
		}
		return result;
	}

	private Object getContainer(EObject object) {
		Object result = object.eContainer();

		if (result == null && object instanceof InternalEObject) {
			// maybe it's a resource root
			result = ((InternalEObject)object).eDirectResource();
		}

		return result;
	}

	/**
	 * Tests {@link EObjectServices#followingSiblings(EObject)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the
	 * {@link EObjectServices#followingSiblings(EObject)} returns the expected value.
	 */
	@Test
	public void testFollowingSiblingsNoEClassFilter() {
		EObject queries = reverseModel.getContents().get(0);
		EObject query_1 = queries.eContents().get(0);
		EObject resultExpectation = query_1.eAllContents().next();
		EObject query_2 = queries.eContents().get(5);

		Object result = eObjectServices.followingSiblings(queries);
		assertNotNull(result);
		LazyList<EObject> listResult = (LazyList<EObject>)result;
		assertEquals(followingSiblingsNumber(queries), listResult.size());

		result = eObjectServices.followingSiblings(query_1);
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(followingSiblingsNumber(query_1), listResult.size());

		result = eObjectServices.followingSiblings(query_2);
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(followingSiblingsNumber(query_2), listResult.size());

		result = eObjectServices.followingSiblings(resultExpectation);
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(followingSiblingsNumber(resultExpectation), listResult.size());
	}

	/**
	 * Tests {@link EObjectServices#followingSiblings(EObject, EClassifier)} method.</br> This test uses the
	 * "resources/ecore/reverse.ecore" model. </br> It ensures that the
	 * {@link EObjectServices#followingSiblings(EObject, EClassifier)} returns the expected value.
	 */
	@Test
	public void testFollowingSiblings() {
		EObject queries = reverseModel.getContents().get(0);
		EObject query_1 = queries.eContents().get(0);
		EObject resultExpectation = query_1.eAllContents().next();
		EObject query_2 = queries.eContents().get(5);

		Object result = eObjectServices.followingSiblings(queries, queries.eClass());
		assertNotNull(result);
		LazyList<EObject> listResult = (LazyList<EObject>)result;
		assertEquals(followingSiblingsNumber(queries, queries.eClass()), listResult.size());

		result = eObjectServices.followingSiblings(query_1, query_1.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(followingSiblingsNumber(query_1, query_1.eClass()), listResult.size());

		result = eObjectServices.followingSiblings(query_2, query_2.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(followingSiblingsNumber(query_2, query_2.eClass()), listResult.size());

		result = eObjectServices.followingSiblings(query_1, queries.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());

		result = eObjectServices.followingSiblings(resultExpectation, resultExpectation.eClass());
		assertNotNull(result);
		listResult = (LazyList<EObject>)result;
		assertEquals(0, listResult.size());
	}
}
