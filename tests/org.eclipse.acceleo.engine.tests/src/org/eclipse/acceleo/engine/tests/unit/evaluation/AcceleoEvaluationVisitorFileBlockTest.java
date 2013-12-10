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
package org.eclipse.acceleo.engine.tests.unit.evaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintStream;
import java.util.Map;

import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

/**
 * This will test the behavior of file blocks evaluation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationVisitorFileBlockTest extends AbstractAcceleoEvaluationVisitorTest {
	/** Constant output of the file block tested here. */
	private static final String OUTPUT = "constantOutput"; //$NON-NLS-1$

	/** System line separator. */
	private static final String LINE_SEPARATOR = System.getProperty("line.separator"); //$NON-NLS-1$

	/**
	 * Tests that the visitor properly appends or overwrites according to the file block "append mode".
	 */
	@Test
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
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
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
	@Test
	public void testFileBlockCollectionURL() {
		final FileBlock mtlFileBlock = getDummyFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression("eAllStructuralFeatures", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		// current URL a collection, no file can be generated as such
		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
		assertTrue("A preview has been generated with incorrect file urls.", getPreview().isEmpty()); //$NON-NLS-1$
	}

	/**
	 * Tests the files containing "@generated" JMerge are properly merged.
	 */
	@Test
	public void testFileBlockJMergeContent() {
		final FileBlock mtlFileBlock = getDummyFileBlock();
		final String fileName = "validFile.java"; //$NON-NLS-1$
		mtlFileBlock.setFileUrl(createOCLExpression('\'' + fileName + '\''));
		mtlFileBlock.getBody().clear();
		final String packageDeclaration = "package test;"; //$NON-NLS-1$
		final String classDeclaration = "class Class {"; //$NON-NLS-1$
		String generatedTag = "/** @generated */"; //$NON-NLS-1$
		String methodDeclaration = "public void test() { } }"; //$NON-NLS-1$
		mtlFileBlock.getBody().add(createOCLExpression('\'' + packageDeclaration + '\''));
		mtlFileBlock.getBody().add(createOCLLineSeparator());
		mtlFileBlock.getBody().add(createOCLExpression('\'' + classDeclaration + '\''));
		mtlFileBlock.getBody().add(createOCLLineSeparator());
		mtlFileBlock.getBody().add(createOCLExpression('\'' + generatedTag + '\''));
		mtlFileBlock.getBody().add(createOCLLineSeparator());
		mtlFileBlock.getBody().add(createOCLExpression('\'' + methodDeclaration + '\''));

		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + fileName, entry.getKey());
		assertEquals("File hasn't been created as expected.", packageDeclaration + LINE_SEPARATOR //$NON-NLS-1$
				+ classDeclaration + LINE_SEPARATOR + generatedTag + LINE_SEPARATOR + methodDeclaration,
				entry.getValue().toString());

		methodDeclaration = methodDeclaration.replace("test()", "changedMethodName()"); //$NON-NLS-1$ //$NON-NLS-2$
		mtlFileBlock.getBody().remove(6);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + methodDeclaration + '\''));
		String expected = packageDeclaration + LINE_SEPARATOR + classDeclaration + LINE_SEPARATOR
				+ generatedTag + LINE_SEPARATOR + methodDeclaration;
		/*
		 * We've changed the method name but didn't alter the "@generated" tag. We then expect the generated
		 * file to be altered as well.
		 */
		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + fileName, entry.getKey());
		assertEquals("File hasn't been modified as expected.", expected, entry.getValue().toString()); //$NON-NLS-1$

		methodDeclaration = methodDeclaration.replace("changedMethodName()", "notGenerated()"); //$NON-NLS-1$ //$NON-NLS-2$
		generatedTag = generatedTag.replace("@generated", "@generated not"); //$NON-NLS-1$ //$NON-NLS-2$
		mtlFileBlock.getBody().remove(4);
		mtlFileBlock.getBody().add(4, createOCLExpression('\'' + generatedTag + '\''));
		mtlFileBlock.getBody().remove(6);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + methodDeclaration + '\''));
		/*
		 * Both the method name and "@generated" tag have changed. The generated file shouldn't be modified
		 * when generating again.
		 */
		expected = packageDeclaration + LINE_SEPARATOR + classDeclaration + LINE_SEPARATOR + generatedTag
				+ LINE_SEPARATOR + methodDeclaration;

		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + fileName, entry.getKey());
		assertEquals("File shouldn't have been modified.", expected, //$NON-NLS-1$ 
				entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a file block with a null URL.
	 */
	@Test
	public void testFileBlockNullURL() {
		final FileBlock mtlFileBlock = getDummyFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		// Current URL of the file is null
		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
		assertTrue("A preview has been generated with null file urls.", getPreview().isEmpty()); //$NON-NLS-1$
	}

	/**
	 * Tests that the we can effectively create file blocks generating text on the standard output stream.
	 */
	@Test
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
	@Test
	public void testFileBlockUndefinedURL() {
		final FileBlock mtlFileBlock = getDummyFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		// current URL is undefined (through an evaluation NPE)
		evaluationVisitor.visitExpression(getParentTemplate(mtlFileBlock));
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
