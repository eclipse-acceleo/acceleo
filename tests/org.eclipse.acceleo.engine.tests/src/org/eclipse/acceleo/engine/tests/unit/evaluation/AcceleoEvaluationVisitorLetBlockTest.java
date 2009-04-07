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
import java.io.Writer;
import java.util.Map;

import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.Variable;

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

	/** Constant output of the let block tested here. */
	private static final String OUTPUT = "constantOutput"; //$NON-NLS-1$

	/**
	 * Tests the evaluation of a let block with its condition being a valid instanceof.
	 */
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
		Map.Entry<String, Writer> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", OUTPUT, entry.getValue() //$NON-NLS-1$ 
				.toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being an invalid instanceof. An else let has
	 * been set for the let, its condition also being an invalid instanceof.
	 */
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
		Map.Entry<String, Writer> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", ELSE, entry.getValue() //$NON-NLS-1$ 
				.toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being an invalid instanceof. An else let hasn't
	 * been set for the let.
	 */
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
		Map.Entry<String, Writer> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", ELSE, entry.getValue() //$NON-NLS-1$ 
				.toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being an invalid instanceof. An else let has
	 * been set for the let, its condition being a valid instanceof.
	 */
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
		Map.Entry<String, Writer> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", ELSELET, entry.getValue() //$NON-NLS-1$ 
				.toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being an invalid instanceof. An else let has
	 * been set for the let, its condition being undefined.
	 */
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
				"eSuperTypes->select(oclIsKindOf(EReference))->first()", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$
		elseLet.setLetVariable(elseLetVar);
		letBlock.getElseLet().add(elseLet);

		try {
			evaluationVisitor.visitExpression(getParentTemplate(letBlock));
			fail("Evaluation visitor did not throw the expected evaluation exception for a let block" + //$NON-NLS-1$
					" with an undefined else let condition"); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(elseLet.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(elseLet)).getName()));
		}
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, Writer> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", "", entry.getValue() //$NON-NLS-1$ //$NON-NLS-2$
				.toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being null.
	 */
	public void testLetBlockNullCondition() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEAttribute());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		try {
			evaluationVisitor.visitExpression(getParentTemplate(letBlock));
			fail("Evaluation visitor did not throw the expected evaluation exception for a let block" + //$NON-NLS-1$
					" with a null condition"); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(letBlock.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(letBlock)).getName()));
		}
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, Writer> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", "", entry.getValue() //$NON-NLS-1$ //$NON-NLS-2$
				.toString());
	}

	/**
	 * Tests the evaluation of a let block with its condition being undefined.
	 */
	public void testLetBlockUndefinedCondition() {
		final LetBlock letBlock = getDummyLetBlock();
		letBlock.getLetVariable().setType(EcorePackage.eINSTANCE.getEAttribute());
		letBlock.getLetVariable().setInitExpression(
				createOCLExpression("eSuperTypes->select(oclIsKindOf(EReference))->first()", //$NON-NLS-1$
						EcorePackage.eINSTANCE.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		try {
			evaluationVisitor.visitExpression(getParentTemplate(letBlock));
			fail("Evaluation visitor did not throw the expected evaluation exception for a let block" + //$NON-NLS-1$
					" with an undefined condition"); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(letBlock.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(letBlock)).getName()));
		}
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, Writer> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the let block.", "", entry.getValue() //$NON-NLS-1$ //$NON-NLS-2$
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
		getDummyTemplate().getBody().add(mtlFileBlock);

		final LetBlock dummy = MtlFactory.eINSTANCE.createLetBlock();
		dummy.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));
		final Variable letVar = EcoreFactory.eINSTANCE.createVariable();
		letVar.setName(LET_VAR_NAME);
		dummy.setLetVariable(letVar);
		final Block elseBlock = MtlFactory.eINSTANCE.createBlock();
		elseBlock.getBody().add(createOCLExpression('\'' + ELSE + '\''));
		dummy.setElse(elseBlock);

		mtlFileBlock.getBody().add(dummy);

		return dummy;
	}
}
