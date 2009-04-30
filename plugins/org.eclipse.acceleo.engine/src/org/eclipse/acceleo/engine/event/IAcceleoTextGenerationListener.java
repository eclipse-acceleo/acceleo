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
package org.eclipse.acceleo.engine.event;

/**
 * Instances of this listener will be notified whenever text is generated from an Acceleo Block.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 0.8
 */
public interface IAcceleoTextGenerationListener {
	/**
	 * This will be called whenever text is generated from an Acceleo Block.
	 * 
	 * @param event
	 *            This will hold information on the generation details.
	 */
	void textGenerated(AcceleoTextGenerationEvent event);

	/**
	 * This will be called by the engine whenever a path has been calculated for a file block. In essence, it
	 * tells registered listener that a given source EObject will generate a file at the given path. Path can
	 * be retrieved as the text from the event.
	 * 
	 * @param event
	 *            This will hold information on the generation details.
	 */
	void filePathComputed(AcceleoTextGenerationEvent event);
}
