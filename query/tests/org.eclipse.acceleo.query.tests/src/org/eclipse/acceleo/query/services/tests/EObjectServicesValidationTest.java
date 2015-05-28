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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.services.XPathServices;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
		getQueryEnvironment().registerServicePackage(XPathServices.class);
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 doesn't contain any other EClass")) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEAllContents() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getETypeParameter())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEOperation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEStructuralFeature())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEGenericType())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEEnumLiteral())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEParameter())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(eCls2) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty or indirectly")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling eAllContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty or indirectly")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEPackage())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEPackage())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEModelElement()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEModelElement())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getETypeParameter())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEOperation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEStructuralFeature())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEEnumLiteral())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEParameter())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEAllContentsFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(eCls2) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty or indirectly")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling eAllContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty or indirectly")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eAllContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEClass() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eClass", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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
			final IType[] expectedReturnTypes = new IType[] {nothingType("EClassifier=eCls1 can't be contained") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType("Nothing will be left after calling eContainer:\nEClassifier=eCls1 can't be contained") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContainer() {
		final IService service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierType(EcorePackage.eINSTANCE.getEAnnotation()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerOnEStringToStringMapEntryBasicMapping() {
		final List<Set<IType>> argTypes = new ArrayList<Set<IType>>();
		argTypes.add(createTypeSet(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEStringToStringMapEntry())));
		argTypes.add(createTypeSet(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getEAnnotation())));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			final Set<IType> types = getValidationServices().callType("eContainer", argTypes);
			assertEquals(1, types.size());
			Iterator<IType> it = types.iterator();
			IType type = it.next();
			assertTrue(type instanceof NothingType);
			assertEquals(
					"Couldn't find the eContainer(EClassifier=EStringToStringMapEntry,EClassifierLiteral=EAnnotation) service or EOperation",
					((NothingType)type).getMessage());
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerOnEStringToStringMapEntryCustomMapping() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);
			getQueryEnvironment().registerCustomClassMapping(
					EcorePackage.eINSTANCE.getEStringToStringMapEntry(), EStringToStringMapEntryImpl.class);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEAnnotation()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEAnnotation()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEObject()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls2), eClassifierLiteralType(eCls1) };
			final IType[] expectedReturnTypes = new IType[] {nothingType("EClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType("Nothing will be left after calling eContainer:\nEClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContainerFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerFilteredLoweredByTypes() {
		final IService service = serviceLookUp("eContainer", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.getEModelElement() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE
				.getETypedElement()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getETypedElement()) };
			final IType[] expectedReturnTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getETypedElement()),
					eClassifierType(EcorePackage.eINSTANCE.getEOperation()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(eCls2) };
			final IType[] expectedReturnTypes = new IType[] {nothingType("EClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType("Nothing will be left after calling eContainer:\nEClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainer", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls2), eClassifierLiteralType(eCls1) };
			final IType[] expectedReturnTypes = new IType[] {nothingType("EClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType("Nothing will be left after calling eContainerOrSelf:\nEClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContainerOrSelfFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerOrSelfFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getETypedElement()) };
			final IType[] expectedReturnTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getETypedElement()),
					eClassifierType(EcorePackage.eINSTANCE.getEOperation()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContainerOrSelfFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEClass()) };

			assertValidation(expectedReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(eCls2) };
			final IType[] expectedReturnTypes = new IType[] {nothingType("EClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1") };
			final IType[] expectedAllReturnTypes = new IType[] {nothingType("Nothing will be left after calling eContainerOrSelf:\nEClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1") };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContainerOrSelf", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 doesn't contain any other EClass")) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContents() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(eCls2) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling eContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEModelElement()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEContentsFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(eCls2) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling eContents:\nEClassifier=eCls1 can't contain EClassifierLiteral=eCls2 direclty")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eContents", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getNsPrefix());
		}
	}

	@Test
	public void testEGet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()), classType(String.class) };
			final IType[] expectedReturnTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE
					.getEJavaObject()) };

			assertValidation(expectedReturnTypes, "eGet", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverse() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {
					setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEFactory())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFeatureName() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()), classType(String.class) };
			final IType[] expectedReturnTypes = new IType[] {
					setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEFactory())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFiltered() {
		final IService service = serviceLookUp("eInverse", new Object[] {EcorePackage.eINSTANCE,
				EcorePackage.eINSTANCE.eClass() });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), EcorePackage.eINSTANCE.getEPackage()));

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEPackage()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEPackage())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEEnum()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEEnum())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEModelElement()) };
			final IType[] expectedReturnTypes = new IType[] {
					setType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEFactory())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {setType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
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
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

	@Test
	public void testEInverseFilteredNoType() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEOperation()) };
			final IType[] expectedReturnTypes = new IType[] {setType(nothingType("EClassifier=EPackage don't have inverse to EClassifierLiteral=EOperation")) };
			final IType[] expectedAllReturnTypes = new IType[] {setType(nothingType("Nothing will be left after calling eInverse:\nEClassifier=EPackage don't have inverse to EClassifierLiteral=EOperation")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "eInverse", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getNsPrefix());
		}
	}

}
