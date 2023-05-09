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

import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

public class CollectionServicesValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(),
				CollectionServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void testAddListNothingString() {
		final NothingType nothingType = nothingType("Empty");
		final IType[] parameterTypes = new IType[] {sequenceType(nothingType), sequenceType(classType(
				String.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType), sequenceType(classType(
				String.class)) };
		final IType[] expectedAllReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, expectedAllReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAddListStringNothing() {
		final NothingType nothingType = nothingType("Empty");
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				nothingType) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				nothingType) };
		final IType[] expectedAllReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, expectedAllReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAddList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAddSetNothingString() {
		final NothingType nothingType = nothingType("Empty");
		final IType[] parameterTypes = new IType[] {setType(nothingType), setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(nothingType), setType(classType(
				String.class)) };
		final IType[] expectedAllReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, expectedAllReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAddSetStringNothing() {
		final NothingType nothingType = nothingType("Empty");
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), setType(nothingType) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)), setType(
				nothingType) };
		final IType[] expectedAllReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, expectedAllReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAddSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), setType(classType(
				Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)), setType(classType(
				Integer.class)) };

		assertValidation(expectedReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAnyNoBooleanLambda() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType(
				"expression in an any must return a boolean") };

		assertValidation(expectedReturnTypes, "any", parameterTypes);
	}

	@Test
	public void testAnySet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {setType(classType(String.class)), lambdaType("i",
					classType(String.class), eClassifierType(EcorePackage.eINSTANCE.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

			assertValidation(expectedReturnTypes, "any", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAnyList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
					classType(String.class), eClassifierType(EcorePackage.eINSTANCE.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

			assertValidation(expectedReturnTypes, "any", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAsOrderedSetSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "asOrderedSet", parameterTypes);
	}

	@Test
	public void testAsOrderedSetList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "asOrderedSet", parameterTypes);
	}

	@Test
	public void testAsSequenceSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "asSequence", parameterTypes);
	}

	@Test
	public void testAsSequenceList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "asSequence", parameterTypes);
	}

	@Test
	public void testAsSetSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "asSet", parameterTypes);
	}

	@Test
	public void testAsSetList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "asSet", parameterTypes);
	}

	@Test
	public void testAtList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "at", parameterTypes);
	}

	@Test
	public void testAtSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "at", parameterTypes);
	}

	@Test
	public void testSortedBySet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "sortedBy", parameterTypes);
	}

	@Test
	public void testSortedByList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "sortedBy", parameterTypes);
	}

	@Test
	public void testCollectSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "collect", parameterTypes);
	}

	@Test
	public void testCollectList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "collect", parameterTypes);
	}

	@Test
	public void testConcat() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "concat", parameterTypes);
	}

	@Test
	public void testCountSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), sequenceType(classType(
				Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "count", parameterTypes);
	}

	@Test
	public void testCountList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "count", parameterTypes);
	}

	@Test
	public void testExcludesSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), sequenceType(classType(
				Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "excludes", parameterTypes);
	}

	@Test
	public void testExcludesList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "excludes", parameterTypes);
	}

	@Test
	public void testIncludesSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), sequenceType(classType(
				Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "includes", parameterTypes);
	}

	@Test
	public void testIncludesList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "includes", parameterTypes);
	}

	@Test
	public void testIncludingList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "including", parameterTypes);
	}

	@Test
	public void testIncludingSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)), setType(classType(
				Integer.class)) };

		assertValidation(expectedReturnTypes, "including", parameterTypes);
	}

	@Test
	public void testReverseList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "reverse", parameterTypes);
	}

	@Test
	public void testReverseSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "reverse", parameterTypes);
	}

	@Test
	public void testIndexOfList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "indexOf", parameterTypes);
	}

	@Test
	public void testIndexOfSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "indexOf", parameterTypes);
	}

	@Test
	public void testLastIndexOfList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "lastIndexOf", parameterTypes);
	}

	@Test
	public void testLastIndexOfSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "lastIndexOf", parameterTypes);
	}

	@Test
	public void testInsertAt() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Double.class)) };

		assertValidation(expectedReturnTypes, "insertAt", parameterTypes);
	}

	@Test
	public void testIntersectionSetSetNothingLeft() {
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

			final IType[] parameterTypes = new IType[] {setType(eClassifierType(eCls1)), setType(
					eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {setType(nothingType(
					"Nothing left after intersection of Set(EClassifier=eCls1) and Set(EClassifier=eCls2)")) };
			final IType[] expectedAllReturnTypes = new IType[] {setType(nothingType(
					"Nothing left after intersection:\n Nothing left after intersection of Set(EClassifier=eCls1) and Set(EClassifier=eCls2)")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testIntersectionSetSetTopSubType() {
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

			final IType[] parameterTypes = new IType[] {setType(eClassifierType(eCls1)), setType(
					eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(eCls3)) };

			assertValidation(expectedReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testIntersectionSetSetSameType() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), setType(classType(
				String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionSetSetEClassEClassifier() {
		final IType[] parameterTypes = new IType[] {setType(classType(EClass.class)), setType(classType(
				EClassifier.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionSetSetEClassifierEClass() {
		final IType[] parameterTypes = new IType[] {setType(classType(EClassifier.class)), setType(classType(
				EClass.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionListListNothingLeft() {
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

			final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(eCls1)), sequenceType(
					eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"Nothing left after intersection of Sequence(EClassifier=eCls1) and Sequence(EClassifier=eCls2)")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType(
					"Nothing left after intersection:\n Nothing left after intersection of Sequence(EClassifier=eCls1) and Sequence(EClassifier=eCls2)")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testIntersectionListListTopSubType() {
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

			final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(eCls1)), sequenceType(
					eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(eCls3)) };

			assertValidation(expectedReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testIntersectionListListSameType() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionListListEClassEClassifier() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(EClass.class)), sequenceType(
				classType(EClassifier.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionListListEClassifierEClass() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(EClassifier.class)), sequenceType(
				classType(EClass.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionListSetNothingLeft() {
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

			final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(eCls1)), setType(
					eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"Nothing left after intersection of Sequence(EClassifier=eCls1) and Set(EClassifier=eCls2)")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType(
					"Nothing left after intersection:\n Nothing left after intersection of Sequence(EClassifier=eCls1) and Set(EClassifier=eCls2)")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testIntersectionListSetTopSubType() {
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

			final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(eCls1)), setType(
					eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(eCls3)) };

			assertValidation(expectedReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testIntersectionListSetSameType() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), setType(classType(
				String.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionListSetEClassEClassifier() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(EClass.class)), setType(classType(
				EClassifier.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionListSetEClassifierEClass() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(EClassifier.class)), setType(
				classType(EClass.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionSetListNothingLeft() {
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

			final IType[] parameterTypes = new IType[] {setType(eClassifierType(eCls1)), sequenceType(
					eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {setType(nothingType(
					"Nothing left after intersection of Set(EClassifier=eCls1) and Sequence(EClassifier=eCls2)")) };
			final IType[] expectedAllReturnTypes = new IType[] {setType(nothingType(
					"Nothing left after intersection:\n Nothing left after intersection of Set(EClassifier=eCls1) and Sequence(EClassifier=eCls2)")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testIntersectionSetListTopSubType() {
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

			final IType[] parameterTypes = new IType[] {setType(eClassifierType(eCls1)), sequenceType(
					eClassifierType(eCls2)) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(eCls3)) };

			assertValidation(expectedReturnTypes, "intersection", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testIntersectionSetListSameType() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), sequenceType(classType(
				String.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionSetListEClassEClassifier() {
		final IType[] parameterTypes = new IType[] {setType(classType(EClass.class)), sequenceType(classType(
				EClassifier.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIntersectionSetListEClassifierEClass() {
		final IType[] parameterTypes = new IType[] {setType(classType(EClassifier.class)), sequenceType(
				classType(EClass.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(EClass.class)) };

		assertValidation(expectedReturnTypes, "intersection", parameterTypes);
	}

	@Test
	public void testIsEmptyList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isEmpty", parameterTypes);
	}

	@Test
	public void testIsEmptySet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isEmpty", parameterTypes);
	}

	@Test
	public void testIsUniqueSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Object.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isUnique", parameterTypes);
	}

	@Test
	public void testIsUniqueList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Object.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isUnique", parameterTypes);
	}

	@Test
	public void testLast() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "last", parameterTypes);
	}

	@Test
	public void testNotEmptyList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "notEmpty", parameterTypes);
	}

	@Test
	public void testNotEmptySet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "notEmpty", parameterTypes);
	}

	@Test
	public void testOneNoBooleanLambda() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType(
				"expression in one must return a boolean") };

		assertValidation(expectedReturnTypes, "one", parameterTypes);
	}

	@Test
	public void testOneSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {setType(classType(String.class)), lambdaType("i",
					classType(String.class), eClassifierType(EcorePackage.eINSTANCE.getEBooleanObject())) };
			final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

			assertValidation(expectedReturnTypes, "one", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testOneList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
					classType(String.class), eClassifierType(EcorePackage.eINSTANCE.getEBooleanObject())) };
			final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

			assertValidation(expectedReturnTypes, "one", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAppend() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "append", parameterTypes);
	}

	@Test
	public void testPrepend() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "prepend", parameterTypes);
	}

	@Test
	public void testRejectNoBooleanLambda() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
				"expression in a reject must return a boolean")) };

		assertValidation(expectedReturnTypes, "reject", parameterTypes);
	}

	@Test
	public void testRejectSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {setType(classType(String.class)), lambdaType("i",
					classType(String.class), eClassifierType(EcorePackage.eINSTANCE.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

			assertValidation(expectedReturnTypes, "reject", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testRejectList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
					classType(String.class), eClassifierType(EcorePackage.eINSTANCE.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

			assertValidation(expectedReturnTypes, "reject", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testSelectNoBooleanLambda() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
				classType(String.class), classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
				"expression in a select must return a boolean")) };

		assertValidation(expectedReturnTypes, "select", parameterTypes);
	}

	@Test
	public void testSelectSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {setType(classType(String.class)), lambdaType("i",
					classType(String.class), eClassifierType(EcorePackage.eINSTANCE.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

			assertValidation(expectedReturnTypes, "select", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testSelectList() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), lambdaType("i",
					classType(String.class), eClassifierType(EcorePackage.eINSTANCE.getEBoolean())) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

			assertValidation(expectedReturnTypes, "select", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testSep2List() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "sep", parameterTypes);
	}

	@Test
	public void testSep2Set() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "sep", parameterTypes);
	}

	@Test
	public void testSep4List() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class), classType(String.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)), sequenceType(classType(Double.class)) };

		assertValidation(expectedReturnTypes, "sep", parameterTypes);
	}

	@Test
	public void testSep4Set() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), classType(
				Integer.class), classType(String.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)), sequenceType(classType(Double.class)) };

		assertValidation(expectedReturnTypes, "sep", parameterTypes);
	}

	@Test
	public void testSizeList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "size", parameterTypes);
	}

	@Test
	public void testSizeSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "size", parameterTypes);
	}

	@Test
	public void testSubList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "sub", parameterTypes);
	}

	@Test
	public void testSubSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), setType(classType(
				Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "sub", parameterTypes);
	}

	@Test
	public void testSubOrderedSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), classType(
				Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "subOrderedSet", parameterTypes);
	}

	@Test
	public void testSubSequence() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "subSequence", parameterTypes);
	}

	@Test
	public void testDropOrderedSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "drop", parameterTypes);
	}

	@Test
	public void testDropSequence() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "drop", parameterTypes);
	}

	@Test
	public void testDropRightOrderedSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "dropRight", parameterTypes);
	}

	@Test
	public void testDropRightSequence() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), classType(
				Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "dropRight", parameterTypes);
	}

	@Test
	public void testSumListInt() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Long.class) };

		assertValidation(expectedReturnTypes, "sum", parameterTypes);
	}

	@Test
	public void testSumListReal() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(Double.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "sum", parameterTypes);
	}

	@Test
	public void testSumListNotNumber() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType(
				"sum can only be used on a collection of numbers.") };

		assertValidation(expectedReturnTypes, "sum", parameterTypes);
	}

	@Test
	public void testSumSetInt() {
		final IType[] parameterTypes = new IType[] {setType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Long.class) };

		assertValidation(expectedReturnTypes, "sum", parameterTypes);
	}

	@Test
	public void testSumSetReal() {
		final IType[] parameterTypes = new IType[] {setType(classType(Double.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "sum", parameterTypes);
	}

	@Test
	public void testSumSetNotNumber() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType(
				"sum can only be used on a collection of numbers.") };

		assertValidation(expectedReturnTypes, "sum", parameterTypes);
	}

	@Test
	public void testMinListInt() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Long.class) };

		assertValidation(expectedReturnTypes, "min", parameterTypes);
	}

	@Test
	public void testMinListReal() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(Double.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "min", parameterTypes);
	}

	@Test
	public void testMinListNotNumber() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType(
				"min can only be used on a collection of numbers.") };

		assertValidation(expectedReturnTypes, "min", parameterTypes);
	}

	@Test
	public void testMinSetInt() {
		final IType[] parameterTypes = new IType[] {setType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Long.class) };

		assertValidation(expectedReturnTypes, "min", parameterTypes);
	}

	@Test
	public void testMinSetReal() {
		final IType[] parameterTypes = new IType[] {setType(classType(Double.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "min", parameterTypes);
	}

	@Test
	public void testMinSetNotNumber() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType(
				"min can only be used on a collection of numbers.") };

		assertValidation(expectedReturnTypes, "min", parameterTypes);
	}

	@Test
	public void testMaxListInt() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Long.class) };

		assertValidation(expectedReturnTypes, "max", parameterTypes);
	}

	@Test
	public void testMaxListReal() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(Double.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "max", parameterTypes);
	}

	@Test
	public void testMaxListNotNumber() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType(
				"max can only be used on a collection of numbers.") };

		assertValidation(expectedReturnTypes, "max", parameterTypes);
	}

	@Test
	public void testMaxSetInt() {
		final IType[] parameterTypes = new IType[] {setType(classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Long.class) };

		assertValidation(expectedReturnTypes, "max", parameterTypes);
	}

	@Test
	public void testMaxSetReal() {
		final IType[] parameterTypes = new IType[] {setType(classType(Double.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "max", parameterTypes);
	}

	@Test
	public void testMaxSetNotNumber() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)) };
		final IType[] expectedReturnTypes = new IType[] {nothingType(
				"max can only be used on a collection of numbers.") };

		assertValidation(expectedReturnTypes, "max", parameterTypes);
	}

	@Test
	public void testUnionList() {
		final IType[] parameterTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)), sequenceType(
				classType(Integer.class)) };

		assertValidation(expectedReturnTypes, "union", parameterTypes);
	}

	@Test
	public void testUnionSet() {
		final IType[] parameterTypes = new IType[] {setType(classType(String.class)), setType(classType(
				Integer.class)) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)), setType(classType(
				Integer.class)) };

		assertValidation(expectedReturnTypes, "union", parameterTypes);
	}

	@Test
	public void testFilterListClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClassifier())), classLiteralType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testFilterListEClassifier() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClassifier())), eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testFilterSetClass() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClassifier())), classLiteralType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {setType(classType(String.class)) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testFilterSetEClassifier() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClassifier())), eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
		final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testFilterListEClassifierSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClassifier())), eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEClass(),
						EcorePackage.eINSTANCE.getEDataType()) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEDataType())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testFilterSetEClassifierSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClassifier())), eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEClass(),
						EcorePackage.eINSTANCE.getEDataType()) };
		final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), setType(eClassifierType(EcorePackage.eINSTANCE.getEDataType())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testFilterListEInt() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {sequenceType(classType(Integer.class)),
				eClassifierLiteralType(EcorePackage.eINSTANCE.getEInt()) };
		final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEInt())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testFilterSetEInt() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(classType(Integer.class)), eClassifierLiteralType(
				EcorePackage.eINSTANCE.getEInt()) };
		final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEInt())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testFilterSetIncompatibleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
		final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEPackage())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testFilterSetCompatibleAndIncompatibleTypes() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEClass(),
						EcorePackage.eINSTANCE.getEPackage()) };
		final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())) };

		assertValidation(expectedReturnTypes, "filter", parameterTypes);
	}

	@Test
	public void testIndexOfSliceListList() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "indexOfSlice", parameterTypes);
	}

	@Test
	public void testIndexOfSliceSetList() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "indexOfSlice", parameterTypes);
	}

	@Test
	public void testIndexOfSliceListSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "indexOfSlice", parameterTypes);
	}

	@Test
	public void testIndexOfSliceSetSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "indexOfSlice", parameterTypes);
	}

	@Test
	public void testlastIndexOfSliceListList() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "lastIndexOfSlice", parameterTypes);
	}

	@Test
	public void testlastIndexOfSliceSetList() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "lastIndexOfSlice", parameterTypes);
	}

	@Test
	public void testlastIndexOfSliceListSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "lastIndexOfSlice", parameterTypes);
	}

	@Test
	public void testlastIndexOfSliceSetSet() {
		getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
		final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
				.getEClass())), setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "lastIndexOfSlice", parameterTypes);
	}

}
