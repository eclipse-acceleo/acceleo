/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
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
}
