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

import static org.eclipse.acceleo.unit.core.assertion.AcceleoAssert.assertThatQueryResultFrom;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.unit.core.annotation.CompiledModuleTest;
import org.eclipse.acceleo.unit.core.annotation.QueryTest;
import org.eclipse.acceleo.unit.core.generation.QueryGenerationHelper;
import org.eclipse.acceleo.unit.core.suite.AcceleoSuite;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@CompiledModuleTest(emtl = "org.eclipse.acceleo.unit.sample/bin/org/eclipse/acceleo/unit/sample/common/queries.emtl")
@RunWith(AcceleoSuite.class)
public class QueriesTest {


	private static EObject[] modelPackages;
	private static EObject[] modelClass;
	
	@BeforeClass
	public static void setUp() throws Exception {
		modelPackages = loadModel();
		assertNotNull(modelPackages);
		assertNotNull(modelPackages[0]);
		assertTrue(modelPackages[0] instanceof EPackage);
		EPackage ePackage = (EPackage) modelPackages[0];
		modelClass = new EObject[1];
		EList<EClassifier> eClassifiers = ePackage.getEClassifiers();
		for (EClassifier eClassifier : eClassifiers) {
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				modelClass = new EObject[]{eClass};
			}
		}
		assertNotNull(modelClass);
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

	@QueryTest(qualifiedName = "reqTextToHtmlId")
	public void testQueryNoIndex(QueryGenerationHelper helper) {
		helper.setModelElement(modelPackages[0]);
		helper.generate();
		
		EObject modelElement = helper.getModelElement();
		assertThat(modelElement, is(instanceOf(EPackage.class)));
		EPackage ePackage = (EPackage)modelElement;

		EList<EClassifier> eClassifiers = ePackage.getEClassifiers();
		assertThatQueryResultFrom(helper, is(eClassifiers));
	}
	
	@QueryTest(qualifiedName = "reqTextToHtmlId", index=0)
	public void testQueryWithIndex(QueryGenerationHelper helper) {
		helper.setModelElement(modelPackages[0]);
		helper.generate();
		
		EObject modelElement = helper.getModelElement();
		assertThat(modelElement, is(instanceOf(EPackage.class)));
		EPackage ePackage = (EPackage)modelElement;

		EList<EClassifier> eClassifiers = ePackage.getEClassifiers();
		assertThatQueryResultFrom(helper, is(eClassifiers));
	}
	
	@QueryTest(qualifiedName = "reqTextToHtmlId", index = 1)
	public void testQuerytWithAnotherIndex(QueryGenerationHelper helper) {
		helper.setModelElement(modelPackages[0]);
		boolean hasFailed = false;
		try {
			helper.generate();
		} catch (IllegalArgumentException e) {
			hasFailed = true;
		}
		assertTrue(hasFailed);
	}
	
	
	
	
	@QueryTest(qualifiedName = "reqTextToHtmlId")
	public void testGenerateElementNoIndexEClass(QueryGenerationHelper helper) {
		helper.setModelElement(modelClass[0]);
		boolean hasFailed = false;
		try {
			helper.generate();
		} catch (IllegalArgumentException e) {
			hasFailed = true;
		}
		assertTrue(hasFailed);
	}
	
	@QueryTest(qualifiedName = "reqTextToHtmlId", index = 0)
	public void testGenerateElementWithIndexEClass(QueryGenerationHelper helper) {
		helper.setModelElement(modelClass[0]);
		boolean hasFailed = false;
		try {
			helper.generate();
		} catch (IllegalArgumentException e) {
			hasFailed = true;
		}
		assertTrue(hasFailed);
	}
	
	@QueryTest(qualifiedName = "reqTextToHtmlId", index = 1)
	public void testGenerateElementWithAnotherIndexEClass(QueryGenerationHelper helper) {
		helper.setModelElement(modelClass[0]);
		helper.generate();
		
		EObject modelElement = helper.getModelElement();
		assertThat(modelElement, is(instanceOf(EClass.class)));
		EClass eClass = (EClass)modelElement;

		EList<EAttribute> eAttributes =  eClass.getEAllAttributes();
		assertThatQueryResultFrom(helper, is(eAttributes));			
	}
}
