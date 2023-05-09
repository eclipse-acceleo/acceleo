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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.services.XPathServices;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * EObject services tests.
 * 
 * @author pguilet
 */
public class XPathServicesTest extends AbstractEngineInitializationWithCrossReferencer {

	public XPathServices xPathServices;

	@Before
	public void before() throws Exception {
		super.before();
		this.xPathServices = new XPathServices(getQueryEnvironment());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void ancestorsNull() {
		xPathServices.ancestors(null);
	}

	@Test
	public void ancestorsNoAncestors() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		final List<EObject> result = xPathServices.ancestors(ePkg);

		assertEquals(0, result.size());
	}

	@Test
	public void ancestorsOneAncestor() {
		final EPackage parentEPkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		parentEPkg.getESubpackages().add(ePkg);

		final List<EObject> result = xPathServices.ancestors(ePkg);

		assertEquals(1, result.size());
		assertEquals(parentEPkg, result.get(0));
	}

	@Test
	public void ancestorsTwoAncestor() {
		final EPackage grandParentEPkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage parentEPkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		grandParentEPkg.getESubpackages().add(parentEPkg);
		parentEPkg.getESubpackages().add(ePkg);

		final List<EObject> result = xPathServices.ancestors(ePkg);

		assertEquals(2, result.size());
		assertEquals(parentEPkg, result.get(0));
		assertEquals(grandParentEPkg, result.get(1));
	}

	@Test
	public void ancestorsThreeAncestor() {
		final EPackage grandGrandParentEPkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage grandParentEPkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage parentEPkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		grandGrandParentEPkg.getESubpackages().add(grandParentEPkg);
		grandParentEPkg.getESubpackages().add(parentEPkg);
		parentEPkg.getESubpackages().add(ePkg);

		final List<EObject> result = xPathServices.ancestors(ePkg);

		assertEquals(3, result.size());
		assertEquals(parentEPkg, result.get(0));
		assertEquals(grandParentEPkg, result.get(1));
		assertEquals(grandGrandParentEPkg, result.get(2));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void ancestorsFilterNullNull() {
		xPathServices.ancestors(null, (EClass)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void ancestorsFilterNullEClass() {
		xPathServices.ancestors(null, EcorePackage.eINSTANCE.getEPackage());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void ancestorsFilterEObjectNull() {
		xPathServices.ancestors(EcorePackage.eINSTANCE.getEPackage(), (EClass)null);
	}

	@Test
	public void ancestorsFilter() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EAttribute eAttr = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		ePkg.getEClassifiers().add(eCls);
		eCls.getEStructuralFeatures().add(eAttr);

		final List<EObject> result = xPathServices.ancestors(eAttr, EcorePackage.eINSTANCE.getEClassifier());

		assertEquals(1, result.size());
		assertEquals(eCls, result.get(0));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void ancestorsFiltersNullNull() {
		xPathServices.ancestors(null, (Set<EClass>)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void ancestorsFiltersNullEClass() {
		Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		xPathServices.ancestors(null, filters);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void ancestorsFiltersEObjectNull() {
		xPathServices.ancestors(EcorePackage.eINSTANCE.getEPackage(), (Set<EClass>)null);
	}

	@Test
	public void ancestorsFilters() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EAttribute eAttr = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		ePkg.getEClassifiers().add(eCls);
		eCls.getEStructuralFeatures().add(eAttr);

		Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEClassifier());
		filters.add(EcorePackage.eINSTANCE.getEPackage());
		final List<EObject> result = xPathServices.ancestors(eAttr, filters);

		assertEquals(2, result.size());
		assertEquals(eCls, result.get(0));
		assertEquals(ePkg, result.get(1));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void siblingsNull() {
		xPathServices.siblings(null);
	}

	@Test
	public void siblingsNoContainer() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		final List<EObject> result = xPathServices.siblings(ePkg);

		assertEquals(0, result.size());
	}

	@Test
	public void siblingsResourceContainer() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);

		final List<EObject> result = xPathServices.siblings(ePkgB);

		assertEquals(2, result.size());
		assertEquals(ePkgA, result.get(0));
		assertEquals(ePkgC, result.get(1));
	}

	@Test
	public void siblingsResourceContainerFragment() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgD = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		ePkgD.getESubpackages().add(ePkgC);

		final List<EObject> result = xPathServices.siblings(ePkgB);

		assertEquals(1, result.size());
		assertEquals(ePkgA, result.get(0));
	}

	@Test
	public void siblingsEObjectContainerMultiValuedEReference() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EOperation eOperation1 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation2 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation3 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EStructuralFeature feature1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature3 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eCls.getEOperations().add(eOperation1);
		eCls.getEOperations().add(eOperation2);
		eCls.getEOperations().add(eOperation3);
		eCls.getEStructuralFeatures().add(feature1);
		eCls.getEStructuralFeatures().add(feature2);
		eCls.getEStructuralFeatures().add(feature3);

		final List<EObject> result = xPathServices.siblings(feature2);

		assertEquals(5, result.size());
		assertEquals(eOperation1, result.get(0));
		assertEquals(eOperation2, result.get(1));
		assertEquals(eOperation3, result.get(2));
		assertEquals(feature1, result.get(3));
		assertEquals(feature3, result.get(4));
	}

	@Test
	public void siblingsEObjectContainerMonoValuedEReference() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);

		final List<EObject> result = xPathServices.siblings(upperBound);

		assertEquals(1, result.size());
		assertEquals(lowerBound, result.get(0));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void siblingsFilterNullNull() {
		xPathServices.siblings(null, (EClass)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void siblingsFilterEObjectNull() {
		xPathServices.siblings(EcorePackage.eINSTANCE.getEClass(), (EClass)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void siblingsFilterNullEClass() {
		xPathServices.siblings(null, EcorePackage.eINSTANCE.getEClass());
	}

	@Test
	public void siblingsFilterNoContainer() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		final List<EObject> result = xPathServices.siblings(ePkg, EcorePackage.eINSTANCE.getEClass());

		assertEquals(0, result.size());
	}

	@Test
	public void siblingsFilterResourceContainer() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		resource.getContents().add(eCls);

		final List<EObject> result = xPathServices.siblings(ePkgB, EcorePackage.eINSTANCE.getEPackage());

		assertEquals(2, result.size());
		assertEquals(ePkgA, result.get(0));
		assertEquals(ePkgC, result.get(1));
	}

	@Test
	public void siblingsFilterResourceContainerFragment() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgD = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		resource.getContents().add(eCls);
		ePkgD.getESubpackages().add(ePkgC);

		final List<EObject> result = xPathServices.siblings(ePkgB, EcorePackage.eINSTANCE.getEPackage());

		assertEquals(1, result.size());
		assertEquals(ePkgA, result.get(0));
	}

	@Test
	public void siblingsFilterEObjectContainerMultiValuedEReference() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EOperation eOperation1 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation2 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation3 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EStructuralFeature feature1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature3 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eCls.getEOperations().add(eOperation1);
		eCls.getEOperations().add(eOperation2);
		eCls.getEOperations().add(eOperation3);
		eCls.getEStructuralFeatures().add(feature1);
		eCls.getEStructuralFeatures().add(feature2);
		eCls.getEStructuralFeatures().add(feature3);

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.siblings(feature2, EcorePackage.eINSTANCE
				.getEStructuralFeature());

		assertEquals(2, result.size());
		assertEquals(feature1, result.get(0));
		assertEquals(feature3, result.get(1));
	}

	@Test
	public void siblingsFilterEObjectContainerMonoValuedEReference() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.siblings(upperBound, EcorePackage.eINSTANCE
				.getEGenericType());

		assertEquals(1, result.size());
		assertEquals(lowerBound, result.get(0));
	}

	@Test
	public void siblingsFilterEObjectContainerMonoValuedEReferenceEmptyResult() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);

		final List<EObject> result = xPathServices.siblings(upperBound, EcorePackage.eINSTANCE.getEPackage());

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void siblingsFiltersNullNull() {
		xPathServices.siblings(null, (Set<EClass>)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void siblingsFiltersEObjectNull() {
		xPathServices.siblings(EcorePackage.eINSTANCE.getEClass(), (Set<EClass>)null);
	}

	@Test
	public void siblingsFiltersEObjectContainsNull() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(null);

		final List<EObject> result = xPathServices.siblings(ePkg, filters);

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void siblingsFiltersNullEClass() {
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEClass());

		xPathServices.siblings(null, filters);
	}

	@Test
	public void siblingsFiltersNoContainer() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEClass());

		final List<EObject> result = xPathServices.siblings(ePkg, filters);

		assertEquals(0, result.size());
	}

	@Test
	public void siblingsFiltersResourceContainer() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		resource.getContents().add(eCls);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		final List<EObject> result = xPathServices.siblings(ePkgB, filters);

		assertEquals(2, result.size());
		assertEquals(ePkgA, result.get(0));
		assertEquals(ePkgC, result.get(1));
	}

	@Test
	public void siblingsFiltersResourceContainerFragment() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgD = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		resource.getContents().add(eCls);
		ePkgD.getESubpackages().add(ePkgC);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		final List<EObject> result = xPathServices.siblings(ePkgB, filters);

		assertEquals(1, result.size());
		assertEquals(ePkgA, result.get(0));
	}

	@Test
	public void siblingsFiltersEObjectContainerMultiValuedEReference() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EOperation eOperation1 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation2 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation3 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EStructuralFeature feature1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature3 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eCls.getEOperations().add(eOperation1);
		eCls.getEOperations().add(eOperation2);
		eCls.getEOperations().add(eOperation3);
		eCls.getEStructuralFeatures().add(feature1);
		eCls.getEStructuralFeatures().add(feature2);
		eCls.getEStructuralFeatures().add(feature3);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEStructuralFeature());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.siblings(feature2, filters);

		assertEquals(2, result.size());
		assertEquals(feature1, result.get(0));
		assertEquals(feature3, result.get(1));
	}

	@Test
	public void siblingsFiltersEObjectContainerMonoValuedEReference() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEGenericType());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.siblings(upperBound, filters);

		assertEquals(1, result.size());
		assertEquals(lowerBound, result.get(0));
	}

	@Test
	public void siblingsFiltersEObjectContainerMonoValuedEReferenceEmptyResult() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		final List<EObject> result = xPathServices.siblings(upperBound, filters);

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void precedingSiblingsNull() {
		xPathServices.precedingSiblings(null);
	}

	@Test
	public void precedingSiblingsNoContainer() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		final List<EObject> result = xPathServices.precedingSiblings(ePkg);

		assertEquals(0, result.size());
	}

	@Test
	public void precedingSiblingsResourceContainer() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);

		final List<EObject> result = xPathServices.precedingSiblings(ePkgB);

		assertEquals(1, result.size());
		assertEquals(ePkgA, result.get(0));
	}

	@Test
	public void precedingSiblingsResourceContainerFragment() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgD = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		ePkgD.getESubpackages().add(ePkgB);

		final List<EObject> result = xPathServices.precedingSiblings(ePkgC);

		assertEquals(1, result.size());
		assertEquals(ePkgA, result.get(0));
	}

	@Test
	public void precedingSiblingsEObjectContainerMultiValuedEReference() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EOperation eOperation1 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation2 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation3 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EStructuralFeature feature1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature3 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eCls.getEOperations().add(eOperation1);
		eCls.getEOperations().add(eOperation2);
		eCls.getEOperations().add(eOperation3);
		eCls.getEStructuralFeatures().add(feature1);
		eCls.getEStructuralFeatures().add(feature2);
		eCls.getEStructuralFeatures().add(feature3);

		final List<EObject> result = xPathServices.precedingSiblings(feature2);

		assertEquals(4, result.size());
		assertEquals(eOperation1, result.get(0));
		assertEquals(eOperation2, result.get(1));
		assertEquals(eOperation3, result.get(2));
		assertEquals(feature1, result.get(3));
	}

	@Test
	public void precedingSiblingsEObjectContainerMonoValuedEReference() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);

		final List<EObject> result = xPathServices.precedingSiblings(lowerBound);

		assertEquals(1, result.size());
		assertEquals(upperBound, result.get(0));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void precedingSiblingsFilterNullNull() {
		xPathServices.precedingSiblings(null, (EClass)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void precedingSiblingsFilterEObjectNull() {
		xPathServices.precedingSiblings(EcorePackage.eINSTANCE.getEClass(), (EClass)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void precedingSiblingsFilterNullEClass() {
		xPathServices.precedingSiblings(null, EcorePackage.eINSTANCE.getEClass());
	}

	@Test
	public void precedingSiblingsFilterNoContainer() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		final List<EObject> result = xPathServices
				.precedingSiblings(ePkg, EcorePackage.eINSTANCE.getEClass());

		assertEquals(0, result.size());
	}

	@Test
	public void precedingSiblingsFilterResourceContainer() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(eCls);
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);

		final List<EObject> result = xPathServices.precedingSiblings(ePkgB, EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(1, result.size());
		assertEquals(ePkgA, result.get(0));
	}

	@Test
	public void precedingSiblingsFilterResourceContainerFragment() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgD = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(eCls);
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		ePkgD.getESubpackages().add(ePkgB);

		final List<EObject> result = xPathServices.precedingSiblings(ePkgC, EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(1, result.size());
		assertEquals(ePkgA, result.get(0));
	}

	@Test
	public void precedingSiblingsFilterEObjectContainerMultiValuedEReference() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EOperation eOperation1 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation2 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation3 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EStructuralFeature feature1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature3 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eCls.getEOperations().add(eOperation1);
		eCls.getEOperations().add(eOperation2);
		eCls.getEOperations().add(eOperation3);
		eCls.getEStructuralFeatures().add(feature1);
		eCls.getEStructuralFeatures().add(feature2);
		eCls.getEStructuralFeatures().add(feature3);

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.precedingSiblings(feature2, EcorePackage.eINSTANCE
				.getEOperation());

		assertEquals(3, result.size());
		assertEquals(eOperation1, result.get(0));
		assertEquals(eOperation2, result.get(1));
		assertEquals(eOperation3, result.get(2));
	}

	@Test
	public void precedingSiblingsFilterEObjectContainerMonoValuedEReference() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.precedingSiblings(lowerBound, EcorePackage.eINSTANCE
				.getEGenericType());

		assertEquals(1, result.size());
		assertEquals(upperBound, result.get(0));
	}

	@Test
	public void precedingSiblingsFilterEObjectContainerMonoValuedEReferenceEmptyResult() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);

		final List<EObject> result = xPathServices.precedingSiblings(upperBound, EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void precedingSiblingsFiltersNullNull() {
		xPathServices.precedingSiblings(null, (Set<EClass>)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void precedingSiblingsFiltersEObjectNull() {
		xPathServices.precedingSiblings(EcorePackage.eINSTANCE.getEClass(), (Set<EClass>)null);
	}

	@Test
	public void precedingSiblingsFiltersEObjectContainsNull() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(null);

		final List<EObject> result = xPathServices.precedingSiblings(ePkg, filters);

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void precedingSiblingsFiltersNullEClass() {
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEClass());

		xPathServices.precedingSiblings(null, filters);
	}

	@Test
	public void precedingSiblingsFiltersNoContainer() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEClass());

		final List<EObject> result = xPathServices.precedingSiblings(ePkg, filters);

		assertEquals(0, result.size());
	}

	@Test
	public void precedingSiblingsFiltersResourceContainer() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(eCls);
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		final List<EObject> result = xPathServices.precedingSiblings(ePkgB, filters);

		assertEquals(1, result.size());
		assertEquals(ePkgA, result.get(0));
	}

	@Test
	public void precedingSiblingsFiltersResourceContainerFragment() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgD = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(eCls);
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		ePkgD.getESubpackages().add(ePkgB);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		final List<EObject> result = xPathServices.precedingSiblings(ePkgC, filters);

		assertEquals(1, result.size());
		assertEquals(ePkgA, result.get(0));
	}

	@Test
	public void precedingSiblingsFiltersEObjectContainerMultiValuedEReference() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EOperation eOperation1 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation2 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation3 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EStructuralFeature feature1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature3 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eCls.getEOperations().add(eOperation1);
		eCls.getEOperations().add(eOperation2);
		eCls.getEOperations().add(eOperation3);
		eCls.getEStructuralFeatures().add(feature1);
		eCls.getEStructuralFeatures().add(feature2);
		eCls.getEStructuralFeatures().add(feature3);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEOperation());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.precedingSiblings(feature2, filters);

		assertEquals(3, result.size());
		assertEquals(eOperation1, result.get(0));
		assertEquals(eOperation2, result.get(1));
		assertEquals(eOperation3, result.get(2));
	}

	@Test
	public void precedingSiblingsFiltersEObjectContainerMonoValuedEReference() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEGenericType());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.precedingSiblings(lowerBound, filters);

		assertEquals(1, result.size());
		assertEquals(upperBound, result.get(0));
	}

	@Test
	public void precedingSiblingsFiltersEObjectContainerMonoValuedEReferenceEmptyResult() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		final List<EObject> result = xPathServices.precedingSiblings(upperBound, filters);

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void followingSiblingsNull() {
		xPathServices.followingSiblings(null);
	}

	@Test
	public void followingSiblingsNoContainer() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		final List<EObject> result = xPathServices.followingSiblings(ePkg);

		assertEquals(0, result.size());
	}

	@Test
	public void followingSiblingsResourceContainer() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);

		final List<EObject> result = xPathServices.followingSiblings(ePkgB);

		assertEquals(1, result.size());
		assertEquals(ePkgC, result.get(0));
	}

	@Test
	public void followingSiblingsResourceContainerFragment() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgD = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		ePkgD.getESubpackages().add(ePkgC);

		final List<EObject> result = xPathServices.followingSiblings(ePkgA);

		assertEquals(1, result.size());
		assertEquals(ePkgB, result.get(0));
	}

	@Test
	public void followingSiblingsEObjectContainerMultiValuedEReference() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EOperation eOperation1 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation2 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation3 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EStructuralFeature feature1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature3 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eCls.getEOperations().add(eOperation1);
		eCls.getEOperations().add(eOperation2);
		eCls.getEOperations().add(eOperation3);
		eCls.getEStructuralFeatures().add(feature1);
		eCls.getEStructuralFeatures().add(feature2);
		eCls.getEStructuralFeatures().add(feature3);

		final List<EObject> result = xPathServices.followingSiblings(eOperation2);

		assertEquals(4, result.size());
		assertEquals(eOperation3, result.get(0));
		assertEquals(feature1, result.get(1));
		assertEquals(feature2, result.get(2));
		assertEquals(feature3, result.get(3));
	}

	@Test
	public void followingSiblingsEObjectContainerMonoValuedEReference() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);

		final List<EObject> result = xPathServices.followingSiblings(upperBound);

		assertEquals(1, result.size());
		assertEquals(lowerBound, result.get(0));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void followingSiblingsFilterNullNull() {
		xPathServices.followingSiblings(null, (EClass)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void followingSiblingsFilterEObjectNull() {
		xPathServices.followingSiblings(EcorePackage.eINSTANCE.getEClass(), (EClass)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void followingSiblingsFilterNullEClass() {
		xPathServices.followingSiblings(null, EcorePackage.eINSTANCE.getEClass());
	}

	@Test
	public void followingSiblingsFilterNoContainer() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		final List<EObject> result = xPathServices
				.followingSiblings(ePkg, EcorePackage.eINSTANCE.getEClass());

		assertEquals(0, result.size());
	}

	@Test
	public void followingSiblingsFilterResourceContainer() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		resource.getContents().add(eCls);

		final List<EObject> result = xPathServices.followingSiblings(ePkgB, EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(1, result.size());
		assertEquals(ePkgC, result.get(0));
	}

	@Test
	public void followingSiblingsFilterResourceContainerFragment() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgD = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgE = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		resource.getContents().add(ePkgE);
		resource.getContents().add(eCls);
		ePkgD.getESubpackages().add(ePkgC);

		final List<EObject> result = xPathServices.followingSiblings(ePkgB, EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(1, result.size());
		assertEquals(ePkgE, result.get(0));
	}

	@Test
	public void followingSiblingsFilterEObjectContainerMultiValuedEReference() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EOperation eOperation1 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation2 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation3 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EStructuralFeature feature1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature3 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eCls.getEOperations().add(eOperation1);
		eCls.getEOperations().add(eOperation2);
		eCls.getEOperations().add(eOperation3);
		eCls.getEStructuralFeatures().add(feature1);
		eCls.getEStructuralFeatures().add(feature2);
		eCls.getEStructuralFeatures().add(feature3);

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.followingSiblings(eOperation2, EcorePackage.eINSTANCE
				.getEStructuralFeature());

		assertEquals(3, result.size());
		assertEquals(feature1, result.get(0));
		assertEquals(feature2, result.get(1));
		assertEquals(feature3, result.get(2));
	}

	@Test
	public void followingSiblingsFilterEObjectContainerMonoValuedEReference() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.followingSiblings(upperBound, EcorePackage.eINSTANCE
				.getEGenericType());

		assertEquals(1, result.size());
		assertEquals(lowerBound, result.get(0));
	}

	@Test
	public void followingSiblingsFilterEObjectContainerMonoValuedEReferenceEmptyResult() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);

		final List<EObject> result = xPathServices.followingSiblings(upperBound, EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void followingSiblingsFiltersNullNull() {
		xPathServices.followingSiblings(null, (Set<EClass>)null);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void followingSiblingsFiltersEObjectNull() {
		xPathServices.followingSiblings(EcorePackage.eINSTANCE.getEClass(), (Set<EClass>)null);
	}

	@Test
	public void followingSiblingsFiltersEObjectContainsNull() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(null);

		final List<EObject> result = xPathServices.followingSiblings(ePkg, filters);

		assertEquals(0, result.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void followingSiblingsFiltersNullEClass() {
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEClass());

		xPathServices.followingSiblings(null, filters);
	}

	@Test
	public void followingSiblingsFiltersNoContainer() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEClass());

		final List<EObject> result = xPathServices.followingSiblings(ePkg, filters);

		assertEquals(0, result.size());
	}

	@Test
	public void followingSiblingsFiltersResourceContainer() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		resource.getContents().add(eCls);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		final List<EObject> result = xPathServices.followingSiblings(ePkgB, filters);

		assertEquals(1, result.size());
		assertEquals(ePkgC, result.get(0));
	}

	@Test
	public void followingSiblingsFiltersResourceContainerFragment() {
		final EPackage ePkgA = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgB = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgC = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgD = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EPackage ePkgE = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final Resource resource = new ResourceImpl();
		resource.getContents().add(ePkgA);
		resource.getContents().add(ePkgB);
		resource.getContents().add(ePkgC);
		resource.getContents().add(ePkgE);
		resource.getContents().add(eCls);
		ePkgD.getESubpackages().add(ePkgC);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		final List<EObject> result = xPathServices.followingSiblings(ePkgB, filters);

		assertEquals(1, result.size());
		assertEquals(ePkgE, result.get(0));
	}

	@Test
	public void followingSiblingsFiltersEObjectContainerMultiValuedEReference() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		final EOperation eOperation1 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation2 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EOperation eOperation3 = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		final EStructuralFeature feature1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		final EStructuralFeature feature3 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eCls.getEOperations().add(eOperation1);
		eCls.getEOperations().add(eOperation2);
		eCls.getEOperations().add(eOperation3);
		eCls.getEStructuralFeatures().add(feature1);
		eCls.getEStructuralFeatures().add(feature2);
		eCls.getEStructuralFeatures().add(feature3);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEStructuralFeature());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.followingSiblings(eOperation2, filters);

		assertEquals(3, result.size());
		assertEquals(feature1, result.get(0));
		assertEquals(feature2, result.get(1));
		assertEquals(feature3, result.get(2));
	}

	@Test
	public void followingSiblingsFiltersEObjectContainerMonoValuedEReference() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEGenericType());

		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final List<EObject> result = xPathServices.followingSiblings(upperBound, filters);

		assertEquals(1, result.size());
		assertEquals(lowerBound, result.get(0));
	}

	@Test
	public void followingSiblingsFiltersEObjectContainerMonoValuedEReferenceEmptyResult() {
		final EGenericType eGenType = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType lowerBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		final EGenericType upperBound = EcorePackage.eINSTANCE.getEcoreFactory().createEGenericType();
		eGenType.setELowerBound(lowerBound);
		eGenType.setEUpperBound(upperBound);
		final Set<EClass> filters = new LinkedHashSet<EClass>();
		filters.add(EcorePackage.eINSTANCE.getEPackage());

		final List<EObject> result = xPathServices.followingSiblings(upperBound, filters);

		assertEquals(0, result.size());
	}

}
