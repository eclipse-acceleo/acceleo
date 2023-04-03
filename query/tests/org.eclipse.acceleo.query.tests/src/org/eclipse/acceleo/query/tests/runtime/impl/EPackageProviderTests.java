/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.runtime.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IEPackageProvider;
import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link EPackageProvider}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@RunWith(Parameterized.class)
public class EPackageProviderTests {

	public static interface ITestEPackageProvider extends IEPackageProvider {

		void registerCustomClassMapping(EClassifier eClassifier, Class<?> cls);

		EPackage registerPackage(EPackage ePackage);

		Collection<EPackage> removePackage(EPackage ePackage);

	}

	public static class TestEPackageProvider extends EPackageProvider implements ITestEPackageProvider {

	}

	private final Class<ITestEPackageProvider> cls;

	private ITestEPackageProvider provider;

	public EPackageProviderTests(Class<ITestEPackageProvider> cls) {
		this.cls = cls;
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> classes() {
		return Arrays.asList(new Object[][] {{TestEPackageProvider.class, }, });
	}

	@Before
	public void before() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		provider = cls.getDeclaredConstructor().newInstance();
	}

	@Test
	public void getEPackageNull() {

		assertEquals(0, provider.getEPackage(null).size());
	}

	@Test
	public void getEPackageNotRegistered() {

		assertEquals(0, provider.getEPackage("ecore").size());
	}

	@Test
	public void getEPackageRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final EPackage ePkg = provider.getEPackage("ecore").iterator().next();

		assertEquals(EcorePackage.eINSTANCE, ePkg);
	}

	@Test
	public void getTypeNull() {
		final Collection<EClassifier> eClassifier = provider.getTypes(null, null);

		assertEquals(0, eClassifier.size());
	}

	@Test
	public void getTypeNotRegistered() {
		assertEquals(0, provider.getTypes("ecore", "EOperation").size());
	}

	@Test
	public void getTypeRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final EClassifier eClassifier = provider.getTypes("ecore", "EOperation").iterator().next();

		assertEquals(EcorePackage.eINSTANCE.getEOperation(), eClassifier);
	}

	@Test
	public void getEnumLiteralNull() {
		assertEquals(0, provider.getEnumLiterals(null, null, null).size());
	}

	@Test
	public void getEnumLiteralNotRegistered() {

		assertEquals(0, provider.getEnumLiterals("anydsl", "Color", "red").size());
	}

	@Test
	public void getEnumLiteralRegistered() {
		provider.registerPackage(AnydslPackage.eINSTANCE);

		final EEnumLiteral eNum = provider.getEnumLiterals("anydsl", "Color", "red").iterator().next();

		assertEquals(AnydslPackage.eINSTANCE.getColor().getEEnumLiteral("red"), eNum);
	}

	@Test
	public void getEClassNull() {
		final Set<EClassifier> eClasses = provider.getEClassifiers(null);

		assertEquals(null, eClasses);
	}

	@Test
	public void getEClassNotRegistered() {
		final Set<EClassifier> eClasses = provider.getEClassifiers(EObject.class);

		assertEquals(null, eClasses);
	}

	@Test
	public void getEClassRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClassifier> eClasses = provider.getEClassifiers(EObject.class);

		assertEquals(1, eClasses.size());
		assertEquals(EcorePackage.eINSTANCE.getEObject(), eClasses.iterator().next());
	}

	@Test
	public void getClassNull() {
		Class<?> cls = provider.getClass(null);

		assertEquals(null, cls);
	}

	@Test
	public void getClassNotRegistered() {
		Class<?> cls = provider.getClass(EcorePackage.eINSTANCE.getEClass());

		assertEquals(null, cls);
	}

	@Test
	public void getClassRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		Class<?> cls = provider.getClass(EcorePackage.eINSTANCE.getEClass());

		assertEquals(EClass.class, cls);
	}

	@Test
	public void isRegisteredNull() {
		assertFalse(provider.isRegistered(null));
	}

	@Test
	public void isRegisteredNotRegistered() {
		assertFalse(provider.isRegistered(EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void isRegisteredRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		assertTrue(provider.isRegistered(EcorePackage.eINSTANCE.getEClass()));
	}

	@Test
	public void getRegisteredEPackagesNothingRegistered() {
		final Set<EPackage> ePkgs = provider.getRegisteredEPackages();

		assertEquals(0, ePkgs.size());
	}

	@Test
	public void getRegisteredEPackages() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EPackage> ePkgs = provider.getRegisteredEPackages();

		assertEquals(1, ePkgs.size());
		final Iterator<EPackage> it = ePkgs.iterator();
		assertEquals(EcorePackage.eINSTANCE, it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getEStructuralFeaturesNull() {
		provider.getEStructuralFeatures(null);
	}

	@Test
	public void getEStructuralFeaturesNotRegistered() {
		final Set<EClass> eClasses = new LinkedHashSet<EClass>();
		eClasses.add(EcorePackage.eINSTANCE.getEClass());

		Set<EStructuralFeature> features = provider.getEStructuralFeatures(eClasses);

		assertEquals(0, features.size());
	}

	@Test
	public void getEStructuralFeaturesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = new LinkedHashSet<EClass>();
		eClasses.add(EcorePackage.eINSTANCE.getEClass());

		Set<EStructuralFeature> features = provider.getEStructuralFeatures(eClasses);

		assertEquals(24, features.size());
	}

	@Test
	public void getEClassifiersNothingRegistered() {
		final Set<EClassifier> eClassifiers = provider.getEClassifiers();

		assertEquals(0, eClassifiers.size());
	}

	@Test
	public void getEClassifiers() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClassifier> eClassifiers = provider.getEClassifiers();

		assertEquals(53, eClassifiers.size());
	}

	@Test
	public void getEEnumLiteralsNothingRegistered() {
		final Set<EEnumLiteral> eEnumLiterals = provider.getEEnumLiterals();

		assertEquals(0, eEnumLiterals.size());
	}

	@Test
	public void getEEnumLiterals() {
		provider.registerPackage(AnydslPackage.eINSTANCE);

		final Set<EEnumLiteral> eEnumLiterals = provider.getEEnumLiterals();

		assertEquals(42, eEnumLiterals.size());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getAllContainingEClassesNull() {
		provider.getAllContainingEClasses(null);
	}

	@Test
	public void getAllContainingEClassesNotRegistered() {
		final Set<EClass> eClasses = provider.getAllContainingEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getAllContainingEClassesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getAllContainingEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(7, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEModelElement(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEEnum(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClass(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEOperation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), it.next());
	}

	@Test
	public void getAllSubTypesNull() {
		final Set<EClass> eClasses = provider.getAllSubTypes(null);

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getAllSubTypesNotRegistered() {
		final Set<EClass> eClasses = provider.getAllSubTypes(EcorePackage.eINSTANCE.getEClassifier());

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getAllSubTypesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getAllSubTypes(EcorePackage.eINSTANCE.getEClassifier());

		assertEquals(3, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEClass(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEDataType(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEEnum(), it.next());
	}

	@Test
	public void getContainedEClassesNull() {
		final Set<EClass> eClasses = provider.getContainedEClasses(null);

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getContainedEClassesNotRegistered() {
		final Set<EClass> eClasses = provider.getContainedEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getContainedEClassesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getContainedEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(3, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
	}

	@Test
	public void getAllContainedEClassesNull() {
		final Set<EClass> eClasses = provider.getAllContainedEClasses(null);

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getAllContainedEClassesNotRegistered() {
		final Set<EClass> eClasses = provider.getAllContainedEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getAllContainedEClassesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getAllContainedEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(11, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEStringToStringMapEntry(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEObject(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getETypeParameter(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEOperation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEStructuralFeature(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEGenericType(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEEnumLiteral(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEParameter(), it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getInverseEClassesNull() {
		provider.getInverseEClasses(null);
	}

	@Test
	public void getInverseEClassesNotRegistered() {
		final Set<EClass> eClasses = provider.getInverseEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getInverseEClassesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getInverseEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(4, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEFactory(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), it.next());
	}

	@Test
	public void getInverseEClassesRegisteredWithSubClasses() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getInverseEClasses(EcorePackage.eINSTANCE.getEClassifier());

		assertEquals(10, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEOperation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getETypedElement(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEGenericType(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClass(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEReference(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEStructuralFeature(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEAttribute(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEEnumLiteral(), it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getFollowingSiblingsEClassesNull() {
		provider.getFollowingSiblingsEClasses(null);
	}

	@Test
	public void getFollowingSiblingsEClassesNotRegistered() {
		final Set<EClass> eClasses = provider.getFollowingSiblingsEClasses(EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getFollowingSiblingsEClassesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getFollowingSiblingsEClasses(EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(2, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEObject(), it.next());
	}

	@Test
	public void getFollowingSiblingsEClassesRegisteredWithSubClasses() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getFollowingSiblingsEClasses(EcorePackage.eINSTANCE
				.getENamedElement());

		assertEquals(9, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEEnumLiteral(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEOperation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEStructuralFeature(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEGenericType(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEParameter(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getETypeParameter(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEObject(), it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getPrecedingSiblingsEClassesNull() {
		provider.getPrecedingSiblingsEClasses(null);
	}

	@Test
	public void getPrecedingSiblingsEClassesNotRegistered() {
		final Set<EClass> eClasses = provider.getPrecedingSiblingsEClasses(EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getPrecedingSiblingsEClassesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getPrecedingSiblingsEClasses(EcorePackage.eINSTANCE
				.getEPackage());

		assertEquals(5, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEStringToStringMapEntry(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEObject(), it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getSiblingsEClassesNull() {
		provider.getSiblingsEClasses(null);
	}

	@Test
	public void getSiblingsEClassesNotRegistered() {
		final Set<EClass> eClasses = provider.getSiblingsEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(0, eClasses.size());
	}

	@Test
	public void getSiblingsEClassesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EClass> eClasses = provider.getSiblingsEClasses(EcorePackage.eINSTANCE.getEPackage());

		assertEquals(5, eClasses.size());
		final Iterator<EClass> it = eClasses.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClassifier(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEStringToStringMapEntry(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEObject(), it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getContainingEStructuralFeaturesNull() {
		provider.getContainingEStructuralFeatures(null);
	}

	@Test
	public void getContainingEStructuralFeaturesNotRegistered() {
		final Set<EStructuralFeature> features = provider.getContainingEStructuralFeatures(
				EcorePackage.eINSTANCE.getEPackage());

		assertEquals(0, features.size());
	}

	@Test
	public void getContainingEStructuralFeaturesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EStructuralFeature> features = provider.getContainingEStructuralFeatures(
				EcorePackage.eINSTANCE.getEPackage());

		assertEquals(2, features.size());
		final Iterator<EStructuralFeature> it = features.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEPackage_ESubpackages(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation_Contents(), it.next());
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getAllContainingEStructuralFeaturesNull() {
		provider.getAllContainingEStructuralFeatures(null);
	}

	@Test
	public void getAllContainingEStructuralFeaturesNotRegistered() {
		final Set<EStructuralFeature> features = provider.getAllContainingEStructuralFeatures(
				EcorePackage.eINSTANCE.getEPackage());

		assertEquals(0, features.size());
	}

	@Test
	public void getAllContainingEStructuralFeaturesRegistered() {
		provider.registerPackage(EcorePackage.eINSTANCE);

		final Set<EStructuralFeature> features = provider.getAllContainingEStructuralFeatures(
				EcorePackage.eINSTANCE.getEPackage());

		assertEquals(10, features.size());
		final Iterator<EStructuralFeature> it = features.iterator();
		assertEquals(EcorePackage.eINSTANCE.getEPackage_ESubpackages(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEAnnotation_Contents(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEModelElement_EAnnotations(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEPackage_EClassifiers(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEEnum_ELiterals(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClass_EOperations(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEOperation_EParameters(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClass_EStructuralFeatures(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEClassifier_ETypeParameters(), it.next());
		assertEquals(EcorePackage.eINSTANCE.getEOperation_ETypeParameters(), it.next());
	}
}
