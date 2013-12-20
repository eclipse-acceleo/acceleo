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
package org.eclipse.acceleo.engine.tests.unit.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

/**
 * This test allows us to check the behavior of the listeners that can be set up on the Acceleo generation
 * engine.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoListenersTest extends AbstractAcceleoTest {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		return "data/Listeners/listeners.mtl"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "Listeners"; //$NON-NLS-1$
	}

	/**
	 * Tests that the generation listeners are accurately called.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testAcceleoGenerationListener() throws IOException {
		this.init("Listeners"); //$NON-NLS-1$
		AcceleoGenerationCountListener listener = new AcceleoGenerationCountListener();
		AcceleoService service = new AcceleoService(defaultStrategy);
		service.addListener(listener);

		generate(service, "test_generation_listeners"); //$NON-NLS-1$

		int eClassCount = 0;
		final TreeIterator<EObject> iterator = inputModel.eAllContents();
		while (iterator.hasNext()) {
			EObject next = iterator.next();
			if (next instanceof EClass) {
				eClassCount++;
			}
		}

		// We know 24 text generations events were fired. If this figure changes, check generated files.
		final int generationCount = 24;
		assertSame("Unexpected count of text generations.", generationCount, listener.generationCount); //$NON-NLS-1$
		assertSame("Wrong count of generated files.", eClassCount, listener.generatedFileCount); //$NON-NLS-1$

		service.removeListener(listener);
	}

	/**
	 * Tests that the generation events are initialized with the accurated data.
	 * 
	 * @throws IOException
	 *             Thrown when the output cannot be saved.
	 */
	@Test
	public void testAcceleoGenerationEvent() throws IOException {
		this.init("Events"); //$NON-NLS-1$

		AcceleoGenerationEventTestListener listener = new AcceleoGenerationEventTestListener();
		AcceleoService service = new AcceleoService(previewStrategy);
		service.addListener(listener);

		final Map<String, String> preview = generate(service, "test_generation_event"); //$NON-NLS-1$

		assertFalse("There should have been a preview generated.", preview.isEmpty()); //$NON-NLS-1$
		assertSame("We expected a single preview to be available.", 1, preview.size()); //$NON-NLS-1$

		final Entry<String, String> previewEntry = preview.entrySet().iterator().next();
		// We know the file block is the first element of the second template
		final EObject fileBlock = module.eContents().get(2).eContents().get(0);

		// file generation event
		assertSame("The FileBlock hasn't been set accurately on the file generation event.", //$NON-NLS-1$
				fileBlock, listener.fileSourceBlock);
		assertSame("Source element hasn't been set accurately on the file generation event.", inputModel, //$NON-NLS-1$
				listener.fileSourceElement);
		assertEquals("File path hasn't been set accurately on the file generation event.", previewEntry //$NON-NLS-1$
				.getKey(), listener.generatedFile);

		// text generation event
		assertSame("The FileBlock hasn't been set accurately on the text generation event.", fileBlock, //$NON-NLS-1$
				listener.textSourceBlock);
		assertSame("Source element hasn't been set accurately on the text generation event.", inputModel, //$NON-NLS-1$
				listener.textSourceElement);
		assertEquals("File path hasn't been set accurately on the text generation event.", previewEntry //$NON-NLS-1$
				.getKey(), listener.generatedFile);

		service.removeListener(listener);
	}

	/**
	 * This implementation of an AcceleoTextGenerationListener allows us to check that the accurate info is
	 * set on the generation events.
	 */
	private class AcceleoGenerationEventTestListener implements IAcceleoTextGenerationListener {
		/** EObject for which text has been generated. */
		EObject textSourceElement;

		/** Block which triggered the text generation. */
		Block textSourceBlock;

		/** Computed path for the generated file. */
		String generatedFile;

		/** EObject for which a file has been created. */
		EObject fileSourceElement;

		/** Block for which this path has been calculated. */
		Block fileSourceBlock;

		/**
		 * Increases visibility of the default constructor.
		 */
		public AcceleoGenerationEventTestListener() {
			// increases visibility
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#textGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
		 */
		public void textGenerated(AcceleoTextGenerationEvent event) {
			textSourceBlock = event.getBlock();
			textSourceElement = event.getSource();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#filePathComputed(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
		 */
		public void filePathComputed(AcceleoTextGenerationEvent event) {
			fileSourceBlock = event.getBlock();
			fileSourceElement = event.getSource();
			generatedFile = event.getText();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#fileGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
		 */
		public void fileGenerated(AcceleoTextGenerationEvent event) {
			// naught to do
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#generationEnd(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
		 */
		public void generationEnd(AcceleoTextGenerationEvent event) {
			// naught to do
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#listensToGenerationEnd()
		 */
		public boolean listensToGenerationEnd() {
			return false;
		}
	}

	/**
	 * This implementation of an AcceleoTextGenerationListener allows us to count the number of times the
	 * engine generated text.
	 */
	private class AcceleoGenerationCountListener implements IAcceleoTextGenerationListener {
		/** This will be incremented for each call to {@link #textGenerated(AcceleoTextGenerationEvent)}. */
		int generationCount;

		/** This will be incremented for each call to {@link #filePathComputed(AcceleoTextGenerationEvent)}. */
		int generatedFileCount;

		/**
		 * Increases visibility of the default constructor.
		 */
		public AcceleoGenerationCountListener() {
			// increases visibility
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#textGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
		 */
		public void textGenerated(AcceleoTextGenerationEvent event) {
			generationCount++;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#filePathComputed(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
		 */
		public void filePathComputed(AcceleoTextGenerationEvent event) {
			generatedFileCount++;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#fileGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
		 */
		public void fileGenerated(AcceleoTextGenerationEvent event) {
			// naught to do
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#generationEnd(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
		 */
		public void generationEnd(AcceleoTextGenerationEvent event) {
			// naught to do
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#listensToGenerationEnd()
		 */
		public boolean listensToGenerationEnd() {
			return false;
		}
	}
}
