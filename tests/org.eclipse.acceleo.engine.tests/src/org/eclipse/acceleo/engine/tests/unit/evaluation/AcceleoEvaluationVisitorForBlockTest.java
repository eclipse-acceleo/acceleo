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
import java.io.StringWriter;
import java.util.Map;

import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.EcoreFactory;
import org.eclipse.ocl.ecore.Variable;

public class AcceleoEvaluationVisitorForBlockTest extends AbstractAcceleoEvaluationVisitorTest {
	/** Constant output of the "after" statement for the for block tested here. */
	private static final String AFTER = "afterOutput"; //$NON-NLS-1$

	/** Constant output of the "before" statement for the for block tested here. */
	private static final String BEFORE = "beforeOutput"; //$NON-NLS-1$

	/** Constant output of the "each" statement for the for block tested here. */
	private static final String EACH = "eachOutput"; //$NON-NLS-1$

	/** This is the file name that will be used for the dummy file block. */
	private static final String FILE_NAME = "validFile.url"; //$NON-NLS-1$

	/** Constant output of the for block tested here. */
	private static final String OUTPUT = "constantOutput"; //$NON-NLS-1$

	/**
	 * Tests the evaluation of a for block which iteration is a valid collection
	 */
	public void testForBlockCollectionIteration() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// We know the class has two super types, we then expect two iterations to have been made
		assertEquals("Unexpected content generated from the for block.", OUTPUT + OUTPUT, entry.getValue() //$NON-NLS-1$
				.toString());
	}

	/**
	 * Tests the evaluation of an "after" statement on a for block with a collection iteration.
	 */
	public void testForBlockCollectionIterationAfter() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		forBlock.setAfter(createOCLExpression('\'' + AFTER + '\'', EcorePackage.eINSTANCE.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// We know the class has two super types, we then expect two iterations to have been made
		assertEquals("Unexpected content generated from the for block.", OUTPUT + OUTPUT + AFTER, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a "before" statement on a for block with a collection iteration.
	 */
	public void testForBlockCollectionIterationBefore() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		forBlock.setBefore(createOCLExpression('\'' + BEFORE + '\'', EcorePackage.eINSTANCE.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// We know the class has two super types, we then expect two iterations to have been made
		assertEquals("Unexpected content generated from the for block.", BEFORE + OUTPUT + OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of an "each" statement on a for block with a collection iteration.
	 */
	public void testForBlockCollectionIterationEach() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		forBlock.setEach(createOCLExpression('\'' + EACH + '\'', EcorePackage.eINSTANCE.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// We know the class has two super types, we then expect two iterations to have been made
		assertEquals("Unexpected content generated from the for block.", OUTPUT + EACH + OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a for block with all possible statements filled in (each, before, after,
	 * guard).
	 */
	public void testForBlockCollectionIterationFull() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		forBlock.setEach(createOCLExpression('\'' + EACH + '\'', EcorePackage.eINSTANCE.getEClass()));
		forBlock.setBefore(createOCLExpression('\'' + BEFORE + '\'', EcorePackage.eINSTANCE.getEClass()));
		forBlock.setAfter(createOCLExpression('\'' + AFTER + '\'', EcorePackage.eINSTANCE.getEClass()));
		forBlock.setGuard(createOCLExpression("oclIsKindOf(EObject)", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the for block.", BEFORE + OUTPUT + EACH + OUTPUT //$NON-NLS-1$
				+ AFTER, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a for block on a collection iteration with an undefined guard.
	 */
	public void testForBlockCollectionIterationUndefinedGuard() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		// Since there is no id attribute, calling "name" on it will make the guard undefined
		forBlock.setGuard(createOCLExpression("eIDAttribute.name", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		try {
			evaluationVisitor.visitExpression(getParentTemplate(forBlock));
			fail("Evaluation visitor did not throw the expected evaluation exception for a for block" + //$NON-NLS-1$
					" with an undefined guard"); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(forBlock.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(forBlock)).getName()));
		}
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// As the guard was incorrect we expect no content to have been generated
		assertEquals("Unexpected content generated from the for block.", "", entry.getValue() //$NON-NLS-1$ //$NON-NLS-2$
				.toString());
	}

	/**
	 * Tests the evaluation of a for block with a null iteration.
	 */
	public void testForBlockNullIteration() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		try {
			evaluationVisitor.visitExpression(getParentTemplate(forBlock));
			fail("Evaluation visitor did not throw the expected evaluation exception for a for block" + //$NON-NLS-1$
					" with a null iteration"); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(forBlock.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(forBlock)).getName()));
		}
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// As the iteration was incorrect we expect no content to have been generated
		assertEquals("Unexpected content generated from the for block.", "", entry.getValue() //$NON-NLS-1$ //$NON-NLS-2$
				.toString());
	}

	/**
	 * Tests the evaluation of a for block with an undefined iteration.
	 */
	public void testForBlockUndefinedIteration() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		// The iteration is undefined (evaluation NPE as the EClass has no super type)
		try {
			evaluationVisitor.visitExpression(getParentTemplate(forBlock));
			fail("Evaluation visitor did not throw the expected evaluation exception for a for block" + //$NON-NLS-1$
					" with an undefined iteration"); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(forBlock.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(forBlock)).getName()));
		}
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// As the iteration was incorrect we expect no content to have been generated
		assertEquals("Unexpected content generated from the for block.", "", entry.getValue() //$NON-NLS-1$ //$NON-NLS-2$
				.toString());
	}

	/**
	 * Tests the evaluation of a for block with a single element as its iteration.
	 */
	public void testForBlockUniqueIteration() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// The iteration had a single element, we then expect one loop to have been made
		assertEquals("Unexpected content generated from the for block.", OUTPUT, entry.getValue() //$NON-NLS-1$
				.toString());
	}

	/**
	 * Tests the evaluation of an "after" statement on a for block with a single element iteration.
	 */
	public void testForBlockUniqueIterationAfter() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		forBlock.setAfter(createOCLExpression('\'' + AFTER + '\'', EcorePackage.eINSTANCE.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// The iteration had a single element, we then expect one loop to have been made
		assertEquals("Unexpected content generated from the for block.", OUTPUT + AFTER, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a "before" statement on a for block with a single element iteration.
	 */
	public void testForBlockUniqueIterationBefore() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		forBlock.setBefore(createOCLExpression('\'' + BEFORE + '\'', EcorePackage.eINSTANCE.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// The iteration had a single element, we then expect one loop to have been made
		assertEquals("Unexpected content generated from the for block.", BEFORE + OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of an "each" statement on a for block with a single element iteration.
	 */
	public void testForBlockUniqueIterationEach() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		forBlock.setEach(createOCLExpression('\'' + EACH + '\'', EcorePackage.eINSTANCE.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// The iteration had a single element, we then expect one loop to have been made
		// The "each" shouldn't be generated in such cases
		assertEquals("Unexpected content generated from the for block.", OUTPUT, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a for block with all possible statements filled in (each, before, after,
	 * guard).
	 */
	public void testForBlockUniqueIterationFull() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		forBlock.setEach(createOCLExpression('\'' + EACH + '\'', EcorePackage.eINSTANCE.getEClass()));
		forBlock.setBefore(createOCLExpression('\'' + BEFORE + '\'', EcorePackage.eINSTANCE.getEClass()));
		forBlock.setAfter(createOCLExpression('\'' + AFTER + '\'', EcorePackage.eINSTANCE.getEClass()));
		forBlock.setGuard(createOCLExpression("oclIsKindOf(EObject)", EcorePackage.eINSTANCE.getEClass())); //$NON-NLS-1$

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(forBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the for block.", BEFORE + OUTPUT + AFTER, entry //$NON-NLS-1$
				.getValue().toString());
	}

	/**
	 * Tests the evaluation of a for block on a single element iteration with an undefined guard.
	 */
	public void testForBlockUniqueIterationUndefinedGuard() {
		final ForBlock forBlock = getDummyForBlock();
		forBlock.setIterSet(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));
		// Since there is no id attribute, calling "name" on it will make the guard undefined
		forBlock.setGuard(createOCLExpression("eIDAttribute.name", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		try {
			evaluationVisitor.visitExpression(getParentTemplate(forBlock));
			fail("Evaluation visitor did not throw the expected evaluation exception for a for block" + //$NON-NLS-1$
					" with an undefined guard"); //$NON-NLS-1$
		} catch (AcceleoEvaluationException e) {
			// expected behavior
			assertTrue(e.getMessage().contains(forBlock.toString()));
			assertTrue(e.getMessage().contains(((Module)EcoreUtil.getRootContainer(forBlock)).getName()));
		}
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, StringWriter> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		// As the guard was incorrect we expect no content to have been generated
		assertEquals("Unexpected content generated from the for block.", "", entry.getValue() //$NON-NLS-1$ //$NON-NLS-2$
				.toString());
	}

	/**
	 * Creates a dummy for block containing a single string literal as its body.
	 * 
	 * @return Dummy for block.
	 */
	private ForBlock getDummyForBlock() {
		final FileBlock mtlFileBlock = MtlFactory.eINSTANCE.createFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression('\'' + FILE_NAME + '\''));
		mtlFileBlock.setOpenMode(OpenModeKind.OVER_WRITE);
		getDummyTemplate().getBody().add(mtlFileBlock);

		final ForBlock dummy = MtlFactory.eINSTANCE.createForBlock();
		dummy.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));

		final Variable loopVar = EcoreFactory.eINSTANCE.createVariable();
		loopVar.setName("tempLoopVar"); //$NON-NLS-1$
		dummy.setLoopVariable(loopVar);

		mtlFileBlock.getBody().add(dummy);

		return dummy;
	}
}
