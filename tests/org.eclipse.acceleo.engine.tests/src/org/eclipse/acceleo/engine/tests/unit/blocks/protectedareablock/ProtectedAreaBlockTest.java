/*******************************************************************************
 * Copyright (c) 2008, 2013 Obeo.
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Class to test Protected Area block.
 * 
 * @author <a href="mailto:freddy.allilaire@obeo.fr">Freddy Allilaire</a>
 */
@Ignore
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
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "ProtectedAreaBlock"; //$NON-NLS-1$
	}

	/**
	 * Tests that the protected areas blocks are accurately generated.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	@Ignore
	public void testProtectedAreaBlock() {
		this.init("Standard"); //$NON-NLS-1$
		this.generate("test_protected_area", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests that lost files are indeed created when a protected area no longer appears in the template.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	@Ignore
	public void testRemovedProtectedArea() {
		this.init("RemovedProtectedArea"); //$NON-NLS-1$
		this.generate("test_removed_protected_area", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	/**
	 * Tests that lost files are indeed created when a protected area changes its id.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	@Ignore
	public void testLostProtectedArea() {
		this.init("RemovedProtectedArea"); //$NON-NLS-1$
		this.generate("test_removed_protected_area", defaultStrategy); //$NON-NLS-1$
		this.compareDirectories();
	}

	private void generate(String templateName) {
		this.generate(templateName, 0);
	}

	private void generate(String templateName, int numberOfLostFile) {
		this.init("ProtectedAreaIndent"); //$NON-NLS-1$
		this.generate(templateName, defaultStrategy);

		int lostFiles = 0;
		for (File generated : generationRoot.listFiles()) {
			if (generated.getName().equals(templateName)) {
				try {
					final String content = this.readFileFromURL(generated.toURI().toURL());
					assertFalse(
							"Invalid indentation for '" + generated.getName() + "'", content.contains("ACCELEO_PROTECTED_AREA_MARKER_FIT_INDENTATION")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					assertTrue(content.contains("Start of user code protected")); //$NON-NLS-1$
					assertTrue(content.contains("protected block")); //$NON-NLS-1$
					assertTrue(content.contains("End of user code")); //$NON-NLS-1$
				} catch (MalformedURLException e) {
					fail(e.getMessage());
				}

			}
			if (generated.getName().startsWith(templateName)
					&& generated.getName().endsWith(IAcceleoConstants.ACCELEO_LOST_FILE_EXTENSION)) {
				lostFiles++;
			}
		}
		assertEquals(lostFiles + " lost files have been created", numberOfLostFile, lostFiles); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaFileBlock() {
		generate("test_protected_area_file_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaFileBlockMerge() {
		generate("test_protected_area_file_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForBlock() {
		generate("test_protected_area_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForBlockMerge() {
		generate("test_protected_area_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetBlock() {
		generate("test_protected_area_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetBlockMerge() {
		generate("test_protected_area_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfBlock() {
		generate("test_protected_area_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfBlockMerge() {
		generate("test_protected_area_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForLetIfBlock() {
		generate("test_protected_area_for_let_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForLetIfBlockMerge() {
		generate("test_protected_area_for_let_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateBlock() {
		generate("test_protected_area_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateBlockMerge() {
		generate("test_protected_area_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateBlock() {
		generate("test_protected_area_for_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateBlockMerge() {
		generate("test_protected_area_for_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateBlock() {
		generate("test_protected_area_let_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateBlockMerge() {
		generate("test_protected_area_let_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateBlock() {
		generate("test_protected_area_if_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateBlockMerge() {
		generate("test_protected_area_if_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateForBlock() {
		generate("test_protected_area_template_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateForBlockMerge() {
		generate("test_protected_area_template_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateForBlock() {
		generate("test_protected_area_for_template_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateForBlockMerge() {
		generate("test_protected_area_for_template_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateForBlock() {
		generate("test_protected_area_let_template_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateForBlockMerge() {
		generate("test_protected_area_let_template_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateForBlock() {
		generate("test_protected_area_if_template_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateForBlockMerge() {
		generate("test_protected_area_if_template_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateLetBlock() {
		generate("test_protected_area_template_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateLetBlockMerge() {
		generate("test_protected_area_template_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateLetBlock() {
		generate("test_protected_area_for_template_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateTetBlockMerge() {
		generate("test_protected_area_for_template_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateLetBlock() {
		generate("test_protected_area_let_template_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateLetBlockMerge() {
		generate("test_protected_area_let_template_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateLetBlock() {
		generate("test_protected_area_if_template_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateLetBlockMerge() {
		generate("test_protected_area_if_template_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfBlock() {
		generate("test_protected_area_template_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfBlockMerge() {
		generate("test_protected_area_template_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateIfBlock() {
		generate("test_protected_area_for_template_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaForTemplateIfBlockMerge() {
		generate("test_protected_area_for_template_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateIfBlock() {
		generate("test_protected_area_let_template_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetTemplateIfBlockMerge() {
		generate("test_protected_area_let_template_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateIfBlock() {
		generate("test_protected_area_if_template_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaIfTemplateIfBlockMerge() {
		generate("test_protected_area_if_template_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileBlock() {
		generate("test_protected_area_template_file_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileBlockMerge() {
		generate("test_protected_area_template_file_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileForBlock() {
		generate("test_protected_area_template_file_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileForBlockMerge() {
		generate("test_protected_area_template_file_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileLetBlock() {
		generate("test_protected_area_template_file_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileLetBlockMerge() {
		generate("test_protected_area_template_file_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileIfBlock() {
		generate("test_protected_area_template_file_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileIfBlockMerge() {
		generate("test_protected_area_template_file_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileForIfBlock() {
		generate("test_protected_area_template_file_for_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileForIfBlockMerge() {
		generate("test_protected_area_template_file_for_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForIfBlock() {
		generate("test_protected_area_template_file_template_for_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForIfBlockMerge() {
		generate("test_protected_area_template_file_template_for_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForForBlock() {
		generate("test_protected_area_template_file_template_for_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForForBlockMerge() {
		generate("test_protected_area_template_file_template_for_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForLetBlock() {
		generate("test_protected_area_template_file_template_for_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateForLetBlockMerge() {
		generate("test_protected_area_template_file_template_for_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfForBlock() {
		generate("test_protected_area_template_file_template_if_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfForBlockMerge() {
		generate("test_protected_area_template_file_template_if_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfLetBlock() {
		generate("test_protected_area_template_file_template_if_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfLetBlockMerge() {
		generate("test_protected_area_template_file_template_if_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfIfBlock() {
		generate("test_protected_area_template_file_template_if_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateIfIfBlockMerge() {
		generate("test_protected_area_template_file_template_if_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetForBlock() {
		generate("test_protected_area_template_file_template_let_for_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetForBlockMerge() {
		generate("test_protected_area_template_file_template_let_for_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetLetBlock() {
		generate("test_protected_area_template_file_template_let_let_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetLetBlockMerge() {
		generate("test_protected_area_template_file_template_let_let_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetIfBlock() {
		generate("test_protected_area_template_file_template_let_if_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateFileTemplateLetIfBlockMerge() {
		generate("test_protected_area_template_file_template_let_if_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForIfElseTemplateBlock() {
		generate("test_protected_area_template_if_file_for_if_else_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForIfElseTemplateBlockMerge() {
		generate("test_protected_area_template_if_file_for_if_else_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForLetElseTemplateBlock() {
		generate("test_protected_area_template_if_file_for_let_else_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForLetElseTemplateBlockMerge() {
		generate("test_protected_area_template_if_file_for_let_else_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForLetElseLetTemplateBlock() {
		generate("test_protected_area_template_if_file_for_let_elselet_template_block"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaTemplateIfFileForLetElseLetTemplateBlockMerge() {
		generate("test_protected_area_template_if_file_for_let_elselet_template_block_merge"); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaMultipleGenerations() {
		generate("test_protected_area_multiple_generations", 0); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaPerformanceMultipleGenerations() {
		generate("test_protected_area_performance_multiple_generations", 0); //$NON-NLS-1$
	}

	@Test
	@Ignore
	public void testProtectedAreaVariableMarkerIdMultipleGenerations() {
		generate("test_protected_area_variable_marker_id_multiple_generations", 1); //$NON-NLS-1$
	}

	@Test
	@Ignore
	public void testProtectedAreaConditionalMultipleGenerations() {
		generate("test_protected_area_conditional_multiple_generations", 1); //$NON-NLS-1$
	}

	@Test
	public void testProtectedAreaLetComplex() {
		generate("test_protected_area_template_let_complex", 0); //$NON-NLS-1$
	}
}
