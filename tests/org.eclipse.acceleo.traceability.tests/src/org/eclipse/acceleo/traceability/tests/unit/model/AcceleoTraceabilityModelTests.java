/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.tests.unit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.tests.unit.AbstractTraceabilityTest;
import org.eclipse.acceleo.traceability.tests.unit.AcceleoTraceabilityListener;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Test;

public class AcceleoTraceabilityModelTests extends AbstractTraceabilityTest {

	@Test
	public void testTraceabilityModelSimple() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/model/modelSimple.mtl", //$NON-NLS-1$
				"main", "data/model/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals(("class" + cpt).length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(("class" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCall = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCall.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(String.class));
			assertEquals("eClass.name", propertyCall.toString()); //$NON-NLS-1$
			assertEquals(178, propertyCall.getStartPosition());
			assertEquals(178 + "eClass.name".length(), propertyCall.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/model/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityModelMetamodel() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/model/modelMetamodel.mtl", //$NON-NLS-1$
				"main", "data/model/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals(("class" + cpt + "EClass").length(), generatedFile.getLength()); //$NON-NLS-1$ //$NON-NLS-2$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(("class" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$ 
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCall = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCall.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(String.class));
			assertEquals("eClass.name", propertyCall.toString()); //$NON-NLS-1$
			assertEquals(181, propertyCall.getStartPosition());
			assertEquals(181 + "eClass.name".length(), propertyCall.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/model/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + "EClass").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCall = (PropertyCallExp)element;
			eGenericType = propertyCall.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(String.class));
			assertEquals("eClass.eClass().name", propertyCall.toString()); //$NON-NLS-1$
			assertEquals(195, propertyCall.getStartPosition());
			assertEquals(195 + "eClass.eClass().name".length(), propertyCall.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("EClass", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/emf/2002/Ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityModelPropertyOperationSize() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/model/modelPropertyOperationSize.mtl", //$NON-NLS-1$
				"main", "data/model/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("6".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("6".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCall = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCall.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(String.class));
			assertEquals("eClass.name", propertyCall.toString()); //$NON-NLS-1$
			assertEquals(193, propertyCall.getStartPosition());
			assertEquals(193 + "eClass.name".length(), propertyCall.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/model/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityModelEnum() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate("data/model/modelEnum.mtl", //$NON-NLS-1$
				"main", "data/model/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(1, generatedFiles.size());

		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals(("MyEnum").length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(1, sourceElements.size()); // the class and its name
			assertEquals("MyEnum", sourceElements.get(0).toString()); //$NON-NLS-1$ 
			assertEquals("enum.txt", generatedFile.getPath()); //$NON-NLS-1$ 

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(("MyEnum").length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCall = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCall.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(String.class));
			assertEquals("eEnum.name", propertyCall.toString()); //$NON-NLS-1$
			assertEquals(164, propertyCall.getStartPosition());
			assertEquals(164 + "eEnum.name".length(), propertyCall.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EEnum);
			assertEquals("MyEnum", ((EEnum)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/model/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
		}
	}

	@Test
	public void testTraceabilityModelEnumToString() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/model/modelEnumToString.mtl", //$NON-NLS-1$
				"main", "data/model/model.uml", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(1, generatedFiles.size());

		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals(("private").length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(1, sourceElements.size()); // the class and its name
			assertEquals("myoperation", sourceElements.get(0).toString()); //$NON-NLS-1$ 
			assertEquals("operation.txt", generatedFile.getPath()); //$NON-NLS-1$ 

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(("private").length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCall = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCall.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(VisibilityKind.class));
			assertEquals("operation.visibility", propertyCall.toString()); //$NON-NLS-1$
			assertEquals(185, propertyCall.getStartPosition());
			assertEquals(185 + "operation.visibility".length(), propertyCall.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof Operation);
			assertEquals("myoperation", ((Operation)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/model/model.uml", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
		}
	}

	@Test
	public void testTraceabilityModelEnumString() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/model/modelEnumString.mtl", //$NON-NLS-1$
				"main", "data/model/model.uml", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(1, generatedFiles.size());

		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals(("private").length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(1, sourceElements.size()); // the class and its name
			assertEquals("myoperation", sourceElements.get(0).toString()); //$NON-NLS-1$ 
			assertEquals("operation.txt", generatedFile.getPath()); //$NON-NLS-1$ 

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(("private").length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCall = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCall.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(VisibilityKind.class));
			assertEquals("operation.visibility", propertyCall.toString()); //$NON-NLS-1$
			assertEquals(183, propertyCall.getStartPosition());
			assertEquals(183 + "operation.visibility".length(), propertyCall.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof Operation);
			assertEquals("myoperation", ((Operation)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/model/model.uml", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
		}
	}
}
