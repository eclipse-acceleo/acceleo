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
package org.eclipse.acceleo.traceability.tests.unit.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.tests.unit.AbstractTraceabilityTest;
import org.eclipse.acceleo.traceability.tests.unit.AcceleoTraceabilityListener;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.text.Region;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.junit.Test;

public class AcceleoTraceabilityTemplateTests extends AbstractTraceabilityTest {

	@Test
	public void testTraceabilityTemplateSimple() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateSimple.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("templateSimple".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templateSimple".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("templateSimple", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(275, string.getStartPosition());
			assertEquals(275 + "templateSimple".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateGuard() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateGuard.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(1, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("templateGuard".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templateGuard".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("templateGuard", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(332, string.getStartPosition());
			assertEquals(332 + "templateGuard".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateMultipleParameters() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateMultipleParameters.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("templateMultipleParameters".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templateMultipleParameters".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("templateMultipleParameters", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(313, string.getStartPosition());
			assertEquals(313 + "templateMultipleParameters".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateMultipleTemplates() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateMultipleTemplates.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("templateMultipleTemplates".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templateMultipleTemplates".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("templateMultipleTemplates", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(510, string.getStartPosition());
			assertEquals(510 + "templateMultipleTemplates".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateTabulations() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateTabulations.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals("\ttemplateTabulations".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("\t".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("\t", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(185, string.getStartPosition());
			assertEquals(185 + "\t".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("\t".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("\t".length() + "templateTabulations".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("templateTabulations", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(292, string.getStartPosition());
			assertEquals(292 + "templateTabulations".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateImbricated() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateImbricated.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals("template1template3".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("template1".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("template1", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(290, string.getStartPosition());
			assertEquals(290 + "template1".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("template1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("template1".length() + "template3".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("template3", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(434, string.getStartPosition());
			assertEquals(434 + "template3".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateIndentation() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateIndentation.mtl", //$NON-NLS-1$
				"main", "data/template/model.uml", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(1, generatedFiles.size());

		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedText = generatedFile.getGeneratedRegions();
			List<Region> regions = new ArrayList<Region>();
			for (GeneratedText text : generatedText) {
				Region region = new Region(text.getStartOffset(), text.getEndOffset() - text.getStartOffset());

				boolean collision = false;
				for (Region otherRegion : regions) {
					if (otherRegion.getOffset() <= region.getOffset()
							&& region.getOffset() < (otherRegion.getOffset() + otherRegion.getLength())) {
						collision = true;
					} else if (otherRegion.getOffset() < (region.getOffset() + region.getLength())
							&& (region.getOffset() + region.getLength()) < (otherRegion.getOffset() + otherRegion
									.getLength())) {
						collision = true;
					}
				}
				regions.add(region);
				assertFalse(
						"The region [" + region.getOffset() + "," + (region.getLength() + region.getOffset()) //$NON-NLS-1$//$NON-NLS-2$
								+ "] has entered in collision with another traceability region.", collision); //$NON-NLS-1$
			}
		}
	}

	@Test
	public void testTraceabilityTemplateCollectIf() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollectIf.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(444, propertyCallExp.getStartPosition());
			assertEquals(444 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollectIf2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollectIf2.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(461, propertyCallExp.getStartPosition());
			assertEquals(461 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollectIf3() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollectIf3.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(480, propertyCallExp.getStartPosition());
			assertEquals(480 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollection() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollection.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals("template1template2".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("template1".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("template1", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(299, string.getStartPosition());
			assertEquals(299 + "template1".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("template1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("template1".length() + "template2".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("template2", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(365, string.getStartPosition());
			assertEquals(365 + "template2".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollection2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollection2.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals("template1template2".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("template1".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("template1", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(311, string.getStartPosition());
			assertEquals(311 + "template1".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("template1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("template1".length() + "template2".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("template2", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(377, string.getStartPosition());
			assertEquals(377 + "template2".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollection3() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollection3.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(4, generatedRegions.size());
			assertEquals("class1class2class3class4".length(), generatedFile.getLength()); //$NON-NLS-1$

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
			assertEquals(239, propertyCallExp.getStartPosition());
			assertEquals(239 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class1", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(1);
			assertEquals("class1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1".length() + "class2".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(239, propertyCallExp.getStartPosition());
			assertEquals(239 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class2", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(2);
			assertEquals("class1class2".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1class2".length() + "class3".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(239, propertyCallExp.getStartPosition());
			assertEquals(239 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class3", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(3);
			assertEquals("class1class2class3".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1class2class3".length() + "class4".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(239, propertyCallExp.getStartPosition());
			assertEquals(239 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class4", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollection4() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollection4.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(4, generatedRegions.size());
			assertEquals("class1class2class3class4".length(), generatedFile.getLength()); //$NON-NLS-1$

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
			assertEquals(249, propertyCallExp.getStartPosition());
			assertEquals(249 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("class1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1".length() + "class2".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(249, propertyCallExp.getStartPosition());
			assertEquals(249 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("class1class2".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1class2".length() + "class3".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(249, propertyCallExp.getStartPosition());
			assertEquals(249 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("class1class2class3".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1class2class3".length() + "class4".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(249, propertyCallExp.getStartPosition());
			assertEquals(249 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class4", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollectionCollect() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollectionCollect.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(9, generatedRegions.size());
			assertEquals("aaabbbccc".length(), generatedFile.getLength()); //$NON-NLS-1$

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
			assertEquals(201, string.getStartPosition());
			assertEquals(201 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("a".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(201, string.getStartPosition());
			assertEquals(201 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("aa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(201, string.getStartPosition());
			assertEquals(201 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("aaa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaab".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(206, string.getStartPosition());
			assertEquals(206 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("aaab".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(206, string.getStartPosition());
			assertEquals(206 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("aaabb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(206, string.getStartPosition());
			assertEquals(206 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("aaabbb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(211, string.getStartPosition());
			assertEquals(211 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(7);
			assertEquals("aaabbbc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbcc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(211, string.getStartPosition());
			assertEquals(211 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(8);
			assertEquals("aaabbbcc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbccc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(211, string.getStartPosition());
			assertEquals(211 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollectionCollect2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollectionCollect2.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(9, generatedRegions.size());
			assertEquals("aaabbbccc".length(), generatedFile.getLength()); //$NON-NLS-1$

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
			assertEquals(202, string.getStartPosition());
			assertEquals(202 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("a".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(202, string.getStartPosition());
			assertEquals(202 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("aa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(202, string.getStartPosition());
			assertEquals(202 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("aaa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaab".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(207, string.getStartPosition());
			assertEquals(207 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("aaab".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(207, string.getStartPosition());
			assertEquals(207 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("aaabb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(207, string.getStartPosition());
			assertEquals(207 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("aaabbb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(212, string.getStartPosition());
			assertEquals(212 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(7);
			assertEquals("aaabbbc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbcc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(212, string.getStartPosition());
			assertEquals(212 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(8);
			assertEquals("aaabbbcc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbccc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(212, string.getStartPosition());
			assertEquals(212 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollectionCollect3() {
		// Parse the file
		Resource moduleResource = parse("data/template/templateCollectionCollect3.mtl"); //$NON-NLS-1$
		EObject rootTemplate = moduleResource.getContents().get(0);
		ResourceSet moduleResourceSet = new ResourceSetImpl();
		moduleResourceSet.getResources().add(moduleResource);
		if (rootTemplate instanceof Module) {
			module = (Module)rootTemplate;
		} else {
			Assert.fail("Couldn't load the input template."); //$NON-NLS-1$
		}

		// Activate the traceability
		AcceleoTraceabilityListener traceabilityListener = new AcceleoTraceabilityListener(true);
		AcceleoPreferences.switchTraceability(true);
		AcceleoService.addStaticListener(traceabilityListener);

		// Load the model
		try {
			final URI inputModelURI = URI.createPlatformPluginURI('/' + AbstractTraceabilityTest.PLUGIN_ID
					+ '/' + "data/template/NonRegressionModel.uml", true); //$NON-NLS-1$
			inputModel = ModelUtils.load(inputModelURI, resourceSet);
		} catch (IOException e) {
			fail("Error loading the input model."); //$NON-NLS-1$
		}

		// Launch the generation
		Map<String, String> map = new AcceleoService(new PreviewStrategy()).doGenerate(module,
				"main", inputModel, null, //$NON-NLS-1$
				new BasicMonitor());

		// Desactivate the traceability
		AcceleoPreferences.switchTraceability(false);
		AcceleoService.removeStaticListener(traceabilityListener);

		String generatedFile = map.get("result.txt"); //$NON-NLS-1$
		assertTrue(generatedFile.length() > 0);

	}

	@Test
	public void testTraceabilityTemplateCollectionSelect() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollectionSelect.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			assertEquals(200, string.getStartPosition());
			assertEquals(200 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateCollectionReject() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateCollectionReject.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals("bc".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("b".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(205, string.getStartPosition());
			assertEquals(205 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("b".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(210, string.getStartPosition());
			assertEquals(210 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostSubstring() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostSubstring.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("mp".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("mp".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(" templatePostTrim ", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(306, string.getStartPosition());
			assertEquals(306 + " templatePostTrim ".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("templatePostTrim".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templatePostTrim".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(" templatePostTrim ", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(292, string.getStartPosition());
			assertEquals(292 + " templatePostTrim ".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim2.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(391, propertyCallExp.getStartPosition());
			assertEquals(391 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim3() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim3.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(423, propertyCallExp.getStartPosition());
			assertEquals(423 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim4() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim4.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(544, propertyCallExp.getStartPosition());
			assertEquals(544 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim5() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim5.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(541, propertyCallExp.getStartPosition());
			assertEquals(541 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim6() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim6.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals(("class" + cpt + "coucou").length(), generatedFile.getLength()); //$NON-NLS-1$ //$NON-NLS-2$

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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(521, propertyCallExp.getStartPosition());
			assertEquals(521 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + "coucou").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("coucou\n\n\n\n", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(360, string.getStartPosition());
			assertEquals(360 + "coucou\n\n\n\n".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim7() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim7.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals(("class" + cpt).length() * 2, generatedFile.getLength()); //$NON-NLS-1$

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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(547, propertyCallExp.getStartPosition());
			assertEquals(547 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt).length() * 2, generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(547, propertyCallExp.getStartPosition());
			assertEquals(547 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim8() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim8.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals(("class" + cpt).length() * 2 + "coucou".length(), generatedFile.getLength()); //$NON-NLS-1$ //$NON-NLS-2$

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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(553, propertyCallExp.getStartPosition());
			assertEquals(553 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals(("class" + cpt).length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals(("class" + cpt + "coucou").length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("coucou", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(360, string.getStartPosition());
			assertEquals(360 + "coucou".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals(("class" + cpt + "coucou").length(), generatedText.getStartOffset()); //$NON-NLS-1$//$NON-NLS-2$
			assertEquals(("class" + cpt).length() * 2 + "coucou".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(553, propertyCallExp.getStartPosition());
			assertEquals(553 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim9() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim9.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(318, propertyCallExp.getStartPosition());
			assertEquals(318 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplatePostTrim10() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templatePostTrim10.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("templatePostTrim".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templatePostTrim".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(" templatePostTrim ", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(305, string.getStartPosition());
			assertEquals(305 + " templatePostTrim ".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateVariableInitialization() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateVariableInitialization.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals("templateVariableInitnewName".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templateVariableInit".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("templateVariableInit", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(335, string.getStartPosition());
			assertEquals(335 + "templateVariableInit".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("templateVariableInit".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("templateVariableInitnewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(323, string.getStartPosition());
			assertEquals(323 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateVariableInitializationOperation() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateVariableInitializationOperation.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(4, generatedRegions.size());
			assertEquals("templateVariableInitnewNameclaYOP".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templateVariableInit".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("templateVariableInit", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(416, string.getStartPosition());
			assertEquals(416 + "templateVariableInit".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("templateVariableInit".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("templateVariableInitnewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(335, string.getStartPosition());
			assertEquals(335 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("templateVariableInitnewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("templateVariableInitnewNamecla".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			PropertyCallExp propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(369, propertyCallExp.getStartPosition());
			assertEquals(369 + "eClass.name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("templateVariableInitnewNamecla".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("templateVariableInitnewNameclaYOP".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("YOP", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(404, string.getStartPosition());
			assertEquals(404 + "'YOP'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateVariableInitializationTemplate() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateVariableInitializationTemplate.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("templateVariableInitnewNameclassName".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templateVariableInit".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("templateVariableInit", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(392, string.getStartPosition());
			assertEquals(392 + "templateVariableInit".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("templateVariableInit".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("templateVariableInitnewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(334, string.getStartPosition());
			assertEquals(334 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("templateVariableInitnewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("templateVariableInitnewNameclassName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("className", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(491, string.getStartPosition());
			assertEquals(491 + "className".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateVariableInitializationQuery() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateVariableInitializationQuery.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("templateVariableInitnewNameclassName".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("templateVariableInit".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("templateVariableInit", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(389, string.getStartPosition());
			assertEquals(389 + "templateVariableInit".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("templateVariableInit".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("templateVariableInitnewName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("newName", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(331, string.getStartPosition());
			assertEquals(331 + "'newName'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("templateVariableInitnewName".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("templateVariableInitnewNameclassName".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("className", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(496, string.getStartPosition());
			assertEquals(496 + "'className'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateVariableExp() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateVariableExp.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("aaa".length(), generatedFile.getLength()); //$NON-NLS-1$

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
			StringLiteralExp str = (StringLiteralExp)element;
			assertEquals("a", str.getStringSymbol()); //$NON-NLS-1$
			assertEquals(201, str.getStartPosition());
			assertEquals(201 + "'a'".length(), str.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("a".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			str = (StringLiteralExp)element;
			assertEquals("a", str.getStringSymbol()); //$NON-NLS-1$
			assertEquals(201, str.getStartPosition());
			assertEquals(201 + "'a'".length(), str.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("aa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			str = (StringLiteralExp)element;
			assertEquals("a", str.getStringSymbol()); //$NON-NLS-1$
			assertEquals(201, str.getStartPosition());
			assertEquals(201 + "'a'".length(), str.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTemplateComplexUseCase() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/template/templateComplexUseCase.mtl", //$NON-NLS-1$
				"main", "data/template/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(6, generatedRegions.size());
			assertEquals("bidulechosegnagnachosegnagnamachin".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("bidule".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("bidule", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(206, string.getStartPosition());
			assertEquals(206 + "'bidule'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("bidule".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechose".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("chose", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(224, string.getStartPosition());
			assertEquals(224 + "'chose'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("bidulechose".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechosegnagna".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("gnagna", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(280, string.getStartPosition());
			assertEquals(280 + "'gnagna'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("bidulechosegnagna".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechosegnagnachose".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("chose", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(240, string.getStartPosition());
			assertEquals(240 + "'chose'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("bidulechosegnagnachose".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechosegnagnachosegnagna".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("gnagna", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(280, string.getStartPosition());
			assertEquals(280 + "'gnagna'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("bidulechosegnagnachosegnagna".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechosegnagnachosegnagnamachin".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("machin", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(257, string.getStartPosition());
			assertEquals(257 + "'machin'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/template/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}
}
