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
package org.eclipse.acceleo.engine.tests.unit.generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.emf.common.util.BasicMonitor;
import org.junit.Test;

/**
 * This will allow us to check that the progress monitor can be properly be used to cancel the Acceleo
 * evaluation.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoProgressMonitorTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/ProgressMonitor/progress_monitor.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "ProgressMonitor";
	}

	/**
	 * Tests the behavior of the evaluation engine with a progress monitor that will automatically be canceled
	 * after a while.
	 */
	@Test
	public void testCanceledEvaluation() {
		final Map<String, String> preview = new AcceleoService(previewStrategy).doGenerate(module,
				"test_cancel", inputModel, generationRoot, new CanceledProgressMonitor());

		assertTrue(preview.isEmpty());
	}

	/**
	 * Checks that using a null progress monitor doesn't fail : a BasicMonitor should have been created.
	 */
	@Test
	public void testNullMonitor() {
		final Map<String, String> preview = new AcceleoService(previewStrategy).doGenerate(module, "test",
				inputModel, generationRoot, null);

		final String writer = preview.get("progress_monitor");
		assertNotNull("A file should have been generated.", writer);
		assertEquals("Unexpected content for the generated file.", "constant output", writer.toString()
				.trim());
	}

	/**
	 * This monitor will cancel itself after 20 checks.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	class CanceledProgressMonitor extends BasicMonitor {
		/** Counts the number of times the monitor is checked for cancellation. */
		private int checks;

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.common.util.BasicMonitor#isCanceled()
		 */
		@Override
		public boolean isCanceled() {
			if (checks < 20) {
				checks++;
			} else {
				setCanceled(true);
			}
			return super.isCanceled();
		}
	}
}
