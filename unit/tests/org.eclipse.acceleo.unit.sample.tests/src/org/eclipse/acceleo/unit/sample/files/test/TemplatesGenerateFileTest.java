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
package org.eclipse.acceleo.unit.sample.files.test;

import static org.eclipse.acceleo.unit.core.assertion.AcceleoAssert.assertThatGeneratedArea;
import static org.eclipse.acceleo.unit.core.assertion.AcceleoAssert.assertThatTemplateResultFrom;
import static org.eclipse.acceleo.unit.core.matchers.TemplateExp.and;
import static org.eclipse.acceleo.unit.core.matchers.TemplateExp.templateLocation;
import static org.eclipse.acceleo.unit.core.matchers.TemplateExp.templateText;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.unit.core.annotation.CompiledModuleTest;
import org.eclipse.acceleo.unit.core.annotation.TemplateTest;
import org.eclipse.acceleo.unit.core.generation.Region;
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

@CompiledModuleTest(emtl = "org.eclipse.acceleo.unit.sample/bin/org/eclipse/acceleo/unit/sample/files/templatesGenerateFile.emtl")
@RunWith(AcceleoSuite.class)
public class TemplatesGenerateFileTest {
	
	private static EPackage testPackage;
	private static EObject testClass;
	
	private static final String MODEL_PROJECT_NAME = "org.eclipse.acceleo.unit.sample";
	private static final String MODEL_RELATIVE_PATH = "model/sample.ecore";
	
	private static final String FIRST_CONTENT_$0 = "This is my template ";
	private static final String FIRST_CONTENT_$1 = "sample";
	private static final String FIRST_CONTENT = FIRST_CONTENT_$0 + FIRST_CONTENT_$1;
	
	private static final String SECOND_CONTENT_$0 = "This is another template ";
	private static final String SECOND_CONTENT_$1 = "sample";
	private static final String SECOND_CONTENT = SECOND_CONTENT_$0 + SECOND_CONTENT_$1;
	
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
				
				EObject eObject = ModelUtils.load(URI.createURI("platform:/plugin/" + MODEL_PROJECT_NAME + "/" + MODEL_RELATIVE_PATH), new ResourceSetImpl());
				return new EObject[]{eObject};
			} else {
				String path = System.getProperty("user.dir");
				File filePath = new File(path);
				filePath = new File(filePath,  "../" + MODEL_PROJECT_NAME + "/" + MODEL_RELATIVE_PATH);
				EObject eObject = ModelUtils.load(URI.createFileURI(filePath.getAbsolutePath()), new ResourceSetImpl());
				return new EObject[]{eObject};
			}			
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return new EObject[]{};
	}
	
	/**
	 * Test the first template with assertThatGeneratedArea.
	 * @param helper
	 */
	@TemplateTest(qualifiedName = "generateElement")
	public void testGenerateElementNoIndex(TemplateGenerationHelper helper) {
		
		helper.activateTraceability();
		helper.setModelElement(testPackage);
		helper.generateFile();
		
		assertThatTemplateResultFrom(helper, is(FIRST_CONTENT));
		
		int firstStrIndex = 0;
		assertThatGeneratedArea(helper, 0, FIRST_CONTENT_$0, templateText(FIRST_CONTENT_$0), is((EObject)testPackage));
		assertThatGeneratedArea(helper, 0, FIRST_CONTENT_$0, templateLocation(193, 213), is((EObject)testPackage));
		assertThatGeneratedArea(helper, 0, FIRST_CONTENT_$0, and(templateLocation(193, 213), templateText(FIRST_CONTENT_$0)), is((EObject)testPackage));
		
		
		int secondStrIndex = firstStrIndex+FIRST_CONTENT_$0.length();
		assertThatGeneratedArea(helper, secondStrIndex, FIRST_CONTENT_$1, templateText("aEPackage.name"), is((EObject)testPackage));
		assertThatGeneratedArea(helper, secondStrIndex, FIRST_CONTENT_$1, templateLocation(214, 228), is((EObject)testPackage));
		List<Region> generatedRegionsFor = helper.getGeneratedRegionsFor(testPackage);
		assertEquals(2, generatedRegionsFor.size());
		
		Region region_$0 = generatedRegionsFor.get(0);
		assertEquals(FIRST_CONTENT_$0, region_$0.getText());
		assertEquals(firstStrIndex, region_$0.getBeginIndex());
		
		Region region_$1 = generatedRegionsFor.get(1);
		assertEquals(FIRST_CONTENT_$1, region_$1.getText());
		assertEquals(secondStrIndex, region_$1.getBeginIndex());
	}
	
	/**
	 * Test the second template with assertThatGeneratedArea.
	 * @param helper
	 */
	@TemplateTest(qualifiedName = "anotherGenerateElement")
	public void testAnotherGenerateElementNoIndex(TemplateGenerationHelper helper) {
		
		helper.activateTraceability();
		helper.setModelElement(testPackage);
		helper.generateFile();
		
		assertThatTemplateResultFrom(helper, is(SECOND_CONTENT));
		
		int firstStrIndex = 0;
		assertThatGeneratedArea(helper, firstStrIndex, SECOND_CONTENT_$0, templateText(SECOND_CONTENT_$0), is((EObject)testPackage));
		
		int secondStrIndex = firstStrIndex+SECOND_CONTENT_$0.length();
		assertThatGeneratedArea(helper, secondStrIndex, SECOND_CONTENT_$1, templateText("aEPackage.name"), is((EObject)testPackage));
	}
}
