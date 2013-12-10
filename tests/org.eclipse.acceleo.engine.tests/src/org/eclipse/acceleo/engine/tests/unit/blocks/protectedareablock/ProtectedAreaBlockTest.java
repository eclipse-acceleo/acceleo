/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.blocks.protectedareablock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

/**
 * Class to test Protected Area block.
 * 
 * @author <a href="mailto:freddy.allilaire@obeo.fr">Freddy Allilaire</a>
 */
public class ProtectedAreaBlockTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/ProtectedAreaBlock/protected_areas.mtl"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getResultPath()
	 */
	@Override
	public String getResultPath() {
		return "ProtectedAreaBlock"; //$NON-NLS-1$
	}

	/**
	 * Tests that the protected areas blocks are accurately generated.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testProtectedAreaBlock() throws IOException {
		generationRoot = new File(getGenerationRootPath("Standard")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("Standard")); //$NON-NLS-1$

		cleanGenerationRoot();

		// We'll only generate for a single class
		EObject target = null;
		for (EObject child : inputModel.eContents()) {
			if (child instanceof EClass) {
				target = child;
				break;
			}
		}
		assertNotNull(target);
		final List<EObject> templateArgs = new ArrayList<EObject>(1);
		templateArgs.add(target);

		new AcceleoService().doGenerateTemplate(module, "test_protected_area", templateArgs, generationRoot, //$NON-NLS-1$
				new BasicMonitor());
		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod);
		}

		for (File generated : getFiles(generationRoot)) {
			assertFalse("a lost file shouldn't have been created", generated.getName().endsWith( //$NON-NLS-1$
					IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION));
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			// We expect two protected areas to have been created
			assertTrue(content.contains("user code 1")); //$NON-NLS-1$
			assertTrue(content.contains("user code 2")); //$NON-NLS-1$
			assertTrue(content.contains("first protected area")); //$NON-NLS-1$
			assertTrue(content.contains("second protected area")); //$NON-NLS-1$
		}
	}

	/**
	 * Tests that lost files are indeed created when a protected area no longer appears in the template.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testRemovedProtectedArea() throws IOException {
		generationRoot = new File(getGenerationRootPath("RemovedProtectedArea")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("RemovedProtectedArea")); //$NON-NLS-1$

		cleanGenerationRoot();

		// We'll only generate for a single class
		EObject target = null;
		for (EObject child : inputModel.eContents()) {
			if (child instanceof EClass) {
				target = child;
				break;
			}
		}
		assertNotNull(target);
		final List<EObject> templateArgs = new ArrayList<EObject>(1);
		templateArgs.add(target);

		new AcceleoService().doGenerateTemplate(module, "test_removed_protected_area", templateArgs, //$NON-NLS-1$
				generationRoot, new BasicMonitor());

		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod + ':' + e.getMessage());
		}

		int lostFiles = 0;
		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			if (generated.getName().endsWith(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION)) {
				lostFiles++;
				// We expect a protected area to have been lost
				assertTrue(content.contains("user code 1")); //$NON-NLS-1$
				assertTrue(content.contains("first protected area")); //$NON-NLS-1$
			} else {
				// We expect a single protected areas to have been created
				assertTrue(content.contains("user code 2")); //$NON-NLS-1$
				assertTrue(content.contains("second protected area")); //$NON-NLS-1$
			}
		}
		assertEquals("There should have been a lost file created", 1, lostFiles); //$NON-NLS-1$
	}

	/**
	 * Tests that lost files are indeed created when a protected area changes its id.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testLostProtectedArea() throws IOException {
		generationRoot = new File(getGenerationRootPath("LostProtectedArea")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("LostProtectedArea")); //$NON-NLS-1$

		cleanGenerationRoot();

		// We'll only generate for a single class
		EObject target = null;
		for (EObject child : inputModel.eContents()) {
			if (child instanceof EClass) {
				target = child;
				break;
			}
		}
		assertNotNull(target);
		final List<EObject> templateArgs = new ArrayList<EObject>(1);
		templateArgs.add(target);

		new AcceleoService().doGenerateTemplate(module, "test_lost_protected_area", templateArgs, //$NON-NLS-1$
				generationRoot, new BasicMonitor());

		try {
			compareDirectories(referenceRoot, generationRoot);
		} catch (IOException e) {
			fail(errorMessageForCompareDirectoriesMethod + ':' + e.getMessage());
		}

		int lostFiles = 0;
		for (File generated : getFiles(generationRoot)) {
			final String content = getAbsoluteFileContent(generated.getAbsolutePath());
			if (generated.getName().endsWith(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION)) {
				lostFiles++;
				// We expect a protected area to have been lost
				assertTrue(content.contains("user code 1")); //$NON-NLS-1$
				assertTrue(content.contains("first protected area")); //$NON-NLS-1$
			} else {
				// We expect two protected areas to have been created
				assertTrue(content.contains("user code 2")); //$NON-NLS-1$
				assertTrue(content.contains("second protected area")); //$NON-NLS-1$
				assertTrue(content.contains("user code 3")); //$NON-NLS-1$
				assertTrue(content.contains("third protected area")); //$NON-NLS-1$
			}
		}
		assertEquals("There should have been a lost file created", 1, lostFiles); //$NON-NLS-1$
	}

	private void generate(String templateName) throws IOException {
		this.generate(templateName, 0);
	}

	private void generate(String templateName, int numberOfLostFile) throws IOException {
		generationRoot = new File(getGenerationRootPath("ProtectedAreaIndent")); //$NON-NLS-1$
		referenceRoot = new File(getReferenceRootPath("ProtectedAreaIndent")); //$NON-NLS-1$

		// We'll only generate for a single class
		EObject target = null;
		for (EObject child : inputModel.eContents()) {
			if (child instanceof EClass) {
				target = child;
				break;
			}
		}
		assertNotNull(target);
		final List<EObject> templateArgs = new ArrayList<EObject>(1);
		templateArgs.add(target);

		new AcceleoService().doGenerateTemplate(module, templateName, templateArgs, generationRoot,
				new BasicMonitor());

		int lostFiles = 0;
		for (File generated : getFiles(generationRoot)) {
			if (generated.getName().equals(templateName)) {
				final String content = getAbsoluteFileContent(generated.getAbsolutePath());
				assertFalse(
						"Invalid indentation for '" + generated.getName() + "'", content.contains("ACCELEO_PROTECTED_AREA_MARKER_FIT_INDENTATION")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				assertTrue(content.contains("Start of user code protected")); //$NON-NLS-1$
				assertTrue(content.contains("protected block")); //$NON-NLS-1$
				assertTrue(content.contains("End of user code")); //$NON-NLS-1$

			}
			if (generated.getName().startsWith(templateName)
					&& generated.getName().endsWith(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION)) {
				lostFiles++;
			}
		}
		assertEquals(lostFiles + " lost files have been created", numberOfLostFile, lostFiles); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaFileBlock() throws IOException {
		generate("test_protected_area_file_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaFileBlockMerge() throws IOException {
		generate("test_protected_area_file_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForBlock() throws IOException {
		generate("test_protected_area_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForBlockMerge() throws IOException {
		generate("test_protected_area_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetBlock() throws IOException {
		generate("test_protected_area_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetBlockMerge() throws IOException {
		generate("test_protected_area_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfBlock() throws IOException {
		generate("test_protected_area_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfBlockMerge() throws IOException {
		generate("test_protected_area_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForLetIfBlock() throws IOException {
		generate("test_protected_area_for_let_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForLetIfBlockMerge() throws IOException {
		generate("test_protected_area_for_let_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateBlock() throws IOException {
		generate("test_protected_area_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateBlockMerge() throws IOException {
		generate("test_protected_area_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateBlock() throws IOException {
		generate("test_protected_area_for_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateBlockMerge() throws IOException {
		generate("test_protected_area_for_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateBlock() throws IOException {
		generate("test_protected_area_let_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateBlockMerge() throws IOException {
		generate("test_protected_area_let_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateBlock() throws IOException {
		generate("test_protected_area_if_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateBlockMerge() throws IOException {
		generate("test_protected_area_if_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateForBlock() throws IOException {
		generate("test_protected_area_template_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateForBlockMerge() throws IOException {
		generate("test_protected_area_template_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateForBlock() throws IOException {
		generate("test_protected_area_for_template_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateForBlockMerge() throws IOException {
		generate("test_protected_area_for_template_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateForBlock() throws IOException {
		generate("test_protected_area_let_template_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateForBlockMerge() throws IOException {
		generate("test_protected_area_let_template_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateForBlock() throws IOException {
		generate("test_protected_area_if_template_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateForBlockMerge() throws IOException {
		generate("test_protected_area_if_template_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateLetBlock() throws IOException {
		generate("test_protected_area_template_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateLetBlockMerge() throws IOException {
		generate("test_protected_area_template_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateLetBlock() throws IOException {
		generate("test_protected_area_for_template_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateTetBlockMerge() throws IOException {
		generate("test_protected_area_for_template_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateLetBlock() throws IOException {
		generate("test_protected_area_let_template_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateLetBlockMerge() throws IOException {
		generate("test_protected_area_let_template_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateLetBlock() throws IOException {
		generate("test_protected_area_if_template_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateLetBlockMerge() throws IOException {
		generate("test_protected_area_if_template_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfBlock() throws IOException {
		generate("test_protected_area_template_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfBlockMerge() throws IOException {
		generate("test_protected_area_template_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateIfBlock() throws IOException {
		generate("test_protected_area_for_template_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateIfBlockMerge() throws IOException {
		generate("test_protected_area_for_template_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateIfBlock() throws IOException {
		generate("test_protected_area_let_template_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateIfBlockMerge() throws IOException {
		generate("test_protected_area_let_template_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateIfBlock() throws IOException {
		generate("test_protected_area_if_template_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateIfBlockMerge() throws IOException {
		generate("test_protected_area_if_template_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileBlock() throws IOException {
		generate("test_protected_area_template_file_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileBlockMerge() throws IOException {
		generate("test_protected_area_template_file_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileForBlock() throws IOException {
		generate("test_protected_area_template_file_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileForBlockMerge() throws IOException {
		generate("test_protected_area_template_file_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileLetBlock() throws IOException {
		generate("test_protected_area_template_file_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileLetBlockMerge() throws IOException {
		generate("test_protected_area_template_file_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileIfBlock() throws IOException {
		generate("test_protected_area_template_file_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileForIfBlock() throws IOException {
		generate("test_protected_area_template_file_for_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileForIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_for_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForIfBlock() throws IOException {
		generate("test_protected_area_template_file_template_for_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_for_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForForBlock() throws IOException {
		generate("test_protected_area_template_file_template_for_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForForBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_for_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForLetBlock() throws IOException {
		generate("test_protected_area_template_file_template_for_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForLetBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_for_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfForBlock() throws IOException {
		generate("test_protected_area_template_file_template_if_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfForBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_if_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfLetBlock() throws IOException {
		generate("test_protected_area_template_file_template_if_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfLetBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_if_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfIfBlock() throws IOException {
		generate("test_protected_area_template_file_template_if_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_if_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetForBlock() throws IOException {
		generate("test_protected_area_template_file_template_let_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetForBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_let_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetLetBlock() throws IOException {
		generate("test_protected_area_template_file_template_let_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetLetBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_let_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetIfBlock() throws IOException {
		generate("test_protected_area_template_file_template_let_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_let_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForIfElseTemplateBlock() throws IOException {
		generate("test_protected_area_template_if_file_for_if_else_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForIfElseTemplateBlockMerge() throws IOException {
		generate("test_protected_area_template_if_file_for_if_else_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForLetElseTemplateBlock() throws IOException {
		generate("test_protected_area_template_if_file_for_let_else_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForLetElseTemplateBlockMerge() throws IOException {
		generate("test_protected_area_template_if_file_for_let_else_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForLetElseLetTemplateBlock() throws IOException {
		generate("test_protected_area_template_if_file_for_let_elselet_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForLetElseLetTemplateBlockMerge() throws IOException {
		generate("test_protected_area_template_if_file_for_let_elselet_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaMultipleGenerations() throws IOException {
		generate("test_protected_area_multiple_generations", 0); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaPerformanceMultipleGenerations() throws IOException {
		generate("test_protected_area_performance_multiple_generations", 0); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaVariableMarkerIdMultipleGenerations() throws IOException {
		generate("test_protected_area_variable_marker_id_multiple_generations", 1); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaConditionalMultipleGenerations() throws IOException {
		generate("test_protected_area_conditional_multiple_generations", 1); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetComplex() throws IOException {
		generate("test_protected_area_template_let_complex", 0); //$NON-NLS-1$
	}
}
