/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.event;

import java.io.File;

import org.eclipse.emf.common.util.Monitor;

/**
 * Default implementation of an {@link IAcceleoTextGenerationListener}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.1
 */
public abstract class AbstractAcceleoTextGenerationListener implements IAcceleoTextGenerationListener {

	/**
	 * The generation ID.
	 */
	protected String generationID;

	/**
	 * Indicates that the generation has started.
	 * 
	 * @param monitor
	 *            The monitor
	 * @param targetFolder
	 *            The target folder
	 * @since 3.1
	 */
	public void generationStart(Monitor monitor, File targetFolder) {
		// Empty implementation
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#fileGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void fileGenerated(AcceleoTextGenerationEvent event) {
		// Empty implementation
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#filePathComputed(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void filePathComputed(AcceleoTextGenerationEvent event) {
		// Empty implementation
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#generationEnd(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void generationEnd(AcceleoTextGenerationEvent event) {
		// Empty implementation
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#listensToGenerationEnd()
	 */
	public boolean listensToGenerationEnd() {
		// Empty implementation
		return false;
	}

	/**
	 * This method is called when a generation is completed in order to indicates the result of the complete
	 * generation. It is called after {@link #generationEnd(AcceleoTextGenerationEvent)}.
	 * 
	 * @since 3.1
	 */
	public void generationCompleted() {
		// Empty implementation
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener#textGenerated(org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent)
	 */
	public void textGenerated(AcceleoTextGenerationEvent event) {
		// Empty implementation
	}

	/**
	 * Sets the generation ID.
	 * 
	 * @param generationID
	 *            The generation ID.
	 * @since 3.1
	 */
	public void setGenerationID(String generationID) {
		this.generationID = generationID;
	}
}
