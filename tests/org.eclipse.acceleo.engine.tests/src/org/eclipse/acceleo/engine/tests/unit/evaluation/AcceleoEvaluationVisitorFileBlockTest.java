/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.evaluation;

import java.io.File;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Map;

import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This will test the behavior of file blocks evaluation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationVisitorFileBlockTest extends AbstractAcceleoEvaluationVisitorTest {
	/** Constant output of the file block tested here. */
	private static final String OUTPUT = "constantOutput"; //$NON-NLS-1$

	/**
	 * Tests that the visitor properly appends or overwrites according to the file block "append mode".
	 */
	public void testFileBlockAppendMode() {
		final FileBlock mtlFileBlock = getDummyFileBlock();
		final String fileName = "validFile.url"; //$NON-NLS-1$
		mtlFileBlock.setFileUrl(createOCLExpression('\'' + fileName + '\''));
		mtlFileBlock.setOpenMode(OpenModeKind.APPEND);

		/*
		 * Appending with no file at first : we expect the preview to contain a single file with the result of
		 * one evaluation
		 */
		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, Writer> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + fileName, entry.getKey());
		assertEquals("File hasn't been created as expected.", OUTPUT, entry.getValue().toString()); //$NON-NLS-1$ 

		/*
		 * Appending a second time : we expect the preview to contain a single file with the result of two
		 * evaluation
		 */
		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + fileName, entry.getKey());
		assertEquals("File hasn't been appended to as expected.", OUTPUT //$NON-NLS-1$ 
				+ System.getProperty("line.separator") + OUTPUT, entry.getValue().toString()); //$NON-NLS-1$ 

		// Now set the append mode to overwrite and check that the file is created anew.
		mtlFileBlock.setOpenMode(OpenModeKind.OVER_WRITE);
		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + fileName, entry.getKey());
		assertEquals("File hasn't been appended to as expected.", OUTPUT, entry.getValue() //$NON-NLS-1$ 
				.toString());
	}

	/**
	 * Tests the evaluation of a file block with a URL evaluating to a Collection.
	 */
	public void testFileBlockCollectionURL() {
		final FileBlock mtlFileBlock = getDummyFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression("eAllStructuralFeatures", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		// current URL is undefined (through an evaluation NPE)
		try {
			evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
			fail("Evaluation visitor didn't throw the expected evaluation exception for a file block" + //$NON-NLS-1$
					" with a collection type URL."); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(mtlFileBlock.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(mtlFileBlock)).getName()));
		}
		assertTrue("A preview has been generated with incorrect file urls.", getPreview().isEmpty()); //$NON-NLS-1$
	}

	/**
	 * Tests the evaluation of a file block with a null URL.
	 */
	public void testFileBlockNullURL() {
		final FileBlock mtlFileBlock = getDummyFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		// Current URL of the file is null
		try {
			evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
			fail("Evaluation visitor didn't throw the expected evaluation exception for a file block with a null URL."); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(mtlFileBlock.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(mtlFileBlock)).getName()));
		}
		assertTrue("A preview has been generated with null file urls.", getPreview().isEmpty()); //$NON-NLS-1$
	}

	/**
	 * Tests that the we can effectively create file blocks generating text on the standard output stream.
	 */
	public void testFileBlockOutputStream() {
		final FileBlock mtlFileBlock = getDummyFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression("'stdout'")); //$NON-NLS-1$

		final PrintStream oldOut = System.out;
		final StringBuffer generatedText = new StringBuffer();

		// redirect standard output to our buffer
		System.setOut(new PrintStream(System.out) {
			@Override
			public void write(int b) {
				generatedText.append((char)b);
			}
		});
		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
		System.setOut(oldOut);

		assertEquals("Generation hasn't been redirected to the standard output.", OUTPUT, generatedText //$NON-NLS-1$
				.toString());
		assertTrue("No preview should have been generated.", getPreview().isEmpty()); //$NON-NLS-1$
	}

	/**
	 * Tests the evaluation of a file block with an incorrect URL.
	 */
	public void testFileBlockUndefinedURL() {
		final FileBlock mtlFileBlock = getDummyFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		// current URL is undefined (through an evaluation NPE)
		try {
			evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
			fail("Evaluation visitor didn't throw the expected evaluation exception for an undefined file block URL."); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(mtlFileBlock.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(mtlFileBlock)).getName()));
		}
		assertTrue("A preview has been generated with undefined file urls.", getPreview().isEmpty()); //$NON-NLS-1$
	}

	/**
	 * Creates a dummy file block containing a single string literal as its body.
	 * 
	 * @return Dummy file block.
	 */
	private FileBlock getDummyFileBlock() {
		final FileBlock mtlFileBlock = MtlFactory.eINSTANCE.createFileBlock();
		mtlFileBlock.setOpenMode(OpenModeKind.OVER_WRITE);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));
		getDummyTemplate().getBody().add(mtlFileBlock);
		return mtlFileBlock;
	}
}
