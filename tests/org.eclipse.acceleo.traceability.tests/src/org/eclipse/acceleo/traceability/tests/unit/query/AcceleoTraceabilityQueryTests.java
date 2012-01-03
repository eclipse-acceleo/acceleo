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
package org.eclipse.acceleo.traceability.tests.unit.query;

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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.junit.Test;

public class AcceleoTraceabilityQueryTests extends AbstractTraceabilityTest {

	@Test
	public void testTraceabilityQuerySimple() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/querySimple.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("querySimple".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("querySimple".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("querySimple", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(274, string.getStartPosition());
			assertEquals(274 + "'querySimple'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryCache() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate("data/query/queryCache.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(3, generatedRegions.size());
			assertEquals("queryCachequeryCachequeryCache".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("queryCache".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("queryCache", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(315, string.getStartPosition());
			assertEquals(315 + "'queryCache'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("queryCache".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("queryCachequeryCache".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("queryCache", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(315, string.getStartPosition());
			assertEquals(315 + "'queryCache'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("queryCachequeryCache".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("queryCachequeryCachequeryCache".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("queryCache", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(315, string.getStartPosition());
			assertEquals(315 + "'queryCache'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryMultipleParameters() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryMultipleParameters.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("queryMultipleParameters".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("queryMultipleParameters".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("queryMultipleParameters", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(336, string.getStartPosition());
			assertEquals(336 + "'queryMultipleParameters'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryMultipleQueries() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryMultipleQueries.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("queryMultipleQueries".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("queryMultipleQueries".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("queryMultipleQueries", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(477, string.getStartPosition());
			assertEquals(477 + "'queryMultipleQueries'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryImbricated() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryImbricated.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals("query1query3".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("query1".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("query1", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(286, string.getStartPosition());
			assertEquals(286 + "'query1'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("query1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("query1".length() + "query3".length(), generatedText.getEndOffset()); //$NON-NLS-1$ //$NON-NLS-2$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("query3", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(415, string.getStartPosition());
			assertEquals(415 + "'query3'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryCollection() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryCollection.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals("query1query2".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("query1".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("query1", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(295, string.getStartPosition());
			assertEquals(295 + "'query1'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("query1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("query1query2".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("query2", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(356, string.getStartPosition());
			assertEquals(356 + "'query2'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryCollection2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryCollection2.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(2, generatedRegions.size());
			assertEquals("query1query2".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("query1".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("query1", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(304, string.getStartPosition());
			assertEquals(304 + "'query1'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("query1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("query1query2".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("query2", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(365, string.getStartPosition());
			assertEquals(365 + "'query2'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryCollection3() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryCollection3.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			assertEquals(243, propertyCallExp.getStartPosition());
			assertEquals(243 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class1", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(1);
			assertEquals("class1".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1class2".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(243, propertyCallExp.getStartPosition());
			assertEquals(243 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class2", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(2);
			assertEquals("class1class2".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1class2class3".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(243, propertyCallExp.getStartPosition());
			assertEquals(243 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class3", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			generatedText = generatedRegions.get(3);
			assertEquals("class1class2class3".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("class1class2class3class4".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof PropertyCallExp);
			propertyCallExp = (PropertyCallExp)element;
			assertEquals("name", propertyCallExp.getReferredProperty().getName()); //$NON-NLS-1$
			assertEquals(243, propertyCallExp.getStartPosition());
			assertEquals(243 + "name".length(), propertyCallExp.getEndPosition()); //$NON-NLS-1$

			sourceElement = generatedText.getSourceElement();
			modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class4", ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());

			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryCollectionCollect() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryCollectionCollect.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			assertEquals(198, string.getStartPosition());
			assertEquals(198 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("a".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(198, string.getStartPosition());
			assertEquals(198 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("aa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(198, string.getStartPosition());
			assertEquals(198 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("aaa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaab".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(203, string.getStartPosition());
			assertEquals(203 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("aaab".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(203, string.getStartPosition());
			assertEquals(203 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("aaabb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(203, string.getStartPosition());
			assertEquals(203 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("aaabbb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(208, string.getStartPosition());
			assertEquals(208 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(7);
			assertEquals("aaabbbc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbcc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(208, string.getStartPosition());
			assertEquals(208 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(8);
			assertEquals("aaabbbcc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbccc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(208, string.getStartPosition());
			assertEquals(208 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryCollectionCollect2() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryCollectionCollect2.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			assertEquals(199, string.getStartPosition());
			assertEquals(199 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("a".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(199, string.getStartPosition());
			assertEquals(199 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("aa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(199, string.getStartPosition());
			assertEquals(199 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("aaa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaab".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(204, string.getStartPosition());
			assertEquals(204 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("aaab".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(204, string.getStartPosition());
			assertEquals(204 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("aaabb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbb".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("b", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(204, string.getStartPosition());
			assertEquals(204 + "'b'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(6);
			assertEquals("aaabbb".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(209, string.getStartPosition());
			assertEquals(209 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(7);
			assertEquals("aaabbbc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbcc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(209, string.getStartPosition());
			assertEquals(209 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(8);
			assertEquals("aaabbbcc".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaabbbccc".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("c", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(209, string.getStartPosition());
			assertEquals(209 + "'c'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryCollectionSelect() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryCollectionSelect.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			assertEquals(197, string.getStartPosition());
			assertEquals(197 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryVariableExp() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryVariableExp.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(195, string.getStartPosition());
			assertEquals(195 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("a".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(195, string.getStartPosition());
			assertEquals(195 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("aa".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("aaa".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("a", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(195, string.getStartPosition());
			assertEquals(195 + "'a'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityQueryComplexUseCase() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/query/queryComplexUseCase.mtl", //$NON-NLS-1$
				"main", "data/query/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
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
			assertEquals(203, string.getStartPosition());
			assertEquals(203 + "'bidule'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(1);
			assertEquals("bidule".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechose".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("chose", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(221, string.getStartPosition());
			assertEquals(221 + "'chose'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(4);
			assertEquals("bidulechose".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechosegnagna".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("gnagna", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(277, string.getStartPosition());
			assertEquals(277 + "'gnagna'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(2);
			assertEquals("bidulechosegnagna".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechosegnagnachose".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("chose", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(221, string.getStartPosition());
			assertEquals(221 + "'chose'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(5);
			assertEquals("bidulechosegnagnachose".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechosegnagnachosegnagna".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("gnagna", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(277, string.getStartPosition());
			assertEquals(277 + "'gnagna'".length(), string.getEndPosition()); //$NON-NLS-1$

			generatedText = generatedRegions.get(3);
			assertEquals("bidulechosegnagnachosegnagna".length(), generatedText.getStartOffset()); //$NON-NLS-1$
			assertEquals("bidulechosegnagnachosegnagnamachin".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			moduleElement = generatedText.getModuleElement();
			element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			string = (StringLiteralExp)element;
			assertEquals("machin", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(254, string.getStartPosition());
			assertEquals(254 + "'machin'".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/query/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}
}
