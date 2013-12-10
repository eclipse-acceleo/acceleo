/*******************************************************************************
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
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

/**
 * This will test the behavior of if blocks evaluation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationVisitorIfBlockTest extends AbstractAcceleoEvaluationVisitorTest {
	/** Constant output of the "else" statement for the if block tested here. */
	private static final String ELSE = "elseOutput"; //$NON-NLS-1$

	/** Constant output of the "else if" statement for the if block tested here. */
	private static final String ELSEIF = "elseIfOutput"; //$NON-NLS-1$

	/** This is the file name that will be used for the dummy file block. */
	private static final String FILE_NAME = "validFile.url"; //$NON-NLS-1$

	/** Constant output of the file block tested here. */
	private static final String OUTPUT = "constantOutput"; //$NON-NLS-1$

	/** Constant output of the if block tested here. */
	private static final String IF_OUTPUT = "ifBodyOutput"; //$NON-NLS-1$

	/**
	 * Tests the evaluation of an if block with its condition evaluating to true.
	 */
	@Test
	public void testIfBlockBody() {
		final IfBlock ifBlock = getDummyIfBlock();
		ifBlock.setIfExpr(createOCLExpression("eSuperTypes->size() > 0", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		final IfBlock elseIf = MtlFactory.eINSTANCE.createIfBlock();
		elseIf.setIfExpr(createOCLExpression("eSuperTypes->size() = 0", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		elseIf.getBody().add(createOCLExpression('\'' + ELSEIF + '\''));
		ifBlock.getElseIf().add(elseIf);

		// The evaluation should go through the body of the if
		evaluationVisitor.visitExpression(getParentTemplate(ifBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals(
				"Unexpected content generated from the if block.", OUTPUT + IF_OUTPUT + OUTPUT, entry.getValue() //$NON-NLS-1$ 
						.toString());
	}

	/**
	 * Tests the evaluation of an if block with its condition evaluating to false. An "Else if" has been set,
	 * but its condition also evaluated to false.
	 */
	@Test
	public void testIfBlockElse() {
		final IfBlock ifBlock = getDummyIfBlock();
		ifBlock.setIfExpr(createOCLExpression("eSuperTypes->size() > 0", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		final IfBlock elseIf = MtlFactory.eINSTANCE.createIfBlock();
		elseIf.setIfExpr(createOCLExpression("eSuperTypes->size() < 0", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		elseIf.getBody().add(createOCLExpression('\'' + ELSEIF + '\''));
		ifBlock.getElseIf().add(elseIf);

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		// The evaluation should go through the "else" of the if
		evaluationVisitor.visitExpression(getParentTemplate(ifBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals(
				"Unexpected content generated from the if block.", OUTPUT + ELSE + OUTPUT, entry.getValue() //$NON-NLS-1$ 
						.toString());
	}

	/**
	 * Tests the evaluation of an if block with its condition evaluating to false. An "Else if" has been set,
	 * and its condition evaluates to true.
	 */
	@Test
	public void testIfBlockElseIf() {
		final IfBlock ifBlock = getDummyIfBlock();
		ifBlock.setIfExpr(createOCLExpression("eSuperTypes->size() > 0", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		final IfBlock elseIf = MtlFactory.eINSTANCE.createIfBlock();
		elseIf.setIfExpr(createOCLExpression("eSuperTypes->size() = 0", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		elseIf.getBody().add(createOCLExpression('\'' + ELSEIF + '\''));
		ifBlock.getElseIf().add(elseIf);

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		// The evaluation should go through the "elseif" of the if
		evaluationVisitor.visitExpression(getParentTemplate(ifBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals(
				"Unexpected content generated from the if block.", OUTPUT + ELSEIF + OUTPUT, entry.getValue() //$NON-NLS-1$ 
						.toString());
	}

	/**
	 * Tests the evaluation of an if block with its condition evaluating to false. An "Else if" has been set,
	 * and its condition is null.
	 */
	@Test
	public void testIfBlockElseIfNullCondition() {
		final IfBlock ifBlock = getDummyIfBlock();
		ifBlock.setIfExpr(createOCLExpression("eSuperTypes->size() > 0", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		final IfBlock elseIf = MtlFactory.eINSTANCE.createIfBlock();
		elseIf.setIfExpr(createOCLExpression(
				"not eSuperTypes->first().oclIsUndefined()", EcorePackage.eINSTANCE //$NON-NLS-1$
						.getEClass()));
		elseIf.getBody().add(createOCLExpression('\'' + ELSEIF + '\''));
		ifBlock.getElseIf().add(elseIf);

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(ifBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// As the condition of the else if was undefined, we expect no content to have been generated
		assertEquals(
				"Unexpected content generated from the if block.", OUTPUT + ELSE + OUTPUT, entry.getValue() //$NON-NLS-1$ 
						.toString());
	}

	/**
	 * Tests the evaluation of an if block with its condition evaluating to false. An "Else if" has been set,
	 * and its condition is undefined.
	 */
	@Test
	public void testIfBlockElseIfUndefinedCondition() {
		final IfBlock ifBlock = getDummyIfBlock();
		ifBlock.setIfExpr(createOCLExpression("eSuperTypes->size() > 0", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		final IfBlock elseIf = MtlFactory.eINSTANCE.createIfBlock();
		elseIf.setIfExpr(createOCLExpression("eSuperTypes->first().name", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		elseIf.getBody().add(createOCLExpression('\'' + ELSEIF + '\''));
		ifBlock.getElseIf().add(elseIf);

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(ifBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// As the condition of the else if was undefined, we expect no content to have been generated
		assertEquals("Unexpected content generated from the if block.", OUTPUT + OUTPUT, entry.getValue() //$NON-NLS-1$ 
				.toString());
	}

	/**
	 * Tests the evaluation of an if block with its condition null.
	 */
	@Test
	public void testIfBlockNullCondition() {
		final IfBlock ifBlock = getDummyIfBlock();
		ifBlock.setIfExpr(createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(ifBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the if block.", OUTPUT + ELSE + OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of an if block with its condition undefined.
	 */
	@Test
	public void testIfBlockUndefinedCondition() {
		final IfBlock ifBlock = getDummyIfBlock();
		ifBlock.setIfExpr(createOCLExpression("eSuperTypes->first().name", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(ifBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the if block.", OUTPUT + OUTPUT, entry.getValue() //$NON-NLS-1$
				.toString());
	}

	/**
	 * Creates a dummy if block containing a single string literal as its body.
	 * 
	 * @return Dummy if block.
	 */
	private IfBlock getDummyIfBlock() {
		final FileBlock mtlFileBlock = MtlFactory.eINSTANCE.createFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression('\'' + FILE_NAME + '\''));
		mtlFileBlock.setOpenMode(OpenModeKind.OVER_WRITE);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));
		getDummyTemplate().getBody().add(mtlFileBlock);

		final IfBlock dummy = MtlFactory.eINSTANCE.createIfBlock();
		dummy.getBody().add(createOCLExpression('\'' + IF_OUTPUT + '\''));
		final Block elseBlock = MtlFactory.eINSTANCE.createBlock();
		elseBlock.getBody().add(createOCLExpression('\'' + ELSE + '\''));
		dummy.setElse(elseBlock);

		mtlFileBlock.getBody().add(dummy);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));

		return dummy;
	}
}
