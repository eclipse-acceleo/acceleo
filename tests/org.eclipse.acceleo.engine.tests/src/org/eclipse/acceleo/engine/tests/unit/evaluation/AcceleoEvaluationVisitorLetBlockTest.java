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

import java.io.File;
import java.util.Map;

import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.Variable;
import org.junit.Test;

/**
 * This will test the behavior of let blocks evaluation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationVisitorLetBlockTest extends AbstractAcceleoEvaluationVisitorTest {
	/** Constant output of the "else" statement for the let block tested here. */
	private static final String ELSE = "elseOutput"; //$NON-NLS-1$

	/** Name of the variable that is to be used as the "else let" variable. */
	private static final String ELSE_LET_VAR_NAME = "elseLetVar"; //$NON-NLS-1$

	/** Constant output of the "else let" statement for the let block tested here. */
	private static final String ELSELET = "elseLetOutput"; //$NON-NLS-1$

	/** This is the file name that will be used for the dummy file block. */
	private static final String FILE_NAME = "validFile.url"; //$NON-NLS-1$

	/** Name of the variable that is to be used as the let variable. */
	private static final String LET_VAR_NAME = "letVar"; //$NON-NLS-1$

	/** Constant output of the file block tested here. */
	private static final String OUTPUT = "constantOutput"; //$NON-NLS-1$

	/** Constant output of the let block tested here. */
	private static final String LET_OUTPUT = "letBodyOutput"; //$NON-NLS-1$

	/**
	 * Tests the evaluation of a let block with its condition being a valid instanceof.
	 */
	@Test
	public void testLetBlockBody() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEObject());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eSuperTypes->last()", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		final LetBlock elseLet = MtlFactory.eINSTANCE.createLetBlock();
		elseLet.getBody().add(createOCLExpression('\'' + ELSELET + '\''));
		final Variable elseLetVar = EcoreFactory.eINSTANCE.createVariable();
		elseLetVar.setName(ELSE_LET_VAR_NAME);
		elseLetVar.setType(getTestPackage().getEClassifiers().get(1));
		elseLetVar.setInitExpression(createOCLExpression("eSuperTypes->last()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		elseLet.setLetVariable(elseLetVar);
		letBlock.getElseLet().add(elseLet);

		// The evaluation should go through the body of the let
		evaluationVisitor.visitExpression(getParentTemplate(letBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", OUTPUT + LET_OUTPUT + OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being an invalid instanceof. An else let has
	 * been set for the let, its condition also being an invalid instanceof.
	 */
	@Test
	public void testLetBlockElse() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEAttribute());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eSuperTypes->last()", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		final LetBlock elseLet = MtlFactory.eINSTANCE.createLetBlock();
		elseLet.getBody().add(createOCLExpression('\'' + ELSELET + '\''));
		final Variable elseLetVar = EcoreFactory.eINSTANCE.createVariable();
		elseLetVar.setName(ELSE_LET_VAR_NAME);
		elseLetVar.setType(getTestPackage().getEClassifiers().get(1));
		elseLetVar.setInitExpression(createOCLExpression("eSuperTypes->last()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		elseLet.setLetVariable(elseLetVar);
		letBlock.getElseLet().add(elseLet);

		// The evaluation should go through the else of the let
		evaluationVisitor.visitExpression(getParentTemplate(letBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", OUTPUT + ELSE + OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being an invalid instanceof. An else let hasn't
	 * been set for the let.
	 */
	@Test
	public void testLetBlockElseNoElseLet() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEAttribute());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eSuperTypes->last()", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		// The evaluation should go through the else of the let
		evaluationVisitor.visitExpression(getParentTemplate(letBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", OUTPUT + ELSE + OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being an invalid instanceof. An else let has
	 * been set for the let, its condition being a valid instanceof.
	 */
	@Test
	public void testLetBlockElseLet() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEAttribute());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eSuperTypes->last()", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		final LetBlock elseLet = MtlFactory.eINSTANCE.createLetBlock();
		elseLet.getBody().add(createOCLExpression('\'' + ELSELET + '\''));
		final Variable elseLetVar = EcoreFactory.eINSTANCE.createVariable();
		elseLetVar.setName(ELSE_LET_VAR_NAME);
		elseLetVar.setType(EcorePackage.eINSTANCE.getEObject());
		elseLetVar.setInitExpression(createOCLExpression("eSuperTypes->last()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		elseLet.setLetVariable(elseLetVar);
		letBlock.getElseLet().add(elseLet);

		// The evaluation should go through the else of the let
		evaluationVisitor.visitExpression(getParentTemplate(letBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", OUTPUT + ELSELET + OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being an invalid instanceof. An else let has
	 * been set for the let, its condition being null.
	 */
	@Test
	public void testLetBlockElseLetNullCondition() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEAttribute());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eSuperTypes->last()", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		final LetBlock elseLet = MtlFactory.eINSTANCE.createLetBlock();
		elseLet.getBody().add(createOCLExpression('\'' + ELSELET + '\''));
		final Variable elseLetVar = EcoreFactory.eINSTANCE.createVariable();
		elseLetVar.setName(ELSE_LET_VAR_NAME);
		elseLetVar.setType(EcorePackage.eINSTANCE.getEObject());
		elseLetVar.setInitExpression(createOCLExpression(
				"eSuperTypes->select(oclIsKindOf(EClass))->first()", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$
		elseLet.setLetVariable(elseLetVar);
		letBlock.getElseLet().add(elseLet);

		evaluationVisitor.visitExpression(getParentTemplate(letBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", OUTPUT + ELSELET + OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being an invalid instanceof. An else let has
	 * been set for the let, its condition being undefined.
	 */
	@Test
	public void testLetBlockElseLetUndefinedCondition() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEAttribute());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eSuperTypes->last()", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		final LetBlock elseLet = MtlFactory.eINSTANCE.createLetBlock();
		elseLet.getBody().add(createOCLExpression('\'' + ELSELET + '\''));
		final Variable elseLetVar = EcoreFactory.eINSTANCE.createVariable();
		elseLetVar.setName(ELSE_LET_VAR_NAME);
		elseLetVar.setType(EcorePackage.eINSTANCE.getEObject());
		elseLetVar.setInitExpression(createOCLExpression(
				"eSuperTypes->select(oclIsKindOf(EReference))->first().name", EcorePackage.eINSTANCE //$NON-NLS-1$
						.getEClass()));
		elseLet.setLetVariable(elseLetVar);
		letBlock.getElseLet().add(elseLet);

		evaluationVisitor.visitExpression(getParentTemplate(letBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", OUTPUT + OUTPUT, entry.getValue() //$NON-NLS-1$ 
				.toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being null.
	 */
	@Test
	public void testLetBlockNullCondition() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEAttribute());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(letBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", OUTPUT + ELSE + OUTPUT, entry //$NON-NLS-1$ 
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being undefined.
	 */
	@Test
	public void testLetBlockUndefinedCondition() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEAttribute());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eSuperTypes->select(oclIsKindOf(EReference))->first().name", //$NON-NLS-1$
						EcorePackage.eINSTANCE.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(letBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", OUTPUT + OUTPUT, entry.getValue() //$NON-NLS-1$ 
				.toString());
	}

	/**
	 * Creates a dummy let block containing a single string literal as its body.
	 * 
	 * @return Dummy let block.
	 */
	private LetBlock getDummyLetBlock() {
		final FileBlock mtlFileBlock = MtlFactory.eINSTANCE.createFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression('\'' + FILE_NAME + '\''));
		mtlFileBlock.setOpenMode(OpenModeKind.OVER_WRITE);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));
		getDummyTemplate().getBody().add(mtlFileBlock);

		final LetBlock dummy = MtlFactory.eINSTANCE.createLetBlock();
		dummy.getBody().add(createOCLExpression('\'' + LET_OUTPUT + '\''));
		final Variable letVar = EcoreFactory.eINSTANCE.createVariable();
		letVar.setName(LET_VAR_NAME);
		dummy.setLetVariable(letVar);
		final Block elseBlock = MtlFactory.eINSTANCE.createBlock();
		elseBlock.getBody().add(createOCLExpression('\'' + ELSE + '\''));
		dummy.setElse(elseBlock);

		mtlFileBlock.getBody().add(dummy);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));

		return dummy;
	}
}
