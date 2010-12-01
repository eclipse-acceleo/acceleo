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
package org.eclipse.acceleo.traceability.tests.unit.block;

import java.util.List;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.tests.unit.AbstractTraceabilityTest;
import org.eclipse.acceleo.traceability.tests.unit.AcceleoTraceabilityListener;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceleoTraceabilityBlockTests extends AbstractTraceabilityTest {

	@Test
	public void testTraceabilityBlockFile() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate("data/block/blockFile.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(0, generatedRegions.size());
			assertEquals(0, generatedFile.getLength());

			List<GeneratedText> nameRegions = generatedFile.getNameRegions();
			assertEquals(2, nameRegions.size());
			assertEquals("class" + cpt, generatedFile.getName()); //$NON-NLS-1$

			GeneratedText generatedText = nameRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(("class" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCallExp.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(String.class));
			assertEquals("eClass.name", propertyCallExp.toString()); //$NON-NLS-1$
			assertEquals(137, propertyCallExp.getStartPosition());
			assertEquals(137 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = nameRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + ".txt").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(".txt", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(151, string.getStartPosition());
			assertEquals(151 + "'.txt'".length(), string.getEndPosition()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockFileConcat() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockFileConcat.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(0, generatedRegions.size());
			assertEquals(0, generatedFile.getLength());

			List<GeneratedText> nameRegions = generatedFile.getNameRegions();
			assertEquals(2, nameRegions.size());
			assertEquals("class" + cpt, generatedFile.getName()); //$NON-NLS-1$

			GeneratedText generatedText = nameRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(("class" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCallExp.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(String.class));
			assertEquals("eClass.name", propertyCallExp.toString()); //$NON-NLS-1$
			assertEquals(137, propertyCallExp.getStartPosition());
			assertEquals(137 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = nameRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + ".txt").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(".txt", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(156, string.getStartPosition());
			assertEquals(156 + "'.txt'".length(), string.getEndPosition()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockFileQuery() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockFileQuery.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(0, generatedRegions.size());
			assertEquals(0, generatedFile.getLength());

			List<GeneratedText> nameRegions = generatedFile.getNameRegions();
			assertEquals(2, nameRegions.size());
			assertEquals("class" + cpt, generatedFile.getName()); //$NON-NLS-1$

			GeneratedText generatedText = nameRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(("class" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCallExp.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(String.class));
			assertEquals("eClass.name", propertyCallExp.toString()); //$NON-NLS-1$
			assertEquals(254, propertyCallExp.getStartPosition());
			assertEquals(254 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = nameRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + ".txt").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(".txt", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(158, string.getStartPosition());
			assertEquals(158 + "'.txt'".length(), string.getEndPosition()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockFileTemplate() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockFileTemplate.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(0, generatedRegions.size());
			assertEquals(0, generatedFile.getLength());

			List<GeneratedText> nameRegions = generatedFile.getNameRegions();
			assertEquals(2, nameRegions.size());
			assertEquals("class" + cpt, generatedFile.getName()); //$NON-NLS-1$

			GeneratedText generatedText = nameRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(("class" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			EGenericType eGenericType = propertyCallExp.getEGenericType();
			assertTrue(eGenericType.getERawType().getInstanceClass().equals(String.class));
			assertEquals("eClass.name", propertyCallExp.toString()); //$NON-NLS-1$
			assertEquals(245, propertyCallExp.getStartPosition());
			assertEquals(245 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = nameRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + ".txt").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(".txt", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(157, string.getStartPosition());
			assertEquals(157 + "'.txt'".length(), string.getEndPosition()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockIf() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate("data/block/blockIf.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("blockIf".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("blockIf".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("blockIf", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(264, string.getStartPosition());
			assertEquals(264 + "blockIf".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockIfQuery() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockIfQuery.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("blockIf".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("blockIf".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("blockIf", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(283, string.getStartPosition());
			assertEquals(283 + "blockIf".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockIfTemplate() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockIfTemplate.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("blockIf".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("blockIf".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("blockIf", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(264, string.getStartPosition());
			assertEquals(264 + "blockIf".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockElse() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate("data/block/blockElse.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("blockElse".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("blockElse".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("blockElse", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(277, string.getStartPosition());
			assertEquals(277 + "blockElse".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockFor() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate("data/block/blockFor.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("blockFor".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			int gencpt = 0;
			for (GeneratedText generatedText : generatedRegions) {
				assertEquals(0 + ("blockFor".length() * gencpt), generatedText.getStartOffset()); //$NON-NLS-1$
				assertEquals("blockFor".length() * (gencpt + 1), generatedText.getEndOffset()); //$NON-NLS-1$
				ModuleElement moduleElement = generatedText.getModuleElement();
				EObject element = moduleElement.getModuleElement();
				assertTrue(element instanceof ASTNode);
				assertTrue(element instanceof StringLiteralExp);
				StringLiteralExp string = (StringLiteralExp)element;
				assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
				assertEquals(281, string.getStartPosition());
				assertEquals(281 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

				InputElement sourceElement = generatedText.getSourceElement();
				EObject modelElement = sourceElement.getModelElement();
				assertTrue(modelElement instanceof EClass);
				assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
				assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
						modelElement.eResource().getURI().path());
				gencpt++;
			}
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockForQuery() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockForQuery.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("blockFor".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			int gencpt = 0;
			for (GeneratedText generatedText : generatedRegions) {
				assertEquals(0 + ("blockFor".length() * gencpt), generatedText.getStartOffset()); //$NON-NLS-1$
				assertEquals("blockFor".length() * (gencpt + 1), generatedText.getEndOffset()); //$NON-NLS-1$
				ModuleElement moduleElement = generatedText.getModuleElement();
				EObject element = moduleElement.getModuleElement();
				assertTrue(element instanceof ASTNode);
				assertTrue(element instanceof StringLiteralExp);
				StringLiteralExp string = (StringLiteralExp)element;
				assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
				assertEquals(291, string.getStartPosition());
				assertEquals(291 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

				InputElement sourceElement = generatedText.getSourceElement();
				EObject modelElement = sourceElement.getModelElement();
				assertTrue(modelElement instanceof EClass);
				assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
				assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
						modelElement.eResource().getURI().path());
				gencpt++;
			}
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockForQuery2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockForQuery2.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(6, generatedRegions.size());
			assertEquals("blockFor".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			int gencpt = 0;
			for (GeneratedText generatedText : generatedRegions) {
				assertEquals(0 + ("blockFor".length() * gencpt), generatedText.getStartOffset()); //$NON-NLS-1$
				assertEquals("blockFor".length() * (gencpt + 1), generatedText.getEndOffset()); //$NON-NLS-1$
				ModuleElement moduleElement = generatedText.getModuleElement();
				EObject element = moduleElement.getModuleElement();
				assertTrue(element instanceof ASTNode);
				assertTrue(element instanceof StringLiteralExp);
				StringLiteralExp string = (StringLiteralExp)element;
				assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
				assertEquals(281, string.getStartPosition());
				assertEquals(281 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

				InputElement sourceElement = generatedText.getSourceElement();
				EObject modelElement = sourceElement.getModelElement();
				assertTrue(modelElement instanceof EClass);
				assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
				assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
						modelElement.eResource().getURI().path());
				gencpt++;
			}
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockForQuery3() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockForQuery3.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(9, generatedRegions.size());
			assertEquals("blockFor".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			int gencpt = 0;
			for (GeneratedText generatedText : generatedRegions) {
				assertEquals(0 + ("blockFor".length() * gencpt), generatedText.getStartOffset()); //$NON-NLS-1$
				assertEquals("blockFor".length() * (gencpt + 1), generatedText.getEndOffset()); //$NON-NLS-1$
				ModuleElement moduleElement = generatedText.getModuleElement();
				EObject element = moduleElement.getModuleElement();
				assertTrue(element instanceof ASTNode);
				assertTrue(element instanceof StringLiteralExp);
				StringLiteralExp string = (StringLiteralExp)element;
				assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
				assertEquals(281, string.getStartPosition());
				assertEquals(281 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

				InputElement sourceElement = generatedText.getSourceElement();
				EObject modelElement = sourceElement.getModelElement();
				assertTrue(modelElement instanceof EClass);
				assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
				assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
						modelElement.eResource().getURI().path());
				gencpt++;
			}
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockForTemplate() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockForTemplate.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("blockFor".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			int gencpt = 0;
			for (GeneratedText generatedText : generatedRegions) {
				assertEquals(0 + ("blockFor".length() * gencpt), generatedText.getStartOffset()); //$NON-NLS-1$
				assertEquals("blockFor".length() * (gencpt + 1), generatedText.getEndOffset()); //$NON-NLS-1$
				ModuleElement moduleElement = generatedText.getModuleElement();
				EObject element = moduleElement.getModuleElement();
				assertTrue(element instanceof ASTNode);
				assertTrue(element instanceof StringLiteralExp);
				StringLiteralExp string = (StringLiteralExp)element;
				assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
				assertEquals(281, string.getStartPosition());
				assertEquals(281 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

				InputElement sourceElement = generatedText.getSourceElement();
				EObject modelElement = sourceElement.getModelElement();
				assertTrue(modelElement instanceof EClass);
				assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
				assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
						modelElement.eResource().getURI().path());
				gencpt++;
			}
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockForTemplate2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockForTemplate2.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("blockFor".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			int gencpt = 0;
			for (GeneratedText generatedText : generatedRegions) {
				assertEquals(0 + ("blockFor".length() * gencpt), generatedText.getStartOffset()); //$NON-NLS-1$
				assertEquals("blockFor".length() * (gencpt + 1), generatedText.getEndOffset()); //$NON-NLS-1$
				ModuleElement moduleElement = generatedText.getModuleElement();
				EObject element = moduleElement.getModuleElement();
				assertTrue(element instanceof ASTNode);
				assertTrue(element instanceof StringLiteralExp);
				StringLiteralExp string = (StringLiteralExp)element;
				assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
				assertEquals(281, string.getStartPosition());
				assertEquals(281 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

				InputElement sourceElement = generatedText.getSourceElement();
				EObject modelElement = sourceElement.getModelElement();
				assertTrue(modelElement instanceof EClass);
				assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
				assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
						modelElement.eResource().getURI().path());
				gencpt++;
			}
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockForVariableInitialization() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockForVariableInitialization.mtl", "main", "data/block/model.ecore", true); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(6, generatedRegions.size());
			assertEquals("blockFornewName".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("blockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("blockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("blockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("blockFornewNameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("blockFornewNameblockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewNameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("blockFornewNameblockFornewNameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewNameblockFornewName".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockForVariableInitializationTemplate() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockForVariableInitializationTemplate.mtl", "main", "data/block/model.ecore", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				true);
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(9, generatedRegions.size());
			assertEquals("blockFornewNameotherName".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("blockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("blockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("blockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("blockFornewNameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("blockFornewNameblockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewNameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("blockFornewNameblockFornewNameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewNameblockFornewName".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockForVariableInitializationQuery() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockForVariableInitializationQuery.mtl", "main", "data/block/model.ecore", true); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(9, generatedRegions.size());
			assertEquals("blockFornewNameotherName".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("blockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("blockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("blockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("blockFornewNameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("blockFornewNameblockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewNameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("blockFornewNameblockFornewNameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewNameblockFornewName".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockForVariableInitializationOperation() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockForVariableInitializationOperation.mtl", "main", "data/block/model.ecore", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				true);
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(9, generatedRegions.size());
			assertEquals("blockFornewNameotherName".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("blockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("blockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("blockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("blockFornewNameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("blockFornewNameblockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewNameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(337, string.getStartPosition());
			assertEquals(337 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("blockFornewNameblockFornewNameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameblockFornewNameblockFornewName".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(325, string.getStartPosition());
			assertEquals(325 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockLet() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate("data/block/blockLet.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			assertTrue(element instanceof VariableExp);
			VariableExp variableExp = (VariableExp)element;
			assertEquals("name", variableExp.getName()); //$NON-NLS-1$
			assertEquals(291, variableExp.getStartPosition());
			assertEquals(291 + "name".length(), variableExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockLetQuery() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockLetQuery.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			assertTrue(element instanceof VariableExp);
			VariableExp variableExp = (VariableExp)element;
			assertEquals("name", variableExp.getName()); //$NON-NLS-1$
			assertEquals(303, variableExp.getStartPosition());
			assertEquals(303 + "name".length(), variableExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockLetTemplate() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockLetTemplate.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			assertTrue(element instanceof VariableExp);
			VariableExp variableExp = (VariableExp)element;
			assertEquals("name", variableExp.getName()); //$NON-NLS-1$
			assertEquals(291, variableExp.getStartPosition());
			assertEquals(291 + "name".length(), variableExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockProtected() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockProtected.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
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
			assertTrue(element instanceof VariableExp);
			VariableExp variableExp = (VariableExp)element;
			assertEquals("name", variableExp.getName()); //$NON-NLS-1$
			assertEquals(291, variableExp.getStartPosition());
			assertEquals(291 + "name".length(), variableExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockProtectedQuery() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockProtectedQuery.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
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
			assertTrue(element instanceof VariableExp);
			VariableExp variableExp = (VariableExp)element;
			assertEquals("name", variableExp.getName()); //$NON-NLS-1$
			assertEquals(291, variableExp.getStartPosition());
			assertEquals(291 + "name".length(), variableExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityBlockProtectedTemplate() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/block/blockProtectedTemplate.mtl", //$NON-NLS-1$
				"main", "data/block/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
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
			assertTrue(element instanceof VariableExp);
			VariableExp variableExp = (VariableExp)element;
			assertEquals("name", variableExp.getName()); //$NON-NLS-1$
			assertEquals(291, variableExp.getStartPosition());
			assertEquals(291 + "name".length(), variableExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/block/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}
}
