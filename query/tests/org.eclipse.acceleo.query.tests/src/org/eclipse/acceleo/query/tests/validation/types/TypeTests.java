/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.validation.types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TypeTests {
	private IQueryEnvironment queryEnvironment;

	/**
	 * Allowed by AQL.
	 * <ul>
	 * <li>byte to short, int, long, float, or double</li>
	 * <li>short to int, long, float, or double</li>
	 * <li>char to int, long, float, or double</li>
	 * <li>int to long, float, or double</li>
	 * <li>long to float or double</li>
	 * <li>float to double</li>
	 * </ul>
	 */
	private Map<TypeVariants, Set<TypeVariants>> wideningConversions;

	/**
	 * Forbidden by AQL.
	 * <ul>
	 * <li>short to byte or char</li>
	 * <li>char to byte or short</li>
	 * <li>int to byte, short, or char</li>
	 * <li>long to byte, short, char, or int</li>
	 * <li>float to byte, short, char, int, or long</li>
	 * <li>double to byte, short, char, int, long, or float</li>
	 * </ul>
	 */
	private Map<TypeVariants, Set<TypeVariants>> narrowingConversions;

	private TypeVariants byteVariants;

	private TypeVariants shortVariants;

	private TypeVariants charVariants;

	private TypeVariants integerVariants;

	private TypeVariants longVariants;

	private TypeVariants floatVariants;

	private TypeVariants doubleVariants;

	private TypeVariants booleanVariants;

	private Set<TypeVariants> allPrimitiveVariants;

	@Before
	public void before() throws Exception {
		queryEnvironment = Query.newEnvironment();

		byteVariants = new TypeVariants(classType(byte.class), classType(Byte.class));
		shortVariants = new TypeVariants(classType(short.class), classType(Short.class));
		charVariants = new TypeVariants(classType(char.class), classType(Character.class));
		integerVariants = new TypeVariants(classType(int.class), classType(Integer.class));
		longVariants = new TypeVariants(classType(long.class), classType(Long.class));
		floatVariants = new TypeVariants(classType(float.class), classType(Float.class));
		doubleVariants = new TypeVariants(classType(double.class), classType(Double.class));
		booleanVariants = new TypeVariants(classType(boolean.class), classType(Boolean.class));

		allPrimitiveVariants = new HashSet<TypeVariants>();
		allPrimitiveVariants.add(byteVariants);
		allPrimitiveVariants.add(shortVariants);
		allPrimitiveVariants.add(charVariants);
		allPrimitiveVariants.add(integerVariants);
		allPrimitiveVariants.add(longVariants);
		allPrimitiveVariants.add(floatVariants);
		allPrimitiveVariants.add(doubleVariants);
		allPrimitiveVariants.add(booleanVariants);

		wideningConversions = new HashMap<TypeVariants, Set<TypeVariants>>();
		wideningConversions.put(byteVariants, newSet(shortVariants, integerVariants, longVariants,
				floatVariants, doubleVariants));
		wideningConversions.put(shortVariants, newSet(integerVariants, longVariants, floatVariants,
				doubleVariants));
		wideningConversions.put(charVariants, newSet(integerVariants, longVariants, floatVariants,
				doubleVariants));
		wideningConversions.put(integerVariants, newSet(longVariants, floatVariants, doubleVariants));
		wideningConversions.put(longVariants, newSet(floatVariants, doubleVariants));
		wideningConversions.put(floatVariants, newSet(doubleVariants));

		narrowingConversions = new HashMap<TypeVariants, Set<TypeVariants>>();
		narrowingConversions.put(shortVariants, newSet(byteVariants, charVariants));
		narrowingConversions.put(charVariants, newSet(byteVariants, shortVariants));
		narrowingConversions.put(integerVariants, newSet(byteVariants, shortVariants, charVariants));
		narrowingConversions.put(longVariants, newSet(byteVariants, shortVariants, charVariants,
				integerVariants));
		narrowingConversions.put(floatVariants, newSet(byteVariants, shortVariants, charVariants,
				integerVariants, longVariants));
		narrowingConversions.put(doubleVariants, newSet(byteVariants, shortVariants, charVariants,
				integerVariants, longVariants, floatVariants));
	}

	private void addEcoreDataTypesAndClassifiersToVariants() {
		byteVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEByte()));
		byteVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEByteObject()));
		shortVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEShort()));
		shortVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEShortObject()));
		charVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEChar()));
		charVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getECharacterObject()));
		integerVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEInt()));
		integerVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEIntegerObject()));
		longVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getELong()));
		longVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getELongObject()));
		floatVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEFloat()));
		floatVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEFloatObject()));
		doubleVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEDouble()));
		doubleVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEDoubleObject()));
		booleanVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEBoolean()));
		booleanVariants.addVariations(eClassifierType(EcorePackage.eINSTANCE.getEBooleanObject()));
	}

	@Test
	public void testVariantsAssignableToEachOther() {
		for (TypeVariants variant : allPrimitiveVariants) {
			for (IType fromType : variant.getVariants()) {
				Set<IType> others = new HashSet<IType>(variant.getVariants());
				others.remove(fromType);
				for (IType toType : others) {
					assertTrue(toType.getType() + " should have been assignable from " + fromType.getType(),
							toType.isAssignableFrom(fromType));
				}
			}
		}
	}

	@Test
	public void testVariantsAssignableToEachOtherWithEcore() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		addEcoreDataTypesAndClassifiersToVariants();

		testVariantsAssignableToEachOther();
	}

	@Test
	public void testVariantsAssignableToEachOtherWithUnregisteredEcore() {
		addEcoreDataTypesAndClassifiersToVariants();

		for (TypeVariants variant : allPrimitiveVariants) {
			for (IType fromType : variant.getVariants()) {
				Set<IType> others = new HashSet<IType>(variant.getVariants());
				others.remove(fromType);
				for (IType toType : others) {
					if (toType instanceof EClassifierType || fromType instanceof EClassifierType) {
						assertFalse(toType.getType() + " should not have been assignable from " + fromType
								.getType() + " as ecore is not registered in the environment.", toType
										.isAssignableFrom(fromType));
					} else {
						assertTrue(toType.getType() + " should have been assignable from " + fromType
								.getType(), toType.isAssignableFrom(fromType));
					}
				}
			}
		}
	}

	@Test
	public void testNarrowingConversions() {
		for (Map.Entry<TypeVariants, Set<TypeVariants>> entry : narrowingConversions.entrySet()) {
			for (IType fromType : entry.getKey().getVariants()) {
				assertFalse(entry.getValue().isEmpty());
				for (TypeVariants toVariant : entry.getValue()) {
					assertFalse(toVariant.getVariants().isEmpty());
					for (IType toType : toVariant.getVariants()) {
						assertFalse(toType.getType() + " should not have been assignable from " + fromType
								.getType(), toType.isAssignableFrom(fromType));
					}
				}
			}
		}
	}

	@Test
	public void testNarrowingConversionsWithEcore() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		addEcoreDataTypesAndClassifiersToVariants();

		testNarrowingConversions();
	}

	@Test
	public void testNarrowingConversionsWithUnregisteredEcore() {
		addEcoreDataTypesAndClassifiersToVariants();

		testNarrowingConversions();
	}

	@Test
	public void testAssignableFromBoolean() {
		for (TypeVariants toVariant : allPrimitiveVariants) {
			if (booleanVariants == toVariant) {
				continue;
			}

			for (IType fromType : booleanVariants.getVariants()) {
				for (IType toType : toVariant.getVariants()) {
					assertFalse(toType.getType() + " should not have been assignable from " + fromType
							.getType(), toType.isAssignableFrom(fromType));
				}
			}
		}
	}

	@Test
	public void testAssignableFromBooleanWithEcore() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		addEcoreDataTypesAndClassifiersToVariants();

		testAssignableFromBoolean();
	}

	@Test
	public void testAssignableFromBooleanWithUnregisteredEcore() {
		addEcoreDataTypesAndClassifiersToVariants();

		testAssignableFromBoolean();
	}

	@Test
	public void testAssignableToBoolean() {
		for (TypeVariants fromVariant : allPrimitiveVariants) {
			if (booleanVariants == fromVariant) {
				continue;
			}

			for (IType toType : booleanVariants.getVariants()) {
				for (IType fromType : fromVariant.getVariants()) {
					assertFalse(toType.getType() + " should not have been assignable from " + fromType
							.getType(), toType.isAssignableFrom(fromType));
				}
			}
		}
	}

	@Test
	public void testAssignableToBooleanWithEcore() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		addEcoreDataTypesAndClassifiersToVariants();

		testAssignableToBoolean();
	}

	@Test
	public void testAssignableToBooleanWithUnregisteredEcore() {
		addEcoreDataTypesAndClassifiersToVariants();

		testAssignableToBoolean();
	}

	@Test
	public void testAssignableFromPrimitiveToEClass() {
		EClassifierType toType = eClassifierType(EcorePackage.eINSTANCE.getEClass());

		for (TypeVariants primitive : allPrimitiveVariants) {
			for (IType fromType : primitive.getVariants()) {
				assertFalse(toType.getType() + " should not have been assignable from " + fromType.getType(),
						toType.isAssignableFrom(fromType));
			}
		}
	}

	@Test
	public void testAssignableFromPrimitiveToEClassWithEcore() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		addEcoreDataTypesAndClassifiersToVariants();

		testAssignableFromPrimitiveToEClass();
	}

	@Test
	public void testAssignableFromPrimitiveToEClassWithUnregisteredEcore() {
		addEcoreDataTypesAndClassifiersToVariants();

		testAssignableFromPrimitiveToEClass();
	}

	@Test
	public void testAssignableFromEClassToPrimitive() {
		EClassifierType fromType = eClassifierType(EcorePackage.eINSTANCE.getEClass());

		for (TypeVariants primitive : allPrimitiveVariants) {
			for (IType toType : primitive.getVariants()) {
				assertFalse(toType.getType() + " should not have been assignable from " + fromType.getType(),
						toType.isAssignableFrom(fromType));
			}
		}
	}

	@Test
	public void testAssignableFromEClassToPrimitiveWithEcore() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		addEcoreDataTypesAndClassifiersToVariants();

		testAssignableFromEClassToPrimitive();
	}

	@Test
	public void testAssignableFromEClassToPrimitiveWithUnregisteredEcore() {
		addEcoreDataTypesAndClassifiersToVariants();

		testAssignableFromEClassToPrimitive();
	}

	@Test
	public void testAssignableFromEClassAndEPackage() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		IType eClass = eClassifierType(EcorePackage.eINSTANCE.getEClass());
		IType ePackage = eClassifierType(EcorePackage.eINSTANCE.getEPackage());

		assertFalse(eClass.isAssignableFrom(ePackage));
		assertFalse(ePackage.isAssignableFrom(eClass));
		assertTrue(eClass.isAssignableFrom(eClass));
		assertTrue(ePackage.isAssignableFrom(ePackage));
	}

	@Test
	public void testAssignableFromEClassAndEPackageUnregistered() {
		IType eClass = eClassifierType(EcorePackage.eINSTANCE.getEClass());
		IType ePackage = eClassifierType(EcorePackage.eINSTANCE.getEPackage());

		assertFalse(eClass.isAssignableFrom(ePackage));
		assertFalse(ePackage.isAssignableFrom(eClass));
		assertFalse(eClass.isAssignableFrom(eClass));
		assertFalse(ePackage.isAssignableFrom(ePackage));
	}

	@Test
	public void testAssignableFromUniversalObject() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		IType universal = classType(Object.class);
		IType ePackage = eClassifierType(EcorePackage.eINSTANCE.getEPackage());
		IType eClass = eClassifierType(EcorePackage.eINSTANCE.getEClass());
		IType otherUniversalInstance = classType(Object.class);

		assertFalse(ePackage.isAssignableFrom(universal));
		assertTrue(universal.isAssignableFrom(ePackage));

		assertFalse(eClass.isAssignableFrom(universal));
		assertTrue(universal.isAssignableFrom(eClass));

		assertTrue(otherUniversalInstance.isAssignableFrom(universal));

		for (TypeVariants primitiveVariants : allPrimitiveVariants) {
			for (IType variant : primitiveVariants.getVariants()) {
				assertFalse(variant.isAssignableFrom(universal));
				assertTrue(universal.isAssignableFrom(variant));
			}
		}
	}

	@Test
	public void testAssignableFromNullToEClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final EClassifierType toType = eClassifierType(EcorePackage.eINSTANCE.getEClass());
		final ClassType fromType = classType(null);

		assertTrue(toType.getType() + " should have been assignable from " + fromType.getType(), toType
				.isAssignableFrom(fromType));
	}

	@Test
	public void testAssignableFromNullToObject() {
		final ClassType toType = classType(Object.class);
		final ClassType fromType = classType(null);

		assertTrue(toType.getType() + " should have been assignable from " + fromType.getType(), toType
				.isAssignableFrom(fromType));
	}

	@Test
	public void testAssignableFromEClassToNull() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final ClassType toType = classType(null);
		final EClassifierType fromType = eClassifierType(EcorePackage.eINSTANCE.getEClass());

		assertFalse(toType.getType() + " should not have been assignable from " + fromType.getType(), toType
				.isAssignableFrom(fromType));
	}

	@Test
	public void testAssignableFromObjectToNull() {
		final ClassType toType = classType(null);
		final ClassType fromType = classType(Object.class);

		assertFalse(toType.getType() + " should not have been assignable from " + fromType.getType(), toType
				.isAssignableFrom(fromType));
	}

	@Test
	public void testAssignableFromNullToNull() {
		final ClassType toType = classType(null);
		final ClassType fromType = classType(null);

		assertFalse(toType.getType() + " should not have been assignable from " + fromType.getType(), toType
				.isAssignableFrom(fromType));
	}

	@Test
	public void collectionTypeAssignable() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

		final IType sequenceEClassifier = new SequenceType(getQueryEnvironment(), new EClassifierType(
				getQueryEnvironment(), EcorePackage.eINSTANCE.getEClassifier()));
		final IType sequenceEClass = new SequenceType(getQueryEnvironment(), new EClassifierType(
				getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));
		final IType setEClassifier = new SetType(getQueryEnvironment(), new EClassifierType(
				getQueryEnvironment(), EcorePackage.eINSTANCE.getEClassifier()));
		final IType setEClass = new SetType(getQueryEnvironment(), new EClassifierType(getQueryEnvironment(),
				EcorePackage.eINSTANCE.getEClass()));
		final IType javaList = new ClassType(getQueryEnvironment(), List.class);
		final IType javaSet = new ClassType(getQueryEnvironment(), Set.class);

		assertEquals(true, sequenceEClassifier.isAssignableFrom(sequenceEClassifier));
		assertEquals(true, sequenceEClassifier.isAssignableFrom(sequenceEClass));
		assertEquals(false, sequenceEClass.isAssignableFrom(sequenceEClassifier));
		assertEquals(true, sequenceEClass.isAssignableFrom(javaList));
		assertEquals(false, sequenceEClass.isAssignableFrom(javaSet));

		assertEquals(true, setEClassifier.isAssignableFrom(setEClassifier));
		assertEquals(true, setEClassifier.isAssignableFrom(setEClass));
		assertEquals(false, setEClass.isAssignableFrom(setEClassifier));
		assertEquals(false, setEClass.isAssignableFrom(javaList));
		assertEquals(true, setEClass.isAssignableFrom(javaSet));

		assertEquals(false, sequenceEClassifier.isAssignableFrom(setEClassifier));
		assertEquals(false, setEClassifier.isAssignableFrom(sequenceEClassifier));

		assertEquals(true, javaList.isAssignableFrom(sequenceEClassifier));
		assertEquals(true, javaList.isAssignableFrom(sequenceEClass));
		assertEquals(true, javaSet.isAssignableFrom(setEClassifier));
		assertEquals(true, javaSet.isAssignableFrom(setEClass));
	}

	protected IQueryEnvironment getQueryEnvironment() {
		return queryEnvironment;
	}

	protected ClassType classType(Class<?> cls) {
		return new ClassType(getQueryEnvironment(), cls);
	}

	protected EClassifierType eClassifierType(EClassifier eClassifier) {
		return new EClassifierType(getQueryEnvironment(), eClassifier);
	}

	private static <T> Set<T> newSet(@SuppressWarnings("unchecked") T... elements) {
		Set<T> set = new LinkedHashSet<T>();
		set.addAll(Arrays.asList(elements));
		return set;
	}

	private static class TypeVariants {
		private Set<IType> variants;

		public TypeVariants(IType... variants) {
			this.variants = new HashSet<IType>();
			this.variants.addAll(Arrays.asList(variants));
		}

		public void addVariations(IType... newVariants) {
			this.variants.addAll(Arrays.asList(newVariants));
		}

		public Set<IType> getVariants() {
			return variants;
		}
	}
}
