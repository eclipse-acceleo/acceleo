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
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.XPathServices;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * EObject services validation tests.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class XPathServicesValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService> services = ServiceUtils.getServices(getQueryEnvironment(), XPathServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void testAncestorsNotContainedEClass() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 can't be contained")) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testAncestorsFilterEClass() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEModelElement())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEEnum())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEOperation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestorsFilterSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEModelElement())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEEnum())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEOperation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestors() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEModelElement())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEEnum())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEOperation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestorsFilteredSet() {

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestorsFilteredSetOnEObject() {

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestorsOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestorsFilteredNotContainedEClass() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling ancestors:\nEClassifierLiteral=eCls1 can't contain directly or indirectly EClassifier=eCls2")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testAncestorsFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestorsFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestorsFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getETypedElement()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getETypedElement())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEOperation())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestorsFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testAncestorsFilteredNoType() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling ancestors:\nEClassifierLiteral=eCls2 can't contain directly or indirectly EClassifier=eCls1")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "ancestors", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testFollowingSiblingsNotContainedEClass() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 can't have following siblings")) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testFollowingSiblings() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testFollowingSiblingsFilterEClass() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testFollowingSiblingsFilterSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFollowingFilteredSet() {

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFollowingFilteredSetOnEObject() {

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testFollowingSiblingsOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredNotContainedEClass() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifierLiteral=eCls1 can't be a following sibling of EClassifier=eCls2")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling followingSiblings:\nEClassifierLiteral=eCls1 can't be a following sibling of EClassifier=eCls2")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testFollowingSiblingsFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClassifier()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClassifier())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getENamedElement()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getENamedElement())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testFollowingSiblingsFilteredNoType() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifierLiteral=eCls2 can't be a following sibling of EClassifier=eCls1")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling followingSiblings:\nEClassifierLiteral=eCls2 can't be a following sibling of EClassifier=eCls1")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "followingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsNotContainedEClass() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 can't have preceding siblings")) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testPrecedingSiblings() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFilterEClass() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFilterSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredSet() {

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredSetOnEObject() {

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredNotContainedEClass() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifierLiteral=eCls1 can't be a preceding sibling of EClassifier=eCls2")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling precedingSiblings:\nEClassifierLiteral=eCls1 can't be a preceding sibling of EClassifier=eCls2")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFiltered() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClassifier()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClassifier())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getENamedElement()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getENamedElement())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testPrecedingSiblingsFilteredNoType() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifierLiteral=eCls2 can't be a preceding sibling of EClassifier=eCls1")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling precedingSiblings:\nEClassifierLiteral=eCls2 can't be a preceding sibling of EClassifier=eCls1")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "precedingSiblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testSiblingsNotContainedEClass() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifier=eCls1 can't have siblings")) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testSiblings() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFilterEClass() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFilterSet() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					setType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEAnnotation())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEStringToStringMapEntry())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEObject())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEObject())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFilteredNotContainedEClass() {
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
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifierLiteral=eCls1 can't be a sibling of EClassifier=eCls2")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling siblings:\nEClassifierLiteral=eCls1 can't be a sibling of EClassifier=eCls2")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

	@Test
	public void testSiblingsFiltered() {

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClassifier()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClassifier())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFilteredSet() {

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFilteredSetOnEObject() {

		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierSetLiteralType(EcorePackage.eINSTANCE.getEPackage(), EcorePackage.eINSTANCE
							.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClass())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFilteredOnEObject() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {eClassifierType(EcorePackage.eINSTANCE.getEObject()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFilteredLoweredByTypes() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getENamedElement()) };
			final IType[] expectedReturnTypes = new IType[] {
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEClassifier())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getEPackage())),
					sequenceType(eClassifierType(EcorePackage.eINSTANCE.getENamedElement())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFilteredLoweredByFilter() {
		try {
			getQueryEnvironment().registerEPackage(EcorePackage.eINSTANCE);

			final IType[] parameterTypes = new IType[] {
					eClassifierType(EcorePackage.eINSTANCE.getEPackage()),
					eClassifierLiteralType(EcorePackage.eINSTANCE.getEClass()) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(eClassifierType(EcorePackage.eINSTANCE
					.getEClass())) };

			assertValidation(expectedReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(EcorePackage.eINSTANCE.getName());
		}
	}

	@Test
	public void testSiblingsFilteredNoType() {
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

		final IService service = serviceLookUp("siblings", new Object[] {EcoreUtil.create(eCls1), eCls2 });
		assertTrue(service != null);
		final List<IType> argTypes = new ArrayList<IType>();
		argTypes.add(new EClassifierType(getQueryEnvironment(), eCls1));
		argTypes.add(new EClassifierLiteralType(getQueryEnvironment(), eCls2));

		try {
			getQueryEnvironment().registerEPackage(ePkg);

			final IType[] parameterTypes = new IType[] {eClassifierType(eCls1), eClassifierLiteralType(eCls2) };
			final IType[] expectedReturnTypes = new IType[] {sequenceType(nothingType("EClassifierLiteral=eCls2 can't be a sibling of EClassifier=eCls1")) };
			final IType[] expectedAllReturnTypes = new IType[] {sequenceType(nothingType("Nothing will be left after calling siblings:\nEClassifierLiteral=eCls2 can't be a sibling of EClassifier=eCls1")) };

			assertValidation(expectedReturnTypes, expectedAllReturnTypes, "siblings", parameterTypes);
		} finally {
			getQueryEnvironment().removeEPackage(ePkg.getName());
		}
	}

}
