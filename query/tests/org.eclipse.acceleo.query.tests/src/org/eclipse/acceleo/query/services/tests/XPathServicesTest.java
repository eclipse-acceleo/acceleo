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
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.XPathServices;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * EObject services tests.
 * 
 * @author pguilet
 */
public class XPathServicesTest extends AbstractEngineInitializationWithCrossReferencer {
	public XPathServices eObjectServices;

	public Resource reverseModel;

	@Before
	public void setup() throws URISyntaxException, IOException {
		this.eObjectServices = new XPathServices(getQueryEnvironment());
		this.reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
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

		List<EObject> result = eObjectServices.siblings(queries);
		assertNotNull(result);
		assertEquals(0, result.size());

		result = eObjectServices.siblings(query);
		assertNotNull(result);
		assertEquals(37, result.size());

		result = eObjectServices.siblings(resultExpectation);
		assertNotNull(result);
		assertEquals(1, result.size());

		result = eObjectServices.siblings(eObjectListResult);
		assertNotNull(result);
		assertEquals(0, result.size());
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

		List<EObject> result = eObjectServices.siblings(queries, queries.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(queries, queries.eClass()), result.size());

		result = eObjectServices.siblings(query, query.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(query, query.eClass()), result.size());

		result = eObjectServices.siblings(query, queries.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(query, queries.eClass()), result.size());

		result = eObjectServices.siblings(resultExpectation, resultExpectation.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(resultExpectation, resultExpectation.eClass()), result.size());

		result = eObjectServices.siblings(eObjectListResult, eObjectListResult.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(eObjectListResult, eObjectListResult.eClass()), result.size());
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

		List<EObject> result = eObjectServices.precedingSiblings(queries);
		assertNotNull(result);
		assertEquals(0, result.size());

		result = eObjectServices.precedingSiblings(query_1);
		assertNotNull(result);
		assertEquals(0, result.size());

		result = eObjectServices.precedingSiblings(query_2);
		assertNotNull(result);
		assertEquals(5, result.size());

		result = eObjectServices.precedingSiblings(resultExpectation);
		assertNotNull(result);
		assertEquals(0, result.size());
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

		List<EObject> result = eObjectServices.precedingSiblings(queries, queries.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());

		result = eObjectServices.precedingSiblings(query_1, query_1.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());

		result = eObjectServices.precedingSiblings(query_2, query_2.eClass());
		assertNotNull(result);
		assertEquals(5, result.size());

		result = eObjectServices.precedingSiblings(query_1, queries.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());

		result = eObjectServices.precedingSiblings(resultExpectation, resultExpectation.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());
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

		List<EObject> result = eObjectServices.followingSiblings(queries);
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(queries), result.size());

		result = eObjectServices.followingSiblings(query_1);
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(query_1), result.size());

		result = eObjectServices.followingSiblings(query_2);
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(query_2), result.size());

		result = eObjectServices.followingSiblings(resultExpectation);
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(resultExpectation), result.size());
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

		List<EObject> result = eObjectServices.followingSiblings(queries, queries.eClass());
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(queries, queries.eClass()), result.size());

		result = eObjectServices.followingSiblings(query_1, query_1.eClass());
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(query_1, query_1.eClass()), result.size());

		result = eObjectServices.followingSiblings(query_2, query_2.eClass());
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(query_2, query_2.eClass()), result.size());

		result = eObjectServices.followingSiblings(query_1, queries.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());

		result = eObjectServices.followingSiblings(resultExpectation, resultExpectation.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());
	}
}
