/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.tests.unit.library;

import java.util.List;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.tests.unit.AbstractTraceabilityTest;
import org.eclipse.acceleo.traceability.tests.unit.AcceleoTraceabilityListener;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceleoTraceabilityLibraryStringTests extends AbstractTraceabilityTest {

	@Test
	public void testTraceabilityLibraryStringReplace() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringReplace.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals(("clazs" + cpt).length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("cla".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(180, propertyCallExp.getStartPosition());
			assertEquals(180 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("cla".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("claz".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("z", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(205, string.getStartPosition());
			assertEquals(205 + "'z'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("claz".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("clazs" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(180, propertyCallExp.getStartPosition());
			assertEquals(180 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringReplaceQuery() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringReplaceQuery.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals(("clazs" + cpt).length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("cla".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(185, propertyCallExp.getStartPosition());
			assertEquals(185 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("cla".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("claz".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("z", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(301, string.getStartPosition());
			assertEquals(301 + "'z'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("claz".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("clazs" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(185, propertyCallExp.getStartPosition());
			assertEquals(185 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringReplaceTemplate() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringReplaceTemplate.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals(("clazs" + cpt).length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("cla".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(188, propertyCallExp.getStartPosition());
			assertEquals(188 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("cla".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("claz".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("z", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(296, string.getStartPosition());
			assertEquals(296 + "z".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("claz".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("clazs" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(188, propertyCallExp.getStartPosition());
			assertEquals(188 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringReplaceAll() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringReplaceAll.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(4, generatedRegions.size());
			assertEquals(("clazz" + cpt).length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("cla".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(183, propertyCallExp.getStartPosition());
			assertEquals(183 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("cla".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("claz".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("z", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(211, string.getStartPosition());
			assertEquals(211 + "'z'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("claz".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("clazz".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("z", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(211, string.getStartPosition());
			assertEquals(211 + "'z'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("clazz".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("clazz" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(183, propertyCallExp.getStartPosition());
			assertEquals(183 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringReplaceAllFirstLast() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringReplaceAllFirstLast.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(9, generatedRegions.size());
			assertEquals("AbrAcAdAbrA".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(1);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("A".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("A", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(222, string.getStartPosition());
			assertEquals(222 + "'A'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(0);
			assertEquals("A".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("Abr".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("abracadabra", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'abracadabra'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("Abr".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("AbrA".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("A", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(222, string.getStartPosition());
			assertEquals(222 + "'A'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("AbrA".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("AbrAc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("abracadabra", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'abracadabra'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("AbrAc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("AbrAcA".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("A", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(222, string.getStartPosition());
			assertEquals(222 + "'A'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("AbrAcA".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("AbrAcAd".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("abracadabra", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'abracadabra'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(7);
			assertEquals("AbrAcAd".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("AbrAcAda".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("A", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(222, string.getStartPosition());
			assertEquals(222 + "'A'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("AbrAcAdA".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("AbrAcAdAbr".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("abracadabra", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'abracadabra'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(8);
			assertEquals("AbrAcAdAbr".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("AbrAcAdAbrA".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("A", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(222, string.getStartPosition());
			assertEquals(222 + "'A'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringReplaceAllTemplate() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringReplaceAllTemplate.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(7, generatedRegions.size());
			assertEquals("aaanewbbbnewcccnewddd".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("aaa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("aaaoldbbboldcccoldddd", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(191, string.getStartPosition());
			assertEquals(191 + "'aaaoldbbboldcccoldddd'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("aaa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanew".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("new", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(316, string.getStartPosition());
			assertEquals(316 + "new".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("aaanew".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("aaaoldbbboldcccoldddd", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(191, string.getStartPosition());
			assertEquals(191 + "'aaaoldbbboldcccoldddd'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("aaanewbbb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbbnew".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("new", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(316, string.getStartPosition());
			assertEquals(316 + "new".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("aaanewbbbnew".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbbnewccc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("aaaoldbbboldcccoldddd", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(191, string.getStartPosition());
			assertEquals(191 + "'aaaoldbbboldcccoldddd'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("aaanewbbbnewccc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbbnewcccnew".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("new", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(316, string.getStartPosition());
			assertEquals(316 + "new".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("aaanewbbbnewcccnew".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbbnewcccnewddd".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("aaaoldbbboldcccoldddd", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(191, string.getStartPosition());
			assertEquals(191 + "'aaaoldbbboldcccoldddd'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringReplaceAllQuery() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringReplaceAllQuery.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(7, generatedRegions.size());
			assertEquals("aaanewbbbnewcccnewddd".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("aaa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("aaaoldbbboldcccoldddd", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(188, string.getStartPosition());
			assertEquals(188 + "'aaaoldbbboldcccoldddd'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("aaa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanew".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("new", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(321, string.getStartPosition());
			assertEquals(321 + "'new'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("aaanew".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("aaaoldbbboldcccoldddd", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(188, string.getStartPosition());
			assertEquals(188 + "'aaaoldbbboldcccoldddd'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("aaanewbbb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbbnew".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("new", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(321, string.getStartPosition());
			assertEquals(321 + "'new'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("aaanewbbbnew".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbbnewccc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("aaaoldbbboldcccoldddd", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(188, string.getStartPosition());
			assertEquals(188 + "'aaaoldbbboldcccoldddd'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("aaanewbbbnewccc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbbnewcccnew".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("new", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(321, string.getStartPosition());
			assertEquals(321 + "'new'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("aaanewbbbnewcccnew".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaanewbbbnewcccnewddd".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("aaaoldbbboldcccoldddd", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(188, string.getStartPosition());
			assertEquals(188 + "'aaaoldbbboldcccoldddd'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringCollection() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringCollection.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("abc".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("a".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("a".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("ab".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(197, string.getStartPosition());
			assertEquals(197 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("ab".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("abc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(202, string.getStartPosition());
			assertEquals(202 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringCollectionReverse() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringCollectionReverse.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("cbbaaa".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("c".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(212, string.getStartPosition());
			assertEquals(212 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("c".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("cbb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("bb", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(206, string.getStartPosition());
			assertEquals(206 + "'bb'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("cbb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("cbbaaa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("aaa", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(199, string.getStartPosition());
			assertEquals(199 + "'aaa'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringCollectionReverse2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringCollectionReverse2.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(4, generatedRegions.size());
			assertEquals("class4class3class2class1".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("class4".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(244, propertyCallExp.getStartPosition());
			assertEquals(244 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class4", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(1);
			assertEquals("class4".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class4class3".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(244, propertyCallExp.getStartPosition());
			assertEquals(244 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class3", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(2);
			assertEquals("class4class3".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class4class3class2".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(244, propertyCallExp.getStartPosition());
			assertEquals(244 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class2", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(3);
			assertEquals("class4class3class2".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class4class3class2class1".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(244, propertyCallExp.getStartPosition());
			assertEquals(244 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class1", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringCollectionSep() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringCollectionSep.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(5, generatedRegions.size());
			assertEquals("a, b, c".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("a".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("a, ".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("a, b".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(197, string.getStartPosition());
			assertEquals(197 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("a".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("a, ".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals(", ", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(212, string.getStartPosition());
			assertEquals(212 + "', '".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("a, b, ".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("a, b, c".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(202, string.getStartPosition());
			assertEquals(202 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("a, b".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("a, b, ".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals(", ", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(212, string.getStartPosition());
			assertEquals(212 + "', '".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringCollectionSep2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringCollectionSep2.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(7, generatedRegions.size());
			assertEquals("class1, class2, class3, class4".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("class1".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(240, propertyCallExp.getStartPosition());
			assertEquals(240 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class1", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(4);
			assertEquals("class1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1, ".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(", ", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(250, string.getStartPosition());
			assertEquals(250 + "', '".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("class1, ".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1, class2".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(240, propertyCallExp.getStartPosition());
			assertEquals(240 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class2", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(5);
			assertEquals("class1, class2".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1, class2, ".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals(", ", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(250, string.getStartPosition());
			assertEquals(250 + "', '".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("class1, class2, ".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1, class2, class3".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(240, propertyCallExp.getStartPosition());
			assertEquals(240 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class3", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(6);
			assertEquals("class1, class2, class3".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1, class2, class3, ".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals(", ", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(250, string.getStartPosition());
			assertEquals(250 + "', '".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("class1, class2, class3, ".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1, class2, class3, class4".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(240, propertyCallExp.getStartPosition());
			assertEquals(240 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class4", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringCollectionFirst() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringCollectionFirst.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("a".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("a".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringCollectionFirst2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringCollectionFirst2.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("a".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("a".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringCollectionLast() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringCollectionLast.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("c".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("a".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringCollectionLast2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringCollectionLast2.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("c".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("a".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, string.getStartPosition());
			assertEquals(192 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringIsAlpha() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringIsAlpha.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("false".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("false".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(180, propertyCallExp.getStartPosition());
			assertEquals(180 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringIsAlpha2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringIsAlpha2.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("false".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("false".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp stringLiteralExp = (StringLiteralExp)element;
			assertEquals(" ljdql ", stringLiteralExp.getStringSymbol()); //$NON-NLS-1$
			assertEquals(181, stringLiteralExp.getStartPosition());
			assertEquals(181 + "' ljdql '".length(), stringLiteralExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringIsAlphaNum() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringIsAlphaNum.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("clas".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("clas".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(183, propertyCallExp.getStartPosition());
			assertEquals(183 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringIsAlphaNum2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringIsAlphaNum2.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("false".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("false".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp stringLiteralExp = (StringLiteralExp)element;
			assertEquals(" hsdkhs 545", stringLiteralExp.getStringSymbol()); //$NON-NLS-1$
			assertEquals(184, stringLiteralExp.getStartPosition());
			assertEquals(184 + "' hsdkhs 545'".length(), stringLiteralExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringSubstitute() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringSubstitute.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals(("clazs" + cpt).length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("cla".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(183, propertyCallExp.getStartPosition());
			assertEquals(183 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("cla".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("claz".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("z", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(211, string.getStartPosition());
			assertEquals(211 + "'z'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("claz".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("clazs" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(183, propertyCallExp.getStartPosition());
			assertEquals(183 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringFirst() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringFirst.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("clas".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("clas".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(178, propertyCallExp.getStartPosition());
			assertEquals(178 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringIndex() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringIndex.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("4".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("2".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(178, propertyCallExp.getStartPosition());
			assertEquals(178 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringStrComp() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringStrComp.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("10".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("2".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(178, propertyCallExp.getStartPosition());
			assertEquals(178 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringStrStr() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringStrStr.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("true".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("2".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(178, propertyCallExp.getStartPosition());
			assertEquals(178 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringStrTok() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringStrTok.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("strt".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("2".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(178, propertyCallExp.getStartPosition());
			assertEquals(178 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringSubstring() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringSubstring.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("clas".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("clas".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(182, propertyCallExp.getStartPosition());
			assertEquals(182 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringTokenize() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringTokenize.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(4, generatedRegions.size());
			assertEquals("tokniz opration".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("clas".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(183, propertyCallExp.getStartPosition());
			assertEquals(183 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryStringTrim() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/string/stringTrim.mtl", //$NON-NLS-1$
				"main", "data/library/string/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("trim operation".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("trim operation".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(" trim operation ", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(177, string.getStartPosition());
			assertEquals(177 + "' trim operation '".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/string/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}
}
