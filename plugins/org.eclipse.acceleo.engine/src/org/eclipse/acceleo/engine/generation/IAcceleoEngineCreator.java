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
package org.eclipse.acceleo.engine.generation;

/**
 * Implementers of this class will be used to create Acceleo engines and determine whether or not they can be
 * created.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.0
 */
public interface IAcceleoEngineCreator {
	/**
	 * This will be called internally by the {@link org.eclipse.acceleo.engine.service.AcceleoService} to
	 * determine whether it can create this engine. If you need to check preferences or any other mean to
	 * activate your engine, do it through this method.
	 * 
	 * @return <code>true</code> if the engine can be created, <code>false</code> otherwise.
	 */
	boolean canBeCreated();

	/**
	 * This will be called internally by the {@link org.eclipse.acceleo.engine.service.AcceleoService} after
	 * confirming that this engine can be created through {@link #canBeCreated()}.
	 * 
	 * @return The created Acceleo engine.
	 */
	IAcceleoEngine createEngine();
}
