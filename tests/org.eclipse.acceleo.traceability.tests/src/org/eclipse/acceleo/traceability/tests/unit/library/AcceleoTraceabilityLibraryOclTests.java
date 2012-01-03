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
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.utilities.ASTNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AcceleoTraceabilityLibraryOclTests extends AbstractTraceabilityTest {

	@Test
	public void testTraceabilityLibraryOclIfPropertyExp() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/ocl/oclIfPropertyExp.mtl", //$NON-NLS-1$
				"main", "data/library/ocl/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("dddd".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("dddd".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			assertTrue(element instanceof StringLiteralExp);
			StringLiteralExp stringLiteralExp = (StringLiteralExp)element;
			assertEquals("dddd", stringLiteralExp.getStringSymbol()); //$NON-NLS-1$
			assertEquals(240, stringLiteralExp.getStartPosition());
			assertEquals(240 + "'dddd'".length(), stringLiteralExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/ocl/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

	@Test
	public void testTraceabilityLibraryOclIfStringLiteral() {
		AcceleoTraceabilityListener traceabilityListener = this.parseAndGenerate(
				"data/library/ocl/oclIfStringLiteral.mtl", //$NON-NLS-1$
				"main", "data/library/ocl/model.ecore", true); //$NON-NLS-1$ //$NON-NLS-2$
		List<GeneratedFile> generatedFiles = traceabilityListener.getGeneratedFiles();
		assertEquals(4, generatedFiles.size());

		int cpt = 1;
		for (GeneratedFile generatedFile : generatedFiles) {
			List<GeneratedText> generatedRegions = generatedFile.getGeneratedRegions();
			assertEquals(1, generatedRegions.size());
			assertEquals("dddd".length(), generatedFile.getLength()); //$NON-NLS-1$

			List<InputElement> sourceElements = generatedFile.getSourceElements();
			assertEquals(2, sourceElements.size()); // the class and its name
			assertEquals("class" + cpt + ", feature='name'", sourceElements.get(0).toString()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("class" + cpt, sourceElements.get(1).toString()); //$NON-NLS-1$
			assertEquals("class" + cpt + ".txt", generatedFile.getPath()); //$NON-NLS-1$ //$NON-NLS-2$

			GeneratedText generatedText = generatedRegions.get(0);
			assertEquals(0, generatedText.getStartOffset());
			assertEquals("dddd".length(), generatedText.getEndOffset()); //$NON-NLS-1$
			ModuleElement moduleElement = generatedText.getModuleElement();
			EObject element = moduleElement.getModuleElement();
			assertTrue(element instanceof ASTNode);
			StringLiteralExp stringLiteralExp = (StringLiteralExp)element;
			assertEquals("dddd", stringLiteralExp.getStringSymbol()); //$NON-NLS-1$
			assertEquals(234, stringLiteralExp.getStartPosition());
			assertEquals(234 + "'dddd'".length(), stringLiteralExp.getEndPosition()); //$NON-NLS-1$

			InputElement sourceElement = generatedText.getSourceElement();
			EObject modelElement = sourceElement.getModelElement();
			assertTrue(modelElement instanceof EClass);
			assertEquals("class" + cpt, ((EClass)modelElement).getName()); //$NON-NLS-1$
			assertEquals("/plugin/org.eclipse.acceleo.traceability.tests/data/library/ocl/model.ecore", //$NON-NLS-1$
					modelElement.eResource().getURI().path());
			cpt++;
		}
	}

}
