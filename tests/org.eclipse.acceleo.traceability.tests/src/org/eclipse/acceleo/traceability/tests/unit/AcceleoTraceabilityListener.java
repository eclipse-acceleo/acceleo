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
package org.eclipse.acceleo.traceability.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoTraceabilityListener implements IAcceleoTextGenerationListener {

	/**
	 * Indicates if we are going to listen to the end of the generation.
	 */
	private boolean listenToGenerationEnd;

	/**
	 * The generated files.
	 */
	private List<GeneratedFile> generatedFiles = new ArrayList<GeneratedFile>();

	/**
	 * The constructor.
	 * 
	 * @param listenToEnd
	 *            Indicates if we listen the event indicating the end of the generation
	 */
	public AcceleoTraceabilityListener(boolean listenToEnd) {
		this.listenToGenerationEnd = listenToEnd;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#textGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void textGenerated(AcceleoTextGenerationEvent event) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#filePathComputed(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void filePathComputed(AcceleoTextGenerationEvent event) {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#fileGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void fileGenerated(AcceleoTextGenerationEvent event) {
		EObject traceabilityInformation = event.getTraceabilityInformation();
		if (traceabilityInformation instanceof GeneratedFile) {
			GeneratedFile generatedFile = (GeneratedFile)traceabilityInformation;
			this.generatedFiles.add(generatedFile);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#generationEnd(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void generationEnd(AcceleoTextGenerationEvent event) {
		// do nothing
	}

	/**
	 * Returns the list of all the generated files.
	 * 
	 * @return The list of all the generated files.
	 */
	public List<GeneratedFile> getGeneratedFiles() {
		return generatedFiles;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#listensToGenerationEnd()
	 */
	public boolean listensToGenerationEnd() {
		return this.listenToGenerationEnd;
	}

}
