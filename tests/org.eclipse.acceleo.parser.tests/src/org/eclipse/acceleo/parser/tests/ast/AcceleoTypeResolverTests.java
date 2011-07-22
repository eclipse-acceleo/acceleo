package org.eclipse.acceleo.parser.tests.ast;

import org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoTypeResolver;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Test;

@SuppressWarnings("restriction")
public class AcceleoTypeResolverTests {

	@Test
	public void testShadowedClassifierNull() {
		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$
		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.getEClassifiers().add(type);
		AcceleoTypeResolver.classifierEqual(null, type);
	}

	@Test
	public void testTypeClassifierNull() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifier"); //$NON-NLS-1$
		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.getEClassifiers().add(eClassifier);
		AcceleoTypeResolver.classifierEqual(eClassifier, null);
	}

	@Test
	public void testShadowedClassifierAndTypeClassifierNull() {
		AcceleoTypeResolver.classifierEqual(null, null);
	}

	@Test
	public void testShadowedClassifierEmptyContainer() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifier"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(type);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testTypeClassifierEmptyContainer() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifier"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(eClassifier);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testShadowedClassifierAndTypeClassifierEmptyContainer() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifier"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(eClassifier);
		container.getEClassifiers().add(type);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testShadowedClassifierContainerNullName() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifier"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.getEClassifiers().add(eClassifier);
		EPackage container2 = EcoreFactory.eINSTANCE.createEPackage();
		container2.setName("container2"); //$NON-NLS-1$
		container2.getEClassifiers().add(type);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testTypeClassifierContainerNullName() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifier"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(eClassifier);
		EPackage container2 = EcoreFactory.eINSTANCE.createEPackage();
		container2.getEClassifiers().add(type);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testShadowedClassifierAndTypeClassifierContainerNullName() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifier"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.getEClassifiers().add(eClassifier);
		EPackage container2 = EcoreFactory.eINSTANCE.createEPackage();
		container2.getEClassifiers().add(type);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testShadowedClassifierContainerInvalidURI() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifieré&)ç\"&)à\"ç&)_'"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(eClassifier);
		container.getEClassifiers().add(type);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testTypeClassifierContainerInvalidURI() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifier"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type\"é_'çèéçà(èé()"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(eClassifier);
		container.getEClassifiers().add(type);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testShadowedClassifierAndTypeClassifierContainerInvalidURI() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("eClassifieré&)ç\"&)à\"ç&)_'"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type\"é_'çèéçà(èé()"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(eClassifier);
		container.getEClassifiers().add(type);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testShadowedClassifierAndTypeDifferentAttributeNumbers() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("type"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(eClassifier);
		container.getEClassifiers().add(type);

		EAttribute attribute0 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute attribute1 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute attribute2 = EcoreFactory.eINSTANCE.createEAttribute();

		eClassifier.getEStructuralFeatures().add(attribute0);
		eClassifier.getEStructuralFeatures().add(attribute1);
		type.getEStructuralFeatures().add(attribute2);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testShadowedClassifierAndTypeDifferentAttributesNoName() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("type"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(eClassifier);
		container.getEClassifiers().add(type);

		EAttribute attribute0 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute attribute1 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute attribute2 = EcoreFactory.eINSTANCE.createEAttribute();
		EAttribute attribute3 = EcoreFactory.eINSTANCE.createEAttribute();

		eClassifier.getEStructuralFeatures().add(attribute0);
		eClassifier.getEStructuralFeatures().add(attribute1);
		type.getEStructuralFeatures().add(attribute2);
		type.getEStructuralFeatures().add(attribute3);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}

	@Test
	public void testShadowedClassifierAndTypeDifferentAttributeBounds() {
		EClass eClassifier = EcoreFactory.eINSTANCE.createEClass();
		eClassifier.setName("type"); //$NON-NLS-1$

		EClass type = EcoreFactory.eINSTANCE.createEClass();
		type.setName("type"); //$NON-NLS-1$

		EPackage container = EcoreFactory.eINSTANCE.createEPackage();
		container.setName("container"); //$NON-NLS-1$
		container.getEClassifiers().add(eClassifier);
		container.getEClassifiers().add(type);

		EAttribute attribute0 = EcoreFactory.eINSTANCE.createEAttribute();
		attribute0.setName("a"); //$NON-NLS-1$
		attribute0.setLowerBound(1);
		attribute0.setUpperBound(1);
		EAttribute attribute1 = EcoreFactory.eINSTANCE.createEAttribute();
		attribute1.setName("a"); //$NON-NLS-1$
		attribute1.setLowerBound(1);
		attribute1.setUpperBound(1);
		EAttribute attribute2 = EcoreFactory.eINSTANCE.createEAttribute();
		attribute2.setName("a"); //$NON-NLS-1$
		attribute2.setLowerBound(1);
		attribute2.setUpperBound(1);
		EAttribute attribute3 = EcoreFactory.eINSTANCE.createEAttribute();
		attribute3.setName("a"); //$NON-NLS-1$
		attribute3.setLowerBound(1);
		attribute3.setUpperBound(0);

		eClassifier.getEStructuralFeatures().add(attribute0);
		eClassifier.getEStructuralFeatures().add(attribute1);
		type.getEStructuralFeatures().add(attribute2);
		type.getEStructuralFeatures().add(attribute3);

		AcceleoTypeResolver.classifierEqual(eClassifier, type);
	}
}
