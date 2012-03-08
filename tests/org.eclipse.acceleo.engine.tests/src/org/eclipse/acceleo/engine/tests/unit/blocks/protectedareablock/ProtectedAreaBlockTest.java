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

	public void testProtectedAreaFileBlock() throws IOException {
		generate("test_protected_area_file_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaFileBlockMerge() throws IOException {
		generate("test_protected_area_file_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaForBlock() throws IOException {
		generate("test_protected_area_for_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaForBlockMerge() throws IOException {
		generate("test_protected_area_for_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetBlock() throws IOException {
		generate("test_protected_area_let_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetBlockMerge() throws IOException {
		generate("test_protected_area_let_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfBlock() throws IOException {
		generate("test_protected_area_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfBlockMerge() throws IOException {
		generate("test_protected_area_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaForLetIfBlock() throws IOException {
		generate("test_protected_area_for_let_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaForLetIfBlockMerge() throws IOException {
		generate("test_protected_area_for_let_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateBlock() throws IOException {
		generate("test_protected_area_template_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateBlockMerge() throws IOException {
		generate("test_protected_area_template_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaForTemplateBlock() throws IOException {
		generate("test_protected_area_for_template_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaForTemplateBlockMerge() throws IOException {
		generate("test_protected_area_for_template_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetTemplateBlock() throws IOException {
		generate("test_protected_area_let_template_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetTemplateBlockMerge() throws IOException {
		generate("test_protected_area_let_template_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfTemplateBlock() throws IOException {
		generate("test_protected_area_if_template_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfTemplateBlockMerge() throws IOException {
		generate("test_protected_area_if_template_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateForBlock() throws IOException {
		generate("test_protected_area_template_for_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateForBlockMerge() throws IOException {
		generate("test_protected_area_template_for_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaForTemplateForBlock() throws IOException {
		generate("test_protected_area_for_template_for_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaForTemplateForBlockMerge() throws IOException {
		generate("test_protected_area_for_template_for_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetTemplateForBlock() throws IOException {
		generate("test_protected_area_let_template_for_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetTemplateForBlockMerge() throws IOException {
		generate("test_protected_area_let_template_for_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfTemplateForBlock() throws IOException {
		generate("test_protected_area_if_template_for_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfTemplateForBlockMerge() throws IOException {
		generate("test_protected_area_if_template_for_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateLetBlock() throws IOException {
		generate("test_protected_area_template_let_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateLetBlockMerge() throws IOException {
		generate("test_protected_area_template_let_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaForTemplateLetBlock() throws IOException {
		generate("test_protected_area_for_template_let_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaForTemplateTetBlockMerge() throws IOException {
		generate("test_protected_area_for_template_let_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetTemplateLetBlock() throws IOException {
		generate("test_protected_area_let_template_let_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetTemplateLetBlockMerge() throws IOException {
		generate("test_protected_area_let_template_let_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfTemplateLetBlock() throws IOException {
		generate("test_protected_area_if_template_let_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfTemplateLetBlockMerge() throws IOException {
		generate("test_protected_area_if_template_let_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateIfBlock() throws IOException {
		generate("test_protected_area_template_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateIfBlockMerge() throws IOException {
		generate("test_protected_area_template_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaForTemplateIfBlock() throws IOException {
		generate("test_protected_area_for_template_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaForTemplateIfBlockMerge() throws IOException {
		generate("test_protected_area_for_template_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetTemplateIfBlock() throws IOException {
		generate("test_protected_area_let_template_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaLetTemplateIfBlockMerge() throws IOException {
		generate("test_protected_area_let_template_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfTemplateIfBlock() throws IOException {
		generate("test_protected_area_if_template_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaIfTemplateIfBlockMerge() throws IOException {
		generate("test_protected_area_if_template_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileBlock() throws IOException {
		generate("test_protected_area_template_file_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileBlockMerge() throws IOException {
		generate("test_protected_area_template_file_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileForBlock() throws IOException {
		generate("test_protected_area_template_file_for_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileForBlockMerge() throws IOException {
		generate("test_protected_area_template_file_for_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileLetBlock() throws IOException {
		generate("test_protected_area_template_file_let_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileLetBlockMerge() throws IOException {
		generate("test_protected_area_template_file_let_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileIfBlock() throws IOException {
		generate("test_protected_area_template_file_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileForIfBlock() throws IOException {
		generate("test_protected_area_template_file_for_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileForIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_for_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateForIfBlock() throws IOException {
		generate("test_protected_area_template_file_template_for_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateForIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_for_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateForForBlock() throws IOException {
		generate("test_protected_area_template_file_template_for_for_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateForForBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_for_for_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateForLetBlock() throws IOException {
		generate("test_protected_area_template_file_template_for_let_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateForLetBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_for_let_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateIfForBlock() throws IOException {
		generate("test_protected_area_template_file_template_if_for_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateIfForBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_if_for_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateIfLetBlock() throws IOException {
		generate("test_protected_area_template_file_template_if_let_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateIfLetBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_if_let_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateIfIfBlock() throws IOException {
		generate("test_protected_area_template_file_template_if_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateIfIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_if_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateLetForBlock() throws IOException {
		generate("test_protected_area_template_file_template_let_for_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateLetForBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_let_for_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateLetLetBlock() throws IOException {
		generate("test_protected_area_template_file_template_let_let_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateLetLetBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_let_let_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateLetIfBlock() throws IOException {
		generate("test_protected_area_template_file_template_let_if_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateFileTemplateLetIfBlockMerge() throws IOException {
		generate("test_protected_area_template_file_template_let_if_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateIfFileForIfElseTemplateBlock() throws IOException {
		generate("test_protected_area_template_if_file_for_if_else_template_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateIfFileForIfElseTemplateBlockMerge() throws IOException {
		generate("test_protected_area_template_if_file_for_if_else_template_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateIfFileForLetElseTemplateBlock() throws IOException {
		generate("test_protected_area_template_if_file_for_let_else_template_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateIfFileForLetElseTemplateBlockMerge() throws IOException {
		generate("test_protected_area_template_if_file_for_let_else_template_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateIfFileForLetElseLetTemplateBlock() throws IOException {
		generate("test_protected_area_template_if_file_for_let_elselet_template_block"); //$NON-NLS-1$
	}

	public void testProtectedAreaTemplateIfFileForLetElseLetTemplateBlockMerge() throws IOException {
		generate("test_protected_area_template_if_file_for_let_elselet_template_block_merge"); //$NON-NLS-1$
	}

	public void testProtectedAreaMultipleGenerations() throws IOException {
		generate("test_protected_area_multiple_generations", 0); //$NON-NLS-1$
	}

	public void testProtectedAreaPerformanceMultipleGenerations() throws IOException {
		generate("test_protected_area_performance_multiple_generations", 0); //$NON-NLS-1$
	}

	public void testProtectedAreaVariableMarkerIdMultipleGenerations() throws IOException {
		generate("test_protected_area_variable_marker_id_multiple_generations", 1); //$NON-NLS-1$
	}

	public void testProtectedAreaConditionalMultipleGenerations() throws IOException {
		generate("test_protected_area_conditional_multiple_generations", 1); //$NON-NLS-1$
	}

	public void testProtectedAreaLetComplex() throws IOException {
		generate("test_protected_area_template_let_complex", 0); //$NON-NLS-1$
	}
}
