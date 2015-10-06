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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.XPathServices;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
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

	public XPathServices xPathServices;

	public Resource reverseModel;

	@Before
	public void before() throws Exception {
		super.before();
		this.xPathServices = new XPathServices(getQueryEnvironment());
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

		List<EObject> queryAncestors = xPathServices.ancestors(query);
		assertEquals(1, queryAncestors.size());
		assertEquals(queries, queryAncestors.get(0));

		List<EObject> eObjectListResultAncestors = xPathServices.ancestors(eObjectListResult);
		assertEquals(3, eObjectListResultAncestors.size());
		assertEquals(resultExpectation, eObjectListResultAncestors.get(0));
		assertEquals(query, eObjectListResultAncestors.get(1));
		assertEquals(queries, eObjectListResultAncestors.get(2));

		EClassifier eDatatype = EcoreFactory.eINSTANCE.createEDataType();
		assertEquals(0, xPathServices.ancestors(eDatatype).size());
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

		List<EObject> queryAncestors = xPathServices.ancestors(query, query.eClass());
		assertTrue(queryAncestors.isEmpty());

		queryAncestors = xPathServices.ancestors(query, queries.eClass());
		assertEquals(1, queryAncestors.size());
		assertEquals(queries, queryAncestors.get(0));

		queryAncestors = xPathServices.ancestors(query, (EClassifier)null);
		assertEquals(1, queryAncestors.size());
		assertEquals(queries, queryAncestors.get(0));

		List<EObject> eObjectListResultAncestors = xPathServices.ancestors(eObjectListResult, query.eClass());
		assertEquals(1, eObjectListResultAncestors.size());
		assertEquals(query, eObjectListResultAncestors.get(0));

		eObjectListResultAncestors = xPathServices.ancestors(eObjectListResult, queries.eClass());
		assertEquals(1, eObjectListResultAncestors.size());
		assertEquals(queries, eObjectListResultAncestors.get(0));

		EClassifier eDatatype = EcoreFactory.eINSTANCE.createEDataType();
		assertEquals(0, xPathServices.ancestors(query, eDatatype).size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAncestorsNullAndNullTypeEClassifierSet() {
		final List<EObject> result = xPathServices.ancestors((EObject)null, (Set<EClassifier>)null);

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testAncestorsNullTypeEClassifierSet() {
		final LinkedHashSet<EClassifier> types = new LinkedHashSet<EClassifier>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		xPathServices.ancestors((EObject)null, types);
	}

	@Test
	public void testAncestorsEObjectTypeEClassifierSet() {
		final LinkedHashSet<EClassifier> types = new LinkedHashSet<EClassifier>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.ancestors(EcorePackage.eINSTANCE.getEClass(), types);

		assertEquals(1, result.size());
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

		List<EObject> result = xPathServices.siblings(queries);
		assertNotNull(result);
		assertEquals(0, result.size());

		result = xPathServices.siblings(query);
		assertNotNull(result);
		assertEquals(37, result.size());

		result = xPathServices.siblings(resultExpectation);
		assertNotNull(result);
		assertEquals(1, result.size());

		result = xPathServices.siblings(eObjectListResult);
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

		List<EObject> result = xPathServices.siblings(queries, queries.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(queries, queries.eClass()), result.size());

		result = xPathServices.siblings(query, query.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(query, query.eClass()), result.size());

		result = xPathServices.siblings(query, queries.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(query, queries.eClass()), result.size());

		result = xPathServices.siblings(resultExpectation, resultExpectation.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(resultExpectation, resultExpectation.eClass()), result.size());

		result = xPathServices.siblings(eObjectListResult, eObjectListResult.eClass());
		assertNotNull(result);
		assertEquals(siblingsNumber(eObjectListResult, eObjectListResult.eClass()), result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSiblingsNullAndNullTypeEClassifierSet() {
		final List<EObject> result = xPathServices.siblings((EObject)null, (Set<EClassifier>)null);

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testSiblingsNullTypeEClassifierSet() {
		final LinkedHashSet<EClassifier> types = new LinkedHashSet<EClassifier>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		xPathServices.siblings((EObject)null, types);
	}

	@Test
	public void testSiblingsEObjectTypeEClassifierSet() {
		final LinkedHashSet<EClassifier> types = new LinkedHashSet<EClassifier>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.siblings(EcorePackage.eINSTANCE.getEClass(), types);

		assertEquals(52, result.size());
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

		List<EObject> result = xPathServices.precedingSiblings(queries);
		assertNotNull(result);
		assertEquals(0, result.size());

		result = xPathServices.precedingSiblings(query_1);
		assertNotNull(result);
		assertEquals(0, result.size());

		result = xPathServices.precedingSiblings(query_2);
		assertNotNull(result);
		assertEquals(5, result.size());

		result = xPathServices.precedingSiblings(resultExpectation);
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

		List<EObject> result = xPathServices.precedingSiblings(queries, queries.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());

		result = xPathServices.precedingSiblings(query_1, query_1.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());

		result = xPathServices.precedingSiblings(query_2, query_2.eClass());
		assertNotNull(result);
		assertEquals(5, result.size());

		result = xPathServices.precedingSiblings(query_1, queries.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());

		result = xPathServices.precedingSiblings(resultExpectation, resultExpectation.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testPrecedingSiblingsNullAndNullTypeEClassifierSet() {
		final List<EObject> result = xPathServices.precedingSiblings((EObject)null, (Set<EClassifier>)null);

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testPrecedingSiblingsNullTypeEClassifierSet() {
		final LinkedHashSet<EClassifier> types = new LinkedHashSet<EClassifier>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		xPathServices.precedingSiblings((EObject)null, types);
	}

	@Test
	public void testPrecedingSiblingsEObjectTypeEClassifierSet() {
		final LinkedHashSet<EClassifier> types = new LinkedHashSet<EClassifier>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.precedingSiblings(EcorePackage.eINSTANCE.getEClass(),
				types);

		assertEquals(2, result.size());
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

		List<EObject> result = xPathServices.followingSiblings(queries);
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(queries), result.size());

		result = xPathServices.followingSiblings(query_1);
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(query_1), result.size());

		result = xPathServices.followingSiblings(query_2);
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(query_2), result.size());

		result = xPathServices.followingSiblings(resultExpectation);
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

		List<EObject> result = xPathServices.followingSiblings(queries, queries.eClass());
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(queries, queries.eClass()), result.size());

		result = xPathServices.followingSiblings(query_1, query_1.eClass());
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(query_1, query_1.eClass()), result.size());

		result = xPathServices.followingSiblings(query_2, query_2.eClass());
		assertNotNull(result);
		assertEquals(followingSiblingsNumber(query_2, query_2.eClass()), result.size());

		result = xPathServices.followingSiblings(query_1, queries.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());

		result = xPathServices.followingSiblings(resultExpectation, resultExpectation.eClass());
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testFollowingSiblingsNullAndNullTypeEClassifierSet() {
		final List<EObject> result = xPathServices.followingSiblings((EObject)null, (Set<EClassifier>)null);

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void testFollowingSiblingsNullTypeEClassifierSet() {
		final LinkedHashSet<EClassifier> types = new LinkedHashSet<EClassifier>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		xPathServices.followingSiblings((EObject)null, types);
	}

	@Test
	public void testFollowingSiblingsEObjectTypeEClassifierSet() {
		final LinkedHashSet<EClassifier> types = new LinkedHashSet<EClassifier>();
		types.add(EcorePackage.eINSTANCE.getEPackage());
		types.add(EcorePackage.eINSTANCE.getEClass());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.followingSiblings(EcorePackage.eINSTANCE.getEClass(),
				types);

		assertEquals(50, result.size());
	}

}
