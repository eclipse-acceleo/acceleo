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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.tests.unit.AbstractTraceabilityTest;
import org.eclipse.acceleo.traceability.tests.unit.AcceleoTraceabilityListener;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ecore.IntegerLiteralExp;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.ecore.VariableExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.junit.Test;

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
			assertEquals(143, propertyCallExp.getStartPosition());
			assertEquals(143 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = nameRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + ".txt").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(".txt", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(162, string.getStartPosition());
			assertEquals(162 + "'.txt'".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(259, propertyCallExp.getStartPosition());
			assertEquals(259 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = nameRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + ".txt").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(".txt", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(163, string.getStartPosition());
			assertEquals(163 + "'.txt'".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(253, propertyCallExp.getStartPosition());
			assertEquals(253 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = nameRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + ".txt").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(".txt", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(165, string.getStartPosition());
			assertEquals(165 + "'.txt'".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(308, string.getStartPosition());
			assertEquals(308 + "blockIf".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals("abc".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			int gencpt = 0;
			int expectedStartPosition = 0;
			int expectedEndPosition = 390;
			for (GeneratedText generatedText : generatedRegions) {
				assertEquals(0 + gencpt, generatedText.getStartOffset());
				assertEquals(gencpt + 1, generatedText.getEndOffset());
				ModuleElement moduleElement = generatedText.getModuleElement();
				EObject element = moduleElement.getModuleElement();
				assertTrue(element instanceof ASTNode);
				assertTrue(element instanceof StringLiteralExp);
				StringLiteralExp string = (StringLiteralExp)element;
				assertEquals(String.valueOf((char)('a' + gencpt)), string.getStringSymbol());
				expectedStartPosition = expectedEndPosition + 2;
				expectedEndPosition = expectedStartPosition
						+ String.valueOf('\'' + (char)('a' + gencpt) + '\'').length();
				assertEquals(expectedStartPosition, string.getStartPosition());
				assertEquals(expectedEndPosition, string.getEndPosition());

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
			assertEquals(("begin".length() + 1 + "end".length()) * 2, generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedFile.getGeneratedRegions().get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("begin".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("begin", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(403, string.getStartPosition());
			assertEquals(403 + "'begin'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(1);
			assertEquals("begin".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("begina".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(293, string.getStartPosition());
			assertEquals(293 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(2);
			assertEquals("begina".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaend".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("end", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(419, string.getStartPosition());
			assertEquals(419 + "'end'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(3);
			assertEquals("beginaend".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaendbegin".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("begin", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(403, string.getStartPosition());
			assertEquals(403 + "'begin'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(4);
			assertEquals("beginaendbegin".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaendbeginb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(315, string.getStartPosition());
			assertEquals(315 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(5);
			assertEquals("beginaendbeginb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaendbeginbend".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("end", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(419, string.getStartPosition());
			assertEquals(419 + "'end'".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(("begin".length() + 1 + "end".length()) * 3, generatedFile.getLength()); //$NON-NLS-1$ //$NON-NLS-2$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedFile.getGeneratedRegions().get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("begin".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("begin", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(402, string.getStartPosition());
			assertEquals(402 + "'begin'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(1);
			assertEquals("begin".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("begina".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(277, string.getStartPosition());
			assertEquals(277 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(2);
			assertEquals("begina".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaend".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("end", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(418, string.getStartPosition());
			assertEquals(418 + "'end'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(3);
			assertEquals("beginaend".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaendbegin".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("begin", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(402, string.getStartPosition());
			assertEquals(402 + "'begin'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(4);
			assertEquals("beginaendbegin".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaendbeginb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(282, string.getStartPosition());
			assertEquals(282 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(5);
			assertEquals("beginaendbeginb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaendbeginbend".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("end", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(418, string.getStartPosition());
			assertEquals(418 + "'end'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(6);
			assertEquals("beginaendbeginbend".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaendbeginbendbegin".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("begin", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(402, string.getStartPosition());
			assertEquals(402 + "'begin'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(7);
			assertEquals("beginaendbeginbendbegin".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaendbeginbendbeginc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(287, string.getStartPosition());
			assertEquals(287 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedFile.getGeneratedRegions().get(8);
			assertEquals("beginaendbeginbendbeginc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("beginaendbeginbendbegincend".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("end", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(418, string.getStartPosition());
			assertEquals(418 + "'end'".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals("123".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			int gencpt = 1;
			for (GeneratedText generatedText : generatedRegions) {
				assertEquals(0 + gencpt - 1, generatedText.getStartOffset());
				assertEquals(gencpt, generatedText.getEndOffset());
				ModuleElement moduleElement = generatedText.getModuleElement();
				EObject element = moduleElement.getModuleElement();
				assertTrue(element instanceof ASTNode);
				assertTrue(element instanceof IntegerLiteralExp);
				IntegerLiteralExp integer = (IntegerLiteralExp)element;
				assertEquals(Integer.valueOf(gencpt), integer.getIntegerSymbol());
				assertEquals(279 + ((gencpt - 1) * 3), integer.getStartPosition());
				assertEquals(279 + ((gencpt - 1) * 3) + Integer.valueOf(gencpt).toString().length(), integer
						.getEndPosition());

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
			assertEquals(338, string.getStartPosition());
			assertEquals(338 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(338, string.getStartPosition());
			assertEquals(338 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(338, string.getStartPosition());
			assertEquals(338 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(388, string.getStartPosition());
			assertEquals(388 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("blockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(333, string.getStartPosition());
			assertEquals(333 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("blockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameothername".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("otherName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(481, string.getStartPosition());
			assertEquals(481 + "otherName".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("blockFornewNameothername".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameothernameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(388, string.getStartPosition());
			assertEquals(388 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("blockFornewNameothernameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameothernameblockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(333, string.getStartPosition());
			assertEquals(333 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("blockFornewNameothernameblockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameothernameblockFornewNameothername".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("otherName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(481, string.getStartPosition());
			assertEquals(481 + "otherName".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("blockFornewNameothernameblockFornewNameothername".length(), generatedText //$NON-NLS-1$
					.getStartOffset());
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFor".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(388, string.getStartPosition());
			assertEquals(388 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(7);
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFor".length(), generatedText //$NON-NLS-1$
					.getStartOffset());
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFornewName".length(), //$NON-NLS-1$
					generatedText.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(333, string.getStartPosition());
			assertEquals(333 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(8);
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFornewName".length(), //$NON-NLS-1$
					generatedText.getStartOffset());
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFornewNameothername".length(), //$NON-NLS-1$
					generatedText.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("otherName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(481, string.getStartPosition());
			assertEquals(481 + "otherName".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(385, string.getStartPosition());
			assertEquals(385 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("blockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(330, string.getStartPosition());
			assertEquals(330 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("blockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameothername".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("otherName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(486, string.getStartPosition());
			assertEquals(486 + "'otherName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("blockFornewNameothername".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameothernameblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(385, string.getStartPosition());
			assertEquals(385 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("blockFornewNameothernameblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameothernameblockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(330, string.getStartPosition());
			assertEquals(330 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("blockFornewNameothernameblockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameothernameblockFornewNameothername".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("otherName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(486, string.getStartPosition());
			assertEquals(486 + "'otherName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("blockFornewNameothernameblockFornewNameothername".length(), generatedText //$NON-NLS-1$
					.getStartOffset());
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFor".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(385, string.getStartPosition());
			assertEquals(385 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(7);
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFor".length(), generatedText //$NON-NLS-1$
					.getStartOffset());
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFornewName".length(), //$NON-NLS-1$
					generatedText.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(330, string.getStartPosition());
			assertEquals(330 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(8);
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFornewName".length(), //$NON-NLS-1$
					generatedText.getStartOffset());
			assertEquals("blockFornewNameothernameblockFornewNameothernameblockFornewNameothername".length(), //$NON-NLS-1$
					generatedText.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("otherName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(486, string.getStartPosition());
			assertEquals(486 + "'otherName'".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(12, generatedRegions.size());
			assertEquals("blockFornewNameclaYOP".length() * 3, generatedFile.getLength()); //$NON-NLS-1$

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
			assertEquals(412, string.getStartPosition());
			assertEquals(412 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("blockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(334, string.getStartPosition());
			assertEquals(334 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("blockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNamecla".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("eClass.name", propertyCallExp.toString()); //$NON-NLS-1$
			assertEquals(367, propertyCallExp.getStartPosition());
			assertEquals(367 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("blockFornewNamecla".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameclaYOP".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("YOP", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(402, string.getStartPosition());
			assertEquals(402 + "'YOP'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("blockFornewNameclaYOP".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameclaYOPblockFor".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(412, string.getStartPosition());
			assertEquals(412 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("blockFornewNameclaYOPblockFor".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameclaYOPblockFornewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(334, string.getStartPosition());
			assertEquals(334 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("blockFornewNameclaYOPblockFornewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameclaYOPblockFornewNamecla".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("eClass.name", propertyCallExp.toString()); //$NON-NLS-1$
			assertEquals(367, propertyCallExp.getStartPosition());
			assertEquals(367 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(7);
			assertEquals("blockFornewNameclaYOPblockFornewNamecla".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("blockFornewNameclaYOPblockFornewNameclaYOP".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("YOP", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(402, string.getStartPosition());
			assertEquals(402 + "'YOP'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(8);
			assertEquals("blockFornewNameclaYOPblockFornewNameclaYOP".length(), generatedText //$NON-NLS-1$
					.getStartOffset());
			assertEquals("blockFornewNameclaYOPblockFornewNameclaYOPblockFor".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("blockFor", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(412, string.getStartPosition());
			assertEquals(412 + "blockFor".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(9);
			assertEquals("blockFornewNameclaYOPblockFornewNameclaYOPblockFor".length(), generatedText //$NON-NLS-1$
					.getStartOffset());
			assertEquals("blockFornewNameclaYOPblockFornewNameclaYOPblockFornewName".length(), generatedText //$NON-NLS-1$
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(334, string.getStartPosition());
			assertEquals(334 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(10);
			assertEquals("blockFornewNameclaYOPblockFornewNameclaYOPblockFornewName".length(), generatedText //$NON-NLS-1$
					.getStartOffset());
			assertEquals("blockFornewNameclaYOPblockFornewNameclaYOPblockFornewNamecla".length(), //$NON-NLS-1$
					generatedText.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("eClass.name", propertyCallExp.toString()); //$NON-NLS-1$
			assertEquals(367, propertyCallExp.getStartPosition());
			assertEquals(367 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(11);
			assertEquals("blockFornewNameclaYOPblockFornewNameclaYOPblockFornewNamecla".length(), //$NON-NLS-1$
					generatedText.getStartOffset());
			assertEquals("blockFornewNameclaYOPblockFornewNameclaYOPblockFornewNameclaYOP".length(), //$NON-NLS-1$
					generatedText.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("YOP", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(402, string.getStartPosition());
			assertEquals(402 + "'YOP'".length(), string.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(309, variableExp.getStartPosition());
			assertEquals(309 + "name".length(), variableExp.getEndPosition()); //$NON-NLS-1$

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
			assertEquals(4, generatedRegions.size());
			assertEquals("start of user code protected\nend of user code".length(), generatedFile //$NON-NLS-1$
					.getLength());

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("start of user code ".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof ProtectedAreaBlock);
			ProtectedAreaBlock protectedAreaBlock = (ProtectedAreaBlock)element;
			assertTrue(protectedAreaBlock.getMarker() instanceof StringLiteralExp);
			assertEquals("protected", ((StringLiteralExp)protectedAreaBlock.getMarker()).getStringSymbol()); //$NON-NLS-1$
			assertEquals(180, protectedAreaBlock.getStartPosition());
			assertEquals(180 + "[protected ('protected')]\n[/protected]".length(), protectedAreaBlock //$NON-NLS-1$
					.getEndPosition());

			generatedText = generatedRegions.get(1);
			assertEquals("start of user code ".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("start of user code protected".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp stringLiteralExp = (StringLiteralExp)element;
			assertEquals("protected", stringLiteralExp.getStringSymbol()); //$NON-NLS-1$
			assertEquals(192, stringLiteralExp.getStartPosition());
			assertEquals(192 + "'protected'".length(), stringLiteralExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("start of user code protected".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("start of user code protected\n".length(), generatedText.getEndOffset()); //$NON-NLS-1$ 
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			stringLiteralExp = (StringLiteralExp)element;
			assertEquals("\n", stringLiteralExp.getStringSymbol()); //$NON-NLS-1$
			assertEquals(205, stringLiteralExp.getStartPosition());
			assertEquals(205 + "\n".length(), stringLiteralExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("start of user code protected\n".length(), generatedText.getStartOffset()); //$NON-NLS-1$ 
			assertEquals("start of user code protected\nend of user code".length(), generatedText //$NON-NLS-1$ 
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof ProtectedAreaBlock);
			protectedAreaBlock = (ProtectedAreaBlock)element;
			assertTrue(protectedAreaBlock.getMarker() instanceof StringLiteralExp);
			assertEquals("protected", ((StringLiteralExp)protectedAreaBlock.getMarker()).getStringSymbol()); //$NON-NLS-1$
			assertEquals(180, protectedAreaBlock.getStartPosition());
			assertEquals(180 + "[protected ('protected')]\n[/protected]".length(), protectedAreaBlock //$NON-NLS-1$
					.getEndPosition());

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
			assertEquals(4, generatedRegions.size());
			assertEquals(("start of user code class" + cpt + "\nend of user code").length(), generatedFile //$NON-NLS-1$ //$NON-NLS-2$
					.getLength());

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("start of user code ".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof ProtectedAreaBlock);
			ProtectedAreaBlock protectedAreaBlock = (ProtectedAreaBlock)element;
			assertTrue(protectedAreaBlock.getMarker() instanceof QueryInvocation);
			assertEquals("queryTest", ((QueryInvocation)protectedAreaBlock.getMarker()).getDefinition() //$NON-NLS-1$
					.getName());
			assertEquals(185, protectedAreaBlock.getStartPosition());
			assertEquals(185 + "[protected (eClass.queryTest())]\n[/protected]".length(), protectedAreaBlock //$NON-NLS-1$
					.getEndPosition());

			generatedText = generatedRegions.get(1);
			assertEquals("start of user code ".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("start of user code class" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("eClass.name", propertyCallExp.toString()); //$NON-NLS-1$
			assertEquals(302, propertyCallExp.getStartPosition());
			assertEquals(302 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals(("start of user code class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("start of user code class" + cpt + "\n").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$ 
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp stringLiteralExp = (StringLiteralExp)element;
			assertEquals("\n", stringLiteralExp.getStringSymbol()); //$NON-NLS-1$
			assertEquals(217, stringLiteralExp.getStartPosition());
			assertEquals(217 + "\n".length(), stringLiteralExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals(("start of user code class" + cpt + "\n").length(), generatedText.getStartOffset()); //$NON-NLS-1$ //$NON-NLS-2$ 
			assertEquals(("start of user code class" + cpt + "\nend of user code").length(), generatedText //$NON-NLS-1$ //$NON-NLS-2$ 
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof ProtectedAreaBlock);
			protectedAreaBlock = (ProtectedAreaBlock)element;
			assertTrue(protectedAreaBlock.getMarker() instanceof QueryInvocation);
			assertEquals("queryTest", ((QueryInvocation)protectedAreaBlock.getMarker()).getDefinition() //$NON-NLS-1$
					.getName());
			assertEquals(185, protectedAreaBlock.getStartPosition());
			assertEquals(185 + "[protected (eClass.queryTest())]\n[/protected]".length(), protectedAreaBlock //$NON-NLS-1$
					.getEndPosition());

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
			assertEquals(4, generatedRegions.size());
			assertEquals(("start of user code class" + cpt + "\nend of user code").length(), generatedFile //$NON-NLS-1$ //$NON-NLS-2$
					.getLength());

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("start of user code ".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof ProtectedAreaBlock);
			ProtectedAreaBlock protectedAreaBlock = (ProtectedAreaBlock)element;
			assertTrue(protectedAreaBlock.getMarker() instanceof TemplateInvocation);
			assertEquals("templateTest", ((TemplateInvocation)protectedAreaBlock.getMarker()).getDefinition() //$NON-NLS-1$
					.getName());
			assertEquals(188, protectedAreaBlock.getStartPosition());
			assertEquals(188 + "[protected (eClass.templateTest())]\n[/protected]".length(), //$NON-NLS-1$
					protectedAreaBlock.getEndPosition());

			generatedText = generatedRegions.get(1);
			assertEquals("start of user code ".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("start of user code class" + cpt).length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("eClass.name", propertyCallExp.toString()); //$NON-NLS-1$
			assertEquals(304, propertyCallExp.getStartPosition());
			assertEquals(304 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals(("start of user code class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("start of user code class" + cpt + "\n").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$ 
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp stringLiteralExp = (StringLiteralExp)element;
			assertEquals("\n", stringLiteralExp.getStringSymbol()); //$NON-NLS-1$
			assertEquals(223, stringLiteralExp.getStartPosition());
			assertEquals(223 + "\n".length(), stringLiteralExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals(("start of user code class" + cpt + "\n").length(), generatedText.getStartOffset()); //$NON-NLS-1$ //$NON-NLS-2$ 
			assertEquals(("start of user code class" + cpt + "\nend of user code").length(), generatedText //$NON-NLS-1$ //$NON-NLS-2$ 
					.getEndOffset());
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof ProtectedAreaBlock);
			protectedAreaBlock = (ProtectedAreaBlock)element;
			assertTrue(protectedAreaBlock.getMarker() instanceof TemplateInvocation);
			assertEquals("templateTest", ((TemplateInvocation)protectedAreaBlock.getMarker()).getDefinition() //$NON-NLS-1$
					.getName());
			assertEquals(188, protectedAreaBlock.getStartPosition());
			assertEquals(188 + "[protected (eClass.templateTest())]\n[/protected]".length(), //$NON-NLS-1$
					protectedAreaBlock.getEndPosition());

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
