/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.sample.common.test;

import static org.eclipse.acceleo.unit.core.assertion.AcceleoAssert.assertThatGenerateThrowIAE;
import static org.eclipse.acceleo.unit.core.assertion.AcceleoAssert.assertThatTemplateResultFrom;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.unit.core.annotation.CompiledModuleTest;
import org.eclipse.acceleo.unit.core.annotation.TemplateTest;
import org.eclipse.acceleo.unit.core.generation.TemplateGenerationHelper;
import org.eclipse.acceleo.unit.core.suite.AcceleoSuite;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@CompiledModuleTest(emtl = "org.eclipse.acceleo.unit.sample/bin/org/eclipse/acceleo/unit/sample/common/templatesGenerate.emtl")
@RunWith(AcceleoSuite.class)
public class TempatesGenerateTest {
	
	private static EPackage testPackage;
	private static EObject testClass;
	
	private static final String FIRST_CONTENT = "This is my template";
	private static final String SECOND_CONTENT = "This is my second template";
	
	@BeforeClass
	public static void setUp() throws Exception {
		EObject[] modelPackages = loadModel();
		assertNotNull(modelPackages);
		assertNotNull(modelPackages[0]);
		assertTrue(modelPackages[0] instanceof EPackage);
		testPackage = (EPackage) modelPackages[0];
		
		EList<EClassifier> eClassifiers = testPackage.getEClassifiers();
		for (EClassifier eClassifier : eClassifiers) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				testClass = (EObject) eClass;
				break;
			}
		}
		assertNotNull(testClass);
	}
	
	public static EObject[] loadModel() {
		try {
			if (EMFPlugin.IS_ECLIPSE_RUNNING) {
				EObject eObject = ModelUtils.load(URI.createURI("platform:/plugin/org.eclipse.acceleo.unit.sample/model/sample.ecore"), new ResourceSetImpl());
				return new EObject[]{eObject};
			} else {
				String path = System.getProperty("user.dir");
				File filePath = new File(path);
				filePath = new File(filePath,  "../org.eclipse.acceleo.unit.sample/model/sample.ecore");
				EObject eObject = ModelUtils.load(URI.createFileURI(filePath.getAbsolutePath()), new ResourceSetImpl());
				return new EObject[]{eObject};
			}			
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return new EObject[]{};
	}
	
	/**
	 * Test a simple text generation.
	 * @param helper
	 */
	@TemplateTest(qualifiedName = "generateElement")
	public void testGenerateElementNoIndex(TemplateGenerationHelper helper) {
		helper.setModelElement(testPackage);
		helper.generate();
		assertThatTemplateResultFrom(helper, is(FIRST_CONTENT));
	}
	
	/**
	 * Test a simple text generation with an index at zero.
	 * @param helper
	 */
	@TemplateTest(qualifiedName = "generateElement", index = 0)
	public void testGenerateElementWithIndex(TemplateGenerationHelper helper) {
		helper.setModelElement(testPackage);
		helper.generate();
		assertThatTemplateResultFrom(helper, is(FIRST_CONTENT));
	}
	
	/**
	 * Test a simple text generation with a bad model argument type.
	 * This test must throw an IllegalArgumentExceptiont.
	 * @param helper
	 */
	@TemplateTest(qualifiedName = "generateElement")
	public void testGenerateElementNoIndexEClass(TemplateGenerationHelper helper) {
		helper.setModelElement(testClass);
		assertThatGenerateThrowIAE(helper);
	}
	@TemplateTest(qualifiedName = "generateElement", index = 0)
	public void testGenerateElementWithIndexEClass(TemplateGenerationHelper helper) {
		helper.setModelElement(testClass);
		assertThatGenerateThrowIAE(helper);
	}
	
	/**
	 * Test a simple text generation with an index at one and a bad model argument type.
	 * This test must throw an IllegalArgumentException.
	 * @param helper
	 */
	@TemplateTest(qualifiedName = "generateElement", index = 1)
	public void testGenerateElementWithAnotherIndex(TemplateGenerationHelper helper) {
		helper.setModelElement(testPackage);
		assertThatGenerateThrowIAE(helper);
	}
	
	/**
	 * Test a simple text generation with an index at one.
	 * @param helper
	 */
	@TemplateTest(qualifiedName = "generateElement", index = 1)
	public void testGenerateElementWithAnotherIndexEClass(TemplateGenerationHelper helper) {
		helper.setModelElement(testClass);
		helper.generate();

		assertThatTemplateResultFrom(helper, is(SECOND_CONTENT));
	}
}
