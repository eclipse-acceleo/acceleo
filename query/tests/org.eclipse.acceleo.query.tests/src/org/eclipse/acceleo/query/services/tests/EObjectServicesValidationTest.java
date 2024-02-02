/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.RootEObjectProvider;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * EObject services validation tests.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EObjectServicesValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(),
				EObjectServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void testEAllContentsNoContainingEClass() {
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"EClassifier=eCls1 doesn't contain any other EClass")) };
			final IType[] expectedReturnAllTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling eAllContents:\nEClassifier=eCls1 doesn't contain any other EClass")) };

			assertValidation(expectedReturnTypes, expectedReturnAllTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEAllContentsFilteredSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredSetClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierSetLiteralType(
					EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredSetOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredSetOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class), eClassifierSetLiteralType(
					EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContents() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
											EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getETypeParameter())), sequenceType(
									eClassifierType(EcorePackage.eINSTANCE.getEOperation())), sequenceType(
											eClassifierType(EcorePackage.eINSTANCE.getEStructuralFeature())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEGenericType())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getEEnumLiteral())), sequenceType(
									eClassifierType(EcorePackage.eINSTANCE.getEParameter())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
											EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getETypeParameter())), sequenceType(
									eClassifierType(EcorePackage.eINSTANCE.getEOperation())), sequenceType(
											eClassifierType(EcorePackage.eINSTANCE.getEStructuralFeature())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEGenericType())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getEEnumLiteral())), sequenceType(
									eClassifierType(EcorePackage.eINSTANCE.getEParameter())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredNoContainingEClass() {
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(
					eCls2) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 directly or indirectly")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling eAllContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 directly or indirectly")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEAllContentsFilteredEClassifierLiteral() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredEClassifierLiteralClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredEClassifier() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
											EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getETypeParameter())), sequenceType(
									eClassifierType(EcorePackage.eINSTANCE.getEOperation())), sequenceType(
											eClassifierType(EcorePackage.eINSTANCE.getEStructuralFeature())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEGenericType())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getEEnumLiteral())), sequenceType(
									eClassifierType(EcorePackage.eINSTANCE.getEParameter())), };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredEClassifierClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
											EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getETypeParameter())), sequenceType(
									eClassifierType(EcorePackage.eINSTANCE.getEOperation())), sequenceType(
											eClassifierType(EcorePackage.eINSTANCE.getEStructuralFeature())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEGenericType())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getEEnumLiteral())), sequenceType(
									eClassifierType(EcorePackage.eINSTANCE.getEParameter())), };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEModelElement()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
											EcorePackage.eINSTANCE.getEModelElement())), sequenceType(
													eClassifierType(EcorePackage.eINSTANCE
															.getETypeParameter())), sequenceType(
																	eClassifierType(EcorePackage.eINSTANCE
																			.getEOperation())), sequenceType(
																					eClassifierType(
																							EcorePackage.eINSTANCE
																									.getEStructuralFeature())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEEnumLiteral())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getEParameter())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredLoweredByTypesClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEModelElement()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
											EcorePackage.eINSTANCE.getEModelElement())), sequenceType(
													eClassifierType(EcorePackage.eINSTANCE
															.getETypeParameter())), sequenceType(
																	eClassifierType(EcorePackage.eINSTANCE
																			.getEOperation())), sequenceType(
																					eClassifierType(
																							EcorePackage.eINSTANCE
																									.getEStructuralFeature())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEEnumLiteral())), sequenceType(
							eClassifierType(EcorePackage.eINSTANCE.getEParameter())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredLoweredByFilterClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEAllContentsFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(
					eCls2) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 directly or indirectly")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling eAllContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 directly or indirectly")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEClass() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {classType(EClass.class) };

			assertValidation(expectedReturnTypes, "eClass", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEClassClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class) };
			final IType[] expectedReturnTypes = new IType[] {classType(EClass.class) };

			assertValidation(expectedReturnTypes, "eClass", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerNotContainedEClass() {
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1) };
			final IType[] expectedReturnTypes = new IType[] {nothingType(
					"EClassifier=eCls1 can't be contained") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType(
					"Nothing will be left after calling eContainer:\nEClassifier=eCls1 can't be contained") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEContainer() {
		final IService<?> service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE
				.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEPackage()), eClassifierType(EcorePackage.eINSTANCE.getEAnnotation()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerClassType() {
		final IService<?> service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE
				.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(classType(EClass.class));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEPackage()), eClassifierType(EcorePackage.eINSTANCE.getEAnnotation()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOnEStringToStringMapEntryBasicMapping() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEStringToStringMapEntry()) };

			assertNoService("eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOnEStringToStringMapEntryBasicMappingClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EStringToStringMapEntryImpl.class) };
			final IType[] expectedReturnTypes = new IType[] {nothingType(
					"Only EClass can be contained into other EClasses not org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType(
					"Nothing will be left after calling eContainer:\nOnly EClass can be contained into other EClasses not org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOnEStringToStringMapEntryCustomMapping() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			getQueryEnvironment().registerCustomClassMapping(EcorePackage.eINSTANCE
					.getEStringToStringMapEntry(), EStringToStringMapEntryImpl.class);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEStringToStringMapEntry()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEAnnotation()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOnEStringToStringMapEntryCustomMappingClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			getQueryEnvironment().registerCustomClassMapping(EcorePackage.eINSTANCE
					.getEStringToStringMapEntry(), EStringToStringMapEntryImpl.class);

			final IType[] parameterTypes = new IType[] {classType(EStringToStringMapEntryImpl.class) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEAnnotation()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEObject()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEObject()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerFilteredNotContainedEClass() {
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls2), eClassifierLiteralType(
					eCls1) };
			final IType[] expectedReturnTypes = new IType[] {nothingType(
					"[EClassifierLiteral=eCls1] can't contain directly or indirectly EClassifier=eCls2") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType(
					"Nothing will be left after calling eContainer:\n[EClassifierLiteral=eCls1] can't contain directly or indirectly EClassifier=eCls2") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEContainerFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerFilteredClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerFilteredOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerFilteredLoweredByTypes() {
		final IService<?> service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEModelElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(eClassifierLiteralType(EcorePackage.eINSTANCE.getETypedElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getETypedElement()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getETypedElement()), eClassifierType(EcorePackage.eINSTANCE.getEOperation()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerFilteredLoweredByTypesClassType() {
		final IService<?> service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEModelElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(classType(EPackage.class));
		argTypes.add(eClassifierLiteralType(EcorePackage.eINSTANCE.getETypedElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getETypedElement()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getETypedElement()), eClassifierType(EcorePackage.eINSTANCE.getEOperation()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerFilteredLoweredByFilterClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(
					eCls2) };
			final IType[] expectedReturnTypes = new IType[] {nothingType(
					"[EClassifierLiteral=eCls2] can't contain directly or indirectly EClassifier=eCls1") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType(
					"Nothing will be left after calling eContainer:\n[EClassifierLiteral=eCls2] can't contain directly or indirectly EClassifier=eCls1") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEContainerOrSelfFilteredNotContainedEClass() {
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls2), eClassifierLiteralType(
					eCls1) };
			final IType[] expectedReturnTypes = new IType[] {nothingType(
					"EClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType(
					"Nothing will be left after calling eContainerOrSelf:\nEClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEContainerOrSelfFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOrSelfFilteredClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOrSelfFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOrSelfFilteredOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOrSelfFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getETypedElement()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getETypedElement()), eClassifierType(EcorePackage.eINSTANCE.getEOperation()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOrSelfFilteredLoweredByTypesClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getETypedElement()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getETypedElement()), eClassifierType(EcorePackage.eINSTANCE.getEOperation()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOrSelfFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOrSelfFilteredLoweredByFilterClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContainerOrSelfFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(
					eCls2) };
			final IType[] expectedReturnTypes = new IType[] {nothingType(
					"EClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType(
					"Nothing will be left after calling eContainerOrSelf:\nEClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEContentsNoContainingEClass() {
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"EClassifier=eCls1 doesn't contain any other EClass")) };
			final IType[] expectedReturnAllTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling eContents:\nEClassifier=eCls1 doesn't contain any other EClass")) };

			assertValidation(expectedReturnTypes, expectedReturnAllTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEContents() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredNoContainingEClass() {
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(
					eCls2) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 directly")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling eContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 directly")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEContentsFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredSetClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierSetLiteralType(
					EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilterEClass() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilterEClassClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilterSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilterSetClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), setType(eClassifierType(
					EcorePackage.eINSTANCE.getEClass())) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredSetOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredSetOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class), eClassifierSetLiteralType(
					EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEModelElement()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredLoweredByTypesClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEModelElement()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAnnotation())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEClassifier())), sequenceType(eClassifierType(
									EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredLoweredByFilterClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEContentsFilteredNoType() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("ePkg");
		ePkg.setNsPrefix("ePkg");
		final EClass eCls1 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls1.setName("eCls1");
		final EReference ref1 = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		ref1.setName("ref1");
		ref1.setContainment(true);
		ref1.setEType(eCls1);
		eCls1.getEStructuralFeatures().add(ref1);
		ePkg.getEClassifiers().add(eCls1);
		final EClass eCls2 = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eCls2.setName("eCls2");
		ePkg.getEClassifiers().add(eCls2);

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(
					eCls2) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 directly")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling eContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 directly")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg);
		}
	}

	@Test
	public void testEInverseFeatureNameEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					classType(String.class) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFeatureNameEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class), classType(String.class) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFiltered() {
		final IService<?> service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(eClassifierType(EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEPackage())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFilteredClassType() {
		final IService<?> service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(classType(EPackage.class));
		argTypes.add(eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEPackage())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEEnum()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEEnum())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFilteredLoweredByFilterClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEEnum()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEEnum())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEModelElement()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEClassifier())), setType(eClassifierType(EcorePackage.eINSTANCE.getEFactory())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())), setType(eClassifierType(
							EcorePackage.eINSTANCE.getEAnnotation())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFilteredLoweredByTypesClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEModelElement()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEClassifier())), setType(eClassifierType(EcorePackage.eINSTANCE.getEFactory())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())), setType(eClassifierType(
							EcorePackage.eINSTANCE.getEAnnotation())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEEnum()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEEnum())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFilteredOnEObjectClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EObject.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEEnum()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEEnum())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFilteredNoType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEOperation()) };
			final IType[] expectedReturnTypes = new IType[] {setType(nothingType(
					"EClassifier=EPackage don't have inverse to EClassifierLiteral=EOperation")) };
			final IType[] expectedAllReturnTypes = new IType[] {setType(nothingType(
					"Nothing will be left after calling eInverse:\nEClassifier=EPackage don't have inverse to EClassifierLiteral=EOperation")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testEInverseFilteredNoTypeClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EPackage.class), eClassifierLiteralType(
					EcorePackage.eINSTANCE.getEOperation()) };
			final IType[] expectedReturnTypes = new IType[] {setType(nothingType(
					"org.eclipse.emf.ecore.EPackage don't have inverse to EClassifierLiteral=EOperation")) };
			final IType[] expectedAllReturnTypes = new IType[] {setType(nothingType(
					"Nothing will be left after calling eInverse:\norg.eclipse.emf.ecore.EPackage don't have inverse to EClassifierLiteral=EOperation")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesNoRootProviderEClassLiteral() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierLiteralType(EcorePackage.eINSTANCE
					.getEAttribute()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"No IRootEObjectProvider registered")) };
			final IType[] expectedReturnAllTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling allInstances:\nNo IRootEObjectProvider registered")) };

			assertValidation(expectedReturnTypes, expectedReturnAllTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesNoRootProviderEClass() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"No IRootEObjectProvider registered")) };
			final IType[] expectedReturnAllTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling allInstances:\nNo IRootEObjectProvider registered")) };

			assertValidation(expectedReturnTypes, expectedReturnAllTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesNoRootProviderEClassClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EClass.class) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"No IRootEObjectProvider registered")) };
			final IType[] expectedReturnAllTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling allInstances:\nNo IRootEObjectProvider registered")) };

			assertValidation(expectedReturnTypes, expectedReturnAllTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesNoRootProviderEClassSetLiteral() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierSetLiteralType(EcorePackage.eINSTANCE
					.getEAttribute(), EcorePackage.eINSTANCE.getEReference()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"No IRootEObjectProvider registered")) };
			final IType[] expectedReturnAllTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling allInstances:\nNo IRootEObjectProvider registered")) };

			assertValidation(expectedReturnTypes, expectedReturnAllTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesNoRootProviderSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"No IRootEObjectProvider registered")) };
			final IType[] expectedReturnAllTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling allInstances:\nNo IRootEObjectProvider registered")) };

			assertValidation(expectedReturnTypes, expectedReturnAllTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesNoRootProviderSetClassType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {setType(classType(EClass.class)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType(
					"No IRootEObjectProvider registered")) };
			final IType[] expectedReturnAllTypes = new IType[] {sequenceType(nothingType(
					"Nothing will be left after calling allInstances:\nNo IRootEObjectProvider registered")) };

			assertValidation(expectedReturnTypes, expectedReturnAllTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesEClassLiteral() {
		try {
			setQueryEnvironment(Query.newEnvironmentWithDefaultServices(null, new RootEObjectProvider(
					EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE)));
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierLiteralType(EcorePackage.eINSTANCE
					.getEAttribute()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAttribute())) };

			assertValidation(expectedReturnTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesEClass() {
		try {
			setQueryEnvironment(Query.newEnvironmentWithDefaultServices(null, new RootEObjectProvider(
					EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE)));
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EObject.class)) };

			assertValidation(expectedReturnTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesEClassClassType() {
		try {
			setQueryEnvironment(Query.newEnvironmentWithDefaultServices(null, new RootEObjectProvider(
					EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE)));
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {classType(EClass.class) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EObject.class)) };

			assertValidation(expectedReturnTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesEClassSetLiteral() {
		try {
			setQueryEnvironment(Query.newEnvironmentWithDefaultServices(null, new RootEObjectProvider(
					EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE)));
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierSetLiteralType(EcorePackage.eINSTANCE
					.getEAttribute(), EcorePackage.eINSTANCE.getEReference()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(
					EcorePackage.eINSTANCE.getEAttribute())), sequenceType(eClassifierType(
							EcorePackage.eINSTANCE.getEReference())) };

			assertValidation(expectedReturnTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesSet() {
		try {
			setQueryEnvironment(Query.newEnvironmentWithDefaultServices(null, new RootEObjectProvider(
					EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE)));
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EObject.class)) };

			assertValidation(expectedReturnTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

	@Test
	public void testAllInstancesSetClassType() {
		try {
			setQueryEnvironment(Query.newEnvironmentWithDefaultServices(null, new RootEObjectProvider(
					EcorePackage.eINSTANCE, AnydslPackage.eINSTANCE)));
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {setType(classType(EClass.class)) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(classType(EObject.class)) };

			assertValidation(expectedReturnTypes, "allInstances", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE);
		}
	}

}
