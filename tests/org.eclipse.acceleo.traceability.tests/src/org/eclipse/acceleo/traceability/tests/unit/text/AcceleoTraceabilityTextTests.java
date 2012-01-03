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
package org.eclipse.acceleo.traceability.tests.unit.text;

import java.util.List;

import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.tests.unit.AbstractTraceabilityTest;
import org.eclipse.acceleo.traceability.tests.unit.AcceleoTraceabilityListener;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceleoTraceabilityTextTests extends AbstractTraceabilityTest {

	@Test
	public void testTraceabilityTextSimple() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate("data/text/textSimple.mtl", //$NON-NLS-1$
				"main", "data/text/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("textSimple".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("textSimple".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("textSimple", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(176, string.getStartPosition());
			assertEquals(176 + "textSimple".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/text/model.ecore", modelElement //$NON-NLS-1$
					.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTextSimpleWithSpace() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/text/textSimpleWithSpace.mtl", //$NON-NLS-1$
				"main", "data/text/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("textSimple textSimple".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("textSimple textSimple".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals("textSimple textSimple", string.getStringSymbol()); //$NON-NLS-1$
			assertEquals(185, string.getStartPosition());
			assertEquals(185 + "textSimple textSimple".length(), string.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/text/model.ecore", modelElement //$NON-NLS-1$
					.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityTextSimpleMultiline() {
		final String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" //$NON-NLS-1$
				+ "Donec pellentesque pharetra tellus lacinia tincidunt. Nullam mattis feugiat ligula eget dapibus. Etiam blandit vulputate ornare.\n" //$NON-NLS-1$
				+ "Fusce a justo turpis, id suscipit magna. Curabitur nibh dolor, consequat id blandit ut, tristique sed quam. Ut vitae velit nisl, in lacinia velit.\n" //$NON-NLS-1$
				+ "Praesent vel."; //$NON-NLS-1$

		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/text/textSimpleMultiline.mtl", //$NON-NLS-1$
				"main", "data/text/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals(text.length(), generatedFile.getLength());

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals(text.length(), generatedText.getEndOffset());
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp string = (StringLiteralExp)element;
			assertEquals(text, string.getStringSymbol());
			assertEquals(185, string.getStartPosition());
			assertEquals(185 + text.length(), string.getEndPosition());

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/text/model.ecore", modelElement //$NON-NLS-1$
					.eResource().getURI().path());
			cpt++;
		}
	}
}
