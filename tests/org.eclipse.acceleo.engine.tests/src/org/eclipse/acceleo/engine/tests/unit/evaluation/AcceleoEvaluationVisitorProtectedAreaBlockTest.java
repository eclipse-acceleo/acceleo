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
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.OpenModeKind;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

/**
 * This will test the behavior of protected area blocks evaluation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationVisitorProtectedAreaBlockTest extends AbstractAcceleoEvaluationVisitorTest {
	/** Constant output of the "protected content" for the file block tested here. */
	private static final String PROTECTED = "protectedOutput"; //$NON-NLS-1$

	/** This will be used as the protected area content to check protected code conservation. */
	private static final String CHANGED = "changedOutput"; //$NON-NLS-1$

	/** This is the file name that will be used for the dummy file block. */
	private static final String FILE_NAME = "validFile.url"; //$NON-NLS-1$

	/** This is the marker that will be used for the dummy protected block. */
	private static final String MARKER = "marker"; //$NON-NLS-1$

	/** This will contain the "start of user code" tag as defined in the engine. */
	private static final String START_USER_CODE = AcceleoEngineMessages.getString("usercode.start"); //$NON-NLS-1$

	/** This will contain the "end of user code" tag as defined in the engine. */
	private static final String END_USER_CODE = AcceleoEngineMessages.getString("usercode.end"); //$NON-NLS-1$

	/** Expected content of the dummy protected area. */
	private static final String EXPECTED_PROTECTED_OUTPUT = START_USER_CODE + ' ' + MARKER
			+ System.getProperty("line.separator") + PROTECTED + END_USER_CODE; //$NON-NLS-1$

	/** Constant output of the file block tested here. */
	private static final String OUTPUT = "constantOutput"; //$NON-NLS-1$

	/**
	 * Tests the evaluation of a protected block.
	 */
	@Test
	public void testProtectedBlockBody() {
		final ProtectedAreaBlock protectedBlock = getDummyProtectedAreaBlock();

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(protectedBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the protected block.", OUTPUT //$NON-NLS-1$
				+ EXPECTED_PROTECTED_OUTPUT + OUTPUT, entry.getValue().toString());

		// If we evaluate a second time while changing the protected content, the output shouldn't be modified
		protectedBlock.getBody().clear();
		protectedBlock.getBody().add(createOCLLineSeparator());
		protectedBlock.getBody().add(createOCLExpression('\'' + CHANGED + '\''));

		evaluationVisitor.visitExpression(getParentTemplate(protectedBlock));
		assertSame("Expecting a single preview as no lost file should have been created", 1, getPreview() //$NON-NLS-1$
				.size());
		entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("The generated content of a protected area shouldn't have changed.", OUTPUT //$NON-NLS-1$
				+ EXPECTED_PROTECTED_OUTPUT + OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a protected block when the marker has changed.
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testProtectedBlockLostArea() {
		final ProtectedAreaBlock protectedBlock = getDummyProtectedAreaBlock();

		// set the value of self to the third eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(2)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(protectedBlock));
		assertSame("Expecting a single preview", 1, getPreview().size()); //$NON-NLS-1$
		Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
				+ FILE_NAME, entry.getKey());
		assertEquals("Unexpected content generated from the protected block.", OUTPUT //$NON-NLS-1$
				+ EXPECTED_PROTECTED_OUTPUT + OUTPUT, entry.getValue().toString());

		// Changes the marker of the protected area
		protectedBlock.setMarker(createOCLExpression('\'' + MARKER + 's' + '\''));

		evaluationVisitor.visitExpression(getParentTemplate(protectedBlock));

		try {
			previewStrategy.awaitCompletion();
		} catch (InterruptedException e) {
			fail("Lost file creator pool termination interrupted."); //$NON-NLS-1$
		}

		assertSame("Expecting two previews created, the lost file and the actual file.", 2, getPreview() //$NON-NLS-1$
				.size());

		final Iterator<Map.Entry<String, String>> iterator = getPreview().entrySet().iterator();
		final String expectedChangedFileContent = EXPECTED_PROTECTED_OUTPUT.replace(MARKER, MARKER + 's');
		boolean lostFileFound = false;
		boolean fileFound = false;

		while (iterator.hasNext()) {
			entry = iterator.next();
			if (entry.getKey().endsWith(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION)) {
				if (lostFileFound) {
					fail("More than one lost file has been created."); //$NON-NLS-1$
				}
				lostFileFound = true;
				assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
						+ FILE_NAME.concat(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION), entry.getKey());
				assertTrue("Content of the lost file does'nt reflect the protected area's.", //$NON-NLS-1$
						entry.getValue().toString().contains(EXPECTED_PROTECTED_OUTPUT));
			} else {
				if (fileFound) {
					fail("More than one file has been created."); //$NON-NLS-1$
				}
				fileFound = true;
				assertEquals("Unexpected file URL.", generationRoot.getAbsolutePath() + File.separatorChar //$NON-NLS-1$
						+ FILE_NAME, entry.getKey());
				assertEquals("Unexpected content of the file when a protected area is lost.", OUTPUT //$NON-NLS-1$
						+ expectedChangedFileContent + OUTPUT, entry.getValue().toString());
			}
		}
	}

	/**
	 * Tests the evaluation of a protected block with its marker evaluating to OclInvalid.
	 */
	@Test
	public void testProtectedBlockUndefinedMarker() {
		final ProtectedAreaBlock protectedBlock = getDummyProtectedAreaBlock();
		protectedBlock.setMarker(createOCLExpression("eSuperTypes->first()", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(protectedBlock));
		assertSame("Expecting a single preview as no lost file should have been created", 1, getPreview() //$NON-NLS-1$
				.size());
		final Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("No output should have been generated for a protected area with no marker.", OUTPUT //$NON-NLS-1$ 
				+ OUTPUT, entry.getValue().toString());
	}

	/**
	 * Tests the evaluation of a protected block with its marker evaluating to null.
	 */
	@Test
	public void testProtectedBlockNullMarker() {
		final ProtectedAreaBlock protectedBlock = getDummyProtectedAreaBlock();
		protectedBlock.setMarker(createOCLExpression("eIDAttribute", EcorePackage.eINSTANCE //$NON-NLS-1$
				.getEClass()));

		// set the value of self to the first eclass of the test package
		evaluationVisitor.getEvaluationEnvironment().add("self", getTestPackage().getEClassifiers().get(0)); //$NON-NLS-1$

		evaluationVisitor.visitExpression(getParentTemplate(protectedBlock));
		assertSame("Expecting a single preview as no lost file should have been created", 1, getPreview() //$NON-NLS-1$
				.size());
		final Map.Entry<String, String> entry = getPreview().entrySet().iterator().next();
		assertEquals("Unexpected file URL.", //$NON-NLS-1$
				generationRoot.getAbsolutePath() + File.separatorChar + FILE_NAME, entry.getKey());
		assertEquals("No output should have been generated for a protected area with no marker.", OUTPUT //$NON-NLS-1$ 
				+ OUTPUT, entry.getValue().toString());
	}

	/**
	 * Creates a dummy file block containing a single protected area surrounded by string literals as its
	 * body. This protected area will then be returned.
	 * 
	 * @return Dummy protected area block.
	 */
	private ProtectedAreaBlock getDummyProtectedAreaBlock() {
		final FileBlock mtlFileBlock = MtlFactory.eINSTANCE.createFileBlock();
		mtlFileBlock.setFileUrl(createOCLExpression('\'' + FILE_NAME + '\''));
		mtlFileBlock.setOpenMode(OpenModeKind.OVER_WRITE);
		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));
		getDummyTemplate().getBody().add(mtlFileBlock);

		final ProtectedAreaBlock dummy = MtlFactory.eINSTANCE.createProtectedAreaBlock();
		dummy.getBody().add(createOCLLineSeparator());
		dummy.getBody().add(createOCLExpression('\'' + PROTECTED + '\''));
		dummy.setMarker(createOCLExpression('\'' + MARKER + '\''));
		mtlFileBlock.getBody().add(dummy);

		mtlFileBlock.getBody().add(createOCLExpression('\'' + OUTPUT + '\''));
		return dummy;
	}
}
